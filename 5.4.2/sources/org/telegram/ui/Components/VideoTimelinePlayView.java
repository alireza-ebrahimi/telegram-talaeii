package org.telegram.ui.Components;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.ui.ActionBar.Theme;

@TargetApi(10)
public class VideoTimelinePlayView extends View {
    private static final Object sync = new Object();
    private AsyncTask<Integer, Integer, Bitmap> currentTask;
    private VideoTimelineViewDelegate delegate;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private int frameHeight;
    private long frameTimeOffset;
    private int frameWidth;
    private ArrayList<Bitmap> frames = new ArrayList();
    private int framesToLoad;
    private boolean isRoundFrames;
    private int lastWidth;
    private float maxProgressDiff = 1.0f;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private float minProgressDiff = BitmapDescriptorFactory.HUE_RED;
    private Paint paint = new Paint(1);
    private Paint paint2;
    private float playProgress = 0.5f;
    private float pressDx;
    private boolean pressedLeft;
    private boolean pressedPlay;
    private boolean pressedRight;
    private float progressLeft;
    private float progressRight = 1.0f;
    private Rect rect1;
    private Rect rect2;
    private RectF rect3 = new RectF();
    private long videoLength;

    /* renamed from: org.telegram.ui.Components.VideoTimelinePlayView$1 */
    class C46401 extends AsyncTask<Integer, Integer, Bitmap> {
        private int frameNum = 0;

        C46401() {
        }

        protected Bitmap doInBackground(Integer... numArr) {
            Throwable th;
            Bitmap bitmap = null;
            this.frameNum = numArr[0].intValue();
            if (isCancelled()) {
                return null;
            }
            try {
                Bitmap frameAtTime = VideoTimelinePlayView.this.mediaMetadataRetriever.getFrameAtTime((VideoTimelinePlayView.this.frameTimeOffset * ((long) this.frameNum)) * 1000, 2);
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (frameAtTime == null) {
                        return frameAtTime;
                    }
                    bitmap = Bitmap.createBitmap(VideoTimelinePlayView.this.frameWidth, VideoTimelinePlayView.this.frameHeight, frameAtTime.getConfig());
                    Canvas canvas = new Canvas(bitmap);
                    float access$200 = ((float) VideoTimelinePlayView.this.frameWidth) / ((float) frameAtTime.getWidth());
                    float access$300 = ((float) VideoTimelinePlayView.this.frameHeight) / ((float) frameAtTime.getHeight());
                    if (access$200 <= access$300) {
                        access$200 = access$300;
                    }
                    int width = (int) (((float) frameAtTime.getWidth()) * access$200);
                    int height = (int) (access$200 * ((float) frameAtTime.getHeight()));
                    canvas.drawBitmap(frameAtTime, new Rect(0, 0, frameAtTime.getWidth(), frameAtTime.getHeight()), new Rect((VideoTimelinePlayView.this.frameWidth - width) / 2, (VideoTimelinePlayView.this.frameHeight - height) / 2, width, height), null);
                    frameAtTime.recycle();
                    return bitmap;
                } catch (Throwable e) {
                    Throwable th2 = e;
                    bitmap = frameAtTime;
                    th = th2;
                    FileLog.e(th);
                    return bitmap;
                }
            } catch (Exception e2) {
                th = e2;
                FileLog.e(th);
                return bitmap;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (!isCancelled()) {
                VideoTimelinePlayView.this.frames.add(bitmap);
                VideoTimelinePlayView.this.invalidate();
                if (this.frameNum < VideoTimelinePlayView.this.framesToLoad) {
                    VideoTimelinePlayView.this.reloadFrames(this.frameNum + 1);
                }
            }
        }
    }

    public interface VideoTimelineViewDelegate {
        void didStartDragging();

        void didStopDragging();

        void onLeftProgressChanged(float f);

        void onPlayProgressChanged(float f);

        void onRightProgressChanged(float f);
    }

    public VideoTimelinePlayView(Context context) {
        super(context);
        this.paint.setColor(-1);
        this.paint2 = new Paint();
        this.paint2.setColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        this.drawableLeft = context.getResources().getDrawable(R.drawable.video_cropleft);
        this.drawableLeft.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
        this.drawableRight = context.getResources().getDrawable(R.drawable.video_cropright);
        this.drawableRight.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
    }

