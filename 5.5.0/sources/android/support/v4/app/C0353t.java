package android.support.v4.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.C0236a.C0234a;
import android.support.v4.app.C0314b.C0313a;
import android.support.v4.p022f.C0463k;
import android.support.v4.p022f.C0482l;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: android.support.v4.app.t */
public class C0353t extends C0344o implements C0234a, C0313a {
    /* renamed from: c */
    final Handler f1079c = new C03491(this);
    /* renamed from: d */
    final C0354v f1080d = C0354v.m1548a(new C0351a(this));
    /* renamed from: e */
    boolean f1081e;
    /* renamed from: f */
    boolean f1082f;
    /* renamed from: g */
    boolean f1083g = true;
    /* renamed from: h */
    boolean f1084h = true;
    /* renamed from: i */
    boolean f1085i;
    /* renamed from: j */
    boolean f1086j;
    /* renamed from: k */
    boolean f1087k;
    /* renamed from: l */
    int f1088l;
    /* renamed from: m */
    C0482l<String> f1089m;

    /* renamed from: android.support.v4.app.t$1 */
    class C03491 extends Handler {
        /* renamed from: a */
        final /* synthetic */ C0353t f1064a;

        C03491(C0353t c0353t) {
            this.f1064a = c0353t;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if (this.f1064a.f1083g) {
                        this.f1064a.m1542a(false);
                        return;
                    }
                    return;
                case 2:
                    this.f1064a.a_();
                    this.f1064a.f1080d.m1577n();
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    /* renamed from: android.support.v4.app.t$a */
    class C0351a extends C0350w<C0353t> {
        /* renamed from: a */
        final /* synthetic */ C0353t f1075a;

        public C0351a(C0353t c0353t) {
            this.f1075a = c0353t;
            super(c0353t);
        }

        /* renamed from: a */
        public View mo199a(int i) {
            return this.f1075a.findViewById(i);
        }

        /* renamed from: a */
        public void mo259a(Fragment fragment, Intent intent, int i, Bundle bundle) {
            this.f1075a.m1539a(fragment, intent, i, bundle);
        }

        /* renamed from: a */
        public void mo260a(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
            this.f1075a.m1540a(fragment, intentSender, i, intent, i2, i3, i4, bundle);
        }

        /* renamed from: a */
        public void mo261a(Fragment fragment, String[] strArr, int i) {
            this.f1075a.m1541a(fragment, strArr, i);
        }

        @SuppressLint({"NewApi"})
        /* renamed from: a */
        public void mo262a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            this.f1075a.dump(str, fileDescriptor, printWriter, strArr);
        }

        /* renamed from: a */
        public boolean mo200a() {
            Window window = this.f1075a.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }

        /* renamed from: a */
        public boolean mo263a(Fragment fragment) {
            return !this.f1075a.isFinishing();
        }

        /* renamed from: a */
        public boolean mo264a(String str) {
            return C0236a.m1081a(this.f1075a, str);
        }

        /* renamed from: b */
        public LayoutInflater mo265b() {
            return this.f1075a.getLayoutInflater().cloneInContext(this.f1075a);
        }

        /* renamed from: b */
        public void mo266b(Fragment fragment) {
            this.f1075a.m1538a(fragment);
        }

        /* renamed from: c */
        public C0353t m1528c() {
            return this.f1075a;
        }

        /* renamed from: d */
        public void mo267d() {
            this.f1075a.mo602c();
        }

        /* renamed from: e */
        public boolean mo268e() {
            return this.f1075a.getWindow() != null;
        }

        /* renamed from: f */
        public int mo269f() {
            Window window = this.f1075a.getWindow();
            return window == null ? 0 : window.getAttributes().windowAnimations;
        }

        /* renamed from: g */
        public /* synthetic */ Object mo270g() {
            return m1528c();
        }
    }

    /* renamed from: android.support.v4.app.t$b */
    static final class C0352b {
        /* renamed from: a */
        Object f1076a;
        /* renamed from: b */
        C0367z f1077b;
        /* renamed from: c */
        C0463k<String, ae> f1078c;

        C0352b() {
        }
    }

