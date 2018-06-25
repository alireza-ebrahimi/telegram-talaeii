package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.view.DragEvent;

@TargetApi(13)
@RequiresApi(13)
public class ActivityCompat extends android.support.v4.app.ActivityCompat {
    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity, dragEvent);
    }

    protected ActivityCompat() {
    }
}
