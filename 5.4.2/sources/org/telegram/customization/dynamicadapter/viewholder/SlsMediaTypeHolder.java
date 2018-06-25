package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.C0424l;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0926a;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.TagFilterAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.MediaType;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.util.C2885i;

@ViewHolderType(model = MediaType.class, type = 109)
public class SlsMediaTypeHolder extends HolderBase implements OnItemClickListener {
    RecyclerView filterRecycleView;
    ArrayList<SlsFilter> filters1;

    public SlsMediaTypeHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_media_type_holder, dynamicAdapter, extraData);
        this.filters1 = new ArrayList();
        this.filters1 = C2885i.m13385f(getActivity());
        this.filterRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        C0910h linearLayoutManager = new LinearLayoutManager(activity, 0, false);
        linearLayoutManager.m4673b(true);
        this.filterRecycleView.setLayoutManager(linearLayoutManager);
    }

    private void sendBroadcast(long j) {
        Intent intent = new Intent("ACTION_SET_MEDIA_TYPE");
        intent.putExtra("EXTRA_MEDIA_TYPE", j);
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    public void itemClicked(ObjBase objBase) {
        More more = (More) objBase;
    }

    public void onBind(ObjBase objBase) {
        MediaType mediaType = (MediaType) objBase;
        if (this.filters1 != null && this.filters1.size() > 0) {
            C0926a tagFilterAdapter = new TagFilterAdapter(getActivity(), this.filters1, this.filterRecycleView, this);
            if (mediaType.getSelectedMediaType() == -100) {
                tagFilterAdapter.setSelected(0);
            } else {
                tagFilterAdapter.setSelected(mediaType.getSelectedMediaType());
            }
            this.filterRecycleView.setAdapter(tagFilterAdapter);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        try {
            sendBroadcast((long) ((SlsFilter) this.filters1.get(i)).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
