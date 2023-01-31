package com.ads.control.event;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAdRevenue;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.ads.control.billing.AppPurchase;
import com.ads.control.funtion.AdType;
import com.applovin.mediation.MaxAd;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.AppsFlyerAdNetworkEventType;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;
import com.appsflyer.adrevenue.adnetworks.generic.Scheme;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.android.gms.ads.AdValue;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lamlt on 13/09/2022.
 */
public class AperoAppsflyer {
    private static final String TAG = "AperoAppsflyer";
    private Context context;
    private static AperoAppsflyer aperoAppsflyer;
    public static boolean enableAppsflyer = false;

    public AperoAppsflyer() {
    }

    public static AperoAppsflyer getInstance(){
        if (aperoAppsflyer==null)
            aperoAppsflyer = new AperoAppsflyer();
        return aperoAppsflyer;
    }

    public void init(Application context, String devKey) {
        init(context, devKey, false);
    }

    public void init(Application context, String devKey, boolean enableDebugLog) {
        this.context = context;
        AppsFlyerLib.getInstance().init(devKey, null, context);
        AppsFlyerLib.getInstance().start(context);

        AppsFlyerAdRevenue.Builder afRevenueBuilder = new AppsFlyerAdRevenue.Builder(context);
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build());

        AppsFlyerLib.getInstance().setDebugLog(enableDebugLog);
    }

    public void onTrackEventAddToCard(String contentId){
        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
        AppsFlyerLib.getInstance().logEvent(context,
                AFInAppEventType.ADD_TO_CART, eventValues, new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "onTrackEventAddToCard contentId:" + contentId + " success ");
                    }

                    @Override
                    public void onError(int i, @NonNull String s) {
                        Log.i(TAG, "onTrackEventAddToCard contentId:" + contentId + " error: " + s);
                    }
                });
    }

    void onTrackRevenuePurchase(float price, String currency, String contentId, int typeIAP) {
        String type = "";
        if (typeIAP == AppPurchase.TYPE_IAP.PURCHASE)
            type = "inapp";
        else
            type = "subs";
        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.REVENUE, price);
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
        eventValues.put(AFInAppEventParameterName.CURRENCY, currency);
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, type);

        AppsFlyerLib.getInstance().logEvent(context,
                AFInAppEventType.PURCHASE, eventValues, new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "onTrackRevenuePurchase contentId:" + contentId + " success ");
                    }

                    @Override
                    public void onError(int i, @NonNull String s) {
                        Log.i(TAG, "onTrackRevenuePurchase contentId:" + contentId + " error: " + s);
                    }
                });
    }


    public void pushTrackEventAdmob(AdValue adValue, String idAd, AdType adType) {
        Log.i(TAG, "pushTrackEventAdmob  enableAppsflyer:"+enableAppsflyer+ " --- value: "+adValue.getValueMicros() / 1000000.0 + " -- adType: " +adType.toString());
        if (enableAppsflyer) {
            Map<String, String> customParams = new HashMap<>();
            customParams.put(Scheme.AD_UNIT, idAd);
            customParams.put(Scheme.AD_TYPE, adType.toString());

            AppsFlyerAdRevenue.logAdRevenue(
                    "Admob",
                    MediationNetwork.googleadmob,
                    Currency.getInstance(Locale.US),
                    adValue.getValueMicros() / 1000000.0,
                    customParams
            );
        }
    }

    public void pushTrackEventApplovin(MaxAd ad,   AdType adType) {
        Log.i(TAG, "pushTrackEventApplovin  enableAppsflyer:"+enableAppsflyer+ " --- value: "+ad.getRevenue()  + " -- adType: " +adType.toString());
        if (enableAppsflyer) {
            Map<String, String> customParams = new HashMap<>();
            customParams.put(Scheme.AD_UNIT, ad.getAdUnitId());
            customParams.put(Scheme.AD_TYPE, adType.toString());

            AppsFlyerAdRevenue.logAdRevenue(
                    "Max",
                    MediationNetwork.applovinmax,
                    Currency.getInstance(Locale.US),
                    ad.getRevenue() ,
                    customParams
            );
        }
    }

    public void updateServerUninstallToken(Application context, String token ) {
        AppsFlyerLib.getInstance().updateServerUninstallToken(context, token);
    }
    public void onTrackRevenue(Context context, String eventName, float revenue, String currency) {

    }

    public void onTrackRevenuePurchase(float revenue, String currency) {

    }
}
