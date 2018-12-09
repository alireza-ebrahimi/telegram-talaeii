package android.support.v7.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.p018c.p019a.C0393a;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.p022f.C0463k;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.menu.C0886q;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

@TargetApi(11)
/* renamed from: android.support.v7.view.f */
public class C0847f extends ActionMode {
    /* renamed from: a */
    final Context f2007a;
    /* renamed from: b */
    final C0814b f2008b;

    /* renamed from: android.support.v7.view.f$a */
    public static class C0846a implements C0797a {
        /* renamed from: a */
        final Callback f2003a;
        /* renamed from: b */
        final Context f2004b;
        /* renamed from: c */
        final ArrayList<C0847f> f2005c = new ArrayList();
        /* renamed from: d */
        final C0463k<Menu, Menu> f2006d = new C0463k();

        public C0846a(Context context, Callback callback) {
            this.f2004b = context;
            this.f2003a = callback;
        }

        /* renamed from: a */
        private Menu m4042a(Menu menu) {
            Menu menu2 = (Menu) this.f2006d.get(menu);
            if (menu2 != null) {
                return menu2;
            }
            menu2 = C0886q.m4324a(this.f2004b, (C0393a) menu);
            this.f2006d.put(menu, menu2);
            return menu2;
        }

        /* renamed from: a */
        public void mo659a(C0814b c0814b) {
            this.f2003a.onDestroyActionMode(m4046b(c0814b));
        }

        /* renamed from: a */
        public boolean mo660a(C0814b c0814b, Menu menu) {
            return this.f2003a.onCreateActionMode(m4046b(c0814b), m4042a(menu));
        }

        /* renamed from: a */
        public boolean mo661a(C0814b c0814b, MenuItem menuItem) {
            return this.f2003a.onActionItemClicked(m4046b(c0814b), C0886q.m4325a(this.f2004b, (C0394b) menuItem));
        }

        /* renamed from: b */
        public ActionMode m4046b(C0814b c0814b) {
            int size = this.f2005c.size();
            for (int i = 0; i < size; i++) {
                C0847f c0847f = (C0847f) this.f2005c.get(i);
                if (c0847f != null && c0847f.f2008b == c0814b) {
                    return c0847f;
                }
            }
            ActionMode c0847f2 = new C0847f(this.f2004b, c0814b);
            this.f2005c.add(c0847f2);
            return c0847f2;
        }

        /* renamed from: b */
        public boolean mo662b(C0814b c0814b, Menu menu) {
            return this.f2003a.onPrepareActionMode(m4046b(c0814b), m4042a(menu));
        }
    }

    public C0847f(Context context, C0814b c0814b) {
        this.f2007a = context;
        this.f2008b = c0814b;
    }

    public void finish() {
        this.f2008b.mo687c();
    }

    public View getCustomView() {
        return this.f2008b.mo692i();
    }

    public Menu getMenu() {
        return C0886q.m4324a(this.f2007a, (C0393a) this.f2008b.mo684b());
    }

    public MenuInflater getMenuInflater() {
        return this.f2008b.mo679a();
    }

    public CharSequence getSubtitle() {
        return this.f2008b.mo690g();
    }

    public Object getTag() {
        return this.f2008b.m3869j();
    }

    public CharSequence getTitle() {
        return this.f2008b.mo689f();
    }

    public boolean getTitleOptionalHint() {
        return this.f2008b.m3870k();
    }

    public void invalidate() {
        this.f2008b.mo688d();
    }

    public boolean isTitleOptional() {
        return this.f2008b.mo691h();
    }

    public void setCustomView(View view) {
        this.f2008b.mo681a(view);
    }

    public void setSubtitle(int i) {
        this.f2008b.mo685b(i);
    }

    public void setSubtitle(CharSequence charSequence) {
        this.f2008b.mo682a(charSequence);
    }

    public void setTag(Object obj) {
        this.f2008b.m3858a(obj);
    }

    public void setTitle(int i) {
        this.f2008b.mo680a(i);
    }

    public void setTitle(CharSequence charSequence) {
        this.f2008b.mo686b(charSequence);
    }

    public void setTitleOptionalHint(boolean z) {
        this.f2008b.mo683a(z);
    }
}
