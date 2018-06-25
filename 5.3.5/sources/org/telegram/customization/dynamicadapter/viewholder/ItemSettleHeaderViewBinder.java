package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.SettleReportHeader;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

public class ItemSettleHeaderViewBinder extends ViewBinder<SettleReportHeader, ViewHolder> {

    static class ViewHolder extends tellh.com.stickyheaderview_rv.adapter.ViewBinder.ViewHolder {
        TextView tvAmount;
        TextView tvCurrnecy;
        TextView tvPrefix;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvPrefix = (TextView) rootView.findViewById(R.id.txt_date);
            this.tvAmount = (TextView) rootView.findViewById(R.id.txt_amount);
            this.tvCurrnecy = (TextView) rootView.findViewById(R.id.v_currency);
        }
    }

    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, SettleReportHeader entity) {
        holder.tvPrefix.setText(entity.getSettleDate());
        holder.tvAmount.setVisibility(0);
        holder.tvAmount.setText(entity.getSettleAmount() + "");
        holder.tvCurrnecy.setVisibility(0);
    }

    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_payment_header;
    }
}
