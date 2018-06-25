package net.hockeyapp.android.objects;

import java.io.Serializable;

public class FeedbackResponse implements Serializable {
    private static final long serialVersionUID = -1093570359639034766L;
    private Feedback mFeedback;
    private String mStatus;
    private String mToken;

    public String getStatus() {
        return this.mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public Feedback getFeedback() {
        return this.mFeedback;
    }

    public void setFeedback(Feedback feedback) {
        this.mFeedback = feedback;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }
}
