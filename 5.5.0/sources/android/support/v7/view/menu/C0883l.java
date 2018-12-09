package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.view.C0616d.C0615b;
import android.support.v7.view.menu.C0881k.C0877a;
import android.view.ActionProvider;
import android.view.ActionProvider.VisibilityListener;
import android.view.MenuItem;
import android.view.View;

@TargetApi(16)
/* renamed from: android.support.v7.view.menu.l */
class C0883l extends C0881k {

    /* renamed from: android.support.v7.view.menu.l$a */
    class C0882a extends C0877a implements VisibilityListener {
        /* renamed from: c */
        C0615b f2237c;
        /* renamed from: d */
        final /* synthetic */ C0883l f2238d;

        public C0882a(C0883l c0883l, Context context, ActionProvider actionProvider) {
            this.f2238d = c0883l;
            super(c0883l, context, actionProvider);
        }

        /* renamed from: a */
        public View mo751a(MenuItem menuItem) {
            return this.a.onCreateActionView(menuItem);
        }

        /* renamed from: a */
        public void mo752a(C0615b c0615b) {
            VisibilityListener visibilityListener;
            this.f2237c = c0615b;
            ActionProvider actionProvider = this.a;
            if (c0615b == null) {
                visibilityListener = null;
            }
            actionProvider.setVisibilityListener(visibilityListener);
        }

        /* renamed from: b */
        public boolean mo753b() {
            return this.a.overridesItemVisibility();
        }

        /* renamed from: c */
        public boolean mo754c() {
            return this.a.isVisible();
        }

        public void onActionProviderVisibilityChanged(boolean z) {
            if (this.f2237c != null) {
                this.f2237c.mo742a(z);
            }
        }
    }

    C0883l(Context context, C0394b c0394b) {
        super(context, c0394b);
    }

    /* renamed from: a */
    C0877a mo755a(ActionProvider actionProvider) {
        return new C0882a(this, this.a, actionProvider);
    }
}
