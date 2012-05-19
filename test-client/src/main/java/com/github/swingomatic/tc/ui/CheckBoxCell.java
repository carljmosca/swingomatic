/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc.ui;

import com.github.swingomatic.message.ComponentInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Carl J. Mosca
 */
public class CheckBoxCell extends TableCell<TableView, Boolean> {

    private CheckBox checkBox;

    public CheckBoxCell() {
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            getCheckBox().setSelected(item);
        }
    }

    public CheckBox getCheckBox() {
        if (checkBox == null) {
            checkBox = new CheckBox();
            setGraphic(checkBox);
            checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ComponentInfo componentInfo = (ComponentInfo) getTableRow().getItem();
                    componentInfo.setSelected(checkBox.isSelected());
                }
            ;
        }
        );
	}
	checkBox.setDisable(!getTableColumn().isEditable());
        return checkBox;
    }
}
