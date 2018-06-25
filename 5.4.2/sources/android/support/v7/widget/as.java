package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.support.v7.view.menu.C0872g;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.ListMenuItemView;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

public class as extends ap implements ar {
    /* renamed from: a */
    private static Method f2928a;
    /* renamed from: b */
    private ar f2929b;

    /* renamed from: android.support.v7.widget.as$a */
    public static class C1025a extends aj {
        /* renamed from: g */
        final int f2924g;
        /* renamed from: h */
        final int f2925h;
        /* renamed from: i */
        private ar f2926i;
        /* renamed from: j */
        private MenuItem f2927j;

        public C1025a(Context context, boolean z) {
            super(context, z);
            Configuration configuration = context.getResources().getConfiguration();
            if (VERSION.SDK_INT < 17 || 1 != configuration.getLayoutDirection()) {
                this.f2924g = 22;
                this.f2925h = 21;
                return;
            }
            this.f2924g = 21;
            this.f2925h = 22;
        }

        /* renamed from: a */
        public /* bridge */ /* synthetic */ boolean mo937a(MotionEvent motionEvent, int i) {
            return super.mo937a(motionEvent, i);
        }

        public /* bridge */ /* synthetic */ boolean hasFocus() {
            return super.hasFocus();
        }

        public /* bridge */ /* synthetic */ boolean hasWindowFocus() {
            return super.hasWindowFocus();
        }

        public /* bridge */ /* synthetic */ boolean isFocused() {
            return super.isFocused();
        }

        public /* bridge */ /* synthetic */ boolean isInTouchMode() {
            return super.isInTouchMode();
        }

        public boolean onHoverEvent(MotionEvent motionEvent) {
            if (this.f2926i != null) {
                int headersCount;
                C0872g c0872g;
                MenuItem a;
                MenuItem menuItem;
                C0873h a2;
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    c0872g = (C0872g) headerViewListAdapter.getWrappedAdapter();
                } else {
                    headersCount = 0;
                    c0872g = (C0872g) adapter;
                }
                if (motionEvent.getAction() != 10) {
                    int pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    if (pointToPosition != -1) {
                        headersCount = pointToPosition - headersCount;
                        if (headersCount >= 0 && headersCount < c0872g.getCount()) {
                            a = c0872g.m4204a(headersCount);
                            menuItem = this.f2927j;
                            if (menuItem != a) {
                                a2 = c0872g.m4203a();
                                if (menuItem != null) {
                                    this.f2926i.mo727a(a2, menuItem);
                                }
                                this.f2927j = a;
                                if (a != null) {
                                    this.f2926i.mo728b(a2, a);
                                }
                            }
                        }
                    }
                }
                a = null;
                menuItem = this.f2927j;
                if (menuItem != a) {
                    a2 = c0872g.m4203a();
                    if (menuItem != null) {
                        this.f2926i.mo727a(a2, menuItem);
                    }
                    this.f2927j = a;
                    if (a != null) {
                        this.f2926i.mo728b(a2, a);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
            if (listMenuItemView != null && i == this.f2924g) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            } else if (listMenuItemView == null || i != this.f2925h) {
                return super.onKeyDown(i, keyEvent);
            } else {
                setSelection(-1);
                ((C0872g) getAdapter()).m4203a().m4230a(false);
                return true;
            }
        }

        public void setHoverListener(ar arVar) {
            this.f2926i = arVar;
        }
    }

    static {
        try {
            f2928a = PopupWindow.class.getDeclaredMethod("setTouchModal", new Class[]{Boolean.TYPE});
        } catch (NoSuchMethodException e) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public as(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* renamed from: a */
    aj mo942a(Context context, boolean z) {
        aj c1025a = new C1025a(context, z);
        c1025a.setHoverListener(this);
        return c1025a;
    }

    /* renamed from: a */
    public void mo727a(C0873h c0873h, MenuItem menuItem) {
        if (this.f2929b != null) {
            this.f2929b.mo727a(c0873h, menuItem);
        }
    }

    /* renamed from: a */
    public void m5503a(ar arVar) {
        this.f2929b = arVar;
    }

    /* renamed from: a */
    public void m5504a(Object obj) {
        if (VERSION.SDK_INT >= 23) {
            this.g.setEnterTransition((Transition) obj);
        }
    }

    /* renamed from: b */
    public void mo728b(C0873h c0873h, MenuItem menuItem) {
        if (this.f2929b != null) {
            this.f2929b.mo728b(c0873h, menuItem);
        }
    }

    /* renamed from: b */
    public void m5506b(Object obj) {
        if (VERSION.SDK_INT >= 23) {
            this.g.setExitTransition((Transition) obj);
        }
    }

    /* renamed from: b */
    public void mo943b(boolean z) {
        if (f2928a != null) {
            try {
                f2928a.invoke(this.g, new Object[]{Boolean.valueOf(z)});
            } catch (Exception e) {
                Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
            }
        }
    }
}
