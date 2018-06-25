package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Paint.Views.EntityView.SelectionView;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.Rect;
import org.telegram.ui.Components.Size;

public class StickerView extends EntityView {
    private int anchor;
    private Size baseSize;
    private ImageReceiver centerImage;
    private FrameLayoutDrawer containerView;
    private boolean mirrored;
    private TLRPC$Document sticker;

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        protected void onDraw(Canvas canvas) {
            StickerView.this.stickerDraw(canvas);
        }
    }

    public class StickerViewSelectionView extends SelectionView {
        private Paint arcPaint = new Paint(1);
        private RectF arcRect = new RectF();

        public StickerViewSelectionView(Context context) {
            super(context);
            this.arcPaint.setColor(-1);
            this.arcPaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
            this.arcPaint.setStyle(Style.STROKE);
        }

        protected int pointInsideHandle(float x, float y) {
            float radius = (float) AndroidUtilities.dp(19.5f);
            float inset = radius + ((float) AndroidUtilities.dp(1.0f));
            float middle = inset + ((((float) getHeight()) - (inset * 2.0f)) / 2.0f);
            if (x > inset - radius && y > middle - radius && x < inset + radius && y < middle + radius) {
                return 1;
            }
            if (x > ((((float) getWidth()) - (inset * 2.0f)) + inset) - radius && y > middle - radius && x < ((((float) getWidth()) - (inset * 2.0f)) + inset) + radius && y < middle + radius) {
                return 2;
            }
            float selectionRadius = ((float) getWidth()) / 2.0f;
            if (Math.pow((double) (x - selectionRadius), 2.0d) + Math.pow((double) (y - selectionRadius), 2.0d) < Math.pow((double) selectionRadius, 2.0d)) {
                return 3;
            }
            return 0;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float radius = (float) AndroidUtilities.dp(4.5f);
            float inset = (radius + ((float) AndroidUtilities.dp(1.0f))) + ((float) AndroidUtilities.dp(15.0f));
            float mainRadius = ((float) (getWidth() / 2)) - inset;
            this.arcRect.set(inset, inset, (mainRadius * 2.0f) + inset, (mainRadius * 2.0f) + inset);
            for (int i = 0; i < 48; i++) {
                Canvas canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (4.0f + 4.0f) * ((float) i), 4.0f, false, this.arcPaint);
            }
            canvas.drawCircle(inset, inset + mainRadius, radius, this.dotPaint);
            canvas.drawCircle(inset, inset + mainRadius, radius, this.dotStrokePaint);
            canvas.drawCircle((mainRadius * 2.0f) + inset, inset + mainRadius, radius, this.dotPaint);
            canvas.drawCircle((mainRadius * 2.0f) + inset, inset + mainRadius, radius, this.dotStrokePaint);
        }
    }

    public StickerView(Context context, Point position, Size baseSize, TLRPC$Document sticker) {
        this(context, position, 0.0f, 1.0f, baseSize, sticker);
    }

    public StickerView(Context context, Point position, float angle, float scale, Size baseSize, TLRPC$Document sticker) {
        super(context, position);
        this.anchor = -1;
        this.mirrored = false;
        this.centerImage = new ImageReceiver();
        setRotation(angle);
        setScale(scale);
        this.sticker = sticker;
        this.baseSize = baseSize;
        for (int a = 0; a < sticker.attributes.size(); a++) {
            DocumentAttribute attribute = (DocumentAttribute) sticker.attributes.get(a);
            if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                if (attribute.mask_coords != null) {
                    this.anchor = attribute.mask_coords.f82n;
                }
                this.containerView = new FrameLayoutDrawer(context);
                addView(this.containerView, LayoutHelper.createFrame(-1, -1.0f));
                this.centerImage.setAspectFit(true);
                this.centerImage.setInvalidateAll(true);
                this.centerImage.setParentView(this.containerView);
                this.centerImage.setImage((TLObject) sticker, null, sticker.thumb.location, null, "webp", 1);
                updatePosition();
            }
        }
        this.containerView = new FrameLayoutDrawer(context);
        addView(this.containerView, LayoutHelper.createFrame(-1, -1.0f));
        this.centerImage.setAspectFit(true);
        this.centerImage.setInvalidateAll(true);
        this.centerImage.setParentView(this.containerView);
        this.centerImage.setImage((TLObject) sticker, null, sticker.thumb.location, null, "webp", 1);
        updatePosition();
    }

    public StickerView(Context context, StickerView stickerView, Point position) {
        this(context, position, stickerView.getRotation(), stickerView.getScale(), stickerView.baseSize, stickerView.sticker);
        if (stickerView.mirrored) {
            mirror();
        }
    }

    public int getAnchor() {
        return this.anchor;
    }

    public void mirror() {
        this.mirrored = !this.mirrored;
        this.containerView.invalidate();
    }

    protected void updatePosition() {
        float halfHeight = this.baseSize.height / 2.0f;
        setX(this.position.f105x - (this.baseSize.width / 2.0f));
        setY(this.position.f106y - halfHeight);
        updateSelectionView();
    }

    protected void stickerDraw(Canvas canvas) {
        if (this.containerView != null) {
            canvas.save();
            if (this.centerImage.getBitmap() != null) {
                if (this.mirrored) {
                    canvas.scale(-1.0f, 1.0f);
                    canvas.translate(-this.baseSize.width, 0.0f);
                }
                this.centerImage.setImageCoords(0, 0, (int) this.baseSize.width, (int) this.baseSize.height);
                this.centerImage.draw(canvas);
            }
            canvas.restore();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec((int) this.baseSize.width, 1073741824), MeasureSpec.makeMeasureSpec((int) this.baseSize.height, 1073741824));
    }

    protected Rect getSelectionBounds() {
        float scale = ((ViewGroup) getParent()).getScaleX();
        float side = ((float) getWidth()) * (getScale() + 0.4f);
        return new Rect((this.position.f105x - (side / 2.0f)) * scale, (this.position.f106y - (side / 2.0f)) * scale, side * scale, side * scale);
    }

    protected SelectionView createSelectionView() {
        return new StickerViewSelectionView(getContext());
    }

    public TLRPC$Document getSticker() {
        return this.sticker;
    }
}
