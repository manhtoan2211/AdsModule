package com.ads.control.ads.wrapper;

import com.applovin.mediation.MaxAd;
import com.google.android.gms.ads.AdValue;

public class ApAdValue {
    private AdValue admobAdValue;
    private MaxAd  maxAdValue;

    public ApAdValue(MaxAd maxAdValue) {
        this.maxAdValue = maxAdValue;
    }

    public ApAdValue(AdValue admobAdValue) {
        this.admobAdValue = admobAdValue;
    }

    public AdValue getAdmobAdValue() {
        return admobAdValue;
    }

    public MaxAd getMaxAdValue() {
        return maxAdValue;
    }
}
