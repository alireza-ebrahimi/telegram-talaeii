package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;

@ViewHolderType(model = ObjBase.class, type = 106)
public class SlsTitleHolder extends HolderBase {
    TextView title = ((TextView) findViewById(R.id.ftv_title));

    public SlsTitleHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_title_holder, dynamicAdapter, extraData);
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        if (!TextUtils.isEmpty(objBase.getTitle())) {
            this.title.setText(objBase.getTitle());
        }
        if (!TextUtils.isEmpty(objBase.getColor())) {
            this.title.setBackgroundColor(Color.parseColor(objBase.getColor()));
        }
    }
}
