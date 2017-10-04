/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.util;

import com.sun.javafx.print.Units;
import static com.sun.javafx.print.Units.MM;
import com.td.trongnghia.constants.UIConstants;
import com.td.trongnghia.daoImpl.BusinessTypeDAO;
import com.td.trongnghia.daoImpl.CustomerDAO;
import com.td.trongnghia.daoImpl.OrderDAO;
import com.td.trongnghia.daoImpl.OrderResourceDAO;
import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.Notifications;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author TRONGNGHIA
 */
public class Util {

    private static ClassPathXmlApplicationContext applicationContext;
    public static OrderDAO orderDAO;
    public static UserDAO userDAO;
    public static ResourceDAO resourceDAO;
    public static OrderResourceDAO orderResourceDAO;
    public static BusinessTypeDAO businessTypeDAO;
    public static CustomerDAO customerDAO;
    public static UserEntity userLogin;

    public static String getMd5Hash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return new String(messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    public static void showNotification(String type, String message) {
        Notifications notifications = Notifications.create()
                .title(type)
                .text(message);
        if (type.equals(UIConstants.TASK_DONE)) {
            notifications.showInformation();
        } else if (type.equals(UIConstants.TASK_FAILED)) {
            notifications.showError();
        }
    }

    public static void printOrder(final ObservableList<OrderResourceEntity> orderDetail, OrderEntity orderEntity) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                double.class, double.class, Units.class);
        c.setAccessible(true);
        Paper orderPaper = c.newInstance("7x10", 65, 100, MM);
        PageLayout pageLayout = printer.createPageLayout(orderPaper, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);

        int fontSize = 8;
        int colWidth = 40;

        GridPane gridPane = new GridPane();        
        gridPane.getColumnConstraints().add(new ColumnConstraints(colWidth + 10));
        gridPane.getColumnConstraints().add(new ColumnConstraints(colWidth));
        gridPane.getColumnConstraints().add(new ColumnConstraints(colWidth));
        Text titleTxt = new Text("Công Ty Thiết Bị Sự Kiện La Thăng");
        titleTxt.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        gridPane.add(titleTxt, 0, 0, 3, 1);
        Text title1Txt = new Text("202/4 đường Linh Trung-Linh Trung-Thủ Đức");
        title1Txt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));        
        gridPane.add(title1Txt, 0, 1, 3, 1);
        Text title2Txt = new Text("0941435373 - 0933.549.011");
        title2Txt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));        
        gridPane.add(title2Txt, 0, 2, 3, 1);

        int rowIndex = 3;
        gridPane.add(new Text(""), 0, rowIndex++);
        Text dateShippedTxt = new Text("Thời điểm giao");
        dateShippedTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(dateShippedTxt, 0, rowIndex);
        Text dateShippedNumTxt = new Text(new SimpleDateFormat("dd/MM/YYYY HH:mm").format(orderEntity.getDateShipped()));
        dateShippedNumTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(dateShippedNumTxt, 1, rowIndex++, 2, 1);
        Text dateReturnTxt = new Text("Thời điểm trả");
        dateReturnTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(dateReturnTxt, 0, rowIndex);
        Text dateReturnNumTxt = new Text(new SimpleDateFormat("dd/MM/YYYY HH:mm").format(orderEntity.getDateReturned()));
        dateReturnNumTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(dateReturnNumTxt, 1, rowIndex++, 2, 1);                
        
        gridPane.add(new Text(""), 0, rowIndex++);
        Text cusPhoneTxt = new Text("ĐTKH");
        cusPhoneTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));        
        gridPane.add(cusPhoneTxt, 0, rowIndex);
        Text cusPhoneNumTxt = new Text(orderEntity.getCustomerEntity().getCustomerPhone());
        cusPhoneNumTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(cusPhoneNumTxt, 1, rowIndex++, 2, 1);
                
        Text cusAddrTxt = new Text("ĐCKH");
        cusAddrTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        gridPane.add(cusAddrTxt, 0, rowIndex);
        GridPane.setValignment(cusAddrTxt, VPos.TOP); // To align vertically in the cell
        Text cusAddrNumTxt = new Text(orderEntity.getCustomerEntity().getCustomerAddr());
        cusAddrNumTxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
        cusAddrNumTxt.setWrappingWidth(80);
        gridPane.add(cusAddrNumTxt, 1, rowIndex++, 2, 1);

        gridPane.add(new Text(""), 0, rowIndex++);
        gridPane.add(new Text(""), 0, rowIndex++);
        Text resourceTxt = new Text("Thiết bị");
        resourceTxt.setFont(Font.font("Arial", FontWeight.BOLD, fontSize - 1));
        Text quantTxt = new Text("SL");
        quantTxt.setFont(Font.font("Arial", FontWeight.BOLD, fontSize - 1));
        Text priceTxt = new Text("Thành tiền");
        priceTxt.setFont(Font.font("Arial", FontWeight.BOLD, fontSize - 1));
        gridPane.add(resourceTxt, 0, rowIndex);
        gridPane.add(quantTxt, 1, rowIndex);
        gridPane.add(priceTxt, 2, rowIndex++);

