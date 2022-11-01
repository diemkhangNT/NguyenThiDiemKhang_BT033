package com.example.nguyenthidiemkhang_bt033;


import android.app.Application;

import java.util.ArrayList;
public class App extends Application {
    public static ArrayList<Contact> data;

    @Override
    public void onCreate() {
        super.onCreate();
        if(data == null){
            data = new ArrayList<>();
        }
    }

    public static ArrayList<Contact> initDataForContact(){

        data.add(new Contact(1,"Zachary","Moore", R.drawable.moore, "(273)-352-7112", "zachary.moore@example.com", "01/01/2000"));
        data.add(new Contact(2,"Dominic"," Thunes", R.drawable.thunes, "(067)-453-1128","dominic.thunes@example.com", "01/01/2000"));
        data.add(new Contact(3,"Apolline", "Renard",R.drawable.renard, "(094)-134-9948", "apolline.renard@example.com", "01/01/2000"));
        data.add(new Contact(4,"Maria","Pascual", R.drawable.pascual, "(167)-351-1900", "maria.pascual@example.com ", "01/01/2000"));
        data.add(new Contact(5,"Djordy", "Valkema", R.drawable.valkema, "(015)-242-8092", "djordy.valkema@example.com", "01/01/2000"));
        data.add(new Contact(6,"Jenny", "Jones", R.drawable.jones, "(536)-393-6219", "jenny.jones@example.com", "01/01/2000"));
        data.add(new Contact(7,"Ceylan", "Çatalbaş", R.drawable.catalbas, "(536)-393-6219","ceylan.catalbas@example.com", "01/01/2000"));
        data.add(new Contact(8,"Seraina", "Henry", R.drawable.henry, "(077)-673-2231", "seraina.henry@example.com", "01/01/2000"));
        return data;
    }
}
