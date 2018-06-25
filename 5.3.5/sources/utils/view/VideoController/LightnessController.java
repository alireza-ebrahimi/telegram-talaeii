package utils.view.VideoController;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import org.ir.talaeii.R;

public class LightnessController {
    private Context context;
    private LightView lv;
    /* renamed from: t */
    private Toast f111t;

    public LightnessController(Context context) {
        this.context = context;
    }

    public static boolean isAutoBrightness(Activity act) {
        try {
            return System.getInt(act.getContentResolver(), "screen_brightness_mode") == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setLightness(Activity act, int value) {
        try {
            System.putInt(act.getContentResolver(), "screen_brightness", value);
            LayoutParams lp = act.getWindow().getAttributes();
            if (value <= 0) {
                value = 1;
            }
            lp.screenBrightness = ((float) value) / 255.0f;
            act.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLightness(Activity act) {
        return System.getInt(act.getContentResolver(), "screen_brightness", -1);
    }

    public static void stopAutoBrightness(Activity activity) {
        System.putInt(activity.getContentResolver(), "screen_brightness_mode", 0);
    }

    public static void startAutoBrightness(Activity activity) {
        System.putInt(activity.getContentResolver(), "screen_brightness_mode", 1);
    }

    public void show(float progress) {
        if (this.f111t == null) {
            this.f111t = new Toast(this.context);
            View layout = LayoutInflater.from(this.context).inflate(R.layout.lv, null);
            this.lv = (LightView) layout.findViewById(R.id.light_view);
            this.f111t.setView(layout);
            this.f111t.setGravity(80, 0, 100);
            this.f111t.setDuration(0);
        }
        this.lv.setProgress(progress);
        this.f111t.show();
    }
}
