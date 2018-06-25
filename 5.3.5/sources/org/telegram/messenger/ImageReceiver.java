package org.telegram.messenger;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentEncrypted;
import org.telegram.tgnet.TLRPC$TL_fileEncryptedLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocation;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.ui.Components.AnimatedFileDrawable;

public class ImageReceiver implements NotificationCenterDelegate {
    private static PorterDuffColorFilter selectedColorFilter = new PorterDuffColorFilter(-2236963, Mode.MULTIPLY);
    private static PorterDuffColorFilter selectedGroupColorFilter = new PorterDuffColorFilter(-4473925, Mode.MULTIPLY);
    private boolean allowDecodeSingleFrame;
    private boolean allowStartAnimation;
    private RectF bitmapRect;
    private BitmapShader bitmapShader;
    private BitmapShader bitmapShaderThumb;
    private boolean canceledLoading;
    private boolean centerRotation;
    private ColorFilter colorFilter;
    private byte crossfadeAlpha;
    private Drawable crossfadeImage;
    private String crossfadeKey;
    private BitmapShader crossfadeShader;
    private boolean crossfadeWithOldImage;
    private boolean crossfadeWithThumb;
    private float currentAlpha;
    private int currentCacheType;
    private String currentExt;
    private String currentFilter;
    private String currentHttpUrl;
    private Drawable currentImage;
    private TLObject currentImageLocation;
    private String currentKey;
    private int currentSize;
    private Drawable currentThumb;
    private String currentThumbFilter;
    private String currentThumbKey;
    private TLRPC$FileLocation currentThumbLocation;
    private ImageReceiverDelegate delegate;
    private Rect drawRegion;
    private boolean forceCrossfade;
    private boolean forceLoding;
    private boolean forcePreview;
    private int imageH;
    private int imageW;
    private int imageX;
    private int imageY;
    private boolean invalidateAll;
    private boolean isAspectFit;
    private int isPressed;
    private boolean isVisible;
    private long lastUpdateAlphaTime;
    private boolean manualAlphaAnimator;
    private boolean needsQualityThumb;
    private int orientation;
    private float overrideAlpha;
    private int param;
    private MessageObject parentMessageObject;
    private View parentView;
    private Paint roundPaint;
    private int roundRadius;
    private RectF roundRect;
    private SetImageBackup setImageBackup;
    private Matrix shaderMatrix;
    private boolean shouldGenerateQualityThumb;
    private Drawable staticThumb;
    private Integer tag;
    private Integer thumbTag;

