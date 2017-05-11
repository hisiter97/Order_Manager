/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.util.Util;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class UserController implements Initializable {

    @FXML
    private Button confirmBtn;
    @FXML
    private TextField userNameTF;
    @FXML
    private TextField nameTF;
    @FXML
    private PasswordField pwTF;
    @FXML
    private PasswordField pw2TF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private ComboBox<String> roleCB;
    @FXML
    private Label pwLbl;
    @FXML
    private Label pw2Lbl;
    private UserEntity currentUser;
    private boolean isNew = false;
    private Stage stage;
    private TableView<UserEntity> usersTable;
    private ObservableList<UserEntity> usersObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] roles = {"ADMIN", "SHIPPER"};
        roleCB.setItems(FXCollections.observableArrayList(Arrays.asList(roles)));
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveUser();
            }
        });

        roleCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (((String) ov.getValue()).equals("SHIPPER")) {
                    setPwUI(true);
                } else if (((String) ov.getValue()).equals("ADMIN")) {
                    setPwUI(false);
                }
            }
        });
    }

    private void setPwUI(boolean disable) {
        pwTF.setDisable(disable);
        pwLbl.setDisable(disable);
        pw2TF.setDisable(disable);
        pw2Lbl.setDisable(disable);
    }

    public void init() {
        if (currentUser != null && currentUser.getRole().equals("ADMIN") && currentUser.getUserId() != Util.userLogin.getUserId()) {
            userNameTF.setDisable(true);
            nameTF.setDisable(true);
            phoneTF.setDisable(true);
            emailTF.setDisable(true);
            roleCB.setDisable(true);
            confirmBtn.setDisable(true);
            setPwUI(true);
        } else if (currentUser != null) {
            userNameTF.setText(currentUser.getUserName());
            nameTF.setText(currentUser.getName());
            phoneTF.setText(currentUser.getPhone());
            emailTF.setText(currentUser.getEmail());
            roleCB.setValue(currentUser.getRole());
        } else {
            setPwUI(true);
        }
    }

    private boolean isUserInforValid() {
        return (!userNameTF.getText().trim().equals("")
                && roleCB.getSelectionModel() != null);
    }

    private boolean isPasswordConfirmed() {
        return (!pwTF.getText().equals("") && !pw2TF.getText().equals("") && pwTF.getText().equals(pw2TF.getText()));
    }

    private void saveUser() {
        if (!isUserInforValid()) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Thiếu thông tin cho người dùng")
                    .showError();
            return;
        }

        UserEntity userEntity = null;

        if (isNew) {
            if (!pwTF.isDisable() &&( pwTF.getText().equals("") || pw2TF.getText().equals("")) && roleCB.getValue().equals("ADMIN")) {
                Notifications.create()
                        .title(UIConstants.TASK_FAILED)
                        .text("Thiếu Mật khẩu")
                        .showError();
                return;
            } else if (!pwTF.isDisable() && !isPasswordConfirmed()) {
                Notifications.create()
                        .title(UIConstants.TASK_FAILED)
                        .text("Mật khẩu nhắc lại không khớp")
                        .showError();
                return;
            }
            userEntity = new UserEntity();
        } else {
            if (!pwTF.isDisable() && !isPasswordConfirmed()) {
                Notifications.create()
                        .title(UIConstants.TASK_FAILED)
                        .text("Mật khẩu nhắc lại không khớp")
                        .showError();
                return;
            }
            userEntity = this.currentUser;
        }

        userEntity.setUserName(userNameTF.getText().trim());
        userEntity.setName(nameTF.getText().trim());
        userEntity.setEmail(emailTF.getText().trim());
        userEntity.setRole(roleCB.getValue());
        userEntity.setPhone(phoneTF.getText());

        if (!pwTF.isDisable() && isPasswordConfirmed()) {
            String pw;
            try {
                pw = Util.getMd5Hash(pwTF.getText());
                userEntity.setPassword(pw);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (this.currentUser != null) {
            Util.userDAO.update(userEntity);
        } else {
            Util.userDAO.save(userEntity);
            this.usersObservableList.add(userEntity);
        }

        Notifications.create()
                .title(UIConstants.TASK_DONE)
                .text("Tạo/ Cập nhật người dùng thành công")
                .showInformation();

        usersTable.refresh();
//        stage.close();
    }

    public void setCurrentUser(UserEntity userEntity) {
        if (userEntity == null) {
            isNew = true;
        } else {
            this.currentUser = userEntity;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsersTable(TableView usersTable) {
        this.usersTable = usersTable;
    }

    public void setUsersObservableList(ObservableList usersObservableList) {
        this.usersObservableList = usersObservableList;
    }
}
