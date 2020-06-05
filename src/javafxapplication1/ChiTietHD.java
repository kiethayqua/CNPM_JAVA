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
public class ChiTietHD {

    private final SimpleStringProperty stt;
    private final SimpleStringProperty tenTS;
    private final SimpleStringProperty soLuong;
    private final SimpleStringProperty gia;

    public String getStt() {
        return stt.get();
    }

    public String getTenTS() {
        return tenTS.get();
    }

    public String getSoLuong() {
        return soLuong.get();
    }

    public String getGia() {
        return gia.get();
    }

    public ChiTietHD(String stt, String tenTS, String soLuong, String gia) {
        this.stt = new SimpleStringProperty(stt);
        this.tenTS = new SimpleStringProperty(tenTS);
        this.soLuong = new SimpleStringProperty(soLuong);
        this.gia = new SimpleStringProperty(gia);
    }

}
