/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.client.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.kvlahov.model.Category;
import com.kvlahov.model.Product;
import com.kvlahov.model.RegistryUser;
import com.kvlahov.services.RegistryService;
import com.kvlahov.utilities.ActionButtonTableCell;
import com.kvlahov.utilities.MyCellEditHandler;
import com.kvlahov.utilities.SafeDoubleStringConverter;
import com.kvlahov.utilities.UIHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;

/**
 * FXML Controller class
 *
 * @author evlakre
 */
public class ProductsFXMLController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Long> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Category> productCategoryColumn;
    @FXML
    private TableColumn<Product, Button> productActionButtonColumn;

    //category table
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, Long> categoryIdColumn;
    @FXML
    private TableColumn<Category, String> categoryNameColumn;
    @FXML
    private TableColumn<Category, Button> categoryActionButtonColumn;
    
    //product form
    @FXML
    private JFXTextField tfProductName;
    @FXML
    private JFXTextField tfProductPrice;
    @FXML
    private JFXComboBox<Category> cbCategory;

    //category form
    @FXML
    private JFXTextField tfCategoryName;

    private RegistryService registryService;
    private ObservableList<Product> products;
    private ObservableList<Category> categories;

    @FXML
    public void handleAddProductClick(ActionEvent e) {
        if (tfProductName.validate() && tfProductPrice.validate()) {
            Product p = new Product(tfProductName.getText(), Double.parseDouble(tfProductPrice.getText()), cbCategory.getValue());
            registryService.insertProduct(p);
            products.add(p);
            tfProductName.clear();
            tfProductPrice.clear();
        } else {
            tfProductName.requestFocus();
        }
    }

    @FXML
    public void handleAddCategoryClick(ActionEvent e) {
        if (tfCategoryName.validate()) {
            Category c = new Category(tfCategoryName.getText());
            registryService.insertCategory(c);
            categories.add(c);
            tfCategoryName.clear();
        } else {
            tfCategoryName.requestFocus();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        initTableView();
        initCategoryTableView();
    }

    private void init() {
        registryService = new RegistryService();
        products = FXCollections.observableArrayList(registryService.getProducts());
        categories = FXCollections.observableArrayList(registryService.getCategories());

        setupProductForm();
        setupCategoryForm();

    }

    private void setupProductForm() {
        ValidatorBase validator = UIHelper.getCustomValidator(name -> productExists(name));
        validator.setMessage("Name already exists");
        tfProductName.setValidators(new RequiredFieldValidator("Name must not be empty"), validator);

        ValidatorBase priceValidator = UIHelper.getCustomValidator(text -> text.equals("0"));
        tfProductPrice.setValidators(new RequiredFieldValidator("Price must not be empty"), priceValidator);
        tfProductPrice.setTextFormatter(UIHelper.getLongTextFormatter());
        cbCategory.setItems(categories);
        cbCategory.setValue(categories.get(0));
    }

    private void setupCategoryForm() {
        ValidatorBase validator = UIHelper.getCustomValidator(name -> categoryExists(name));
        validator.setMessage("Name already exists");
        tfCategoryName.setValidators(new RequiredFieldValidator("Name must not be empty"), validator);
    }

    private void initTableView() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new SafeDoubleStringConverter()));
        productCategoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn(categories));
        productActionButtonColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", p -> {
            UIHelper.showWarningDialog("Are you sure you want to remove this product?", b -> {
                products.remove(p);
                registryService.deleteProduct(p);
            });
        }));

        MyCellEditHandler<Product, String> handler = new MyCellEditHandler<>();
        handler.setAlertMessage("Product already exists");
        handler.setValidationPredicate(newVal -> !productExists(newVal) && !newVal.isEmpty());
        handler.setValidationSuccessConsumer((rowVal, celVal) -> {
            rowVal.setName(celVal);
            registryService.updateProduct(rowVal);
        });

        productNameColumn.setOnEditCommit(handler);

        productCategoryColumn.setOnEditCommit(event -> {
            Category newValue = event.getNewValue();

            Product p = event.getRowValue();
            p.setCategory(newValue);
            registryService.updateProduct(p);
        });

//        productPriceColumn.setOnEditCommit(event -> {
//            Double newValue = event.getNewValue();
//            if (newValue != null && newValue > 0) {
//                Product p = event.getRowValue();
//                p.setPrice(newValue);
//                registryService.updateProduct(p);
//            } else {
//                UIHelper.showInfoAlert("Price must be a number greater than 0!");
//                event.getTableView().refresh();
//            }
//        });
        MyCellEditHandler<Product, Double> priceHandler = new MyCellEditHandler<>(
                newVal -> newVal != null && newVal > 0,
                "Price must be a number greater than 0!",
                (p, newVal) -> {
                    p.setPrice(newVal);
                    registryService.updateProduct(p);
                }
        );
        productPriceColumn.setOnEditCommit(priceHandler);

        productTable.setItems(products);
    }

    private boolean productExists(String productName) {
        return products.stream().anyMatch(p -> p.getName().equals(productName));
    }

    private boolean categoryExists(String name) {
        return categories.stream().anyMatch(c -> c.getName().equals(name));
    }

    private void initCategoryTableView() {
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryActionButtonColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", c -> {
            UIHelper.showWarningDialog("Are you sure you want to remove this category and all of its products?", b -> {
                categories.remove(c);
                products.removeIf(p -> p.getCategory().equals(c));
                registryService.deleteCategory(c);
            });
        }));
        
        MyCellEditHandler<Category, String> handler = new MyCellEditHandler<>();
        handler.setAlertMessage("Category already exists");
        handler.setValidationPredicate(newVal -> !categoryExists(newVal) && !newVal.isEmpty());
        handler.setValidationSuccessConsumer((category, newVal) -> {
            String oldName = category.getName();
            category.setName(newVal);
            if(registryService.updateCategory(category)){
                products.stream()
                        .filter(p -> p.getCategory().getName().equals(oldName))
                        .forEach(p -> p.setCategory(category));
            }
        });

        categoryNameColumn.setOnEditCommit(handler);
        
        categoryTable.setItems(categories);
    }

}
