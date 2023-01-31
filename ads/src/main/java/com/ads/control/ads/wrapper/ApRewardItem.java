package com.ads.control.ads.wrapper;

import com.google.android.gms.ads.rewarded.RewardItem;

public class ApRewardItem {

    private RewardItem admobRewardItem;

    public ApRewardItem(RewardItem admobRewardItem) {
        this.admobRewardItem = admobRewardItem;
    }

    public RewardItem getAdmobRewardItem() {
        return admobRewardItem;
    }

    public void setAdmobRewardItem(RewardItem admobRewardItem) {
        this.admobRewardItem = admobRewardItem;
    }
}