package android.support.p005a.p006a;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.p005a.p006a.C0011d.C0010b;
import android.support.v4.content.p020a.C0405d;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.p022f.C0464a;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: android.support.a.a.g */
public class C0020g extends C0007f {
    /* renamed from: a */
    static final Mode f82a = Mode.SRC_IN;
    /* renamed from: c */
    private C0018f f83c;
    /* renamed from: d */
    private PorterDuffColorFilter f84d;
    /* renamed from: e */
    private ColorFilter f85e;
    /* renamed from: f */
    private boolean f86f;
    /* renamed from: g */
    private boolean f87g;
    /* renamed from: h */
    private ConstantState f88h;
    /* renamed from: i */
    private final float[] f89i;
    /* renamed from: j */
    private final Matrix f90j;
    /* renamed from: k */
    private final Rect f91k;

    /* renamed from: android.support.a.a.g$d */
    private static class C0013d {
        /* renamed from: m */
        protected C0010b[] f24m = null;
        /* renamed from: n */
        String f25n;
        /* renamed from: o */
        int f26o;

        public C0013d(C0013d c0013d) {
            this.f25n = c0013d.f25n;
            this.f26o = c0013d.f26o;
            this.f24m = C0011d.m18a(c0013d.f24m);
        }

        /* renamed from: a */
        public void m26a(Path path) {
            path.reset();
            if (this.f24m != null) {
                C0010b.m11a(this.f24m, path);
            }
        }

        /* renamed from: a */
        public boolean mo22a() {
            return false;
        }

        public C0010b[] getPathData() {
            return this.f24m;
        }

        public String getPathName() {
            return this.f25n;
        }

        public void setPathData(C0010b[] c0010bArr) {
            if (C0011d.m15a(this.f24m, c0010bArr)) {
                C0011d.m19b(this.f24m, c0010bArr);
            } else {
                this.f24m = C0011d.m18a(c0010bArr);
            }
        }
    }

    /* renamed from: android.support.a.a.g$a */
    private static class C0014a extends C0013d {
        public C0014a(C0014a c0014a) {
            super(c0014a);
        }

