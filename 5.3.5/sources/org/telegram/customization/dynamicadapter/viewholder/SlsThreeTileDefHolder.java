package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseImages;
import org.telegram.customization.util.AppImageLoader;

@ViewHolderType(model = SlsBaseImages.class, type = 6)
public class SlsThreeTileDefHolder extends HolderBase {
    ImageView iv1 = ((ImageView) findViewById(R.id.iv_1));
    ImageView iv2 = ((ImageView) findViewById(R.id.iv_2));
    ImageView iv3 = ((ImageView) findViewById(R.id.iv_3));
    TextView tv1 = ((TextView) findViewById(R.id.ftv_1));
    TextView tv2 = ((TextView) findViewById(R.id.ftv_2));
    TextView tv3 = ((TextView) findViewById(R.id.ftv_3));

    public SlsThreeTileDefHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.home_tile_3_def, adapter, extraData);
    }

    public void onBind(ObjBase obj) {
        SlsBaseImages baseImages = (SlsBaseImages) obj;
        AppImageLoader.loadImage(this.iv1, (String) baseImages.getImages().get(0));
        AppImageLoader.loadImage(this.iv2, (String) baseImages.getImages().get(1));
        AppImageLoader.loadImage(this.iv3, (String) baseImages.getImages().get(2));
    }

    public void itemClicked(ObjBase obj) {
    }
}
