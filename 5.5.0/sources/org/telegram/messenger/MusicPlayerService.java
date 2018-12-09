package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.MediaStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.PlaybackState.Builder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.app.al.C0266d;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.LaunchActivity;

public class MusicPlayerService extends Service implements NotificationCenterDelegate {
    private static final int ID_NOTIFICATION = 5;
    public static final String NOTIFY_CLOSE = "org.telegram.android.musicplayer.close";
    public static final String NOTIFY_NEXT = "org.telegram.android.musicplayer.next";
    public static final String NOTIFY_PAUSE = "org.telegram.android.musicplayer.pause";
    public static final String NOTIFY_PLAY = "org.telegram.android.musicplayer.play";
    public static final String NOTIFY_PREVIOUS = "org.telegram.android.musicplayer.previous";
    private static boolean supportBigNotifications = (VERSION.SDK_INT >= 16);
    private static boolean supportLockScreenControls;
    private Bitmap albumArtPlaceholder;
    private AudioManager audioManager;
    private MediaSession mediaSession;
    private Builder playbackState;
    private RemoteControlClient remoteControlClient;

    /* renamed from: org.telegram.messenger.MusicPlayerService$1 */
    class C33001 extends Callback {
        C33001() {
        }

        public void onPause() {
            MediaController.getInstance().pauseMessage(MediaController.getInstance().getPlayingMessageObject());
        }

        public void onPlay() {
            MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
        }

        public void onSkipToNext() {
            MediaController.getInstance().playNextMessage();
        }

        public void onSkipToPrevious() {
            MediaController.getInstance().playPreviousMessage();
        }

        public void onStop() {
        }
    }

    /* renamed from: org.telegram.messenger.MusicPlayerService$2 */
    class C33012 implements Runnable {
        C33012() {
        }

        public void run() {
            MusicPlayerService.this.stopSelf();
        }
    }

    static {
        boolean z = true;
        if (VERSION.SDK_INT >= 21) {
            z = false;
        }
        supportLockScreenControls = z;
    }

