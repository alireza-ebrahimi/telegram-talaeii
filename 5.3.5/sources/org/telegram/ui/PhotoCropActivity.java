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
    class C31911 extends ActionBarMenuOnItemClick {
        C31911() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PhotoCropActivity.this.finishFragment();
            } else if (id == 1) {
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
        float oldX = 0.0f;
        float oldY = 0.0f;
        Paint rectPaint = null;
        float rectSizeX = 600.0f;
        float rectSizeY = 600.0f;
        float rectX = -1.0f;
        float rectY = -1.0f;
        int viewHeight;
        int viewWidth;

        /* renamed from: org.telegram.ui.PhotoCropActivity$PhotoCropView$1 */
        class C31921 implements OnTouchListener {
            C31921() {
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                int cornerSide = AndroidUtilities.dp(14.0f);
                if (motionEvent.getAction() == 0) {
                    if (PhotoCropView.this.rectX - ((float) cornerSide) < x && PhotoCropView.this.rectX + ((float) cornerSide) > x && PhotoCropView.this.rectY - ((float) cornerSide) < y && PhotoCropView.this.rectY + ((float) cornerSide) > y) {
                        PhotoCropView.this.draggingState = 1;
                    } else if ((PhotoCropView.this.rectX - ((float) cornerSide)) + PhotoCropView.this.rectSizeX < x && (PhotoCropView.this.rectX + ((float) cornerSide)) + PhotoCropView.this.rectSizeX > x && PhotoCropView.this.rectY - ((float) cornerSide) < y && PhotoCropView.this.rectY + ((float) cornerSide) > y) {
                        PhotoCropView.this.draggingState = 2;
                    } else if (PhotoCropView.this.rectX - ((float) cornerSide) < x && PhotoCropView.this.rectX + ((float) cornerSide) > x && (PhotoCropView.this.rectY - ((float) cornerSide)) + PhotoCropView.this.rectSizeY < y && (PhotoCropView.this.rectY + ((float) cornerSide)) + PhotoCropView.this.rectSizeY > y) {
                        PhotoCropView.this.draggingState = 3;
                    } else if ((PhotoCropView.this.rectX - ((float) cornerSide)) + PhotoCropView.this.rectSizeX < x && (PhotoCropView.this.rectX + ((float) cornerSide)) + PhotoCropView.this.rectSizeX > x && (PhotoCropView.this.rectY - ((float) cornerSide)) + PhotoCropView.this.rectSizeY < y && (PhotoCropView.this.rectY + ((float) cornerSide)) + PhotoCropView.this.rectSizeY > y) {
                        PhotoCropView.this.draggingState = 4;
                    } else if (PhotoCropView.this.rectX >= x || PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX <= x || PhotoCropView.this.rectY >= y || PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY <= y) {
                        PhotoCropView.this.draggingState = 0;
                    } else {
                        PhotoCropView.this.draggingState = 5;
                    }
                    if (PhotoCropView.this.draggingState != 0) {
                        PhotoCropView.this.requestDisallowInterceptTouchEvent(true);
                    }
                    PhotoCropView.this.oldX = x;
                    PhotoCropView.this.oldY = y;
                } else if (motionEvent.getAction() == 1) {
                    PhotoCropView.this.draggingState = 0;
                } else if (motionEvent.getAction() == 2 && PhotoCropView.this.draggingState != 0) {
                    float diffX = x - PhotoCropView.this.oldX;
                    float diffY = y - PhotoCropView.this.oldY;
                    PhotoCropView photoCropView;
                    if (PhotoCropView.this.draggingState == 5) {
                        photoCropView = PhotoCropView.this;
                        photoCropView.rectX += diffX;
                        photoCropView = PhotoCropView.this;
                        photoCropView.rectY += diffY;
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
                        if (PhotoCropView.this.rectSizeX - diffX < 160.0f) {
                            diffX = PhotoCropView.this.rectSizeX - 160.0f;
                        }
                        if (PhotoCropView.this.rectX + diffX < ((float) PhotoCropView.this.bitmapX)) {
                            diffX = ((float) PhotoCropView.this.bitmapX) - PhotoCropView.this.rectX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if (PhotoCropView.this.rectSizeY - diffY < 160.0f) {
                                diffY = PhotoCropView.this.rectSizeY - 160.0f;
                            }
                            if (PhotoCropView.this.rectY + diffY < ((float) PhotoCropView.this.bitmapY)) {
                                diffY = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY += diffY;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY -= diffY;
                        } else {
                            if (PhotoCropView.this.rectY + diffX < ((float) PhotoCropView.this.bitmapY)) {
                                diffX = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY -= diffX;
                        }
                    } else if (PhotoCropView.this.draggingState == 2) {
                        if (PhotoCropView.this.rectSizeX + diffX < 160.0f) {
                            diffX = -(PhotoCropView.this.rectSizeX - 160.0f);
                        }
                        if ((PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX) + diffX > ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth))) {
                            diffX = (((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth)) - PhotoCropView.this.rectX) - PhotoCropView.this.rectSizeX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if (PhotoCropView.this.rectSizeY - diffY < 160.0f) {
                                diffY = PhotoCropView.this.rectSizeY - 160.0f;
                            }
                            if (PhotoCropView.this.rectY + diffY < ((float) PhotoCropView.this.bitmapY)) {
                                diffY = ((float) PhotoCropView.this.bitmapY) - PhotoCropView.this.rectY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY += diffY;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY -= diffY;
                        } else {
                            if (PhotoCropView.this.rectY - diffX < ((float) PhotoCropView.this.bitmapY)) {
                                diffX = PhotoCropView.this.rectY - ((float) PhotoCropView.this.bitmapY);
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectY -= diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY += diffX;
                        }
                    } else if (PhotoCropView.this.draggingState == 3) {
                        if (PhotoCropView.this.rectSizeX - diffX < 160.0f) {
                            diffX = PhotoCropView.this.rectSizeX - 160.0f;
                        }
                        if (PhotoCropView.this.rectX + diffX < ((float) PhotoCropView.this.bitmapX)) {
                            diffX = ((float) PhotoCropView.this.bitmapX) - PhotoCropView.this.rectX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY) + diffY > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                diffY = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY += diffY;
                            if (PhotoCropView.this.rectSizeY < 160.0f) {
                                PhotoCropView.this.rectSizeY = 160.0f;
                            }
                        } else {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) - diffX > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                diffX = ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) - ((float) PhotoCropView.this.bitmapY)) - ((float) PhotoCropView.this.bitmapHeight);
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX -= diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY -= diffX;
                        }
                    } else if (PhotoCropView.this.draggingState == 4) {
                        if ((PhotoCropView.this.rectX + PhotoCropView.this.rectSizeX) + diffX > ((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth))) {
                            diffX = (((float) (PhotoCropView.this.bitmapX + PhotoCropView.this.bitmapWidth)) - PhotoCropView.this.rectX) - PhotoCropView.this.rectSizeX;
                        }
                        if (PhotoCropView.this.freeform) {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeY) + diffY > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                diffY = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeY;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY += diffY;
                        } else {
                            if ((PhotoCropView.this.rectY + PhotoCropView.this.rectSizeX) + diffX > ((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight))) {
                                diffX = (((float) (PhotoCropView.this.bitmapY + PhotoCropView.this.bitmapHeight)) - PhotoCropView.this.rectY) - PhotoCropView.this.rectSizeX;
                            }
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeX += diffX;
                            photoCropView = PhotoCropView.this;
                            photoCropView.rectSizeY += diffX;
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
            setOnTouchListener(new C31921());
        }

        private void updateBitmapSize() {
            if (this.viewWidth != 0 && this.viewHeight != 0 && PhotoCropActivity.this.imageToCrop != null) {
                float percX = (this.rectX - ((float) this.bitmapX)) / ((float) this.bitmapWidth);
                float percY = (this.rectY - ((float) this.bitmapY)) / ((float) this.bitmapHeight);
                float percSizeX = this.rectSizeX / ((float) this.bitmapWidth);
                float percSizeY = this.rectSizeY / ((float) this.bitmapHeight);
                float w = (float) PhotoCropActivity.this.imageToCrop.getWidth();
                float h = (float) PhotoCropActivity.this.imageToCrop.getHeight();
                float scaleX = ((float) this.viewWidth) / w;
                float scaleY = ((float) this.viewHeight) / h;
                if (scaleX > scaleY) {
                    this.bitmapHeight = this.viewHeight;
                    this.bitmapWidth = (int) Math.ceil((double) (w * scaleY));
                } else {
                    this.bitmapWidth = this.viewWidth;
                    this.bitmapHeight = (int) Math.ceil((double) (h * scaleX));
                }
                this.bitmapX = ((this.viewWidth - this.bitmapWidth) / 2) + AndroidUtilities.dp(14.0f);
                this.bitmapY = ((this.viewHeight - this.bitmapHeight) / 2) + AndroidUtilities.dp(14.0f);
                if (this.rectX != -1.0f || this.rectY != -1.0f) {
                    this.rectX = (((float) this.bitmapWidth) * percX) + ((float) this.bitmapX);
                    this.rectY = (((float) this.bitmapHeight) * percY) + ((float) this.bitmapY);
                    this.rectSizeX = ((float) this.bitmapWidth) * percSizeX;
                    this.rectSizeY = ((float) this.bitmapHeight) * percSizeY;
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

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            this.viewWidth = (right - left) - AndroidUtilities.dp(28.0f);
            this.viewHeight = (bottom - top) - AndroidUtilities.dp(28.0f);
            updateBitmapSize();
        }

        public Bitmap getBitmap() {
            int x = (int) (((float) PhotoCropActivity.this.imageToCrop.getWidth()) * ((this.rectX - ((float) this.bitmapX)) / ((float) this.bitmapWidth)));
            int y = (int) (((float) PhotoCropActivity.this.imageToCrop.getHeight()) * ((this.rectY - ((float) this.bitmapY)) / ((float) this.bitmapHeight)));
            int sizeX = (int) (((float) PhotoCropActivity.this.imageToCrop.getWidth()) * (this.rectSizeX / ((float) this.bitmapWidth)));
            int sizeY = (int) (((float) PhotoCropActivity.this.imageToCrop.getWidth()) * (this.rectSizeY / ((float) this.bitmapWidth)));
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x + sizeX > PhotoCropActivity.this.imageToCrop.getWidth()) {
                sizeX = PhotoCropActivity.this.imageToCrop.getWidth() - x;
            }
            if (y + sizeY > PhotoCropActivity.this.imageToCrop.getHeight()) {
                sizeY = PhotoCropActivity.this.imageToCrop.getHeight() - y;
            }
            try {
                return Bitmaps.createBitmap(PhotoCropActivity.this.imageToCrop, x, y, sizeX, sizeY);
            } catch (Throwable e2) {
                FileLog.e(e2);
                return null;
            }
        }

        protected void onDraw(Canvas canvas) {
            if (PhotoCropActivity.this.drawable != null) {
                try {
                    PhotoCropActivity.this.drawable.setBounds(this.bitmapX, this.bitmapY, this.bitmapX + this.bitmapWidth, this.bitmapY + this.bitmapHeight);
                    PhotoCropActivity.this.drawable.draw(canvas);
                } catch (Throwable e) {
                    FileLog.e(e);
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
            int side = AndroidUtilities.dp(1.0f);
            canvas2 = canvas;
            canvas2.drawRect(((float) side) + this.rectX, ((float) side) + this.rectY, ((float) AndroidUtilities.dp(20.0f)) + (this.rectX + ((float) side)), ((float) (side * 3)) + this.rectY, this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) side) + this.rectX, ((float) side) + this.rectY, ((float) (side * 3)) + this.rectX, ((float) AndroidUtilities.dp(20.0f)) + (this.rectY + ((float) side)), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((this.rectX + this.rectSizeX) - ((float) side)) - ((float) AndroidUtilities.dp(20.0f)), ((float) side) + this.rectY, (this.rectX + this.rectSizeX) - ((float) side), ((float) (side * 3)) + this.rectY, this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect((this.rectX + this.rectSizeX) - ((float) (side * 3)), ((float) side) + this.rectY, (this.rectX + this.rectSizeX) - ((float) side), ((float) AndroidUtilities.dp(20.0f)) + (this.rectY + ((float) side)), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) side) + this.rectX, ((this.rectY + this.rectSizeY) - ((float) side)) - ((float) AndroidUtilities.dp(20.0f)), ((float) (side * 3)) + this.rectX, (this.rectY + this.rectSizeY) - ((float) side), this.circlePaint);
            canvas2 = canvas;
            canvas2.drawRect(((float) side) + this.rectX, (this.rectY + this.rectSizeY) - ((float) (side * 3)), ((float) AndroidUtilities.dp(20.0f)) + (this.rectX + ((float) side)), (this.rectY + this.rectSizeY) - ((float) side), this.circlePaint);
            canvas.drawRect(((this.rectX + this.rectSizeX) - ((float) side)) - ((float) AndroidUtilities.dp(20.0f)), (this.rectY + this.rectSizeY) - ((float) (side * 3)), (this.rectX + this.rectSizeX) - ((float) side), (this.rectY + this.rectSizeY) - ((float) side), this.circlePaint);
            canvas.drawRect((this.rectX + this.rectSizeX) - ((float) (side * 3)), ((this.rectY + this.rectSizeY) - ((float) side)) - ((float) AndroidUtilities.dp(20.0f)), (this.rectX + this.rectSizeX) - ((float) side), (this.rectY + this.rectSizeY) - ((float) side), this.circlePaint);
            for (int a = 1; a < 3; a++) {
                canvas2 = canvas;
                canvas2.drawRect(((this.rectSizeX / 3.0f) * ((float) a)) + this.rectX, ((float) side) + this.rectY, ((this.rectSizeX / 3.0f) * ((float) a)) + (this.rectX + ((float) side)), (this.rectY + this.rectSizeY) - ((float) side), this.circlePaint);
                canvas2 = canvas;
                canvas2.drawRect(((float) side) + this.rectX, ((this.rectSizeY / 3.0f) * ((float) a)) + this.rectY, this.rectSizeX + (this.rectX - ((float) side)), ((float) side) + (this.rectY + ((this.rectSizeY / 3.0f) * ((float) a))), this.circlePaint);
            }
        }
    }

    public PhotoCropActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        this.swipeBackEnabled = false;
        if (this.imageToCrop == null) {
            String photoPath = getArguments().getString("photoPath");
            Uri photoUri = (Uri) getArguments().getParcelable("photoUri");
            if (photoPath == null && photoUri == null) {
                return false;
            }
            if (photoPath != null && !new File(photoPath).exists()) {
                return false;
            }
            int size;
            if (AndroidUtilities.isTablet()) {
                size = AndroidUtilities.dp(520.0f);
            } else {
                size = Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y);
            }
            this.imageToCrop = ImageLoader.loadBitmap(photoPath, photoUri, (float) size, (float) size, true);
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

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.ACTION_BAR_MEDIA_PICKER_COLOR);
        this.actionBar.setItemsBackgroundColor(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, false);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("CropImage", R.string.CropImage));
        this.actionBar.setActionBarMenuOnItemClick(new C31911());
        this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        View photoCropView = new PhotoCropView(context);
        this.view = photoCropView;
        this.fragmentView = photoCropView;
        ((PhotoCropView) this.fragmentView).freeform = getArguments().getBoolean("freeform", false);
        this.fragmentView.setLayoutParams(new LayoutParams(-1, -1));
        return this.fragmentView;
    }

    public void setDelegate(PhotoEditActivityDelegate delegate) {
        this.delegate = delegate;
    }
}