    public interface ImageReceiverDelegate {
        void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2);
    }

    private class SetImageBackup {
        public int cacheType;
        public String ext;
        public TLObject fileLocation;
        public String filter;
        public String httpUrl;
        public int size;
        public Drawable thumb;
        public String thumbFilter;
        public TLRPC$FileLocation thumbLocation;

        private SetImageBackup() {
        }
    }

    public ImageReceiver() {
        this(null);
    }

    public ImageReceiver(View view) {
        this.allowStartAnimation = true;
        this.drawRegion = new Rect();
        this.isVisible = true;
        this.roundRect = new RectF();
        this.bitmapRect = new RectF();
        this.shaderMatrix = new Matrix();
        this.overrideAlpha = 1.0f;
        this.crossfadeAlpha = (byte) 1;
        this.parentView = view;
        this.roundPaint = new Paint(1);
    }

    public void cancelLoadImage() {
        this.forceLoding = false;
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
        this.canceledLoading = true;
    }

    public void setForceLoading(boolean value) {
        this.forceLoding = value;
    }

    public boolean isForceLoding() {
        return this.forceLoding;
    }

    public void setImage(TLObject path, String filter, Drawable thumb, String ext, int cacheType) {
        setImage(path, null, filter, thumb, null, null, 0, ext, cacheType);
    }

    public void setImage(TLObject path, String filter, Drawable thumb, int size, String ext, int cacheType) {
        setImage(path, null, filter, thumb, null, null, size, ext, cacheType);
    }

    public void setImage(String httpUrl, String filter, Drawable thumb, String ext, int size) {
        setImage(null, httpUrl, filter, thumb, null, null, size, ext, 1);
    }

    public void setImage(TLObject fileLocation, String filter, TLRPC$FileLocation thumbLocation, String thumbFilter, String ext, int cacheType) {
        setImage(fileLocation, null, filter, null, thumbLocation, thumbFilter, 0, ext, cacheType);
    }

    public void setImage(TLObject fileLocation, String filter, TLRPC$FileLocation thumbLocation, String thumbFilter, int size, String ext, int cacheType) {
        setImage(fileLocation, null, filter, null, thumbLocation, thumbFilter, size, ext, cacheType);
    }

    public void setImage(TLObject fileLocation, String httpUrl, String filter, Drawable thumb, TLRPC$FileLocation thumbLocation, String thumbFilter, int size, String ext, int cacheType) {
        if (this.setImageBackup != null) {
            this.setImageBackup.fileLocation = null;
            this.setImageBackup.httpUrl = null;
            this.setImageBackup.thumbLocation = null;
            this.setImageBackup.thumb = null;
        }
        ImageReceiverDelegate imageReceiverDelegate;
        boolean z;
        boolean z2;
        if (!(fileLocation == null && httpUrl == null && thumbLocation == null) && (fileLocation == null || (fileLocation instanceof TLRPC$TL_fileLocation) || (fileLocation instanceof TLRPC$TL_fileEncryptedLocation) || (fileLocation instanceof TLRPC$TL_document) || (fileLocation instanceof TLRPC$TL_webDocument) || (fileLocation instanceof TLRPC$TL_documentEncrypted))) {
            if (!((thumbLocation instanceof TLRPC$TL_fileLocation) || (thumbLocation instanceof TLRPC$TL_fileEncryptedLocation))) {
                thumbLocation = null;
            }
            String key = null;
            if (fileLocation != null) {
                if (fileLocation instanceof TLRPC$FileLocation) {
                    TLRPC$FileLocation location = (TLRPC$FileLocation) fileLocation;
                    key = location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + location.local_id;
                } else if (fileLocation instanceof TLRPC$TL_webDocument) {
                    key = Utilities.MD5(((TLRPC$TL_webDocument) fileLocation).url);
                } else {
                    TLRPC$Document location2 = (TLRPC$Document) fileLocation;
                    if (location2.dc_id != 0) {
                        key = location2.version == 0 ? location2.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + location2.id : location2.dc_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + location2.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + location2.version;
                    } else {
                        fileLocation = null;
                    }
                }
            } else if (httpUrl != null) {
                key = Utilities.MD5(httpUrl);
            }
            if (!(key == null || filter == null)) {
                key = key + "@" + filter;
            }
            if (!(this.currentKey == null || key == null || !this.currentKey.equals(key))) {
                if (this.delegate != null) {
                    imageReceiverDelegate = this.delegate;
                    z = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
                    if (this.currentImage == null) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    imageReceiverDelegate.didSetImage(this, z, z2);
                }
                if (!(this.canceledLoading || this.forcePreview)) {
                    return;
                }
            }
            String thumbKey = null;
            if (thumbLocation != null) {
                thumbKey = thumbLocation.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + thumbLocation.local_id;
                if (thumbFilter != null) {
                    thumbKey = thumbKey + "@" + thumbFilter;
                }
            }
            if (!this.crossfadeWithOldImage) {
                recycleBitmap(key, 0);
                recycleBitmap(thumbKey, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = null;
            } else if (this.currentImage != null) {
                recycleBitmap(thumbKey, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = this.bitmapShader;
                this.crossfadeImage = this.currentImage;
                this.crossfadeKey = this.currentKey;
                this.currentImage = null;
                this.currentKey = null;
            } else if (this.currentThumb != null) {
                recycleBitmap(key, 0);
                recycleBitmap(null, 2);
                this.crossfadeShader = this.bitmapShaderThumb;
                this.crossfadeImage = this.currentThumb;
                this.crossfadeKey = this.currentThumbKey;
                this.currentThumb = null;
                this.currentThumbKey = null;
            } else {
                recycleBitmap(key, 0);
                recycleBitmap(thumbKey, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = null;
            }
            this.currentThumbKey = thumbKey;
            this.currentKey = key;
            this.currentExt = ext;
            this.currentImageLocation = fileLocation;
            this.currentHttpUrl = httpUrl;
            this.currentFilter = filter;
            this.currentThumbFilter = thumbFilter;
            this.currentSize = size;
            this.currentCacheType = cacheType;
            this.currentThumbLocation = thumbLocation;
            this.staticThumb = thumb;
            this.bitmapShader = null;
            this.bitmapShaderThumb = null;
            this.currentAlpha = 1.0f;
            if (this.delegate != null) {
                imageReceiverDelegate = this.delegate;
                z = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
                imageReceiverDelegate.didSetImage(this, z, this.currentImage == null);
            }
            ImageLoader.getInstance().loadImageForImageReceiver(this);
            if (this.parentView == null) {
                return;
            }
            if (this.invalidateAll) {
                this.parentView.invalidate();
                return;
            } else {
                this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                return;
            }
        }
        for (int a = 0; a < 3; a++) {
            recycleBitmap(null, a);
        }
        this.currentKey = null;
        this.currentExt = ext;
        this.currentThumbKey = null;
        this.currentThumbFilter = null;
        this.currentImageLocation = null;
        this.currentHttpUrl = null;
        this.currentFilter = null;
        this.currentCacheType = 0;
        this.staticThumb = thumb;
        this.currentAlpha = 1.0f;
        this.currentThumbLocation = null;
        this.currentSize = 0;
        this.currentImage = null;
        this.bitmapShader = null;
        this.bitmapShaderThumb = null;
        this.crossfadeShader = null;
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
        if (this.parentView != null) {
            if (this.invalidateAll) {
                this.parentView.invalidate();
            } else {
                this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
            }
        }
        if (this.delegate != null) {
            imageReceiverDelegate = this.delegate;
            if (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) {
                z = false;
            } else {
                z = true;
            }
            if (this.currentImage == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            imageReceiverDelegate.didSetImage(this, z, z2);
        }
    }

    public void setColorFilter(ColorFilter filter) {
        this.colorFilter = filter;
    }

    public void setDelegate(ImageReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void setPressed(int value) {
        this.isPressed = value;
    }

    public boolean getPressed() {
        return this.isPressed != 0;
    }

    public void setOrientation(int angle, boolean center) {
        while (angle < 0) {
            angle += 360;
        }
        while (angle > 360) {
            angle -= 360;
        }
        this.orientation = angle;
        this.centerRotation = center;
    }

    public void setInvalidateAll(boolean value) {
        this.invalidateAll = value;
    }

    public Drawable getStaticThumb() {
        return this.staticThumb;
    }

    public int getAnimatedOrientation() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            return ((AnimatedFileDrawable) this.currentImage).getOrientation();
        }
        if (this.staticThumb instanceof AnimatedFileDrawable) {
            return ((AnimatedFileDrawable) this.staticThumb).getOrientation();
        }
        return 0;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setImageBitmap(Bitmap bitmap) {
        Drawable bitmapDrawable;
        if (bitmap != null) {
            bitmapDrawable = new BitmapDrawable(null, bitmap);
        } else {
            bitmapDrawable = null;
        }
        setImageBitmap(bitmapDrawable);
    }

    public void setImageBitmap(Drawable bitmap) {
        boolean z = false;
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
        for (int a = 0; a < 3; a++) {
            recycleBitmap(null, a);
        }
        this.staticThumb = bitmap;
        if (this.roundRadius == 0 || !(bitmap instanceof BitmapDrawable)) {
            this.bitmapShaderThumb = null;
        } else {
            this.bitmapShaderThumb = new BitmapShader(((BitmapDrawable) bitmap).getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
        }
        this.currentThumbLocation = null;
        this.currentKey = null;
        this.currentExt = null;
        this.currentThumbKey = null;
        this.currentImage = null;
        this.currentThumbFilter = null;
        this.currentImageLocation = null;
        this.currentHttpUrl = null;
        this.currentFilter = null;
        this.currentSize = 0;
        this.currentCacheType = 0;
        this.bitmapShader = null;
        this.crossfadeShader = null;
        if (this.setImageBackup != null) {
            this.setImageBackup.fileLocation = null;
            this.setImageBackup.httpUrl = null;
            this.setImageBackup.thumbLocation = null;
            this.setImageBackup.thumb = null;
        }
        this.currentAlpha = 1.0f;
        if (this.delegate != null) {
            ImageReceiverDelegate imageReceiverDelegate = this.delegate;
            if (!(this.currentThumb == null && this.staticThumb == null)) {
                z = true;
            }
            imageReceiverDelegate.didSetImage(this, z, true);
        }
        if (this.parentView == null) {
            return;
        }
        if (this.invalidateAll) {
            this.parentView.invalidate();
        } else {
            this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
        }
    }

    public void clearImage() {
        for (int a = 0; a < 3; a++) {
            recycleBitmap(null, a);
        }
        if (this.needsQualityThumb) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageThumbGenerated);
        }
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
    }

    public void onDetachedFromWindow() {
        if (!(this.currentImageLocation == null && this.currentHttpUrl == null && this.currentThumbLocation == null && this.staticThumb == null)) {
            if (this.setImageBackup == null) {
                this.setImageBackup = new SetImageBackup();
            }
            this.setImageBackup.fileLocation = this.currentImageLocation;
            this.setImageBackup.httpUrl = this.currentHttpUrl;
            this.setImageBackup.filter = this.currentFilter;
            this.setImageBackup.thumb = this.staticThumb;
            this.setImageBackup.thumbLocation = this.currentThumbLocation;
            this.setImageBackup.thumbFilter = this.currentThumbFilter;
            this.setImageBackup.size = this.currentSize;
            this.setImageBackup.ext = this.currentExt;
            this.setImageBackup.cacheType = this.currentCacheType;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReplacedPhotoInMemCache);
        clearImage();
    }

    public boolean onAttachedToWindow() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReplacedPhotoInMemCache);
        if (this.needsQualityThumb) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageThumbGenerated);
        }
        if (this.setImageBackup == null || (this.setImageBackup.fileLocation == null && this.setImageBackup.httpUrl == null && this.setImageBackup.thumbLocation == null && this.setImageBackup.thumb == null)) {
            return false;
        }
        setImage(this.setImageBackup.fileLocation, this.setImageBackup.httpUrl, this.setImageBackup.filter, this.setImageBackup.thumb, this.setImageBackup.thumbLocation, this.setImageBackup.thumbFilter, this.setImageBackup.size, this.setImageBackup.ext, this.setImageBackup.cacheType);
        return true;
    }

    private void drawDrawable(Canvas canvas, Drawable drawable, int alpha, BitmapShader shader) {
        if (drawable instanceof BitmapDrawable) {
            Paint paint;
            int bitmapW;
            int bitmapH;
            Drawable bitmapDrawable = (BitmapDrawable) drawable;
            if (shader != null) {
                paint = this.roundPaint;
            } else {
                paint = bitmapDrawable.getPaint();
            }
            boolean hasFilter = (paint == null || paint.getColorFilter() == null) ? false : true;
            if (hasFilter && this.isPressed == 0) {
                if (shader != null) {
                    this.roundPaint.setColorFilter(null);
                } else if (this.staticThumb != drawable) {
                    bitmapDrawable.setColorFilter(null);
                }
            } else if (!(hasFilter || this.isPressed == 0)) {
                if (this.isPressed == 1) {
                    if (shader != null) {
                        this.roundPaint.setColorFilter(selectedColorFilter);
                    } else {
                        bitmapDrawable.setColorFilter(selectedColorFilter);
                    }
                } else if (shader != null) {
                    this.roundPaint.setColorFilter(selectedGroupColorFilter);
                } else {
                    bitmapDrawable.setColorFilter(selectedGroupColorFilter);
                }
            }
            if (this.colorFilter != null) {
                if (shader != null) {
                    this.roundPaint.setColorFilter(this.colorFilter);
                } else {
                    bitmapDrawable.setColorFilter(this.colorFilter);
                }
            }
            if (bitmapDrawable instanceof AnimatedFileDrawable) {
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    bitmapW = bitmapDrawable.getIntrinsicHeight();
                    bitmapH = bitmapDrawable.getIntrinsicWidth();
                } else {
                    bitmapW = bitmapDrawable.getIntrinsicWidth();
                    bitmapH = bitmapDrawable.getIntrinsicHeight();
                }
            } else if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                bitmapW = bitmapDrawable.getBitmap().getHeight();
                bitmapH = bitmapDrawable.getBitmap().getWidth();
            } else {
                bitmapW = bitmapDrawable.getBitmap().getWidth();
                bitmapH = bitmapDrawable.getBitmap().getHeight();
            }
            float scaleW = ((float) bitmapW) / ((float) this.imageW);
            float scaleH = ((float) bitmapH) / ((float) this.imageH);
            float scale;
            if (shader != null) {
                this.roundPaint.setShader(shader);
                scale = Math.min(scaleW, scaleH);
                this.roundRect.set((float) this.imageX, (float) this.imageY, (float) (this.imageX + this.imageW), (float) (this.imageY + this.imageH));
                this.shaderMatrix.reset();
                if (Math.abs(scaleW - scaleH) <= 1.0E-5f) {
                    this.drawRegion.set(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                } else if (((float) bitmapW) / scaleH > ((float) this.imageW)) {
                    this.drawRegion.set(this.imageX - ((((int) (((float) bitmapW) / scaleH)) - this.imageW) / 2), this.imageY, this.imageX + ((((int) (((float) bitmapW) / scaleH)) + this.imageW) / 2), this.imageY + this.imageH);
                } else {
                    this.drawRegion.set(this.imageX, this.imageY - ((((int) (((float) bitmapH) / scaleW)) - this.imageH) / 2), this.imageX + this.imageW, this.imageY + ((((int) (((float) bitmapH) / scaleW)) + this.imageH) / 2));
                }
                if (this.isVisible) {
                    if (Math.abs(scaleW - scaleH) > 1.0E-5f) {
                        int w = (int) Math.floor((double) (((float) this.imageW) * scale));
                        int h = (int) Math.floor((double) (((float) this.imageH) * scale));
                        this.bitmapRect.set((float) ((bitmapW - w) / 2), (float) ((bitmapH - h) / 2), (float) ((bitmapW + w) / 2), (float) ((bitmapH + h) / 2));
                        this.shaderMatrix.setRectToRect(this.bitmapRect, this.roundRect, ScaleToFit.START);
                    } else {
                        this.bitmapRect.set(0.0f, 0.0f, (float) bitmapW, (float) bitmapH);
                        this.shaderMatrix.setRectToRect(this.bitmapRect, this.roundRect, ScaleToFit.FILL);
                    }
                    shader.setLocalMatrix(this.shaderMatrix);
                    this.roundPaint.setAlpha(alpha);
                    canvas.drawRoundRect(this.roundRect, (float) this.roundRadius, (float) this.roundRadius, this.roundPaint);
                    return;
                }
                return;
            } else if (this.isAspectFit) {
                scale = Math.max(scaleW, scaleH);
                canvas.save();
                bitmapW = (int) (((float) bitmapW) / scale);
                bitmapH = (int) (((float) bitmapH) / scale);
                this.drawRegion.set(this.imageX + ((this.imageW - bitmapW) / 2), this.imageY + ((this.imageH - bitmapH) / 2), this.imageX + ((this.imageW + bitmapW) / 2), this.imageY + ((this.imageH + bitmapH) / 2));
                bitmapDrawable.setBounds(this.drawRegion);
                try {
                    bitmapDrawable.setAlpha(alpha);
                    bitmapDrawable.draw(canvas);
                } catch (Exception e) {
                    if (bitmapDrawable == this.currentImage && this.currentKey != null) {
                        ImageLoader.getInstance().removeImage(this.currentKey);
                        this.currentKey = null;
                    } else if (bitmapDrawable == this.currentThumb && this.currentThumbKey != null) {
                        ImageLoader.getInstance().removeImage(this.currentThumbKey);
                        this.currentThumbKey = null;
                    }
                    setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                    FileLog.e(e);
                }
                canvas.restore();
                return;
            } else if (Math.abs(scaleW - scaleH) > 1.0E-5f) {
                canvas.save();
                canvas.clipRect(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                if (this.orientation % 360 != 0) {
                    if (this.centerRotation) {
                        canvas.rotate((float) this.orientation, (float) (this.imageW / 2), (float) (this.imageH / 2));
                    } else {
                        canvas.rotate((float) this.orientation, 0.0f, 0.0f);
                    }
                }
                if (((float) bitmapW) / scaleH > ((float) this.imageW)) {
                    bitmapW = (int) (((float) bitmapW) / scaleH);
                    this.drawRegion.set(this.imageX - ((bitmapW - this.imageW) / 2), this.imageY, this.imageX + ((this.imageW + bitmapW) / 2), this.imageY + this.imageH);
                } else {
                    bitmapH = (int) (((float) bitmapH) / scaleW);
                    this.drawRegion.set(this.imageX, this.imageY - ((bitmapH - this.imageH) / 2), this.imageX + this.imageW, this.imageY + ((this.imageH + bitmapH) / 2));
                }
                if (bitmapDrawable instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) bitmapDrawable).setActualDrawRect(this.imageX, this.imageY, this.imageW, this.imageH);
                }
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    width = (this.drawRegion.right - this.drawRegion.left) / 2;
                    height = (this.drawRegion.bottom - this.drawRegion.top) / 2;
                    centerX = (this.drawRegion.right + this.drawRegion.left) / 2;
                    centerY = (this.drawRegion.top + this.drawRegion.bottom) / 2;
                    bitmapDrawable.setBounds(centerX - height, centerY - width, centerX + height, centerY + width);
                } else {
                    bitmapDrawable.setBounds(this.drawRegion);
                }
                if (this.isVisible) {
                    try {
                        bitmapDrawable.setAlpha(alpha);
                        bitmapDrawable.draw(canvas);
                    } catch (Exception e2) {
                        if (bitmapDrawable == this.currentImage && this.currentKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentKey);
                            this.currentKey = null;
                        } else if (bitmapDrawable == this.currentThumb && this.currentThumbKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentThumbKey);
                            this.currentThumbKey = null;
                        }
                        setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                        FileLog.e(e2);
                    }
                }
                canvas.restore();
                return;
            } else {
                canvas.save();
                if (this.orientation % 360 != 0) {
                    if (this.centerRotation) {
                        canvas.rotate((float) this.orientation, (float) (this.imageW / 2), (float) (this.imageH / 2));
                    } else {
                        canvas.rotate((float) this.orientation, 0.0f, 0.0f);
                    }
                }
                this.drawRegion.set(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                if (bitmapDrawable instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) bitmapDrawable).setActualDrawRect(this.imageX, this.imageY, this.imageW, this.imageH);
                }
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    width = (this.drawRegion.right - this.drawRegion.left) / 2;
                    height = (this.drawRegion.bottom - this.drawRegion.top) / 2;
                    centerX = (this.drawRegion.right + this.drawRegion.left) / 2;
                    centerY = (this.drawRegion.top + this.drawRegion.bottom) / 2;
                    bitmapDrawable.setBounds(centerX - height, centerY - width, centerX + height, centerY + width);
                } else {
                    bitmapDrawable.setBounds(this.drawRegion);
                }
                if (this.isVisible) {
                    try {
                        bitmapDrawable.setAlpha(alpha);
                        bitmapDrawable.draw(canvas);
                    } catch (Exception e22) {
                        if (bitmapDrawable == this.currentImage && this.currentKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentKey);
                            this.currentKey = null;
                        } else if (bitmapDrawable == this.currentThumb && this.currentThumbKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentThumbKey);
                            this.currentThumbKey = null;
                        }
                        setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                        FileLog.e(e22);
                    }
                }
                canvas.restore();
                return;
            }
        }
        this.drawRegion.set(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
        drawable.setBounds(this.drawRegion);
        if (this.isVisible) {
            try {
                drawable.setAlpha(alpha);
                drawable.draw(canvas);
            } catch (Exception e222) {
                FileLog.e(e222);
            }
        }
    }

    private void checkAlphaAnimation(boolean skip) {
        if (!this.manualAlphaAnimator && this.currentAlpha != 1.0f) {
            if (!skip) {
                long dt = System.currentTimeMillis() - this.lastUpdateAlphaTime;
                if (dt > 18) {
                    dt = 18;
                }
                this.currentAlpha += ((float) dt) / 150.0f;
                if (this.currentAlpha > 1.0f) {
                    this.currentAlpha = 1.0f;
                    if (this.crossfadeImage != null) {
                        recycleBitmap(null, 2);
                        this.crossfadeShader = null;
                    }
                }
            }
            this.lastUpdateAlphaTime = System.currentTimeMillis();
            if (this.parentView == null) {
                return;
            }
            if (this.invalidateAll) {
                this.parentView.invalidate();
            } else {
                this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
            }
        }
    }

    public boolean draw(Canvas canvas) {
        Drawable drawable = null;
        try {
            boolean animationNotReady = (this.currentImage instanceof AnimatedFileDrawable) && !((AnimatedFileDrawable) this.currentImage).hasBitmap();
            boolean isThumb = false;
            BitmapShader customShader = null;
            if (!this.forcePreview && this.currentImage != null && !animationNotReady) {
                drawable = this.currentImage;
            } else if (this.crossfadeImage != null) {
                drawable = this.crossfadeImage;
                customShader = this.crossfadeShader;
            } else if (this.staticThumb instanceof BitmapDrawable) {
                drawable = this.staticThumb;
                isThumb = true;
            } else if (this.currentThumb != null) {
                drawable = this.currentThumb;
                isThumb = true;
            }
            if (drawable != null) {
                boolean z;
                int i;
                if (this.crossfadeAlpha == (byte) 0) {
                    i = (int) (this.overrideAlpha * 255.0f);
                    if (customShader == null) {
                        customShader = isThumb ? this.bitmapShaderThumb : this.bitmapShader;
                    }
                    drawDrawable(canvas, drawable, i, customShader);
                } else if (this.crossfadeWithThumb && animationNotReady) {
                    drawDrawable(canvas, drawable, (int) (this.overrideAlpha * 255.0f), this.bitmapShaderThumb);
                } else {
                    if (this.crossfadeWithThumb && this.currentAlpha != 1.0f) {
                        Drawable thumbDrawable = null;
                        BitmapShader customThumbShader = null;
                        if (drawable == this.currentImage) {
                            if (this.crossfadeImage != null) {
                                thumbDrawable = this.crossfadeImage;
                                customThumbShader = this.crossfadeShader;
                            } else if (this.staticThumb != null) {
                                thumbDrawable = this.staticThumb;
                            } else if (this.currentThumb != null) {
                                thumbDrawable = this.currentThumb;
                            }
                        } else if (drawable == this.currentThumb && this.staticThumb != null) {
                            thumbDrawable = this.staticThumb;
                        }
                        if (thumbDrawable != null) {
                            i = (int) (this.overrideAlpha * 255.0f);
                            if (customThumbShader == null) {
                                customThumbShader = this.bitmapShaderThumb;
                            }
                            drawDrawable(canvas, thumbDrawable, i, customThumbShader);
                        }
                    }
                    i = (int) ((this.overrideAlpha * this.currentAlpha) * 255.0f);
                    if (customShader == null) {
                        customShader = isThumb ? this.bitmapShaderThumb : this.bitmapShader;
                    }
                    drawDrawable(canvas, drawable, i, customShader);
                }
                if (animationNotReady && this.crossfadeWithThumb) {
                    z = true;
                } else {
                    z = false;
                }
                checkAlphaAnimation(z);
                return true;
            } else if (this.staticThumb != null) {
                drawDrawable(canvas, this.staticThumb, 255, null);
                checkAlphaAnimation(animationNotReady);
                return true;
            } else {
                checkAlphaAnimation(animationNotReady);
                return false;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void setManualAlphaAnimator(boolean value) {
        this.manualAlphaAnimator = value;
    }

    public float getCurrentAlpha() {
        return this.currentAlpha;
    }

    public void setCurrentAlpha(float value) {
        this.currentAlpha = value;
    }

    public Bitmap getBitmap() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            return ((AnimatedFileDrawable) this.currentImage).getAnimatedBitmap();
        }
        if (this.staticThumb instanceof AnimatedFileDrawable) {
            return ((AnimatedFileDrawable) this.staticThumb).getAnimatedBitmap();
        }
        if (this.currentImage instanceof BitmapDrawable) {
            return ((BitmapDrawable) this.currentImage).getBitmap();
        }
        if (this.currentThumb instanceof BitmapDrawable) {
            return ((BitmapDrawable) this.currentThumb).getBitmap();
        }
        if (this.staticThumb instanceof BitmapDrawable) {
            return ((BitmapDrawable) this.staticThumb).getBitmap();
        }
        return null;
    }

    public Bitmap getThumbBitmap() {
        if (this.currentThumb instanceof BitmapDrawable) {
            return ((BitmapDrawable) this.currentThumb).getBitmap();
        }
        if (this.staticThumb instanceof BitmapDrawable) {
            return ((BitmapDrawable) this.staticThumb).getBitmap();
        }
        return null;
    }

    public int getBitmapWidth() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            if (this.orientation % 360 == 0 || this.orientation % 360 == 180) {
                return this.currentImage.getIntrinsicWidth();
            }
            return this.currentImage.getIntrinsicHeight();
        } else if (this.staticThumb instanceof AnimatedFileDrawable) {
            return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.staticThumb.getIntrinsicWidth() : this.staticThumb.getIntrinsicHeight();
        } else {
            Bitmap bitmap = getBitmap();
            if (bitmap != null) {
                return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? bitmap.getWidth() : bitmap.getHeight();
            } else {
                if (this.staticThumb != null) {
                    return this.staticThumb.getIntrinsicWidth();
                }
                return 1;
            }
        }
    }

    public int getBitmapHeight() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            if (this.orientation % 360 == 0 || this.orientation % 360 == 180) {
                return this.currentImage.getIntrinsicHeight();
            }
            return this.currentImage.getIntrinsicWidth();
        } else if (this.staticThumb instanceof AnimatedFileDrawable) {
            return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.staticThumb.getIntrinsicHeight() : this.staticThumb.getIntrinsicWidth();
        } else {
            Bitmap bitmap = getBitmap();
            if (bitmap != null) {
                return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? bitmap.getHeight() : bitmap.getWidth();
            } else {
                if (this.staticThumb != null) {
                    return this.staticThumb.getIntrinsicHeight();
                }
                return 1;
            }
        }
    }

    public void setVisible(boolean value, boolean invalidate) {
        if (this.isVisible != value) {
            this.isVisible = value;
            if (invalidate && this.parentView != null) {
                if (this.invalidateAll) {
                    this.parentView.invalidate();
                } else {
                    this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                }
            }
        }
    }

    public boolean getVisible() {
        return this.isVisible;
    }

    public void setAlpha(float value) {
        this.overrideAlpha = value;
    }

    public void setCrossfadeAlpha(byte value) {
        this.crossfadeAlpha = value;
    }

    public boolean hasImage() {
        return (this.currentImage == null && this.currentThumb == null && this.currentKey == null && this.currentHttpUrl == null && this.staticThumb == null) ? false : true;
    }

    public boolean hasBitmapImage() {
        return (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
    }

    public void setAspectFit(boolean value) {
        this.isAspectFit = value;
    }

    public void setParentView(View view) {
        this.parentView = view;
        if (this.currentImage instanceof AnimatedFileDrawable) {
            this.currentImage.setParentView(this.parentView);
        }
    }

    public void setImageX(int x) {
        this.imageX = x;
    }

    public void setImageY(int y) {
        this.imageY = y;
    }

    public void setImageWidth(int width) {
        this.imageW = width;
    }

    public void setImageCoords(int x, int y, int width, int height) {
        this.imageX = x;
        this.imageY = y;
        this.imageW = width;
        this.imageH = height;
    }

    public float getCenterX() {
        return ((float) this.imageX) + (((float) this.imageW) / 2.0f);
    }

    public float getCenterY() {
        return ((float) this.imageY) + (((float) this.imageH) / 2.0f);
    }

    public int getImageX() {
        return this.imageX;
    }

    public int getImageX2() {
        return this.imageX + this.imageW;
    }

    public int getImageY() {
        return this.imageY;
    }

    public int getImageY2() {
        return this.imageY + this.imageH;
    }

    public int getImageWidth() {
        return this.imageW;
    }

    public int getImageHeight() {
        return this.imageH;
    }

    public String getExt() {
        return this.currentExt;
    }

    public boolean isInsideImage(float x, float y) {
        return x >= ((float) this.imageX) && x <= ((float) (this.imageX + this.imageW)) && y >= ((float) this.imageY) && y <= ((float) (this.imageY + this.imageH));
    }

    public Rect getDrawRegion() {
        return this.drawRegion;
    }

    public String getFilter() {
        return this.currentFilter;
    }

    public String getThumbFilter() {
        return this.currentThumbFilter;
    }

    public String getKey() {
        return this.currentKey;
    }

    public String getThumbKey() {
        return this.currentThumbKey;
    }

    public int getSize() {
        return this.currentSize;
    }

    public TLObject getImageLocation() {
        return this.currentImageLocation;
    }

    public TLRPC$FileLocation getThumbLocation() {
        return this.currentThumbLocation;
    }

    public String getHttpImageLocation() {
        return this.currentHttpUrl;
    }

    public int getCacheType() {
        return this.currentCacheType;
    }

    public void setForcePreview(boolean value) {
        this.forcePreview = value;
    }

    public void setForceCrossfade(boolean value) {
        this.forceCrossfade = value;
    }

    public boolean isForcePreview() {
        return this.forcePreview;
    }

    public void setRoundRadius(int value) {
        this.roundRadius = value;
    }

    public int getRoundRadius() {
        return this.roundRadius;
    }

    public void setParentMessageObject(MessageObject messageObject) {
        this.parentMessageObject = messageObject;
    }

    public MessageObject getParentMessageObject() {
        return this.parentMessageObject;
    }

    public void setNeedsQualityThumb(boolean value) {
        this.needsQualityThumb = value;
        if (this.needsQualityThumb) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageThumbGenerated);
        } else {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageThumbGenerated);
        }
    }

    public void setCrossfadeWithOldImage(boolean value) {
        this.crossfadeWithOldImage = value;
    }

    public boolean isNeedsQualityThumb() {
        return this.needsQualityThumb;
    }

    public void setShouldGenerateQualityThumb(boolean value) {
        this.shouldGenerateQualityThumb = value;
    }

    public boolean isShouldGenerateQualityThumb() {
        return this.shouldGenerateQualityThumb;
    }

    public void setAllowStartAnimation(boolean value) {
        this.allowStartAnimation = value;
    }

    public void setAllowDecodeSingleFrame(boolean value) {
        this.allowDecodeSingleFrame = value;
    }

    public boolean isAllowStartAnimation() {
        return this.allowStartAnimation;
    }

    public void startAnimation() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            ((AnimatedFileDrawable) this.currentImage).start();
        }
    }

    public void stopAnimation() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            ((AnimatedFileDrawable) this.currentImage).stop();
        }
    }

    public boolean isAnimationRunning() {
        return (this.currentImage instanceof AnimatedFileDrawable) && ((AnimatedFileDrawable) this.currentImage).isRunning();
    }

    public AnimatedFileDrawable getAnimation() {
        return this.currentImage instanceof AnimatedFileDrawable ? (AnimatedFileDrawable) this.currentImage : null;
    }

    protected Integer getTag(boolean thumb) {
        if (thumb) {
            return this.thumbTag;
        }
        return this.tag;
    }

    protected void setTag(Integer value, boolean thumb) {
        if (thumb) {
            this.thumbTag = value;
        } else {
            this.tag = value;
        }
    }

    public void setParam(int value) {
        this.param = value;
    }

    public int getParam() {
        return this.param;
    }

    protected boolean setImageBitmapByKey(BitmapDrawable bitmap, String key, boolean thumb, boolean memCache) {
        boolean z = false;
        if (bitmap == null || key == null) {
            return false;
        }
        boolean z2;
        if (thumb) {
            if (this.currentThumb == null && (this.currentImage == null || (((this.currentImage instanceof AnimatedFileDrawable) && !((AnimatedFileDrawable) this.currentImage).hasBitmap()) || this.forcePreview))) {
                if (this.currentThumbKey == null || !key.equals(this.currentThumbKey)) {
                    return false;
                }
                ImageLoader.getInstance().incrementUseCount(this.currentThumbKey);
                this.currentThumb = bitmap;
                if (this.roundRadius == 0 || !(bitmap instanceof BitmapDrawable)) {
                    this.bitmapShaderThumb = null;
                } else if (bitmap instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) bitmap).setRoundRadius(this.roundRadius);
                } else {
                    this.bitmapShaderThumb = new BitmapShader(bitmap.getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
                }
                if (memCache || this.crossfadeAlpha == (byte) 2) {
                    this.currentAlpha = 1.0f;
                } else if (this.parentMessageObject != null && this.parentMessageObject.isRoundVideo() && this.parentMessageObject.isSending()) {
                    this.currentAlpha = 1.0f;
                } else {
                    this.currentAlpha = 0.0f;
                    this.lastUpdateAlphaTime = System.currentTimeMillis();
                    z2 = this.staticThumb != null && this.currentKey == null;
                    this.crossfadeWithThumb = z2;
                }
                if (!((this.staticThumb instanceof BitmapDrawable) || this.parentView == null)) {
                    if (this.invalidateAll) {
                        this.parentView.invalidate();
                    } else {
                        this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                    }
                }
            }
        } else if (this.currentKey == null || !key.equals(this.currentKey)) {
            return false;
        } else {
            if (!(bitmap instanceof AnimatedFileDrawable)) {
                ImageLoader.getInstance().incrementUseCount(this.currentKey);
            }
            this.currentImage = bitmap;
            if (this.roundRadius == 0 || !(bitmap instanceof BitmapDrawable)) {
                this.bitmapShader = null;
            } else if (bitmap instanceof AnimatedFileDrawable) {
                ((AnimatedFileDrawable) bitmap).setRoundRadius(this.roundRadius);
            } else {
                this.bitmapShader = new BitmapShader(bitmap.getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
            }
            if ((memCache || this.forcePreview) && !this.forceCrossfade) {
                this.currentAlpha = 1.0f;
            } else if ((this.currentThumb == null && this.staticThumb == null) || this.currentAlpha == 1.0f || this.forceCrossfade) {
                this.currentAlpha = 0.0f;
                this.lastUpdateAlphaTime = System.currentTimeMillis();
                if (this.currentThumb == null && this.staticThumb == null) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                this.crossfadeWithThumb = z2;
            }
            if (bitmap instanceof AnimatedFileDrawable) {
                AnimatedFileDrawable fileDrawable = (AnimatedFileDrawable) bitmap;
                fileDrawable.setParentView(this.parentView);
                if (this.allowStartAnimation) {
                    fileDrawable.start();
                } else {
                    fileDrawable.setAllowDecodeSingleFrame(this.allowDecodeSingleFrame);
                }
            }
            if (this.parentView != null) {
                if (this.invalidateAll) {
                    this.parentView.invalidate();
                } else {
                    this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                }
            }
        }
        if (this.delegate != null) {
            ImageReceiverDelegate imageReceiverDelegate = this.delegate;
            if (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (this.currentImage == null) {
                z = true;
            }
            imageReceiverDelegate.didSetImage(this, z2, z);
        }
        return true;
    }

    private void recycleBitmap(String newKey, int type) {
        String key;
        Drawable image;
        if (type == 2) {
            key = this.crossfadeKey;
            image = this.crossfadeImage;
        } else if (type == 1) {
            key = this.currentThumbKey;
            image = this.currentThumb;
        } else {
            key = this.currentKey;
            image = this.currentImage;
        }
        if (key != null && ((newKey == null || !newKey.equals(key)) && image != null)) {
            if (image instanceof AnimatedFileDrawable) {
                ((AnimatedFileDrawable) image).recycle();
            } else if (image instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
                boolean canDelete = ImageLoader.getInstance().decrementUseCount(key);
                if (!ImageLoader.getInstance().isInCache(key) && canDelete) {
                    bitmap.recycle();
                }
            }
        }
        if (type == 2) {
            this.crossfadeKey = null;
            this.crossfadeImage = null;
        } else if (type == 1) {
            this.currentThumb = null;
            this.currentThumbKey = null;
        } else {
            this.currentImage = null;
            this.currentKey = null;
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.messageThumbGenerated) {
            String key = args[1];
            if (this.currentThumbKey != null && this.currentThumbKey.equals(key)) {
                if (this.currentThumb == null) {
                    ImageLoader.getInstance().incrementUseCount(this.currentThumbKey);
                }
                this.currentThumb = (BitmapDrawable) args[0];
                if (this.roundRadius == 0 || this.currentImage != null || !(this.currentThumb instanceof BitmapDrawable) || (this.currentThumb instanceof AnimatedFileDrawable)) {
                    this.bitmapShaderThumb = null;
                } else {
                    this.bitmapShaderThumb = new BitmapShader(((BitmapDrawable) this.currentThumb).getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
                }
                if (this.staticThumb instanceof BitmapDrawable) {
                    this.staticThumb = null;
                }
                if (this.parentView == null) {
                    return;
                }
                if (this.invalidateAll) {
                    this.parentView.invalidate();
                } else {
                    this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                }
            }
        } else if (id == NotificationCenter.didReplacedPhotoInMemCache) {
            String oldKey = args[0];
            if (this.currentKey != null && this.currentKey.equals(oldKey)) {
                this.currentKey = (String) args[1];
                this.currentImageLocation = (TLRPC$FileLocation) args[2];
            }
            if (this.currentThumbKey != null && this.currentThumbKey.equals(oldKey)) {
                this.currentThumbKey = (String) args[1];
                this.currentThumbLocation = (TLRPC$FileLocation) args[2];
            }
            if (this.setImageBackup != null) {
                if (this.currentKey != null && this.currentKey.equals(oldKey)) {
                    this.currentKey = (String) args[1];
                    this.currentImageLocation = (TLRPC$FileLocation) args[2];
                }
                if (this.currentThumbKey != null && this.currentThumbKey.equals(oldKey)) {
                    this.currentThumbKey = (String) args[1];
                    this.currentThumbLocation = (TLRPC$FileLocation) args[2];
                }
            }
        }
    }
}
