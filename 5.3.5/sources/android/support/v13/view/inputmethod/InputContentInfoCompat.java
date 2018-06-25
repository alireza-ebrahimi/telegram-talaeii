package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.os.BuildCompat;

@TargetApi(13)
@RequiresApi(13)
public final class InputContentInfoCompat {
    private final InputContentInfoCompatImpl mImpl;

    private interface InputContentInfoCompatImpl {
        @NonNull
        Uri getContentUri();

        @NonNull
        ClipDescription getDescription();

        @Nullable
        Object getInputContentInfo();

        @Nullable
        Uri getLinkUri();

        void releasePermission();

        void requestPermission();
    }

    private static final class Api25InputContentInfoCompatImpl implements InputContentInfoCompatImpl {
        @NonNull
        final Object mObject;

        public Api25InputContentInfoCompatImpl(@NonNull Object inputContentInfo) {
            this.mObject = inputContentInfo;
        }

        public Api25InputContentInfoCompatImpl(@NonNull Uri contentUri, @NonNull ClipDescription description, @Nullable Uri linkUri) {
            this.mObject = InputContentInfoCompatApi25.create(contentUri, description, linkUri);
        }

        @NonNull
        public Uri getContentUri() {
            return InputContentInfoCompatApi25.getContentUri(this.mObject);
        }

        @NonNull
        public ClipDescription getDescription() {
            return InputContentInfoCompatApi25.getDescription(this.mObject);
        }

        @Nullable
        public Uri getLinkUri() {
            return InputContentInfoCompatApi25.getLinkUri(this.mObject);
        }

        @Nullable
        public Object getInputContentInfo() {
            return this.mObject;
        }

        public void requestPermission() {
            InputContentInfoCompatApi25.requestPermission(this.mObject);
        }

        public void releasePermission() {
            InputContentInfoCompatApi25.releasePermission(this.mObject);
        }
    }

    private static final class BaseInputContentInfoCompatImpl implements InputContentInfoCompatImpl {
        @NonNull
        private final Uri mContentUri;
        @NonNull
        private final ClipDescription mDescription;
        @Nullable
        private final Uri mLinkUri;

        public BaseInputContentInfoCompatImpl(@NonNull Uri contentUri, @NonNull ClipDescription description, @Nullable Uri linkUri) {
            this.mContentUri = contentUri;
            this.mDescription = description;
            this.mLinkUri = linkUri;
        }

        @NonNull
        public Uri getContentUri() {
            return this.mContentUri;
        }

        @NonNull
        public ClipDescription getDescription() {
            return this.mDescription;
        }

        @Nullable
        public Uri getLinkUri() {
            return this.mLinkUri;
        }

        @Nullable
        public Object getInputContentInfo() {
            return null;
        }

        public void requestPermission() {
        }

        public void releasePermission() {
        }
    }

    public InputContentInfoCompat(@NonNull Uri contentUri, @NonNull ClipDescription description, @Nullable Uri linkUri) {
        if (BuildCompat.isAtLeastNMR1()) {
            this.mImpl = new Api25InputContentInfoCompatImpl(contentUri, description, linkUri);
        } else {
            this.mImpl = new BaseInputContentInfoCompatImpl(contentUri, description, linkUri);
        }
    }

    private InputContentInfoCompat(@NonNull InputContentInfoCompatImpl impl) {
        this.mImpl = impl;
    }

    @NonNull
    public Uri getContentUri() {
        return this.mImpl.getContentUri();
    }

    @NonNull
    public ClipDescription getDescription() {
        return this.mImpl.getDescription();
    }

    @Nullable
    public Uri getLinkUri() {
        return this.mImpl.getLinkUri();
    }

    @Nullable
    public static InputContentInfoCompat wrap(@Nullable Object inputContentInfo) {
        if (inputContentInfo != null && BuildCompat.isAtLeastNMR1()) {
            return new InputContentInfoCompat(new Api25InputContentInfoCompatImpl(inputContentInfo));
        }
        return null;
    }

    @Nullable
    public Object unwrap() {
        return this.mImpl.getInputContentInfo();
    }

    public void requestPermission() {
        this.mImpl.requestPermission();
    }

    public void releasePermission() {
        this.mImpl.releasePermission();
    }
}
