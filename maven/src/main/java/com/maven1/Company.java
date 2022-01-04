package com.maven1;

import java.util.Date;
import java.util.List;

public class Company {
    int id;
    String name;
    String address;
    String phoneNumber;
    String inn;
    Date founded;
    List<Security> securities;

    public Company(int id, String name, String address, String phoneNumber, String inn, Date founded, List<Security> securities) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.inn = inn;
        this.founded = founded;
        this.securities = securities;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", inn='" + inn + '\'' +
                ", founded='" + founded + '\'' +
                ", securities=" + securities +
                '}'+"\n";
    }
}
