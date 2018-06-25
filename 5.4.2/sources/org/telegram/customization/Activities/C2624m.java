package org.telegram.customization.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import org.ir.talaeii.R;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;

/* renamed from: org.telegram.customization.Activities.m */
public class C2624m extends BaseFragment implements OnClickListener {
    /* renamed from: a */
    String f8786a = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: b */
    LinearLayout f8787b;

    /* renamed from: org.telegram.customization.Activities.m$1 */
    class C26231 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2624m f8785a;

        C26231(C2624m c2624m) {
            this.f8785a = c2624m;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8785a.finishFragment();
            }
        }
    }

    public C2624m(Bundle bundle) {
        this.f8786a = bundle.getString("EXTRA_TAG");
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.actionBarFontSize = 12;
        this.actionBar.setTitle(this.f8786a);
        this.actionBar.setActionBarMenuOnItemClick(new C26231(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_search_tag, null);
        this.f8787b = (LinearLayout) this.fragmentView.findViewById(R.id.ll_activity_container);
        this.f8787b.addView(new C2622l(getParentActivity(), 2, 2, this.actionBar, this.f8786a));
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[5];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        return themeDescriptionArr;
    }

    public void onClick(View view) {
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
    }
}
