package org.telegram.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.C0235a;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v7.widget.Toolbar;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ksoichiro.android.observablescrollview.C1651a;
import com.github.ksoichiro.android.observablescrollview.C1652b;
import com.github.ksoichiro.android.observablescrollview.C1654c;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.p098a.C1768f;
import com.p077f.p078a.p086b.C1570c;
import com.p077f.p078a.p086b.C1570c.C1566a;
import com.p077f.p078a.p086b.p087a.C1550b;
import com.p077f.p078a.p086b.p090c.C1568b;
import com.p077f.p078a.p086b.p093f.C1586a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ImageViewerActivity;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.C2868b;
import org.telegram.news.p177b.C3744b;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import utils.C3792d;
import utils.p178a.C3791b;
import utils.view.CircularProgress;
import utils.view.FarsiTextView;

/* renamed from: org.telegram.news.e */
public class C3773e extends LinearLayout implements C0188f, ImageGetter, OnClickListener, C1651a, C2497d, C3733c {
    /* renamed from: A */
    LinearLayout f10082A;
    /* renamed from: B */
    LinearLayout f10083B;
    /* renamed from: C */
    LayoutInflater f10084C;
    /* renamed from: D */
    volatile ArrayList<WeakReference<TextView>> f10085D = new ArrayList();
    /* renamed from: E */
    int f10086E;
    /* renamed from: F */
    LinearLayout f10087F;
    /* renamed from: G */
    ImageView f10088G;
    /* renamed from: H */
    View f10089H;
    /* renamed from: I */
    LinearLayout f10090I;
    /* renamed from: J */
    LinearLayout f10091J;
    /* renamed from: K */
    LinearLayout f10092K;
    /* renamed from: L */
    ImageView f10093L;
    /* renamed from: M */
    ImageView f10094M;
    /* renamed from: N */
    ImageView f10095N;
    /* renamed from: O */
    LinearLayout f10096O;
    /* renamed from: P */
    LinearLayout f10097P;
    /* renamed from: Q */
    View f10098Q;
    /* renamed from: R */
    View f10099R;
    /* renamed from: S */
    View f10100S;
    /* renamed from: T */
    View f10101T;
    /* renamed from: U */
    TextView f10102U;
    /* renamed from: V */
    ImageView f10103V;
    /* renamed from: W */
    LinearLayout f10104W;
    /* renamed from: a */
    final Integer f10105a = Integer.valueOf(1);
    boolean aa = false;
    private int ab = 0;
    private Toolbar ac;
    private View ad;
    private View ae;
    private ObservableScrollView af;
    private int ag;
    private int ah;
    private boolean ai = false;
    private C1570c aj;
    private boolean ak = false;
    private View al;
    /* renamed from: b */
    public boolean f10106b = false;
    /* renamed from: c */
    C3744b f10107c;
    /* renamed from: d */
    C3744b f10108d = new C3744b();
    /* renamed from: e */
    View f10109e;
    /* renamed from: f */
    View f10110f;
    /* renamed from: g */
    CircularProgress f10111g;
    /* renamed from: h */
    View f10112h;
    /* renamed from: i */
    ImageView f10113i;
    /* renamed from: j */
    TextView f10114j;
    /* renamed from: k */
    TextView f10115k;
    /* renamed from: l */
    TextView f10116l;
    /* renamed from: m */
    LinearLayout f10117m;
    /* renamed from: n */
    LinearLayout f10118n;
    /* renamed from: o */
    ProgressBar f10119o;
    /* renamed from: p */
    Activity f10120p;
    /* renamed from: q */
    Activity f10121q;
    /* renamed from: r */
    LinearLayout f10122r;
    /* renamed from: s */
    ImageView f10123s;
    /* renamed from: t */
    View f10124t;
    /* renamed from: u */
    View f10125u;
    /* renamed from: v */
    TextView f10126v;
    /* renamed from: w */
    View f10127w;
    /* renamed from: x */
    LinearLayout f10128x;
    /* renamed from: y */
    float f10129y = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: z */
    TextView f10130z;

    /* renamed from: org.telegram.news.e$4 */
    class C37564 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C3773e f10051a;

        C37564(C3773e c3773e) {
            this.f10051a = c3773e;
        }

