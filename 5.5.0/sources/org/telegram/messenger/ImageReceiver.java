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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentEncrypted;
import org.telegram.tgnet.TLRPC$TL_fileEncryptedLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocation;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.FileLocation;
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
    private FileLocation currentThumbLocation;
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
        public FileLocation thumbLocation;

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

    private void checkAlphaAnimation(boolean z) {
        long j = 18;
        if (!this.manualAlphaAnimator && this.currentAlpha != 1.0f) {
            if (!z) {
                long currentTimeMillis = System.currentTimeMillis() - this.lastUpdateAlphaTime;
                if (currentTimeMillis <= 18) {
                    j = currentTimeMillis;
                }
                this.currentAlpha = (((float) j) / 150.0f) + this.currentAlpha;
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

    private void drawDrawable(Canvas canvas, Drawable drawable, int i, BitmapShader bitmapShader) {
        Throwable th;
        if (drawable instanceof BitmapDrawable) {
            int intrinsicHeight;
            int intrinsicWidth;
            Drawable drawable2 = (BitmapDrawable) drawable;
            Paint paint = bitmapShader != null ? this.roundPaint : drawable2.getPaint();
            Object obj = (paint == null || paint.getColorFilter() == null) ? null : 1;
            if (obj == null || this.isPressed != 0) {
                if (obj == null && this.isPressed != 0) {
                    if (this.isPressed == 1) {
                        if (bitmapShader != null) {
                            this.roundPaint.setColorFilter(selectedColorFilter);
                        } else {
                            drawable2.setColorFilter(selectedColorFilter);
                        }
                    } else if (bitmapShader != null) {
                        this.roundPaint.setColorFilter(selectedGroupColorFilter);
                    } else {
                        drawable2.setColorFilter(selectedGroupColorFilter);
                    }
                }
            } else if (bitmapShader != null) {
                this.roundPaint.setColorFilter(null);
            } else if (this.staticThumb != drawable) {
                drawable2.setColorFilter(null);
            }
            if (this.colorFilter != null) {
                if (bitmapShader != null) {
                    this.roundPaint.setColorFilter(this.colorFilter);
                } else {
                    drawable2.setColorFilter(this.colorFilter);
                }
            }
            if (drawable2 instanceof AnimatedFileDrawable) {
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    intrinsicHeight = drawable2.getIntrinsicHeight();
                    intrinsicWidth = drawable2.getIntrinsicWidth();
                } else {
                    intrinsicHeight = drawable2.getIntrinsicWidth();
                    intrinsicWidth = drawable2.getIntrinsicHeight();
                }
            } else if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                intrinsicHeight = drawable2.getBitmap().getHeight();
                intrinsicWidth = drawable2.getBitmap().getWidth();
            } else {
                intrinsicHeight = drawable2.getBitmap().getWidth();
                intrinsicWidth = drawable2.getBitmap().getHeight();
            }
            float f = ((float) intrinsicHeight) / ((float) this.imageW);
            float f2 = ((float) intrinsicWidth) / ((float) this.imageH);
            int floor;
            if (bitmapShader != null) {
                this.roundPaint.setShader(bitmapShader);
                float min = Math.min(f, f2);
                this.roundRect.set((float) this.imageX, (float) this.imageY, (float) (this.imageX + this.imageW), (float) (this.imageY + this.imageH));
                this.shaderMatrix.reset();
                if (Math.abs(f - f2) <= 1.0E-5f) {
                    this.drawRegion.set(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                } else if (((float) intrinsicHeight) / f2 > ((float) this.imageW)) {
                    this.drawRegion.set(this.imageX - ((((int) (((float) intrinsicHeight) / f2)) - this.imageW) / 2), this.imageY, this.imageX + ((((int) (((float) intrinsicHeight) / f2)) + this.imageW) / 2), this.imageY + this.imageH);
                } else {
                    this.drawRegion.set(this.imageX, this.imageY - ((((int) (((float) intrinsicWidth) / f)) - this.imageH) / 2), this.imageX + this.imageW, this.imageY + ((((int) (((float) intrinsicWidth) / f)) + this.imageH) / 2));
                }
                if (this.isVisible) {
                    if (Math.abs(f - f2) > 1.0E-5f) {
                        floor = (int) Math.floor((double) (((float) this.imageW) * min));
                        int floor2 = (int) Math.floor((double) (min * ((float) this.imageH)));
                        this.bitmapRect.set((float) ((intrinsicHeight - floor) / 2), (float) ((intrinsicWidth - floor2) / 2), (float) ((intrinsicHeight + floor) / 2), (float) ((floor2 + intrinsicWidth) / 2));
                        this.shaderMatrix.setRectToRect(this.bitmapRect, this.roundRect, ScaleToFit.START);
                    } else {
                        this.bitmapRect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) intrinsicWidth);
                        this.shaderMatrix.setRectToRect(this.bitmapRect, this.roundRect, ScaleToFit.FILL);
                    }
                    bitmapShader.setLocalMatrix(this.shaderMatrix);
                    this.roundPaint.setAlpha(i);
                    canvas.drawRoundRect(this.roundRect, (float) this.roundRadius, (float) this.roundRadius, this.roundPaint);
                    return;
                }
                return;
            } else if (this.isAspectFit) {
                f = Math.max(f, f2);
                canvas.save();
                intrinsicHeight = (int) (((float) intrinsicHeight) / f);
                intrinsicWidth = (int) (((float) intrinsicWidth) / f);
                this.drawRegion.set(this.imageX + ((this.imageW - intrinsicHeight) / 2), this.imageY + ((this.imageH - intrinsicWidth) / 2), ((intrinsicHeight + this.imageW) / 2) + this.imageX, ((intrinsicWidth + this.imageH) / 2) + this.imageY);
                drawable2.setBounds(this.drawRegion);
                try {
                    drawable2.setAlpha(i);
                    drawable2.draw(canvas);
                } catch (Throwable e) {
                    th = e;
                    if (drawable2 == this.currentImage && this.currentKey != null) {
                        ImageLoader.getInstance().removeImage(this.currentKey);
                        this.currentKey = null;
                    } else if (drawable2 == this.currentThumb && this.currentThumbKey != null) {
                        ImageLoader.getInstance().removeImage(this.currentThumbKey);
                        this.currentThumbKey = null;
                    }
                    setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                    FileLog.m13728e(th);
                }
                canvas.restore();
                return;
            } else if (Math.abs(f - f2) > 1.0E-5f) {
                canvas.save();
                canvas.clipRect(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                if (this.orientation % 360 != 0) {
                    if (this.centerRotation) {
                        canvas.rotate((float) this.orientation, (float) (this.imageW / 2), (float) (this.imageH / 2));
                    } else {
                        canvas.rotate((float) this.orientation, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    }
                }
                if (((float) intrinsicHeight) / f2 > ((float) this.imageW)) {
                    intrinsicWidth = (int) (((float) intrinsicHeight) / f2);
                    this.drawRegion.set(this.imageX - ((intrinsicWidth - this.imageW) / 2), this.imageY, ((intrinsicWidth + this.imageW) / 2) + this.imageX, this.imageY + this.imageH);
                } else {
                    intrinsicWidth = (int) (((float) intrinsicWidth) / f);
                    this.drawRegion.set(this.imageX, this.imageY - ((intrinsicWidth - this.imageH) / 2), this.imageX + this.imageW, ((intrinsicWidth + this.imageH) / 2) + this.imageY);
                }
                if (drawable2 instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) drawable2).setActualDrawRect(this.imageX, this.imageY, this.imageW, this.imageH);
                }
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    intrinsicWidth = (this.drawRegion.right - this.drawRegion.left) / 2;
                    intrinsicHeight = (this.drawRegion.bottom - this.drawRegion.top) / 2;
                    floor = (this.drawRegion.right + this.drawRegion.left) / 2;
                    r6 = (this.drawRegion.top + this.drawRegion.bottom) / 2;
                    drawable2.setBounds(floor - intrinsicHeight, r6 - intrinsicWidth, intrinsicHeight + floor, intrinsicWidth + r6);
                } else {
                    drawable2.setBounds(this.drawRegion);
                }
                if (this.isVisible) {
                    try {
                        drawable2.setAlpha(i);
                        drawable2.draw(canvas);
                    } catch (Throwable e2) {
                        th = e2;
                        if (drawable2 == this.currentImage && this.currentKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentKey);
                            this.currentKey = null;
                        } else if (drawable2 == this.currentThumb && this.currentThumbKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentThumbKey);
                            this.currentThumbKey = null;
                        }
                        setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                        FileLog.m13728e(th);
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
                        canvas.rotate((float) this.orientation, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    }
                }
                this.drawRegion.set(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                if (drawable2 instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) drawable2).setActualDrawRect(this.imageX, this.imageY, this.imageW, this.imageH);
                }
                if (this.orientation % 360 == 90 || this.orientation % 360 == 270) {
                    intrinsicWidth = (this.drawRegion.right - this.drawRegion.left) / 2;
                    intrinsicHeight = (this.drawRegion.bottom - this.drawRegion.top) / 2;
                    floor = (this.drawRegion.right + this.drawRegion.left) / 2;
                    r6 = (this.drawRegion.top + this.drawRegion.bottom) / 2;
                    drawable2.setBounds(floor - intrinsicHeight, r6 - intrinsicWidth, intrinsicHeight + floor, intrinsicWidth + r6);
                } else {
                    drawable2.setBounds(this.drawRegion);
                }
                if (this.isVisible) {
                    try {
                        drawable2.setAlpha(i);
                        drawable2.draw(canvas);
                    } catch (Throwable e22) {
                        th = e22;
                        if (drawable2 == this.currentImage && this.currentKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentKey);
                            this.currentKey = null;
                        } else if (drawable2 == this.currentThumb && this.currentThumbKey != null) {
                            ImageLoader.getInstance().removeImage(this.currentThumbKey);
                            this.currentThumbKey = null;
                        }
                        setImage(this.currentImageLocation, this.currentHttpUrl, this.currentFilter, this.currentThumb, this.currentThumbLocation, this.currentThumbFilter, this.currentSize, this.currentExt, this.currentCacheType);
                        FileLog.m13728e(th);
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
                drawable.setAlpha(i);
                drawable.draw(canvas);
            } catch (Throwable e3) {
                FileLog.m13728e(e3);
            }
        }
    }

    private void recycleBitmap(String str, int i) {
        String str2;
        Drawable drawable;
        if (i == 2) {
            str2 = this.crossfadeKey;
            drawable = this.crossfadeImage;
        } else if (i == 1) {
            str2 = this.currentThumbKey;
            drawable = this.currentThumb;
        } else {
            str2 = this.currentKey;
            drawable = this.currentImage;
        }
        if (str2 != null && ((str == null || !str.equals(str2)) && drawable != null)) {
            if (drawable instanceof AnimatedFileDrawable) {
                ((AnimatedFileDrawable) drawable).recycle();
            } else if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                boolean decrementUseCount = ImageLoader.getInstance().decrementUseCount(str2);
                if (!ImageLoader.getInstance().isInCache(str2) && decrementUseCount) {
                    bitmap.recycle();
                }
            }
        }
        if (i == 2) {
            this.crossfadeKey = null;
            this.crossfadeImage = null;
        } else if (i == 1) {
            this.currentThumb = null;
            this.currentThumbKey = null;
        } else {
            this.currentImage = null;
            this.currentKey = null;
        }
    }

    public void cancelLoadImage() {
        this.forceLoding = false;
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
        this.canceledLoading = true;
    }

    public void clearImage() {
        for (int i = 0; i < 3; i++) {
            recycleBitmap(null, i);
        }
        if (this.needsQualityThumb) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageThumbGenerated);
        }
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        if (i == NotificationCenter.messageThumbGenerated) {
            str = (String) objArr[1];
            if (this.currentThumbKey != null && this.currentThumbKey.equals(str)) {
                if (this.currentThumb == null) {
                    ImageLoader.getInstance().incrementUseCount(this.currentThumbKey);
                }
                this.currentThumb = (BitmapDrawable) objArr[0];
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
        } else if (i == NotificationCenter.didReplacedPhotoInMemCache) {
            str = (String) objArr[0];
            if (this.currentKey != null && this.currentKey.equals(str)) {
                this.currentKey = (String) objArr[1];
                this.currentImageLocation = (FileLocation) objArr[2];
            }
            if (this.currentThumbKey != null && this.currentThumbKey.equals(str)) {
                this.currentThumbKey = (String) objArr[1];
                this.currentThumbLocation = (FileLocation) objArr[2];
            }
            if (this.setImageBackup != null) {
                if (this.currentKey != null && this.currentKey.equals(str)) {
                    this.currentKey = (String) objArr[1];
                    this.currentImageLocation = (FileLocation) objArr[2];
                }
                if (this.currentThumbKey != null && this.currentThumbKey.equals(str)) {
                    this.currentThumbKey = (String) objArr[1];
                    this.currentThumbLocation = (FileLocation) objArr[2];
                }
            }
        }
    }

    public boolean draw(Canvas canvas) {
        BitmapShader bitmapShader = null;
        try {
            Object obj;
            Drawable drawable;
            BitmapShader bitmapShader2;
            Drawable drawable2;
            boolean z = (this.currentImage instanceof AnimatedFileDrawable) && !((AnimatedFileDrawable) this.currentImage).hasBitmap();
            if (!this.forcePreview && this.currentImage != null && !z) {
                obj = null;
                drawable = this.currentImage;
                bitmapShader2 = null;
            } else if (this.crossfadeImage != null) {
                drawable2 = this.crossfadeImage;
                bitmapShader2 = this.crossfadeShader;
                obj = null;
                drawable = drawable2;
            } else if (this.staticThumb instanceof BitmapDrawable) {
                r5 = 1;
                drawable = this.staticThumb;
                bitmapShader2 = null;
            } else if (this.currentThumb != null) {
                r5 = 1;
                drawable = this.currentThumb;
                bitmapShader2 = null;
            } else {
                bitmapShader2 = null;
                obj = null;
                drawable = null;
            }
            if (drawable != null) {
                int i;
                if (this.crossfadeAlpha == (byte) 0) {
                    i = (int) (this.overrideAlpha * 255.0f);
                    if (bitmapShader2 == null) {
                        bitmapShader2 = obj != null ? this.bitmapShaderThumb : this.bitmapShader;
                    }
                    drawDrawable(canvas, drawable, i, bitmapShader2);
                } else if (this.crossfadeWithThumb && z) {
                    drawDrawable(canvas, drawable, (int) (this.overrideAlpha * 255.0f), this.bitmapShaderThumb);
                } else {
                    if (this.crossfadeWithThumb && this.currentAlpha != 1.0f) {
                        if (drawable != this.currentImage) {
                            if (drawable == this.currentThumb && this.staticThumb != null) {
                                drawable2 = this.staticThumb;
                            }
                            drawable2 = null;
                        } else if (this.crossfadeImage != null) {
                            drawable2 = this.crossfadeImage;
                            bitmapShader = this.crossfadeShader;
                        } else if (this.staticThumb != null) {
                            drawable2 = this.staticThumb;
                        } else {
                            if (this.currentThumb != null) {
                                drawable2 = this.currentThumb;
                            }
                            drawable2 = null;
                        }
                        if (drawable2 != null) {
                            int i2 = (int) (this.overrideAlpha * 255.0f);
                            if (bitmapShader == null) {
                                bitmapShader = this.bitmapShaderThumb;
                            }
                            drawDrawable(canvas, drawable2, i2, bitmapShader);
                        }
                    }
                    i = (int) ((this.overrideAlpha * this.currentAlpha) * 255.0f);
                    if (bitmapShader2 == null) {
                        bitmapShader2 = obj != null ? this.bitmapShaderThumb : this.bitmapShader;
                    }
                    drawDrawable(canvas, drawable, i, bitmapShader2);
                }
                boolean z2 = z && this.crossfadeWithThumb;
                checkAlphaAnimation(z2);
                return true;
            } else if (this.staticThumb != null) {
                drawDrawable(canvas, this.staticThumb, 255, null);
                checkAlphaAnimation(z);
                return true;
            } else {
                checkAlphaAnimation(z);
                return false;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public int getAnimatedOrientation() {
        return this.currentImage instanceof AnimatedFileDrawable ? ((AnimatedFileDrawable) this.currentImage).getOrientation() : this.staticThumb instanceof AnimatedFileDrawable ? ((AnimatedFileDrawable) this.staticThumb).getOrientation() : 0;
    }

    public AnimatedFileDrawable getAnimation() {
        return this.currentImage instanceof AnimatedFileDrawable ? (AnimatedFileDrawable) this.currentImage : null;
    }

    public Bitmap getBitmap() {
        return this.currentImage instanceof AnimatedFileDrawable ? ((AnimatedFileDrawable) this.currentImage).getAnimatedBitmap() : this.staticThumb instanceof AnimatedFileDrawable ? ((AnimatedFileDrawable) this.staticThumb).getAnimatedBitmap() : this.currentImage instanceof BitmapDrawable ? ((BitmapDrawable) this.currentImage).getBitmap() : this.currentThumb instanceof BitmapDrawable ? ((BitmapDrawable) this.currentThumb).getBitmap() : this.staticThumb instanceof BitmapDrawable ? ((BitmapDrawable) this.staticThumb).getBitmap() : null;
    }

    public int getBitmapHeight() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.currentImage.getIntrinsicHeight() : this.currentImage.getIntrinsicWidth();
        } else {
            if (this.staticThumb instanceof AnimatedFileDrawable) {
                return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.staticThumb.getIntrinsicHeight() : this.staticThumb.getIntrinsicWidth();
            } else {
                Bitmap bitmap = getBitmap();
                return bitmap == null ? this.staticThumb != null ? this.staticThumb.getIntrinsicHeight() : 1 : (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? bitmap.getHeight() : bitmap.getWidth();
            }
        }
    }

    public int getBitmapWidth() {
        if (this.currentImage instanceof AnimatedFileDrawable) {
            return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.currentImage.getIntrinsicWidth() : this.currentImage.getIntrinsicHeight();
        } else {
            if (this.staticThumb instanceof AnimatedFileDrawable) {
                return (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? this.staticThumb.getIntrinsicWidth() : this.staticThumb.getIntrinsicHeight();
            } else {
                Bitmap bitmap = getBitmap();
                return bitmap == null ? this.staticThumb != null ? this.staticThumb.getIntrinsicWidth() : 1 : (this.orientation % 360 == 0 || this.orientation % 360 == 180) ? bitmap.getWidth() : bitmap.getHeight();
            }
        }
    }

    public int getCacheType() {
        return this.currentCacheType;
    }

    public float getCenterX() {
        return ((float) this.imageX) + (((float) this.imageW) / 2.0f);
    }

    public float getCenterY() {
        return ((float) this.imageY) + (((float) this.imageH) / 2.0f);
    }

    public float getCurrentAlpha() {
        return this.currentAlpha;
    }

    public Rect getDrawRegion() {
        return this.drawRegion;
    }

    public String getExt() {
        return this.currentExt;
    }

    public String getFilter() {
        return this.currentFilter;
    }

    public String getHttpImageLocation() {
        return this.currentHttpUrl;
    }

    public int getImageHeight() {
        return this.imageH;
    }

    public TLObject getImageLocation() {
        return this.currentImageLocation;
    }

    public int getImageWidth() {
        return this.imageW;
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

    public String getKey() {
        return this.currentKey;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getParam() {
        return this.param;
    }

    public MessageObject getParentMessageObject() {
        return this.parentMessageObject;
    }

    public boolean getPressed() {
        return this.isPressed != 0;
    }

    public int getRoundRadius() {
        return this.roundRadius;
    }

    public int getSize() {
        return this.currentSize;
    }

    public Drawable getStaticThumb() {
        return this.staticThumb;
    }

    protected Integer getTag(boolean z) {
        return z ? this.thumbTag : this.tag;
    }

    public Bitmap getThumbBitmap() {
        return this.currentThumb instanceof BitmapDrawable ? ((BitmapDrawable) this.currentThumb).getBitmap() : this.staticThumb instanceof BitmapDrawable ? ((BitmapDrawable) this.staticThumb).getBitmap() : null;
    }

    public String getThumbFilter() {
        return this.currentThumbFilter;
    }

    public String getThumbKey() {
        return this.currentThumbKey;
    }

    public FileLocation getThumbLocation() {
        return this.currentThumbLocation;
    }

    public boolean getVisible() {
        return this.isVisible;
    }

    public boolean hasBitmapImage() {
        return (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
    }

    public boolean hasImage() {
        return (this.currentImage == null && this.currentThumb == null && this.currentKey == null && this.currentHttpUrl == null && this.staticThumb == null) ? false : true;
    }

    public boolean isAllowStartAnimation() {
        return this.allowStartAnimation;
    }

    public boolean isAnimationRunning() {
        return (this.currentImage instanceof AnimatedFileDrawable) && ((AnimatedFileDrawable) this.currentImage).isRunning();
    }

    public boolean isForceLoding() {
        return this.forceLoding;
    }

    public boolean isForcePreview() {
        return this.forcePreview;
    }

    public boolean isInsideImage(float f, float f2) {
        return f >= ((float) this.imageX) && f <= ((float) (this.imageX + this.imageW)) && f2 >= ((float) this.imageY) && f2 <= ((float) (this.imageY + this.imageH));
    }

    public boolean isNeedsQualityThumb() {
        return this.needsQualityThumb;
    }

    public boolean isShouldGenerateQualityThumb() {
        return this.shouldGenerateQualityThumb;
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

    public void setAllowDecodeSingleFrame(boolean z) {
        this.allowDecodeSingleFrame = z;
    }

    public void setAllowStartAnimation(boolean z) {
        this.allowStartAnimation = z;
    }

    public void setAlpha(float f) {
        this.overrideAlpha = f;
    }

    public void setAspectFit(boolean z) {
        this.isAspectFit = z;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    public void setCrossfadeAlpha(byte b) {
        this.crossfadeAlpha = b;
    }

    public void setCrossfadeWithOldImage(boolean z) {
        this.crossfadeWithOldImage = z;
    }

    public void setCurrentAlpha(float f) {
        this.currentAlpha = f;
    }

    public void setDelegate(ImageReceiverDelegate imageReceiverDelegate) {
        this.delegate = imageReceiverDelegate;
    }

    public void setForceCrossfade(boolean z) {
        this.forceCrossfade = z;
    }

    public void setForceLoading(boolean z) {
        this.forceLoding = z;
    }

    public void setForcePreview(boolean z) {
        this.forcePreview = z;
    }

    public void setImage(String str, String str2, Drawable drawable, String str3, int i) {
        setImage(null, str, str2, drawable, null, null, i, str3, 1);
    }

    public void setImage(TLObject tLObject, String str, Drawable drawable, int i, String str2, int i2) {
        setImage(tLObject, null, str, drawable, null, null, i, str2, i2);
    }

    public void setImage(TLObject tLObject, String str, Drawable drawable, String str2, int i) {
        setImage(tLObject, null, str, drawable, null, null, 0, str2, i);
    }

    public void setImage(TLObject tLObject, String str, String str2, Drawable drawable, FileLocation fileLocation, String str3, int i, String str4, int i2) {
        if (this.setImageBackup != null) {
            this.setImageBackup.fileLocation = null;
            this.setImageBackup.httpUrl = null;
            this.setImageBackup.thumbLocation = null;
            this.setImageBackup.thumb = null;
        }
        if (!(tLObject == null && str == null && fileLocation == null) && (tLObject == null || (tLObject instanceof TLRPC$TL_fileLocation) || (tLObject instanceof TLRPC$TL_fileEncryptedLocation) || (tLObject instanceof TLRPC$TL_document) || (tLObject instanceof TLRPC$TL_webDocument) || (tLObject instanceof TLRPC$TL_documentEncrypted))) {
            String MD5;
            if (!((fileLocation instanceof TLRPC$TL_fileLocation) || (fileLocation instanceof TLRPC$TL_fileEncryptedLocation))) {
                fileLocation = null;
            }
            if (tLObject == null) {
                MD5 = str != null ? Utilities.MD5(str) : null;
            } else if (tLObject instanceof FileLocation) {
                FileLocation fileLocation2 = (FileLocation) tLObject;
                MD5 = fileLocation2.volume_id + "_" + fileLocation2.local_id;
            } else if (tLObject instanceof TLRPC$TL_webDocument) {
                MD5 = Utilities.MD5(((TLRPC$TL_webDocument) tLObject).url);
            } else {
                Document document = (Document) tLObject;
                if (document.dc_id != 0) {
                    MD5 = document.version == 0 ? document.dc_id + "_" + document.id : document.dc_id + "_" + document.id + "_" + document.version;
                } else {
                    tLObject = null;
                    MD5 = null;
                }
            }
            if (!(MD5 == null || str2 == null)) {
                MD5 = MD5 + "@" + str2;
            }
            if (!(this.currentKey == null || MD5 == null || !this.currentKey.equals(MD5))) {
                if (this.delegate != null) {
                    ImageReceiverDelegate imageReceiverDelegate = this.delegate;
                    boolean z = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
                    imageReceiverDelegate.didSetImage(this, z, this.currentImage == null);
                }
                if (!(this.canceledLoading || this.forcePreview)) {
                    return;
                }
            }
            String str5 = null;
            if (fileLocation != null) {
                str5 = fileLocation.volume_id + "_" + fileLocation.local_id;
                if (str3 != null) {
                    str5 = str5 + "@" + str3;
                }
            }
            if (!this.crossfadeWithOldImage) {
                recycleBitmap(MD5, 0);
                recycleBitmap(str5, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = null;
            } else if (this.currentImage != null) {
                recycleBitmap(str5, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = this.bitmapShader;
                this.crossfadeImage = this.currentImage;
                this.crossfadeKey = this.currentKey;
                this.currentImage = null;
                this.currentKey = null;
            } else if (this.currentThumb != null) {
                recycleBitmap(MD5, 0);
                recycleBitmap(null, 2);
                this.crossfadeShader = this.bitmapShaderThumb;
                this.crossfadeImage = this.currentThumb;
                this.crossfadeKey = this.currentThumbKey;
                this.currentThumb = null;
                this.currentThumbKey = null;
            } else {
                recycleBitmap(MD5, 0);
                recycleBitmap(str5, 1);
                recycleBitmap(null, 2);
                this.crossfadeShader = null;
            }
            this.currentThumbKey = str5;
            this.currentKey = MD5;
            this.currentExt = str4;
            this.currentImageLocation = tLObject;
            this.currentHttpUrl = str;
            this.currentFilter = str2;
            this.currentThumbFilter = str3;
            this.currentSize = i;
            this.currentCacheType = i2;
            this.currentThumbLocation = fileLocation;
            this.staticThumb = drawable;
            this.bitmapShader = null;
            this.bitmapShaderThumb = null;
            this.currentAlpha = 1.0f;
            if (this.delegate != null) {
                ImageReceiverDelegate imageReceiverDelegate2 = this.delegate;
                boolean z2 = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
                imageReceiverDelegate2.didSetImage(this, z2, this.currentImage == null);
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
        for (int i3 = 0; i3 < 3; i3++) {
            recycleBitmap(null, i3);
        }
        this.currentKey = null;
        this.currentExt = str4;
        this.currentThumbKey = null;
        this.currentThumbFilter = null;
        this.currentImageLocation = null;
        this.currentHttpUrl = null;
        this.currentFilter = null;
        this.currentCacheType = 0;
        this.staticThumb = drawable;
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
            imageReceiverDelegate2 = this.delegate;
            z2 = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
            imageReceiverDelegate2.didSetImage(this, z2, this.currentImage == null);
        }
    }

    public void setImage(TLObject tLObject, String str, FileLocation fileLocation, String str2, int i, String str3, int i2) {
        setImage(tLObject, null, str, null, fileLocation, str2, i, str3, i2);
    }

    public void setImage(TLObject tLObject, String str, FileLocation fileLocation, String str2, String str3, int i) {
        setImage(tLObject, null, str, null, fileLocation, str2, 0, str3, i);
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap != null ? new BitmapDrawable(null, bitmap) : null);
    }

    public void setImageBitmap(Drawable drawable) {
        boolean z = false;
        ImageLoader.getInstance().cancelLoadingForImageReceiver(this, 0);
        for (int i = 0; i < 3; i++) {
            recycleBitmap(null, i);
        }
        this.staticThumb = drawable;
        if (this.roundRadius == 0 || !(drawable instanceof BitmapDrawable)) {
            this.bitmapShaderThumb = null;
        } else {
            this.bitmapShaderThumb = new BitmapShader(((BitmapDrawable) drawable).getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
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

    protected boolean setImageBitmapByKey(BitmapDrawable bitmapDrawable, String str, boolean z, boolean z2) {
        boolean z3 = false;
        if (bitmapDrawable == null || str == null) {
            return false;
        }
        boolean z4;
        if (z) {
            if (this.currentThumb == null && (this.currentImage == null || (((this.currentImage instanceof AnimatedFileDrawable) && !((AnimatedFileDrawable) this.currentImage).hasBitmap()) || this.forcePreview))) {
                if (this.currentThumbKey == null || !str.equals(this.currentThumbKey)) {
                    return false;
                }
                ImageLoader.getInstance().incrementUseCount(this.currentThumbKey);
                this.currentThumb = bitmapDrawable;
                if (this.roundRadius == 0 || !(bitmapDrawable instanceof BitmapDrawable)) {
                    this.bitmapShaderThumb = null;
                } else if (bitmapDrawable instanceof AnimatedFileDrawable) {
                    ((AnimatedFileDrawable) bitmapDrawable).setRoundRadius(this.roundRadius);
                } else {
                    this.bitmapShaderThumb = new BitmapShader(bitmapDrawable.getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
                }
                if (z2 || this.crossfadeAlpha == (byte) 2) {
                    this.currentAlpha = 1.0f;
                } else if (this.parentMessageObject != null && this.parentMessageObject.isRoundVideo() && this.parentMessageObject.isSending()) {
                    this.currentAlpha = 1.0f;
                } else {
                    this.currentAlpha = BitmapDescriptorFactory.HUE_RED;
                    this.lastUpdateAlphaTime = System.currentTimeMillis();
                    z4 = this.staticThumb != null && this.currentKey == null;
                    this.crossfadeWithThumb = z4;
                }
                if (!((this.staticThumb instanceof BitmapDrawable) || this.parentView == null)) {
                    if (this.invalidateAll) {
                        this.parentView.invalidate();
                    } else {
                        this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                    }
                }
            }
        } else if (this.currentKey == null || !str.equals(this.currentKey)) {
            return false;
        } else {
            if (!(bitmapDrawable instanceof AnimatedFileDrawable)) {
                ImageLoader.getInstance().incrementUseCount(this.currentKey);
            }
            this.currentImage = bitmapDrawable;
            if (this.roundRadius == 0 || !(bitmapDrawable instanceof BitmapDrawable)) {
                this.bitmapShader = null;
            } else if (bitmapDrawable instanceof AnimatedFileDrawable) {
                ((AnimatedFileDrawable) bitmapDrawable).setRoundRadius(this.roundRadius);
            } else {
                this.bitmapShader = new BitmapShader(bitmapDrawable.getBitmap(), TileMode.CLAMP, TileMode.CLAMP);
            }
            if ((z2 || this.forcePreview) && !this.forceCrossfade) {
                this.currentAlpha = 1.0f;
            } else if ((this.currentThumb == null && this.staticThumb == null) || this.currentAlpha == 1.0f || this.forceCrossfade) {
                this.currentAlpha = BitmapDescriptorFactory.HUE_RED;
                this.lastUpdateAlphaTime = System.currentTimeMillis();
                z4 = (this.currentThumb == null && this.staticThumb == null) ? false : true;
                this.crossfadeWithThumb = z4;
            }
            if (bitmapDrawable instanceof AnimatedFileDrawable) {
                AnimatedFileDrawable animatedFileDrawable = (AnimatedFileDrawable) bitmapDrawable;
                animatedFileDrawable.setParentView(this.parentView);
                if (this.allowStartAnimation) {
                    animatedFileDrawable.start();
                } else {
                    animatedFileDrawable.setAllowDecodeSingleFrame(this.allowDecodeSingleFrame);
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
            z4 = (this.currentImage == null && this.currentThumb == null && this.staticThumb == null) ? false : true;
            if (this.currentImage == null) {
                z3 = true;
            }
            imageReceiverDelegate.didSetImage(this, z4, z3);
        }
        return true;
    }

    public void setImageCoords(int i, int i2, int i3, int i4) {
        this.imageX = i;
        this.imageY = i2;
        this.imageW = i3;
        this.imageH = i4;
    }

    public void setImageWidth(int i) {
        this.imageW = i;
    }

    public void setImageX(int i) {
        this.imageX = i;
    }

    public void setImageY(int i) {
        this.imageY = i;
    }

    public void setInvalidateAll(boolean z) {
        this.invalidateAll = z;
    }

    public void setManualAlphaAnimator(boolean z) {
        this.manualAlphaAnimator = z;
    }

    public void setNeedsQualityThumb(boolean z) {
        this.needsQualityThumb = z;
        if (this.needsQualityThumb) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.messageThumbGenerated);
        } else {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messageThumbGenerated);
        }
    }

    public void setOrientation(int i, boolean z) {
        int i2 = i;
        while (i2 < 0) {
            i2 += 360;
        }
        while (i2 > 360) {
            i2 -= 360;
        }
        this.orientation = i2;
        this.centerRotation = z;
    }

    public void setParam(int i) {
        this.param = i;
    }

    public void setParentMessageObject(MessageObject messageObject) {
        this.parentMessageObject = messageObject;
    }

    public void setParentView(View view) {
        this.parentView = view;
        if (this.currentImage instanceof AnimatedFileDrawable) {
            ((AnimatedFileDrawable) this.currentImage).setParentView(this.parentView);
        }
    }

    public void setPressed(int i) {
        this.isPressed = i;
    }

    public void setRoundRadius(int i) {
        this.roundRadius = i;
    }

    public void setShouldGenerateQualityThumb(boolean z) {
        this.shouldGenerateQualityThumb = z;
    }

    protected void setTag(Integer num, boolean z) {
        if (z) {
            this.thumbTag = num;
        } else {
            this.tag = num;
        }
    }

    public void setVisible(boolean z, boolean z2) {
        if (this.isVisible != z) {
            this.isVisible = z;
            if (z2 && this.parentView != null) {
                if (this.invalidateAll) {
                    this.parentView.invalidate();
                } else {
                    this.parentView.invalidate(this.imageX, this.imageY, this.imageX + this.imageW, this.imageY + this.imageH);
                }
            }
        }
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
}
