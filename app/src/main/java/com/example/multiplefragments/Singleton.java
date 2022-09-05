package com.example.multiplefragments;

public class Singleton {
    private static Singleton single_instance = null;
    private String data;

    private Singleton() {
//objectclone method
    }

    public static Singleton getInstance() {
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
