package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.PhotoViewer;

public class SharedPhotoVideoCell extends FrameLayout {
    private SharedPhotoVideoCellDelegate delegate;
    private int[] indeces = new int[6];
    private boolean isFirst;
    private int itemsCount;
    private MessageObject[] messageObjects = new MessageObject[6];
    private PhotoVideoView[] photoVideoViews = new PhotoVideoView[6];

    /* renamed from: org.telegram.ui.Cells.SharedPhotoVideoCell$1 */
    class C21881 implements OnClickListener {
        C21881() {
        }

        public void onClick(View v) {
            if (SharedPhotoVideoCell.this.delegate != null) {
                int a = ((Integer) v.getTag()).intValue();
                SharedPhotoVideoCell.this.delegate.didClickItem(SharedPhotoVideoCell.this, SharedPhotoVideoCell.this.indeces[a], SharedPhotoVideoCell.this.messageObjects[a], a);
            }
        }
    }

    /* renamed from: org.telegram.ui.Cells.SharedPhotoVideoCell$2 */
    class C21892 implements OnLongClickListener {
        C21892() {
        }

        public boolean onLongClick(View v) {
            if (SharedPhotoVideoCell.this.delegate == null) {
                return false;
            }
            int a = ((Integer) v.getTag()).intValue();
            return SharedPhotoVideoCell.this.delegate.didLongClickItem(SharedPhotoVideoCell.this, SharedPhotoVideoCell.this.indeces[a], SharedPhotoVideoCell.this.messageObjects[a], a);
        }
    }

    private class PhotoVideoView extends FrameLayout {
        private AnimatorSet animator;
        private CheckBox checkBox;
        private FrameLayout container;
        private BackupImageView imageView;
        private View selector;
        private FrameLayout videoInfoContainer;
        private TextView videoTextView;

        public PhotoVideoView(Context context) {
            super(context);
            this.container = new FrameLayout(context);
            addView(this.container, LayoutHelper.createFrame(-1, -1.0f));
            this.imageView = new BackupImageView(context);
            this.imageView.getImageReceiver().setNeedsQualityThumb(true);
            this.imageView.getImageReceiver().setShouldGenerateQualityThumb(true);
            this.container.addView(this.imageView, LayoutHelper.createFrame(-1, -1.0f));
            this.videoInfoContainer = new FrameLayout(context);
            this.videoInfoContainer.setBackgroundResource(R.drawable.phototime);
            this.videoInfoContainer.setPadding(AndroidUtilities.dp(3.0f), 0, AndroidUtilities.dp(3.0f), 0);
            this.container.addView(this.videoInfoContainer, LayoutHelper.createFrame(-1, 16, 83));
            ImageView imageView1 = new ImageView(context);
            imageView1.setImageResource(R.drawable.ic_video);
            this.videoInfoContainer.addView(imageView1, LayoutHelper.createFrame(-2, -2, 19));
            this.videoTextView = new TextView(context);
            this.videoTextView.setTextColor(-1);
            this.videoTextView.setTextSize(1, 12.0f);
            this.videoInfoContainer.addView(this.videoTextView, LayoutHelper.createFrame(-2, -2.0f, 19, 18.0f, -0.7f, 0.0f, 0.0f));
            this.selector = new View(context);
            this.selector.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            addView(this.selector, LayoutHelper.createFrame(-1, -1.0f));
            this.checkBox = new CheckBox(context, R.drawable.round_check2);
            this.checkBox.setVisibility(4);
            this.checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
            addView(this.checkBox, LayoutHelper.createFrame(22, 22.0f, 53, 0.0f, 2.0f, 2.0f, 0.0f));
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (VERSION.SDK_INT >= 21) {
                this.selector.drawableHotspotChanged(event.getX(), event.getY());
            }
            return super.onTouchEvent(event);
        }

