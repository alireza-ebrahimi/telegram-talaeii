package utils.view.VideoController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import org.ir.talaeii.R;

public class VolumnController {
    /* renamed from: a */
    private Toast f10328a;
    /* renamed from: b */
    private VolumnView f10329b;
    /* renamed from: c */
    private Context f10330c;

    public VolumnController(Context context) {
        this.f10330c = context;
    }

    /* renamed from: a */
    public void m14202a(float f) {
        if (this.f10328a == null) {
            this.f10328a = new Toast(this.f10330c);
            View inflate = LayoutInflater.from(this.f10330c).inflate(R.layout.vv, null);
            this.f10329b = (VolumnView) inflate.findViewById(R.id.volumnView);
            this.f10328a.setView(inflate);
            this.f10328a.setGravity(80, 0, 100);
            this.f10328a.setDuration(0);
        }
        this.f10329b.setProgress(f);
        this.f10328a.show();
    }
}
