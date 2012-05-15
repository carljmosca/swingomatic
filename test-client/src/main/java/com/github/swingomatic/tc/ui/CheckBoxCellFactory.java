/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

/**
 *
 * @author Carl J. Mosca
 */
public class CheckBoxCellFactory implements Callback {

    @Override
    public Object call(Object p) {
         final CheckBox checkBox = new CheckBox();
                final TableCell cell = new TableCell() {

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
    
}
