package net.hockeyapp.android.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.File;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.C2417f.C2413a;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p135c.C2372c;
import net.hockeyapp.android.p137e.C2402f;

@SuppressLint({"ViewConstructor"})
/* renamed from: net.hockeyapp.android.views.a */
public class C2424a extends FrameLayout {
    /* renamed from: a */
    private final Context f8117a;
    /* renamed from: b */
    private final ViewGroup f8118b;
    /* renamed from: c */
    private final C2372c f8119c;
    /* renamed from: d */
    private final Uri f8120d;
    /* renamed from: e */
    private final String f8121e;
    /* renamed from: f */
    private ImageView f8122f;
    /* renamed from: g */
    private TextView f8123g;
    /* renamed from: h */
    private int f8124h;
    /* renamed from: i */
    private int f8125i;
    /* renamed from: j */
    private int f8126j;
    /* renamed from: k */
    private int f8127k;
    /* renamed from: l */
    private int f8128l;
    /* renamed from: m */
    private int f8129m;

    /* renamed from: net.hockeyapp.android.views.a$1 */
    class C24201 extends AsyncTask<Void, Void, Bitmap> {
        /* renamed from: a */
        final /* synthetic */ C2424a f8111a;

        C24201(C2424a c2424a) {
            this.f8111a = c2424a;
        }

        /* renamed from: a */
        protected Bitmap m11915a(Void... voidArr) {
            return this.f8111a.m11927c();
        }

        /* renamed from: a */
        protected void m11916a(Bitmap bitmap) {
            if (bitmap != null) {
                this.f8111a.m11921a(bitmap, false);
            } else {
                this.f8111a.m11924a(false);
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m11915a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            m11916a((Bitmap) obj);
        }
    }

    /* renamed from: net.hockeyapp.android.views.a$2 */
    class C24212 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2424a f8112a;

        C24212(C2424a c2424a) {
            this.f8112a = c2424a;
        }

        public void onClick(View view) {
            this.f8112a.m11928a();
        }
    }

    public C2424a(Context context, ViewGroup viewGroup, Uri uri, boolean z) {
        super(context);
        this.f8117a = context;
        this.f8118b = viewGroup;
        this.f8119c = null;
        this.f8120d = uri;
        this.f8121e = uri.getLastPathSegment();
        m11919a(20);
        m11920a(context, z);
        this.f8123g.setText(this.f8121e);
        new C24201(this).execute(new Void[0]);
    }

    public C2424a(Context context, ViewGroup viewGroup, C2372c c2372c, boolean z) {
        super(context);
        this.f8117a = context;
        this.f8118b = viewGroup;
        this.f8119c = c2372c;
        this.f8120d = Uri.fromFile(new File(C2367a.m11717a(), c2372c.m11743c()));
        this.f8121e = c2372c.m11737a();
        m11919a(30);
        m11920a(context, z);
        this.f8129m = 0;
        this.f8123g.setText(C2416d.hockeyapp_feedback_attachment_loading);
        m11924a(false);
    }

    /* renamed from: a */
    private Drawable m11918a(String str) {
        return VERSION.SDK_INT >= 21 ? getResources().getDrawable(getResources().getIdentifier(str, "drawable", "android"), this.f8117a.getTheme()) : getResources().getDrawable(getResources().getIdentifier(str, "drawable", "android"));
    }

