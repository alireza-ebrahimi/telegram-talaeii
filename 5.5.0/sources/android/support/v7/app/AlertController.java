package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ah;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.C0665b;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0747j;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;

class AlertController {
    /* renamed from: A */
    private boolean f1698A = false;
    /* renamed from: B */
    private CharSequence f1699B;
    /* renamed from: C */
    private CharSequence f1700C;
    /* renamed from: D */
    private CharSequence f1701D;
    /* renamed from: E */
    private int f1702E = 0;
    /* renamed from: F */
    private Drawable f1703F;
    /* renamed from: G */
    private ImageView f1704G;
    /* renamed from: H */
    private TextView f1705H;
    /* renamed from: I */
    private TextView f1706I;
    /* renamed from: J */
    private View f1707J;
    /* renamed from: K */
    private int f1708K;
    /* renamed from: L */
    private int f1709L;
    /* renamed from: M */
    private boolean f1710M;
    /* renamed from: N */
    private int f1711N = 0;
    /* renamed from: O */
    private final OnClickListener f1712O = new C07491(this);
    /* renamed from: a */
    final C0146l f1713a;
    /* renamed from: b */
    ListView f1714b;
    /* renamed from: c */
    Button f1715c;
    /* renamed from: d */
    Message f1716d;
    /* renamed from: e */
    Button f1717e;
    /* renamed from: f */
    Message f1718f;
    /* renamed from: g */
    Button f1719g;
    /* renamed from: h */
    Message f1720h;
    /* renamed from: i */
    NestedScrollView f1721i;
    /* renamed from: j */
    ListAdapter f1722j;
    /* renamed from: k */
    int f1723k = -1;
    /* renamed from: l */
    int f1724l;
    /* renamed from: m */
    int f1725m;
    /* renamed from: n */
    int f1726n;
    /* renamed from: o */
    int f1727o;
    /* renamed from: p */
    Handler f1728p;
    /* renamed from: q */
    private final Context f1729q;
    /* renamed from: r */
    private final Window f1730r;
    /* renamed from: s */
    private CharSequence f1731s;
    /* renamed from: t */
    private CharSequence f1732t;
    /* renamed from: u */
    private View f1733u;
    /* renamed from: v */
    private int f1734v;
    /* renamed from: w */
    private int f1735w;
    /* renamed from: x */
    private int f1736x;
    /* renamed from: y */
    private int f1737y;
    /* renamed from: z */
    private int f1738z;

    /* renamed from: android.support.v7.app.AlertController$1 */
    class C07491 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ AlertController f1631a;

        C07491(AlertController alertController) {
            this.f1631a = alertController;
        }

