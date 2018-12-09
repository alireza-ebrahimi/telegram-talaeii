package android.support.v4.widget;

import android.widget.ListView;

/* renamed from: android.support.v4.widget.n */
public class C0710n extends C0678a {
    /* renamed from: f */
    private final ListView f1577f;

    public C0710n(ListView listView) {
        super(listView);
        this.f1577f = listView;
    }

    /* renamed from: a */
    public void mo587a(int i, int i2) {
        C0711o.m3473a(this.f1577f, i2);
    }

    /* renamed from: e */
    public boolean mo588e(int i) {
        return false;
    }

    /* renamed from: f */
    public boolean mo589f(int i) {
        ListView listView = this.f1577f;
        int count = listView.getCount();
        if (count == 0) {
            return false;
        }
        int childCount = listView.getChildCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int i2 = firstVisiblePosition + childCount;
        if (i > 0) {
            if (i2 >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                return false;
            }
        } else if (i >= 0) {
            return false;
        } else {
            if (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) {
                return false;
            }
        }
        return true;
    }
}
