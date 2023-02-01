package com.example.andmoduleads;

import com.ads.control.ads.CustomAds;
import com.ads.control.application.AdsMultiDexApplication;
import com.ads.control.billing.AppPurchase;
import com.ads.control.admob.Admob;
import com.ads.control.admob.AppOpenManager;
import com.ads.control.config.AdsConfig;
import com.example.andmoduleads.activity.MainActivity;
import com.example.andmoduleads.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends AdsMultiDexApplication {

    private static MyApplication context;

    public static MyApplication getApplication() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Admob.getInstance().setNumToShowAds(0);

        initBilling();
        initAds();
    }

    private void initAds() {
        String environment = BuildConfig.DEBUG ? AdsConfig.ENVIRONMENT_DEBUG : AdsConfig.ENVIRONMENT_PRODUCTION;
        adsConfig = new AdsConfig(this, environment);

        // Optional: enable ads resume
        adsConfig.setIdAdResume(BuildConfig.AD_APPOPEN_RESUME);

        // Optional: setup list device test - recommended to use
        listTestDevice.add("EC25F576DA9B6CE74778B268CB87E431");
        adsConfig.setListDeviceTest(listTestDevice);
        adsConfig.setIntervalInterstitialAd(15);

        CustomAds.getInstance().init(this, adsConfig, false);

        // Auto disable ad resume after user click ads and back to app
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        // If true -> onNextAction() is called right after Ad Interstitial showed
        Admob.getInstance().setOpenActivityAfterShowInterAds(false);

        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
    }

    private void initBilling() {
        List<String> listINAPId = new ArrayList<>();
        listINAPId.add(MainActivity.PRODUCT_ID);
        List<String> listSubsId = new ArrayList<>();

        AppPurchase.getInstance().initBilling(getApplication(), listINAPId, listSubsId);
    }

}
