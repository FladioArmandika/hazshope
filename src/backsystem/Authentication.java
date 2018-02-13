/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Fladio
 */
public class Authentication {
    
    public Authentication() {
        
    }
    
    public boolean Authentication(String username, String password) {
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
            //prepare query
            String query = "SELECT * FROM user";
            //create statement
            stmt = con.createStatement();
            //execute
            rs = stmt.executeQuery(query);
            
            //iterate
            while(rs.next()) {
                String usernameDB = rs.getString("username");
                String passwordDB = rs.getString("password");
                if (usernameDB.contains(username) && passwordDB.contains(password)) {
                    return true;
                }
                return false;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
    
    
}
