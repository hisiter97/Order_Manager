/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.application;

import com.td.trongnghia.manager.Manager;
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
//        Manager manager = new Manager(scene);
//        manager.showLoginScreen();
//        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String... args){
//        launch(args);
    }
}