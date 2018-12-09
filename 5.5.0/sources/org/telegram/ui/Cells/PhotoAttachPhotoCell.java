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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MediaController.PhotoEntry;
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
    private PhotoEntry photoEntry;
    private boolean pressed;
    private FrameLayout videoInfoContainer;
    private TextView videoTextView;

    /* renamed from: org.telegram.ui.Cells.PhotoAttachPhotoCell$1 */
    class C40131 extends AnimatorListenerAdapter {
        C40131() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(PhotoAttachPhotoCell.this.animatorSet)) {
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
        addView(this.checkFrame, LayoutHelper.createFrame(42, 42.0f, 51, 38.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.videoInfoContainer = new FrameLayout(context);
        this.videoInfoContainer.setBackgroundResource(R.drawable.phototime);
        this.videoInfoContainer.setPadding(AndroidUtilities.dp(3.0f), 0, AndroidUtilities.dp(3.0f), 0);
        addView(this.videoInfoContainer, LayoutHelper.createFrame(80, 16, 83));
        View imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_video);
        this.videoInfoContainer.addView(imageView, LayoutHelper.createFrame(-2, -2, 19));
        this.videoTextView = new TextView(context);
        this.videoTextView.setTextColor(-1);
        this.videoTextView.setTextSize(1, 12.0f);
        this.videoInfoContainer.addView(this.videoTextView, LayoutHelper.createFrame(-2, -2.0f, 19, 18.0f, -0.7f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new CheckBox(context, R.drawable.checkbig);
        this.checkBox.setSize(30);
        this.checkBox.setCheckOffset(AndroidUtilities.dp(1.0f));
        this.checkBox.setDrawBackground(true);
        this.checkBox.setColor(-12793105, -1);
        addView(this.checkBox, LayoutHelper.createFrame(30, 30.0f, 51, 46.0f, 4.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.checkBox.setVisibility(0);
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public BackupImageView getImageView() {
        return this.imageView;
    }

    public PhotoEntry getPhotoEntry() {
        return this.photoEntry;
    }

    public View getVideoInfoContainer() {
        return this.videoInfoContainer;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp((float) ((this.isLast ? 0 : 6) + 80)), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(80.0f), 1073741824));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = true;
        this.checkFrame.getHitRect(rect);
        if (motionEvent.getAction() == 0) {
            if (rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.pressed = true;
                invalidate();
            }
            z = false;
        } else {
            if (this.pressed) {
                if (motionEvent.getAction() == 1) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.pressed = false;
                    playSoundEffect(0);
                    this.delegate.onCheckClick(this);
                    invalidate();
                    z = false;
                } else if (motionEvent.getAction() == 3) {
                    this.pressed = false;
                    invalidate();
                    z = false;
                } else if (motionEvent.getAction() == 2 && !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    this.pressed = false;
                    invalidate();
                }
            }
            z = false;
        }
        return !z ? super.onTouchEvent(motionEvent) : z;
    }

    public void setChecked(int i, boolean z, boolean z2) {
        this.checkBox.setChecked(i, z, z2);
    }

    public void setDelegate(PhotoAttachPhotoCellDelegate photoAttachPhotoCellDelegate) {
        this.delegate = photoAttachPhotoCellDelegate;
    }

    public void setNum(int i) {
        this.checkBox.setNum(i);
    }

    public void setOnCheckClickLisnener(OnClickListener onClickListener) {
        this.checkFrame.setOnClickListener(onClickListener);
    }

    public void setPhotoEntry(PhotoEntry photoEntry, boolean z) {
        int i = 0;
        this.pressed = false;
        this.photoEntry = photoEntry;
        this.isLast = z;
        if (this.photoEntry.thumbPath != null) {
            this.imageView.setImage(this.photoEntry.thumbPath, null, getResources().getDrawable(R.drawable.nophotos));
        } else if (this.photoEntry.path == null) {
            this.imageView.setImageResource(R.drawable.nophotos);
        } else if (this.photoEntry.isVideo) {
            this.imageView.setOrientation(0, true);
            this.videoInfoContainer.setVisibility(0);
            int i2 = this.photoEntry.duration - ((this.photoEntry.duration / 60) * 60);
            this.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(r0), Integer.valueOf(i2)}));
            this.imageView.setImage("vthumb://" + this.photoEntry.imageId + ":" + this.photoEntry.path, null, getResources().getDrawable(R.drawable.nophotos));
        } else {
            this.videoInfoContainer.setVisibility(4);
            this.imageView.setOrientation(this.photoEntry.orientation, true);
            this.imageView.setImage("thumb://" + this.photoEntry.imageId + ":" + this.photoEntry.path, null, getResources().getDrawable(R.drawable.nophotos));
        }
        boolean isShowingImage = PhotoViewer.getInstance().isShowingImage(this.photoEntry.path);
        this.imageView.getImageReceiver().setVisible(!isShowingImage, true);
        CheckBox checkBox = this.checkBox;
        if (isShowingImage) {
            i = 4;
        }
        checkBox.setVisibility(i);
        requestLayout();
    }

    public void showCheck(boolean z) {
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
        fArr[0] = z ? 1.0f : 0.0f;
        animatorArr[0] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
        CheckBox checkBox = this.checkBox;
        String str2 = "alpha";
        float[] fArr2 = new float[1];
        if (!z) {
            f = 0.0f;
        }
        fArr2[0] = f;
        animatorArr[1] = ObjectAnimator.ofFloat(checkBox, str2, fArr2);
        animatorSet.playTogether(animatorArr);
        this.animatorSet.addListener(new C40131());
        this.animatorSet.start();
    }
}
