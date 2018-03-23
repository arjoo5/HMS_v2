package com.example.hp.hms;

/**
 * Created by HP on 2/13/2018.
 */

public class ListItemProvider {
    String name,gender,phno,street,area,ano;

    public ListItemProvider(String name, String gender, String phno, String street, String area, String ano) {
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.street = street;
        this.area = area;
        this.ano = ano;
    }

    public String getNameSP() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhno() {
        return phno;
    }

    public String getStreet() {
        return street;
    }

    public String getArea() {
        return area;
    }

    public String getAno() {
        return ano;
    }
}
