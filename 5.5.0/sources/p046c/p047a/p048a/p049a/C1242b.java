package p046c.p047a.p048a.p049a;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView.C0926a;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: c.a.a.a.b */
public class C1242b extends C1241a {
    public C1242b(C0926a c0926a) {
        super(c0926a);
    }

    /* renamed from: a */
    protected Animator[] mo1092a(View view) {
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getMeasuredHeight(), BitmapDescriptorFactory.HUE_RED});
        return animatorArr;
    }
}
