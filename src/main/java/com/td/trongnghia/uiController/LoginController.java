/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.manager.Manager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author TRONGNGHIA
 */
public class LoginController {
    @FXML private TextField userName;
    @FXML private TextField password;
    @FXML private Button loginButton;
    
    public void initManager(final Manager manager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {
              System.out.println("CLICKED");
              if (login()){
                manager.showMainScreen();
              }
          }
        });
    }
    
    private Boolean login(){
        String userName = this.userName.getText();
        String password = this.password.getText();        
        return true;
    }
}
