package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsSearchImgClickable;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.ui.ActionBar.Theme;

@ViewHolderType(model = SlsSearchImgClickable.class, type = 112)
public class SlsSearchImgClickableHolder extends HolderBase implements IResponseReceiver {
    ImageView image = ((ImageView) findViewById(R.id.iv_image));
    View llContainer = this.itemView.findViewById(R.id.lp_ll_root);
    TextView title = ((TextView) findViewById(R.id.title));

    public SlsSearchImgClickableHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_img_clickable_holder, adapter, extraData);
        this.title.setTextColor(Theme.getColor(Theme.key_chats_name));
    }

    public void onBind(ObjBase obj) {
        SlsSearchImgClickable myObj = (SlsSearchImgClickable) obj;
        this.title.setText(myObj.getShowName());
        AppImageLoader.loadImage(this.image, myObj.getImage());
        this.llContainer.setOnClickListener(new SlsSearchImgClickableHolder$1(this, obj));
    }

    public void itemClicked(ObjBase obj) {
        SlsSearchImgClickable myObj = (SlsSearchImgClickable) obj;
        sendBroadcast(myObj.getSearchText(), myObj.getMediaType());
    }

    private void sendBroadcast(String searchStr, long mediaType) {
        new Handler().postDelayed(new SlsSearchImgClickableHolder$2(this, searchStr, mediaType), 100);
    }

    public void onResult(Object object, int StatusCode) {
    }
}
