package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.content.C0424l;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2622l;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import utils.view.collectionpicker.HomeCollectionPicker;
import utils.view.collectionpicker.Item;
import utils.view.collectionpicker.OnItemClickListener;

@ViewHolderType(model = SlsTagCollection.class, type = 102)
public class SlsTagCollectionHolder extends HolderBase {
    public static final int collapseThreshold = 6;
    boolean collapse = true;
    HomeCollectionPicker collectionPicker = ((HomeCollectionPicker) findViewById(R.id.collection_item_picker));
    C2622l frg;
    View more = findViewById(R.id.draw_more);
    ArrayList<SlsTag> tags = new ArrayList();

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsTagCollectionHolder$1 */
    class C27141 implements OnClickListener {
        C27141() {
        }

        public void onClick(View view) {
            SlsTagCollectionHolder.this.setCollapse(!SlsTagCollectionHolder.this.isCollapse());
            SlsTagCollectionHolder.this.setupCollectionView();
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsTagCollectionHolder$2 */
    class C27152 implements OnItemClickListener {
        C27152() {
        }

        public void onClick(Item item, int i) {
            if (SlsTagCollectionHolder.this.tags != null && SlsTagCollectionHolder.this.tags.size() > i) {
                if (((SlsTag) SlsTagCollectionHolder.this.tags.get(i)).isChannel()) {
                    MessagesController.openByUserName(((SlsTag) SlsTagCollectionHolder.this.tags.get(i)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                } else {
                    SlsTagCollectionHolder.this.sendToFrg(((SlsTag) SlsTagCollectionHolder.this.tags.get(i)).getId());
                }
            }
        }
    }

    public SlsTagCollectionHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_tag_collection_holder, dynamicAdapter, extraData);
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.collectionPicker.setmWidth(point.x);
    }

    private void sendToFrg(long j) {
        Intent intent = new Intent("ACTION_SET_TAG_ID");
        intent.putExtra("EXTRA_TAG_ID", j);
        intent.putExtra("EXTRA_POOL_ID", getExtraData().getPoolId());
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    private void setupCollectionView() {
        List arrayList = new ArrayList();
        this.collectionPicker.setmTextColor(R.color.black);
        Iterator it = this.tags.iterator();
        while (it.hasNext()) {
            SlsTag slsTag = (SlsTag) it.next();
            if (!TextUtils.isEmpty(slsTag.getShowName())) {
                arrayList.add(new Item(String.valueOf(slsTag.getId()), slsTag.getShowName(), slsTag));
            }
        }
        this.collectionPicker.c();
        this.collectionPicker.b();
        if (isCollapse()) {
            this.collectionPicker.setItems(arrayList.subList(0, 5));
            this.collectionPicker.a(arrayList.subList(0, 5));
        } else {
            this.collectionPicker.setItems(arrayList.subList(0, arrayList.size() - 1));
            this.collectionPicker.a(arrayList.subList(0, arrayList.size() - 1));
        }
        this.collectionPicker.setResID(R.layout.collection_picker_item_layout_home);
        this.collectionPicker.setOnItemClickListener(new C27152());
    }

    public C2622l getFrg() {
        return this.frg;
    }

    public boolean isCollapse() {
        return this.collapse;
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) objBase;
        if (slsTagCollection != null) {
            this.tags = slsTagCollection.getTags();
            setupCollectionView();
            this.more.setOnClickListener(new C27141());
            getAdapter().notifyItemChanged(objBase.getPosition());
            getAdapter().notifyItemChanged(objBase.getPosition());
        }
    }

    public void setCollapse(boolean z) {
        this.collapse = z;
    }

    public void setFrg(C2622l c2622l) {
        this.frg = c2622l;
    }
}
