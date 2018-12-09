package org.telegram.ui.Components.Paint;

import android.graphics.Bitmap;
import android.graphics.PointF;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.Size;

public class PhotoFace {
    private float angle;
    private Point chinPoint;
    private Point eyesCenterPoint;
    private float eyesDistance;
    private Point foreheadPoint;
    private Point mouthPoint;
    private float width;

    public PhotoFace(Face face, Bitmap bitmap, Size size, boolean z) {
        Point point = null;
        Point point2 = null;
        Point point3 = null;
        Point point4 = null;
        for (Landmark landmark : face.getLandmarks()) {
            Point point5;
            PointF position = landmark.getPosition();
            Point point6;
            switch (landmark.getType()) {
                case 4:
                    point6 = point4;
                    point4 = point3;
                    point3 = point2;
                    point2 = transposePoint(position, bitmap, size, z);
                    point5 = point6;
                    break;
                case 5:
                    point3 = point2;
                    point2 = point;
                    point6 = point4;
                    point4 = transposePoint(position, bitmap, size, z);
                    point5 = point6;
                    break;
                case 10:
                    point2 = point;
                    point6 = point3;
                    point3 = transposePoint(position, bitmap, size, z);
                    point5 = point4;
                    point4 = point6;
                    break;
                case 11:
                    point5 = transposePoint(position, bitmap, size, z);
                    point4 = point3;
                    point3 = point2;
                    point2 = point;
                    break;
                default:
                    point5 = point4;
                    point4 = point3;
                    point3 = point2;
                    point2 = point;
                    break;
            }
            point = point2;
            point2 = point3;
            point3 = point4;
            point4 = point5;
        }
        if (!(point == null || point2 == null)) {
            this.eyesCenterPoint = new Point((0.5f * point.f10184x) + (0.5f * point2.f10184x), (0.5f * point.f10185y) + (0.5f * point2.f10185y));
            this.eyesDistance = (float) Math.hypot((double) (point2.f10184x - point.f10184x), (double) (point2.f10185y - point.f10185y));
            this.angle = (float) Math.toDegrees(Math.atan2((double) (point2.f10185y - point.f10185y), (double) (point2.f10184x - point.f10184x)) + 3.141592653589793d);
            this.width = this.eyesDistance * 2.35f;
            float f = 0.8f * this.eyesDistance;
            float toRadians = (float) Math.toRadians((double) (this.angle - 90.0f));
            this.foreheadPoint = new Point(this.eyesCenterPoint.f10184x + (((float) Math.cos((double) toRadians)) * f), (f * ((float) Math.sin((double) toRadians))) + this.eyesCenterPoint.f10185y);
        }
        if (point3 != null && point4 != null) {
            this.mouthPoint = new Point((0.5f * point3.f10184x) + (0.5f * point4.f10184x), (point4.f10185y * 0.5f) + (point3.f10185y * 0.5f));
            f = 0.7f * this.eyesDistance;
            float toRadians2 = (float) Math.toRadians((double) (this.angle + 90.0f));
            this.chinPoint = new Point(this.mouthPoint.f10184x + (((float) Math.cos((double) toRadians2)) * f), (f * ((float) Math.sin((double) toRadians2))) + this.mouthPoint.f10185y);
        }
    }

    private Point transposePoint(PointF pointF, Bitmap bitmap, Size size, boolean z) {
        return new Point((size.width * pointF.x) / (z ? (float) bitmap.getHeight() : (float) bitmap.getWidth()), (size.height * pointF.y) / (z ? (float) bitmap.getWidth() : (float) bitmap.getHeight()));
    }

    public float getAngle() {
        return this.angle;
    }

    public Point getPointForAnchor(int i) {
        switch (i) {
            case 0:
                return this.foreheadPoint;
            case 1:
                return this.eyesCenterPoint;
            case 2:
                return this.mouthPoint;
            case 3:
                return this.chinPoint;
            default:
                return null;
        }
    }

    public float getWidthForAnchor(int i) {
        return i == 1 ? this.eyesDistance : this.width;
    }

    public boolean isSufficient() {
        return this.eyesCenterPoint != null;
    }
}
