package org.telegram.messenger.support.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ah;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.support.widget.RecyclerView;

class ItemTouchUIUtilImpl {

    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() {
        }

        private void draw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2) {
            canvas.save();
            canvas.translate(f, f2);
            recyclerView.drawChild(canvas, view, 0);
            canvas.restore();
        }

        public void clearView(View view) {
            view.setVisibility(0);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i != 2) {
                draw(canvas, recyclerView, view, f, f2);
            }
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i == 2) {
                draw(canvas, recyclerView, view, f, f2);
            }
        }

        public void onSelected(View view) {
            view.setVisibility(4);
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {
        Honeycomb() {
        }

        public void clearView(View view) {
            ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
            ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            ah.m2775a(view, f);
            ah.m2795b(view, f2);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
        }

        public void onSelected(View view) {
        }
    }

    static class Lollipop extends Honeycomb {
        Lollipop() {
        }

        private float findMaxElevation(RecyclerView recyclerView, View view) {
            int childCount = recyclerView.getChildCount();
            float f = BitmapDescriptorFactory.HUE_RED;
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                if (childAt != view) {
                    float u = ah.m2831u(childAt);
                    if (u > f) {
                        f = u;
                    }
                }
            }
            return f;
        }

        public void clearView(View view) {
            Object tag = view.getTag();
            if (tag != null && (tag instanceof Float)) {
                ah.m2821k(view, ((Float) tag).floatValue());
            }
            view.setTag(null);
            super.clearView(view);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (z && view.getTag() == null) {
                Float valueOf = Float.valueOf(ah.m2831u(view));
                ah.m2821k(view, 1.0f + findMaxElevation(recyclerView, view));
                view.setTag(valueOf);
            }
            super.onDraw(canvas, recyclerView, view, f, f2, i, z);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
