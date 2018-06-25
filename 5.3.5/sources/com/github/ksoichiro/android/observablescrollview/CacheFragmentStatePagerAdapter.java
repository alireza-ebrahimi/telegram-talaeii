package com.github.ksoichiro.android.observablescrollview;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class CacheFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private static final String STATE_PAGES = "pages";
    private static final String STATE_PAGE_INDEX_PREFIX = "pageIndex:";
    private static final String STATE_PAGE_KEY_PREFIX = "page:";
    private static final String STATE_SUPER_STATE = "superState";
    private FragmentManager mFm;
    private SparseArray<Fragment> mPages = new SparseArray();

    protected abstract Fragment createItem(int i);

    public CacheFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFm = fm;
    }

    public Parcelable saveState() {
        Parcelable p = super.saveState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER_STATE, p);
        bundle.putInt(STATE_PAGES, this.mPages.size());
        if (this.mPages.size() > 0) {
            for (int i = 0; i < this.mPages.size(); i++) {
                int position = this.mPages.keyAt(i);
                bundle.putInt(createCacheIndex(i), position);
                this.mFm.putFragment(bundle, createCacheKey(position), (Fragment) this.mPages.get(position));
            }
        }
        return bundle;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle bundle = (Bundle) state;
        int pages = bundle.getInt(STATE_PAGES);
        if (pages > 0) {
            for (int i = 0; i < pages; i++) {
                int position = bundle.getInt(createCacheIndex(i));
                this.mPages.put(position, this.mFm.getFragment(bundle, createCacheKey(position)));
            }
        }
        super.restoreState(bundle.getParcelable(STATE_SUPER_STATE), loader);
    }

    public Fragment getItem(int position) {
        Fragment f = createItem(position);
        this.mPages.put(position, f);
        return f;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        if (this.mPages.indexOfKey(position) >= 0) {
            this.mPages.remove(position);
        }
        super.destroyItem(container, position, object);
    }

    public Fragment getItemAt(int position) {
        return (Fragment) this.mPages.get(position);
    }

    protected String createCacheIndex(int index) {
        return STATE_PAGE_INDEX_PREFIX + index;
    }

    protected String createCacheKey(int position) {
        return STATE_PAGE_KEY_PREFIX + position;
    }
}
