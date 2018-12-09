package android.support.design.widget;

import android.widget.ImageButton;

class af extends ImageButton {
    /* renamed from: a */
    private int f341a;

    /* renamed from: a */
    final void m582a(int i, boolean z) {
        super.setVisibility(i);
        if (z) {
            this.f341a = i;
        }
    }

    final int getUserSetVisibility() {
        return this.f341a;
    }

    public void setVisibility(int i) {
        m582a(i, true);
    }
}
