/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.application;

import com.td.trongnghia.manager.AppManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author TRONGNGHIA
 */
public class MainApp extends Application{

    @Override
    public void start(Stage stage) throws Exception {   
        Scene scene = new Scene(new StackPane());
        AppManager appManager = new AppManager(stage, scene);
        appManager.showLoginScreen();
        stage.setScene(scene);  
//        stage.setResizable(true);
        stage.show();
    }
    
    public static void main(String... args){
        launch(args);
    }
}