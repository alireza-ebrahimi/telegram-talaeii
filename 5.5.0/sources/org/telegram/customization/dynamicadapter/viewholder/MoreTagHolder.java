package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.C0424l;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.p151g.C2497d;

@ViewHolderType(model = More.class, type = 108)
public class MoreTagHolder extends HolderBase implements C2497d {
    Activity activity;
    View llShowAllChannel = findViewById(R.id.ll_container);
    View llSort = findViewById(R.id.rl_sort);
    TextView title = ((TextView) findViewById(R.id.tv_title));
    TextView tvSortTitle;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.MoreTagHolder$2 */
    class C26832 implements OnClickListener {
        C26832() {
        }

        public void onClick(View view) {
            MoreTagHolder.this.sortBy();
        }
    }

    public MoreTagHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_more_holder, dynamicAdapter, extraData);
        this.activity = activity;
        this.tvSortTitle = (TextView) findViewById(R.id.tv1);
    }

    private void sendBroadcast() {
        C0424l.m1899a(getActivity()).m1904a(new Intent("ACTION_SHOW_MORE"));
    }

    private void sendBroadcastForRemove() {
        C0424l.m1899a(getActivity()).m1904a(new Intent("ACTION_SHOW_LESS"));
    }

    public void itemClicked(ObjBase objBase) {
        More more = (More) objBase;
        sendBroadcast();
    }

    public void onBind(final ObjBase objBase) {
        More more = (More) objBase;
        if (more.getTagCount() != 9999) {
            this.title.setText("نمایش سایر کانال یافت شده");
        } else {
            this.title.setText("نمایش کمتر");
        }
        this.tvSortTitle.setText(more.getSortTitle());
        this.llShowAllChannel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (((More) objBase).getTagCount() == 9999) {
                    MoreTagHolder.this.sendBroadcastForRemove();
                } else {
                    MoreTagHolder.this.itemClicked(objBase);
                }
            }
        });
        if (more.isShowMoreButtonVisibility()) {
            this.llShowAllChannel.setVisibility(0);
        } else {
            this.llShowAllChannel.setVisibility(8);
        }
        this.llSort.setOnClickListener(new C26832());
    }

    public void onResult(Object obj, int i) {
    }

    public void sortBy() {
        C0424l.m1899a(getActivity()).m1904a(new Intent("ACTION_SHOW_SORT_DIALOG"));
    }
}
