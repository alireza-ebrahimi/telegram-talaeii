package net.hockeyapp.android.tasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.onesignal.OneSignalDbContract.NotificationTable;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.util.ArrayList;
import net.hockeyapp.android.FeedbackActivity;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.FeedbackManagerListener;
import net.hockeyapp.android.objects.FeedbackMessage;
import net.hockeyapp.android.objects.FeedbackResponse;
import net.hockeyapp.android.utils.FeedbackParser;
import net.hockeyapp.android.utils.Util;

public class ParseFeedbackTask extends AsyncTask<Void, Void, FeedbackResponse> {
    public static final String BUNDLE_PARSE_FEEDBACK_RESPONSE = "parse_feedback_response";
    public static final String ID_LAST_MESSAGE_PROCESSED = "idLastMessageProcessed";
    public static final String ID_LAST_MESSAGE_SEND = "idLastMessageSend";
    public static final int NEW_ANSWER_NOTIFICATION_ID = 2;
    public static final String PREFERENCES_NAME = "net.hockeyapp.android.feedback";
    private Context mContext;
    private String mFeedbackResponse;
    private Handler mHandler;
    private String mRequestType;
    private String mUrlString = null;

    public ParseFeedbackTask(Context context, String feedbackResponse, Handler handler, String requestType) {
        this.mContext = context;
        this.mFeedbackResponse = feedbackResponse;
        this.mHandler = handler;
        this.mRequestType = requestType;
    }

    public void setUrlString(String urlString) {
        this.mUrlString = urlString;
    }

    protected FeedbackResponse doInBackground(Void... params) {
        if (this.mContext == null || this.mFeedbackResponse == null) {
            return null;
        }
        FeedbackResponse response = FeedbackParser.getInstance().parseFeedbackResponse(this.mFeedbackResponse);
        if (response == null || response.getFeedback() == null) {
            return response;
        }
        ArrayList<FeedbackMessage> messages = response.getFeedback().getMessages();
        if (messages == null || messages.isEmpty()) {
            return response;
        }
        checkForNewAnswers(messages);
        return response;
    }

    protected void onPostExecute(FeedbackResponse result) {
        if (result != null && this.mHandler != null) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_PARSE_FEEDBACK_RESPONSE, result);
            msg.setData(bundle);
            this.mHandler.sendMessage(msg);
        }
    }

    private void checkForNewAnswers(ArrayList<FeedbackMessage> messages) {
        FeedbackMessage latestMessage = (FeedbackMessage) messages.get(messages.size() - 1);
        int idLatestMessage = latestMessage.getId();
        SharedPreferences preferences = this.mContext.getSharedPreferences(PREFERENCES_NAME, 0);
        if (this.mRequestType.equals("send")) {
            preferences.edit().putInt(ID_LAST_MESSAGE_SEND, idLatestMessage).putInt(ID_LAST_MESSAGE_PROCESSED, idLatestMessage).apply();
        } else if (this.mRequestType.equals("fetch")) {
            int idLastMessageSend = preferences.getInt(ID_LAST_MESSAGE_SEND, -1);
            int idLastMessageProcessed = preferences.getInt(ID_LAST_MESSAGE_PROCESSED, -1);
            if (idLatestMessage != idLastMessageSend && idLatestMessage != idLastMessageProcessed) {
                preferences.edit().putInt(ID_LAST_MESSAGE_PROCESSED, idLatestMessage).apply();
                boolean eventHandled = false;
                FeedbackManagerListener listener = FeedbackManager.getLastListener();
                if (listener != null) {
                    eventHandled = listener.feedbackAnswered(latestMessage);
                }
                if (!eventHandled) {
                    startNotification(this.mContext);
                }
            }
        }
    }

    private void startNotification(Context context) {
        if (this.mUrlString != null) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
            int iconId = context.getResources().getIdentifier("ic_menu_refresh", "drawable", AbstractSpiCall.ANDROID_CLIENT_TYPE);
            Class<?> activityClass = null;
            if (FeedbackManager.getLastListener() != null) {
                activityClass = FeedbackManager.getLastListener().getFeedbackActivityClass();
            }
            if (activityClass == null) {
                activityClass = FeedbackActivity.class;
            }
            Intent intent = new Intent();
            intent.setFlags(805306368);
            intent.setClass(context, activityClass);
            intent.putExtra("url", this.mUrlString);
            Notification notification = Util.createNotification(context, PendingIntent.getActivity(context, 0, intent, 1073741824), "HockeyApp Feedback", "A new answer to your feedback is available.", iconId);
            if (notification != null) {
                notificationManager.notify(2, notification);
            }
        }
    }
}
