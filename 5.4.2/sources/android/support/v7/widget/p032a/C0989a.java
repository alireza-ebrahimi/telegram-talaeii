package android.support.v7.widget.p032a;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.p017a.C0211a;
import android.support.v4.p017a.C0212b;
import android.support.v4.p017a.C0214d;
import android.support.v4.p017a.C0216g;
import android.support.v4.view.C0621e;
import android.support.v4.view.C0659t;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.support.v7.p029e.C0839a.C0836a;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0929d;
import android.support.v7.widget.RecyclerView.C0933e;
import android.support.v7.widget.RecyclerView.C0935g;
import android.support.v7.widget.RecyclerView.C0941j;
import android.support.v7.widget.RecyclerView.C0943l;
import android.support.v7.widget.RecyclerView.C0952s;
import android.support.v7.widget.RecyclerView.C0955v;
import android.support.v7.widget.p032a.C0994c.C0991a;
import android.support.v7.widget.p032a.C0994c.C0992b;
import android.support.v7.widget.p032a.C0994c.C0993c;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v7.widget.a.a */
public class C0989a extends C0935g implements C0941j {
    /* renamed from: A */
    private final C0943l f2753A = new C09792(this);
    /* renamed from: B */
    private Rect f2754B;
    /* renamed from: C */
    private long f2755C;
    /* renamed from: a */
    final List<View> f2756a = new ArrayList();
    /* renamed from: b */
    C0955v f2757b = null;
    /* renamed from: c */
    float f2758c;
    /* renamed from: d */
    float f2759d;
    /* renamed from: e */
    float f2760e;
    /* renamed from: f */
    float f2761f;
    /* renamed from: g */
    float f2762g;
    /* renamed from: h */
    float f2763h;
    /* renamed from: i */
    float f2764i;
    /* renamed from: j */
    float f2765j;
    /* renamed from: k */
    int f2766k = -1;
    /* renamed from: l */
    C0986a f2767l;
    /* renamed from: m */
    int f2768m = 0;
    /* renamed from: n */
    int f2769n;
    /* renamed from: o */
    List<C0980c> f2770o = new ArrayList();
    /* renamed from: p */
    RecyclerView f2771p;
    /* renamed from: q */
    final Runnable f2772q = new C09781(this);
    /* renamed from: r */
    VelocityTracker f2773r;
    /* renamed from: s */
    View f2774s = null;
    /* renamed from: t */
    int f2775t = -1;
    /* renamed from: u */
    C0621e f2776u;
    /* renamed from: v */
    private final float[] f2777v = new float[2];
    /* renamed from: w */
    private int f2778w;
    /* renamed from: x */
    private List<C0955v> f2779x;
    /* renamed from: y */
    private List<Integer> f2780y;
    /* renamed from: z */
    private C0929d f2781z = null;

    /* renamed from: android.support.v7.widget.a.a$d */
    public interface C0911d {
        /* renamed from: a */
        void mo810a(View view, View view2, int i, int i2);
    }

    /* renamed from: android.support.v7.widget.a.a$1 */
    class C09781 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0989a f2722a;

        C09781(C0989a c0989a) {
            this.f2722a = c0989a;
        }

