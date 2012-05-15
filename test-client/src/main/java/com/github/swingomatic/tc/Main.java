/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

/**
 *
 * @author Carl J. Mosca
 */
public class Main {

    @FXML
    private Button btnList;

    @FXML
    private void btnListAction(ActionEvent event) {
        load();
    }
    
    @FXML
    private TextField txtServerAddress;
    
    @FXML
    private TableView tblList;
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
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        });


    }
}
