package com.ads.control.ads.wrapper;

import com.google.android.gms.ads.AdValue;

public class ApAdValue {
    private AdValue admobAdValue;

    public ApAdValue(AdValue admobAdValue) {
        this.admobAdValue = admobAdValue;
    }

    public AdValue getAdmobAdValue() {
        return admobAdValue;
    }
}
