package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.PaymentReport;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

public class ItemReportViewBinder extends ViewBinder<PaymentReport, ViewHolder> {

    static class ViewHolder extends tellh.com.stickyheaderview_rv.adapter.ViewBinder.ViewHolder {
        CircleImageView ivStatus;
        TextView tvPrefix;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvPrefix = (TextView) rootView.findViewById(R.id.txt_payment_desc);
            this.ivStatus = (CircleImageView) rootView.findViewById(R.id.iv_status);
        }
    }

    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, PaymentReport entity) {
        if (entity.getStatus() == 92) {
            holder.ivStatus.setBackgroundResource(R.drawable.greencircle);
            holder.tvPrefix.setText("خرید موفق مبلغ : " + entity.getAmount());
            return;
        }
        holder.ivStatus.setBackgroundResource(R.drawable.redcircle);
        holder.tvPrefix.setText("خرید ناموفق مبلغ : " + entity.getAmount());
    }

    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_payment;
    }
}
