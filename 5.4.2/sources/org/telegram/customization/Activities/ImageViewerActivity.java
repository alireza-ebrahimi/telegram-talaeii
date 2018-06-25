package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager.C0188f;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.p098a.C1768f;
import com.google.p098a.p103c.C1748a;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.p156a.C2642e;
import org.telegram.customization.p156a.C2665l;
import org.telegram.customization.util.C2888l;
import org.telegram.customization.util.view.zoom.ExtendedViewPager;
import org.telegram.news.NewsDescriptionActivity;
import utils.view.FarsiTextView;
import utils.view.TitleTextView;

public class ImageViewerActivity extends Activity implements OnClickListener {
    /* renamed from: a */
    boolean f8364a;
    /* renamed from: b */
    ExtendedViewPager f8365b;
    /* renamed from: c */
    int f8366c = 0;
    /* renamed from: d */
    ArrayList<String> f8367d = new ArrayList();
    /* renamed from: e */
    ArrayList<String> f8368e;
    /* renamed from: f */
    ArrayList<String> f8369f;
    /* renamed from: g */
    View f8370g;
    /* renamed from: h */
    View f8371h;
    /* renamed from: i */
    View f8372i;
    /* renamed from: j */
    View f8373j;
    /* renamed from: k */
    View f8374k;
    /* renamed from: l */
    TitleTextView f8375l;
    /* renamed from: m */
    FarsiTextView f8376m;
    /* renamed from: n */
    Activity f8377n;
    /* renamed from: o */
    View f8378o;
    /* renamed from: p */
    View f8379p;
    /* renamed from: q */
    Dialog f8380q;
    /* renamed from: r */
    String f8381r;
    /* renamed from: s */
    C1768f f8382s = new C1768f();
    /* renamed from: t */
    private boolean f8383t = false;

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$1 */
    class C24991 extends C1748a<ArrayList<String>> {
        /* renamed from: d */
        final /* synthetic */ ImageViewerActivity f8358d;

        C24991(ImageViewerActivity imageViewerActivity) {
            this.f8358d = imageViewerActivity;
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$2 */
    class C25002 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ ImageViewerActivity f8359a;

        C25002(ImageViewerActivity imageViewerActivity) {
            this.f8359a = imageViewerActivity;
        }

        public void onClick(View view) {
            this.f8359a.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$3 */
    class C25013 implements C0188f {
        /* renamed from: a */
        final /* synthetic */ ImageViewerActivity f8360a;

        C25013(ImageViewerActivity imageViewerActivity) {
            this.f8360a = imageViewerActivity;
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            this.f8360a.f8366c = i;
            Log.d("sadegh", "imagevieweract pos2:" + this.f8360a.f8366c);
            this.f8360a.m12237a((String) this.f8360a.f8367d.get(i));
            Log.d("sadegh", "imagevieweract url:" + ((String) this.f8360a.f8367d.get(i)));
            this.f8360a.m12234b();
            this.f8360a.m12236c();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$4 */
    class C25024 implements C0188f {
        /* renamed from: a */
        final /* synthetic */ ImageViewerActivity f8361a;

        C25024(ImageViewerActivity imageViewerActivity) {
            this.f8361a = imageViewerActivity;
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            this.f8361a.f8366c = i;
            this.f8361a.m12237a((String) this.f8361a.f8367d.get(i));
            this.f8361a.m12234b();
            this.f8361a.m12236c();
        }
    }

    /* renamed from: a */
    private ArrayList<String> m12230a(ArrayList<String> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList();
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(m12233b((String) it.next()));
            }
        }
        return arrayList2;
    }

    /* renamed from: a */
    private void m12231a() {
        if (this.f8364a) {
            Log.d("sadegh", "imagevieweract pos1:" + this.f8366c);
            this.f8365b.setAdapter(new C2665l(this, this.f8377n, this.f8383t ? m12230a(this.f8367d) : this.f8367d));
            this.f8365b.setCurrentItem(this.f8366c);
            this.f8365b.setPageTransformer(true, new C2888l());
            this.f8365b.setOnPageChangeListener(new C25013(this));
        } else {
            Object c2642e = new C2642e(this, this, this.f8383t ? m12230a(this.f8367d) : this.f8367d);
            this.f8365b.setAdapter(c2642e);
            this.f8365b.setOnClickListener(c2642e);
            this.f8365b.setCurrentItem(this.f8366c);
            this.f8365b.setPageTransformer(true, new C2888l());
            this.f8365b.setOnPageChangeListener(new C25024(this));
        }
        m12234b();
        m12236c();
    }

    /* renamed from: b */
    private String m12233b(String str) {
        return "file://" + str;
    }

    /* renamed from: b */
    private void m12234b() {
        if (this.f8368e != null && this.f8368e.size() > this.f8366c) {
            this.f8376m.setText((CharSequence) this.f8368e.get(this.f8366c));
        }
        this.f8375l.setText((this.f8366c + 1) + "/" + this.f8367d.size());
    }

    /* renamed from: c */
    private void m12236c() {
        if (this.f8369f == null || this.f8369f.size() > this.f8366c) {
        }
        if (this.f8369f != null && this.f8369f.size() > this.f8366c) {
            final String str = (String) this.f8369f.get(this.f8366c);
            String str2 = (String) this.f8368e.get(this.f8366c);
            str2 = (String) this.f8367d.get(this.f8366c);
            this.f8371h.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ ImageViewerActivity f8363b;

                public void onClick(View view) {
                    Intent intent = new Intent(this.f8363b.f8377n, NewsDescriptionActivity.class);
                    intent.setAction(System.currentTimeMillis() + TtmlNode.ANONYMOUS_REGION_ID);
                    intent.putExtra("SPECIAL_NEWS", str);
                    intent.putExtra("EXTRA_BACK_TO_HOME", false);
                    this.f8363b.f8377n.startActivity(intent);
                }
            });
        }
    }