    /* renamed from: a */
    private static String m1533a(View view) {
        char c = 'F';
        char c2 = '.';
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(view.getClass().getName());
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(view)));
        stringBuilder.append(' ');
        switch (view.getVisibility()) {
            case 0:
                stringBuilder.append('V');
                break;
            case 4:
                stringBuilder.append('I');
                break;
            case 8:
                stringBuilder.append('G');
                break;
            default:
                stringBuilder.append('.');
                break;
        }
        stringBuilder.append(view.isFocusable() ? 'F' : '.');
        stringBuilder.append(view.isEnabled() ? 'E' : '.');
        stringBuilder.append(view.willNotDraw() ? '.' : 'D');
        stringBuilder.append(view.isHorizontalScrollBarEnabled() ? 'H' : '.');
        stringBuilder.append(view.isVerticalScrollBarEnabled() ? 'V' : '.');
        stringBuilder.append(view.isClickable() ? 'C' : '.');
        stringBuilder.append(view.isLongClickable() ? 'L' : '.');
        stringBuilder.append(' ');
        if (!view.isFocused()) {
            c = '.';
        }
        stringBuilder.append(c);
        stringBuilder.append(view.isSelected() ? 'S' : '.');
        if (view.isPressed()) {
            c2 = 'P';
        }
        stringBuilder.append(c2);
        stringBuilder.append(' ');
        stringBuilder.append(view.getLeft());
        stringBuilder.append(',');
        stringBuilder.append(view.getTop());
        stringBuilder.append('-');
        stringBuilder.append(view.getRight());
        stringBuilder.append(',');
        stringBuilder.append(view.getBottom());
        int id = view.getId();
        if (id != -1) {
            stringBuilder.append(" #");
            stringBuilder.append(Integer.toHexString(id));
            Resources resources = view.getResources();
            if (!(id == 0 || resources == null)) {
                String str;
                switch (Theme.ACTION_BAR_VIDEO_EDIT_COLOR & id) {
                    case 16777216:
                        str = "android";
                        break;
                    case Theme.ACTION_BAR_PHOTO_VIEWER_COLOR /*2130706432*/:
                        str = "app";
                        break;
                    default:
                        try {
                            str = resources.getResourcePackageName(id);
                            break;
                        } catch (NotFoundException e) {
                            break;
                        }
                }
                String resourceTypeName = resources.getResourceTypeName(id);
                String resourceEntryName = resources.getResourceEntryName(id);
                stringBuilder.append(" ");
                stringBuilder.append(str);
                stringBuilder.append(":");
                stringBuilder.append(resourceTypeName);
                stringBuilder.append("/");
                stringBuilder.append(resourceEntryName);
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /* renamed from: a */
    private void m1534a(String str, PrintWriter printWriter, View view) {
        printWriter.print(str);
        if (view == null) {
            printWriter.println("null");
            return;
        }
        printWriter.println(C0353t.m1533a(view));
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            if (childCount > 0) {
                String str2 = str + "  ";
                for (int i = 0; i < childCount; i++) {
                    m1534a(str2, printWriter, viewGroup.getChildAt(i));
                }
            }
        }
    }

    /* renamed from: b */
    private int m1535b(Fragment fragment) {
        if (this.f1089m.m2042b() >= 65534) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (this.f1089m.m2050f(this.f1088l) >= 0) {
            this.f1088l = (this.f1088l + 1) % 65534;
        }
        int i = this.f1088l;
        this.f1089m.m2044b(i, fragment.mWho);
        this.f1088l = (this.f1088l + 1) % 65534;
        return i;
    }

    /* renamed from: a */
    final View mo271a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.f1080d.m1551a(view, str, context, attributeSet);
    }

    /* renamed from: a */
    public final void mo272a(int i) {
        if (!this.f1087k && i != -1) {
            C0342m.m1482b(i);
        }
    }

    /* renamed from: a */
    public void m1538a(Fragment fragment) {
    }

    /* renamed from: a */
    public void m1539a(Fragment fragment, Intent intent, int i, Bundle bundle) {
        this.b = true;
        if (i == -1) {
            try {
                C0236a.m1078a(this, intent, -1, bundle);
            } finally {
                this.b = false;
            }
        } else {
            C0342m.m1482b(i);
            C0236a.m1078a(this, intent, ((m1535b(fragment) + 1) << 16) + (65535 & i), bundle);
            this.b = false;
        }
    }

    /* renamed from: a */
    public void m1540a(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        this.a = true;
        if (i == -1) {
            try {
                C0236a.m1079a(this, intentSender, i, intent, i2, i3, i4, bundle);
            } finally {
                this.a = false;
            }
        } else {
            C0342m.m1482b(i);
            C0236a.m1079a(this, intentSender, ((m1535b(fragment) + 1) << 16) + (65535 & i), intent, i2, i3, i4, bundle);
            this.a = false;
        }
    }

