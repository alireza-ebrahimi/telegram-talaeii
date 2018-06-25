package android.support.v7.widget.p032a;

import android.graphics.Canvas;
import android.support.v4.view.ah;
import android.support.v7.p029e.C0839a.C0837b;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v7.widget.a.c */
class C0994c {

    /* renamed from: android.support.v7.widget.a.c$a */
    static class C0991a implements C0990b {
        C0991a() {
        }

        /* renamed from: a */
        private void m5280a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2) {
            canvas.save();
            canvas.translate(f, f2);
            recyclerView.drawChild(canvas, view, 0);
            canvas.restore();
        }

        /* renamed from: a */
        public void mo898a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i != 2) {
                m5280a(canvas, recyclerView, view, f, f2);
            }
        }

        /* renamed from: a */
        public void mo899a(View view) {
            view.setVisibility(0);
        }

        /* renamed from: b */
        public void mo900b(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i == 2) {
                m5280a(canvas, recyclerView, view, f, f2);
            }
        }

        /* renamed from: b */
        public void mo901b(View view) {
            view.setVisibility(4);
        }
    }

    /* renamed from: android.support.v7.widget.a.c$b */
    static class C0992b implements C0990b {
        C0992b() {
        }

        /* renamed from: a */
        public void mo898a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            ah.m2775a(view, f);
            ah.m2795b(view, f2);
        }

        /* renamed from: a */
        public void mo899a(View view) {
            ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
            ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
        }

        /* renamed from: b */
        public void mo900b(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
        }

        /* renamed from: b */
        public void mo901b(View view) {
        }
    }

    /* renamed from: android.support.v7.widget.a.c$c */
    static class C0993c extends C0992b {
        C0993c() {
        }

        /* renamed from: a */
        private float m5289a(RecyclerView recyclerView, View view) {
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

        /* renamed from: a */
        public void mo898a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (z && view.getTag(C0837b.item_touch_helper_previous_elevation) == null) {
                Float valueOf = Float.valueOf(ah.m2831u(view));
                ah.m2821k(view, 1.0f + m5289a(recyclerView, view));
                view.setTag(C0837b.item_touch_helper_previous_elevation, valueOf);
            }
            super.mo898a(canvas, recyclerView, view, f, f2, i, z);
        }

        /* renamed from: a */
        public void mo899a(View view) {
            Object tag = view.getTag(C0837b.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ah.m2821k(view, ((Float) tag).floatValue());
            }
            view.setTag(C0837b.item_touch_helper_previous_elevation, null);
            super.mo899a(view);
        }
    }
}
