package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.BaseCell;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;

public class PopupAudioView extends BaseCell implements FileDownloadProgressListener, SeekBarDelegate {
    private int TAG;
    private int buttonPressed = 0;
    private int buttonState = 0;
    private int buttonX;
    private int buttonY;
    protected MessageObject currentMessageObject;
    private String lastTimeString = null;
    private ProgressView progressView;
    private SeekBar seekBar;
    private int seekBarX;
    private int seekBarY;
    private StaticLayout timeLayout;
    private TextPaint timePaint = new TextPaint(1);
    int timeWidth = 0;
    private int timeX;
    private boolean wasLayout = false;

    public PopupAudioView(Context context) {
        super(context);
        this.timePaint.setTextSize((float) AndroidUtilities.dp(16.0f));
        this.TAG = MediaController.getInstance().generateObserverTag();
        this.seekBar = new SeekBar(getContext());
        this.seekBar.setDelegate(this);
        this.progressView = new ProgressView();
    }

    private void didPressedButton() {
        if (this.buttonState == 0) {
            boolean playMessage = MediaController.getInstance().playMessage(this.currentMessageObject);
            if (!this.currentMessageObject.isOut() && this.currentMessageObject.isContentUnread() && this.currentMessageObject.messageOwner.to_id.channel_id == 0) {
                MessagesController.getInstance().markMessageContentAsRead(this.currentMessageObject);
                this.currentMessageObject.setContentIsRead();
            }
            if (playMessage) {
                this.buttonState = 1;
                invalidate();
            }
        } else if (this.buttonState == 1) {
            if (MediaController.getInstance().pauseMessage(this.currentMessageObject)) {
                this.buttonState = 0;
                invalidate();
            }
        } else if (this.buttonState == 2) {
            FileLoader.getInstance().loadFile(this.currentMessageObject.getDocument(), true, 0);
            this.buttonState = 4;
            invalidate();
        } else if (this.buttonState == 3) {
            FileLoader.getInstance().cancelLoadFile(this.currentMessageObject.getDocument());
            this.buttonState = 2;
            invalidate();
        }
    }

    public void downloadAudioIfNeed() {
        if (this.buttonState == 2) {
            FileLoader.getInstance().loadFile(this.currentMessageObject.getDocument(), true, 0);
            this.buttonState = 3;
            invalidate();
        }
    }

