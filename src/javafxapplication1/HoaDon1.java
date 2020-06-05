/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author My_Love_Is_My
 */
public class HoaDon1 {
    String maHD;
    String ngayTaoHD;
    String tongGia;
    String maTV;
    String maNV;

    public HoaDon1(String maHD, String ngayTaoHD, String tongGia, String maTV, String maNV) {
        this.maHD = maHD;
        this.ngayTaoHD = ngayTaoHD;
        this.tongGia = tongGia;
        this.maTV = maTV;
        this.maNV = maNV;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayTaoHD() {
        return ngayTaoHD;
    }

    public void setNgayTaoHD(String ngayTaoHD) {
        this.ngayTaoHD = ngayTaoHD;
    }

    public String getTongGia() {
        return tongGia;
    }

    public void setTongGia(String tongGia) {
        this.tongGia = tongGia;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
