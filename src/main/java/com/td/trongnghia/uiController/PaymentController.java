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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class PaymentController implements Initializable {

    @FXML
    private Button confirmBtn;
    @FXML
    private TextField paymentTF;
    private OrderEntity currentOrder;
    private String paymentType;
    private Scene mainScene;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (paymentTF.getText().isEmpty() || !paymentTF.getText().matches("-?\\d+")) {
                    Util.showNotification(UIConstants.TASK_FAILED, "Chưa nhập số lượng hoặc nhập sai");
                    return;
                }
                Double payment = Double.valueOf(paymentTF.getText());
                if (paymentType.equals(UIConstants.SHIPPER_PAYMENT)) {
                    currentOrder.setShipperPayment(payment);
                    TextField shipperPaymentTF = (TextField) mainScene.lookup("#shipperPayment");
                    shipperPaymentTF.setText(Util.getMoneyNumber(payment * -1));
                } else if (paymentType.equals(UIConstants.RECEIVER_PAYMENT)) {
                    currentOrder.setReceiverPayment(payment);
                    TextField receiverPaymentTF = (TextField) mainScene.lookup("#receiverPayment");
                    receiverPaymentTF.setText(Util.getMoneyNumber(payment * -1));
                } else if ((paymentType.equals(UIConstants.SUPP_PAYMENT))) {
                    currentOrder.setSuppPayment(payment);
                    TextField suppPaymentTF = (TextField) mainScene.lookup("#suppPayment");
                    suppPaymentTF.setText(Util.getMoneyNumber(payment * -1));
                }
                Util.calTotalPayment(currentOrder);
                //Statistics
                TextField finalPaymentTF = (TextField) mainScene.lookup("#finalPayment");
                finalPaymentTF.setText(Util.getMoneyNumber(currentOrder.getFinalPayment()));
                TableView<OrderEntity> orderTable = (TableView) mainScene.lookup("#orderTable");
                Double totalPlannedPayment = orderTable.getItems().stream().mapToDouble(OrderEntity::getPlannedPayment).sum();
                Double totalFinalPayment = orderTable.getItems().stream().mapToDouble(OrderEntity::getFinalPayment).sum();
                TextField invoiceTF = (TextField) mainScene.lookup("#invoice");
                TextField realInvoiceTF = (TextField) mainScene.lookup("#realInvoice");
                invoiceTF.setText(Util.getMoneyNumber(totalPlannedPayment));
                realInvoiceTF.setText(Util.getMoneyNumber(totalFinalPayment));

                Util.orderDAO.update(currentOrder);
                stage.close();
                Util.showNotification(UIConstants.TASK_DONE, "Cập nhật Thanh toán cho shipper thành công");
            }
        });
    }

    public void setCurrentOrder(OrderEntity orderEntity) {
        this.currentOrder = orderEntity;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
