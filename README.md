
# AdsModule
This is SDK ads custom. It has built in some sdk for easy use like
- Admob
- Google Billing
- Firebase auto log tracking event, tROAS

# Import Module
~~~
	maven { url 'https://jitpack.io' }
	implementation 'com.github.manhtoan2211:AdsModule:1.0.0'
~~~	 
# Summary
* [Setup CustomAds](#setup_aperoad)
	* [Setup id ads](#set_up_ads)
	* [Config ads](#config_ads)
	* [Ads Formats](#ads_formats)

* [Billing App](#billing_app)
* [Ads rule](#ads_rule)

# <a id="setup_aperoad"></a>Setup CustomAds
## <a id="set_up_ads"></a>Setup enviroment with id ads for project

We recommend you to setup 2 environments for your project, and only use test id during development, ids from your admob only use when needed and for publishing to Google Store
* The name must be the same as the name of the marketing request
* Config variant test and release in gradle
* appDev: using id admob test while dev
* appProd: use ids from your admob,  build release (build file .aab)

~~~   
buildTypes {
	def ADS_INTER_SPLASH_ID = "AD_INTERSTITIAL_SPLASH"
        def ADS_BANNER_ID = "AD_BANNER"
        def ADS_REWARD_ID = "AD_REWARD"
        def ADS_REWARD_INTER_ID = "AD_REWARD_INTER"
        def ADS_RESUME_ID = "AD_APPOPEN_RESUME"
        def ADS_NATIVE_ID = "AD_NATIVE"
        def ADS_OPEN_ID = "ADS_OPEN_APP"

        debug {
            manifestPlaceholders = [ad_app_id:"ca-app-pub-3940256099942544~3347511713"]
            buildConfigField "String", ADS_INTER_SPLASH_ID, "\"ca-app-pub-3940256099942544/1033173712\""
            buildConfigField "String", ADS_BANNER_ID, "\"ca-app-pub-3940256099942544/6300978111\""
            buildConfigField "String", ADS_REWARD_ID, "\"ca-app-pub-3940256099942544/5224354917\""
            buildConfigField "String", ADS_REWARD_INTER_ID, "\"ca-app-pub-3940256099942544/5354046379\""
            buildConfigField "String", ADS_RESUME_ID, "\"ca-app-pub-3940256099942544/3419835294\""
            buildConfigField "String", ADS_NATIVE_ID, "\"ca-app-pub-3940256099942544/2247696110\""
            buildConfigField "String", ADS_OPEN_ID, "\"ca-app-pub-3940256099942544/3419835294\""
        }

        release {
	   //Your Id adding here
            manifestPlaceholders = [ad_app_id:"ca-app-pub-3940256099942544~3347511713"]
            buildConfigField "String", ADS_INTER_SPLASH_ID, "\"ca-app-pub-3940256099942544/1033173712\""
            buildConfigField "String", ADS_BANNER_ID, "\"ca-app-pub-3940256099942544/6300978111\""
            buildConfigField "String", ADS_REWARD_ID, "\"ca-app-pub-3940256099942544/5224354917\""
            buildConfigField "String", ADS_REWARD_INTER_ID, "\"ca-app-pub-3940256099942544/5354046379\""
            buildConfigField "String", ADS_RESUME_ID, "\"ca-app-pub-3940256099942544/3419835294\""
            buildConfigField "String", ADS_NATIVE_ID, "\"ca-app-pub-3940256099942544/2247696110\""
            buildConfigField "String", ADS_OPEN_ID, "\"ca-app-pub-3940256099942544/3419835294\""
        }
~~~
AndroidManiafest.xml
~~~
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="${ad_app_id}" />
~~~
## <a id="config_ads"></a>Config ads
Create class Application

Configure your mediation here.

*** Note:Cannot use id ad test for production enviroment 
~~~
class App : AdsMultiDexApplication(){
    @Override
    public void onCreate() {
        super.onCreate();
	...
        String environment = BuildConfig.DEBUG ? AperoAdConfig.ENVIRONMENT_DEBUG : AperoAdConfig.ENVIRONMENT_PRODUCTION;
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
}
~~~
AndroidManiafest.xml
~~~
<application
android:name=".App"
...
>
~~~

## <a id="ads_formats"></a>Ads formats
### Ad Splash Interstitial
SplashActivity
~~~ 
    AdsCallback adCallback = new AdsCallback() {
        @Override
        public void onNextAction() {
            super.onNextAction();
            Log.d(TAG, "onNextAction");
            startMain();
        }
    };
~~~
~~~
        CustomAds.getInstance().setInitCallback(new AdsInitCallback() {
            @Override
            public void initAdSuccess() {
                CustomAds.getInstance().loadSplashInterstitialAds(SplashActivity.this, idAdSplash, 30000, 5000, true, adCallback);
            }
        });
~~~
### Interstitial
Load ad interstital before show
~~~
  private fun loadInterCreate() {
	ApInterstitialAd mInterstitialAd = CustomAds.getInstance().getInterstitialAds(this, idInter);
  }
~~~
Show and auto release ad interstitial
~~~
         if (mInterstitialAd.isReady()) {
                CustomAds.getInstance().forceShowInterstitial(this, mInterstitialAd, new AperoAdCallback() {
			@Override
			public void onNextAction() {
			    super.onNextAction();
			    Log.d(TAG, "onNextAction");
			   startActivity(new Intent(MainActivity.this, MaxSimpleListActivity.class));
			}
                
                }, true);
            } else {
                loadAdInterstitial();
            }
~~~
### Ad Banner

#### Latest way:
~~~
    <com.ads.control.ads.bannerAds.BannerAdsView
        android:id="@+id/bannerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        app:layout_constraintBottom_toBottomOf="parent" />
~~~
call load ad banner
~~~
	bannerAdsView.loadBanner(this, idBanner);
~~~
#### The older way:
~~~
  <include
  android:id="@+id/include"
  layout="@layout/layout_banner_control"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_alignParentBottom="true"
  app:layout_constraintBottom_toBottomOf="parent" />
~~~
call load ad banner
~~~
  CustomAds.getInstance().loadBanner(this, idBanner);
~~~

### Ad Native
Load ad native before show
~~~
        CustomAds.getInstance().loadNativeAdResultCallback(this,ID_NATIVE_AD, com.ads.control.R.layout.custom_native_max_small,new AdsCallback(){
            @Override
            public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                super.onNativeAdLoaded(nativeAd);
               //save or show native 
            }
        });
~~~
Populate native ad to view
~~~
	CustomAds.getInstance().populateNativeAdView(MainApplovinActivity.this,nativeAd,flParentNative,shimmerFrameLayout);
~~~
auto load and show native contains loading

in layout XML
~~~
      <com.ads.control.ads.nativeAds.NativeAdsView
        android:id="@+id/aperoNativeAds"
        android:layout_width="match_parent"
        android:layout_height="@dimen/_150sdp"
        android:background="@drawable/bg_card_ads"
        app:layoutCustomNativeAd="@layout/custom_native_admod_medium_rate"
        app:layoutLoading="@layout/loading_native_medium"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
~~~
Call load native ad
~~~
 loadNativeAd.loadNativeAd(this, idNative);
~~~
Load Ad native for recyclerView
~~~~
	// ad native repeating interval
	AdsAdapter adsAdapter = CustomAds.getInstance().getNativeRepeatAdapter(this, idNative, layoutCustomNative, com.ads.control.R.layout.layout_native_medium,
                originalAdapter, listener, 4);
	
	// ad native fixed in position
    	AdsAdapter adsAdapter = CustomAds.getInstance().getNativeFixedPositionAdapter(this, idNative, layoutCustomNative, com.ads.control.R.layout.layout_native_medium,
                originalAdapter, listener, 4);
	
        recyclerView.setAdapter(adAdapter.getAdapter());
        adAdapter.loadAds();
~~~~
### Ad Reward
Get and show reward
~~~
  ApRewardAd rewardAd = CustomAds.getInstance().getRewardAd(this, idAdReward);

   if (rewardAd != null && rewardAd.isReady()) {
                CustomAds.getInstance().forceShowRewardAd(this, rewardAd, new AperoAdCallback());
            }
});
~~~
### Ad resume
App
~~~ 
  override fun onCreate() {
  	super.onCreate()
  	AppOpenManager.getInstance().enableAppResume()
	AdsConfig.setIdAdResume(AppOpenManager.AD_UNIT_ID_TEST);
	...
  }
	

~~~


# <a id="billing_app"></a>Billing app
## Init Billing
Application
~~~
    @Override
    public void onCreate() {
        super.onCreate();
        AppPurchase.getInstance().initBilling(this,listINAPId,listSubsId);
    }
~~~
## Check status billing init
~~~
 if (AppPurchase.getInstance().getInitBillingFinish()){
            loadAdsPlash();
        }else {
            AppPurchase.getInstance().setBillingListener(new BillingListener() {
                @Override
                public void onInitBillingListener(int code) {
                         loadAdsPlash();
                }
            },5000);
        }
~~~
## Check purchase status
    //check purchase with PRODUCT_ID
	 AppPurchase.getInstance().isPurchased(this,PRODUCT_ID);
	 //check purchase all
	 AppPurchase.getInstance().isPurchased(this);
##  Purchase
	 AppPurchase.getInstance().purchase(this,PRODUCT_ID);
	 AppPurchase.getInstance().subscribe(this,SUBS_ID);
## Purchase Listener
	         AppPurchase.getInstance().setPurchaseListioner(new PurchaseListioner() {
                 @Override
                 public void onProductPurchased(String productId,String transactionDetails) {

                 }

                 @Override
                 public void displayErrorMessage(String errorMsg) {

                 }
             });

## Get id purchased
	  AppPurchase.getInstance().getIdPurchased();
## Consume purchase
	  AppPurchase.getInstance().consumePurchase(PRODUCT_ID);
## Get price
	  AppPurchase.getInstance().getPrice(PRODUCT_ID)
	  AppPurchase.getInstance().getPriceSub(SUBS_ID)
### Show iap dialog
	InAppDialog dialog = new InAppDialog(this);
	dialog.setCallback(() -> {
	     AppPurchase.getInstance().purchase(this,PRODUCT_ID);
	    dialog.dismiss();
	});
	dialog.show();



# <a id="ads_rule"></a>Ads rule
## Always add device test to idTestList with all of your team's device
To ignore invalid ads traffic
https://support.google.com/adsense/answer/16737?hl=en
## Before show full-screen ad (interstitial, app open ad), alway show a short loading dialog
To ignore accident click from user. This feature is existed in library
## Never reload ad on onAdFailedToLoad
To ignore infinite loop
