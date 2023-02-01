package com.example.andmoduleads.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.control.admob.Admob;
import com.ads.control.admob.AppOpenManager;
import com.ads.control.ads.AdsCallback;
import com.ads.control.ads.CustomAds;
import com.ads.control.ads.bannerAds.BannerAdsView;
import com.ads.control.ads.nativeAds.NativeAdsView;
import com.ads.control.ads.wrapper.ApAdError;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.ads.wrapper.ApRewardAd;
import com.ads.control.billing.AppPurchase;
import com.ads.control.dialog.DialogExitApp;
import com.ads.control.dialog.InAppDialog;
import com.ads.control.funtion.AdCallback;
import com.ads.control.funtion.DialogExitListener;
import com.ads.control.funtion.PurchaseListener;
import com.example.andmoduleads.BuildConfig;
import com.example.andmoduleads.R;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.nativead.NativeAd;

public class MainActivity extends AppCompatActivity {
    public static final String PRODUCT_ID = "android.test.purchased";
    private static final String TAG = "MAIN_TEST";

    private NativeAd unifiedNativeAd;
    private ApInterstitialAd mInterstitialAd;
    private ApRewardAd rewardAd;

    private String idBanner = "";
    private String idNative = "";
    private String idInter = "";

    private NativeAdsView nativeAdsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nativeAdsView = findViewById(R.id.aperoNativeAds);

        configMediationProvider();
        CustomAds.getInstance().setCountClickToShowAds(3);

        AppOpenManager.getInstance().setEnableScreenContentCallback(true);
        AppOpenManager.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                Log.e("AppOpenManager", "onAdShowedFullScreenContent: ");

            }
        });
        /**
         * Sample integration native ads
         */
        nativeAdsView.loadNativeAd(this, idNative, new AdsCallback() {
            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });


        AppPurchase.getInstance().setPurchaseListener(new PurchaseListener() {
            @Override
            public void onProductPurchased(String productId, String transactionDetails) {
                Log.e("PurchaseListioner", "ProductPurchased:" + productId);
                Log.e("PurchaseListioner", "transactionDetails:" + transactionDetails);
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void displayErrorMessage(String errorMsg) {
                Log.e("PurchaseListioner", "displayErrorMessage:" + errorMsg);
            }

            @Override
            public void onUserCancelBilling() {

            }
        });

        BannerAdsView bannerAdsView = findViewById(R.id.bannerView);
        bannerAdsView.loadBanner(this, idBanner, new AdsCallback() {
            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        loadAdInterstitial();
        loadAdReward();
        findViewById(R.id.btShowAds).setOnClickListener(v -> {
            if (mInterstitialAd.isReady()) {
                CustomAds.getInstance().showInterstitialAdByTimes(this, mInterstitialAd, new AdsCallback() {
                    @Override
                    public void onNextAction() {
                        Log.i(TAG, "onNextAction: start content and finish main");
                        startActivity(new Intent(MainActivity.this, ContentActivity.class));
                    }

                    @Override
                    public void onAdFailedToShow(@Nullable ApAdError adError) {
                        super.onAdFailedToShow(adError);
                        Log.i(TAG, "onAdFailedToShow:" + adError.getMessage());
                        startActivity(new Intent(MainActivity.this, ContentActivity.class));
                    }

                    @Override
                    public void onInterstitialShow() {
                        super.onInterstitialShow();
                        Log.d(TAG, "onInterstitialShow");
                    }
                }, true);
            } else {
                Toast.makeText(this, "start loading ads", Toast.LENGTH_SHORT).show();
                loadAdInterstitial();
                startActivity(new Intent(MainActivity.this, ContentActivity.class));
            }
        });

        findViewById(R.id.btForceShowAds).setOnClickListener(v -> {
            if (mInterstitialAd.isReady()) {
                CustomAds.getInstance().forceShowInterstitial(this, mInterstitialAd, new AdsCallback() {
                    @Override
                    public void onNextAction() {
                        Log.i(TAG, "onAdClosed: start content and finish main");
                        loadAdInterstitial();
                        startActivity(new Intent(MainActivity.this, SimpleListActivity.class));
                    }

                    @Override
                    public void onAdFailedToShow(@Nullable ApAdError adError) {
                        super.onAdFailedToShow(adError);
                        Log.i(TAG, "onAdFailedToShow:" + adError.getMessage());
                        startActivity(new Intent(MainActivity.this, SimpleListActivity.class));
                    }

                    @Override
                    public void onInterstitialShow() {
                        super.onInterstitialShow();
                        Log.d(TAG, "onInterstitialShow");
                    }
                }, true);
            } else {
                Toast.makeText(this, "start loading ads", Toast.LENGTH_SHORT).show();
                loadAdInterstitial();
                startActivity(new Intent(MainActivity.this, SimpleListActivity.class));
            }

        });

        findViewById(R.id.btnShowReward).setOnClickListener(v -> {
            if (rewardAd != null && rewardAd.isReady()) {
                CustomAds.getInstance().forceShowRewardAd(this, rewardAd, new AdsCallback() {
                    @Override
                    public void onAdClosed() {

                    }

                    @Override
                    public void onAdFailedToShow(@Nullable ApAdError adError) {

                    }

                    @Override
                    public void onNextAction() {

                    }
                });
                return;
            }
            loadAdReward();
        });

        Button btnIAP = findViewById(R.id.btIap);
        if (AppPurchase.getInstance().isPurchased()) {
            btnIAP.setText("Consume Purchase");
        } else {
            btnIAP.setText("Purchase");
        }
        btnIAP.setOnClickListener(v -> {
            if (AppPurchase.getInstance().isPurchased()) {
                AppPurchase.getInstance().consumePurchase(AppPurchase.PRODUCT_ID_TEST);
            } else {
                InAppDialog dialog = new InAppDialog(this);
                dialog.setCallback(() -> {
                    AppPurchase.getInstance().purchase(this, PRODUCT_ID);
                    dialog.dismiss();
                });
                dialog.show();
            }
        });

    }

    private void configMediationProvider() {
        idBanner = BuildConfig.AD_BANNER;
        idNative = BuildConfig.AD_NATIVE;
        idInter = BuildConfig.AD_INTERSTITIAL_SPLASH;
    }

    private void loadAdInterstitial() {
        mInterstitialAd = CustomAds.getInstance().getInterstitialAds(this, idInter);
    }

    private void loadAdReward() {
        rewardAd = CustomAds.getInstance().getRewardAd(this,  BuildConfig.AD_REWARD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNativeExit();
    }

    private void loadNativeExit() {
        if (unifiedNativeAd != null)
            return;
        Admob.getInstance().loadNativeAd(this, BuildConfig.AD_NATIVE, new AdCallback() {
            @Override
            public void onUnifiedNativeAdLoaded(NativeAd unifiedNativeAd) {
                MainActivity.this.unifiedNativeAd = unifiedNativeAd;
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (unifiedNativeAd == null)
            return;

        DialogExitApp dialogExitApp = new DialogExitApp(this, unifiedNativeAd, 1);
        dialogExitApp.setDialogExitListener(new DialogExitListener() {
            @Override
            public void onExit(boolean exit) {
                MainActivity.super.onBackPressed();
            }
        });
        dialogExitApp.setCancelable(false);
        dialogExitApp.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "ProductPurchased:" + data.toString());
        if (AppPurchase.getInstance().isPurchased(this)) {
            findViewById(R.id.btIap).setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}