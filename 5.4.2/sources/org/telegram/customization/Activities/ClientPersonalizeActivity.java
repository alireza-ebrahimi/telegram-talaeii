package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.p010c.p011a.C0039e;
import android.support.v4.content.C0424l;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p162e.C2718a;
import org.telegram.customization.p162e.C2720b;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;

public class ClientPersonalizeActivity extends Activity implements C2497d {
    /* renamed from: a */
    ViewPager f8347a;
    /* renamed from: b */
    TextView f8348b;
    /* renamed from: c */
    String f8349c = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: d */
    C2720b f8350d;
    /* renamed from: e */
    C2718a f8351e;
    /* renamed from: f */
    private ViewPagerIndicator f8352f;

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$1 */
    class C24931 implements C0188f {
        /* renamed from: a */
        final /* synthetic */ ClientPersonalizeActivity f8343a;

        C24931(ClientPersonalizeActivity clientPersonalizeActivity) {
            this.f8343a = clientPersonalizeActivity;
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            if (i == 0) {
                this.f8343a.f8348b.setText(LocaleController.getString("Next", R.string.Next));
            } else {
                this.f8343a.f8348b.setText(LocaleController.getString("Done", R.string.Done));
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$2 */
    class C24942 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ ClientPersonalizeActivity f8344a;

        C24942(ClientPersonalizeActivity clientPersonalizeActivity) {
            this.f8344a = clientPersonalizeActivity;
        }

        public void onClick(View view) {
            if (this.f8344a.f8347a.getCurrentItem() == 0) {
                this.f8344a.f8347a.setCurrentItem(1);
                return;
            }
            int i;
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            edit.putBoolean("hideTabs", this.f8344a.f8351e.m12593a());
            edit.apply();
            this.f8344a.f8349c = this.f8344a.f8350d.m12594a();
            int i2 = 0;
            while (i2 < Theme.themes.size()) {
                try {
                    if (((ThemeInfo) Theme.themes.get(i2)).name.contentEquals(this.f8344a.f8349c)) {
                        i = i2;
                        break;
                    }
                    i2++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i = 0;
            Theme.applyTheme((ThemeInfo) Theme.themes.get(i));
            C0424l.m1899a(this.f8344a.getApplicationContext()).m1904a(new Intent("ACTION_REBUILD_ALL"));
            this.f8344a.finish();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$3 */
    class C24953 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ ClientPersonalizeActivity f8345a;

        C24953(ClientPersonalizeActivity clientPersonalizeActivity) {
            this.f8345a = clientPersonalizeActivity;
        }

        public void onClick(View view) {
            this.f8345a.finish();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$a */
    public class C2496a extends C0039e {
        /* renamed from: a */
        final /* synthetic */ ClientPersonalizeActivity f8346a;

        public C2496a(ClientPersonalizeActivity clientPersonalizeActivity, FragmentManager fragmentManager) {
            this.f8346a = clientPersonalizeActivity;
            super(fragmentManager);
        }

        /* renamed from: a */
        public Fragment mo3413a(int i) {
            return i == 0 ? this.f8346a.f8351e : this.f8346a.f8350d;
        }

        public int getCount() {
            return 2;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_client_personolize);
        this.f8347a = (ViewPager) findViewById(R.id.pager);
        this.f8352f = (ViewPagerIndicator) findViewById(R.id.view_pager_indicator);
        this.f8348b = (TextView) findViewById(R.id.btn_next);
        this.f8350d = new C2720b();
        this.f8351e = new C2718a();
        this.f8347a.setAdapter(new C2496a(this, getFragmentManager()));
        this.f8352f.setupWithViewPager(this.f8347a);
        this.f8347a.setOnPageChangeListener(new C24931(this));
        this.f8348b.setOnClickListener(new C24942(this));
        findViewById(R.id.iv_close).setOnClickListener(new C24953(this));
    }

    public void onResult(Object obj, int i) {
        switch (i) {
        }
    }
}
