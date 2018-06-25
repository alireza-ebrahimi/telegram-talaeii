package net.hockeyapp.android.views;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.internal.view.SupportMenu;
import android.view.MotionEvent;
import android.widget.ImageView;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
import net.hockeyapp.android.utils.HockeyLog;

@SuppressLint({"ViewConstructor"})
public class PaintView extends ImageView {
    private static final float TOUCH_TOLERANCE = 4.0f;
    private float mX;
    private float mY;
    private Paint paint = new Paint();
    private Path path = new Path();
    private Stack<Path> paths = new Stack();

    /* renamed from: net.hockeyapp.android.views.PaintView$1 */
    class C09951 extends AsyncTask<Object, Void, Bitmap> {
        C09951() {
        }

        protected void onPreExecute() {
            PaintView.this.setAdjustViewBounds(true);
        }

        protected Bitmap doInBackground(Object... args) {
            try {
                return PaintView.decodeSampledBitmapFromResource(args[0].getContentResolver(), args[1], args[2].intValue(), args[3].intValue());
            } catch (Throwable e) {
                HockeyLog.error("Could not load image into ImageView.", e);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bm) {
            if (bm != null) {
                PaintView.this.setImageBitmap(bm);
            }
        }
    }

    public static int determineOrientation(ContentResolver resolver, Uri imageUri) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(resolver.openInputStream(imageUri), null, options);
            if (((float) options.outWidth) / ((float) options.outHeight) > 1.0f) {
                return 0;
            }
            return 1;
        } catch (Throwable e) {
            HockeyLog.error("Unable to determine necessary screen orientation.", e);
            return 1;
        }
    }

    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmapFromResource(ContentResolver resolver, Uri imageUri, int reqWidth, int reqHeight) throws IOException {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(resolver.openInputStream(imageUri), null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(resolver.openInputStream(imageUri), null, options);
    }

    public PaintView(Context context, Uri imageUri, int displayWidth, int displayHeight) {
        super(context);
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setColor(SupportMenu.CATEGORY_MASK);
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeJoin(Join.ROUND);
        this.paint.setStrokeCap(Cap.ROUND);
        this.paint.setStrokeWidth(12.0f);
        new C09951().execute(new Object[]{context, imageUri, Integer.valueOf(displayWidth), Integer.valueOf(displayHeight)});
    }

    public void clearImage() {
        this.paths.clear();
        invalidate();
    }

    public void undo() {
        if (!this.paths.empty()) {
            this.paths.pop();
            invalidate();
        }
    }

    public boolean isClear() {
        return this.paths.empty();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator it = this.paths.iterator();
        while (it.hasNext()) {
            canvas.drawPath((Path) it.next(), this.paint);
        }
        canvas.drawPath(this.path, this.paint);
    }

    private void touchStart(float x, float y) {
        this.path.reset();
        this.path.moveTo(x, y);
        this.mX = x;
        this.mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - this.mX);
        float dy = Math.abs(y - this.mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            this.path.quadTo(this.mX, this.mY, (this.mX + x) / 2.0f, (this.mY + y) / 2.0f);
            this.mX = x;
            this.mY = y;
        }
    }

    private void touchUp() {
        this.path.lineTo(this.mX, this.mY);
        this.paths.push(this.path);
        this.path = new Path();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case 0:
                touchStart(x, y);
                invalidate();
                break;
            case 1:
                touchUp();
                invalidate();
                break;
            case 2:
                touchMove(x, y);
                invalidate();
                break;
        }
        return true;
    }
}