    /* renamed from: a */
    public void m12237a(String str) {
        this.f8381r = str;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root:
            case R.id.root2:
                finish();
                return;
            default:
                return;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 2) {
            this.f8364a = false;
        } else if (configuration.orientation == 1) {
            this.f8364a = true;
        }
        m12231a();
    }

    public void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        setContentView(R.layout.activity_imageviewer);
        overridePendingTransition(R.anim.screen_shot_open, R.anim.no_anim);
        this.f8370g = findViewById(R.id.iv_share);
        this.f8374k = findViewById(R.id.iv_back);
        this.f8371h = findViewById(R.id.iv_shownews);
        this.f8372i = findViewById(R.id.iv_download);
        this.f8373j = findViewById(R.id.iv_wallpaper);
        this.f8375l = (TitleTextView) findViewById(R.id.ftv_image_number);
        this.f8376m = (FarsiTextView) findViewById(R.id.news_title);
        this.f8378o = findViewById(R.id.root);
        this.f8379p = findViewById(R.id.root2);
        this.f8377n = this;
        this.f8380q = new Dialog(this.f8377n);
        if (getResources().getConfiguration().orientation == 2) {
            this.f8364a = false;
        } else {
            this.f8364a = true;
        }
        if (getIntent().getExtras() != null) {
            this.f8367d = (ArrayList) this.f8382s.m8393a(getIntent().getStringExtra(TtmlNode.ATTR_ID), new C24991(this).m8360b());
            if (this.f8369f != null && this.f8369f.size() > 0) {
                this.f8371h.setVisibility(0);
            }
            if (this.f8383t) {
                this.f8372i.setVisibility(8);
            }
            this.f8366c = getIntent().getIntExtra("CURRENT_PIC_ID", 0);
        }
        if (this.f8368e != null) {
            while (i < this.f8368e.size()) {
                if (TextUtils.isEmpty((CharSequence) this.f8368e.get(i))) {
                    this.f8368e.set(i, " ");
                }
                i++;
            }
        }
        this.f8365b = (ExtendedViewPager) findViewById(R.id.view_pager);
        this.f8375l = (TitleTextView) findViewById(R.id.ftv_image_number);
        m12231a();
        m12237a((String) this.f8367d.get(this.f8366c));
        this.f8374k.setOnClickListener(new C25002(this));
        this.f8370g.setVisibility(8);
        this.f8378o.setOnClickListener(this);
        this.f8379p.setOnClickListener(this);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        try {
            m12231a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
