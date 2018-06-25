package org.telegram.customization.Adapters;

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
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;

public class AdsChannelsAdapter extends Adapter<ChangeLogHolder> {
    ArrayList<Category> categories = new ArrayList();
    OnItemClickListener listener;
    private RecyclerView recyclerView;

    public interface OnQuickAccessClickListener {
        void onClick(User user, TLRPC$Chat tLRPC$Chat);
    }

    class ChangeLogHolder extends ViewHolder {
        ImageView avatar;
        ImageView ivSelected;
        View root;
        TextView tvDesc;
        TextView tvName;

        public ChangeLogHolder(View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.avatar = (ImageView) itemView.findViewById(R.id.iv_channel_item);
            this.ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_channel_name);
            this.tvDesc = (TextView) itemView.findViewById(R.id.tv_channel_desc);
            itemView.setOnClickListener(new OnClickListener(AdsChannelsAdapter.this) {
                public void onClick(View view) {
                    try {
                        AdsChannelsAdapter.this.listener.onItemClick(null, view, AdsChannelsAdapter.this.recyclerView.getChildPosition(view), System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    public ChangeLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChangeLogHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ads_channel, parent, false));
    }

    public AdsChannelsAdapter(ArrayList<Category> data, OnItemClickListener listener, RecyclerView recyclerView) {
        this.categories = data;
        this.listener = listener;
        this.recyclerView = recyclerView;
    }

    public ArrayList<Category> getData() {
        return this.categories;
    }

    public void onBindViewHolder(ChangeLogHolder holder, int position) {
        Category category = (Category) getData().get(position);
        holder.tvName.setText(category.getTitle());
        holder.tvDesc.setText(category.getDesc());
        AppImageLoader.loadImage(holder.avatar, category.getImage());
        if (category.getStatus() == 1) {
            holder.ivSelected.setImageResource(R.drawable.check_circle_green);
        } else {
            holder.ivSelected.setImageResource(R.drawable.check_circle_gray);
        }
    }

    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }
}
