package android.support.design.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.C0073a.C0063b;
import android.support.design.C0073a.C0068g;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(21)
class ae {
    /* renamed from: a */
    private static final int[] f439a = new int[]{16843848};

    /* renamed from: a */
    static void m661a(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    /* renamed from: a */
    static void m662a(View view, float f) {
        int integer = view.getResources().getInteger(C0068g.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[]{16842766, C0063b.state_collapsible, -C0063b.state_collapsed}, ObjectAnimator.ofFloat(view, "elevation", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration((long) integer));
        stateListAnimator.addState(new int[]{16842766}, ObjectAnimator.ofFloat(view, "elevation", new float[]{f}).setDuration((long) integer));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(view, "elevation", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(0));
        view.setStateListAnimator(stateListAnimator);
    }

    /* renamed from: a */
    static void m663a(View view, AttributeSet attributeSet, int i, int i2) {
        Context context = view.getContext();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f439a, i, i2);
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, obtainStyledAttributes.getResourceId(0, 0)));
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }
}
