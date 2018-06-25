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
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;

public class ThemeListAdapter extends Adapter<ChangeLogHolder> {
    OnItemClickListener listener;
    private RecyclerView recyclerView;
    ArrayList<HotgramTheme> themes = new ArrayList();

    class ChangeLogHolder extends ViewHolder {
        ImageView avatar;
        ImageView ivCheck;
        View root;
        TextView tvName;

        public ChangeLogHolder(View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.avatar = (ImageView) itemView.findViewById(R.id.theme_image);
            this.ivCheck = (ImageView) itemView.findViewById(R.id.iv_check);
            this.tvName = (TextView) itemView.findViewById(R.id.theme_name);
            itemView.setOnClickListener(new OnClickListener(ThemeListAdapter.this) {
                public void onClick(View view) {
                    try {
                        ThemeListAdapter.this.listener.onItemClick(null, view, ThemeListAdapter.this.recyclerView.getChildPosition(view), System.currentTimeMillis());
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            });
        }
    }

    public interface OnQuickAccessClickListener {
        void onClick(User user, TLRPC$Chat tLRPC$Chat);
    }

    public ChangeLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChangeLogHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false));
    }

    public ThemeListAdapter(ArrayList<HotgramTheme> data, OnItemClickListener listener, RecyclerView recyclerView) {
        this.themes = data;
        this.listener = listener;
        this.recyclerView = recyclerView;
    }

    public ArrayList<HotgramTheme> getData() {
        return this.themes;
    }

    public void onBindViewHolder(ChangeLogHolder holder, int position) {
        HotgramTheme category = (HotgramTheme) getData().get(position);
        holder.tvName.setText(category.getName());
        AppImageLoader.loadImage(holder.avatar, category.getPreviewUrl());
        if (category.isSelected()) {
            holder.ivCheck.setImageResource(R.drawable.check_circle_green);
        } else {
            holder.ivCheck.setImageResource(R.drawable.check_circle_gray);
        }
    }

    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }

    public void setData(ArrayList<HotgramTheme> mThemes) {
        this.themes = mThemes;
        notifyDataSetChanged();
    }
}
