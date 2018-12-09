package com.persianswitch.sdk.base.widgets;

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
import com.persianswitch.sdk.C2262R;
import org.telegram.ui.ActionBar.Theme;

public class CircleImageView extends ImageView {
    /* renamed from: a */
    private static final ScaleType f7308a = ScaleType.CENTER_CROP;
    /* renamed from: b */
    private static final Config f7309b = Config.ARGB_8888;
    /* renamed from: c */
    private final RectF f7310c;
    /* renamed from: d */
    private final RectF f7311d;
    /* renamed from: e */
    private final Matrix f7312e;
    /* renamed from: f */
    private final Paint f7313f;
    /* renamed from: g */
    private final Paint f7314g;
    /* renamed from: h */
    private final Paint f7315h;
    /* renamed from: i */
    private int f7316i;
    /* renamed from: j */
    private int f7317j;
    /* renamed from: k */
    private int f7318k;
    /* renamed from: l */
    private Bitmap f7319l;
    /* renamed from: m */
    private BitmapShader f7320m;
    /* renamed from: n */
    private int f7321n;
    /* renamed from: o */
    private int f7322o;
    /* renamed from: p */
    private float f7323p;
    /* renamed from: q */
    private float f7324q;
    /* renamed from: r */
    private ColorFilter f7325r;
    /* renamed from: s */
    private boolean f7326s;
    /* renamed from: t */
    private boolean f7327t;
    /* renamed from: u */
    private boolean f7328u;
    /* renamed from: v */
    private boolean f7329v;

    public CircleImageView(Context context) {
        super(context);
        this.f7310c = new RectF();
        this.f7311d = new RectF();
        this.f7312e = new Matrix();
        this.f7313f = new Paint();
        this.f7314g = new Paint();
        this.f7315h = new Paint();
        this.f7316i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.f7317j = 0;
        this.f7318k = 0;
        m10990a();
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f7310c = new RectF();
        this.f7311d = new RectF();
        this.f7312e = new Matrix();
        this.f7313f = new Paint();
        this.f7314g = new Paint();
        this.f7315h = new Paint();
        this.f7316i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.f7317j = 0;
        this.f7318k = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2262R.styleable.asanpardakht_CircleImageView, i, 0);
        this.f7317j = obtainStyledAttributes.getDimensionPixelSize(C2262R.styleable.asanpardakht_CircleImageView_asanpardakht_civ_border_width, 0);
        this.f7316i = obtainStyledAttributes.getColor(C2262R.styleable.asanpardakht_CircleImageView_asanpardakht_civ_border_color, Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.f7328u = obtainStyledAttributes.getBoolean(C2262R.styleable.asanpardakht_CircleImageView_asanpardakht_civ_border_overlay, false);
        this.f7318k = obtainStyledAttributes.getColor(C2262R.styleable.asanpardakht_CircleImageView_asanpardakht_civ_fill_color, 0);
        obtainStyledAttributes.recycle();
        m10990a();
    }

