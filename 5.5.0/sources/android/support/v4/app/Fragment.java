package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.p022f.C0463k;
import android.support.v4.p022f.C0468d;
import android.support.v4.view.C0636j;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    static final Object USE_DEFAULT_TRANSITION = new Object();
    private static final C0463k<String, Class<?>> sClassMap = new C0463k();
    boolean mAdded;
    C0228a mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    C0366y mChildFragmentManager;
    C0367z mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    C0366y mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    C0350w mHost;
    boolean mInLayout;
    int mIndex = -1;
    View mInnerView;
    boolean mIsNewlyAdded;
    af mLoaderManager;
    boolean mLoadersStarted;
    boolean mMenuVisible = true;
    Fragment mParentFragment;
    float mPostponedAlpha;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    SparseArray<Parcelable> mSavedViewState;
    int mState = 0;
    String mTag;
    Fragment mTarget;
    int mTargetIndex = -1;
    int mTargetRequestCode;
    boolean mUserVisibleHint = true;
    View mView;
    String mWho;

    /* renamed from: android.support.v4.app.Fragment$1 */
    class C02241 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ Fragment f730a;

        C02241(Fragment fragment) {
            this.f730a = fragment;
        }

        public void run() {
            this.f730a.callStartTransitionListener();
        }
    }

    /* renamed from: android.support.v4.app.Fragment$2 */
    class C02262 extends C0225u {
        /* renamed from: a */
        final /* synthetic */ Fragment f731a;

        C02262(Fragment fragment) {
            this.f731a = fragment;
        }

        /* renamed from: a */
        public View mo199a(int i) {
            if (this.f731a.mView != null) {
                return this.f731a.mView.findViewById(i);
            }
            throw new IllegalStateException("Fragment does not have a view");
        }

        /* renamed from: a */
        public boolean mo200a() {
            return this.f731a.mView != null;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C02271();
        /* renamed from: a */
        final Bundle f732a;

        /* renamed from: android.support.v4.app.Fragment$SavedState$1 */
        static class C02271 implements Creator<SavedState> {
            C02271() {
            }

            /* renamed from: a */
            public SavedState m1041a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] m1042a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m1041a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m1042a(i);
            }
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            this.f732a = parcel.readBundle();
            if (classLoader != null && this.f732a != null) {
                this.f732a.setClassLoader(classLoader);
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBundle(this.f732a);
        }
    }

    /* renamed from: android.support.v4.app.Fragment$a */
    static class C0228a {
        /* renamed from: a */
        View f733a;
        /* renamed from: b */
        int f734b;
        /* renamed from: c */
        int f735c;
        /* renamed from: d */
        int f736d;
        /* renamed from: e */
        int f737e;
        /* renamed from: f */
        bd f738f = null;
        /* renamed from: g */
        bd f739g = null;
        /* renamed from: h */
        boolean f740h;
        /* renamed from: i */
        C0230c f741i;
        /* renamed from: j */
        boolean f742j;
        /* renamed from: k */
        private Object f743k = null;
        /* renamed from: l */
        private Object f744l = Fragment.USE_DEFAULT_TRANSITION;
        /* renamed from: m */
        private Object f745m = null;
        /* renamed from: n */
        private Object f746n = Fragment.USE_DEFAULT_TRANSITION;
        /* renamed from: o */
        private Object f747o = null;
        /* renamed from: p */
        private Object f748p = Fragment.USE_DEFAULT_TRANSITION;
        /* renamed from: q */
        private Boolean f749q;
        /* renamed from: r */
        private Boolean f750r;

        C0228a() {
        }
    }

    /* renamed from: android.support.v4.app.Fragment$b */
    public static class C0229b extends RuntimeException {
        public C0229b(String str, Exception exception) {
            super(str, exception);
        }
    }

    /* renamed from: android.support.v4.app.Fragment$c */
    interface C0230c {
        /* renamed from: a */
        void mo281a();

        /* renamed from: b */
        void mo282b();
    }

    private void callStartTransitionListener() {
        C0230c c0230c = null;
        if (this.mAnimationInfo != null) {
            this.mAnimationInfo.f740h = false;
            C0230c c0230c2 = this.mAnimationInfo.f741i;
            this.mAnimationInfo.f741i = null;
            c0230c = c0230c2;
        }
        if (c0230c != null) {
            c0230c.mo281a();
        }
    }

    private C0228a ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new C0228a();
        }
        return this.mAnimationInfo;
    }

    public static Fragment instantiate(Context context, String str) {
        return instantiate(context, str, null);
    }

    public static Fragment instantiate(Context context, String str, Bundle bundle) {
        try {
            Class cls = (Class) sClassMap.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                sClassMap.put(str, cls);
            }
            Fragment fragment = (Fragment) cls.newInstance();
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.mArguments = bundle;
            }
            return fragment;
        } catch (Exception e) {
            throw new C0229b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e);
        } catch (Exception e2) {
            throw new C0229b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e2);
        } catch (Exception e22) {
            throw new C0229b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e22);
        }
    }

    static boolean isSupportFragmentClass(Context context, String str) {
        try {
            Class cls = (Class) sClassMap.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                sClassMap.put(str, cls);
            }
            return Fragment.class.isAssignableFrom(cls);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (getNextAnim() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.mView);
        }
        if (this.mInnerView != null) {
            printWriter.print(str);
            printWriter.print("mInnerView=");
            printWriter.println(this.mView);
        }
        if (getAnimatingAway() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(getAnimatingAway());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.println("Loader Manager:");
            this.mLoaderManager.m1166a(str + "  ", fileDescriptor, printWriter, strArr);
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(str);
            printWriter.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.mo287a(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    Fragment findFragmentByWho(String str) {
        return str.equals(this.mWho) ? this : this.mChildFragmentManager != null ? this.mChildFragmentManager.m1666b(str) : null;
    }

    public final C0353t getActivity() {
        return this.mHost == null ? null : (C0353t) this.mHost.m1509h();
    }

    public boolean getAllowEnterTransitionOverlap() {
        return (this.mAnimationInfo == null || this.mAnimationInfo.f750r == null) ? true : this.mAnimationInfo.f750r.booleanValue();
    }

    public boolean getAllowReturnTransitionOverlap() {
        return (this.mAnimationInfo == null || this.mAnimationInfo.f749q == null) ? true : this.mAnimationInfo.f749q.booleanValue();
    }

    View getAnimatingAway() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f733a;
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public final C0357x getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            if (this.mState >= 5) {
                this.mChildFragmentManager.m1706n();
            } else if (this.mState >= 4) {
                this.mChildFragmentManager.m1705m();
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.m1702l();
            } else if (this.mState >= 1) {
                this.mChildFragmentManager.m1700k();
            }
        }
        return this.mChildFragmentManager;
    }

    public Context getContext() {
        return this.mHost == null ? null : this.mHost.m1510i();
    }

    public Object getEnterTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f743k;
    }

    bd getEnterTransitionCallback() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f738f;
    }

    public Object getExitTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f745m;
    }

    bd getExitTransitionCallback() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f739g;
    }

    public final C0357x getFragmentManager() {
        return this.mFragmentManager;
    }

    public final Object getHost() {
        return this.mHost == null ? null : this.mHost.mo270g();
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public LayoutInflater getLayoutInflater(Bundle bundle) {
        LayoutInflater b = this.mHost.mo265b();
        getChildFragmentManager();
        C0636j.m3150a(b, this.mChildFragmentManager.m1713u());
        return b;
    }

    public ae getLoaderManager() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = this.mHost.m1490a(this.mWho, this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    int getNextAnim() {
        return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.f735c;
    }

    int getNextTransition() {
        return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.f736d;
    }

    int getNextTransitionStyle() {
        return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.f737e;
    }

    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public Object getReenterTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f746n == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mAnimationInfo.f746n;
    }

    public final Resources getResources() {
        if (this.mHost != null) {
            return this.mHost.m1510i().getResources();
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public Object getReturnTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f744l == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mAnimationInfo.f744l;
    }

    public Object getSharedElementEnterTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f747o;
    }

    public Object getSharedElementReturnTransition() {
        return this.mAnimationInfo == null ? null : this.mAnimationInfo.f748p == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : this.mAnimationInfo.f748p;
    }

    int getStateAfterAnimating() {
        return this.mAnimationInfo == null ? 0 : this.mAnimationInfo.f734b;
    }

    public final String getString(int i) {
        return getResources().getString(i);
    }

    public final String getString(int i, Object... objArr) {
        return getResources().getString(i, objArr);
    }

    public final String getTag() {
        return this.mTag;
    }

    public final Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public final CharSequence getText(int i) {
        return getResources().getText(i);
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    public View getView() {
        return this.mView;
    }

    public final boolean hasOptionsMenu() {
        return this.mHasMenu;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    void instantiateChildFragmentManager() {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment has not been attached yet.");
        }
        this.mChildFragmentManager = new C0366y();
        this.mChildFragmentManager.m1656a(this.mHost, new C02262(this), this);
    }

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    boolean isHideReplaced() {
        return this.mAnimationInfo == null ? false : this.mAnimationInfo.f742j;
    }

    final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    boolean isPostponed() {
        return this.mAnimationInfo == null ? false : this.mAnimationInfo.f740h;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isResumed() {
        return this.mState >= 5;
    }

    public final boolean isVisible() {
        return (!isAdded() || isHidden() || this.mView == null || this.mView.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @Deprecated
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    public void onAttach(Context context) {
        this.mCalled = true;
        Activity h = this.mHost == null ? null : this.mHost.m1509h();
        if (h != null) {
            this.mCalled = false;
            onAttach(h);
        }
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        this.mCalled = true;
        restoreChildFragmentState(bundle);
        if (this.mChildFragmentManager != null && !this.mChildFragmentManager.m1660a(1)) {
            this.mChildFragmentManager.m1700k();
        }
    }

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        return null;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void onDestroy() {
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.m1490a(this.mWho, this.mLoadersStarted, false);
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.m1174h();
        }
    }

    public void onDestroyOptionsMenu() {
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public void onHiddenChanged(boolean z) {
    }

    @Deprecated
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
        Activity h = this.mHost == null ? null : this.mHost.m1509h();
        if (h != null) {
            this.mCalled = false;
            onInflate(h, attributeSet, bundle);
        }
    }

    public void onLowMemory() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean z) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    public void onPause() {
        this.mCalled = true;
    }

    public void onPictureInPictureModeChanged(boolean z) {
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
        this.mCalled = true;
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.m1490a(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.m1168b();
            }
        }
    }

    public void onStop() {
        this.mCalled = true;
    }

    public void onViewCreated(View view, Bundle bundle) {
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    C0357x peekChildFragmentManager() {
        return this.mChildFragmentManager;
    }

    void performActivityCreated(Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1698j();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated(bundle);
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onActivityCreated()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1702l();
        }
    }

    void performConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1647a(configuration);
        }
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (onContextItemSelected(menuItem)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.m1675b(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performCreate(Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1698j();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate(bundle);
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onCreate()");
        }
    }

    boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true;
            onCreateOptionsMenu(menu, menuInflater);
        }
        return this.mChildFragmentManager != null ? z | this.mChildFragmentManager.m1662a(menu, menuInflater) : z;
    }

    View performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1698j();
        }
        return onCreateView(layoutInflater, viewGroup, bundle);
    }

    void performDestroy() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1711s();
        }
        this.mState = 0;
        this.mCalled = false;
        onDestroy();
        if (this.mCalled) {
            this.mChildFragmentManager = null;
            return;
        }
        throw new be("Fragment " + this + " did not call through to super.onDestroy()");
    }

    void performDestroyView() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1710r();
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onDestroyView()");
        } else if (this.mLoaderManager != null) {
            this.mLoaderManager.m1172f();
        }
    }

    void performDetach() {
        this.mCalled = false;
        onDetach();
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onDetach()");
        } else if (this.mChildFragmentManager == null) {
        } else {
            if (this.mRetaining) {
                this.mChildFragmentManager.m1711s();
                this.mChildFragmentManager = null;
                return;
            }
            throw new IllegalStateException("Child FragmentManager of " + this + " was not " + " destroyed and this fragment is not retaining instance");
        }
    }

    void performLowMemory() {
        onLowMemory();
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1712t();
        }
    }

    void performMultiWindowModeChanged(boolean z) {
        onMultiWindowModeChanged(z);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1659a(z);
        }
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(menuItem)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.m1663a(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performOptionsMenuClosed(Menu menu) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onOptionsMenuClosed(menu);
            }
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.m1672b(menu);
            }
        }
    }

    void performPause() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1707o();
        }
        this.mState = 4;
        this.mCalled = false;
        onPause();
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    void performPictureInPictureModeChanged(boolean z) {
        onPictureInPictureModeChanged(z);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1673b(z);
        }
    }

    boolean performPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            z = true;
            onPrepareOptionsMenu(menu);
        }
        return this.mChildFragmentManager != null ? z | this.mChildFragmentManager.m1661a(menu) : z;
    }

    void performReallyStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1709q();
        }
        this.mState = 2;
        if (this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.m1490a(this.mWho, this.mLoadersStarted, false);
            }
            if (this.mLoaderManager == null) {
                return;
            }
            if (this.mHost.m1513l()) {
                this.mLoaderManager.m1170d();
            } else {
                this.mLoaderManager.m1169c();
            }
        }
    }

    void performResume() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1698j();
            this.mChildFragmentManager.m1686e();
        }
        this.mState = 5;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onResume()");
        } else if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1706n();
            this.mChildFragmentManager.m1686e();
        }
    }

    void performSaveInstanceState(Bundle bundle) {
        onSaveInstanceState(bundle);
        if (this.mChildFragmentManager != null) {
            Parcelable i = this.mChildFragmentManager.m1696i();
            if (i != null) {
                bundle.putParcelable("android:support:fragments", i);
            }
        }
    }

    void performStart() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1698j();
            this.mChildFragmentManager.m1686e();
        }
        this.mState = 4;
        this.mCalled = false;
        onStart();
        if (this.mCalled) {
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.m1705m();
            }
            if (this.mLoaderManager != null) {
                this.mLoaderManager.m1173g();
                return;
            }
            return;
        }
        throw new be("Fragment " + this + " did not call through to super.onStart()");
    }

    void performStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.m1708p();
        }
        this.mState = 3;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onStop()");
        }
    }

    public void postponeEnterTransition() {
        ensureAnimationInfo().f740h = true;
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public final void requestPermissions(String[] strArr, int i) {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.mo261a(this, strArr, i);
    }

    void restoreChildFragmentState(Bundle bundle) {
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable("android:support:fragments");
            if (parcelable != null) {
                if (this.mChildFragmentManager == null) {
                    instantiateChildFragmentManager();
                }
                this.mChildFragmentManager.m1649a(parcelable, this.mChildNonConfig);
                this.mChildNonConfig = null;
                this.mChildFragmentManager.m1700k();
            }
        }
    }

    final void restoreViewState(Bundle bundle) {
        if (this.mSavedViewState != null) {
            this.mInnerView.restoreHierarchyState(this.mSavedViewState);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored(bundle);
        if (!this.mCalled) {
            throw new be("Fragment " + this + " did not call through to super.onViewStateRestored()");
        }
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
        ensureAnimationInfo().f750r = Boolean.valueOf(z);
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
        ensureAnimationInfo().f749q = Boolean.valueOf(z);
    }

    void setAnimatingAway(View view) {
        ensureAnimationInfo().f733a = view;
    }

    public void setArguments(Bundle bundle) {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        this.mArguments = bundle;
    }

    public void setEnterSharedElementCallback(bd bdVar) {
        ensureAnimationInfo().f738f = bdVar;
    }

    public void setEnterTransition(Object obj) {
        ensureAnimationInfo().f743k = obj;
    }

    public void setExitSharedElementCallback(bd bdVar) {
        ensureAnimationInfo().f739g = bdVar;
    }

    public void setExitTransition(Object obj) {
        ensureAnimationInfo().f745m = obj;
    }

    public void setHasOptionsMenu(boolean z) {
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (isAdded() && !isHidden()) {
                this.mHost.mo267d();
            }
        }
    }

    void setHideReplaced(boolean z) {
        ensureAnimationInfo().f742j = z;
    }

    final void setIndex(int i, Fragment fragment) {
        this.mIndex = i;
        if (fragment != null) {
            this.mWho = fragment.mWho + ":" + this.mIndex;
        } else {
            this.mWho = "android:fragment:" + this.mIndex;
        }
    }

    public void setInitialSavedState(SavedState savedState) {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        Bundle bundle = (savedState == null || savedState.f732a == null) ? null : savedState.f732a;
        this.mSavedFragmentState = bundle;
    }

    public void setMenuVisibility(boolean z) {
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mHost.mo267d();
            }
        }
    }

    void setNextAnim(int i) {
        if (this.mAnimationInfo != null || i != 0) {
            ensureAnimationInfo().f735c = i;
        }
    }

    void setNextTransition(int i, int i2) {
        if (this.mAnimationInfo != null || i != 0 || i2 != 0) {
            ensureAnimationInfo();
            this.mAnimationInfo.f736d = i;
            this.mAnimationInfo.f737e = i2;
        }
    }

    void setOnStartEnterTransitionListener(C0230c c0230c) {
        ensureAnimationInfo();
        if (c0230c != this.mAnimationInfo.f741i) {
            if (c0230c == null || this.mAnimationInfo.f741i == null) {
                if (this.mAnimationInfo.f740h) {
                    this.mAnimationInfo.f741i = c0230c;
                }
                if (c0230c != null) {
                    c0230c.mo282b();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
    }

    public void setReenterTransition(Object obj) {
        ensureAnimationInfo().f746n = obj;
    }

    public void setRetainInstance(boolean z) {
        this.mRetainInstance = z;
    }

    public void setReturnTransition(Object obj) {
        ensureAnimationInfo().f744l = obj;
    }

    public void setSharedElementEnterTransition(Object obj) {
        ensureAnimationInfo().f747o = obj;
    }

    public void setSharedElementReturnTransition(Object obj) {
        ensureAnimationInfo().f748p = obj;
    }

    void setStateAfterAnimating(int i) {
        ensureAnimationInfo().f734b = i;
    }

    public void setTargetFragment(Fragment fragment, int i) {
        this.mTarget = fragment;
        this.mTargetRequestCode = i;
    }

    public void setUserVisibleHint(boolean z) {
        if (!this.mUserVisibleHint && z && this.mState < 4 && this.mFragmentManager != null && isAdded()) {
            this.mFragmentManager.m1650a(this);
        }
        this.mUserVisibleHint = z;
        boolean z2 = this.mState < 4 && !z;
        this.mDeferStart = z2;
    }

    public boolean shouldShowRequestPermissionRationale(String str) {
        return this.mHost != null ? this.mHost.mo264a(str) : false;
    }

    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(Intent intent, Bundle bundle) {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.mo259a(this, intent, -1, bundle);
    }

    public void startActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.mo259a(this, intent, i, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mHost.mo260a(this, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public void startPostponedEnterTransition() {
        if (this.mFragmentManager == null || this.mFragmentManager.f1128n == null) {
            ensureAnimationInfo().f740h = false;
        } else if (Looper.myLooper() != this.mFragmentManager.f1128n.m1511j().getLooper()) {
            this.mFragmentManager.f1128n.m1511j().postAtFrontOfQueue(new C02241(this));
        } else {
            callStartTransitionListener();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        C0468d.m2014a(this, stringBuilder);
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mTag);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }
}
