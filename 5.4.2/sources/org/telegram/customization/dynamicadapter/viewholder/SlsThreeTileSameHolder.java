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
import org.telegram.customization.util.C2868b;

@ViewHolderType(model = SlsBaseImages.class, type = 5)
public class SlsThreeTileSameHolder extends HolderBase {
    ImageView iv1 = ((ImageView) findViewById(R.id.iv_1));
    ImageView iv2 = ((ImageView) findViewById(R.id.iv_2));
    ImageView iv3 = ((ImageView) findViewById(R.id.iv_3));
    TextView tv1 = ((TextView) findViewById(R.id.ftv_1));
    TextView tv2 = ((TextView) findViewById(R.id.ftv_2));
    TextView tv3 = ((TextView) findViewById(R.id.ftv_3));

    public SlsThreeTileSameHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.home_tile_3_same, dynamicAdapter, extraData);
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        SlsBaseImages slsBaseImages = (SlsBaseImages) objBase;
        C2868b.m13329a(this.iv1, (String) slsBaseImages.getImages().get(0));
        C2868b.m13329a(this.iv2, (String) slsBaseImages.getImages().get(1));
        C2868b.m13329a(this.iv3, (String) slsBaseImages.getImages().get(2));
    }
}
