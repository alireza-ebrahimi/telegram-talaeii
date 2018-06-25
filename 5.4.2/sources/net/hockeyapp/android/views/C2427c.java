package net.hockeyapp.android.views;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.widget.ImageView;
import java.util.Iterator;
import java.util.Stack;
import net.hockeyapp.android.p137e.C2400d;

@SuppressLint({"ViewConstructor"})
/* renamed from: net.hockeyapp.android.views.c */
public class C2427c extends ImageView {
    /* renamed from: a */
    private Path f8139a = new Path();
    /* renamed from: b */
    private Stack<Path> f8140b = new Stack();
    /* renamed from: c */
    private Paint f8141c = new Paint();
    /* renamed from: d */
    private float f8142d;
    /* renamed from: e */
    private float f8143e;

    /* renamed from: net.hockeyapp.android.views.c$1 */
    class C24261 extends AsyncTask<Object, Void, Bitmap> {
        /* renamed from: a */
        final /* synthetic */ C2427c f8138a;

        C24261(C2427c c2427c) {
            this.f8138a = c2427c;
        }

        /* renamed from: a */
        protected Bitmap m11931a(Object... objArr) {
            try {
                return C2427c.m11937b(((Context) objArr[0]).getContentResolver(), (Uri) objArr[1], ((Integer) objArr[2]).intValue(), ((Integer) objArr[3]).intValue());
            } catch (Throwable e) {
                C2400d.m11843a("Could not load image into ImageView.", e);
                return null;
            }
        }

        /* renamed from: a */
        protected void m11932a(Bitmap bitmap) {
            if (bitmap != null) {
                this.f8138a.setImageBitmap(bitmap);
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m11931a(objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            m11932a((Bitmap) obj);
        }

        protected void onPreExecute() {
            this.f8138a.setAdjustViewBounds(true);
        }
    }

    public C2427c(Context context, Uri uri, int i, int i2) {
        super(context);
        this.f8141c.setAntiAlias(true);
        this.f8141c.setDither(true);
        this.f8141c.setColor(-65536);
        this.f8141c.setStyle(Style.STROKE);
        this.f8141c.setStrokeJoin(Join.ROUND);
        this.f8141c.setStrokeCap(Cap.ROUND);
        this.f8141c.setStrokeWidth(12.0f);
        new C24261(this).execute(new Object[]{context, uri, Integer.valueOf(i), Integer.valueOf(i2)});
    }

    /* renamed from: a */
    public static int m11933a(ContentResolver contentResolver, Uri uri) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
            return ((float) options.outWidth) / ((float) options.outHeight) > 1.0f ? 0 : 1;
        } catch (Throwable e) {
            C2400d.m11843a("Unable to determine necessary screen orientation.", e);
            return 1;
        }
    }

    /* renamed from: a */
    private static int m11934a(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 > i2 && i4 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    /* renamed from: a */
    private void m11936a(float f, float f2) {
        this.f8139a.reset();
        this.f8139a.moveTo(f, f2);
        this.f8142d = f;
        this.f8143e = f2;
    }

    /* renamed from: b */
    private static Bitmap m11937b(ContentResolver contentResolver, Uri uri, int i, int i2) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
        options.inSampleSize = C2427c.m11934a(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
    }

    /* renamed from: b */
    private void m11938b(float f, float f2) {
        float abs = Math.abs(f - this.f8142d);
        float abs2 = Math.abs(f2 - this.f8143e);
        if (abs >= 4.0f || abs2 >= 4.0f) {
            this.f8139a.quadTo(this.f8142d, this.f8143e, (this.f8142d + f) / 2.0f, (this.f8143e + f2) / 2.0f);
            this.f8142d = f;
            this.f8143e = f2;
        }
    }

    /* renamed from: d */
    private void m11939d() {
        this.f8139a.lineTo(this.f8142d, this.f8143e);
        this.f8140b.push(this.f8139a);
        this.f8139a = new Path();
    }

    /* renamed from: a */
    public void m11940a() {
        this.f8140b.clear();
        invalidate();
    }

    /* renamed from: b */
    public void m11941b() {
        if (!this.f8140b.empty()) {
            this.f8140b.pop();
            invalidate();
        }
    }

    /* renamed from: c */
    public boolean m11942c() {
        return this.f8140b.empty();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator it = this.f8140b.iterator();
        while (it.hasNext()) {
            canvas.drawPath((Path) it.next(), this.f8141c);
        }
        canvas.drawPath(this.f8139a, this.f8141c);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                m11936a(x, y);
                invalidate();
                break;
            case 1:
                m11939d();
                invalidate();
                break;
            case 2:
                m11938b(x, y);
                invalidate();
                break;
        }
        return true;
    }
}
