/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.dto.ResourceOrderDTO;
import com.td.trongnghia.entity.BusinessTypeEntity;
import com.td.trongnghia.entity.CustomerEntity;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.manager.AppManager;
import com.td.trongnghia.util.ChoiceBoxUtil;
import com.td.trongnghia.util.Util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 *
 * @author TRONGNGHIA
 */
public class NewOrderController {
    
    @FXML
    private TextField newCustomerNameTF;
    @FXML
    private TextField newCustomerPhoneTF;
    @FXML
    private TextField newCustomerIdTF;
    @FXML
    private TextField newCustomerAddrTF;
    @FXML
    private TextField customerNameTF;
    @FXML
    private TextField customerPhoneTF;
    @FXML
    private TextField customerIdTF;
    @FXML
    private ChoiceBox<UserEntity> shipperCB;
    @FXML
    private ChoiceBox<UserEntity> receiverCB;
    @FXML
    private ChoiceBox<ResourceEntity> resourceCB;
    @FXML
    private ChoiceBox<BusinessTypeEntity> businessTypeCB;
    @FXML
    private LocalDateTimeTextField dateOrderedPK;
    @FXML
    private Button addOrderBtn;
    @FXML
    private Button addCusBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TableView<CustomerEntity> customersTable;
    @FXML
    private TableView<ResourceOrderDTO> resourcesOrderTable;
    @FXML
    private Button addResourceBtn;
    @FXML
    private TextField quantityTF;
    @FXML
    private TextField plannedDaysTF;
    @FXML
    private TextField shipPaymentTF;
    @FXML
    private TextField depositTF;
    @FXML
    private Text editTxt;
    @FXML
    private TextField plannedDateReturnedTF;
    private ObservableList<CustomerEntity> customerObservableList;
    private ObservableList<ResourceOrderDTO> resourcesOrderObservableList;
    
    private CustomerEntity chosenCustomerEntity;
    private OrderEntity currentOrder;
    