        /* renamed from: a */
        private void m28a(TypedArray typedArray) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.n = string;
            }
            string = typedArray.getString(1);
            if (string != null) {
                this.m = C0011d.m17a(string);
            }
        }

        /* renamed from: a */
        public void m29a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            if (C0012e.m24a(xmlPullParser, "pathData")) {
                TypedArray a = C0007f.m4a(resources, theme, attributeSet, C0001a.f3d);
                m28a(a);
                a.recycle();
            }
        }

        /* renamed from: a */
        public boolean mo22a() {
            return true;
        }
    }

    /* renamed from: android.support.a.a.g$b */
    private static class C0015b extends C0013d {
        /* renamed from: a */
        int f27a = 0;
        /* renamed from: b */
        float f28b = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: c */
        int f29c = 0;
        /* renamed from: d */
        float f30d = 1.0f;
        /* renamed from: e */
        int f31e;
        /* renamed from: f */
        float f32f = 1.0f;
        /* renamed from: g */
        float f33g = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: h */
        float f34h = 1.0f;
        /* renamed from: i */
        float f35i = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: j */
        Cap f36j = Cap.BUTT;
        /* renamed from: k */
        Join f37k = Join.MITER;
        /* renamed from: l */
        float f38l = 4.0f;
        /* renamed from: p */
        private int[] f39p;

        public C0015b(C0015b c0015b) {
            super(c0015b);
            this.f39p = c0015b.f39p;
            this.f27a = c0015b.f27a;
            this.f28b = c0015b.f28b;
            this.f30d = c0015b.f30d;
            this.f29c = c0015b.f29c;
            this.f31e = c0015b.f31e;
            this.f32f = c0015b.f32f;
            this.f33g = c0015b.f33g;
            this.f34h = c0015b.f34h;
            this.f35i = c0015b.f35i;
            this.f36j = c0015b.f36j;
            this.f37k = c0015b.f37k;
            this.f38l = c0015b.f38l;
        }

        /* renamed from: a */
        private Cap m31a(int i, Cap cap) {
            switch (i) {
                case 0:
                    return Cap.BUTT;
                case 1:
                    return Cap.ROUND;
                case 2:
                    return Cap.SQUARE;
                default:
                    return cap;
            }
        }

        /* renamed from: a */
        private Join m32a(int i, Join join) {
            switch (i) {
                case 0:
                    return Join.MITER;
                case 1:
                    return Join.ROUND;
                case 2:
                    return Join.BEVEL;
                default:
                    return join;
            }
        }

        /* renamed from: a */
        private void m33a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.f39p = null;
            if (C0012e.m24a(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.n = string;
                }
                string = typedArray.getString(2);
                if (string != null) {
                    this.m = C0011d.m17a(string);
                }
                this.f29c = C0012e.m25b(typedArray, xmlPullParser, "fillColor", 1, this.f29c);
                this.f32f = C0012e.m21a(typedArray, xmlPullParser, "fillAlpha", 12, this.f32f);
                this.f36j = m31a(C0012e.m22a(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.f36j);
                this.f37k = m32a(C0012e.m22a(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.f37k);
                this.f38l = C0012e.m21a(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.f38l);
                this.f27a = C0012e.m25b(typedArray, xmlPullParser, "strokeColor", 3, this.f27a);
                this.f30d = C0012e.m21a(typedArray, xmlPullParser, "strokeAlpha", 11, this.f30d);
                this.f28b = C0012e.m21a(typedArray, xmlPullParser, "strokeWidth", 4, this.f28b);
                this.f34h = C0012e.m21a(typedArray, xmlPullParser, "trimPathEnd", 6, this.f34h);
                this.f35i = C0012e.m21a(typedArray, xmlPullParser, "trimPathOffset", 7, this.f35i);
                this.f33g = C0012e.m21a(typedArray, xmlPullParser, "trimPathStart", 5, this.f33g);
            }
        }

        /* renamed from: a */
        public void m34a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a = C0007f.m4a(resources, theme, attributeSet, C0001a.f2c);
            m33a(a, xmlPullParser);
            a.recycle();
        }

        float getFillAlpha() {
            return this.f32f;
        }

        int getFillColor() {
            return this.f29c;
        }

        float getStrokeAlpha() {
            return this.f30d;
        }

        int getStrokeColor() {
            return this.f27a;
        }

        float getStrokeWidth() {
            return this.f28b;
        }

        float getTrimPathEnd() {
            return this.f34h;
        }

        float getTrimPathOffset() {
            return this.f35i;
        }

        float getTrimPathStart() {
            return this.f33g;
        }

        void setFillAlpha(float f) {
            this.f32f = f;
        }

        void setFillColor(int i) {
            this.f29c = i;
        }

        void setStrokeAlpha(float f) {
            this.f30d = f;
        }

        void setStrokeColor(int i) {
            this.f27a = i;
        }

        void setStrokeWidth(float f) {
            this.f28b = f;
        }

        void setTrimPathEnd(float f) {
            this.f34h = f;
        }

        void setTrimPathOffset(float f) {
            this.f35i = f;
        }

        void setTrimPathStart(float f) {
            this.f33g = f;
        }
    }

    /* renamed from: android.support.a.a.g$c */
    private static class C0016c {
        /* renamed from: a */
        final ArrayList<Object> f40a = new ArrayList();
        /* renamed from: b */
        float f41b = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: c */
        int f42c;
        /* renamed from: d */
        private final Matrix f43d = new Matrix();
        /* renamed from: e */
        private float f44e = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: f */
        private float f45f = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: g */
        private float f46g = 1.0f;
        /* renamed from: h */
        private float f47h = 1.0f;
        /* renamed from: i */
        private float f48i = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: j */
        private float f49j = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: k */
        private final Matrix f50k = new Matrix();
        /* renamed from: l */
        private int[] f51l;
        /* renamed from: m */
        private String f52m = null;

        public C0016c(C0016c c0016c, C0464a<String, Object> c0464a) {
            this.f41b = c0016c.f41b;
            this.f44e = c0016c.f44e;
            this.f45f = c0016c.f45f;
            this.f46g = c0016c.f46g;
            this.f47h = c0016c.f47h;
            this.f48i = c0016c.f48i;
            this.f49j = c0016c.f49j;
            this.f51l = c0016c.f51l;
            this.f52m = c0016c.f52m;
            this.f42c = c0016c.f42c;
            if (this.f52m != null) {
                c0464a.put(this.f52m, this);
            }
            this.f50k.set(c0016c.f50k);
            ArrayList arrayList = c0016c.f40a;
            for (int i = 0; i < arrayList.size(); i++) {
                Object obj = arrayList.get(i);
                if (obj instanceof C0016c) {
                    this.f40a.add(new C0016c((C0016c) obj, c0464a));
                } else {
                    C0013d c0015b;
                    if (obj instanceof C0015b) {
                        c0015b = new C0015b((C0015b) obj);
                    } else if (obj instanceof C0014a) {
                        c0015b = new C0014a((C0014a) obj);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.f40a.add(c0015b);
                    if (c0015b.f25n != null) {
                        c0464a.put(c0015b.f25n, c0015b);
                    }
                }
            }
        }

        /* renamed from: a */
        private void m36a() {
            this.f50k.reset();
            this.f50k.postTranslate(-this.f44e, -this.f45f);
            this.f50k.postScale(this.f46g, this.f47h);
            this.f50k.postRotate(this.f41b, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
            this.f50k.postTranslate(this.f48i + this.f44e, this.f49j + this.f45f);
        }

        /* renamed from: a */
        private void m37a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.f51l = null;
            this.f41b = C0012e.m21a(typedArray, xmlPullParser, "rotation", 5, this.f41b);
            this.f44e = typedArray.getFloat(1, this.f44e);
            this.f45f = typedArray.getFloat(2, this.f45f);
            this.f46g = C0012e.m21a(typedArray, xmlPullParser, "scaleX", 3, this.f46g);
            this.f47h = C0012e.m21a(typedArray, xmlPullParser, "scaleY", 4, this.f47h);
            this.f48i = C0012e.m21a(typedArray, xmlPullParser, "translateX", 6, this.f48i);
            this.f49j = C0012e.m21a(typedArray, xmlPullParser, "translateY", 7, this.f49j);
            String string = typedArray.getString(0);
            if (string != null) {
                this.f52m = string;
            }
            m36a();
        }

        /* renamed from: a */
        public void m39a(Resources resources, AttributeSet attributeSet, Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a = C0007f.m4a(resources, theme, attributeSet, C0001a.f1b);
            m37a(a, xmlPullParser);
            a.recycle();
        }

        public String getGroupName() {
            return this.f52m;
        }

        public Matrix getLocalMatrix() {
            return this.f50k;
        }

        public float getPivotX() {
            return this.f44e;
        }

        public float getPivotY() {
            return this.f45f;
        }

        public float getRotation() {
            return this.f41b;
        }

        public float getScaleX() {
            return this.f46g;
        }

        public float getScaleY() {
            return this.f47h;
        }

        public float getTranslateX() {
            return this.f48i;
        }

        public float getTranslateY() {
            return this.f49j;
        }

        public void setPivotX(float f) {
            if (f != this.f44e) {
                this.f44e = f;
                m36a();
            }
        }

        public void setPivotY(float f) {
            if (f != this.f45f) {
                this.f45f = f;
                m36a();
            }
        }

        public void setRotation(float f) {
            if (f != this.f41b) {
                this.f41b = f;
                m36a();
            }
        }

        public void setScaleX(float f) {
            if (f != this.f46g) {
                this.f46g = f;
                m36a();
            }
        }

        public void setScaleY(float f) {
            if (f != this.f47h) {
                this.f47h = f;
                m36a();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.f48i) {
                this.f48i = f;
                m36a();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.f49j) {
                this.f49j = f;
                m36a();
            }
        }
    }

    /* renamed from: android.support.a.a.g$e */
    private static class C0017e {
        /* renamed from: k */
        private static final Matrix f53k = new Matrix();
        /* renamed from: a */
        final C0016c f54a;
        /* renamed from: b */
        float f55b;
        /* renamed from: c */
        float f56c;
        /* renamed from: d */
        float f57d;
        /* renamed from: e */
        float f58e;
        /* renamed from: f */
        int f59f;
        /* renamed from: g */
        String f60g;
        /* renamed from: h */
        final C0464a<String, Object> f61h;
        /* renamed from: i */
        private final Path f62i;
        /* renamed from: j */
        private final Path f63j;
        /* renamed from: l */
        private final Matrix f64l;
        /* renamed from: m */
        private Paint f65m;
        /* renamed from: n */
        private Paint f66n;
        /* renamed from: o */
        private PathMeasure f67o;
        /* renamed from: p */
        private int f68p;

        public C0017e() {
            this.f64l = new Matrix();
            this.f55b = BitmapDescriptorFactory.HUE_RED;
            this.f56c = BitmapDescriptorFactory.HUE_RED;
            this.f57d = BitmapDescriptorFactory.HUE_RED;
            this.f58e = BitmapDescriptorFactory.HUE_RED;
            this.f59f = 255;
            this.f60g = null;
            this.f61h = new C0464a();
            this.f54a = new C0016c();
            this.f62i = new Path();
            this.f63j = new Path();
        }

        public C0017e(C0017e c0017e) {
            this.f64l = new Matrix();
            this.f55b = BitmapDescriptorFactory.HUE_RED;
            this.f56c = BitmapDescriptorFactory.HUE_RED;
            this.f57d = BitmapDescriptorFactory.HUE_RED;
            this.f58e = BitmapDescriptorFactory.HUE_RED;
            this.f59f = 255;
            this.f60g = null;
            this.f61h = new C0464a();
            this.f54a = new C0016c(c0017e.f54a, this.f61h);
            this.f62i = new Path(c0017e.f62i);
            this.f63j = new Path(c0017e.f63j);
            this.f55b = c0017e.f55b;
            this.f56c = c0017e.f56c;
            this.f57d = c0017e.f57d;
            this.f58e = c0017e.f58e;
            this.f68p = c0017e.f68p;
            this.f59f = c0017e.f59f;
            this.f60g = c0017e.f60g;
            if (c0017e.f60g != null) {
                this.f61h.put(c0017e.f60g, this);
            }
        }

        /* renamed from: a */
        private static float m40a(float f, float f2, float f3, float f4) {
            return (f * f4) - (f2 * f3);
        }

        /* renamed from: a */
        private float m41a(Matrix matrix) {
            float[] fArr = new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f, 1.0f, BitmapDescriptorFactory.HUE_RED};
            matrix.mapVectors(fArr);
            float hypot = (float) Math.hypot((double) fArr[0], (double) fArr[1]);
            float hypot2 = (float) Math.hypot((double) fArr[2], (double) fArr[3]);
            float a = C0017e.m40a(fArr[0], fArr[1], fArr[2], fArr[3]);
            hypot = Math.max(hypot, hypot2);
            return hypot > BitmapDescriptorFactory.HUE_RED ? Math.abs(a) / hypot : BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: a */
        private void m44a(C0016c c0016c, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            c0016c.f43d.set(matrix);
            c0016c.f43d.preConcat(c0016c.f50k);
            canvas.save();
            for (int i3 = 0; i3 < c0016c.f40a.size(); i3++) {
                Object obj = c0016c.f40a.get(i3);
                if (obj instanceof C0016c) {
                    m44a((C0016c) obj, c0016c.f43d, canvas, i, i2, colorFilter);
                } else if (obj instanceof C0013d) {
                    m45a(c0016c, (C0013d) obj, canvas, i, i2, colorFilter);
                }
            }
            canvas.restore();
        }

        /* renamed from: a */
        private void m45a(C0016c c0016c, C0013d c0013d, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            float f = ((float) i) / this.f57d;
            float f2 = ((float) i2) / this.f58e;
            float min = Math.min(f, f2);
            Matrix a = c0016c.f43d;
            this.f64l.set(a);
            this.f64l.postScale(f, f2);
            f = m41a(a);
            if (f != BitmapDescriptorFactory.HUE_RED) {
                c0013d.m26a(this.f62i);
                Path path = this.f62i;
                this.f63j.reset();
                if (c0013d.mo22a()) {
                    this.f63j.addPath(path, this.f64l);
                    canvas.clipPath(this.f63j);
                    return;
                }
                Paint paint;
                C0015b c0015b = (C0015b) c0013d;
                if (!(c0015b.f33g == BitmapDescriptorFactory.HUE_RED && c0015b.f34h == 1.0f)) {
                    float f3 = (c0015b.f33g + c0015b.f35i) % 1.0f;
                    float f4 = (c0015b.f34h + c0015b.f35i) % 1.0f;
                    if (this.f67o == null) {
                        this.f67o = new PathMeasure();
                    }
                    this.f67o.setPath(this.f62i, false);
                    float length = this.f67o.getLength();
                    f3 *= length;
                    f4 *= length;
                    path.reset();
                    if (f3 > f4) {
                        this.f67o.getSegment(f3, length, path, true);
                        this.f67o.getSegment(BitmapDescriptorFactory.HUE_RED, f4, path, true);
                    } else {
                        this.f67o.getSegment(f3, f4, path, true);
                    }
                    path.rLineTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                }
                this.f63j.addPath(path, this.f64l);
                if (c0015b.f29c != 0) {
                    if (this.f66n == null) {
                        this.f66n = new Paint();
                        this.f66n.setStyle(Style.FILL);
                        this.f66n.setAntiAlias(true);
                    }
                    paint = this.f66n;
                    paint.setColor(C0020g.m57a(c0015b.f29c, c0015b.f32f));
                    paint.setColorFilter(colorFilter);
                    canvas.drawPath(this.f63j, paint);
                }
                if (c0015b.f27a != 0) {
                    if (this.f65m == null) {
                        this.f65m = new Paint();
                        this.f65m.setStyle(Style.STROKE);
                        this.f65m.setAntiAlias(true);
                    }
                    paint = this.f65m;
                    if (c0015b.f37k != null) {
                        paint.setStrokeJoin(c0015b.f37k);
                    }
                    if (c0015b.f36j != null) {
                        paint.setStrokeCap(c0015b.f36j);
                    }
                    paint.setStrokeMiter(c0015b.f38l);
                    paint.setColor(C0020g.m57a(c0015b.f27a, c0015b.f30d));
                    paint.setColorFilter(colorFilter);
                    paint.setStrokeWidth((f * min) * c0015b.f28b);
                    canvas.drawPath(this.f63j, paint);
                }
            }
        }

        /* renamed from: a */
        public void m48a(Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            m44a(this.f54a, f53k, canvas, i, i2, colorFilter);
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public int getRootAlpha() {
            return this.f59f;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (255.0f * f));
        }

        public void setRootAlpha(int i) {
            this.f59f = i;
        }
    }

    /* renamed from: android.support.a.a.g$f */
    private static class C0018f extends ConstantState {
        /* renamed from: a */
        int f69a;
        /* renamed from: b */
        C0017e f70b;
        /* renamed from: c */
        ColorStateList f71c;
        /* renamed from: d */
        Mode f72d;
        /* renamed from: e */
        boolean f73e;
        /* renamed from: f */
        Bitmap f74f;
        /* renamed from: g */
        ColorStateList f75g;
        /* renamed from: h */
        Mode f76h;
        /* renamed from: i */
        int f77i;
        /* renamed from: j */
        boolean f78j;
        /* renamed from: k */
        boolean f79k;
        /* renamed from: l */
        Paint f80l;

        public C0018f() {
            this.f71c = null;
            this.f72d = C0020g.f82a;
            this.f70b = new C0017e();
        }

        public C0018f(C0018f c0018f) {
            this.f71c = null;
            this.f72d = C0020g.f82a;
            if (c0018f != null) {
                this.f69a = c0018f.f69a;
                this.f70b = new C0017e(c0018f.f70b);
                if (c0018f.f70b.f66n != null) {
                    this.f70b.f66n = new Paint(c0018f.f70b.f66n);
                }
                if (c0018f.f70b.f65m != null) {
                    this.f70b.f65m = new Paint(c0018f.f70b.f65m);
                }
                this.f71c = c0018f.f71c;
                this.f72d = c0018f.f72d;
                this.f73e = c0018f.f73e;
            }
        }

        /* renamed from: a */
        public Paint m49a(ColorFilter colorFilter) {
            if (!m52a() && colorFilter == null) {
                return null;
            }
            if (this.f80l == null) {
                this.f80l = new Paint();
                this.f80l.setFilterBitmap(true);
            }
            this.f80l.setAlpha(this.f70b.getRootAlpha());
            this.f80l.setColorFilter(colorFilter);
            return this.f80l;
        }

        /* renamed from: a */
        public void m50a(int i, int i2) {
            this.f74f.eraseColor(0);
            this.f70b.m48a(new Canvas(this.f74f), i, i2, null);
        }

        /* renamed from: a */
        public void m51a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f74f, null, rect, m49a(colorFilter));
        }

        /* renamed from: a */
        public boolean m52a() {
            return this.f70b.getRootAlpha() < 255;
        }

        /* renamed from: b */
        public void m53b(int i, int i2) {
            if (this.f74f == null || !m56c(i, i2)) {
                this.f74f = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
                this.f79k = true;
            }
        }

        /* renamed from: b */
        public boolean m54b() {
            return !this.f79k && this.f75g == this.f71c && this.f76h == this.f72d && this.f78j == this.f73e && this.f77i == this.f70b.getRootAlpha();
        }

        /* renamed from: c */
        public void m55c() {
            this.f75g = this.f71c;
            this.f76h = this.f72d;
            this.f77i = this.f70b.getRootAlpha();
            this.f78j = this.f73e;
            this.f79k = false;
        }

        /* renamed from: c */
        public boolean m56c(int i, int i2) {
            return i == this.f74f.getWidth() && i2 == this.f74f.getHeight();
        }

        public int getChangingConfigurations() {
            return this.f69a;
        }

        public Drawable newDrawable() {
            return new C0020g(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new C0020g(this);
        }
    }

    /* renamed from: android.support.a.a.g$g */
    private static class C0019g extends ConstantState {
        /* renamed from: a */
        private final ConstantState f81a;

        public C0019g(ConstantState constantState) {
            this.f81a = constantState;
        }

        public boolean canApplyTheme() {
            return this.f81a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.f81a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            Drawable c0020g = new C0020g();
            c0020g.b = (VectorDrawable) this.f81a.newDrawable();
            return c0020g;
        }

        public Drawable newDrawable(Resources resources) {
            Drawable c0020g = new C0020g();
            c0020g.b = (VectorDrawable) this.f81a.newDrawable(resources);
            return c0020g;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            Drawable c0020g = new C0020g();
            c0020g.b = (VectorDrawable) this.f81a.newDrawable(resources, theme);
            return c0020g;
        }
    }

    C0020g() {
        this.f87g = true;
        this.f89i = new float[9];
        this.f90j = new Matrix();
        this.f91k = new Rect();
        this.f83c = new C0018f();
    }

    C0020g(C0018f c0018f) {
        this.f87g = true;
        this.f89i = new float[9];
        this.f90j = new Matrix();
        this.f91k = new Rect();
        this.f83c = c0018f;
        this.f84d = m64a(this.f84d, c0018f.f71c, c0018f.f72d);
    }

    /* renamed from: a */
    static int m57a(int i, float f) {
        return (((int) (((float) Color.alpha(i)) * f)) << 24) | (16777215 & i);
    }

    /* renamed from: a */
    private static Mode m58a(int i, Mode mode) {
        switch (i) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return VERSION.SDK_INT >= 11 ? Mode.ADD : mode;
            default:
                return mode;
        }
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    public static C0020g m59a(Resources resources, int i, Theme theme) {
        if (VERSION.SDK_INT >= 24) {
            C0020g c0020g = new C0020g();
            c0020g.b = C0405d.m1869a(resources, i, theme);
            c0020g.f88h = new C0019g(c0020g.b.getConstantState());
            return c0020g;
        }
        try {
            int next;
            XmlPullParser xml = resources.getXml(i);
            AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return C0020g.m60a(resources, xml, asAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (Throwable e) {
            Log.e("VectorDrawableCompat", "parser error", e);
            return null;
        } catch (Throwable e2) {
            Log.e("VectorDrawableCompat", "parser error", e2);
            return null;
        }
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    public static C0020g m60a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        C0020g c0020g = new C0020g();
        c0020g.inflate(resources, xmlPullParser, attributeSet, theme);
        return c0020g;
    }

    /* renamed from: a */
    private void m61a(TypedArray typedArray, XmlPullParser xmlPullParser) {
        C0018f c0018f = this.f83c;
        C0017e c0017e = c0018f.f70b;
        c0018f.f72d = C0020g.m58a(C0012e.m22a(typedArray, xmlPullParser, "tintMode", 6, -1), Mode.SRC_IN);
        ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            c0018f.f71c = colorStateList;
        }
        c0018f.f73e = C0012e.m23a(typedArray, xmlPullParser, "autoMirrored", 5, c0018f.f73e);
        c0017e.f57d = C0012e.m21a(typedArray, xmlPullParser, "viewportWidth", 7, c0017e.f57d);
        c0017e.f58e = C0012e.m21a(typedArray, xmlPullParser, "viewportHeight", 8, c0017e.f58e);
        if (c0017e.f57d <= BitmapDescriptorFactory.HUE_RED) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (c0017e.f58e <= BitmapDescriptorFactory.HUE_RED) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        } else {
            c0017e.f55b = typedArray.getDimension(3, c0017e.f55b);
            c0017e.f56c = typedArray.getDimension(2, c0017e.f56c);
            if (c0017e.f55b <= BitmapDescriptorFactory.HUE_RED) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (c0017e.f56c <= BitmapDescriptorFactory.HUE_RED) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
            } else {
                c0017e.setAlpha(C0012e.m21a(typedArray, xmlPullParser, "alpha", 4, c0017e.getAlpha()));
                String string = typedArray.getString(0);
                if (string != null) {
                    c0017e.f60g = string;
                    c0017e.f61h.put(string, c0017e);
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    private boolean m62a() {
        boolean z = true;
        if (VERSION.SDK_INT < 17) {
            return false;
        }
        if (!(isAutoMirrored() && getLayoutDirection() == 1)) {
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    private void m63b(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        C0018f c0018f = this.f83c;
        C0017e c0017e = c0018f.f70b;
        Stack stack = new Stack();
        stack.push(c0017e.f54a);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        Object obj = 1;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                Object obj2;
                String name = xmlPullParser.getName();
                C0016c c0016c = (C0016c) stack.peek();
                if ("path".equals(name)) {
                    C0015b c0015b = new C0015b();
                    c0015b.m34a(resources, attributeSet, theme, xmlPullParser);
                    c0016c.f40a.add(c0015b);
                    if (c0015b.getPathName() != null) {
                        c0017e.f61h.put(c0015b.getPathName(), c0015b);
                    }
                    obj2 = null;
                    c0018f.f69a = c0015b.o | c0018f.f69a;
                } else if ("clip-path".equals(name)) {
                    C0014a c0014a = new C0014a();
                    c0014a.m29a(resources, attributeSet, theme, xmlPullParser);
                    c0016c.f40a.add(c0014a);
                    if (c0014a.getPathName() != null) {
                        c0017e.f61h.put(c0014a.getPathName(), c0014a);
                    }
                    c0018f.f69a |= c0014a.o;
                    obj2 = obj;
                } else {
                    if ("group".equals(name)) {
                        C0016c c0016c2 = new C0016c();
                        c0016c2.m39a(resources, attributeSet, theme, xmlPullParser);
                        c0016c.f40a.add(c0016c2);
                        stack.push(c0016c2);
                        if (c0016c2.getGroupName() != null) {
                            c0017e.f61h.put(c0016c2.getGroupName(), c0016c2);
                        }
                        c0018f.f69a |= c0016c2.f42c;
                    }
                    obj2 = obj;
                }
                obj = obj2;
            } else if (eventType == 3) {
                if ("group".equals(xmlPullParser.getName())) {
                    stack.pop();
                }
            }
            eventType = xmlPullParser.next();
        }
        if (obj != null) {
            StringBuffer stringBuffer = new StringBuffer();
            if (stringBuffer.length() > 0) {
                stringBuffer.append(" or ");
            }
            stringBuffer.append("path");
            throw new XmlPullParserException("no " + stringBuffer + " defined");
        }
    }

    /* renamed from: a */
    PorterDuffColorFilter m64a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, Mode mode) {
        return (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    /* renamed from: a */
    Object m65a(String str) {
        return this.f83c.f70b.f61h.get(str);
    }

    /* renamed from: a */
    void m66a(boolean z) {
        this.f87g = z;
    }

    public /* bridge */ /* synthetic */ void applyTheme(Theme theme) {
        super.applyTheme(theme);
    }

    public boolean canApplyTheme() {
        if (this.b != null) {
            C0375a.m1781d(this.b);
        }
        return false;
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public void draw(Canvas canvas) {
        if (this.b != null) {
            this.b.draw(canvas);
            return;
        }
        copyBounds(this.f91k);
        if (this.f91k.width() > 0 && this.f91k.height() > 0) {
            ColorFilter colorFilter = this.f85e == null ? this.f84d : this.f85e;
            canvas.getMatrix(this.f90j);
            this.f90j.getValues(this.f89i);
            float abs = Math.abs(this.f89i[0]);
            float abs2 = Math.abs(this.f89i[4]);
            float abs3 = Math.abs(this.f89i[1]);
            float abs4 = Math.abs(this.f89i[3]);
            if (!(abs3 == BitmapDescriptorFactory.HUE_RED && abs4 == BitmapDescriptorFactory.HUE_RED)) {
                abs2 = 1.0f;
                abs = 1.0f;
            }
            int height = (int) (abs2 * ((float) this.f91k.height()));
            int min = Math.min(2048, (int) (abs * ((float) this.f91k.width())));
            height = Math.min(2048, height);
            if (min > 0 && height > 0) {
                int save = canvas.save();
                canvas.translate((float) this.f91k.left, (float) this.f91k.top);
                if (m62a()) {
                    canvas.translate((float) this.f91k.width(), BitmapDescriptorFactory.HUE_RED);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.f91k.offsetTo(0, 0);
                this.f83c.m53b(min, height);
                if (!this.f87g) {
                    this.f83c.m50a(min, height);
                } else if (!this.f83c.m54b()) {
                    this.f83c.m50a(min, height);
                    this.f83c.m55c();
                }
                this.f83c.m51a(canvas, colorFilter, this.f91k);
                canvas.restoreToCount(save);
            }
        }
    }

    public int getAlpha() {
        return this.b != null ? C0375a.m1780c(this.b) : this.f83c.f70b.getRootAlpha();
    }

    public int getChangingConfigurations() {
        return this.b != null ? this.b.getChangingConfigurations() : super.getChangingConfigurations() | this.f83c.getChangingConfigurations();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public ConstantState getConstantState() {
        if (this.b != null) {
            return new C0019g(this.b.getConstantState());
        }
        this.f83c.f69a = getChangingConfigurations();
        return this.f83c;
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.b != null ? this.b.getIntrinsicHeight() : (int) this.f83c.f70b.f56c;
    }

    public int getIntrinsicWidth() {
        return this.b != null ? this.b.getIntrinsicWidth() : (int) this.f83c.f70b.f55b;
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public int getOpacity() {
        return this.b != null ? this.b.getOpacity() : -3;
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @SuppressLint({"NewApi"})
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if (this.b != null) {
            this.b.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        if (this.b != null) {
            C0375a.m1775a(this.b, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        C0018f c0018f = this.f83c;
        c0018f.f70b = new C0017e();
        TypedArray a = C0007f.m4a(resources, theme, attributeSet, C0001a.f0a);
        m61a(a, xmlPullParser);
        a.recycle();
        c0018f.f69a = getChangingConfigurations();
        c0018f.f79k = true;
        m63b(resources, xmlPullParser, attributeSet, theme);
        this.f84d = m64a(this.f84d, c0018f.f71c, c0018f.f72d);
    }

    public void invalidateSelf() {
        if (this.b != null) {
            this.b.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public boolean isAutoMirrored() {
        return this.b != null ? C0375a.m1778b(this.b) : this.f83c.f73e;
    }

    public boolean isStateful() {
        return this.b != null ? this.b.isStateful() : super.isStateful() || !(this.f83c == null || this.f83c.f71c == null || !this.f83c.f71c.isStateful());
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public Drawable mutate() {
        if (this.b != null) {
            this.b.mutate();
        } else if (!this.f86f && super.mutate() == this) {
            this.f83c = new C0018f(this.f83c);
            this.f86f = true;
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        if (this.b != null) {
            this.b.setBounds(rect);
        }
    }

    protected boolean onStateChange(int[] iArr) {
        if (this.b != null) {
            return this.b.setState(iArr);
        }
        C0018f c0018f = this.f83c;
        if (c0018f.f71c == null || c0018f.f72d == null) {
            return false;
        }
        this.f84d = m64a(this.f84d, c0018f.f71c, c0018f.f72d);
        invalidateSelf();
        return true;
    }

    public void scheduleSelf(Runnable runnable, long j) {
        if (this.b != null) {
            this.b.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    public void setAlpha(int i) {
        if (this.b != null) {
            this.b.setAlpha(i);
        } else if (this.f83c.f70b.getRootAlpha() != i) {
            this.f83c.f70b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    public void setAutoMirrored(boolean z) {
        if (this.b != null) {
            C0375a.m1777a(this.b, z);
        } else {
            this.f83c.f73e = z;
        }
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, Mode mode) {
        super.setColorFilter(i, mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.b != null) {
            this.b.setColorFilter(colorFilter);
            return;
        }
        this.f85e = colorFilter;
        invalidateSelf();
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    @SuppressLint({"NewApi"})
    public void setTint(int i) {
        if (this.b != null) {
            C0375a.m1771a(this.b, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (this.b != null) {
            C0375a.m1773a(this.b, colorStateList);
            return;
        }
        C0018f c0018f = this.f83c;
        if (c0018f.f71c != colorStateList) {
            c0018f.f71c = colorStateList;
            this.f84d = m64a(this.f84d, colorStateList, c0018f.f72d);
            invalidateSelf();
        }
    }

    public void setTintMode(Mode mode) {
        if (this.b != null) {
            C0375a.m1776a(this.b, mode);
            return;
        }
        C0018f c0018f = this.f83c;
        if (c0018f.f72d != mode) {
            c0018f.f72d = mode;
            this.f84d = m64a(this.f84d, c0018f.f71c, mode);
            invalidateSelf();
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        return this.b != null ? this.b.setVisible(z, z2) : super.setVisible(z, z2);
    }

    public void unscheduleSelf(Runnable runnable) {
        if (this.b != null) {
            this.b.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }
}
