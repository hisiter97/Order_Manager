import com.td.trongnghia.daoImpl.BusinessTypeDAO;
import com.td.trongnghia.daoImpl.CustomerDAO;
import com.td.trongnghia.daoImpl.OrderDAO;
import com.td.trongnghia.daoImpl.OrderResourceDAO;
import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.entity.BusinessTypeEntity;
import com.td.trongnghia.entity.CustomerEntity;
import com.td.trongnghia.entity.OrderEntity;
import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
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
        CustomerDAO customerDAO = ctx.getBean(CustomerDAO.class);
        OrderResourceDAO orderResourceDAO = ctx.getBean(OrderResourceDAO.class);
        BusinessTypeDAO businessTypeDAO = ctx.getBean(BusinessTypeDAO.class);
        
//        ResourceEntity resourceEntity = new ResourceEntity();
//        resourceEntity.setResourceName("LOA 400W)");
//        resourceEntity.setDescription("Loa cho thuê 400W");
//        resourceEntity.setOriginalPrice(2600);
//        resourceEntity.setRentPrice(400);
//        resourceEntity.setResourceType(1);
//        resourceDAO.save(resourceEntity);
//        
//        List<ResourceEntity> resourceEntities = new ArrayList<>();
//        resourceEntities.add(resourceEntity);
//        
//        BusinessTypeEntity businessTypeEntity = businessTypeDAO.findById(1l);
        
//        CustomerEntity customerEntity = new CustomerEntity();
//        customerEntity.setCustomerName("Test");
//        customerEntity.setCustomerPhone("0900456357");
//        customerEntity.setCustomerIdent("1900023232");
//        customerEntity = customerDAO.save(customerEntity);        
//        ResourceEntity resourceEntity = resourceDAO.findById(11l);
//        UserEntity userEntity = userDAO.findById(17l);
//        CustomerEntity customerEntity = customerDAO.findById(113l);
//        
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setResourceEntities(resourceEntities);
//        orderEntity.setShipper(userEntity);
//        orderEntity.setCustomerEntity(customerEntity);
//        orderEntity.setUserCreated(userEntity);
//        orderEntity.setBusinessTypeEntity(businessTypeEntity);
//        orderEntity = orderDAO.save(orderEntity);
//        OrderEntity orderEntity = orderDAO.findById(1l);   
//        orderEntity.setResourceEntities(resourceEntities);

        List<OrderResourceEntity> orderResourceEntities = orderResourceDAO.getOrderResourcesByOrderId(1l);

        int a = 0;
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

//        try {
//            Util.getMd5Hash("nghia");
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
