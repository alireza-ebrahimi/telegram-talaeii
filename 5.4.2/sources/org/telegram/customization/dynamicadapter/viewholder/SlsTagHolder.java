package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.C0424l;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2625n;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.util.C2868b;
import org.telegram.customization.util.C2879f;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;

@ViewHolderType(model = SlsTag.class, type = 101)
public class SlsTagHolder extends HolderBase implements C2497d {
    ImageView ivChannelIcon = ((ImageView) findViewById(R.id.iv_icon));
    ImageView ivLeft = ((ImageView) findViewById(R.id.iv_left));
    View llContainer = this.itemView.findViewById(R.id.lp_ll_root);
    TextView title = ((TextView) findViewById(R.id.title));

    public SlsTagHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tag_holder, dynamicAdapter, extraData);
        this.llContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.title.setTextColor(Theme.getColor(Theme.key_chats_name));
    }

    private void sendBroadcast(final long j, final String str) {
        if (str.contentEquals("دنبال می کنم")) {
            getActivity().startActivity(new Intent(getActivity(), C2625n.class));
            return;
        }
        if (getActivity() instanceof C2625n) {
            getActivity().finish();
        }
        Intent intent = new Intent("ACTION_SWITCH_TAB");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent("ACTION_SET_TAG_ID");
                intent.putExtra("EXTRA_TAG_ID", j);
                intent.putExtra("EXTRA_TAG_NAME", str);
                intent.putExtra("EXTRA_POOL_ID", SlsTagHolder.this.getExtraData().getPoolId());
                C0424l.m1899a(SlsTagHolder.this.getActivity()).m1904a(intent);
            }
        }, 100);
    }

    public void itemClicked(ObjBase objBase) {
        SlsTag slsTag = (SlsTag) objBase;
        if (slsTag.isChannel()) {
            C2879f.m13356a(Math.abs(slsTag.getId()), 0, getActivity(), slsTag.getUsername());
            MessagesController.openByUserName(slsTag.getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
            return;
        }
        sendBroadcast(slsTag.getId(), slsTag.getShowName());
    }

    public void onBind(final ObjBase objBase) {
        SlsTag slsTag = (SlsTag) objBase;
        this.title.setText(slsTag.getShowName());
        this.llContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlsTagHolder.this.itemClicked(objBase);
            }
        });
        if (slsTag.isChannel()) {
            this.ivChannelIcon.setVisibility(0);
            if (!TextUtils.isEmpty(slsTag.getImage())) {
                C2868b.m13329a(this.ivChannelIcon, "drawable://2130837719");
                return;
            }
            return;
        }
        this.ivChannelIcon.setVisibility(8);
    }

    public void onResult(Object obj, int i) {
    }
}
