package android.support.v13.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

@TargetApi(24)
@RequiresApi(24)
class DragAndDropPermissionsCompatApi24 {
    DragAndDropPermissionsCompatApi24() {
    }

    public static Object request(Activity activity, DragEvent dragEvent) {
        return activity.requestDragAndDropPermissions(dragEvent);
    }

    public static void release(Object dragAndDropPermissions) {
        ((DragAndDropPermissions) dragAndDropPermissions).release();
    }
}
