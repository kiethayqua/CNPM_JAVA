/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author My_Love_Is_My
 */
public class HoaDon {

    private final SimpleStringProperty tenNV;
    private final SimpleStringProperty tongGia;
    private final SimpleStringProperty ngayTao;
    private final SimpleStringProperty stt;
    private final SimpleStringProperty sdt;
    private final SimpleStringProperty mahd;

    public String getTongGia() {
        return tongGia.get();
    }

    public String getNgayTao() {
        return ngayTao.get();
    }

    public String getStt() {
        return stt.get();
    }

    public String getTenNV() {
        return tenNV.get();
    }

    public String getSdt() {
        return sdt.get();
    }

    public String getMahd() {
        return mahd.get();
    }

    public HoaDon(String tongGia, String tenNV, String ngayTao, String sdt, String stt, String mahd) {
        this.tenNV = new SimpleStringProperty(tenNV);
        this.tongGia = new SimpleStringProperty(tongGia);
        this.ngayTao = new SimpleStringProperty(ngayTao);
        this.stt = new SimpleStringProperty(stt);
        this.sdt = new SimpleStringProperty(sdt);
        this.mahd = new SimpleStringProperty(mahd);
    }
}
