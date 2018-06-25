package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0652q.C0651e;
import android.support.v7.view.C0843c;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

@TargetApi(14)
/* renamed from: android.support.v7.view.menu.k */
public class C0881k extends C0862c<C0394b> implements MenuItem {
    /* renamed from: c */
    private Method f2236c;

    /* renamed from: android.support.v7.view.menu.k$a */
    class C0877a extends C0616d {
        /* renamed from: a */
        final ActionProvider f2231a;
        /* renamed from: b */
        final /* synthetic */ C0881k f2232b;

        public C0877a(C0881k c0881k, Context context, ActionProvider actionProvider) {
            this.f2232b = c0881k;
            super(context);
            this.f2231a = actionProvider;
        }

        /* renamed from: a */
        public View mo743a() {
            return this.f2231a.onCreateActionView();
        }

        /* renamed from: a */
        public void mo744a(SubMenu subMenu) {
            this.f2231a.onPrepareSubMenu(this.f2232b.m4136a(subMenu));
        }

        /* renamed from: d */
        public boolean mo745d() {
            return this.f2231a.onPerformDefaultAction();
        }

        /* renamed from: e */
        public boolean mo746e() {
            return this.f2231a.hasSubMenu();
        }
    }

    /* renamed from: android.support.v7.view.menu.k$b */
    static class C0878b extends FrameLayout implements C0843c {
        /* renamed from: a */
        final CollapsibleActionView f2233a;

        C0878b(View view) {
            super(view.getContext());
            this.f2233a = (CollapsibleActionView) view;
            addView(view);
        }

        /* renamed from: a */
        public void mo747a() {
            this.f2233a.onActionViewExpanded();
        }

        /* renamed from: b */
        public void mo748b() {
            this.f2233a.onActionViewCollapsed();
        }

        /* renamed from: c */
        View m4300c() {
            return (View) this.f2233a;
        }
    }

    /* renamed from: android.support.v7.view.menu.k$c */
    private class C0879c extends C0861d<OnActionExpandListener> implements C0651e {
        /* renamed from: a */
        final /* synthetic */ C0881k f2234a;

        C0879c(C0881k c0881k, OnActionExpandListener onActionExpandListener) {
            this.f2234a = c0881k;
            super(onActionExpandListener);
        }

        /* renamed from: a */
        public boolean mo749a(MenuItem menuItem) {
            return ((OnActionExpandListener) this.b).onMenuItemActionExpand(this.f2234a.m4135a(menuItem));
        }

        /* renamed from: b */
        public boolean mo750b(MenuItem menuItem) {
            return ((OnActionExpandListener) this.b).onMenuItemActionCollapse(this.f2234a.m4135a(menuItem));
        }
    }

    /* renamed from: android.support.v7.view.menu.k$d */
    private class C0880d extends C0861d<OnMenuItemClickListener> implements OnMenuItemClickListener {
        /* renamed from: a */
        final /* synthetic */ C0881k f2235a;

        C0880d(C0881k c0881k, OnMenuItemClickListener onMenuItemClickListener) {
            this.f2235a = c0881k;
            super(onMenuItemClickListener);
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return ((OnMenuItemClickListener) this.b).onMenuItemClick(this.f2235a.m4135a(menuItem));
        }
    }

    C0881k(Context context, C0394b c0394b) {
        super(context, c0394b);
    }

    /* renamed from: a */
    C0877a mo755a(ActionProvider actionProvider) {
        return new C0877a(this, this.a, actionProvider);
    }

