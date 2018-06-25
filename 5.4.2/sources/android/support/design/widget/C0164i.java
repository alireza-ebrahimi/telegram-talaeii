package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.design.widget.C0160j.C0108a;
import android.support.design.widget.C0201w.C0127d;
import android.support.v4.view.ah;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(14)
/* renamed from: android.support.design.widget.i */
class C0164i extends C0161h {
    /* renamed from: q */
    private float f562q = this.n.getRotation();

    C0164i(af afVar, C0111p c0111p, C0127d c0127d) {
        super(afVar, c0111p, c0127d);
    }

    /* renamed from: l */
    private boolean m793l() {
        return ah.m2767G(this.n) && !this.n.isInEditMode();
    }

    /* renamed from: m */
    private void m794m() {
        if (VERSION.SDK_INT == 19) {
            if (this.f562q % 90.0f != BitmapDescriptorFactory.HUE_RED) {
                if (this.n.getLayerType() != 1) {
                    this.n.setLayerType(1, null);
                }
            } else if (this.n.getLayerType() != 0) {
                this.n.setLayerType(0, null);
            }
        }
        if (this.a != null) {
            this.a.m819a(-this.f562q);
        }
        if (this.f != null) {
            this.f.m692a(-this.f562q);
        }
    }

    /* renamed from: a */
    void mo151a(final C0108a c0108a, final boolean z) {
        if (!m779k()) {
            this.n.animate().cancel();
            if (m793l()) {
                this.c = 1;
                this.n.animate().scaleX(BitmapDescriptorFactory.HUE_RED).scaleY(BitmapDescriptorFactory.HUE_RED).alpha(BitmapDescriptorFactory.HUE_RED).setDuration(200).setInterpolator(C0126a.f428c).setListener(new AnimatorListenerAdapter(this) {
                    /* renamed from: c */
                    final /* synthetic */ C0164i f557c;
                    /* renamed from: d */
                    private boolean f558d;

                    public void onAnimationCancel(Animator animator) {
                        this.f558d = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        this.f557c.c = 0;
                        if (!this.f558d) {
                            this.f557c.n.m582a(z ? 8 : 4, z);
                            if (c0108a != null) {
                                c0108a.mo109b();
                            }
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.f557c.n.m582a(0, z);
                        this.f558d = false;
                    }
                });
                return;
            }
            this.n.m582a(z ? 8 : 4, z);
            if (c0108a != null) {
                c0108a.mo109b();
            }
        }
    }

    /* renamed from: b */
    void mo154b(final C0108a c0108a, final boolean z) {
        if (!m778j()) {
            this.n.animate().cancel();
            if (m793l()) {
                this.c = 2;
                if (this.n.getVisibility() != 0) {
                    this.n.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.n.setScaleY(BitmapDescriptorFactory.HUE_RED);
                    this.n.setScaleX(BitmapDescriptorFactory.HUE_RED);
                }
                this.n.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(C0126a.f429d).setListener(new AnimatorListenerAdapter(this) {
                    /* renamed from: c */
                    final /* synthetic */ C0164i f561c;

                    public void onAnimationEnd(Animator animator) {
                        this.f561c.c = 0;
                        if (c0108a != null) {
                            c0108a.mo108a();
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.f561c.n.m582a(0, z);
                    }
                });
                return;
            }
            this.n.m582a(0, z);
            this.n.setAlpha(1.0f);
            this.n.setScaleY(1.0f);
            this.n.setScaleX(1.0f);
            if (c0108a != null) {
                c0108a.mo108a();
            }
        }
    }

    /* renamed from: d */
    boolean mo156d() {
        return true;
    }

    /* renamed from: e */
    void mo157e() {
        float rotation = this.n.getRotation();
        if (this.f562q != rotation) {
            this.f562q = rotation;
            m794m();
        }
    }
}
