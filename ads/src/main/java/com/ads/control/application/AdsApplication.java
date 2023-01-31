package com.ads.control.application;

import android.app.Application;

import com.ads.control.config.AperoAdConfig;
import com.ads.control.util.AppUtil;
import com.ads.control.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated As of release 5.5.0, replaced by {@link #AdsMultiDexApplication}
 */
@Deprecated
public abstract class AdsApplication extends Application {

    protected AperoAdConfig aperoAdConfig;
    protected List<String> listTestDevice ;
    @Override
    public void onCreate() {
        super.onCreate();
        listTestDevice = new ArrayList<String>();
        aperoAdConfig = new AperoAdConfig(this);
        if (SharePreferenceUtils.getInstallTime(this) == 0) {
            SharePreferenceUtils.setInstallTime(this);
        }
        AppUtil.currentTotalRevenue001Ad = SharePreferenceUtils.getCurrentTotalRevenue001Ad(this);
    }

}
