/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author My_Love_Is_My
 */
public class ComponentTraSua extends AnchorPane {
    
    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;
    private String maTS;
    private int soLuong;
    private Image img;
    static ObservableList<ThanhToan> dataThanhToan = FXCollections.observableArrayList();
    
    public ComponentTraSua(String tenTS, int giaTS, String maTS, int soLuong){
        FXMLUtils.loadFXML(this);
        labelTenTS.setText(tenTS);
        labelGiaTS.setText(String.valueOf(giaTS));
        this.maTS = maTS;
        showImage(maTS);   
    }
    
    String getMaTS(){
        return this.maTS;
    }
    
    int getSoLuong(){
        return this.soLuong;
    }
    
    void setSoLuong(int soLuong){
        this.soLuong = soLuong;
    }
    
    
    private void showImage(String maTS) {
        String sql = "SELECT hinhanh FROM trasua WHERE mats=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(maTS));
            rs = ps.executeQuery();
            while (rs.next()) {
                InputStream is = rs.getBinaryStream("hinhanh");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size = is.read(contents)) != -1) {
                    os.write(contents, 0, size);
                }
                img = new Image("file:photo.jpg", avatarTS.getFitWidth(), avatarTS.getFitHeight(), true, true);
                avatarTS.setImage(img);
                avatarTS.setPreserveRatio(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    

    @FXML
    private ImageView avatarTS;
    @FXML
    private Label labelTenTS;
    @FXML
    private Label labelGiaTS;
    @FXML
    private JFXButton btnMuaTS;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clickMua(ActionEvent event) {
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Chọn số lượng");
        textInput.getDialogPane().setContentText("Nhập Số Lượng: ");
        Optional<String>result = textInput.showAndWait();
        TextField input = textInput.getEditor();
        
        if(input.getText() != null && input.getText().toString().length() != 0){
            this.soLuong = Integer.valueOf(input.getText());
            for(int i = 0; i < dataThanhToan.size(); i++){
                if(dataThanhToan.get(i).getMaTS() == this.maTS){
                    dataThanhToan.set(i, new ThanhToan(this.maTS, labelTenTS.getText(), this.soLuong, Integer.valueOf(labelGiaTS.getText())));
                    return;
                }
            }
            dataThanhToan.add(new ThanhToan(this.maTS, labelTenTS.getText(), this.soLuong, Integer.valueOf(labelGiaTS.getText())));
        }else{
            return;
        }
    }
    
    public String getTenTS(){
        return labelTenTS.getText();
    }
    
    public String getGiaTS(){
        return labelGiaTS.getText();
    }
    
}
