/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.elements;

import com.td.trongnghia.entity.OrderEntity;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jfxtras.scene.control.LocalDateTimeTextField;

/**
 *
 * @author TRONGNGHIA
 */
public class DateEditingCell extends TableCell<OrderEntity, Timestamp> {

    private LocalDateTimeTextField dateTimePicker;

    public DateEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(dateTimePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();        
        if (getTimestamp() != null) {
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Timestamp(getTimestamp().toEpochSecond(ZoneOffset.of("+7")) * 1000));
            setText(formattedDate);
        } else {
            setText(null);            
        }
        setGraphic(null);
    }

    @Override
    public void updateItem(Timestamp item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (dateTimePicker != null) {
                    dateTimePicker.setLocalDateTime(getTimestamp());
                }
                setText(null);
                setGraphic(dateTimePicker);
            } else {
                String formattedDate = null;
                if (getTimestamp() != null) {
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Timestamp(getTimestamp().toEpochSecond(ZoneOffset.of("+7")) * 1000));
                }
                setText(formattedDate);
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        dateTimePicker = new LocalDateTimeTextField();
        dateTimePicker.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        dateTimePicker.setMinWidth(150);
        dateTimePicker.setLocalDateTime(getTimestamp());
        dateTimePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(Timestamp.valueOf(dateTimePicker.getLocalDateTime()));
            }           
        }); 

        dateTimePicker.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(Timestamp.valueOf(dateTimePicker.getLocalDateTime()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
//
        dateTimePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    commitEdit(Timestamp.valueOf(dateTimePicker.getDisplayedLocalDateTime()));
                }
            }
        });
    }

    private LocalDateTime getTimestamp() {
        if (getItem() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(getItem().getTime());
            return LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        }
        return null;
    }
}
