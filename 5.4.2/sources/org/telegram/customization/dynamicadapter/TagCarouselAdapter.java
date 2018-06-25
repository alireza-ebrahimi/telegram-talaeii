package org.telegram.customization.dynamicadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
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
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.util.C2868b;
import org.telegram.ui.ActionBar.Theme;

public class TagCarouselAdapter extends C0926a<MyViewHolder> {
    private Context context;
    private ArrayList<SlsTag> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    class MyViewHolder extends C0955v {
        ImageView ivMain;
        TextView name;
        View root;

        public MyViewHolder(final View view) {
            super(view);
            this.root = view.findViewById(R.id.root);
            this.name = (TextView) view.findViewById(R.id.tv_name);
            this.ivMain = (ImageView) view.findViewById(R.id.iv_user);
            this.root.setOnClickListener(new OnClickListener(TagCarouselAdapter.this) {
                public void onClick(View view) {
                    try {
                        TagCarouselAdapter.this.listener.onItemClick(null, view, TagCarouselAdapter.this.recyclerView.m273f(view), System.currentTimeMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public TagCarouselAdapter(Context context, ArrayList<SlsTag> arrayList, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setData(arrayList);
        this.listener = onItemClickListener;
        this.recyclerView = recyclerView;
    }

    private void setBackgroundShape(MyViewHolder myViewHolder) {
        Drawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(20, 20, 20, 20);
        shapeDrawable.setShape(new RoundRectShape(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}, null, null));
        shapeDrawable.getPaint().setColor(Theme.getColor(Theme.key_chat_inBubble));
        myViewHolder.root.setBackgroundDrawable(shapeDrawable);
    }

    public ArrayList<SlsTag> getData() {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        return this.data;
    }

    public int getItemCount() {
        return getData().size();
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        SlsTag slsTag = (SlsTag) getData().get(i);
        myViewHolder.name.setText(slsTag.getShowName());
        myViewHolder.name.setTextColor(Theme.getColor(Theme.key_chats_name));
        if (slsTag.isChannel()) {
            C2868b.m13329a(myViewHolder.ivMain, "drawable://2130837750");
        } else {
            C2868b.m13329a(myViewHolder.ivMain, "drawable://2130838216");
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(this.inflater.inflate(R.layout.tag_carousel_item, viewGroup, false));
        setBackgroundShape(myViewHolder);
        return myViewHolder;
    }

    public void setData(ArrayList<SlsTag> arrayList) {
        this.data = arrayList;
    }
}
