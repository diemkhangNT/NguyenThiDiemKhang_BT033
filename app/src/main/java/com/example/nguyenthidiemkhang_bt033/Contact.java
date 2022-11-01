package com.example.nguyenthidiemkhang_bt033;


import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact> {
    int id;
    String fname;
    String lname;
    int Image;
    String number;
    String email;
    String birthday;


    public Contact(int id, String fname, String lname, int image, String number, String email, String birthday) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        Image = image;
        this.number = number;
        this.email = email;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public int compareTo(Contact o) {
        if (fname.compareToIgnoreCase(o.fname)==0){
            return lname.compareToIgnoreCase(o.lname);
        }
        return fname.compareToIgnoreCase(o.fname);
    }
    public String toString(){
        return "Peoples{" +
                "id" + id +
                "lName" + lname+
                "image"+Image+
                "phone"+ number+
                "email"+email+
                "birthday"+birthday +'\''+ "}";
    }
}
