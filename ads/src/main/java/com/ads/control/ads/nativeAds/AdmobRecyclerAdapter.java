package com.ads.control.ads.nativeAds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;

public class AdmobRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public int TYPE_AD_VIEW = 0;
    private final int TYPE_CONTENT_VIEW = 1;
    private final AperoAdPlacerSettings settings;

    private RecyclerView.Adapter adapterOriginal;
    private Activity activity;
    private AperoAdPlacer adPlacer;
    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver();

    public AdmobRecyclerAdapter(AperoAdPlacerSettings settings, RecyclerView.Adapter adapterOriginal, Activity activity) {
        this.adapterOriginal = adapterOriginal;
        this.registerAdapterDataObserver(adapterDataObserver);
        this.activity = activity;
        this.settings = settings;
        adPlacer = new AperoAdPlacer(settings, adapterOriginal, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(settings.getLayoutAdPlaceHolder(), parent, false);
            return new AperoViewHolder(view);
        } else {
            return adapterOriginal.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (adPlacer.isAdPosition(position)) {
            holder.setIsRecyclable(false);
            adPlacer.renderAd(position,holder);
        } else {
            adapterOriginal.onBindViewHolder(holder, adPlacer.getOriginalPosition(position));
        }
    }

    public void loadAds(){
        adPlacer.loadAds();
    }

    @Override
    public int getItemViewType(int position) {
        if (adPlacer.isAdPosition(position)) {
            return TYPE_AD_VIEW;
        } else {
            return TYPE_CONTENT_VIEW;
        }
    }



    @Override
    public int getItemCount() {
        return adPlacer.getAdjustedCount();
    }

    private class AperoViewHolder extends RecyclerView.ViewHolder {
        public AperoViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public void destroy(){
        if (adapterOriginal!=null)
            adapterOriginal.unregisterAdapterDataObserver(adapterDataObserver);
    }

    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        private AdapterDataObserver() {
        }

        @SuppressLint({"NotifyDataSetChanged"})
        public void onChanged() {
            AdmobRecyclerAdapter.this.adPlacer.configData();
            Log.d("AdapterDataObserver", "onChanged: ");
        }

        public void onItemRangeChanged(int var1, int var2) {
            Log.d("AdapterDataObserver", "onItemRangeChanged: ");

        }

        public void onItemRangeInserted(int var1, int var2) {
            Log.d("AdapterDataObserver", "onItemRangeInserted: ");
        }

        public void onItemRangeRemoved(int var1, int var2) {
            Log.d("AdapterDataObserver", "onItemRangeRemoved: ");
        }

        @SuppressLint({"NotifyDataSetChanged"})
        public void onItemRangeMoved(int var1, int var2, int var3) {
            Log.d("AdapterDataObserver", "onItemRangeMoved: ");
            notifyDataSetChanged();
        }
    }
}
