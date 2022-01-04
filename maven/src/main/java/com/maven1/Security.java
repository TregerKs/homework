package com.maven1;

import com.google.gson.JsonArray;

import java.util.Date;
import java.util.List;

public class Security {
    String name;
    List<String> currency;
    String code;
    String date;

    public Security(String name, List<String> currency, String code, String date) {
        this.name = name;
        this.currency = currency;
        this.code = code;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Security{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
