/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazshope;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fladio
 */
public class HazShope extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        //Parent loginScreen = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        String filename = "";
        String status = null;
        
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\res\\datalogin.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            status = sb.toString();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        if (status.contains("null")) {
            root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            System.out.println("file kosong");
        }
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
