package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;

public class PhotoPickerPhotoCell extends FrameLayout {
    private AnimatorSet animator;
    private AnimatorSet animatorSet;
    public CheckBox checkBox;
    public FrameLayout checkFrame;
    public int itemWidth;
    public BackupImageView photoImage;
    public FrameLayout videoInfoContainer;
    public TextView videoTextView;
    private boolean zoomOnSelect;

    /* renamed from: org.telegram.ui.Cells.PhotoPickerPhotoCell$1 */
    class C40201 extends AnimatorListenerAdapter {
        C40201() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(PhotoPickerPhotoCell.this.animatorSet)) {
                PhotoPickerPhotoCell.this.animatorSet = null;
            }
        }
    }

    public PhotoPickerPhotoCell(Context context, boolean z) {
        super(context);
        this.zoomOnSelect = z;
        this.photoImage = new BackupImageView(context);
        addView(this.photoImage, LayoutHelper.createFrame(-1, -1.0f));
        this.checkFrame = new FrameLayout(context);
        addView(this.checkFrame, LayoutHelper.createFrame(42, 42, 53));
        this.videoInfoContainer = new FrameLayout(context);
        this.videoInfoContainer.setBackgroundResource(R.drawable.phototime);
        this.videoInfoContainer.setPadding(AndroidUtilities.dp(3.0f), 0, AndroidUtilities.dp(3.0f), 0);
        addView(this.videoInfoContainer, LayoutHelper.createFrame(-1, 16, 83));
        View imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_video);
        this.videoInfoContainer.addView(imageView, LayoutHelper.createFrame(-2, -2, 19));
        this.videoTextView = new TextView(context);
        this.videoTextView.setTextColor(-1);
        this.videoTextView.setTextSize(1, 12.0f);
        this.videoInfoContainer.addView(this.videoTextView, LayoutHelper.createFrame(-2, -2.0f, 19, 18.0f, -0.7f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new CheckBox(context, R.drawable.checkbig);
        this.checkBox.setSize(z ? 30 : 26);
        this.checkBox.setCheckOffset(AndroidUtilities.dp(1.0f));
        this.checkBox.setDrawBackground(true);
        this.checkBox.setColor(-10043398, -1);
        addView(this.checkBox, LayoutHelper.createFrame(z ? 30 : 26, z ? 30.0f : 26.0f, 53, BitmapDescriptorFactory.HUE_RED, 4.0f, 4.0f, BitmapDescriptorFactory.HUE_RED));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(this.itemWidth, 1073741824), MeasureSpec.makeMeasureSpec(this.itemWidth, 1073741824));
    }

    public void setChecked(int i, final boolean z, boolean z2) {
        int i2 = -16119286;
        float f = 0.85f;
        this.checkBox.setChecked(i, z, z2);
        if (this.animator != null) {
            this.animator.cancel();
            this.animator = null;
        }
        if (!this.zoomOnSelect) {
            return;
        }
        if (z2) {
            if (z) {
                setBackgroundColor(-16119286);
            }
            this.animator = new AnimatorSet();
            AnimatorSet animatorSet = this.animator;
            Animator[] animatorArr = new Animator[2];
            BackupImageView backupImageView = this.photoImage;
            String str = "scaleX";
            float[] fArr = new float[1];
            fArr[0] = z ? 0.85f : 1.0f;
            animatorArr[0] = ObjectAnimator.ofFloat(backupImageView, str, fArr);
            BackupImageView backupImageView2 = this.photoImage;
            String str2 = "scaleY";
            float[] fArr2 = new float[1];
            if (!z) {
                f = 1.0f;
            }
            fArr2[0] = f;
            animatorArr[1] = ObjectAnimator.ofFloat(backupImageView2, str2, fArr2);
            animatorSet.playTogether(animatorArr);
            this.animator.setDuration(200);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (PhotoPickerPhotoCell.this.animator != null && PhotoPickerPhotoCell.this.animator.equals(animator)) {
                        PhotoPickerPhotoCell.this.animator = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (PhotoPickerPhotoCell.this.animator != null && PhotoPickerPhotoCell.this.animator.equals(animator)) {
                        PhotoPickerPhotoCell.this.animator = null;
                        if (!z) {
                            PhotoPickerPhotoCell.this.setBackgroundColor(0);
                        }
                    }
                }
            });
            this.animator.start();
            return;
        }
        if (!z) {
            i2 = 0;
        }
        setBackgroundColor(i2);
        this.photoImage.setScaleX(z ? 0.85f : 1.0f);
        backupImageView2 = this.photoImage;
        if (!z) {
            f = 1.0f;
        }
        backupImageView2.setScaleY(f);
    }

    public void setNum(int i) {
        this.checkBox.setNum(i);
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
        this.animatorSet.addListener(new C40201());
        this.animatorSet.start();
    }
}
