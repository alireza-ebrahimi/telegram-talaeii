package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.util.Constants;

@ViewHolderType(model = More.class, type = 108)
public class MoreTagHolder extends HolderBase implements IResponseReceiver {
    Activity activity;
    View llShowAllChannel = findViewById(R.id.ll_container);
    View llSort = findViewById(R.id.rl_sort);
    TextView title = ((TextView) findViewById(R.id.tv_title));
    TextView tvSortTitle;

    public MoreTagHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_more_holder, adapter, extraData);
        this.activity = activity;
        this.tvSortTitle = (TextView) findViewById(R.id.tv1);
    }

    public void onBind(ObjBase obj) {
        More myObj = (More) obj;
        if (myObj.getTagCount() != 9999) {
            this.title.setText("نمایش سایر کانال یافت شده");
        } else {
            this.title.setText("نمایش کمتر");
        }
        this.tvSortTitle.setText(myObj.getSortTitle());
        this.llShowAllChannel.setOnClickListener(new MoreTagHolder$1(this, obj));
        if (myObj.isShowMoreButtonVisibility()) {
            this.llShowAllChannel.setVisibility(0);
        } else {
            this.llShowAllChannel.setVisibility(8);
        }
        this.llSort.setOnClickListener(new MoreTagHolder$2(this));
    }

    private void sendBroadcastForRemove() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Constants.ACTION_SHOW_LESS));
    }

    public void itemClicked(ObjBase obj) {
        More myObj = (More) obj;
        sendBroadcast();
    }

    private void sendBroadcast() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Constants.ACTION_SHOW_MORE));
    }

    public void sortBy() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Constants.ACTION_SHOW_SORT_DIALOG));
    }

    public void onResult(Object object, int StatusCode) {
    }
}
