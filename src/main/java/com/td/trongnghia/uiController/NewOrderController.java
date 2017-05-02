/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.daoImpl.BusinessTypeDAO;
import com.td.trongnghia.daoImpl.CustomerDAO;
import com.td.trongnghia.daoImpl.OrderDAO;
import com.td.trongnghia.daoImpl.OrderResourceDAO;
import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.dto.ResourceOrderDTO;
import com.td.trongnghia.entity.BusinessTypeEntity;
import com.td.trongnghia.entity.CustomerEntity;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.manager.AppManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    private TextField customerNameTF;
    @FXML
    private TextField customerPhoneTF;
    @FXML
    private TextField customerIdTF;
    @FXML
    private ChoiceBox<UserEntity> shipperCB;
    @FXML
    private ChoiceBox<ResourceEntity> resourceCB;
    @FXML
    private ChoiceBox<BusinessTypeEntity> businessTypeCB;
    @FXML
    private DatePicker dateOrderedPK;
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
    private ObservableList<CustomerEntity> customerObservableList;
    private ObservableList<ResourceOrderDTO> resourcesOrderObservableList;

    private UserDAO userDAO;
    private ResourceDAO resourceDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private BusinessTypeDAO businessTypeDAO;
    private OrderResourceDAO orderResourceDAO;

    private CustomerEntity chosenCustomerEntity;

    @FXML
    private void initialize() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        this.orderDAO = ctx.getBean(OrderDAO.class);

        this.userDAO = ctx.getBean(UserDAO.class);
        List<UserEntity> userEntities = this.userDAO.findAll();

        this.resourceDAO = ctx.getBean(ResourceDAO.class);
        List<ResourceEntity> resourceEntities = this.resourceDAO.findAll();

        this.customerDAO = ctx.getBean(CustomerDAO.class);
        List<CustomerEntity> customerEntities = this.customerDAO.findAll();

        this.businessTypeDAO = ctx.getBean(BusinessTypeDAO.class);
        List<BusinessTypeEntity> businessTypeEntities = this.businessTypeDAO.findAll();

        this.orderResourceDAO = ctx.getBean(OrderResourceDAO.class);

        shipperCB.setItems(FXCollections.observableArrayList(userEntities));
        resourceCB.setItems(FXCollections.observableArrayList(resourceEntities));
        businessTypeCB.setItems(FXCollections.observableArrayList(businessTypeEntities));

        //Customers Table
        this.customerObservableList = FXCollections.observableArrayList(customerEntities);
        TableColumn customerNameCol = new TableColumn("Customer name");
        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerName"));

        TableColumn customerPhoneCol = new TableColumn("Customer phone");
        customerPhoneCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerPhone"));

        TableColumn customerIdentCol = new TableColumn("Customer Identity");
        customerIdentCol.setCellValueFactory(
                new PropertyValueFactory<CustomerEntity, String>("customerIdent"));

        customersTable.setItems(this.customerObservableList);
        customersTable.getColumns().addAll(customerNameCol, customerPhoneCol, customerIdentCol);

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
        TableColumn resourceNameCol = new TableColumn("Resource Name");
        resourceNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ResourceOrderDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ResourceOrderDTO, String> data) {
                return new SimpleStringProperty(data.getValue().getResourceEntity().getResourceName());
            }
        });
        resourceNameCol.setMinWidth(150);

        TableColumn quantityCol = new TableColumn("Quantity");
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
                final MenuItem removeMenuItem = new MenuItem("Remove");
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

    }

    public void initManager(final AppManager appManager) {
        addOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveNewOrder(appManager);
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
                && dateOrderedPK.getValue() != null);
    }

    private void saveNewOrder(AppManager appManager) {
        if (!isOrderInforValid()) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Missing 1 or some infor about new ORDER")
                    .showError();
            return;
        }
        UserEntity shipper = shipperCB.getValue();
        BusinessTypeEntity businessType = businessTypeCB.getValue();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        OrderEntity newOrder = new OrderEntity();
        newOrder.setShipper(shipper);
        newOrder.setBusinessTypeEntity(businessType);
        if (this.chosenCustomerEntity != null) {
            newOrder.setCustomerEntity(this.chosenCustomerEntity);
        }
        newOrder.setDateCreated(now);
        newOrder.setUserCreated(AppManager.userLoggedIn);
        LocalDate dateOrdered = dateOrderedPK.getValue();
        if (dateOrdered != null) {
            Instant instant = Instant.from(dateOrdered.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            newOrder.setDateShipped(new Timestamp(date.getTime()));
        }
        boolean saveDone = true;
        try {
            newOrder = orderDAO.save(newOrder);
            for (ResourceOrderDTO resourceOrderDTO : resourcesOrderTable.getItems()) {
                OrderResourceEntity orderResourceEntity = new OrderResourceEntity();
                orderResourceEntity.setOrderEntity(newOrder);
                orderResourceEntity.setResourceEntity(resourceOrderDTO.getResourceEntity());
                orderResourceEntity.setQuantity(resourceOrderDTO.getQuantity());
                orderResourceDAO.save(orderResourceEntity);
            }
        } catch (Exception ex) {
            saveDone = false;
        }

        if (saveDone) {
            Notifications.create()
                    .title(UIConstants.TASK_DONE)
                    .text("Add new order done")
                    .showInformation();
        } else {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Add new order failed")
                    .showError();
        }
        appManager.showMainScreen();
    }

    private void saveNewCustomer() {
        String customerName = newCustomerNameTF.getText().trim();
        String customerPhone = newCustomerPhoneTF.getText().trim();
        String customerID = newCustomerIdTF.getText().trim();

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerName(customerName);
        customerEntity.setCustomerPhone(customerPhone);
        customerEntity.setCustomerIdent(customerID);

        boolean saveDone = true;
        try {
            this.chosenCustomerEntity = customerDAO.save(customerEntity);
            customerObservableList.add(customerEntity);
        } catch (Exception ex) {
            saveDone = false;
        }
        if (saveDone) {
            customerNameTF.setText(this.chosenCustomerEntity.getCustomerName());
            customerPhoneTF.setText(this.chosenCustomerEntity.getCustomerPhone());
            customerIdTF.setText(this.chosenCustomerEntity.getCustomerIdent());
            Notifications.create()
                    .title(UIConstants.TASK_DONE)
                    .text("Add new customer done")
                    .showInformation();
        } else {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Add new customer failed")
                    .showError();
        }
    }

    private void addNewResourceOrder() {
        ResourceOrderDTO resourceOrderDTO = new ResourceOrderDTO();
        ResourceEntity resourceEntity = resourceCB.getValue();
        if (resourceEntity == null || quantityTF.getText().equals("")) {
            Notifications.create()
                    .title(UIConstants.TASK_FAILED)
                    .text("Add new resource order failed. Missing resource or quantity!")
                    .showError();
        }
        Integer quantity = Integer.valueOf(quantityTF.getText());
        resourceOrderDTO.setResourceEntity(resourceEntity);
        resourceOrderDTO.setQuantity(quantity);
        this.resourcesOrderObservableList.add(resourceOrderDTO);
        resourcesOrderTable.refresh();
    }
}
