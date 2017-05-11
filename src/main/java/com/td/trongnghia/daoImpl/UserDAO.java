package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.UserEntity;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Repository
@Transactional
public class UserDAO extends GenericDAO<UserEntity>{
    @Autowired
    private SessionFactory sessionFactory;

    public UserEntity checkAccountExisted(String userName, String password){
        StringBuilder sql = new StringBuilder("FROM UserEntity u");
        sql.append(" WHERE u.userName = :userName");
        sql.append(" AND u.password = :password");
        sql.append(" AND u.role = 'ADMIN'");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql.toString());
        query.setParameter("userName", userName);
        query.setParameter("password", password);        
        return query.list().size() > 0? (UserEntity) query.list().get(0): null;
    }
    
    public List<UserEntity> findShippers(){
        StringBuilder sql = new StringBuilder("FROM UserEntity u");
        sql.append(" WHERE u.role = 'SHIPPER'");        
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql.toString());         
        return query.list();
    }
}
