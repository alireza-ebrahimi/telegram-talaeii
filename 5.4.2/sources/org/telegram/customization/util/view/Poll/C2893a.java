package org.telegram.customization.util.view.Poll;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2868b;

/* renamed from: org.telegram.customization.util.view.Poll.a */
public class C2893a extends BaseAdapter implements OnItemClickListener, OnItemSelectedListener {
    /* renamed from: a */
    volatile List<C2894b> f9533a = new ArrayList();
    /* renamed from: b */
    volatile ArrayList<Boolean> f9534b = new ArrayList();
    /* renamed from: c */
    int f9535c;
    /* renamed from: d */
    int f9536d;
    /* renamed from: e */
    int f9537e;
    /* renamed from: f */
    int f9538f;
    /* renamed from: g */
    int f9539g = 0;
    /* renamed from: h */
    private Context f9540h;

    /* renamed from: org.telegram.customization.util.view.Poll.a$3 */
    class C28913 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ C2893a f9528a;

        C28913(C2893a c2893a) {
            this.f9528a = c2893a;
        }

        public void onAnimationEnd(Animation animation) {
            this.f9528a.notifyDataSetChanged();
            this.f9528a.f9534b = new ArrayList();
            for (int i = 0; i < this.f9528a.getCount(); i++) {
                this.f9528a.f9534b.add(Boolean.valueOf(false));
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.Poll.a$a */
    class C2892a extends Animation {
        /* renamed from: a */
        final /* synthetic */ C2893a f9529a;
        /* renamed from: b */
        private final float f9530b;
        /* renamed from: c */
        private final float f9531c;
        /* renamed from: d */
        private ProgressBar f9532d;

        public C2892a(C2893a c2893a, ProgressBar progressBar, float f, float f2) {
            this.f9529a = c2893a;
            this.f9530b = f;
            this.f9531c = f2 - f;
            this.f9532d = progressBar;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            this.f9532d.setProgress((int) (this.f9530b + (this.f9531c * f)));
        }

        public boolean willChangeBounds() {
            return false;
        }
    }

    public C2893a(Context context, List<C2894b> list, int i, int i2, int i3) {
        this.f9540h = context;
        this.f9533a = list;
        this.f9536d = i;
        this.f9537e = i2;
        this.f9538f = i3;
        this.f9535c = -1;
    }

    /* renamed from: a */
    public int m13409a() {
        return this.f9538f;
    }

    /* renamed from: a */
    public synchronized void m13410a(int i, View view) {
        try {
            C2894b c2894b = (C2894b) m13413d().get(i);
            if (this.f9535c < 0) {
                this.f9535c = i;
                ((C2894b) this.f9533a.get(i)).m13415a(1);
                Animation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.5f);
                scaleAnimation.setInterpolator(new LinearInterpolator());
                scaleAnimation.setRepeatMode(2);
                scaleAnimation.setRepeatCount(1);
                scaleAnimation.setDuration(200);
                scaleAnimation.setAnimationListener(new C28913(this));
                view.findViewById(R.id.rl).startAnimation(scaleAnimation);
            } else if (this.f9535c == i) {
                ((C2894b) this.f9533a.get(i)).m13415a(-1);
                this.f9535c = -1;
                notifyDataSetChanged();
            } else {
                ((C2894b) this.f9533a.get(this.f9535c)).m13415a(-1);
                ((C2894b) this.f9533a.get(i)).m13415a(1);
                this.f9535c = i;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
    }

    /* renamed from: b */
    public int m13411b() {
        return this.f9537e;
    }

    /* renamed from: c */
    public int m13412c() {
        return this.f9536d;
    }

    /* renamed from: d */
    public List<C2894b> m13413d() {
        return this.f9533a;
    }

    public synchronized int getCount() {
        return m13413d() != null ? m13413d().size() : 0;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View inflate = view == null ? View.inflate(this.f9540h, R.layout.poll_item, null) : view;
        try {
            final C2894b c2894b = (C2894b) m13413d().get(i);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.rl);
            final ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.progress);
            View findViewById = inflate.findViewById(R.id.isSelected);
            TextView textView = (TextView) inflate.findViewById(R.id.percentage);
            ((TextView) inflate.findViewById(R.id.title)).setText(c2894b.m13416b());
            textView.setText(String.format(this.f9540h.getResources().getString(R.string.percentageText), new Object[]{Integer.valueOf(c2894b.m13417c())}));
            C2868b.m13329a(imageView, c2894b.m13414a());
            try {
                LayerDrawable layerDrawable = (LayerDrawable) this.f9540h.getResources().getDrawable(R.drawable.poll_progress_drawable);
                layerDrawable.setDrawableByLayerId(16908288, new ColorDrawable(m13409a()));
                if (this.f9535c == i) {
                    layerDrawable.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(m13412c()), 3, 1));
                } else {
                    findViewById.setVisibility(8);
                    layerDrawable.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(m13411b()), 3, 1));
                }
                progressBar.setProgressDrawable(layerDrawable);
                if (this.f9535c < 0) {
                    progressBar.setProgress(100);
                    textView.setVisibility(8);
                } else {
                    textView.setVisibility(0);
                    try {
                        if (!((Boolean) this.f9534b.get(i)).booleanValue()) {
                            progressBar.post(new Runnable(this) {
                                /* renamed from: d */
                                final /* synthetic */ C2893a f9522d;

                                public void run() {
                                    this.f9522d.f9534b.set(i, Boolean.valueOf(true));
                                    Animation c2892a = new C2892a(this.f9522d, progressBar, BitmapDescriptorFactory.HUE_RED, (float) c2894b.m13417c());
                                    c2892a.setDuration(500);
                                    c2892a.setInterpolator(new LinearInterpolator());
                                    progressBar.startAnimation(c2892a);
                                }
                            });
                        } else if (progressBar.getProgress() != c2894b.m13417c()) {
                            final int progress = progressBar.getProgress();
                            final int i2 = i;
                            progressBar.post(new Runnable(this) {
                                /* renamed from: e */
                                final /* synthetic */ C2893a f9527e;

                                public void run() {
                                    this.f9527e.f9534b.set(i2, Boolean.valueOf(true));
                                    Animation c2892a = new C2892a(this.f9527e, progressBar, (float) progress, (float) c2894b.m13417c());
                                    c2892a.setDuration(500);
                                    c2892a.setInterpolator(new LinearInterpolator());
                                    progressBar.startAnimation(c2892a);
                                }
                            });
                        } else {
                            progressBar.setProgress(c2894b.m13417c());
                        }
                    } catch (Exception e) {
                        progressBar.setProgress(c2894b.m13417c());
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return inflate;
        } catch (Exception e22) {
            e22.printStackTrace();
            return inflate;
        }
    }

    public synchronized void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        onItemSelected(adapterView, view, i, j);
    }

    public synchronized void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        m13410a(i, view);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
