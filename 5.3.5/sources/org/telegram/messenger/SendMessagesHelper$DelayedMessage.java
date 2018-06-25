package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncryptedMultiMedia;
import org.telegram.tgnet.TLRPC$TL_messages_sendMultiMedia;

protected class SendMessagesHelper$DelayedMessage {
    public TLRPC$EncryptedChat encryptedChat;
    public HashMap<Object, Object> extraHashMap;
    public int finalGroupMessage;
    public long groupId;
    public String httpLocation;
    public TLRPC$FileLocation location;
    public ArrayList<MessageObject> messageObjects;
    public ArrayList<TLRPC$Message> messages;
    public MessageObject obj;
    public String originalPath;
    public ArrayList<String> originalPaths;
    public long peer;
    ArrayList<SendMessagesHelper$DelayedMessageSendAfterRequest> requests;
    public TLObject sendEncryptedRequest;
    public TLObject sendRequest;
    final /* synthetic */ SendMessagesHelper this$0;
    public int type;
    public boolean upload;
    public VideoEditedInfo videoEditedInfo;

    public SendMessagesHelper$DelayedMessage(SendMessagesHelper this$0, long peer) {
        this.this$0 = this$0;
        this.peer = peer;
    }

    public void addDelayedRequest(TLObject req, MessageObject msgObj, String originalPath) {
        SendMessagesHelper$DelayedMessageSendAfterRequest request = new SendMessagesHelper$DelayedMessageSendAfterRequest(this.this$0);
        request.request = req;
        request.msgObj = msgObj;
        request.originalPath = originalPath;
        if (this.requests == null) {
            this.requests = new ArrayList();
        }
        this.requests.add(request);
    }

    public void addDelayedRequest(TLObject req, ArrayList<MessageObject> msgObjs, ArrayList<String> originalPaths) {
        SendMessagesHelper$DelayedMessageSendAfterRequest request = new SendMessagesHelper$DelayedMessageSendAfterRequest(this.this$0);
        request.request = req;
        request.msgObjs = msgObjs;
        request.originalPaths = originalPaths;
        if (this.requests == null) {
            this.requests = new ArrayList();
        }
        this.requests.add(request);
    }

    public void sendDelayedRequests() {
        if (this.requests == null) {
            return;
        }
        if (this.type == 4 || this.type == 0) {
            int size = this.requests.size();
            for (int a = 0; a < size; a++) {
                SendMessagesHelper$DelayedMessageSendAfterRequest request = (SendMessagesHelper$DelayedMessageSendAfterRequest) this.requests.get(a);
                if (request.request instanceof TLRPC$TL_messages_sendEncryptedMultiMedia) {
                    SecretChatHelper.getInstance().performSendEncryptedRequest((TLRPC$TL_messages_sendEncryptedMultiMedia) request.request, this);
                } else if (request.request instanceof TLRPC$TL_messages_sendMultiMedia) {
                    SendMessagesHelper.access$700(this.this$0, (TLRPC$TL_messages_sendMultiMedia) request.request, request.msgObjs, request.originalPaths);
                } else {
                    SendMessagesHelper.access$800(this.this$0, request.request, request.msgObj, request.originalPath);
                }
            }
            this.requests = null;
        }
    }

    public void markAsError() {
        if (this.type == 4) {
            for (int a = 0; a < this.messageObjects.size(); a++) {
                MessageObject obj = (MessageObject) this.messageObjects.get(a);
                MessagesStorage.getInstance().markMessageAsSendError(obj.messageOwner);
                obj.messageOwner.send_state = 2;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(obj.getId())});
                this.this$0.processSentMessage(obj.getId());
            }
            SendMessagesHelper.access$900(this.this$0).remove("group_" + this.groupId);
        } else {
            MessagesStorage.getInstance().markMessageAsSendError(this.obj.messageOwner);
            this.obj.messageOwner.send_state = 2;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(this.obj.getId())});
            this.this$0.processSentMessage(this.obj.getId());
        }
        sendDelayedRequests();
    }
}
