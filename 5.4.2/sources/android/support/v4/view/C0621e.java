package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.view.e */
public final class C0621e {
    /* renamed from: a */
    private final C0617a f1388a;

    /* renamed from: android.support.v4.view.e$a */
    interface C0617a {
        /* renamed from: a */
        boolean mo549a(MotionEvent motionEvent);
    }

    /* renamed from: android.support.v4.view.e$b */
    static class C0619b implements C0617a {
        /* renamed from: j */
        private static final int f1363j = ViewConfiguration.getLongPressTimeout();
        /* renamed from: k */
        private static final int f1364k = ViewConfiguration.getTapTimeout();
        /* renamed from: l */
        private static final int f1365l = ViewConfiguration.getDoubleTapTimeout();
        /* renamed from: a */
        final OnGestureListener f1366a;
        /* renamed from: b */
        OnDoubleTapListener f1367b;
        /* renamed from: c */
        boolean f1368c;
        /* renamed from: d */
        boolean f1369d;
        /* renamed from: e */
        MotionEvent f1370e;
        /* renamed from: f */
        private int f1371f;
        /* renamed from: g */
        private int f1372g;
        /* renamed from: h */
        private int f1373h;
        /* renamed from: i */
        private int f1374i;
        /* renamed from: m */
        private final Handler f1375m;
        /* renamed from: n */
        private boolean f1376n;
        /* renamed from: o */
        private boolean f1377o;
        /* renamed from: p */
        private boolean f1378p;
        /* renamed from: q */
        private MotionEvent f1379q;
        /* renamed from: r */
        private boolean f1380r;
        /* renamed from: s */
        private float f1381s;
        /* renamed from: t */
        private float f1382t;
        /* renamed from: u */
        private float f1383u;
        /* renamed from: v */
        private float f1384v;
        /* renamed from: w */
        private boolean f1385w;
        /* renamed from: x */
        private VelocityTracker f1386x;

        /* renamed from: android.support.v4.view.e$b$a */
        private class C0618a extends Handler {
            /* renamed from: a */
            final /* synthetic */ C0619b f1362a;

            C0618a(C0619b c0619b) {
                this.f1362a = c0619b;
            }

            C0618a(C0619b c0619b, Handler handler) {
                this.f1362a = c0619b;
                super(handler.getLooper());
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        this.f1362a.f1366a.onShowPress(this.f1362a.f1370e);
                        return;
                    case 2:
                        this.f1362a.m3109a();
                        return;
                    case 3:
                        if (this.f1362a.f1367b == null) {
                            return;
                        }
                        if (this.f1362a.f1368c) {
                            this.f1362a.f1369d = true;
                            return;
                        } else {
                            this.f1362a.f1367b.onSingleTapConfirmed(this.f1362a.f1370e);
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message " + message);
                }
            }
        }

        public C0619b(Context context, OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.f1375m = new C0618a(this, handler);
            } else {
                this.f1375m = new C0618a(this);
            }
            this.f1366a = onGestureListener;
            if (onGestureListener instanceof OnDoubleTapListener) {
                m3110a((OnDoubleTapListener) onGestureListener);
            }
            m3105a(context);
        }

