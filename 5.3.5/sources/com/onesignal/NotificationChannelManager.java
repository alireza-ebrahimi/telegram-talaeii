package com.onesignal;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class NotificationChannelManager {
    private static final String DEFAULT_CHANNEL_ID = "fcm_fallback_notification_channel";
    private static final String RESTORE_CHANNEL_ID = "restored_OS_notifications";

    NotificationChannelManager() {
    }

    static String createNotificationChannel(NotificationGenerationJob notifJob) {
        if (VERSION.SDK_INT < 26) {
            return DEFAULT_CHANNEL_ID;
        }
        Context context = notifJob.context;
        JSONObject jsonPayload = notifJob.jsonPayload;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
        if (notifJob.restoring) {
            return createRestoreChannel(notificationManager);
        }
        if (jsonPayload.has("oth_chnl")) {
            String otherChannel = jsonPayload.optString("oth_chnl");
            if (notificationManager.getNotificationChannel(otherChannel) != null) {
                return otherChannel;
            }
        }
        if (!jsonPayload.has("chnl")) {
            return createDefaultChannel(notificationManager);
        }
        try {
            return createChannel(context, notificationManager, jsonPayload);
        } catch (JSONException e) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Could not create notification channel due to JSON payload error!", e);
            return DEFAULT_CHANNEL_ID;
        }
    }

    @RequiresApi(api = 26)
    private static String createChannel(Context context, NotificationManager notificationManager, JSONObject payload) throws JSONException {
        JSONObject channelPayload;
        JSONObject objChannelPayload = payload.opt("chnl");
        if (objChannelPayload instanceof String) {
            channelPayload = new JSONObject((String) objChannelPayload);
        } else {
            channelPayload = objChannelPayload;
        }
        String channel_id = channelPayload.optString("id", DEFAULT_CHANNEL_ID);
        if (channel_id.equals("miscellaneous")) {
            channel_id = DEFAULT_CHANNEL_ID;
        }
        JSONObject payloadWithText = channelPayload;
        if (channelPayload.has("langs")) {
            JSONObject langList = channelPayload.getJSONObject("langs");
            String deviceLanguage = OSUtils.getCorrectedLanguage();
            if (langList.has(deviceLanguage)) {
                payloadWithText = langList.optJSONObject(deviceLanguage);
            }
        }
        NotificationChannel channel = new NotificationChannel(channel_id, payloadWithText.optString("nm", "Miscellaneous"), priorityToImportance(payload.optInt("pri", 6)));
        channel.setDescription(payloadWithText.optString("dscr", null));
        if (channelPayload.has("grp_id")) {
            String group_id = channelPayload.optString("grp_id");
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(group_id, payloadWithText.optString("grp_nm")));
            channel.setGroup(group_id);
        }
        if (payload.has("ledc")) {
            channel.setLightColor(new BigInteger(payload.optString("ledc"), 16).intValue());
        }
        channel.enableLights(payload.optInt("led", 1) == 1);
        if (payload.has("vib_pt")) {
            long[] vibrationPattern = OSUtils.parseVibrationPattern(payload);
            if (vibrationPattern != null) {
                channel.setVibrationPattern(vibrationPattern);
            }
        }
        channel.enableVibration(payload.optInt("vib", 1) == 1);
        if (payload.has("sound")) {
            String sound = payload.optString("sound", null);
            Uri uri = OSUtils.getSoundUri(context, sound);
            if (uri != null) {
                channel.setSound(uri, null);
            } else if ("null".equals(sound) || "nil".equals(sound)) {
                channel.setSound(null, null);
            }
        }
        channel.setLockscreenVisibility(payload.optInt("vis", 0));
        channel.setShowBadge(payload.optInt("bdg", 1) == 1);
        channel.setBypassDnd(payload.optInt("bdnd", 0) == 1);
        notificationManager.createNotificationChannel(channel);
        return channel_id;
    }

    @RequiresApi(api = 26)
    private static String createDefaultChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(DEFAULT_CHANNEL_ID, "Miscellaneous", 3);
        channel.enableLights(true);
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
        return DEFAULT_CHANNEL_ID;
    }

    @RequiresApi(api = 26)
    private static String createRestoreChannel(NotificationManager notificationManager) {
        notificationManager.createNotificationChannel(new NotificationChannel(RESTORE_CHANNEL_ID, "Restored", 2));
        return RESTORE_CHANNEL_ID;
    }

    static void processChannelList(Context context, JSONObject payload) {
        if (VERSION.SDK_INT >= 26 && payload.has("chnl_lst")) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
            Set<String> sycnedChannelSet = new HashSet();
            JSONArray chnlList = payload.optJSONArray("chnl_lst");
            int jsonArraySize = chnlList.length();
            for (int i = 0; i < jsonArraySize; i++) {
                try {
                    sycnedChannelSet.add(createChannel(context, notificationManager, chnlList.getJSONObject(i)));
                } catch (JSONException e) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Could not create notification channel due to JSON payload error!", e);
                }
            }
            for (NotificationChannel existingChannel : notificationManager.getNotificationChannels()) {
                String id = existingChannel.getId();
                if (id.startsWith("OS_") && !sycnedChannelSet.contains(id)) {
                    notificationManager.deleteNotificationChannel(id);
                }
            }
        }
    }

    private static int priorityToImportance(int priority) {
        if (priority > 9) {
            return 5;
        }
        if (priority > 7) {
            return 4;
        }
        if (priority > 5) {
            return 3;
        }
        if (priority > 3) {
            return 2;
        }
        if (priority > 1) {
            return 1;
        }
        return 0;
    }
}
