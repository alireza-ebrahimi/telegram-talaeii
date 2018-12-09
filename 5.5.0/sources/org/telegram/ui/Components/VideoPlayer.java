package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.view.TextureView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.ExoPlaybackException;
import org.telegram.messenger.exoplayer2.ExoPlayer.EventListener;
import org.telegram.messenger.exoplayer2.ExoPlayerFactory;
import org.telegram.messenger.exoplayer2.PlaybackParameters;
import org.telegram.messenger.exoplayer2.Player;
import org.telegram.messenger.exoplayer2.SimpleExoPlayer;
import org.telegram.messenger.exoplayer2.SimpleExoPlayer.VideoListener;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.trackselection.AdaptiveTrackSelection;
import org.telegram.messenger.exoplayer2.trackselection.DefaultTrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.MappingTrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectionArray;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.upstream.DefaultBandwidthMeter;
import org.telegram.messenger.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import org.telegram.messenger.secretmedia.ExtendedDefaultDataSourceFactory;

@SuppressLint({"NewApi"})
public class VideoPlayer implements EventListener, VideoListener {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final int RENDERER_BUILDING_STATE_BUILDING = 2;
    private static final int RENDERER_BUILDING_STATE_BUILT = 3;
    private static final int RENDERER_BUILDING_STATE_IDLE = 1;
    private SimpleExoPlayer audioPlayer;
    private boolean audioPlayerReady;
    private boolean autoplay;
    private VideoPlayerDelegate delegate;
    private boolean lastReportedPlayWhenReady;
    private int lastReportedPlaybackState = 1;
    private Handler mainHandler = new Handler();
    private Factory mediaDataSourceFactory = new ExtendedDefaultDataSourceFactory(ApplicationLoader.applicationContext, BANDWIDTH_METER, new DefaultHttpDataSourceFactory("Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20150101 Firefox/47.0 (Chrome)", BANDWIDTH_METER));
    private boolean mixedAudio;
    private boolean mixedPlayWhenReady;
    private SimpleExoPlayer player;
    private TextureView textureView;
    private MappingTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
    private boolean videoPlayerReady;

    public interface VideoPlayerDelegate {
        void onError(Exception exception);

        void onRenderedFirstFrame();

        void onStateChanged(boolean z, int i);

        boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture);

