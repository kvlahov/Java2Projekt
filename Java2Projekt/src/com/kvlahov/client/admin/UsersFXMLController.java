/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.model.User;
import com.kvlahov.model.enums.UserRoleEnum;
import com.kvlahov.services.AccountService;
import com.kvlahov.utilities.ActionButtonTableCell;
import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class UsersFXMLController implements Initializable {

    @FXML
    private TableView<User> appUsersTable;
    @FXML
    private TableColumn<User, Integer> appUsersIdColumn;
    @FXML
    private TableColumn<User, String> appUsersUsernameColumn;
    @FXML
    private TableColumn<User, String> appUsersRoleColumn;
    @FXML
    private TableColumn<User, Button> appUsersActionButtonColumn;

    //Registry user
    @FXML
    private TableView<RegistryUser> registryUsersTable;
    @FXML
    private TableColumn<RegistryUser, Integer> registryUsersIdColumn;
    @FXML
    private TableColumn<RegistryUser, Long> registryUsersPinColumn;
    @FXML
    private TableColumn<RegistryUser, String> registryUsersUsernameColumn;
    @FXML
    private TableColumn<RegistryUser, Button> registryUsersActionButtonColumn;

    //App users
    private AccountService accountService;
    private ObservableList<User> usersList;
    private List<String> userRoleValues;

    //Registry users
    private ObservableList<RegistryUser> registryUserList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountService = new AccountService();
        usersList = FXCollections.observableArrayList(accountService.getUsers());
        userRoleValues = Stream.of(UserRoleEnum.values()).map(e -> e.toString()).collect(Collectors.toList());

        registryUserList = FXCollections.observableArrayList(accountService.getRegistryUsers());

        setupAppUserTable();
        setupRegistryUserTable();

        setupNewAppUserForm();

        setupNewRegistryUserForm();
    }

    private void setupNewRegistryUserForm() {
        ValidatorBase pinValidator = new ValidatorBase() {
            @Override
            protected void eval() {
                String input = tfRegistryPin.getText();
                if (input == null || input.isEmpty()) {
//                    hasErrors.set(true);
                    return;
                }
                if (userWithPinExists(Long.parseLong(input))) {
                    hasErrors.set(true);
                } else {
                    hasErrors.set(false);
                }
            }
        };
        pinValidator.setMessage("Pin is already taken");
        tfRegistryPin.setValidators(new RequiredFieldValidator("Pin must not be empty"), pinValidator);
        tfRegistryPin.setTextFormatter(UIHelper.getLongTextFormatter());
        tfRegistryUsername.setValidators(new RequiredFieldValidator("Name can't be empty"));
    }

    private void setupNewAppUserForm() {
        ValidatorBase validator = new ValidatorBase() {
            @Override
            protected void eval() {
                if (userExists(tfAppUsername.getText())) {
                    hasErrors.set(true);
                } else {
                    hasErrors.set(false);
                }
            }
        };

        RequiredFieldValidator reqvalidator = new RequiredFieldValidator("Username is required");

        validator.setMessage("Username already exists!");
        tfAppUsername.setValidators(validator, reqvalidator);

        cbAppRole.setItems(FXCollections.observableArrayList(userRoleValues));
        cbAppRole.setValue(userRoleValues.get(0));
    }

    private void setupAppUserTable() {
        appUsersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appUsersUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        appUsersRoleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole().toString()));

        String[] roleValuesArr = new String[userRoleValues.size()];

        roleValuesArr = userRoleValues.toArray(roleValuesArr);

        appUsersUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        appUsersRoleColumn.setCellFactory(ComboBoxTableCell.forTableColumn(roleValuesArr));
        appUsersActionButtonColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", u -> {
            Optional<ButtonType> buttonType = showWarningDialog("Are you sure you want to delete this user?");

            if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                usersList.remove(u);
                accountService.removeUser(u);
            }

        }));

        appUsersUsernameColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            String value = newValue != null && !newValue.isEmpty() ? newValue : event.getOldValue();

            if (!userExists(value)) {
                event.getRowValue().setUsername(value);
                accountService.updateUser(event.getRowValue());
            } else {
                showInfoAlert("Username already exists");
                event.getTableView().refresh();
            }
        });
        appUsersRoleColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            String value = newValue != null && !newValue.isEmpty() ? newValue : event.getOldValue();

            event.getRowValue().setRole(UserRoleEnum.valueOf(value.toUpperCase()));
            accountService.updateUser(event.getRowValue());
        });

        appUsersTable.setItems(usersList);
    }

    private boolean userExists(String username) {
        return usersList.stream().map(u -> u.getUsername()).anyMatch(u -> u.equals(username));
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    private JFXTextField tfAppUsername;
    @FXML
    private JFXComboBox<String> cbAppRole;

    @FXML
    public void handleAddAppUserClick(ActionEvent event) {
        if (tfAppUsername.validate()) {
            User newUser = new User(tfAppUsername.getText(), "password", UserRoleEnum.valueOf(cbAppRole.getValue().toUpperCase()));
            usersList.add(newUser);
            tfAppUsername.clear();
            accountService.addUser(newUser);
        } else {
            tfAppUsername.requestFocus();
        }
    }

    @FXML
    private JFXTextField tfRegistryUsername;
    @FXML
    private JFXTextField tfRegistryPin;

    @FXML
    public void handleAddRegistryUserClick(ActionEvent event) {
        if (tfRegistryPin.validate() && tfRegistryUsername.validate()) {
            RegistryUser ru = new RegistryUser(Long.parseLong(tfRegistryPin.getText()), tfRegistryUsername.getText());
            registryUserList.add(ru);
            tfRegistryPin.clear();
            tfRegistryUsername.clear();
            
            accountService.addRegistryUser(ru);

        } else {
            tfRegistryPin.requestFocus();
        }
    }

    private void setupRegistryUserTable() {
        registryUsersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        registryUsersUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        registryUsersPinColumn.setCellValueFactory(new PropertyValueFactory<>("pin"));

        registryUsersUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        registryUsersPinColumn.setCellFactory();

        registryUsersActionButtonColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", u -> {
            Optional<ButtonType> buttonType = showWarningDialog("Are you sure you want to delete this user?");

            if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                registryUserList.remove(u);
                accountService.removeRegistryUser(u);
            }
        }));

        registryUsersPinColumn.setOnEditCommit(event -> {
            Long newValue = event.getNewValue();
            Long value = newValue != null ? newValue : event.getOldValue();

            if (!userWithPinExists(value)) {
                event.getRowValue().setPin(value);
                accountService.updateRegistryUser(event.getRowValue());
            } else {
                showInfoAlert("Pin is already taken");
                event.consume();
            }
        });
        registryUsersUsernameColumn.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            String value = newValue != null && !newValue.isEmpty() ? newValue : event.getOldValue();

            event.getRowValue().setUsername(value);
            accountService.updateRegistryUser(event.getRowValue());
        });

        registryUsersTable.setItems(registryUserList);
    }

    private Optional<ButtonType> showWarningDialog(final String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType;
    }

    private boolean userWithPinExists(long value) {
        return registryUserList.stream()
                .map(u -> u.getPin())
                .anyMatch(p -> p == value);
    }

}
