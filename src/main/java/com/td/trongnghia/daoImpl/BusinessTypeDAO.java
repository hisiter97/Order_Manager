/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.BusinessTypeEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TRONGNGHIA
 */
@Repository
@Transactional
public class BusinessTypeDAO extends GenericDAO<BusinessTypeEntity> {
    @Autowired
    private SessionFactory sessionFactory;
}
