package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.view.TextureView;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.ExoPlaybackException;
import org.telegram.messenger.exoplayer2.ExoPlayer.EventListener;
import org.telegram.messenger.exoplayer2.ExoPlayerFactory;
import org.telegram.messenger.exoplayer2.PlaybackParameters;
import org.telegram.messenger.exoplayer2.Player;
import org.telegram.messenger.exoplayer2.SimpleExoPlayer;
import org.telegram.messenger.exoplayer2.SimpleExoPlayer$VideoListener;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.extractor.DefaultExtractorsFactory;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import org.telegram.messenger.exoplayer2.source.LoopingMediaSource;
import org.telegram.messenger.exoplayer2.source.MediaSource;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.source.dash.DashMediaSource;
import org.telegram.messenger.exoplayer2.source.dash.DefaultDashChunkSource;
import org.telegram.messenger.exoplayer2.source.hls.HlsMediaSource;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.SsMediaSource;
import org.telegram.messenger.exoplayer2.trackselection.AdaptiveTrackSelection;
import org.telegram.messenger.exoplayer2.trackselection.DefaultTrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.MappingTrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectionArray;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.upstream.DefaultBandwidthMeter;
import org.telegram.messenger.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import org.telegram.messenger.secretmedia.ExtendedDefaultDataSourceFactory;

@SuppressLint({"NewApi"})
public class VideoPlayer implements EventListener, SimpleExoPlayer$VideoListener {
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
    class C28011 implements Player.EventListener {
        C28011() {
        }

        public void onTimelineChanged(Timeline timeline, Object manifest) {
        }

        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

        public void onLoadingChanged(boolean isLoading) {
        }

        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (!VideoPlayer.this.audioPlayerReady && playbackState == 3) {
                VideoPlayer.this.audioPlayerReady = true;
                VideoPlayer.this.checkPlayersReady();
            }
        }

        public void onRepeatModeChanged(int repeatMode) {
        }

        public void onPlayerError(ExoPlaybackException error) {
        }

