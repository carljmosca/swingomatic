/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author moscac
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FXML TableView Example");
        stage.setScene((Scene) FXMLLoader.load(getClass().getResource("fxml_tableview.fxml")));
        stage.show();
    }
}
