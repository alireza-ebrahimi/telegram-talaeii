package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;

public abstract class HolderBase extends C0955v {
    private Activity activity;
    private DynamicAdapter adapter;
    protected ExtraData extraData;

    public HolderBase(Activity activity, ViewGroup viewGroup, int i, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false));
        setActivity(activity);
        setExtraData(extraData);
        setAdapter(dynamicAdapter);
    }

    public View findViewById(int i) {
        return this.itemView.findViewById(i);
    }

    public Activity getActivity() {
        return this.activity;
    }

    public DynamicAdapter getAdapter() {
        return this.adapter;
    }

    public ExtraData getExtraData() {
        return this.extraData;
    }

    public int getType() {
        return ((ViewHolderType) getClass().getAnnotation(ViewHolderType.class)).type();
    }

    public abstract void itemClicked(ObjBase objBase);

    public abstract void onBind(ObjBase objBase);

    public void onRecycled() {
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAdapter(DynamicAdapter dynamicAdapter) {
        this.adapter = dynamicAdapter;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }
}
