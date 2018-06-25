package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.C0424l;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsSearchImgClickable;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.util.C2868b;
import org.telegram.ui.ActionBar.Theme;

@ViewHolderType(model = SlsSearchImgClickable.class, type = 112)
public class SlsSearchImgClickableHolder extends HolderBase implements C2497d {
    ImageView image = ((ImageView) findViewById(R.id.iv_image));
    View llContainer = this.itemView.findViewById(R.id.lp_ll_root);
    TextView title = ((TextView) findViewById(R.id.title));

    public SlsSearchImgClickableHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_img_clickable_holder, dynamicAdapter, extraData);
        this.title.setTextColor(Theme.getColor(Theme.key_chats_name));
    }

    private void sendBroadcast(final String str, final long j) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent("ACTION_SEARCH");
                intent.putExtra("EXTRA_SCROLL_TO_TOP", true);
                intent.putExtra("EXTRA_SEARCH_STRING", str);
                intent.putExtra("EXTRA_MEDIA_TYPE", j);
                C0424l.m1899a(SlsSearchImgClickableHolder.this.getActivity()).m1904a(intent);
            }
        }, 100);
    }

    public void itemClicked(ObjBase objBase) {
        SlsSearchImgClickable slsSearchImgClickable = (SlsSearchImgClickable) objBase;
        sendBroadcast(slsSearchImgClickable.getSearchText(), slsSearchImgClickable.getMediaType());
    }

    public void onBind(final ObjBase objBase) {
        SlsSearchImgClickable slsSearchImgClickable = (SlsSearchImgClickable) objBase;
        this.title.setText(slsSearchImgClickable.getShowName());
        C2868b.m13329a(this.image, slsSearchImgClickable.getImage());
        this.llContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlsSearchImgClickableHolder.this.itemClicked(objBase);
            }
        });
    }

    public void onResult(Object obj, int i) {
    }
}
