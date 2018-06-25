package org.telegram.customization.dynamicadapter;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
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
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.ui.ActionBar.Theme;

public class TagCarouselAdapter extends Adapter<MyViewHolder> {
    private Context context;
    private ArrayList<SlsTag> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    class MyViewHolder extends ViewHolder {
        ImageView ivMain;
        TextView name;
        View root;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
            this.ivMain = (ImageView) itemView.findViewById(R.id.iv_user);
            this.root.setOnClickListener(new OnClickListener(TagCarouselAdapter.this) {
                public void onClick(View view) {
                    try {
                        TagCarouselAdapter.this.listener.onItemClick(null, itemView, TagCarouselAdapter.this.recyclerView.getChildPosition(itemView), System.currentTimeMillis());
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            });
        }
    }

    public TagCarouselAdapter(Context context, ArrayList<SlsTag> data, RecyclerView recyclerView, OnItemClickListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setData(data);
        this.listener = listener;
        this.recyclerView = recyclerView;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(this.inflater.inflate(R.layout.tag_carousel_item, parent, false));
        setBackgroundShape(holder);
        return holder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        SlsTag tag = (SlsTag) getData().get(position);
        holder.name.setText(tag.getShowName());
        holder.name.setTextColor(Theme.getColor(Theme.key_chats_name));
        if (tag.isChannel()) {
            AppImageLoader.loadImage(holder.ivMain, "drawable://2130837749");
        } else {
            AppImageLoader.loadImage(holder.ivMain, "drawable://2130838215");
        }
    }

    public int getItemCount() {
        return getData().size();
    }

    public ArrayList<SlsTag> getData() {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        return this.data;
    }

    public void setData(ArrayList<SlsTag> data) {
        this.data = data;
    }

    private void setBackgroundShape(MyViewHolder holder) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(20, 20, 20, 20);
        shapeDrawable.setShape(new RoundRectShape(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}, null, null));
        shapeDrawable.getPaint().setColor(Theme.getColor(Theme.key_chat_inBubble));
        holder.root.setBackgroundDrawable(shapeDrawable);
    }
}
