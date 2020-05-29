/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javafxapplication1.LoginController.loginAccount;

/**
 *
 * @author My_Love_Is_My
 */
public class AdminLoginController implements Initializable {

    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private TableView<Person> tableNhanVien;

    @FXML
    private JFXButton logoutAdmin;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField pfPassword;
    @FXML
    private JFXPasswordField pfConfirm;
    @FXML
    private JFXTextField tfHoTen;
    @FXML
    private JFXTextField tfCMND;
    @FXML
    private JFXDatePicker datePickerNgaySinh;
    @FXML
    private JFXTextField tfQueQuan;
    @FXML
    private ChoiceBox<?> choiceBoxChucVu;
    @FXML
    private JFXButton themNhanVienBtn;
    @FXML
    private JFXButton xoaNhanVienBtn;
    @FXML
    private JFXButton suaNhanVienBtn;
    @FXML
    private JFXButton clearInfoBtn;
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXTextField filterField;

    // Tra Sua
    @FXML
    private TableView<TraSua> tableTraSua;
    @FXML
    private JFXTextField tfTenTS;
    @FXML
    private JFXTextArea moTa;
    @FXML
    private JFXTextField tfGia;
    @FXML
    private ImageView imgView;
    private FileChooser fc;
    private File file;
    private Image img;
    private FileInputStream fis;
    @FXML
    private JFXTextField filterFieldTS;

    void loadNhanVien() {
        final ObservableList<Person> data = FXCollections.observableArrayList();
        String sql = "SELECT hoten, cmnd, ngaysinh, quequan, taikhoan, quyen FROM nhanvien";
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                String userName = rs.getString("taikhoan");
                String hoTen = rs.getString("hoten");
                String cmnd = rs.getString("cmnd");
                String ngaySinh = rs.getString("ngaysinh");
                String queQuan = rs.getString("quequan");
                int quyen = rs.getInt("quyen");
                data.add(new Person(userName, String.valueOf(stt), hoTen, cmnd, ngaySinh, queQuan, String.valueOf(quyen)));
            }
            tableNhanVien.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "* *"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        // table NhanVien
        tableNhanVien.setEditable(true);
        final ObservableList<String> IMPORTVARIABLES = FXCollections.observableArrayList("Nhân Viên", "Quản Lí");
        ((ChoiceBox) choiceBoxChucVu).setItems(IMPORTVARIABLES);
        choiceBoxChucVu.getSelectionModel().selectFirst();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePickerNgaySinh.setValue(LocalDate.parse("01/01/1999", formatter));

        TableColumn column1 = new TableColumn("STT");
        column1.setCellValueFactory(new PropertyValueFactory<Person, String>("stt"));
        column1.setPrefWidth(47);

        TableColumn column2 = new TableColumn("Họ Tên");
        column2.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        column2.setPrefWidth(153);

        TableColumn column3 = new TableColumn("CMND");
        column3.setCellValueFactory(new PropertyValueFactory<Person, String>("cmnd"));
        column3.setPrefWidth(105);

        TableColumn column4 = new TableColumn("Ngày Sinh");
        column4.setCellValueFactory(new PropertyValueFactory<Person, String>("birthDay"));
        column4.setPrefWidth(120);

        TableColumn column5 = new TableColumn("Quê Quán");
        column5.setCellValueFactory(new PropertyValueFactory<Person, String>("country"));
        column5.setPrefWidth(124);

        tableNhanVien.getColumns().addAll(column1, column2, column3, column4, column5);
        loadNhanVien();
        
        // Tra Sua
        tableTraSua.setEditable(true);
        TableColumn clTS1 = new TableColumn("STT");
        clTS1.setCellValueFactory(new PropertyValueFactory<TraSua, String>("stt"));
        clTS1.setPrefWidth(50);

