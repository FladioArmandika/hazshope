/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Fladio
 */
public class ProfileScreenController implements Initializable{

    @FXML
    private JFXButton btBackHome;

    @FXML
    private Label labelId;

    @FXML
    private ImageView avatar;
    
    @FXML
    private Label labelNama;

    @FXML
    private Label labelJabatan;

    @FXML
    private Label labelGolongan;

    @FXML
    private TextArea tfAlamat;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNoHp;

    @FXML
    private TextField tfNoTelp;

   

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        File f = new File("src\\res\\datalogin.txt");
        String readLine = "";
        int x = 0;
        try {
            BufferedReader buff = new BufferedReader(new FileReader(f));
            
            while( (readLine = buff.readLine()) != null) {
                System.out.println(readLine);
                switch (x) {
                    case 0 : labelId.setText(readLine); break;
                    case 3 : labelNama.setText(readLine);break;
                    case 4 : //avatar.setImage(new Image("/res/img/pp/dio.jpg", 201,201, false, true));
                             //circleImg.setFill(new ImagePattern(img));
                             //imgpp.setImage(img);break;
                    case 5 : labelJabatan.setText(readLine);break;
                }
                x++;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (IOException ex) {
               Logger.getLogger(ProfileScreenController.class.getName()).log(Level.SEVERE, null, ex);
           }
        
    }
    
}
