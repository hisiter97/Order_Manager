package com.td.trongnghia.daoImpl;

import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
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
public class ResourceDAO extends GenericDAO<ResourceEntity>{
    
}
