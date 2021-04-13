/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package milionerzy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Karol
 */
public class Milionerzy {
    
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        new Panel().setVisible(true);    
    }
    
}
