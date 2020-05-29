/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author My_Love_Is_My
 */
public class LoginController implements Initializable {

    public static String loginAccount;
    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField userName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        String sql = "SELECT * FROM TaiKhoan, NhanVien WHERE TaiKhoan.TaiKhoan = NhanVien.TaiKhoan AND TaiKhoan.TaiKhoan=? AND TaiKhoan.MatKhau=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, userName.getText());
            ps.setString(2, password.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                loginAccount = rs.getString("taikhoan");
                loginBtn.getScene().getWindow().hide();
                if (rs.getInt("quyen") == 1) {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));

                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setTitle("Admin");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } else if (rs.getInt("quyen") == 0) {
                    Parent root = FXMLLoader.load(getClass().getResource("NhanVienLogin.fxml"));

                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(root, 800, 600);

                    primaryStage.setTitle("Nhân Viên");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }

            } else {
                System.out.println("Login thất bại!");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

}
