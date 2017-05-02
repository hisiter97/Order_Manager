package com.td.trongnghia.daoImpl;

import java.io.Serializable;
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
        String sql = "FROM " + ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
        return (List<T>)session.createQuery(sql).list();
    };
    
    public T save(T instance){
        Session session = sessionFactory.getCurrentSession();
        session.save(instance);
        return instance;
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
