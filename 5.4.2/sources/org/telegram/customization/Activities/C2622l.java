package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.C0236a;
import android.support.v4.content.C0235a;
import android.support.v4.content.C0424l;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.C0675b;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2622l.12.C26061;
import org.telegram.customization.Activities.C2622l.15.C26071;
import org.telegram.customization.Activities.C2622l.19.C26081;
import org.telegram.customization.Activities.l.AnonymousClass12;
import org.telegram.customization.Activities.l.AnonymousClass15;
import org.telegram.customization.Activities.l.AnonymousClass19;
import org.telegram.customization.Model.FilterItem;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.Helper;
import org.telegram.customization.dynamicadapter.TagFilterAdapter;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.MediaType;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.NoResultType;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p152f.C2620a;
import org.telegram.customization.p153c.C2671d;
import org.telegram.customization.p161d.C2677a;
import org.telegram.customization.speechrecognitionview.RecognitionProgressView;
import org.telegram.customization.speechrecognitionview.p157a.C2609a;
import org.telegram.customization.util.C2885i;
import org.telegram.customization.util.view.observerRecyclerView.C2621b;
import org.telegram.customization.util.view.observerRecyclerView.C2952c;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.customization.util.view.sva.JJSearchView;
import org.telegram.customization.util.view.sva.p173a.p174a.C2961a;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;
import utils.p178a.C3791b;
import utils.p178a.C5312a;
import utils.view.FarsiCheckBox;

/* renamed from: org.telegram.customization.Activities.l */
public class C2622l extends FrameLayout implements C0675b, OnClickListener, OnItemClickListener, Observer, C2620a, C2497d, C2621b {
    /* renamed from: K */
    public static ArrayList<FilterItem> f8736K = null;
    /* renamed from: A */
    boolean f8737A = false;
    /* renamed from: B */
    boolean f8738B = false;
    /* renamed from: C */
    ArrayList<SlsTag> f8739C = new ArrayList();
    /* renamed from: D */
    boolean f8740D = false;
    /* renamed from: E */
    int f8741E = 0;
    /* renamed from: F */
    More f8742F;
    /* renamed from: G */
    MediaType f8743G;
    /* renamed from: H */
    ImageView f8744H;
    /* renamed from: I */
    ImageView f8745I;
    /* renamed from: J */
    RecognitionProgressView f8746J;
    /* renamed from: L */
    private long f8747L = 0;
    /* renamed from: M */
    private int f8748M = 0;
    /* renamed from: N */
    private int f8749N = 3;
    /* renamed from: O */
    private View f8750O;
    /* renamed from: P */
    private boolean f8751P = true;
    /* renamed from: Q */
    private long f8752Q;
    /* renamed from: R */
    private DynamicAdapter f8753R;
    /* renamed from: S */
    private long f8754S = 0;
    /* renamed from: T */
    private final int f8755T = 20;
    /* renamed from: U */
    private SwipeRefreshLayout f8756U;
    /* renamed from: V */
    private Activity f8757V;
    /* renamed from: W */
    private RelativeLayout f8758W;
    /* renamed from: a */
    ProgressDialog f8759a = null;
    private JJSearchView aa;
    private TextWatcher ab;
    private View ac;
    private View ad;
    private boolean ae = false;
    private SpeechRecognizer af;
    private BroadcastReceiver ag = new C26199(this);
    /* renamed from: b */
    public int f8760b;
    /* renamed from: c */
    boolean f8761c;
    /* renamed from: d */
    ArrayList<SlsFilter> f8762d = new ArrayList();
    /* renamed from: e */
    TagFilterAdapter f8763e;
    /* renamed from: f */
    TextView f8764f;
    /* renamed from: g */
    ObservableRecyclerView f8765g;
    /* renamed from: h */
    int f8766h = 0;
    /* renamed from: i */
    RecyclerView f8767i;
    /* renamed from: j */
    CoordinatorLayout f8768j;
    /* renamed from: k */
    AppBarLayout f8769k;
    /* renamed from: l */
    View f8770l;
    /* renamed from: m */
    View f8771m;
    /* renamed from: n */
    EditText f8772n;
    /* renamed from: o */
    String f8773o;
    /* renamed from: p */
    boolean f8774p;
    /* renamed from: q */
    boolean f8775q;
    /* renamed from: r */
    ActionBar f8776r;
    /* renamed from: s */
    boolean f8777s = true;
    /* renamed from: t */
    TextView f8778t;
    /* renamed from: u */
    TextView f8779u;
    /* renamed from: v */
    TextView f8780v;
    /* renamed from: w */
    View f8781w;
    /* renamed from: x */
    View f8782x;
    /* renamed from: y */
    View f8783y;
    /* renamed from: z */
    FarsiCheckBox f8784z;

    /* renamed from: org.telegram.customization.Activities.l$1 */
    class C26101 extends C2609a {
        /* renamed from: a */
        final /* synthetic */ C2622l f8721a;

        C26101(C2622l c2622l) {
            this.f8721a = c2622l;
        }

        public void onError(int i) {
            if (i == 7) {
                this.f8721a.f8746J.m13215b();
                this.f8721a.f8746J.setVisibility(8);
                Log.d("alireza", "alireza on error speach");
            }
        }

        public void onResults(Bundle bundle) {
            this.f8721a.f8746J.setVisibility(8);
            this.f8721a.m12434a(bundle);
        }
    }

    /* renamed from: org.telegram.customization.Activities.l$2 */
    class C26112 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2622l f8724a;

        C26112(C2622l c2622l) {
            this.f8724a = c2622l;
        }

