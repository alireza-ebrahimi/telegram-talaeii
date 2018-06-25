package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputContentInfo;

@TargetApi(25)
@RequiresApi(25)
final class InputContentInfoCompatApi25 {
    InputContentInfoCompatApi25() {
    }

    public static Object create(Uri contentUri, ClipDescription description, Uri linkUri) {
        return new InputContentInfo(contentUri, description, linkUri);
    }

    public static Uri getContentUri(Object inputContentInfo) {
        return ((InputContentInfo) inputContentInfo).getContentUri();
    }

    public static ClipDescription getDescription(Object inputContentInfo) {
        return ((InputContentInfo) inputContentInfo).getDescription();
    }

    public static Uri getLinkUri(Object inputContentInfo) {
        return ((InputContentInfo) inputContentInfo).getLinkUri();
    }

    public static void requestPermission(Object inputContentInfo) {
        ((InputContentInfo) inputContentInfo).requestPermission();
    }

    public static void releasePermission(Object inputContentInfo) {
        ((InputContentInfo) inputContentInfo).releasePermission();
    }
}
