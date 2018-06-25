package utils.view.VideoController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import org.ir.talaeii.R;

public class VolumnController {
    private Context context;
    /* renamed from: t */
    private Toast f112t;
    private VolumnView tv;

    public VolumnController(Context context) {
        this.context = context;
    }

    public void show(float progress) {
        if (this.f112t == null) {
            this.f112t = new Toast(this.context);
            View layout = LayoutInflater.from(this.context).inflate(R.layout.vv, null);
            this.tv = (VolumnView) layout.findViewById(R.id.volumnView);
            this.f112t.setView(layout);
            this.f112t.setGravity(80, 0, 100);
            this.f112t.setDuration(0);
        }
        this.tv.setProgress(progress);
        this.f112t.show();
    }
}
