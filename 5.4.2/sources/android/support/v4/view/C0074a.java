package android.support.v4.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.C0606b.C0497a;
import android.support.v4.view.C0613c.C0502a;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0546l;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.a */
public class C0074a {
    private static final Object DEFAULT_DELEGATE = IMPL.mo342a();
    private static final C0499b IMPL;
    final Object mBridge = IMPL.mo343a(this);

    /* renamed from: android.support.v4.view.a$b */
    interface C0499b {
        /* renamed from: a */
        C0546l mo341a(Object obj, View view);

        /* renamed from: a */
        Object mo342a();

        /* renamed from: a */
        Object mo343a(C0074a c0074a);

        /* renamed from: a */
        void mo344a(Object obj, View view, int i);

        /* renamed from: a */
        void mo345a(Object obj, View view, C0531e c0531e);

        /* renamed from: a */
        boolean mo346a(Object obj, View view, int i, Bundle bundle);

        /* renamed from: a */
        boolean mo347a(Object obj, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: a */
        boolean mo348a(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: b */
        void mo349b(Object obj, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: c */
        void mo350c(Object obj, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: d */
        void mo351d(Object obj, View view, AccessibilityEvent accessibilityEvent);
    }

    /* renamed from: android.support.v4.view.a$d */
    static class C0500d implements C0499b {
        C0500d() {
        }

        /* renamed from: a */
        public C0546l mo341a(Object obj, View view) {
            return null;
        }

        /* renamed from: a */
        public Object mo342a() {
            return null;
        }

        /* renamed from: a */
        public Object mo343a(C0074a c0074a) {
            return null;
        }

        /* renamed from: a */
        public void mo344a(Object obj, View view, int i) {
        }

        /* renamed from: a */
        public void mo345a(Object obj, View view, C0531e c0531e) {
        }

        /* renamed from: a */
        public boolean mo346a(Object obj, View view, int i, Bundle bundle) {
            return false;
        }

        /* renamed from: a */
        public boolean mo347a(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            return false;
        }

        /* renamed from: a */
        public boolean mo348a(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return true;
        }

        /* renamed from: b */
        public void mo349b(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        }

        /* renamed from: c */
        public void mo350c(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        }

        /* renamed from: d */
        public void mo351d(Object obj, View view, AccessibilityEvent accessibilityEvent) {
        }
    }

    /* renamed from: android.support.v4.view.a$a */
    static class C0501a extends C0500d {
        C0501a() {
        }

        /* renamed from: a */
        public Object mo342a() {
            return C0606b.m3045a();
        }

        /* renamed from: a */
        public Object mo343a(final C0074a c0074a) {
            return C0606b.m3046a(new C0497a(this) {
                /* renamed from: b */
                final /* synthetic */ C0501a f1294b;

                /* renamed from: a */
                public void mo334a(View view, int i) {
                    c0074a.sendAccessibilityEvent(view, i);
                }

                /* renamed from: a */
                public void mo335a(View view, Object obj) {
                    c0074a.onInitializeAccessibilityNodeInfo(view, new C0531e(obj));
                }

                /* renamed from: a */
                public boolean mo336a(View view, AccessibilityEvent accessibilityEvent) {
                    return c0074a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: a */
                public boolean mo337a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    return c0074a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
                }

                /* renamed from: b */
                public void mo338b(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.onInitializeAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: c */
                public void mo339c(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.onPopulateAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: d */
                public void mo340d(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
                }
            });
        }

        /* renamed from: a */
        public void mo344a(Object obj, View view, int i) {
            C0606b.m3047a(obj, view, i);
        }

        /* renamed from: a */
        public void mo345a(Object obj, View view, C0531e c0531e) {
            C0606b.m3048a(obj, view, c0531e.m2304a());
        }

        /* renamed from: a */
        public boolean mo347a(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            return C0606b.m3049a(obj, view, accessibilityEvent);
        }

        /* renamed from: a */
        public boolean mo348a(Object obj, ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return C0606b.m3050a(obj, viewGroup, view, accessibilityEvent);
        }

        /* renamed from: b */
        public void mo349b(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            C0606b.m3051b(obj, view, accessibilityEvent);
        }

        /* renamed from: c */
        public void mo350c(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            C0606b.m3052c(obj, view, accessibilityEvent);
        }

        /* renamed from: d */
        public void mo351d(Object obj, View view, AccessibilityEvent accessibilityEvent) {
            C0606b.m3053d(obj, view, accessibilityEvent);
        }
    }

    /* renamed from: android.support.v4.view.a$c */
    static class C0504c extends C0501a {
        C0504c() {
        }

        /* renamed from: a */
        public C0546l mo341a(Object obj, View view) {
            Object a = C0613c.m3089a(obj, view);
            return a != null ? new C0546l(a) : null;
        }

        /* renamed from: a */
        public Object mo343a(final C0074a c0074a) {
            return C0613c.m3088a(new C0502a(this) {
                /* renamed from: b */
                final /* synthetic */ C0504c f1296b;

                /* renamed from: a */
                public Object mo352a(View view) {
                    C0546l accessibilityNodeProvider = c0074a.getAccessibilityNodeProvider(view);
                    return accessibilityNodeProvider != null ? accessibilityNodeProvider.m2423a() : null;
                }

                /* renamed from: a */
                public void mo353a(View view, int i) {
                    c0074a.sendAccessibilityEvent(view, i);
                }

                /* renamed from: a */
                public void mo354a(View view, Object obj) {
                    c0074a.onInitializeAccessibilityNodeInfo(view, new C0531e(obj));
                }

                /* renamed from: a */
                public boolean mo355a(View view, int i, Bundle bundle) {
                    return c0074a.performAccessibilityAction(view, i, bundle);
                }

                /* renamed from: a */
                public boolean mo356a(View view, AccessibilityEvent accessibilityEvent) {
                    return c0074a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: a */
                public boolean mo357a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    return c0074a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
                }

                /* renamed from: b */
                public void mo358b(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.onInitializeAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: c */
                public void mo359c(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.onPopulateAccessibilityEvent(view, accessibilityEvent);
                }

                /* renamed from: d */
                public void mo360d(View view, AccessibilityEvent accessibilityEvent) {
                    c0074a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
                }
            });
        }

        /* renamed from: a */
        public boolean mo346a(Object obj, View view, int i, Bundle bundle) {
            return C0613c.m3090a(obj, view, i, bundle);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new C0504c();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new C0501a();
        } else {
            IMPL = new C0500d();
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.mo347a(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public C0546l getAccessibilityNodeProvider(View view) {
        return IMPL.mo341a(DEFAULT_DELEGATE, view);
    }

    Object getBridge() {
        return this.mBridge;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo349b(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
        IMPL.mo345a(DEFAULT_DELEGATE, view, c0531e);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo350c(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.mo348a(DEFAULT_DELEGATE, viewGroup, view, accessibilityEvent);
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return IMPL.mo346a(DEFAULT_DELEGATE, view, i, bundle);
    }

    public void sendAccessibilityEvent(View view, int i) {
        IMPL.mo344a(DEFAULT_DELEGATE, view, i);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo351d(DEFAULT_DELEGATE, view, accessibilityEvent);
    }
}