        public void setChecked(final boolean checked, boolean animated) {
            int i = -657931;
            float f = 0.85f;
            if (this.checkBox.getVisibility() != 0) {
                this.checkBox.setVisibility(0);
            }
            this.checkBox.setChecked(checked, animated);
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
            if (animated) {
                if (checked) {
                    setBackgroundColor(-657931);
                }
                this.animator = new AnimatorSet();
                AnimatorSet animatorSet = this.animator;
                Animator[] animatorArr = new Animator[2];
                FrameLayout frameLayout = this.container;
                String str = "scaleX";
                float[] fArr = new float[1];
                fArr[0] = checked ? 0.85f : 1.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(frameLayout, str, fArr);
                FrameLayout frameLayout2 = this.container;
                String str2 = "scaleY";
                float[] fArr2 = new float[1];
                if (!checked) {
                    f = 1.0f;
                }
                fArr2[0] = f;
                animatorArr[1] = ObjectAnimator.ofFloat(frameLayout2, str2, fArr2);
                animatorSet.playTogether(animatorArr);
                this.animator.setDuration(200);
                this.animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (PhotoVideoView.this.animator != null && PhotoVideoView.this.animator.equals(animation)) {
                            PhotoVideoView.this.animator = null;
                            if (!checked) {
                                PhotoVideoView.this.setBackgroundColor(0);
                            }
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (PhotoVideoView.this.animator != null && PhotoVideoView.this.animator.equals(animation)) {
                            PhotoVideoView.this.animator = null;
                        }
                    }
                });
                this.animator.start();
                return;
            }
            float f2;
            if (!checked) {
                i = 0;
            }
            setBackgroundColor(i);
            FrameLayout frameLayout3 = this.container;
            if (checked) {
                f2 = 0.85f;
            } else {
                f2 = 1.0f;
            }
            frameLayout3.setScaleX(f2);
            frameLayout2 = this.container;
            if (!checked) {
                f = 1.0f;
            }
            frameLayout2.setScaleY(f);
        }

        public void clearAnimation() {
            super.clearAnimation();
            if (this.animator != null) {
                this.animator.cancel();
                this.animator = null;
            }
        }
    }

    public interface SharedPhotoVideoCellDelegate {
        void didClickItem(SharedPhotoVideoCell sharedPhotoVideoCell, int i, MessageObject messageObject, int i2);

        boolean didLongClickItem(SharedPhotoVideoCell sharedPhotoVideoCell, int i, MessageObject messageObject, int i2);
    }

    public SharedPhotoVideoCell(Context context) {
        super(context);
        for (int a = 0; a < 6; a++) {
            this.photoVideoViews[a] = new PhotoVideoView(context);
            addView(this.photoVideoViews[a]);
            this.photoVideoViews[a].setVisibility(4);
            this.photoVideoViews[a].setTag(Integer.valueOf(a));
            this.photoVideoViews[a].setOnClickListener(new C21881());
            this.photoVideoViews[a].setOnLongClickListener(new C21892());
        }
    }

    public void updateCheckboxColor() {
        for (int a = 0; a < 6; a++) {
            this.photoVideoViews[a].checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
        }
    }

    public void setDelegate(SharedPhotoVideoCellDelegate delegate) {
        this.delegate = delegate;
    }

    public void setItemsCount(int count) {
        int a = 0;
        while (a < this.photoVideoViews.length) {
            this.photoVideoViews[a].clearAnimation();
            this.photoVideoViews[a].setVisibility(a < count ? 0 : 4);
            a++;
        }
        this.itemsCount = count;
    }

    public BackupImageView getImageView(int a) {
        if (a >= this.itemsCount) {
            return null;
        }
        return this.photoVideoViews[a].imageView;
    }

    public MessageObject getMessageObject(int a) {
        if (a >= this.itemsCount) {
            return null;
        }
        return this.messageObjects[a];
    }

    public void setIsFirst(boolean first) {
        this.isFirst = first;
    }

    public void setChecked(int a, boolean checked, boolean animated) {
        this.photoVideoViews[a].setChecked(checked, animated);
    }

    public void setItem(int a, int index, MessageObject messageObject) {
        this.messageObjects[a] = messageObject;
        this.indeces[a] = index;
        if (messageObject != null) {
            this.photoVideoViews[a].setVisibility(0);
            PhotoVideoView photoVideoView = this.photoVideoViews[a];
            photoVideoView.imageView.getImageReceiver().setParentMessageObject(messageObject);
            photoVideoView.imageView.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(messageObject), false);
            if (messageObject.isVideo()) {
                photoVideoView.videoInfoContainer.setVisibility(0);
                int duration = 0;
                for (int b = 0; b < messageObject.getDocument().attributes.size(); b++) {
                    DocumentAttribute attribute = (DocumentAttribute) messageObject.getDocument().attributes.get(b);
                    if (attribute instanceof TLRPC$TL_documentAttributeVideo) {
                        duration = attribute.duration;
                        break;
                    }
                }
                int seconds = duration - ((duration / 60) * 60);
                photoVideoView.videoTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(duration / 60), Integer.valueOf(seconds)}));
                if (messageObject.getDocument().thumb != null) {
                    photoVideoView.imageView.setImage(null, null, null, ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.photo_placeholder_in), null, messageObject.getDocument().thumb.location, "b", null, 0);
                    return;
                } else {
                    photoVideoView.imageView.setImageResource(R.drawable.photo_placeholder_in);
                    return;
                }
            } else if (!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || messageObject.messageOwner.media.photo == null || messageObject.photoThumbs.isEmpty()) {
                photoVideoView.videoInfoContainer.setVisibility(4);
                photoVideoView.imageView.setImageResource(R.drawable.photo_placeholder_in);
                return;
            } else {
                photoVideoView.videoInfoContainer.setVisibility(4);
                photoVideoView.imageView.setImage(null, null, null, ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.photo_placeholder_in), null, FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 80).location, "b", null, 0);
                return;
            }
        }
        this.photoVideoViews[a].clearAnimation();
        this.photoVideoViews[a].setVisibility(4);
        this.messageObjects[a] = null;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int itemWidth;
        int i = 0;
        if (AndroidUtilities.isTablet()) {
            itemWidth = (AndroidUtilities.dp(490.0f) - ((this.itemsCount + 1) * AndroidUtilities.dp(4.0f))) / this.itemsCount;
        } else {
            itemWidth = (AndroidUtilities.displaySize.x - ((this.itemsCount + 1) * AndroidUtilities.dp(4.0f))) / this.itemsCount;
        }
        for (int a = 0; a < this.itemsCount; a++) {
            LayoutParams layoutParams = (LayoutParams) this.photoVideoViews[a].getLayoutParams();
            layoutParams.topMargin = this.isFirst ? 0 : AndroidUtilities.dp(4.0f);
            layoutParams.leftMargin = ((AndroidUtilities.dp(4.0f) + itemWidth) * a) + AndroidUtilities.dp(4.0f);
            layoutParams.width = itemWidth;
            layoutParams.height = itemWidth;
            layoutParams.gravity = 51;
            this.photoVideoViews[a].setLayoutParams(layoutParams);
        }
        if (!this.isFirst) {
            i = AndroidUtilities.dp(4.0f);
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(i + itemWidth, 1073741824));
    }
}