        TableColumn clTS2 = new TableColumn("Tên Trà Sữa");
        clTS2.setCellValueFactory(new PropertyValueFactory<TraSua, String>("tenTraSua"));
        clTS2.setPrefWidth(180);

        TableColumn clTS3 = new TableColumn("Mô Tả");
        clTS3.setCellValueFactory(new PropertyValueFactory<TraSua, String>("moTa"));
        clTS3.setPrefWidth(155);

        TableColumn clTS4 = new TableColumn("Giá Thành");
        clTS4.setCellValueFactory(new PropertyValueFactory<TraSua, String>("gia"));
        clTS4.setPrefWidth(133);

        tableTraSua.getColumns().addAll(clTS1, clTS2, clTS3, clTS4);
        loadTraSua();

    }

    @FXML
    private void logoutAdmin(ActionEvent event) throws IOException {
        logoutAdmin.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Stage primaryStage = new Stage();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void selectedItems(MouseEvent event) {

        TableViewSelectionModel<Person> selectionModel = tableNhanVien.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Person> selectedItems = selectionModel.getSelectedItems();

        String userName = selectedItems.get(0).getUserName();

        String sql = "SELECT * FROM taikhoan WHERE taikhoan=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next()) {
                String password = rs.getString("matkhau");
                tfUsername.setText(userName);
                tfUsername.setDisable(true);
                pfPassword.setText(password);
                pfConfirm.setText(password);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        tfHoTen.setText(selectedItems.get(0).getName());
        tfCMND.setText(selectedItems.get(0).getCmnd());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePickerNgaySinh.setValue(LocalDate.parse(selectedItems.get(0).getBirthDay(), formatter));
        tfQueQuan.setText(selectedItems.get(0).getCountry());
        String checkAdmin = selectedItems.get(0).getQuyen();
        if (checkAdmin.equals("1")) {
            choiceBoxChucVu.getSelectionModel().select(1);
        } else {
            choiceBoxChucVu.getSelectionModel().select(0);
        }
    }

    boolean checkInputInfo(String userName, String cmnd) {
        if (tfUsername.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền Username"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        String sql = "SELECT taikhoan FROM taikhoan WHERE taikhoan=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            if (rs.next()) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });

                dialogLayout.setBody(new Text("Tài khoản này đã tồn tại"));
                dialogLayout.setActions(button);
                dialog.show();
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        if (pfPassword.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền Password"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        if (pfConfirm.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền Confirm Password"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        if (!pfPassword.getText().equals(pfConfirm.getText())) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Xác nhận mật khẩu không trùng khớp"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        if (tfHoTen.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền Họ tên"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        if (tfCMND.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền CMND"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        sql = "SELECT cmnd FROM nhanvien WHERE cmnd=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, cmnd);
            rs = ps.executeQuery();
            if (rs.next()) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });

                dialogLayout.setBody(new Text("CMND này đã tồn tại"));
                dialogLayout.setActions(button);
                dialog.show();
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        if (tfQueQuan.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền Quê quán"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        return true;
    }

    @FXML
    private void themNhanVien(ActionEvent event) {
        String sql = "INSERT INTO taikhoan VALUES(?, ?)";
        boolean next = false;
        boolean success = false;
        if (checkInputInfo(tfUsername.getText(), tfCMND.getText())) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, tfUsername.getText());
                ps.setString(2, pfConfirm.getText());
                ps.execute();
                next = true;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        if (next) {
            String sql1 = "INSERT INTO nhanvien(hoten, cmnd, ngaysinh, quequan, taikhoan, quyen) VALUES(?, ?, ?, ?, ?, ?)";
            try {
                ps = connect.prepareStatement(sql1);
                ps.setString(1, tfHoTen.getText());
                ps.setString(2, tfCMND.getText());
                ps.setString(3, datePickerNgaySinh.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                ps.setString(4, tfQueQuan.getText());
                ps.setString(5, tfUsername.getText());
                if (choiceBoxChucVu.getSelectionModel().getSelectedIndex() == 0) {
                    ps.setInt(6, 0);
                } else {
                    ps.setInt(6, 1);
                }
                ps.execute();
                success = true;

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        if (success) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Thêm Nhân Viên Thành Công"));
            dialogLayout.setActions(button);
            dialog.show();
        }
        loadNhanVien();

    }

    @FXML
    private void xoaNhanVien(ActionEvent event) {
        boolean success = false;
        String sql = "DELETE FROM nhanvien WHERE taikhoan=?";
        String sql1 = "DELETE FROM taikhoan WHERE taikhoan=?";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác Nhận");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa Nhân Viên này ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, tfUsername.getText());
                if (tfUsername.getText().equals(loginAccount)) {
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("OK");
                    button.setStyle("-fx-background-color: #337ab7;");
                    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                        dialog.close();

                    });

                    dialogLayout.setBody(new Text("Không thể xóa tài khoản đang đăng nhập"));
                    dialogLayout.setActions(button);
                    dialog.show();

                    return;
                }
                ps.execute();
            } catch (SQLException e) {
                System.err.println(e);
            }
            try {
                ps = connect.prepareStatement(sql1);
                ps.setString(1, tfUsername.getText());
                ps.execute();
                success = true;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        if (success) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Xóa Viên Thành Công"));
            dialogLayout.setActions(button);
            dialog.show();
        }
        //tableNhanVien.getItems().clear();
        loadNhanVien();
    }

    @FXML
    private void suaNhanVien(ActionEvent event) {
        boolean success = false;
        String cmnd = "";
        String loadCMND = "SELECT cmnd FROM nhanvien WHERE taikhoan=?";
        try {
            ps = connect.prepareStatement(loadCMND);
            ps.setString(1, tfUsername.getText());
            rs = ps.executeQuery();
            while (rs.next()) {
                cmnd = rs.getString("cmnd");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        String sql = "UPDATE taikhoan SET matkhau=? WHERE taikhoan=?";
        String sql1 = "UPDATE nhanvien SET hoten=?, ngaysinh=?, quequan=?, quyen=? WHERE taikhoan=?";
        String sql2 = "UPDATE nhanvien SET hoten=?, cmnd=?, ngaysinh=?, quequan=?, quyen=? WHERE taikhoan=?";

        if (checkInputInfo("", "")) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Xác Nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn chỉnh sửa Nhân Viên này ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                if (cmnd.equals(tfCMND.getText())) {
                    try {
                        ps = connect.prepareStatement(sql);
                        ps.setString(1, pfPassword.getText());
                        ps.setString(2, tfUsername.getText());
                        ps.execute();
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
                    try {
                        ps = connect.prepareStatement(sql1);
                        ps.setString(1, tfHoTen.getText());

                        //ps.setString(2, tfCMND.getText());
                        ps.setString(2, datePickerNgaySinh.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        ps.setString(3, tfQueQuan.getText());
                        if (choiceBoxChucVu.getSelectionModel().getSelectedIndex() == 0) {
                            ps.setInt(4, 0);
                        } else {
                            ps.setInt(4, 1);
                        }
                        ps.setString(5, tfUsername.getText());
                        ps.execute();
                        success = true;
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
                } else {
                    String sql3 = "SELECT * FROM nhanvien WHERE cmnd=?";
                    try {
                        ps = connect.prepareStatement(sql3);
                        ps.setString(1, tfCMND.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            JFXDialogLayout dialogLayout = new JFXDialogLayout();
                            JFXButton button = new JFXButton("OK");
                            button.setStyle("-fx-background-color: #337ab7;");
                            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                                dialog.close();
                            });

                            dialogLayout.setBody(new Text("CMND đã tồn tại"));
                            dialogLayout.setActions(button);
                            dialog.show();
                            return;
                        } else {
                            try {
                                ps = connect.prepareStatement(sql);
                                ps.setString(1, pfPassword.getText());
                                ps.setString(2, tfUsername.getText());
                                ps.execute();
                            } catch (SQLException e) {
                                System.err.println(e);
                            }
                            try {
                                ps = connect.prepareStatement(sql2);
                                ps.setString(1, tfHoTen.getText());

                                ps.setString(2, tfCMND.getText());
                                ps.setString(3, datePickerNgaySinh.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                ps.setString(4, tfQueQuan.getText());
                                if (choiceBoxChucVu.getSelectionModel().getSelectedIndex() == 0) {
                                    ps.setInt(5, 0);
                                } else {
                                    ps.setInt(5, 1);
                                }
                                ps.setString(6, tfUsername.getText());
                                ps.execute();
                                success = true;
                            } catch (SQLException e) {
                                System.err.println(e);
                            }

                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
                if (success) {
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("OK");
                    button.setStyle("-fx-background-color: #337ab7;");
                    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                        dialog.close();
                    });

                    dialogLayout.setBody(new Text("Sửa Viên Thành Công"));
                    dialogLayout.setActions(button);
                    dialog.show();
                }
                //tableNhanVien.getItems().clear();
                loadNhanVien();
            }

        }
    }

    @FXML
    private void clearInfo(ActionEvent event) {
        tfUsername.setText("");
        tfUsername.setDisable(false);
        pfPassword.setText("");
        pfConfirm.setText("");
        tfHoTen.setText("");
        tfCMND.setText("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePickerNgaySinh.setValue(LocalDate.parse("01/01/1999", formatter));
        tfQueQuan.setText("");
        choiceBoxChucVu.getSelectionModel().selectFirst();
    }

    @FXML
    private void filterNhanVien(MouseEvent event) {
        FilteredList<Person> filteredData = new FilteredList<>(tableNhanVien.getItems(), e -> true);

        try {
            filterField.setOnKeyReleased(e -> {
                filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Person>) person -> {

                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (person.getCountry().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (person.getBirthDay().contains(lowerCaseFilter)) {
                            return true;
                        } else if (person.getCmnd().contains(lowerCaseFilter)) {
                            return true;
                        } else {
                            return false;
                        }
                    });

                });
            });

            SortedList<Person> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableNhanVien.comparatorProperty());

            tableNhanVien.setItems(sortedData);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /* 
                                    Tra Sua
     */
    void loadTraSua() {
        final ObservableList<TraSua> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trasua";
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                String tenTraSua = rs.getString("tents");
                String moTa = rs.getString("mota");
                int gia = rs.getInt("gia");
                String maTS = String.valueOf(rs.getInt("mats"));

                data.add(new TraSua(tenTraSua, moTa, String.valueOf(gia), String.valueOf(stt), maTS));
            }
            tableTraSua.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void selectedItemsTS(MouseEvent event) {
        TableViewSelectionModel<TraSua> selectionModel = tableTraSua.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TraSua> selectedItems = selectionModel.getSelectedItems();
        try {
            tfTenTS.setText(selectedItems.get(0).getTenTraSua());
            moTa.setText(selectedItems.get(0).getMoTa());
            tfGia.setText(selectedItems.get(0).getGia());

            String maTS = selectedItems.get(0).getMaTS();
            showImage(maTS);
        } catch (Exception e) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Bảng chưa có dữ liệu"));
            dialogLayout.setActions(button);
            dialog.show();
        }

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
                img = new Image("file:photo.jpg", imgView.getFitWidth(), imgView.getFitHeight(), true, true);
                imgView.setImage(img);
                imgView.setPreserveRatio(true);
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
    private void chooseImage(ActionEvent event) {
        file = fc.showOpenDialog(null);
        if (file != null) {
            img = new Image(file.getAbsoluteFile().toURI().toString(), imgView.getFitWidth(), imgView.getFitHeight(), true, true);
            imgView.setImage(img);
            imgView.setPreserveRatio(true);
        }
    }

    @FXML
    private void themTraSua(ActionEvent event) {
        String sql = "INSERT INTO trasua(tents, mota, gia, hinhanh) VALUES (?, ?, ?, ?)";
        boolean success = false;

        if (checkInputInfoTS() && checkTenTraSua("")) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, tfTenTS.getText());
                ps.setString(2, moTa.getText());
                ps.setInt(3, Integer.valueOf(tfGia.getText()));
                fis = new FileInputStream(file);
                ps.setBinaryStream(4, fis, file.length());

                ps.execute();
                success = true;
            } catch (SQLException e) {
                System.err.println(e);
            } catch (Exception ex) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });
                dialogLayout.setBody(new Text("Mời chọn hình ảnh"));
                dialogLayout.setActions(button);
                dialog.show();
            }
            if (success) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });

                dialogLayout.setBody(new Text("Thêm Trà Sữa Thành Công"));
                dialogLayout.setActions(button);
                dialog.show();
                clearInfoTS(event);
            }
            loadTraSua();
        }
    }

    @FXML
    private void xoaTraSua(ActionEvent event) {
        boolean success = false;
        TableSelectionModel<TraSua> selectionModel = tableTraSua.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TraSua> selectedItems = selectionModel.getSelectedItems();
        String maTS = selectedItems.get(0).getMaTS();

        String sql = "DELETE FROM trasua WHERE mats=?";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác Nhận");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa Đồ Uống này ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setInt(1, Integer.valueOf(maTS));
                ps.execute();
                success = true;
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        if (success) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Xóa Thành Công"));
            dialogLayout.setActions(button);
            dialog.show();
            clearInfoTS(event);
        }
        loadTraSua();
    }

    @FXML
    private void suaTraSua(ActionEvent event) throws SQLException {
        boolean success = false;
        TableViewSelectionModel<TraSua> selectionModel = tableTraSua.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TraSua> selectedItems = selectionModel.getSelectedItems();
        String maTS = selectedItems.get(0).getMaTS();
        String sql = "UPDATE trasua SET tents=?, mota=?, gia=?, hinhanh=? WHERE mats=?";

        String tenTS = "";
        String loadTenTS = "SELECT tents from trasua where mats=?";
        try {
            ps = connect.prepareCall(loadTenTS);
            ps.setInt(1, Integer.valueOf(maTS));
            rs = ps.executeQuery();
            while (rs.next()) {
                tenTS = rs.getString("tents");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        String sql1 = "UPDATE trasua SET mota=?, gia=?, hinhanh=? WHERE mats=?";

        if (checkInputInfoTS() && checkTenTraSua("")) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Xác Nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn chỉnh sửa Trà Sữa này ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                if (tenTS.equals(tfTenTS.getText())) {
                    if (file != null) {
                        try {
                            ps = connect.prepareStatement(sql1);
                            //ps.setString(1, tfTenTS.getText());
                            ps.setString(1, moTa.getText());
                            ps.setInt(2, Integer.valueOf(tfGia.getText()));
                            fis = new FileInputStream(file);
                            ps.setBinaryStream(3, (InputStream) fis, (int) file.length());
                            ps.setInt(4, Integer.valueOf(maTS));

                            ps.executeUpdate();
                            success = true;
                        } catch (Exception ex) {
                            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            String sql2 = "UPDATE trasua SET tents=?, mota=?, gia=? WHERE mats=?";
                            ps = connect.prepareStatement(sql2);
                            ps.setString(1, tfTenTS.getText());
                            ps.setString(2, moTa.getText());
                            ps.setInt(3, Integer.valueOf(tfGia.getText()));
                            ps.setInt(4, Integer.valueOf(maTS));
                            ps.executeUpdate();
                            success = true;
                        } catch (SQLException e) {
                            System.err.println(e);
                        }
                    }
                } else {
                    String sql3 = "SELECT * FROM trasua WHERE tents=?";
                    try {
                        ps = connect.prepareCall(sql3);
                        ps.setString(1, tfTenTS.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            JFXDialogLayout dialogLayout = new JFXDialogLayout();
                            JFXButton button = new JFXButton("OK");
                            button.setStyle("-fx-background-color: #337ab7;");
                            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                                dialog.close();
                            });

                            dialogLayout.setBody(new Text("Tên Trà Sữa Đã Tồn Tại"));
                            dialogLayout.setActions(button);
                            dialog.show();
                            return;
                        } else {
                            if (file != null) {
                                try {
                                    ps = connect.prepareStatement(sql1);
                                    //ps.setString(1, tfTenTS.getText());
                                    ps.setString(1, moTa.getText());
                                    ps.setInt(2, Integer.valueOf(tfGia.getText()));
                                    fis = new FileInputStream(file);
                                    ps.setBinaryStream(3, (InputStream) fis, (int) file.length());
                                    ps.setInt(4, Integer.valueOf(maTS));

                                    ps.executeUpdate();
                                    success = true;
                                } catch (Exception ex) {
                                    Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                try {
                                    String sql2 = "UPDATE trasua SET tents=?, mota=?, gia=? WHERE mats=?";
                                    ps = connect.prepareStatement(sql2);
                                    ps.setString(1, tfTenTS.getText());
                                    ps.setString(2, moTa.getText());
                                    ps.setInt(3, Integer.valueOf(tfGia.getText()));
                                    ps.setInt(4, Integer.valueOf(maTS));
                                    ps.executeUpdate();
                                    success = true;
                                } catch (SQLException e) {
                                    System.err.println(e);
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
                }
                if (success) {
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("OK");
                    button.setStyle("-fx-background-color: #337ab7;");
                    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                        dialog.close();
                    });

                    dialogLayout.setBody(new Text("Sửa Trà Sữa Thành Công"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    clearInfoTS(event);
                }
                loadTraSua();

            }
        }
    }

    @FXML
    private void clearInfoTS(ActionEvent event) {
        tfTenTS.setText("");
        moTa.setText("");
        tfGia.setText("");
        imgView.setImage(null);
    }

    @FXML
    private void filterTraSua(MouseEvent event) {
        FilteredList<TraSua> filteredData = new FilteredList<>(tableTraSua.getItems(), e -> true);

        try {
            filterFieldTS.setOnKeyReleased(e -> {
                filterFieldTS.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super TraSua>) trasua -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (trasua.getTenTraSua().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (trasua.getMoTa().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (trasua.getGia().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                });
            });

            SortedList<TraSua> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableTraSua.comparatorProperty());
            tableTraSua.setItems(sortedData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    boolean checkInputInfoTS() {
        if (tfTenTS.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền tên Trà Sữa"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }

        if (moTa.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });
            dialogLayout.setBody(new Text("Chưa điền mô tả"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }
        if (tfGia.getText().equals("") || !tfGia.getText().matches("([0-9]+(\\\\.[0-9+])?)+")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });
            dialogLayout.setBody(new Text("Giá thành không hợp lệ !"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }

        return true;
    }

    boolean checkTenTraSua(String tenTS) {
        TableSelectionModel<TraSua> selectModel = tableTraSua.getSelectionModel();
        ObservableList<TraSua> selectedItems = selectModel.getSelectedItems();
        //      String maTS = selectedItems.get(0).getMaTS();
        try {
            String sql1 = "SELECT tents from trasua WHERE tents=?";
            ps = connect.prepareStatement(sql1);
            ps.setString(1, tenTS);

            rs = ps.executeQuery();
            if (rs.next()) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });

                dialogLayout.setBody(new Text("Đồ Uống này đã tồn tại"));
                dialogLayout.setActions(button);
                dialog.show();
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return true;
    }

}
