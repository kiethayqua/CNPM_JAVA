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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
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

    public static String maTV;
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
    private JFXTextField tfGia;
    @FXML
    private ImageView imgView;
    private FileChooser fc;
    private File file;
    private Image img;
    private FileInputStream fis;
    @FXML
    private JFXTextField filterFieldTS;

    // Quan Li Hoa Don
    @FXML
    private TableView<HoaDon> tableHoaDon;
    @FXML
    private JFXTextField filterFieldHD;
    @FXML
    private TableView<ChiTietHD> tableChiTietHD;

    // Quan Li Thanh Vien
    @FXML
    private TableView<ThanhVien> tableThanhVien;
    @FXML
    private JFXTextField tfTTV;
    @FXML
    private JFXTextField tfSDT;
    @FXML
    private JFXTextField tfDiem;
    @FXML
    private JFXTextField tfTim;
    @FXML
    private JFXDatePicker tfngayTao;
    @FXML
    private Button btSuaTV;
    @FXML
    private Button btXoaTV;
    @FXML
    private Button btThem;
    @FXML
    private ChoiceBox<String> choiceBoxStatus;
    @FXML
    private Button btXHD;
    @FXML
    private ChoiceBox<String> choiceBoxTrangThai;
    @FXML
    private JFXButton chonAnh;
    @FXML
    private JFXButton themTSBtn;
    @FXML
    private JFXButton xoaTSBtn;
    @FXML
    private JFXButton suaTSBtn;
    
    @FXML
    private Label lblDoanhThu;
    @FXML
    private JFXDatePicker datepickerHD;

    void loadNhanVien(int quyen) {
        final ObservableList<Person> data = FXCollections.observableArrayList();
        String sql = "SELECT hoten, cmnd, ngaysinh, quequan, taikhoan, quyen FROM nhanvien WHERE quyen = 0 OR quyen = 1";
        if (quyen == -1) {
            sql = "SELECT hoten, cmnd, ngaysinh, quequan, taikhoan, quyen FROM nhanvien WHERE quyen=?";
            themNhanVienBtn.setDisable(true);
            suaNhanVienBtn.setDisable(true);
            xoaNhanVienBtn.setDisable(true);
        }else{
            themNhanVienBtn.setDisable(false);
            suaNhanVienBtn.setDisable(false);
            xoaNhanVienBtn.setDisable(false);
        }
        try {
            ps = connect.prepareStatement(sql);
            if (quyen == -1) {
                ps.setInt(1, quyen);
            }
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                String userName = rs.getString("taikhoan");
                String hoTen = rs.getString("hoten");
                String cmnd = rs.getString("cmnd");
                String ngaySinh = rs.getString("ngaysinh");
                String queQuan = rs.getString("quequan");
                int q = rs.getInt("quyen");
                data.add(new Person(userName, String.valueOf(stt), hoTen, cmnd, ngaySinh, queQuan, String.valueOf(q)));
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

        // choicebox trạng thái làm việc
        final ObservableList<String> cbStatus = FXCollections.observableArrayList("Đang Làm Việc", "Đã Nghỉ Việc");
        ((ChoiceBox) choiceBoxStatus).setItems(cbStatus);
        choiceBoxStatus.getSelectionModel().selectFirst();

        // choicebox trạng thái của trà sữa
        final ObservableList<String> cbTrangThai = FXCollections.observableArrayList("Còn Bán", "Ngưng Bán");
        ((ChoiceBox) choiceBoxTrangThai).setItems(cbTrangThai);
        choiceBoxTrangThai.getSelectionModel().selectFirst();

        // table NhanVien
        // choicebox Chức vụ
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
        loadNhanVien(0);
        loadNhanVien(1);

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

// Hoa Don
        tableHoaDon.setEditable(true);
        TableColumn clHD1 = new TableColumn("STT");
        clHD1.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("stt"));
        clHD1.setPrefWidth(40);

        TableColumn clHD4 = new TableColumn("Tổng Giá");
        clHD4.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("tongGia"));
        clHD4.setPrefWidth(90);

        TableColumn clHD5 = new TableColumn("Tên Nhân Viên");
        clHD5.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("tenNV"));
        clHD5.setPrefWidth(122);      

        TableColumn clHD6 = new TableColumn("Ngày Tạo");
        clHD6.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("ngayTao"));
        clHD6.setPrefWidth(85);

        TableColumn clHD2 = new TableColumn("Mã Thành Viên");
        clHD2.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("sdt"));
        clHD2.setPrefWidth(123);

        tableHoaDon.getColumns().addAll(clHD1, clHD5, clHD6, clHD4, clHD2);
        loadHoaDon();

        // Chi Tiet HD
        tableChiTietHD.setEditable(true);
        TableColumn cl1 = new TableColumn("STT");
        cl1.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("stt"));
        cl1.setPrefWidth(50);

        TableColumn cl2 = new TableColumn("Tên Trà Sữa");
        cl2.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("tenTS"));
        cl2.setPrefWidth(126);

        TableColumn cl3 = new TableColumn("Số Lượng");
        cl3.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("soLuong"));
        cl3.setPrefWidth(80);      

        TableColumn cl4 = new TableColumn("Giá");
        cl4.setCellValueFactory(new PropertyValueFactory<HoaDon, String>("gia"));
        cl4.setPrefWidth(85);

        tableChiTietHD.getColumns().addAll(cl1, cl2, cl3, cl4);
        loadTraSua(1);

        // Thanh Vien
        //tableThanhVien
        tfngayTao.setValue(LocalDate.parse("01/01/2020", formatter));
        tableThanhVien.setEditable(true);
        TableColumn clTV1 = new TableColumn("STT");
        clTV1.setCellValueFactory(new PropertyValueFactory<ThanhVien, String>("stt"));
        clTV1.setPrefWidth(100);

        TableColumn clTV2 = new TableColumn("Tên Thành Viên");
        clTV2.setCellValueFactory(new PropertyValueFactory<ThanhVien, String>("tenTV"));
        clTV2.setPrefWidth(180);

        TableColumn clTV3 = new TableColumn("Số Điện Thoại");
        clTV3.setCellValueFactory(new PropertyValueFactory<ThanhVien, String>("SDT"));
        clTV3.setPrefWidth(155);

        TableColumn clTV4 = new TableColumn("Ngày Tạo");
        clTV4.setCellValueFactory(new PropertyValueFactory<ThanhVien, String>("ngayTao"));
        clTV4.setPrefWidth(133);

        tableThanhVien.getColumns().addAll(clTV1, clTV2, clTV3, clTV4);
        loadThanhVien();

        choiceBoxStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (choiceBoxStatus.getSelectionModel().getSelectedIndex() == 0) {
                    loadNhanVien(0);
                    loadNhanVien(1);
                } else if (choiceBoxStatus.getSelectionModel().getSelectedIndex() == 1) {
                    loadNhanVien(-1);
                }
            }

        });

        choiceBoxTrangThai.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (choiceBoxTrangThai.getSelectionModel().getSelectedIndex() == 0) {
                    loadTraSua(1);
                } else if (choiceBoxTrangThai.getSelectionModel().getSelectedIndex() == 1) {
                    loadTraSua(-1);
                }
            }

        });

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

        selectionModel.setSelectionMode(SelectionMode.SINGLE);

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
            if(tfUsername.getText().equals(loginAccount)){
               choiceBoxChucVu.setDisable(true);
            }else{
               choiceBoxChucVu.setDisable(false);
            }
        } else if(checkAdmin.equals("0")){
            choiceBoxChucVu.getSelectionModel().select(0);
            choiceBoxChucVu.setDisable(false);
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

        if (!tfCMND.getText().matches("([0-9]+(\\\\.[0-9+])?)+") || tfCMND.getText().length() != 9) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });
            dialogLayout.setBody(new Text("CMND là số và phải có 9 kí tự"));
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
        loadNhanVien(0);
        loadNhanVien(1);

    }

    @FXML
    private void xoaNhanVien(ActionEvent event) {
        boolean success = false;
        String sql = "UPDATE nhanvien SET quyen = -1 WHERE taikhoan = ?";
//        String sql = "DELETE FROM nhanvien WHERE taikhoan=?";
//        String sql1 = "DELETE FROM taikhoan WHERE taikhoan=?";
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
        }
        if (success) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Xóa Nhân Viên Thành Công"));
            dialogLayout.setActions(button);
            dialog.show();
        }
        //tableNhanVien.getItems().clear();
        loadNhanVien(0);
        loadNhanVien(1);
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
                loadNhanVien(0);
                loadNhanVien(1);
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
    void loadTraSua(int trangThai) {
        final ObservableList<TraSua> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trasua WHERE trangthai= ?";
        if(trangThai == -1){
            chonAnh.setDisable(true);
            themTSBtn.setDisable(true);
            suaTSBtn.setDisable(true);
            xoaTSBtn.setDisable(true);
        }else{
            chonAnh.setDisable(false);
            themTSBtn.setDisable(false);
            suaTSBtn.setDisable(false);
            xoaTSBtn.setDisable(false);
        }
        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, trangThai);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                String tenTraSua = rs.getString("tents");
                String tt = rs.getInt("trangthai") == 1 ? "Đang bán" : "Ngưng bán";
                int gia = rs.getInt("gia");
                String maTS = String.valueOf(rs.getInt("mats"));

                data.add(new TraSua(tenTraSua, tt, String.valueOf(gia), String.valueOf(stt), maTS));
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
        String sql = "INSERT INTO trasua(tents, trangthai, gia, hinhanh) VALUES (?, ?, ?, ?)";
        boolean success = false;

        if (checkInputInfoTS() && checkTenTraSua("")) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, tfTenTS.getText());
                ps.setInt(2, 1);
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
            loadTraSua(1);
        }
    }

    @FXML
    private void xoaTraSua(ActionEvent event) {
        boolean success = false;
        TableSelectionModel<TraSua> selectionModel = tableTraSua.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TraSua> selectedItems = selectionModel.getSelectedItems();
        String maTS = selectedItems.get(0).getMaTS();

        String sql = "UPDATE trasua SET trangthai=-1 WHERE mats=?";
        //String sql = "DELETE FROM trasua WHERE mats=?";
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
        loadTraSua(1);
    }

    @FXML
    private void suaTraSua(ActionEvent event) throws SQLException {
        boolean success = false;
        TableViewSelectionModel<TraSua> selectionModel = tableTraSua.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<TraSua> selectedItems = selectionModel.getSelectedItems();
        String maTS = selectedItems.get(0).getMaTS();
        String sql = "UPDATE trasua SET tents=?, gia=?, hinhanh=? WHERE mats=?";

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

        String sql1 = "UPDATE trasua SET gia=?, hinhanh=? WHERE mats=?";

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
                            ps.setInt(1, Integer.valueOf(tfGia.getText()));
                            fis = new FileInputStream(file);
                            ps.setBinaryStream(2, (InputStream) fis, (int) file.length());
                            ps.setInt(3, Integer.valueOf(maTS));

                            ps.executeUpdate();
                            success = true;
                        } catch (Exception ex) {
                            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            String sql2 = "UPDATE trasua SET tents=?, gia=? WHERE mats=?";
                            ps = connect.prepareStatement(sql2);
                            ps.setString(1, tfTenTS.getText());
                            ps.setInt(2, Integer.valueOf(tfGia.getText()));
                            ps.setInt(3, Integer.valueOf(maTS));
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
                                    ps.setInt(1, Integer.valueOf(tfGia.getText()));
                                    fis = new FileInputStream(file);
                                    ps.setBinaryStream(2, (InputStream) fis, (int) file.length());
                                    ps.setInt(3, Integer.valueOf(maTS));

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
                                    ps.setInt(2, Integer.valueOf(tfGia.getText()));
                                    ps.setInt(3, Integer.valueOf(maTS));
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
                loadTraSua(1);

            }
        }

    }

    @FXML
    private void clearInfoTS(ActionEvent event) {
        tfTenTS.setText("");
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

    // Hoa Don
    void loadHoaDon() {
        final ObservableList<HoaDon> data = FXCollections.observableArrayList();

        String sql = "SELECT hd.mahd, hd.matv, hd.tonggia, hd.ngaytao, nv.hoten FROM hoadon hd JOIN nhanvien nv ON hd.manv = nv.manv";
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                int tongGia = rs.getInt("tonggia");
                String tenNV = rs.getString("hoten");
                String ngayTao = rs.getString("ngaytao");
                String sdt = " ";
                int mahd = rs.getInt("mahd");
                if (String.valueOf(rs.getInt("matv")) != null) {
                    String sql1 = "SELECT sdt from thanhvien where matv=?";
                    try {
                        PreparedStatement ps1;
                        ResultSet rs1;
                        ps1 = connect.prepareStatement(sql1);
                        ps1.setInt(1, rs.getInt("matv"));
                        rs1 = ps1.executeQuery();
                        if (rs1.next()) {
                            sdt = rs1.getString("sdt");
                        }
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
                }
                data.add(new HoaDon(String.valueOf(tongGia), tenNV, ngayTao, sdt, String.valueOf(stt), String.valueOf(mahd)));
            }
            tableHoaDon.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    void loadChiTietHD(int mahd) {
        final ObservableList<ChiTietHD> data = FXCollections.observableArrayList();
        String sql = "SELECT ct.soluong, ts.gia, ts.tents FROM chitiethd ct JOIN trasua ts ON ts.mats = ct.mats where mahd=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, mahd);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;
                String tenTS = rs.getString("tents");
                int gia = rs.getInt("gia");
                int soLuong = rs.getInt("soluong");
                data.add(new ChiTietHD(String.valueOf(stt), tenTS, String.valueOf(soLuong), String.valueOf(gia)));

            }
            tableChiTietHD.setItems(data);

        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    @FXML
    private void selectedItemsHD(MouseEvent event) {
        TableViewSelectionModel<HoaDon> selectionModel = tableHoaDon.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<HoaDon> selectedItems = selectionModel.getSelectedItems();

        try {
            loadChiTietHD(Integer.valueOf(selectedItems.get(0).getMahd()));
        } catch (NumberFormatException e) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("B?ng chua có d? li?u"));
            dialogLayout.setActions(button);
            dialog.show();
        }
    }

    @FXML
    private void xoaHoaDon(ActionEvent event) {
        boolean success = false;
        TableSelectionModel<HoaDon> selectionModel = tableHoaDon.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<HoaDon> selectedItems = selectionModel.getSelectedItems();
        String maHD = selectedItems.get(0).getMahd();

        String sql = "DELETE FROM hoadon WHERE mahd=?";
        //String sql1 = "DELETE FROM chitiethd WHERE mahd=?";
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác Nh?n");
        alert.setHeaderText(null);
        alert.setContentText("B?n có ch?c mu?n xóa Hóa Ðon này ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setInt(1, Integer.valueOf(maHD));
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
            loadChiTietHD(Integer.valueOf(maHD));
        }
        loadHoaDon();
    }

    @FXML
    private void filterHoaDon(MouseEvent event) {
        FilteredList<HoaDon> filteredData = new FilteredList<>(tableHoaDon.getItems(), e -> true);

        try {
            filterFieldHD.setOnKeyReleased(e -> {
                filterFieldHD.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super HoaDon>) hd -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (hd.getTenNV().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (hd.getNgayTao().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (hd.getTongGia().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (hd.getStt().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }else if (hd.getSdt().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }else {
                            return false;
                        }
                    });
                });
            });

            SortedList<HoaDon> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableHoaDon.comparatorProperty());
            tableHoaDon.setItems(sortedData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Quan Li Thanh Vien
    //
    void loadThanhVien() {
        final ObservableList<ThanhVien> data = FXCollections.observableArrayList();
        String sql = "SELECT MaTV, HoTen, SDT, NgayTao FROM THANHVIEN";
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            int stt = 0;
            while (rs.next()) {
                stt++;

                String tenTV = rs.getString("HoTen");
                String SDT = rs.getString("SDT");
                String ngayTao = rs.getString("NgayTao");
                String maTV = rs.getString("maTV");
                //    int quyen = rs.getInt("quyen");
                data.add(new ThanhVien(String.valueOf(stt), tenTV, SDT, ngayTao, maTV));
            }
            tableThanhVien.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void selectedItemsTV(MouseEvent event) {
        TableViewSelectionModel<ThanhVien> selectionModel = tableThanhVien.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ThanhVien> selectedItems = selectionModel.getSelectedItems();
        try {
            tfTTV.setText(selectedItems.get(0).getTenTV());
            tfSDT.setText(selectedItems.get(0).getSDT());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tfngayTao.setValue(LocalDate.parse(selectedItems.get(0).getNgayTao(), formatter));

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

    private void clearInfoTV(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        tfTTV.setText("");
        tfSDT.setText("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tfngayTao.setValue(LocalDate.parse(dateFormat.format(today), formatter));
    }

    @FXML
    private void filterThanhVien(MouseEvent event) {
        FilteredList<ThanhVien> filteredData = new FilteredList<>(tableThanhVien.getItems(), e -> true);

        try {
            tfTim.setOnKeyReleased(e -> {
                tfTim.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super ThanhVien>) thanhvien -> {

                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        if (thanhvien.getTenTV().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (thanhvien.getSDT().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (thanhvien.getNgayTao().contains(lowerCaseFilter)) {
                            return true;

                        } else {
                            return false;
                        }
                    });

                });
            });

            SortedList<ThanhVien> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableThanhVien.comparatorProperty());

            tableThanhVien.setItems(sortedData);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    boolean checkInputInfoTV() {
        if (tfTTV.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });

            dialogLayout.setBody(new Text("Chưa điền tên Thành Viên"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }

        if (tfSDT.getText().equals("")) {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("OK");
            button.setStyle("-fx-background-color: #337ab7;");
            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                dialog.close();
            });
            dialogLayout.setBody(new Text("Chưa điền Số Điện Thoại"));
            dialogLayout.setActions(button);
            dialog.show();
            return false;
        }

        return true;
    }

    boolean checkSDT(String SDT) {
        TableSelectionModel<ThanhVien> selectModel = tableThanhVien.getSelectionModel();
        ObservableList<ThanhVien> selectedItems = selectModel.getSelectedItems();
        try {
            String sql1 = "SELECT sdt from thanhvien WHERE sdt=?";
            ps = connect.prepareStatement(sql1);
            ps.setString(1, SDT);

            rs = ps.executeQuery();
            if (rs.next()) {
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("OK");
                button.setStyle("-fx-background-color: #337ab7;");
                JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                    dialog.close();
                });

                dialogLayout.setBody(new Text("Số Điện Thoại này đã tồn tại"));
                dialogLayout.setActions(button);
                dialog.show();
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return true;
    }

    @FXML
    private void themThanhVien(ActionEvent event) {
        String sql = "INSERT INTO thanhvien VALUES(?, ?,?)";

        boolean success = false;
        if (checkInputInfoTV() && checkSDT(tfSDT.getText())) {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, tfTTV.getText());
                ps.setString(2, tfSDT.getText());
                ps.setString(3, tfngayTao.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

            dialogLayout.setBody(new Text("Thêm Thành Viên Thành Công"));
            dialogLayout.setActions(button);
            dialog.show();
        }
        loadThanhVien();

    }

    @FXML
    private void suaThanhVien(ActionEvent event) throws SQLException {
        boolean success = false;
        TableViewSelectionModel<ThanhVien> selectionModel = tableThanhVien.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ThanhVien> selectedItems = selectionModel.getSelectedItems();
        String maTV = selectedItems.get(0).getMaTV();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        String sql = "UPDATE thanhvien SET HoTen=?, SDT=? WHERE MaTV=?";

        String SDT = "";
        String loadSDT = "SELECT SDT from thanhvien where matv=?";
        try {
            ps = connect.prepareCall(loadSDT);
            ps.setInt(1, Integer.valueOf(maTV));
            rs = ps.executeQuery();
            while (rs.next()) {
                SDT = rs.getString("sdt");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        System.err.println(SDT);
        String sql1 = "UPDATE thanhvien SET hoten=?, ngaytao=? WHERE MaTV=?";

        if (checkInputInfoTV() && checkSDT("")) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Xác Nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn chỉnh sửa Thành Viên này ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                if (SDT.equals(tfSDT.getText())) {

                    try {
                        ps = connect.prepareStatement(sql1);

                        ps.setString(1, tfTTV.getText());
                        ps.setString(2, dateFormat.format(today));

                        ps.setInt(3, Integer.valueOf(maTV));

                        ps.execute();
                        success = true;
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                } else {
                    String sql3 = "SELECT * FROM thanhvien WHERE SDT=?";
                    try {
                        ps = connect.prepareCall(sql3);
                        ps.setString(1, tfSDT.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            JFXDialogLayout dialogLayout = new JFXDialogLayout();
                            JFXButton button = new JFXButton("OK");
                            button.setStyle("-fx-background-color: #337ab7;");
                            JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
                            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
                                dialog.close();
                            });

                            dialogLayout.setBody(new Text("Số Điện Thoại Đã Tồn Tại"));
                            dialogLayout.setActions(button);
                            dialog.show();
                            return;
                        } else {
                            try {
                                String sql2 = "UPDATE thanhvien SET hoten=?, SDT=?, ngaytao=? WHERE matv=?";
                                ps = connect.prepareStatement(sql2);
                                ps.setString(1, tfTTV.getText());
                                ps.setString(2, tfSDT.getText());
                                ps.setString(3, dateFormat.format(today));
                                ps.setInt(4, Integer.valueOf(maTV));

                                ps.executeUpdate();
                                success = true;

                            } catch (SQLException e) {
                                System.err.println(e);
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println(e);
                    }
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

                dialogLayout.setBody(new Text("Sửa Thành Viên Thành Công"));
                dialogLayout.setActions(button);
                dialog.show();
                clearInfoTV(event);
            }
            loadThanhVien();

        }
    }

    @FXML
    void XemHoaDon(ActionEvent event) throws IOException {
        TableViewSelectionModel<ThanhVien> selectionModel = tableThanhVien.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ThanhVien> selectedItems = selectionModel.getSelectedItems();
        maTV = selectedItems.get(0).getMaTV();
        System.out.println(maTV);

        Parent root = FXMLLoader.load(getClass().getResource("XemHoaDon.fxml"));

        Scene scene = new Scene(root, 510, 400);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Thông tin Hóa Đơn");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void thongkeHD(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = datepickerHD.getValue().format(formatter);
        String sql = "SELECT SUM(tonggia) AS tongtien FROM hoadon WHERE ngaytao=?";
        int sum = 0;
        try{
            ps = connect.prepareStatement(sql);
            ps.setString(1, date);
            rs = ps.executeQuery();
            if(rs.next()){
                sum = rs.getInt("tongtien");
            }
            lblDoanhThu.setText("Tổng Doanh Thu Ngày " + date + " là: " + sum + " VNĐ");
        }catch(Exception e){
            System.err.println(e);
        }
    }

}
