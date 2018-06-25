package io.fabric.sdk.android.services.common;

import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.ui.ChatActivity;

public class ResponseParser {
    public static final int ResponseActionDiscard = 0;
    public static final int ResponseActionRetry = 1;

    public static int parse(int statusCode) {
        if (statusCode >= 200 && statusCode <= 299) {
            return 0;
        }
        if (statusCode >= ScheduleDownloadActivity.CHECK_CELL2 && statusCode <= 399) {
            return 1;
        }
        if (statusCode >= ChatActivity.scheduleDownloads && statusCode <= 499) {
            return 0;
        }
        if (statusCode >= 500) {
            return 1;
        }
        return 1;
    }
}
