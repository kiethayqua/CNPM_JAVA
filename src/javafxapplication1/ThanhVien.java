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
public class ThanhVien {

    private String maTV;
    private String tenTV;
    private String SDT;
    private String ngayTao;
    private String stt;

    public ThanhVien(String stt, String tenTV, String SDT, String ngayTao, String maTV) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.SDT = SDT;
        this.ngayTao = ngayTao;
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

}
