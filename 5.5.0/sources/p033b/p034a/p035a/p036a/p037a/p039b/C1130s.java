package p033b.p034a.p035a.p036a.p037a.p039b;

import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ChatActivity;

/* renamed from: b.a.a.a.a.b.s */
public class C1130s {
    /* renamed from: a */
    public static int m6103a(int i) {
        return (i < Callback.DEFAULT_DRAG_ANIMATION_DURATION || i > 299) ? (i < 300 || i > 399) ? (i < ChatActivity.scheduleDownloads || i > 499) ? i >= ChatActivity.startAllServices ? 1 : 1 : 0 : 1 : 0;
    }
}
