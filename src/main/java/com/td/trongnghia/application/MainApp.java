/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.application;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.manager.AppManager;
import com.td.trongnghia.util.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author TRONGNGHIA
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/logo.png"));
        stage.setTitle(UIConstants.APP_TITLE);
        new FirstPreloader().start(stage);
        Scene scene = new Scene(new StackPane());
        Util.setApplicationContext();
        AppManager appManager = new AppManager(stage, scene);
        appManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}
