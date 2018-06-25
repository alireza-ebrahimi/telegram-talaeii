package com.h6ah4i.android.widget.advrecyclerview.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewEventDistributor<T> {
    protected List<T> mListeners;
    protected boolean mPerformingClearMethod;
    protected RecyclerView mRecyclerView;
    protected boolean mReleased;

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void release() {
        if (!this.mReleased) {
            this.mReleased = true;
            clear(true);
            onRelease();
        }
    }

    public boolean isReleased() {
        return this.mReleased;
    }

    public void attachRecyclerView(RecyclerView rv) {
        String METHOD_NAME = "attachRecyclerView()";
        if (rv == null) {
            throw new IllegalArgumentException("RecyclerView cannot be null");
        }
        verifyIsNotReleased("attachRecyclerView()");
        verifyIsNotPerformingClearMethod("attachRecyclerView()");
        onRecyclerViewAttached(rv);
    }

    public boolean add(T listener) {
        return add(listener, -1);
    }

    public boolean add(@NonNull T listener, int index) {
        String METHOD_NAME = "add()";
        verifyIsNotReleased("add()");
        verifyIsNotPerformingClearMethod("add()");
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        if (!this.mListeners.contains(listener)) {
            if (index < 0) {
                this.mListeners.add(listener);
            } else {
                this.mListeners.add(index, listener);
            }
            if (listener instanceof RecyclerViewEventDistributorListener) {
                ((RecyclerViewEventDistributorListener) listener).onAddedToEventDistributor(this);
            }
        }
        return true;
    }

    public boolean remove(@NonNull T listener) {
        String METHOD_NAME = "remove()";
        verifyIsNotPerformingClearMethod("remove()");
        verifyIsNotReleased("remove()");
        if (this.mListeners == null) {
            return false;
        }
        boolean removed = this.mListeners.remove(listener);
        if (!removed || !(listener instanceof RecyclerViewEventDistributorListener)) {
            return removed;
        }
        ((RecyclerViewEventDistributorListener) listener).onRemovedFromEventDistributor(this);
        return removed;
    }

    public void clear() {
        clear(false);
    }

    protected void clear(boolean calledFromRelease) {
        String METHOD_NAME = "clear()";
        if (!calledFromRelease) {
            verifyIsNotReleased("clear()");
        }
        verifyIsNotPerformingClearMethod("clear()");
        if (this.mListeners != null) {
            try {
                this.mPerformingClearMethod = true;
                for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                    T listener = this.mListeners.remove(i);
                    if (listener instanceof RecyclerViewEventDistributorListener) {
                        ((RecyclerViewEventDistributorListener) listener).onRemovedFromEventDistributor(this);
                    }
                }
            } finally {
                this.mPerformingClearMethod = false;
            }
        }
    }

    public int size() {
        if (this.mListeners != null) {
            return this.mListeners.size();
        }
        return 0;
    }

    public boolean contains(T listener) {
        if (this.mListeners != null) {
            return this.mListeners.contains(listener);
        }
        return false;
    }

    protected void onRelease() {
        this.mRecyclerView = null;
        this.mListeners = null;
        this.mPerformingClearMethod = false;
    }

    protected void onRecyclerViewAttached(RecyclerView rv) {
        this.mRecyclerView = rv;
    }

    protected void verifyIsNotPerformingClearMethod(String methodName) {
        if (this.mPerformingClearMethod) {
            throw new IllegalStateException(methodName + " can not be called while performing the clear() method");
        }
    }

    protected void verifyIsNotReleased(String methodName) {
        if (this.mReleased) {
            throw new IllegalStateException(methodName + " can not be called after release() method called");
        }
    }
}
