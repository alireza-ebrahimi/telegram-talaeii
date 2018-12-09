package de.hdodenhof.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import de.hdodenhof.circleimageview.C2345a.C2344a;
import org.telegram.ui.ActionBar.Theme;

public class CircleImageView extends ImageView {
    /* renamed from: a */
    private static final ScaleType f7866a = ScaleType.CENTER_CROP;
    /* renamed from: b */
    private static final Config f7867b = Config.ARGB_8888;
    /* renamed from: c */
    private final RectF f7868c;
    /* renamed from: d */
    private final RectF f7869d;
    /* renamed from: e */
    private final Matrix f7870e;
    /* renamed from: f */
    private final Paint f7871f;
    /* renamed from: g */
    private final Paint f7872g;
    /* renamed from: h */
    private final Paint f7873h;
    /* renamed from: i */
    private int f7874i;
    /* renamed from: j */
    private int f7875j;
    /* renamed from: k */
    private int f7876k;
    /* renamed from: l */
    private Bitmap f7877l;
    /* renamed from: m */
    private BitmapShader f7878m;
    /* renamed from: n */
    private int f7879n;
    /* renamed from: o */
    private int f7880o;
    /* renamed from: p */
    private float f7881p;
    /* renamed from: q */
    private float f7882q;
    /* renamed from: r */
    private ColorFilter f7883r;
    /* renamed from: s */
    private boolean f7884s;
    /* renamed from: t */
    private boolean f7885t;
    /* renamed from: u */
    private boolean f7886u;

    public CircleImageView(Context context) {
        super(context);
        this.f7868c = new RectF();
        this.f7869d = new RectF();
        this.f7870e = new Matrix();
        this.f7871f = new Paint();
        this.f7872g = new Paint();
        this.f7873h = new Paint();
        this.f7874i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.f7875j = 0;
        this.f7876k = 0;
        m11655a();
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f7868c = new RectF();
        this.f7869d = new RectF();
        this.f7870e = new Matrix();
        this.f7871f = new Paint();
        this.f7872g = new Paint();
        this.f7873h = new Paint();
        this.f7874i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.f7875j = 0;
        this.f7876k = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2344a.CircleImageView, i, 0);
        this.f7875j = obtainStyledAttributes.getDimensionPixelSize(C2344a.CircleImageView_civ_border_width, 0);
        this.f7874i = obtainStyledAttributes.getColor(C2344a.CircleImageView_civ_border_color, Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.f7886u = obtainStyledAttributes.getBoolean(C2344a.CircleImageView_civ_border_overlay, false);
        this.f7876k = obtainStyledAttributes.getColor(C2344a.CircleImageView_civ_fill_color, 0);
        obtainStyledAttributes.recycle();
        m11655a();
    }

    /* renamed from: a */
    private Bitmap m11654a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap createBitmap = drawable instanceof ColorDrawable ? Bitmap.createBitmap(2, 2, f7867b) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), f7867b);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return createBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private void m11655a() {
        super.setScaleType(f7866a);
        this.f7884s = true;
        if (this.f7885t) {
            m11656b();
            this.f7885t = false;
        }
    }

    /* renamed from: b */
    private void m11656b() {
        if (!this.f7884s) {
            this.f7885t = true;
        } else if (getWidth() != 0 || getHeight() != 0) {
            if (this.f7877l == null) {
                invalidate();
                return;
            }
            this.f7878m = new BitmapShader(this.f7877l, TileMode.CLAMP, TileMode.CLAMP);
            this.f7871f.setAntiAlias(true);
            this.f7871f.setShader(this.f7878m);
            this.f7872g.setStyle(Style.STROKE);
            this.f7872g.setAntiAlias(true);
            this.f7872g.setColor(this.f7874i);
            this.f7872g.setStrokeWidth((float) this.f7875j);
            this.f7873h.setStyle(Style.FILL);
            this.f7873h.setAntiAlias(true);
            this.f7873h.setColor(this.f7876k);
            this.f7880o = this.f7877l.getHeight();
            this.f7879n = this.f7877l.getWidth();
            this.f7869d.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight());
            this.f7882q = Math.min((this.f7869d.height() - ((float) this.f7875j)) / 2.0f, (this.f7869d.width() - ((float) this.f7875j)) / 2.0f);
            this.f7868c.set(this.f7869d);
            if (!this.f7886u) {
                this.f7868c.inset((float) this.f7875j, (float) this.f7875j);
            }
            this.f7881p = Math.min(this.f7868c.height() / 2.0f, this.f7868c.width() / 2.0f);
            m11657c();
            invalidate();
        }
    }

    /* renamed from: c */
    private void m11657c() {
        float height;
        float width;
        float f = BitmapDescriptorFactory.HUE_RED;
        this.f7870e.set(null);
        if (((float) this.f7879n) * this.f7868c.height() > this.f7868c.width() * ((float) this.f7880o)) {
            height = this.f7868c.height() / ((float) this.f7880o);
            width = (this.f7868c.width() - (((float) this.f7879n) * height)) * 0.5f;
        } else {
            height = this.f7868c.width() / ((float) this.f7879n);
            float height2 = (this.f7868c.height() - (((float) this.f7880o) * height)) * 0.5f;
            width = BitmapDescriptorFactory.HUE_RED;
            f = height2;
        }
        this.f7870e.setScale(height, height);
        this.f7870e.postTranslate(((float) ((int) (width + 0.5f))) + this.f7868c.left, ((float) ((int) (f + 0.5f))) + this.f7868c.top);
        this.f7878m.setLocalMatrix(this.f7870e);
    }

    public int getBorderColor() {
        return this.f7874i;
    }

    public int getBorderWidth() {
        return this.f7875j;
    }

    public int getFillColor() {
        return this.f7876k;
    }

    public ScaleType getScaleType() {
        return f7866a;
    }

    protected void onDraw(Canvas canvas) {
        if (this.f7877l != null) {
            if (this.f7876k != 0) {
                canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, this.f7881p, this.f7873h);
            }
            canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, this.f7881p, this.f7871f);
            if (this.f7875j != 0) {
                canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, this.f7882q, this.f7872g);
            }
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        m11656b();
    }

    public void setAdjustViewBounds(boolean z) {
        if (z) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setBorderColor(int i) {
        if (i != this.f7874i) {
            this.f7874i = i;
            this.f7872g.setColor(this.f7874i);
            invalidate();
        }
    }

    public void setBorderColorResource(int i) {
        setBorderColor(getContext().getResources().getColor(i));
    }

    public void setBorderOverlay(boolean z) {
        if (z != this.f7886u) {
            this.f7886u = z;
            m11656b();
        }
    }

    public void setBorderWidth(int i) {
        if (i != this.f7875j) {
            this.f7875j = i;
            m11656b();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter != this.f7883r) {
            this.f7883r = colorFilter;
            this.f7871f.setColorFilter(this.f7883r);
            invalidate();
        }
    }

    public void setFillColor(int i) {
        if (i != this.f7876k) {
            this.f7876k = i;
            this.f7873h.setColor(i);
            invalidate();
        }
    }

    public void setFillColorResource(int i) {
        setFillColor(getContext().getResources().getColor(i));
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.f7877l = bitmap;
        m11656b();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        this.f7877l = m11654a(drawable);
        m11656b();
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        this.f7877l = m11654a(getDrawable());
        m11656b();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.f7877l = uri != null ? m11654a(getDrawable()) : null;
        m11656b();
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != f7866a) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", new Object[]{scaleType}));
        }
    }
}
