package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import org.telegram.customization.util.Constants;
import org.telegram.customization.util.Prefs;

@ViewHolderType(model = MediaType.class, type = 109)
public class SlsMediaTypeHolder extends HolderBase implements OnItemClickListener {
    RecyclerView filterRecycleView;
    ArrayList<SlsFilter> filters1;

    public SlsMediaTypeHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_media_type_holder, adapter, extraData);
        this.filters1 = new ArrayList();
        this.filters1 = Prefs.getFilters(getActivity());
        this.filterRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, 0, false);
        layoutManager.setReverseLayout(true);
        this.filterRecycleView.setLayoutManager(layoutManager);
    }

    public void onBind(ObjBase obj) {
        MediaType mediaType = (MediaType) obj;
        if (this.filters1 != null && this.filters1.size() > 0) {
            TagFilterAdapter tagFilterAdapter = new TagFilterAdapter(getActivity(), this.filters1, this.filterRecycleView, this);
            if (mediaType.getSelectedMediaType() == -100) {
                tagFilterAdapter.setSelected(0);
            } else {
                tagFilterAdapter.setSelected(mediaType.getSelectedMediaType());
            }
            this.filterRecycleView.setAdapter(tagFilterAdapter);
        }
    }

    public void itemClicked(ObjBase obj) {
        More myObj = (More) obj;
    }

    private void sendBroadcast(long mediaType) {
        Intent intent = new Intent(Constants.ACTION_SET_MEDIA_TYPE);
        intent.putExtra(Constants.EXTRA_MEDIA_TYPE, mediaType);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            sendBroadcast((long) ((SlsFilter) this.filters1.get(i)).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
