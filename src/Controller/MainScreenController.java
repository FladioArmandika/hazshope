/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import static com.sun.deploy.util.ReflectionUtil.getClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.Action;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;
import static sun.security.x509.OIDMap.getClass;
import static sun.security.x509.OIDMap.getClass;

/**
 * FXML Controller class
 *
 * @author Fladio
 */
public class MainScreenController implements Initializable {

    /*----------------------------------  MAINSCREEN ------------------------------*/
    
   // private ImageView imgpp;    
    @FXML
    private ImageView circleImg;
    @FXML
    private Label labelId;
    @FXML
    private Label labelNama;
    @FXML
    private Label labelPosition;
    @FXML
    private Pane btDataBarang;
    @FXML
    private Pane btKasir;
    @FXML
    private Pane btDataKeuangan;
    @FXML
    private Pane ripplerDataBarang;
    @FXML
    private Pane ripplerDataKeuangan;
    @FXML
    private Pane ripplerKasir;
    @FXML
    private Pane btProfil;
    @FXML
    private Pane btLogOut;
    
    
    @FXML
    private void handleButtonKasir (MouseEvent e) throws IOException {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) btKasir.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/hazshope/KasirScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(Exception excep) {
            System.out.println(excep + "excep");
        }
    }
    
    
    @FXML
    private void handleButtonDataBarang (MouseEvent e) throws IOException {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) btDataBarang.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/hazshope/DataBarangScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception excep) {
            System.out.println(excep);
        }
    }
    
    
    @FXML
    private void goToProfil(MouseEvent e) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btProfil.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/hazshope/ProfileScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    
    @FXML
    private void LogOut(MouseEvent e)  {
        try {
            FileWriter fw = new FileWriter("src\\res\\datalogin.txt");
            fw.write("null");
            fw.close();     
            System.out.println("berhasil");
            
            Stage stage;
            Parent root;
            stage = (Stage) btLogOut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/hazshope/LoginScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException eg) {
            System.out.println(e);
        }    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        JFXRippler rippler = new JFXRippler(ripplerDataBarang);
        JFXRippler rippler1 = new JFXRippler(ripplerDataKeuangan);
        JFXRippler rippler2 = new JFXRippler(ripplerKasir);
        rippler.setRipplerFill(Paint.valueOf("#08c5ff"));
        rippler1.setRipplerFill(Paint.valueOf("#08c5ff"));
        rippler2.setRipplerFill(Paint.valueOf("#08c5ff"));
        btDataBarang.getChildren().add(rippler);
        btDataKeuangan.getChildren().add(rippler1);
        btKasir.getChildren().add(rippler2);
        
        try {
            File f = new File("src\\res\\datalogin.txt");
            BufferedReader  b =  new BufferedReader(new FileReader(f));
            String readLine = "";
            int x = 0;
            
            while( (readLine = b.readLine()) != null ) {
                System.out.println(readLine);
                switch (x) {
                    case 0 : labelId.setText(readLine); break;
                    case 3 : labelNama.setText(readLine);break;
                    case 4 : Image img = new Image("/res/img/pp/dio.jpg", 201,201, false, true);
                             //circleImg.setFill(new ImagePattern(img));
                             //imgpp.setImage(img);break;
                    case 5 : labelPosition.setText(readLine);break;
                }
                x++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }    
    
}
