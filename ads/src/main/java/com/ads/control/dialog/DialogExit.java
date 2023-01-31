package com.ads.control.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class DialogExit {
    public static void showDialogExit(Context context, NativeAd nativeAd, int type){
        DialogExitApp1 dialogExitApp1 = new DialogExitApp1(context,nativeAd,type);
        dialogExitApp1.setCancelable(false);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h  = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogExitApp1.getWindow().setLayout(w,h);
        dialogExitApp1.show();
    }

    public static void showDialogExit(Context context, NativeAd nativeAd, int type, NativeAdView adView){
        DialogExitApp1 dialogExitApp1 = new DialogExitApp1(context,nativeAd,type,adView);
        dialogExitApp1.setCancelable(false);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h  = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogExitApp1.getWindow().setLayout(w,h);
        dialogExitApp1.show();
    }

}
