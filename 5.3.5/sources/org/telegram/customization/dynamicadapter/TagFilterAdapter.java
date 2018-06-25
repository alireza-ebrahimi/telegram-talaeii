package org.telegram.customization.dynamicadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.util.Constants;
import org.telegram.ui.ActionBar.Theme;

public class TagFilterAdapter extends Adapter<MyViewHolder> {
    private Context context;
    private ArrayList<SlsFilter> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    /* renamed from: org.telegram.customization.dynamicadapter.TagFilterAdapter$1 */
    class C11901 implements OnClickListener {
        C11901() {
        }

        public void onClick(View v) {
            LocalBroadcastManager.getInstance(TagFilterAdapter.this.context).sendBroadcast(new Intent(Constants.ACTION_SEARCH));
        }
    }

    class MyViewHolder extends ViewHolder {
        ImageView ivClose;
        TextView name;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tag_name);
            this.ivClose = (ImageView) itemView.findViewById(R.id.deleteTag);
            this.name.setOnClickListener(new OnClickListener(TagFilterAdapter.this) {
                public void onClick(View view) {
                    try {
                        TagFilterAdapter.this.listener.onItemClick(null, itemView, TagFilterAdapter.this.recyclerView.getChildPosition(itemView), System.currentTimeMillis());
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            });
        }
    }

    public TagFilterAdapter(Context context, ArrayList<SlsFilter> data, RecyclerView recyclerView, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setData(data);
        this.listener = listener;
        this.recyclerView = recyclerView;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(this.inflater.inflate(R.layout.tag_view, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        SlsFilter filter = (SlsFilter) getData().get(position);
        holder.name.setText(filter.getName());
        if (filter.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.tag_bg_selected);
            holder.name.setTextColor(ContextCompat.getColor(this.context, R.color.white));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.tag_bg);
            holder.name.setTextColor(Theme.getColor(Theme.key_chats_name));
        }
        if (filter.isDeletable()) {
            holder.ivClose.setVisibility(0);
        } else {
            holder.ivClose.setVisibility(8);
        }
        holder.ivClose.setOnClickListener(new C11901());
    }

    public int getItemCount() {
        return getData().size();
    }

    public void changeSelectedItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            ((SlsFilter) getData().get(i)).setSelected(false);
        }
        ((SlsFilter) getData().get(position)).setSelected(true);
        notifyDataSetChanged();
    }

    public ArrayList<SlsFilter> getData() {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        return this.data;
    }

    public void setData(ArrayList<SlsFilter> data) {
        this.data = data;
    }

    public void resetSelected() {
    }

    public void setSelected(long mediaType) {
        for (int i = 0; i < getData().size(); i++) {
            ((SlsFilter) getData().get(i)).setSelected(false);
            if (((long) ((SlsFilter) getData().get(i)).getId()) == mediaType) {
                ((SlsFilter) getData().get(i)).setSelected(true);
            } else if (mediaType == -100) {
                ((SlsFilter) getData().get(i)).setSelected(true);
            }
        }
    }
}
