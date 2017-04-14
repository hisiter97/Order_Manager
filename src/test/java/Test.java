import com.td.trongnghia.daoImpl.ResourceDAO;
import com.td.trongnghia.daoImpl.UserDAO;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import com.td.trongnghia.util.Util;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by TRONGNGHIA on 3/27/2017.
 */
public class Test {
    @Autowired
    static UserDAO userDAO;
    
    public static void main(String... args){
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
//        UserDAO userDAO = ctx.getBean(UserDAO.class);
                
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("Nguyễn Trọng Nghĩa");
//        userEntity.setPassword("nghia");
//        userEntity.setEmail("nghia@nghia.com");
//        userEntity.setRole("ADMIN");
//        userEntity.setUserName("nghia");
//        userDAO.save(userEntity);
//        List<UserEntity> userEntities = userDAO.findAll();                
            //           UserEntity userEntity = userDAO.findById(1l);
//           userEntity.setEmail("admin@admin.admin");
//           userDAO.update(userEntity);
        
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

           Boolean userNameAndPassword = userDAO.checkAccountExisted("nghia","nghia");
    }
}
