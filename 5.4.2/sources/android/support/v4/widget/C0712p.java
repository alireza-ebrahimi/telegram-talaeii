package android.support.v4.widget;

import android.annotation.TargetApi;
import android.view.View;
import android.widget.ListView;

@TargetApi(9)
/* renamed from: android.support.v4.widget.p */
class C0712p {
    /* renamed from: a */
    static void m3474a(ListView listView, int i) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        if (firstVisiblePosition != -1) {
            View childAt = listView.getChildAt(0);
            if (childAt != null) {
                listView.setSelectionFromTop(firstVisiblePosition, childAt.getTop() - i);
            }
        }
    }
}
