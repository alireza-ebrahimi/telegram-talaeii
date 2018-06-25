package org.telegram.ui.Adapters;

import android.content.Context;
import android.view.ViewGroup;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.ui.Cells.LocationCell;
import org.telegram.ui.Components.RecyclerListView.Holder;

public class LocationActivitySearchAdapter extends BaseLocationAdapter {
    private Context mContext;

    public LocationActivitySearchAdapter(Context context) {
        this.mContext = context;
    }

    public int getItemCount() {
        return this.places.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(new LocationCell(this.mContext));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ((LocationCell) holder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(position), (String) this.iconUrls.get(position), position != this.places.size() + -1);
    }

    public TLRPC$TL_messageMediaVenue getItem(int i) {
        if (i < 0 || i >= this.places.size()) {
            return null;
        }
        return (TLRPC$TL_messageMediaVenue) this.places.get(i);
    }

    public boolean isEnabled(ViewHolder holder) {
        return true;
    }
}
