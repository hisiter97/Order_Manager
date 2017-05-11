/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.util;

import com.td.trongnghia.entity.BusinessTypeEntity;
import com.td.trongnghia.entity.ResourceEntity;
import com.td.trongnghia.entity.UserEntity;
import java.lang.reflect.ParameterizedType;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author TRONGNGHIA
 */
public class ChoiceBoxUtil<T> {

    private ChoiceBox<T> choiceBox;
    private T entity;

    public ChoiceBoxUtil(ChoiceBox<T> choiceBox, T entity) {
        this.choiceBox = choiceBox;
        this.entity = entity;
    }

    public void apply() {
        int count = 0;
        for (T item : choiceBox.getItems()) {
            if (this.entity == null) {
                return;
            }
            if (equalz(item, this.entity)) {
                this.choiceBox.setValue(this.choiceBox.getItems().get(count));
                break;
            }
            count++;
        }
    }

    public Boolean equalz(T entity1, T entity2) {
        String className = entity1.getClass().getName();
        if (className.equals("com.td.trongnghia.entity.UserEntity")) {
            return ((UserEntity) entity1).getUserId() == ((UserEntity) entity2).getUserId();
        } else if (className.equals("com.td.trongnghia.entity.BusinessTypeEntity")) {
            return ((BusinessTypeEntity) entity1).getBusinessTypeId() == ((BusinessTypeEntity) entity2).getBusinessTypeId();
        } else if (className.equals("com.td.trongnghia.entity.ResourceEntity")) {
            return ((ResourceEntity) entity1).getResourceId() == ((ResourceEntity) entity2).getResourceId();
        }
        return null;
    }
}
