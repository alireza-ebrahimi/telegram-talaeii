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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class FeaturedStickerSetInfoCell extends FrameLayout {
    private TextView addButton;
    private Drawable addDrawable = Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
    private int angle;
    private Paint botProgressPaint = new Paint(1);
    private Drawable delDrawable = Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_featuredStickers_delButton), Theme.getColor(Theme.key_featuredStickers_delButtonPressed));
    private boolean drawProgress;
    Drawable drawable = new C40081();
    private boolean hasOnClick;
    private TextView infoTextView;
    private boolean isInstalled;
    private long lastUpdateTime;
    private TextView nameTextView;
    private float progressAlpha;
    private RectF rect = new RectF();
    private StickerSetCovered set;

    /* renamed from: org.telegram.ui.Cells.FeaturedStickerSetInfoCell$1 */
    class C40081 extends Drawable {
        Paint paint = new Paint(1);

        C40081() {
        }

        public void draw(Canvas canvas) {
            this.paint.setColor(Theme.getColor(Theme.key_featuredStickers_unread));
            canvas.drawCircle((float) AndroidUtilities.dp(8.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(4.0f), this.paint);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(26.0f);
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

    public FeaturedStickerSetInfoCell(Context context, int i) {
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
        addView(this.nameTextView, LayoutHelper.createFrame(-2, -1.0f, 51, (float) i, 8.0f, 100.0f, BitmapDescriptorFactory.HUE_RED));
        this.infoTextView = new TextView(context);
        this.infoTextView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelTrendingDescription));
        this.infoTextView.setTextSize(1, 13.0f);
        this.infoTextView.setEllipsize(TruncateAt.END);
        this.infoTextView.setSingleLine(true);
        addView(this.infoTextView, LayoutHelper.createFrame(-2, -1.0f, 51, (float) i, 30.0f, 100.0f, BitmapDescriptorFactory.HUE_RED));
        this.addButton = new TextView(context) {
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (FeaturedStickerSetInfoCell.this.drawProgress || !(FeaturedStickerSetInfoCell.this.drawProgress || FeaturedStickerSetInfoCell.this.progressAlpha == BitmapDescriptorFactory.HUE_RED)) {
                    FeaturedStickerSetInfoCell.this.botProgressPaint.setAlpha(Math.min(255, (int) (FeaturedStickerSetInfoCell.this.progressAlpha * 255.0f)));
                    int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(11.0f);
                    FeaturedStickerSetInfoCell.this.rect.set((float) measuredWidth, (float) AndroidUtilities.dp(3.0f), (float) (measuredWidth + AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(11.0f));
                    canvas.drawArc(FeaturedStickerSetInfoCell.this.rect, (float) FeaturedStickerSetInfoCell.this.angle, 220.0f, false, FeaturedStickerSetInfoCell.this.botProgressPaint);
                    invalidate(((int) FeaturedStickerSetInfoCell.this.rect.left) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.top) - AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.right) + AndroidUtilities.dp(2.0f), ((int) FeaturedStickerSetInfoCell.this.rect.bottom) + AndroidUtilities.dp(2.0f));
                    long currentTimeMillis = System.currentTimeMillis();
                    if (Math.abs(FeaturedStickerSetInfoCell.this.lastUpdateTime - System.currentTimeMillis()) < 1000) {
                        long access$500 = currentTimeMillis - FeaturedStickerSetInfoCell.this.lastUpdateTime;
                        FeaturedStickerSetInfoCell.this.angle = (int) ((((float) (360 * access$500)) / 2000.0f) + ((float) FeaturedStickerSetInfoCell.this.angle));
                        FeaturedStickerSetInfoCell.this.angle = FeaturedStickerSetInfoCell.this.angle - ((FeaturedStickerSetInfoCell.this.angle / 360) * 360);
                        if (FeaturedStickerSetInfoCell.this.drawProgress) {
                            if (FeaturedStickerSetInfoCell.this.progressAlpha < 1.0f) {
                                FeaturedStickerSetInfoCell.this.progressAlpha = (((float) access$500) / 200.0f) + FeaturedStickerSetInfoCell.this.progressAlpha;
                                if (FeaturedStickerSetInfoCell.this.progressAlpha > 1.0f) {
                                    FeaturedStickerSetInfoCell.this.progressAlpha = 1.0f;
                                }
                            }
                        } else if (FeaturedStickerSetInfoCell.this.progressAlpha > BitmapDescriptorFactory.HUE_RED) {
                            FeaturedStickerSetInfoCell.this.progressAlpha = FeaturedStickerSetInfoCell.this.progressAlpha - (((float) access$500) / 200.0f);
                            if (FeaturedStickerSetInfoCell.this.progressAlpha < BitmapDescriptorFactory.HUE_RED) {
                                FeaturedStickerSetInfoCell.this.progressAlpha = BitmapDescriptorFactory.HUE_RED;
                            }
                        }
                    }
                    FeaturedStickerSetInfoCell.this.lastUpdateTime = currentTimeMillis;
                    invalidate();
                }
            }
        };
        this.addButton.setGravity(17);
        this.addButton.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        this.addButton.setTextSize(1, 14.0f);
        this.addButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(this.addButton, LayoutHelper.createFrame(-2, 28.0f, 53, BitmapDescriptorFactory.HUE_RED, 16.0f, 14.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public StickerSetCovered getStickerSet() {
        return this.set;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(60.0f), 1073741824));
    }

    public void setAddOnClickListener(OnClickListener onClickListener) {
        this.hasOnClick = true;
        this.addButton.setOnClickListener(onClickListener);
    }

    public void setDrawProgress(boolean z) {
        this.drawProgress = z;
        this.lastUpdateTime = System.currentTimeMillis();
        this.addButton.invalidate();
    }

    public void setStickerSet(StickerSetCovered stickerSetCovered, boolean z) {
        this.lastUpdateTime = System.currentTimeMillis();
        this.nameTextView.setText(stickerSetCovered.set.title);
        this.infoTextView.setText(LocaleController.formatPluralString("Stickers", stickerSetCovered.set.count));
        if (z) {
            this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, this.drawable, null);
        } else {
            this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        if (this.hasOnClick) {
            this.addButton.setVisibility(0);
            boolean isStickerPackInstalled = StickersQuery.isStickerPackInstalled(stickerSetCovered.set.id);
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
        this.set = stickerSetCovered;
    }
}
