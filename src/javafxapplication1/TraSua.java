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
public class TraSua {
     private final SimpleStringProperty tenTraSua;
    private final SimpleStringProperty moTa;
    private final SimpleStringProperty gia;
    private final SimpleStringProperty stt;
    private final SimpleStringProperty maTS;
 
    
    public String getTenTraSua() {
        return tenTraSua.get();
    }

    public void setTenTraSua(String tenTraSua) {
        this.tenTraSua.set(tenTraSua);
    }

    public String getMoTa() {
        return moTa.get();
    }

    public void setMoTa(String moTa) {
        this.moTa.set(moTa);
    }

    public String getGia() {
        return gia.get();
    }

    public void setGia(String gia) {
        this.gia.set(gia);
    }
    
    public String getStt() {
        return stt.get();
    }

    public void setStt(String stt) {
        this.stt.set(stt);
    }
  
    public String getMaTS() {
        return maTS.get();
    }


    public TraSua(String tenTraSua, String moTa, String gia, String stt, String maTS) {
        this.tenTraSua = new SimpleStringProperty(tenTraSua);
        this.moTa = new SimpleStringProperty(moTa);
        this.gia = new SimpleStringProperty(gia);
        this.stt = new SimpleStringProperty(stt);
        this.maTS = new SimpleStringProperty(maTS);
        
    }
}
