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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

        colSelected.setCellFactory(new Callback() {

            @Override
            public Object call(Object p) {
                ComponentInfo componentInfo = (ComponentInfo)p;
                ObservableBooleanValue bp = new SimpleBooleanProperty(componentInfo, "selected");
                return bp;
            }
        });
        //colSelected.setCellFactory(new CheckBoxCellFactory());
        //colSelected.setCellValueFactory(n);
    }
}
