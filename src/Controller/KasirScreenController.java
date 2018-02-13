/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JDialog;

/**
 * FXML Controller class
 *
 * @author Fladio
 */
public class KasirScreenController implements Initializable {

    @FXML
    private Pane paneKasir;
    
    @FXML
    private StackPane stackpaneKasir;
        
    @FXML
    private JFXTextField inputId;

    @FXML
    private JFXTextField inputQuantity;
    
    @FXML
    private Label labelBarangStok;

    @FXML
    private Label labelBarangHarga;

    @FXML
    private Label labelBarangNama;

    @FXML
    private Label labelBarangId;
    
    @FXML
    private Label warningId;
    
    @FXML
    private Label warningQuantity;
    
    @FXML
    private JFXButton btTambah;
    
    @FXML
    private JFXButton btHapus;
    
    @FXML
    private JFXButton btBackHome;
    
    @FXML
    private Label labelTotalHarga;
    
    @FXML
    private JFXTextField tfBayar;
    
    @FXML
    private Label labelSubmitBayar;
    
    @FXML
    private Label labelSubmitKembalian;
    
    @FXML
    private JFXButton btSubmit;
    
    @FXML
    private Label labelSubmitNotValid;
    
    @FXML
    private Label labelEmpName;
    
    @FXML
    private Label labelEmpId;
    
    @FXML
    private Label labelDate;
    
    @FXML
    private Label labelHour;
    
    @FXML 
    private JFXButton btOkDialog;
    
    @FXML
    private JFXTreeTableView<ItemJual> tabelPenjualan;
    
