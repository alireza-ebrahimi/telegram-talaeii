package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.TagCarouselAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;
import org.telegram.customization.util.Constants;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;

@ViewHolderType(model = SlsTagCollection.class, type = 102)
public class SlsTagsCarouselHolder extends HolderBase implements OnItemClickListener {
    TagCarouselAdapter adapter;
    RecyclerView recycleView = ((RecyclerView) findViewById(R.id.recyclerView));
    ArrayList<SlsTag> tags = new ArrayList();

    public SlsTagsCarouselHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tags_carousel_item, adapter, extraData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, 0, false);
        layoutManager.setReverseLayout(true);
        this.recycleView.setLayoutManager(layoutManager);
    }

    public void onBind(ObjBase obj) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) obj;
        if (slsTagCollection != null) {
            this.tags = slsTagCollection.getTags();
            this.adapter = new TagCarouselAdapter(getActivity(), this.tags, this.recycleView, this);
            this.recycleView.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
            this.recycleView.setAdapter(this.adapter);
        }
    }

    public void itemClicked(ObjBase obj) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.tags != null && this.tags.size() > position) {
            if (((SlsTag) this.tags.get(position)).isChannel()) {
                MessagesController.openByUserName(((SlsTag) this.tags.get(position)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
            } else {
                sendToFrg(((SlsTag) this.tags.get(position)).getId());
            }
        }
    }

    private void sendToFrg(long id) {
        Intent intent = new Intent(Constants.ACTION_SET_TAG_ID);
        intent.putExtra("EXTRA_TAG_ID", id);
        intent.putExtra(utils.view.Constants.EXTRA_POOL_ID, getExtraData().getPoolId());
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