    @FXML
    private void initialize() {
        List<UserEntity> shippers = Util.userDAO.findShippers();
        
        List<ResourceEntity> resourceEntities = Util.resourceDAO.findAll();
        
        List<CustomerEntity> customerEntities = Util.customerDAO.findAll();
        
        List<BusinessTypeEntity> businessTypeEntities = Util.businessTypeDAO.findAll();
        
        shipperCB.setItems(FXCollections.observableArrayList(shippers));
        receiverCB.setItems(FXCollections.observableArrayList(shippers));
        resourceCB.setItems(FXCollections.observableArrayList(resourceEntities));
        businessTypeCB.setItems(FXCollections.observableArrayList(businessTypeEntities));
        
        plannedDaysTF.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    System.out.println("Textfield on focus");
                } else {
                    if (dateOrderedPK.getLocalDateTime() != null) {
                        Calendar cal = Calendar.getInstance();
                        Timestamp timestamp = Timestamp.valueOf(dateOrderedPK.getLocalDateTime());
                        cal.setTime(timestamp);
                        cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(plannedDaysTF.getText()));
                        cal.add(Calendar.MINUTE, -1);
                        String plannedDateReturned = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Timestamp(cal.getTimeInMillis()));
                        plannedDateReturnedTF.setText(plannedDateReturned);
                    }
                }
            }
        });

        //Customers Table
        this.customerObservableList = FXCollections.observableArrayList(customerEntities);
        TableColumn customerNameCol = new TableColumn("Tên khách hàng");
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerName"));
        
        TableColumn customerPhoneCol = new TableColumn("SĐT khách hàng");
        customerPhoneCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerPhone"));
        
        TableColumn customerAddrCol = new TableColumn("Địa chỉ khách hàng");
        customerAddrCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerAddr"));
        
        TableColumn customerIdentCol = new TableColumn("CMND khách hàng");
        customerIdentCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerIdent"));
        
        customersTable.setItems(this.customerObservableList);
        customersTable.getColumns().addAll(customerNameCol, customerPhoneCol, customerIdentCol, customerAddrCol);

        //Filter on table like Excel
        TableFilter tableFilter = new TableFilter(customersTable);
        
        customersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                chosenCustomerEntity = newSelection;
                customerNameTF.setText(newSelection.getCustomerName());
                customerPhoneTF.setText(newSelection.getCustomerPhone());
                customerIdTF.setText(newSelection.getCustomerIdent());
            }
        });
        
        addCusBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveNewCustomer();
            }
        });
        
        List<ResourceOrderDTO> resourceOrderDTOs = new ArrayList<>();
        this.resourcesOrderObservableList = FXCollections.observableArrayList(resourceOrderDTOs);
        //Resources Order Table
        TableColumn resourceNameCol = new TableColumn("Tên thiết bị");
        resourceNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ResourceOrderDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ResourceOrderDTO, String> data) {
                return new SimpleStringProperty(data.getValue().getResourceEntity().getResourceName());
            }
        });
        resourceNameCol.setMinWidth(150);
        
        TableColumn quantityCol = new TableColumn("Số lượng");
        quantityCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ResourceOrderDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ResourceOrderDTO, String> data) {
                return new SimpleStringProperty(data.getValue().getQuantity().toString());
            }
        });
        
        resourcesOrderTable.setItems(resourcesOrderObservableList);
        resourcesOrderTable.getColumns().addAll(resourceNameCol, quantityCol);
        
        resourcesOrderTable.setRowFactory(new Callback<TableView<ResourceOrderDTO>, TableRow<ResourceOrderDTO>>() {
            @Override
            public TableRow<ResourceOrderDTO> call(TableView<ResourceOrderDTO> tableView) {
                final TableRow<ResourceOrderDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Xóa");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ResourceOrderDTO currentResourceOrderDTO = row.getItem();
                        resourcesOrderObservableList.remove(currentResourceOrderDTO);
                        resourcesOrderTable.refresh();
                    }
                });
                contextMenu.getItems().addAll(removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                return row;
            }
        });
        
        addResourceBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addNewResourceOrder();
            }
        });
        
        dateOrderedPK.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    public void initManager(final AppManager appManager, OrderEntity currentOrder) {
        this.currentOrder = currentOrder;
        //If user is editting an existing order
        if (currentOrder != null) {
            editTxt.setText("Đang chỉnh sửa hóa đơn");
            plannedDaysTF.setDisable(true);
            plannedDateReturnedTF.setDisable(true);
            customerNameTF.setText(currentOrder.getCustomerEntity().getCustomerName());
            customerPhoneTF.setText(currentOrder.getCustomerEntity().getCustomerPhone());
            customerIdTF.setText(currentOrder.getCustomerEntity().getCustomerIdent());
            dateOrderedPK.setLocalDateTime(currentOrder.getDateShipped().toLocalDateTime());
            shipPaymentTF.setText(String.valueOf(currentOrder.getShipPayment().intValue()));
            depositTF.setText(currentOrder.getDeposit() != null ? (currentOrder.getDeposit() == -1 ? "CMND" : currentOrder.getDeposit().toString()) : null);
            ChoiceBoxUtil<UserEntity> choiceBoxUtil = new ChoiceBoxUtil<>(shipperCB, currentOrder.getShipper());
            choiceBoxUtil.apply();
            ChoiceBoxUtil<UserEntity> choiceBoxUtil3 = new ChoiceBoxUtil<>(receiverCB, currentOrder.getReceiver());
            choiceBoxUtil3.apply();
            ChoiceBoxUtil<BusinessTypeEntity> choiceBoxUtil2 = new ChoiceBoxUtil<>(businessTypeCB, currentOrder.getBusinessTypeEntity());
            choiceBoxUtil2.apply();
            List<OrderResourceEntity> orderResourceEntities = Util.orderResourceDAO.getOrderResourcesByOrderId(currentOrder.getOrderId());
            List<ResourceOrderDTO> resourceOrderDTOs = new ArrayList<>();
            for (OrderResourceEntity orderResourceEntity : orderResourceEntities) {
                ResourceOrderDTO resourceOrderDTO = new ResourceOrderDTO();
                resourceOrderDTO.setResourceEntity(orderResourceEntity.getResourceEntity());
                resourceOrderDTO.setQuantity(orderResourceEntity.getQuantity());
                resourceOrderDTO.setPayment(orderResourceEntity.getPayment());
                resourceOrderDTOs.add(resourceOrderDTO);
            }
            this.chosenCustomerEntity = currentOrder.getCustomerEntity();
            this.resourcesOrderObservableList.addAll(resourceOrderDTOs);
            resourcesOrderTable.refresh();
        } else {
            editTxt.setText("Đang tạo mới hóa đơn");
        }
        
        addOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveOrder(appManager);
            }
        });
        
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appManager.showMainScreen();
            }
        });
    }
    
    private boolean isOrderInforValid() {
        return (this.chosenCustomerEntity != null
                && shipperCB.getSelectionModel().getSelectedItem() != null
                && this.resourcesOrderObservableList.size() > 0
                && businessTypeCB.getSelectionModel().getSelectedItem() != null
                && dateOrderedPK.getLocalDateTime() != null);
    }
    
    private void saveOrder(AppManager appManager) {
        if (!isOrderInforValid()) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Thiếu thông tin cho hóa đơn")
                    .showError();
            return;
        }

        //Get information from UI
        UserEntity shipper = shipperCB.getValue();
        UserEntity receiver = receiverCB.getValue();
        BusinessTypeEntity businessType = businessTypeCB.getValue();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String dateReturnedString = plannedDateReturnedTF.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Double shipPayment = Double.valueOf(shipPaymentTF.getText());
        Integer deposit = null;
        if (depositTF.getText().toLowerCase().equals("cmnd")) {
            deposit = -1;
        } else {
            try {
                deposit = Integer.valueOf(depositTF.getText());
            } catch (NumberFormatException ex) {
                Util.showNotification(UIConstants.TASK_FAILED, "Nhập sai giá trị Cọc");
                return;
            }
        }        
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateReturnedString);
        } catch (ParseException ex) {
            Logger.getLogger(NewOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        OrderEntity newOrder = null;
        //If editting an existing order
        if (this.currentOrder != null) {
            newOrder = this.currentOrder;
            Util.orderDAO.delete(this.currentOrder);
        } else {
            newOrder = new OrderEntity();
            
            newOrder.setDateCreated(now);
            newOrder.setUserCreated(AppManager.userLoggedIn);
        }
        
        newOrder.setDeposit(deposit);
        newOrder.setShipPayment(shipPayment);
        newOrder.setShipper(shipper);
        newOrder.setReceiver(receiver);
        newOrder.setBusinessTypeEntity(businessType);
        newOrder.setCustomerEntity(this.chosenCustomerEntity);
        LocalDateTime dateOrdered = dateOrderedPK.getLocalDateTime();
        if (dateOrdered != null) {
            newOrder.setDateShipped(Timestamp.valueOf(dateOrdered));
        }
        if (parsedDate != null) {
            Timestamp plannedDateReturned = new java.sql.Timestamp(parsedDate.getTime());
            newOrder.setDateReturned(plannedDateReturned);
        }
        
        boolean saveDone = true;
        try {
            newOrder = Util.orderDAO.save(newOrder);
            for (ResourceOrderDTO resourceOrderDTO : resourcesOrderTable.getItems()) {
                OrderResourceEntity orderResourceEntity = new OrderResourceEntity();
                orderResourceEntity.setOrderEntity(newOrder);
                orderResourceEntity.setResourceEntity(resourceOrderDTO.getResourceEntity());
                orderResourceEntity.setQuantity(resourceOrderDTO.getQuantity());
                orderResourceEntity.setPayment(resourceOrderDTO.getPayment());
                Util.orderResourceDAO.save(orderResourceEntity);
            }
        } catch (Exception ex) {
            saveDone = false;
        }
        
        if (saveDone) {
            Util.showNotification(UIConstants.TASK_DONE, "Tạo/Cập nhật hóa đơn thành công");
        } else {
            Util.showNotification(UIConstants.TASK_FAILED, "Tạo/Cập nhật hóa đơn thất bại");
        }
        appManager.showMainScreen();
    }
    
    private void saveNewCustomer() {
        String customerName = newCustomerNameTF.getText().trim();
        String customerPhone = newCustomerPhoneTF.getText().trim();
        String customerID = newCustomerIdTF.getText().trim();
        String customerAddr = newCustomerAddrTF.getText().trim();
        
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerName(customerName);
        customerEntity.setCustomerPhone(customerPhone);
        customerEntity.setCustomerIdent(customerID);
        customerEntity.setCustomerAddr(customerAddr);
        
        boolean saveDone = true;
        try {
            this.chosenCustomerEntity = Util.customerDAO.save(customerEntity);
            customerObservableList.add(customerEntity);
        } catch (Exception ex) {
            saveDone = false;
        }
        if (saveDone) {
            customerNameTF.setText(this.chosenCustomerEntity.getCustomerName());
            customerPhoneTF.setText(this.chosenCustomerEntity.getCustomerPhone());
            customerIdTF.setText(this.chosenCustomerEntity.getCustomerIdent());
            Util.showNotification(UIConstants.TASK_DONE, "Tạo khách hàng thành công");
        } else {
            Util.showNotification(UIConstants.TASK_FAILED, "Tạo khách hàng thất bại");
        }
    }
    
    private void addNewResourceOrder() {
        ResourceOrderDTO resourceOrderDTO = new ResourceOrderDTO();
        ResourceEntity resourceEntity = resourceCB.getValue();
        if (resourceEntity == null || quantityTF.getText().equals("")) {
            Util.showNotification(UIConstants.TASK_FAILED, "Thêm thiết bị cho hóa đơn thất bại");
        }
        Integer quantity = Integer.valueOf(quantityTF.getText());
        resourceOrderDTO.setResourceEntity(resourceEntity);
        resourceOrderDTO.setQuantity(quantity);
        this.resourcesOrderObservableList.add(resourceOrderDTO);
        resourcesOrderTable.refresh();
    }
}
