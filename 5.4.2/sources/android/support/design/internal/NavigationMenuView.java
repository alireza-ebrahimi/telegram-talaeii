package android.support.design.internal;

import android.content.Context;
import android.support.v7.view.menu.C0079p;
import android.support.v7.view.menu.C0873h;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class NavigationMenuView extends RecyclerView implements C0079p {
    public NavigationMenuView(Context context) {
        this(context, null);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutManager(new LinearLayoutManager(context, 1, false));
    }

    /* renamed from: a */
    public void mo54a(C0873h c0873h) {
    }

    public int getWindowAnimations() {
        return 0;
    }
}
