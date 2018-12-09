package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.SettleReportHeader;
import tellh.com.stickyheaderview_rv.p179a.C5306e;
import tellh.com.stickyheaderview_rv.p179a.C5308f;
import tellh.com.stickyheaderview_rv.p179a.C5308f.C5307a;

public class ItemSettleHeaderViewBinder extends C5308f<SettleReportHeader, ViewHolder> {

    static class ViewHolder extends C5307a {
        TextView tvAmount;
        TextView tvCurrnecy;
        TextView tvPrefix;

        public ViewHolder(View view) {
            super(view);
            this.tvPrefix = (TextView) view.findViewById(R.id.txt_date);
            this.tvAmount = (TextView) view.findViewById(R.id.txt_amount);
            this.tvCurrnecy = (TextView) view.findViewById(R.id.v_currency);
        }
    }

    public void bindView(C5306e c5306e, ViewHolder viewHolder, int i, SettleReportHeader settleReportHeader) {
        viewHolder.tvPrefix.setText(settleReportHeader.getSettleDate());
        viewHolder.tvAmount.setVisibility(0);
        viewHolder.tvAmount.setText(settleReportHeader.getSettleAmount() + TtmlNode.ANONYMOUS_REGION_ID);
        viewHolder.tvCurrnecy.setVisibility(0);
    }

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment_header;
    }

    public ViewHolder provideViewHolder(View view) {
        return new ViewHolder(view);
    }
}
