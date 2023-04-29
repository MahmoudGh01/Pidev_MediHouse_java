/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

/**
 *
 * @author chaab
 */
import javafx.scene.control.TableCell;

public class BooleanTableCell<S, T> extends TableCell<S, Boolean> {
    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
        } else {
            setText(item ? "Activé" : "Désactivé");
        }
    }
}

