package org.telegram.ui.Components;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

@TargetApi(10)
public class VideoTimelineView extends View {
    private static final Object sync = new Object();
    private AsyncTask<Integer, Integer, Bitmap> currentTask;
    private VideoTimelineViewDelegate delegate;
    private int frameHeight;
    private long frameTimeOffset;
    private int frameWidth;
    private ArrayList<Bitmap> frames = new ArrayList();
    private int framesToLoad;
    private boolean isRoundFrames;
    private float maxProgressDiff = 1.0f;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private float minProgressDiff = 0.0f;
    private Paint paint = new Paint(1);
    private Paint paint2;
    private float pressDx;
    private boolean pressedLeft;
    private boolean pressedRight;
    private float progressLeft;
    private float progressRight = 1.0f;
    private Rect rect1;
    private Rect rect2;
    private long videoLength;

    public interface VideoTimelineViewDelegate {
        void didStartDragging();

        void didStopDragging();

        void onLeftProgressChanged(float f);

        void onRightProgressChanged(float f);
    }

    /* renamed from: org.telegram.ui.Components.VideoTimelineView$1 */
    class C28031 extends AsyncTask<Integer, Integer, Bitmap> {
        private int frameNum = 0;

        C28031() {
        }

        protected Bitmap doInBackground(Integer... objects) {
            this.frameNum = objects[0].intValue();
            Bitmap bitmap = null;
            if (isCancelled()) {
                return null;
            }
            try {
                bitmap = VideoTimelineView.this.mediaMetadataRetriever.getFrameAtTime((VideoTimelineView.this.frameTimeOffset * ((long) this.frameNum)) * 1000, 2);
                if (isCancelled()) {
                    return null;
                }
                if (bitmap != null) {
                    float scale;
                    Bitmap result = Bitmap.createBitmap(VideoTimelineView.this.frameWidth, VideoTimelineView.this.frameHeight, bitmap.getConfig());
                    Canvas canvas = new Canvas(result);
                    float scaleX = ((float) VideoTimelineView.this.frameWidth) / ((float) bitmap.getWidth());
                    float scaleY = ((float) VideoTimelineView.this.frameHeight) / ((float) bitmap.getHeight());
                    if (scaleX > scaleY) {
                        scale = scaleX;
                    } else {
                        scale = scaleY;
                    }
                    int w = (int) (((float) bitmap.getWidth()) * scale);
                    int h = (int) (((float) bitmap.getHeight()) * scale);
                    canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect((VideoTimelineView.this.frameWidth - w) / 2, (VideoTimelineView.this.frameHeight - h) / 2, w, h), null);
                    bitmap.recycle();
                    bitmap = result;
                }
                return bitmap;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (!isCancelled()) {
                VideoTimelineView.this.frames.add(bitmap);
                VideoTimelineView.this.invalidate();
                if (this.frameNum < VideoTimelineView.this.framesToLoad) {
                    VideoTimelineView.this.reloadFrames(this.frameNum + 1);
                }
            }
        }
    }

    public VideoTimelineView(Context context) {
        super(context);
        this.paint.setColor(-1);
        this.paint2 = new Paint();
        this.paint2.setColor(2130706432);
    }

    public float getLeftProgress() {
        return this.progressLeft;
    }

    public float getRightProgress() {
        return this.progressRight;
    }

    public void setMinProgressDiff(float value) {
        this.minProgressDiff = value;
    }

