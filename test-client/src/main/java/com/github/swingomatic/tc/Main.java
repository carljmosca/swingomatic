/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.github.swingomatic.tc.ui.CheckBoxCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
    private List<ComponentInfo> list = FXCollections.observableList(new ArrayList<ComponentInfo>(0));

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
                    list = ac.getComponents();
                    tblList.getItems().clear();
                    tblList.getItems().addAll(list);
                    lblStatus.setText("Done");
                } catch (Exception ex) {
                    lblStatus.setText(ex.getMessage());
                }
            }
        });
    }

    private void createOutput() {
        int selected = 0;
        for (ComponentInfo componentInfo : list) {
            if (componentInfo.isSelected()) {
                selected++;
                
            }
        }
        lblStatus.setText(selected + "selected items");

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
