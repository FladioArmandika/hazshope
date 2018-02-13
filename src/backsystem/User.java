/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fladio
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String urlpp;
    private String position;
    
    
    public User(String username) throws SQLException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
            Statement stmt =  con.createStatement();
            String query = "SELECT * FROM user";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                String idDB = rs.getString("id");
                String usernameDB = rs.getString("username");
                String passwordDB = rs.getString("password");
                String namaDB = rs.getString("name");
                String urlppDB = rs.getString("urlpp");
                String positionDB = rs.getString("position");
                
                int idDBINT = Integer.parseInt(idDB);
                
                if (usernameDB.contains(username)) {
                    this.id = idDBINT;
                    this.username = usernameDB;
                    this.password = passwordDB;
                    this.nama = namaDB;
                    this.urlpp = urlppDB;
                    this.position = positionDB;
                    
                    try {
                        FileWriter fw = new FileWriter("src\\res\\datalogin.txt");
                        
                            fw.write(idDB);
                            fw.write(System.lineSeparator());
                            fw.write(usernameDB);
                            fw.write(System.lineSeparator());
                            fw.write(passwordDB);
                            fw.write(System.lineSeparator());
                            fw.write(namaDB);
                            fw.write(System.lineSeparator());
                            fw.write(urlppDB);
                            fw.write(System.lineSeparator());
                            fw.write(positionDB);
                            
                        
                        fw.close();     
                        System.out.println("berhasil");
                    } catch(IOException e) {
                        System.out.println(e);
                    }
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       
    }
    
    public static void writeFile() throws IOException {
	FileWriter fw = new FileWriter("out.txt");
 
	for (int i = 0; i < 10; i++) {
		fw.write("something");
	}
 
	fw.close();
}
    
}
