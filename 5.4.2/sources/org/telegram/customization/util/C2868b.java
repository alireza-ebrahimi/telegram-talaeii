package org.telegram.customization.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.p077f.p078a.p086b.C1570c;
import com.p077f.p078a.p086b.C1570c.C1566a;
import com.p077f.p078a.p086b.C1575d;
import com.p077f.p078a.p086b.p087a.C1550b;
import com.p077f.p078a.p086b.p090c.C1568b;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p077f.p078a.p086b.p093f.C1587b;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.ui.ChatActivity;

/* renamed from: org.telegram.customization.util.b */
public class C2868b {
    /* renamed from: a */
    private static C1570c f9479a;

    /* renamed from: org.telegram.customization.util.b$a */
    private static class C2867a implements C1586a, C1587b {
        /* renamed from: b */
        static final List<String> f9477b = Collections.synchronizedList(new LinkedList());
        /* renamed from: a */
        ProgressBar f9478a;

        /* renamed from: org.telegram.customization.util.b$a$1 */
        class C28661 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C2867a f9476a;

            C28661(C2867a c2867a) {
                this.f9476a = c2867a;
            }

            public void run() {
                if (this.f9476a.f9478a != null) {
                    this.f9476a.f9478a.setVisibility(8);
                }
            }
        }

        public C2867a(ProgressBar progressBar) {
            this.f9478a = progressBar;
        }

        /* renamed from: a */
        public void mo3497a(String str, View view, int i, int i2) {
            if (this.f9478a != null) {
                this.f9478a.setProgress(i);
            }
        }

        public void onLoadingCancelled(String str, View view) {
        }

        public void onLoadingComplete(String str, View view, Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) view;
                if ((!f9477b.contains(str) ? 1 : null) != null) {
                    C1568b.m7743a(imageView, ChatActivity.startAllServices);
                    f9477b.add(str);
                }
                new Handler().postDelayed(new C28661(this), 2000);
            }
        }

        public void onLoadingFailed(String str, View view, C1550b c1550b) {
            if (c1550b != null) {
                try {
                    if (!(c1550b.m7672a() == null || TextUtils.isEmpty(c1550b.m7672a().getMessage()))) {
                        c1550b.m7672a().printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            ((ImageView) view).setImageResource(R.drawable.placeholderno3);
        }

        public void onLoadingStarted(String str, View view) {
            ((ImageView) view).setImageResource(R.drawable.placeholderloading);
            if (this.f9478a != null) {
                this.f9478a.setVisibility(0);
                this.f9478a.startAnimation(AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate));
            }
        }
    }

    /* renamed from: a */
    public static C1570c m13328a() {
        if (f9479a == null) {
            f9479a = new C1566a().m7732a((int) R.drawable.placeholderloading).m7738b((int) R.drawable.placeholderno3).m7740c((int) R.drawable.placeholderno3).m7736a(false).m7739b(true).m7741c(true).m7733a(Config.RGB_565).m7737a();
        }
        return f9479a;
    }

    /* renamed from: a */
    public static void m13329a(ImageView imageView, String str) {
        if (str == null || str.length() <= 0) {
            imageView.setImageResource(R.drawable.placeholderno3);
        } else {
            C1575d.m7807a().m7812a(str, imageView, C2868b.m13328a(), new C2867a(null));
        }
    }

    /* renamed from: a */
    public static void m13330a(ImageView imageView, String str, final CircularProgressBar circularProgressBar) {
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(R.drawable.placeholderno4);
            return;
        }
        C1575d a = C1575d.m7807a();
        final Animation loadAnimation = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        loadAnimation.setRepeatCount(-1);
        a.m7813a(str, imageView, C2868b.m13328a(), new C1586a() {
            public void onLoadingCancelled(String str, View view) {
                circularProgressBar.setVisibility(8);
                circularProgressBar.clearAnimation();
            }

            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                circularProgressBar.clearAnimation();
                circularProgressBar.setVisibility(8);
            }

            public void onLoadingFailed(String str, View view, C1550b c1550b) {
                circularProgressBar.clearAnimation();
                circularProgressBar.setVisibility(8);
            }

            public void onLoadingStarted(String str, View view) {
                circularProgressBar.setVisibility(0);
                circularProgressBar.startAnimation(loadAnimation);
            }
        }, new C1587b() {
            /* renamed from: a */
            public void mo3497a(String str, View view, int i, int i2) {
                if (circularProgressBar != null) {
                    circularProgressBar.setProgress((float) ((i * 100) / i2));
                }
            }
        });
    }

    /* renamed from: b */
    public static void m13331b(ImageView imageView, String str, final CircularProgressBar circularProgressBar) {
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(R.drawable.placeholderno4);
            return;
        }
        C1575d a = C1575d.m7807a();
        final Animation loadAnimation = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        loadAnimation.setRepeatCount(-1);
        a.m7813a(str, imageView, C2868b.m13328a(), new C1586a() {
            public void onLoadingCancelled(String str, View view) {
                circularProgressBar.setVisibility(8);
                circularProgressBar.clearAnimation();
            }

            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                circularProgressBar.clearAnimation();
                circularProgressBar.setVisibility(8);
                if (VERSION.SDK_INT >= 17) {
                    Bitmap a = C2872c.m13337a(view.getContext(), bitmap, 3);
                    if (a == null) {
                        ((ImageView) view).setImageBitmap(bitmap);
                        return;
                    } else {
                        ((ImageView) view).setImageBitmap(a);
                        return;
                    }
                }
                ((ImageView) view).setImageBitmap(bitmap);
            }

            public void onLoadingFailed(String str, View view, C1550b c1550b) {
                circularProgressBar.clearAnimation();
                circularProgressBar.setVisibility(8);
            }

            public void onLoadingStarted(String str, View view) {
                circularProgressBar.setVisibility(0);
                circularProgressBar.startAnimation(loadAnimation);
            }
        }, new C1587b() {
            /* renamed from: a */
            public void mo3497a(String str, View view, int i, int i2) {
                if (circularProgressBar != null) {
                    circularProgressBar.setProgress((float) ((i * 100) / i2));
                }
            }
        });
    }
}
