/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    @FXML
    private StackPane rootPane;

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

        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, tfHoTen.getText());
            ps.setString(2, tfSDT.getText());
            ps.setString(3, dateFormat.format(today));
            ps.execute();
            btnTao.getScene().getWindow().hide();
        } catch (Exception e) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });
            dialogLayout.setBody(new Text("Tên Trà Sữa đã Tồn Tại"));
            dialogLayout.setActions(button);
            dialog.show();
        }
    }

}
