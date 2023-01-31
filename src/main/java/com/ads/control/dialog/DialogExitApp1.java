package com.ads.control.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ads.control.Admod;
import com.ads.control.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class DialogExitApp1 extends Dialog {

    private Context context;
    private FrameLayout frameLayout;
    UnifiedNativeAdView adView;
    UnifiedNativeAd nativeAd;

    private TextView btnExit;
    private TextView btnCancel;

    int type;
    public DialogExitApp1(Context context, UnifiedNativeAd nativeAd,int type) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.nativeAd = nativeAd;
        this.type = type;
    }
    public DialogExitApp1(Context context, UnifiedNativeAd nativeAd,int type,UnifiedNativeAdView adView) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.nativeAd = nativeAd;
        this.type = type;
        this.adView =adView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (type==1){
            setContentView(R.layout.view_dialog_exit1);
        }else if (type==2){
            setContentView(R.layout.view_dialog_exit2);
        } else {
            setContentView(R.layout.view_dialog_exit3);
        }

        initView();
    }

    private void initView() {
        btnExit = findViewById(R.id.btnExit);
        btnCancel = findViewById(R.id.btnCancel);
        frameLayout = findViewById(R.id.frAds);
        if (adView==null) {
            if (type == 1) {
                adView = (UnifiedNativeAdView) LayoutInflater.from(context)
                        .inflate(R.layout.native_exit1, null);
            } else if (type == 2) {
                adView = (UnifiedNativeAdView) LayoutInflater.from(context)
                        .inflate(R.layout.native_exit1, null);
            } else {
                adView = (UnifiedNativeAdView) LayoutInflater.from(context)
                        .inflate(R.layout.native_exit3, null);
            }
        }

        frameLayout.addView(adView);
        Admod.getInstance().populateUnifiedNativeAdView(nativeAd, adView);

        btnExit.setOnClickListener(v -> ((Activity) context).finish());
        btnCancel.setOnClickListener(v -> dismiss());
    }
}
