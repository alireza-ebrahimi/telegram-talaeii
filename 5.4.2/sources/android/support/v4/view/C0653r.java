package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.MenuItem;
import android.view.View;

@TargetApi(11)
/* renamed from: android.support.v4.view.r */
class C0653r {
    /* renamed from: a */
    public static MenuItem m3195a(MenuItem menuItem, View view) {
        return menuItem.setActionView(view);
    }

    /* renamed from: a */
    public static View m3196a(MenuItem menuItem) {
        return menuItem.getActionView();
    }

    /* renamed from: a */
    public static void m3197a(MenuItem menuItem, int i) {
        menuItem.setShowAsAction(i);
    }

    /* renamed from: b */
    public static MenuItem m3198b(MenuItem menuItem, int i) {
        return menuItem.setActionView(i);
    }
}
