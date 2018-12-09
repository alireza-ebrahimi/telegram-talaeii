package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class FeaturedStickerSetCell extends FrameLayout {
    private TextView addButton;
    private int angle;
    private ImageView checkImage;
    private AnimatorSet currentAnimation;
    private boolean drawProgress;
    private BackupImageView imageView;
    private boolean isInstalled;
    private long lastUpdateTime;
    private boolean needDivider;
    private float progressAlpha;
    private Paint progressPaint = new Paint(1);
    private RectF progressRect = new RectF();
    private Rect rect = new Rect();
    private StickerSetCovered stickersSet;
    private TextView textView;
    private TextView valueTextView;
    private boolean wasLayout;

    /* renamed from: org.telegram.ui.Cells.FeaturedStickerSetCell$2 */
    class C40052 extends Drawable {
        Paint paint = new Paint(1);

        C40052() {
        }

        public void draw(Canvas canvas) {
            this.paint.setColor(-12277526);
            canvas.drawCircle((float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(3.0f), this.paint);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(8.0f);
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(12.0f);
        }

        public int getOpacity() {
            return 0;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    /* renamed from: org.telegram.ui.Cells.FeaturedStickerSetCell$3 */
    class C40063 extends AnimatorListenerAdapter {
        C40063() {
        }

        public void onAnimationCancel(Animator animator) {
            if (FeaturedStickerSetCell.this.currentAnimation != null && FeaturedStickerSetCell.this.currentAnimation.equals(animator)) {
                FeaturedStickerSetCell.this.currentAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (FeaturedStickerSetCell.this.currentAnimation != null && FeaturedStickerSetCell.this.currentAnimation.equals(animator)) {
                FeaturedStickerSetCell.this.addButton.setVisibility(4);
            }
        }
    }

    /* renamed from: org.telegram.ui.Cells.FeaturedStickerSetCell$4 */
    class C40074 extends AnimatorListenerAdapter {
        C40074() {
        }

        public void onAnimationCancel(Animator animator) {
            if (FeaturedStickerSetCell.this.currentAnimation != null && FeaturedStickerSetCell.this.currentAnimation.equals(animator)) {
                FeaturedStickerSetCell.this.currentAnimation = null;
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (FeaturedStickerSetCell.this.currentAnimation != null && FeaturedStickerSetCell.this.currentAnimation.equals(animator)) {
                FeaturedStickerSetCell.this.checkImage.setVisibility(4);
            }
        }
    }

    public FeaturedStickerSetCell(Context context) {
        int i = 3;
        super(context);
        this.progressPaint.setColor(Theme.getColor(Theme.key_featuredStickers_buttonProgress));
        this.progressPaint.setStrokeCap(Cap.ROUND);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.textView, LayoutHelper.createFrame(-2, -2.0f, LocaleController.isRTL ? 5 : 3, LocaleController.isRTL ? 100.0f : 71.0f, 10.0f, LocaleController.isRTL ? 71.0f : 100.0f, BitmapDescriptorFactory.HUE_RED));
        this.valueTextView = new TextView(context);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setEllipsize(TruncateAt.END);
        this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        addView(this.valueTextView, LayoutHelper.createFrame(-2, -2.0f, LocaleController.isRTL ? 5 : 3, LocaleController.isRTL ? 100.0f : 71.0f, 35.0f, LocaleController.isRTL ? 71.0f : 100.0f, BitmapDescriptorFactory.HUE_RED));
        this.imageView = new BackupImageView(context);
        this.imageView.setAspectFit(true);
        addView(this.imageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.addButton = new TextView(context) {
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (FeaturedStickerSetCell.this.drawProgress || !(FeaturedStickerSetCell.this.drawProgress || FeaturedStickerSetCell.this.progressAlpha == BitmapDescriptorFactory.HUE_RED)) {
                    FeaturedStickerSetCell.this.progressPaint.setAlpha(Math.min(255, (int) (FeaturedStickerSetCell.this.progressAlpha * 255.0f)));
                    int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(11.0f);
                    FeaturedStickerSetCell.this.progressRect.set((float) measuredWidth, (float) AndroidUtilities.dp(3.0f), (float) (measuredWidth + AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(11.0f));
                    canvas.drawArc(FeaturedStickerSetCell.this.progressRect, (float) FeaturedStickerSetCell.this.angle, 220.0f, false, FeaturedStickerSetCell.this.progressPaint);
                    invalidate(((int) FeaturedStickerSetCell.this.progressRect.left) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetCell.this.progressRect.top) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetCell.this.progressRect.right) + AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetCell.this.progressRect.bottom) + AndroidUtilities.dp(2.0f));
                    long currentTimeMillis = System.currentTimeMillis();
                    if (Math.abs(FeaturedStickerSetCell.this.lastUpdateTime - System.currentTimeMillis()) < 1000) {
                        long access$500 = currentTimeMillis - FeaturedStickerSetCell.this.lastUpdateTime;
                        FeaturedStickerSetCell.this.angle = (int) ((((float) (360 * access$500)) / 2000.0f) + ((float) FeaturedStickerSetCell.this.angle));
                        FeaturedStickerSetCell.this.angle = FeaturedStickerSetCell.this.angle - ((FeaturedStickerSetCell.this.angle / 360) * 360);
                        if (FeaturedStickerSetCell.this.drawProgress) {
                            if (FeaturedStickerSetCell.this.progressAlpha < 1.0f) {
                                FeaturedStickerSetCell.this.progressAlpha = (((float) access$500) / 200.0f) + FeaturedStickerSetCell.this.progressAlpha;
                                if (FeaturedStickerSetCell.this.progressAlpha > 1.0f) {
                                    FeaturedStickerSetCell.this.progressAlpha = 1.0f;
                                }
                            }
                        } else if (FeaturedStickerSetCell.this.progressAlpha > BitmapDescriptorFactory.HUE_RED) {
                            FeaturedStickerSetCell.this.progressAlpha = FeaturedStickerSetCell.this.progressAlpha - (((float) access$500) / 200.0f);
                            if (FeaturedStickerSetCell.this.progressAlpha < BitmapDescriptorFactory.HUE_RED) {
                                FeaturedStickerSetCell.this.progressAlpha = BitmapDescriptorFactory.HUE_RED;
                            }
                        }
                    }
                    FeaturedStickerSetCell.this.lastUpdateTime = currentTimeMillis;
                    invalidate();
                }
            }
        };
        this.addButton.setGravity(17);
        this.addButton.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        this.addButton.setTextSize(1, 14.0f);
        this.addButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.addButton.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
        this.addButton.setText(LocaleController.getString("Add", R.string.Add).toUpperCase());
        this.addButton.setPadding(AndroidUtilities.dp(17.0f), 0, AndroidUtilities.dp(17.0f), 0);
        View view = this.addButton;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(-2, 28.0f, i | 48, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, 18.0f, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, BitmapDescriptorFactory.HUE_RED));
        this.checkImage = new ImageView(context);
        this.checkImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
        this.checkImage.setImageResource(R.drawable.sticker_added);
        addView(this.checkImage, LayoutHelper.createFrame(19, 14.0f));
    }

    public StickerSetCovered getStickerSet() {
        return this.stickersSet;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.wasLayout = false;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int left = (this.addButton.getLeft() + (this.addButton.getMeasuredWidth() / 2)) - (this.checkImage.getMeasuredWidth() / 2);
        int top = (this.addButton.getTop() + (this.addButton.getMeasuredHeight() / 2)) - (this.checkImage.getMeasuredHeight() / 2);
        this.checkImage.layout(left, top, this.checkImage.getMeasuredWidth() + left, this.checkImage.getMeasuredHeight() + top);
        this.wasLayout = true;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(64.0f), 1073741824));
    }

    public void setAddOnClickListener(OnClickListener onClickListener) {
        this.addButton.setOnClickListener(onClickListener);
    }

    public void setDrawProgress(boolean z) {
        this.drawProgress = z;
        this.lastUpdateTime = System.currentTimeMillis();
        this.addButton.invalidate();
    }

    public void setStickersSet(StickerSetCovered stickerSetCovered, boolean z, boolean z2) {
        boolean z3 = stickerSetCovered == this.stickersSet && this.wasLayout;
        this.needDivider = z;
        this.stickersSet = stickerSetCovered;
        this.lastUpdateTime = System.currentTimeMillis();
        setWillNotDraw(!this.needDivider);
        if (this.currentAnimation != null) {
            this.currentAnimation.cancel();
            this.currentAnimation = null;
        }
        this.textView.setText(this.stickersSet.set.title);
        if (z2) {
            Drawable c40052 = new C40052();
            TextView textView = this.textView;
            Drawable drawable = LocaleController.isRTL ? null : c40052;
            if (!LocaleController.isRTL) {
                c40052 = null;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, c40052, null);
        } else {
            this.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        this.valueTextView.setText(LocaleController.formatPluralString("Stickers", stickerSetCovered.set.count));
        if (stickerSetCovered.cover != null && stickerSetCovered.cover.thumb != null && stickerSetCovered.cover.thumb.location != null) {
            this.imageView.setImage(stickerSetCovered.cover.thumb.location, null, "webp", null);
        } else if (!stickerSetCovered.covers.isEmpty()) {
            this.imageView.setImage(((Document) stickerSetCovered.covers.get(0)).thumb.location, null, "webp", null);
        }
        boolean z4;
        if (z3) {
            z4 = this.isInstalled;
            z3 = StickersQuery.isStickerPackInstalled(stickerSetCovered.set.id);
            this.isInstalled = z3;
            if (z3) {
                if (!z4) {
                    this.checkImage.setVisibility(0);
                    this.addButton.setClickable(false);
                    this.currentAnimation = new AnimatorSet();
                    this.currentAnimation.setDuration(200);
                    this.currentAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.addButton, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}), ObjectAnimator.ofFloat(this.addButton, "scaleX", new float[]{1.0f, 0.01f}), ObjectAnimator.ofFloat(this.addButton, "scaleY", new float[]{1.0f, 0.01f}), ObjectAnimator.ofFloat(this.checkImage, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.checkImage, "scaleX", new float[]{0.01f, 1.0f}), ObjectAnimator.ofFloat(this.checkImage, "scaleY", new float[]{0.01f, 1.0f})});
                    this.currentAnimation.addListener(new C40063());
                    this.currentAnimation.start();
                    return;
                }
                return;
            } else if (z4) {
                this.addButton.setVisibility(0);
                this.addButton.setClickable(true);
                this.currentAnimation = new AnimatorSet();
                this.currentAnimation.setDuration(200);
                this.currentAnimation.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.checkImage, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}), ObjectAnimator.ofFloat(this.checkImage, "scaleX", new float[]{1.0f, 0.01f}), ObjectAnimator.ofFloat(this.checkImage, "scaleY", new float[]{1.0f, 0.01f}), ObjectAnimator.ofFloat(this.addButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.addButton, "scaleX", new float[]{0.01f, 1.0f}), ObjectAnimator.ofFloat(this.addButton, "scaleY", new float[]{0.01f, 1.0f})});
                this.currentAnimation.addListener(new C40074());
                this.currentAnimation.start();
                return;
            } else {
                return;
            }
        }
        z4 = StickersQuery.isStickerPackInstalled(stickerSetCovered.set.id);
        this.isInstalled = z4;
        if (z4) {
            this.addButton.setVisibility(4);
            this.addButton.setClickable(false);
            this.checkImage.setVisibility(0);
            this.checkImage.setScaleX(1.0f);
            this.checkImage.setScaleY(1.0f);
            this.checkImage.setAlpha(1.0f);
            return;
        }
        this.addButton.setVisibility(0);
        this.addButton.setClickable(true);
        this.checkImage.setVisibility(4);
        this.addButton.setScaleX(1.0f);
        this.addButton.setScaleY(1.0f);
        this.addButton.setAlpha(1.0f);
    }
}