        public void run() {
            this.f10051a.af.scrollTo(0, this.f10051a.ah - this.f10051a.ag);
            this.f10051a.af.scrollTo(0, 1);
            this.f10051a.af.scrollTo(0, 0);
        }
    }

    /* renamed from: org.telegram.news.e$5 */
    class C37575 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C3773e f10052a;

        C37575(C3773e c3773e) {
            this.f10052a = c3773e;
        }

        public void onClick(View view) {
            this.f10052a.m13864a(this.f10052a.f10107c);
        }
    }

    /* renamed from: org.telegram.news.e$6 */
    class C37586 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C3773e f10053a;

        C37586(C3773e c3773e) {
            this.f10053a = c3773e;
        }

        public void run() {
            LayoutParams layoutParams = this.f10053a.f10109e.getLayoutParams();
            layoutParams.height = -1;
            layoutParams.width = -1;
            this.f10053a.f10109e.setLayoutParams(layoutParams);
        }
    }

    /* renamed from: org.telegram.news.e$7 */
    class C37687 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C3773e f10074a;

        /* renamed from: org.telegram.news.e$7$1 */
        class C37591 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C37687 f10055a;

            C37591(C37687 c37687) {
                this.f10055a = c37687;
            }

            public void onClick(View view) {
            }
        }

        /* renamed from: org.telegram.news.e$7$2 */
        class C37602 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C37687 f10056a;

            C37602(C37687 c37687) {
                this.f10056a = c37687;
            }

            public void run() {
                this.f10056a.f10074a.m13879g();
            }
        }

        /* renamed from: org.telegram.news.e$7$7 */
        class C37657 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C37687 f10068a;

            C37657(C37687 c37687) {
                this.f10068a = c37687;
            }

            public void onClick(View view) {
            }
        }

        /* renamed from: org.telegram.news.e$7$9 */
        class C37679 implements C1586a {
            /* renamed from: a */
            final /* synthetic */ C37687 f10073a;

            C37679(C37687 c37687) {
                this.f10073a = c37687;
            }

            public void onLoadingCancelled(String str, View view) {
            }

            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                synchronized (this.f10073a.f10074a.f10105a) {
                    this.f10073a.f10074a.f10113i.setImageDrawable(new C3771a(this.f10073a.f10074a, this.f10073a.f10074a.f10120p.getResources(), bitmap));
                    this.f10073a.f10074a.mo4197a(1, true, true);
                    this.f10073a.f10074a.f10111g.setVisibility(8);
                    C1568b.m7743a(view, ChatActivity.startAllServices);
                }
            }

            public void onLoadingFailed(String str, View view, C1550b c1550b) {
            }

            public void onLoadingStarted(String str, View view) {
            }
        }

        C37687(C3773e c3773e) {
            this.f10074a = c3773e;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r19 = this;
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10128x;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10124t;
            r3 = 8;
            r2.setVisibility(r3);
            r4 = 0;
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10128x;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10125u;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10115k;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13793k();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10115k;
            r0 = r19;
            r3 = r0.f10074a;
            r2.setOnClickListener(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10123s;
            r0 = r19;
            r3 = r0.f10074a;
            r2.setOnClickListener(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13783f();
            if (r2 <= 0) goto L_0x027b;
        L_0x0065:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10099R;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10099R;
            r3 = 2131690001; // 0x7f0f0211 float:1.9009033E38 double:1.053194797E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r0 = r19;
            r5 = r0.f10074a;
            r5 = r5.f10108d;
            r5 = r5.m13783f();
            r3 = r3.append(r5);
            r5 = "";
            r3 = r3.append(r5);
            r3 = r3.toString();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10127w;
            r3 = 0;
            r2.setVisibility(r3);
        L_0x00a9:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13797n();
            r3 = 1;
            if (r2 <= r3) goto L_0x02a0;
        L_0x00b6:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10100S;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10100S;
            r3 = 2131690004; // 0x7f0f0214 float:1.900904E38 double:1.0531947986E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r0 = r19;
            r5 = r0.f10074a;
            r5 = r5.f10108d;
            r5 = r5.m13797n();
            r3 = r3.append(r5);
            r5 = "";
            r3 = r3.append(r5);
            r3 = r3.toString();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13783f();
            r3 = 1;
            if (r2 >= r3) goto L_0x0293;
        L_0x00fd:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10098Q;
            r3 = 0;
            r2.setVisibility(r3);
        L_0x0107:
            r2 = new java.util.Date;	 Catch:{ Exception -> 0x02b8 }
            r0 = r19;
            r3 = r0.f10074a;	 Catch:{ Exception -> 0x02b8 }
            r3 = r3.f10108d;	 Catch:{ Exception -> 0x02b8 }
            r6 = r3.m13791j();	 Catch:{ Exception -> 0x02b8 }
            r6 = (double) r6;	 Catch:{ Exception -> 0x02b8 }
            r8 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
            r6 = r6 * r8;
            r6 = (long) r6;	 Catch:{ Exception -> 0x02b8 }
            r2.<init>(r6);	 Catch:{ Exception -> 0x02b8 }
            r0 = r19;
            r3 = r0.f10074a;	 Catch:{ Exception -> 0x02b8 }
            r3 = r3.f10116l;	 Catch:{ Exception -> 0x02b8 }
            r2 = utils.C3792d.m14079a(r2);	 Catch:{ Exception -> 0x02b8 }
            r3.setText(r2);	 Catch:{ Exception -> 0x02b8 }
        L_0x012b:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10114j;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13789i();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10102U;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13789i();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10117m;
            r2.removeAllViews();
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10118n;
            r2.removeAllViews();
            r5 = 0;
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13782e();
            if (r2 == 0) goto L_0x05bd;
        L_0x0170:
            r3 = -1;
            r6 = -1;
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r7 = r2.m13782e();
            r8 = r7.length;
            r2 = 0;
            r18 = r2;
            r2 = r6;
            r6 = r18;
        L_0x0183:
            if (r6 >= r8) goto L_0x0831;
        L_0x0185:
            r9 = r7[r6];
            r2 = r2 + 1;
            r10 = r9.m13753a();
            r11 = 3;
            if (r10 != r11) goto L_0x02d6;
        L_0x0190:
            r3 = 1;
            r4 = new android.widget.LinearLayout$LayoutParams;
            r6 = -1;
            r7 = -2;
            r4.<init>(r6, r7);
            r6 = 0;
            r7 = 0;
            r8 = 0;
            r10 = 0;
            r4.setMargins(r6, r7, r8, r10);
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10125u;
            r6.setLayoutParams(r4);
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10126v;
            r6 = 0;
            r4.setVisibility(r6);
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10127w;
            r6 = 0;
            r4.setVisibility(r6);
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10098Q;
            r6 = 8;
            r4.setVisibility(r6);
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10126v;
            r6 = r9.m13756d();
            r4.setText(r6);
            r4 = "sadegh";
            r6 = "vTop.setOnClickListenerL : video for1";
            android.util.Log.d(r4, r6);
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10110f;
            r6 = new org.telegram.news.e$7$1;
            r0 = r19;
            r6.<init>(r0);
            r4.setOnClickListener(r6);
            r0 = r19;
            r4 = r0.f10074a;
            r6 = 1;
            r4.f10106b = r6;
            r0 = r19;
            r4 = r0.f10074a;
            r6 = 1;
            r4.ai = r6;
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10086E;
            r6 = r9.m13757e();
            r7 = android.text.TextUtils.isEmpty(r6);
            if (r7 == 0) goto L_0x02be;
        L_0x020c:
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10113i;
            r6 = 2130838203; // 0x7f0202bb float:1.7281382E38 double:1.052773953E-314;
            r4.setImageResource(r6);
        L_0x0218:
            r4 = "LEE";
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "ContentDesc:";
            r6 = r6.append(r7);
            r0 = r19;
            r7 = r0.f10074a;
            r7 = r7.f10108d;
            r7 = r7.m13782e();
            r7 = r7.length;
            r6 = r6.append(r7);
            r6 = r6.toString();
            android.util.Log.d(r4, r6);
            r9 = -1;
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10108d;
            r13 = r4.m13782e();
            r14 = r13.length;
            r4 = 0;
            r11 = r4;
            r4 = r2;
            r2 = r5;
            r5 = r3;
        L_0x024e:
            if (r11 >= r14) goto L_0x05be;
        L_0x0250:
            r15 = r13[r11];
            r9 = r9 + 1;
            r3 = r15.m13753a();
            switch(r3) {
                case 1: goto L_0x02da;
                case 2: goto L_0x038e;
                case 3: goto L_0x04d7;
                case 4: goto L_0x025b;
                case 5: goto L_0x0555;
                case 6: goto L_0x0559;
                case 7: goto L_0x055d;
                default: goto L_0x025b;
            };
        L_0x025b:
            r3 = r4;
            r4 = r2;
        L_0x025d:
            r2 = r4.getParent();	 Catch:{ Exception -> 0x0827 }
            r2 = (android.view.ViewGroup) r2;	 Catch:{ Exception -> 0x0827 }
            r2.removeView(r4);	 Catch:{ Exception -> 0x0827 }
        L_0x0266:
            if (r3 == r9) goto L_0x0271;
        L_0x0268:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10117m;
            r2.addView(r4);
        L_0x0271:
            r2 = r3;
            r3 = r4;
            r4 = r5;
        L_0x0274:
            r5 = r11 + 1;
            r11 = r5;
            r5 = r4;
            r4 = r2;
            r2 = r3;
            goto L_0x024e;
        L_0x027b:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10099R;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10127w;
            r3 = 8;
            r2.setVisibility(r3);
            goto L_0x00a9;
        L_0x0293:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10098Q;
            r3 = 8;
            r2.setVisibility(r3);
            goto L_0x0107;
        L_0x02a0:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10100S;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10098Q;
            r3 = 8;
            r2.setVisibility(r3);
            goto L_0x0107;
        L_0x02b8:
            r2 = move-exception;
            r2.printStackTrace();
            goto L_0x012b;
        L_0x02be:
            r7 = org.telegram.news.C3741a.m13750a();
            r0 = r19;
            r8 = r0.f10074a;
            r8 = r8.getOptions();
            r9 = new org.telegram.news.e$7$3;
            r0 = r19;
            r9.<init>(r0, r4);
            r7.m13752a(r6, r8, r9);
            goto L_0x0218;
        L_0x02d6:
            r6 = r6 + 1;
            goto L_0x0183;
        L_0x02da:
            r2 = "LEE";
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r6 = "ContentDesc:";
            r3 = r3.append(r6);
            r6 = r15.m13758f();
            r3 = r3.append(r6);
            r3 = r3.toString();
            android.util.Log.d(r2, r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10120p;
            r2 = android.view.LayoutInflater.from(r2);
            r3 = 2130903127; // 0x7f030057 float:1.7413063E38 double:1.0528060297E-314;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10117m;
            r7 = 0;
            r3 = r2.inflate(r3, r6, r7);
            r2 = 2131689845; // 0x7f0f0175 float:1.9008717E38 double:1.05319472E-314;
            r2 = r3.findViewById(r2);
            r2 = (android.widget.TextView) r2;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10120p;
            r6 = org.telegram.news.C3773e.C3772b.m13860a(r6);
            r2.setMovementMethod(r6);
            r6 = r15.m13758f();
            r7 = "<blockquote>";
            r8 = "";
            r6 = r6.replace(r7, r8);
            r7 = "</blockquote>";
            r8 = "<br/>";
            r6 = r6.replace(r7, r8);
            r7 = "<p>";
            r8 = "";
            r6 = r6.replace(r7, r8);
            r7 = "</p>";
            r8 = "";
            r6 = r6.replace(r7, r8);
            r0 = r19;
            r7 = r0.f10074a;
            r8 = 0;
            r6 = android.text.Html.fromHtml(r6, r7, r8);
            r7 = new android.text.SpannedString;
            r7.<init>(r6);
            r2.setText(r7);
            r6 = 2;
            r0 = r19;
            r7 = r0.f10074a;
            r7 = r7.f10129y;
            r2.setTextSize(r6, r7);
            r6 = 1;
            r2.setTextIsSelectable(r6);
            r6 = 1;
            r2.setFocusable(r6);
            r6 = 1;
            r2.setFocusableInTouchMode(r6);
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10085D;
            r7 = new java.lang.ref.WeakReference;
            r7.<init>(r2);
            r6.add(r7);
            r18 = r4;
            r4 = r3;
            r3 = r18;
            goto L_0x025d;
        L_0x038e:
            r3 = r15.m13754b();	 Catch:{ Exception -> 0x082a }
            r6 = 0;
            r3 = r3[r6];	 Catch:{ Exception -> 0x082a }
            if (r3 == 0) goto L_0x03a6;
        L_0x0397:
            r3 = r15.m13754b();	 Catch:{ Exception -> 0x082a }
            r6 = 0;
            r3 = r3[r6];	 Catch:{ Exception -> 0x082a }
            r3 = r3.length();	 Catch:{ Exception -> 0x082a }
            r6 = 10;
            if (r3 >= r6) goto L_0x03ab;
        L_0x03a6:
            r3 = r2;
            r2 = r4;
            r4 = r5;
            goto L_0x0274;
        L_0x03ab:
            r0 = r19;
            r2 = r0.f10074a;	 Catch:{ Exception -> 0x082a }
            r2 = r2.f10108d;	 Catch:{ Exception -> 0x082a }
            r2 = r2.m13796m();	 Catch:{ Exception -> 0x082a }
            r3 = 0;
            r2 = r2.get(r3);	 Catch:{ Exception -> 0x082a }
            r2 = (java.lang.String) r2;	 Catch:{ Exception -> 0x082a }
            r3 = r15.m13754b();	 Catch:{ Exception -> 0x082a }
            r6 = 0;
            r3 = r3[r6];	 Catch:{ Exception -> 0x082a }
            r2 = r2.equals(r3);	 Catch:{ Exception -> 0x082a }
            if (r2 == 0) goto L_0x03c9;
        L_0x03c9:
            if (r5 != 0) goto L_0x04d4;
        L_0x03cb:
            r6 = 1;
        L_0x03cc:
            if (r6 == 0) goto L_0x082d;
        L_0x03ce:
            r5 = 1;
            r2 = new android.widget.LinearLayout$LayoutParams;
            r3 = -1;
            r4 = -2;
            r2.<init>(r3, r4);
            r3 = 0;
            r4 = 0;
            r7 = 0;
            r8 = 0;
            r2.setMargins(r3, r4, r7, r8);
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10125u;
            r3.setLayoutParams(r2);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10112h;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10126v;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10127w;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10126v;
            r3 = r15.m13756d();
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10112h;
            r3 = 2131690004; // 0x7f0f0214 float:1.900904E38 double:1.0531947986E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r3 = r15.m13754b();
            r3 = r3.length;
            r3 = java.lang.String.valueOf(r3);
            r2.setText(r3);
            r2 = "sadegh";
            r3 = "vTop.setOnClickListenerL : img for2";
            android.util.Log.d(r2, r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10110f;
            r3 = new org.telegram.news.e$7$4;
            r0 = r19;
            r3.<init>(r0, r15);
            r2.setOnClickListener(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r3 = 1;
            r2.ai = r3;
            r8 = r9;
            r10 = r5;
        L_0x0451:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10120p;
            r2 = android.view.LayoutInflater.from(r2);
            r3 = 2130903125; // 0x7f030055 float:1.741306E38 double:1.0528060287E-314;
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10117m;
            r5 = 0;
            r12 = r2.inflate(r3, r4, r5);
            r2 = 2131689839; // 0x7f0f016f float:1.9008705E38 double:1.053194717E-314;
            r5 = r12.findViewById(r2);
            r5 = (android.widget.ImageView) r5;
            r2 = 2131689841; // 0x7f0f0171 float:1.9008709E38 double:1.053194718E-314;
            r2 = r12.findViewById(r2);
            r2 = (android.widget.TextView) r2;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "";
            r3 = r3.append(r4);
            r4 = r15.m13754b();
            r4 = r4.length;
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2.setText(r3);
            r2 = 2131689664; // 0x7f0f00c0 float:1.900835E38 double:1.0531946306E-314;
            r4 = r12.findViewById(r2);
            r0 = r19;
            r2 = r0.f10074a;
            r7 = r2.f10086E;
            r2 = new org.telegram.news.e$7$5;
            r0 = r19;
            r2.<init>(r0, r15);
            r12.setOnClickListener(r2);
            r16 = org.telegram.news.C3741a.m13750a();
            r2 = r15.m13754b();
            r3 = 0;
            r15 = r2[r3];
            r0 = r19;
            r2 = r0.f10074a;
            r17 = r2.getOptions();
            r2 = new org.telegram.news.e$7$6;
            r3 = r19;
            r2.<init>(r3, r4, r5, r6, r7);
            r0 = r16;
            r1 = r17;
            r0.m13752a(r15, r1, r2);
            r3 = r8;
            r4 = r12;
            r5 = r10;
            goto L_0x025d;
        L_0x04d4:
            r6 = 0;
            goto L_0x03cc;
        L_0x04d7:
            r3 = r15.m13755c();	 Catch:{ Exception -> 0x04ee }
            if (r3 == 0) goto L_0x04e9;
        L_0x04dd:
            r3 = r15.m13755c();	 Catch:{ Exception -> 0x04ee }
            r3 = r3.length();	 Catch:{ Exception -> 0x04ee }
            r6 = 10;
            if (r3 >= r6) goto L_0x04ef;
        L_0x04e9:
            r3 = r2;
            r2 = r4;
            r4 = r5;
            goto L_0x0274;
        L_0x04ee:
            r2 = move-exception;
        L_0x04ef:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10120p;
            r2 = android.view.LayoutInflater.from(r2);
            r3 = 2130903126; // 0x7f030056 float:1.7413061E38 double:1.052806029E-314;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10117m;
            r7 = 0;
            r6 = r2.inflate(r3, r6, r7);
            r2 = 2131689839; // 0x7f0f016f float:1.9008705E38 double:1.053194717E-314;
            r2 = r6.findViewById(r2);
            r2 = (android.widget.ImageView) r2;
            r3 = 2131689664; // 0x7f0f00c0 float:1.900835E38 double:1.0531946306E-314;
            r7 = r6.findViewById(r3);
            r3 = 2131689844; // 0x7f0f0174 float:1.9008715E38 double:1.0531947195E-314;
            r3 = r6.findViewById(r3);
            r3 = (utils.view.TitleTextView) r3;
            r8 = r15.m13756d();
            r3.setText(r8);
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10086E;
            r8 = new org.telegram.news.e$7$7;
            r0 = r19;
            r8.<init>(r0);
            r6.setOnClickListener(r8);
            r8 = org.telegram.news.C3741a.m13750a();
            r10 = r15.m13757e();
            r0 = r19;
            r12 = r0.f10074a;
            r12 = r12.getOptions();
            r15 = new org.telegram.news.e$7$8;
            r0 = r19;
            r15.<init>(r0, r7, r2, r3);
            r8.m13752a(r10, r12, r15);
            r3 = r4;
            r4 = r6;
            goto L_0x025d;
        L_0x0555:
            r3 = r4;
            r4 = r2;
            goto L_0x025d;
        L_0x0559:
            r3 = r4;
            r4 = r2;
            goto L_0x025d;
        L_0x055d:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10120p;
            r2 = android.view.LayoutInflater.from(r2);
            r3 = 2130903127; // 0x7f030057 float:1.7413063E38 double:1.0528060297E-314;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10117m;
            r7 = 0;
            r3 = r2.inflate(r3, r6, r7);
            r2 = 2131689845; // 0x7f0f0175 float:1.9008717E38 double:1.05319472E-314;
            r2 = r3.findViewById(r2);
            r2 = (android.widget.TextView) r2;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.f10120p;
            r6 = org.telegram.news.C3773e.C3772b.m13860a(r6);
            r2.setMovementMethod(r6);
            r6 = r15.m13758f();
            r0 = r19;
            r7 = r0.f10074a;
            r8 = 0;
            r6 = android.text.Html.fromHtml(r6, r7, r8);
            r7 = new android.text.SpannedString;
            r7.<init>(r6);
            r2.setText(r7);
            r6 = 2;
            r0 = r19;
            r7 = r0.f10074a;
            r7 = r7.f10129y;
            r2.setTextSize(r6, r7);
            r6 = 1;
            r2.setTextIsSelectable(r6);
            r6 = 1;
            r2.setFocusable(r6);
            r6 = 1;
            r2.setFocusableInTouchMode(r6);
            r18 = r4;
            r4 = r3;
            r3 = r18;
            goto L_0x025d;
        L_0x05bd:
            r5 = r4;
        L_0x05be:
            r0 = r19;
            r2 = r0.f10074a;
            r2.m13868b();
            r0 = r19;
            r2 = r0.f10074a;
            r3 = 1;
            r2.f10106b = r3;
            r2 = com.p077f.p078a.p086b.C1575d.m7807a();
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13785g();
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10123s;
            r0 = r19;
            r6 = r0.f10074a;
            r6 = r6.getOptions();
            r2.m7811a(r3, r4, r6);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            if (r2 == 0) goto L_0x0607;
        L_0x05f7:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            r2 = r2.size();
            if (r2 != 0) goto L_0x0632;
        L_0x0607:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13800q();
            r2 = android.text.TextUtils.isEmpty(r2);
            if (r2 != 0) goto L_0x0632;
        L_0x0617:
            r2 = new java.util.ArrayList;
            r2.<init>();
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13800q();
            r2.add(r3);
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3.m13771b(r2);
        L_0x0632:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            if (r2 == 0) goto L_0x07d2;
        L_0x063e:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            r2 = r2.size();
            if (r2 <= 0) goto L_0x07d2;
        L_0x064e:
            r0 = r19;
            r2 = r0.f10074a;
            r3 = r2.f10108d;
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            r4 = 0;
            r2 = r2.get(r4);
            r2 = (java.lang.String) r2;
            r3.m13790i(r2);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10112h;
            r3 = 2131690004; // 0x7f0f0214 float:1.900904E38 double:1.0531947986E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13796m();
            r3 = r3.size();
            r3 = java.lang.String.valueOf(r3);
            r2.setText(r3);
            r3 = "LEE";
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r4 = "test:";
            r4 = r2.append(r4);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            r5 = 0;
            r2 = r2.get(r5);
            r2 = (java.lang.String) r2;
            r2 = r4.append(r2);
            r2 = r2.toString();
            android.util.Log.d(r3, r2);
            r0 = r19;
            r2 = r0.f10074a;
            r3 = 1;
            r2.f10106b = r3;
            r3 = org.telegram.news.C3741a.m13750a();
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13796m();
            r4 = 0;
            r2 = r2.get(r4);
            r2 = (java.lang.String) r2;
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.getOptions();
            r5 = new org.telegram.news.e$7$9;
            r0 = r19;
            r5.<init>(r0);
            r3.m13752a(r2, r4, r5);
            r2 = com.p077f.p078a.p086b.C1575d.m7807a();
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r3 = r3.m13785g();
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.f10123s;
            r2.m7810a(r3, r4);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10110f;
            r3 = new org.telegram.news.e$7$10;
            r0 = r19;
            r3.<init>(r0);
            r2.setOnClickListener(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r3 = 1;
            r2.ai = r3;
            r2 = new android.widget.LinearLayout$LayoutParams;
            r3 = -1;
            r4 = -2;
            r2.<init>(r3, r4);
            r3 = 0;
            r4 = 0;
            r5 = 0;
            r6 = 0;
            r2.setMargins(r3, r4, r5, r6);
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10125u;
            r3.setLayoutParams(r2);
        L_0x072b:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.ac;
            r3 = "actionBarDefault";
            r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
            r2.setBackgroundColor(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10120p;
            r3 = "actionBarDefault";
            r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
            org.telegram.news.C3773e.m13863a(r2, r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.ak;
            if (r2 == 0) goto L_0x07b4;
        L_0x0757:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10108d;
            r2 = r2.m13799p();
            r2 = android.text.TextUtils.isEmpty(r2);
            if (r2 != 0) goto L_0x07b4;
        L_0x0767:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10109e;
            r3 = 2131690020; // 0x7f0f0224 float:1.9009072E38 double:1.0531948065E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10130z;
            r3 = 0;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10109e;
            r3 = 2131690043; // 0x7f0f023b float:1.9009118E38 double:1.053194818E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.TextView) r2;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10120p;
            r4 = "attention.txt";
            r3 = org.telegram.customization.util.C2886j.m13401a(r3, r4);
            r2.setText(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10109e;
            r3 = 2131690042; // 0x7f0f023a float:1.9009116E38 double:1.0531948173E-314;
            r2 = r2.findViewById(r3);
            r3 = 0;
            r2.setVisibility(r3);
        L_0x07b4:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10109e;
            r3 = new org.telegram.news.e$7$2;
            r0 = r19;
            r3.<init>(r0);
            r2.post(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10108d;
            r2.m13869b(r3);
            return;
        L_0x07d2:
            if (r5 != 0) goto L_0x072b;
        L_0x07d4:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.ak;
            if (r2 == 0) goto L_0x072b;
        L_0x07de:
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.f10110f;
            r3 = 8;
            r2.setVisibility(r3);
            r0 = r19;
            r2 = r0.f10074a;
            r3 = 0;
            r2.ai = r3;
            r2 = new android.widget.LinearLayout$LayoutParams;
            r3 = -1;
            r4 = -2;
            r2.<init>(r3, r4);
            r3 = 0;
            r0 = r19;
            r4 = r0.f10074a;
            r4 = r4.ac;
            r4 = r4.getHeight();
            r5 = 0;
            r6 = 0;
            r2.setMargins(r3, r4, r5, r6);
            r0 = r19;
            r3 = r0.f10074a;
            r3 = r3.f10125u;
            r3.setLayoutParams(r2);
            r0 = r19;
            r2 = r0.f10074a;
            r2 = r2.ac;
            r3 = "actionBarDefault";
            r3 = org.telegram.ui.ActionBar.Theme.getColor(r3);
            r2.setBackgroundColor(r3);
            goto L_0x072b;
        L_0x0827:
            r2 = move-exception;
            goto L_0x0266;
        L_0x082a:
            r2 = move-exception;
            goto L_0x03c9;
        L_0x082d:
            r8 = r4;
            r10 = r5;
            goto L_0x0451;
        L_0x0831:
            r2 = r3;
            r3 = r4;
            goto L_0x0218;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.news.e.7.run():void");
        }
    }

    /* renamed from: org.telegram.news.e$a */
    class C3771a extends BitmapDrawable {
        /* renamed from: a */
        final /* synthetic */ C3773e f10079a;

        public C3771a(C3773e c3773e, Resources resources, Bitmap bitmap) {
            this.f10079a = c3773e;
            super(resources, bitmap);
        }

        public void draw(Canvas canvas) {
            try {
                super.draw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: org.telegram.news.e$b */
    static class C3772b extends LinkMovementMethod {
        /* renamed from: a */
        private static Context f10080a;
        /* renamed from: b */
        private static C3772b f10081b = new C3772b();

        C3772b() {
        }

        /* renamed from: a */
        public static MovementMethod m13860a(Context context) {
            f10080a = context;
            return f10081b;
        }

        /* renamed from: a */
        public boolean m13861a(String str) {
            Log.d("LEE", "PackageNameNotif123:" + str);
            Uri uri = null;
            try {
                uri = Uri.parse(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (uri == null || !uri.getScheme().equals("tag")) {
                f10080a.startActivity(new Intent("android.intent.action.VIEW", uri));
            } else if (str.contains("tag://open/img")) {
                Object queryParameter = uri.getQueryParameter(ImagesContract.URL);
                if (!(queryParameter == null || TextUtils.isEmpty(queryParameter))) {
                    String toLowerCase = queryParameter.toLowerCase();
                    if (toLowerCase.startsWith("http") && (toLowerCase.endsWith(".png") || toLowerCase.endsWith(".jpg") || toLowerCase.endsWith(".jpeg") || toLowerCase.endsWith(".gif") || toLowerCase.endsWith(".webp"))) {
                        Intent intent = new Intent(f10080a, ImageViewerActivity.class);
                        C1768f c1768f = new C1768f();
                        Object arrayList = new ArrayList();
                        arrayList.add(queryParameter);
                        intent.putExtra(TtmlNode.ATTR_ID, c1768f.m8395a(arrayList));
                        f10080a.startActivity(intent);
                    }
                }
            } else if (str.contains("tag://open/video")) {
                String str2;
                r1 = uri.getQueryParameter(ImagesContract.URL);
                if (uri.getQueryParameter("title") == null) {
                    str2 = TtmlNode.ANONYMOUS_REGION_ID;
                }
                if (!(r1 == null || TextUtils.isEmpty(r1))) {
                    str2 = r1.toLowerCase();
                    if (!str2.startsWith("http") && str2.startsWith("rtsp")) {
                    }
                }
            } else if (!str.contains("tag://open/news")) {
                r1 = uri.getQueryParameter("tagId");
                CharSequence queryParameter2 = uri.getQueryParameter("tagName");
                uri.getQueryParameter("tagColor");
                if (!(TextUtils.isEmpty(r1) || TextUtils.isEmpty(queryParameter2) || TextUtils.isEmpty(r1))) {
                    Integer.parseInt(r1);
                }
            }
            return true;
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                int x = (((int) motionEvent.getX()) - textView.getTotalPaddingLeft()) + textView.getScrollX();
                int y = (((int) motionEvent.getY()) - textView.getTotalPaddingTop()) + textView.getScrollY();
                Layout layout = textView.getLayout();
                x = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
                URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(x, x, URLSpan.class);
                if (uRLSpanArr.length != 0) {
                    return m13861a(uRLSpanArr[0].getURL());
                }
            }
            return true;
        }
    }

    public C3773e(Context context) {
        super(context);
    }

    /* renamed from: a */
    public static void m13863a(Activity activity, int i) {
        if (VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(C3792d.m14074a(i, 0.8f));
        }
    }

    /* renamed from: a */
    private void m13864a(C3744b c3744b) {
        this.ak = false;
        C2818c.m13087a(this.f10120p, (C2497d) this).m13112a((long) this.ab, c3744b.m13787h(), this.f10109e);
    }

    /* renamed from: b */
    private void m13868b() {
        this.f10118n.removeAllViews();
        if (this.f10108d.m13773c() != null) {
            Iterator it = this.f10108d.m13773c().iterator();
            while (it.hasNext()) {
                final C3744b c3744b = (C3744b) it.next();
                View inflate = this.f10084C.inflate(R.layout.small_news_item_new, this.f10118n, false);
                inflate.setBackgroundColor(C0235a.m1075c(this.f10120p, R.color.white));
                TextView textView = (TextView) inflate.findViewById(R.id.txt_source);
                TextView textView2 = (TextView) inflate.findViewById(R.id.txt_title);
                TextView textView3 = (TextView) inflate.findViewById(R.id.ftv_pic_count);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_news_image);
                ImageView imageView2 = (ImageView) inflate.findViewById(R.id.iv_agency);
                TextView textView4 = (TextView) inflate.findViewById(R.id.txt_time);
                inflate.findViewById(R.id.v_home_divider).setVisibility(0);
                inflate.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C3773e f10036b;

                    public void onClick(View view) {
                        Intent intent = new Intent(this.f10036b.f10120p, NewsDescriptionActivity.class);
                        intent.setAction(System.currentTimeMillis() + TtmlNode.ANONYMOUS_REGION_ID);
                        intent.putExtra("SPECIAL_NEWS", c3744b.m13787h());
                        intent.putExtra("EXTRA_BACK_TO_HOME", false);
                        this.f10036b.f10120p.startActivity(intent);
                    }
                });
                if (c3744b.m13797n() > 1) {
                    textView3.setVisibility(0);
                    textView3.setText(c3744b.m13797n() + TtmlNode.ANONYMOUS_REGION_ID);
                } else {
                    textView3.setVisibility(8);
                }
                C2868b.m13329a(imageView, c3744b.m13800q());
                textView2.setText(c3744b.m13789i());
                textView2.setTextColor(C0235a.m1075c(this.f10120p, R.color.black));
                textView.setText(c3744b.m13793k());
                C2868b.m13329a(imageView2, c3744b.m13785g());
                C3744b.m13759a(textView4, c3744b);
                this.f10118n.addView(inflate);
            }
        }
    }

    /* renamed from: b */
    private void m13869b(final C3744b c3744b) {
        if (c3744b == null || c3744b.m13797n() <= 1) {
            this.f10104W.setVisibility(8);
            return;
        }
        this.f10104W.setVisibility(0);
        this.f10104W.removeAllViews();
        this.f10104W.setWeightSum(100.0f);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(0, C3792d.m14073a((float) (C3792d.m14075a(getContext()) / 5), getContext()));
        layoutParams.weight = 20.0f;
        Iterator it = c3744b.m13796m().iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            int i2 = i + 1;
            LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.item_thumbnail, null);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setTag(Integer.valueOf(i2));
            C2868b.m13329a((ImageView) linearLayout.findViewById(R.id.thumbnail), str);
            this.f10104W.addView(linearLayout);
            linearLayout.setOnClickListener(new OnClickListener(this) {
                /* renamed from: b */
                final /* synthetic */ C3773e f10076b;

                public void onClick(View view) {
                    Intent intent = new Intent(this.f10076b.f10120p, ImageViewerActivity.class);
                    intent.putExtra(TtmlNode.ATTR_ID, new C1768f().m8395a(c3744b.m13796m()));
                    if (view.getTag() != null) {
                        intent.putExtra("CURRENT_PIC_ID", Integer.parseInt(view.getTag().toString()) - 1);
                    }
                    this.f10076b.f10120p.startActivity(intent);
                }
            });
            if (i2 != 4 || c3744b.m13797n() <= 5) {
                i = i2;
            } else {
                LinearLayout linearLayout2 = (LinearLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.item_thumbnail, null);
                linearLayout2.setLayoutParams(layoutParams);
                ImageView imageView = (ImageView) linearLayout2.findViewById(R.id.thumbnail);
                imageView.setPadding(20, 20, 20, 20);
                C2868b.m13329a(imageView, "drawable://2130838097");
                this.f10104W.addView(linearLayout2);
                linearLayout2.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C3773e f10078b;

                    public void onClick(View view) {
                        Intent intent = new Intent(this.f10078b.f10120p, ImageViewerActivity.class);
                        intent.putExtra(TtmlNode.ATTR_ID, new C1768f().m8395a(c3744b.m13796m()));
                        this.f10078b.f10120p.startActivity(intent);
                    }
                });
                return;
            }
        }
    }

    /* renamed from: c */
    private void m13872c() {
        this.ah = this.f10120p.getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        this.ag = getActionBarSize();
        this.f10115k.setMovementMethod(C3772b.m13860a(this.f10120p));
        this.af.setScrollViewCallbacks(this);
        this.f10121q.setTitle(null);
        C1654c.m8073a(this.af, new C37564(this));
    }

    /* renamed from: d */
    private void m13873d() {
        this.f10128x.setOnClickListener(new C37575(this));
    }

    /* renamed from: e */
    private void m13875e() {
        this.f10109e = this.f10084C.inflate(R.layout.news_page, null);
        this.f10109e.post(new C37586(this));
        this.f10101T = this.f10109e.findViewById(R.id.iv_political);
        this.f10082A = (LinearLayout) this.f10109e.findViewById(R.id.scrollContent);
        this.f10115k = (TextView) this.f10109e.findViewById(R.id.ftv_agency_name);
        this.f10116l = (TextView) this.f10109e.findViewById(R.id.ftv_news_creation_date);
        this.f10103V = (ImageView) this.f10109e.findViewById(R.id.action_back);
        this.f10103V.setOnClickListener(this);
        this.f10117m = (LinearLayout) this.f10109e.findViewById(R.id.myLL);
        this.f10118n = (LinearLayout) this.f10109e.findViewById(R.id.ll_extra_content);
        this.f10102U = (TextView) this.f10109e.findViewById(R.id.actvity_title);
        this.f10119o = (ProgressBar) this.f10109e.findViewById(R.id.pb_loading_news);
        this.f10122r = (LinearLayout) this.f10109e.findViewById(R.id.ll_tag_container);
        this.f10130z = (FarsiTextView) this.f10109e.findViewById(R.id.ftv_send_report);
        this.f10113i = (ImageView) this.f10109e.findViewById(R.id.iv_first_image);
        this.f10114j = (TextView) this.f10109e.findViewById(R.id.ftv_news_title);
        this.f10123s = (ImageView) this.f10109e.findViewById(R.id.iv_agency);
        this.ac = (Toolbar) this.f10109e.findViewById(R.id.toolbar);
        this.f10125u = this.f10109e.findViewById(R.id.rel_context);
        this.f10112h = this.f10109e.findViewById(R.id.v_news_metadata);
        this.ad = this.f10109e.findViewById(R.id.header_view);
        this.ae = this.f10109e.findViewById(R.id.overlay);
        this.af = (ObservableScrollView) this.f10109e.findViewById(R.id.scroll);
        this.f10110f = this.f10109e.findViewById(R.id.top_view);
        this.f10124t = this.f10109e.findViewById(R.id.pb_page_loading);
        this.f10111g = (CircularProgress) this.f10109e.findViewById(R.id.pb_loading);
        this.f10126v = (TextView) this.f10109e.findViewById(R.id.duration);
        this.f10127w = this.f10109e.findViewById(R.id.playBtn);
        this.f10128x = (LinearLayout) this.f10109e.findViewById(R.id.ll_error);
        this.f10089H = this.f10109e.findViewById(R.id.btn_add_comment);
        this.f10089H.setOnClickListener(this);
        this.f10087F = (LinearLayout) this.f10109e.findViewById(R.id.ll_fave);
        this.f10087F.setOnClickListener(this);
        this.f10088G = (ImageView) this.f10109e.findViewById(R.id.iv_fave);
        this.f10091J = (LinearLayout) this.f10109e.findViewById(R.id.ll_like);
        this.f10092K = (LinearLayout) this.f10109e.findViewById(R.id.ll_dislike);
        this.f10093L = (ImageView) this.f10109e.findViewById(R.id.iv_like);
        this.f10094M = (ImageView) this.f10109e.findViewById(R.id.iv_dislike);
        this.f10095N = (ImageView) this.f10109e.findViewById(R.id.btn_share);
        this.f10090I = (LinearLayout) this.f10109e.findViewById(R.id.show_more_comments);
        this.f10090I.setOnClickListener(this);
        this.f10083B = (LinearLayout) this.f10109e.findViewById(R.id.ll_share);
        this.f10083B.setOnClickListener(this);
        this.f10091J.setOnClickListener(this);
        this.f10092K.setOnClickListener(this);
        this.f10095N.setOnClickListener(this);
        this.ac.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        this.f10096O = (LinearLayout) this.f10109e.findViewById(R.id.ll_comments);
        this.f10110f.setVisibility(4);
        this.al = this.f10109e.findViewById(R.id.news_data_layout);
        this.f10097P = (LinearLayout) this.f10109e.findViewById(R.id.ll_more_info);
        this.f10097P.setOnClickListener(this);
        this.f10098Q = this.f10109e.findViewById(R.id.grid);
        this.f10099R = this.f10109e.findViewById(R.id.videoCountContainer);
        this.f10100S = this.f10109e.findViewById(R.id.picCountContainer);
        this.f10104W = (LinearLayout) this.f10109e.findViewById(R.id.ll_thumbnail_container);
    }

    /* renamed from: f */
    private void m13878f() {
        if (this.f10129y == BitmapDescriptorFactory.HUE_RED) {
            this.f10129y = C3791b.m14039s(this.f10120p);
        }
        this.f10120p.runOnUiThread(new C37687(this));
        if (this.f10108d != null) {
            if (TextUtils.isEmpty(this.f10108d.m13800q()) && this.f10108d.m13796m().size() > 0) {
                this.f10108d.m13790i((String) this.f10108d.m13796m().get(0));
            }
            this.f10108d.m13769b(System.currentTimeMillis());
        }
    }

    /* renamed from: g */
    private void m13879g() {
        Object obj;
        Exception exception;
        final Animation loadAnimation;
        final Animation loadAnimation2;
        final Animation loadAnimation3;
        final Animation loadAnimation4;
        final Animation loadAnimation5;
        final Animation loadAnimation6;
        final Animation loadAnimation7;
        final Animation loadAnimation8;
        final Animation loadAnimation9;
        final Animation loadAnimation10;
        final Animation loadAnimation11;
        final View findViewById;
        final Animation loadAnimation12;
        Object obj2 = null;
        try {
            if ((this.f10120p instanceof NewsDescriptionActivity) && ((NewsDescriptionActivity) this.f10120p).f9906c.getCurrentItem() != this.f10086E) {
                obj = 1;
                try {
                    if (this.ai) {
                        this.f10110f.setVisibility(0);
                        this.ad.setVisibility(0);
                    }
                    this.al.setVisibility(0);
                    this.f10114j.setVisibility(0);
                    this.f10109e.findViewById(R.id.devider).setVisibility(0);
                    this.f10109e.findViewById(R.id.divider2).setVisibility(0);
                    this.f10109e.findViewById(R.id.ll_show_source).setVisibility(0);
                    this.f10109e.findViewById(R.id.ll_tag_container).setVisibility(0);
                    this.f10122r.setVisibility(0);
                    this.f10117m.setVisibility(0);
                    this.f10109e.findViewById(R.id.ll_buttons).setVisibility(0);
                    this.f10109e.findViewById(R.id.ll_comments).setVisibility(0);
                    this.f10109e.findViewById(R.id.ll_btn_add_commnet).setVisibility(0);
                    this.f10109e.findViewById(R.id.report_container).setVisibility(0);
                    this.f10118n.setVisibility(0);
                } catch (Exception e) {
                    Exception exception2 = e;
                    int i = 1;
                    exception = exception2;
                    exception.printStackTrace();
                    obj = obj2;
                    if (obj == null) {
                        if (this.ai) {
                            loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                            loadAnimation.setAnimationListener(new C3775g(this.f10110f));
                            loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                            loadAnimation2.setAnimationListener(new C3775g(this.ad));
                            this.ad.postDelayed(new Runnable(this) {
                                /* renamed from: c */
                                final /* synthetic */ C3773e f10031c;

                                public void run() {
                                    this.f10031c.f10110f.startAnimation(loadAnimation);
                                    this.f10031c.ad.startAnimation(loadAnimation2);
                                }
                            }, 100);
                        }
                        loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation.setAnimationListener(new C3775g(this.al));
                        loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation2.setAnimationListener(new C3775g(this.f10114j));
                        this.f10114j.postDelayed(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ C3773e f10034c;

                            public void run() {
                                this.f10034c.al.startAnimation(loadAnimation);
                                this.f10034c.f10114j.startAnimation(loadAnimation2);
                            }
                        }, 200);
                        loadAnimation3 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation3.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.devider)));
                        loadAnimation4 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation4.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.divider2)));
                        loadAnimation5 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation5.setAnimationListener(new C3775g(this.f10117m));
                        loadAnimation6 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation6.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_show_source)));
                        loadAnimation7 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation7.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_tag_container)));
                        loadAnimation8 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation8.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_buttons)));
                        loadAnimation9 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation9.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_comments)));
                        loadAnimation10 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation10.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_btn_add_commnet)));
                        loadAnimation11 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation11.setAnimationListener(new C3775g(this.f10118n));
                        findViewById = this.f10109e.findViewById(R.id.report_container);
                        loadAnimation12 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation12.setAnimationListener(new C3775g(findViewById));
                        findViewById.postDelayed(new Runnable(this) {
                            /* renamed from: l */
                            final /* synthetic */ C3773e f10048l;

                            public void run() {
                                this.f10048l.f10109e.findViewById(R.id.devider).startAnimation(loadAnimation3);
                                this.f10048l.f10109e.findViewById(R.id.divider2).startAnimation(loadAnimation4);
                                this.f10048l.f10117m.startAnimation(loadAnimation5);
                                this.f10048l.f10109e.findViewById(R.id.ll_show_source).startAnimation(loadAnimation6);
                                this.f10048l.f10109e.findViewById(R.id.ll_tag_container).startAnimation(loadAnimation7);
                                this.f10048l.f10109e.findViewById(R.id.ll_buttons).startAnimation(loadAnimation8);
                                this.f10048l.f10109e.findViewById(R.id.ll_comments).startAnimation(loadAnimation9);
                                this.f10048l.f10109e.findViewById(R.id.ll_btn_add_commnet).startAnimation(loadAnimation10);
                                this.f10048l.f10118n.startAnimation(loadAnimation11);
                                findViewById.startAnimation(loadAnimation12);
                            }
                        }, 300);
                    }
                }
                if (obj == null) {
                    if (this.ai) {
                        loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation.setAnimationListener(new C3775g(this.f10110f));
                        loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                        loadAnimation2.setAnimationListener(new C3775g(this.ad));
                        this.ad.postDelayed(/* anonymous class already generated */, 100);
                    }
                    loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation.setAnimationListener(new C3775g(this.al));
                    loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation2.setAnimationListener(new C3775g(this.f10114j));
                    this.f10114j.postDelayed(/* anonymous class already generated */, 200);
                    loadAnimation3 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation3.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.devider)));
                    loadAnimation4 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation4.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.divider2)));
                    loadAnimation5 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation5.setAnimationListener(new C3775g(this.f10117m));
                    loadAnimation6 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation6.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_show_source)));
                    loadAnimation7 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation7.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_tag_container)));
                    loadAnimation8 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation8.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_buttons)));
                    loadAnimation9 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation9.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_comments)));
                    loadAnimation10 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation10.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_btn_add_commnet)));
                    loadAnimation11 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation11.setAnimationListener(new C3775g(this.f10118n));
                    findViewById = this.f10109e.findViewById(R.id.report_container);
                    loadAnimation12 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation12.setAnimationListener(new C3775g(findViewById));
                    findViewById.postDelayed(/* anonymous class already generated */, 300);
                }
            }
        } catch (Exception e2) {
            exception = e2;
            exception.printStackTrace();
            obj = obj2;
            if (obj == null) {
                if (this.ai) {
                    loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation.setAnimationListener(new C3775g(this.f10110f));
                    loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                    loadAnimation2.setAnimationListener(new C3775g(this.ad));
                    this.ad.postDelayed(/* anonymous class already generated */, 100);
                }
                loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation.setAnimationListener(new C3775g(this.al));
                loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation2.setAnimationListener(new C3775g(this.f10114j));
                this.f10114j.postDelayed(/* anonymous class already generated */, 200);
                loadAnimation3 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation3.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.devider)));
                loadAnimation4 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation4.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.divider2)));
                loadAnimation5 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation5.setAnimationListener(new C3775g(this.f10117m));
                loadAnimation6 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation6.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_show_source)));
                loadAnimation7 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation7.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_tag_container)));
                loadAnimation8 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation8.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_buttons)));
                loadAnimation9 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation9.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_comments)));
                loadAnimation10 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation10.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_btn_add_commnet)));
                loadAnimation11 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation11.setAnimationListener(new C3775g(this.f10118n));
                findViewById = this.f10109e.findViewById(R.id.report_container);
                loadAnimation12 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation12.setAnimationListener(new C3775g(findViewById));
                findViewById.postDelayed(/* anonymous class already generated */, 300);
            }
        }
        obj = obj2;
        if (obj == null) {
            if (this.ai) {
                loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation.setAnimationListener(new C3775g(this.f10110f));
                loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
                loadAnimation2.setAnimationListener(new C3775g(this.ad));
                this.ad.postDelayed(/* anonymous class already generated */, 100);
            }
            loadAnimation = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation.setAnimationListener(new C3775g(this.al));
            loadAnimation2 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation2.setAnimationListener(new C3775g(this.f10114j));
            this.f10114j.postDelayed(/* anonymous class already generated */, 200);
            loadAnimation3 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation3.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.devider)));
            loadAnimation4 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation4.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.divider2)));
            loadAnimation5 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation5.setAnimationListener(new C3775g(this.f10117m));
            loadAnimation6 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation6.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_show_source)));
            loadAnimation7 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation7.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_tag_container)));
            loadAnimation8 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation8.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_buttons)));
            loadAnimation9 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation9.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_comments)));
            loadAnimation10 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation10.setAnimationListener(new C3775g(this.f10109e.findViewById(R.id.ll_btn_add_commnet)));
            loadAnimation11 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation11.setAnimationListener(new C3775g(this.f10118n));
            findViewById = this.f10109e.findViewById(R.id.report_container);
            loadAnimation12 = AnimationUtils.loadAnimation(this.f10120p, R.anim.fadein);
            loadAnimation12.setAnimationListener(new C3775g(findViewById));
            findViewById.postDelayed(/* anonymous class already generated */, 300);
        }
    }

    /* renamed from: h */
    private boolean m13882h() {
        return this.af.canScrollVertically(this.af.getCurrentScrollY() + 1);
    }

    /* renamed from: a */
    public void mo4196a() {
    }

    /* renamed from: a */
    public void mo4195a(float f) {
        this.f10129y = f;
        if (this.f10085D != null) {
            Iterator it = this.f10085D.iterator();
            while (it.hasNext()) {
                try {
                    ((TextView) ((WeakReference) it.next()).get()).setTextSize(2, f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: a */
    public void mo4197a(int i, boolean z, boolean z2) {
        float f = (float) (this.ah - this.ag);
        int height = this.ag - this.ae.getHeight();
        if (this.ai) {
            height = Math.abs(this.af.getChildAt(0).getHeight() - C3792d.m14084b(this.f10120p));
            if (height == 0) {
                height = 1;
            }
            if ((height < this.ah ? this.ah / height : 1) < 1) {
            }
            if (!m13882h()) {
                height = (int) f;
            }
            if (!m13882h()) {
            }
        }
    }

    /* renamed from: a */
    public void m13888a(Activity activity, LayoutInflater layoutInflater, C3744b c3744b, ViewGroup viewGroup, int i, int i2) {
        this.f10120p = activity;
        C3773e.m13863a(this.f10120p, Theme.getColor(Theme.key_actionBarDefault));
        this.f10121q = activity;
        this.f10084C = layoutInflater;
        this.ab = i;
        this.f10107c = c3744b;
        m13875e();
        m13873d();
        m13872c();
        removeAllViews();
        addView(this.f10109e);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        m13864a(c3744b);
    }

    /* renamed from: a */
    public void mo4198a(C1652b c1652b) {
    }

    /* renamed from: a */
    public void m13890a(C3744b c3744b, int i, int i2) {
        this.f10086E = i2;
        this.aa = false;
        this.af.scrollTo(0, this.ah - this.ag);
        this.af.scrollTo(0, 1);
        this.af.scrollTo(0, 0);
        this.f10110f.setVisibility(4);
        this.al.setVisibility(4);
        this.f10114j.setVisibility(4);
        this.f10109e.findViewById(R.id.devider).setVisibility(4);
        this.f10109e.findViewById(R.id.divider2).setVisibility(4);
        this.f10117m.setVisibility(4);
        this.f10109e.findViewById(R.id.ll_show_source).setVisibility(4);
        this.f10109e.findViewById(R.id.ll_tag_container).setVisibility(4);
        this.f10109e.findViewById(R.id.ll_buttons).setVisibility(4);
        this.f10109e.findViewById(R.id.ll_btn_add_commnet).setVisibility(4);
        this.f10109e.findViewById(R.id.ll_comments).setVisibility(4);
        this.f10104W.setVisibility(8);
        this.f10118n.setVisibility(4);
        this.f10109e.findViewById(R.id.report_container).setVisibility(4);
        try {
            this.f10113i.setImageBitmap(null);
            this.f10113i.setImageDrawable(null);
        } catch (Exception e) {
        }
        this.ad.setVisibility(4);
        this.f10124t.setVisibility(0);
        this.ak = false;
        this.ab = i;
        this.f10107c = c3744b;
        m13864a(c3744b);
    }

    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int getActionBarSize() {
        int[] iArr = new int[]{R.attr.actionBarSize};
        TypedArray obtainStyledAttributes = this.f10120p.obtainStyledAttributes(new TypedValue().data, iArr);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        try {
            obtainStyledAttributes.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dimensionPixelSize;
    }

    public Drawable getDrawable(String str) {
        Drawable levelListDrawable = new LevelListDrawable();
        Drawable drawable = this.f10120p.getResources().getDrawable(R.drawable.ic_launcher);
        levelListDrawable.addLevel(0, 0, drawable);
        levelListDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return levelListDrawable;
    }

    public C1570c getOptions() {
        if (this.aj == null) {
            this.aj = new C1566a().m7732a((int) R.drawable.transparent).m7738b((int) R.drawable.transparent).m7740c((int) R.drawable.transparent).m7736a(false).m7739b(true).m7741c(true).m7733a(Config.RGB_565).m7737a();
        }
        return this.aj;
    }

    public View getView() {
        return this.f10109e;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_comment:
                if (this.f10108d != null && TextUtils.isEmpty(this.f10108d.m13787h())) {
                    return;
                }
                return;
            case R.id.action_back:
                try {
                    this.f10120p.onBackPressed();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.btn_share:
                this.f10120p.startActivity(C3774f.m13891a(this.f10108d.m13789i(), this.f10108d.m13798o(), this.f10108d.m13799p(), true));
                return;
            default:
                return;
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public void onPageSelected(int i) {
    }

    public void onResult(final Object obj, int i) {
        Log.d("alireza", "alireza call webservice onresult111" + i);
        switch (i) {
            case -12:
                this.ak = true;
                this.f10119o.setVisibility(8);
                this.f10124t.setVisibility(8);
                this.f10128x.setVisibility(0);
                return;
            case 12:
                this.ak = true;
                Log.d("alireza", "alireza call webservice onresult");
                new Thread(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C3773e f10050b;

                    public void run() {
                        try {
                            Object obj = (C3744b) ((Object[]) obj)[1];
                            Log.d("LEE", "lllliio:2" + obj.m13789i() + "newsId: " + obj.m13787h() + obj.m13800q());
                            this.f10050b.f10108d.m13775c(obj.m13787h());
                            this.f10050b.f10108d.m13778d(obj.m13789i());
                            this.f10050b.f10108d.m13786g(obj.m13798o());
                            this.f10050b.f10108d.m13792j(obj.m13802s());
                            this.f10050b.f10108d.m13776c(obj.m13801r());
                            this.f10050b.f10108d.m13771b(null);
                            this.f10050b.f10108d.m13790i(obj.m13800q());
                            this.f10050b.f10108d.m13770b(obj.m13785g());
                            this.f10050b.f10108d.m13781e(obj.m13793k());
                            this.f10050b.f10108d.m13762a(obj.m13791j());
                            this.f10050b.f10108d.m13788h(obj.m13799p());
                            this.f10050b.f10108d.m13774c(obj.m13797n());
                            this.f10050b.f10108d.m13777d(this.f10050b.f10107c.m13803t());
                            this.f10050b.f10108d.m13784f(this.f10050b.f10107c.m13795l());
                            this.f10050b.f10108d.m13765a(obj.m13804u());
                            this.f10050b.f10108d.m13768b(obj.m13783f());
                            this.f10050b.f10108d.m13772b(obj.m13782e());
                            this.f10050b.f10108d.m13766a(obj.m13779d());
                            this.f10050b.f10108d.m13794k(new C1768f().m8395a(obj));
                            this.f10050b.f10108d.m13764a(obj.m13773c());
                            this.f10050b.f10108d.m13761a(obj.m13767b());
                            this.f10050b.f10108d.m13780e(obj.m13805v());
                            this.f10050b.f10108d.m13763a(obj.m13760a());
                            this.f10050b.m13878f();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return;
            default:
                return;
        }
    }

    public void setPos(int i) {
        this.f10086E = i;
    }
}
