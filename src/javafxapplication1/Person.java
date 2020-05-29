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
public class Person {

    public String getQuyen() {
        return quyen.get();
    }
    
    public void setQuyen(String quyen){
        this.quyen.set(quyen);
    }

    public String getUserName() {
        return userName.get();
    }
    
    public void setUserName(String userName){
        this.userName.set(userName);
    }
    
    public String getStt() {
        return stt.get();
    }

    public void setStt(String stt) {
        this.stt.set(stt);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCmnd() {
        return cmnd.get();
    }

    public void setCmnd(String cmnd) {
        this.cmnd.set(cmnd);
    }

    public String getBirthDay() {
        return birthDay.get();
    }

    public void setBirthDay(String birthDay) {
        this.birthDay.set(birthDay);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    private final SimpleStringProperty userName;
    private final SimpleStringProperty stt;
    private final SimpleStringProperty name;
    private final SimpleStringProperty cmnd;
    private final SimpleStringProperty birthDay;
    private final SimpleStringProperty country;
    private final SimpleStringProperty quyen;

    public Person(String userName, String stt, String name, String cmnd, String birthDay, String country, String quyen) {
        this.userName = new SimpleStringProperty(userName);
        this.stt = new SimpleStringProperty(stt);
        this.name = new SimpleStringProperty(name);
        this.cmnd = new SimpleStringProperty(cmnd);
        this.birthDay = new SimpleStringProperty(birthDay);
        this.country = new SimpleStringProperty(country);
        this.quyen = new SimpleStringProperty(quyen);
    }
}
