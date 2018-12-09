package org.telegram.customization.p156a;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.aa;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.p077f.p078a.p086b.C1575d;
import com.p077f.p078a.p086b.p087a.C1550b;
import com.p077f.p078a.p086b.p093f.C1586a;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.util.view.zoom.ExtendedViewPager;
import org.telegram.customization.util.view.zoom.TouchImageView;

/* renamed from: org.telegram.customization.a.l */
public class C2665l extends aa implements OnClickListener {
    /* renamed from: a */
    Context f8891a;
    /* renamed from: b */
    LayoutInflater f8892b;
    /* renamed from: c */
    ArrayList<String> f8893c = new ArrayList();
    /* renamed from: d */
    private final Activity f8894d;

    public C2665l(Context context, Activity activity, ArrayList<String> arrayList) {
        this.f8891a = context;
        this.f8893c = arrayList;
        this.f8892b = (LayoutInflater) context.getSystemService("layout_inflater");
        this.f8894d = activity;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ExtendedViewPager) viewGroup).removeView((View) obj);
    }

    public int getCount() {
        return this.f8893c.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.f8892b.inflate(R.layout.screen_shot_full_item, null);
        final TouchImageView touchImageView = (TouchImageView) inflate.findViewById(R.id.iv_screen_shot);
        final ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.pb_loading);
        String str = (String) this.f8893c.get(i);
        Log.d("sadegh", "imagevieweract url2:" + str);
        if (str != null) {
            inflate.findViewById(R.id.root).setOnClickListener(this);
            C1575d.m7807a().m7812a(str, touchImageView, C2666a.getImageOptions(), new C1586a(this) {
                /* renamed from: c */
                final /* synthetic */ C2665l f8890c;

                public void onLoadingCancelled(String str, View view) {
                }

                public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                    Log.d("sadegh", "onLoadingComplete1");
                    touchImageView.setImageBitmap(bitmap);
                    touchImageView.setVisibility(0);
                    progressBar.setVisibility(8);
                    touchImageView.setZoom(1.0f);
                }

                public void onLoadingFailed(String str, View view, C1550b c1550b) {
                }

                public void onLoadingStarted(String str, View view) {
                }
            });
            ((ExtendedViewPager) viewGroup).addView(inflate, 0);
        }
        return inflate;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root:
                this.f8894d.finish();
                return;
            default:
                return;
        }
    }
}
