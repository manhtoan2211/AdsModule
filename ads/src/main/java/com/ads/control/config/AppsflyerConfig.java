package com.ads.control.config;

/**
 * Created by lamlt on 05/12/2022.
 */
public class AppsflyerConfig {

    private boolean enableAppsflyer = false;

    /**
     * appsflyerToken enable Appsflyer and setup dev key
     */
    private String appsflyerToken = "";

    /**
     * eventNamePurchase push event to appsflyer when ad impression
     */
    private String eventAdImpression = "";

    public AppsflyerConfig(boolean enableAppsflyer) {
        this.enableAppsflyer = enableAppsflyer;
    }

    public AppsflyerConfig(boolean enableAppsflyer, String appsflyerToken) {
        this.enableAppsflyer = enableAppsflyer;
        this.appsflyerToken = appsflyerToken;
    }

    public boolean isEnableAppsflyer() {
        return enableAppsflyer;
    }

    public void setEnableAppsflyer(boolean enableAppsflyer) {
        this.enableAppsflyer = enableAppsflyer;
    }

    public String getAppsflyerToken() {
        return appsflyerToken;
    }

    public void setAppsflyerToken(String appsflyerToken) {
        this.appsflyerToken = appsflyerToken;
    }


    public String getEventAdImpression() {
        return eventAdImpression;
    }

    public void setEventAdImpression(String eventAdImpression) {
        this.eventAdImpression = eventAdImpression;
    }
    
}