        public void onPositionDiscontinuity() {
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }
    }

    public interface RendererBuilder {
        void buildRenderers(VideoPlayer videoPlayer);

        void cancel();
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
            this.audioPlayer.addListener(new C28011());
            this.audioPlayer.setPlayWhenReady(this.autoplay);
        }
    }

    public void preparePlayerLoop(Uri videoUri, String videoType, Uri audioUri, String audioType) {
        this.mixedAudio = true;
        this.audioPlayerReady = false;
        this.videoPlayerReady = false;
        ensurePleyaerCreated();
        MediaSource mediaSource1 = null;
        MediaSource mediaSource2 = null;
        for (int a = 0; a < 2; a++) {
            String type;
            Uri uri;
            MediaSource mediaSource;
            if (a == 0) {
                type = videoType;
                uri = videoUri;
            } else {
                type = audioType;
                uri = audioUri;
            }
            Object obj = -1;
            switch (type.hashCode()) {
                case 3680:
                    if (type.equals("ss")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 103407:
                    if (type.equals("hls")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 3075986:
                    if (type.equals("dash")) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    mediaSource = new DashMediaSource(uri, this.mediaDataSourceFactory, new DefaultDashChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, null);
                    break;
                case 1:
                    mediaSource = new HlsMediaSource(uri, this.mediaDataSourceFactory, this.mainHandler, null);
                    break;
                case 2:
                    mediaSource = new SsMediaSource(uri, this.mediaDataSourceFactory, new DefaultSsChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, null);
                    break;
                default:
                    mediaSource = new ExtractorMediaSource(uri, this.mediaDataSourceFactory, new DefaultExtractorsFactory(), this.mainHandler, null);
                    break;
            }
            MediaSource mediaSource3 = new LoopingMediaSource(mediaSource);
            if (a == 0) {
                mediaSource1 = mediaSource3;
            } else {
                mediaSource2 = mediaSource3;
            }
        }
        this.player.prepare(mediaSource1, true, true);
        this.audioPlayer.prepare(mediaSource2, true, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void preparePlayer(android.net.Uri r8, java.lang.String r9) {
        /*
        r7 = this;
        r6 = 1;
        r1 = 0;
        r5 = 0;
        r7.videoPlayerReady = r1;
        r7.mixedAudio = r1;
        r7.ensurePleyaerCreated();
        r2 = -1;
        r3 = r9.hashCode();
        switch(r3) {
            case 3680: goto L_0x0040;
            case 103407: goto L_0x0035;
            case 3075986: goto L_0x002b;
            default: goto L_0x0012;
        };
    L_0x0012:
        r1 = r2;
    L_0x0013:
        switch(r1) {
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
        r3 = "dash";
        r3 = r9.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x0034:
        goto L_0x0013;
    L_0x0035:
        r1 = "hls";
        r1 = r9.equals(r1);
        if (r1 == 0) goto L_0x0012;
    L_0x003e:
        r1 = r6;
        goto L_0x0013;
    L_0x0040:
        r1 = "ss";
        r1 = r9.equals(r1);
        if (r1 == 0) goto L_0x0012;
    L_0x0049:
        r1 = 2;
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

    public boolean isPlayerPrepared() {
        return this.player != null;
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

    public void setTextureView(TextureView texture) {
        if (this.textureView != texture) {
            this.textureView = texture;
            if (this.player != null) {
                this.player.setVideoTextureView(this.textureView);
            }
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

    public void pause() {
        this.mixedPlayWhenReady = false;
        if (this.player != null) {
            this.player.setPlayWhenReady(false);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setPlayWhenReady(false);
        }
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.mixedPlayWhenReady = playWhenReady;
        if (playWhenReady && this.mixedAudio && (!this.audioPlayerReady || !this.videoPlayerReady)) {
            if (this.player != null) {
                this.player.setPlayWhenReady(false);
            }
            if (this.audioPlayer != null) {
                this.audioPlayer.setPlayWhenReady(false);
                return;
            }
            return;
        }
        this.autoplay = playWhenReady;
        if (this.player != null) {
            this.player.setPlayWhenReady(playWhenReady);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    public long getDuration() {
        return this.player != null ? this.player.getDuration() : 0;
    }

    public long getCurrentPosition() {
        return this.player != null ? this.player.getCurrentPosition() : 0;
    }

    public boolean isMuted() {
        return this.player.getVolume() == 0.0f;
    }

    public void setMute(boolean value) {
        float f = 0.0f;
        if (this.player != null) {
            this.player.setVolume(value ? 0.0f : 1.0f);
        }
        if (this.audioPlayer != null) {
            SimpleExoPlayer simpleExoPlayer = this.audioPlayer;
            if (!value) {
                f = 1.0f;
            }
            simpleExoPlayer.setVolume(f);
        }
    }

    public void onRepeatModeChanged(int repeatMode) {
    }

    public void setVolume(float volume) {
        if (this.player != null) {
            this.player.setVolume(volume);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setVolume(volume);
        }
    }

    public void seekTo(long positionMs) {
        if (this.player != null) {
            this.player.seekTo(positionMs);
        }
    }

    public void setDelegate(VideoPlayerDelegate videoPlayerDelegate) {
        this.delegate = videoPlayerDelegate;
    }

    public int getBufferedPercentage() {
        return this.player != null ? this.player.getBufferedPercentage() : 0;
    }

    public long getBufferedPosition() {
        return this.player != null ? this.player.getBufferedPosition() : 0;
    }

    public boolean isPlaying() {
        return (this.mixedAudio && this.mixedPlayWhenReady) || (this.player != null && this.player.getPlayWhenReady());
    }

    public boolean isBuffering() {
        return this.player != null && this.lastReportedPlaybackState == 2;
    }

    public void setStreamType(int type) {
        if (this.player != null) {
            this.player.setAudioStreamType(type);
        }
        if (this.audioPlayer != null) {
            this.audioPlayer.setAudioStreamType(type);
        }
    }

    private void checkPlayersReady() {
        if (this.audioPlayerReady && this.videoPlayerReady && this.mixedPlayWhenReady) {
            play();
        }
    }

    public void onLoadingChanged(boolean isLoading) {
    }

    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        maybeReportPlayerState();
        if (!this.videoPlayerReady && playbackState == 3) {
            this.videoPlayerReady = true;
            checkPlayersReady();
        }
    }

    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    public void onPlayerError(ExoPlaybackException error) {
        this.delegate.onError(error);
    }

    public void onPositionDiscontinuity() {
    }

    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        this.delegate.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
    }

    public void onRenderedFirstFrame() {
        this.delegate.onRenderedFirstFrame();
    }

    public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        return this.delegate.onSurfaceDestroyed(surfaceTexture);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.delegate.onSurfaceTextureUpdated(surfaceTexture);
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
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
}