    public final MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public int getObserverTag() {
        return this.TAG;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    protected void onDraw(Canvas canvas) {
        if (this.currentMessageObject != null) {
            if (this.wasLayout) {
                setDrawableBounds(Theme.chat_msgInMediaDrawable, 0, 0, getMeasuredWidth(), getMeasuredHeight());
                Theme.chat_msgInMediaDrawable.draw(canvas);
                if (this.currentMessageObject != null) {
                    canvas.save();
                    if (this.buttonState == 0 || this.buttonState == 1) {
                        canvas.translate((float) this.seekBarX, (float) this.seekBarY);
                        this.seekBar.draw(canvas);
                    } else {
                        canvas.translate((float) (this.seekBarX + AndroidUtilities.dp(12.0f)), (float) this.seekBarY);
                        this.progressView.draw(canvas);
                    }
                    canvas.restore();
                    int i = this.buttonState + 5;
                    this.timePaint.setColor(-6182221);
                    Drawable drawable = Theme.chat_fileStatesDrawable[i][this.buttonPressed];
                    int dp = AndroidUtilities.dp(36.0f);
                    setDrawableBounds(drawable, ((dp - drawable.getIntrinsicWidth()) / 2) + this.buttonX, ((dp - drawable.getIntrinsicHeight()) / 2) + this.buttonY);
                    drawable.draw(canvas);
                    canvas.save();
                    canvas.translate((float) this.timeX, (float) AndroidUtilities.dp(18.0f));
                    this.timeLayout.draw(canvas);
                    canvas.restore();
                    return;
                }
                return;
            }
            requestLayout();
        }
    }

    public void onFailedDownload(String str) {
        updateButtonState();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.currentMessageObject != null) {
            this.seekBarX = AndroidUtilities.dp(54.0f);
            this.buttonX = AndroidUtilities.dp(10.0f);
            this.timeX = (getMeasuredWidth() - this.timeWidth) - AndroidUtilities.dp(16.0f);
            this.seekBar.setSize((getMeasuredWidth() - AndroidUtilities.dp(70.0f)) - this.timeWidth, AndroidUtilities.dp(30.0f));
            this.progressView.width = (getMeasuredWidth() - AndroidUtilities.dp(94.0f)) - this.timeWidth;
            this.progressView.height = AndroidUtilities.dp(30.0f);
            this.seekBarY = AndroidUtilities.dp(13.0f);
            this.buttonY = AndroidUtilities.dp(10.0f);
            updateProgress();
            if (z || !this.wasLayout) {
                this.wasLayout = true;
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(56.0f));
    }

    public void onProgressDownload(String str, float f) {
        this.progressView.setProgress(f);
        if (this.buttonState != 3) {
            updateButtonState();
        }
        invalidate();
    }

    public void onProgressUpload(String str, float f, boolean z) {
    }

    public void onSeekBarDrag(float f) {
        if (this.currentMessageObject != null) {
            this.currentMessageObject.audioProgress = f;
            MediaController.getInstance().seekToProgress(this.currentMessageObject, f);
        }
    }

    public void onSuccessDownload(String str) {
        updateButtonState();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean onTouch = this.seekBar.onTouch(motionEvent.getAction(), motionEvent.getX() - ((float) this.seekBarX), motionEvent.getY() - ((float) this.seekBarY));
        if (onTouch) {
            if (motionEvent.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            invalidate();
            return onTouch;
        }
        int dp = AndroidUtilities.dp(36.0f);
        if (motionEvent.getAction() == 0) {
            if (x >= ((float) this.buttonX) && x <= ((float) (this.buttonX + dp)) && y >= ((float) this.buttonY) && y <= ((float) (this.buttonY + dp))) {
                this.buttonPressed = 1;
                invalidate();
                onTouch = true;
            }
        } else if (this.buttonPressed == 1) {
            if (motionEvent.getAction() == 1) {
                this.buttonPressed = 0;
                playSoundEffect(0);
                didPressedButton();
                invalidate();
            } else if (motionEvent.getAction() == 3) {
                this.buttonPressed = 0;
                invalidate();
            } else if (motionEvent.getAction() == 2 && (x < ((float) this.buttonX) || x > ((float) (this.buttonX + dp)) || y < ((float) this.buttonY) || y > ((float) (this.buttonY + dp)))) {
                this.buttonPressed = 0;
                invalidate();
            }
        }
        return !onTouch ? super.onTouchEvent(motionEvent) : onTouch;
    }

    public void setMessageObject(MessageObject messageObject) {
        if (this.currentMessageObject != messageObject) {
            this.seekBar.setColors(Theme.getColor(Theme.key_chat_inAudioSeekbar), Theme.getColor(Theme.key_chat_inAudioSeekbarFill), Theme.getColor(Theme.key_chat_inAudioSeekbarSelected));
            this.progressView.setProgressColors(-2497813, -7944712);
            this.currentMessageObject = messageObject;
            this.wasLayout = false;
            requestLayout();
        }
        updateButtonState();
    }

    public void updateButtonState() {
        String fileName = this.currentMessageObject.getFileName();
        if (FileLoader.getPathToMessage(this.currentMessageObject.messageOwner).exists()) {
            MediaController.getInstance().removeLoadingFileObserver(this);
            boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
            if (!isPlayingMessage || (isPlayingMessage && MediaController.getInstance().isMessagePaused())) {
                this.buttonState = 0;
            } else {
                this.buttonState = 1;
            }
            this.progressView.setProgress(BitmapDescriptorFactory.HUE_RED);
        } else {
            MediaController.getInstance().addLoadingFileObserver(fileName, this);
            if (FileLoader.getInstance().isLoadingFile(fileName)) {
                this.buttonState = 3;
                Float fileProgress = ImageLoader.getInstance().getFileProgress(fileName);
                if (fileProgress != null) {
                    this.progressView.setProgress(fileProgress.floatValue());
                } else {
                    this.progressView.setProgress(BitmapDescriptorFactory.HUE_RED);
                }
            } else {
                this.buttonState = 2;
                this.progressView.setProgress(BitmapDescriptorFactory.HUE_RED);
            }
        }
        updateProgress();
    }

    public void updateProgress() {
        if (this.currentMessageObject != null) {
            int i;
            if (!this.seekBar.isDragging()) {
                this.seekBar.setProgress(this.currentMessageObject.audioProgress);
            }
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                i = this.currentMessageObject.audioProgressSec;
            } else {
                for (int i2 = 0; i2 < this.currentMessageObject.getDocument().attributes.size(); i2++) {
                    DocumentAttribute documentAttribute = (DocumentAttribute) this.currentMessageObject.getDocument().attributes.get(i2);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                        i = documentAttribute.duration;
                        break;
                    }
                }
                i = 0;
            }
            CharSequence format = String.format("%02d:%02d", new Object[]{Integer.valueOf(i / 60), Integer.valueOf(i % 60)});
            if (this.lastTimeString == null || !(this.lastTimeString == null || this.lastTimeString.equals(format))) {
                this.timeWidth = (int) Math.ceil((double) this.timePaint.measureText(format));
                this.timeLayout = new StaticLayout(format, this.timePaint, this.timeWidth, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            }
            invalidate();
        }
    }
}
