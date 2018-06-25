package android.support.v13.view.inputmethod;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.os.BuildCompat;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

@TargetApi(13)
@RequiresApi(13)
public final class InputConnectionCompat {
    private static final InputConnectionCompatImpl IMPL;
    public static int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    private interface InputConnectionCompatImpl {
        boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle);

        @NonNull
        InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener);
    }

    private static final class Api25InputContentInfoCompatImpl implements InputConnectionCompatImpl {
        private Api25InputContentInfoCompatImpl() {
        }

        public boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfo, int flags, @Nullable Bundle opts) {
            return InputConnectionCompatApi25.commitContent(inputConnection, inputContentInfo.unwrap(), flags, opts);
        }

        @Nullable
        public InputConnection createWrapper(@Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo, @Nullable OnCommitContentListener onCommitContentListener) {
            final OnCommitContentListener listener = onCommitContentListener;
            return InputConnectionCompatApi25.createWrapper(inputConnection, new android.support.v13.view.inputmethod.InputConnectionCompatApi25.OnCommitContentListener() {
                public boolean onCommitContent(Object inputContentInfo, int flags, Bundle opts) {
                    return listener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), flags, opts);
                }
            });
        }
    }

    static final class BaseInputContentInfoCompatImpl implements InputConnectionCompatImpl {
        private static String COMMIT_CONTENT_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
        private static String COMMIT_CONTENT_CONTENT_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
        private static String COMMIT_CONTENT_DESCRIPTION_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
        private static String COMMIT_CONTENT_FLAGS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
        private static String COMMIT_CONTENT_LINK_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
        private static String COMMIT_CONTENT_OPTS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
        private static String COMMIT_CONTENT_RESULT_RECEIVER = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";

        BaseInputContentInfoCompatImpl() {
        }

        public boolean commitContent(@NonNull InputConnection inputConnection, @NonNull InputContentInfoCompat inputContentInfo, int flags, @Nullable Bundle opts) {
            Bundle params = new Bundle();
            params.putParcelable(COMMIT_CONTENT_CONTENT_URI_KEY, inputContentInfo.getContentUri());
            params.putParcelable(COMMIT_CONTENT_DESCRIPTION_KEY, inputContentInfo.getDescription());
            params.putParcelable(COMMIT_CONTENT_LINK_URI_KEY, inputContentInfo.getLinkUri());
            params.putInt(COMMIT_CONTENT_FLAGS_KEY, flags);
            params.putParcelable(COMMIT_CONTENT_OPTS_KEY, opts);
            return inputConnection.performPrivateCommand(COMMIT_CONTENT_ACTION, params);
        }

        @NonNull
        public InputConnection createWrapper(@NonNull InputConnection ic, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener) {
            if (EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0) {
                return ic;
            }
            final OnCommitContentListener listener = onCommitContentListener;
            return new InputConnectionWrapper(ic, false) {
                public boolean performPrivateCommand(String action, Bundle data) {
                    if (BaseInputContentInfoCompatImpl.handlePerformPrivateCommand(action, data, listener)) {
                        return true;
                    }
                    return super.performPrivateCommand(action, data);
                }
            };
        }

        static boolean handlePerformPrivateCommand(@Nullable String action, @NonNull Bundle data, @NonNull OnCommitContentListener onCommitContentListener) {
            int i = 1;
            if (!TextUtils.equals(COMMIT_CONTENT_ACTION, action) || data == null) {
                return false;
            }
            ResultReceiver resultReceiver = null;
            boolean result = false;
            try {
                resultReceiver = (ResultReceiver) data.getParcelable(COMMIT_CONTENT_RESULT_RECEIVER);
                Uri contentUri = (Uri) data.getParcelable(COMMIT_CONTENT_CONTENT_URI_KEY);
                ClipDescription description = (ClipDescription) data.getParcelable(COMMIT_CONTENT_DESCRIPTION_KEY);
                Uri linkUri = (Uri) data.getParcelable(COMMIT_CONTENT_LINK_URI_KEY);
                result = onCommitContentListener.onCommitContent(new InputContentInfoCompat(contentUri, description, linkUri), data.getInt(COMMIT_CONTENT_FLAGS_KEY), (Bundle) data.getParcelable(COMMIT_CONTENT_OPTS_KEY));
                if (resultReceiver != null) {
                    int i2;
                    if (result) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    resultReceiver.send(i2, null);
                }
                return result;
            } catch (Throwable th) {
                if (resultReceiver != null) {
                    if (!result) {
                        i = 0;
                    }
                    resultReceiver.send(i, null);
                }
            }
        }
    }

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    static {
        if (BuildCompat.isAtLeastNMR1()) {
            IMPL = new Api25InputContentInfoCompatImpl();
        } else {
            IMPL = new BaseInputContentInfoCompatImpl();
        }
    }

    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfo, int flags, @Nullable Bundle opts) {
        ClipDescription description = inputContentInfo.getDescription();
        boolean supported = false;
        for (String mimeType : EditorInfoCompat.getContentMimeTypes(editorInfo)) {
            if (description.hasMimeType(mimeType)) {
                supported = true;
                break;
            }
        }
        if (supported) {
            return IMPL.commitContent(inputConnection, inputContentInfo, flags, opts);
        }
        return false;
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (onCommitContentListener != null) {
            return IMPL.createWrapper(inputConnection, editorInfo, onCommitContentListener);
        } else {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        }
    }
}
