package com.ads.control.ads.wrapper;

import com.applovin.mediation.ads.MaxRewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

public class ApRewardAd extends ApAdBase {
    private RewardedAd admobReward;

    public ApRewardAd() {
    }

    public void setAdmobReward(RewardedAd admobReward) {
        this.admobReward = admobReward;
        status = StatusAd.AD_LOADED;
    }

    public RewardedAd getAdmobReward() {
        return admobReward;
    }

    /**
     * Clean reward when shown
     */
    public void clean() {
        admobReward = null;
    }

    @Override
    public boolean isReady() {
        return admobReward != null;
    }
}
