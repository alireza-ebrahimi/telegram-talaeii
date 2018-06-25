package utils.view.bottombar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;

class BottomBarBadge extends TextView {
    /* renamed from: a */
    private int f10389a;
    /* renamed from: b */
    private boolean f10390b = false;

    BottomBarBadge(Context context) {
        super(context);
    }

    /* renamed from: a */
    private void m14260a(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    /* renamed from: c */
    private void m14261c(final BottomBarTab bottomBarTab) {
        ViewGroup viewGroup = (ViewGroup) bottomBarTab.getParent();
        viewGroup.removeView(bottomBarTab);
        final View badgeContainer = new BadgeContainer(getContext());
        badgeContainer.setLayoutParams(new LayoutParams(-2, -2));
        badgeContainer.addView(bottomBarTab);
        badgeContainer.addView(this);
        viewGroup.addView(badgeContainer, bottomBarTab.getIndexInTabContainer());
        badgeContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            /* renamed from: c */
            final /* synthetic */ BottomBarBadge f10388c;

            public void onGlobalLayout() {
                badgeContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                this.f10388c.m14268b(bottomBarTab);
            }
        });
    }

    /* renamed from: a */
    int m14262a() {
        return this.f10389a;
    }

    /* renamed from: a */
    void m14263a(int i) {
        this.f10389a = i;
        setText(String.valueOf(i));
    }

    /* renamed from: a */
    void m14264a(BottomBarTab bottomBarTab) {
        BadgeContainer badgeContainer = (BadgeContainer) getParent();
        ViewGroup viewGroup = (ViewGroup) badgeContainer.getParent();
        badgeContainer.removeView(bottomBarTab);
        viewGroup.removeView(badgeContainer);
        viewGroup.addView(bottomBarTab, bottomBarTab.getIndexInTabContainer());
    }

    /* renamed from: a */
    void m14265a(BottomBarTab bottomBarTab, int i) {
        setLayoutParams(new LayoutParams(-2, -2));
        setGravity(17);
        MiscUtils.m14346a((TextView) this, (int) R.style.BB_BottomBarBadge_Text);
        m14267b(i);
        m14261c(bottomBarTab);
    }

    /* renamed from: b */
    void m14266b() {
        this.f10390b = true;
        ah.q(this).a(150).a(1.0f).d(1.0f).e(1.0f).c();
    }

    /* renamed from: b */
    void m14267b(int i) {
        int a = MiscUtils.m14344a(getContext(), 1.0f);
        Drawable a2 = BadgeCircle.m14205a(a * 3, i);
        setPadding(a, a, a, a);
        m14260a(a2);
    }

    /* renamed from: b */
    void m14268b(BottomBarTab bottomBarTab) {
        AppCompatImageView iconView = bottomBarTab.getIconView();
        LayoutParams layoutParams = getLayoutParams();
        int max = Math.max(getWidth(), getHeight());
        setX(iconView.getX() + ((float) (((double) iconView.getWidth()) / 1.25d)));
        setTranslationY(10.0f);
        if (layoutParams.width != max || layoutParams.height != max) {
            layoutParams.width = max;
            layoutParams.height = max;
            setLayoutParams(layoutParams);
        }
    }

    /* renamed from: c */
    void m14269c() {
        this.f10390b = false;
        ah.q(this).a(150).a(BitmapDescriptorFactory.HUE_RED).d(BitmapDescriptorFactory.HUE_RED).e(BitmapDescriptorFactory.HUE_RED).c();
    }
}