    /* renamed from: a */
    public void m4304a(boolean z) {
        try {
            if (this.f2236c == null) {
                this.f2236c = ((C0394b) this.b).getClass().getDeclaredMethod("setExclusiveCheckable", new Class[]{Boolean.TYPE});
            }
            this.f2236c.invoke(this.b, new Object[]{Boolean.valueOf(z)});
        } catch (Throwable e) {
            Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e);
        }
    }

    public boolean collapseActionView() {
        return ((C0394b) this.b).collapseActionView();
    }

    public boolean expandActionView() {
        return ((C0394b) this.b).expandActionView();
    }

    public ActionProvider getActionProvider() {
        C0616d a = ((C0394b) this.b).mo710a();
        return a instanceof C0877a ? ((C0877a) a).f2231a : null;
    }

    public View getActionView() {
        View actionView = ((C0394b) this.b).getActionView();
        return actionView instanceof C0878b ? ((C0878b) actionView).m4300c() : actionView;
    }

    public char getAlphabeticShortcut() {
        return ((C0394b) this.b).getAlphabeticShortcut();
    }

    public int getGroupId() {
        return ((C0394b) this.b).getGroupId();
    }

    public Drawable getIcon() {
        return ((C0394b) this.b).getIcon();
    }

    public Intent getIntent() {
        return ((C0394b) this.b).getIntent();
    }

    public int getItemId() {
        return ((C0394b) this.b).getItemId();
    }

    public ContextMenuInfo getMenuInfo() {
        return ((C0394b) this.b).getMenuInfo();
    }

    public char getNumericShortcut() {
        return ((C0394b) this.b).getNumericShortcut();
    }

    public int getOrder() {
        return ((C0394b) this.b).getOrder();
    }

    public SubMenu getSubMenu() {
        return m4136a(((C0394b) this.b).getSubMenu());
    }

    public CharSequence getTitle() {
        return ((C0394b) this.b).getTitle();
    }

    public CharSequence getTitleCondensed() {
        return ((C0394b) this.b).getTitleCondensed();
    }

    public boolean hasSubMenu() {
        return ((C0394b) this.b).hasSubMenu();
    }

    public boolean isActionViewExpanded() {
        return ((C0394b) this.b).isActionViewExpanded();
    }

    public boolean isCheckable() {
        return ((C0394b) this.b).isCheckable();
    }

    public boolean isChecked() {
        return ((C0394b) this.b).isChecked();
    }

    public boolean isEnabled() {
        return ((C0394b) this.b).isEnabled();
    }

    public boolean isVisible() {
        return ((C0394b) this.b).isVisible();
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        ((C0394b) this.b).mo708a(actionProvider != null ? mo755a(actionProvider) : null);
        return this;
    }

    public MenuItem setActionView(int i) {
        ((C0394b) this.b).setActionView(i);
        View actionView = ((C0394b) this.b).getActionView();
        if (actionView instanceof CollapsibleActionView) {
            ((C0394b) this.b).setActionView(new C0878b(actionView));
        }
        return this;
    }

    public MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new C0878b(view);
        }
        ((C0394b) this.b).setActionView(view);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c) {
        ((C0394b) this.b).setAlphabeticShortcut(c);
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        ((C0394b) this.b).setCheckable(z);
        return this;
    }

    public MenuItem setChecked(boolean z) {
        ((C0394b) this.b).setChecked(z);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        ((C0394b) this.b).setEnabled(z);
        return this;
    }

    public MenuItem setIcon(int i) {
        ((C0394b) this.b).setIcon(i);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        ((C0394b) this.b).setIcon(drawable);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        ((C0394b) this.b).setIntent(intent);
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        ((C0394b) this.b).setNumericShortcut(c);
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        ((C0394b) this.b).mo709a(onActionExpandListener != null ? new C0879c(this, onActionExpandListener) : null);
        return this;
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        ((C0394b) this.b).setOnMenuItemClickListener(onMenuItemClickListener != null ? new C0880d(this, onMenuItemClickListener) : null);
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        ((C0394b) this.b).setShortcut(c, c2);
        return this;
    }

    public void setShowAsAction(int i) {
        ((C0394b) this.b).setShowAsAction(i);
    }

    public MenuItem setShowAsActionFlags(int i) {
        ((C0394b) this.b).setShowAsActionFlags(i);
        return this;
    }

    public MenuItem setTitle(int i) {
        ((C0394b) this.b).setTitle(i);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        ((C0394b) this.b).setTitle(charSequence);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        ((C0394b) this.b).setTitleCondensed(charSequence);
        return this;
    }

    public MenuItem setVisible(boolean z) {
        return ((C0394b) this.b).setVisible(z);
    }
}
