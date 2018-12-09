package org.telegram.customization.easyvideoplayer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.content.C0235a;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.view.ah;
import android.support.v7.app.C0769e;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ActionBar.Theme;

public class EasyVideoPlayer extends FrameLayout implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceTextureListener, OnClickListener, OnSeekBarChangeListener {
    /* renamed from: A */
    private CharSequence f8957A;
    /* renamed from: B */
    private CharSequence f8958B;
    /* renamed from: C */
    private Drawable f8959C;
    /* renamed from: D */
    private Drawable f8960D;
    /* renamed from: E */
    private Drawable f8961E;
    /* renamed from: F */
    private CharSequence f8962F;
    /* renamed from: G */
    private CharSequence f8963G;
    /* renamed from: H */
    private boolean f8964H = true;
    /* renamed from: I */
    private boolean f8965I;
    /* renamed from: J */
    private int f8966J = -1;
    /* renamed from: K */
    private boolean f8967K;
    /* renamed from: L */
    private int f8968L = 0;
    /* renamed from: M */
    private boolean f8969M = false;
    /* renamed from: N */
    private boolean f8970N = false;
    /* renamed from: O */
    private final Runnable f8971O = new C27211(this);
    /* renamed from: a */
    public ImageButton f8972a;
    /* renamed from: b */
    public TextView f8973b;
    /* renamed from: c */
    private TextureView f8974c;
    /* renamed from: d */
    private Surface f8975d;
    /* renamed from: e */
    private View f8976e;
    /* renamed from: f */
    private View f8977f;
    /* renamed from: g */
    private View f8978g;
    /* renamed from: h */
    private SeekBar f8979h;
    /* renamed from: i */
    private TextView f8980i;
    /* renamed from: j */
    private TextView f8981j;
    /* renamed from: k */
    private ImageButton f8982k;
    /* renamed from: l */
    private TextView f8983l;
    /* renamed from: m */
    private TextView f8984m;
    /* renamed from: n */
    private TextView f8985n;
    /* renamed from: o */
    private MediaPlayer f8986o;
    /* renamed from: p */
    private boolean f8987p;
    /* renamed from: q */
    private boolean f8988q;
    /* renamed from: r */
    private boolean f8989r;
    /* renamed from: s */
    private int f8990s;
    /* renamed from: t */
    private int f8991t;
    /* renamed from: u */
    private Handler f8992u;
    /* renamed from: v */
    private Uri f8993v;
    /* renamed from: w */
    private C2541a f8994w;
    /* renamed from: x */
    private C2725b f8995x;
    /* renamed from: y */
    private int f8996y = 1;
    /* renamed from: z */
    private int f8997z = 3;

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$1 */
    class C27211 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ EasyVideoPlayer f8952a;

        C27211(EasyVideoPlayer easyVideoPlayer) {
            this.f8952a = easyVideoPlayer;
        }

