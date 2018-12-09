package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.ReportHelper;
import tellh.com.stickyheaderview_rv.p179a.C5306e;
import tellh.com.stickyheaderview_rv.p179a.C5308f;
import tellh.com.stickyheaderview_rv.p179a.C5308f.C5307a;

public class ItemHeaderViewBinder extends C5308f<ReportHelper, ViewHolder> {

    static class ViewHolder extends C5307a {
        TextView tvPrefix;

        public ViewHolder(View view) {
            super(view);
            this.tvPrefix = (TextView) view.findViewById(R.id.txt_date);
        }
    }

    public void bindView(C5306e c5306e, ViewHolder viewHolder, int i, ReportHelper reportHelper) {
        viewHolder.tvPrefix.setText(reportHelper.getKey());
    }

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment_header;
    }

    public ViewHolder provideViewHolder(View view) {
        return new ViewHolder(view);
    }
}
