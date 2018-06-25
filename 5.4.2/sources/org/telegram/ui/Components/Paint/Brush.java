package org.telegram.ui.Components.Paint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.ApplicationLoader;

public interface Brush {

    public static class Elliptical implements Brush {
        public float getAlpha() {
            return 0.3f;
        }

        public float getAngle() {
            return (float) Math.toRadians(125.0d);
        }

        public float getScale() {
            return 1.5f;
        }

        public float getSpacing() {
            return 0.04f;
        }

        public Bitmap getStamp() {
            Options options = new Options();
            options.inScaled = false;
            return BitmapFactory.decodeResource(ApplicationLoader.applicationContext.getResources(), R.drawable.paint_elliptical_brush, options);
        }

        public boolean isLightSaber() {
            return false;
        }
    }

    public static class Neon implements Brush {
        public float getAlpha() {
            return 0.7f;
        }

        public float getAngle() {
            return BitmapDescriptorFactory.HUE_RED;
        }

        public float getScale() {
            return 1.45f;
        }

        public float getSpacing() {
            return 0.07f;
        }

        public Bitmap getStamp() {
            Options options = new Options();
            options.inScaled = false;
            return BitmapFactory.decodeResource(ApplicationLoader.applicationContext.getResources(), R.drawable.paint_neon_brush, options);
        }

        public boolean isLightSaber() {
            return true;
        }
    }

    public static class Radial implements Brush {
        public float getAlpha() {
            return 0.85f;
        }

        public float getAngle() {
            return BitmapDescriptorFactory.HUE_RED;
        }

        public float getScale() {
            return 1.0f;
        }

        public float getSpacing() {
            return 0.15f;
        }

        public Bitmap getStamp() {
            Options options = new Options();
            options.inScaled = false;
            return BitmapFactory.decodeResource(ApplicationLoader.applicationContext.getResources(), R.drawable.paint_radial_brush, options);
        }

        public boolean isLightSaber() {
            return false;
        }
    }

    float getAlpha();

    float getAngle();

    float getScale();

    float getSpacing();

    Bitmap getStamp();

    boolean isLightSaber();
}
