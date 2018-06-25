package com.onesignal;

import android.content.Context;
import android.net.Uri;
import com.onesignal.NotificationExtenderService.OverrideSettings;
import java.util.Random;
import org.json.JSONObject;

class NotificationGenerationJob {
    Context context;
    JSONObject jsonPayload;
    Integer orgFlags;
    Uri orgSound;
    CharSequence overriddenBodyFromExtender;
    Integer overriddenFlags;
    Uri overriddenSound;
    CharSequence overriddenTitleFromExtender;
    OverrideSettings overrideSettings;
    boolean restoring;
    boolean showAsAlert;
    Long shownTimeStamp;

    NotificationGenerationJob(Context context) {
        this.context = context;
    }

    CharSequence getTitle() {
        if (this.overriddenTitleFromExtender != null) {
            return this.overriddenTitleFromExtender;
        }
        return this.jsonPayload.optString("title", null);
    }

    CharSequence getBody() {
        if (this.overriddenBodyFromExtender != null) {
            return this.overriddenBodyFromExtender;
        }
        return this.jsonPayload.optString("alert", null);
    }

    Integer getAndroidId() {
        if (this.overrideSettings == null) {
            this.overrideSettings = new OverrideSettings();
        }
        if (this.overrideSettings.androidNotificationId == null) {
            this.overrideSettings.androidNotificationId = Integer.valueOf(new Random().nextInt());
        }
        return this.overrideSettings.androidNotificationId;
    }

    int getAndroidIdWithoutCreate() {
        if (this.overrideSettings == null || this.overrideSettings.androidNotificationId == null) {
            return -1;
        }
        return this.overrideSettings.androidNotificationId.intValue();
    }

    void setAndroidIdWithOutOverriding(Integer id) {
        if (id != null) {
            if (this.overrideSettings == null || this.overrideSettings.androidNotificationId == null) {
                if (this.overrideSettings == null) {
                    this.overrideSettings = new OverrideSettings();
                }
                this.overrideSettings.androidNotificationId = id;
            }
        }
    }

    boolean hasExtender() {
        return (this.overrideSettings == null || this.overrideSettings.extender == null) ? false : true;
    }
}
