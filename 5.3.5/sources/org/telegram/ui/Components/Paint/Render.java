package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Render {
    public static RectF RenderPath(Path path, RenderState state) {
        state.baseWeight = path.getBaseWeight();
        state.spacing = path.getBrush().getSpacing();
        state.alpha = path.getBrush().getAlpha();
        state.angle = path.getBrush().getAngle();
        state.scale = path.getBrush().getScale();
        int length = path.getLength();
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            PaintStamp(path.getPoints()[0], state);
        } else {
            Point[] points = path.getPoints();
            state.prepare();
            for (int i = 0; i < points.length - 1; i++) {
                PaintSegment(points[i], points[i + 1], state);
            }
        }
        path.remainder = state.remainder;
        return Draw(state);
    }

    private static void PaintSegment(Point lastPoint, Point point, RenderState state) {
        float vectorAngle;
        double distance = (double) lastPoint.getDistanceTo(point);
        Point vector = point.substract(lastPoint);
        Point unitVector = new Point(1.0d, 1.0d, 0.0d);
        if (Math.abs(state.angle) > 0.0f) {
            vectorAngle = state.angle;
        } else {
            vectorAngle = (float) Math.atan2(vector.f102y, vector.f101x);
        }
        float brushWeight = state.baseWeight * state.scale;
        double step = (double) Math.max(1.0f, state.spacing * brushWeight);
        if (distance > 0.0d) {
            unitVector = vector.multiplyByScalar(1.0d / distance);
        }
        float boldenedAlpha = Math.min(1.0f, state.alpha * 1.15f);
        boolean boldenHead = lastPoint.edge;
        boolean boldenTail = point.edge;
        int count = (int) Math.ceil((distance - state.remainder) / step);
        int currentCount = state.getCount();
        state.appendValuesCount(count);
        state.setPosition(currentCount);
        Point start = lastPoint.add(unitVector.multiplyByScalar(state.remainder));
        boolean succeed = true;
        double f = state.remainder;
        while (f <= distance) {
            succeed = state.addPoint(start.toPointF(), brushWeight, vectorAngle, boldenHead ? boldenedAlpha : state.alpha, -1);
            if (!succeed) {
                break;
            }
            start = start.add(unitVector.multiplyByScalar(step));
            boldenHead = false;
            f += step;
        }
        if (succeed && boldenTail) {
            state.appendValuesCount(1);
            state.addPoint(point.toPointF(), brushWeight, vectorAngle, boldenedAlpha, -1);
        }
        state.remainder = f - distance;
    }

    private static void PaintStamp(Point point, RenderState state) {
        float angle = 0.0f;
        float brushWeight = state.baseWeight * state.scale;
        PointF start = point.toPointF();
        if (Math.abs(state.angle) > 0.0f) {
            angle = state.angle;
        }
        float alpha = state.alpha;
        state.prepare();
        state.appendValuesCount(1);
        state.addPoint(start, brushWeight, angle, alpha, 0);
    }

    private static RectF Draw(RenderState state) {
        RectF rectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        int count = state.getCount();
        if (count != 0) {
            ByteBuffer bb = ByteBuffer.allocateDirect(20 * ((count * 4) + ((count - 1) * 2)));
            bb.order(ByteOrder.nativeOrder());
            FloatBuffer vertexData = bb.asFloatBuffer();
            vertexData.position(0);
            state.setPosition(0);
            int n = 0;
            for (int i = 0; i < count; i++) {
                float x = state.read();
                float y = state.read();
                float size = state.read();
                float angle = state.read();
                float alpha = state.read();
                rectF = new RectF(x - size, y - size, x + size, y + size);
                float[] points = new float[]{rectF.left, rectF.top, rectF.right, rectF.top, rectF.left, rectF.bottom, rectF.right, rectF.bottom};
                float centerX = rectF.centerX();
                float centerY = rectF.centerY();
                Matrix t = new Matrix();
                t.setRotate((float) Math.toDegrees((double) angle), centerX, centerY);
                t.mapPoints(points);
                t.mapRect(rectF);
                Utils.RectFIntegral(rectF);
                rectF.union(rectF);
                if (n != 0) {
                    vertexData.put(points[0]);
                    vertexData.put(points[1]);
                    vertexData.put(0.0f);
                    vertexData.put(0.0f);
                    vertexData.put(alpha);
                    n++;
                }
                vertexData.put(points[0]);
                vertexData.put(points[1]);
                vertexData.put(0.0f);
                vertexData.put(0.0f);
                vertexData.put(alpha);
                n++;
                vertexData.put(points[2]);
                vertexData.put(points[3]);
                vertexData.put(1.0f);
                vertexData.put(0.0f);
                vertexData.put(alpha);
                n++;
                vertexData.put(points[4]);
                vertexData.put(points[5]);
                vertexData.put(0.0f);
                vertexData.put(1.0f);
                vertexData.put(alpha);
                n++;
                vertexData.put(points[6]);
                vertexData.put(points[7]);
                vertexData.put(1.0f);
                vertexData.put(1.0f);
                vertexData.put(alpha);
                n++;
                if (i != count - 1) {
                    vertexData.put(points[6]);
                    vertexData.put(points[7]);
                    vertexData.put(1.0f);
                    vertexData.put(1.0f);
                    vertexData.put(alpha);
                    n++;
                }
            }
            vertexData.position(0);
            GLES20.glVertexAttribPointer(0, 2, 5126, false, 20, vertexData.slice());
            GLES20.glEnableVertexAttribArray(0);
            vertexData.position(2);
            GLES20.glVertexAttribPointer(1, 2, 5126, true, 20, vertexData.slice());
            GLES20.glEnableVertexAttribArray(1);
            vertexData.position(4);
            GLES20.glVertexAttribPointer(2, 1, 5126, true, 20, vertexData.slice());
            GLES20.glEnableVertexAttribArray(2);
            GLES20.glDrawArrays(5, 0, n);
        }
        return rectF;
    }
}
