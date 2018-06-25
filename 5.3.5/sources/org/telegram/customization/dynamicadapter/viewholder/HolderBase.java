package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;

public abstract class HolderBase extends ViewHolder {
    private Activity activity;
    private DynamicAdapter adapter;
    protected ExtraData extraData;

    public abstract void itemClicked(ObjBase objBase);

    public abstract void onBind(ObjBase objBase);

    public ExtraData getExtraData() {
        return this.extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }

    public DynamicAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(DynamicAdapter adapter) {
        this.adapter = adapter;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public HolderBase(Activity activity, ViewGroup viewGroup, int layoutId, DynamicAdapter mAdapter, ExtraData extraData) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));
        setActivity(activity);
        setExtraData(extraData);
        setAdapter(mAdapter);
    }

    public int getType() {
        return ((ViewHolderType) getClass().getAnnotation(ViewHolderType.class)).type();
    }

    public View findViewById(int id) {
        return this.itemView.findViewById(id);
    }

    public void onRecycled() {
    }
}
