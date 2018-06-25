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
    /* renamed from: a */
    private Toast f10325a;
    /* renamed from: b */
    private LightView f10326b;
    /* renamed from: c */
    private Context f10327c;

    public LightnessController(Context context) {
        this.f10327c = context;
    }

    /* renamed from: a */
    public static int m14199a(Activity activity) {
        return System.getInt(activity.getContentResolver(), "screen_brightness", -1);
    }

    /* renamed from: a */
    public static void m14200a(Activity activity, int i) {
        try {
            System.putInt(activity.getContentResolver(), "screen_brightness", i);
            LayoutParams attributes = activity.getWindow().getAttributes();
            if (i <= 0) {
                i = 1;
            }
            attributes.screenBrightness = ((float) i) / 255.0f;
            activity.getWindow().setAttributes(attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m14201a(float f) {
        if (this.f10325a == null) {
            this.f10325a = new Toast(this.f10327c);
            View inflate = LayoutInflater.from(this.f10327c).inflate(R.layout.lv, null);
            this.f10326b = (LightView) inflate.findViewById(R.id.light_view);
            this.f10325a.setView(inflate);
            this.f10325a.setGravity(80, 0, 100);
            this.f10325a.setDuration(0);
        }
        this.f10326b.setProgress(f);
        this.f10325a.show();
    }
}
