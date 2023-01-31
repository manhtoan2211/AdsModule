package com.ads.control.ads.wrapper;

import com.google.android.gms.ads.interstitial.InterstitialAd;

public class ApInterstitialAd extends ApAdBase{
    private InterstitialAd interstitialAd;

    public ApInterstitialAd(StatusAd status) {
        super(status);
    }

    public ApInterstitialAd() {
    }

    public ApInterstitialAd(InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
        status = StatusAd.AD_LOADED;
    }

    public void setInterstitialAd(InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
        status = StatusAd.AD_LOADED;
    }

    @Override
    public boolean isReady() {
        return interstitialAd != null;
    }

    public InterstitialAd getInterstitialAd() {
        return interstitialAd;
    }
}
