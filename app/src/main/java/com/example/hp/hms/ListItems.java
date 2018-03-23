package com.example.hp.hms;

/**
 * Created by HP on 2/10/2018.
 */

public class ListItems {
    String service;
    int rate;

    public ListItems(String service, int rate) {
        this.service = service;
        this.rate = rate;
    }

    public String getService() {
        return service;
    }

    public int getRate() {
        return rate;
    }
}
