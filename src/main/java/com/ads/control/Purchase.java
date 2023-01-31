package com.ads.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ads.control.funtion.AdmodHelper;
import com.ads.control.funtion.PurchaseListioner;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Purchase {
    private static final String LICENSE_KEY = null;
    private static final String MERCHANT_ID = null;
    private static final String TAG = "PurchaseEG";
    private BillingProcessor bp;
    //    public static final String PRODUCT_ID = "android.test.purchased";
    @SuppressLint("StaticFieldLeak")
    private static Purchase instance;

    @SuppressLint("StaticFieldLeak")
    private String price = "1.49$";
    private String oldPrice = "2.99$";
    private String productId;
    private List<String> listSubcriptionId;
    private List<String> listProductId;
    private PurchaseListioner purchaseListioner;

    public void setPurchaseListioner(PurchaseListioner purchaseListioner) {
        this.purchaseListioner = purchaseListioner;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public static Purchase getInstance() {
        if (instance == null) {
            instance = new Purchase();
        }
        return instance;
    }


    private Purchase() {

    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void addSubcriptionId(String id) {
        listSubcriptionId.add(id);
    }
    public void addProductId(String id) {
        listProductId.add(id);
    }

    public void initBilling(final Context context) {
        listSubcriptionId = new ArrayList<>();
        listProductId = new ArrayList<>();
        bp = new BillingProcessor(context, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                Log.e(TAG, "ProductPurchased:" + productId);
                if (purchaseListioner != null)
                    purchaseListioner.onProductPurchased(productId);
            }


            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
            }

            @Override
            public void onBillingInitialized() {

            }

            @Override
            public void onPurchaseHistoryRestored() {
                Log.e(TAG, "PurchaseHistoryRestored");
            }
        });


        bp.initialize();
        bp.loadOwnedPurchasesFromGoogle();
    }

    public void initBilling(final Context context,String LICENSE_KEY) {
        listSubcriptionId = new ArrayList<>();
        listProductId = new ArrayList<>();
        bp = new BillingProcessor(context, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                Log.e(TAG, "ProductPurchased:" + productId);
                if (purchaseListioner != null)
                    purchaseListioner.onProductPurchased(productId);
            }


            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
            }

            @Override
            public void onBillingInitialized() {

            }

            @Override
            public void onPurchaseHistoryRestored() {
                Log.e(TAG, "PurchaseHistoryRestored");
            }
        });


        bp.initialize();
        bp.loadOwnedPurchasesFromGoogle();
    }

    public boolean isPurchased(Context context) {
        return isPurchased(context, productId);
    }

    public boolean isPurchased(Context context, String productId) {
        if (bp == null) {
            initBilling(context);
        }

        for (String id : listSubcriptionId) {
            if (bp.isSubscribed(id)){
                Log.d(TAG, "isSubcription:true " );
                return true;
            }
        }

        for (String id : listProductId) {
            if (bp.isPurchased(id)){
                Log.d(TAG, "isPurchased:true " );
                return true;
            }
        }
        if (productId == null)
            return false;

        boolean pp = bp.isPurchased(productId);
        Log.d(TAG, "isPurchased:" + pp);
        return pp;
    }

    public void purchase(Activity activity) {
        if (productId == null) {
            Log.e(TAG, "Purchase false:productId null");
            Toast.makeText(activity, "Product id must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        purchase(activity, productId);
    }


    public void purchase(Activity activity, String productId) {
        if (bp == null) {
            initBilling(activity);
        }
        bp.purchase(activity, productId);
    }

    public void subscribe(Activity activity, String productId) {
        if (bp == null) {
            initBilling(activity);
        }
        bp.subscribe(activity, productId);

    }

    public void consumePurchase() {
        if (productId == null) {
            Log.e(TAG, "Consume Purchase false:productId null ");
            return;
        }
        consumePurchase(productId);
    }

    public void consumePurchase(String productId) {
        try {
            bp.consumePurchase(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean  handleActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       return bp.handleActivityResult(requestCode, resultCode, data);
    }

    public String getPrice() {
        return getPrice(productId);
    }

    public String getPrice(String productId) {
        SkuDetails skuDetails = bp.getPurchaseListingDetails(productId);
        if (skuDetails == null)
            return "";
        return formatCurrency(skuDetails.priceValue, skuDetails.currency);
    }

    public String getOldPrice() {
        SkuDetails skuDetails = bp.getPurchaseListingDetails(productId);
        if (skuDetails == null)
            return "";
        return formatCurrency(skuDetails.priceValue / discount, skuDetails.currency);
    }

    private String formatCurrency(double price, String currency) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(currency));
        return format.format(price);
    }

    private double discount = 1;

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
