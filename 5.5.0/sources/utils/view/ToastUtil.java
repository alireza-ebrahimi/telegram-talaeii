package utils.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Toast;
import org.ir.talaeii.R;
import utils.C3792d;

public class ToastUtil {
    /* renamed from: a */
    public static Toast m14196a(Context context, String str) {
        Toast toast = new Toast(context);
        toast.setDuration(1);
        FarsiTextView farsiTextView = (FarsiTextView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast, null);
        farsiTextView.setText(str);
        farsiTextView.setBackgroundColor(Color.parseColor(C3792d.a("69BA6D")));
        farsiTextView.setTextColor(Color.parseColor(C3792d.a("ffffff")));
        farsiTextView.setTextSize(2, 16.0f);
        toast.setView(farsiTextView);
        toast.setGravity(80, 10, 10);
        return toast;
    }
}
