package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES20;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Render {
    private static RectF Draw(RenderState renderState) {
        RectF rectF = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        int count = renderState.getCount();
        if (count == 0) {
            return rectF;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(((count * 4) + ((count - 1) * 2)) * 20);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.position(0);
        renderState.setPosition(0);
        int i = 0;
        int i2 = 0;
        while (i2 < count) {
            int i3;
            float read = renderState.read();
            float read2 = renderState.read();
            float read3 = renderState.read();
            float read4 = renderState.read();
            float read5 = renderState.read();
            RectF rectF2 = new RectF(read - read3, read2 - read3, read + read3, read2 + read3);
            float[] fArr = new float[]{rectF2.left, rectF2.top, rectF2.right, rectF2.top, rectF2.left, rectF2.bottom, rectF2.right, rectF2.bottom};
            read = rectF2.centerX();
            read3 = rectF2.centerY();
            Matrix matrix = new Matrix();
            matrix.setRotate((float) Math.toDegrees((double) read4), read, read3);
            matrix.mapPoints(fArr);
            matrix.mapRect(rectF2);
            Utils.RectFIntegral(rectF2);
            rectF.union(rectF2);
            if (i != 0) {
                asFloatBuffer.put(fArr[0]);
                asFloatBuffer.put(fArr[1]);
                asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
                asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
                asFloatBuffer.put(read5);
                i3 = i + 1;
            } else {
                i3 = i;
            }
            asFloatBuffer.put(fArr[0]);
            asFloatBuffer.put(fArr[1]);
            asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
            asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
            asFloatBuffer.put(read5);
            i3++;
            asFloatBuffer.put(fArr[2]);
            asFloatBuffer.put(fArr[3]);
            asFloatBuffer.put(1.0f);
            asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
            asFloatBuffer.put(read5);
            i3++;
            asFloatBuffer.put(fArr[4]);
            asFloatBuffer.put(fArr[5]);
            asFloatBuffer.put(BitmapDescriptorFactory.HUE_RED);
            asFloatBuffer.put(1.0f);
            asFloatBuffer.put(read5);
            i3++;
            asFloatBuffer.put(fArr[6]);
            asFloatBuffer.put(fArr[7]);
            asFloatBuffer.put(1.0f);
            asFloatBuffer.put(1.0f);
            asFloatBuffer.put(read5);
            i3++;
            if (i2 != count - 1) {
                asFloatBuffer.put(fArr[6]);
                asFloatBuffer.put(fArr[7]);
                asFloatBuffer.put(1.0f);
                asFloatBuffer.put(1.0f);
                asFloatBuffer.put(read5);
                i3++;
            }
            i2++;
            i = i3;
        }
        asFloatBuffer.position(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 20, asFloatBuffer.slice());
        GLES20.glEnableVertexAttribArray(0);
        asFloatBuffer.position(2);
        GLES20.glVertexAttribPointer(1, 2, 5126, true, 20, asFloatBuffer.slice());
        GLES20.glEnableVertexAttribArray(1);
        asFloatBuffer.position(4);
        GLES20.glVertexAttribPointer(2, 1, 5126, true, 20, asFloatBuffer.slice());
        GLES20.glEnableVertexAttribArray(2);
        GLES20.glDrawArrays(5, 0, i);
        return rectF;
    }

    private static void PaintSegment(Point point, Point point2, RenderState renderState) {
        double distanceTo = (double) point.getDistanceTo(point2);
        Point substract = point2.substract(point);
        Point point3 = new Point(1.0d, 1.0d, 0.0d);
        float atan2 = Math.abs(renderState.angle) > BitmapDescriptorFactory.HUE_RED ? renderState.angle : (float) Math.atan2(substract.f10181y, substract.f10180x);
        float f = renderState.baseWeight * renderState.scale;
        double max = (double) Math.max(1.0f, renderState.spacing * f);
        Point multiplyByScalar = distanceTo > 0.0d ? substract.multiplyByScalar(1.0d / distanceTo) : point3;
        float min = Math.min(1.0f, renderState.alpha * 1.15f);
        boolean z = point.edge;
        boolean z2 = point2.edge;
        int ceil = (int) Math.ceil((distanceTo - renderState.remainder) / max);
        int count = renderState.getCount();
        renderState.appendValuesCount(ceil);
        renderState.setPosition(count);
        Point add = point.add(multiplyByScalar.multiplyByScalar(renderState.remainder));
        boolean z3 = true;
        boolean z4 = z;
        double d = renderState.remainder;
        while (d <= distanceTo) {
            z3 = renderState.addPoint(add.toPointF(), f, atan2, z4 ? min : renderState.alpha, -1);
            if (!z3) {
                break;
            }
            d += max;
            z4 = false;
            add = add.add(multiplyByScalar.multiplyByScalar(max));
        }
        if (z3 && z2) {
            renderState.appendValuesCount(1);
            renderState.addPoint(point2.toPointF(), f, atan2, min, -1);
        }
        renderState.remainder = d - distanceTo;
    }

    private static void PaintStamp(Point point, RenderState renderState) {
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = renderState.baseWeight * renderState.scale;
        PointF toPointF = point.toPointF();
        if (Math.abs(renderState.angle) > BitmapDescriptorFactory.HUE_RED) {
            f = renderState.angle;
        }
        float f3 = renderState.alpha;
        renderState.prepare();
        renderState.appendValuesCount(1);
        renderState.addPoint(toPointF, f2, f, f3, 0);
    }

    public static RectF RenderPath(Path path, RenderState renderState) {
        int i = 0;
        renderState.baseWeight = path.getBaseWeight();
        renderState.spacing = path.getBrush().getSpacing();
        renderState.alpha = path.getBrush().getAlpha();
        renderState.angle = path.getBrush().getAngle();
        renderState.scale = path.getBrush().getScale();
        int length = path.getLength();
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            PaintStamp(path.getPoints()[0], renderState);
        } else {
            Point[] points = path.getPoints();
            renderState.prepare();
            while (i < points.length - 1) {
                PaintSegment(points[i], points[i + 1], renderState);
                i++;
            }
        }
        path.remainder = renderState.remainder;
        return Draw(renderState);
    }
}
