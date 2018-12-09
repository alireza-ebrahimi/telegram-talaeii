package org.telegram.messenger.exoplayer2;

import android.content.Context;
import org.telegram.messenger.exoplayer2.drm.DrmSessionManager;
import org.telegram.messenger.exoplayer2.drm.FrameworkMediaCrypto;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelector;

public final class ExoPlayerFactory {
    private ExoPlayerFactory() {
    }

    public static ExoPlayer newInstance(Renderer[] rendererArr, TrackSelector trackSelector) {
        return newInstance(rendererArr, trackSelector, new DefaultLoadControl());
    }

    public static ExoPlayer newInstance(Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl) {
        return new ExoPlayerImpl(rendererArr, trackSelector, loadControl);
    }

    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector) {
        return newSimpleInstance(new DefaultRenderersFactory(context), trackSelector);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl) {
        return newSimpleInstance(new DefaultRenderersFactory(context), trackSelector, loadControl);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(new DefaultRenderersFactory(context, drmSessionManager), trackSelector, loadControl);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i) {
        return newSimpleInstance(new DefaultRenderersFactory(context, drmSessionManager, i), trackSelector, loadControl);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i, long j) {
        return newSimpleInstance(new DefaultRenderersFactory(context, drmSessionManager, i, j), trackSelector, loadControl);
    }

    public static SimpleExoPlayer newSimpleInstance(RenderersFactory renderersFactory, TrackSelector trackSelector) {
        return newSimpleInstance(renderersFactory, trackSelector, new DefaultLoadControl());
    }

    public static SimpleExoPlayer newSimpleInstance(RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl) {
        return new SimpleExoPlayer(renderersFactory, trackSelector, loadControl);
    }
}
