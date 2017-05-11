/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.util.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class ResourceController implements Initializable {

    @FXML
    private Button confirmBtn;
    @FXML
    private TextField resourceNameTF;
    @FXML
    private TextField origPriceTF;
    @FXML
    private TextField rentPriceTF;
    @FXML
    private TextField descTF;
    @FXML
    private TextField quantityTF;
    private ResourceEntity currentResource;
    private boolean isNew = false;
    private Stage stage;
    private TableView<ResourceEntity> resourcesTable;
    private ObservableList<ResourceEntity> resourcesObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveResource();
            }
        });
    }

    public void init() {
        if (!isNew) {
            resourceNameTF.setText(currentResource.getResourceName());
            origPriceTF.setText(currentResource.getOriginalPrice().toString());
            rentPriceTF.setText(currentResource.getRentPrice().toString());
            descTF.setText(currentResource.getDescription());
            quantityTF.setText(currentResource.getQuantity() == null ? "0" : currentResource.getQuantity().toString());
        }
    }

    private boolean isResourceInforValid() {
        return (!resourceNameTF.getText().trim().equals("")
                && !origPriceTF.getText().trim().equals("")
                && !rentPriceTF.getText().trim().equals(""));
    }

    private boolean isNumberValid(String numberInText) {
        return (numberInText.matches("\\d+"));
    }

    private void saveResource() {
        if (!isResourceInforValid()) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Thiếu thông tin cho thiết bị")
                    .showError();
            return;
        }

        if (!isNumberValid(origPriceTF.getText())
                || !isNumberValid(rentPriceTF.getText())
                || (!quantityTF.getText().equals("") && !isNumberValid(quantityTF.getText()))) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Số lượng sai định dạng")
                    .showError();
            return;
        }

        ResourceEntity resourceEntity = null;

        if (isNew) {
            resourceEntity = new ResourceEntity();
        } else {
            resourceEntity = this.currentResource;
        }

        resourceEntity.setResourceName(resourceNameTF.getText());
        resourceEntity.setOriginalPrice(Integer.valueOf(origPriceTF.getText().trim()));
        resourceEntity.setRentPrice(Integer.valueOf(rentPriceTF.getText().trim()));
        resourceEntity.setDescription(descTF.getText().trim());
        resourceEntity.setQuantity(quantityTF.getText().trim().equals("") ? 0 : Integer.valueOf(quantityTF.getText().trim()));

        if (isNew) {
            Util.resourceDAO.save(resourceEntity);
            this.resourcesObservableList.add(resourceEntity);
        } else {
            Util.resourceDAO.update(resourceEntity);
        }

        Notifications.create()
                .title(UIConstants.TASK_DONE)
                .text("Tạo/ Cập nhật thiết bị thành công")
                .showInformation();

        resourcesTable.refresh();
    }

    public void setCurrentResource(ResourceEntity resourceEntity) {
        if (resourceEntity == null) {
            isNew = true;
        } else {
            this.currentResource = resourceEntity;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setResourcesTable(TableView resourcesTable) {
        this.resourcesTable = resourcesTable;
    }

    public void setResourcesObservableList(ObservableList resourcesObservableList) {
        this.resourcesObservableList = resourcesObservableList;
    }
}
