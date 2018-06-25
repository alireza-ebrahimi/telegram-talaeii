package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.ui.ActionBar.Theme;

@ViewHolderType(model = ObjBase.class, type = 104)
public class SlsStatisticsHolder extends HolderBase {
    TextView title = ((TextView) findViewById(R.id.ftv_title));

    public SlsStatisticsHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_statistics_holder, adapter, extraData);
    }

    public void onBind(ObjBase obj) {
        this.title.setText(obj.getTitle());
        this.title.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
    }

    public void itemClicked(ObjBase obj) {
    }
}
