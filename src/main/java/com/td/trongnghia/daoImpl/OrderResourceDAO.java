/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.OrderResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TRONGNGHIA
 */
@Repository
@Transactional
public class OrderResourceDAO extends GenericDAO<OrderResourceEntity>{
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<OrderResourceEntity> getOrderResourcesByOrderId(Long orderId){
        StringBuilder sql = new StringBuilder("FROM OrderResourceEntity ore");
        sql.append(" WHERE ore.orderEntity.orderId = :orderId");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql.toString());
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
