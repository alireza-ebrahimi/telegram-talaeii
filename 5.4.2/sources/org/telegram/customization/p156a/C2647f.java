package org.telegram.customization.p156a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.content.C0424l;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.util.view.slideshow.SlideshowView.C2646a;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import utils.view.collectionpicker.HomeCollectionPicker;
import utils.view.collectionpicker.Item;
import utils.view.collectionpicker.OnItemClickListener;

/* renamed from: org.telegram.customization.a.f */
public class C2647f extends C2646a {
    /* renamed from: a */
    List<SlsTag> f8838a;
    /* renamed from: b */
    List<List<SlsTag>> f8839b = new ArrayList();
    /* renamed from: c */
    private Activity f8840c;
    /* renamed from: d */
    private LayoutInflater f8841d = null;

    /* renamed from: org.telegram.customization.a.f$1 */
    class C26431 implements OnItemClickListener {
        /* renamed from: a */
        final /* synthetic */ C2647f f8835a;

        C26431(C2647f c2647f) {
            this.f8835a = c2647f;
        }

        public void onClick(Item item, int i) {
            if (this.f8835a.f8838a != null && this.f8835a.f8838a.size() > i) {
                if (((SlsTag) this.f8835a.f8838a.get(i)).isChannel()) {
                    MessagesController.openByUserName(((SlsTag) this.f8835a.f8838a.get(i)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                } else {
                    this.f8835a.m12489a(((SlsTag) this.f8835a.f8838a.get(i)).getId());
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.a.f$a */
    class C2644a {
        /* renamed from: a */
        HomeCollectionPicker f8836a;
        /* renamed from: b */
        final /* synthetic */ C2647f f8837b;

        C2644a(C2647f c2647f) {
            this.f8837b = c2647f;
        }
    }

    public C2647f(Activity activity, List<SlsTag> list) {
        this.f8840c = activity;
        this.f8841d = (LayoutInflater) this.f8840c.getSystemService("layout_inflater");
        this.f8838a = list;
        int i = 0;
        while (i < list.size()) {
            this.f8839b.add(list.subList(i, i + 6 > list.size() ? list.size() : i + 6));
            i += 6;
        }
    }

    /* renamed from: a */
    private void m12489a(long j) {
        Intent intent = new Intent("ACTION_SET_TAG_ID");
        intent.putExtra("EXTRA_TAG_ID", j);
        C0424l.m1899a(this.f8840c).m1904a(intent);
    }

    /* renamed from: a */
    private void m12491a(HomeCollectionPicker homeCollectionPicker, int i) {
        List arrayList = new ArrayList();
        homeCollectionPicker.setmTextColor(R.color.black);
        for (SlsTag slsTag : (List) this.f8839b.get(i)) {
            if (!TextUtils.isEmpty(slsTag.getShowName())) {
                arrayList.add(new Item(String.valueOf(slsTag.getId()), slsTag.getShowName(), slsTag));
            }
        }
        homeCollectionPicker.c();
        homeCollectionPicker.b();
        homeCollectionPicker.setItems(arrayList);
        homeCollectionPicker.a(arrayList);
        homeCollectionPicker.setResID(R.layout.collection_picker_item_layout_home);
        homeCollectionPicker.setOnItemClickListener(new C26431(this));
    }

    /* renamed from: a */
    public int mo3457a(int i) {
        return R.drawable.indicator_drawable_orange;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public int getCount() {
        return this.f8838a.size() % 6 == 0 ? this.f8838a.size() / 6 : (this.f8838a.size() / 6) + 1;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        C2644a c2644a;
        View view = null;
        if (view == null) {
            C2644a c2644a2 = new C2644a(this);
            View inflate = this.f8841d.inflate(R.layout.sls_tag_collection_holder, viewGroup, false);
            c2644a2.f8836a = (HomeCollectionPicker) inflate.findViewById(R.id.collection_item_picker);
            Display defaultDisplay = this.f8840c.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            c2644a2.f8836a.setmWidth(point.x);
            inflate.setTag(c2644a2);
            c2644a = c2644a2;
            view = inflate;
        } else {
            c2644a = (C2644a) view.getTag();
        }
        m12491a(c2644a.f8836a, i);
        viewGroup.addView(view);
        return view;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
