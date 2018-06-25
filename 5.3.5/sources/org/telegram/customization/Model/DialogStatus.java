package org.telegram.customization.Model;

public class DialogStatus {
    long dialogId;
    boolean hasHotgram;
    boolean inviteSent;
    boolean isFilter;

    public DialogStatus(long mDialogId, boolean mHasHotgram, boolean mInivite) {
        this.dialogId = mDialogId;
        this.hasHotgram = mHasHotgram;
        this.inviteSent = mInivite;
    }

    public DialogStatus(long mDialogId, boolean mHasHotgram, boolean mInivite, boolean isFilter) {
        this.dialogId = mDialogId;
        this.hasHotgram = mHasHotgram;
        this.inviteSent = mInivite;
        this.isFilter = isFilter;
    }

    public boolean isFilter() {
        return this.isFilter;
    }

    public void setFilter(boolean filter) {
        this.isFilter = filter;
    }

    public long getDialogId() {
        return this.dialogId;
    }

    public void setDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    public boolean isHasHotgram() {
        return this.hasHotgram;
    }

    public void setHasHotgram(boolean hasHotgram) {
        this.hasHotgram = hasHotgram;
    }

    public boolean isInviteSent() {
        return this.inviteSent;
    }

    public void setInviteSent(boolean inviteSent) {
        this.inviteSent = inviteSent;
    }
}
