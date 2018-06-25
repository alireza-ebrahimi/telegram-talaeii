package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPathSegment;
import com.h6ah4i.android.widget.advrecyclerview.adapter.SimpleWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.UnwrapPositionResult;
import com.h6ah4i.android.widget.advrecyclerview.adapter.WrapperAdapter;

public class DebugWrapperAdapter extends SimpleWrapperAdapter<ViewHolder> {
    public static final int FLAGS_ALL_DEBUG_FEATURES = 3;
    public static final int FLAG_VERIFY_UNWRAP_POSITION = 2;
    public static final int FLAG_VERIFY_WRAP_POSITION = 1;
    private int mFlags = 3;

    public DebugWrapperAdapter(@NonNull Adapter adapter) {
        super(adapter);
    }

    public int wrapPosition(@NonNull AdapterPathSegment pathSegment, int position) {
        if ((this.mFlags & 1) != 0 && (getWrappedAdapter() instanceof WrapperAdapter)) {
            WrapperAdapter wrapperAdapter = (WrapperAdapter) getWrappedAdapter();
            int wrappedPosition = wrapperAdapter.wrapPosition(pathSegment, position);
            if (wrappedPosition != -1) {
                UnwrapPositionResult tmpResult = new UnwrapPositionResult();
                wrapperAdapter.unwrapPosition(tmpResult, wrappedPosition);
                if (tmpResult.position != position) {
                    throw new IllegalStateException("Found a WrapperAdapter implementation issue while executing wrapPosition(): " + getWrappedAdapter().getClass().getSimpleName() + "\nwrapPosition(" + position + ") returns " + wrappedPosition + ", but unwrapPosition(" + wrappedPosition + ") returns " + tmpResult.position);
                }
            }
        }
        return super.wrapPosition(pathSegment, position);
    }

    public void unwrapPosition(@NonNull UnwrapPositionResult dest, int position) {
        if ((this.mFlags & 2) != 0 && (getWrappedAdapter() instanceof WrapperAdapter)) {
            WrapperAdapter wrapperAdapter = (WrapperAdapter) getWrappedAdapter();
            UnwrapPositionResult tmpResult = new UnwrapPositionResult();
            wrapperAdapter.unwrapPosition(tmpResult, position);
            if (tmpResult.isValid()) {
                int reWrappedPosition = wrapperAdapter.wrapPosition(new AdapterPathSegment(tmpResult.adapter, tmpResult.tag), tmpResult.position);
                if (position != reWrappedPosition) {
                    throw new IllegalStateException("Found a WrapperAdapter implementation issue while executing unwrapPosition(): " + getWrappedAdapter().getClass().getSimpleName() + "\nunwrapPosition(" + position + ") returns " + tmpResult.position + ", but wrapPosition(" + tmpResult.position + ") returns " + reWrappedPosition);
                }
            }
        }
        super.unwrapPosition(dest, position);
    }

    public void setSettingFlags(int flags) {
        this.mFlags = flags;
    }

    public int getSettingFlags() {
        return this.mFlags;
    }
}