    public void setMaxProgressDiff(float value) {
        this.maxProgressDiff = value;
        if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
            this.progressRight = this.progressLeft + this.maxProgressDiff;
            invalidate();
        }
    }

    public void setRoundFrames(boolean value) {
        this.isRoundFrames = value;
        if (this.isRoundFrames) {
            this.rect1 = new Rect(AndroidUtilities.dp(14.0f), AndroidUtilities.dp(14.0f), AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
            this.rect2 = new Rect();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int width = getMeasuredWidth() - AndroidUtilities.dp(32.0f);
        int startX = ((int) (((float) width) * this.progressLeft)) + AndroidUtilities.dp(16.0f);
        int endX = ((int) (((float) width) * this.progressRight)) + AndroidUtilities.dp(16.0f);
        if (event.getAction() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.mediaMetadataRetriever == null) {
                return false;
            }
            int additionWidth = AndroidUtilities.dp(12.0f);
            if (((float) (startX - additionWidth)) <= x && x <= ((float) (startX + additionWidth)) && y >= 0.0f && y <= ((float) getMeasuredHeight())) {
                if (this.delegate != null) {
                    this.delegate.didStartDragging();
                }
                this.pressedLeft = true;
                this.pressDx = (float) ((int) (x - ((float) startX)));
                invalidate();
                return true;
            } else if (((float) (endX - additionWidth)) > x || x > ((float) (endX + additionWidth)) || y < 0.0f || y > ((float) getMeasuredHeight())) {
                return false;
            } else {
                if (this.delegate != null) {
                    this.delegate.didStartDragging();
                }
                this.pressedRight = true;
                this.pressDx = (float) ((int) (x - ((float) endX)));
                invalidate();
                return true;
            }
        } else if (event.getAction() == 1 || event.getAction() == 3) {
            if (this.pressedLeft) {
                if (this.delegate != null) {
                    this.delegate.didStopDragging();
                }
                this.pressedLeft = false;
                return true;
            } else if (!this.pressedRight) {
                return false;
            } else {
                if (this.delegate != null) {
                    this.delegate.didStopDragging();
                }
                this.pressedRight = false;
                return true;
            }
        } else if (event.getAction() != 2) {
            return false;
        } else {
            if (this.pressedLeft) {
                startX = (int) (x - this.pressDx);
                if (startX < AndroidUtilities.dp(16.0f)) {
                    startX = AndroidUtilities.dp(16.0f);
                } else if (startX > endX) {
                    startX = endX;
                }
                this.progressLeft = ((float) (startX - AndroidUtilities.dp(16.0f))) / ((float) width);
                if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
                    this.progressRight = this.progressLeft + this.maxProgressDiff;
                } else if (this.minProgressDiff != 0.0f && this.progressRight - this.progressLeft < this.minProgressDiff) {
                    this.progressLeft = this.progressRight - this.minProgressDiff;
                    if (this.progressLeft < 0.0f) {
                        this.progressLeft = 0.0f;
                    }
                }
                if (this.delegate != null) {
                    this.delegate.onLeftProgressChanged(this.progressLeft);
                }
                invalidate();
                return true;
            } else if (!this.pressedRight) {
                return false;
            } else {
                endX = (int) (x - this.pressDx);
                if (endX < startX) {
                    endX = startX;
                } else if (endX > AndroidUtilities.dp(16.0f) + width) {
                    endX = width + AndroidUtilities.dp(16.0f);
                }
                this.progressRight = ((float) (endX - AndroidUtilities.dp(16.0f))) / ((float) width);
                if (this.progressRight - this.progressLeft > this.maxProgressDiff) {
                    this.progressLeft = this.progressRight - this.maxProgressDiff;
                } else if (this.minProgressDiff != 0.0f && this.progressRight - this.progressLeft < this.minProgressDiff) {
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
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }

    public void setVideoPath(String path) {
        destroy();
        this.mediaMetadataRetriever = new MediaMetadataRetriever();
        this.progressLeft = 0.0f;
        this.progressRight = 1.0f;
        try {
            this.mediaMetadataRetriever.setDataSource(path);
            this.videoLength = Long.parseLong(this.mediaMetadataRetriever.extractMetadata(9));
        } catch (Exception e) {
            FileLog.e(e);
        }
        invalidate();
    }

    public void setDelegate(VideoTimelineViewDelegate delegate) {
        this.delegate = delegate;
    }

    private void reloadFrames(int frameNum) {
        if (this.mediaMetadataRetriever != null) {
            if (frameNum == 0) {
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
            this.currentTask = new C28031();
            this.currentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[]{Integer.valueOf(frameNum), null, null});
        }
    }

    public void destroy() {
        synchronized (sync) {
            try {
                if (this.mediaMetadataRetriever != null) {
                    this.mediaMetadataRetriever.release();
                    this.mediaMetadataRetriever = null;
                }
            } catch (Exception e) {
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

    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth() - AndroidUtilities.dp(36.0f);
        int startX = ((int) (((float) width) * this.progressLeft)) + AndroidUtilities.dp(16.0f);
        int endX = ((int) (((float) width) * this.progressRight)) + AndroidUtilities.dp(16.0f);
        canvas.save();
        canvas.clipRect(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(20.0f) + width, getMeasuredHeight());
        if (this.frames.isEmpty() && this.currentTask == null) {
            reloadFrames(0);
        } else {
            int offset = 0;
            for (int a = 0; a < this.frames.size(); a++) {
                Bitmap bitmap = (Bitmap) this.frames.get(a);
                if (bitmap != null) {
                    int x = AndroidUtilities.dp(16.0f) + ((this.isRoundFrames ? this.frameWidth / 2 : this.frameWidth) * offset);
                    int y = AndroidUtilities.dp(2.0f);
                    if (this.isRoundFrames) {
                        this.rect2.set(x, y, AndroidUtilities.dp(28.0f) + x, AndroidUtilities.dp(28.0f) + y);
                        canvas.drawBitmap(bitmap, this.rect1, this.rect2, null);
                    } else {
                        canvas.drawBitmap(bitmap, (float) x, (float) y, null);
                    }
                }
                offset++;
            }
        }
        int top = AndroidUtilities.dp(2.0f);
        canvas.drawRect((float) AndroidUtilities.dp(16.0f), (float) top, (float) startX, (float) (getMeasuredHeight() - top), this.paint2);
        canvas.drawRect((float) (AndroidUtilities.dp(4.0f) + endX), (float) top, (float) ((AndroidUtilities.dp(16.0f) + width) + AndroidUtilities.dp(4.0f)), (float) (getMeasuredHeight() - top), this.paint2);
        canvas.drawRect((float) startX, 0.0f, (float) (AndroidUtilities.dp(2.0f) + startX), (float) getMeasuredHeight(), this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + endX), 0.0f, (float) (AndroidUtilities.dp(4.0f) + endX), (float) getMeasuredHeight(), this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + startX), 0.0f, (float) (AndroidUtilities.dp(4.0f) + endX), (float) top, this.paint);
        canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + startX), (float) (getMeasuredHeight() - top), (float) (AndroidUtilities.dp(4.0f) + endX), (float) getMeasuredHeight(), this.paint);
        canvas.restore();
        canvas.drawCircle((float) startX, (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(7.0f), this.paint);
        canvas.drawCircle((float) (AndroidUtilities.dp(4.0f) + endX), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(7.0f), this.paint);
    }
}
