package org.telegram.customization.Model;

public class DialogStatus {
    long dialogId;
    boolean hasHotgram;
    boolean inviteSent;
    boolean isFilter;
    String username;

    public DialogStatus(long j, boolean z, boolean z2) {
        this.dialogId = j;
        this.hasHotgram = z;
        this.inviteSent = z2;
    }

    public DialogStatus(long j, boolean z, boolean z2, boolean z3) {
        this.dialogId = j;
        this.hasHotgram = z;
        this.inviteSent = z2;
        this.isFilter = z3;
    }

    public long getDialogId() {
        return this.dialogId;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isFilter() {
        return this.isFilter;
    }

    public boolean isHasHotgram() {
        return this.hasHotgram;
    }

    public boolean isInviteSent() {
        return this.inviteSent;
    }

    public void setDialogId(long j) {
        this.dialogId = j;
    }

    public void setFilter(boolean z) {
        this.isFilter = z;
    }

    public void setHasHotgram(boolean z) {
        this.hasHotgram = z;
    }

    public void setInviteSent(boolean z) {
        this.inviteSent = z;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
