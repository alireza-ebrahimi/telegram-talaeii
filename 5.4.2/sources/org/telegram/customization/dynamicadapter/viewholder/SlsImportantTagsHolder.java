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
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.dynamicadapter.data.SlsTagCollection;

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

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsImportantTagsHolder$3 */
    class C26863 implements Runnable {
        C26863() {
        }

        public void run() {
            Intent intent = new Intent("ACTION_SET_MEDIA_TYPE");
            intent.putExtra("EXTRA_MEDIA_TYPE", 8);
            intent.putExtra("EXTRA_MEDIA_TYPE_IN_HOT", true);
            C0424l.m1899a(SlsImportantTagsHolder.this.getActivity()).m1904a(intent);
        }
    }

    public SlsImportantTagsHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_header_item, dynamicAdapter, extraData);
    }

    private void sendBroadcastToHot(long j) {
        Intent intent = new Intent("ACTION_SWITCH_TAB");
        intent.putExtra("EXTRA_CURRENT_POSITION", 2);
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    private void sendBroadcastToVideo(long j) {
        Intent intent = new Intent("ACTION_SWITCH_TAB");
        intent.putExtra("EXTRA_CURRENT_POSITION", 2);
        C0424l.m1899a(getActivity()).m1904a(intent);
        new Handler().postDelayed(new C26863(), 300);
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        SlsTagCollection slsTagCollection = (SlsTagCollection) objBase;
        final ArrayList arrayList = slsTagCollection == null ? new ArrayList() : slsTagCollection.getTags();
        if (arrayList != null && arrayList.size() > 1) {
            this.tv1.setText(((SlsTag) arrayList.get(0)).getShowName());
            this.tv12.setText(((SlsTag) arrayList.get(0)).getDescription());
            if (slsTagCollection.getTags().size() > 1) {
                this.tv21.setText(((SlsTag) arrayList.get(1)).getShowName());
                this.tv22.setText(((SlsTag) arrayList.get(1)).getDescription());
            }
            this.topVideos.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SlsImportantTagsHolder.this.sendBroadcastToVideo(((SlsTag) arrayList.get(0)).getId());
                }
            });
            this.topPosts.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SlsImportantTagsHolder.this.sendBroadcastToHot(((SlsTag) arrayList.get(1)).getId());
                }
            });
        }
    }
}