//        Text separatortxt = new Text("--------------------------------------------------------------------");
//        separatortxt.setFont(Font.font("Arial", FontWeight.MEDIUM, fontSize - 1));
//        gridPane.add(separatortxt, 0, rowIndex, 3, 1);        
        for (OrderResourceEntity orderResourceEntity : orderDetail) {
            Text resourceNameTxt = new Text(orderResourceEntity.getResourceEntity().getResourceName());
            resourceNameTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
            Text quantityTxt = new Text(orderResourceEntity.getQuantity().toString());
            quantityTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
            Text totalRentPriceTxt = new Text(String.format(UIConstants.DOUBLE_FORMAT, orderResourceEntity.getPayment() * 1000));
            GridPane.setHalignment(totalRentPriceTxt, HPos.RIGHT); // To align vertically in the cell
            totalRentPriceTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
            gridPane.add(resourceNameTxt, 0, rowIndex);
            gridPane.add(quantityTxt, 1, rowIndex);
            gridPane.add(totalRentPriceTxt, 2, rowIndex);
            rowIndex++;
        }

        gridPane.add(new HBox(10.0), 0, rowIndex++, 3, 1);

        gridPane.add(new Text(""), 1, rowIndex++);
        Text shipPaymentTxt = new Text("Phí giao");
        shipPaymentTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));       
        gridPane.add(shipPaymentTxt, 0, rowIndex);
        Text shipPaymentNumTxt = new Text(orderEntity.getShipPayment() != null ? String.format(UIConstants.DOUBLE_FORMAT, orderEntity.getShipPayment() * 1000) : "");
        shipPaymentNumTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
        shipPaymentNumTxt.setTextAlignment(TextAlignment.RIGHT);
        GridPane.setHalignment(shipPaymentNumTxt, HPos.RIGHT);
        gridPane.add(shipPaymentNumTxt, 2, rowIndex++);

        gridPane.add(new Text(""), 1, rowIndex++);
        Text totalPriceTxt = new Text("Tổng cộng");
        totalPriceTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
        gridPane.add(totalPriceTxt, 0, rowIndex);
        Double total = orderEntity.getShipPayment() != null ? orderEntity.getPlannedPayment() + orderEntity.getShipPayment() : orderEntity.getPlannedPayment();
        Text totalPriceNumTxt = new Text(String.format(UIConstants.DOUBLE_FORMAT, total * 1000));
        GridPane.setHalignment(totalPriceNumTxt, HPos.RIGHT);
        totalPriceNumTxt.setFont(Font.font("Arial", FontWeight.LIGHT, fontSize - 1));
        totalPriceNumTxt.setTextAlignment(TextAlignment.RIGHT);
        gridPane.add(totalPriceNumTxt, 2, rowIndex++);

        gridPane.add(new HBox(50.0), 0, rowIndex++, 3, 1);

        for (int i = 0; i < 1; ++i) {
            gridPane.add(new Text(""), 1, rowIndex++);
        }
        Text footerTxt = new Text("Cảm ơn quý khách. Hẹn gặp lại!");
        footerTxt.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        footerTxt.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(footerTxt, 0, rowIndex, 3, 1);

        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        jobSettings.setPageLayout(pageLayout);

        if (job != null) {
            boolean success = job.printPage(gridPane);
            if (success) {
                job.endJob();
                Util.showNotification(UIConstants.TASK_DONE, "Đang in hóa đơn");
            }
        }
    }

    public static String getMoneyNumber(Double number) {
        return String.format(UIConstants.DOUBLE_FORMAT, number * 1000);
    }

    public static void calTotalPayment(OrderEntity orderEntity) {
        orderEntity.setFinalPayment(orderEntity.getPlannedPayment() + orderEntity.getShipPayment() - orderEntity.getShipperPayment()
                - orderEntity.getReceiverPayment() + orderEntity.getSuppPayment());
    }

    public static void setApplicationContext() {
        applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        orderDAO = applicationContext.getBean(OrderDAO.class);
        userDAO = applicationContext.getBean(UserDAO.class);
        orderResourceDAO = applicationContext.getBean(OrderResourceDAO.class);
        resourceDAO = applicationContext.getBean(ResourceDAO.class);
        businessTypeDAO = applicationContext.getBean(BusinessTypeDAO.class);
        customerDAO = applicationContext.getBean(CustomerDAO.class);
    }
}
