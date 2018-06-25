package org.telegram.customization.dynamicadapter.viewholder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLoader.FileLoaderDelegate;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.ui.Components.AnimatedFileDrawable;

class SlsMessageHolder$1 implements FileLoaderDelegate {
    final /* synthetic */ SlsMessageHolder this$0;

    SlsMessageHolder$1(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void fileUploadProgressChanged(String location, float progress, boolean isEncrypted) {
    }

    public void fileDidUploaded(String location, TLRPC$InputFile inputFile, TLRPC$InputEncryptedFile inputEncryptedFile, byte[] key, byte[] iv, long totalFileSize) {
    }

    public void fileDidFailedUpload(String location, boolean isEncrypted) {
    }

    public void fileDidLoaded(String location, File finalFile, final int type) {
        if (finalFile.getName().contains(String.valueOf(this.this$0.delegateDocId))) {
            final File f = finalFile;
            AndroidUtilities.runOnUIThread(new Runnable() {

                /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$1 */
                class C11921 implements OnClickListener {
                    C11921() {
                    }

                    public void onClick(View v) {
                        SlsMessageHolder$1.this.this$0.ivPlayVideo.setVisibility(8);
                        SlsMessageHolder.access$102(SlsMessageHolder$1.this.this$0, new AnimatedFileDrawable(f, false));
                        SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0).setParentView(SlsMessageHolder$1.this.this$0.ivMain);
                        SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0).start();
                        SlsMessageHolder$1.this.this$0.ivMain.setImageDrawable(SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0));
                    }
                }

                /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$2 */
                class C11932 implements OnClickListener {
                    C11932() {
                    }

                    public void onClick(View view) {
                        SlsMessageHolder.access$200(SlsMessageHolder$1.this.this$0);
                    }
                }

                /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$3 */
                class C11943 implements OnClickListener {
                    C11943() {
                    }

                    public void onClick(View view) {
                        Log.d("LEE", "Music_Ready_For_Play");
                        SlsMessageHolder.access$302(SlsMessageHolder$1.this.this$0, 3);
                        SlsMessageHolder.access$400(SlsMessageHolder$1.this.this$0);
                    }
                }

                public void run() {
                    if (f.getName().contains(String.valueOf(SlsMessageHolder$1.this.this$0.delegateDocId))) {
                        SlsMessageHolder$1.this.this$0.pbImageLoading.clearAnimation();
                        SlsMessageHolder$1.this.this$0.pbImageLoading.setVisibility(8);
                        SlsMessageHolder$1.this.this$0.musicProgressBar.clearAnimation();
                        SlsMessageHolder$1.this.this$0.musicProgressBar.setVisibility(8);
                        if (f.exists()) {
                            SlsMessageHolder$1.this.this$0.pbImageLoading.clearAnimation();
                            SlsMessageHolder$1.this.this$0.ivPlayStreamVideo.setVisibility(8);
                            if (type != -22 && SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() != 8 && SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() != 9) {
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setVisibility(0);
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setImageResource(R.drawable.playvideo_pressed);
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setOnClickListener(new C11921());
                            } else if (SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() == 6) {
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setVisibility(8);
                                SlsMessageHolder.access$102(SlsMessageHolder$1.this.this$0, new AnimatedFileDrawable(f, false));
                                SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0).setParentView(SlsMessageHolder$1.this.this$0.ivMain);
                                SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0).start();
                                SlsMessageHolder$1.this.this$0.ivMain.setImageDrawable(SlsMessageHolder.access$100(SlsMessageHolder$1.this.this$0));
                            } else if (SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() == 8) {
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setVisibility(0);
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setImageResource(R.drawable.playvideo_pressed);
                                SlsMessageHolder$1.this.this$0.ivPlayVideo.setOnClickListener(new C11932());
                            } else if (SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() == 9) {
                                SlsMessageHolder$1.this.this$0.musicProgressBar.setVisibility(8);
                                SlsMessageHolder$1.this.this$0.ivDownloadMusic.setVisibility(0);
                                SlsMessageHolder$1.this.this$0.ivDownloadMusic.setImageResource(R.drawable.playvideo_pressed);
                                SlsMessageHolder$1.this.this$0.ivDownloadMusic.setOnClickListener(new C11943());
                            }
                        }
                    }
                }
            });
        }
    }

    public void fileDidFailedLoad(String location, int state) {
        if (location.contains(String.valueOf(this.this$0.delegateDocId))) {
            final String loc = location;
            this.this$0.ivPlayVideo.post(new Runnable() {

                /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1 */
                class C11981 implements Runnable {

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1$1 */
                    class C11961 implements OnClickListener {
                        C11961() {
                        }

                        public void onClick(View view) {
                            SlsMessageHolder$1.this.this$0.fillTlMsg(SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0));
                            SlsMessageHolder.access$502(SlsMessageHolder$1.this.this$0, SlsMessageHolder$1.this.this$0.tlMsg.getMessage().media.document);
                            if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0)))) {
                                FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0));
                                return;
                            }
                            FileLoader.getInstance().loadFile(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0), true, 0);
                            SlsMessageHolder$1.this.this$0.musicProgressBar.setVisibility(0);
                            SlsMessageHolder$1.this.this$0.musicProgressBar.setProgress(2.0f);
                            SlsMessageHolder$1.this.this$0.musicProgressBar.startAnimation(SlsMessageHolder$1.this.this$0.rotation1);
                            SlsMessageHolder$1.this.this$0.musicProgressBar.setAnimation(SlsMessageHolder$1.this.this$0.rotation1);
                            SlsMessageHolder$1.this.this$0.rotation1.startNow();
                            SlsMessageHolder$1.this.this$0.musicProgressBar.invalidate();
                        }
                    }

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1$2 */
                    class C11972 implements OnClickListener {
                        C11972() {
                        }

                        public void onClick(View v) {
                            SlsMessageHolder$1.this.this$0.fillTlMsg(SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0));
                            SlsMessageHolder.access$502(SlsMessageHolder$1.this.this$0, SlsMessageHolder$1.this.this$0.tlMsg.getMessage().media.document);
                            if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0)))) {
                                FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0));
                                return;
                            }
                            FileLoader.getInstance().loadFile(SlsMessageHolder.access$500(SlsMessageHolder$1.this.this$0), true, 0);
                            SlsMessageHolder$1.this.this$0.pbImageLoading.setVisibility(0);
                            SlsMessageHolder$1.this.this$0.pbImageLoading.setProgress(2.0f);
                            SlsMessageHolder$1.this.this$0.pbImageLoading.startAnimation(SlsMessageHolder$1.this.this$0.rotation);
                            SlsMessageHolder$1.this.this$0.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                        }
                    }

                    C11981() {
                    }

                    public void run() {
                        if (!loc.contains(String.valueOf(SlsMessageHolder$1.this.this$0.delegateDocId))) {
                            return;
                        }
                        if (SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() == 9) {
                            SlsMessageHolder$1.this.this$0.musicProgressBar.clearAnimation();
                            SlsMessageHolder$1.this.this$0.musicProgressBar.setVisibility(8);
                            SlsMessageHolder$1.this.this$0.ivDownloadMusic.setVisibility(0);
                            SlsMessageHolder$1.this.this$0.ivDownloadMusic.setImageResource(R.drawable.load_big);
                            SlsMessageHolder$1.this.this$0.ivDownloadMusic.setOnClickListener(new C11961());
                            return;
                        }
                        SlsMessageHolder$1.this.this$0.pbImageLoading.clearAnimation();
                        SlsMessageHolder$1.this.this$0.pbImageLoading.setVisibility(8);
                        SlsMessageHolder$1.this.this$0.ivPlayVideo.setVisibility(0);
                        SlsMessageHolder$1.this.this$0.ivPlayVideo.setImageResource(R.drawable.load_big);
                        SlsMessageHolder$1.this.this$0.ivPlayVideo.setOnClickListener(new C11972());
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C11981());
                }
            });
        }
    }

    public void fileLoadProgressChanged(String location, float progress) {
        if (location.contains(String.valueOf(this.this$0.delegateDocId))) {
            final String loc = location;
            final int prog = (int) (100.0f * progress);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (!loc.contains(String.valueOf(SlsMessageHolder$1.this.this$0.delegateDocId))) {
                        return;
                    }
                    if (SlsMessageHolder.access$000(SlsMessageHolder$1.this.this$0).getMessage().getMediaType() == 9) {
                        SlsMessageHolder$1.this.this$0.musicProgressBar.setVisibility(0);
                        SlsMessageHolder$1.this.this$0.musicProgressBar.setProgress((float) prog);
                        return;
                    }
                    SlsMessageHolder$1.this.this$0.pbImageLoading.setVisibility(0);
                    SlsMessageHolder$1.this.this$0.pbImageLoading.setProgress((float) prog);
                    SlsMessageHolder$1.this.this$0.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                }
            });
        }
    }
}
