package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.support.annotation.RequiresApi;

@TargetApi(24)
@RequiresApi(24)
class FragmentCompatApi24 {
    FragmentCompatApi24() {
    }

    public static void setUserVisibleHint(Fragment f, boolean isVisible) {
        f.setUserVisibleHint(isVisible);
    }
}
