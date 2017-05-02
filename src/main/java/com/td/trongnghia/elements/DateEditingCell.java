/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.elements;

import com.td.trongnghia.entity.OrderEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;

/**
 *
 * @author TRONGNGHIA
 */
public class DateEditingCell extends TableCell<OrderEntity, Date> {

    private DatePicker datePicker;

    public DateEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        Date date = Date.from(getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        setText(formattedDate);
        setGraphic(null);
    }

    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                String formattedDate = null;
                if (getDate() != null) {
                    Date date = Date.from(getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                }
                setText(formattedDate);
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        });
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        });
    }

    private LocalDate getDate() {
        return getItem() == null ? null : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
