package net.hockeyapp.android.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 2590172806951065320L;
    private String mCreatedAt;
    private String mEmail;
    private int mId;
    private ArrayList<FeedbackMessage> mMessages;
    private String mName;

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getCreatedAt() {
        return this.mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public ArrayList<FeedbackMessage> getMessages() {
        return this.mMessages;
    }

    public void setMessages(ArrayList<FeedbackMessage> messages) {
        this.mMessages = messages;
    }
}
