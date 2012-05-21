/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.github.swingomatic.tc.ui.CheckBoxCell;
import com.github.swingomatic.tc.util.ClipboardUtility;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 *
 * @author Carl J. Mosca
 */
public class Main implements Initializable {

    @FXML
    private Button btnList;

    @FXML
    private void btnListAction(ActionEvent event) {
        load();
    }

    @FXML
    private void btnGenerateAction(ActionEvent event) {
        createOutput();
    }
    @FXML
    private TextField txtServerAddress;
    @FXML
    private Label lblStatus;
    @FXML
    private TableView tblList;
    @FXML
    private TableColumn colSelected;
    private static Logger logger = Logger.getLogger(Main.class);
    private ObservableList<ComponentInfo> list = FXCollections.observableList(new ArrayList<ComponentInfo>(0));

    public void load() {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                String host = "http://localhost:8088";
                if (txtServerAddress.getText().length() > 0) {
                    host = txtServerAddress.getText();
                }
                Client client = new Client();
                ApplicationCommand ac = new ApplicationCommand();
                ac.setCommand("list-components");
                try {
                    ac = client.execute(host, ac);
                    list.addAll(ac.getComponents());
                    tblList.setItems(list);
                    lblStatus.setText("Done");
                } catch (Exception ex) {
                    lblStatus.setText(ex.getMessage());
                }
            }
        });
    }

    private void createOutput() {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("List<ComponentInfo> list = new ArrayList<ComponentInfo>(0);\n");
        javaCode.append("ComponentInfo componentInfo;\n");
        int selected = 0;
        for (ComponentInfo componentInfo : list) {
            if (componentInfo.isSelected()) {
                selected++;
                javaCode.append("componentInfo = new ComponentInfo();\n");
                if (componentInfo.getCaption() != null) {
                    javaCode.append("componentInfo.setOfLabel(\"").append(componentInfo.getCaption()).append("\");\n");
                }
                if (componentInfo.getCaption() != null) {
                    javaCode.append("componentInfo.setClazz(\"").append(componentInfo.getClazz()).append("\");\n");
                }
                if (componentInfo.getCommand() != null) {
                    javaCode.append("componentInfo.setCommand(\"").append(componentInfo.getCommand()).append("\");\n");
                }
                javaCode.append("componentInfo.setDelay(").append(componentInfo.getDelay()).append(");\n");
                if (componentInfo.getName() != null) {
                    javaCode.append("componentInfo.setName(\"").append(componentInfo.getName()).append("\");\n");
                }
                if (componentInfo.getOfLabel() != null) {
                    javaCode.append("componentInfo.setOfLabel(\"").append(componentInfo.getOfLabel()).append("\");\n");
                }
                javaCode.append("componentInfo.setRetries(").append(componentInfo.getRetries()).append(");\n");
                if (componentInfo.getText() != null) {
                    javaCode.append("componentInfo.setText(\"").append(componentInfo.getText()).append("\");\n");
                }
                if (componentInfo.getToolTipText() != null) {
                    javaCode.append("componentInfo.setToolTipText(\"").append(componentInfo.getToolTipText()).append("\");\n");
                }
                javaCode.append("componentInfo.setxCoordinate(").append(componentInfo.getxCoordinate()).append(");\n");
                javaCode.append("componentInfo.setyCoordinate(").append(componentInfo.getyCoordinate()).append(");\n");

                javaCode.append("list.add(componentInfo);\n");
            }
        }
        if (selected > 0) {
            ClipboardUtility.pasteToClipboard(javaCode.toString());
        }
        lblStatus.setText(selected + " selected items" + (selected > 0 ? " code is on clipboard" : ""));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Callback<TableColumn<TableView, Boolean>, TableCell<TableView, Boolean>> booleanCellFactory =
                new Callback<TableColumn<TableView, Boolean>, TableCell<TableView, Boolean>>() {

                    @Override
                    public TableCell<TableView, Boolean> call(TableColumn<TableView, Boolean> p) {
                        return new CheckBoxCell();
                    }
                };
        colSelected.setCellValueFactory(new PropertyValueFactory<TableView, Boolean>("selected"));
        colSelected.setCellFactory(booleanCellFactory);
    }
}
