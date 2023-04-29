/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

/**
 *
 * @author chaab
 */
public enum Role {
    ROLE_DOCTOR,ROLE_PATIENT,ROLE_PARA,ROLE_ADMIN;

      public static Role valueOf1(String roleName) {
    for (Role role : Role.values()) {
        if (role.toString().equalsIgnoreCase(roleName)) {
            return role;
        }
    }
    throw new IllegalArgumentException("Invalid role name: " + roleName);
}
      
      



    
}
