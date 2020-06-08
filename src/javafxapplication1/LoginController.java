/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
    private StackPane rootPane;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField userName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    login();
                    rootPane.requestFocus();
                }
            }

        });
        userName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    login();
                    rootPane.requestFocus();
                }
            }

        });
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        login();
    }

    private void repeatFocus(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                repeatFocus(node);
            }
        });
    }

    private void login() {
        String sql = "SELECT * FROM TaiKhoan, NhanVien WHERE TaiKhoan.TaiKhoan = NhanVien.TaiKhoan AND TaiKhoan.TaiKhoan=? AND TaiKhoan.MatKhau=?";
        if (userName.getText().equals("") || password.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();

            });
            repeatFocus(dialogLayout);
            //dialogLayout.requestFocus();
            dialogLayout.setBody(new Text("Chưa Điền Tài Khoản Hoặc Mật Khẩu"));
            dialogLayout.setActions(button);
            dialog.show();
            dialogLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        dialog.close();
                    }
                }
            });

        } else {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, userName.getText());
                ps.setString(2, password.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    loginAccount = rs.getString("taikhoan");
                    if (rs.getInt("quyen") == 1) {
                        loginBtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));

                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root, 800, 600);

                        primaryStage.setTitle("Admin");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } else if (rs.getInt("quyen") == 0) {
                        loginBtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("NhanVienLogin.fxml"));

                        Stage primaryStage = new Stage();
                        Scene scene = new Scene(root, 800, 600);

                        primaryStage.setTitle("Nhân Viên");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } else if (rs.getInt("quyen") == -1) {
                        JFXDialogLayout dialogLayout = new JFXDialogLayout();
                        JFXButton button = new JFXButton("OK");
                        button.setStyle("-fx-background-color: #337ab7;");
                        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                            dialog.close();

                        });
                        dialogLayout.setBody(new Text("Tài Khoản Nhân Viên Này Không Khả Dụng"));
                        dialogLayout.setActions(button);
                        dialog.show();
                        repeatFocus(dialogLayout);
                        //dialogLayout.requestFocus();
                        dialogLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode() == KeyCode.ENTER) {
                                    dialog.close();
                                }
                            }
                        });
                    }

                } else {
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("OK");
                    button.setStyle("-fx-background-color: #337ab7;");
                    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                        dialog.close();

                    });
                    dialogLayout.setBody(new Text("Tên Tài Khoản Hoặc Mật Khẩu Không Đúng"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    repeatFocus(dialogLayout);
                    //dialogLayout.requestFocus();
                    dialogLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.ENTER) {
                                dialog.close();
                            }
                        }
                    });
                }

            } catch (SQLException e) {
                System.err.println(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
