package org.telegram.messenger.exoplayer2;

import android.os.Handler;
import org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener;
import org.telegram.messenger.exoplayer2.metadata.MetadataRenderer;
import org.telegram.messenger.exoplayer2.text.TextRenderer.Output;
import org.telegram.messenger.exoplayer2.video.VideoRendererEventListener;

public interface RenderersFactory {
    Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, Output output, MetadataRenderer.Output output2);
}
