/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller; 

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Fladio
 */
public class DataBarangScreenController implements Initializable {

    @FXML
    private JFXTreeTableView<Item> treeTabel;
    
    @FXML
    private JFXButton btBacktoHome;
    
    @FXML
    private JFXButton btTambahItem;
    @FXML
    private JFXTextField tfIdBarang;
    @FXML
    private JFXTextField tfNamaBarang;
    @FXML
    private JFXTextField tfHargaBeli;
    @FXML
    private JFXTextField tfHargaJual;
    @FXML
    private JFXTextField tfStok;
    
   
    @FXML
    private JFXButton btHapus;
    
    ObservableList<Item> Item;
    
    
    @FXML
    void tambahItem(ActionEvent e)  {
        String id = tfIdBarang.getText();
        String nama = tfNamaBarang.getText();
        String hargaBeli = tfHargaBeli.getText();
        String hargaJual = tfHargaJual.getText();
        String stok = tfStok.getText();
        
        Item.add(new Item(id,nama,hargaBeli,hargaJual,stok));
        tfIdBarang.clear();
        tfNamaBarang.clear();
        tfHargaBeli.clear();
        tfHargaJual.clear();
        tfStok.clear();
        
        try {
            String query1 = "INSERT INTO barang(id,name,hargabeli,hargajual,stok, disc)"
                      + "VALUES (" + id + ", '" + nama + "'," + hargaBeli + "," + hargaJual + "," + stok + ",0);";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query1);
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
    
    @FXML
    void clearTextField(ActionEvent e) {
        tfIdBarang.clear();
        tfNamaBarang.clear();
        tfHargaBeli.clear();
        tfHargaJual.clear();
        tfStok.clear();
    }
    
    @FXML
    void backToHome(ActionEvent e) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btBacktoHome.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/hazshope/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void hapusItem(ActionEvent e) {
        String idT =  treeTabel.getSelectionModel().getSelectedItem().getValue().getId();
        String namaT = treeTabel.getSelectionModel().getSelectedItem().getValue().getNama();
        String hBeliT = treeTabel.getSelectionModel().getSelectedItem().getValue().getHargaBeli();
        String hJualT = treeTabel.getSelectionModel().getSelectedItem().getValue().getHargaJual();
        String stokT = treeTabel.getSelectionModel().getSelectedItem().getValue().getStok();
        
        TreeItem c = (TreeItem)treeTabel.getSelectionModel().getSelectedItem();
        c.getParent().getChildren().remove(c);
        System.out.println("Remove");
        
        
        try {
            String query1 = "DELETE FROM barang WHERE id=" + idT + ";";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query1);
            
        } catch(SQLException sqlex) {
            System.out.println(sqlex);
        } catch (ClassNotFoundException en) {
            System.out.println(en);
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        //Tabel
        JFXTreeTableColumn<Item,String> colId = new JFXTreeTableColumn<>("ID");
        //colId.setPrefWidth(70);
        colId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().getValue().idBarang;
            }
        });
        
        
        JFXTreeTableColumn<Item,String> colNama = new JFXTreeTableColumn<>("NAMA BARANG");
        //colNama.setPrefWidth(250);
        colNama.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().getValue().namaBarang;
            }
        });
        
        JFXTreeTableColumn<Item,String> colhBeli = new JFXTreeTableColumn<>("HARGA BELI");
        colhBeli.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().getValue().hargaBeli;
            }
        });
        
        
        JFXTreeTableColumn<Item,String> colhJual = new JFXTreeTableColumn<>("HARGA JUAL");
        colhJual.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().getValue().hargaJual;
            }
        });
        
        
        JFXTreeTableColumn<Item,String> colStok = new JFXTreeTableColumn<>("STOK");
        colStok.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Item, String> param) {
                return param.getValue().getValue().stok;
            }
        });
        
        colStok.setStyle("-fx-alignment: TOP-CENTER;");
        colhBeli.setStyle("-fx-alignment: TOP-CENTER;");
        colhJual.setStyle("-fx-alignment: TOP-CENTER;");
        
        
       Item = FXCollections.observableArrayList();
       
        try {
            String query1 = "SELECT * FROM barang";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query1);
            
            while (rs.next()) {
                String idDB = rs.getString("id");
                String nameDB = rs.getString("name");
                String hargabeliDB = rs.getString("hargabeli");
                String hargajualDB = rs.getString("hargajual");
                String stok = rs.getString("stok");
                Item.add(new Item(idDB,
                                  nameDB,
                                  NumberFormat.getNumberInstance().format(Integer.parseInt(hargabeliDB)),
                                  NumberFormat.getNumberInstance().format(Integer.parseInt(hargajualDB)),
                                  stok));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        
        
        final TreeItem<Item> root = new RecursiveTreeItem<Item>(Item, RecursiveTreeObject::getChildren);
        treeTabel.getColumns().setAll(colId,colNama,colhBeli,colhJual,colStok);
        treeTabel.setRoot(root);
        treeTabel.setShowRoot(false);
        
    }    
    
    class Item extends RecursiveTreeObject<Item> {
        StringProperty no;
        StringProperty idBarang;
        StringProperty namaBarang;
        StringProperty hargaBeli;
        StringProperty hargaJual;
        StringProperty stok;
        
        public Item(String id, String nama, String hBeli, String hJual, String stok) {
            this.idBarang = new SimpleStringProperty(id);
            this.namaBarang = new SimpleStringProperty(nama);
            this.hargaBeli = new SimpleStringProperty(hBeli);
            this.hargaJual = new SimpleStringProperty(hJual);
            this.stok = new SimpleStringProperty(stok);
        }

        public String getId() {
            return idBarang.get();
        }
        
        public String getNama() {
            return namaBarang.get();
        }
        
        public String getHargaBeli() {
            return hargaBeli.get();
        }
        
        public String getHargaJual() {
            return hargaJual.get();
        }
        
        public String getStok() {
            return stok.get();
        }
    }  
    
}


