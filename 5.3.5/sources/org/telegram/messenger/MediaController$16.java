package org.telegram.messenger;

import android.graphics.SurfaceTexture;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;

class MediaController$16 implements VideoPlayerDelegate {
    final /* synthetic */ MediaController this$0;

    /* renamed from: org.telegram.messenger.MediaController$16$1 */
    class C14181 implements Runnable {
        C14181() {
        }

        public void run() {
            MediaController$16.this.this$0.cleanupPlayer(true, true);
        }
    }

    MediaController$16(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (MediaController.access$2700(this.this$0) != null) {
            if (playbackState == 4 || playbackState == 1) {
                try {
                    MediaController.access$5200(this.this$0).getWindow().clearFlags(128);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else {
                try {
                    MediaController.access$5200(this.this$0).getWindow().addFlags(128);
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            if (playbackState == 3) {
                MediaController.access$5302(this.this$0, true);
                if (MediaController.access$5400(this.this$0) != null && MediaController.access$5400(this.this$0).getVisibility() != 0) {
                    MediaController.access$5400(this.this$0).setVisibility(0);
                }
            } else if (MediaController.access$2700(this.this$0).isPlaying() && playbackState == 4) {
                this.this$0.cleanupPlayer(true, true, true);
            }
        }
    }

    public void onError(Exception e) {
        FileLog.e(e);
    }

    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        MediaController.access$5502(this.this$0, unappliedRotationDegrees);
        if (unappliedRotationDegrees == 90 || unappliedRotationDegrees == 270) {
            int temp = width;
            width = height;
            height = temp;
        }
        MediaController.access$5602(this.this$0, height == 0 ? 1.0f : (((float) width) * pixelWidthHeightRatio) / ((float) height));
        if (MediaController.access$5700(this.this$0) != null) {
            MediaController.access$5700(this.this$0).setAspectRatio(MediaController.access$5600(this.this$0), MediaController.access$5500(this.this$0));
        }
    }

    public void onRenderedFirstFrame() {
        if (MediaController.access$5700(this.this$0) != null && !MediaController.access$5700(this.this$0).isDrawingReady()) {
            MediaController.access$5802(this.this$0, true);
            MediaController.access$5700(this.this$0).setDrawingReady(true);
            if (MediaController.access$5400(this.this$0) != null && MediaController.access$5400(this.this$0).getVisibility() != 0) {
                MediaController.access$5400(this.this$0).setVisibility(0);
            }
        }
    }

    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        if (MediaController.access$2700(this.this$0) == null) {
            return false;
        }
        if (MediaController.access$5900(this.this$0) == 2) {
            if (MediaController.access$5700(this.this$0) != null) {
                if (MediaController.access$5800(this.this$0)) {
                    MediaController.access$5700(this.this$0).setDrawingReady(true);
                }
                if (MediaController.access$5700(this.this$0).getParent() == null) {
                    MediaController.access$5400(this.this$0).addView(MediaController.access$5700(this.this$0));
                }
                if (MediaController.access$6000(this.this$0).getSurfaceTexture() != surfaceTexture) {
                    MediaController.access$6000(this.this$0).setSurfaceTexture(surfaceTexture);
                }
                MediaController.access$2700(this.this$0).setTextureView(MediaController.access$6000(this.this$0));
            }
            MediaController.access$5902(this.this$0, 0);
            return true;
        } else if (MediaController.access$5900(this.this$0) != 1) {
            return false;
        } else {
            if (MediaController.access$5200(this.this$0) != null) {
                if (MediaController.access$6100(this.this$0) == null) {
                    try {
                        MediaController.access$6102(this.this$0, new PipRoundVideoView());
                        MediaController.access$6100(this.this$0).show(MediaController.access$5200(this.this$0), new C14181());
                    } catch (Exception e) {
                        MediaController.access$6102(this.this$0, null);
                    }
                }
                if (MediaController.access$6100(this.this$0) != null) {
                    if (MediaController.access$6100(this.this$0).getTextureView().getSurfaceTexture() != surfaceTexture) {
                        MediaController.access$6100(this.this$0).getTextureView().setSurfaceTexture(surfaceTexture);
                    }
                    MediaController.access$2700(this.this$0).setTextureView(MediaController.access$6100(this.this$0).getTextureView());
                }
            }
            MediaController.access$5902(this.this$0, 0);
            return true;
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
