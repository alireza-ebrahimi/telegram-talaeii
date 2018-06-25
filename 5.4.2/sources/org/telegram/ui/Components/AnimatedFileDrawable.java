package org.telegram.ui.Components;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

public class AnimatedFileDrawable extends BitmapDrawable implements Animatable {
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2, new DiscardPolicy());
    private static final Handler uiHandler = new Handler(Looper.getMainLooper());
    private RectF actualDrawRect = new RectF();
    private boolean applyTransformation;
    private Bitmap backgroundBitmap;
    private BitmapShader backgroundShader;
    private RectF bitmapRect = new RectF();
    private boolean decodeSingleFrame;
    private boolean decoderCreated;
    private boolean destroyWhenDone;
    private final Rect dstRect = new Rect();
    private int invalidateAfter = 50;
    private volatile boolean isRecycled;
    private volatile boolean isRunning;
    private long lastFrameDecodeTime;
    private long lastFrameTime;
    private int lastTimeStamp;
    private Runnable loadFrameRunnable = new C43213();
    private Runnable loadFrameTask;
    protected final Runnable mInvalidateTask = new C43191();
    private final Runnable mStartTask = new C43224();
    private final int[] metaData = new int[4];
    private volatile int nativePtr;
    private Bitmap nextRenderingBitmap;
    private BitmapShader nextRenderingShader;
    private View parentView = null;
    private File path;
    private boolean recycleWithSecond;
    private Bitmap renderingBitmap;
    private BitmapShader renderingShader;
    private int roundRadius;
    private RectF roundRect = new RectF();
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private View secondParentView = null;
    private Matrix shaderMatrix = new Matrix();
    private boolean singleFrameDecoded;
    private Runnable uiRunnable = new C43202();

    /* renamed from: org.telegram.ui.Components.AnimatedFileDrawable$1 */
    class C43191 implements Runnable {
        C43191() {
        }

        public void run() {
            if (AnimatedFileDrawable.this.secondParentView != null) {
                AnimatedFileDrawable.this.secondParentView.invalidate();
            } else if (AnimatedFileDrawable.this.parentView != null) {
                AnimatedFileDrawable.this.parentView.invalidate();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.AnimatedFileDrawable$2 */
    class C43202 implements Runnable {
        C43202() {
        }

        public void run() {
            if (AnimatedFileDrawable.this.destroyWhenDone && AnimatedFileDrawable.this.nativePtr != 0) {
                AnimatedFileDrawable.destroyDecoder(AnimatedFileDrawable.this.nativePtr);
                AnimatedFileDrawable.this.nativePtr = 0;
            }
            if (AnimatedFileDrawable.this.nativePtr == 0) {
                if (AnimatedFileDrawable.this.renderingBitmap != null) {
                    AnimatedFileDrawable.this.renderingBitmap.recycle();
                    AnimatedFileDrawable.this.renderingBitmap = null;
                }
                if (AnimatedFileDrawable.this.backgroundBitmap != null) {
                    AnimatedFileDrawable.this.backgroundBitmap.recycle();
                    AnimatedFileDrawable.this.backgroundBitmap = null;
                    return;
                }
                return;
            }
            AnimatedFileDrawable.this.singleFrameDecoded = true;
            AnimatedFileDrawable.this.loadFrameTask = null;
            AnimatedFileDrawable.this.nextRenderingBitmap = AnimatedFileDrawable.this.backgroundBitmap;
            AnimatedFileDrawable.this.nextRenderingShader = AnimatedFileDrawable.this.backgroundShader;
            if (AnimatedFileDrawable.this.metaData[3] < AnimatedFileDrawable.this.lastTimeStamp) {
                AnimatedFileDrawable.this.lastTimeStamp = 0;
            }
            if (AnimatedFileDrawable.this.metaData[3] - AnimatedFileDrawable.this.lastTimeStamp != 0) {
                AnimatedFileDrawable.this.invalidateAfter = AnimatedFileDrawable.this.metaData[3] - AnimatedFileDrawable.this.lastTimeStamp;
            }
            AnimatedFileDrawable.this.lastTimeStamp = AnimatedFileDrawable.this.metaData[3];
            if (AnimatedFileDrawable.this.secondParentView != null) {
                AnimatedFileDrawable.this.secondParentView.invalidate();
            } else if (AnimatedFileDrawable.this.parentView != null) {
                AnimatedFileDrawable.this.parentView.invalidate();
            }
            AnimatedFileDrawable.this.scheduleNextGetFrame();
        }
    }

    /* renamed from: org.telegram.ui.Components.AnimatedFileDrawable$3 */
    class C43213 implements Runnable {
        C43213() {
        }

        public void run() {
            if (!AnimatedFileDrawable.this.isRecycled) {
                if (!AnimatedFileDrawable.this.decoderCreated && AnimatedFileDrawable.this.nativePtr == 0) {
                    AnimatedFileDrawable.this.nativePtr = AnimatedFileDrawable.createDecoder(AnimatedFileDrawable.this.path.getAbsolutePath(), AnimatedFileDrawable.this.metaData);
                    AnimatedFileDrawable.this.decoderCreated = true;
                }
                try {
                    if (AnimatedFileDrawable.this.backgroundBitmap == null) {
                        AnimatedFileDrawable.this.backgroundBitmap = Bitmap.createBitmap(AnimatedFileDrawable.this.metaData[0], AnimatedFileDrawable.this.metaData[1], Config.ARGB_8888);
                        if (!(AnimatedFileDrawable.this.backgroundShader != null || AnimatedFileDrawable.this.backgroundBitmap == null || AnimatedFileDrawable.this.roundRadius == 0)) {
                            AnimatedFileDrawable.this.backgroundShader = new BitmapShader(AnimatedFileDrawable.this.backgroundBitmap, TileMode.CLAMP, TileMode.CLAMP);
                        }
                    }
                } catch (Throwable th) {
                    FileLog.e(th);
                }
                if (AnimatedFileDrawable.this.backgroundBitmap != null) {
                    AnimatedFileDrawable.this.lastFrameDecodeTime = System.currentTimeMillis();
                    AnimatedFileDrawable.getVideoFrame(AnimatedFileDrawable.this.nativePtr, AnimatedFileDrawable.this.backgroundBitmap, AnimatedFileDrawable.this.metaData);
                }
            }
            AndroidUtilities.runOnUIThread(AnimatedFileDrawable.this.uiRunnable);
        }
    }

    /* renamed from: org.telegram.ui.Components.AnimatedFileDrawable$4 */
    class C43224 implements Runnable {
        C43224() {
        }

        public void run() {
            if (AnimatedFileDrawable.this.secondParentView != null) {
                AnimatedFileDrawable.this.secondParentView.invalidate();
            } else if (AnimatedFileDrawable.this.parentView != null) {
                AnimatedFileDrawable.this.parentView.invalidate();
            }
        }
    }

    public AnimatedFileDrawable(File file, boolean z) {
        this.path = file;
        if (z) {
            this.nativePtr = createDecoder(file.getAbsolutePath(), this.metaData);
            this.decoderCreated = true;
        }
    }

    private static native int createDecoder(String str, int[] iArr);

    private static native void destroyDecoder(int i);

    private static native int getVideoFrame(int i, Bitmap bitmap, int[] iArr);

    protected static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == uiHandler.getLooper()) {
            runnable.run();
        } else {
            uiHandler.post(runnable);
        }
    }

    private void scheduleNextGetFrame() {
        long j = 0;
        if (this.loadFrameTask != null) {
            return;
        }
        if ((this.nativePtr != 0 || !this.decoderCreated) && !this.destroyWhenDone) {
            if (!this.isRunning) {
                if (!this.decodeSingleFrame) {
                    return;
                }
                if (this.decodeSingleFrame && this.singleFrameDecoded) {
                    return;
                }
            }
            if (this.lastFrameDecodeTime != 0) {
                j = Math.min((long) this.invalidateAfter, Math.max(0, ((long) this.invalidateAfter) - (System.currentTimeMillis() - this.lastFrameDecodeTime)));
            }
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = executor;
            Runnable runnable = this.loadFrameRunnable;
            this.loadFrameTask = runnable;
            scheduledThreadPoolExecutor.schedule(runnable, j, TimeUnit.MILLISECONDS);
        }
    }

    public void draw(Canvas canvas) {
        if ((this.nativePtr != 0 || !this.decoderCreated) && !this.destroyWhenDone) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.isRunning) {
                if (this.renderingBitmap == null && this.nextRenderingBitmap == null) {
                    scheduleNextGetFrame();
                } else if (Math.abs(currentTimeMillis - this.lastFrameTime) >= ((long) this.invalidateAfter) && this.nextRenderingBitmap != null) {
                    this.renderingBitmap = this.nextRenderingBitmap;
                    this.renderingShader = this.nextRenderingShader;
                    this.nextRenderingBitmap = null;
                    this.nextRenderingShader = null;
                    this.lastFrameTime = currentTimeMillis;
                }
            } else if (!this.isRunning && this.decodeSingleFrame && Math.abs(currentTimeMillis - this.lastFrameTime) >= ((long) this.invalidateAfter) && this.nextRenderingBitmap != null) {
                this.renderingBitmap = this.nextRenderingBitmap;
                this.renderingShader = this.nextRenderingShader;
                this.nextRenderingBitmap = null;
                this.nextRenderingShader = null;
                this.lastFrameTime = currentTimeMillis;
            }
            if (this.renderingBitmap != null) {
                int width;
                int height;
                if (this.applyTransformation) {
                    width = this.renderingBitmap.getWidth();
                    height = this.renderingBitmap.getHeight();
                    if (this.metaData[2] == 90 || this.metaData[2] == 270) {
                        int i = width;
                        width = height;
                        height = i;
                    }
                    this.dstRect.set(getBounds());
                    this.scaleX = ((float) this.dstRect.width()) / ((float) width);
                    this.scaleY = ((float) this.dstRect.height()) / ((float) height);
                    this.applyTransformation = false;
                }
                if (this.roundRadius != 0) {
                    float max = Math.max(this.scaleX, this.scaleY);
                    if (this.renderingShader == null) {
                        this.renderingShader = new BitmapShader(this.backgroundBitmap, TileMode.CLAMP, TileMode.CLAMP);
                    }
                    getPaint().setShader(this.renderingShader);
                    this.roundRect.set(this.dstRect);
                    this.shaderMatrix.reset();
                    if (Math.abs(this.scaleX - this.scaleY) > 1.0E-5f) {
                        if (this.metaData[2] == 90 || this.metaData[2] == 270) {
                            width = (int) Math.floor((double) (((float) this.dstRect.height()) / max));
                            height = (int) Math.floor((double) (((float) this.dstRect.width()) / max));
                        } else {
                            width = (int) Math.floor((double) (((float) this.dstRect.width()) / max));
                            height = (int) Math.floor((double) (((float) this.dstRect.height()) / max));
                        }
                        this.bitmapRect.set((float) ((this.renderingBitmap.getWidth() - width) / 2), (float) ((this.renderingBitmap.getHeight() - height) / 2), (float) width, (float) height);
                        AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.metaData[2], ScaleToFit.START);
                    } else {
                        this.bitmapRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) this.renderingBitmap.getWidth(), (float) this.renderingBitmap.getHeight());
                        AndroidUtilities.setRectToRect(this.shaderMatrix, this.bitmapRect, this.roundRect, this.metaData[2], ScaleToFit.FILL);
                    }
                    this.renderingShader.setLocalMatrix(this.shaderMatrix);
                    canvas.drawRoundRect(this.actualDrawRect, (float) this.roundRadius, (float) this.roundRadius, getPaint());
                } else {
                    canvas.translate((float) this.dstRect.left, (float) this.dstRect.top);
                    if (this.metaData[2] == 90) {
                        canvas.rotate(90.0f);
                        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) (-this.dstRect.width()));
                    } else if (this.metaData[2] == 180) {
                        canvas.rotate(180.0f);
                        canvas.translate((float) (-this.dstRect.width()), (float) (-this.dstRect.height()));
                    } else if (this.metaData[2] == 270) {
                        canvas.rotate(270.0f);
                        canvas.translate((float) (-this.dstRect.height()), BitmapDescriptorFactory.HUE_RED);
                    }
                    canvas.scale(this.scaleX, this.scaleY);
                    canvas.drawBitmap(this.renderingBitmap, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, getPaint());
                }
                if (this.isRunning) {
                    long max2 = Math.max(1, (((long) this.invalidateAfter) - (currentTimeMillis - this.lastFrameTime)) - 17);
                    uiHandler.removeCallbacks(this.mInvalidateTask);
                    uiHandler.postDelayed(this.mInvalidateTask, Math.min(max2, (long) this.invalidateAfter));
                }
            }
        }
    }

    protected void finalize() {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    public Bitmap getAnimatedBitmap() {
        return this.renderingBitmap != null ? this.renderingBitmap : this.nextRenderingBitmap != null ? this.nextRenderingBitmap : null;
    }

    public int getIntrinsicHeight() {
        return this.decoderCreated ? (this.metaData[2] == 90 || this.metaData[2] == 270) ? this.metaData[0] : this.metaData[1] : AndroidUtilities.dp(100.0f);
    }

    public int getIntrinsicWidth() {
        return this.decoderCreated ? (this.metaData[2] == 90 || this.metaData[2] == 270) ? this.metaData[1] : this.metaData[0] : AndroidUtilities.dp(100.0f);
    }

    public int getMinimumHeight() {
        return this.decoderCreated ? (this.metaData[2] == 90 || this.metaData[2] == 270) ? this.metaData[0] : this.metaData[1] : AndroidUtilities.dp(100.0f);
    }

    public int getMinimumWidth() {
        return this.decoderCreated ? (this.metaData[2] == 90 || this.metaData[2] == 270) ? this.metaData[1] : this.metaData[0] : AndroidUtilities.dp(100.0f);
    }

    public int getOpacity() {
        return -2;
    }

    public int getOrientation() {
        return this.metaData[2];
    }

    public boolean hasBitmap() {
        return (this.nativePtr == 0 || (this.renderingBitmap == null && this.nextRenderingBitmap == null)) ? false : true;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public AnimatedFileDrawable makeCopy() {
        AnimatedFileDrawable animatedFileDrawable = new AnimatedFileDrawable(this.path, false);
        animatedFileDrawable.metaData[0] = this.metaData[0];
        animatedFileDrawable.metaData[1] = this.metaData[1];
        return animatedFileDrawable;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.applyTransformation = true;
    }

    public void recycle() {
        if (this.secondParentView != null) {
            this.recycleWithSecond = true;
            return;
        }
        this.isRunning = false;
        this.isRecycled = true;
        if (this.loadFrameTask == null) {
            if (this.nativePtr != 0) {
                destroyDecoder(this.nativePtr);
                this.nativePtr = 0;
            }
            if (this.renderingBitmap != null) {
                this.renderingBitmap.recycle();
                this.renderingBitmap = null;
            }
            if (this.nextRenderingBitmap != null) {
                this.nextRenderingBitmap.recycle();
                this.nextRenderingBitmap = null;
                return;
            }
            return;
        }
        this.destroyWhenDone = true;
    }

    public void setActualDrawRect(int i, int i2, int i3, int i4) {
        this.actualDrawRect.set((float) i, (float) i2, (float) (i + i3), (float) (i2 + i4));
    }

    public void setAllowDecodeSingleFrame(boolean z) {
        this.decodeSingleFrame = z;
        if (this.decodeSingleFrame) {
            scheduleNextGetFrame();
        }
    }

    public void setParentView(View view) {
        this.parentView = view;
    }

    public void setRoundRadius(int i) {
        this.roundRadius = i;
        getPaint().setFlags(1);
    }

    public void setSecondParentView(View view) {
        this.secondParentView = view;
        if (view == null && this.recycleWithSecond) {
            recycle();
        }
    }

    public void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            scheduleNextGetFrame();
            runOnUiThread(this.mStartTask);
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
