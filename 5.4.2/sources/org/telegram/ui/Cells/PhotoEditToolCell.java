package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PhotoEditorSeekBar;
import org.telegram.ui.Components.PhotoEditorSeekBar.PhotoEditorSeekBarDelegate;

public class PhotoEditToolCell extends FrameLayout {
    private Runnable hideValueRunnable = new C40161();
    private TextView nameTextView;
    private PhotoEditorSeekBar seekBar;
    private AnimatorSet valueAnimation;
    private TextView valueTextView;

    /* renamed from: org.telegram.ui.Cells.PhotoEditToolCell$1 */
    class C40161 implements Runnable {

        /* renamed from: org.telegram.ui.Cells.PhotoEditToolCell$1$1 */
        class C40151 extends AnimatorListenerAdapter {
            C40151() {
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoEditToolCell.this.valueAnimation)) {
                    PhotoEditToolCell.this.valueAnimation = null;
                }
            }
        }

        C40161() {
        }

        public void run() {
            PhotoEditToolCell.this.valueTextView.setTag(null);
            PhotoEditToolCell.this.valueAnimation = new AnimatorSet();
            AnimatorSet access$100 = PhotoEditToolCell.this.valueAnimation;
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(PhotoEditToolCell.this.valueTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[1] = ObjectAnimator.ofFloat(PhotoEditToolCell.this.nameTextView, "alpha", new float[]{1.0f});
            access$100.playTogether(r1);
            PhotoEditToolCell.this.valueAnimation.setDuration(180);
            PhotoEditToolCell.this.valueAnimation.setInterpolator(new DecelerateInterpolator());
            PhotoEditToolCell.this.valueAnimation.addListener(new C40151());
            PhotoEditToolCell.this.valueAnimation.start();
        }
    }

    public PhotoEditToolCell(Context context) {
        super(context);
        this.nameTextView = new TextView(context);
        this.nameTextView.setGravity(5);
        this.nameTextView.setTextColor(-1);
        this.nameTextView.setTextSize(1, 12.0f);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        addView(this.nameTextView, LayoutHelper.createFrame(80, -2.0f, 19, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(-9649153);
        this.valueTextView.setTextSize(1, 12.0f);
        this.valueTextView.setGravity(5);
        this.valueTextView.setSingleLine(true);
        addView(this.valueTextView, LayoutHelper.createFrame(80, -2.0f, 19, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.seekBar = new PhotoEditorSeekBar(context);
        addView(this.seekBar, LayoutHelper.createFrame(-1, 40.0f, 19, 96.0f, BitmapDescriptorFactory.HUE_RED, 24.0f, BitmapDescriptorFactory.HUE_RED));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(40.0f), 1073741824));
    }

    public void setIconAndTextAndValue(String str, float f, int i, int i2) {
        if (this.valueAnimation != null) {
            this.valueAnimation.cancel();
            this.valueAnimation = null;
        }
        AndroidUtilities.cancelRunOnUIThread(this.hideValueRunnable);
        this.valueTextView.setTag(null);
        this.nameTextView.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
        if (f > BitmapDescriptorFactory.HUE_RED) {
            this.valueTextView.setText("+" + ((int) f));
        } else {
            this.valueTextView.setText(TtmlNode.ANONYMOUS_REGION_ID + ((int) f));
        }
        this.valueTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.nameTextView.setAlpha(1.0f);
        this.seekBar.setMinMax(i, i2);
        this.seekBar.setProgress((int) f, false);
    }

    public void setSeekBarDelegate(final PhotoEditorSeekBarDelegate photoEditorSeekBarDelegate) {
        this.seekBar.setDelegate(new PhotoEditorSeekBarDelegate() {

            /* renamed from: org.telegram.ui.Cells.PhotoEditToolCell$2$1 */
            class C40171 extends AnimatorListenerAdapter {
                C40171() {
                }

                public void onAnimationEnd(Animator animator) {
                    AndroidUtilities.runOnUIThread(PhotoEditToolCell.this.hideValueRunnable, 1000);
                }
            }

            public void onProgressChanged(int i, int i2) {
                photoEditorSeekBarDelegate.onProgressChanged(i, i2);
                if (i2 > 0) {
                    PhotoEditToolCell.this.valueTextView.setText("+" + i2);
                } else {
                    PhotoEditToolCell.this.valueTextView.setText(TtmlNode.ANONYMOUS_REGION_ID + i2);
                }
                if (PhotoEditToolCell.this.valueTextView.getTag() == null) {
                    if (PhotoEditToolCell.this.valueAnimation != null) {
                        PhotoEditToolCell.this.valueAnimation.cancel();
                    }
                    PhotoEditToolCell.this.valueTextView.setTag(Integer.valueOf(1));
                    PhotoEditToolCell.this.valueAnimation = new AnimatorSet();
                    AnimatorSet access$100 = PhotoEditToolCell.this.valueAnimation;
                    r1 = new Animator[2];
                    r1[0] = ObjectAnimator.ofFloat(PhotoEditToolCell.this.valueTextView, "alpha", new float[]{1.0f});
                    r1[1] = ObjectAnimator.ofFloat(PhotoEditToolCell.this.nameTextView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    access$100.playTogether(r1);
                    PhotoEditToolCell.this.valueAnimation.setDuration(180);
                    PhotoEditToolCell.this.valueAnimation.setInterpolator(new DecelerateInterpolator());
                    PhotoEditToolCell.this.valueAnimation.addListener(new C40171());
                    PhotoEditToolCell.this.valueAnimation.start();
                    return;
                }
                AndroidUtilities.cancelRunOnUIThread(PhotoEditToolCell.this.hideValueRunnable);
                AndroidUtilities.runOnUIThread(PhotoEditToolCell.this.hideValueRunnable, 1000);
            }
        });
    }

    public void setTag(Object obj) {
        super.setTag(obj);
        this.seekBar.setTag(obj);
    }
}
