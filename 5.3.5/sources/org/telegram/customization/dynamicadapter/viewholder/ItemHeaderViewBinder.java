package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.ReportHelper;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

public class ItemHeaderViewBinder extends ViewBinder<ReportHelper, ViewHolder> {

    static class ViewHolder extends tellh.com.stickyheaderview_rv.adapter.ViewBinder.ViewHolder {
        TextView tvPrefix;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvPrefix = (TextView) rootView.findViewById(R.id.txt_date);
        }
    }

    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, ReportHelper entity) {
        holder.tvPrefix.setText(entity.getKey());
    }

    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_payment_header;
    }
}