        void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture);

        void onVideoSizeChanged(int i, int i2, int i3, float f);
    }

    /* renamed from: org.telegram.ui.Components.VideoPlayer$1 */
    class C46391 implements Player.EventListener {
        C46391() {
        }

        public void onLoadingChanged(boolean z) {
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        }

        public void onPlayerStateChanged(boolean z, int i) {
            if (!VideoPlayer.this.audioPlayerReady && i == 3) {
                VideoPlayer.this.audioPlayerReady = true;
                VideoPlayer.this.checkPlayersReady();
            }
        }

        public void onPositionDiscontinuity() {
        }

        public void onRepeatModeChanged(int i) {
        }

        public void onTimelineChanged(Timeline timeline, Object obj) {
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        }
    }

    public interface RendererBuilder {
        void buildRenderers(VideoPlayer videoPlayer);

        void cancel();
    }

    private void checkPlayersReady() {
        if (this.audioPlayerReady && this.videoPlayerReady && this.mixedPlayWhenReady) {
            play();
        }
    }

    private void ensurePleyaerCreated() {
        if (this.player == null) {
            this.player = ExoPlayerFactory.newSimpleInstance(ApplicationLoader.applicationContext, this.trackSelector, new DefaultLoadControl(), null, 0);
            this.player.addListener(this);
            this.player.setVideoListener(this);
            this.player.setVideoTextureView(this.textureView);
            this.player.setPlayWhenReady(this.autoplay);
        }
        if (this.mixedAudio && this.audioPlayer == null) {
            this.audioPlayer = ExoPlayerFactory.newSimpleInstance(ApplicationLoader.applicationContext, this.trackSelector, new DefaultLoadControl(), null, 0);
            this.audioPlayer.addListener(new C46391());
            this.audioPlayer.setPlayWhenReady(this.autoplay);
        }
    }

    private void maybeReportPlayerState() {
        boolean playWhenReady = this.player.getPlayWhenReady();
        int playbackState = this.player.getPlaybackState();
        if (this.lastReportedPlayWhenReady != playWhenReady || this.lastReportedPlaybackState != playbackState) {
            this.delegate.onStateChanged(playWhenReady, playbackState);
            this.lastReportedPlayWhenReady = playWhenReady;
            this.lastReportedPlaybackState = playbackState;
        }
    }

    public int getBufferedPercentage() {
        return this.player != null ? this.player.getBufferedPercentage() : 0;
    }

    public long getBufferedPosition() {
        return this.player != null ? this.player.getBufferedPosition() : 0;
    }

    public long getCurrentPosition() {
        return this.player != null ? this.player.getCurrentPosition() : 0;
    }

    public long getDuration() {
        return this.player != null ? this.player.getDuration() : 0;
    }

    public boolean isBuffering() {
        return this.player != null && this.lastReportedPlaybackState == 2;
    }

    public boolean isMuted() {
        return this.player.getVolume() == BitmapDescriptorFactory.HUE_RED;
    }

    public boolean isPlayerPrepared() {
        return this.player != null;
    }

    public boolean isPlaying() {
        return (this.mixedAudio && this.mixedPlayWhenReady) || (this.player != null && this.player.getPlayWhenReady());
    }

    public void onLoadingChanged(boolean z) {
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        this.delegate.onError(exoPlaybackException);
    }

    public void onPlayerStateChanged(boolean z, int i) {
        maybeReportPlayerState();
        if (!this.videoPlayerReady && i == 3) {
            this.videoPlayerReady = true;
            checkPlayersReady();
        }
    }

    public void onPositionDiscontinuity() {
    }

    public void onRenderedFirstFrame() {
        this.delegate.onRenderedFirstFrame();
    }

    public void onRepeatModeChanged(int i) {
    }

    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        return this.delegate.onSurfaceDestroyed(surfaceTexture);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.delegate.onSurfaceTextureUpdated(surfaceTexture);
    }

    public void onTimelineChanged(Timeline timeline, Object obj) {
    }

    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
    }

    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
        this.delegate.onVideoSizeChanged(i, i2, i3, f);
    }

    public void pause() {
        this.mixedPlayWhenReady = false;
        if (this.player != null) {
            this.player.setPlayWhenReady(false);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setPlayWhenReady(false);
        }
    }

    public void play() {
        this.mixedPlayWhenReady = true;
        if (!this.mixedAudio || (this.audioPlayerReady && this.videoPlayerReady)) {
            if (this.player != null) {
                this.player.setPlayWhenReady(true);
            }
            if (this.audioPlayer != null) {
                this.audioPlayer.setPlayWhenReady(true);
                return;
            }
            return;
        }
        if (this.player != null) {
            this.player.setPlayWhenReady(false);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setPlayWhenReady(false);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void preparePlayer(android.net.Uri r8, java.lang.String r9) {
        /*
        r7 = this;
        r6 = 1;
        r0 = 0;
        r5 = 0;
        r7.videoPlayerReady = r0;
        r7.mixedAudio = r0;
        r7.ensurePleyaerCreated();
        r1 = -1;
        r2 = r9.hashCode();
        switch(r2) {
            case 3680: goto L_0x0040;
            case 103407: goto L_0x0035;
            case 3075986: goto L_0x002b;
            default: goto L_0x0012;
        };
    L_0x0012:
        r0 = r1;
    L_0x0013:
        switch(r0) {
            case 0: goto L_0x004b;
            case 1: goto L_0x005d;
            case 2: goto L_0x0067;
            default: goto L_0x0016;
        };
    L_0x0016:
        r0 = new org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
        r2 = r7.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.extractor.DefaultExtractorsFactory;
        r3.<init>();
        r4 = r7.mainHandler;
        r1 = r8;
        r0.<init>(r1, r2, r3, r4, r5);
    L_0x0025:
        r1 = r7.player;
        r1.prepare(r0, r6, r6);
        return;
    L_0x002b:
        r2 = "dash";
        r2 = r9.equals(r2);
        if (r2 == 0) goto L_0x0012;
    L_0x0034:
        goto L_0x0013;
    L_0x0035:
        r0 = "hls";
        r0 = r9.equals(r0);
        if (r0 == 0) goto L_0x0012;
    L_0x003e:
        r0 = r6;
        goto L_0x0013;
    L_0x0040:
        r0 = "ss";
        r0 = r9.equals(r0);
        if (r0 == 0) goto L_0x0012;
    L_0x0049:
        r0 = 2;
        goto L_0x0013;
    L_0x004b:
        r0 = new org.telegram.messenger.exoplayer2.source.dash.DashMediaSource;
        r2 = r7.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.source.dash.DefaultDashChunkSource$Factory;
        r1 = r7.mediaDataSourceFactory;
        r3.<init>(r1);
        r4 = r7.mainHandler;
        r1 = r8;
        r0.<init>(r1, r2, r3, r4, r5);
        goto L_0x0025;
    L_0x005d:
        r0 = new org.telegram.messenger.exoplayer2.source.hls.HlsMediaSource;
        r1 = r7.mediaDataSourceFactory;
        r2 = r7.mainHandler;
        r0.<init>(r8, r1, r2, r5);
        goto L_0x0025;
    L_0x0067:
        r0 = new org.telegram.messenger.exoplayer2.source.smoothstreaming.SsMediaSource;
        r2 = r7.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.source.smoothstreaming.DefaultSsChunkSource$Factory;
        r1 = r7.mediaDataSourceFactory;
        r3.<init>(r1);
        r4 = r7.mainHandler;
        r1 = r8;
        r0.<init>(r1, r2, r3, r4, r5);
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.VideoPlayer.preparePlayer(android.net.Uri, java.lang.String):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void preparePlayerLoop(android.net.Uri r10, java.lang.String r11, android.net.Uri r12, java.lang.String r13) {
        /*
        r9 = this;
        r0 = 1;
        r9.mixedAudio = r0;
        r0 = 0;
        r9.audioPlayerReady = r0;
        r0 = 0;
        r9.videoPlayerReady = r0;
        r9.ensurePleyaerCreated();
        r7 = 0;
        r6 = 0;
        r0 = 0;
        r8 = r0;
    L_0x0010:
        r0 = 2;
        if (r8 >= r0) goto L_0x009a;
    L_0x0013:
        if (r8 != 0) goto L_0x0042;
    L_0x0015:
        r1 = r10;
        r0 = r11;
    L_0x0017:
        r2 = -1;
        r3 = r0.hashCode();
        switch(r3) {
            case 3680: goto L_0x005b;
            case 103407: goto L_0x0050;
            case 3075986: goto L_0x0045;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = r2;
    L_0x0020:
        switch(r0) {
            case 0: goto L_0x0066;
            case 1: goto L_0x0079;
            case 2: goto L_0x0085;
            default: goto L_0x0023;
        };
    L_0x0023:
        r0 = new org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
        r2 = r9.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.extractor.DefaultExtractorsFactory;
        r3.<init>();
        r4 = r9.mainHandler;
        r5 = 0;
        r0.<init>(r1, r2, r3, r4, r5);
        r1 = r0;
    L_0x0033:
        r0 = new org.telegram.messenger.exoplayer2.source.LoopingMediaSource;
        r0.<init>(r1);
        if (r8 != 0) goto L_0x0098;
    L_0x003a:
        r1 = r0;
        r0 = r6;
    L_0x003c:
        r2 = r8 + 1;
        r8 = r2;
        r6 = r0;
        r7 = r1;
        goto L_0x0010;
    L_0x0042:
        r1 = r12;
        r0 = r13;
        goto L_0x0017;
    L_0x0045:
        r3 = "dash";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x001f;
    L_0x004e:
        r0 = 0;
        goto L_0x0020;
    L_0x0050:
        r3 = "hls";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0059:
        r0 = 1;
        goto L_0x0020;
    L_0x005b:
        r3 = "ss";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0064:
        r0 = 2;
        goto L_0x0020;
    L_0x0066:
        r0 = new org.telegram.messenger.exoplayer2.source.dash.DashMediaSource;
        r2 = r9.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.source.dash.DefaultDashChunkSource$Factory;
        r4 = r9.mediaDataSourceFactory;
        r3.<init>(r4);
        r4 = r9.mainHandler;
        r5 = 0;
        r0.<init>(r1, r2, r3, r4, r5);
        r1 = r0;
        goto L_0x0033;
    L_0x0079:
        r0 = new org.telegram.messenger.exoplayer2.source.hls.HlsMediaSource;
        r2 = r9.mediaDataSourceFactory;
        r3 = r9.mainHandler;
        r4 = 0;
        r0.<init>(r1, r2, r3, r4);
        r1 = r0;
        goto L_0x0033;
    L_0x0085:
        r0 = new org.telegram.messenger.exoplayer2.source.smoothstreaming.SsMediaSource;
        r2 = r9.mediaDataSourceFactory;
        r3 = new org.telegram.messenger.exoplayer2.source.smoothstreaming.DefaultSsChunkSource$Factory;
        r4 = r9.mediaDataSourceFactory;
        r3.<init>(r4);
        r4 = r9.mainHandler;
        r5 = 0;
        r0.<init>(r1, r2, r3, r4, r5);
        r1 = r0;
        goto L_0x0033;
    L_0x0098:
        r1 = r7;
        goto L_0x003c;
    L_0x009a:
        r0 = r9.player;
        r1 = 1;
        r2 = 1;
        r0.prepare(r7, r1, r2);
        r0 = r9.audioPlayer;
        r1 = 1;
        r2 = 1;
        r0.prepare(r6, r1, r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.VideoPlayer.preparePlayerLoop(android.net.Uri, java.lang.String, android.net.Uri, java.lang.String):void");
    }

    public void releasePlayer() {
        if (this.player != null) {
            this.player.release();
            this.player = null;
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.release();
            this.audioPlayer = null;
        }
    }

    public void seekTo(long j) {
        if (this.player != null) {
            this.player.seekTo(j);
        }
    }

    public void setDelegate(VideoPlayerDelegate videoPlayerDelegate) {
        this.delegate = videoPlayerDelegate;
    }

    public void setMute(boolean z) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.player != null) {
            this.player.setVolume(z ? BitmapDescriptorFactory.HUE_RED : 1.0f);
        }
        if (this.audioPlayer != null) {
            SimpleExoPlayer simpleExoPlayer = this.audioPlayer;
            if (!z) {
                f = 1.0f;
            }
            simpleExoPlayer.setVolume(f);
        }
    }

    public void setPlayWhenReady(boolean z) {
        this.mixedPlayWhenReady = z;
        if (z && this.mixedAudio && (!this.audioPlayerReady || !this.videoPlayerReady)) {
            if (this.player != null) {
                this.player.setPlayWhenReady(false);
            }
            if (this.audioPlayer != null) {
                this.audioPlayer.setPlayWhenReady(false);
                return;
            }
            return;
        }
        this.autoplay = z;
        if (this.player != null) {
            this.player.setPlayWhenReady(z);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setPlayWhenReady(z);
        }
    }

    public void setStreamType(int i) {
        if (this.player != null) {
            this.player.setAudioStreamType(i);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setAudioStreamType(i);
        }
    }

    public void setTextureView(TextureView textureView) {
        if (this.textureView != textureView) {
            this.textureView = textureView;
            if (this.player != null) {
                this.player.setVideoTextureView(this.textureView);
            }
        }
    }

    public void setVolume(float f) {
        if (this.player != null) {
            this.player.setVolume(f);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setVolume(f);
        }
    }
}
