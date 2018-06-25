package org.telegram.customization.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.util.Constants;
import org.telegram.customization.util.view.slideshow.SlideshowView.SlideshowAdapter;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import utils.view.collectionpicker.HomeCollectionPicker;
import utils.view.collectionpicker.Item;
import utils.view.collectionpicker.OnItemClickListener;

public class ImagePagerActivityAdapter extends SlideshowAdapter {
    public static final int pageTagCount = 6;
    List<List<SlsTag>> arrayLists = new ArrayList();
    private Activity context;
    private LayoutInflater inflater = null;
    List<SlsTag> tags;

    /* renamed from: org.telegram.customization.Adapters.ImagePagerActivityAdapter$1 */
    class C11171 implements OnItemClickListener {
        C11171() {
        }

        public void onClick(Item item, int position) {
            if (ImagePagerActivityAdapter.this.tags != null && ImagePagerActivityAdapter.this.tags.size() > position) {
                if (((SlsTag) ImagePagerActivityAdapter.this.tags.get(position)).isChannel()) {
                    MessagesController.openByUserName(((SlsTag) ImagePagerActivityAdapter.this.tags.get(position)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                } else {
                    ImagePagerActivityAdapter.this.sendToFrg(((SlsTag) ImagePagerActivityAdapter.this.tags.get(position)).getId());
                }
            }
        }
    }

    class ViewHolder {
        HomeCollectionPicker collectionPicker;

        ViewHolder() {
        }
    }

    public List<SlsTag> getTags() {
        if (this.tags == null) {
            this.tags = new ArrayList();
        }
        return this.tags;
    }

    public void setTags(List<SlsTag> tags) {
        this.tags = tags;
    }

    public ImagePagerActivityAdapter(Activity c, List<SlsTag> tags) {
        this.context = c;
        this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.tags = tags;
        for (int i = 0; i < tags.size(); i += 6) {
            int end;
            if (i + 6 > tags.size()) {
                end = tags.size();
            } else {
                end = i + 6;
            }
            this.arrayLists.add(tags.subList(i, end));
        }
    }

    public int getIconResId(int index) {
        return R.drawable.indicator_drawable_orange;
    }

    public int getCount() {
        if (this.tags.size() % 6 == 0) {
            return this.tags.size() / 6;
        }
        return (this.tags.size() / 6) + 1;
    }

    public SlsTag getItem(int position) {
        return (SlsTag) getTags().get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder viewholder;
        View view = null;
        if (view == null) {
            viewholder = new ViewHolder();
            view = this.inflater.inflate(R.layout.sls_tag_collection_holder, container, false);
            viewholder.collectionPicker = (HomeCollectionPicker) view.findViewById(R.id.collection_item_picker);
            Display display = this.context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            viewholder.collectionPicker.setmWidth(size.x);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        setupCollectionView(viewholder.collectionPicker, position);
        container.addView(view);
        return view;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void setupCollectionView(HomeCollectionPicker collectionPicker, int pos) {
        List<Item> items = new ArrayList();
        collectionPicker.setmTextColor(R.color.black);
        for (SlsTag tag : (List) this.arrayLists.get(pos)) {
            if (!TextUtils.isEmpty(tag.getShowName())) {
                items.add(new Item(String.valueOf(tag.getId()), tag.getShowName(), tag));
            }
        }
        collectionPicker.clearItems();
        collectionPicker.clearUi();
        collectionPicker.setItems(items);
        collectionPicker.drawItemView4(items);
        collectionPicker.setResID(R.layout.collection_picker_item_layout_home);
        collectionPicker.setOnItemClickListener(new C11171());
    }

    private void sendToFrg(long id) {
        Intent intent = new Intent(Constants.ACTION_SET_TAG_ID);
        intent.putExtra("EXTRA_TAG_ID", id);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }
}
