package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.PaymentReport;
import tellh.com.stickyheaderview_rv.p179a.C5306e;
import tellh.com.stickyheaderview_rv.p179a.C5308f;
import tellh.com.stickyheaderview_rv.p179a.C5308f.C5307a;

public class ItemReportViewBinder extends C5308f<PaymentReport, ViewHolder> {

    static class ViewHolder extends C5307a {
        CircleImageView ivStatus;
        TextView tvPrefix;

        public ViewHolder(View view) {
            super(view);
            this.tvPrefix = (TextView) view.findViewById(R.id.txt_payment_desc);
            this.ivStatus = (CircleImageView) view.findViewById(R.id.iv_status);
        }
    }

    public void bindView(C5306e c5306e, ViewHolder viewHolder, int i, PaymentReport paymentReport) {
        if (paymentReport.getStatus() == 92) {
            viewHolder.ivStatus.setBackgroundResource(R.drawable.greencircle);
            viewHolder.tvPrefix.setText("خرید موفق مبلغ : " + paymentReport.getAmount());
            return;
        }
        viewHolder.ivStatus.setBackgroundResource(R.drawable.redcircle);
        viewHolder.tvPrefix.setText("خرید ناموفق مبلغ : " + paymentReport.getAmount());
    }

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment;
    }

    public ViewHolder provideViewHolder(View view) {
        return new ViewHolder(view);
    }
}
