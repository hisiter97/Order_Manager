/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.uiController;

import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.elements.DateEditingCell;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.manager.AppManager;
import com.td.trongnghia.util.Util;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author TRONGNGHIA
 */
public class OverviewController {

    @FXML
    private TableView<OrderEntity> orderTable;
    @FXML
    private TableView<OrderResourceEntity> orderDetailTable;
    @FXML
    private TableView<ResourceEntity> resourcesTable;
    @FXML
    private TextField totalOrdersCreatedTF;
    @FXML
    private TextField totalOrdersCompletedTF;
    @FXML
    private TextField totalOrdersNotCompletedTF;
    @FXML
    private TextField invoiceTF;
    @FXML
    private TextField realInvoiceTF;
    @FXML
    private TextField shipperPaymentTF;
    @FXML
    private TextField receiverPaymentTF;
    @FXML
    private TextField suppPaymentTF;
    @FXML
    private TextField shipperTF;
    @FXML
    private TextField receiverTF;
    @FXML
    private TextField plannedPaymentTF;
    @FXML
    private TextField finalPaymentTF;
    @FXML
    private TextField shipPaymentTF;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> monthPicker;
    @FXML
    private ComboBox<Integer> yearPicker;
    @FXML
    private Button viewBtn;
    @FXML
    private Button viewByMonthBtn;
    @FXML
    private Label statLbl;
    @FXML
    private Button backBtn;
    @FXML
    private Button printBtn;
    private ObservableList<OrderEntity> orderObservableList;
    private ObservableList<OrderResourceEntity> orderDetailObservableList;
    private ObservableList<OrderEntity> orderObservableBackingList;

    private OrderEntity orderSelected;

    private Scene mainScene;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        orderTable.setEditable(true);
        this.orderObservableList = FXCollections.observableArrayList(new ArrayList<OrderEntity>());

