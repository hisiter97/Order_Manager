package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.CustomerEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Repository
@Transactional
public class CustomerDAO extends GenericDAO<CustomerEntity>{
    @Autowired
    private SessionFactory sessionFactory;
}
