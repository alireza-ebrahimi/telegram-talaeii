package android.support.p005a.p006a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.p022f.C0464a;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

@SuppressLint({"NewApi"})
/* renamed from: android.support.a.a.c */
public class C0008c extends C0007f implements C0002b {
    /* renamed from: a */
    final Callback f14a;
    /* renamed from: c */
    private C0004a f15c;
    /* renamed from: d */
    private Context f16d;
    /* renamed from: e */
    private ArgbEvaluator f17e;
    /* renamed from: f */
    private AnimatorListener f18f;
    /* renamed from: g */
    private ArrayList<Object> f19g;

    /* renamed from: android.support.a.a.c$1 */
    class C00031 implements Callback {
        /* renamed from: a */
        final /* synthetic */ C0008c f6a;

        C00031(C0008c c0008c) {
            this.f6a = c0008c;
        }

        public void invalidateDrawable(Drawable drawable) {
            this.f6a.invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            this.f6a.scheduleSelf(runnable, j);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            this.f6a.unscheduleSelf(runnable);
        }
    }

    /* renamed from: android.support.a.a.c$a */
    private static class C0004a extends ConstantState {
        /* renamed from: a */
        int f7a;
        /* renamed from: b */
        C0020g f8b;
        /* renamed from: c */
        AnimatorSet f9c;
        /* renamed from: d */
        C0464a<Animator, String> f10d;
        /* renamed from: e */
        private ArrayList<Animator> f11e;

        public C0004a(Context context, C0004a c0004a, Callback callback, Resources resources) {
            int i = 0;
            if (c0004a != null) {
                this.f7a = c0004a.f7a;
                if (c0004a.f8b != null) {
                    ConstantState constantState = c0004a.f8b.getConstantState();
                    if (resources != null) {
                        this.f8b = (C0020g) constantState.newDrawable(resources);
                    } else {
                        this.f8b = (C0020g) constantState.newDrawable();
                    }
                    this.f8b = (C0020g) this.f8b.mutate();
                    this.f8b.setCallback(callback);
                    this.f8b.setBounds(c0004a.f8b.getBounds());
                    this.f8b.m66a(false);
                }
                if (c0004a.f11e != null) {
                    int size = c0004a.f11e.size();
                    this.f11e = new ArrayList(size);
                    this.f10d = new C0464a(size);
                    while (i < size) {
                        Animator animator = (Animator) c0004a.f11e.get(i);
                        Animator clone = animator.clone();
                        String str = (String) c0004a.f10d.get(animator);
                        clone.setTarget(this.f8b.m65a(str));
                        this.f11e.add(clone);
                        this.f10d.put(clone, str);
                        i++;
                    }
                    m3a();
                }
            }
        }

        /* renamed from: a */
        public void m3a() {
            if (this.f9c == null) {
                this.f9c = new AnimatorSet();
            }
            this.f9c.playTogether(this.f11e);
        }

        public int getChangingConfigurations() {
            return this.f7a;
        }

        public Drawable newDrawable() {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public Drawable newDrawable(Resources resources) {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }
    }

    /* renamed from: android.support.a.a.c$b */
    private static class C0005b extends ConstantState {
        /* renamed from: a */
        private final ConstantState f12a;

        public C0005b(ConstantState constantState) {
            this.f12a = constantState;
        }

        public boolean canApplyTheme() {
            return this.f12a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.f12a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            Drawable c0008c = new C0008c();
            c0008c.b = this.f12a.newDrawable();
            c0008c.b.setCallback(c0008c.f14a);
            return c0008c;
        }

        public Drawable newDrawable(Resources resources) {
            Drawable c0008c = new C0008c();
            c0008c.b = this.f12a.newDrawable(resources);
            c0008c.b.setCallback(c0008c.f14a);
            return c0008c;
        }

        public Drawable newDrawable(Resources resources, Theme theme) {
            Drawable c0008c = new C0008c();
            c0008c.b = this.f12a.newDrawable(resources, theme);
            c0008c.b.setCallback(c0008c.f14a);
            return c0008c;
        }
    }

    C0008c() {
        this(null, null, null);
    }

    private C0008c(Context context) {
        this(context, null, null);
    }

    private C0008c(Context context, C0004a c0004a, Resources resources) {
        this.f17e = null;
        this.f18f = null;
        this.f19g = null;
        this.f14a = new C00031(this);
        this.f16d = context;
        if (c0004a != null) {
            this.f15c = c0004a;
        } else {
            this.f15c = new C0004a(context, c0004a, this.f14a, resources);
        }
    }

    /* renamed from: a */
    public static C0008c m5a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        C0008c c0008c = new C0008c(context);
        c0008c.inflate(resources, xmlPullParser, attributeSet, theme);
        return c0008c;
    }

    /* renamed from: a */
    private void m6a(Animator animator) {
        if (animator instanceof AnimatorSet) {
            List childAnimations = ((AnimatorSet) animator).getChildAnimations();
            if (childAnimations != null) {
                for (int i = 0; i < childAnimations.size(); i++) {
                    m6a((Animator) childAnimations.get(i));
                }
            }
        }
        if (animator instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            String propertyName = objectAnimator.getPropertyName();
            if ("fillColor".equals(propertyName) || "strokeColor".equals(propertyName)) {
                if (this.f17e == null) {
                    this.f17e = new ArgbEvaluator();
                }
                objectAnimator.setEvaluator(this.f17e);
            }
        }
    }

