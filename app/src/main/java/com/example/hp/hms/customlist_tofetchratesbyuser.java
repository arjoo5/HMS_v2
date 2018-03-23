package com.example.hp.hms;

/**
 * Created by HP on 2/22/2018.
 */

public class customlist_tofetchratesbyuser {
    String service;
    int rate;

    public customlist_tofetchratesbyuser(String service, int rate) {
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
