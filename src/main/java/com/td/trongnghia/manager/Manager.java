/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.manager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author TRONGNGHIA
 */
public class Manager {
    private Scene scene;
    public Manager(Scene scene){
        this.scene = scene;
    }
    public void showLoginScreen(){
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/login.fxml")
          );
        try {
            this.scene.setRoot((Parent) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
