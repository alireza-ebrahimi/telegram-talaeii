package org.telegram.ui;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.aa;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SelectLanguageActivity;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Intro;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController.LocaleInfo;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_langPackString;
import org.telegram.tgnet.TLRPC$TL_langpack_getStrings;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.LangPackString;
import org.telegram.ui.Components.LayoutHelper;

public class IntroActivity extends Activity implements C2497d, NotificationCenterDelegate {
    private BottomPagesView bottomPages;
    private long currentDate;
    private int currentViewPagerPage;
    private boolean destroyed;
    private boolean dragging;
    private EGLThread eglThread;
    private boolean justCreated = false;
    private boolean justEndDragging;
    private int lastPage = 0;
    private LocaleInfo localeInfo;
    private String[] messages;
    private int startDragX;
    private boolean startPressed = false;
    private TextView textView;
    private String[] titles;
    private ViewPager viewPager;

    /* renamed from: org.telegram.ui.IntroActivity$1 */
    class C47981 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.IntroActivity$1$1 */
        class C47971 implements Runnable {
            C47971() {
            }

            public void run() {
                IntroActivity.this.eglThread.drawRunnable.run();
            }
        }

        C47981() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            if (IntroActivity.this.eglThread == null && surfaceTexture != null) {
                IntroActivity.this.eglThread = new EGLThread(surfaceTexture);
                IntroActivity.this.eglThread.setSurfaceTextureSize(i, i2);
                IntroActivity.this.eglThread.postRunnable(new C47971());
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (IntroActivity.this.eglThread != null) {
                IntroActivity.this.eglThread.shutdown();
                IntroActivity.this.eglThread = null;
            }
            return true;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            if (IntroActivity.this.eglThread != null) {
                IntroActivity.this.eglThread.setSurfaceTextureSize(i, i2);
            }
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$2 */
    class C47992 implements C0188f {
        C47992() {
        }

        public void onPageScrollStateChanged(int i) {
            if (i == 1) {
                IntroActivity.this.dragging = true;
                IntroActivity.this.startDragX = IntroActivity.this.viewPager.getCurrentItem() * IntroActivity.this.viewPager.getMeasuredWidth();
            } else if (i == 0 || i == 2) {
                if (IntroActivity.this.dragging) {
                    IntroActivity.this.justEndDragging = true;
                    IntroActivity.this.dragging = false;
                }
                if (IntroActivity.this.lastPage != IntroActivity.this.viewPager.getCurrentItem()) {
                    IntroActivity.this.lastPage = IntroActivity.this.viewPager.getCurrentItem();
                }
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            IntroActivity.this.bottomPages.setPageOffset(i, f);
            float measuredWidth = (float) IntroActivity.this.viewPager.getMeasuredWidth();
            if (measuredWidth != BitmapDescriptorFactory.HUE_RED) {
                Intro.setScrollOffset((((((float) i) * measuredWidth) + ((float) i2)) - (((float) IntroActivity.this.currentViewPagerPage) * measuredWidth)) / measuredWidth);
            }
        }

        public void onPageSelected(int i) {
            IntroActivity.this.currentViewPagerPage = i;
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$3 */
    class C48003 implements OnClickListener {
        C48003() {
        }

        public void onClick(View view) {
            if (!IntroActivity.this.startPressed) {
                IntroActivity.this.startPressed = true;
                IntroActivity.this.startActivity(new Intent(IntroActivity.this.getApplicationContext(), SelectLanguageActivity.class));
                IntroActivity.this.finish();
            }
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$4 */
    class C48014 implements OnLongClickListener {
        C48014() {
        }

        public boolean onLongClick(View view) {
            ConnectionsManager.getInstance().switchBackend();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$5 */
    class C48025 implements OnClickListener {
        C48025() {
        }

        public void onClick(View view) {
            if (!IntroActivity.this.startPressed && IntroActivity.this.localeInfo != null) {
                LocaleController.getInstance().applyLanguage(IntroActivity.this.localeInfo, true, false);
                IntroActivity.this.startPressed = true;
                Intent intent = new Intent(IntroActivity.this, LaunchActivity.class);
                intent.putExtra("fromIntro", true);
                IntroActivity.this.startActivity(intent);
                IntroActivity.this.destroyed = true;
                IntroActivity.this.finish();
            }
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$6 */
    class C48046 implements RequestDelegate {
        C48046() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                if (!tLRPC$Vector.objects.isEmpty()) {
                    final LangPackString langPackString = (LangPackString) tLRPC$Vector.objects.get(0);
                    if (langPackString instanceof TLRPC$TL_langPackString) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (!IntroActivity.this.destroyed) {
                                    IntroActivity.this.textView.setText(langPackString.value);
                                    ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putString("language_showed2", LocaleController.getSystemLocaleStringIso639().toLowerCase()).commit();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private class BottomPagesView extends View {
        private float animatedProgress;
        private int currentPage;
        private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        private Paint paint = new Paint(1);
        private float progress;
        private RectF rect = new RectF();
        private int scrollPosition;

        public BottomPagesView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            int i;
            float dp = (float) AndroidUtilities.dp(5.0f);
            this.paint.setColor(-4473925);
            this.currentPage = IntroActivity.this.viewPager.getCurrentItem();
            for (i = 0; i < 6; i++) {
                if (i != this.currentPage) {
                    int dp2 = AndroidUtilities.dp(11.0f) * i;
                    this.rect.set((float) dp2, BitmapDescriptorFactory.HUE_RED, (float) (dp2 + AndroidUtilities.dp(5.0f)), (float) AndroidUtilities.dp(5.0f));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.5f), (float) AndroidUtilities.dp(2.5f), this.paint);
                }
            }
            this.paint.setColor(-13851168);
            i = this.currentPage * AndroidUtilities.dp(11.0f);
            if (this.progress == BitmapDescriptorFactory.HUE_RED) {
                this.rect.set((float) i, BitmapDescriptorFactory.HUE_RED, (float) (i + AndroidUtilities.dp(5.0f)), (float) AndroidUtilities.dp(5.0f));
            } else if (this.scrollPosition >= this.currentPage) {
                this.rect.set((float) i, BitmapDescriptorFactory.HUE_RED, ((float) (i + AndroidUtilities.dp(5.0f))) + (((float) AndroidUtilities.dp(11.0f)) * this.progress), (float) AndroidUtilities.dp(5.0f));
            } else {
                this.rect.set(((float) i) - (((float) AndroidUtilities.dp(11.0f)) * (1.0f - this.progress)), BitmapDescriptorFactory.HUE_RED, (float) (i + AndroidUtilities.dp(5.0f)), (float) AndroidUtilities.dp(5.0f));
            }
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.5f), (float) AndroidUtilities.dp(2.5f), this.paint);
        }

        public void setCurrentPage(int i) {
            this.currentPage = i;
            invalidate();
        }

        public void setPageOffset(int i, float f) {
            this.progress = f;
            this.scrollPosition = i;
            invalidate();
        }
    }

    public class EGLThread extends DispatchQueue {
        private final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private final int EGL_OPENGL_ES2_BIT = 4;
        private Runnable drawRunnable = new C48061();
        private EGL10 egl10;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLDisplay eglDisplay;
        private EGLSurface eglSurface;
        private GL gl;
        private boolean initied;
        private long lastRenderCallTime;
        private int surfaceHeight;
        private SurfaceTexture surfaceTexture;
        private int surfaceWidth;
        private int[] textures = new int[23];

        /* renamed from: org.telegram.ui.IntroActivity$EGLThread$1 */
        class C48061 implements Runnable {

            /* renamed from: org.telegram.ui.IntroActivity$EGLThread$1$1 */
            class C48051 implements Runnable {
                C48051() {
                }

                public void run() {
                    EGLThread.this.drawRunnable.run();
                }
            }

            C48061() {
            }

            public void run() {
                if (!EGLThread.this.initied) {
                    return;
                }
                if ((EGLThread.this.eglContext.equals(EGLThread.this.egl10.eglGetCurrentContext()) && EGLThread.this.eglSurface.equals(EGLThread.this.egl10.eglGetCurrentSurface(12377))) || EGLThread.this.egl10.eglMakeCurrent(EGLThread.this.eglDisplay, EGLThread.this.eglSurface, EGLThread.this.eglSurface, EGLThread.this.eglContext)) {
                    float currentTimeMillis = ((float) (System.currentTimeMillis() - IntroActivity.this.currentDate)) / 1000.0f;
                    Intro.setPage(IntroActivity.this.currentViewPagerPage);
                    Intro.setDate(currentTimeMillis);
                    Intro.onDrawFrame();
                    EGLThread.this.egl10.eglSwapBuffers(EGLThread.this.eglDisplay, EGLThread.this.eglSurface);
                    EGLThread.this.postRunnable(new C48051(), 16);
                    return;
                }
                FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGLThread.this.egl10.eglGetError()));
            }
        }

        /* renamed from: org.telegram.ui.IntroActivity$EGLThread$2 */
        class C48072 implements Runnable {
            C48072() {
            }

            public void run() {
                EGLThread.this.finish();
                Looper myLooper = Looper.myLooper();
                if (myLooper != null) {
                    myLooper.quit();
                }
            }
        }

        public EGLThread(SurfaceTexture surfaceTexture) {
            super("EGLThread");
            this.surfaceTexture = surfaceTexture;
        }

        private boolean initGL() {
            this.egl10 = (EGL10) EGLContext.getEGL();
            this.eglDisplay = this.egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.eglDisplay == EGL10.EGL_NO_DISPLAY) {
                FileLog.e("eglGetDisplay failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                finish();
                return false;
            }
            if (this.egl10.eglInitialize(this.eglDisplay, new int[2])) {
                int[] iArr = new int[1];
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 24, 12326, 0, 12338, 1, 12337, 2, 12344}, eGLConfigArr, 1, iArr)) {
                    FileLog.e("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    finish();
                    return false;
                } else if (iArr[0] > 0) {
                    this.eglConfig = eGLConfigArr[0];
                    this.eglContext = this.egl10.eglCreateContext(this.eglDisplay, this.eglConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                    if (this.eglContext == null) {
                        FileLog.e("eglCreateContext failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        finish();
                        return false;
                    } else if (this.surfaceTexture instanceof SurfaceTexture) {
                        this.eglSurface = this.egl10.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surfaceTexture, null);
                        if (this.eglSurface == null || this.eglSurface == EGL10.EGL_NO_SURFACE) {
                            FileLog.e("createWindowSurface failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                            finish();
                            return false;
                        } else if (this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                            this.gl = this.eglContext.getGL();
                            GLES20.glGenTextures(23, this.textures, 0);
                            loadTexture(R.drawable.intro_fast_arrow_shadow, 0);
                            loadTexture(R.drawable.intro_fast_arrow, 1);
                            loadTexture(R.drawable.intro_fast_body, 2);
                            loadTexture(R.drawable.intro_fast_spiral, 3);
                            loadTexture(R.drawable.intro_ic_bubble_dot, 4);
                            loadTexture(R.drawable.intro_ic_bubble, 5);
                            loadTexture(R.drawable.intro_ic_cam_lens, 6);
                            loadTexture(R.drawable.intro_ic_cam, 7);
                            loadTexture(R.drawable.intro_ic_pencil, 8);
                            loadTexture(R.drawable.intro_ic_pin, 9);
                            loadTexture(R.drawable.intro_ic_smile_eye, 10);
                            loadTexture(R.drawable.intro_ic_smile, 11);
                            loadTexture(R.drawable.intro_ic_videocam, 12);
                            loadTexture(R.drawable.intro_knot_down, 13);
                            loadTexture(R.drawable.intro_knot_up, 14);
                            loadTexture(R.drawable.intro_powerful_infinity_white, 15);
                            loadTexture(R.drawable.intro_powerful_infinity, 16);
                            loadTexture(R.drawable.intro_powerful_mask, 17);
                            loadTexture(R.drawable.intro_powerful_star, 18);
                            loadTexture(R.drawable.intro_private_door, 19);
                            loadTexture(R.drawable.intro_private_screw, 20);
                            loadTexture(R.drawable.intro_tg_plane, 21);
                            loadTexture(R.drawable.intro_tg_sphere, 22);
                            Intro.setTelegramTextures(this.textures[22], this.textures[21]);
                            Intro.setPowerfulTextures(this.textures[17], this.textures[18], this.textures[16], this.textures[15]);
                            Intro.setPrivateTextures(this.textures[19], this.textures[20]);
                            Intro.setFreeTextures(this.textures[14], this.textures[13]);
                            Intro.setFastTextures(this.textures[2], this.textures[3], this.textures[1], this.textures[0]);
                            Intro.setIcTextures(this.textures[4], this.textures[5], this.textures[6], this.textures[7], this.textures[8], this.textures[9], this.textures[10], this.textures[11], this.textures[12]);
                            Intro.onSurfaceCreated();
                            IntroActivity.this.currentDate = System.currentTimeMillis() - 1000;
                            return true;
                        } else {
                            FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                            finish();
                            return false;
                        }
                    } else {
                        finish();
                        return false;
                    }
                } else {
                    FileLog.e("eglConfig not initialized");
                    finish();
                    return false;
                }
            }
            FileLog.e("eglInitialize failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
            finish();
            return false;
        }

        private void loadTexture(int i, int i2) {
            Drawable drawable = IntroActivity.this.getResources().getDrawable(i);
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                GLES20.glBindTexture(3553, this.textures[i2]);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GLUtils.texImage2D(3553, 0, bitmap, 0);
            }
        }

        public void finish() {
            if (this.eglSurface != null) {
                this.egl10.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.egl10.eglDestroySurface(this.eglDisplay, this.eglSurface);
                this.eglSurface = null;
            }
            if (this.eglContext != null) {
                this.egl10.eglDestroyContext(this.eglDisplay, this.eglContext);
                this.eglContext = null;
            }
            if (this.eglDisplay != null) {
                this.egl10.eglTerminate(this.eglDisplay);
                this.eglDisplay = null;
            }
        }

        public void run() {
            this.initied = initGL();
            super.run();
        }

        public void setSurfaceTextureSize(int i, int i2) {
            this.surfaceWidth = i;
            this.surfaceHeight = i2;
            Intro.onSurfaceChanged(i, i2, Math.min(((float) this.surfaceWidth) / 150.0f, ((float) this.surfaceHeight) / 150.0f), 0);
        }

        public void shutdown() {
            postRunnable(new C48072());
        }
    }

    private class IntroAdapter extends aa {
        private IntroAdapter() {
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            return IntroActivity.this.titles.length;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View frameLayout = new FrameLayout(viewGroup.getContext());
            View textView = new TextView(viewGroup.getContext());
            textView.setTextColor(-14606047);
            textView.setTextSize(1, 26.0f);
            textView.setGravity(17);
            textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
            frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 51, 18.0f, 244.0f, 18.0f, BitmapDescriptorFactory.HUE_RED));
            View textView2 = new TextView(viewGroup.getContext());
            textView2.setTextColor(-8355712);
            textView2.setTextSize(1, 15.0f);
            textView2.setGravity(17);
            textView2.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
            frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 51, 16.0f, 286.0f, 16.0f, BitmapDescriptorFactory.HUE_RED));
            viewGroup.addView(frameLayout, 0);
            textView.setText(IntroActivity.this.titles[i]);
            textView2.setText(AndroidUtilities.replaceTags(IntroActivity.this.messages[i]));
            return frameLayout;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            super.setPrimaryItem(viewGroup, i, obj);
            IntroActivity.this.bottomPages.setCurrentPage(i);
            IntroActivity.this.currentViewPagerPage = i;
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (dataSetObserver != null) {
                super.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private void checkContinueText() {
        LocaleInfo localeInfo = null;
        int i = 0;
        LocaleInfo currentLocaleInfo = LocaleController.getInstance().getCurrentLocaleInfo();
        String toLowerCase = LocaleController.getSystemLocaleStringIso639().toLowerCase();
        String str = toLowerCase.contains("-") ? toLowerCase.split("-")[0] : toLowerCase;
        String localeAlias = LocaleController.getLocaleAlias(str);
        LocaleInfo localeInfo2 = null;
        while (i < LocaleController.getInstance().languages.size()) {
            LocaleInfo localeInfo3 = (LocaleInfo) LocaleController.getInstance().languages.get(i);
            if (localeInfo3.shortName.equals("en")) {
                localeInfo2 = localeInfo3;
            }
            if (localeInfo3.shortName.replace("_", "-").equals(toLowerCase) || localeInfo3.shortName.equals(str) || (localeAlias != null && localeInfo3.shortName.equals(localeAlias))) {
                localeInfo = localeInfo3;
            }
            if (localeInfo2 != null && r4 != null) {
                break;
            }
            i++;
        }
        if (localeInfo2 != null && localeInfo != null && localeInfo2 != localeInfo) {
            TLObject tLRPC$TL_langpack_getStrings = new TLRPC$TL_langpack_getStrings();
            if (localeInfo != currentLocaleInfo) {
                tLRPC$TL_langpack_getStrings.lang_code = localeInfo.shortName.replace("_", "-");
                this.localeInfo = localeInfo;
            } else {
                tLRPC$TL_langpack_getStrings.lang_code = localeInfo2.shortName.replace("_", "-");
                this.localeInfo = localeInfo2;
            }
            tLRPC$TL_langpack_getStrings.keys.add("ContinueOnThisLanguage");
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_langpack_getStrings, new C48046(), 8);
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.suggestedLangpack) {
            checkContinueText();
        }
    }

    protected void onCreate(Bundle bundle) {
        setTheme(R.style.Theme.TMessages);
        super.onCreate(bundle);
        requestWindowFeature(1);
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("intro_crashed_time", System.currentTimeMillis()).commit();
        C2818c.a(this, this).d();
        this.titles = new String[]{LocaleController.getString("Page1Title", R.string.Page1Title), LocaleController.getString("Page2Title", R.string.Page2Title), LocaleController.getString("Page3Title", R.string.Page3Title), LocaleController.getString("Page5Title", R.string.Page5Title), LocaleController.getString("Page4Title", R.string.Page4Title), LocaleController.getString("Page6Title", R.string.Page6Title)};
        this.messages = new String[]{LocaleController.getString("Page1Message", R.string.Page1Message), LocaleController.getString("Page2Message", R.string.Page2Message), LocaleController.getString("Page3Message", R.string.Page3Message), LocaleController.getString("Page5Message", R.string.Page5Message), LocaleController.getString("Page4Message", R.string.Page4Message), LocaleController.getString("Page6Message", R.string.Page6Message)};
        View scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        View frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(-1);
        scrollView.addView(frameLayout, LayoutHelper.createScroll(-1, -2, 51));
        View frameLayout2 = new FrameLayout(this);
        frameLayout.addView(frameLayout2, LayoutHelper.createFrame(-1, -2.0f, 51, BitmapDescriptorFactory.HUE_RED, 78.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View textureView = new TextureView(this);
        frameLayout2.addView(textureView, LayoutHelper.createFrame(Callback.DEFAULT_DRAG_ANIMATION_DURATION, BuildConfig.VERSION_CODE, 17));
        textureView.setSurfaceTextureListener(new C47981());
        this.viewPager = new ViewPager(this);
        this.viewPager.setAdapter(new IntroAdapter());
        this.viewPager.setPageMargin(0);
        this.viewPager.setOffscreenPageLimit(1);
        frameLayout.addView(this.viewPager, LayoutHelper.createFrame(-1, -1.0f));
        this.viewPager.addOnPageChangeListener(new C47992());
        textureView = new TextView(this);
        textureView.setText(LocaleController.getString("StartMessaging", R.string.StartMessaging).toUpperCase());
        textureView.setGravity(17);
        textureView.setTextColor(-1);
        textureView.setTextSize(1, 16.0f);
        textureView.setBackgroundResource(R.drawable.regbtn_states);
        textureView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(textureView, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(textureView, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            textureView.setStateListAnimator(stateListAnimator);
        }
        textureView.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(10.0f));
        frameLayout.addView(textureView, LayoutHelper.createFrame(-2, -2.0f, 81, 10.0f, BitmapDescriptorFactory.HUE_RED, 10.0f, 76.0f));
        textureView.setOnClickListener(new C48003());
        if (BuildVars.DEBUG_VERSION) {
            textureView.setOnLongClickListener(new C48014());
        }
        this.bottomPages = new BottomPagesView(this);
        frameLayout.addView(this.bottomPages, LayoutHelper.createFrame(66, 5.0f, 49, BitmapDescriptorFactory.HUE_RED, 350.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.textView = new TextView(this);
        this.textView.setTextColor(-15494190);
        this.textView.setGravity(17);
        this.textView.setTextSize(1, 16.0f);
        this.textView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        frameLayout.addView(this.textView, LayoutHelper.createFrame(-2, 30.0f, 81, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 20.0f));
        this.textView.setOnClickListener(new C48025());
        if (AndroidUtilities.isTablet()) {
            View frameLayout3 = new FrameLayout(this);
            setContentView(frameLayout3);
            View imageView = new ImageView(this);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.catstile);
            bitmapDrawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            imageView.setBackgroundDrawable(bitmapDrawable);
            frameLayout3.addView(imageView, LayoutHelper.createFrame(-1, -1.0f));
            textureView = new FrameLayout(this);
            textureView.setBackgroundResource(R.drawable.btnshadow);
            textureView.addView(scrollView, LayoutHelper.createFrame(-1, -1.0f));
            frameLayout3.addView(textureView, LayoutHelper.createFrame(498, 528, 17));
        } else {
            setRequestedOrientation(1);
            setContentView(scrollView);
        }
        LocaleController.getInstance().loadRemoteLanguages();
        checkContinueText();
        this.justCreated = true;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.suggestedLangpack);
        AndroidUtilities.handleProxyIntent(this, getIntent());
    }

    protected void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("intro_crashed_time", 0).commit();
    }

    protected void onPause() {
        super.onPause();
        AndroidUtilities.unregisterUpdates();
        ConnectionsManager.getInstance().setAppPaused(true, false);
    }

    public void onResult(Object obj, int i) {
    }

    protected void onResume() {
        super.onResume();
        if (this.justCreated) {
            if (LocaleController.isRTL) {
                this.viewPager.setCurrentItem(6);
                this.lastPage = 6;
            } else {
                this.viewPager.setCurrentItem(0);
                this.lastPage = 0;
            }
            this.justCreated = false;
        }
        AndroidUtilities.checkForCrashes(this);
        AndroidUtilities.checkForUpdates(this);
        ConnectionsManager.getInstance().setAppPaused(false, false);
    }
}
