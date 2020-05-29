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
public class ThanhToan {
    private String maTS;
    private String tenTS;
    private int soLuong;
    private int giaTS;
    
    public ThanhToan(String maTS, String tenTS, int soLuong, int giaTS){
        this.maTS = maTS;
        this.tenTS = tenTS;
        this.soLuong = soLuong;
        this.giaTS = giaTS;
    }

    public String getMaTS() {
        return maTS;
    }

    public void setMaTS(String maTS) {
        this.maTS = maTS;
    }

    public String getTenTS() {
        return tenTS;
    }

    public void setTenTS(String tenTS) {
        this.tenTS = tenTS;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTS() {
        return giaTS;
    }

    public void setGiaTS(int giaTS) {
        this.giaTS = giaTS;
    }
    
    
}
