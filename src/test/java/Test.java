import com.td.trongnghia.constants.DBConstants;
import com.td.trongnghia.daoImpl.OrderDAO;
import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.util.Util;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

/**
 * Created by TRONGNGHIA on 3/27/2017.
 */
@Repository
public class Test {
    
    public static void main(String... args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        UserDAO userDAO = ctx.getBean(UserDAO.class);
        OrderDAO orderDAO = ctx.getBean(OrderDAO.class);
        ResourceDAO resourceDAO = ctx.getBean(ResourceDAO.class);
        
//        ResourceEntity resourceEntity = resourceDAO.findById(11l);
//        UserEntity userEntity = userDAO.findById(17l);
//        
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setResourceEntity(resourceEntity);
//        orderEntity.setShipper(userEntity);
//        orderEntity.setCustomerName("Messi");
//        orderEntity.setUserCreated(userEntity);
//        
//        Timestamp now = new Timestamp(System.currentTimeMillis());
//        orderEntity.setDateCreated(now);
//        orderEntity.setDateShipped(now);
//        
//        orderDAO.save(orderEntity);
                
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("Shipper");
//        try {
//            userEntity.setPassword(Util.getMd5Hash("shipper"));
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        userEntity.setEmail("shipper@shipper.com");
//        userEntity.setRole(DBConstants.SHIPPER_ROLE);
//        userEntity.setUserName("shipper");
//        userDAO.save(userEntity);        
        
//        ResourceDAO resourceDAO = ctx.getBean(ResourceDAO.class);
//        ResourceEntity resourceEntity = new ResourceEntity();
//        resourceEntity.setResourceName("LOA 200W");
//        resourceEntity.setDescription("Loa cho thuê, công suất 200W");
//        resourceEntity.setOriginalPrice(2000000);
//        resourceEntity.setPricePerDay(300000);
//        resourceEntity.setPricePerHour(50000);
//        resourceDAO.save(resourceEntity);

//        try {
//            Util.getMd5Hash("nghia");
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
