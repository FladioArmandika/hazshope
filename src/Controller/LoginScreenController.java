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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Fladio
 */
public class LoginScreenController implements Initializable {

     /*----------------------------------  LOGINSCREEN ------------------------------*/
    @FXML
    private Pane paneLoginScreen;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField tfPassword;
    @FXML
    private JFXButton btLogin;
    @FXML
    private Label labelLoginNotValid;
    
    @FXML
    void makeLogin(ActionEvent event) throws SQLException, IOException {
        String username = tfUsername.getText().toString();
        String password = tfPassword.getText().toString();
        
        Authentication auth = new Authentication();
        User user = new User(username);
        
        if(auth.Authentication(username, password) == true) {
            
            Stage stage;
            Parent root;
            
            stage = (Stage) btLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/hazshope/MainScreen.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            labelLoginNotValid.setVisible(true);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelLoginNotValid.setVisible(false);
    }    
    
}
