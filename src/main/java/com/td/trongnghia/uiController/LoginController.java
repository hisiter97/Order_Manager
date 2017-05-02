/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.manager.AppManager;
import com.td.trongnghia.util.Util;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author TRONGNGHIA
 */
public class LoginController {

    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
    }

    public void initManager(final AppManager appManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean loginFailed = false;
                try {
                    UserEntity userLogIn = login();
                    if (userLogIn != null) {
                        Notifications.create()
                                .title(UIConstants.TASK_DONE)
                                .text(userLogIn.getUserName() + " has logged in")
                                .showInformation();
                        AppManager.userLoggedIn = userLogIn;
                        appManager.showMainScreen();
                    } else {
                        loginFailed = true;
                    }
                } catch (NoSuchAlgorithmException ex) {
                    loginFailed = true;
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    loginFailed = true;
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (loginFailed) {
                    Notifications.create()
                            .title(UIConstants.TASK_FAILED)
                            .text("Username/password wrong or something crashes the system!")
                            .showError();
                }
            }
        });
    }

    private UserEntity login() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String userName = this.userName.getText();
        String password = this.password.getText();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        UserDAO userDAO = ctx.getBean(UserDAO.class);
        return userDAO.checkAccountExisted(userName, Util.getMd5Hash(password));
    }
}
