package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPath;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPathSegment;
import com.h6ah4i.android.widget.advrecyclerview.adapter.SimpleWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.UnwrapPositionResult;
import com.h6ah4i.android.widget.advrecyclerview.adapter.WrapperAdapter;
import java.util.ArrayList;
import java.util.List;

public class WrapperAdapterUtils {
    private WrapperAdapterUtils() {
    }

    public static <T> T findWrappedAdapter(Adapter adapter, Class<T> clazz) {
        if (clazz.isInstance(adapter)) {
            return clazz.cast(adapter);
        }
        if (adapter instanceof SimpleWrapperAdapter) {
            return findWrappedAdapter(((SimpleWrapperAdapter) adapter).getWrappedAdapter(), clazz);
        }
        return null;
    }

    public static <T> T findWrappedAdapter(Adapter originAdapter, Class<T> clazz, int position) {
        AdapterPath path = new AdapterPath();
        if (unwrapPosition(originAdapter, null, null, position, path) == -1) {
            return null;
        }
        for (AdapterPathSegment segment : path.segments()) {
            if (clazz.isInstance(segment.adapter)) {
                return clazz.cast(segment.adapter);
            }
        }
        return null;
    }

    public static Adapter releaseAll(Adapter adapter) {
        return releaseCyclically(adapter);
    }

    private static Adapter releaseCyclically(Adapter adapter) {
        if (adapter instanceof WrapperAdapter) {
            WrapperAdapter wrapperAdapter = (WrapperAdapter) adapter;
            List<Adapter> wrappedAdapters = new ArrayList();
            wrapperAdapter.getWrappedAdapters(wrappedAdapters);
            wrapperAdapter.release();
            for (int i = wrappedAdapters.size() - 1; i >= 0; i--) {
                releaseCyclically((Adapter) wrappedAdapters.get(i));
            }
            wrappedAdapters.clear();
        }
        return adapter;
    }

    public static int unwrapPosition(@NonNull Adapter originAdapter, int position) {
        return unwrapPosition(originAdapter, null, position);
    }

    public static int unwrapPosition(@NonNull Adapter originAdapter, @Nullable Adapter targetAdapter, int position) {
        return unwrapPosition(originAdapter, targetAdapter, null, position, null);
    }

    public static int unwrapPosition(@NonNull Adapter originAdapter, @Nullable Adapter targetAdapter, Object targetAdapterTag, int position) {
        return unwrapPosition(originAdapter, targetAdapter, targetAdapterTag, position, null);
    }

    public static int unwrapPosition(Adapter originAdapter, Adapter targetAdapter, Object targetAdapterTag, int originPosition, @Nullable AdapterPath destPath) {
        Adapter wrapper = originAdapter;
        int wrappedPosition = originPosition;
        UnwrapPositionResult tmpResult = new UnwrapPositionResult();
        Object wrappedAdapterTag = null;
        if (destPath != null) {
            destPath.clear();
        }
        if (wrapper == null) {
            return -1;
        }
        if (destPath != null) {
            destPath.append(new AdapterPathSegment(originAdapter, null));
        }
        while (wrappedPosition != -1 && wrapper != targetAdapter) {
            if (wrapper instanceof WrapperAdapter) {
                WrapperAdapter wrapperParentAdapter = (WrapperAdapter) wrapper;
                tmpResult.clear();
                wrapperParentAdapter.unwrapPosition(tmpResult, wrappedPosition);
                wrappedPosition = tmpResult.position;
                wrappedAdapterTag = tmpResult.tag;
                if (tmpResult.isValid() && destPath != null) {
                    destPath.append(tmpResult);
                }
                wrapper = tmpResult.adapter;
                if (wrapper == null) {
                    break;
                }
            } else if (targetAdapter != null) {
                wrappedPosition = -1;
            }
        }
        if (!(targetAdapter == null || wrapper == targetAdapter)) {
            wrappedPosition = -1;
        }
        if (!(targetAdapterTag == null || r1 == targetAdapterTag)) {
            wrappedPosition = -1;
        }
        if (wrappedPosition == -1 && destPath != null) {
            destPath.clear();
        }
        return wrappedPosition;
    }

    public static int wrapPosition(@NonNull AdapterPath path, @Nullable Adapter originAdapter, @Nullable Adapter targetAdapter, int position) {
        int originSegmentIndex;
        int targetSegmentIndex;
        List<AdapterPathSegment> segments = path.segments();
        int nSegments = segments.size();
        if (originAdapter == null) {
            originSegmentIndex = nSegments - 1;
        } else {
            originSegmentIndex = -1;
        }
        if (targetAdapter == null) {
            targetSegmentIndex = 0;
        } else {
            targetSegmentIndex = -1;
        }
        if (!(originAdapter == null && targetAdapter == null)) {
            for (int i = 0; i < nSegments; i++) {
                AdapterPathSegment segment = (AdapterPathSegment) segments.get(i);
                if (originAdapter != null && segment.adapter == originAdapter) {
                    originSegmentIndex = i;
                }
                if (targetAdapter != null && segment.adapter == targetAdapter) {
                    targetSegmentIndex = i;
                }
            }
        }
        if (originSegmentIndex == -1 || targetSegmentIndex == -1 || targetSegmentIndex > originSegmentIndex) {
            return -1;
        }
        return wrapPosition(path, originSegmentIndex, targetSegmentIndex, position);
    }

    public static int wrapPosition(@NonNull AdapterPath path, int originSegmentIndex, int targetSegmentIndex, int position) {
        List<AdapterPathSegment> segments = path.segments();
        int wrappedPosition = position;
        for (int i = originSegmentIndex; i > targetSegmentIndex; i--) {
            int prevWrappedPosition = wrappedPosition;
            wrappedPosition = ((WrapperAdapter) ((AdapterPathSegment) segments.get(i - 1)).adapter).wrapPosition((AdapterPathSegment) segments.get(i), wrappedPosition);
            if (wrappedPosition == -1) {
                break;
            }
        }
        return wrappedPosition;
    }
}
