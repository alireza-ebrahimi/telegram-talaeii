package org.telegram.messenger;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.io.File;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;

class MediaController$24 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ TLRPC$TL_document val$audioToSend;
    final /* synthetic */ File val$recordingAudioFileToSend;
    final /* synthetic */ int val$send;

    /* renamed from: org.telegram.messenger.MediaController$24$1 */
    class C14261 implements Runnable {

        /* renamed from: org.telegram.messenger.MediaController$24$1$1 */
        class C14241 implements OnClickListener {
            C14241() {
            }

            public void onClick(DialogInterface dialog, int which) {
            }
        }

        /* renamed from: org.telegram.messenger.MediaController$24$1$2 */
        class C14252 implements OnClickListener {
            C14252() {
            }

            public void onClick(DialogInterface dialog, int which) {
                SendMessagesHelper.getInstance().sendMessage(MediaController$24.this.val$audioToSend, null, MediaController$24.this.val$recordingAudioFileToSend.getAbsolutePath(), MediaController.access$6800(MediaController$24.this.this$0), MediaController.access$6900(MediaController$24.this.this$0), null, null, 0);
            }
        }

        C14261() {
        }

        public void run() {
            MediaController$24.this.val$audioToSend.date = ConnectionsManager.getInstance().getCurrentTime();
            MediaController$24.this.val$audioToSend.size = (int) MediaController$24.this.val$recordingAudioFileToSend.length();
            TLRPC$TL_documentAttributeAudio attributeAudio = new TLRPC$TL_documentAttributeAudio();
            attributeAudio.voice = true;
            attributeAudio.waveform = MediaController$24.this.this$0.getWaveform2(MediaController.access$400(MediaController$24.this.this$0), MediaController.access$400(MediaController$24.this.this$0).length);
            if (attributeAudio.waveform != null) {
                attributeAudio.flags |= 4;
            }
            long duration = MediaController.access$700(MediaController$24.this.this$0);
            attributeAudio.duration = (int) (MediaController.access$700(MediaController$24.this.this$0) / 1000);
            MediaController$24.this.val$audioToSend.attributes.add(attributeAudio);
            if (duration > 700) {
                if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("confirmForStickers", true) || MediaController.access$7200(MediaController$24.this.this$0) == null || MediaController.access$7200(MediaController$24.this.this$0).getParentActivity() == null) {
                    SendMessagesHelper.getInstance().sendMessage(MediaController$24.this.val$audioToSend, null, MediaController$24.this.val$recordingAudioFileToSend.getAbsolutePath(), MediaController.access$6800(MediaController$24.this.this$0), MediaController.access$6900(MediaController$24.this.this$0), null, null, 0);
                } else {
                    new Builder(MediaController.access$7200(MediaController$24.this.this$0).getParentActivity()).setMessage("برای ارسال صدا مطمین هستید؟").setPositiveButton("بله", new C14252()).setNegativeButton("خیر", new C14241()).setIcon(17301543).show();
                }
                NotificationCenter instance = NotificationCenter.getInstance();
                int i = NotificationCenter.audioDidSent;
                Object[] objArr = new Object[2];
                objArr[0] = MediaController$24.this.val$send == 2 ? MediaController$24.this.val$audioToSend : null;
                objArr[1] = MediaController$24.this.val$send == 2 ? MediaController$24.this.val$recordingAudioFileToSend.getAbsolutePath() : null;
                instance.postNotificationName(i, objArr);
                return;
            }
            MediaController$24.this.val$recordingAudioFileToSend.delete();
        }
    }

    MediaController$24(MediaController this$0, TLRPC$TL_document tLRPC$TL_document, File file, int i) {
        this.this$0 = this$0;
        this.val$audioToSend = tLRPC$TL_document;
        this.val$recordingAudioFileToSend = file;
        this.val$send = i;
    }

    public void run() {
        MediaController.access$7000(this.this$0);
        AndroidUtilities.runOnUIThread(new C14261());
    }
}
