package org.telegram.messenger;

import android.media.AudioRecord;
import java.io.File;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;

class MediaController$22 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ MessageObject val$reply_to_msg;

    /* renamed from: org.telegram.messenger.MediaController$22$1 */
    class C14191 implements Runnable {
        C14191() {
        }

        public void run() {
            MediaController.access$2102(MediaController$22.this.this$0, null);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$22$2 */
    class C14202 implements Runnable {
        C14202() {
        }

        public void run() {
            MediaController.access$2102(MediaController$22.this.this$0, null);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$22$3 */
    class C14213 implements Runnable {
        C14213() {
        }

        public void run() {
            MediaController.access$2102(MediaController$22.this.this$0, null);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$22$4 */
    class C14224 implements Runnable {
        C14224() {
        }

        public void run() {
            MediaController.access$2102(MediaController$22.this.this$0, null);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStarted, new Object[0]);
        }
    }

    MediaController$22(MediaController this$0, long j, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$dialog_id = j;
        this.val$reply_to_msg = messageObject;
    }

    public void run() {
        if (MediaController.access$000(this.this$0) != null) {
            AndroidUtilities.runOnUIThread(new C14191());
            return;
        }
        MediaController.access$2202(this.this$0, new TLRPC$TL_document());
        MediaController.access$2200(this.this$0).dc_id = Integer.MIN_VALUE;
        MediaController.access$2200(this.this$0).id = (long) UserConfig.lastLocalId;
        MediaController.access$2200(this.this$0).user_id = UserConfig.getClientUserId();
        MediaController.access$2200(this.this$0).mime_type = "audio/ogg";
        MediaController.access$2200(this.this$0).thumb = new TLRPC$TL_photoSizeEmpty();
        MediaController.access$2200(this.this$0).thumb.type = "s";
        UserConfig.lastLocalId--;
        UserConfig.saveConfig(false);
        MediaController.access$6602(this.this$0, new File(FileLoader.getInstance().getDirectory(4), FileLoader.getAttachFileName(MediaController.access$2200(this.this$0))));
        try {
            if (MediaController.access$6700(this.this$0, MediaController.access$6600(this.this$0).getAbsolutePath()) == 0) {
                AndroidUtilities.runOnUIThread(new C14202());
                return;
            }
            MediaController.access$002(this.this$0, new AudioRecord(1, 16000, 16, 2, MediaController.access$200(this.this$0) * 10));
            MediaController.access$1102(this.this$0, System.currentTimeMillis());
            MediaController.access$702(this.this$0, 0);
            MediaController.access$302(this.this$0, 0);
            MediaController.access$6802(this.this$0, this.val$dialog_id);
            MediaController.access$6902(this.this$0, this.val$reply_to_msg);
            MediaController.access$500(this.this$0).rewind();
            MediaController.access$000(this.this$0).startRecording();
            MediaController.access$800(this.this$0).postRunnable(MediaController.access$1000(this.this$0));
            AndroidUtilities.runOnUIThread(new C14224());
        } catch (Exception e) {
            FileLog.e(e);
            MediaController.access$2202(this.this$0, null);
            MediaController.access$7000(this.this$0);
            MediaController.access$6600(this.this$0).delete();
            MediaController.access$6602(this.this$0, null);
            try {
                MediaController.access$000(this.this$0).release();
                MediaController.access$002(this.this$0, null);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            AndroidUtilities.runOnUIThread(new C14213());
        }
    }
}
