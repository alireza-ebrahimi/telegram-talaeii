package org.telegram.messenger.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import org.telegram.messenger.exoplayer2.audio.AudioProcessor;
import org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener;
import org.telegram.messenger.exoplayer2.drm.DrmSessionManager;
import org.telegram.messenger.exoplayer2.drm.FrameworkMediaCrypto;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecSelector;
import org.telegram.messenger.exoplayer2.metadata.MetadataRenderer;
import org.telegram.messenger.exoplayer2.metadata.MetadataRenderer.Output;
import org.telegram.messenger.exoplayer2.text.TextRenderer;
import org.telegram.messenger.exoplayer2.video.MediaCodecVideoRenderer;
import org.telegram.messenger.exoplayer2.video.VideoRendererEventListener;

public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    protected static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private final long allowedVideoJoiningTimeMs;
    private final Context context;
    private final DrmSessionManager<FrameworkMediaCrypto> drmSessionManager;
    private final int extensionRendererMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    public DefaultRenderersFactory(Context context) {
        this(context, null);
    }

    public DefaultRenderersFactory(Context context, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        this(context, drmSessionManager, 0);
    }

    public DefaultRenderersFactory(Context context, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i) {
        this(context, drmSessionManager, i, DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    public DefaultRenderersFactory(Context context, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i, long j) {
        this.context = context;
        this.drmSessionManager = drmSessionManager;
        this.extensionRendererMode = i;
        this.allowedVideoJoiningTimeMs = j;
    }

    protected AudioProcessor[] buildAudioProcessors() {
        return new AudioProcessor[0];
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void buildAudioRenderers(android.content.Context r9, org.telegram.messenger.exoplayer2.drm.DrmSessionManager<org.telegram.messenger.exoplayer2.drm.FrameworkMediaCrypto> r10, org.telegram.messenger.exoplayer2.audio.AudioProcessor[] r11, android.os.Handler r12, org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener r13, int r14, java.util.ArrayList<org.telegram.messenger.exoplayer2.Renderer> r15) {
        /*
        r8 = this;
        r0 = new org.telegram.messenger.exoplayer2.audio.MediaCodecAudioRenderer;
        r1 = org.telegram.messenger.exoplayer2.mediacodec.MediaCodecSelector.DEFAULT;
        r3 = 1;
        r6 = org.telegram.messenger.exoplayer2.audio.AudioCapabilities.getCapabilities(r9);
        r2 = r10;
        r4 = r12;
        r5 = r13;
        r7 = r11;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r15.add(r0);
        if (r14 != 0) goto L_0x0016;
    L_0x0015:
        return;
    L_0x0016:
        r2 = r15.size();
        r0 = 2;
        if (r14 != r0) goto L_0x001f;
    L_0x001d:
        r2 = r2 + -1;
    L_0x001f:
        r0 = "org.telegram.messenger.exoplayer2.ext.opus.LibopusAudioRenderer";
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r1 = 3;
        r1 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 0;
        r4 = android.os.Handler.class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 1;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener.class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 2;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioProcessor[].class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r0 = r0.getConstructor(r1);	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r1 = 3;
        r1 = new java.lang.Object[r1];	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 0;
        r1[r3] = r12;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 1;
        r1[r3] = r13;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r3 = 2;
        r1[r3] = r11;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r0 = r0.newInstance(r1);	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r0 = (org.telegram.messenger.exoplayer2.Renderer) r0;	 Catch:{ ClassNotFoundException -> 0x00dc, Exception -> 0x00e1 }
        r1 = r2 + 1;
        r15.add(r2, r0);	 Catch:{ ClassNotFoundException -> 0x00fd, Exception -> 0x00e1 }
        r0 = "DefaultRenderersFactory";
        r2 = "Loaded LibopusAudioRenderer.";
        android.util.Log.i(r0, r2);	 Catch:{ ClassNotFoundException -> 0x00fd, Exception -> 0x00e1 }
        r2 = r1;
    L_0x005d:
        r0 = "org.telegram.messenger.exoplayer2.ext.flac.LibflacAudioRenderer";
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r1 = 3;
        r1 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 0;
        r4 = android.os.Handler.class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 1;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener.class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 2;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioProcessor[].class;
        r1[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r0 = r0.getConstructor(r1);	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r1 = 3;
        r1 = new java.lang.Object[r1];	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 0;
        r1[r3] = r12;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 1;
        r1[r3] = r13;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r3 = 2;
        r1[r3] = r11;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r0 = r0.newInstance(r1);	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r0 = (org.telegram.messenger.exoplayer2.Renderer) r0;	 Catch:{ ClassNotFoundException -> 0x00e8, Exception -> 0x00ec }
        r1 = r2 + 1;
        r15.add(r2, r0);	 Catch:{ ClassNotFoundException -> 0x00fa, Exception -> 0x00ec }
        r0 = "DefaultRenderersFactory";
        r2 = "Loaded LibflacAudioRenderer.";
        android.util.Log.i(r0, r2);	 Catch:{ ClassNotFoundException -> 0x00fa, Exception -> 0x00ec }
    L_0x009a:
        r0 = "org.telegram.messenger.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer";
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r2 = 3;
        r2 = new java.lang.Class[r2];	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 0;
        r4 = android.os.Handler.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 1;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 2;
        r4 = org.telegram.messenger.exoplayer2.audio.AudioProcessor[].class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r0 = r0.getConstructor(r2);	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r2 = 3;
        r2 = new java.lang.Object[r2];	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 0;
        r2[r3] = r12;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 1;
        r2[r3] = r13;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r3 = 2;
        r2[r3] = r11;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r0 = r0.newInstance(r2);	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r0 = (org.telegram.messenger.exoplayer2.Renderer) r0;	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r2 = r1 + 1;
        r15.add(r1, r0);	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        r0 = "DefaultRenderersFactory";
        r1 = "Loaded FfmpegAudioRenderer.";
        android.util.Log.i(r0, r1);	 Catch:{ ClassNotFoundException -> 0x00d9, Exception -> 0x00f3 }
        goto L_0x0015;
    L_0x00d9:
        r0 = move-exception;
        goto L_0x0015;
    L_0x00dc:
        r0 = move-exception;
        r0 = r2;
    L_0x00de:
        r2 = r0;
        goto L_0x005d;
    L_0x00e1:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r1.<init>(r0);
        throw r1;
    L_0x00e8:
        r0 = move-exception;
        r0 = r2;
    L_0x00ea:
        r1 = r0;
        goto L_0x009a;
    L_0x00ec:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r1.<init>(r0);
        throw r1;
    L_0x00f3:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r1.<init>(r0);
        throw r1;
    L_0x00fa:
        r0 = move-exception;
        r0 = r1;
        goto L_0x00ea;
    L_0x00fd:
        r0 = move-exception;
        r0 = r1;
        goto L_0x00de;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.DefaultRenderersFactory.buildAudioRenderers(android.content.Context, org.telegram.messenger.exoplayer2.drm.DrmSessionManager, org.telegram.messenger.exoplayer2.audio.AudioProcessor[], android.os.Handler, org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener, int, java.util.ArrayList):void");
    }

    protected void buildMetadataRenderers(Context context, Output output, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new MetadataRenderer(output, looper));
    }

    protected void buildMiscellaneousRenderers(Context context, Handler handler, int i, ArrayList<Renderer> arrayList) {
    }

    protected void buildTextRenderers(Context context, TextRenderer.Output output, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new TextRenderer(output, looper));
    }

    protected void buildVideoRenderers(Context context, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, long j, Handler handler, VideoRendererEventListener videoRendererEventListener, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new MediaCodecVideoRenderer(context, MediaCodecSelector.DEFAULT, j, drmSessionManager, false, handler, videoRendererEventListener, 50));
        if (i != 0) {
            int size = arrayList.size();
            int i2 = i == 2 ? size - 1 : size;
            try {
                int i3 = i2 + 1;
                arrayList.add(i2, (Renderer) Class.forName("org.telegram.messenger.exoplayer2.ext.vp9.LibvpxVideoRenderer").getConstructor(new Class[]{Boolean.TYPE, Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE}).newInstance(new Object[]{Boolean.valueOf(true), Long.valueOf(j), handler, videoRendererEventListener, Integer.valueOf(50)}));
                Log.i(TAG, "Loaded LibvpxVideoRenderer.");
            } catch (ClassNotFoundException e) {
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextRenderer.Output output, Output output2) {
        ArrayList arrayList = new ArrayList();
        buildVideoRenderers(this.context, this.drmSessionManager, this.allowedVideoJoiningTimeMs, handler, videoRendererEventListener, this.extensionRendererMode, arrayList);
        buildAudioRenderers(this.context, this.drmSessionManager, buildAudioProcessors(), handler, audioRendererEventListener, this.extensionRendererMode, arrayList);
        buildTextRenderers(this.context, output, handler.getLooper(), this.extensionRendererMode, arrayList);
        buildMetadataRenderers(this.context, output2, handler.getLooper(), this.extensionRendererMode, arrayList);
        buildMiscellaneousRenderers(this.context, handler, this.extensionRendererMode, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[arrayList.size()]);
    }
}
