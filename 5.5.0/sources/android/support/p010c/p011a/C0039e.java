package android.support.p010c.p011a;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v4.view.aa;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(13)
/* renamed from: android.support.c.a.e */
public abstract class C0039e extends aa {
    /* renamed from: a */
    private final FragmentManager f100a;
    /* renamed from: b */
    private FragmentTransaction f101b = null;
    /* renamed from: c */
    private Fragment f102c = null;

    public C0039e(FragmentManager fragmentManager) {
        this.f100a = fragmentManager;
    }

    /* renamed from: a */
    private static String m116a(int i, long j) {
        return "android:switcher:" + i + ":" + j;
    }

    /* renamed from: a */
    public abstract Fragment mo3413a(int i);

    /* renamed from: b */
    public long m118b(int i) {
        return (long) i;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.f101b == null) {
            this.f101b = this.f100a.beginTransaction();
        }
        this.f101b.detach((Fragment) obj);
    }

    public void finishUpdate(ViewGroup viewGroup) {
        if (this.f101b != null) {
            this.f101b.commitAllowingStateLoss();
            this.f101b = null;
            this.f100a.executePendingTransactions();
        }
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.f101b == null) {
            this.f101b = this.f100a.beginTransaction();
        }
        long b = m118b(i);
        Fragment findFragmentByTag = this.f100a.findFragmentByTag(C0039e.m116a(viewGroup.getId(), b));
        if (findFragmentByTag != null) {
            this.f101b.attach(findFragmentByTag);
        } else {
            findFragmentByTag = mo3413a(i);
            this.f101b.add(viewGroup.getId(), findFragmentByTag, C0039e.m116a(viewGroup.getId(), b));
        }
        if (findFragmentByTag != this.f102c) {
            C0035a.m111a(findFragmentByTag, false);
            C0035a.m112b(findFragmentByTag, false);
        }
        return findFragmentByTag;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (fragment != this.f102c) {
            if (this.f102c != null) {
                C0035a.m111a(this.f102c, false);
                C0035a.m112b(this.f102c, false);
            }
            if (fragment != null) {
                C0035a.m111a(fragment, true);
                C0035a.m112b(fragment, true);
            }
            this.f102c = fragment;
        }
    }

    public void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }
}
