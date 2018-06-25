package org.telegram.tgnet;

import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;

class ConnectionsManager$1 implements Runnable {
    final /* synthetic */ ConnectionsManager this$0;
    final /* synthetic */ int val$connetionType;
    final /* synthetic */ int val$datacenterId;
    final /* synthetic */ int val$flags;
    final /* synthetic */ boolean val$immediate;
    final /* synthetic */ TLObject val$object;
    final /* synthetic */ RequestDelegate val$onComplete;
    final /* synthetic */ QuickAckDelegate val$onQuickAck;
    final /* synthetic */ WriteToSocketDelegate val$onWriteToSocket;
    final /* synthetic */ int val$requestToken;

    /* renamed from: org.telegram.tgnet.ConnectionsManager$1$1 */
    class C19521 implements RequestDelegateInternal {
        C19521() {
        }

        public void run(int response, int errorCode, String errorText, int networkType) {
            Exception e;
            TLObject resp = null;
            TLRPC$TL_error error = null;
            if (response != 0) {
                try {
                    NativeByteBuffer buff = NativeByteBuffer.wrap(response);
                    buff.reused = true;
                    resp = ConnectionsManager$1.this.val$object.deserializeResponse(buff, buff.readInt32(true), true);
                } catch (Exception e2) {
                    e = e2;
                    FileLog.e(e);
                    return;
                }
            } else if (errorText != null) {
                TLRPC$TL_error error2 = new TLRPC$TL_error();
                try {
                    error2.code = errorCode;
                    error2.text = errorText;
                    FileLog.e(ConnectionsManager$1.this.val$object + " got error " + error2.code + " " + error2.text);
                    error = error2;
                } catch (Exception e3) {
                    e = e3;
                    error = error2;
                    FileLog.e(e);
                    return;
                }
            }
            if (resp != null) {
                resp.networkType = networkType;
            }
            FileLog.d("java received " + resp + " error = " + error);
            final TLObject finalResponse = resp;
            final TLRPC$TL_error finalError = error;
            Utilities.stageQueue.postRunnable(new Runnable() {
                public void run() {
                    ConnectionsManager$1.this.val$onComplete.run(finalResponse, finalError);
                    if (finalResponse != null) {
                        finalResponse.freeResources();
                    }
                }
            });
        }
    }

    ConnectionsManager$1(ConnectionsManager this$0, TLObject tLObject, int i, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i2, int i3, int i4, boolean z) {
        this.this$0 = this$0;
        this.val$object = tLObject;
        this.val$requestToken = i;
        this.val$onComplete = requestDelegate;
        this.val$onQuickAck = quickAckDelegate;
        this.val$onWriteToSocket = writeToSocketDelegate;
        this.val$flags = i2;
        this.val$datacenterId = i3;
        this.val$connetionType = i4;
        this.val$immediate = z;
    }

    public void run() {
        FileLog.d("send request " + this.val$object + " with token = " + this.val$requestToken);
        try {
            NativeByteBuffer buffer = new NativeByteBuffer(this.val$object.getObjectSize());
            this.val$object.serializeToStream(buffer);
            this.val$object.freeResources();
            ConnectionsManager.native_sendRequest(buffer.address, new C19521(), this.val$onQuickAck, this.val$onWriteToSocket, this.val$flags, this.val$datacenterId, this.val$connetionType, this.val$immediate, this.val$requestToken);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }
}
