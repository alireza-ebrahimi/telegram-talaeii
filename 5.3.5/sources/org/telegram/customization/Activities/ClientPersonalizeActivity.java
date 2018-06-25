package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import org.ir.talaeii.R;
import org.telegram.customization.Fragments.ChooseTabStyleFragment;
import org.telegram.customization.Fragments.ChooseThemeFragment;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.util.Constants;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;

public class ClientPersonalizeActivity extends Activity implements IResponseReceiver {
    TextView btnNext;
    ChooseTabStyleFragment chooseTabStyleFrg;
    ChooseThemeFragment chooseThemeFrg;
    ViewPager mViewPager;
    String themeName = "";
    private ViewPagerIndicator viewPagerIndicator;

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$1 */
    class C10221 implements OnPageChangeListener {
        C10221() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (position == 0) {
                ClientPersonalizeActivity.this.btnNext.setText(LocaleController.getString("Next", R.string.Next));
            } else {
                ClientPersonalizeActivity.this.btnNext.setText(LocaleController.getString("Done", R.string.Done));
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$2 */
    class C10232 implements OnClickListener {
        C10232() {
        }

        public void onClick(View view) {
            if (ClientPersonalizeActivity.this.mViewPager.getCurrentItem() == 0) {
                ClientPersonalizeActivity.this.mViewPager.setCurrentItem(1);
                return;
            }
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).edit();
            editor.putBoolean("hideTabs", ClientPersonalizeActivity.this.chooseTabStyleFrg.isWithTab());
            editor.apply();
            ClientPersonalizeActivity.this.themeName = ClientPersonalizeActivity.this.chooseThemeFrg.getThemeName();
            int pos = 0;
            int i = 0;
            while (i < Theme.themes.size()) {
                try {
                    if (((ThemeInfo) Theme.themes.get(i)).name.contentEquals(ClientPersonalizeActivity.this.themeName)) {
                        pos = i;
                        break;
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Theme.applyTheme((ThemeInfo) Theme.themes.get(pos));
            LocalBroadcastManager.getInstance(ClientPersonalizeActivity.this.getApplicationContext()).sendBroadcast(new Intent(Constants.ACTION_REBUILD_ALL));
            ClientPersonalizeActivity.this.finish();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ClientPersonalizeActivity$3 */
    class C10243 implements OnClickListener {
        C10243() {
        }

        public void onClick(View view) {
            ClientPersonalizeActivity.this.finish();
        }
    }

    public class SamplePagerAdapter extends FragmentPagerAdapter {
        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return ClientPersonalizeActivity.this.chooseTabStyleFrg;
            }
            return ClientPersonalizeActivity.this.chooseThemeFrg;
        }

        public int getCount() {
            return 2;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_personolize);
        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.view_pager_indicator);
        this.btnNext = (TextView) findViewById(R.id.btn_next);
        this.chooseThemeFrg = new ChooseThemeFragment();
        this.chooseTabStyleFrg = new ChooseTabStyleFragment();
        this.mViewPager.setAdapter(new SamplePagerAdapter(getFragmentManager()));
        this.viewPagerIndicator.setupWithViewPager(this.mViewPager);
        this.mViewPager.setOnPageChangeListener(new C10221());
        this.btnNext.setOnClickListener(new C10232());
        findViewById(R.id.iv_close).setOnClickListener(new C10243());
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
        }
    }
}
