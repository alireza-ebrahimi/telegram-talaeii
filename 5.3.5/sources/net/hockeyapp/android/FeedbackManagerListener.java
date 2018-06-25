package net.hockeyapp.android;

import net.hockeyapp.android.objects.FeedbackMessage;

public abstract class FeedbackManagerListener {
    public abstract boolean feedbackAnswered(FeedbackMessage feedbackMessage);

    public Class<? extends FeedbackActivity> getFeedbackActivityClass() {
        return FeedbackActivity.class;
    }

    public boolean shouldCreateNewFeedbackThread() {
        return false;
    }
}
