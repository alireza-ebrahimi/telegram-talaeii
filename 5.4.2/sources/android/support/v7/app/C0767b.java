package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertController.C0759a;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;

/* renamed from: android.support.v7.app.b */
public class C0767b extends C0146l implements DialogInterface {
    /* renamed from: a */
    final AlertController f1742a = new AlertController(getContext(), this, getWindow());

    /* renamed from: android.support.v7.app.b$a */
    public static class C0766a {
        /* renamed from: a */
        private final C0759a f1740a;
        /* renamed from: b */
        private final int f1741b;

        public C0766a(Context context) {
            this(context, C0767b.m3641a(context, 0));
        }

        public C0766a(Context context, int i) {
            this.f1740a = new C0759a(new ContextThemeWrapper(context, C0767b.m3641a(context, i)));
            this.f1741b = i;
        }

        /* renamed from: a */
        public Context m3629a() {
            return this.f1740a.f1671a;
        }

        /* renamed from: a */
        public C0766a m3630a(OnCancelListener onCancelListener) {
            this.f1740a.f1686p = onCancelListener;
            return this;
        }

        /* renamed from: a */
        public C0766a m3631a(OnKeyListener onKeyListener) {
            this.f1740a.f1688r = onKeyListener;
            return this;
        }

        /* renamed from: a */
        public C0766a m3632a(Drawable drawable) {
            this.f1740a.f1674d = drawable;
            return this;
        }

        /* renamed from: a */
        public C0766a m3633a(View view) {
            this.f1740a.f1677g = view;
            return this;
        }

        /* renamed from: a */
        public C0766a m3634a(ListAdapter listAdapter, OnClickListener onClickListener) {
            this.f1740a.f1690t = listAdapter;
            this.f1740a.f1691u = onClickListener;
            return this;
        }

        /* renamed from: a */
        public C0766a m3635a(CharSequence charSequence) {
            this.f1740a.f1676f = charSequence;
            return this;
        }

        /* renamed from: a */
        public C0766a m3636a(CharSequence charSequence, OnClickListener onClickListener) {
            this.f1740a.f1679i = charSequence;
            this.f1740a.f1680j = onClickListener;
            return this;
        }

        /* renamed from: a */
        public C0766a m3637a(boolean z) {
            this.f1740a.f1685o = z;
            return this;
        }

        /* renamed from: b */
        public C0766a m3638b(CharSequence charSequence) {
            this.f1740a.f1678h = charSequence;
            return this;
        }

        /* renamed from: b */
        public C0767b m3639b() {
            C0767b c0767b = new C0767b(this.f1740a.f1671a, this.f1741b);
            this.f1740a.m3579a(c0767b.f1742a);
            c0767b.setCancelable(this.f1740a.f1685o);
            if (this.f1740a.f1685o) {
                c0767b.setCanceledOnTouchOutside(true);
            }
            c0767b.setOnCancelListener(this.f1740a.f1686p);
            c0767b.setOnDismissListener(this.f1740a.f1687q);
            if (this.f1740a.f1688r != null) {
                c0767b.setOnKeyListener(this.f1740a.f1688r);
            }
            return c0767b;
        }

        /* renamed from: c */
        public C0767b m3640c() {
            C0767b b = m3639b();
            b.show();
            return b;
        }
    }

    protected C0767b(Context context, int i) {
        super(context, C0767b.m3641a(context, i));
    }

    /* renamed from: a */
    static int m3641a(Context context, int i) {
        if (i >= 16777216) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0738a.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1742a.m3592a();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.f1742a.m3598a(i, keyEvent) ? true : super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.f1742a.m3602b(i, keyEvent) ? true : super.onKeyUp(i, keyEvent);
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.f1742a.m3597a(charSequence);
    }
}