        public void run() {
            this.f8724a.f8769k.setVisibility(8);
        }
    }

    /* renamed from: org.telegram.customization.Activities.l$4 */
    class C26134 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2622l f8727a;

        C26134(C2622l c2622l) {
            this.f8727a = c2622l;
        }

        public void run() {
            this.f8727a.f8756U.setRefreshing(false);
        }
    }

    /* renamed from: org.telegram.customization.Activities.l$5 */
    class C26145 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2622l f8728a;

        C26145(C2622l c2622l) {
            this.f8728a = c2622l;
        }

        public void run() {
            this.f8728a.f8769k.setVisibility(8);
            this.f8728a.ad.setVisibility(8);
        }
    }

    /* renamed from: org.telegram.customization.Activities.l$8 */
    class C26178 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2622l f8733a;

        C26178(C2622l c2622l) {
            this.f8733a = c2622l;
        }

        public void run() {
            if (this.f8733a.f8753R != null) {
                try {
                    this.f8733a.f8753R.notifyItemRangeChanged(this.f8733a.f8753R.getItemCount(), 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.l$9 */
    class C26199 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ C2622l f8735a;

        /* renamed from: org.telegram.customization.Activities.l$9$1 */
        class C26181 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C26199 f8734a;

            C26181(C26199 c26199) {
                this.f8734a = c26199;
            }

            public void run() {
                this.f8734a.f8735a.f8769k.setExpanded(true);
                this.f8734a.f8735a.ad.setVisibility(0);
            }
        }

        C26199(C2622l c2622l) {
            this.f8735a = c2622l;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACTION_SET_TAG_ID")) {
                if (this.f8735a.getPoolId() == intent.getIntExtra("EXTRA_POOL_ID", 0)) {
                    Log.d("LEE", "CallApi Search onReceive");
                    try {
                        long longExtra = intent.getLongExtra("EXTRA_TAG_ID", 0);
                        String stringExtra = intent.getStringExtra("EXTRA_TAG_NAME");
                        this.f8735a.setTagId((long) ((int) longExtra));
                        if (this.f8735a.f8766h == 2) {
                            this.f8735a.f8754S = -100;
                            this.f8735a.f8762d = new ArrayList();
                            SlsFilter slsFilter = new SlsFilter();
                            slsFilter.setName(stringExtra);
                            slsFilter.setId((int) longExtra);
                            slsFilter.setDeletable(true);
                            slsFilter.setSelected(true);
                            slsFilter.setClickable(false);
                            this.f8735a.f8762d.add(slsFilter);
                            this.f8735a.f8767i.setVisibility(0);
                            this.f8735a.m12451h();
                            new Handler().postDelayed(new C26181(this), 100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.f8735a.f8753R.getItems().clear();
                    this.f8735a.f8753R.notifyDataSetChanged();
                    this.f8735a.setExistMorePost(true);
                }
            } else if (intent.getAction().equals("ACTION_SEARCH")) {
                CharSequence stringExtra2 = intent.getStringExtra("EXTRA_SEARCH_STRING");
                r2 = intent.getLongExtra("EXTRA_MEDIA_TYPE", 0);
                if (!TextUtils.isEmpty(stringExtra2)) {
                    this.f8735a.f8772n.setText(stringExtra2);
                    this.f8735a.f8754S = r2;
                }
                this.f8735a.m12439a(true, r2);
            } else if (intent.getAction().equals("ACTION_SHOW_SORT_DIALOG") && this.f8735a.f8766h == 2) {
                this.f8735a.m12459l();
            } else if (intent.getAction().equals("ACTION_SHOW_MORE")) {
                this.f8735a.f8753R.removeAllChannelItem();
                this.f8735a.f8753R.getItems().addAll(0, this.f8735a.f8739C);
                this.f8735a.f8753R.notifyDataSetChanged();
                if (this.f8735a.f8742F != null) {
                    this.f8735a.f8742F.setTagCount(9999);
                }
            } else if (intent.getAction().equals("ACTION_SHOW_LESS")) {
                this.f8735a.f8753R.removeAllChannelItem();
                try {
                    if (this.f8735a.f8739C != null && this.f8735a.f8739C.size() > this.f8735a.f8749N) {
                        this.f8735a.f8753R.getItems().addAll(0, this.f8735a.f8739C.subList(0, this.f8735a.f8749N));
                    }
                } catch (Exception e2) {
                }
                this.f8735a.f8753R.notifyDataSetChanged();
                if (this.f8735a.f8742F != null) {
                    this.f8735a.f8742F.setTagCount(3456789);
                }
                this.f8735a.f8765g.m13618h(0);
            } else if (intent.getAction().equals("ACTION_SET_MEDIA_TYPE")) {
                r2 = intent.getLongExtra("EXTRA_MEDIA_TYPE", 0);
                boolean booleanExtra = intent.getBooleanExtra("EXTRA_MEDIA_TYPE_IN_HOT", false);
                Log.d("LEE", "MediaType:" + r2);
                if (this.f8735a.f8754S != r2) {
                    this.f8735a.setExistMorePost(true);
                    if (this.f8735a.f8766h != 2 || booleanExtra) {
                        this.f8735a.f8754S = 0;
                        this.f8735a.setMediaType(r2);
                        if (this.f8735a.f8763e != null && this.f8735a.f8763e.getData() != null && this.f8735a.f8763e.getData().size() > 0) {
                            Iterator it = this.f8735a.f8763e.getData().iterator();
                            int i = 0;
                            while (it.hasNext() && ((long) ((SlsFilter) it.next()).getId()) != r2) {
                                i++;
                            }
                            this.f8735a.f8763e.changeSelectedItem(i);
                            return;
                        }
                        return;
                    }
                    this.f8735a.f8754S = r2;
                    this.f8735a.f8753R.updateMediaTypeItem(r2);
                    this.f8735a.f8753R.removeAllMessageItem();
                    this.f8735a.f8753R.removeNoResultItem();
                    C2671d.m12551c(this.f8735a.f8760b);
                    this.f8735a.f8753R.notifyDataSetChanged();
                    this.f8735a.m12470b(true);
                }
            }
        }
    }

    public C2622l(Activity activity, int i, int i2, ActionBar actionBar) {
        super(activity);
        this.f8766h = i;
        this.f8776r = actionBar;
        setPoolId(i2);
        m12463a(activity);
    }

    public C2622l(Activity activity, int i, int i2, ActionBar actionBar, String str) {
        super(activity);
        this.f8773o = str;
        this.f8766h = i;
        this.f8776r = actionBar;
        setPoolId(i2);
        m12463a(activity);
    }

    /* renamed from: a */
    private String m12432a(long j) {
        Iterator it = C2885i.m13387g(getContext()).iterator();
        while (it.hasNext()) {
            SlsFilter slsFilter = (SlsFilter) it.next();
            if (((long) slsFilter.getId()) == j) {
                return slsFilter.getName();
            }
        }
        return "مرتب سازی";
    }

    /* renamed from: a */
    private void m12433a(int i) {
        if (this.f8766h != 2) {
            this.f8765g.setVisibility(8);
        }
        this.f8753R.setHaveProgress(false);
        this.f8753R.notifyDataSetChanged();
        setExistMorePost(false);
        this.ac.setVisibility(0);
        this.f8769k.setExpanded(false);
        TextView textView = (TextView) this.ac.findViewById(R.id.txt_error);
        ImageView imageView = (ImageView) this.ac.findViewById(R.id.iv_error);
        m12473d();
        switch (i) {
            case -3000:
                textView.setText(getResources().getString(R.string.err_empty));
                imageView.setImageDrawable(C0235a.m1066a(this.f8757V, (int) R.drawable.sad));
                break;
            case -2000:
                textView.setText(getResources().getString(R.string.err_get_data));
                imageView.setImageDrawable(C0235a.m1066a(this.f8757V, (int) R.drawable.sad));
                break;
            case C3446C.PRIORITY_DOWNLOAD /*-1000*/:
                textView.setText(getResources().getString(R.string.network_error));
                imageView.setImageDrawable(C0235a.m1066a(this.f8757V, (int) R.drawable.wifi_off));
                break;
        }
        this.f8756U.setEnabled(true);
    }

    /* renamed from: a */
    private void m12434a(Bundle bundle) {
        this.f8772n.setText((CharSequence) bundle.getStringArrayList("results_recognition").get(0));
        this.aa.performClick();
    }

    /* renamed from: a */
    private void m12439a(boolean z, long j) {
        if (TextUtils.isEmpty(getTerm())) {
            this.f8744H.setVisibility(0);
            this.f8746J.m13215b();
            this.f8746J.setVisibility(8);
        }
        m12474e();
        if (j != 0) {
            this.f8754S = j;
        }
        this.f8773o = this.f8772n.getText().toString();
        this.f8737A = false;
        if (this.f8762d != null) {
            this.f8762d.clear();
        }
        this.f8769k.setExpanded(false);
        AndroidUtilities.runOnUIThread(new Runnable(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8722a;

            {
                this.f8722a = r1;
            }

            public void run() {
                this.f8722a.f8769k.setVisibility(8);
                this.f8722a.ad.setVisibility(8);
            }
        });
        C2671d.m12551c(getPoolId());
        this.f8752Q = 0;
        this.f8753R.setItems(new ArrayList());
        this.f8765g.setAdapter(this.f8753R);
        m12473d();
        AndroidUtilities.hideKeyboard(this.f8750O);
        m12470b(false);
    }

    /* renamed from: c */
    private void m12442c(boolean z) {
        m12439a(z, 0);
    }

    /* renamed from: d */
    private void m12444d(boolean z) {
        if (this.f8766h != 2 && (this.f8762d == null || this.f8762d.isEmpty())) {
            this.f8762d = C2885i.m13385f(this.f8757V);
        }
        if (this.f8762d == null || this.f8762d.size() <= 0) {
            if (!z) {
                C2818c.m13087a(this.f8757V, (C2497d) this).m13124b();
            }
            AndroidUtilities.runOnUIThread(new C26112(this));
            return;
        }
        this.f8767i.post(new Runnable(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8723a;

            {
                this.f8723a = r1;
            }

            public void run() {
                this.f8723a.f8769k.setVisibility(0);
                this.f8723a.f8763e = new TagFilterAdapter(this.f8723a.f8757V, this.f8723a.f8762d, this.f8723a.f8767i, this.f8723a);
                this.f8723a.f8763e.setSelected(this.f8723a.f8754S);
                this.f8723a.f8767i.setAdapter(this.f8723a.f8763e);
            }
        });
    }

    /* renamed from: f */
    private void m12447f() {
        this.ae = true;
        this.f8768j = (CoordinatorLayout) this.f8750O.findViewById(R.id.quickreturn_coordinator);
        this.f8769k = (AppBarLayout) this.f8750O.findViewById(R.id.appBarLayout);
        RelativeLayout relativeLayout = (RelativeLayout) this.f8769k.findViewById(R.id.appbar_container);
        if (!Theme.getCurrentThemeName().contentEquals("hotgram")) {
            relativeLayout.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        }
        m12449g();
        View findViewById = this.f8750O.findViewById(R.id.ll_container);
        findViewById.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        if (Theme.getCurrentThemeName().contentEquals("Dark")) {
            findViewById.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        } else {
            findViewById.setBackgroundDrawable(Theme.getCachedWallpaper());
        }
        this.ad = this.f8769k.findViewById(R.id.iv_back);
        this.ad.setOnClickListener(this);
        this.ac = this.f8750O.findViewById(R.id.error_layout);
        setExistMorePost(true);
        this.f8756U = (SwipeRefreshLayout) this.f8750O.findViewById(R.id.refresher);
        this.f8756U.setOnRefreshListener(this);
        this.f8756U.setColorSchemeResources(R.color.flag_text_color, R.color.elv_btn_pressed, R.color.pink);
        this.f8756U.m3273a(true, Callback.DEFAULT_DRAG_ANIMATION_DURATION, 300);
        this.f8767i = (RecyclerView) this.f8750O.findViewById(R.id.recyclerView);
        C0910h linearLayoutManager = new LinearLayoutManager(this.f8757V, 0, false);
        linearLayoutManager.m4673b(true);
        this.f8767i.setLayoutManager(linearLayoutManager);
        this.f8770l = this.f8750O.findViewById(R.id.statisticHolder);
        this.f8771m = this.f8750O.findViewById(R.id.searchHolder);
        this.f8772n = (EditText) this.f8750O.findViewById(R.id.et_term);
        this.f8758W = (RelativeLayout) this.f8750O.findViewById(R.id.ll_root);
        this.aa = (JJSearchView) this.f8750O.findViewById(R.id.jjsv);
        this.aa.setController(new C2961a());
        this.f8764f = (TextView) this.f8750O.findViewById(R.id.tv_advance_search);
        this.f8779u = (TextView) this.f8750O.findViewById(R.id.tv_simple_saerch);
        this.f8779u.setOnClickListener(this);
        this.f8764f.setOnClickListener(this);
        this.f8778t = (TextView) this.f8750O.findViewById(R.id.tv_search_help);
        this.f8781w = this.f8750O.findViewById(R.id.ll_advance_container);
        this.f8782x = this.f8750O.findViewById(R.id.select_category);
        this.f8783y = this.f8750O.findViewById(R.id.select_filter_type);
        this.f8771m.findViewById(R.id.btn_search).setOnClickListener(this);
        this.f8784z = (FarsiCheckBox) this.f8750O.findViewById(R.id.cb_search_exact);
        this.f8780v = (TextView) this.f8750O.findViewById(R.id.tv_selected_filters);
        this.f8744H = (ImageView) this.f8771m.findViewById(R.id.iv_voice_search);
        this.f8745I = (ImageView) this.f8771m.findViewById(R.id.iv_voice_search1);
        int[] iArr = new int[]{C0235a.m1075c(getContext(), R.color.color1), C0235a.m1075c(getContext(), R.color.color2), C0235a.m1075c(getContext(), R.color.color3), C0235a.m1075c(getContext(), R.color.color4), C0235a.m1075c(getContext(), R.color.color5)};
        int[] iArr2 = new int[]{20, 24, 18, 23, 16};
        this.af = SpeechRecognizer.createSpeechRecognizer(getContext());
        this.f8746J = (RecognitionProgressView) this.f8771m.findViewById(R.id.recognition_view);
        this.f8746J.setSpeechRecognizer(this.af);
        this.f8746J.setColors(iArr);
        this.f8746J.setBarMaxHeightsInDp(iArr2);
        this.f8746J.setCircleRadiusInDp(2);
        this.f8746J.setSpacingInDp(2);
        this.f8746J.setIdleStateAmplitudeInDp(2);
        this.f8746J.setRotationRadiusInDp(10);
        if (!TextUtils.isEmpty(this.f8773o)) {
            this.f8772n.setText(this.f8773o);
        }
        this.f8746J.setRecognitionListener(new C26101(this));
        this.f8744H.setOnClickListener(new OnClickListener(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8706a;

            /* renamed from: org.telegram.customization.Activities.l$12$1 */
            class C26061 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass12 f8705a;

                C26061(AnonymousClass12 anonymousClass12) {
                    this.f8705a = anonymousClass12;
                }

                public void run() {
                    this.f8705a.f8706a.m12460m();
                }
            }

            {
                this.f8706a = r1;
            }

            public void onClick(View view) {
                if (C0235a.m1072b(this.f8706a.getContext(), "android.permission.RECORD_AUDIO") != 0) {
                    this.f8706a.m12461n();
                    return;
                }
                this.f8706a.f8746J.setVisibility(0);
                this.f8706a.f8746J.m13214a();
                this.f8706a.m12460m();
                this.f8706a.f8746J.postDelayed(new C26061(this), 50);
            }
        });
        this.f8745I.setOnClickListener(new OnClickListener(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8715a;

            /* renamed from: org.telegram.customization.Activities.l$15$1 */
            class C26071 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass15 f8714a;

                C26071(AnonymousClass15 anonymousClass15) {
                    this.f8714a = anonymousClass15;
                }

                public void run() {
                    this.f8714a.f8715a.m12460m();
                }
            }

            {
                this.f8715a = r1;
            }

            public void onClick(View view) {
                this.f8715a.f8746J.setVisibility(0);
                this.f8715a.f8746J.m13214a();
                this.f8715a.m12460m();
                this.f8715a.f8746J.postDelayed(new C26071(this), 50);
            }
        });
        ((TextView) this.f8783y.findViewById(R.id.title)).setText(R.string.serch_in_media);
        ((TextView) this.f8782x.findViewById(R.id.title)).setText(R.string.serch_in_category);
        this.f8782x.setOnClickListener(this);
        this.f8783y.setOnClickListener(this);
        Object str = new String("جستجوی پیشرفته");
        CharSequence spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        this.f8764f.setText(spannableString);
        this.f8764f.setOnClickListener(this);
        str = new String("راهنمای جستجوی پیشرفته");
        spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        this.f8778t.setText(spannableString);
        str = new String("جستجوی ساده");
        spannableString = new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        this.f8779u.setText(spannableString);
        this.aa.setOnClickListener(new OnClickListener(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8716a;

            {
                this.f8716a = r1;
            }

            public void onClick(View view) {
                ((InputMethodManager) this.f8716a.getContext().getSystemService("input_method")).toggleSoftInput(2, 0);
                if (!this.f8716a.f8777s) {
                    this.f8716a.m12456k();
                }
                this.f8716a.m12442c(false);
            }
        });
        if (this.ab == null) {
            this.ab = new TextWatcher(this) {
                /* renamed from: a */
                final /* synthetic */ C2622l f8717a;

                {
                    this.f8717a = r1;
                }

                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if (charSequence.length() == 0) {
                        this.f8717a.aa.m13649b();
                        this.f8717a.m12442c(false);
                        return;
                    }
                    this.f8717a.aa.m13647a();
                }
            };
            this.f8772n.addTextChangedListener(this.ab);
            this.f8772n.setOnEditorActionListener(new OnEditorActionListener(this) {
                /* renamed from: a */
                final /* synthetic */ C2622l f8718a;

                {
                    this.f8718a = r1;
                }

                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 3) {
                        return false;
                    }
                    ((InputMethodManager) this.f8718a.getContext().getSystemService("input_method")).toggleSoftInput(2, 0);
                    if (!this.f8718a.f8777s) {
                        this.f8718a.m12456k();
                    }
                    this.f8718a.m12442c(false);
                    return true;
                }
            });
        }
        this.f8772n.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8720a;

            /* renamed from: org.telegram.customization.Activities.l$19$1 */
            class C26081 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass19 f8719a;

                C26081(AnonymousClass19 anonymousClass19) {
                    this.f8719a = anonymousClass19;
                }

                public void run() {
                    this.f8719a.f8720a.f8772n.selectAll();
                }
            }

            {
                this.f8720a = r1;
            }

            public void onFocusChange(View view, boolean z) {
                FileLog.m13725d("onFocusChange has: " + z);
                if (z && this.f8720a.f8772n.getText().toString().length() > 0) {
                    this.f8720a.f8772n.post(new C26081(this));
                }
            }
        });
        this.f8772n.setSelectAllOnFocus(true);
        this.f8765g = (ObservableRecyclerView) this.f8750O.findViewById(R.id.recycler);
        this.f8765g.setScrollPositionChangesListener(this);
        this.f8765g.setLayoutManager(new LinearLayoutManager(this.f8757V, 1, false));
        ExtraData extraData = new ExtraData();
        extraData.setPoolId(getPoolId());
        extraData.setTagId(this.f8752Q);
        this.f8753R = new DynamicAdapter(this.f8757V, extraData);
        this.f8765g.setAdapter(this.f8753R);
        m12470b(true);
        m12451h();
        m12453i();
    }

    /* renamed from: g */
    private void m12449g() {
        if (this.f8776r != null) {
            ActionBarMenu createMenu = this.f8776r.createMenu();
            try {
                createMenu.getItem(2).setVisibility(8);
                createMenu.getItem(0).setVisibility(8);
                createMenu.getItem(4).setVisibility(8);
                createMenu.getItem(1).setVisibility(8);
                createMenu.getItem(8).setVisibility(8);
                createMenu.getItem(5).setVisibility(8);
                createMenu.getItem(7).setVisibility(8);
                createMenu.getItem(6).setVisibility(8);
                this.f8776r.getTitleTextView().setGravity(5);
            } catch (Exception e) {
            }
        }
    }

    private long getFirstPostId() {
        return (this.f8753R == null || this.f8753R.getItems().isEmpty()) ? 0 : (long) ((ObjBase) this.f8753R.getItems().get(0)).getRow();
    }

    private long getLastPostId() {
        if (this.f8753R == null || this.f8753R.getItems().isEmpty()) {
            return 0;
        }
        return (long) ((ObjBase) this.f8753R.getItems().get((this.f8753R.getItemCount() - 1) - (this.f8753R.isHaveProgress() ? 1 : 0))).getRow();
    }

    /* renamed from: h */
    private void m12451h() {
        m12444d(false);
    }

    /* renamed from: i */
    private void m12453i() {
        ((LaunchActivity) this.f8757V).drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    /* renamed from: j */
    private void m12454j() {
        this.f8765g.setVisibility(0);
        this.ac.setVisibility(8);
    }

    /* renamed from: k */
    private void m12456k() {
        if (this.f8777s) {
            C5312a.a(this.f8781w);
        } else {
            C5312a.b(this.f8781w);
        }
        this.f8777s = !this.f8777s;
    }

    /* renamed from: l */
    private void m12459l() {
        final ArrayList g = C2885i.m13387g(getContext());
        if (g != null && !g.isEmpty()) {
            final Dialog dialog = new Dialog(this.f8757V, R.style.Theme_Dialog);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.dialog_sort_filter);
            this.f8757V.getWindow().setLayout(-1, -1);
            ((TextView) dialog.findViewById(R.id.header)).setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
            TextView textView = (TextView) dialog.findViewById(R.id.done);
            final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ll_sort_container);
            final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.filterRecyclerView);
            C0910h linearLayoutManager = new LinearLayoutManager(this.f8757V, 0, false);
            linearLayoutManager.m4673b(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            this.f8762d = C2885i.m13385f(this.f8757V);
            if (this.f8762d != null && this.f8762d.size() > 0) {
                recyclerView.post(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2622l f8704b;

                    public void run() {
                        this.f8704b.f8763e = new TagFilterAdapter(this.f8704b.f8757V, this.f8704b.f8762d, recyclerView, this.f8704b);
                        recyclerView.setAdapter(this.f8704b.f8763e);
                    }
                });
            }
            LayoutInflater layoutInflater = (LayoutInflater) this.f8757V.getSystemService("layout_inflater");
            Iterator it = g.iterator();
            while (it.hasNext()) {
                SlsFilter slsFilter = (SlsFilter) it.next();
                View inflate = layoutInflater.inflate(R.layout.checkbox_option_item, linearLayout, false);
                FarsiCheckBox farsiCheckBox = (FarsiCheckBox) inflate.findViewById(R.id.checkbox2);
                farsiCheckBox.setId(slsFilter.getId());
                farsiCheckBox.setTag(Integer.valueOf(slsFilter.getId()));
                farsiCheckBox.setTitle(slsFilter.getName());
                if (C2671d.m12558j(this.f8760b) == ((long) slsFilter.getId())) {
                    farsiCheckBox.setChecked(true);
                }
                farsiCheckBox.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: c */
                    final /* synthetic */ C2622l f8709c;

                    public void onClick(View view) {
                        Iterator it = g.iterator();
                        while (it.hasNext()) {
                            SlsFilter slsFilter = (SlsFilter) it.next();
                            if (((Integer) view.getTag()).intValue() != slsFilter.getId()) {
                                FarsiCheckBox farsiCheckBox = (FarsiCheckBox) linearLayout.findViewById(slsFilter.getId());
                                farsiCheckBox.setChecked(false);
                                farsiCheckBox.setSelected(false);
                            }
                        }
                    }
                });
                linearLayout.addView(inflate);
            }
            textView.setOnClickListener(new OnClickListener(this) {
                /* renamed from: d */
                final /* synthetic */ C2622l f8713d;

                public void onClick(View view) {
                    int id;
                    Iterator it = g.iterator();
                    while (it.hasNext()) {
                        SlsFilter slsFilter = (SlsFilter) it.next();
                        if (((FarsiCheckBox) linearLayout.findViewById(slsFilter.getId())).a()) {
                            id = slsFilter.getId();
                            break;
                        }
                    }
                    id = 0;
                    try {
                        id = ((SlsFilter) g.get(id)).getValue();
                        Log.d("alireza", "alireza sort option id : " + id);
                        C2671d.m12549b(this.f8713d.getPoolId(), (long) id);
                        this.f8713d.m12474e();
                        this.f8713d.m12470b(false);
                    } catch (Exception e) {
                        FileLog.m13725d(e + TtmlNode.ANONYMOUS_REGION_ID);
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    /* renamed from: m */
    private void m12460m() {
        Log.d("alireza", "alireza voice search clicked1");
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", "fa-IR");
        intent.putExtra("android.speech.extra.LANGUAGE_PREFERENCE", "fa-IR");
        intent.putExtra("android.speech.extra.MAX_RESULTS", 1);
        this.af.startListening(intent);
    }

    /* renamed from: n */
    private void m12461n() {
        if (C0236a.m1081a(this.f8757V, "android.permission.RECORD_AUDIO")) {
            Toast.makeText(getContext(), "Requires RECORD_AUDIO permission", 0).show();
            return;
        }
        C0236a.m1080a(this.f8757V, new String[]{"android.permission.RECORD_AUDIO"}, 1);
    }

    private void setUpSearchView(ObjBase objBase) {
        if (objBase != null) {
            if (!TextUtils.isEmpty(objBase.getTitle())) {
                this.f8772n.setHint(objBase.getTitle() + TtmlNode.ANONYMOUS_REGION_ID);
            }
            if (this.f8772n.getText().toString().length() <= 0) {
                this.f8772n.clearFocus();
                this.f8750O.findViewById(R.id.linearLayout_focus).requestFocus();
                this.f8775q = true;
            } else {
                this.f8772n.clearFocus();
                this.f8750O.findViewById(R.id.linearLayout_focus).requestFocus();
                this.f8775q = true;
            }
        }
    }

    private void setUpStatisticView(ObjBase objBase) {
        if (objBase != null) {
            if (this.f8776r != null) {
                this.f8776r.searchTabTitle = objBase.getTitle();
                this.f8776r.actionBarFontSize = 12;
                this.f8776r.setTitle(objBase.getTitle() + TtmlNode.ANONYMOUS_REGION_ID);
            }
            this.f8774p = true;
        }
    }

    /* renamed from: a */
    public View m12462a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View linearLayout = new LinearLayout(this.f8757V);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(-1);
        this.f8750O = layoutInflater.inflate(R.layout.sls_fragment_hot, linearLayout, false);
        linearLayout.addView(this.f8750O);
        this.f8750O.findViewById(R.id.ll_container).setBackgroundResource(R.drawable.background_hd);
        m12447f();
        return linearLayout;
    }

    /* renamed from: a */
    void m12463a(Activity activity) {
        this.f8757V = activity;
        addView(m12462a((LayoutInflater) this.f8757V.getSystemService("layout_inflater"), null, null), -1, -1);
        C2818c.m13087a(this.f8757V, (C2497d) this).m13124b();
    }

    /* renamed from: a */
    public void mo3451a(View view) {
        this.f8756U.setEnabled(false);
        m12470b(true);
    }

    /* renamed from: a */
    public void mo3452a(View view, int i) {
        this.f8756U.setEnabled(false);
    }

    /* renamed from: a */
    public void mo3453a(FilterItem filterItem) {
        switch (filterItem.getType()) {
            case 1:
                ((TextView) this.f8782x.findViewById(R.id.title)).setText(filterItem.getName());
                setTagId(filterItem.getId());
                return;
            case 2:
                ((TextView) this.f8783y.findViewById(R.id.title)).setText(filterItem.getName());
                this.f8754S = filterItem.getId();
                return;
            case 3:
                C2671d.m12549b(getPoolId(), filterItem.getId());
                return;
            default:
                return;
        }
    }

    /* renamed from: a */
    public void mo3454a(C2952c c2952c) {
    }

    /* renamed from: a */
    void m12468a(final boolean z) {
        this.f8756U.post(new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ C2622l f8726b;

            public void run() {
                if (!z || this.f8726b.f8753R.getItems().size() < 1) {
                    this.f8726b.f8756U.setRefreshing(true);
                }
            }
        });
        if (z) {
            this.f8765g.setLoadingStart(true);
        } else {
            this.f8765g.setLoadingEnd(true);
        }
        m12454j();
    }

    /* renamed from: b */
    public void mo3455b(View view) {
        this.f8765g.setLoadingEnd(false);
        this.f8765g.setLoadingStart(false);
        this.f8756U.setEnabled(true);
    }

    /* renamed from: b */
    void m12470b(boolean z) {
        m12453i();
        if (m12472c() || !z) {
            m12468a(z);
            if (this.f8766h == 1) {
                this.f8767i.setVisibility(0);
                try {
                    C2818c.m13087a(this.f8757V, (C2497d) this).m13110a(getTagId(), z ? getLastPostId() : getFirstPostId(), z, getMediaType(), 20);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else if (this.f8766h == 2) {
                if (getTerm().isEmpty() && getTagId() == 0) {
                    setExistMorePost(false);
                    this.f8753R.setHaveProgress(false);
                } else {
                    setExistMorePost(true);
                    this.f8753R.setHaveProgress(true);
                }
                if (getTagId() == 0) {
                    this.f8767i.setVisibility(8);
                    AndroidUtilities.runOnUIThread(new C26145(this));
                }
                C2818c.m13097c("v12/content/getSearch");
                C2818c.m13087a(this.f8757V, (C2497d) this).m13111a(getTagId(), z ? getLastPostId() : getFirstPostId(), z, getTerm(), getMediaType(), 20, C2671d.m12558j(getPoolId()), C2671d.m12555g(this.f8760b));
                return;
            } else if (this.f8766h == 3) {
                m12473d();
                C2818c.m13087a(this.f8757V, (C2497d) this).m13126b(getTagId(), z ? getLastPostId() : getFirstPostId(), z, getMediaType(), 20);
                return;
            } else {
                return;
            }
        }
        m12473d();
        this.f8753R.setHaveProgress(false);
    }

    /* renamed from: b */
    public boolean m12471b() {
        return this.f8740D;
    }

    /* renamed from: c */
    public boolean m12472c() {
        return this.f8751P;
    }

    /* renamed from: d */
    void m12473d() {
        this.f8756U.post(new C26134(this));
        this.f8765g.setLoadingEnd(false);
        this.f8765g.setLoadingStart(false);
    }

    /* renamed from: e */
    void m12474e() {
        this.f8737A = false;
        this.f8738B = false;
        setMediaTypeAdded(false);
        Log.d("LEE", "TagId:" + getTagId());
        this.f8739C = new ArrayList();
        setExistMorePost(true);
        C2671d.m12551c(getPoolId());
        this.f8752Q = 0;
        this.f8754S = 0;
        this.f8750O.findViewById(R.id.error_layout).setVisibility(8);
        this.f8765g.setVisibility(0);
        this.f8765g.invalidate();
        this.f8769k.setExpanded(false);
        this.f8769k.setExpanded(true);
        this.f8753R.setItems(new ArrayList());
        this.f8765g.setAdapter(this.f8753R);
        if (!ConnectionsManager.isNetworkOnline()) {
            mo3451a(this.f8750O);
        }
        this.f8775q = false;
        this.f8774p = false;
    }

    public ArrayList<FilterItem> getCategories() {
        if (f8736K == null) {
            f8736K = new ArrayList();
            Iterator it = this.f8753R.getItems().iterator();
            while (it.hasNext()) {
                ObjBase objBase = (ObjBase) it.next();
                if (objBase.getType() == 101) {
                    SlsTag slsTag = (SlsTag) objBase;
                    FilterItem filterItem = new FilterItem();
                    filterItem.setId(slsTag.getId());
                    filterItem.setName(slsTag.getShowName());
                    filterItem.setType(1);
                    f8736K.add(filterItem);
                }
            }
        }
        return f8736K;
    }

    public long getMediaType() {
        return this.f8754S;
    }

    public int getPoolId() {
        return this.f8760b;
    }

    public String getSelectedFilterString() {
        return C2671d.m12555g(this.f8760b) ? "جستجوی دقیق فعال است" : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public long getTagId() {
        return this.f8752Q;
    }

    public String getTerm() {
        this.f8773o = this.f8772n.getText().toString();
        if (this.f8773o == null) {
            this.f8773o = TtmlNode.ANONYMOUS_REGION_ID;
        }
        return this.f8773o;
    }

    public int getType() {
        return this.f8766h;
    }

    public void j_() {
        this.f8762d.clear();
        m12451h();
        m12474e();
        m12470b(true);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        C2671d.m12538a().addObserver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_SET_TAG_ID");
        intentFilter.addAction("ACTION_SHOW_MORE");
        intentFilter.addAction("ACTION_SHOW_LESS");
        intentFilter.addAction("ACTION_SET_MEDIA_TYPE");
        intentFilter.addAction("ACTION_SHOW_SORT_DIALOG");
        if (this.f8766h == 2) {
            intentFilter.addAction("ACTION_SEARCH");
        }
        C0424l.m1899a(this.f8757V).m1903a(this.ag, intentFilter);
        new Handler().postDelayed(new Runnable(this) {
            /* renamed from: a */
            final /* synthetic */ C2622l f8702a;

            {
                this.f8702a = r1;
            }

            public void run() {
            }
        }, 100);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                m12442c(true);
                return;
            case R.id.tv_advance_search:
                m12456k();
                return;
            case R.id.select_category:
                new C2677a(this.f8757V, "جستجو براساس دسته بندی", 1, getCategories(), this).show();
                return;
            case R.id.select_filter_type:
                ArrayList arrayList = new ArrayList();
                Iterator it = C2885i.m13385f(this.f8757V).iterator();
                while (it.hasNext()) {
                    SlsFilter slsFilter = (SlsFilter) it.next();
                    FilterItem filterItem = new FilterItem();
                    filterItem.setId((long) slsFilter.getId());
                    filterItem.setName(slsFilter.getName());
                    filterItem.setType(2);
                    arrayList.add(filterItem);
                }
                new C2677a(this.f8757V, "جستجو براساس نوع محتوا", 2, arrayList, this).show();
                return;
            case R.id.tv_simple_saerch:
                m12456k();
                return;
            case R.id.btn_search:
                m12456k();
                this.f8753R.getItems().clear();
                this.f8753R.notifyDataSetChanged();
                C2671d.m12544a(getPoolId(), this.f8784z.a());
                this.f8780v.setText(getSelectedFilterString());
                m12470b(false);
                return;
            default:
                return;
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C2671d.m12538a().deleteObserver(this);
        C0424l.m1899a(this.f8757V).m1902a(this.ag);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (((SlsFilter) this.f8763e.getData().get(i)).isClickable()) {
            Log.d("alireza", "alireza  ttttt " + i + "---- " + ((SlsFilter) this.f8763e.getData().get(i)).getId());
            this.f8763e.changeSelectedItem(i);
            setMediaType((long) ((SlsFilter) this.f8763e.getData().get(i)).getId());
        }
    }

    public void onResult(Object obj, int i) {
        int i2 = 8;
        switch (i) {
            case C3446C.PRIORITY_DOWNLOAD /*-1000*/:
                m12473d();
                m12433a((int) C3446C.PRIORITY_DOWNLOAD);
                Log.d("LEE", "showErrorView 3");
                return;
            case -1:
                m12433a(-2000);
                Log.d("LEE", "showErrorView 2");
                m12473d();
                return;
            case 1:
                ArrayList arrayList = obj != null ? (ArrayList) obj : new ArrayList();
                if (arrayList.size() < 20) {
                    setExistMorePost(false);
                    this.f8753R.setHaveProgress(false);
                }
                final ArrayList arrayList2 = new ArrayList();
                if (this.f8766h == 2) {
                    setUpStatisticView(Helper.getStatisticsItem(arrayList));
                    setUpSearchView(Helper.getSearchItem(arrayList));
                    if (TextUtils.isEmpty(this.f8773o) && Helper.getImportantItem(arrayList) != null) {
                        arrayList2.add(Helper.getImportantItem(arrayList));
                    }
                    if (TextUtils.isEmpty(this.f8773o) && Helper.getSuggestedSearchItem(arrayList) != null) {
                        arrayList2.add(Helper.getSuggestedSearchItem(arrayList));
                    }
                    if (this.f8771m != null) {
                        this.f8771m.setVisibility(this.f8775q ? 0 : 8);
                    }
                    if (this.f8770l != null) {
                        View view = this.f8770l;
                        if (this.f8774p) {
                            i2 = 0;
                        }
                        view.setVisibility(i2);
                    }
                    if (Helper.getFullChannelItems(arrayList).size() > 0) {
                        this.f8739C = Helper.getFullChannelItems(arrayList);
                    }
                    if (TextUtils.isEmpty(getTerm())) {
                        arrayList2.addAll(this.f8739C);
                    } else {
                        if (!this.f8738B) {
                            if (this.f8739C.size() > this.f8749N) {
                                arrayList2.addAll(new ArrayList(Helper.getLimitedChannelItems(this.f8739C, this.f8749N)));
                            } else {
                                arrayList2.addAll(new ArrayList(this.f8739C));
                            }
                        }
                        if (this.f8739C.size() > 0) {
                            this.f8738B = true;
                        }
                        if (!this.f8737A) {
                            this.f8742F = new More();
                            this.f8742F.setSortTitle(m12432a(C2671d.m12558j(this.f8760b)));
                            arrayList2.add(arrayList2.size(), this.f8742F);
                            this.f8737A = true;
                            if (this.f8739C.size() > this.f8749N) {
                                this.f8742F.setShowMoreButtonVisibility(true);
                            } else {
                                this.f8742F.setShowMoreButtonVisibility(false);
                            }
                        }
                        if (!m12471b()) {
                            this.f8743G = new MediaType();
                            this.f8743G.setSelectedMediaType(this.f8754S);
                            arrayList2.add(arrayList2.size(), this.f8743G);
                            setMediaTypeAdded(true);
                        }
                    }
                }
                Object messageItems = Helper.getMessageItems(arrayList);
                arrayList2.addAll(messageItems);
                if (!TextUtils.isEmpty(this.f8773o) && messageItems.size() == 0) {
                    NoResultType noResultType = new NoResultType();
                    noResultType.setType(110);
                    arrayList2.add(noResultType);
                    setExistMorePost(false);
                    this.f8753R.setHaveProgress(false);
                    this.f8753R.notifyDataSetChanged();
                }
                C2671d.m12543a(getPoolId(), arrayList2);
                C2671d.m12542a(getPoolId(), getTerm());
                C2671d.m12548b(getPoolId(), 20);
                C2671d.m12540a(getPoolId(), getMediaType());
                this.f8753R.addItemsBySort(arrayList2);
                try {
                    new Handler().postDelayed(new Runnable(this) {
                        /* renamed from: b */
                        final /* synthetic */ C2622l f8730b;

                        public void run() {
                            try {
                                this.f8730b.f8753R.notifyItemRangeInserted(this.f8730b.f8753R.getItemCount(), arrayList2.size());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 150);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final int a = C3791b.m13923a(ApplicationLoader.applicationContext, this.f8766h);
                if (a > 0) {
                    this.f8765g.post(new Runnable(this) {
                        /* renamed from: b */
                        final /* synthetic */ C2622l f8732b;

                        public void run() {
                            this.f8732b.f8765g.getLayoutManager().mo820d(a);
                            C3791b.m13927a(ApplicationLoader.applicationContext, this.f8732b.f8766h, 0);
                        }
                    });
                }
                m12473d();
                return;
            case 2:
                Log.d("LEE", "showErrorView 4");
                m12444d(true);
                return;
            default:
                return;
        }
    }

    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
    }

    public void setExistMorePost(boolean z) {
        this.f8751P = z;
    }

    public void setMediaType(long j) {
        if (j != this.f8754S) {
            m12474e();
            this.f8754S = j;
            m12470b(true);
        }
    }

    public void setMediaTypeAdded(boolean z) {
        this.f8740D = z;
    }

    public void setPhraseSearch(boolean z) {
        this.f8761c = z;
    }

    public void setPoolId(int i) {
        this.f8760b = i;
    }

    public void setTagId(long j) {
        this.f8752Q = j;
    }

    public void setType(int i) {
        this.f8766h = i;
    }

    public void update(Observable observable, Object obj) {
        new Handler().postDelayed(new C26178(this), 100);
    }
}
