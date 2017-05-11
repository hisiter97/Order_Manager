/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.util.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class StatusController implements Initializable {

    @FXML
    private Button confirmBtn;
    @FXML
    private ChoiceBox<String> statusCB;
    private OrderEntity currentOrder;
    private TableView orderTable;

    private static String NEW = "Mới tạo";
    private static String RECEIVED = "Đã nhận";
    private static String SHIPPED = "Đã giao";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusCB.getItems().addAll(new String[]{NEW, RECEIVED, SHIPPED});
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (statusCB.getValue().equals(RECEIVED)) {
                    currentOrder.setStatus(2);
                } else if (statusCB.getValue().equals(SHIPPED)) {
                    currentOrder.setStatus(1);
                } else if (statusCB.getValue().equals(NEW)) {
                    currentOrder.setStatus(0);
                }
                Util.orderDAO.update(currentOrder);
                orderTable.refresh();
                Util.showNotification(UIConstants.TASK_DONE, "Cập nhật Trạng thái thành công");
            }
        });
    }

    public void setCurrentOrder(OrderEntity orderEntity) {
        this.currentOrder = orderEntity;
    }
    
    public void setOrderTable(TableView orderTable) {
        this.orderTable = orderTable;
    }
}
