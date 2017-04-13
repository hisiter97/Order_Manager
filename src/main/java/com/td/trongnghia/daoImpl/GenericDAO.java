package com.td.trongnghia.daoImpl;

import com.td.trongnghia.dao.DAO;
import com.td.trongnghia.entity.UserEntity;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Repository
@Transactional
public class GenericDAO<T>{            
    @Autowired
    private SessionFactory sessionFactory;
    
    private Class<T> persistenceClass;
    
    public GenericDAO(){        
    }
    
    public List<T> findAll(){
        Session session = sessionFactory.getCurrentSession();        
        String sql = "FROM " + this.persistenceClass;
        return (List<T>)session.createQuery(sql).list();
    };
    
    public void save(T instance){
        Session session = sessionFactory.getCurrentSession();
        session.save(instance);
    };
    
    public void update(T instance){
        Session session = sessionFactory.getCurrentSession();
        session.update(instance);
    };
    
    public void delete(T instance){
        Session session = sessionFactory.getCurrentSession();
        session.delete(instance);
        
    };
    
    public T findById(Long id){
        Session session = sessionFactory.getCurrentSession();  
        this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return session.get(this.persistenceClass, id);          
    }
}
