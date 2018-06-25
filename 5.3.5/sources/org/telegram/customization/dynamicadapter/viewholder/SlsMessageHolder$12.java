package org.telegram.customization.dynamicadapter.viewholder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.File;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.MediaController;

class SlsMessageHolder$12 implements OnClickListener {
    final /* synthetic */ SlsMessageHolder this$0;
    final /* synthetic */ File val$dlFile1;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$12$1 */
    class C12021 implements Runnable {

        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$12$1$1 */
        class C12011 implements Runnable {
            C12011() {
            }

            public void run() {
                SlsMessageHolder$12.this.this$0.musicProgressBar.setVisibility(0);
                SlsMessageHolder$12.this.this$0.musicProgressBar.setProgress(2.0f);
                SlsMessageHolder$12.this.this$0.musicProgressBar.requestLayout();
                SlsMessageHolder$12.this.this$0.musicProgressBar.invalidate();
                SlsMessageHolder$12.this.this$0.musicProgressBar.startAnimation(SlsMessageHolder$12.this.this$0.rotation1);
                SlsMessageHolder$12.this.this$0.musicProgressBar.setAnimation(SlsMessageHolder$12.this.this$0.rotation1);
                SlsMessageHolder$12.this.this$0.rotation1.startNow();
                SlsMessageHolder$12.this.this$0.musicProgressBar.invalidate();
                SlsMessageHolder.access$302(SlsMessageHolder$12.this.this$0, 3);
                SlsMessageHolder.access$400(SlsMessageHolder$12.this.this$0);
            }
        }

        C12021() {
        }

        public void run() {
            SlsMessageHolder$12.this.this$0.musicProgressBar.post(new C12011());
        }
    }

    SlsMessageHolder$12(SlsMessageHolder this$0, File file) {
        this.this$0 = this$0;
        this.val$dlFile1 = file;
    }

    public void onClick(View view) {
        if (FileLoader.getInstance().isLoadingFile(FileLoader.slsGetAttachFileName(SlsMessageHolder.access$500(this.this$0), null))) {
            Log.d("alireza", "alireza file is loading");
            FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.access$500(this.this$0));
            MediaController.getInstance().cleanupPlayer(true, true);
        } else if (this.val$dlFile1.exists() && this.val$dlFile1.length() == ((long) this.this$0.tlMsg.getMessage().media.document.size)) {
            SlsMessageHolder.access$302(this.this$0, 3);
            SlsMessageHolder.access$400(this.this$0);
        } else {
            AndroidUtilities.runOnUIThread(new C12021());
        }
    }
}
