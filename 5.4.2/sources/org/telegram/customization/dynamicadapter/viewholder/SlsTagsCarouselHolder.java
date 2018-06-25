package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.C0424l;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
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
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;

@ViewHolderType(model = SlsTagCollection.class, type = 102)
public class SlsTagsCarouselHolder extends HolderBase implements OnItemClickListener {
    TagCarouselAdapter adapter;
    RecyclerView recycleView = ((RecyclerView) findViewById(R.id.recyclerView));
    ArrayList<SlsTag> tags = new ArrayList();

    public SlsTagsCarouselHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tags_carousel_item, dynamicAdapter, extraData);
        C0910h linearLayoutManager = new LinearLayoutManager(activity, 0, false);
        linearLayoutManager.m4673b(true);
        this.recycleView.setLayoutManager(linearLayoutManager);
    }

    private void sendToFrg(long j) {
        Intent intent = new Intent("ACTION_SET_TAG_ID");
        intent.putExtra("EXTRA_TAG_ID", j);
        intent.putExtra("EXTRA_POOL_ID", getExtraData().getPoolId());
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) objBase;
        if (slsTagCollection != null) {
            this.tags = slsTagCollection.getTags();
            this.adapter = new TagCarouselAdapter(getActivity(), this.tags, this.recycleView, this);
            this.recycleView.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
            this.recycleView.setAdapter(this.adapter);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.tags != null && this.tags.size() > i) {
            if (((SlsTag) this.tags.get(i)).isChannel()) {
                MessagesController.openByUserName(((SlsTag) this.tags.get(i)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
            } else {
                sendToFrg(((SlsTag) this.tags.get(i)).getId());
            }
        }
    }
}
