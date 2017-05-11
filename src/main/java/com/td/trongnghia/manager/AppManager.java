/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.manager;

import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.uiController.LoginController;
import com.td.trongnghia.uiController.MainController;
import com.td.trongnghia.uiController.NewOrderController;
import com.td.trongnghia.uiController.OverviewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author TRONGNGHIA
 */
public class AppManager {

    private Stage stage;
    private Scene scene;
    @Autowired
    public static UserEntity userLoggedIn;

    public AppManager(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    public void showLoginScreen() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/login.fxml")
        );
        try {
            this.scene.setRoot((Parent) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoginController controller
                = loader.<LoginController>getController();
        controller.initManager(this);
    }

    public void showMainScreen() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main.fxml")
        );
        try {
            this.scene.setRoot((Parent) loader.load());
            this.stage.setResizable(false);
            this.stage.setMaximized(true);
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainController controller
                = loader.<MainController>getController();
        controller.initManager(this, scene);
    }

    public void showNewOrderScreen(OrderEntity currentOrder) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/new_order.fxml")
        );
        try {
            this.scene.setRoot((Parent) loader.load());
            this.stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        NewOrderController controller
                = loader.<NewOrderController>getController();
        controller.initManager(this, currentOrder);
    }

    public void showOverviewScreen() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/overview.fxml")
        );
        try {
            this.scene.setRoot((Parent) loader.load());
            this.stage.setResizable(false);
            this.stage.setMaximized(true);
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        OverviewController controller
                = loader.<OverviewController>getController();
        controller.initManager(this, scene);
    }
}