        statistics();

        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.getDayOfWeek() != DayOfWeek.MONDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };

        Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        monthPicker.setItems(FXCollections.observableArrayList(Arrays.asList(months)));
        monthPicker.setValue(1);
        Integer[] years = {2017, 2018};
        yearPicker.setItems(FXCollections.observableArrayList(Arrays.asList(years)));
        yearPicker.setValue(2017);

        Locale.setDefault(Locale.UK);
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setShowWeekNumbers(true);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });

        viewBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate localDate = datePicker.getValue();
                Calendar cal = Calendar.getInstance(Locale.FRANCE);
                java.sql.Date firstDate = java.sql.Date.valueOf(localDate);
                cal.setTimeInMillis(firstDate.getTime());
                cal.add(Calendar.DATE, 7);
                java.sql.Date lastDate = new java.sql.Date(cal.getTime().getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                statLbl.setText("THỐNG KÊ TUẦN " + formatter.format(firstDate) + " đến " + formatter.format(lastDate));
                orderObservableList.clear();
                orderObservableList.addAll(FXCollections.observableArrayList(Util.orderDAO.findByTimePeriod(firstDate, lastDate)));
                orderTable.refresh();
                statistics();
            }
        });

        viewByMonthBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer month = monthPicker.getValue();
                Integer year = yearPicker.getValue();
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.YEAR, year);
                java.sql.Date firstDate = new java.sql.Date(cal.getTime().getTime());
                cal.add(Calendar.MONTH, 1);
                java.sql.Date lastDate = new java.sql.Date(cal.getTime().getTime());
                statLbl.setText("THỐNG KÊ THÁNG " + month.toString() + "/" + year.toString());
                orderObservableList.clear();
                orderObservableList.addAll(FXCollections.observableArrayList(Util.orderDAO.findByTimePeriod(firstDate, lastDate)));
                orderTable.refresh();
                statistics();
            }
        });
    }

    public void initManager(final AppManager appManager, Scene scene) {
        this.mainScene = scene;
        loadOrderTable(appManager);
        loadOrderDetailTable();

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appManager.showMainScreen();
            }
        });

        printBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (orderSelected == null || orderSelected.getDateReturned() == null || orderDetailObservableList == null || orderDetailObservableList.size() == 0) {
                    Util.showNotification(UIConstants.TASK_FAILED, "Không chọn hóa đơn nào hoặc hóa đơn chưa thanh toán");
                    return;
                }
                try {
                    Util.printOrder(orderDetailObservableList, orderSelected);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void loadOrderDetailTable() {
        TableColumn resourceNameCol = new TableColumn("Tên thiết bị");
        resourceNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderResourceEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderResourceEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getResourceEntity().getResourceName());
            }
        });

        TableColumn quantityCol = new TableColumn("Số lượng");
        quantityCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        quantityCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderResourceEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderResourceEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getQuantity().toString());
            }
        });

        TableColumn paymentCol = new TableColumn("Thành tiền");
        paymentCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        paymentCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderResourceEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderResourceEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getPayment() != null ? String.format(UIConstants.DOUBLE_FORMAT, data.getValue().getPayment() * 1000) : null);
            }
        });

        orderDetailTable.getColumns().addAll(resourceNameCol, quantityCol, paymentCol);

        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OrderEntity currentOrder = newSelection;
                this.orderSelected = currentOrder;
                this.orderDetailObservableList = FXCollections.observableArrayList(Util.orderResourceDAO.getOrderResourcesByOrderId(currentOrder.getOrderId()));
                orderDetailTable.setItems(this.orderDetailObservableList);
                shipperTF.setText(currentOrder.getShipper() == null ? null : currentOrder.getShipper().getName());
                receiverTF.setText(currentOrder.getReceiver() == null ? null : currentOrder.getReceiver().getName());
                shipperPaymentTF.setText(Util.getMoneyNumber(currentOrder.getShipperPayment() * -1));
                receiverPaymentTF.setText(Util.getMoneyNumber(currentOrder.getReceiverPayment() * -1));
                plannedPaymentTF.setText(Util.getMoneyNumber(currentOrder.getPlannedPayment()));
                shipPaymentTF.setText(Util.getMoneyNumber(currentOrder.getShipPayment()));
                suppPaymentTF.setText(Util.getMoneyNumber(currentOrder.getSuppPayment()));
                finalPaymentTF.setText(Util.getMoneyNumber(currentOrder.getFinalPayment()));
            }
        });
    }

    private void loadOrderTable(AppManager appManager) {
        TableColumn statusCol = new TableColumn("Trạng thái");
        statusCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String displayedString = null;
                switch (data.getValue().getStatus()) {
                    case 1:
                        displayedString = "Đã giao";
                        break;
                    case 2:
                        displayedString = "Đã nhận";
                        break;
                    default:
                        displayedString = "Mới tạo";
                        break;
                }
                return new SimpleStringProperty(displayedString);
            }
        });
        statusCol.setCellFactory(column -> {
            return new TableCell<OrderEntity, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Style all dates in March with a different color.
                        if (item.equals("Đã nhận")) {
                            setTextFill(Color.CHOCOLATE);
                            setStyle("-fx-background-color: yellow");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });

        TableColumn depositCol = new TableColumn("Cọc");
        depositCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String displayedString = null;
                switch (data.getValue().getDeposit()) {
                    case -1:
                        displayedString = "CMND";
                        break;
                    case 2:
                        displayedString = data.getValue().getDeposit().toString();
                        break;
                    default:
                        break;
                }
                return new SimpleStringProperty(displayedString);
            }
        });

        TableColumn shipperCol = new TableColumn("Người giao");
        shipperCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getShipper() == null ? null : data.getValue().getShipper().getName());
            }
        });

        TableColumn receiverCol = new TableColumn("Người nhận");
        receiverCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getReceiver() == null ? null : data.getValue().getReceiver().getName());
            }
        });

        TableColumn customerNameCol = new TableColumn("Tên khách hàng");
        customerNameCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerName());
            }
        });

        TableColumn customerPhoneCol = new TableColumn("SĐT khách hàng");
        customerPhoneCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerPhone());
            }
        });

        TableColumn customerIdentCol = new TableColumn("CMND khách hàng");
        customerIdentCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getCustomerEntity().getCustomerIdent());
            }
        });

        TableColumn dateCreatedCol = new TableColumn("Thời điểm tạo");
        dateCreatedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String formattedDate = new SimpleDateFormat(UIConstants.DATE_TIME_FORMAT).format(data.getValue().getDateCreated());
                return new SimpleStringProperty(formattedDate);
            }
        });

        TableColumn dateModifiedCol = new TableColumn("Thời điểm sửa");
        dateModifiedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String formattedDate = data.getValue().getDateModified() == null ? null : new SimpleDateFormat(UIConstants.DATE_TIME_FORMAT).format(data.getValue().getDateModified());
                return new SimpleStringProperty(formattedDate);
            }
        });

        TableColumn dateOrderedCol = new TableColumn("Thời điểm giao");
        dateOrderedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                String formattedDate = new SimpleDateFormat(UIConstants.DATE_TIME_FORMAT).format(data.getValue().getDateShipped());
                return new SimpleStringProperty(formattedDate);
            }
        });

        TableColumn dateReturnCol = new TableColumn("Thời điểm trả");
        dateReturnCol.setCellFactory(new Callback<TableColumn<OrderEntity, Timestamp>, TableCell<OrderEntity, Timestamp>>() {

            @Override
            public TableCell<OrderEntity, Timestamp> call(TableColumn<OrderEntity, Timestamp> p) {
                return new DateEditingCell();
            }

        });
        dateReturnCol.setCellValueFactory(new PropertyValueFactory("dateReturned"));
        dateReturnCol.setEditable(true);
        dateReturnCol.setMinWidth(180);
        dateReturnCol.setOnEditCommit(
                new EventHandler<CellEditEvent<OrderEntity, Timestamp>>() {
            @Override
            public void handle(CellEditEvent<OrderEntity, Timestamp> t) {
                ((OrderEntity) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDateReturned(t.getNewValue());
            }
        }
        );

        TableColumn userCreatedCol = new TableColumn("Người tạo");
        userCreatedCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getUserCreated().getUserName());
            }
        });

        TableColumn businessTypeCol = new TableColumn("Loại giao dịch");
        businessTypeCol.setCellValueFactory(
                new Callback<CellDataFeatures<OrderEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<OrderEntity, String> data) {
                return new SimpleStringProperty(data.getValue().getBusinessTypeEntity().getBusinessTypeDescription());
            }
        });

        orderTable.setItems(this.orderObservableList);
        orderTable.getColumns().addAll(statusCol, depositCol, shipperCol, receiverCol, customerNameCol, customerPhoneCol, customerIdentCol, dateOrderedCol, dateReturnCol, dateCreatedCol, dateModifiedCol, userCreatedCol, businessTypeCol);

        orderTable.setRowFactory(new Callback<TableView<OrderEntity>, TableRow<OrderEntity>>() {
            @Override
            public TableRow<OrderEntity> call(TableView<OrderEntity> tableView) {
                final TableRow<OrderEntity> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Xóa");
                final MenuItem confirmShippedMenuItem = new MenuItem("Xác nhận Đã Giao");
                final MenuItem confirmReceivedMenuItem = new MenuItem("Xác nhận Đã Nhận");
                final MenuItem shipperPaymentMenuItem = new MenuItem("Thanh toán cho Người giao");
                final MenuItem receiverPaymentMenuItem = new MenuItem("Thanh toán cho Người nhận");
                final MenuItem suppPaymentMenuItem = new MenuItem("Thanh toán Thêm/ Bớt");
                final MenuItem editMenuItem = new MenuItem("Chỉnh sửa thông tin");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        removeOrder(currentOrder);
                        orderTable.refresh();
                    }
                });
                confirmShippedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        if (currentOrder.getDateReturned() == null) {
                            Util.showNotification(UIConstants.TASK_FAILED, "Thiếu thông tin Thời điểm trả");
                            return;
                        }
                        currentOrder.setStatus(1);
                        updateOrder(currentOrder);
                        orderTable.refresh();
                    }
                });
                confirmReceivedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        if (currentOrder.getDateReturned() == null) {
                            Util.showNotification(UIConstants.TASK_FAILED, "Thiếu thông tin Thời điểm trả");
                            return;
                        }
                        currentOrder.setStatus(2);
                        updateOrder(currentOrder);
                        orderTable.refresh();
                    }
                });
                editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        openNewOrderWindow(currentOrder);
                    }

                    private void openNewOrderWindow(OrderEntity currentOrder) {
                        appManager.showNewOrderScreen(currentOrder);
                    }
                });
                shipperPaymentMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        showPaymentDialog(currentOrder, UIConstants.SHIPPER_PAYMENT, mainScene);
                    }
                });
                receiverPaymentMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        showPaymentDialog(currentOrder, UIConstants.RECEIVER_PAYMENT, mainScene);
                    }
                });
                suppPaymentMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OrderEntity currentOrder = row.getItem();
                        showPaymentDialog(currentOrder, UIConstants.SUPP_PAYMENT, mainScene);
                    }
                });
                contextMenu.getItems().addAll(removeMenuItem, confirmShippedMenuItem, confirmReceivedMenuItem, shipperPaymentMenuItem, receiverPaymentMenuItem, suppPaymentMenuItem, editMenuItem);
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

    private void removeOrder(OrderEntity orderEntity) {
        Util.orderDAO.delete(orderEntity);
        Util.showNotification(UIConstants.TASK_DONE, "Xóa hóa đơn khỏi CSDL thành công");
        this.orderObservableBackingList.remove(orderEntity);
        statistics();
    }

    private void updateOrder(OrderEntity orderEntity) {
        calPayment(orderEntity);
        Util.showNotification(UIConstants.TASK_DONE, "Cập nhật Thành tiền và Trạng thái cho hóa đơn thành công");
        statistics();
    }

    private Integer calPayment(OrderEntity orderEntity) {
        switch (orderEntity.getBusinessTypeEntity().getBusinessTypeCode()) {
            case "RENT_BY_DAY":
                if (orderEntity.getDateReturned() == null) {
                    return null;
                }
                Double totalPayment = 0.0;
                for (OrderResourceEntity orderResourceEntity : this.orderDetailObservableList) {
                    Long rentTime = orderEntity.getDateReturned().getTime() - orderEntity.getDateShipped().getTime();
                    Long daysOfRent = (rentTime / 86400000) + 1;
                    Double rentPrice = daysOfRent >= 1 ? (orderResourceEntity.getResourceEntity().getRentPrice() + (daysOfRent - 1) * UIConstants.PRICE_DEC_FACTOR * orderResourceEntity.getResourceEntity().getRentPrice()) * orderResourceEntity.getQuantity() : 0.0;
                    rentPrice = Math.round(rentPrice * 100.0) / 100.0;
                    totalPayment += rentPrice;
                    orderResourceEntity.setPayment(rentPrice);
                    Util.orderResourceDAO.update(orderResourceEntity);
                }
                orderEntity.setPlannedPayment(totalPayment);
                Util.calTotalPayment(orderEntity);
                Util.orderDAO.update(orderEntity);
                orderDetailTable.refresh();
                break;
            case "BUY":
//                return orderEntity.getResourceEntity().getOriginalPrice() * -1;
            case "SELL":
//                return orderEntity.getResourceEntity().getOriginalPrice();
        }
        return null;
    }

    private void statistics() {
        Integer numOfOrderCreated = this.orderObservableList.size();
        Predicate<OrderEntity> predicate = s -> s.getStatus() == 2;
        Integer numOfOrderCompleted = Integer.valueOf(String.valueOf(this.orderObservableList.stream().filter(predicate).count()));
        totalOrdersCreatedTF.setText(numOfOrderCreated.toString());
        totalOrdersCompletedTF.setText(numOfOrderCompleted.toString());
        totalOrdersNotCompletedTF.setText(String.valueOf(numOfOrderCreated - numOfOrderCompleted));
        Double totalPlannedPayment = 0.0;
        for (OrderEntity orderEntity : this.orderObservableList) {
            totalPlannedPayment += orderEntity.getPlannedPayment() + orderEntity.getShipPayment();
        }
        Double totalFinalPayment = this.orderObservableList.stream().mapToDouble(OrderEntity::getFinalPayment).sum();
        invoiceTF.setText(Util.getMoneyNumber(totalPlannedPayment));
        realInvoiceTF.setText(Util.getMoneyNumber(totalFinalPayment));
        if (this.orderSelected != null) {
            plannedPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getPlannedPayment()));
            shipPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getShipPayment()));
            shipperPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getShipperPayment() * -1));
            receiverPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getReceiverPayment() * -1));
            suppPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getSuppPayment()));
            finalPaymentTF.setText(Util.getMoneyNumber(this.orderSelected.getFinalPayment()));
        }
    }

    private void showPaymentDialog(OrderEntity currentOrder, String paymentType, Scene mainScene) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);

        Scene scene = new Scene(new StackPane());
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/payment.fxml")
        );
        try {
            scene.setRoot((Parent) loader.load());
            dialog.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialog.setScene(scene);

        if (paymentType.equals(UIConstants.SHIPPER_PAYMENT)) {
            dialog.setTitle("Cập nhật thanh toán cho người giao");
        } else if (paymentType.equals(UIConstants.RECEIVER_PAYMENT)) {
            dialog.setTitle("Cập nhật thanh toán cho người nhận");
        } else if (paymentType.equals(UIConstants.SUPP_PAYMENT)) {
            dialog.setTitle("Cập nhật thanh toán bổ sung");
        }

        PaymentController controller
                = loader.<PaymentController>getController();
        controller.setCurrentOrder(currentOrder);
        controller.setPaymentType(paymentType);
        controller.setMainScene(this.mainScene);

        dialog.show();
    }
}
