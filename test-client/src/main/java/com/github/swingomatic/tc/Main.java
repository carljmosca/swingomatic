/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    }
    
    @FXML
    private TableView tblList;

    private static Logger logger = Logger.getLogger(Main.class);

    public void load() {
        Client client = new Client();
        ApplicationCommand ac = new ApplicationCommand();
        ac.setCommand("list-components");
        try {
            ac = client.execute("http://localhost:8088", ac);
            List<ComponentInfo> list = ac.getComponents();
            for (ComponentInfo componentInfo : list) {
                System.out.println(componentInfo.toString());
            }
            tblList.getItems().clear();
            tblList.getItems().addAll(list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }
}
