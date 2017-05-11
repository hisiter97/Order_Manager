package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.OrderEntity;
import java.sql.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Repository
@Transactional
public class OrderDAO extends GenericDAO<OrderEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    public List<OrderEntity> findByTimePeriod(Date firstDate, Date lastDate) {
        StringBuilder sql = new StringBuilder("FROM OrderEntity oe");
        sql.append(" WHERE oe.dateShipped BETWEEN :firstDate AND :lastDate");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql.toString());
        query.setParameter("firstDate", firstDate);
        query.setParameter("lastDate", lastDate);
        return (List<OrderEntity>) query.list();
    }
    
    public List<OrderEntity> getCurrentOrders(Date firstDate, Date lastDate) {
        StringBuilder sql = new StringBuilder("FROM OrderEntity oe");
        sql.append(" WHERE (oe.dateShipped BETWEEN :firstDate AND :lastDate)");
        sql.append(" OR oe.status <> 2");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql.toString());
        query.setParameter("firstDate", firstDate);
        query.setParameter("lastDate", lastDate);
        return (List<OrderEntity>) query.list();
    }
}
