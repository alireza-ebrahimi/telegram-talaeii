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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.persianswitch.sdk.base.manager.LanguageManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SelectLanguageActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Intro;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController$LocaleInfo;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$LangPackString;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_langPackString;
import org.telegram.tgnet.TLRPC$TL_langpack_getStrings;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.Components.LayoutHelper;

public class IntroActivity extends Activity implements NotificationCenterDelegate, IResponseReceiver {
    private BottomPagesView bottomPages;
    private long currentDate;
    private int currentViewPagerPage;
    private boolean destroyed;
    private boolean dragging;
    private EGLThread eglThread;
    private boolean justCreated = false;
    private boolean justEndDragging;
    private int lastPage = 0;
    private LocaleController$LocaleInfo localeInfo;
    private String[] messages;
    private int startDragX;
    private boolean startPressed = false;
    private TextView textView;
    private String[] titles;
    private ViewPager viewPager;

    /* renamed from: org.telegram.ui.IntroActivity$1 */
    class C29601 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.IntroActivity$1$1 */
        class C29591 implements Runnable {
            C29591() {
            }

            public void run() {
                IntroActivity.this.eglThread.drawRunnable.run();
            }
        }

        C29601() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if (IntroActivity.this.eglThread == null && surface != null) {
                IntroActivity.this.eglThread = new EGLThread(surface);
                IntroActivity.this.eglThread.setSurfaceTextureSize(width, height);
                IntroActivity.this.eglThread.postRunnable(new C29591());
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            if (IntroActivity.this.eglThread != null) {
                IntroActivity.this.eglThread.setSurfaceTextureSize(width, height);
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (IntroActivity.this.eglThread != null) {
                IntroActivity.this.eglThread.shutdown();
                IntroActivity.this.eglThread = null;
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$2 */
    class C29612 implements OnPageChangeListener {
        C29612() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            IntroActivity.this.bottomPages.setPageOffset(position, positionOffset);
            float width = (float) IntroActivity.this.viewPager.getMeasuredWidth();
            if (width != 0.0f) {
                Intro.setScrollOffset((((((float) position) * width) + ((float) positionOffsetPixels)) - (((float) IntroActivity.this.currentViewPagerPage) * width)) / width);
            }
        }

        public void onPageSelected(int i) {
            IntroActivity.this.currentViewPagerPage = i;
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
    }

    /* renamed from: org.telegram.ui.IntroActivity$3 */
    class C29623 implements OnClickListener {
        C29623() {
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
    class C29634 implements OnLongClickListener {
        C29634() {
        }

        public boolean onLongClick(View v) {
            ConnectionsManager.getInstance().switchBackend();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$5 */
    class C29645 implements OnClickListener {
        C29645() {
        }

        public void onClick(View v) {
            if (!IntroActivity.this.startPressed && IntroActivity.this.localeInfo != null) {
                LocaleController.getInstance().applyLanguage(IntroActivity.this.localeInfo, true, false);
                IntroActivity.this.startPressed = true;
                Intent intent2 = new Intent(IntroActivity.this, LaunchActivity.class);
                intent2.putExtra("fromIntro", true);
                IntroActivity.this.startActivity(intent2);
                IntroActivity.this.destroyed = true;
                IntroActivity.this.finish();
            }
        }
    }

    /* renamed from: org.telegram.ui.IntroActivity$6 */
    class C29666 implements RequestDelegate {
        C29666() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
            if (response != null) {
                TLRPC$Vector vector = (TLRPC$Vector) response;
                if (!vector.objects.isEmpty()) {
                    final TLRPC$LangPackString string = (TLRPC$LangPackString) vector.objects.get(0);
                    if (string instanceof TLRPC$TL_langPackString) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (!IntroActivity.this.destroyed) {
                                    IntroActivity.this.textView.setText(string.value);
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

        public void setPageOffset(int position, float offset) {
            this.progress = offset;
            this.scrollPosition = position;
            invalidate();
        }

        public void setCurrentPage(int page) {
            this.currentPage = page;
            invalidate();
        }

        protected void onDraw(Canvas canvas) {
            int x;
            float d = (float) AndroidUtilities.dp(5.0f);
            this.paint.setColor(-4473925);
            this.currentPage = IntroActivity.this.viewPager.getCurrentItem();
            for (int a = 0; a < 6; a++) {
                if (a != this.currentPage) {
                    x = a * AndroidUtilities.dp(11.0f);
                    this.rect.set((float) x, 0.0f, (float) (AndroidUtilities.dp(5.0f) + x), (float) AndroidUtilities.dp(5.0f));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.5f), (float) AndroidUtilities.dp(2.5f), this.paint);
                }
            }
            this.paint.setColor(-13851168);
            x = this.currentPage * AndroidUtilities.dp(11.0f);
            if (this.progress == 0.0f) {
                this.rect.set((float) x, 0.0f, (float) (AndroidUtilities.dp(5.0f) + x), (float) AndroidUtilities.dp(5.0f));
            } else if (this.scrollPosition >= this.currentPage) {
                this.rect.set((float) x, 0.0f, ((float) (AndroidUtilities.dp(5.0f) + x)) + (((float) AndroidUtilities.dp(11.0f)) * this.progress), (float) AndroidUtilities.dp(5.0f));
            } else {
                this.rect.set(((float) x) - (((float) AndroidUtilities.dp(11.0f)) * (1.0f - this.progress)), 0.0f, (float) (AndroidUtilities.dp(5.0f) + x), (float) AndroidUtilities.dp(5.0f));
            }
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.5f), (float) AndroidUtilities.dp(2.5f), this.paint);
        }
    }

    public class EGLThread extends DispatchQueue {
        private final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private final int EGL_OPENGL_ES2_BIT = 4;
        private Runnable drawRunnable = new C29681();
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
        class C29681 implements Runnable {

            /* renamed from: org.telegram.ui.IntroActivity$EGLThread$1$1 */
            class C29671 implements Runnable {
                C29671() {
                }

                public void run() {
                    EGLThread.this.drawRunnable.run();
                }
            }

            C29681() {
            }

            public void run() {
                if (!EGLThread.this.initied) {
                    return;
                }
                if ((EGLThread.this.eglContext.equals(EGLThread.this.egl10.eglGetCurrentContext()) && EGLThread.this.eglSurface.equals(EGLThread.this.egl10.eglGetCurrentSurface(12377))) || EGLThread.this.egl10.eglMakeCurrent(EGLThread.this.eglDisplay, EGLThread.this.eglSurface, EGLThread.this.eglSurface, EGLThread.this.eglContext)) {
                    float time = ((float) (System.currentTimeMillis() - IntroActivity.this.currentDate)) / 1000.0f;
                    Intro.setPage(IntroActivity.this.currentViewPagerPage);
                    Intro.setDate(time);
                    Intro.onDrawFrame();
                    EGLThread.this.egl10.eglSwapBuffers(EGLThread.this.eglDisplay, EGLThread.this.eglSurface);
                    EGLThread.this.postRunnable(new C29671(), 16);
                    return;
                }
                FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGLThread.this.egl10.eglGetError()));
            }
        }

        /* renamed from: org.telegram.ui.IntroActivity$EGLThread$2 */
        class C29692 implements Runnable {
            C29692() {
            }

            public void run() {
                EGLThread.this.finish();
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    looper.quit();
                }
            }
        }

        public EGLThread(SurfaceTexture surface) {
            super("EGLThread");
            this.surfaceTexture = surface;
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
                int[] configsCount = new int[1];
                EGLConfig[] configs = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 24, 12326, 0, 12338, 1, 12337, 2, 12344}, configs, 1, configsCount)) {
                    FileLog.e("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    finish();
                    return false;
                } else if (configsCount[0] > 0) {
                    this.eglConfig = configs[0];
                    int[] iArr = new int[3];
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

        private void loadTexture(int resId, int index) {
            Drawable drawable = IntroActivity.this.getResources().getDrawable(resId);
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                GLES20.glBindTexture(3553, this.textures[index]);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GLUtils.texImage2D(3553, 0, bitmap, 0);
            }
        }

        public void shutdown() {
            postRunnable(new C29692());
        }

        public void setSurfaceTextureSize(int width, int height) {
            this.surfaceWidth = width;
            this.surfaceHeight = height;
            Intro.onSurfaceChanged(width, height, Math.min(((float) this.surfaceWidth) / 150.0f, ((float) this.surfaceHeight) / 150.0f), 0);
        }

        public void run() {
            this.initied = initGL();
            super.run();
        }
    }

    private class IntroAdapter extends PagerAdapter {
        private IntroAdapter() {
        }

        public int getCount() {
            return IntroActivity.this.titles.length;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameLayout = new FrameLayout(container.getContext());
            TextView headerTextView = new TextView(container.getContext());
            headerTextView.setTextColor(-14606047);
            headerTextView.setTextSize(1, 26.0f);
            headerTextView.setGravity(17);
            headerTextView.setTypeface(AndroidUtilities.getTypeface(""));
            frameLayout.addView(headerTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 18.0f, 244.0f, 18.0f, 0.0f));
            TextView messageTextView = new TextView(container.getContext());
            messageTextView.setTextColor(-8355712);
            messageTextView.setTextSize(1, 15.0f);
            messageTextView.setGravity(17);
            messageTextView.setTypeface(AndroidUtilities.getTypeface(""));
            frameLayout.addView(messageTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 16.0f, 286.0f, 16.0f, 0.0f));
            container.addView(frameLayout, 0);
            headerTextView.setText(IntroActivity.this.titles[position]);
            messageTextView.setText(AndroidUtilities.replaceTags(IntroActivity.this.messages[position]));
            return frameLayout;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            IntroActivity.this.bottomPages.setCurrentPage(position);
            IntroActivity.this.currentViewPagerPage = position;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }

    public void onResult(Object object, int StatusCode) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme.TMessages);
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("intro_crashed_time", System.currentTimeMillis()).commit();
        HandleRequest.getNew(this, this).getSelfUpdateAndSetting();
        this.titles = new String[]{LocaleController.getString("Page1Title", R.string.Page1Title), LocaleController.getString("Page2Title", R.string.Page2Title), LocaleController.getString("Page3Title", R.string.Page3Title), LocaleController.getString("Page5Title", R.string.Page5Title), LocaleController.getString("Page4Title", R.string.Page4Title), LocaleController.getString("Page6Title", R.string.Page6Title)};
        this.messages = new String[]{LocaleController.getString("Page1Message", R.string.Page1Message), LocaleController.getString("Page2Message", R.string.Page2Message), LocaleController.getString("Page3Message", R.string.Page3Message), LocaleController.getString("Page5Message", R.string.Page5Message), LocaleController.getString("Page4Message", R.string.Page4Message), LocaleController.getString("Page6Message", R.string.Page6Message)};
        View scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(-1);
        scrollView.addView(frameLayout, LayoutHelper.createScroll(-1, -2, 51));
        FrameLayout frameLayout2 = new FrameLayout(this);
        frameLayout.addView(frameLayout2, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 78.0f, 0.0f, 0.0f));
        scrollView = new TextureView(this);
        frameLayout2.addView(scrollView, LayoutHelper.createFrame(200, 150, 17));
        scrollView.setSurfaceTextureListener(new C29601());
        this.viewPager = new ViewPager(this);
        IntroActivity introActivity = this;
        this.viewPager.setAdapter(new IntroAdapter());
        this.viewPager.setPageMargin(0);
        this.viewPager.setOffscreenPageLimit(1);
        frameLayout.addView(this.viewPager, LayoutHelper.createFrame(-1, -1.0f));
        this.viewPager.addOnPageChangeListener(new C29612());
        scrollView = new TextView(this);
        scrollView.setText(LocaleController.getString("StartMessaging", R.string.StartMessaging).toUpperCase());
        scrollView.setGravity(17);
        scrollView.setTextColor(-1);
        scrollView.setTextSize(1, 16.0f);
        scrollView.setBackgroundResource(R.drawable.regbtn_states);
        scrollView.setTypeface(AndroidUtilities.getTypeface(""));
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator animator = new StateListAnimator();
            scrollView = scrollView;
            animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(scrollView, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            scrollView = scrollView;
            animator.addState(new int[0], ObjectAnimator.ofFloat(scrollView, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            scrollView.setStateListAnimator(animator);
        }
        scrollView.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(10.0f));
        frameLayout.addView(scrollView, LayoutHelper.createFrame(-2, -2.0f, 81, 10.0f, 0.0f, 10.0f, 76.0f));
        scrollView.setOnClickListener(new C29623());
        if (BuildVars.DEBUG_VERSION) {
            scrollView.setOnLongClickListener(new C29634());
        }
        this.bottomPages = new BottomPagesView(this);
        frameLayout.addView(this.bottomPages, LayoutHelper.createFrame(66, 5.0f, 49, 0.0f, 350.0f, 0.0f, 0.0f));
        this.textView = new TextView(this);
        this.textView.setTextColor(-15494190);
        this.textView.setGravity(17);
        this.textView.setTextSize(1, 16.0f);
        this.textView.setTypeface(AndroidUtilities.getTypeface(""));
        frameLayout.addView(this.textView, LayoutHelper.createFrame(-2, 30.0f, 81, 0.0f, 0.0f, 0.0f, 20.0f));
        this.textView.setOnClickListener(new C29645());
        if (AndroidUtilities.isTablet()) {
            FrameLayout frameLayout3 = new FrameLayout(this);
            setContentView(frameLayout3);
            View imageView = new ImageView(this);
            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.catstile);
            drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            imageView.setBackgroundDrawable(drawable);
            frameLayout3.addView(imageView, LayoutHelper.createFrame(-1, -1.0f));
            FrameLayout frameLayout4 = new FrameLayout(this);
            frameLayout4.setBackgroundResource(R.drawable.btnshadow);
            frameLayout4.addView(scrollView, LayoutHelper.createFrame(-1, -1.0f));
            frameLayout3.addView(frameLayout4, LayoutHelper.createFrame(498, 528, 17));
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

    protected void onPause() {
        super.onPause();
        AndroidUtilities.unregisterUpdates();
        ConnectionsManager.getInstance().setAppPaused(true, false);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putLong("intro_crashed_time", 0).commit();
    }

    private void checkContinueText() {
        String arg;
        LocaleController$LocaleInfo englishInfo = null;
        LocaleController$LocaleInfo systemInfo = null;
        LocaleController$LocaleInfo currentLocaleInfo = LocaleController.getInstance().getCurrentLocaleInfo();
        String systemLang = LocaleController.getSystemLocaleStringIso639().toLowerCase();
        if (systemLang.contains("-")) {
            arg = systemLang.split("-")[0];
        } else {
            arg = systemLang;
        }
        String alias = LocaleController.getLocaleAlias(arg);
        for (int a = 0; a < LocaleController.getInstance().languages.size(); a++) {
            LocaleController$LocaleInfo info = (LocaleController$LocaleInfo) LocaleController.getInstance().languages.get(a);
            if (info.shortName.equals(LanguageManager.ENGLISH)) {
                englishInfo = info;
            }
            if (info.shortName.replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-").equals(systemLang) || info.shortName.equals(arg) || (alias != null && info.shortName.equals(alias))) {
                systemInfo = info;
            }
            if (englishInfo != null && systemInfo != null) {
                break;
            }
        }
        if (englishInfo != null && systemInfo != null && englishInfo != systemInfo) {
            TLRPC$TL_langpack_getStrings req = new TLRPC$TL_langpack_getStrings();
            if (systemInfo != currentLocaleInfo) {
                req.lang_code = systemInfo.shortName.replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-");
                this.localeInfo = systemInfo;
            } else {
                req.lang_code = englishInfo.shortName.replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-");
                this.localeInfo = englishInfo;
            }
            req.keys.add("ContinueOnThisLanguage");
            ConnectionsManager.getInstance().sendRequest(req, new C29666(), 8);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.suggestedLangpack) {
            checkContinueText();
        }
    }
}