        public void run() {
            if (this.f8952a.f8992u != null && this.f8952a.f8988q && this.f8952a.f8979h != null && this.f8952a.f8986o != null) {
                int currentPosition = this.f8952a.f8986o.getCurrentPosition();
                int duration = this.f8952a.f8986o.getDuration();
                if (currentPosition > duration) {
                    currentPosition = duration;
                }
                this.f8952a.f8980i.setText(C2726c.m12634a((long) currentPosition, false));
                this.f8952a.f8981j.setText(C2726c.m12634a((long) (duration - currentPosition), true));
                this.f8952a.f8979h.setProgress(currentPosition);
                this.f8952a.f8979h.setMax(duration);
                if (this.f8952a.f8995x != null) {
                    this.f8952a.f8995x.m12629a(currentPosition, duration);
                }
                if (this.f8952a.f8992u != null) {
                    this.f8952a.f8992u.postDelayed(this, 100);
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$2 */
    class C27222 extends AnimatorListenerAdapter {
        /* renamed from: a */
        final /* synthetic */ EasyVideoPlayer f8953a;

        C27222(EasyVideoPlayer easyVideoPlayer) {
            this.f8953a = easyVideoPlayer;
        }

        public void onAnimationEnd(Animator animator) {
            if (this.f8953a.f8969M) {
                this.f8953a.setFullscreen(false);
            }
        }
    }

    /* renamed from: org.telegram.customization.easyvideoplayer.EasyVideoPlayer$3 */
    class C27233 extends AnimatorListenerAdapter {
        /* renamed from: a */
        final /* synthetic */ EasyVideoPlayer f8954a;

        C27233(EasyVideoPlayer easyVideoPlayer) {
            this.f8954a = easyVideoPlayer;
        }

        public void onAnimationEnd(Animator animator) {
            this.f8954a.setFullscreen(true);
            if (this.f8954a.f8976e != null) {
                this.f8954a.f8976e.setVisibility(4);
            }
        }
    }

    public EasyVideoPlayer(Context context) {
        super(context);
        m12599a(context, null);
    }

    public EasyVideoPlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m12599a(context, attributeSet);
    }

    public EasyVideoPlayer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m12599a(context, attributeSet);
    }

    /* renamed from: a */
    private Drawable m12596a(Drawable drawable, int i) {
        Drawable g = C0375a.m1784g(drawable.mutate());
        C0375a.m1771a(g, i);
        return g;
    }

    /* renamed from: a */
    private void m12598a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        double d = ((double) i4) / ((double) i3);
        if (i2 > ((int) (((double) i) * d))) {
            i5 = (int) (d * ((double) i));
            i6 = i;
        } else {
            i6 = (int) (((double) i2) / d);
            i5 = i2;
        }
        int i7 = (i - i6) / 2;
        int i8 = (i2 - i5) / 2;
        Matrix matrix = new Matrix();
        this.f8974c.getTransform(matrix);
        matrix.setScale(((float) i6) / ((float) i), ((float) i5) / ((float) i2));
        matrix.postTranslate((float) i7, (float) i8);
        this.f8974c.setTransform(matrix);
    }