        public void run() {
            if (this.f2722a.f2757b != null && this.f2722a.m5273b()) {
                if (this.f2722a.f2757b != null) {
                    this.f2722a.m5261a(this.f2722a.f2757b);
                }
                this.f2722a.f2771p.removeCallbacks(this.f2722a.f2772q);
                ah.m2787a(this.f2722a.f2771p, (Runnable) this);
            }
        }
    }

    /* renamed from: android.support.v7.widget.a.a$2 */
    class C09792 implements C0943l {
        /* renamed from: a */
        final /* synthetic */ C0989a f2723a;

        C09792(C0989a c0989a) {
            this.f2723a = c0989a;
        }

        /* renamed from: a */
        public void mo884a(boolean z) {
            if (z) {
                this.f2723a.m5262a(null, 0);
            }
        }

        /* renamed from: a */
        public boolean mo885a(RecyclerView recyclerView, MotionEvent motionEvent) {
            this.f2723a.f2776u.m3113a(motionEvent);
            int a = C0659t.m3205a(motionEvent);
            if (a == 0) {
                this.f2723a.f2766k = motionEvent.getPointerId(0);
                this.f2723a.f2758c = motionEvent.getX();
                this.f2723a.f2759d = motionEvent.getY();
                this.f2723a.m5274c();
                if (this.f2723a.f2757b == null) {
                    C0980c b = this.f2723a.m5269b(motionEvent);
                    if (b != null) {
                        C0989a c0989a = this.f2723a;
                        c0989a.f2758c -= b.f2734l;
                        c0989a = this.f2723a;
                        c0989a.f2759d -= b.f2735m;
                        this.f2723a.m5257a(b.f2730h, true);
                        if (this.f2723a.f2756a.remove(b.f2730h.itemView)) {
                            this.f2723a.f2767l.mo3510d(this.f2723a.f2771p, b.f2730h);
                        }
                        this.f2723a.m5262a(b.f2730h, b.f2731i);
                        this.f2723a.m5265a(motionEvent, this.f2723a.f2769n, 0);
                    }
                }
            } else if (a == 3 || a == 1) {
                this.f2723a.f2766k = -1;
                this.f2723a.m5262a(null, 0);
            } else if (this.f2723a.f2766k != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(this.f2723a.f2766k);
                if (findPointerIndex >= 0) {
                    this.f2723a.m5268a(a, motionEvent, findPointerIndex);
                }
            }
            if (this.f2723a.f2773r != null) {
                this.f2723a.f2773r.addMovement(motionEvent);
            }
            return this.f2723a.f2757b != null;
        }

        /* renamed from: b */
        public void mo886b(RecyclerView recyclerView, MotionEvent motionEvent) {
            int i = 0;
            this.f2723a.f2776u.m3113a(motionEvent);
            if (this.f2723a.f2773r != null) {
                this.f2723a.f2773r.addMovement(motionEvent);
            }
            if (this.f2723a.f2766k != -1) {
                int a = C0659t.m3205a(motionEvent);
                int findPointerIndex = motionEvent.findPointerIndex(this.f2723a.f2766k);
                if (findPointerIndex >= 0) {
                    this.f2723a.m5268a(a, motionEvent, findPointerIndex);
                }
                C0955v c0955v = this.f2723a.f2757b;
                if (c0955v != null) {
                    switch (a) {
                        case 1:
                            break;
                        case 2:
                            if (findPointerIndex >= 0) {
                                this.f2723a.m5265a(motionEvent, this.f2723a.f2769n, findPointerIndex);
                                this.f2723a.m5261a(c0955v);
                                this.f2723a.f2771p.removeCallbacks(this.f2723a.f2772q);
                                this.f2723a.f2772q.run();
                                this.f2723a.f2771p.invalidate();
                                return;
                            }
                            return;
                        case 3:
                            if (this.f2723a.f2773r != null) {
                                this.f2723a.f2773r.clear();
                                break;
                            }
                            break;
                        case 6:
                            a = C0659t.m3206b(motionEvent);
                            if (motionEvent.getPointerId(a) == this.f2723a.f2766k) {
                                if (a == 0) {
                                    i = 1;
                                }
                                this.f2723a.f2766k = motionEvent.getPointerId(i);
                                this.f2723a.m5265a(motionEvent, this.f2723a.f2769n, a);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                    this.f2723a.m5262a(null, 0);
                    this.f2723a.f2766k = -1;
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.a.a$c */
    private class C0980c implements C0212b {
        /* renamed from: a */
        private final C0216g f2724a;
        /* renamed from: b */
        private float f2725b;
        /* renamed from: d */
        final float f2726d;
        /* renamed from: e */
        final float f2727e;
        /* renamed from: f */
        final float f2728f;
        /* renamed from: g */
        final float f2729g;
        /* renamed from: h */
        final C0955v f2730h;
        /* renamed from: i */
        final int f2731i;
        /* renamed from: j */
        final int f2732j;
        /* renamed from: k */
        public boolean f2733k;
        /* renamed from: l */
        float f2734l;
        /* renamed from: m */
        float f2735m;
        /* renamed from: n */
        boolean f2736n = false;
        /* renamed from: o */
        boolean f2737o = false;
        /* renamed from: p */
        final /* synthetic */ C0989a f2738p;

        public C0980c(final C0989a c0989a, C0955v c0955v, int i, int i2, float f, float f2, float f3, float f4) {
            this.f2738p = c0989a;
            this.f2731i = i2;
            this.f2732j = i;
            this.f2730h = c0955v;
            this.f2726d = f;
            this.f2727e = f2;
            this.f2728f = f3;
            this.f2729g = f4;
            this.f2724a = C0211a.m992a();
            this.f2724a.mo193a(new C0214d(this) {
                /* renamed from: b */
                final /* synthetic */ C0980c f2752b;

                public void onAnimationUpdate(C0216g c0216g) {
                    this.f2752b.m5212a(c0216g.mo196c());
                }
            });
            this.f2724a.mo194a(c0955v.itemView);
            this.f2724a.mo192a((C0212b) this);
            m5212a((float) BitmapDescriptorFactory.HUE_RED);
        }

        /* renamed from: a */
        public void m5211a() {
            this.f2730h.setIsRecyclable(false);
            this.f2724a.mo190a();
        }

        /* renamed from: a */
        public void m5212a(float f) {
            this.f2725b = f;
        }

        /* renamed from: a */
        public void m5213a(long j) {
            this.f2724a.mo191a(j);
        }

        /* renamed from: b */
        public void m5214b() {
            this.f2724a.mo195b();
        }

        /* renamed from: c */
        public void m5215c() {
            if (this.f2726d == this.f2728f) {
                this.f2734l = ah.m2822l(this.f2730h.itemView);
            } else {
                this.f2734l = this.f2726d + (this.f2725b * (this.f2728f - this.f2726d));
            }
            if (this.f2727e == this.f2729g) {
                this.f2735m = ah.m2823m(this.f2730h.itemView);
            } else {
                this.f2735m = this.f2727e + (this.f2725b * (this.f2729g - this.f2727e));
            }
        }

        public void onAnimationCancel(C0216g c0216g) {
            m5212a(1.0f);
        }

        public void onAnimationEnd(C0216g c0216g) {
            if (!this.f2737o) {
                this.f2730h.setIsRecyclable(true);
            }
            this.f2737o = true;
        }

        public void onAnimationRepeat(C0216g c0216g) {
        }

        public void onAnimationStart(C0216g c0216g) {
        }
    }

    /* renamed from: android.support.v7.widget.a.a$5 */
    class C09835 implements C0929d {
        /* renamed from: a */
        final /* synthetic */ C0989a f2745a;

        C09835(C0989a c0989a) {
            this.f2745a = c0989a;
        }

        /* renamed from: a */
        public int mo891a(int i, int i2) {
            if (this.f2745a.f2774s == null) {
                return i2;
            }
            int i3 = this.f2745a.f2775t;
            if (i3 == -1) {
                i3 = this.f2745a.f2771p.indexOfChild(this.f2745a.f2774s);
                this.f2745a.f2775t = i3;
            }
            return i2 == i + -1 ? i3 : i2 >= i3 ? i2 + 1 : i2;
        }
    }

    /* renamed from: android.support.v7.widget.a.a$a */
    public static abstract class C0986a {
        /* renamed from: a */
        private static final C0990b f2746a;
        /* renamed from: b */
        private static final Interpolator f2747b = new C09841();
        /* renamed from: c */
        private static final Interpolator f2748c = new C09852();
        /* renamed from: d */
        private int f2749d = -1;

        /* renamed from: android.support.v7.widget.a.a$a$1 */
        static class C09841 implements Interpolator {
            C09841() {
            }

            public float getInterpolation(float f) {
                return (((f * f) * f) * f) * f;
            }
        }

        /* renamed from: android.support.v7.widget.a.a$a$2 */
        static class C09852 implements Interpolator {
            C09852() {
            }

            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
            }
        }

        static {
            if (VERSION.SDK_INT >= 21) {
                f2746a = new C0993c();
            } else if (VERSION.SDK_INT >= 11) {
                f2746a = new C0992b();
            } else {
                f2746a = new C0991a();
            }
        }

        /* renamed from: a */
        public static int m5217a(int i, int i2) {
            int i3 = i & 789516;
            if (i3 == 0) {
                return i;
            }
            int i4 = (i3 ^ -1) & i;
            return i2 == 0 ? i4 | (i3 << 2) : (i4 | ((i3 << 1) & -789517)) | (((i3 << 1) & 789516) << 2);
        }

        /* renamed from: a */
        private int m5218a(RecyclerView recyclerView) {
            if (this.f2749d == -1) {
                this.f2749d = recyclerView.getResources().getDimensionPixelSize(C0836a.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.f2749d;
        }

        /* renamed from: b */
        public static int m5219b(int i, int i2) {
            return (C0986a.m5220c(0, i2 | i) | C0986a.m5220c(1, i2)) | C0986a.m5220c(2, i);
        }

        /* renamed from: c */
        public static int m5220c(int i, int i2) {
            return i2 << (i * 8);
        }

        /* renamed from: a */
        public float m5221a(float f) {
            return f;
        }

        /* renamed from: a */
        public float m5222a(C0955v c0955v) {
            return 0.5f;
        }

        /* renamed from: a */
        public int m5223a(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float f = 1.0f;
            int a = (int) (((float) (m5218a(recyclerView) * ((int) Math.signum((float) i2)))) * f2748c.getInterpolation(Math.min(1.0f, (((float) Math.abs(i2)) * 1.0f) / ((float) i))));
            if (j <= 2000) {
                f = ((float) j) / 2000.0f;
            }
            int interpolation = (int) (f2747b.getInterpolation(f) * ((float) a));
            return interpolation == 0 ? i2 > 0 ? 1 : -1 : interpolation;
        }

        /* renamed from: a */
        public abstract int mo3504a(RecyclerView recyclerView, C0955v c0955v);

        /* renamed from: a */
        public long m5225a(RecyclerView recyclerView, int i, float f, float f2) {
            C0933e itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? i == 8 ? 200 : 250 : i == 8 ? itemAnimator.m4838e() : itemAnimator.m4841g();
        }

        /* renamed from: a */
        public C0955v m5226a(C0955v c0955v, List<C0955v> list, int i, int i2) {
            int width = i + c0955v.itemView.getWidth();
            int height = i2 + c0955v.itemView.getHeight();
            C0955v c0955v2 = null;
            int i3 = -1;
            int left = i - c0955v.itemView.getLeft();
            int top = i2 - c0955v.itemView.getTop();
            int size = list.size();
            int i4 = 0;
            while (i4 < size) {
                int i5;
                C0955v c0955v3;
                int i6;
                C0955v c0955v4;
                int i7;
                C0955v c0955v5 = (C0955v) list.get(i4);
                if (left > 0) {
                    int right = c0955v5.itemView.getRight() - width;
                    if (right < 0 && c0955v5.itemView.getRight() > c0955v.itemView.getRight()) {
                        right = Math.abs(right);
                        if (right > i3) {
                            i5 = right;
                            c0955v3 = c0955v5;
                            if (left < 0) {
                                i3 = c0955v5.itemView.getLeft() - i;
                                if (i3 > 0 && c0955v5.itemView.getLeft() < c0955v.itemView.getLeft()) {
                                    i3 = Math.abs(i3);
                                    if (i3 > i5) {
                                        c0955v3 = c0955v5;
                                        if (top < 0) {
                                            i5 = c0955v5.itemView.getTop() - i2;
                                            if (i5 > 0 && c0955v5.itemView.getTop() < c0955v.itemView.getTop()) {
                                                i5 = Math.abs(i5);
                                                if (i5 > i3) {
                                                    c0955v3 = c0955v5;
                                                    if (top > 0) {
                                                        i3 = c0955v5.itemView.getBottom() - height;
                                                        if (i3 < 0 && c0955v5.itemView.getBottom() > c0955v.itemView.getBottom()) {
                                                            i3 = Math.abs(i3);
                                                            if (i3 > i5) {
                                                                i6 = i3;
                                                                c0955v4 = c0955v5;
                                                                i7 = i6;
                                                                i4++;
                                                                c0955v2 = c0955v4;
                                                                i3 = i7;
                                                            }
                                                        }
                                                    }
                                                    i7 = i5;
                                                    c0955v4 = c0955v3;
                                                    i4++;
                                                    c0955v2 = c0955v4;
                                                    i3 = i7;
                                                }
                                            }
                                        }
                                        i5 = i3;
                                        if (top > 0) {
                                            i3 = c0955v5.itemView.getBottom() - height;
                                            i3 = Math.abs(i3);
                                            if (i3 > i5) {
                                                i6 = i3;
                                                c0955v4 = c0955v5;
                                                i7 = i6;
                                                i4++;
                                                c0955v2 = c0955v4;
                                                i3 = i7;
                                            }
                                        }
                                        i7 = i5;
                                        c0955v4 = c0955v3;
                                        i4++;
                                        c0955v2 = c0955v4;
                                        i3 = i7;
                                    }
                                }
                            }
                            i3 = i5;
                            if (top < 0) {
                                i5 = c0955v5.itemView.getTop() - i2;
                                i5 = Math.abs(i5);
                                if (i5 > i3) {
                                    c0955v3 = c0955v5;
                                    if (top > 0) {
                                        i3 = c0955v5.itemView.getBottom() - height;
                                        i3 = Math.abs(i3);
                                        if (i3 > i5) {
                                            i6 = i3;
                                            c0955v4 = c0955v5;
                                            i7 = i6;
                                            i4++;
                                            c0955v2 = c0955v4;
                                            i3 = i7;
                                        }
                                    }
                                    i7 = i5;
                                    c0955v4 = c0955v3;
                                    i4++;
                                    c0955v2 = c0955v4;
                                    i3 = i7;
                                }
                            }
                            i5 = i3;
                            if (top > 0) {
                                i3 = c0955v5.itemView.getBottom() - height;
                                i3 = Math.abs(i3);
                                if (i3 > i5) {
                                    i6 = i3;
                                    c0955v4 = c0955v5;
                                    i7 = i6;
                                    i4++;
                                    c0955v2 = c0955v4;
                                    i3 = i7;
                                }
                            }
                            i7 = i5;
                            c0955v4 = c0955v3;
                            i4++;
                            c0955v2 = c0955v4;
                            i3 = i7;
                        }
                    }
                }
                c0955v3 = c0955v2;
                i5 = i3;
                if (left < 0) {
                    i3 = c0955v5.itemView.getLeft() - i;
                    i3 = Math.abs(i3);
                    if (i3 > i5) {
                        c0955v3 = c0955v5;
                        if (top < 0) {
                            i5 = c0955v5.itemView.getTop() - i2;
                            i5 = Math.abs(i5);
                            if (i5 > i3) {
                                c0955v3 = c0955v5;
                                if (top > 0) {
                                    i3 = c0955v5.itemView.getBottom() - height;
                                    i3 = Math.abs(i3);
                                    if (i3 > i5) {
                                        i6 = i3;
                                        c0955v4 = c0955v5;
                                        i7 = i6;
                                        i4++;
                                        c0955v2 = c0955v4;
                                        i3 = i7;
                                    }
                                }
                                i7 = i5;
                                c0955v4 = c0955v3;
                                i4++;
                                c0955v2 = c0955v4;
                                i3 = i7;
                            }
                        }
                        i5 = i3;
                        if (top > 0) {
                            i3 = c0955v5.itemView.getBottom() - height;
                            i3 = Math.abs(i3);
                            if (i3 > i5) {
                                i6 = i3;
                                c0955v4 = c0955v5;
                                i7 = i6;
                                i4++;
                                c0955v2 = c0955v4;
                                i3 = i7;
                            }
                        }
                        i7 = i5;
                        c0955v4 = c0955v3;
                        i4++;
                        c0955v2 = c0955v4;
                        i3 = i7;
                    }
                }
                i3 = i5;
                if (top < 0) {
                    i5 = c0955v5.itemView.getTop() - i2;
                    i5 = Math.abs(i5);
                    if (i5 > i3) {
                        c0955v3 = c0955v5;
                        if (top > 0) {
                            i3 = c0955v5.itemView.getBottom() - height;
                            i3 = Math.abs(i3);
                            if (i3 > i5) {
                                i6 = i3;
                                c0955v4 = c0955v5;
                                i7 = i6;
                                i4++;
                                c0955v2 = c0955v4;
                                i3 = i7;
                            }
                        }
                        i7 = i5;
                        c0955v4 = c0955v3;
                        i4++;
                        c0955v2 = c0955v4;
                        i3 = i7;
                    }
                }
                i5 = i3;
                if (top > 0) {
                    i3 = c0955v5.itemView.getBottom() - height;
                    i3 = Math.abs(i3);
                    if (i3 > i5) {
                        i6 = i3;
                        c0955v4 = c0955v5;
                        i7 = i6;
                        i4++;
                        c0955v2 = c0955v4;
                        i3 = i7;
                    }
                }
                i7 = i5;
                c0955v4 = c0955v3;
                i4++;
                c0955v2 = c0955v4;
                i3 = i7;
            }
            return c0955v2;
        }

        /* renamed from: a */
        public void m5227a(Canvas canvas, RecyclerView recyclerView, C0955v c0955v, float f, float f2, int i, boolean z) {
            f2746a.mo898a(canvas, recyclerView, c0955v.itemView, f, f2, i, z);
        }

        /* renamed from: a */
        void m5228a(Canvas canvas, RecyclerView recyclerView, C0955v c0955v, List<C0980c> list, int i, float f, float f2) {
            int i2;
            int size = list.size();
            for (i2 = 0; i2 < size; i2++) {
                C0980c c0980c = (C0980c) list.get(i2);
                c0980c.m5215c();
                int save = canvas.save();
                m5227a(canvas, recyclerView, c0980c.f2730h, c0980c.f2734l, c0980c.f2735m, c0980c.f2731i, false);
                canvas.restoreToCount(save);
            }
            if (c0955v != null) {
                i2 = canvas.save();
                m5227a(canvas, recyclerView, c0955v, f, f2, i, true);
                canvas.restoreToCount(i2);
            }
        }

        /* renamed from: a */
        public abstract void mo3505a(C0955v c0955v, int i);

        /* renamed from: a */
        public void m5230a(RecyclerView recyclerView, C0955v c0955v, int i, C0955v c0955v2, int i2, int i3, int i4) {
            C0910h layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof C0911d) {
                ((C0911d) layoutManager).mo810a(c0955v.itemView, c0955v2.itemView, i3, i4);
                return;
            }
            if (layoutManager.mo821d()) {
                if (layoutManager.m4595h(c0955v2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.m234a(i2);
                }
                if (layoutManager.m4599j(c0955v2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.m234a(i2);
                }
            }
            if (layoutManager.mo823e()) {
                if (layoutManager.m4597i(c0955v2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.m234a(i2);
                }
                if (layoutManager.m4601k(c0955v2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.m234a(i2);
                }
            }
        }

        /* renamed from: a */
        public boolean mo3506a() {
            return true;
        }

        /* renamed from: a */
        public boolean m5232a(RecyclerView recyclerView, C0955v c0955v, C0955v c0955v2) {
            return true;
        }

        /* renamed from: b */
        public float m5233b(float f) {
            return f;
        }

        /* renamed from: b */
        public float m5234b(C0955v c0955v) {
            return 0.5f;
        }

        /* renamed from: b */
        final int m5235b(RecyclerView recyclerView, C0955v c0955v) {
            return m5243d(mo3504a(recyclerView, c0955v), ah.m2812g(recyclerView));
        }

        /* renamed from: b */
        public void m5236b(Canvas canvas, RecyclerView recyclerView, C0955v c0955v, float f, float f2, int i, boolean z) {
            f2746a.mo900b(canvas, recyclerView, c0955v.itemView, f, f2, i, z);
        }

        /* renamed from: b */
        void m5237b(Canvas canvas, RecyclerView recyclerView, C0955v c0955v, List<C0980c> list, int i, float f, float f2) {
            int i2;
            int size = list.size();
            for (i2 = 0; i2 < size; i2++) {
                C0980c c0980c = (C0980c) list.get(i2);
                int save = canvas.save();
                m5236b(canvas, recyclerView, c0980c.f2730h, c0980c.f2734l, c0980c.f2735m, c0980c.f2731i, false);
                canvas.restoreToCount(save);
            }
            if (c0955v != null) {
                i2 = canvas.save();
                m5236b(canvas, recyclerView, c0955v, f, f2, i, true);
                canvas.restoreToCount(i2);
            }
            Object obj = null;
            int i3 = size - 1;
            while (i3 >= 0) {
                Object obj2;
                c0980c = (C0980c) list.get(i3);
                if (!c0980c.f2737o || c0980c.f2733k) {
                    obj2 = !c0980c.f2737o ? 1 : obj;
                } else {
                    list.remove(i3);
                    obj2 = obj;
                }
                i3--;
                obj = obj2;
            }
            if (obj != null) {
                recyclerView.invalidate();
            }
        }

        /* renamed from: b */
        public void mo3507b(C0955v c0955v, int i) {
            if (c0955v != null) {
                f2746a.mo901b(c0955v.itemView);
            }
        }

        /* renamed from: b */
        public boolean mo3508b() {
            return true;
        }

        /* renamed from: b */
        public abstract boolean mo3509b(RecyclerView recyclerView, C0955v c0955v, C0955v c0955v2);

        /* renamed from: c */
        public int m5241c() {
            return 0;
        }

        /* renamed from: c */
        boolean m5242c(RecyclerView recyclerView, C0955v c0955v) {
            return (m5235b(recyclerView, c0955v) & 16711680) != 0;
        }

        /* renamed from: d */
        public int m5243d(int i, int i2) {
            int i3 = i & 3158064;
            if (i3 == 0) {
                return i;
            }
            int i4 = (i3 ^ -1) & i;
            return i2 == 0 ? i4 | (i3 >> 2) : (i4 | ((i3 >> 1) & -3158065)) | (((i3 >> 1) & 3158064) >> 2);
        }

        /* renamed from: d */
        public void mo3510d(RecyclerView recyclerView, C0955v c0955v) {
            f2746a.mo899a(c0955v.itemView);
        }
    }

    /* renamed from: android.support.v7.widget.a.a$b */
    private class C0987b extends SimpleOnGestureListener {
        /* renamed from: a */
        final /* synthetic */ C0989a f2750a;

        C0987b(C0989a c0989a) {
            this.f2750a = c0989a;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            View a = this.f2750a.m5258a(motionEvent);
            if (a != null) {
                C0955v b = this.f2750a.f2771p.m252b(a);
                if (b != null && this.f2750a.f2767l.m5242c(this.f2750a.f2771p, b) && motionEvent.getPointerId(0) == this.f2750a.f2766k) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.f2750a.f2766k);
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    this.f2750a.f2758c = x;
                    this.f2750a.f2759d = y;
                    C0989a c0989a = this.f2750a;
                    this.f2750a.f2763h = BitmapDescriptorFactory.HUE_RED;
                    c0989a.f2762g = BitmapDescriptorFactory.HUE_RED;
                    if (this.f2750a.f2767l.mo3506a()) {
                        this.f2750a.m5262a(b, 2);
                    }
                }
            }
        }
    }

    public C0989a(C0986a c0986a) {
        this.f2767l = c0986a;
    }

    /* renamed from: a */
    private void m5245a(float[] fArr) {
        if ((this.f2769n & 12) != 0) {
            fArr[0] = (this.f2764i + this.f2762g) - ((float) this.f2757b.itemView.getLeft());
        } else {
            fArr[0] = ah.m2822l(this.f2757b.itemView);
        }
        if ((this.f2769n & 3) != 0) {
            fArr[1] = (this.f2765j + this.f2763h) - ((float) this.f2757b.itemView.getTop());
        } else {
            fArr[1] = ah.m2823m(this.f2757b.itemView);
        }
    }

    /* renamed from: a */
    private static boolean m5246a(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= ((float) view.getWidth()) + f3 && f2 >= f4 && f2 <= ((float) view.getHeight()) + f4;
    }

    /* renamed from: b */
    private int m5247b(C0955v c0955v, int i) {
        int i2 = 8;
        if ((i & 12) != 0) {
            int i3 = this.f2762g > BitmapDescriptorFactory.HUE_RED ? 8 : 4;
            if (this.f2773r != null && this.f2766k > -1) {
                this.f2773r.computeCurrentVelocity(1000, this.f2767l.m5233b(this.f2761f));
                float a = af.m2516a(this.f2773r, this.f2766k);
                float b = af.m2517b(this.f2773r, this.f2766k);
                if (a <= BitmapDescriptorFactory.HUE_RED) {
                    i2 = 4;
                }
                float abs = Math.abs(a);
                if ((i2 & i) != 0 && i3 == i2 && abs >= this.f2767l.m5221a(this.f2760e) && abs > Math.abs(b)) {
                    return i2;
                }
            }
            float width = ((float) this.f2771p.getWidth()) * this.f2767l.m5222a(c0955v);
            if ((i & i3) != 0 && Math.abs(this.f2762g) > width) {
                return i3;
            }
        }
        return 0;
    }

    /* renamed from: c */
    private int m5248c(C0955v c0955v, int i) {
        int i2 = 2;
        if ((i & 3) != 0) {
            int i3 = this.f2763h > BitmapDescriptorFactory.HUE_RED ? 2 : 1;
            if (this.f2773r != null && this.f2766k > -1) {
                this.f2773r.computeCurrentVelocity(1000, this.f2767l.m5233b(this.f2761f));
                float a = af.m2516a(this.f2773r, this.f2766k);
                float b = af.m2517b(this.f2773r, this.f2766k);
                if (b <= BitmapDescriptorFactory.HUE_RED) {
                    i2 = 1;
                }
                float abs = Math.abs(b);
                if ((i2 & i) != 0 && i2 == i3 && abs >= this.f2767l.m5221a(this.f2760e) && abs > Math.abs(a)) {
                    return i2;
                }
            }
            float height = ((float) this.f2771p.getHeight()) * this.f2767l.m5222a(c0955v);
            if ((i & i3) != 0 && Math.abs(this.f2763h) > height) {
                return i3;
            }
        }
        return 0;
    }

    /* renamed from: c */
    private C0955v m5249c(MotionEvent motionEvent) {
        C0910h layoutManager = this.f2771p.getLayoutManager();
        if (this.f2766k == -1) {
            return null;
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.f2766k);
        float x = motionEvent.getX(findPointerIndex) - this.f2758c;
        float y = motionEvent.getY(findPointerIndex) - this.f2759d;
        x = Math.abs(x);
        y = Math.abs(y);
        if (x < ((float) this.f2778w) && y < ((float) this.f2778w)) {
            return null;
        }
        if (x > y && layoutManager.mo821d()) {
            return null;
        }
        if (y > x && layoutManager.mo823e()) {
            return null;
        }
        View a = m5258a(motionEvent);
        return a != null ? this.f2771p.m252b(a) : null;
    }

    /* renamed from: c */
    private List<C0955v> m5250c(C0955v c0955v) {
        if (this.f2779x == null) {
            this.f2779x = new ArrayList();
            this.f2780y = new ArrayList();
        } else {
            this.f2779x.clear();
            this.f2780y.clear();
        }
        int c = this.f2767l.m5241c();
        int round = Math.round(this.f2764i + this.f2762g) - c;
        int round2 = Math.round(this.f2765j + this.f2763h) - c;
        int width = (c0955v.itemView.getWidth() + round) + (c * 2);
        int height = (c0955v.itemView.getHeight() + round2) + (c * 2);
        int i = (round + width) / 2;
        int i2 = (round2 + height) / 2;
        C0910h layoutManager = this.f2771p.getLayoutManager();
        int w = layoutManager.m4615w();
        for (int i3 = 0; i3 < w; i3++) {
            View h = layoutManager.m4596h(i3);
            if (h != c0955v.itemView && h.getBottom() >= round2 && h.getTop() <= height && h.getRight() >= round && h.getLeft() <= width) {
                C0955v b = this.f2771p.m252b(h);
                if (this.f2767l.m5232a(this.f2771p, this.f2757b, b)) {
                    int abs = Math.abs(i - ((h.getLeft() + h.getRight()) / 2));
                    c = Math.abs(i2 - ((h.getBottom() + h.getTop()) / 2));
                    int i4 = (abs * abs) + (c * c);
                    int size = this.f2779x.size();
                    int i5 = 0;
                    abs = 0;
                    while (abs < size && i4 > ((Integer) this.f2780y.get(abs)).intValue()) {
                        i5++;
                        abs++;
                    }
                    this.f2779x.add(i5, b);
                    this.f2780y.add(i5, Integer.valueOf(i4));
                }
            }
        }
        return this.f2779x;
    }

    /* renamed from: d */
    private int m5251d(C0955v c0955v) {
        if (this.f2768m == 2) {
            return 0;
        }
        int a = this.f2767l.mo3504a(this.f2771p, c0955v);
        int d = (this.f2767l.m5243d(a, ah.m2812g(this.f2771p)) & 65280) >> 8;
        if (d == 0) {
            return 0;
        }
        int i = (a & 65280) >> 8;
        if (Math.abs(this.f2762g) > Math.abs(this.f2763h)) {
            a = m5247b(c0955v, d);
            if (a > 0) {
                return (i & a) == 0 ? C0986a.m5217a(a, ah.m2812g(this.f2771p)) : a;
            } else {
                a = m5248c(c0955v, d);
                return a > 0 ? a : 0;
            }
        } else {
            a = m5248c(c0955v, d);
            if (a > 0) {
                return a;
            }
            a = m5247b(c0955v, d);
            return a > 0 ? (i & a) == 0 ? C0986a.m5217a(a, ah.m2812g(this.f2771p)) : a : 0;
        }
    }

    /* renamed from: d */
    private void m5252d() {
        this.f2778w = ViewConfiguration.get(this.f2771p.getContext()).getScaledTouchSlop();
        this.f2771p.m239a((C0935g) this);
        this.f2771p.m242a(this.f2753A);
        this.f2771p.m241a((C0941j) this);
        m5254f();
    }

    /* renamed from: e */
    private void m5253e() {
        this.f2771p.m255b((C0935g) this);
        this.f2771p.m257b(this.f2753A);
        this.f2771p.m256b((C0941j) this);
        for (int size = this.f2770o.size() - 1; size >= 0; size--) {
            this.f2767l.mo3510d(this.f2771p, ((C0980c) this.f2770o.get(0)).f2730h);
        }
        this.f2770o.clear();
        this.f2774s = null;
        this.f2775t = -1;
        m5255g();
    }

    /* renamed from: f */
    private void m5254f() {
        if (this.f2776u == null) {
            this.f2776u = new C0621e(this.f2771p.getContext(), new C0987b(this));
        }
    }

    /* renamed from: g */
    private void m5255g() {
        if (this.f2773r != null) {
            this.f2773r.recycle();
            this.f2773r = null;
        }
    }

    /* renamed from: h */
    private void m5256h() {
        if (VERSION.SDK_INT < 21) {
            if (this.f2781z == null) {
                this.f2781z = new C09835(this);
            }
            this.f2771p.setChildDrawingOrderCallback(this.f2781z);
        }
    }

    /* renamed from: a */
    int m5257a(C0955v c0955v, boolean z) {
        for (int size = this.f2770o.size() - 1; size >= 0; size--) {
            C0980c c0980c = (C0980c) this.f2770o.get(size);
            if (c0980c.f2730h == c0955v) {
                c0980c.f2736n |= z;
                if (!c0980c.f2737o) {
                    c0980c.m5214b();
                }
                this.f2770o.remove(size);
                return c0980c.f2732j;
            }
        }
        return 0;
    }

    /* renamed from: a */
    View m5258a(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.f2757b != null) {
            View view = this.f2757b.itemView;
            if (C0989a.m5246a(view, x, y, this.f2764i + this.f2762g, this.f2765j + this.f2763h)) {
                return view;
            }
        }
        for (int size = this.f2770o.size() - 1; size >= 0; size--) {
            C0980c c0980c = (C0980c) this.f2770o.get(size);
            View view2 = c0980c.f2730h.itemView;
            if (C0989a.m5246a(view2, x, y, c0980c.f2734l, c0980c.f2735m)) {
                return view2;
            }
        }
        return this.f2771p.m232a(x, y);
    }

    /* renamed from: a */
    public void mo893a(Canvas canvas, RecyclerView recyclerView, C0952s c0952s) {
        float f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        this.f2775t = -1;
        if (this.f2757b != null) {
            m5245a(this.f2777v);
            f = this.f2777v[0];
            f2 = this.f2777v[1];
        } else {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        this.f2767l.m5228a(canvas, recyclerView, this.f2757b, this.f2770o, this.f2768m, f, f2);
    }

    /* renamed from: a */
    public void mo894a(Rect rect, View view, RecyclerView recyclerView, C0952s c0952s) {
        rect.setEmpty();
    }

    /* renamed from: a */
    void m5261a(C0955v c0955v) {
        if (!this.f2771p.isLayoutRequested() && this.f2768m == 2) {
            float b = this.f2767l.m5234b(c0955v);
            int i = (int) (this.f2764i + this.f2762g);
            int i2 = (int) (this.f2765j + this.f2763h);
            if (((float) Math.abs(i2 - c0955v.itemView.getTop())) >= ((float) c0955v.itemView.getHeight()) * b || ((float) Math.abs(i - c0955v.itemView.getLeft())) >= b * ((float) c0955v.itemView.getWidth())) {
                List c = m5250c(c0955v);
                if (c.size() != 0) {
                    C0955v a = this.f2767l.m5226a(c0955v, c, i, i2);
                    if (a == null) {
                        this.f2779x.clear();
                        this.f2780y.clear();
                        return;
                    }
                    int adapterPosition = a.getAdapterPosition();
                    int adapterPosition2 = c0955v.getAdapterPosition();
                    if (this.f2767l.mo3509b(this.f2771p, c0955v, a)) {
                        this.f2767l.m5230a(this.f2771p, c0955v, adapterPosition2, a, adapterPosition, i, i2);
                    }
                }
            }
        }
    }

    /* renamed from: a */
    void m5262a(C0955v c0955v, int i) {
        if (c0955v != this.f2757b || i != this.f2768m) {
            this.f2755C = Long.MIN_VALUE;
            int i2 = this.f2768m;
            m5257a(c0955v, true);
            this.f2768m = i;
            if (i == 2) {
                this.f2774s = c0955v.itemView;
                m5256h();
            }
            int i3 = (1 << ((i * 8) + 8)) - 1;
            Object obj = null;
            if (this.f2757b != null) {
                C0955v c0955v2 = this.f2757b;
                if (c0955v2.itemView.getParent() != null) {
                    float f;
                    float signum;
                    final int d = i2 == 2 ? 0 : m5251d(c0955v2);
                    m5255g();
                    switch (d) {
                        case 1:
                        case 2:
                            f = BitmapDescriptorFactory.HUE_RED;
                            signum = Math.signum(this.f2763h) * ((float) this.f2771p.getHeight());
                            break;
                        case 4:
                        case 8:
                        case 16:
                        case 32:
                            signum = BitmapDescriptorFactory.HUE_RED;
                            f = Math.signum(this.f2762g) * ((float) this.f2771p.getWidth());
                            break;
                        default:
                            f = BitmapDescriptorFactory.HUE_RED;
                            signum = BitmapDescriptorFactory.HUE_RED;
                            break;
                    }
                    int i4 = i2 == 2 ? 8 : d > 0 ? 2 : 4;
                    m5245a(this.f2777v);
                    float f2 = this.f2777v[0];
                    float f3 = this.f2777v[1];
                    final C0955v c0955v3 = c0955v2;
                    C0980c c09813 = new C0980c(this, c0955v2, i4, i2, f2, f3, f, signum) {
                        /* renamed from: c */
                        final /* synthetic */ C0989a f2741c;

                        public void onAnimationEnd(C0216g c0216g) {
                            super.onAnimationEnd(c0216g);
                            if (!this.n) {
                                if (d <= 0) {
                                    this.f2741c.f2767l.mo3510d(this.f2741c.f2771p, c0955v3);
                                } else {
                                    this.f2741c.f2756a.add(c0955v3.itemView);
                                    this.k = true;
                                    if (d > 0) {
                                        this.f2741c.m5264a((C0980c) this, d);
                                    }
                                }
                                if (this.f2741c.f2774s == c0955v3.itemView) {
                                    this.f2741c.m5275c(c0955v3.itemView);
                                }
                            }
                        }
                    };
                    c09813.m5213a(this.f2767l.m5225a(this.f2771p, i4, f - f2, signum - f3));
                    this.f2770o.add(c09813);
                    c09813.m5211a();
                    obj = 1;
                } else {
                    m5275c(c0955v2.itemView);
                    this.f2767l.mo3510d(this.f2771p, c0955v2);
                }
                this.f2757b = null;
            }
            Object obj2 = obj;
            if (c0955v != null) {
                this.f2769n = (this.f2767l.m5235b(this.f2771p, c0955v) & i3) >> (this.f2768m * 8);
                this.f2764i = (float) c0955v.itemView.getLeft();
                this.f2765j = (float) c0955v.itemView.getTop();
                this.f2757b = c0955v;
                if (i == 2) {
                    this.f2757b.itemView.performHapticFeedback(0);
                }
            }
            ViewParent parent = this.f2771p.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(this.f2757b != null);
            }
            if (obj2 == null) {
                this.f2771p.getLayoutManager().m4500K();
            }
            this.f2767l.mo3507b(this.f2757b, this.f2768m);
            this.f2771p.invalidate();
        }
    }

    /* renamed from: a */
    public void m5263a(RecyclerView recyclerView) {
        if (this.f2771p != recyclerView) {
            if (this.f2771p != null) {
                m5253e();
            }
            this.f2771p = recyclerView;
            if (this.f2771p != null) {
                Resources resources = recyclerView.getResources();
                this.f2760e = resources.getDimension(C0836a.item_touch_helper_swipe_escape_velocity);
                this.f2761f = resources.getDimension(C0836a.item_touch_helper_swipe_escape_max_velocity);
                m5252d();
            }
        }
    }

    /* renamed from: a */
    void m5264a(final C0980c c0980c, final int i) {
        this.f2771p.post(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C0989a f2744c;

            public void run() {
                if (this.f2744c.f2771p != null && this.f2744c.f2771p.isAttachedToWindow() && !c0980c.f2736n && c0980c.f2730h.getAdapterPosition() != -1) {
                    C0933e itemAnimator = this.f2744c.f2771p.getItemAnimator();
                    if ((itemAnimator == null || !itemAnimator.m4829a(null)) && !this.f2744c.m5267a()) {
                        this.f2744c.f2767l.mo3505a(c0980c.f2730h, i);
                    } else {
                        this.f2744c.f2771p.post(this);
                    }
                }
            }
        });
    }

    /* renamed from: a */
    void m5265a(MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        this.f2762g = x - this.f2758c;
        this.f2763h = y - this.f2759d;
        if ((i & 4) == 0) {
            this.f2762g = Math.max(BitmapDescriptorFactory.HUE_RED, this.f2762g);
        }
        if ((i & 8) == 0) {
            this.f2762g = Math.min(BitmapDescriptorFactory.HUE_RED, this.f2762g);
        }
        if ((i & 1) == 0) {
            this.f2763h = Math.max(BitmapDescriptorFactory.HUE_RED, this.f2763h);
        }
        if ((i & 2) == 0) {
            this.f2763h = Math.min(BitmapDescriptorFactory.HUE_RED, this.f2763h);
        }
    }

    /* renamed from: a */
    public void mo895a(View view) {
    }

    /* renamed from: a */
    boolean m5267a() {
        int size = this.f2770o.size();
        for (int i = 0; i < size; i++) {
            if (!((C0980c) this.f2770o.get(i)).f2737o) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    boolean m5268a(int i, MotionEvent motionEvent, int i2) {
        if (this.f2757b != null || i != 2 || this.f2768m == 2 || !this.f2767l.mo3508b() || this.f2771p.getScrollState() == 1) {
            return false;
        }
        C0955v c = m5249c(motionEvent);
        if (c == null) {
            return false;
        }
        int b = (this.f2767l.m5235b(this.f2771p, c) & 65280) >> 8;
        if (b == 0) {
            return false;
        }
        float x = motionEvent.getX(i2);
        x -= this.f2758c;
        float y = motionEvent.getY(i2) - this.f2759d;
        float abs = Math.abs(x);
        float abs2 = Math.abs(y);
        if (abs < ((float) this.f2778w) && abs2 < ((float) this.f2778w)) {
            return false;
        }
        if (abs > abs2) {
            if (x < BitmapDescriptorFactory.HUE_RED && (b & 4) == 0) {
                return false;
            }
            if (x > BitmapDescriptorFactory.HUE_RED && (b & 8) == 0) {
                return false;
            }
        } else if (y < BitmapDescriptorFactory.HUE_RED && (b & 1) == 0) {
            return false;
        } else {
            if (y > BitmapDescriptorFactory.HUE_RED && (b & 2) == 0) {
                return false;
            }
        }
        this.f2763h = BitmapDescriptorFactory.HUE_RED;
        this.f2762g = BitmapDescriptorFactory.HUE_RED;
        this.f2766k = motionEvent.getPointerId(0);
        m5262a(c, 1);
        return true;
    }

    /* renamed from: b */
    C0980c m5269b(MotionEvent motionEvent) {
        if (this.f2770o.isEmpty()) {
            return null;
        }
        View a = m5258a(motionEvent);
        for (int size = this.f2770o.size() - 1; size >= 0; size--) {
            C0980c c0980c = (C0980c) this.f2770o.get(size);
            if (c0980c.f2730h.itemView == a) {
                return c0980c;
            }
        }
        return null;
    }

    /* renamed from: b */
    public void mo896b(Canvas canvas, RecyclerView recyclerView, C0952s c0952s) {
        float f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        if (this.f2757b != null) {
            m5245a(this.f2777v);
            f = this.f2777v[0];
            f2 = this.f2777v[1];
        } else {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        this.f2767l.m5237b(canvas, recyclerView, this.f2757b, this.f2770o, this.f2768m, f, f2);
    }

    /* renamed from: b */
    public void m5271b(C0955v c0955v) {
        if (!this.f2767l.m5242c(this.f2771p, c0955v)) {
            Log.e("ItemTouchHelper", "Start drag has been called but swiping is not enabled");
        } else if (c0955v.itemView.getParent() != this.f2771p) {
            Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            m5274c();
            this.f2763h = BitmapDescriptorFactory.HUE_RED;
            this.f2762g = BitmapDescriptorFactory.HUE_RED;
            m5262a(c0955v, 2);
        }
    }

    /* renamed from: b */
    public void mo897b(View view) {
        m5275c(view);
        C0955v b = this.f2771p.m252b(view);
        if (b != null) {
            if (this.f2757b == null || b != this.f2757b) {
                m5257a(b, false);
                if (this.f2756a.remove(b.itemView)) {
                    this.f2767l.mo3510d(this.f2771p, b);
                    return;
                }
                return;
            }
            m5262a(null, 0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: b */
    boolean m5273b() {
        /*
        r14 = this;
        r12 = -9223372036854775808;
        r0 = 0;
        r5 = 0;
        r1 = r14.f2757b;
        if (r1 != 0) goto L_0x000b;
    L_0x0008:
        r14.f2755C = r12;
    L_0x000a:
        return r0;
    L_0x000b:
        r10 = java.lang.System.currentTimeMillis();
        r2 = r14.f2755C;
        r1 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r1 != 0) goto L_0x00bb;
    L_0x0015:
        r6 = 0;
    L_0x0017:
        r1 = r14.f2771p;
        r1 = r1.getLayoutManager();
        r2 = r14.f2754B;
        if (r2 != 0) goto L_0x0028;
    L_0x0021:
        r2 = new android.graphics.Rect;
        r2.<init>();
        r14.f2754B = r2;
    L_0x0028:
        r2 = r14.f2757b;
        r2 = r2.itemView;
        r3 = r14.f2754B;
        r1.m4558b(r2, r3);
        r2 = r1.mo821d();
        if (r2 == 0) goto L_0x00e6;
    L_0x0037:
        r2 = r14.f2764i;
        r3 = r14.f2762g;
        r2 = r2 + r3;
        r2 = (int) r2;
        r3 = r14.f2754B;
        r3 = r3.left;
        r3 = r2 - r3;
        r4 = r14.f2771p;
        r4 = r4.getPaddingLeft();
        r4 = r3 - r4;
        r3 = r14.f2762g;
        r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r3 >= 0) goto L_0x00c1;
    L_0x0051:
        if (r4 >= 0) goto L_0x00c1;
    L_0x0053:
        r1 = r1.mo823e();
        if (r1 == 0) goto L_0x010e;
    L_0x0059:
        r1 = r14.f2765j;
        r2 = r14.f2763h;
        r1 = r1 + r2;
        r1 = (int) r1;
        r2 = r14.f2754B;
        r2 = r2.top;
        r2 = r1 - r2;
        r3 = r14.f2771p;
        r3 = r3.getPaddingTop();
        r8 = r2 - r3;
        r2 = r14.f2763h;
        r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r2 >= 0) goto L_0x00e9;
    L_0x0073:
        if (r8 >= 0) goto L_0x00e9;
    L_0x0075:
        if (r4 == 0) goto L_0x0117;
    L_0x0077:
        r1 = r14.f2767l;
        r2 = r14.f2771p;
        r3 = r14.f2757b;
        r3 = r3.itemView;
        r3 = r3.getWidth();
        r5 = r14.f2771p;
        r5 = r5.getWidth();
        r4 = r1.m5223a(r2, r3, r4, r5, r6);
        r9 = r4;
    L_0x008e:
        if (r8 == 0) goto L_0x0115;
    L_0x0090:
        r1 = r14.f2767l;
        r2 = r14.f2771p;
        r3 = r14.f2757b;
        r3 = r3.itemView;
        r3 = r3.getHeight();
        r4 = r14.f2771p;
        r5 = r4.getHeight();
        r4 = r8;
        r1 = r1.m5223a(r2, r3, r4, r5, r6);
    L_0x00a7:
        if (r9 != 0) goto L_0x00ab;
    L_0x00a9:
        if (r1 == 0) goto L_0x0111;
    L_0x00ab:
        r2 = r14.f2755C;
        r0 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r0 != 0) goto L_0x00b3;
    L_0x00b1:
        r14.f2755C = r10;
    L_0x00b3:
        r0 = r14.f2771p;
        r0.scrollBy(r9, r1);
        r0 = 1;
        goto L_0x000a;
    L_0x00bb:
        r2 = r14.f2755C;
        r6 = r10 - r2;
        goto L_0x0017;
    L_0x00c1:
        r3 = r14.f2762g;
        r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r3 <= 0) goto L_0x00e6;
    L_0x00c7:
        r3 = r14.f2757b;
        r3 = r3.itemView;
        r3 = r3.getWidth();
        r2 = r2 + r3;
        r3 = r14.f2754B;
        r3 = r3.right;
        r2 = r2 + r3;
        r3 = r14.f2771p;
        r3 = r3.getWidth();
        r4 = r14.f2771p;
        r4 = r4.getPaddingRight();
        r3 = r3 - r4;
        r4 = r2 - r3;
        if (r4 > 0) goto L_0x0053;
    L_0x00e6:
        r4 = r0;
        goto L_0x0053;
    L_0x00e9:
        r2 = r14.f2763h;
        r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r2 <= 0) goto L_0x010e;
    L_0x00ef:
        r2 = r14.f2757b;
        r2 = r2.itemView;
        r2 = r2.getHeight();
        r1 = r1 + r2;
        r2 = r14.f2754B;
        r2 = r2.bottom;
        r1 = r1 + r2;
        r2 = r14.f2771p;
        r2 = r2.getHeight();
        r3 = r14.f2771p;
        r3 = r3.getPaddingBottom();
        r2 = r2 - r3;
        r8 = r1 - r2;
        if (r8 > 0) goto L_0x0075;
    L_0x010e:
        r8 = r0;
        goto L_0x0075;
    L_0x0111:
        r14.f2755C = r12;
        goto L_0x000a;
    L_0x0115:
        r1 = r8;
        goto L_0x00a7;
    L_0x0117:
        r9 = r4;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.a.a.b():boolean");
    }

    /* renamed from: c */
    void m5274c() {
        if (this.f2773r != null) {
            this.f2773r.recycle();
        }
        this.f2773r = VelocityTracker.obtain();
    }

    /* renamed from: c */
    void m5275c(View view) {
        if (view == this.f2774s) {
            this.f2774s = null;
            if (this.f2781z != null) {
                this.f2771p.setChildDrawingOrderCallback(null);
            }
        }
    }
}
