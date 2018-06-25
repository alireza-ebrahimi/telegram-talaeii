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

    public TLRPC$TL_messageMediaVenue getItem(int i) {
        return (i < 0 || i >= this.places.size()) ? null : (TLRPC$TL_messageMediaVenue) this.places.get(i);
    }

    public int getItemCount() {
        return this.places.size();
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        return true;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ((LocationCell) viewHolder.itemView).setLocation((TLRPC$TL_messageMediaVenue) this.places.get(i), (String) this.iconUrls.get(i), i != this.places.size() + -1);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(new LocationCell(this.mContext));
    }
}
