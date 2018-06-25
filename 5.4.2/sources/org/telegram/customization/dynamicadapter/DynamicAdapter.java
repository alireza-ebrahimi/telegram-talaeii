package org.telegram.customization.dynamicadapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.C0926a;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.MediaType;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.viewholder.HolderBase;

public class DynamicAdapter extends C0926a<HolderBase> {
    Activity activity;
    ExtraData extraData;
    boolean haveProgress = true;
    private ArrayList<ObjBase> items = new ArrayList();

    public DynamicAdapter(Activity activity, ExtraData extraData) {
        this.activity = activity;
        this.extraData = extraData;
    }

    public void addItemsBySort(ArrayList<ObjBase> arrayList) {
        if (arrayList != null) {
            getItems().addAll(arrayList);
        }
    }

    public int getItemCount() {
        return (isHaveProgress() ? 1 : 0) + getItems().size();
    }

    public int getItemViewType(int i) {
        if (i == getItems().size()) {
        }
        return i == getItems().size() ? 107 : (i >= getItems().size() || getItems().get(i) == null) ? -1 : ((ObjBase) getItems().get(i)).getType();
    }

    public ArrayList<ObjBase> getItems() {
        if (this.items == null) {
            this.items = new ArrayList();
        }
        return this.items;
    }

    public boolean isHaveProgress() {
        return this.items.size() < 2 ? false : this.haveProgress;
    }

    public void onBindViewHolder(final HolderBase holderBase, final int i) {
        try {
            ObjBase objBase = (ObjBase) getItems().get(i);
            objBase.setPosition(i);
            holderBase.onBind(objBase);
        } catch (Exception e) {
        }
        holderBase.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                holderBase.itemClicked((ObjBase) DynamicAdapter.this.getItems().get(i));
            }
        });
    }

    public HolderBase onCreateViewHolder(ViewGroup viewGroup, int i) {
        return Helper.createViewHolder(this.activity, viewGroup, i, this, this.extraData);
    }

    public void onViewRecycled(HolderBase holderBase) {
        try {
            super.onViewRecycled(holderBase);
            holderBase.onRecycled();
        } catch (Exception e) {
        }
    }

    public void removeAllChannelItem() {
        Iterator it = new ArrayList(getItems()).iterator();
        int i = 0;
        while (it.hasNext()) {
            ObjBase objBase = (ObjBase) it.next();
            if (objBase.getType() == 101) {
                getItems().remove(objBase);
            }
            i++;
        }
    }

    public void removeAllMessageItem() {
        Iterator it = new ArrayList(getItems()).iterator();
        int i = 0;
        while (it.hasNext()) {
            ObjBase objBase = (ObjBase) it.next();
            if (objBase.getType() == 100) {
                getItems().remove(objBase);
            }
            i++;
        }
    }

    public void removeNoResultItem() {
        Iterator it = new ArrayList(getItems()).iterator();
        int i = 0;
        while (it.hasNext()) {
            ObjBase objBase = (ObjBase) it.next();
            if (objBase.getType() == 110) {
                getItems().remove(objBase);
                return;
            }
            i++;
        }
    }

    public void setHaveProgress(boolean z) {
        this.haveProgress = z;
    }

    public void setItems(ArrayList<ObjBase> arrayList) {
        this.items = arrayList;
    }

    public void updateMediaTypeItem(long j) {
        Iterator it = new ArrayList(getItems()).iterator();
        int i = 0;
        while (it.hasNext()) {
            ObjBase objBase = (ObjBase) it.next();
            if (objBase.getType() == 109) {
                MediaType mediaType = (MediaType) objBase;
                mediaType.setSelectedMediaType(j);
                getItems().set(i, mediaType);
                return;
            }
            i++;
        }
    }
}
