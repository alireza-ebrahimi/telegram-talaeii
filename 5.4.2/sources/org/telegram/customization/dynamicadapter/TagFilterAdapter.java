package org.telegram.customization.dynamicadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.C0235a;
import android.support.v4.content.C0424l;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0926a;
import android.support.v7.widget.RecyclerView.C0955v;
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
import org.telegram.ui.ActionBar.Theme;

public class TagFilterAdapter extends C0926a<MyViewHolder> {
    private Context context;
    private ArrayList<SlsFilter> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    /* renamed from: org.telegram.customization.dynamicadapter.TagFilterAdapter$1 */
    class C26801 implements OnClickListener {
        C26801() {
        }

        public void onClick(View view) {
            C0424l.m1899a(TagFilterAdapter.this.context).m1904a(new Intent("ACTION_SEARCH"));
        }
    }

    class MyViewHolder extends C0955v {
        ImageView ivClose;
        TextView name;

        public MyViewHolder(final View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.tag_name);
            this.ivClose = (ImageView) view.findViewById(R.id.deleteTag);
            this.name.setOnClickListener(new OnClickListener(TagFilterAdapter.this) {
                public void onClick(View view) {
                    try {
                        TagFilterAdapter.this.listener.onItemClick(null, view, TagFilterAdapter.this.recyclerView.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public TagFilterAdapter(Context context, ArrayList<SlsFilter> arrayList, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setData(arrayList);
        this.listener = onItemClickListener;
        this.recyclerView = recyclerView;
    }

    public void changeSelectedItem(int i) {
        for (int i2 = 0; i2 < getData().size(); i2++) {
            ((SlsFilter) getData().get(i2)).setSelected(false);
        }
        ((SlsFilter) getData().get(i)).setSelected(true);
        notifyDataSetChanged();
    }

    public ArrayList<SlsFilter> getData() {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        return this.data;
    }

    public int getItemCount() {
        return getData().size();
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        SlsFilter slsFilter = (SlsFilter) getData().get(i);
        myViewHolder.name.setText(slsFilter.getName());
        if (slsFilter.isSelected()) {
            myViewHolder.itemView.setBackgroundResource(R.drawable.tag_bg_selected);
            myViewHolder.name.setTextColor(C0235a.m1075c(this.context, R.color.white));
        } else {
            myViewHolder.itemView.setBackgroundResource(R.drawable.tag_bg);
            myViewHolder.name.setTextColor(Theme.getColor(Theme.key_chats_name));
        }
        if (slsFilter.isDeletable()) {
            myViewHolder.ivClose.setVisibility(0);
        } else {
            myViewHolder.ivClose.setVisibility(8);
        }
        myViewHolder.ivClose.setOnClickListener(new C26801());
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(this.inflater.inflate(R.layout.tag_view, viewGroup, false));
    }

    public void resetSelected() {
    }

    public void setData(ArrayList<SlsFilter> arrayList) {
        this.data = arrayList;
    }

    public void setSelected(long j) {
        for (int i = 0; i < getData().size(); i++) {
            ((SlsFilter) getData().get(i)).setSelected(false);
            if (((long) ((SlsFilter) getData().get(i)).getId()) == j) {
                ((SlsFilter) getData().get(i)).setSelected(true);
            } else if (j == -100) {
                ((SlsFilter) getData().get(i)).setSelected(true);
            }
        }
    }
}
