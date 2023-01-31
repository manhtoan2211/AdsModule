package com.ads.control.ads.wrapper;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;

public class ApAdError {

    private LoadAdError loadAdError;
    private AdError adError;
    private String message = "";

    public ApAdError(AdError adError) {
        this.adError = adError;
    }

    public ApAdError(LoadAdError loadAdError) {
        this.loadAdError = loadAdError;
    }

    public ApAdError(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        if (loadAdError!=null)
            return loadAdError.getMessage();
        if (adError!=null)
            return adError.getMessage();
        if (!message.isEmpty())
            return message;
        return "unknown error";
    }
}