    /* renamed from: a */
    private void m11919a(int i) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.f8128l = Math.round(TypedValue.applyDimension(1, 10.0f, displayMetrics));
        int round = Math.round(TypedValue.applyDimension(1, (float) i, displayMetrics));
        int i2 = displayMetrics.widthPixels;
        int i3 = (i2 - (round * 2)) - (this.f8128l * 2);
        i2 = (i2 - (round * 2)) - this.f8128l;
        this.f8124h = i3 / 3;
        this.f8126j = i2 / 2;
        this.f8125i = this.f8124h * 2;
        this.f8127k = this.f8126j;
    }

    /* renamed from: a */
    private void m11920a(Context context, boolean z) {
        setLayoutParams(new LayoutParams(-2, -2, 80));
        setPadding(0, this.f8128l, 0, 0);
        this.f8122f = new ImageView(context);
        View linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LayoutParams(-1, -2, 80));
        linearLayout.setGravity(8388611);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(Color.parseColor("#80262626"));
        this.f8123g = new TextView(context);
        this.f8123g.setLayoutParams(new LayoutParams(-1, -2, 17));
        this.f8123g.setGravity(17);
        this.f8123g.setTextColor(context.getResources().getColor(C2413a.hockeyapp_text_white));
        this.f8123g.setSingleLine();
        this.f8123g.setEllipsize(TruncateAt.MIDDLE);
        if (z) {
            View imageButton = new ImageButton(context);
            imageButton.setLayoutParams(new LayoutParams(-1, -2, 80));
            imageButton.setAdjustViewBounds(true);
            imageButton.setImageDrawable(m11918a("ic_menu_delete"));
            imageButton.setBackgroundResource(0);
            imageButton.setOnClickListener(new C24212(this));
            linearLayout.addView(imageButton);
        }
        linearLayout.addView(this.f8123g);
        addView(this.f8122f);
        addView(linearLayout);
    }

    /* renamed from: a */
    private void m11921a(Bitmap bitmap, final boolean z) {
        int i = this.f8129m == 1 ? this.f8126j : this.f8124h;
        int i2 = this.f8129m == 1 ? this.f8127k : this.f8125i;
        this.f8123g.setMaxWidth(i);
        this.f8123g.setMinWidth(i);
        this.f8122f.setLayoutParams(new LayoutParams(-2, -2));
        this.f8122f.setAdjustViewBounds(true);
        this.f8122f.setMinimumWidth(i);
        this.f8122f.setMaxWidth(i);
        this.f8122f.setMaxHeight(i2);
        this.f8122f.setScaleType(ScaleType.CENTER_INSIDE);
        this.f8122f.setImageBitmap(bitmap);
        this.f8122f.setOnClickListener(new OnClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2424a f8114b;

            public void onClick(View view) {
                if (z) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setDataAndType(this.f8114b.f8120d, "image/*");
                    this.f8114b.f8117a.startActivity(intent);
                }
            }
        });
    }

    /* renamed from: a */
    private void m11924a(final boolean z) {
        this.f8123g.setMaxWidth(this.f8124h);
        this.f8123g.setMinWidth(this.f8124h);
        this.f8122f.setLayoutParams(new LayoutParams(-2, -2));
        this.f8122f.setAdjustViewBounds(false);
        this.f8122f.setBackgroundColor(Color.parseColor("#eeeeee"));
        this.f8122f.setMinimumHeight((int) (((float) this.f8124h) * 1.2f));
        this.f8122f.setMinimumWidth(this.f8124h);
        this.f8122f.setScaleType(ScaleType.FIT_CENTER);
        this.f8122f.setImageDrawable(m11918a("ic_menu_attachment"));
        this.f8122f.setOnClickListener(new OnClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2424a f8116b;

            public void onClick(View view) {
                if (z) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setDataAndType(this.f8116b.f8120d, "*/*");
                    this.f8116b.f8117a.startActivity(intent);
                }
            }
        });
    }

    /* renamed from: c */
    private Bitmap m11927c() {
        try {
            this.f8129m = C2402f.m11858a(this.f8117a, this.f8120d);
            return C2402f.m11862a(this.f8117a, this.f8120d, this.f8129m == 1 ? this.f8126j : this.f8124h, this.f8129m == 1 ? this.f8127k : this.f8125i);
        } catch (Throwable th) {
            return null;
        }
    }

    /* renamed from: a */
    public void m11928a() {
        this.f8118b.removeView(this);
    }

    /* renamed from: a */
    public void m11929a(Bitmap bitmap, int i) {
        this.f8123g.setText(this.f8121e);
        this.f8129m = i;
        if (bitmap == null) {
            m11924a(true);
        } else {
            m11921a(bitmap, true);
        }
    }

    /* renamed from: b */
    public void m11930b() {
        this.f8123g.setText(C2416d.hockeyapp_feedback_attachment_error);
    }

    public C2372c getAttachment() {
        return this.f8119c;
    }

    public Uri getAttachmentUri() {
        return this.f8120d;
    }

    public int getEffectiveMaxHeight() {
        return this.f8129m == 1 ? this.f8127k : this.f8125i;
    }

    public int getGap() {
        return this.f8128l;
    }

    public int getMaxHeightLandscape() {
        return this.f8127k;
    }

    public int getMaxHeightPortrait() {
        return this.f8125i;
    }

    public int getWidthLandscape() {
        return this.f8126j;
    }

    public int getWidthPortrait() {
        return this.f8124h;
    }
}
