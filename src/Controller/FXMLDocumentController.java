/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import backsystem.Authentication;
import backsystem.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Fladio
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
   
    
    
   
    
    
    /*----------------------------------  MAINSCREEN ------------------------------*/
    @FXML
    private ImageView imgpp;    
    @FXML
    private Label labelId;
    @FXML
    private Label labelNama;
    @FXML
    private Label labelPosition;
    @FXML
    private JFXButton btDataBarang;
    @FXML
    private JFXButton btKasir;
    @FXML
    private JFXButton btDataKeuangan;
    @FXML
    private JFXButton btPengaturan;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