    /* renamed from: a */
    private void m12599a(Context context, AttributeSet attributeSet) {
        C0769e.m3658a(true);
        setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3336R.styleable.EasyVideoPlayer, 0, 0);
            try {
                String string = obtainStyledAttributes.getString(0);
                if (!(string == null || string.trim().isEmpty())) {
                    this.f8993v = Uri.parse(string);
                }
                this.f8996y = obtainStyledAttributes.getInteger(1, 1);
                this.f8997z = obtainStyledAttributes.getInteger(2, 3);
                this.f8962F = obtainStyledAttributes.getText(3);
                this.f8957A = obtainStyledAttributes.getText(4);
                this.f8958B = obtainStyledAttributes.getText(5);
                this.f8963G = obtainStyledAttributes.getText(6);
                int resourceId = obtainStyledAttributes.getResourceId(7, -1);
                int resourceId2 = obtainStyledAttributes.getResourceId(8, -1);
                int resourceId3 = obtainStyledAttributes.getResourceId(9, -1);
                if (resourceId != -1) {
                    this.f8959C = C0825b.m3939b(context, resourceId);
                }
                if (resourceId2 != -1) {
                    this.f8960D = C0825b.m3939b(context, resourceId2);
                }
                if (resourceId3 != -1) {
                    this.f8961E = C0825b.m3939b(context, resourceId3);
                }
                this.f8964H = obtainStyledAttributes.getBoolean(10, true);
                this.f8965I = obtainStyledAttributes.getBoolean(11, false);
                this.f8967K = obtainStyledAttributes.getBoolean(12, false);
                this.f8968L = obtainStyledAttributes.getColor(13, C2726c.m12631a(context, (int) R.attr.colorPrimary));
                this.f8969M = obtainStyledAttributes.getBoolean(14, false);
                this.f8970N = obtainStyledAttributes.getBoolean(15, false);
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            this.f8996y = 1;
            this.f8997z = 3;
            this.f8964H = true;
            this.f8965I = false;
            this.f8967K = false;
            this.f8968L = C2726c.m12631a(context, (int) R.attr.colorPrimary);
            this.f8969M = false;
            this.f8970N = false;
        }
        if (this.f8957A == null) {
            this.f8957A = context.getResources().getText(R.string.evp_retry);
        }
        if (this.f8958B == null) {
            this.f8958B = context.getResources().getText(R.string.evp_submit);
        }
        if (this.f8959C == null) {
            this.f8959C = C0825b.m3939b(context, R.drawable.evp_action_restart);
        }
        if (this.f8960D == null) {
            this.f8960D = C0825b.m3939b(context, R.drawable.evp_action_play);
        }
        if (this.f8961E == null) {
            this.f8961E = C0825b.m3939b(context, R.drawable.evp_action_pause);
        }
    }

    /* renamed from: a */
    private void m12600a(View view, int i) {
        if (VERSION.SDK_INT >= 21 && (view.getBackground() instanceof RippleDrawable)) {
            ((RippleDrawable) view.getBackground()).setColor(ColorStateList.valueOf(C2726c.m12630a(i, 0.3f)));
        }
    }

    /* renamed from: a */
    private static void m12601a(SeekBar seekBar, int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        if (VERSION.SDK_INT >= 21) {
            seekBar.setThumbTintList(valueOf);
            seekBar.setProgressTintList(valueOf);
            seekBar.setSecondaryProgressTintList(valueOf);
        } else if (VERSION.SDK_INT > 10) {
            Drawable g = C0375a.m1784g(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(g);
            C0375a.m1773a(g, valueOf);
            if (VERSION.SDK_INT >= 16) {
                g = C0375a.m1784g(seekBar.getThumb());
                C0375a.m1773a(g, valueOf);
                seekBar.setThumb(g);
            }
        } else {
            Mode mode = Mode.SRC_IN;
            if (VERSION.SDK_INT <= 10) {
                mode = Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null) {
                seekBar.getIndeterminateDrawable().setColorFilter(i, mode);
            }
            if (seekBar.getProgressDrawable() != null) {
                seekBar.getProgressDrawable().setColorFilter(i, mode);
            }
        }
    }

    /* renamed from: a */
    private void m12602a(Exception exception) {
        if (this.f8994w != null) {
            this.f8994w.mo3434a(this, exception);
            return;
        }
        throw new RuntimeException(exception);
    }

    /* renamed from: a */
    private static void m12603a(String str, Object... objArr) {
        if (objArr != null) {
            try {
                str = String.format(str, objArr);
            } catch (Exception e) {
                return;
            }
        }
        Log.d("EasyVideoPlayer", str);
    }

    /* renamed from: j */
    private void m12614j() {
        setControlsEnabled(false);
        this.f8979h.setProgress(0);
        this.f8979h.setEnabled(false);
        this.f8986o.reset();
        if (this.f8994w != null) {
            this.f8994w.mo3437c(this);
        }
        try {
            m12615k();
        } catch (Exception e) {
            m12602a(e);
        }
    }

    /* renamed from: k */
    private void m12615k() {
        if (this.f8993v.getScheme() != null && (this.f8993v.getScheme().equals("http") || this.f8993v.getScheme().equals("https"))) {
            m12603a("Loading web URI: " + this.f8993v.toString(), new Object[0]);
            this.f8986o.setDataSource(this.f8993v.toString());
        } else if (this.f8993v.getScheme() != null && this.f8993v.getScheme().equals("file") && this.f8993v.getPath().contains("/android_assets/")) {
            m12603a("Loading assets URI: " + this.f8993v.toString(), new Object[0]);
            r6 = getContext().getAssets().openFd(this.f8993v.toString().replace("file:///android_assets/", TtmlNode.ANONYMOUS_REGION_ID));
            this.f8986o.setDataSource(r6.getFileDescriptor(), r6.getStartOffset(), r6.getLength());
            r6.close();
        } else if (this.f8993v.getScheme() == null || !this.f8993v.getScheme().equals("asset")) {
            m12603a("Loading local URI: " + this.f8993v.toString(), new Object[0]);
            this.f8986o.setDataSource(getContext(), this.f8993v);
        } else {
            m12603a("Loading assets URI: " + this.f8993v.toString(), new Object[0]);
            r6 = getContext().getAssets().openFd(this.f8993v.toString().replace("asset://", TtmlNode.ANONYMOUS_REGION_ID));
            this.f8986o.setDataSource(r6.getFileDescriptor(), r6.getStartOffset(), r6.getLength());
            r6.close();
        }
        this.f8986o.prepareAsync();
    }

    /* renamed from: l */
    private void m12616l() {
        if (this.f8987p && this.f8993v != null && this.f8986o != null && !this.f8988q) {
            if (this.f8994w != null) {
                this.f8994w.mo3437c(this);
            }
            try {
                this.f8986o.setSurface(this.f8975d);
                m12615k();
            } catch (Exception e) {
                m12602a(e);
            }
        }
    }

    /* renamed from: m */
    private void m12617m() {
        switch (this.f8996y) {
            case 0:
                this.f8973b.setVisibility(8);
                this.f8972a.setVisibility(8);
                break;
            case 1:
                this.f8973b.setVisibility(8);
                this.f8972a.setVisibility(0);
                break;
            case 2:
                this.f8973b.setVisibility(0);
                this.f8972a.setVisibility(8);
                break;
        }
        switch (this.f8997z) {
            case 3:
                this.f8983l.setVisibility(8);
                this.f8984m.setVisibility(8);
                return;
            case 4:
                this.f8983l.setVisibility(0);
                this.f8984m.setVisibility(8);
                return;
            case 5:
                this.f8983l.setVisibility(8);
                this.f8984m.setVisibility(0);
                return;
            default:
                return;
        }
    }

    /* renamed from: n */
    private void m12618n() {
        int i = C2726c.m12635a(this.f8968L) ? -1 : Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        this.f8976e.setBackgroundColor(C2726c.m12630a(this.f8968L, 0.8f));
        m12600a(this.f8972a, i);
        m12600a(this.f8982k, i);
        this.f8981j.setTextColor(i);
        this.f8980i.setTextColor(i);
        m12601a(this.f8979h, i);
        this.f8973b.setTextColor(i);
        m12600a(this.f8973b, i);
        this.f8983l.setTextColor(i);
        m12600a(this.f8983l, i);
        this.f8984m.setTextColor(i);
        this.f8985n.setTextColor(i);
        this.f8960D = m12596a(this.f8960D.mutate(), i);
        this.f8959C = m12596a(this.f8959C.mutate(), i);
        this.f8961E = m12596a(this.f8961E.mutate(), i);
    }

    private void setControlsEnabled(boolean z) {
        float f = 1.0f;
        if (this.f8979h != null) {
            this.f8979h.setEnabled(z);
            this.f8982k.setEnabled(z);
            this.f8983l.setEnabled(z);
            this.f8972a.setEnabled(z);
            this.f8973b.setEnabled(z);
            this.f8982k.setAlpha(z ? 1.0f : 0.4f);
            this.f8983l.setAlpha(z ? 1.0f : 0.4f);
            ImageButton imageButton = this.f8972a;
            if (!z) {
                f = 0.4f;
            }
            imageButton.setAlpha(f);
            this.f8978g.setEnabled(z);
        }
    }

    @TargetApi(14)
    private void setFullscreen(boolean z) {
        boolean z2 = true;
        if (VERSION.SDK_INT >= 14 && this.f8969M) {
            int i = !z ? 0 : 1;
            View view = this.f8976e;
            if (z) {
                z2 = false;
            }
            ah.m2789a(view, z2);
            if (VERSION.SDK_INT >= 19) {
                i |= 1792;
                if (z) {
                    i |= 2054;
                }
            }
            this.f8978g.setSystemUiVisibility(i);
        }
    }

    /* renamed from: a */
    public void m12619a() {
        if (!this.f8967K && !m12622c() && this.f8979h != null) {
            this.f8976e.animate().cancel();
            this.f8976e.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.f8976e.setVisibility(0);
            this.f8976e.animate().alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener(new C27222(this)).start();
        }
    }

    /* renamed from: a */
    public void m12620a(int i) {
        if (this.f8986o != null) {
            this.f8986o.seekTo(i);
        }
    }

    /* renamed from: b */
    public void m12621b() {
        if (!this.f8967K && m12622c() && this.f8979h != null) {
            this.f8976e.animate().cancel();
            this.f8976e.setAlpha(1.0f);
            this.f8976e.setVisibility(0);
            this.f8976e.animate().alpha(BitmapDescriptorFactory.HUE_RED).setInterpolator(new DecelerateInterpolator()).setListener(new C27233(this)).start();
        }
    }

    /* renamed from: c */
    public boolean m12622c() {
        return (this.f8967K || this.f8976e == null || this.f8976e.getAlpha() <= 0.5f) ? false : true;
    }

    /* renamed from: d */
    public void m12623d() {
        if (!this.f8967K) {
            if (m12622c()) {
                m12621b();
            } else {
                m12619a();
            }
        }
    }

    /* renamed from: e */
    public boolean m12624e() {
        return this.f8986o != null && this.f8986o.isPlaying();
    }

    /* renamed from: f */
    public void m12625f() {
        if (this.f8986o != null) {
            this.f8986o.start();
            if (this.f8994w != null) {
                this.f8994w.mo3432a(this);
            }
            if (this.f8992u == null) {
                this.f8992u = new Handler();
            }
            this.f8992u.post(this.f8971O);
            this.f8982k.setImageDrawable(this.f8961E);
        }
    }

    /* renamed from: g */
    public void m12626g() {
        if (this.f8986o != null && m12624e()) {
            this.f8986o.pause();
            if (this.f8994w != null) {
                this.f8994w.mo3435b(this);
            }
            if (this.f8992u != null) {
                this.f8992u.removeCallbacks(this.f8971O);
                this.f8982k.setImageDrawable(this.f8960D);
            }
        }
    }

    public int getCurrentPosition() {
        return this.f8986o == null ? -1 : this.f8986o.getCurrentPosition();
    }

    public int getDuration() {
        return this.f8986o == null ? -1 : this.f8986o.getDuration();
    }

    /* renamed from: h */
    public void m12627h() {
        if (this.f8986o != null) {
            try {
                this.f8986o.stop();
            } catch (Throwable th) {
            }
            if (this.f8992u != null) {
                this.f8992u.removeCallbacks(this.f8971O);
                this.f8982k.setImageDrawable(this.f8961E);
            }
        }
    }

    /* renamed from: i */
    public void m12628i() {
        this.f8988q = false;
        if (this.f8986o != null) {
            try {
                this.f8986o.release();
            } catch (Throwable th) {
            }
            this.f8986o = null;
        }
        if (this.f8992u != null) {
            this.f8992u.removeCallbacks(this.f8971O);
            this.f8992u = null;
        }
        m12603a("Released player and Handler", new Object[0]);
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        m12603a("Buffering: %d%%", Integer.valueOf(i));
        if (this.f8994w != null) {
            this.f8994w.mo3431a(i);
        }
        if (this.f8979h == null) {
            return;
        }
        if (i == 100) {
            this.f8979h.setSecondaryProgress(0);
        } else {
            this.f8979h.setSecondaryProgress(this.f8979h.getMax() * (i / 100));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnPlayPause) {
            if (this.f8986o.isPlaying()) {
                m12626g();
                return;
            }
            if (this.f8964H && !this.f8967K) {
                m12621b();
            }
            m12625f();
        } else if (view.getId() == R.id.btnRestart) {
            m12620a(0);
            if (!m12624e()) {
                m12625f();
            }
        } else if (view.getId() == R.id.btnRetry) {
            if (this.f8994w != null) {
                this.f8994w.mo3433a(this, this.f8993v);
            }
        } else if (view.getId() == R.id.btnSubmit && this.f8994w != null) {
            this.f8994w.mo3436b(this, this.f8993v);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        m12603a("onCompletion()", new Object[0]);
        if (this.f8970N) {
            this.f8982k.setImageDrawable(this.f8960D);
            if (this.f8992u != null) {
                this.f8992u.removeCallbacks(this.f8971O);
            }
            this.f8979h.setProgress(this.f8979h.getMax());
            m12619a();
        }
        if (this.f8994w != null) {
            this.f8994w.mo3439e(this);
            if (this.f8970N) {
                this.f8994w.mo3432a(this);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        m12603a("Detached from window", new Object[0]);
        m12628i();
        this.f8979h = null;
        this.f8980i = null;
        this.f8981j = null;
        this.f8982k = null;
        this.f8972a = null;
        this.f8983l = null;
        this.f8976e = null;
        this.f8978g = null;
        this.f8977f = null;
        if (this.f8992u != null) {
            this.f8992u.removeCallbacks(this.f8971O);
            this.f8992u = null;
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        if (i != -38) {
            String str = "Preparation/playback error (" + i + "): ";
            switch (i) {
                case -1010:
                    str = str + "Unsupported";
                    break;
                case -1007:
                    str = str + "Malformed";
                    break;
                case -1004:
                    str = str + "I/O error";
                    break;
                case -110:
                    str = str + "Timed out";
                    break;
                case 100:
                    str = str + "Server died";
                    break;
                case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
                    str = str + "Not valid for progressive playback";
                    break;
                default:
                    str = str + "Unknown error";
                    break;
            }
            m12602a(new Exception(str));
        }
        return false;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            setKeepScreenOn(true);
            this.f8992u = new Handler();
            this.f8986o = new MediaPlayer();
            this.f8986o.setOnPreparedListener(this);
            this.f8986o.setOnBufferingUpdateListener(this);
            this.f8986o.setOnCompletionListener(this);
            this.f8986o.setOnVideoSizeChangedListener(this);
            this.f8986o.setOnErrorListener(this);
            this.f8986o.setAudioStreamType(3);
            this.f8986o.setLooping(this.f8970N);
            LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            this.f8974c = new TextureView(getContext());
            addView(this.f8974c, layoutParams);
            this.f8974c.setSurfaceTextureListener(this);
            LayoutInflater from = LayoutInflater.from(getContext());
            this.f8977f = from.inflate(R.layout.evp_include_progress, this, false);
            addView(this.f8977f);
            this.f8978g = new FrameLayout(getContext());
            ((FrameLayout) this.f8978g).setForeground(C2726c.m12636b(getContext(), R.attr.selectableItemBackground));
            addView(this.f8978g, new LayoutParams(-1, -1));
            this.f8976e = from.inflate(R.layout.evp_include_controls, this, false);
            layoutParams = new FrameLayout.LayoutParams(-1, -2);
            layoutParams.gravity = 80;
            addView(this.f8976e, layoutParams);
            if (this.f8967K) {
                this.f8978g.setOnClickListener(null);
                this.f8976e.setVisibility(8);
            } else {
                this.f8978g.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ EasyVideoPlayer f8956b;

                    public void onClick(View view) {
                        this.f8956b.m12623d();
                        try {
                            this.f8956b.f8994w.mo3440f(this);
                        } catch (Exception e) {
                        }
                    }
                });
            }
            this.f8979h = (SeekBar) this.f8976e.findViewById(R.id.seeker);
            this.f8979h.setOnSeekBarChangeListener(this);
            this.f8980i = (TextView) this.f8976e.findViewById(R.id.position);
            this.f8980i.setText(C2726c.m12634a(0, false));
            this.f8981j = (TextView) this.f8976e.findViewById(R.id.duration);
            this.f8981j.setText(C2726c.m12634a(0, true));
            this.f8972a = (ImageButton) this.f8976e.findViewById(R.id.btnRestart);
            this.f8972a.setOnClickListener(this);
            this.f8972a.setImageDrawable(this.f8959C);
            this.f8973b = (TextView) this.f8976e.findViewById(R.id.btnRetry);
            this.f8973b.setOnClickListener(this);
            this.f8973b.setText(this.f8957A);
            this.f8982k = (ImageButton) this.f8976e.findViewById(R.id.btnPlayPause);
            this.f8982k.setOnClickListener(this);
            this.f8982k.setImageDrawable(this.f8960D);
            this.f8983l = (TextView) this.f8976e.findViewById(R.id.btnSubmit);
            this.f8983l.setOnClickListener(this);
            this.f8983l.setText(this.f8958B);
            this.f8984m = (TextView) this.f8976e.findViewById(R.id.labelCustom);
            this.f8984m.setText(this.f8962F);
            this.f8985n = (TextView) this.f8976e.findViewById(R.id.labelBottom);
            setBottomLabelText(this.f8963G);
            m12618n();
            setControlsEnabled(false);
            m12617m();
            m12616l();
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        m12603a("onPrepared()", new Object[0]);
        this.f8977f.setVisibility(4);
        this.f8988q = true;
        if (this.f8994w != null) {
            this.f8994w.mo3438d(this);
        }
        this.f8980i.setText(C2726c.m12634a(0, false));
        this.f8981j.setText(C2726c.m12634a((long) mediaPlayer.getDuration(), false));
        this.f8979h.setProgress(0);
        this.f8979h.setMax(mediaPlayer.getDuration());
        setControlsEnabled(true);
        if (this.f8965I) {
            if (!this.f8967K && this.f8964H) {
                m12621b();
            }
            m12625f();
            if (this.f8966J > 0) {
                m12620a(this.f8966J);
                this.f8966J = -1;
                return;
            }
            return;
        }
        this.f8986o.start();
        this.f8986o.pause();
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            m12620a(i);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.f8989r = m12624e();
        if (this.f8989r) {
            this.f8986o.pause();
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.f8989r) {
            this.f8986o.start();
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        m12603a("Surface texture available: %dx%d", Integer.valueOf(i), Integer.valueOf(i2));
        this.f8990s = i;
        this.f8991t = i2;
        this.f8987p = true;
        this.f8975d = new Surface(surfaceTexture);
        if (this.f8988q) {
            this.f8986o.setSurface(this.f8975d);
        } else {
            m12616l();
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        m12603a("Surface texture destroyed", new Object[0]);
        this.f8987p = false;
        this.f8975d = null;
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        m12603a("Surface texture changed: %dx%d", Integer.valueOf(i), Integer.valueOf(i2));
        m12598a(i, i2, this.f8986o.getVideoWidth(), this.f8986o.getVideoHeight());
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        m12603a("Video size changed: %dx%d", Integer.valueOf(i), Integer.valueOf(i2));
        m12598a(this.f8990s, this.f8991t, i, i2);
    }

    public void setAutoFullscreen(boolean z) {
        this.f8969M = z;
    }

    public void setAutoPlay(boolean z) {
        this.f8965I = z;
    }

    public void setBottomLabelText(CharSequence charSequence) {
        this.f8963G = charSequence;
        this.f8985n.setText(charSequence);
        if (charSequence == null || charSequence.toString().trim().length() == 0) {
            this.f8985n.setVisibility(8);
        } else {
            this.f8985n.setVisibility(0);
        }
    }

    public void setBottomLabelTextRes(int i) {
        setBottomLabelText(getResources().getText(i));
    }

    public void setCallback(C2541a c2541a) {
    }

    public void setCustomLabelText(CharSequence charSequence) {
        this.f8962F = charSequence;
        this.f8984m.setText(charSequence);
        setRightAction(5);
    }

    public void setCustomLabelTextRes(int i) {
        setCustomLabelText(getResources().getText(i));
    }

    public void setHideControlsOnPlay(boolean z) {
        this.f8964H = z;
    }

    public void setInitialPosition(int i) {
        this.f8966J = i;
    }

    public void setLeftAction(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Invalid left action specified.");
        }
        this.f8996y = i;
        m12617m();
    }

    public void setLoop(boolean z) {
        this.f8970N = z;
        if (this.f8986o != null) {
            this.f8986o.setLooping(z);
        }
    }

    public void setPauseDrawable(Drawable drawable) {
        this.f8961E = drawable;
        if (m12624e()) {
            this.f8982k.setImageDrawable(drawable);
        }
    }

    public void setPauseDrawableRes(int i) {
        setPauseDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setPlayDrawable(Drawable drawable) {
        this.f8960D = drawable;
        if (!m12624e()) {
            this.f8982k.setImageDrawable(drawable);
        }
    }

    public void setPlayDrawableRes(int i) {
        setPlayDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setProgressCallback(C2725b c2725b) {
        this.f8995x = c2725b;
    }

    public void setRestartDrawable(Drawable drawable) {
        this.f8959C = drawable;
        this.f8972a.setImageDrawable(drawable);
    }

    public void setRestartDrawableRes(int i) {
        setRestartDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setRetryText(CharSequence charSequence) {
        this.f8957A = charSequence;
        this.f8973b.setText(charSequence);
    }

    public void setRetryTextRes(int i) {
        setRetryText(getResources().getText(i));
    }

    public void setRightAction(int i) {
        if (i < 3 || i > 5) {
            throw new IllegalArgumentException("Invalid right action specified.");
        }
        this.f8997z = i;
        m12617m();
    }

    public void setSource(Uri uri) {
        Object obj = this.f8993v != null ? 1 : null;
        if (obj != null) {
            m12627h();
        }
        this.f8993v = uri;
        if (this.f8986o == null) {
            return;
        }
        if (obj != null) {
            m12614j();
        } else {
            m12616l();
        }
    }

    public void setSubmitText(CharSequence charSequence) {
        this.f8958B = charSequence;
        this.f8983l.setText(charSequence);
    }

    public void setSubmitTextRes(int i) {
        setSubmitText(getResources().getText(i));
    }

    public void setThemeColor(int i) {
        this.f8968L = i;
        m12618n();
    }

    public void setThemeColorRes(int i) {
        setThemeColor(C0235a.m1075c(getContext(), i));
    }
}