    ObservableList<ItemJual> itemJual;
    ArrayList<Integer> jumlahhargalist = new ArrayList<>();
   
    
    boolean idValid;
    boolean qtyValid;
    String idKasir;
    String quantityKasir;
    int stokvalid;
    int total = 0;

    
    @FXML
    void tambahBarang(ActionEvent e) {
        if (idValid && qtyValid) {
            try {
                String query = "SELECT * FROM barang WHERE id= '" + idKasir +"';";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) { System.out.println(quantityKasir);
                    int hj = Integer.parseInt(rs.getString("hargajual"));
                    int qk = Integer.parseInt(quantityKasir);
                    Integer totalhargaitem = hj * qk;

                    itemJual.add(new ItemJual(
                        rs.getString("id"), 
                        rs.getString("name"), 
                        NumberFormat.getNumberInstance().format(Integer.parseInt(rs.getString("hargajual"))), 
                        quantityKasir,
                        rs.getString("disc"),
                        NumberFormat.getNumberInstance().format(totalhargaitem) )
                    );
                    inputId.clear();
                    inputQuantity.clear();
                    labelBarangHarga.setText("");
                    labelBarangId.setText("");
                    labelBarangNama.setText("");
                    labelBarangStok.setText("");

                    jumlahhargalist.add(totalhargaitem);
                    total = total + totalhargaitem;
                    labelTotalHarga.setText(NumberFormat.getNumberInstance().format(total));
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    
    @FXML
    void hapusBarang(ActionEvent e) {
        String hargaT = tabelPenjualan.getSelectionModel().getSelectedItem().getValue().getHarga();
        TreeItem c = (TreeItem) tabelPenjualan.getSelectionModel().getSelectedItem();
        c.getParent().getChildren().remove(c);
        
        total = total - Integer.parseInt(hargaT);
        labelTotalHarga.setText(String.valueOf(total));
    }
    
    @FXML
    void submitPembayaran(ActionEvent e) {
        String uangmuka = tfBayar.getText();
        
        if (Integer.parseInt(uangmuka) < total) {
            labelSubmitNotValid.setVisible(true);    
            tfBayar.clear();
        } else {
            labelSubmitBayar.setText(NumberFormat.getNumberInstance().format(Integer.parseInt(uangmuka)) );
            labelSubmitKembalian.setText(NumberFormat.getNumberInstance().format(Integer.parseInt(uangmuka) - total));
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
                for (ItemJual j :itemJual) {
                     String query = "UPDATE barang SET stok = stok - " + j.getQuantity() + " WHERE id = " + j.getId() + ";";
                     Statement stmt = con.createStatement();
                     stmt.executeUpdate(query);
                }
                
                labelTotalHarga.setText("");
                labelSubmitBayar.setText("");
                labelSubmitKembalian.setText("");
                itemJual.clear();
                jumlahhargalist.clear();
                
                
                
                //Dialog
                stackpaneKasir.setVisible(true);
                JFXDialogLayout content = new JFXDialogLayout();
                content.setBody(new Text("Transaksi berhasil!"));
                
                JFXDialog dialog = new JFXDialog(stackpaneKasir, content, JFXDialog.DialogTransition.CENTER);
                JFXButton dialogButton = new JFXButton("Ok");
                dialogButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                        stackpaneKasir.setVisible(false);
                    }
                });
                
                content.setActions(dialogButton);
                dialog.show();
                
            } catch(Exception ed) {
                System.out.println(ed);
            }
            
        }
        
    }
    
    @FXML
    void backToHome(ActionEvent e) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btBackHome.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/hazshope/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Date
        labelDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        labelHour.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        
        //TOTAL HARGA
        labelTotalHarga.setText("0");
        
        //StackPane
        stackpaneKasir.setVisible(false);
        
        
        //warning sign
        inputQuantity.setDisable(true);
        warningId.setVisible(false);
        warningQuantity.setVisible(false);
        
        labelBarangHarga.setText("");
        labelBarangId.setText("");
        labelBarangNama.setText("");
        labelBarangStok.setText("");
        
        labelSubmitNotValid.setVisible(false);        

        inputId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                idKasir = inputId.getText();
                System.out.println(idKasir);
                
                try {
                    String query = "SELECT * FROM barang WHERE id=" + idKasir + ";" ;
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hazshope", "root", "91378531");
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    
                    String idlbl = " ";
                    
                    while(rs.next()) {
                        idlbl = rs.getString("id");
                        String namalbl = rs.getString("name");
                        String hargalbl = rs.getString("hargajual");
                        String stoklbl = rs.getString("stok");
                    
                        labelBarangId.setText(idlbl);
                        labelBarangNama.setText(namalbl);
                        labelBarangHarga.setText(hargalbl);
                        labelBarangStok.setText(stoklbl);
                        
                        stokvalid = Integer.parseInt(stoklbl);
                    }
                    
                    if ( !(labelBarangId.getText().contains(idlbl)) ) {
                        inputQuantity.setDisable(true); 
                        warningId.setVisible(true);
                        idValid = false;
                    } else {
                        inputQuantity.setDisable(false);
                        warningId.setVisible(false);
                        idValid = true;
                    }
                     
                } catch (Exception e) {
                   
                } 
            }
        });
        
        inputQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    int quantity = Integer.parseInt(inputQuantity.getText());
                    System.out.println(quantity + " da " + stokvalid);
                    if (quantity < 0 || quantity > stokvalid ) {
                        warningQuantity.setVisible(true);
                        qtyValid = false;
                    } else {
                        warningQuantity.setVisible(false);
                        quantityKasir = String.valueOf(quantity);
                        qtyValid = true;
                    }
                } catch (NumberFormatException e) {
                    
                }
            }
        });
        
        
        
        
        
        //Table
        JFXTreeTableColumn<ItemJual,String> colId = new JFXTreeTableColumn<>("ID");
        colId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().id;
            }
        });
        
        JFXTreeTableColumn<ItemJual, String> colNama = new JFXTreeTableColumn<>("NAMA");
        colNama.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().nama;
            }
        });
        
        JFXTreeTableColumn<ItemJual, String> colHargaSatuan = new JFXTreeTableColumn<>("HARGA SATUAN");
        colHargaSatuan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().hargaSatuan;
            }
        });
        
        JFXTreeTableColumn<ItemJual, String> colQty = new JFXTreeTableColumn<>("QTY");
        colQty.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().quantity;
            }
        });
        
        JFXTreeTableColumn<ItemJual, String> colDisc = new JFXTreeTableColumn<>("DISC");
        colDisc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().disc;
            }
        });
        
        JFXTreeTableColumn<ItemJual, String> colHarga = new JFXTreeTableColumn<>("JUMLAH HARGA");
        colHarga.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ItemJual, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ItemJual, String> param) {
                return param.getValue().getValue().harga;
            }
        });
        
        //col size
        colId.setPrefWidth(80);
        colNama.setPrefWidth(370);
        colHargaSatuan.setPrefWidth(150);
        colQty.setPrefWidth(40);
        colDisc.setPrefWidth(60);
        colHarga.setPrefWidth(150);
        
        
        //col-alignment
        colHargaSatuan.setStyle("-fx-alignment: CENTER;");
        colQty.setStyle("-fx-alignment: CENTER;");
        colDisc.setStyle("-fx-alignment: CENTER;");
        colHarga.setStyle("-fx-alignment: CENTER;");
                
                
        itemJual = FXCollections.observableArrayList();
        
        final TreeItem<ItemJual> root = new RecursiveTreeItem<ItemJual>(itemJual, RecursiveTreeObject::getChildren);
        tabelPenjualan.getColumns().setAll(colId,colNama,colHargaSatuan,colQty,colDisc,colHarga);
        tabelPenjualan.setRoot(root);
        tabelPenjualan.setShowRoot(false);
        
    } 
    
    class ItemJual extends RecursiveTreeObject<ItemJual> {
        StringProperty id;
        StringProperty nama;
        StringProperty hargaSatuan;
        StringProperty quantity;
        StringProperty disc;
        StringProperty harga;
        
        public ItemJual(String id, String nama, String hargaSatuan, String quantity, String disc ,String harga) {
            this.id = new SimpleStringProperty(id);
            this.nama = new SimpleStringProperty(nama);
            this.hargaSatuan = new SimpleStringProperty(hargaSatuan);
            this.quantity = new SimpleStringProperty(quantity);
            this.disc = new SimpleStringProperty(disc);
            this.harga = new SimpleStringProperty(harga);
        }

        public String getId() {
            return id.get();
        }

        public String getNama() {
            return nama.get();
        }

        public String getHargaSatuan() {
            return hargaSatuan.get();
        }

        public String getQuantity() {
            return quantity.get();
        }
        
        public String getDisc() {
            return disc.get();
        }

        public String getHarga() {
            return harga.get();
        }
    }
    
}
