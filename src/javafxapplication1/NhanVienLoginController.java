/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafxapplication1.ComponentTraSua.dataThanhToan;

/**
 * FXML Controller class
 *
 * @author My_Love_Is_My
 */
public class NhanVienLoginController implements Initializable {

    Connection connect = KetNoiDB.ketNoi();
    PreparedStatement ps;
    ResultSet rs;
    private String maTS;

    @FXML
    private JFXListView<ComponentTraSua> menuTS;
    @FXML
    private TableView<ThanhToan> tableThanhToan;
    @FXML
    private JFXButton xoaBtn;
    @FXML
    private Label tongHoaDon;
    @FXML
    private Label giamGia;
    @FXML
    private Label thanhTien;
    @FXML
    private JFXButton thanhToanBtn;
    @FXML
    private JFXTextField tfMaThanhVien;
    @FXML
    private JFXButton dangXuatBtn;
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXButton taoThanhVienBtn;
    @FXML
    private JFXTextField tfTimKiem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableThanhToan.setEditable(true);
        TableColumn column1 = new TableColumn("Tên Trà Sữa");
        column1.setCellValueFactory(new PropertyValueFactory<ThanhToan, String>("tenTS"));
        //column1.setPrefWidth(47);

        TableColumn column2 = new TableColumn("Giá");
        column2.setCellValueFactory(new PropertyValueFactory<ThanhToan, String>("giaTS"));
        //column2.setPrefWidth(153);

        TableColumn column3 = new TableColumn("Số Lượng");
        column3.setCellValueFactory(new PropertyValueFactory<Person, String>("soLuong"));
        // column3.setPrefWidth(105);

        tableThanhToan.getColumns().addAll(column1, column2, column3);
        loadTraSua();

        tableThanhToan.setItems(ComponentTraSua.dataThanhToan);

