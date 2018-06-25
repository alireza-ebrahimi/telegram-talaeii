package utils.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import org.ir.talaeii.R;
import utils.Utilities;

public class ToastUtil {
    static Toast toast;

    public static Toast getLoadingToastInstanse(Context context) {
        if (toast == null) {
            View toastRoot = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast, null);
            toast = new Toast(context);
            toast.setView(toastRoot);
            toast.setGravity(81, 10, 10);
            toast.setDuration(1);
        }
        return toast;
    }

    public static Toast Custom(Context context, String message, String backgroundColor, String textColor, int length, int gravity) {
        Toast result = new Toast(context);
        if (length != -1) {
            result.setDuration(length);
        }
        FarsiTextView toastRoot = (FarsiTextView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast, null);
        toastRoot.setText(message);
        toastRoot.setBackgroundColor(Color.parseColor(Utilities.correctColor(backgroundColor)));
        toastRoot.setTextColor(Color.parseColor(Utilities.correctColor(textColor)));
        toastRoot.setTextSize(2, 16.0f);
        result.setView(toastRoot);
        result.setGravity(gravity, 10, 10);
        return result;
    }

    public static Toast AppToast(Context context, String message) {
        Toast result = new Toast(context);
        result.setDuration(1);
        FarsiTextView toastRoot = (FarsiTextView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast, null);
        toastRoot.setText(message);
        toastRoot.setBackgroundColor(Color.parseColor(Utilities.correctColor("69BA6D")));
        toastRoot.setTextColor(Color.parseColor(Utilities.correctColor("ffffff")));
        toastRoot.setTextSize(2, 16.0f);
        result.setView(toastRoot);
        result.setGravity(80, 10, 10);
        return result;
    }
}
