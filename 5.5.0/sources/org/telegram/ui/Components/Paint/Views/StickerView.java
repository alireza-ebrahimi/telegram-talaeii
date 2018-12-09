package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC.Document;
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
    private Document sticker;

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

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float dp = (float) AndroidUtilities.dp(4.5f);
            float dp2 = (((float) AndroidUtilities.dp(1.0f)) + dp) + ((float) AndroidUtilities.dp(15.0f));
            float width = ((float) (getWidth() / 2)) - dp2;
            this.arcRect.set(dp2, dp2, (width * 2.0f) + dp2, (width * 2.0f) + dp2);
            for (int i = 0; i < 48; i++) {
                Canvas canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (4.0f + 4.0f) * ((float) i), 4.0f, false, this.arcPaint);
            }
            canvas.drawCircle(dp2, dp2 + width, dp, this.dotPaint);
            canvas.drawCircle(dp2, dp2 + width, dp, this.dotStrokePaint);
            canvas.drawCircle((width * 2.0f) + dp2, dp2 + width, dp, this.dotPaint);
            canvas.drawCircle((width * 2.0f) + dp2, dp2 + width, dp, this.dotStrokePaint);
        }

        protected int pointInsideHandle(float f, float f2) {
            float dp = (float) AndroidUtilities.dp(19.5f);
            float dp2 = ((float) AndroidUtilities.dp(1.0f)) + dp;
            float height = ((((float) getHeight()) - (dp2 * 2.0f)) / 2.0f) + dp2;
            if (f > dp2 - dp && f2 > height - dp && f < dp2 + dp && f2 < height + dp) {
                return 1;
            }
            if (f > ((((float) getWidth()) - (dp2 * 2.0f)) + dp2) - dp && f2 > height - dp && f < (dp2 + (((float) getWidth()) - (dp2 * 2.0f))) + dp && f2 < height + dp) {
                return 2;
            }
            dp2 = ((float) getWidth()) / 2.0f;
            return Math.pow((double) (f - dp2), 2.0d) + Math.pow((double) (f2 - dp2), 2.0d) < Math.pow((double) dp2, 2.0d) ? 3 : 0;
        }
    }

    public StickerView(Context context, StickerView stickerView, Point point) {
        this(context, point, stickerView.getRotation(), stickerView.getScale(), stickerView.baseSize, stickerView.sticker);
        if (stickerView.mirrored) {
            mirror();
        }
    }

    public StickerView(Context context, Point point, float f, float f2, Size size, Document document) {
        super(context, point);
        this.anchor = -1;
        this.mirrored = false;
        this.centerImage = new ImageReceiver();
        setRotation(f);
        setScale(f2);
        this.sticker = document;
        this.baseSize = size;
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                if (documentAttribute.mask_coords != null) {
                    this.anchor = documentAttribute.mask_coords.f10161n;
                }
                this.containerView = new FrameLayoutDrawer(context);
                addView(this.containerView, LayoutHelper.createFrame(-1, -1.0f));
                this.centerImage.setAspectFit(true);
                this.centerImage.setInvalidateAll(true);
                this.centerImage.setParentView(this.containerView);
                this.centerImage.setImage(document, null, document.thumb.location, null, "webp", 1);
                updatePosition();
            }
        }
        this.containerView = new FrameLayoutDrawer(context);
        addView(this.containerView, LayoutHelper.createFrame(-1, -1.0f));
        this.centerImage.setAspectFit(true);
        this.centerImage.setInvalidateAll(true);
        this.centerImage.setParentView(this.containerView);
        this.centerImage.setImage(document, null, document.thumb.location, null, "webp", 1);
        updatePosition();
    }

    public StickerView(Context context, Point point, Size size, Document document) {
        this(context, point, BitmapDescriptorFactory.HUE_RED, 1.0f, size, document);
    }

    protected SelectionView createSelectionView() {
        return new StickerViewSelectionView(getContext());
    }

    public int getAnchor() {
        return this.anchor;
    }

    protected Rect getSelectionBounds() {
        float scaleX = ((ViewGroup) getParent()).getScaleX();
        float width = ((float) getWidth()) * (getScale() + 0.4f);
        return new Rect((this.position.f10184x - (width / 2.0f)) * scaleX, (this.position.f10185y - (width / 2.0f)) * scaleX, width * scaleX, scaleX * width);
    }

    public Document getSticker() {
        return this.sticker;
    }

    public void mirror() {
        this.mirrored = !this.mirrored;
        this.containerView.invalidate();
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec((int) this.baseSize.width, 1073741824), MeasureSpec.makeMeasureSpec((int) this.baseSize.height, 1073741824));
    }

    protected void stickerDraw(Canvas canvas) {
        if (this.containerView != null) {
            canvas.save();
            if (this.centerImage.getBitmap() != null) {
                if (this.mirrored) {
                    canvas.scale(-1.0f, 1.0f);
                    canvas.translate(-this.baseSize.width, BitmapDescriptorFactory.HUE_RED);
                }
                this.centerImage.setImageCoords(0, 0, (int) this.baseSize.width, (int) this.baseSize.height);
                this.centerImage.draw(canvas);
            }
            canvas.restore();
        }
    }

    protected void updatePosition() {
        float f = this.baseSize.height / 2.0f;
        setX(this.position.f10184x - (this.baseSize.width / 2.0f));
        setY(this.position.f10185y - f);
        updateSelectionView();
    }
}
