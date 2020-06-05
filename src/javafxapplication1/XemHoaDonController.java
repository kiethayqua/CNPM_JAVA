/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafxapplication1.AdminLoginController.maTV;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class XemHoaDonController implements Initializable {

    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private TableView<HoaDon1> tbXemHD;

    @FXML
    private TableColumn<HoaDon1, String> clmaHD;
    @FXML
    private TableColumn<HoaDon1, String> clngayTao;
    @FXML
    private TableColumn<HoaDon1, String> cltongTien;
    @FXML
    private TableColumn<HoaDon1, String> clmaTV;
    @FXML
    private TableColumn<HoaDon1, String> clmaNV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        System.out.println(maTV);
        tbXemHD.setEditable(true);
        String sql = "SELECT mahd, ngaytao, tonggia, matv, manv FROM HoaDon Where matv=? ";
        final ObservableList<HoaDon1> data = FXCollections.observableArrayList();
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, maTV);
            rs = ps.executeQuery();

            while (rs.next()) {

                String maHD = rs.getString("mahd");
                String ngayTaoHD = rs.getString("ngaytao");
                String tongGia = rs.getString("tonggia");
                String maTV = rs.getString("matv");
                String maNV = rs.getString("manv");

                data.add(new HoaDon1(maHD, ngayTaoHD, tongGia, maTV, maNV));
            }
            tbXemHD.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
        clmaHD.setCellValueFactory(new PropertyValueFactory<HoaDon1, String>("maHD"));
        clngayTao.setCellValueFactory(new PropertyValueFactory<HoaDon1, String>("ngayTaoHD"));
        cltongTien.setCellValueFactory(new PropertyValueFactory<HoaDon1, String>("tongGia"));
        clmaTV.setCellValueFactory(new PropertyValueFactory<HoaDon1, String>("maTV"));
        clmaNV.setCellValueFactory(new PropertyValueFactory<HoaDon1, String>("maNV"));

    }

}
