package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.view.ViewGroup;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.ImagePagerActivityAdapter;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;
import org.telegram.customization.util.view.slideshow.SlideshowView;

@ViewHolderType(model = SlsTagCollection.class, type = 102)
public class SlsTagCollectionPagerHolder extends HolderBase {
    SlideshowView slideshowView = ((SlideshowView) findViewById(R.id.slide_show));
    ArrayList<SlsTag> tags = new ArrayList();

    public SlsTagCollectionPagerHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.slideshow_item, adapter, extraData);
    }

    public void onBind(ObjBase obj) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) obj;
        if (slsTagCollection != null) {
            this.tags = slsTagCollection.getTags();
            this.slideshowView.setAdapter(new ImagePagerActivityAdapter(getActivity(), this.tags));
        }
    }

    public void itemClicked(ObjBase obj) {
    }
}