    @SuppressLint({"NewApi"})
    private void createNotification(MessageObject messageObject) {
        CharSequence musicTitle = messageObject.getMusicTitle();
        CharSequence musicAuthor = messageObject.getMusicAuthor();
        AudioInfo audioInfo = MediaController.getInstance().getAudioInfo();
        Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
        intent.setAction("com.tmessages.openplayer");
        intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
        PendingIntent activity = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 0);
        Bitmap cover;
        Notification build;
        if (VERSION.SDK_INT >= 21) {
            Bitmap smallCover = audioInfo != null ? audioInfo.getSmallCover() : null;
            cover = audioInfo != null ? audioInfo.getCover() : null;
            boolean z = !MediaController.getInstance().isMessagePaused();
            PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_PREVIOUS).setComponent(new ComponentName(this, MusicPlayerReceiver.class)), ErrorDialogData.BINDER_CRASH);
            PendingIntent service = PendingIntent.getService(getApplicationContext(), 0, new Intent(this, getClass()).setAction(getPackageName() + ".STOP_PLAYER"), ErrorDialogData.BINDER_CRASH);
            PendingIntent broadcast2 = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(z ? NOTIFY_PAUSE : NOTIFY_PLAY).setComponent(new ComponentName(this, MusicPlayerReceiver.class)), ErrorDialogData.BINDER_CRASH);
            PendingIntent broadcast3 = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_NEXT).setComponent(new ComponentName(this, MusicPlayerReceiver.class)), ErrorDialogData.BINDER_CRASH);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.player).setOngoing(z).setContentTitle(musicTitle).setContentText(musicAuthor).setSubText(audioInfo != null ? audioInfo.getAlbum() : null).setContentIntent(activity).setDeleteIntent(service).setShowWhen(false).setCategory("transport").setPriority(2).setStyle(new MediaStyle().setMediaSession(this.mediaSession.getSessionToken()).setShowActionsInCompactView(new int[]{0, 1, 2}));
            if (smallCover != null) {
                builder.setLargeIcon(smallCover);
            } else {
                builder.setLargeIcon(this.albumArtPlaceholder);
            }
            if (MediaController.getInstance().isDownloadingCurrentMessage()) {
                this.playbackState.setState(6, 0, 1.0f).setActions(0);
                builder.addAction(new Action.Builder(R.drawable.ic_action_previous, TtmlNode.ANONYMOUS_REGION_ID, broadcast).build()).addAction(new Action.Builder(R.drawable.loading_animation2, TtmlNode.ANONYMOUS_REGION_ID, null).build()).addAction(new Action.Builder(R.drawable.ic_action_next, TtmlNode.ANONYMOUS_REGION_ID, broadcast3).build());
            } else {
                this.playbackState.setState(z ? 3 : 2, ((long) MediaController.getInstance().getPlayingMessageObject().audioProgressSec) * 1000, z ? 1.0f : BitmapDescriptorFactory.HUE_RED).setActions(566);
                builder.addAction(new Action.Builder(R.drawable.ic_action_previous, TtmlNode.ANONYMOUS_REGION_ID, broadcast).build()).addAction(new Action.Builder(z ? R.drawable.ic_action_pause : R.drawable.ic_action_play, TtmlNode.ANONYMOUS_REGION_ID, broadcast2).build()).addAction(new Action.Builder(R.drawable.ic_action_next, TtmlNode.ANONYMOUS_REGION_ID, broadcast3).build());
            }
            this.mediaSession.setPlaybackState(this.playbackState.build());
            this.mediaSession.setMetadata(new MediaMetadata.Builder().putBitmap("android.media.metadata.ALBUM_ART", cover).putString("android.media.metadata.ALBUM_ARTIST", musicAuthor).putString("android.media.metadata.TITLE", musicTitle).putString("android.media.metadata.ALBUM", audioInfo != null ? audioInfo.getAlbum() : null).build());
            build = builder.build();
            if (z) {
                startForeground(5, build);
            } else {
                stopForeground(false);
                ((NotificationManager) getSystemService("notification")).notify(5, build);
            }
        } else {
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.player_small_notification);
            RemoteViews remoteViews2 = null;
            if (supportBigNotifications) {
                remoteViews2 = new RemoteViews(getApplicationContext().getPackageName(), R.layout.player_big_notification);
            }
            build = new C0266d(getApplicationContext()).m1227a((int) R.drawable.player).m1232a(activity).m1238a(musicTitle).m1242b();
            build.contentView = remoteViews;
            if (supportBigNotifications) {
                build.bigContentView = remoteViews2;
            }
            setListeners(remoteViews);
            if (supportBigNotifications) {
                setListeners(remoteViews2);
            }
            cover = audioInfo != null ? audioInfo.getSmallCover() : null;
            if (cover != null) {
                build.contentView.setImageViewBitmap(R.id.player_album_art, cover);
                if (supportBigNotifications) {
                    build.bigContentView.setImageViewBitmap(R.id.player_album_art, cover);
                }
            } else {
                build.contentView.setImageViewResource(R.id.player_album_art, R.drawable.nocover_small);
                if (supportBigNotifications) {
                    build.bigContentView.setImageViewResource(R.id.player_album_art, R.drawable.nocover_big);
                }
            }
            if (MediaController.getInstance().isDownloadingCurrentMessage()) {
                build.contentView.setViewVisibility(R.id.player_pause, 8);
                build.contentView.setViewVisibility(R.id.player_play, 8);
                build.contentView.setViewVisibility(R.id.player_next, 8);
                build.contentView.setViewVisibility(R.id.player_previous, 8);
                build.contentView.setViewVisibility(R.id.player_progress_bar, 0);
                if (supportBigNotifications) {
                    build.bigContentView.setViewVisibility(R.id.player_pause, 8);
                    build.bigContentView.setViewVisibility(R.id.player_play, 8);
                    build.bigContentView.setViewVisibility(R.id.player_next, 8);
                    build.bigContentView.setViewVisibility(R.id.player_previous, 8);
                    build.bigContentView.setViewVisibility(R.id.player_progress_bar, 0);
                }
            } else {
                build.contentView.setViewVisibility(R.id.player_progress_bar, 8);
                build.contentView.setViewVisibility(R.id.player_next, 0);
                build.contentView.setViewVisibility(R.id.player_previous, 0);
                if (supportBigNotifications) {
                    build.bigContentView.setViewVisibility(R.id.player_next, 0);
                    build.bigContentView.setViewVisibility(R.id.player_previous, 0);
                    build.bigContentView.setViewVisibility(R.id.player_progress_bar, 8);
                }
                if (MediaController.getInstance().isMessagePaused()) {
                    build.contentView.setViewVisibility(R.id.player_pause, 8);
                    build.contentView.setViewVisibility(R.id.player_play, 0);
                    if (supportBigNotifications) {
                        build.bigContentView.setViewVisibility(R.id.player_pause, 8);
                        build.bigContentView.setViewVisibility(R.id.player_play, 0);
                    }
                } else {
                    build.contentView.setViewVisibility(R.id.player_pause, 0);
                    build.contentView.setViewVisibility(R.id.player_play, 8);
                    if (supportBigNotifications) {
                        build.bigContentView.setViewVisibility(R.id.player_pause, 0);
                        build.bigContentView.setViewVisibility(R.id.player_play, 8);
                    }
                }
            }
            build.contentView.setTextViewText(R.id.player_song_name, musicTitle);
            build.contentView.setTextViewText(R.id.player_author_name, musicAuthor);
            if (supportBigNotifications) {
                build.bigContentView.setTextViewText(R.id.player_song_name, musicTitle);
                build.bigContentView.setTextViewText(R.id.player_author_name, musicAuthor);
                remoteViews = build.bigContentView;
                CharSequence album = (audioInfo == null || TextUtils.isEmpty(audioInfo.getAlbum())) ? TtmlNode.ANONYMOUS_REGION_ID : audioInfo.getAlbum();
                remoteViews.setTextViewText(R.id.player_album_title, album);
            }
            build.flags |= 2;
            startForeground(5, build);
        }
        if (this.remoteControlClient != null) {
            MetadataEditor editMetadata = this.remoteControlClient.editMetadata(true);
            editMetadata.putString(2, musicAuthor);
            editMetadata.putString(7, musicTitle);
            if (!(audioInfo == null || audioInfo.getCover() == null)) {
                try {
                    editMetadata.putBitmap(100, audioInfo.getCover());
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
            editMetadata.apply();
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.messagePlayingPlayStateChanged) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject != null) {
                createNotification(playingMessageObject);
            } else {
                stopSelf();
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        if (VERSION.SDK_INT >= 21) {
            this.mediaSession = new MediaSession(this, "telegramAudioPlayer");
            this.playbackState = new Builder();
            this.albumArtPlaceholder = Bitmap.createBitmap(AndroidUtilities.dp(102.0f), AndroidUtilities.dp(102.0f), Config.ARGB_8888);
            Drawable drawable = getResources().getDrawable(R.drawable.nocover_big);
            drawable.setBounds(0, 0, this.albumArtPlaceholder.getWidth(), this.albumArtPlaceholder.getHeight());
            drawable.draw(new Canvas(this.albumArtPlaceholder));
            this.mediaSession.setCallback(new C33001());
            this.mediaSession.setActive(true);
        }
        super.onCreate();
    }

    @SuppressLint({"NewApi"})
    public void onDestroy() {
        super.onDestroy();
        if (this.remoteControlClient != null) {
            MetadataEditor editMetadata = this.remoteControlClient.editMetadata(true);
            editMetadata.clear();
            editMetadata.apply();
            this.audioManager.unregisterRemoteControlClient(this.remoteControlClient);
        }
        if (VERSION.SDK_INT >= 21) {
            this.mediaSession.release();
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
    }

    @SuppressLint({"NewApi"})
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            try {
                if ((getPackageName() + ".STOP_PLAYER").equals(intent.getAction())) {
                    MediaController.getInstance().cleanupPlayer(true, true);
                    return 2;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        }
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject == null) {
            AndroidUtilities.runOnUIThread(new C33012());
            return 1;
        }
        if (supportLockScreenControls) {
            ComponentName componentName = new ComponentName(getApplicationContext(), MusicPlayerReceiver.class.getName());
            try {
                if (this.remoteControlClient == null) {
                    this.audioManager.registerMediaButtonEventReceiver(componentName);
                    Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent2.setComponent(componentName);
                    this.remoteControlClient = new RemoteControlClient(PendingIntent.getBroadcast(this, 0, intent2, 0));
                    this.audioManager.registerRemoteControlClient(this.remoteControlClient);
                }
                this.remoteControlClient.setTransportControlFlags(PsExtractor.PRIVATE_STREAM_1);
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
        }
        createNotification(playingMessageObject);
        return 1;
    }

    public void setListeners(RemoteViews remoteViews) {
        remoteViews.setOnClickPendingIntent(R.id.player_previous, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_PREVIOUS), 134217728));
        remoteViews.setOnClickPendingIntent(R.id.player_close, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_CLOSE), 134217728));
        remoteViews.setOnClickPendingIntent(R.id.player_pause, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_PAUSE), 134217728));
        remoteViews.setOnClickPendingIntent(R.id.player_next, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_NEXT), 134217728));
        remoteViews.setOnClickPendingIntent(R.id.player_play, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(NOTIFY_PLAY), 134217728));
    }
}