    /* renamed from: a */
    void m1541a(Fragment fragment, String[] strArr, int i) {
        if (i == -1) {
            C0236a.m1080a(this, strArr, i);
            return;
        }
        C0342m.m1482b(i);
        try {
            this.f1087k = true;
            C0236a.m1080a(this, strArr, ((m1535b(fragment) + 1) << 16) + (65535 & i));
        } finally {
            this.f1087k = false;
        }
    }

    /* renamed from: a */
    void m1542a(boolean z) {
        if (!this.f1084h) {
            this.f1084h = true;
            this.f1085i = z;
            this.f1079c.removeMessages(1);
            m1546d();
        } else if (z) {
            this.f1080d.m1578o();
            this.f1080d.m1566c(true);
        }
    }

    /* renamed from: a */
    protected boolean m1543a(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    protected void a_() {
        this.f1080d.m1571h();
    }

    /* renamed from: b */
    public Object m1544b() {
        return null;
    }

    /* renamed from: c */
    public void mo602c() {
        if (VERSION.SDK_INT >= 11) {
            C0324c.m1432a(this);
        } else {
            this.f1086j = true;
        }
    }

    /* renamed from: d */
    void m1546d() {
        this.f1080d.m1566c(this.f1085i);
        this.f1080d.m1574k();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2;
        if (VERSION.SDK_INT >= 11) {
            printWriter.print(str);
            printWriter.print("Local FragmentActivity ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(" State:");
            str2 = str + "  ";
            printWriter.print(str2);
            printWriter.print("mCreated=");
            printWriter.print(this.f1081e);
            printWriter.print("mResumed=");
            printWriter.print(this.f1082f);
            printWriter.print(" mStopped=");
            printWriter.print(this.f1083g);
            printWriter.print(" mReallyStopped=");
            printWriter.println(this.f1084h);
            this.f1080d.m1556a(str2, fileDescriptor, printWriter, strArr);
            this.f1080d.m1550a().mo287a(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.println("View Hierarchy:");
            m1534a(str + "  ", printWriter, getWindow().getDecorView());
        } else {
            printWriter.print(str);
            printWriter.print("Local FragmentActivity ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(" State:");
            str2 = str + "  ";
            printWriter.print(str2);
            printWriter.print("mCreated=");
            printWriter.print(this.f1081e);
            printWriter.print("mResumed=");
            printWriter.print(this.f1082f);
            printWriter.print(" mStopped=");
            printWriter.print(this.f1083g);
            printWriter.print(" mReallyStopped=");
            printWriter.println(this.f1084h);
            this.f1080d.m1556a(str2, fileDescriptor, printWriter, strArr);
            this.f1080d.m1550a().mo287a(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.println("View Hierarchy:");
            m1534a(str + "  ", printWriter, getWindow().getDecorView());
        }
    }

    /* renamed from: e */
    public C0357x m1547e() {
        return this.f1080d.m1550a();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        this.f1080d.m1561b();
        int i3 = i >> 16;
        if (i3 != 0) {
            int i4 = i3 - 1;
            String str = (String) this.f1089m.m2040a(i4);
            this.f1089m.m2046c(i4);
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a = this.f1080d.m1549a(str);
            if (a == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + str);
                return;
            } else {
                a.onActivityResult(65535 & i, i2, intent);
                return;
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    public void onBackPressed() {
        if (!this.f1080d.m1550a().mo288b()) {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.f1080d.m1552a(configuration);
    }

    protected void onCreate(Bundle bundle) {
        this.f1080d.m1554a(null);
        super.onCreate(bundle);
        C0352b c0352b = (C0352b) getLastNonConfigurationInstance();
        if (c0352b != null) {
            this.f1080d.m1555a(c0352b.f1078c);
        }
        if (bundle != null) {
            this.f1080d.m1553a(bundle.getParcelable("android:support:fragments"), c0352b != null ? c0352b.f1077b : null);
            if (bundle.containsKey("android:support:next_request_index")) {
                this.f1088l = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.f1089m = new C0482l(intArray.length);
                    for (int i = 0; i < intArray.length; i++) {
                        this.f1089m.m2044b(intArray[i], stringArray[i]);
                    }
                }
            }
        }
        if (this.f1089m == null) {
            this.f1089m = new C0482l();
            this.f1088l = 0;
        }
        this.f1080d.m1568e();
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return super.onCreatePanelMenu(i, menu);
        }
        return VERSION.SDK_INT >= 11 ? super.onCreatePanelMenu(i, menu) | this.f1080d.m1559a(menu, getMenuInflater()) : true;
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onDestroy() {
        super.onDestroy();
        m1542a(false);
        this.f1080d.m1575l();
        this.f1080d.m1579p();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.f1080d.m1576m();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        switch (i) {
            case 0:
                return this.f1080d.m1560a(menuItem);
            case 6:
                return this.f1080d.m1564b(menuItem);
            default:
                return false;
        }
    }

    public void onMultiWindowModeChanged(boolean z) {
        this.f1080d.m1557a(z);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.f1080d.m1561b();
    }

    public void onPanelClosed(int i, Menu menu) {
        switch (i) {
            case 0:
                this.f1080d.m1562b(menu);
                break;
        }
        super.onPanelClosed(i, menu);
    }

    protected void onPause() {
        super.onPause();
        this.f1082f = false;
        if (this.f1079c.hasMessages(2)) {
            this.f1079c.removeMessages(2);
            a_();
        }
        this.f1080d.m1572i();
    }

    public void onPictureInPictureModeChanged(boolean z) {
        this.f1080d.m1563b(z);
    }

    protected void onPostResume() {
        super.onPostResume();
        this.f1079c.removeMessages(2);
        a_();
        this.f1080d.m1577n();
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0 || menu == null) {
            return super.onPreparePanel(i, view, menu);
        }
        if (this.f1086j) {
            this.f1086j = false;
            menu.clear();
            onCreatePanelMenu(i, menu);
        }
        return m1543a(view, menu) | this.f1080d.m1558a(menu);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        int i2 = (i >> 16) & 65535;
        if (i2 != 0) {
            int i3 = i2 - 1;
            String str = (String) this.f1089m.m2040a(i3);
            this.f1089m.m2046c(i3);
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment a = this.f1080d.m1549a(str);
            if (a == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: " + str);
            } else {
                a.onRequestPermissionsResult(i & 65535, strArr, iArr);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        this.f1079c.sendEmptyMessage(2);
        this.f1082f = true;
        this.f1080d.m1577n();
    }

    public final Object onRetainNonConfigurationInstance() {
        if (this.f1083g) {
            m1542a(true);
        }
        Object b = m1544b();
        C0367z d = this.f1080d.m1567d();
        C0463k r = this.f1080d.m1581r();
        if (d == null && r == null && b == null) {
            return null;
        }
        C0352b c0352b = new C0352b();
        c0352b.f1076a = b;
        c0352b.f1077b = d;
        c0352b.f1078c = r;
        return c0352b;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Parcelable c = this.f1080d.m1565c();
        if (c != null) {
            bundle.putParcelable("android:support:fragments", c);
        }
        if (this.f1089m.m2042b() > 0) {
            bundle.putInt("android:support:next_request_index", this.f1088l);
            int[] iArr = new int[this.f1089m.m2042b()];
            String[] strArr = new String[this.f1089m.m2042b()];
            for (int i = 0; i < this.f1089m.m2042b(); i++) {
                iArr[i] = this.f1089m.m2048d(i);
                strArr[i] = (String) this.f1089m.m2049e(i);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    protected void onStart() {
        super.onStart();
        this.f1083g = false;
        this.f1084h = false;
        this.f1079c.removeMessages(1);
        if (!this.f1081e) {
            this.f1081e = true;
            this.f1080d.m1569f();
        }
        this.f1080d.m1561b();
        this.f1080d.m1577n();
        this.f1080d.m1578o();
        this.f1080d.m1570g();
        this.f1080d.m1580q();
    }

    public void onStateNotSaved() {
        this.f1080d.m1561b();
    }

    protected void onStop() {
        super.onStop();
        this.f1083g = true;
        this.f1079c.sendEmptyMessage(1);
        this.f1080d.m1573j();
    }

    public void startActivityForResult(Intent intent, int i) {
        if (!(this.b || i == -1)) {
            C0342m.m1482b(i);
        }
        super.startActivityForResult(intent, i);
    }

    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i, Bundle bundle) {
        super.startActivityForResult(intent, i, bundle);
    }

    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }
}