        public void onClick(View view) {
            Message obtain = (view != this.f1631a.f1715c || this.f1631a.f1716d == null) ? (view != this.f1631a.f1717e || this.f1631a.f1718f == null) ? (view != this.f1631a.f1719g || this.f1631a.f1720h == null) ? null : Message.obtain(this.f1631a.f1720h) : Message.obtain(this.f1631a.f1718f) : Message.obtain(this.f1631a.f1716d);
            if (obtain != null) {
                obtain.sendToTarget();
            }
            this.f1631a.f1728p.obtainMessage(1, this.f1631a.f1713a).sendToTarget();
        }
    }

    public static class RecycleListView extends ListView {
        /* renamed from: a */
        private final int f1644a;
        /* renamed from: b */
        private final int f1645b;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.RecycleListView);
            this.f1645b = obtainStyledAttributes.getDimensionPixelOffset(C0747j.RecycleListView_paddingBottomNoButtons, -1);
            this.f1644a = obtainStyledAttributes.getDimensionPixelOffset(C0747j.RecycleListView_paddingTopNoTitle, -1);
        }

        /* renamed from: a */
        public void m3576a(boolean z, boolean z2) {
            if (!z2 || !z) {
                setPadding(getPaddingLeft(), z ? getPaddingTop() : this.f1644a, getPaddingRight(), z2 ? getPaddingBottom() : this.f1645b);
            }
        }
    }

    /* renamed from: android.support.v7.app.AlertController$a */
    public static class C0759a {
        /* renamed from: A */
        public int f1658A;
        /* renamed from: B */
        public boolean f1659B = false;
        /* renamed from: C */
        public boolean[] f1660C;
        /* renamed from: D */
        public boolean f1661D;
        /* renamed from: E */
        public boolean f1662E;
        /* renamed from: F */
        public int f1663F = -1;
        /* renamed from: G */
        public OnMultiChoiceClickListener f1664G;
        /* renamed from: H */
        public Cursor f1665H;
        /* renamed from: I */
        public String f1666I;
        /* renamed from: J */
        public String f1667J;
        /* renamed from: K */
        public OnItemSelectedListener f1668K;
        /* renamed from: L */
        public C0758a f1669L;
        /* renamed from: M */
        public boolean f1670M = true;
        /* renamed from: a */
        public final Context f1671a;
        /* renamed from: b */
        public final LayoutInflater f1672b;
        /* renamed from: c */
        public int f1673c = 0;
        /* renamed from: d */
        public Drawable f1674d;
        /* renamed from: e */
        public int f1675e = 0;
        /* renamed from: f */
        public CharSequence f1676f;
        /* renamed from: g */
        public View f1677g;
        /* renamed from: h */
        public CharSequence f1678h;
        /* renamed from: i */
        public CharSequence f1679i;
        /* renamed from: j */
        public DialogInterface.OnClickListener f1680j;
        /* renamed from: k */
        public CharSequence f1681k;
        /* renamed from: l */
        public DialogInterface.OnClickListener f1682l;
        /* renamed from: m */
        public CharSequence f1683m;
        /* renamed from: n */
        public DialogInterface.OnClickListener f1684n;
        /* renamed from: o */
        public boolean f1685o;
        /* renamed from: p */
        public OnCancelListener f1686p;
        /* renamed from: q */
        public OnDismissListener f1687q;
        /* renamed from: r */
        public OnKeyListener f1688r;
        /* renamed from: s */
        public CharSequence[] f1689s;
        /* renamed from: t */
        public ListAdapter f1690t;
        /* renamed from: u */
        public DialogInterface.OnClickListener f1691u;
        /* renamed from: v */
        public int f1692v;
        /* renamed from: w */
        public View f1693w;
        /* renamed from: x */
        public int f1694x;
        /* renamed from: y */
        public int f1695y;
        /* renamed from: z */
        public int f1696z;

        /* renamed from: android.support.v7.app.AlertController$a$a */
        public interface C0758a {
            /* renamed from: a */
            void m3577a(ListView listView);
        }

        public C0759a(Context context) {
            this.f1671a = context;
            this.f1685o = true;
            this.f1672b = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        /* renamed from: b */
        private void m3578b(final AlertController alertController) {
            ListAdapter simpleCursorAdapter;
            final RecycleListView recycleListView = (RecycleListView) this.f1672b.inflate(alertController.f1724l, null);
            if (!this.f1661D) {
                int i = this.f1662E ? alertController.f1726n : alertController.f1727o;
                simpleCursorAdapter = this.f1665H != null ? new SimpleCursorAdapter(this.f1671a, i, this.f1665H, new String[]{this.f1666I}, new int[]{16908308}) : this.f1690t != null ? this.f1690t : new C0761c(this.f1671a, i, 16908308, this.f1689s);
            } else if (this.f1665H == null) {
                simpleCursorAdapter = new ArrayAdapter<CharSequence>(this, this.f1671a, alertController.f1725m, 16908308, this.f1689s) {
                    /* renamed from: b */
                    final /* synthetic */ C0759a f1647b;

                    public View getView(int i, View view, ViewGroup viewGroup) {
                        View view2 = super.getView(i, view, viewGroup);
                        if (this.f1647b.f1660C != null && this.f1647b.f1660C[i]) {
                            recycleListView.setItemChecked(i, true);
                        }
                        return view2;
                    }
                };
            } else {
                final AlertController alertController2 = alertController;
                Object c07552 = new CursorAdapter(this, this.f1671a, this.f1665H, false) {
                    /* renamed from: c */
                    final /* synthetic */ C0759a f1650c;
                    /* renamed from: d */
                    private final int f1651d;
                    /* renamed from: e */
                    private final int f1652e;

                    public void bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView) view.findViewById(16908308)).setText(cursor.getString(this.f1651d));
                        recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.f1652e) == 1);
                    }

                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return this.f1650c.f1672b.inflate(alertController2.f1725m, viewGroup, false);
                    }
                };
            }
            if (this.f1669L != null) {
                this.f1669L.m3577a(recycleListView);
            }
            alertController.f1722j = simpleCursorAdapter;
            alertController.f1723k = this.f1663F;
            if (this.f1691u != null) {
                recycleListView.setOnItemClickListener(new OnItemClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C0759a f1654b;

                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        this.f1654b.f1691u.onClick(alertController.f1713a, i);
                        if (!this.f1654b.f1662E) {
                            alertController.f1713a.dismiss();
                        }
                    }
                });
            } else if (this.f1664G != null) {
                recycleListView.setOnItemClickListener(new OnItemClickListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ C0759a f1657c;

                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (this.f1657c.f1660C != null) {
                            this.f1657c.f1660C[i] = recycleListView.isItemChecked(i);
                        }
                        this.f1657c.f1664G.onClick(alertController.f1713a, i, recycleListView.isItemChecked(i));
                    }
                });
            }
            if (this.f1668K != null) {
                recycleListView.setOnItemSelectedListener(this.f1668K);
            }
            if (this.f1662E) {
                recycleListView.setChoiceMode(1);
            } else if (this.f1661D) {
                recycleListView.setChoiceMode(2);
            }
            alertController.f1714b = recycleListView;
        }

        /* renamed from: a */
        public void m3579a(AlertController alertController) {
            if (this.f1677g != null) {
                alertController.m3600b(this.f1677g);
            } else {
                if (this.f1676f != null) {
                    alertController.m3597a(this.f1676f);
                }
                if (this.f1674d != null) {
                    alertController.m3595a(this.f1674d);
                }
                if (this.f1673c != 0) {
                    alertController.m3599b(this.f1673c);
                }
                if (this.f1675e != 0) {
                    alertController.m3599b(alertController.m3603c(this.f1675e));
                }
            }
            if (this.f1678h != null) {
                alertController.m3601b(this.f1678h);
            }
            if (this.f1679i != null) {
                alertController.m3594a(-1, this.f1679i, this.f1680j, null);
            }
            if (this.f1681k != null) {
                alertController.m3594a(-2, this.f1681k, this.f1682l, null);
            }
            if (this.f1683m != null) {
                alertController.m3594a(-3, this.f1683m, this.f1684n, null);
            }
            if (!(this.f1689s == null && this.f1665H == null && this.f1690t == null)) {
                m3578b(alertController);
            }
            if (this.f1693w != null) {
                if (this.f1659B) {
                    alertController.m3596a(this.f1693w, this.f1694x, this.f1695y, this.f1696z, this.f1658A);
                    return;
                }
                alertController.m3604c(this.f1693w);
            } else if (this.f1692v != 0) {
                alertController.m3593a(this.f1692v);
            }
        }
    }

    /* renamed from: android.support.v7.app.AlertController$b */
    private static final class C0760b extends Handler {
        /* renamed from: a */
        private WeakReference<DialogInterface> f1697a;

        public C0760b(DialogInterface dialogInterface) {
            this.f1697a = new WeakReference(dialogInterface);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) this.f1697a.get(), message.what);
                    return;
                case 1:
                    ((DialogInterface) message.obj).dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: android.support.v7.app.AlertController$c */
    private static class C0761c extends ArrayAdapter<CharSequence> {
        public C0761c(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean hasStableIds() {
            return true;
        }
    }

    public AlertController(Context context, C0146l c0146l, Window window) {
        this.f1729q = context;
        this.f1713a = c0146l;
        this.f1730r = window;
        this.f1728p = new C0760b(c0146l);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, C0747j.AlertDialog, C0738a.alertDialogStyle, 0);
        this.f1708K = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_android_layout, 0);
        this.f1709L = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_buttonPanelSideLayout, 0);
        this.f1724l = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_listLayout, 0);
        this.f1725m = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_multiChoiceItemLayout, 0);
        this.f1726n = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_singleChoiceItemLayout, 0);
        this.f1727o = obtainStyledAttributes.getResourceId(C0747j.AlertDialog_listItemLayout, 0);
        this.f1710M = obtainStyledAttributes.getBoolean(C0747j.AlertDialog_showTitle, true);
        obtainStyledAttributes.recycle();
        c0146l.m685a(1);
    }

    /* renamed from: a */
    private ViewGroup m3580a(View view, View view2) {
        if (view == null) {
            return (ViewGroup) (view2 instanceof ViewStub ? ((ViewStub) view2).inflate() : view2);
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        return (ViewGroup) (view instanceof ViewStub ? ((ViewStub) view).inflate() : view);
    }

    /* renamed from: a */
    static void m3581a(View view, View view2, View view3) {
        int i = 0;
        if (view2 != null) {
            view2.setVisibility(ah.m2798b(view, -1) ? 0 : 4);
        }
        if (view3 != null) {
            if (!ah.m2798b(view, 1)) {
                i = 4;
            }
            view3.setVisibility(i);
        }
    }

    /* renamed from: a */
    private void m3582a(ViewGroup viewGroup) {
        boolean z = false;
        View inflate = this.f1733u != null ? this.f1733u : this.f1734v != 0 ? LayoutInflater.from(this.f1729q).inflate(this.f1734v, viewGroup, false) : null;
        if (inflate != null) {
            z = true;
        }
        if (!(z && m3586a(inflate))) {
            this.f1730r.setFlags(131072, 131072);
        }
        if (z) {
            FrameLayout frameLayout = (FrameLayout) this.f1730r.findViewById(C0743f.custom);
            frameLayout.addView(inflate, new LayoutParams(-1, -1));
            if (this.f1698A) {
                frameLayout.setPadding(this.f1735w, this.f1736x, this.f1737y, this.f1738z);
            }
            if (this.f1714b != null) {
                ((LinearLayout.LayoutParams) viewGroup.getLayoutParams()).weight = BitmapDescriptorFactory.HUE_RED;
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    /* renamed from: a */
    private void m3583a(ViewGroup viewGroup, View view, int i, int i2) {
        View view2 = null;
        View findViewById = this.f1730r.findViewById(C0743f.scrollIndicatorUp);
        View findViewById2 = this.f1730r.findViewById(C0743f.scrollIndicatorDown);
        if (VERSION.SDK_INT >= 23) {
            ah.m2776a(view, i, i2);
            if (findViewById != null) {
                viewGroup.removeView(findViewById);
            }
            if (findViewById2 != null) {
                viewGroup.removeView(findViewById2);
                return;
            }
            return;
        }
        if (findViewById != null && (i & 1) == 0) {
            viewGroup.removeView(findViewById);
            findViewById = null;
        }
        if (findViewById2 == null || (i & 2) != 0) {
            view2 = findViewById2;
        } else {
            viewGroup.removeView(findViewById2);
        }
        if (findViewById != null || view2 != null) {
            if (this.f1732t != null) {
                this.f1721i.setOnScrollChangeListener(new C0665b(this) {
                    /* renamed from: c */
                    final /* synthetic */ AlertController f1634c;

                    /* renamed from: a */
                    public void mo599a(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                        AlertController.m3581a(nestedScrollView, findViewById, view2);
                    }
                });
                this.f1721i.post(new Runnable(this) {
                    /* renamed from: c */
                    final /* synthetic */ AlertController f1637c;

                    public void run() {
                        AlertController.m3581a(this.f1637c.f1721i, findViewById, view2);
                    }
                });
            } else if (this.f1714b != null) {
                this.f1714b.setOnScrollListener(new OnScrollListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ AlertController f1640c;

                    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                        AlertController.m3581a(absListView, findViewById, view2);
                    }

                    public void onScrollStateChanged(AbsListView absListView, int i) {
                    }
                });
                this.f1714b.post(new Runnable(this) {
                    /* renamed from: c */
                    final /* synthetic */ AlertController f1643c;

                    public void run() {
                        AlertController.m3581a(this.f1643c.f1714b, findViewById, view2);
                    }
                });
            } else {
                if (findViewById != null) {
                    viewGroup.removeView(findViewById);
                }
                if (view2 != null) {
                    viewGroup.removeView(view2);
                }
            }
        }
    }

    /* renamed from: a */
    private void m3584a(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    /* renamed from: a */
    private static boolean m3585a(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0738a.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    /* renamed from: a */
    static boolean m3586a(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (m3586a(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private int m3587b() {
        return this.f1709L == 0 ? this.f1708K : this.f1711N == 1 ? this.f1709L : this.f1708K;
    }

    /* renamed from: b */
    private void m3588b(ViewGroup viewGroup) {
        if (this.f1707J != null) {
            viewGroup.addView(this.f1707J, 0, new LayoutParams(-1, -2));
            this.f1730r.findViewById(C0743f.title_template).setVisibility(8);
            return;
        }
        this.f1704G = (ImageView) this.f1730r.findViewById(16908294);
        if ((!TextUtils.isEmpty(this.f1731s) ? 1 : 0) == 0 || !this.f1710M) {
            this.f1730r.findViewById(C0743f.title_template).setVisibility(8);
            this.f1704G.setVisibility(8);
            viewGroup.setVisibility(8);
            return;
        }
        this.f1705H = (TextView) this.f1730r.findViewById(C0743f.alertTitle);
        this.f1705H.setText(this.f1731s);
        if (this.f1702E != 0) {
            this.f1704G.setImageResource(this.f1702E);
        } else if (this.f1703F != null) {
            this.f1704G.setImageDrawable(this.f1703F);
        } else {
            this.f1705H.setPadding(this.f1704G.getPaddingLeft(), this.f1704G.getPaddingTop(), this.f1704G.getPaddingRight(), this.f1704G.getPaddingBottom());
            this.f1704G.setVisibility(8);
        }
    }

    /* renamed from: c */
    private void m3589c() {
        View findViewById = this.f1730r.findViewById(C0743f.parentPanel);
        View findViewById2 = findViewById.findViewById(C0743f.topPanel);
        View findViewById3 = findViewById.findViewById(C0743f.contentPanel);
        View findViewById4 = findViewById.findViewById(C0743f.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) findViewById.findViewById(C0743f.customPanel);
        m3582a(viewGroup);
        View findViewById5 = viewGroup.findViewById(C0743f.topPanel);
        View findViewById6 = viewGroup.findViewById(C0743f.contentPanel);
        View findViewById7 = viewGroup.findViewById(C0743f.buttonPanel);
        ViewGroup a = m3580a(findViewById5, findViewById2);
        ViewGroup a2 = m3580a(findViewById6, findViewById3);
        ViewGroup a3 = m3580a(findViewById7, findViewById4);
        m3590c(a2);
        m3591d(a3);
        m3588b(a);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (a == null || a.getVisibility() == 8) ? false : true;
        boolean z3 = (a3 == null || a3.getVisibility() == 8) ? false : true;
        if (!(z3 || a2 == null)) {
            findViewById = a2.findViewById(C0743f.textSpacerNoButtons);
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
        }
        if (z2) {
            if (this.f1721i != null) {
                this.f1721i.setClipToPadding(true);
            }
            findViewById = null;
            if (!((this.f1732t == null && this.f1714b == null && !z) || z)) {
                findViewById = a.findViewById(C0743f.titleDividerNoCustom);
            }
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
        } else if (a2 != null) {
            findViewById = a2.findViewById(C0743f.textSpacerNoTitle);
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
        }
        if (this.f1714b instanceof RecycleListView) {
            ((RecycleListView) this.f1714b).m3576a(z2, z3);
        }
        if (!z) {
            findViewById3 = this.f1714b != null ? this.f1714b : this.f1721i;
            if (findViewById3 != null) {
                m3583a(a2, findViewById3, (z3 ? 2 : 0) | (z2 ? 1 : 0), 3);
            }
        }
        ListView listView = this.f1714b;
        if (listView != null && this.f1722j != null) {
            listView.setAdapter(this.f1722j);
            int i = this.f1723k;
            if (i > -1) {
                listView.setItemChecked(i, true);
                listView.setSelection(i);
            }
        }
    }

    /* renamed from: c */
    private void m3590c(ViewGroup viewGroup) {
        this.f1721i = (NestedScrollView) this.f1730r.findViewById(C0743f.scrollView);
        this.f1721i.setFocusable(false);
        this.f1721i.setNestedScrollingEnabled(false);
        this.f1706I = (TextView) viewGroup.findViewById(16908299);
        if (this.f1706I != null) {
            if (this.f1732t != null) {
                this.f1706I.setText(this.f1732t);
                return;
            }
            this.f1706I.setVisibility(8);
            this.f1721i.removeView(this.f1706I);
            if (this.f1714b != null) {
                ViewGroup viewGroup2 = (ViewGroup) this.f1721i.getParent();
                int indexOfChild = viewGroup2.indexOfChild(this.f1721i);
                viewGroup2.removeViewAt(indexOfChild);
                viewGroup2.addView(this.f1714b, indexOfChild, new LayoutParams(-1, -1));
                return;
            }
            viewGroup.setVisibility(8);
        }
    }

    /* renamed from: d */
    private void m3591d(ViewGroup viewGroup) {
        int i;
        int i2 = 1;
        this.f1715c = (Button) viewGroup.findViewById(16908313);
        this.f1715c.setOnClickListener(this.f1712O);
        if (TextUtils.isEmpty(this.f1699B)) {
            this.f1715c.setVisibility(8);
            i = 0;
        } else {
            this.f1715c.setText(this.f1699B);
            this.f1715c.setVisibility(0);
            i = 1;
        }
        this.f1717e = (Button) viewGroup.findViewById(16908314);
        this.f1717e.setOnClickListener(this.f1712O);
        if (TextUtils.isEmpty(this.f1700C)) {
            this.f1717e.setVisibility(8);
        } else {
            this.f1717e.setText(this.f1700C);
            this.f1717e.setVisibility(0);
            i |= 2;
        }
        this.f1719g = (Button) viewGroup.findViewById(16908315);
        this.f1719g.setOnClickListener(this.f1712O);
        if (TextUtils.isEmpty(this.f1701D)) {
            this.f1719g.setVisibility(8);
        } else {
            this.f1719g.setText(this.f1701D);
            this.f1719g.setVisibility(0);
            i |= 4;
        }
        if (m3585a(this.f1729q)) {
            if (i == 1) {
                m3584a(this.f1715c);
            } else if (i == 2) {
                m3584a(this.f1717e);
            } else if (i == 4) {
                m3584a(this.f1719g);
            }
        }
        if (i == 0) {
            i2 = 0;
        }
        if (i2 == 0) {
            viewGroup.setVisibility(8);
        }
    }

    /* renamed from: a */
    public void m3592a() {
        this.f1713a.setContentView(m3587b());
        m3589c();
    }

    /* renamed from: a */
    public void m3593a(int i) {
        this.f1733u = null;
        this.f1734v = i;
        this.f1698A = false;
    }

    /* renamed from: a */
    public void m3594a(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.f1728p.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case -3:
                this.f1701D = charSequence;
                this.f1720h = message;
                return;
            case -2:
                this.f1700C = charSequence;
                this.f1718f = message;
                return;
            case -1:
                this.f1699B = charSequence;
                this.f1716d = message;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    /* renamed from: a */
    public void m3595a(Drawable drawable) {
        this.f1703F = drawable;
        this.f1702E = 0;
        if (this.f1704G == null) {
            return;
        }
        if (drawable != null) {
            this.f1704G.setVisibility(0);
            this.f1704G.setImageDrawable(drawable);
            return;
        }
        this.f1704G.setVisibility(8);
    }

    /* renamed from: a */
    public void m3596a(View view, int i, int i2, int i3, int i4) {
        this.f1733u = view;
        this.f1734v = 0;
        this.f1698A = true;
        this.f1735w = i;
        this.f1736x = i2;
        this.f1737y = i3;
        this.f1738z = i4;
    }

    /* renamed from: a */
    public void m3597a(CharSequence charSequence) {
        this.f1731s = charSequence;
        if (this.f1705H != null) {
            this.f1705H.setText(charSequence);
        }
    }

    /* renamed from: a */
    public boolean m3598a(int i, KeyEvent keyEvent) {
        return this.f1721i != null && this.f1721i.m3246a(keyEvent);
    }

    /* renamed from: b */
    public void m3599b(int i) {
        this.f1703F = null;
        this.f1702E = i;
        if (this.f1704G == null) {
            return;
        }
        if (i != 0) {
            this.f1704G.setVisibility(0);
            this.f1704G.setImageResource(this.f1702E);
            return;
        }
        this.f1704G.setVisibility(8);
    }

    /* renamed from: b */
    public void m3600b(View view) {
        this.f1707J = view;
    }

    /* renamed from: b */
    public void m3601b(CharSequence charSequence) {
        this.f1732t = charSequence;
        if (this.f1706I != null) {
            this.f1706I.setText(charSequence);
        }
    }

    /* renamed from: b */
    public boolean m3602b(int i, KeyEvent keyEvent) {
        return this.f1721i != null && this.f1721i.m3246a(keyEvent);
    }

    /* renamed from: c */
    public int m3603c(int i) {
        TypedValue typedValue = new TypedValue();
        this.f1729q.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    /* renamed from: c */
    public void m3604c(View view) {
        this.f1733u = view;
        this.f1734v = 0;
        this.f1698A = false;
    }
}