    private void reloadFrames(int i) {
        if (this.mediaMetadataRetriever != null) {
            if (i == 0) {
                if (this.isRoundFrames) {
                    int dp = AndroidUtilities.dp(56.0f);
                    this.frameWidth = dp;
                    this.frameHeight = dp;
                    this.framesToLoad = (int) Math.ceil((double) (((float) (getMeasuredWidth() - AndroidUtilities.dp(16.0f))) / (((float) this.frameHeight) / 2.0f)));
                } else {
                    this.frameHeight = AndroidUtilities.dp(40.0f);
                    this.framesToLoad = (getMeasuredWidth() - AndroidUtilities.dp(16.0f)) / this.frameHeight;
                    this.frameWidth = (int) Math.ceil((double) (((float) (getMeasuredWidth() - AndroidUtilities.dp(16.0f))) / ((float) this.framesToLoad)));
                }
                this.frameTimeOffset = this.videoLength / ((long) this.framesToLoad);
            }
            this.currentTask = new C46401();
            this.currentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[]{Integer.valueOf(i), null, null});
        }
    }

    public void clearFrames() {
        Iterator it = this.frames.iterator();
        while (it.hasNext()) {
            Bitmap bitmap = (Bitmap) it.next();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        this.frames.clear();
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
        invalidate();
    }

    public void destroy() {
        synchronized (sync) {
            try {
                if (this.mediaMetadataRetriever != null) {
                    this.mediaMetadataRetriever.release();
                    this.mediaMetadataRetriever = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        Iterator it = this.frames.iterator();
        while (it.hasNext()) {
            Bitmap bitmap = (Bitmap) it.next();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        this.frames.clear();
        if (this.currentTask != null) {
            this.currentTask.cancel(true);
            this.currentTask = null;
        }
    }

    public float getLeftProgress() {
        return this.progressLeft;
    }

    public float getProgress() {
        return this.playProgress;
    }

    public float getRightProgress() {
        return this.progressRight;
    }

    public boolean isDragging() {
        return this.pressedPlay;
    }

    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(36.0f);
        int dp = ((int) (((float) measuredWidth) * this.progressLeft)) + AndroidUtilities.dp(16.0f);
        int dp2 = ((int) (((float) measuredWidth) * this.progressRight)) + AndroidUtilities.dp(16.0f);
        canvas.save();
        canvas.clipRect(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(20.0f) + measuredWidth, AndroidUtilities.dp(48.0f));
        if (this.frames.isEmpty() && this.currentTask == null) {
            reloadFrames(0);
        } else {
            int i = 0;
            for (int i2 = 0; i2 < this.frames.size(); i2++) {
                Bitmap bitmap = (Bitmap) this.frames.get(i2);
                if (bitmap != null) {
                    int dp3 = ((this.isRoundFrames ? this.frameWidth / 2 : this.frameWidth) * i) + AndroidUtilities.dp(16.0f);
                    int dp4 = AndroidUtilities.dp(6.0f);
                    if (this.isRoundFrames) {
                        this.rect2.set(dp3, dp4, AndroidUtilities.dp(28.0f) + dp3, AndroidUtilities.dp(28.0f) + dp4);
                        canvas.drawBitmap(bitmap, this.rect1, this.rect2, null);
                    } else {
                        canvas.drawBitmap(bitmap, (float) dp3, (float) dp4, null);
                    }
                }
                i++;
            }
        }
        int dp5 = AndroidUtilities.dp(6.0f);
        int dp6 = AndroidUtilities.dp(48.0f);
        canvas.drawRect((float) AndroidUtilities.dp(16.0f), (float) dp5, (float) dp, (float) AndroidUtilities.dp(46.0f), this.paint2);
        canvas.drawRect((float) (AndroidUtilities.dp(4.0f) + dp2), (float) dp5, (float) ((AndroidUtilities.dp(16.0f) + measuredWidth) + AndroidUtilities.dp(4.0f)), (float) AndroidUtilities.dp(46.0f), this.paint2);
        canvas.drawRect((float) dp, (float) AndroidUtilities.dp(4.0f), (float) (AndroidUtilities.dp(2.0f) + dp), (float) dp6, this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + dp2), (float) AndroidUtilities.dp(4.0f), (float) (AndroidUtilities.dp(4.0f) + dp2), (float) dp6, this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + dp), (float) AndroidUtilities.dp(4.0f), (float) (AndroidUtilities.dp(4.0f) + dp2), (float) dp5, this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + dp), (float) (dp6 - AndroidUtilities.dp(2.0f)), (float) (AndroidUtilities.dp(4.0f) + dp2), (float) dp6, this.paint);
        canvas.restore();
        this.rect3.set((float) (dp - AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(4.0f), (float) (AndroidUtilities.dp(2.0f) + dp), (float) dp6);
        canvas.drawRoundRect(this.rect3, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.paint);
        this.drawableLeft.setBounds(dp - AndroidUtilities.dp(8.0f), AndroidUtilities.dp(4.0f) + ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2), AndroidUtilities.dp(2.0f) + dp, ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2) + AndroidUtilities.dp(22.0f));
        this.drawableLeft.draw(canvas);
        this.rect3.set((float) (AndroidUtilities.dp(2.0f) + dp2), (float) AndroidUtilities.dp(4.0f), (float) (AndroidUtilities.dp(12.0f) + dp2), (float) dp6);
        canvas.drawRoundRect(this.rect3, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.paint);
        this.drawableRight.setBounds(AndroidUtilities.dp(2.0f) + dp2, AndroidUtilities.dp(4.0f) + ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2), AndroidUtilities.dp(12.0f) + dp2, ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2) + AndroidUtilities.dp(22.0f));
        this.drawableRight.draw(canvas);
        float dp7 = ((float) AndroidUtilities.dp(18.0f)) + (((float) measuredWidth) * (this.progressLeft + ((this.progressRight - this.progressLeft) * this.playProgress)));
        this.rect3.set(dp7 - ((float) AndroidUtilities.dp(1.5f)), (float) AndroidUtilities.dp(2.0f), ((float) AndroidUtilities.dp(1.5f)) + dp7, (float) AndroidUtilities.dp(50.0f));
        canvas.drawRoundRect(this.rect3, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), this.paint2);
        canvas.drawCircle(dp7, (float) AndroidUtilities.dp(52.0f), (float) AndroidUtilities.dp(3.5f), this.paint2);
        this.rect3.set(dp7 - ((float) AndroidUtilities.dp(1.0f)), (float) AndroidUtilities.dp(2.0f), ((float) AndroidUtilities.dp(1.0f)) + dp7, (float) AndroidUtilities.dp(50.0f));
        canvas.drawRoundRect(this.rect3, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), this.paint);
        canvas.drawCircle(dp7, (float) AndroidUtilities.dp(52.0f), (float) AndroidUtilities.dp(3.0f), this.paint);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = MeasureSpec.getSize(i);
        if (this.lastWidth != size) {
            clearFrames();
            this.lastWidth = size;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(32.0f);
        int dp = AndroidUtilities.dp(16.0f) + ((int) (((float) measuredWidth) * this.progressLeft));
        int dp2 = AndroidUtilities.dp(16.0f) + ((int) (((float) measuredWidth) * (this.progressLeft + ((this.progressRight - this.progressLeft) * this.playProgress))));
        int dp3 = ((int) (((float) measuredWidth) * this.progressRight)) + AndroidUtilities.dp(16.0f);
        if (motionEvent.getAction() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.mediaMetadataRetriever == null) {
                return false;
            }
            measuredWidth = AndroidUtilities.dp(12.0f);
            int dp4 = AndroidUtilities.dp(8.0f);
            if (((float) (dp2 - dp4)) <= x && x <= ((float) (dp4 + dp2)) && y >= BitmapDescriptorFactory.HUE_RED && y <= ((float) getMeasuredHeight())) {
                if (this.delegate != null) {
                    this.delegate.didStartDragging();
                }
                this.pressedPlay = true;
                this.pressDx = (float) ((int) (x - ((float) dp2)));
                invalidate();
                return true;
            } else if (((float) (dp - measuredWidth)) <= x && x <= ((float) (dp + measuredWidth)) && y >= BitmapDescriptorFactory.HUE_RED && y <= ((float) getMeasuredHeight())) {
                if (this.delegate != null) {
                    this.delegate.didStartDragging();
                }
                this.pressedLeft = true;
                this.pressDx = (float) ((int) (x - ((float) dp)));
                invalidate();
                return true;
            } else if (((float) (dp3 - measuredWidth)) <= x && x <= ((float) (dp3 + measuredWidth)) && y >= BitmapDescriptorFactory.HUE_RED && y <= ((float) getMeasuredHeight())) {
                if (this.delegate != null) {
                    this.delegate.didStartDragging();
                }
                this.pressedRight = true;
                this.pressDx = (float) ((int) (x - ((float) dp3)));
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (this.pressedLeft) {
                if (this.delegate != null) {
                    this.delegate.didStopDragging();
                }
                this.pressedLeft = false;
                return true;
            } else if (this.pressedRight) {
                if (this.delegate != null) {
                    this.delegate.didStopDragging();
                }
                this.pressedRight = false;
                return true;
            } else if (this.pressedPlay) {
                if (this.delegate != null) {
                    this.delegate.didStopDragging();
                }
                this.pressedPlay = false;
                return true;
            }
        } else if (motionEvent.getAction() == 2) {
            if (this.pressedPlay) {
                this.playProgress = ((float) (((int) (x - this.pressDx)) - AndroidUtilities.dp(16.0f))) / ((float) measuredWidth);
                if (this.playProgress < this.progressLeft) {
                    this.playProgress = this.progressLeft;
                } else if (this.playProgress > this.progressRight) {
                    this.playProgress = this.progressRight;
                }
                this.playProgress = (this.playProgress - this.progressLeft) / (this.progressRight - this.progressLeft);
                if (this.delegate != null) {
                    this.delegate.onPlayProgressChanged(this.progressLeft + ((this.progressRight - this.progressLeft) * this.playProgress));
                }
                invalidate();
                return true;
            } else if (this.pressedLeft) {
                dp = (int) (x - this.pressDx);
                if (dp < AndroidUtilities.dp(16.0f)) {
                    dp3 = AndroidUtilities.dp(16.0f);
                } else if (dp <= dp3) {
                    dp3 = dp;
                }
                this.progressLeft = ((float) (dp3 - AndroidUtilities.dp(16.0f))) / ((float) measuredWidth);
                if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
                    this.progressRight = this.progressLeft + this.maxProgressDiff;
                } else if (this.minProgressDiff != BitmapDescriptorFactory.HUE_RED && this.progressRight - this.progressLeft < this.minProgressDiff) {
                    this.progressLeft = this.progressRight - this.minProgressDiff;
                    if (this.progressLeft < BitmapDescriptorFactory.HUE_RED) {
                        this.progressLeft = BitmapDescriptorFactory.HUE_RED;
                    }
                }
                if (this.delegate != null) {
                    this.delegate.onLeftProgressChanged(this.progressLeft);
                }
                invalidate();
                return true;
            } else if (this.pressedRight) {
                dp3 = (int) (x - this.pressDx);
                if (dp3 < dp) {
                    dp3 = dp;
                } else if (dp3 > AndroidUtilities.dp(16.0f) + measuredWidth) {
                    dp3 = AndroidUtilities.dp(16.0f) + measuredWidth;
                }
                this.progressRight = ((float) (dp3 - AndroidUtilities.dp(16.0f))) / ((float) measuredWidth);
                if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
                    this.progressLeft = this.progressRight - this.maxProgressDiff;
                } else if (this.minProgressDiff != BitmapDescriptorFactory.HUE_RED && this.progressRight - this.progressLeft < this.minProgressDiff) {
                    this.progressRight = this.progressLeft + this.minProgressDiff;
                    if (this.progressRight > 1.0f) {
                        this.progressRight = 1.0f;
                    }
                }
                if (this.delegate != null) {
                    this.delegate.onRightProgressChanged(this.progressRight);
                }
                invalidate();
                return true;
            }
        }
        return false;
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    public void setDelegate(VideoTimelineViewDelegate videoTimelineViewDelegate) {
        this.delegate = videoTimelineViewDelegate;
    }

    public void setMaxProgressDiff(float f) {
        this.maxProgressDiff = f;
        if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
            this.progressRight = this.progressLeft + this.maxProgressDiff;
            invalidate();
        }
    }

    public void setMinProgressDiff(float f) {
        this.minProgressDiff = f;
    }

    public void setProgress(float f) {
        this.playProgress = f;
        invalidate();
    }

    public void setRoundFrames(boolean z) {
        this.isRoundFrames = z;
        if (this.isRoundFrames) {
            this.rect1 = new Rect(AndroidUtilities.dp(14.0f), AndroidUtilities.dp(14.0f), AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
            this.rect2 = new Rect();
        }
    }

    public void setVideoPath(String str) {
        destroy();
        this.mediaMetadataRetriever = new MediaMetadataRetriever();
        this.progressLeft = BitmapDescriptorFactory.HUE_RED;
        this.progressRight = 1.0f;
        try {
            this.mediaMetadataRetriever.setDataSource(str);
            this.videoLength = Long.parseLong(this.mediaMetadataRetriever.extractMetadata(9));
        } catch (Throwable e) {
            FileLog.e(e);
        }
        invalidate();
    }
}
