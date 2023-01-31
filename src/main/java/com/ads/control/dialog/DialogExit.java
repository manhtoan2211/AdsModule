package com.ads.control.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class DialogExit {
    public static void showDialogExit(Context context, UnifiedNativeAd nativeAd,int type){
        DialogExitApp1 dialogExitApp1 = new DialogExitApp1(context,nativeAd,type);
        dialogExitApp1.setCancelable(false);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h  = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogExitApp1.getWindow().setLayout(w,h);
        dialogExitApp1.show();
    }
    public static void showDialogExit(Context context, UnifiedNativeAd nativeAd,int type,UnifiedNativeAdView adView){
        DialogExitApp1 dialogExitApp1 = new DialogExitApp1(context,nativeAd,type,adView);
        dialogExitApp1.setCancelable(false);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h  = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogExitApp1.getWindow().setLayout(w,h);
        dialogExitApp1.show();
    }

}
