/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.daoImpl.CustomerDAO;
import com.td.trongnghia.daoImpl.OrderDAO;
import com.td.trongnghia.daoImpl.OrderResourceDAO;
import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.elements.DateEditingCell;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.manager.AppManager;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
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
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class MainController {

    @FXML
    private TableView<OrderEntity> orderTable;
    @FXML
    private TableView<OrderResourceEntity> orderDetailTable;
    @FXML
    private Button newOrderBtn;
    @FXML
    private TextField filterTF;
    private ObservableList<OrderEntity> orderObservableList;
    private ObservableList<OrderResourceEntity> orderDetailObservableList;
    private ObservableList<OrderEntity> orderObservableBackingList;

    private UserDAO userDAO;
    private ResourceDAO resourceDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private OrderResourceDAO orderResourceDAO;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        orderTable.setEditable(true);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        this.orderDAO = ctx.getBean(OrderDAO.class);
        List<OrderEntity> orderEntities = this.orderDAO.findAll();
        this.orderObservableList = FXCollections.observableArrayList(orderEntities);

        this.userDAO = ctx.getBean(UserDAO.class);
        List<UserEntity> userEntities = this.userDAO.findAll();

        this.resourceDAO = ctx.getBean(ResourceDAO.class);
        List<ResourceEntity> resourceEntities = this.resourceDAO.findAll();

        this.customerDAO = ctx.getBean(CustomerDAO.class);

        this.orderResourceDAO = ctx.getBean(OrderResourceDAO.class);
    }

    public void initManager(final AppManager appManager) {
        loadOrderTable();
        loadOrderDetailTable();
        newOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openNewOrderWindow();
            }

            private void openNewOrderWindow() {
                appManager.showNewOrderScreen();
            }
        });
    }

    private void loadOrderDetailTable() {
        TableColumn resourceNameCol = new TableColumn("Resource Name");
        resourceNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderResourceEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderResourceEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getResourceEntity().getResourceName());
            }
        });
        resourceNameCol.setMinWidth(150);

        TableColumn quantityCol = new TableColumn("Quantity");
        quantityCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderResourceEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderResourceEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getQuantity().toString());
            }
        });

        orderDetailTable.getColumns().addAll(resourceNameCol, quantityCol);

        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OrderEntity currentOrder = newSelection;
                this.orderDetailObservableList = FXCollections.observableArrayList(this.orderResourceDAO.getOrderResourcesByOrderId(currentOrder.getOrderId()));
                orderDetailTable.setItems(this.orderDetailObservableList);
            }
        });
    }

    private void loadOrderTable() {
        TableColumn shipperNameCol = new TableColumn("Shipper Name");
        shipperNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getShipper().getUserName());
            }
        });

        TableColumn customerNameCol = new TableColumn("Customer name");
        customerNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerName());
            }
        });

        TableColumn customerPhoneCol = new TableColumn("Customer phone");
        customerPhoneCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerPhone());
            }
        });

        TableColumn customerIdentCol = new TableColumn("Customer Identity");
        customerIdentCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerIdent());
            }
        });

        TableColumn dateCreatedCol = new TableColumn("Date Created");
        dateCreatedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data.getValue().getDateCreated());
                return new SimpleStringProperty(formattedDate);
            }
        });

        TableColumn dateOrderedCol = new TableColumn("Date Ordered");
        dateOrderedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(data.getValue().getDateShipped());
                return new SimpleStringProperty(formattedDate);
            }
        });

        TableColumn dateReturnCol = new TableColumn("Date return");
        dateReturnCol.setCellFactory(new Callback<TableColumn<OrderEntity, Date>, TableCell<OrderEntity, Date>>() {

            @Override
            public TableCell<OrderEntity, Date> call(TableColumn<OrderEntity, Date> p) {
                return new DateEditingCell();
            }

        });
        dateReturnCol.setCellValueFactory(new PropertyValueFactory("dateReturned"));
        dateReturnCol.setEditable(true);
        dateReturnCol.setOnEditCommit(
                new EventHandler<CellEditEvent<OrderEntity, Date>>() {
            @Override
            public void handle(CellEditEvent<OrderEntity, Date> t) {
                ((OrderEntity) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDateReturned(t.getNewValue());
            }
        }
        );

        TableColumn userCreatedCol = new TableColumn("User created");
        userCreatedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getUserCreated().getUserName());
            }
        });

        TableColumn businessTypeCol = new TableColumn("Business Type");
        businessTypeCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getBusinessTypeEntity().getBusinessTypeDescription());
            }
        });

        TableColumn paymentCol = new TableColumn("Payment");
        paymentCol.setCellValueFactory(new PropertyValueFactory("payment"));

        TableColumn statusCol = new TableColumn<>("Status");
        statusCol.setCellFactory(new Callback<TableColumn<OrderEntity, Boolean>, TableCell<OrderEntity, Boolean>>() {

            @Override
            public TableCell<OrderEntity, Boolean> call(TableColumn<OrderEntity, Boolean> p) {
                return new StatusCell();
            }

        });

        orderTable.setItems(this.orderObservableList);
        orderTable.getColumns().addAll(statusCol, shipperNameCol, customerNameCol, customerPhoneCol, customerIdentCol, dateOrderedCol, dateReturnCol, dateCreatedCol, userCreatedCol, businessTypeCol, paymentCol);

        orderTable.setRowFactory(new Callback<TableView<OrderEntity>, TableRow<OrderEntity>>() {
            @Override
            public TableRow<OrderEntity> call(TableView<OrderEntity> tableView) {
                final TableRow<OrderEntity> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                final MenuItem confirmMenuItem = new MenuItem("Confirm Date Return");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        removeOrder(currentOrder);
                        orderTable.refresh();
                    }
                });
                confirmMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        if (currentOrder.getDateReturned() == null) {
                            Notifications.create()
                                    .title(UIConstants.TASK_FAILED)
                                    .text("Missing Date Returned info")
                                    .showInformation();
                            return;
                        }
                        updateOrder(currentOrder);
                        orderTable.refresh();
                    }
                });
                contextMenu.getItems().addAll(removeMenuItem, confirmMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                return row;
            }
        });

        //Filter on table like Excel
        TableFilter tableFilter = new TableFilter(orderTable);
        this.orderObservableBackingList = tableFilter.getBackingList();
    }

    //Define the button cell
    private class StatusCell extends TableCell<OrderEntity, Boolean> {

        final Label cellLbl = new Label("Status");

        //Display button if the row is not empty
//        @Override
//        protected void updateItem(String t, boolean empty) {
//            super.updateItem(t, empty);
//            if (!empty) {
//                setGraphic(cellLbl);
//            }
//        }
    }

    private void removeOrder(OrderEntity orderEntity) {
        this.orderDAO.delete(orderEntity);
        Notifications.create()
                .title(UIConstants.TASK_DONE)
                .text("Remove order done")
                .showInformation();
        this.orderObservableBackingList.remove(orderEntity);
    }

    private void updateOrder(OrderEntity orderEntity) {
        this.orderDAO.update(orderEntity);
        Notifications.create()
                .title(UIConstants.TASK_DONE)
                .text("Update order done")
                .showInformation();
    }

    private Integer calPayment(OrderEntity orderEntity) {
        switch (orderEntity.getBusinessTypeEntity().getBusinessTypeCode()) {
            case "RENT_BY_DAY":
//                if (orderEntity.getDateReturned() == null) {
//                    return null;
//                } else {
//                    Long rentTime = orderEntity.getDateReturned().getTime() - orderEntity.getDateShipped().getTime();
//                    Long rentPrice = ((rentTime / 86400000) + 1) * orderEntity.getResourceEntity().getRentPrice();
//                    return Integer.valueOf(rentPrice.toString());
//                }
            case "RENT_BY_HOUR":
            case "BUY":
//                return orderEntity.getResourceEntity().getOriginalPrice() * -1;
            case "SELL":
//                return orderEntity.getResourceEntity().getOriginalPrice();
        }
        return null;
    }
}
