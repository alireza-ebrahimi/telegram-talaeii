package org.telegram.customization.dynamicadapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.MediaType;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.viewholder.HolderBase;

public class DynamicAdapter extends Adapter<HolderBase> {
    Activity activity;
    ExtraData extraData;
    boolean haveProgress = true;
    private ArrayList<ObjBase> items = new ArrayList();

    public DynamicAdapter(Activity activity, ExtraData extraData) {
        this.activity = activity;
        this.extraData = extraData;
    }

    public void setItems(ArrayList<ObjBase> items) {
        this.items = items;
    }

    public void addItemsBySort(@NonNull ArrayList<ObjBase> items) {
        if (items != null) {
            getItems().addAll(items);
        }
    }

    public HolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        return Helper.createViewHolder(this.activity, parent, viewType, this, this.extraData);
    }

    public void onViewRecycled(HolderBase holder) {
        try {
            super.onViewRecycled(holder);
            holder.onRecycled();
        } catch (Exception e) {
        }
    }

    public void onBindViewHolder(final HolderBase holder, final int position) {
        try {
            ObjBase baseModel = (ObjBase) getItems().get(position);
            baseModel.setPosition(position);
            holder.onBind(baseModel);
        } catch (Exception e) {
        }
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                holder.itemClicked((ObjBase) DynamicAdapter.this.getItems().get(position));
            }
        });
    }

    public int getItemViewType(int position) {
        if (position == getItems().size()) {
        }
        if (position == getItems().size()) {
            return 107;
        }
        if (position >= getItems().size() || getItems().get(position) == null) {
            return -1;
        }
        return ((ObjBase) getItems().get(position)).getType();
    }

    public int getItemCount() {
        return (isHaveProgress() ? 1 : 0) + getItems().size();
    }

    public ArrayList<ObjBase> getItems() {
        if (this.items == null) {
            this.items = new ArrayList();
        }
        return this.items;
    }

    public void removeAllChannelItem() {
        int counter = 0;
        Iterator it = new ArrayList(getItems()).iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 101) {
                getItems().remove(item);
            }
            counter++;
        }
    }

    public void removeNoResultItem() {
        int counter = 0;
        Iterator it = new ArrayList(getItems()).iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 110) {
                getItems().remove(item);
                return;
            }
            counter++;
        }
    }

    public void removeAllMessageItem() {
        int counter = 0;
        Iterator it = new ArrayList(getItems()).iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 100) {
                getItems().remove(item);
            }
            counter++;
        }
    }

    public void updateMediaTypeItem(long mediaTypeS) {
        int counter = 0;
        Iterator it = new ArrayList(getItems()).iterator();
        while (it.hasNext()) {
            ObjBase item = (ObjBase) it.next();
            if (item.getType() == 109) {
                MediaType mediaType = (MediaType) item;
                mediaType.setSelectedMediaType(mediaTypeS);
                getItems().set(counter, mediaType);
                return;
            }
            counter++;
        }
    }

    public boolean isHaveProgress() {
        if (this.items.size() < 2) {
            return false;
        }
        return this.haveProgress;
    }

    public void setHaveProgress(boolean haveProgress) {
        this.haveProgress = haveProgress;
    }
}
