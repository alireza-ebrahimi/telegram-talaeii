package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class FragmentCompatICS {
    FragmentCompatICS() {
    }

    public static void setMenuVisibility(Fragment f, boolean visible) {
        f.setMenuVisibility(visible);
    }
}