        // tinh tong tien hoa don
        ComponentTraSua.dataThanhToan.addListener(new ListChangeListener<ThanhToan>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends ThanhToan> pChange) {
                while (pChange.next()) {
                    int tongTien = 0;
                    for (int i = 0; i < tableThanhToan.getItems().size(); i++) {
                        tongTien += tableThanhToan.getItems().get(i).getGiaTS() * tableThanhToan.getItems().get(i).getSoLuong();
                    }
                    tongHoaDon.setText(String.valueOf(tongTien));
                    thanhTien.setText(String.valueOf(tongTien - Integer.valueOf(giamGia.getText())));
                }
            }
        });

        if (tableThanhToan.getItems().size() == 0 || !tableThanhToan.isFocused()) {
            xoaBtn.setDisable(true);
        }
    }

    void loadTraSua() {
        final ObservableList<ComponentTraSua> data = FXCollections.observableArrayList();
        String sql = "SELECT * FROM TraSua";
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maTS = String.valueOf(rs.getInt("mats"));
                String tenTS = rs.getString("tenTS");
                int gia = rs.getInt("gia");

                data.add(new ComponentTraSua(tenTS, gia, maTS, 0));
            }
            menuTS.setItems(data);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void selectedItem(MouseEvent event) {
        TableView.TableViewSelectionModel<ThanhToan> selectionModel = tableThanhToan.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<ThanhToan> selectedItems = selectionModel.getSelectedItems();

        this.maTS = selectedItems.get(0).getMaTS();

        xoaBtn.setDisable(false);
    }

    @FXML
    private void xoaThanhToan(ActionEvent event) {
        for (int i = 0; i < tableThanhToan.getItems().size(); i++) {
            if (tableThanhToan.getItems().get(i).getMaTS().equals(this.maTS)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác Nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc muốn xóa đồ uống này ?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    tableThanhToan.getItems().remove(i);
                }
            }
        }
    }

    @FXML
    private void inputMaThanhVien(KeyEvent event) {
        String sql = "SELECT * FROM ThanhVien WHERE sdt=?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, tfMaThanhVien.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                int tongHoaDon = Integer.valueOf(this.tongHoaDon.getText());
                int giamGia = tongHoaDon * 5 / 100;
                int thanhTien = tongHoaDon - giamGia;
                this.giamGia.setText(String.valueOf(giamGia));
                this.thanhTien.setText(String.valueOf(thanhTien));
            } else {
                int tongHoaDon = Integer.valueOf(this.tongHoaDon.getText());
                int giamGia = 0;
                int thanhTien = tongHoaDon - giamGia;
                this.giamGia.setText(String.valueOf(giamGia));
                this.thanhTien.setText(String.valueOf(thanhTien));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void thanhToan(ActionEvent event) {
        // lay ma thanh vien
        String sql1 = "SELECT manv FROM NhanVien WHERE TaiKhoan=?";
        int maNV = -1;
        try {
            ps = connect.prepareStatement(sql1);
            ps.setString(1, LoginController.loginAccount);
            rs = ps.executeQuery();
            if (rs.next()) {
                maNV = rs.getInt("MaNV");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        // lay ma thanh vien va them vao hoa don
        String sql2 = "SELECT matv FROM ThanhVien WHERE sdt=?";
        try {
            // lay ngay hom nay
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date today = new Date();
            System.out.println(dateFormat.format(today));

            ps = connect.prepareStatement(sql2);
            ps.setString(1, tfMaThanhVien.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                int maTV = rs.getInt("matv");
                String sql3 = "INSERT INTO hoadon(ngaytao, tonggia, matv, manv) VALUES(?, ?, ?, ?)";
                try {
                    ps = connect.prepareStatement(sql3);
                    ps.setString(1, dateFormat.format(today));
                    ps.setInt(2, Integer.valueOf(thanhTien.getText()));
                    ps.setInt(3, maTV);
                    ps.setInt(4, maNV);
                    ps.execute();
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                String sql3 = "INSERT INTO hoadon(ngaytao, tonggia, manv) VALUES(?, ?, ?)";
                try {
                    ps = connect.prepareStatement(sql3);
                    ps.setString(1, dateFormat.format(today));
                    ps.setInt(2, Integer.valueOf(thanhTien.getText()));
                    ps.setInt(3, maNV);
                    ps.execute();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        // them vao chi tiet hoa don
        int maHD = -1;
        String sql4 = "SELECT MAX(mahd) AS maxhd FROM hoadon";
        try {
            ps = connect.prepareStatement(sql4);
            rs = ps.executeQuery();
            if (rs.next()) {
                maHD = rs.getInt("maxhd");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        String sql5 = "INSERT INTO chitiethd(mahd, mats, soluong) VALUES(?, ?, ?)";
        for (int i = 0; i < tableThanhToan.getItems().size(); i++) {

            try {
                ps = connect.prepareStatement(sql5);
                ps.setInt(1, maHD);
                ps.setInt(2, Integer.valueOf(tableThanhToan.getItems().get(i).getMaTS()));
                ps.setInt(3, tableThanhToan.getItems().get(i).getSoLuong());
                ps.execute();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #337ab7;");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mountEvent) -> {
            dialog.close();
        });

        dialogLayout.setBody(new Text("Thanh toán thành công"));
        dialogLayout.setActions(button);
        dialog.show();
        tfMaThanhVien.setText("");
        giamGia.setText("0");
        ComponentTraSua.dataThanhToan.clear();
    }

    @FXML
    private void dangXuat(ActionEvent event) throws IOException {
        ComponentTraSua.dataThanhToan.clear();
        dangXuatBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Stage primaryStage = new Stage();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void taoThanhVien(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TaoThanhVien.fxml"));

        Stage primaryStage = new Stage();
        Scene scene = new Scene(root, 266, 191);

        primaryStage.setTitle("Tạo Thành Viên");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void filterComponentTraSua(MouseEvent event) {
        FilteredList<ComponentTraSua> filteredData = new FilteredList<>(menuTS.getItems(), e -> true);
        
        try {
            tfTimKiem.setOnKeyReleased(e -> {
                tfTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super ComponentTraSua>) trasua -> {

                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();

                        if (trasua.getTenTS().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (trasua.getGiaTS().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else {
                            return false;
                        }
                    });

                });
            });

            SortedList<ComponentTraSua> sortedData = new SortedList<>(filteredData);

            menuTS.setItems(sortedData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
