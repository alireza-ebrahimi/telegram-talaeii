package org.telegram.customization.dynamicadapter.viewholder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import org.ir.talaeii.R;
import org.telegram.messenger.FileLoader;

class SlsMessageHolder$10 implements OnClickListener {
    final /* synthetic */ SlsMessageHolder this$0;

    SlsMessageHolder$10(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        Log.d("LEE", "OnClick:ivPlayVideo");
        this.this$0.fillTlMsg(SlsMessageHolder.access$000(this.this$0));
        SlsMessageHolder.access$502(this.this$0, this.this$0.tlMsg.getMessage().media.document);
        if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.access$500(this.this$0)))) {
            FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.access$500(this.this$0));
            return;
        }
        FileLoader.getInstance().loadFile(SlsMessageHolder.access$500(this.this$0), true, 0);
        this.this$0.pbImageLoading.setVisibility(0);
        this.this$0.pbImageLoading.setProgress(2.0f);
        this.this$0.pbImageLoading.startAnimation(this.this$0.rotation);
        this.this$0.ivPlayVideo.setImageResource(R.drawable.cancel_big);
    }
}
