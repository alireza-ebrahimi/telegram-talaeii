package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.C0235a;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0652q.C0651e;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

/* renamed from: android.support.v7.view.menu.a */
public class C0858a implements C0394b {
    /* renamed from: a */
    private final int f2094a;
    /* renamed from: b */
    private final int f2095b;
    /* renamed from: c */
    private final int f2096c;
    /* renamed from: d */
    private final int f2097d;
    /* renamed from: e */
    private CharSequence f2098e;
    /* renamed from: f */
    private CharSequence f2099f;
    /* renamed from: g */
    private Intent f2100g;
    /* renamed from: h */
    private char f2101h;
    /* renamed from: i */
    private char f2102i;
    /* renamed from: j */
    private Drawable f2103j;
    /* renamed from: k */
    private int f2104k = 0;
    /* renamed from: l */
    private Context f2105l;
    /* renamed from: m */
    private OnMenuItemClickListener f2106m;
    /* renamed from: n */
    private int f2107n = 16;

    public C0858a(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.f2105l = context;
        this.f2094a = i2;
        this.f2095b = i;
        this.f2096c = i3;
        this.f2097d = i4;
        this.f2098e = charSequence;
    }

    /* renamed from: a */
    public C0394b m4104a(int i) {
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    public C0394b mo708a(C0616d c0616d) {
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    public C0394b mo709a(C0651e c0651e) {
        return this;
    }

    /* renamed from: a */
    public C0394b m4107a(View view) {
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    public C0616d mo710a() {
        return null;
    }

    /* renamed from: b */
    public C0394b m4109b(int i) {
        setShowAsAction(i);
        return this;
    }

    public boolean collapseActionView() {
        return false;
    }

    public boolean expandActionView() {
        return false;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    public View getActionView() {
        return null;
    }

    public char getAlphabeticShortcut() {
        return this.f2102i;
    }

    public int getGroupId() {
        return this.f2095b;
    }

    public Drawable getIcon() {
        return this.f2103j;
    }

    public Intent getIntent() {
        return this.f2100g;
    }

    public int getItemId() {
        return this.f2094a;
    }

    public ContextMenuInfo getMenuInfo() {
        return null;
    }

    public char getNumericShortcut() {
        return this.f2101h;
    }

    public int getOrder() {
        return this.f2097d;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return this.f2098e;
    }

    public CharSequence getTitleCondensed() {
        return this.f2099f != null ? this.f2099f : this.f2098e;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return (this.f2107n & 1) != 0;
    }

    public boolean isChecked() {
        return (this.f2107n & 2) != 0;
    }

    public boolean isEnabled() {
        return (this.f2107n & 16) != 0;
    }

    public boolean isVisible() {
        return (this.f2107n & 8) == 0;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ MenuItem setActionView(int i) {
        return m4104a(i);
    }

    public /* synthetic */ MenuItem setActionView(View view) {
        return m4107a(view);
    }

    public MenuItem setAlphabeticShortcut(char c) {
        this.f2102i = c;
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        this.f2107n = (z ? 1 : 0) | (this.f2107n & -2);
        return this;
    }

    public MenuItem setChecked(boolean z) {
        this.f2107n = (z ? 2 : 0) | (this.f2107n & -3);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        this.f2107n = (z ? 16 : 0) | (this.f2107n & -17);
        return this;
    }

    public MenuItem setIcon(int i) {
        this.f2104k = i;
        this.f2103j = C0235a.m1066a(this.f2105l, i);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.f2103j = drawable;
        this.f2104k = 0;
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.f2100g = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        this.f2101h = c;
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.f2106m = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        this.f2101h = c;
        this.f2102i = c2;
        return this;
    }

    public void setShowAsAction(int i) {
    }

    public /* synthetic */ MenuItem setShowAsActionFlags(int i) {
        return m4109b(i);
    }

    public MenuItem setTitle(int i) {
        this.f2098e = this.f2105l.getResources().getString(i);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.f2098e = charSequence;
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f2099f = charSequence;
        return this;
    }

    public MenuItem setVisible(boolean z) {
        this.f2107n = (z ? 0 : 8) | (this.f2107n & 8);
        return this;
    }
}
