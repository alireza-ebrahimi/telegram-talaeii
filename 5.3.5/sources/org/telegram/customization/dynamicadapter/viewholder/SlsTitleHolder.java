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

    public SlsTitleHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_title_holder, adapter, extraData);
    }

    public void onBind(ObjBase obj) {
        ObjBase myObj = obj;
        if (!TextUtils.isEmpty(myObj.getTitle())) {
            this.title.setText(myObj.getTitle());
        }
        if (!TextUtils.isEmpty(myObj.getColor())) {
            this.title.setBackgroundColor(Color.parseColor(myObj.getColor()));
        }
    }

    public void itemClicked(ObjBase obj) {
    }
}
