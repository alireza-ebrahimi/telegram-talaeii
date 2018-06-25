package net.hockeyapp.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.gms.measurement.AppMeasurement.Param;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hockeyapp.android.C0962R;
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.HttpURLConnectionBuilder;
import net.hockeyapp.android.utils.Util;

public class SendFeedbackTask extends ConnectionTask<Void, Void, HashMap<String, String>> {
    public static final String BUNDLE_FEEDBACK_RESPONSE = "feedback_response";
    public static final String BUNDLE_FEEDBACK_STATUS = "feedback_status";
    public static final String BUNDLE_REQUEST_TYPE = "request_type";
    private static final String FILE_TAG = "HockeyApp";
    private static final String TAG = "SendFeedbackTask";
    private List<Uri> mAttachmentUris;
    private Context mContext;
    private String mEmail;
    private Handler mHandler;
    private boolean mIsFetchMessages;
    private int mLastMessageId = -1;
    private String mName;
    private ProgressDialog mProgressDialog;
    private boolean mShowProgressDialog = true;
    private String mSubject;
    private String mText;
    private String mToken;
    private String mUrlString;

    public SendFeedbackTask(Context context, String urlString, String name, String email, String subject, String text, List<Uri> attachmentUris, String token, Handler handler, boolean isFetchMessages) {
        this.mContext = context;
        this.mUrlString = urlString;
        this.mName = name;
        this.mEmail = email;
        this.mSubject = subject;
        this.mText = text;
        this.mAttachmentUris = attachmentUris;
        this.mToken = token;
        this.mHandler = handler;
        this.mIsFetchMessages = isFetchMessages;
        if (context != null) {
            Constants.loadFromContext(context);
        }
    }

    public void setShowProgressDialog(boolean showProgressDialog) {
        this.mShowProgressDialog = showProgressDialog;
    }

    public void setLastMessageId(int lastMessageId) {
        this.mLastMessageId = lastMessageId;
    }

    public void attach(Context context) {
        this.mContext = context;
    }

    public void detach() {
        this.mContext = null;
        this.mProgressDialog = null;
    }

    protected void onPreExecute() {
        String loadingMessage = this.mContext.getString(C0962R.string.hockeyapp_feedback_sending_feedback_text);
        if (this.mIsFetchMessages) {
            loadingMessage = this.mContext.getString(C0962R.string.hockeyapp_feedback_fetching_feedback_text);
        }
        if ((this.mProgressDialog == null || !this.mProgressDialog.isShowing()) && this.mShowProgressDialog) {
            this.mProgressDialog = ProgressDialog.show(this.mContext, "", loadingMessage, true, false);
        }
    }

    protected HashMap<String, String> doInBackground(Void... args) {
        if (this.mIsFetchMessages && this.mToken != null) {
            return doGet();
        }
        if (this.mIsFetchMessages) {
            return null;
        }
        if (this.mAttachmentUris.isEmpty()) {
            return doPostPut();
        }
        HashMap<String, String> result = doPostPutWithAttachments();
        if (result == null) {
            return result;
        }
        clearTemporaryFolder(result);
        return result;
    }

    private void clearTemporaryFolder(HashMap<String, String> result) {
        String status = (String) result.get("status");
        if (status != null && status.startsWith("2") && this.mContext != null) {
            File folder = new File(this.mContext.getCacheDir(), "HockeyApp");
            if (folder != null && folder.exists()) {
                for (File file : folder.listFiles()) {
                    if (!(file == null || Boolean.valueOf(file.delete()).booleanValue())) {
                        HockeyLog.debug(TAG, "Error deleting file from temporary folder");
                    }
                }
            }
        }
    }

    protected void onPostExecute(HashMap<String, String> result) {
        if (this.mProgressDialog != null) {
            try {
                this.mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mHandler != null) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            if (result != null) {
                bundle.putString(BUNDLE_REQUEST_TYPE, (String) result.get(Param.TYPE));
                bundle.putString(BUNDLE_FEEDBACK_RESPONSE, (String) result.get("response"));
                bundle.putString(BUNDLE_FEEDBACK_STATUS, (String) result.get("status"));
            } else {
                bundle.putString(BUNDLE_REQUEST_TYPE, "unknown");
            }
            msg.setData(bundle);
            this.mHandler.sendMessage(msg);
        }
    }

    private HashMap<String, String> doPostPut() {
        HashMap<String, String> result = new HashMap();
        result.put(Param.TYPE, "send");
        HttpURLConnection urlConnection = null;
        try {
            Map<String, String> parameters = new HashMap();
            parameters.put("name", this.mName);
            parameters.put("email", this.mEmail);
            parameters.put("subject", this.mSubject);
            parameters.put("text", this.mText);
            parameters.put("bundle_identifier", Constants.APP_PACKAGE);
            parameters.put("bundle_short_version", Constants.APP_VERSION_NAME);
            parameters.put("bundle_version", Constants.APP_VERSION);
            parameters.put("os_version", Constants.ANDROID_VERSION);
            parameters.put("oem", Constants.PHONE_MANUFACTURER);
            parameters.put("model", Constants.PHONE_MODEL);
            parameters.put("sdk_version", "4.1.3");
            if (this.mToken != null) {
                this.mUrlString += this.mToken + "/";
            }
            urlConnection = new HttpURLConnectionBuilder(this.mUrlString).setRequestMethod(this.mToken != null ? HttpRequest.METHOD_PUT : HttpRequest.METHOD_POST).writeFormFields(parameters).build();
            urlConnection.connect();
            result.put("status", String.valueOf(urlConnection.getResponseCode()));
            result.put("response", ConnectionTask.getStringFromConnection(urlConnection));
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private HashMap<String, String> doPostPutWithAttachments() {
        HashMap<String, String> result = new HashMap();
        result.put(Param.TYPE, "send");
        HttpURLConnection urlConnection = null;
        try {
            Map<String, String> parameters = new HashMap();
            parameters.put("name", this.mName);
            parameters.put("email", this.mEmail);
            parameters.put("subject", this.mSubject);
            parameters.put("text", this.mText);
            parameters.put("bundle_identifier", Constants.APP_PACKAGE);
            parameters.put("bundle_short_version", Constants.APP_VERSION_NAME);
            parameters.put("bundle_version", Constants.APP_VERSION);
            parameters.put("os_version", Constants.ANDROID_VERSION);
            parameters.put("oem", Constants.PHONE_MANUFACTURER);
            parameters.put("model", Constants.PHONE_MODEL);
            parameters.put("sdk_version", "4.1.3");
            if (this.mToken != null) {
                this.mUrlString += this.mToken + "/";
            }
            urlConnection = new HttpURLConnectionBuilder(this.mUrlString).setRequestMethod(this.mToken != null ? HttpRequest.METHOD_PUT : HttpRequest.METHOD_POST).writeMultipartData(parameters, this.mContext, this.mAttachmentUris).build();
            urlConnection.connect();
            result.put("status", String.valueOf(urlConnection.getResponseCode()));
            result.put("response", ConnectionTask.getStringFromConnection(urlConnection));
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private HashMap<String, String> doGet() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mUrlString + Util.encodeParam(this.mToken));
        if (this.mLastMessageId != -1) {
            sb.append("?last_message_id=" + this.mLastMessageId);
        }
        HashMap<String, String> result = new HashMap();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = new HttpURLConnectionBuilder(sb.toString()).build();
            result.put(Param.TYPE, "fetch");
            urlConnection.connect();
            result.put("status", String.valueOf(urlConnection.getResponseCode()));
            result.put("response", ConnectionTask.getStringFromConnection(urlConnection));
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }
}
