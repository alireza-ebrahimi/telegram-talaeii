package org.telegram.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

public class PhotoCropActivity extends BaseFragment {
    private static final int done_button = 1;
    private String bitmapKey;
    private PhotoEditActivityDelegate delegate = null;
    private boolean doneButtonPressed = false;
    private BitmapDrawable drawable;
    private Bitmap imageToCrop;
    private boolean sameBitmap = false;
    private PhotoCropView view;

    public interface PhotoEditActivityDelegate {
        void didFinishEdit(Bitmap bitmap);
    }

    /* renamed from: org.telegram.ui.PhotoCropActivity$1 */
    class C50301 extends ActionBarMenuOnItemClick {
        C50301() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PhotoCropActivity.this.finishFragment();
            } else if (i == 1) {
                if (!(PhotoCropActivity.this.delegate == null || PhotoCropActivity.this.doneButtonPressed)) {
                    Bitmap bitmap = PhotoCropActivity.this.view.getBitmap();
                    if (bitmap == PhotoCropActivity.this.imageToCrop) {
                        PhotoCropActivity.this.sameBitmap = true;
                    }
                    PhotoCropActivity.this.delegate.didFinishEdit(bitmap);
                    PhotoCropActivity.this.doneButtonPressed = true;
                }
                PhotoCropActivity.this.finishFragment();
            }
        }
    }

    private class PhotoCropView extends FrameLayout {
        int bitmapHeight;
        int bitmapWidth;
        int bitmapX;
        int bitmapY;
        Paint circlePaint = null;
        int draggingState = 0;
        boolean freeform;
        Paint halfPaint = null;
        float oldX = BitmapDescriptorFactory.HUE_RED;
        float oldY = BitmapDescriptorFactory.HUE_RED;
        Paint rectPaint = null;
        float rectSizeX = 600.0f;
        float rectSizeY = 600.0f;
        float rectX = -1.0f;
        float rectY = -1.0f;
        int viewHeight;
        int viewWidth;

        /* renamed from: org.telegram.ui.PhotoCropActivity$PhotoCropView$1 */
        class C50311 implements OnTouchListener {
            C50311() {
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int dp = AndroidUtilities.dp(14.0f);
                if (motionEvent.getAction() == 0) {
                    if (PhotoCropView.this.rectX - ((float) dp) < x && PhotoCropView.this.rectX + ((float) dp) > x && PhotoCropView.this.rectY - ((float) dp) < y && PhotoCropView.this.rectY + ((float) dp) > y) {
                        PhotoCropView.this.draggingState = 1;
                    } else if ((PhotoCropView.this.rectX - ((float) dp)) + PhotoCropView.this.rectSizeX < x && (PhotoCropView.this.rectX + ((float) dp)) + PhotoCropView.this.rectSizeX > x && PhotoCropView.this.rectY - ((float) dp) < y && PhotoCropView.this.rectY + ((float) dp) > y) {
                        PhotoCropView.this.draggingState = 2;
                    } else if (PhotoCropView.this.rectX - ((float) dp) >= x || PhotoCropView.this.rectX + ((float) dp) <= x || (PhotoCropView.this.rectY - ((float) dp)) + PhotoCropView.this.rectSizeY >= y || (PhotoCropView.this.rectY + ((float) dp)) + PhotoCropView.this.rectSizeY <= y) {
                        if ((PhotoCropView.this.rectX - ((float) dp)) + PhotoCropView.this.rectSizeX < x && (PhotoCropView.this.rectX + ((float) dp)) + PhotoCropView.this.rectSizeX > x && (PhotoCropView.this.rectY - ((float) dp)) + PhotoCropView.this.rectSizeY < y) {
                            if ((((float) dp) + PhotoCropView.this.rectY) + PhotoCropView.this.rectSizeY > y) {
                                PhotoCropView.this.draggingState = 4;
                            }
                        }
                        if (PhotoCropView.this.rectX >= x || PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX <= x || PhotoCropView.this.rectY >= y || PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY <= y) {
                            PhotoCropView.this.draggingState = 0;
                        } else {
                            PhotoCropView.this.draggingState = 5;
                        }
                    } else {
                        PhotoCropView.this.draggingState = 3;
                    }
                    if (PhotoCropView.this.draggingState != 0) {
                        PhotoCropView.this.requestDisallowInterceptTouchEvent(true);
                    }
                    PhotoCropView.this.oldX = x;
                    PhotoCropView.this.oldY = y;
                } else if (motionEvent.getAction() == 1) {
                    PhotoCropView.this.draggingState = 0;
                } else if (motionEvent.getAction() == 2 && PhotoCropView.this.draggingState != 0) {
                    float f = x - PhotoCropView.this.oldX;
                    float f2 = y - PhotoCropView.this.oldY;
                    PhotoCropView photoCropView;
                    PhotoCropView photoCropView2;
                    if (PhotoCropView.this.draggingState == 5) {
                        photoCropView = PhotoCropView.this;
                        photoCropView.rectX = f + photoCropView.rectX;
                        photoCropView2 = PhotoCropView.this;
                        photoCropView2.rectY = f2 + photoCropView2.rectY;
                        if (PhotoCropView.this.rectX < ((float) PhotoCropView.this.bitmapX)) {
                            PhotoCropView.this.rectX = (float) PhotoCropView.this.bitmapX;
                        } else if (PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX > ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth))) {
                            PhotoCropView.this.rectX = ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth)) - PhotoCropView.this.rectSizeX;
                        }
                        if (PhotoCropView.this.rectY < ((float) PhotoCropView.this.bitmapY)) {
                            PhotoCropView.this.rectY = (float) PhotoCropView.this.bitmapY;
                        } else if (PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                            PhotoCropView.this.rectY = ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectSizeY;
                        }
                    } else if (PhotoCropView.this.draggingState == 1) {
                        if (PhotoCropView.this.rectSizeX - f < 160.0f) {
                            f = PhotoCropView.this.rectSizeX - 160.0f;
                        }
                        if (PhotoCropView.this.rectX + f < ((float) PhotoCropView.this.bitmapX)) {
                            f = ((float) PhotoCropView.this.bitmapX) - PhotoCropView.this.rectX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if (PhotoCropView.this.rectSizeY - f2 < 160.0f) {
                                f2 = PhotoCropView.this.rectSizeY - 160.0f;
                            }
                            if (PhotoCropView.this.rectY + f2 < ((float) PhotoCropView.this.bitmapY)) {
                                f2 = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += f;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY += f2;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= f;
                            photoCropView2 = PhotoCropView.this;
                            photoCropView2.rectSizeY -= f2;
                        } else {
                            if (PhotoCropView.this.rectY + f < ((float) PhotoCropView.this.bitmapY)) {
                                f = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            r1 = PhotoCropView.this;
                            r1.rectX += f;
                            r1 = PhotoCropView.this;
                            r1.rectY += f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeX -= f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeY -= f;
                        }
                    } else if (PhotoCropView.this.draggingState == 2) {
                        if (PhotoCropView.this.rectSizeX + f < 160.0f) {
                            f = -(PhotoCropView.this.rectSizeX - 160.0f);
                        }
                        if ((PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX) + f > ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth))) {
                            f = (((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth)) - PhotoCropView.this.rectX) - PhotoCropView.this.rectSizeX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if (PhotoCropView.this.rectSizeY - f2 < 160.0f) {
                                f2 = PhotoCropView.this.rectSizeY - 160.0f;
                            }
                            if (PhotoCropView.this.rectY + f2 < ((float) PhotoCropView.this.bitmapY)) {
                                f2 = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY += f2;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX = f + photoCropView.rectSizeX;
                            photoCropView2 = PhotoCropView.this;
                            photoCropView2.rectSizeY -= f2;
                        } else {
                            if (PhotoCropView.this.rectY - f < ((float) PhotoCropView.this.bitmapY)) {
                                f = PhotoCropView.this.rectY - ((float) PhotoCropView.this.bitmapY);
                            }
                            r1 = PhotoCropView.this;
                            r1.rectY -= f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeX += f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeY = f + r1.rectSizeY;
                        }
                    } else if (PhotoCropView.this.draggingState == 3) {
                        if (PhotoCropView.this.rectSizeX - f < 160.0f) {
                            f = PhotoCropView.this.rectSizeX - 160.0f;
                        }
                        if (PhotoCropView.this.rectX + f < ((float) PhotoCropView.this.bitmapX)) {
                            f = ((float) PhotoCropView.this.bitmapX) - PhotoCropView.this.rectX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY) + f2 > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                f2 = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += f;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= f;
                            photoCropView2 = PhotoCropView.this;
                            photoCropView2.rectSizeY = f2 + photoCropView2.rectSizeY;
                            if (PhotoCropView.this.rectSizeY < 160.0f) {
                                PhotoCropView.this.rectSizeY = 160.0f;
                            }
                        } else {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) - f > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                f = ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) - ((float) PhotoCropView.this.bitmapY)) - ((float) PhotoCropView.this.bitmapHeight);
                            }
                            r1 = PhotoCropView.this;
                            r1.rectX += f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeX -= f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeY -= f;
                        }
                    } else if (PhotoCropView.this.draggingState == 4) {
                        if ((PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX) + f > ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth))) {
                            f = (((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth)) - PhotoCropView.this.rectX) - PhotoCropView.this.rectSizeX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY) + f2 > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                f2 = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX = f + photoCropView.rectSizeX;
                            photoCropView2 = PhotoCropView.this;
                            photoCropView2.rectSizeY = f2 + photoCropView2.rectSizeY;
                        } else {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) + f > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                f = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeX;
                            }
                            r1 = PhotoCropView.this;
                            r1.rectSizeX += f;
                            r1 = PhotoCropView.this;
                            r1.rectSizeY = f + r1.rectSizeY;
                        }
                        if (PhotoCropView.this.rectSizeX < 160.0f) {
                            PhotoCropView.this.rectSizeX = 160.0f;
                        }
                        if (PhotoCropView.this.rectSizeY < 160.0f) {
                            PhotoCropView.this.rectSizeY = 160.0f;
                        }
                    }
                    PhotoCropView.this.oldX = x;
                    PhotoCropView.this.oldY = y;
                    PhotoCropView.this.invalidate();
                }
                return true;
            }
        }

        public PhotoCropView(Context context) {
            super(context);
            init();
        }

        private void init() {
            this.rectPaint = new Paint();
            this.rectPaint.setColor(1073412858);
            this.rectPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            this.rectPaint.setStyle(Style.STROKE);
            this.circlePaint = new Paint();
            this.circlePaint.setColor(-1);
            this.halfPaint = new Paint();
            this.halfPaint.setColor(-939524096);
            setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
            setOnTouchListener(new C50311());
        }

        private void updateBitmapSize() {
            if (this.viewWidth != 0 && this.viewHeight != 0 && PhotoCropActivity.this.imageToCrop != null) {
                float f = (this.rectX - ((float) this.bitmapX)) / ((float) this.bitmapWidth);
                float f2 = (this.rectY - ((float) this.bitmapY)) / ((float) this.bitmapHeight);
                float f3 = this.rectSizeX / ((float) this.bitmapWidth);
                float f4 = this.rectSizeY / ((float) this.bitmapHeight);
                float width = (float) PhotoCropActivity.this.imageToCrop.getWidth();
                float height = (float) PhotoCropActivity.this.imageToCrop.getHeight();
                float f5 = ((float) this.viewWidth) / width;
                float f6 = ((float) this.viewHeight) / height;
                if (f5 > f6) {
                    this.bitmapHeight = this.viewHeight;
                    this.bitmapWidth = (int) Math.ceil((double) (width * f6));
                } else {
                    this.bitmapWidth = this.viewWidth;
                    this.bitmapHeight = (int) Math.ceil((double) (height * f5));
                }
                this.bitmapX = ((this.viewWidth - this.bitmapWidth) / 2) + AndroidUtilities.dp(14.0f);
                this.bitmapY = ((this.viewHeight - this.bitmapHeight) / 2) + AndroidUtilities.dp(14.0f);
                if (this.rectX != -1.0f || this.rectY != -1.0f) {
                    this.rectX = (f * ((float) this.bitmapWidth)) + ((float) this.bitmapX);
                    this.rectY = (((float) this.bitmapHeight) * f2) + ((float) this.bitmapY);
                    this.rectSizeX = ((float) this.bitmapWidth) * f3;
                    this.rectSizeY = ((float) this.bitmapHeight) * f4;
                } else if (this.freeform) {
                    this.rectY = (float) this.bitmapY;
                    this.rectX = (float) this.bitmapX;
                    this.rectSizeX = (float) this.bitmapWidth;
                    this.rectSizeY = (float) this.bitmapHeight;
                } else if (this.bitmapWidth > this.bitmapHeight) {
                    this.rectY = (float) this.bitmapY;
                    this.rectX = (float) (((this.viewWidth - this.bitmapHeight) / 2) + AndroidUtilities.dp(14.0f));
                    this.rectSizeX = (float) this.bitmapHeight;
                    this.rectSizeY = (float) this.bitmapHeight;
                } else {
                    this.rectX = (float) this.bitmapX;
                    this.rectY = (float) (((this.viewHeight - this.bitmapWidth) / 2) + AndroidUtilities.dp(14.0f));
                    this.rectSizeX = (float) this.bitmapWidth;
                    this.rectSizeY = (float) this.bitmapWidth;
                }
                invalidate();
            }
        }

        public Bitmap getBitmap() {
            int i = 0;
            int width = (int) (((this.rectX - ((float) this.bitmapX)) / ((float) this.bitmapWidth)) * ((float) PhotoCropActivity.this.imageToCrop.getWidth()));
            int height = (int) (((float) PhotoCropActivity.this.imageToCrop.getHeight()) * ((this.rectY - ((float) this.bitmapY)) / ((float) this.bitmapHeight)));
            int width2 = (int) (((float) PhotoCropActivity.this.imageToCrop.getWidth()) * (this.rectSizeX / ((float) this.bitmapWidth)));
            int width3 = (int) (((float) PhotoCropActivity.this.imageToCrop.getWidth()) * (this.rectSizeY / ((float) this.bitmapWidth)));
            if (width < 0) {
                width = 0;
            }
            if (height >= 0) {
                i = height;
            }
            height = width + width2 > PhotoCropActivity.this.imageToCrop.getWidth() ? PhotoCropActivity.this.imageToCrop.getWidth() - width : width2;
            if (i + width3 > PhotoCropActivity.this.imageToCrop.getHeight()) {
                width3 = PhotoCropActivity.this.imageToCrop.getHeight() - i;
            }
            try {
                return Bitmaps.createBitmap(PhotoCropActivity.this.imageToCrop, width, i, height, width3);
            } catch (Throwable th) {
                FileLog.e(th);
                return null;
            }
        }

        protected void onDraw(Canvas canvas) {
            if (PhotoCropActivity.this.drawable != null) {
                try {
                    PhotoCropActivity.this.drawable.setBounds(this.bitmapX, this.bitmapY, this.bitmapX + this.bitmapWidth, this.bitmapY + this.bitmapHeight);
                    PhotoCropActivity.this.drawable.draw(canvas);
                } catch (Throwable th) {
                    FileLog.e(th);
                }
            }
            canvas.drawRect((float) this.bitmapX, (float) this.bitmapY, (float) (this.bitmapX + this.bitmapWidth), this.rectY, this.halfPaint);
            Canvas canvas2 = canvas;
            canvas2.drawRect((float) this.bitmapX, this.rectY, this.rectX, this.rectSizeY + this.rectY, this.halfPaint);
            canvas2 = canvas;
            canvas2.drawRect(this.rectSizeX + this.rectX, this.rectY, (float) (this.bitmapX + this.bitmapWidth), this.rectSizeY + this.rectY, this.halfPaint);
            canvas2 = canvas;
            canvas2.drawRect((float) this.bitmapX, this.rectSizeY + this.rectY, (float) (this.bitmapX + this.bitmapWidth), (float) (this.bitmapY + this.bitmapHeight), this.halfPaint);
            canvas2 = canvas;
            canvas2.drawRect(this.rectX, this.rectY, this.rectSizeX + this.rectX, this.rectSizeY + this.rectY, this.rectPaint);
            int dp = AndroidUtilities.dp(1.0f);
            canvas2 = canvas;
            canvas2.drawRect(((float) dp) + this.rectX, ((float) dp) + this.rectY, ((float) AndroidUtilities.dp(20.0f)) + (this.rectX + ((float) dp)), ((float) (dp * 3)) + this.rectY, this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) dp) + this.rectX, ((float) dp) + this.rectY, ((float) (dp * 3)) + this.rectX, ((float) AndroidUtilities.dp(20.0f)) + (this.rectY + ((float) dp)), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((this.rectX + this.rectSizeX) - ((float) dp)) - ((float) AndroidUtilities.dp(20.0f)), ((float) dp) + this.rectY, (this.rectX + this.rectSizeX) - ((float) dp), ((float) (dp * 3)) + this.rectY, this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect((this.rectX + this.rectSizeX) - ((float) (dp * 3)), ((float) dp) + this.rectY, (this.rectX + this.rectSizeX) - ((float) dp), ((float) AndroidUtilities.dp(20.0f)) + (this.rectY + ((float) dp)), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) dp) + this.rectX, ((this.rectY + this.rectSizeY) - ((float) dp)) - ((float) AndroidUtilities.dp(20.0f)), ((float) (dp * 3)) + this.rectX, (this.rectY + this.rectSizeY) - ((float) dp), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) dp) + this.rectX, (this.rectY + this.rectSizeY) - ((float) (dp * 3)), ((float) AndroidUtilities.dp(20.0f)) + (this.rectX + ((float) dp)), (this.rectY + this.rectSizeY) - ((float) dp), this.circlePaint);
            canvas.drawRect(((this.rectX + this.rectSizeX) - ((float) dp)) - ((float) AndroidUtilities.dp(20.0f)), (this.rectY + this.rectSizeY) - ((float) (dp * 3)), (this.rectX + this.rectSizeX) - ((float) dp), (this.rectY + this.rectSizeY) - ((float) dp), this.circlePaint);
            canvas.drawRect((this.rectX + this.rectSizeX) - ((float) (dp * 3)), ((this.rectY + this.rectSizeY) - ((float) dp)) - ((float) AndroidUtilities.dp(20.0f)), (this.rectX + this.rectSizeX) - ((float) dp), (this.rectY + this.rectSizeY) - ((float) dp), this.circlePaint);
            for (int i = 1; i < 3; i++) {
                canvas2 = canvas;
                canvas2.drawRect(((this.rectSizeX / 3.0f) * ((float) i)) + this.rectX, ((float) dp) + this.rectY, ((this.rectSizeX / 3.0f) * ((float) i)) + (this.rectX + ((float) dp)), (this.rectY + this.rectSizeY) - ((float) dp), this.circlePaint);
                canvas2 = canvas;
                canvas2.drawRect(((float) dp) + this.rectX, ((this.rectSizeY / 3.0f) * ((float) i)) + this.rectY, this.rectSizeX + (this.rectX - ((float) dp)), ((float) dp) + (this.rectY + ((this.rectSizeY / 3.0f) * ((float) i))), this.circlePaint);
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.viewWidth = (i3 - i) - AndroidUtilities.dp(28.0f);
            this.viewHeight = (i4 - i2) - AndroidUtilities.dp(28.0f);
            updateBitmapSize();
        }
    }

    public PhotoCropActivity(Bundle bundle) {
        super(bundle);
    }

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, false);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("CropImage", R.string.CropImage));
        this.actionBar.setActionBarMenuOnItemClick(new C50301());
        this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        View photoCropView = new PhotoCropView(context);
        this.view = photoCropView;
        this.fragmentView = photoCropView;
        ((PhotoCropView) this.fragmentView).freeform = getArguments().getBoolean("freeform", false);
        this.fragmentView.setLayoutParams(new LayoutParams(-1, -1));
        return this.fragmentView;
    }

    public boolean onFragmentCreate() {
        this.swipeBackEnabled = false;
        if (this.imageToCrop == null) {
            String string = getArguments().getString("photoPath");
            Uri uri = (Uri) getArguments().getParcelable("photoUri");
            if (string == null && uri == null) {
                return false;
            }
            if (string != null && !new File(string).exists()) {
                return false;
            }
            int dp = AndroidUtilities.isTablet() ? AndroidUtilities.dp(520.0f) : Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y);
            this.imageToCrop = ImageLoader.loadBitmap(string, uri, (float) dp, (float) dp, true);
            if (this.imageToCrop == null) {
                return false;
            }
        }
        this.drawable = new BitmapDrawable(this.imageToCrop);
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (!(this.bitmapKey == null || !ImageLoader.getInstance().decrementUseCount(this.bitmapKey) || ImageLoader.getInstance().isInCache(this.bitmapKey))) {
            this.bitmapKey = null;
        }
        if (!(this.bitmapKey != null || this.imageToCrop == null || this.sameBitmap)) {
            this.imageToCrop.recycle();
            this.imageToCrop = null;
        }
        this.drawable = null;
    }

    public void setDelegate(PhotoEditActivityDelegate photoEditActivityDelegate) {
        this.delegate = photoEditActivityDelegate;
    }
}