        /* renamed from: a */
        private void m3105a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.f1366a == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.f1385w = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.f1373h = viewConfiguration.getScaledMinimumFlingVelocity();
                this.f1374i = viewConfiguration.getScaledMaximumFlingVelocity();
                this.f1371f = scaledTouchSlop * scaledTouchSlop;
                this.f1372g = scaledDoubleTapSlop * scaledDoubleTapSlop;
            }
        }

        /* renamed from: a */
        private boolean m3106a(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            if (!this.f1378p || motionEvent3.getEventTime() - motionEvent2.getEventTime() > ((long) f1365l)) {
                return false;
            }
            int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            return (x * x) + (y * y) < this.f1372g;
        }

        /* renamed from: b */
        private void m3107b() {
            this.f1375m.removeMessages(1);
            this.f1375m.removeMessages(2);
            this.f1375m.removeMessages(3);
            this.f1386x.recycle();
            this.f1386x = null;
            this.f1380r = false;
            this.f1368c = false;
            this.f1377o = false;
            this.f1378p = false;
            this.f1369d = false;
            if (this.f1376n) {
                this.f1376n = false;
            }
        }

        /* renamed from: c */
        private void m3108c() {
            this.f1375m.removeMessages(1);
            this.f1375m.removeMessages(2);
            this.f1375m.removeMessages(3);
            this.f1380r = false;
            this.f1377o = false;
            this.f1378p = false;
            this.f1369d = false;
            if (this.f1376n) {
                this.f1376n = false;
            }
        }

        /* renamed from: a */
        void m3109a() {
            this.f1375m.removeMessages(3);
            this.f1369d = false;
            this.f1376n = true;
            this.f1366a.onLongPress(this.f1370e);
        }

        /* renamed from: a */
        public void m3110a(OnDoubleTapListener onDoubleTapListener) {
            this.f1367b = onDoubleTapListener;
        }

        /* renamed from: a */
        public boolean mo549a(MotionEvent motionEvent) {
            int i;
            int action = motionEvent.getAction();
            if (this.f1386x == null) {
                this.f1386x = VelocityTracker.obtain();
            }
            this.f1386x.addMovement(motionEvent);
            boolean z = (action & 255) == 6;
            int b = z ? C0659t.m3206b(motionEvent) : -1;
            int pointerCount = motionEvent.getPointerCount();
            float f = BitmapDescriptorFactory.HUE_RED;
            float f2 = BitmapDescriptorFactory.HUE_RED;
            for (i = 0; i < pointerCount; i++) {
                if (b != i) {
                    f2 += motionEvent.getX(i);
                    f += motionEvent.getY(i);
                }
            }
            b = z ? pointerCount - 1 : pointerCount;
            f2 /= (float) b;
            f /= (float) b;
            boolean hasMessages;
            float b2;
            float a;
            switch (action & 255) {
                case 0:
                    if (this.f1367b != null) {
                        hasMessages = this.f1375m.hasMessages(3);
                        if (hasMessages) {
                            this.f1375m.removeMessages(3);
                        }
                        if (this.f1370e == null || this.f1379q == null || !hasMessages || !m3106a(this.f1370e, this.f1379q, motionEvent)) {
                            this.f1375m.sendEmptyMessageDelayed(3, (long) f1365l);
                        } else {
                            this.f1380r = true;
                            b = (this.f1367b.onDoubleTap(this.f1370e) | 0) | this.f1367b.onDoubleTapEvent(motionEvent);
                            this.f1381s = f2;
                            this.f1383u = f2;
                            this.f1382t = f;
                            this.f1384v = f;
                            if (this.f1370e != null) {
                                this.f1370e.recycle();
                            }
                            this.f1370e = MotionEvent.obtain(motionEvent);
                            this.f1377o = true;
                            this.f1378p = true;
                            this.f1368c = true;
                            this.f1376n = false;
                            this.f1369d = false;
                            if (this.f1385w) {
                                this.f1375m.removeMessages(2);
                                this.f1375m.sendEmptyMessageAtTime(2, (this.f1370e.getDownTime() + ((long) f1364k)) + ((long) f1363j));
                            }
                            this.f1375m.sendEmptyMessageAtTime(1, this.f1370e.getDownTime() + ((long) f1364k));
                            return b | this.f1366a.onDown(motionEvent);
                        }
                    }
                    b = 0;
                    this.f1381s = f2;
                    this.f1383u = f2;
                    this.f1382t = f;
                    this.f1384v = f;
                    if (this.f1370e != null) {
                        this.f1370e.recycle();
                    }
                    this.f1370e = MotionEvent.obtain(motionEvent);
                    this.f1377o = true;
                    this.f1378p = true;
                    this.f1368c = true;
                    this.f1376n = false;
                    this.f1369d = false;
                    if (this.f1385w) {
                        this.f1375m.removeMessages(2);
                        this.f1375m.sendEmptyMessageAtTime(2, (this.f1370e.getDownTime() + ((long) f1364k)) + ((long) f1363j));
                    }
                    this.f1375m.sendEmptyMessageAtTime(1, this.f1370e.getDownTime() + ((long) f1364k));
                    return b | this.f1366a.onDown(motionEvent);
                case 1:
                    this.f1368c = false;
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    if (this.f1380r) {
                        hasMessages = this.f1367b.onDoubleTapEvent(motionEvent) | 0;
                    } else if (this.f1376n) {
                        this.f1375m.removeMessages(3);
                        this.f1376n = false;
                        hasMessages = false;
                    } else if (this.f1377o) {
                        hasMessages = this.f1366a.onSingleTapUp(motionEvent);
                        if (this.f1369d && this.f1367b != null) {
                            this.f1367b.onSingleTapConfirmed(motionEvent);
                        }
                    } else {
                        VelocityTracker velocityTracker = this.f1386x;
                        int pointerId = motionEvent.getPointerId(0);
                        velocityTracker.computeCurrentVelocity(1000, (float) this.f1374i);
                        b2 = af.m2517b(velocityTracker, pointerId);
                        a = af.m2516a(velocityTracker, pointerId);
                        hasMessages = (Math.abs(b2) > ((float) this.f1373h) || Math.abs(a) > ((float) this.f1373h)) ? this.f1366a.onFling(this.f1370e, motionEvent, a, b2) : false;
                    }
                    if (this.f1379q != null) {
                        this.f1379q.recycle();
                    }
                    this.f1379q = obtain;
                    if (this.f1386x != null) {
                        this.f1386x.recycle();
                        this.f1386x = null;
                    }
                    this.f1380r = false;
                    this.f1369d = false;
                    this.f1375m.removeMessages(1);
                    this.f1375m.removeMessages(2);
                    return hasMessages;
                case 2:
                    if (this.f1376n) {
                        return false;
                    }
                    a = this.f1381s - f2;
                    b2 = this.f1382t - f;
                    if (this.f1380r) {
                        return false | this.f1367b.onDoubleTapEvent(motionEvent);
                    }
                    if (this.f1377o) {
                        i = (int) (f2 - this.f1383u);
                        int i2 = (int) (f - this.f1384v);
                        i = (i * i) + (i2 * i2);
                        if (i > this.f1371f) {
                            hasMessages = this.f1366a.onScroll(this.f1370e, motionEvent, a, b2);
                            this.f1381s = f2;
                            this.f1382t = f;
                            this.f1377o = false;
                            this.f1375m.removeMessages(3);
                            this.f1375m.removeMessages(1);
                            this.f1375m.removeMessages(2);
                        } else {
                            hasMessages = false;
                        }
                        if (i > this.f1371f) {
                            this.f1378p = false;
                        }
                        return hasMessages;
                    } else if (Math.abs(a) < 1.0f && Math.abs(b2) < 1.0f) {
                        return false;
                    } else {
                        boolean onScroll = this.f1366a.onScroll(this.f1370e, motionEvent, a, b2);
                        this.f1381s = f2;
                        this.f1382t = f;
                        return onScroll;
                    }
                case 3:
                    m3107b();
                    return false;
                case 5:
                    this.f1381s = f2;
                    this.f1383u = f2;
                    this.f1382t = f;
                    this.f1384v = f;
                    m3108c();
                    return false;
                case 6:
                    this.f1381s = f2;
                    this.f1383u = f2;
                    this.f1382t = f;
                    this.f1384v = f;
                    this.f1386x.computeCurrentVelocity(1000, (float) this.f1374i);
                    int b3 = C0659t.m3206b(motionEvent);
                    b = motionEvent.getPointerId(b3);
                    f2 = af.m2516a(this.f1386x, b);
                    float b4 = af.m2517b(this.f1386x, b);
                    for (b = 0; b < pointerCount; b++) {
                        if (b != b3) {
                            int pointerId2 = motionEvent.getPointerId(b);
                            if ((af.m2517b(this.f1386x, pointerId2) * b4) + (af.m2516a(this.f1386x, pointerId2) * f2) < BitmapDescriptorFactory.HUE_RED) {
                                this.f1386x.clear();
                                return false;
                            }
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.v4.view.e$c */
    static class C0620c implements C0617a {
        /* renamed from: a */
        private final GestureDetector f1387a;

        public C0620c(Context context, OnGestureListener onGestureListener, Handler handler) {
            this.f1387a = new GestureDetector(context, onGestureListener, handler);
        }

        /* renamed from: a */
        public boolean mo549a(MotionEvent motionEvent) {
            return this.f1387a.onTouchEvent(motionEvent);
        }
    }

    public C0621e(Context context, OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public C0621e(Context context, OnGestureListener onGestureListener, Handler handler) {
        if (VERSION.SDK_INT > 17) {
            this.f1388a = new C0620c(context, onGestureListener, handler);
        } else {
            this.f1388a = new C0619b(context, onGestureListener, handler);
        }
    }

    /* renamed from: a */
    public boolean m3113a(MotionEvent motionEvent) {
        return this.f1388a.mo549a(motionEvent);
    }
}