    /* renamed from: a */
    private Bitmap m10989a(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap createBitmap = drawable instanceof ColorDrawable ? Bitmap.createBitmap(2, 2, f7309b) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), f7309b);
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
    private void m10990a() {
        super.setScaleType(f7308a);
        this.f7326s = true;
        if (this.f7327t) {
            m10993d();
            this.f7327t = false;
        }
    }

    /* renamed from: b */
    private void m10991b() {
        if (this.f7313f != null) {
            this.f7313f.setColorFilter(this.f7325r);
        }
    }

    /* renamed from: c */
    private void m10992c() {
        if (this.f7329v) {
            this.f7319l = null;
        } else {
            this.f7319l = m10989a(getDrawable());
        }
        m10993d();
    }

    /* renamed from: d */
    private void m10993d() {
        if (!this.f7326s) {
            this.f7327t = true;
        } else if (getWidth() != 0 || getHeight() != 0) {
            if (this.f7319l == null) {
                invalidate();
                return;
            }
            this.f7320m = new BitmapShader(this.f7319l, TileMode.CLAMP, TileMode.CLAMP);
            this.f7313f.setAntiAlias(true);
            this.f7313f.setShader(this.f7320m);
            this.f7314g.setStyle(Style.STROKE);
            this.f7314g.setAntiAlias(true);
            this.f7314g.setColor(this.f7316i);
            this.f7314g.setStrokeWidth((float) this.f7317j);
            this.f7315h.setStyle(Style.FILL);
            this.f7315h.setAntiAlias(true);
            this.f7315h.setColor(this.f7318k);
            this.f7322o = this.f7319l.getHeight();
            this.f7321n = this.f7319l.getWidth();
            this.f7311d.set(m10994e());
            this.f7324q = Math.min((this.f7311d.height() - ((float) this.f7317j)) / 2.0f, (this.f7311d.width() - ((float) this.f7317j)) / 2.0f);
            this.f7310c.set(this.f7311d);
            if (!this.f7328u && this.f7317j > 0) {
                this.f7310c.inset(((float) this.f7317j) - 1.0f, ((float) this.f7317j) - 1.0f);
            }
            this.f7323p = Math.min(this.f7310c.height() / 2.0f, this.f7310c.width() / 2.0f);
            m10991b();
            m10995f();
            invalidate();
        }
    }

    /* renamed from: e */
    private RectF m10994e() {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        int min = Math.min(width, height);
        float paddingLeft = (((float) (width - min)) / 2.0f) + ((float) getPaddingLeft());
        float paddingTop = (((float) (height - min)) / 2.0f) + ((float) getPaddingTop());
        return new RectF(paddingLeft, paddingTop, ((float) min) + paddingLeft, ((float) min) + paddingTop);
    }

    /* renamed from: f */
    private void m10995f() {
        float height;
        float width;
        float f = BitmapDescriptorFactory.HUE_RED;
        this.f7312e.set(null);
        if (((float) this.f7321n) * this.f7310c.height() > this.f7310c.width() * ((float) this.f7322o)) {
            height = this.f7310c.height() / ((float) this.f7322o);
            width = (this.f7310c.width() - (((float) this.f7321n) * height)) * 0.5f;
        } else {
            height = this.f7310c.width() / ((float) this.f7321n);
            float height2 = (this.f7310c.height() - (((float) this.f7322o) * height)) * 0.5f;
            width = BitmapDescriptorFactory.HUE_RED;
            f = height2;
        }
        this.f7312e.setScale(height, height);
        this.f7312e.postTranslate(((float) ((int) (width + 0.5f))) + this.f7310c.left, ((float) ((int) (f + 0.5f))) + this.f7310c.top);
        this.f7320m.setLocalMatrix(this.f7312e);
    }

    public int getBorderColor() {
        return this.f7316i;
    }

    public int getBorderWidth() {
        return this.f7317j;
    }

    public ColorFilter getColorFilter() {
        return this.f7325r;
    }

    @Deprecated
    public int getFillColor() {
        return this.f7318k;
    }

    public ScaleType getScaleType() {
        return f7308a;
    }

    protected void onDraw(Canvas canvas) {
        if (this.f7329v) {
            super.onDraw(canvas);
        } else if (this.f7319l != null) {
            if (this.f7318k != 0) {
                canvas.drawCircle(this.f7310c.centerX(), this.f7310c.centerY(), this.f7323p, this.f7315h);
            }
            canvas.drawCircle(this.f7310c.centerX(), this.f7310c.centerY(), this.f7323p, this.f7313f);
            if (this.f7317j > 0) {
                canvas.drawCircle(this.f7311d.centerX(), this.f7311d.centerY(), this.f7324q, this.f7314g);
            }
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        m10993d();
    }

    public void setAdjustViewBounds(boolean z) {
        if (z) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setBorderColor(int i) {
        if (i != this.f7316i) {
            this.f7316i = i;
            this.f7314g.setColor(this.f7316i);
            invalidate();
        }
    }

    @Deprecated
    public void setBorderColorResource(int i) {
        setBorderColor(getContext().getResources().getColor(i));
    }

    public void setBorderOverlay(boolean z) {
        if (z != this.f7328u) {
            this.f7328u = z;
            m10993d();
        }
    }

    public void setBorderWidth(int i) {
        if (i != this.f7317j) {
            this.f7317j = i;
            m10993d();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter != this.f7325r) {
            this.f7325r = colorFilter;
            m10991b();
            invalidate();
        }
    }

    public void setDisableCircularTransformation(boolean z) {
        if (this.f7329v != z) {
            this.f7329v = z;
            m10992c();
        }
    }

    @Deprecated
    public void setFillColor(int i) {
        if (i != this.f7318k) {
            this.f7318k = i;
            this.f7315h.setColor(i);
            invalidate();
        }
    }

    @Deprecated
    public void setFillColorResource(int i) {
        setFillColor(getContext().getResources().getColor(i));
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        m10992c();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        m10992c();
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        m10992c();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        m10992c();
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != f7308a) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", new Object[]{scaleType}));
        }
    }
}
