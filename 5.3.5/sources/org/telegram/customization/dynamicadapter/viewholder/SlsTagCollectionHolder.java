package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SlsHotPostActivity;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;
import org.telegram.customization.util.Constants;
import utils.view.collectionpicker.HomeCollectionPicker;
import utils.view.collectionpicker.Item;

@ViewHolderType(model = SlsTagCollection.class, type = 102)
public class SlsTagCollectionHolder extends HolderBase {
    public static final int collapseThreshold = 6;
    boolean collapse = true;
    HomeCollectionPicker collectionPicker = ((HomeCollectionPicker) findViewById(R.id.collection_item_picker));
    SlsHotPostActivity frg;
    View more = findViewById(R.id.draw_more);
    ArrayList<SlsTag> tags = new ArrayList();

    public boolean isCollapse() {
        return this.collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public SlsHotPostActivity getFrg() {
        return this.frg;
    }

    public void setFrg(SlsHotPostActivity frg) {
        this.frg = frg;
    }

    public SlsTagCollectionHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tag_collection_holder, adapter, extraData);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.collectionPicker.setmWidth(size.x);
    }

    public void onBind(ObjBase obj) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) obj;
        if (slsTagCollection != null) {
            this.tags = slsTagCollection.getTags();
            setupCollectionView();
            this.more.setOnClickListener(new SlsTagCollectionHolder$1(this));
            getAdapter().notifyItemChanged(obj.getPosition());
            getAdapter().notifyItemChanged(obj.getPosition());
        }
    }

    private void setupCollectionView() {
        List<Item> items = new ArrayList();
        this.collectionPicker.setmTextColor(R.color.black);
        Iterator it = this.tags.iterator();
        while (it.hasNext()) {
            SlsTag tag = (SlsTag) it.next();
            if (!TextUtils.isEmpty(tag.getShowName())) {
                items.add(new Item(String.valueOf(tag.getId()), tag.getShowName(), tag));
            }
        }
        this.collectionPicker.clearItems();
        this.collectionPicker.clearUi();
        if (isCollapse()) {
            this.collectionPicker.setItems(items.subList(0, 5));
            this.collectionPicker.drawItemView4(items.subList(0, 5));
        } else {
            this.collectionPicker.setItems(items.subList(0, items.size() - 1));
            this.collectionPicker.drawItemView4(items.subList(0, items.size() - 1));
        }
        this.collectionPicker.setResID(R.layout.collection_picker_item_layout_home);
        this.collectionPicker.setOnItemClickListener(new SlsTagCollectionHolder$2(this));
    }

    private void sendToFrg(long id) {
        Intent intent = new Intent(Constants.ACTION_SET_TAG_ID);
        intent.putExtra("EXTRA_TAG_ID", id);
        intent.putExtra(utils.view.Constants.EXTRA_POOL_ID, getExtraData().getPoolId());
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    public void itemClicked(ObjBase obj) {
    }
}
