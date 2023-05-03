/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.tools;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Skymil
 */
public class BCryptPass {
    public static String cryptMDP(String mdp){
        return BCrypt.hashpw(mdp,BCrypt.gensalt(10));
    }
    public static boolean checkPass(String pass,String password){
        return BCrypt.checkpw(pass, password);
    }
    
    
}