    /* renamed from: a */
    private void m7a(String str, Animator animator) {
        animator.setTarget(this.f15c.f8b.m65a(str));
        if (VERSION.SDK_INT < 21) {
            m6a(animator);
        }
        if (this.f15c.f11e == null) {
            this.f15c.f11e = new ArrayList();
            this.f15c.f10d = new C0464a();
        }
        this.f15c.f11e.add(animator);
        this.f15c.f10d.put(animator, str);
    }

    public void applyTheme(Theme theme) {
        if (this.b != null) {
            C0375a.m1774a(this.b, theme);
        }
    }

    public boolean canApplyTheme() {
        return this.b != null ? C0375a.m1781d(this.b) : false;
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public void draw(Canvas canvas) {
        if (this.b != null) {
            this.b.draw(canvas);
            return;
        }
        this.f15c.f8b.draw(canvas);
        if (this.f15c.f9c.isStarted()) {
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.b != null ? C0375a.m1780c(this.b) : this.f15c.f8b.getAlpha();
    }

    public int getChangingConfigurations() {
        return this.b != null ? this.b.getChangingConfigurations() : super.getChangingConfigurations() | this.f15c.f7a;
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public ConstantState getConstantState() {
        return this.b != null ? new C0005b(this.b.getConstantState()) : null;
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.b != null ? this.b.getIntrinsicHeight() : this.f15c.f8b.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.b != null ? this.b.getIntrinsicWidth() : this.f15c.f8b.getIntrinsicWidth();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public int getOpacity() {
        return this.b != null ? this.b.getOpacity() : this.f15c.f8b.getOpacity();
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

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        if (this.b != null) {
            C0375a.m1775a(this.b, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                TypedArray a;
                if ("animated-vector".equals(name)) {
                    a = C0007f.m4a(resources, theme, attributeSet, C0001a.f4e);
                    int resourceId = a.getResourceId(0, 0);
                    if (resourceId != 0) {
                        C0020g a2 = C0020g.m59a(resources, resourceId, theme);
                        a2.m66a(false);
                        a2.setCallback(this.f14a);
                        if (this.f15c.f8b != null) {
                            this.f15c.f8b.setCallback(null);
                        }
                        this.f15c.f8b = a2;
                    }
                    a.recycle();
                } else if ("target".equals(name)) {
                    a = resources.obtainAttributes(attributeSet, C0001a.f5f);
                    String string = a.getString(0);
                    int resourceId2 = a.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        if (this.f16d != null) {
                            m7a(string, AnimatorInflater.loadAnimator(this.f16d, resourceId2));
                        } else {
                            a.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                    }
                    a.recycle();
                } else {
                    continue;
                }
            }
            eventType = xmlPullParser.next();
        }
        this.f15c.m3a();
    }

    public boolean isAutoMirrored() {
        return this.b != null ? C0375a.m1778b(this.b) : this.f15c.f8b.isAutoMirrored();
    }

    public boolean isRunning() {
        return this.b != null ? ((AnimatedVectorDrawable) this.b).isRunning() : this.f15c.f9c.isRunning();
    }

    public boolean isStateful() {
        return this.b != null ? this.b.isStateful() : this.f15c.f8b.isStateful();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public Drawable mutate() {
        if (this.b != null) {
            this.b.mutate();
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        if (this.b != null) {
            this.b.setBounds(rect);
        } else {
            this.f15c.f8b.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int i) {
        return this.b != null ? this.b.setLevel(i) : this.f15c.f8b.setLevel(i);
    }

    protected boolean onStateChange(int[] iArr) {
        return this.b != null ? this.b.setState(iArr) : this.f15c.f8b.setState(iArr);
    }

    public void setAlpha(int i) {
        if (this.b != null) {
            this.b.setAlpha(i);
        } else {
            this.f15c.f8b.setAlpha(i);
        }
    }

    public void setAutoMirrored(boolean z) {
        if (this.b != null) {
            this.b.setAutoMirrored(z);
        } else {
            this.f15c.f8b.setAutoMirrored(z);
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
        } else {
            this.f15c.f8b.setColorFilter(colorFilter);
        }
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

    public void setTint(int i) {
        if (this.b != null) {
            C0375a.m1771a(this.b, i);
        } else {
            this.f15c.f8b.setTint(i);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (this.b != null) {
            C0375a.m1773a(this.b, colorStateList);
        } else {
            this.f15c.f8b.setTintList(colorStateList);
        }
    }

    public void setTintMode(Mode mode) {
        if (this.b != null) {
            C0375a.m1776a(this.b, mode);
        } else {
            this.f15c.f8b.setTintMode(mode);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        if (this.b != null) {
            return this.b.setVisible(z, z2);
        }
        this.f15c.f8b.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    public void start() {
        if (this.b != null) {
            ((AnimatedVectorDrawable) this.b).start();
        } else if (!this.f15c.f9c.isStarted()) {
            this.f15c.f9c.start();
            invalidateSelf();
        }
    }

    public void stop() {
        if (this.b != null) {
            ((AnimatedVectorDrawable) this.b).stop();
        } else {
            this.f15c.f9c.end();
        }
    }
}
