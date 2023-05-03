/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import java.util.List;

/**
 *
 * @author DELL
 */
public interface EntityCRUD<T> {
    public void addEntity(T t);
    public List<T> display();
    
}
