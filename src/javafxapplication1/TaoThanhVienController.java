/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author My_Love_Is_My
 */
public class TaoThanhVienController implements Initializable {
    
    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private JFXTextField tfHoTen;
    @FXML
    private JFXTextField tfSDT;
    @FXML
    private JFXButton btnTao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void taoThanhVien(ActionEvent event) {
        String sql = "INSERT INTO thanhvien(hoten, sdt, ngaytao) VALUES(?, ?, ?)";
        
        // lay ngay hom nay
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        System.out.println(dateFormat.format(today));
        
        try{
            ps = connect.prepareStatement(sql);
            ps.setString(1, tfHoTen.getText());
            ps.setString(2, tfSDT.getText());
            ps.setString(3, dateFormat.format(today));
            ps.execute();
        }catch(Exception e){
            System.err.println(e);
        }
        
        btnTao.getScene().getWindow().hide();
    }

}
