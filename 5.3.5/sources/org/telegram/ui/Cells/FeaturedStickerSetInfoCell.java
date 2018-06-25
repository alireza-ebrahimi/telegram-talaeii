package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class FeaturedStickerSetInfoCell extends FrameLayout {
    private TextView addButton;
    private Drawable addDrawable = Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
    private int angle;
    private Paint botProgressPaint = new Paint(1);
    private Drawable delDrawable = Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_featuredStickers_delButton), Theme.getColor(Theme.key_featuredStickers_delButtonPressed));
    private boolean drawProgress;
    Drawable drawable = new C21701();
    private boolean hasOnClick;
    private TextView infoTextView;
    private boolean isInstalled;
    private long lastUpdateTime;
    private TextView nameTextView;
    private float progressAlpha;
    private RectF rect = new RectF();
    private TLRPC$StickerSetCovered set;

    /* renamed from: org.telegram.ui.Cells.FeaturedStickerSetInfoCell$1 */
    class C21701 extends Drawable {
        Paint paint = new Paint(1);

        C21701() {
        }

        public void draw(Canvas canvas) {
            this.paint.setColor(Theme.getColor(Theme.key_featuredStickers_unread));
            canvas.drawCircle((float) AndroidUtilities.dp(8.0f), 0.0f, (float) AndroidUtilities.dp(4.0f), this.paint);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 0;
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(12.0f);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(26.0f);
        }
    }

    public FeaturedStickerSetInfoCell(Context context, int left) {
        super(context);
        this.botProgressPaint.setColor(Theme.getColor(Theme.key_featuredStickers_buttonProgress));
        this.botProgressPaint.setStrokeCap(Cap.ROUND);
        this.botProgressPaint.setStyle(Style.STROKE);
        this.botProgressPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelTrendingTitle));
        this.nameTextView.setTextSize(1, 17.0f);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setSingleLine(true);
        addView(this.nameTextView, LayoutHelper.createFrame(-2, -1.0f, 51, (float) left, 8.0f, 100.0f, 0.0f));
        this.infoTextView = new TextView(context);
        this.infoTextView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelTrendingDescription));
        this.infoTextView.setTextSize(1, 13.0f);
        this.infoTextView.setEllipsize(TruncateAt.END);
        this.infoTextView.setSingleLine(true);
        addView(this.infoTextView, LayoutHelper.createFrame(-2, -1.0f, 51, (float) left, 30.0f, 100.0f, 0.0f));
        this.addButton = new TextView(context) {
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (FeaturedStickerSetInfoCell.this.drawProgress || !(FeaturedStickerSetInfoCell.this.drawProgress || FeaturedStickerSetInfoCell.this.progressAlpha == 0.0f)) {
                    FeaturedStickerSetInfoCell.this.botProgressPaint.setAlpha(Math.min(255, (int) (FeaturedStickerSetInfoCell.this.progressAlpha * 255.0f)));
                    int x = getMeasuredWidth() - AndroidUtilities.dp(11.0f);
                    FeaturedStickerSetInfoCell.this.rect.set((float) x, (float) AndroidUtilities.dp(3.0f), (float) (AndroidUtilities.dp(8.0f) + x), (float) AndroidUtilities.dp(11.0f));
                    canvas.drawArc(FeaturedStickerSetInfoCell.this.rect, (float) FeaturedStickerSetInfoCell.this.angle, 220.0f, false, FeaturedStickerSetInfoCell.this.botProgressPaint);
                    invalidate(((int) FeaturedStickerSetInfoCell.this.rect.left) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.top) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.right) + AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.bottom) + AndroidUtilities.dp(2.0f));
                    long newTime = System.currentTimeMillis();
                    if (Math.abs(FeaturedStickerSetInfoCell.this.lastUpdateTime - System.currentTimeMillis()) < 1000) {
                        long delta = newTime - FeaturedStickerSetInfoCell.this.lastUpdateTime;
                        FeaturedStickerSetInfoCell.this.angle = (int) (((float) FeaturedStickerSetInfoCell.this.angle) + (((float) (360 * delta)) / 2000.0f));
                        FeaturedStickerSetInfoCell.this.angle = FeaturedStickerSetInfoCell.this.angle - ((FeaturedStickerSetInfoCell.this.angle / 360) * 360);
                        if (FeaturedStickerSetInfoCell.this.drawProgress) {
                            if (FeaturedStickerSetInfoCell.this.progressAlpha < 1.0f) {
                                FeaturedStickerSetInfoCell.this.progressAlpha = FeaturedStickerSetInfoCell.this.progressAlpha + (((float) delta) / 200.0f);
                                if (FeaturedStickerSetInfoCell.this.progressAlpha > 1.0f) {
                                    FeaturedStickerSetInfoCell.this.progressAlpha = 1.0f;
                                }
                            }
                        } else if (FeaturedStickerSetInfoCell.this.progressAlpha > 0.0f) {
                            FeaturedStickerSetInfoCell.this.progressAlpha = FeaturedStickerSetInfoCell.this.progressAlpha - (((float) delta) / 200.0f);
                            if (FeaturedStickerSetInfoCell.this.progressAlpha < 0.0f) {
                                FeaturedStickerSetInfoCell.this.progressAlpha = 0.0f;
                            }
                        }
                    }
                    FeaturedStickerSetInfoCell.this.lastUpdateTime = newTime;
                    invalidate();
                }
            }
        };
        this.addButton.setGravity(17);
        this.addButton.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        this.addButton.setTextSize(1, 14.0f);
        this.addButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(this.addButton, LayoutHelper.createFrame(-2, 28.0f, 53, 0.0f, 16.0f, 14.0f, 0.0f));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(60.0f), 1073741824));
    }

    public void setAddOnClickListener(OnClickListener onClickListener) {
        this.hasOnClick = true;
        this.addButton.setOnClickListener(onClickListener);
    }

    public void setStickerSet(TLRPC$StickerSetCovered stickerSet, boolean unread) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.nameTextView.setText(stickerSet.set.title);
        this.infoTextView.setText(LocaleController.formatPluralString("Stickers", stickerSet.set.count));
        if (unread) {
            this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, this.drawable, null);
        } else {
            this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        if (this.hasOnClick) {
            this.addButton.setVisibility(0);
            boolean isStickerPackInstalled = StickersQuery.isStickerPackInstalled(stickerSet.set.id);
            this.isInstalled = isStickerPackInstalled;
            if (isStickerPackInstalled) {
                this.addButton.setBackgroundDrawable(this.delDrawable);
                this.addButton.setText(LocaleController.getString("StickersRemove", R.string.StickersRemove).toUpperCase());
            } else {
                this.addButton.setBackgroundDrawable(this.addDrawable);
                this.addButton.setText(LocaleController.getString("Add", R.string.Add).toUpperCase());
            }
            this.addButton.setPadding(AndroidUtilities.dp(17.0f), 0, AndroidUtilities.dp(17.0f), 0);
        } else {
            this.addButton.setVisibility(8);
        }
        this.set = stickerSet;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setDrawProgress(boolean value) {
        this.drawProgress = value;
        this.lastUpdateTime = System.currentTimeMillis();
        this.addButton.invalidate();
    }

    public TLRPC$StickerSetCovered getStickerSet() {
        return this.set;
    }
}
