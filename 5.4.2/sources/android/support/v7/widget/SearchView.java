package android.support.v7.widget;

import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.ResultReceiver;
import android.support.v4.content.p020a.C0402a;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.AbsSavedState;
import android.support.v4.widget.C0694g;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0741d;
import android.support.v7.view.C0843c;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.tgnet.ConnectionsManager;

public class SearchView extends ao implements C0843c {
    /* renamed from: i */
    static final C0958a f2565i = new C0958a();
    /* renamed from: A */
    private OnClickListener f2566A;
    /* renamed from: B */
    private boolean f2567B;
    /* renamed from: C */
    private boolean f2568C;
    /* renamed from: D */
    private boolean f2569D;
    /* renamed from: E */
    private CharSequence f2570E;
    /* renamed from: F */
    private boolean f2571F;
    /* renamed from: G */
    private boolean f2572G;
    /* renamed from: H */
    private int f2573H;
    /* renamed from: I */
    private boolean f2574I;
    /* renamed from: J */
    private CharSequence f2575J;
    /* renamed from: K */
    private boolean f2576K;
    /* renamed from: L */
    private int f2577L;
    /* renamed from: M */
    private Bundle f2578M;
    /* renamed from: N */
    private Runnable f2579N;
    /* renamed from: O */
    private final Runnable f2580O;
    /* renamed from: P */
    private Runnable f2581P;
    /* renamed from: Q */
    private final WeakHashMap<String, ConstantState> f2582Q;
    /* renamed from: a */
    final SearchAutoComplete f2583a;
    /* renamed from: b */
    final ImageView f2584b;
    /* renamed from: c */
    final ImageView f2585c;
    /* renamed from: d */
    final ImageView f2586d;
    /* renamed from: e */
    final ImageView f2587e;
    /* renamed from: f */
    OnFocusChangeListener f2588f;
    /* renamed from: g */
    C0694g f2589g;
    /* renamed from: h */
    SearchableInfo f2590h;
    /* renamed from: j */
    private final View f2591j;
    /* renamed from: k */
    private final View f2592k;
    /* renamed from: l */
    private C0962e f2593l;
    /* renamed from: m */
    private Rect f2594m;
    /* renamed from: n */
    private Rect f2595n;
    /* renamed from: o */
    private int[] f2596o;
    /* renamed from: p */
    private int[] f2597p;
    /* renamed from: q */
    private final ImageView f2598q;
    /* renamed from: r */
    private final Drawable f2599r;
    /* renamed from: s */
    private final int f2600s;
    /* renamed from: t */
    private final int f2601t;
    /* renamed from: u */
    private final Intent f2602u;
    /* renamed from: v */
    private final Intent f2603v;
    /* renamed from: w */
    private final CharSequence f2604w;
    /* renamed from: x */
    private C0960c f2605x;
    /* renamed from: y */
    private C0959b f2606y;
    /* renamed from: z */
    private C0961d f2607z;

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C09561());
        /* renamed from: a */
        boolean f2549a;

        /* renamed from: android.support.v7.widget.SearchView$SavedState$1 */
        static class C09561 implements C0085g<SavedState> {
            C09561() {
            }

            /* renamed from: a */
            public SavedState m4973a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m4974a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m4973a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m4974a(i);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2549a = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.f2549a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.f2549a));
        }
    }

    public static class SearchAutoComplete extends C0957g {
        /* renamed from: a */
        private int f2553a;
        /* renamed from: b */
        private SearchView f2554b;

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, C0738a.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.f2553a = getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int b = C0402a.m1861b(getResources());
            int a = C0402a.m1860a(getResources());
            return (b < 960 || a < 720 || configuration.orientation != 2) ? (b >= 600 || (b >= 640 && a >= 480)) ? PsExtractor.AUDIO_STREAM : 160 : 256;
        }

        public boolean enoughToFilter() {
            return this.f2553a <= 0 || super.enoughToFilter();
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, (float) getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        protected void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.f2554b.m5005g();
        }

        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                DispatcherState keyDispatcherState;
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState == null) {
                        return true;
                    }
                    keyDispatcherState.startTracking(keyEvent, this);
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.f2554b.clearFocus();
                        this.f2554b.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.f2554b.hasFocus() && getVisibility() == 0) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                if (SearchView.m4984a(getContext())) {
                    SearchView.f2565i.m4976a(this, true);
                }
            }
        }

        public void performCompletion() {
        }

        protected void replaceText(CharSequence charSequence) {
        }

        void setSearchView(SearchView searchView) {
            this.f2554b = searchView;
        }

        public void setThreshold(int i) {
            super.setThreshold(i);
            this.f2553a = i;
        }
    }

    /* renamed from: android.support.v7.widget.SearchView$a */
    private static class C0958a {
        /* renamed from: a */
        private Method f2555a;
        /* renamed from: b */
        private Method f2556b;
        /* renamed from: c */
        private Method f2557c;
        /* renamed from: d */
        private Method f2558d;

        C0958a() {
            try {
                this.f2555a = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.f2555a.setAccessible(true);
            } catch (NoSuchMethodException e) {
            }
            try {
                this.f2556b = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.f2556b.setAccessible(true);
            } catch (NoSuchMethodException e2) {
            }
            try {
                this.f2557c = AutoCompleteTextView.class.getMethod("ensureImeVisible", new Class[]{Boolean.TYPE});
                this.f2557c.setAccessible(true);
            } catch (NoSuchMethodException e3) {
            }
            try {
                this.f2558d = InputMethodManager.class.getMethod("showSoftInputUnchecked", new Class[]{Integer.TYPE, ResultReceiver.class});
                this.f2558d.setAccessible(true);
            } catch (NoSuchMethodException e4) {
            }
        }

        /* renamed from: a */
        void m4975a(AutoCompleteTextView autoCompleteTextView) {
            if (this.f2555a != null) {
                try {
                    this.f2555a.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        /* renamed from: a */
        void m4976a(AutoCompleteTextView autoCompleteTextView, boolean z) {
            if (this.f2557c != null) {
                try {
                    this.f2557c.invoke(autoCompleteTextView, new Object[]{Boolean.valueOf(z)});
                } catch (Exception e) {
                }
            }
        }

        /* renamed from: b */
        void m4977b(AutoCompleteTextView autoCompleteTextView) {
            if (this.f2556b != null) {
                try {
                    this.f2556b.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: android.support.v7.widget.SearchView$b */
    public interface C0959b {
        /* renamed from: a */
        boolean m4978a();
    }

    /* renamed from: android.support.v7.widget.SearchView$c */
    public interface C0960c {
        /* renamed from: a */
        boolean m4979a(String str);
    }

    /* renamed from: android.support.v7.widget.SearchView$d */
    public interface C0961d {
    }

    /* renamed from: android.support.v7.widget.SearchView$e */
    private static class C0962e extends TouchDelegate {
        /* renamed from: a */
        private final View f2559a;
        /* renamed from: b */
        private final Rect f2560b = new Rect();
        /* renamed from: c */
        private final Rect f2561c = new Rect();
        /* renamed from: d */
        private final Rect f2562d = new Rect();
        /* renamed from: e */
        private final int f2563e;
        /* renamed from: f */
        private boolean f2564f;

        public C0962e(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.f2563e = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            m4980a(rect, rect2);
            this.f2559a = view;
        }

        /* renamed from: a */
        public void m4980a(Rect rect, Rect rect2) {
            this.f2560b.set(rect);
            this.f2562d.set(rect);
            this.f2562d.inset(-this.f2563e, -this.f2563e);
            this.f2561c.set(rect2);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r7) {
            /*
            r6 = this;
            r1 = 1;
            r0 = 0;
            r2 = r7.getX();
            r3 = (int) r2;
            r2 = r7.getY();
            r4 = (int) r2;
            r2 = r7.getAction();
            switch(r2) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0048;
                case 2: goto L_0x0048;
                case 3: goto L_0x0056;
                default: goto L_0x0013;
            };
        L_0x0013:
            r2 = r0;
        L_0x0014:
            if (r2 == 0) goto L_0x003b;
        L_0x0016:
            if (r1 == 0) goto L_0x005b;
        L_0x0018:
            r0 = r6.f2561c;
            r0 = r0.contains(r3, r4);
            if (r0 != 0) goto L_0x005b;
        L_0x0020:
            r0 = r6.f2559a;
            r0 = r0.getWidth();
            r0 = r0 / 2;
            r0 = (float) r0;
            r1 = r6.f2559a;
            r1 = r1.getHeight();
            r1 = r1 / 2;
            r1 = (float) r1;
            r7.setLocation(r0, r1);
        L_0x0035:
            r0 = r6.f2559a;
            r0 = r0.dispatchTouchEvent(r7);
        L_0x003b:
            return r0;
        L_0x003c:
            r2 = r6.f2560b;
            r2 = r2.contains(r3, r4);
            if (r2 == 0) goto L_0x0013;
        L_0x0044:
            r6.f2564f = r1;
            r2 = r1;
            goto L_0x0014;
        L_0x0048:
            r2 = r6.f2564f;
            if (r2 == 0) goto L_0x0014;
        L_0x004c:
            r5 = r6.f2562d;
            r5 = r5.contains(r3, r4);
            if (r5 != 0) goto L_0x0014;
        L_0x0054:
            r1 = r0;
            goto L_0x0014;
        L_0x0056:
            r2 = r6.f2564f;
            r6.f2564f = r0;
            goto L_0x0014;
        L_0x005b:
            r0 = r6.f2561c;
            r0 = r0.left;
            r0 = r3 - r0;
            r0 = (float) r0;
            r1 = r6.f2561c;
            r1 = r1.top;
            r1 = r4 - r1;
            r1 = (float) r1;
            r7.setLocation(r0, r1);
            goto L_0x0035;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SearchView.e.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    /* renamed from: a */
    private Intent m4981a(String str, Uri uri, String str2, String str3, int i, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(ErrorDialogData.BINDER_CRASH);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.f2575J);
        if (str3 != null) {
            intent.putExtra(SearchIntents.EXTRA_QUERY, str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        if (this.f2578M != null) {
            intent.putExtra("app_data", this.f2578M);
        }
        if (i != 0) {
            intent.putExtra("action_key", i);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.f2590h.getSearchActivity());
        return intent;
    }

    /* renamed from: a */
    private void m4982a(View view, Rect rect) {
        view.getLocationInWindow(this.f2596o);
        getLocationInWindow(this.f2597p);
        int i = this.f2596o[1] - this.f2597p[1];
        int i2 = this.f2596o[0] - this.f2597p[0];
        rect.set(i2, i, view.getWidth() + i2, view.getHeight() + i);
    }

    /* renamed from: a */
    private void m4983a(boolean z) {
        boolean z2 = true;
        int i = 8;
        this.f2568C = z;
        int i2 = z ? 0 : 8;
        boolean z3 = !TextUtils.isEmpty(this.f2583a.getText());
        this.f2584b.setVisibility(i2);
        m4986b(z3);
        this.f2591j.setVisibility(z ? 8 : 0);
        if (!(this.f2598q.getDrawable() == null || this.f2567B)) {
            i = 0;
        }
        this.f2598q.setVisibility(i);
        m4991m();
        if (z3) {
            z2 = false;
        }
        m4987c(z2);
        m4990l();
    }

    /* renamed from: a */
    static boolean m4984a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    /* renamed from: b */
    private CharSequence m4985b(CharSequence charSequence) {
        if (!this.f2567B || this.f2599r == null) {
            return charSequence;
        }
        int textSize = (int) (((double) this.f2583a.getTextSize()) * 1.25d);
        this.f2599r.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new ImageSpan(this.f2599r), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    /* renamed from: b */
    private void m4986b(boolean z) {
        int i = 8;
        if (this.f2569D && m4989k() && hasFocus() && (z || !this.f2574I)) {
            i = 0;
        }
        this.f2585c.setVisibility(i);
    }

    /* renamed from: c */
    private void m4987c(boolean z) {
        int i;
        if (this.f2574I && !m5001c() && z) {
            i = 0;
            this.f2585c.setVisibility(8);
        } else {
            i = 8;
        }
        this.f2587e.setVisibility(i);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(C0741d.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(C0741d.abc_search_view_preferred_width);
    }

    /* renamed from: i */
    private boolean m4988i() {
        if (this.f2590h == null || !this.f2590h.getVoiceSearchEnabled()) {
            return false;
        }
        Intent intent = null;
        if (this.f2590h.getVoiceSearchLaunchWebSearch()) {
            intent = this.f2602u;
        } else if (this.f2590h.getVoiceSearchLaunchRecognizer()) {
            intent = this.f2603v;
        }
        return (intent == null || getContext().getPackageManager().resolveActivity(intent, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) == null) ? false : true;
    }

    /* renamed from: k */
    private boolean m4989k() {
        return (this.f2569D || this.f2574I) && !m5001c();
    }

    /* renamed from: l */
    private void m4990l() {
        int i = 8;
        if (m4989k() && (this.f2585c.getVisibility() == 0 || this.f2587e.getVisibility() == 0)) {
            i = 0;
        }
        this.f2592k.setVisibility(i);
    }

    /* renamed from: m */
    private void m4991m() {
        int i = 1;
        int i2 = 0;
        int i3 = !TextUtils.isEmpty(this.f2583a.getText()) ? 1 : 0;
        if (i3 == 0 && (!this.f2567B || this.f2576K)) {
            i = 0;
        }
        ImageView imageView = this.f2586d;
        if (i == 0) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        Drawable drawable = this.f2586d.getDrawable();
        if (drawable != null) {
            drawable.setState(i3 != 0 ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    /* renamed from: n */
    private void m4992n() {
        post(this.f2580O);
    }

    /* renamed from: o */
    private void m4993o() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.f2583a;
        if (queryHint == null) {
            queryHint = TtmlNode.ANONYMOUS_REGION_ID;
        }
        searchAutoComplete.setHint(m4985b(queryHint));
    }

    /* renamed from: p */
    private void m4994p() {
        int i = 1;
        this.f2583a.setThreshold(this.f2590h.getSuggestThreshold());
        this.f2583a.setImeOptions(this.f2590h.getImeOptions());
        int inputType = this.f2590h.getInputType();
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.f2590h.getSuggestAuthority() != null) {
                inputType = (inputType | C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) | 524288;
            }
        }
        this.f2583a.setInputType(inputType);
        if (this.f2589g != null) {
            this.f2589g.mo572a(null);
        }
        if (this.f2590h.getSuggestAuthority() != null) {
            this.f2589g = new be(getContext(), this, this.f2590h, this.f2582Q);
            this.f2583a.setAdapter(this.f2589g);
            be beVar = (be) this.f2589g;
            if (this.f2571F) {
                i = 2;
            }
            beVar.m5638a(i);
        }
    }

    /* renamed from: q */
    private void m4995q() {
        this.f2583a.dismissDropDown();
    }

    private void setQuery(CharSequence charSequence) {
        this.f2583a.setText(charSequence);
        this.f2583a.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    /* renamed from: a */
    public void mo747a() {
        if (!this.f2576K) {
            this.f2576K = true;
            this.f2577L = this.f2583a.getImeOptions();
            this.f2583a.setImeOptions(this.f2577L | ConnectionsManager.FileTypeVideo);
            this.f2583a.setText(TtmlNode.ANONYMOUS_REGION_ID);
            setIconified(false);
        }
    }

    /* renamed from: a */
    void m4997a(int i, String str, String str2) {
        getContext().startActivity(m4981a("android.intent.action.SEARCH", null, null, str2, i, str));
    }

    /* renamed from: a */
    void m4998a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    /* renamed from: a */
    public void m4999a(CharSequence charSequence, boolean z) {
        this.f2583a.setText(charSequence);
        if (charSequence != null) {
            this.f2583a.setSelection(this.f2583a.length());
            this.f2575J = charSequence;
        }
        if (z && !TextUtils.isEmpty(charSequence)) {
            m5002d();
        }
    }

    /* renamed from: b */
    public void mo748b() {
        m4999a(TtmlNode.ANONYMOUS_REGION_ID, false);
        clearFocus();
        m4983a(true);
        this.f2583a.setImeOptions(this.f2577L);
        this.f2576K = false;
    }

    /* renamed from: c */
    public boolean m5001c() {
        return this.f2568C;
    }

    public void clearFocus() {
        this.f2572G = true;
        setImeVisibility(false);
        super.clearFocus();
        this.f2583a.clearFocus();
        this.f2572G = false;
    }

    /* renamed from: d */
    void m5002d() {
        CharSequence text = this.f2583a.getText();
        if (text != null && TextUtils.getTrimmedLength(text) > 0) {
            if (this.f2605x == null || !this.f2605x.m4979a(text.toString())) {
                if (this.f2590h != null) {
                    m4997a(0, null, text.toString());
                }
                setImeVisibility(false);
                m4995q();
            }
        }
    }

    /* renamed from: e */
    void m5003e() {
        if (!TextUtils.isEmpty(this.f2583a.getText())) {
            this.f2583a.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.f2583a.requestFocus();
            setImeVisibility(true);
        } else if (!this.f2567B) {
        } else {
            if (this.f2606y == null || !this.f2606y.m4978a()) {
                clearFocus();
                m4983a(true);
            }
        }
    }

    /* renamed from: f */
    void m5004f() {
        m4983a(false);
        this.f2583a.requestFocus();
        setImeVisibility(true);
        if (this.f2566A != null) {
            this.f2566A.onClick(this);
        }
    }

    /* renamed from: g */
    void m5005g() {
        m4983a(m5001c());
        m4992n();
        if (this.f2583a.hasFocus()) {
            m5006h();
        }
    }

    public int getImeOptions() {
        return this.f2583a.getImeOptions();
    }

    public int getInputType() {
        return this.f2583a.getInputType();
    }

    public int getMaxWidth() {
        return this.f2573H;
    }

    public CharSequence getQuery() {
        return this.f2583a.getText();
    }

    public CharSequence getQueryHint() {
        return this.f2570E != null ? this.f2570E : (this.f2590h == null || this.f2590h.getHintId() == 0) ? this.f2604w : getContext().getText(this.f2590h.getHintId());
    }

    int getSuggestionCommitIconResId() {
        return this.f2601t;
    }

    int getSuggestionRowLayout() {
        return this.f2600s;
    }

    public C0694g getSuggestionsAdapter() {
        return this.f2589g;
    }

    /* renamed from: h */
    void m5006h() {
        f2565i.m4975a(this.f2583a);
        f2565i.m4977b(this.f2583a);
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.f2580O);
        post(this.f2581P);
        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            m4982a(this.f2583a, this.f2594m);
            this.f2595n.set(this.f2594m.left, 0, this.f2594m.right, i4 - i2);
            if (this.f2593l == null) {
                this.f2593l = new C0962e(this.f2595n, this.f2594m, this.f2583a);
                setTouchDelegate(this.f2593l);
                return;
            }
            this.f2593l.m4980a(this.f2595n, this.f2594m);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (m5001c()) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        switch (mode) {
            case Integer.MIN_VALUE:
                if (this.f2573H <= 0) {
                    size = Math.min(getPreferredWidth(), size);
                    break;
                } else {
                    size = Math.min(this.f2573H, size);
                    break;
                }
            case 0:
                if (this.f2573H <= 0) {
                    size = getPreferredWidth();
                    break;
                } else {
                    size = this.f2573H;
                    break;
                }
            case 1073741824:
                if (this.f2573H > 0) {
                    size = Math.min(this.f2573H, size);
                    break;
                }
                break;
        }
        int mode2 = MeasureSpec.getMode(i2);
        mode = MeasureSpec.getSize(i2);
        switch (mode2) {
            case Integer.MIN_VALUE:
                mode = Math.min(getPreferredHeight(), mode);
                break;
            case 0:
                mode = getPreferredHeight();
                break;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(mode, 1073741824));
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            m4983a(savedState.f2549a);
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f2549a = m5001c();
        return savedState;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        m4992n();
    }

    public boolean requestFocus(int i, Rect rect) {
        if (this.f2572G || !isFocusable()) {
            return false;
        }
        if (m5001c()) {
            return super.requestFocus(i, rect);
        }
        boolean requestFocus = this.f2583a.requestFocus(i, rect);
        if (requestFocus) {
            m4983a(false);
        }
        return requestFocus;
    }

    public void setAppSearchData(Bundle bundle) {
        this.f2578M = bundle;
    }

    public void setIconified(boolean z) {
        if (z) {
            m5003e();
        } else {
            m5004f();
        }
    }

    public void setIconifiedByDefault(boolean z) {
        if (this.f2567B != z) {
            this.f2567B = z;
            m4983a(z);
            m4993o();
        }
    }

    public void setImeOptions(int i) {
        this.f2583a.setImeOptions(i);
    }

    void setImeVisibility(boolean z) {
        if (z) {
            post(this.f2579N);
            return;
        }
        removeCallbacks(this.f2579N);
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    public void setInputType(int i) {
        this.f2583a.setInputType(i);
    }

    public void setMaxWidth(int i) {
        this.f2573H = i;
        requestLayout();
    }

    public void setOnCloseListener(C0959b c0959b) {
        this.f2606y = c0959b;
    }

    public void setOnQueryTextFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.f2588f = onFocusChangeListener;
    }

    public void setOnQueryTextListener(C0960c c0960c) {
        this.f2605x = c0960c;
    }

    public void setOnSearchClickListener(OnClickListener onClickListener) {
        this.f2566A = onClickListener;
    }

    public void setOnSuggestionListener(C0961d c0961d) {
        this.f2607z = c0961d;
    }

    public void setQueryHint(CharSequence charSequence) {
        this.f2570E = charSequence;
        m4993o();
    }

    public void setQueryRefinementEnabled(boolean z) {
        this.f2571F = z;
        if (this.f2589g instanceof be) {
            ((be) this.f2589g).m5638a(z ? 2 : 1);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.f2590h = searchableInfo;
        if (this.f2590h != null) {
            m4994p();
            m4993o();
        }
        this.f2574I = m4988i();
        if (this.f2574I) {
            this.f2583a.setPrivateImeOptions("nm");
        }
        m4983a(m5001c());
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.f2569D = z;
        m4983a(m5001c());
    }

    public void setSuggestionsAdapter(C0694g c0694g) {
        this.f2589g = c0694g;
        this.f2583a.setAdapter(this.f2589g);
    }
}
