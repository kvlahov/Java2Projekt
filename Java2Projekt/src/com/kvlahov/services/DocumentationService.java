/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.H1;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.H3;
import com.hp.gagawa.java.elements.H4;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Li;
import com.hp.gagawa.java.elements.Link;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Title;
import com.hp.gagawa.java.elements.Ul;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author evlakre
 */
public class DocumentationService {

    private final String PATH = "data/documentation/docs.html";

    public DocumentationService() {

    }

    public boolean generateDocs() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            String html = getDocsHtml();
            bw.write(html);

            Desktop.getDesktop().open(new File(PATH));

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getDocsHtml() {
        Document document = new Document(DocumentType.XHTMLStrict);

        document.head.appendChild(new Title().appendText("Documentation"));

        Body body = document.body;

        Div container = new Div();
        container.setCSSClass("container");

        Map<String, List<String>> classesAndFields = getClassesAndFields();

        for (Map.Entry<String, List<String>> entry : classesAndFields.entrySet()) {
            H2 packageHeading = new H2().appendText(
                    entry.getKey().substring(0, entry.getKey().lastIndexOf('.'))
            );
            H3 classHeading = new H3().appendText(
                    entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1, entry.getKey().length())
            );

            P classFieldsParagraph = new P();
            Ul propsList = new Ul();

            for (String prop : entry.getValue()) {
                propsList.appendChild(new Li().appendText(prop));
            }

            classFieldsParagraph.appendChild(propsList);

            container.appendChild(packageHeading, classHeading, classFieldsParagraph);
        }

        document.body.appendChild(container);
        return document.write();

    }

    private Map<String, List<String>> getClassesAndFields() {
        final String PACKAGE_SRC = "src/com/kvlahov";

        Map<String, List<String>> map = new LinkedHashMap<>();

        File src = new File(PACKAGE_SRC);

        List<Path> files = new ArrayList<>();
        getFileNames(files, Paths.get(src.getPath()), ".java");

        for (Path file : files) {
            try {
                String pckg = file.subpath(1, file.getNameCount())
                        .toString()
                        .replace("\\", ".");

                String className = pckg.substring(0, pckg.lastIndexOf('.'));

                List<String> properties = new ArrayList<>();

                Class clazz = Class.forName(className);
                properties.addAll(
                        Stream.of(clazz.getDeclaredFields())
                                .map(this::getFieldName)
                                .collect(Collectors.toList())
                );

                properties.addAll(
                        Stream.of(clazz.getDeclaredMethods())
                                .map(this::getMethodSignature)
                                .collect(Collectors.toList())
                );

                map.put(className, properties);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DocumentationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return map;
    }

    private void getFileNames(List<Path> files, Path path, String extension) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path currentPath : stream) {
                currentPath.toString();
                if (currentPath.toFile().isDirectory()) {
                    getFileNames(files, currentPath, extension);
                } else {
                    if (currentPath.toString().endsWith(extension)) {
                        files.add(currentPath);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(DocumentationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getFieldName(Field f) {
        return String.format("%s %s %s",
                Modifier.toString(f.getModifiers()),
                f.getType().getSimpleName(),
                f.getName()
                );
    }

    private String getMethodSignature(Method m) {
        List<String> parameters = Stream
                .of(m.getParameterTypes())
                .map(p -> p.getSimpleName())
                .collect(Collectors.toList());

        return String.format("%s %s %s(%s)",
                Modifier.toString(m.getModifiers()),
                m.getReturnType().getSimpleName(),
                m.getName(),
                String.join(",", parameters)
        );
    }
}
