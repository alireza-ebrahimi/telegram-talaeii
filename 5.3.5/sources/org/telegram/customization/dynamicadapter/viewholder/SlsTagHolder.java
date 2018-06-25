package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.UserTagListActivity;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.customization.util.Constants;
import org.telegram.customization.util.IntentMaker;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;

@ViewHolderType(model = SlsTag.class, type = 101)
public class SlsTagHolder extends HolderBase implements IResponseReceiver {
    ImageView ivChannelIcon = ((ImageView) findViewById(R.id.iv_icon));
    ImageView ivLeft = ((ImageView) findViewById(R.id.iv_left));
    View llContainer = this.itemView.findViewById(R.id.lp_ll_root);
    TextView title = ((TextView) findViewById(R.id.title));

    public SlsTagHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tag_holder, adapter, extraData);
        this.llContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.title.setTextColor(Theme.getColor(Theme.key_chats_name));
    }

    public void onBind(ObjBase obj) {
        SlsTag myObj = (SlsTag) obj;
        this.title.setText(myObj.getShowName());
        this.llContainer.setOnClickListener(new SlsTagHolder$1(this, obj));
        if (myObj.isChannel()) {
            this.ivChannelIcon.setVisibility(0);
            if (!TextUtils.isEmpty(myObj.getImage())) {
                AppImageLoader.loadImage(this.ivChannelIcon, "drawable://2130837718");
                return;
            }
            return;
        }
        this.ivChannelIcon.setVisibility(8);
    }

    public void itemClicked(ObjBase obj) {
        SlsTag myObj = (SlsTag) obj;
        if (myObj.isChannel()) {
            IntentMaker.goToChannel(Math.abs(myObj.getId()), 0, getActivity(), myObj.getUsername());
            MessagesController.openByUserName(myObj.getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
            return;
        }
        sendBroadcast(myObj.getId(), myObj.getShowName());
    }

    private void sendBroadcast(long id, String showName) {
        if (showName.contentEquals("دنبال می کنم")) {
            getActivity().startActivity(new Intent(getActivity(), UserTagListActivity.class));
            return;
        }
        if (getActivity() instanceof UserTagListActivity) {
            getActivity().finish();
        }
        Intent intentSwitch = new Intent(Constants.ACTION_SWITCH_TAB);
        new Handler().postDelayed(new SlsTagHolder$2(this, id, showName), 100);
    }

    public void onResult(Object object, int StatusCode) {
    }
}
