package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MediaController$PhotoEntry;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.PhotoViewer;

public class PhotoAttachPhotoCell extends FrameLayout {
    private static Rect rect = new Rect();
    private AnimatorSet animatorSet;
    private CheckBox checkBox;
    private FrameLayout checkFrame;
    private PhotoAttachPhotoCellDelegate delegate;
    private BackupImageView imageView;
    private boolean isLast;
    private MediaController$PhotoEntry photoEntry;
    private boolean pressed;
    private FrameLayout videoInfoContainer;
    private TextView videoTextView;

    /* renamed from: org.telegram.ui.Cells.PhotoAttachPhotoCell$1 */
    class C21751 extends AnimatorListenerAdapter {
        C21751() {
        }

        public void onAnimationEnd(Animator animation) {
            if (animation.equals(PhotoAttachPhotoCell.this.animatorSet)) {
                PhotoAttachPhotoCell.this.animatorSet = null;
            }
        }
    }

    public interface PhotoAttachPhotoCellDelegate {
        void onCheckClick(PhotoAttachPhotoCell photoAttachPhotoCell);
    }

    public PhotoAttachPhotoCell(Context context) {
        super(context);
        this.imageView = new BackupImageView(context);
        addView(this.imageView, LayoutHelper.createFrame(80, 80.0f));
        this.checkFrame = new FrameLayout(context);
        addView(this.checkFrame, LayoutHelper.createFrame(42, 42.0f, 51, 38.0f, 0.0f, 0.0f, 0.0f));
        this.videoInfoContainer = new FrameLayout(context);
        this.videoInfoContainer.setBackgroundResource(R.drawable.phototime);
        this.videoInfoContainer.setPadding(AndroidUtilities.dp(3.0f), 0, AndroidUtilities.dp(3.0f), 0);
        addView(this.videoInfoContainer, LayoutHelper.createFrame(80, 16, 83));
        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(R.drawable.ic_video);
        this.videoInfoContainer.addView(imageView1, LayoutHelper.createFrame(-2, -2, 19));
        this.videoTextView = new TextView(context);
        this.videoTextView.setTextColor(-1);
        this.videoTextView.setTextSize(1, 12.0f);
        this.videoInfoContainer.addView(this.videoTextView, LayoutHelper.createFrame(-2, -2.0f, 19, 18.0f, -0.7f, 0.0f, 0.0f));
        this.checkBox = new CheckBox(context, R.drawable.checkbig);
        this.checkBox.setSize(30);
        this.checkBox.setCheckOffset(AndroidUtilities.dp(1.0f));
        this.checkBox.setDrawBackground(true);
        this.checkBox.setColor(-12793105, -1);
        addView(this.checkBox, LayoutHelper.createFrame(30, 30.0f, 51, 46.0f, 4.0f, 0.0f, 0.0f));
        this.checkBox.setVisibility(0);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp((float) ((this.isLast ? 0 : 6) + 80)), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(80.0f), 1073741824));
    }

    public MediaController$PhotoEntry getPhotoEntry() {
        return this.photoEntry;
    }

    public BackupImageView getImageView() {
        return this.imageView;
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public View getVideoInfoContainer() {
        return this.videoInfoContainer;
    }

    public void setPhotoEntry(MediaController$PhotoEntry entry, boolean last) {
        boolean z;
        int i = 0;
        this.pressed = false;
        this.photoEntry = entry;
        this.isLast = last;
        if (this.photoEntry.thumbPath != null) {
            this.imageView.setImage(this.photoEntry.thumbPath, null, getResources().getDrawable(R.drawable.nophotos));
        } else if (this.photoEntry.path == null) {
            this.imageView.setImageResource(R.drawable.nophotos);
        } else if (this.photoEntry.isVideo) {
            this.imageView.setOrientation(0, true);
            this.videoInfoContainer.setVisibility(0);
            int seconds = this.photoEntry.duration - ((this.photoEntry.duration / 60) * 60);
            this.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}));
            this.imageView.setImage("vthumb://" + this.photoEntry.imageId + ":" + this.photoEntry.path, null, getResources().getDrawable(R.drawable.nophotos));
        } else {
            this.videoInfoContainer.setVisibility(4);
            this.imageView.setOrientation(this.photoEntry.orientation, true);
            this.imageView.setImage("thumb://" + this.photoEntry.imageId + ":" + this.photoEntry.path, null, getResources().getDrawable(R.drawable.nophotos));
        }
        boolean showing = PhotoViewer.getInstance().isShowingImage(this.photoEntry.path);
        ImageReceiver imageReceiver = this.imageView.getImageReceiver();
        if (showing) {
            z = false;
        } else {
            z = true;
        }
        imageReceiver.setVisible(z, true);
        CheckBox checkBox = this.checkBox;
        if (showing) {
            i = 4;
        }
        checkBox.setVisibility(i);
        requestLayout();
    }

    public void setChecked(int num, boolean value, boolean animated) {
        this.checkBox.setChecked(num, value, animated);
    }

    public void setNum(int num) {
        this.checkBox.setNum(num);
    }

    public void setOnCheckClickLisnener(OnClickListener onCheckClickLisnener) {
        this.checkFrame.setOnClickListener(onCheckClickLisnener);
    }

    public void setDelegate(PhotoAttachPhotoCellDelegate delegate) {
        this.delegate = delegate;
    }

    public void showCheck(boolean show) {
        float f = 1.0f;
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
            this.animatorSet = null;
        }
        this.animatorSet = new AnimatorSet();
        this.animatorSet.setInterpolator(new DecelerateInterpolator());
        this.animatorSet.setDuration(180);
        AnimatorSet animatorSet = this.animatorSet;
        Animator[] animatorArr = new Animator[2];
        FrameLayout frameLayout = this.videoInfoContainer;
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = show ? 1.0f : 0.0f;
        animatorArr[0] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        CheckBox checkBox = this.checkBox;
        String str2 = "alpha";
        float[] fArr2 = new float[1];
        if (!show) {
            f = 0.0f;
        }
        fArr2[0] = f;
        animatorArr[1] = ObjectAnimator.ofFloat(checkBox, str2, fArr2);
        animatorSet.playTogether(animatorArr);
        this.animatorSet.addListener(new C21751());
        this.animatorSet.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;
        this.checkFrame.getHitRect(rect);
        if (event.getAction() == 0) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                this.pressed = true;
                invalidate();
                result = true;
            }
        } else if (this.pressed) {
            if (event.getAction() == 1) {
                getParent().requestDisallowInterceptTouchEvent(true);
                this.pressed = false;
                playSoundEffect(0);
                this.delegate.onCheckClick(this);
                invalidate();
            } else if (event.getAction() == 3) {
                this.pressed = false;
                invalidate();
            } else if (event.getAction() == 2 && !rect.contains((int) event.getX(), (int) event.getY())) {
                this.pressed = false;
                invalidate();
            }
        }
        if (result) {
            return result;
        }
        return super.onTouchEvent(event);
    }
}
