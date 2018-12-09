package org.telegram.ui.Cells;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.RadialProgress;

public class AudioPlayerCell extends View implements FileDownloadProgressListener {
    private int TAG = MediaController.getInstance().generateObserverTag();
    private boolean buttonPressed;
    private int buttonState;
    private MessageObject currentMessageObject;
    private StaticLayout descriptionLayout;
    private int descriptionY = AndroidUtilities.dp(29.0f);
    private RadialProgress radialProgress = new RadialProgress(this);
    private StaticLayout titleLayout;
    private int titleY = AndroidUtilities.dp(9.0f);

    public AudioPlayerCell(Context context) {
        super(context);
    }

    private Drawable getDrawableForCurrentState() {
        int i = 0;
        if (this.buttonState == -1) {
            return null;
        }
        this.radialProgress.setAlphaForPrevious(false);
        Drawable[] drawableArr = Theme.chat_fileStatesDrawable[this.buttonState + 5];
        if (this.buttonPressed) {
            i = 1;
        }
        return drawableArr[i];
    }

    public void didPressedButton() {
        if (this.buttonState == 0) {
            if (MediaController.getInstance().findMessageInPlaylistAndPlay(this.currentMessageObject)) {
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        } else if (this.buttonState == 1) {
            if (MediaController.getInstance().pauseMessage(this.currentMessageObject)) {
                this.buttonState = 0;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        } else if (this.buttonState == 2) {
            this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
            FileLoader.getInstance().loadFile(this.currentMessageObject.getDocument(), true, 0);
            this.buttonState = 4;
            this.radialProgress.setBackground(getDrawableForCurrentState(), true, false);
            invalidate();
        } else if (this.buttonState == 4) {
            FileLoader.getInstance().cancelLoadFile(this.currentMessageObject.getDocument());
            this.buttonState = 2;
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
            invalidate();
        }
    }

    public MessageObject getMessageObject() {
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
        float f = 8.0f;
        if (this.titleLayout != null) {
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.titleY);
            this.titleLayout.draw(canvas);
            canvas.restore();
        }
        if (this.descriptionLayout != null) {
            Theme.chat_contextResult_descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
            canvas.save();
            if (!LocaleController.isRTL) {
                f = (float) AndroidUtilities.leftBaseline;
            }
            canvas.translate((float) AndroidUtilities.dp(f), (float) this.descriptionY);
            this.descriptionLayout.draw(canvas);
            canvas.restore();
        }
        this.radialProgress.setProgressColor(Theme.getColor(this.buttonPressed ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress));
        this.radialProgress.draw(canvas);
    }

    public void onFailedDownload(String str) {
        updateButtonState(false);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        this.descriptionLayout = null;
        this.titleLayout = null;
        int size = (MeasureSpec.getSize(i) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - AndroidUtilities.dp(28.0f);
        try {
            String musicTitle = this.currentMessageObject.getMusicTitle();
            this.titleLayout = new StaticLayout(TextUtils.ellipsize(musicTitle.replace('\n', ' '), Theme.chat_contextResult_titleTextPaint, (float) Math.min((int) Math.ceil((double) Theme.chat_contextResult_titleTextPaint.measureText(musicTitle)), size), TruncateAt.END), Theme.chat_contextResult_titleTextPaint, AndroidUtilities.dp(4.0f) + size, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e) {
            FileLog.e(e);
        }
        try {
            musicTitle = this.currentMessageObject.getMusicAuthor();
            this.descriptionLayout = new StaticLayout(TextUtils.ellipsize(musicTitle.replace('\n', ' '), Theme.chat_contextResult_descriptionTextPaint, (float) Math.min((int) Math.ceil((double) Theme.chat_contextResult_descriptionTextPaint.measureText(musicTitle)), size), TruncateAt.END), Theme.chat_contextResult_descriptionTextPaint, AndroidUtilities.dp(4.0f) + size, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(56.0f));
        int size2 = LocaleController.isRTL ? (MeasureSpec.getSize(i) - AndroidUtilities.dp(8.0f)) - AndroidUtilities.dp(52.0f) : AndroidUtilities.dp(8.0f);
        this.radialProgress.setProgressRect(AndroidUtilities.dp(4.0f) + size2, AndroidUtilities.dp(6.0f), size2 + AndroidUtilities.dp(48.0f), AndroidUtilities.dp(50.0f));
    }

    public void onProgressDownload(String str, float f) {
        this.radialProgress.setProgress(f, true);
        if (this.buttonState != 4) {
            updateButtonState(false);
        }
    }

    public void onProgressUpload(String str, float f, boolean z) {
    }

    public void onSuccessDownload(String str) {
        this.radialProgress.setProgress(1.0f, true);
        updateButtonState(true);
    }

    public void setMessageObject(MessageObject messageObject) {
        this.currentMessageObject = messageObject;
        requestLayout();
        updateButtonState(false);
    }

    public void updateButtonState(boolean z) {
        File file;
        String fileName = this.currentMessageObject.getFileName();
        if (TextUtils.isEmpty(this.currentMessageObject.messageOwner.attachPath)) {
            file = null;
        } else {
            file = new File(this.currentMessageObject.messageOwner.attachPath);
            if (!file.exists()) {
                file = null;
            }
        }
        if (file == null) {
            file = FileLoader.getPathToAttach(this.currentMessageObject.getDocument());
        }
        if (TextUtils.isEmpty(fileName)) {
            this.radialProgress.setBackground(null, false, false);
            return;
        }
        if (file.exists() && file.length() == 0) {
            file.delete();
        }
        if (file.exists()) {
            MediaController.getInstance().removeLoadingFileObserver(this);
            boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
            if (!isPlayingMessage || (isPlayingMessage && MediaController.getInstance().isMessagePaused())) {
                this.buttonState = 0;
            } else {
                this.buttonState = 1;
            }
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            invalidate();
            return;
        }
        MediaController.getInstance().addLoadingFileObserver(fileName, this);
        if (FileLoader.getInstance().isLoadingFile(fileName)) {
            this.buttonState = 4;
            Float fileProgress = ImageLoader.getInstance().getFileProgress(fileName);
            if (fileProgress != null) {
                this.radialProgress.setProgress(fileProgress.floatValue(), z);
            } else {
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
            }
            this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
        } else {
            this.buttonState = 2;
            this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
        }
        invalidate();
    }
}
