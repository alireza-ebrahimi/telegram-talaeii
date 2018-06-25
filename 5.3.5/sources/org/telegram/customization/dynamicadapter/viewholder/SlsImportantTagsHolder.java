package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;
import org.telegram.customization.util.Constants;

@ViewHolderType(model = SlsTagCollection.class, type = 103)
public class SlsImportantTagsHolder extends HolderBase {
    ImageView iv1 = ((ImageView) this.itemView.findViewById(R.id.iv_1));
    ImageView iv2 = ((ImageView) this.itemView.findViewById(R.id.iv_2));
    View topPosts = this.itemView.findViewById(R.id.ll_top_posts);
    View topVideos = this.itemView.findViewById(R.id.ll_top_videos);
    TextView tv1 = ((TextView) this.itemView.findViewById(R.id.ftv_1));
    TextView tv12 = ((TextView) this.itemView.findViewById(R.id.ftv_12));
    TextView tv21 = ((TextView) this.itemView.findViewById(R.id.ftv_21));
    TextView tv22 = ((TextView) this.itemView.findViewById(R.id.ftv_22));

    public SlsImportantTagsHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_header_item, adapter, extraData);
    }

    public void onBind(ObjBase obj) {
        SlsTagCollection tagCollection = (SlsTagCollection) obj;
        ArrayList<SlsTag> tags = tagCollection == null ? new ArrayList() : tagCollection.getTags();
        if (tags != null && tags.size() > 1) {
            this.tv1.setText(((SlsTag) tags.get(0)).getShowName());
            this.tv12.setText(((SlsTag) tags.get(0)).getDescription());
            if (tagCollection.getTags().size() > 1) {
                this.tv21.setText(((SlsTag) tags.get(1)).getShowName());
                this.tv22.setText(((SlsTag) tags.get(1)).getDescription());
            }
            this.topVideos.setOnClickListener(new SlsImportantTagsHolder$1(this, tags));
            this.topPosts.setOnClickListener(new SlsImportantTagsHolder$2(this, tags));
        }
    }

    private void sendBroadcastToHot(long id) {
        Intent intentSwitch = new Intent(Constants.ACTION_SWITCH_TAB);
        intentSwitch.putExtra(Constants.EXTRA_CURRENT_POSITION, 2);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentSwitch);
    }

    private void sendBroadcastToVideo(long id) {
        Intent intentSwitch = new Intent(Constants.ACTION_SWITCH_TAB);
        intentSwitch.putExtra(Constants.EXTRA_CURRENT_POSITION, 2);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentSwitch);
        new Handler().postDelayed(new SlsImportantTagsHolder$3(this), 300);
    }

    public void itemClicked(ObjBase obj) {
    }
}
