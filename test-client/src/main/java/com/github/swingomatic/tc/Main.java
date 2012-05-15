/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.javafxdata.control.cell.CheckBoxTableCell;

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
                    List<ComponentInfo> list = ac.getComponents();
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
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

           Callback<TableColumn, TableCell> selectecCellFactory = new Callback<TableColumn, TableCell>() {

            @Override
            public TableCell call(final TableColumn param) {
                final CheckBox checkBox = new CheckBox();
                final CheckBoxTableCell cell = new CheckBoxTableCell() {

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            checkBox.setDisable(true);
                            checkBox.setSelected(false);
                        } else {
                            checkBox.setDisable(false);
                            checkBox.setSelected(item.toString().equals("Yes") ? true : false);
                            commitEdit(checkBox.isSelected() ? "Yes" : "No");
                        }
                    }
                };
                cell.setGraphic(checkBox);
                return cell;
            }
        };
 
        colSelected.setCellFactory(selectecCellFactory);
    }
}
