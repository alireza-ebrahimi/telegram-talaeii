package org.telegram.customization.dynamicadapter.viewholder;

class SlsMessageHolder$9 implements Runnable {
    final /* synthetic */ SlsMessageHolder this$0;

    SlsMessageHolder$9(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.ivPlayVideo.setVisibility(8);
        this.this$0.pbImageLoading.setVisibility(0);
        this.this$0.pbImageLoading.startAnimation(this.this$0.rotation);
        this.this$0.pbImageLoading.setProgress(2.0f);
    }
}
