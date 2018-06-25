package org.telegram.messenger;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.media.MediaDescription;
import android.media.MediaMetadata.Builder;
import android.media.browse.MediaBrowser.MediaItem;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.MediaSession.QueueItem;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.service.media.MediaBrowserService;
import android.service.media.MediaBrowserService.BrowserRoot;
import android.service.media.MediaBrowserService.Result;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_fileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.LaunchActivity;

@TargetApi(21)
public class MusicBrowserService extends MediaBrowserService implements NotificationCenterDelegate {
    public static final String ACTION_CMD = "com.example.android.mediabrowserservice.ACTION_CMD";
    public static final String CMD_NAME = "CMD_NAME";
    public static final String CMD_PAUSE = "CMD_PAUSE";
    private static final String MEDIA_ID_ROOT = "__ROOT__";
    private static final String SLOT_RESERVATION_QUEUE = "com.google.android.gms.car.media.ALWAYS_RESERVE_SPACE_FOR.ACTION_QUEUE";
    private static final String SLOT_RESERVATION_SKIP_TO_NEXT = "com.google.android.gms.car.media.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT";
    private static final String SLOT_RESERVATION_SKIP_TO_PREV = "com.google.android.gms.car.media.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS";
    private static final int STOP_DELAY = 30000;
    private RectF bitmapRect;
    private HashMap<Integer, TLRPC$Chat> chats = new HashMap();
    private boolean chatsLoaded;
    private DelayedStopHandler delayedStopHandler = new DelayedStopHandler();
    private ArrayList<Integer> dialogs = new ArrayList();
    private int lastSelectedDialog;
    private boolean loadingChats;
    private MediaSession mediaSession;
    private HashMap<Integer, ArrayList<MessageObject>> musicObjects = new HashMap();
    private HashMap<Integer, ArrayList<QueueItem>> musicQueues = new HashMap();
    private Paint roundPaint;
    private boolean serviceStarted;
    private HashMap<Integer, User> users = new HashMap();

    private static class DelayedStopHandler extends Handler {
        private final WeakReference<MusicBrowserService> mWeakReference;

        private DelayedStopHandler(MusicBrowserService service) {
            this.mWeakReference = new WeakReference(service);
        }

        public void handleMessage(Message msg) {
            MusicBrowserService service = (MusicBrowserService) this.mWeakReference.get();
            if (service == null) {
                return;
            }
            if (MediaController.getInstance().getPlayingMessageObject() == null || MediaController.getInstance().isMessagePaused()) {
                service.stopSelf();
                service.serviceStarted = false;
            }
        }
    }

    private final class MediaSessionCallback extends Callback {
        private MediaSessionCallback() {
        }

        public void onPlay() {
            MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
            if (messageObject == null) {
                onPlayFromMediaId(MusicBrowserService.this.lastSelectedDialog + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + 0, null);
            } else {
                MediaController.getInstance().playMessage(messageObject);
            }
        }

        public void onSkipToQueueItem(long queueId) {
            MediaController.getInstance().playMessageAtIndex((int) queueId);
            MusicBrowserService.this.handlePlayRequest();
        }

        public void onSeekTo(long position) {
            MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
            if (messageObject != null) {
                MediaController.getInstance().seekToProgress(messageObject, ((float) (position / 1000)) / ((float) messageObject.getDuration()));
            }
        }

        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            String[] args = mediaId.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (args.length == 2) {
                try {
                    int did = Integer.parseInt(args[0]);
                    int id = Integer.parseInt(args[1]);
                    ArrayList<MessageObject> arrayList = (ArrayList) MusicBrowserService.this.musicObjects.get(Integer.valueOf(did));
                    ArrayList<QueueItem> arrayList1 = (ArrayList) MusicBrowserService.this.musicQueues.get(Integer.valueOf(did));
                    if (arrayList != null && id >= 0 && id < arrayList.size()) {
                        MusicBrowserService.this.lastSelectedDialog = did;
                        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("auto_lastSelectedDialog", did).commit();
                        MediaController.getInstance().setPlaylist(arrayList, (MessageObject) arrayList.get(id), false);
                        MusicBrowserService.this.mediaSession.setQueue(arrayList1);
                        if (did > 0) {
                            User user = (User) MusicBrowserService.this.users.get(Integer.valueOf(did));
                            if (user != null) {
                                MusicBrowserService.this.mediaSession.setQueueTitle(ContactsController.formatName(user.first_name, user.last_name));
                            } else {
                                MusicBrowserService.this.mediaSession.setQueueTitle("DELETED USER");
                            }
                            MusicBrowserService.this.handlePlayRequest();
                        }
                        TLRPC$Chat chat = (TLRPC$Chat) MusicBrowserService.this.chats.get(Integer.valueOf(-did));
                        if (chat != null) {
                            MusicBrowserService.this.mediaSession.setQueueTitle(chat.title);
                        } else {
                            MusicBrowserService.this.mediaSession.setQueueTitle("DELETED CHAT");
                        }
                        MusicBrowserService.this.handlePlayRequest();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }

        public void onPause() {
            MusicBrowserService.this.handlePauseRequest();
        }

        public void onStop() {
            MusicBrowserService.this.handleStopRequest(null);
        }

        public void onSkipToNext() {
            MediaController.getInstance().playNextMessage();
        }

        public void onSkipToPrevious() {
            MediaController.getInstance().playPreviousMessage();
        }

        public void onPlayFromSearch(String query, Bundle extras) {
            if (query != null && query.length() != 0) {
                query = query.toLowerCase();
                for (int a = 0; a < MusicBrowserService.this.dialogs.size(); a++) {
                    int did = ((Integer) MusicBrowserService.this.dialogs.get(a)).intValue();
                    if (did > 0) {
                        User user = (User) MusicBrowserService.this.users.get(Integer.valueOf(did));
                        if (user != null && ((user.first_name != null && user.first_name.startsWith(query)) || (user.last_name != null && user.last_name.startsWith(query)))) {
                            onPlayFromMediaId(did + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + 0, null);
                            return;
                        }
                    }
                    TLRPC$Chat chat = (TLRPC$Chat) MusicBrowserService.this.chats.get(Integer.valueOf(-did));
                    if (!(chat == null || chat.title == null || !chat.title.toLowerCase().contains(query))) {
                        onPlayFromMediaId(did + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + 0, null);
                        return;
                    }
                }
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        ApplicationLoader.postInitApplication();
        this.lastSelectedDialog = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getInt("auto_lastSelectedDialog", 0);
        this.mediaSession = new MediaSession(this, "MusicService");
        setSessionToken(this.mediaSession.getSessionToken());
        this.mediaSession.setCallback(new MediaSessionCallback());
        this.mediaSession.setFlags(3);
        Context context = getApplicationContext();
        this.mediaSession.setSessionActivity(PendingIntent.getActivity(context, 99, new Intent(context, LaunchActivity.class), 134217728));
        Bundle extras = new Bundle();
        extras.putBoolean(SLOT_RESERVATION_QUEUE, true);
        extras.putBoolean(SLOT_RESERVATION_SKIP_TO_PREV, true);
        extras.putBoolean(SLOT_RESERVATION_SKIP_TO_NEXT, true);
        this.mediaSession.setExtras(extras);
        updatePlaybackState(null);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
    }

    public int onStartCommand(Intent startIntent, int flags, int startId) {
        return 1;
    }

    public void onDestroy() {
        handleStopRequest(null);
        this.delayedStopHandler.removeCallbacksAndMessages(null);
        this.mediaSession.release();
    }

    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
        if (clientPackageName == null || (1000 != clientUid && Process.myUid() != clientUid && !clientPackageName.equals("com.google.android.mediasimulator") && !clientPackageName.equals("com.google.android.projection.gearhead"))) {
            return null;
        }
        return new BrowserRoot(MEDIA_ID_ROOT, null);
    }

    public void onLoadChildren(final String parentMediaId, final Result<List<MediaItem>> result) {
        if (this.chatsLoaded) {
            loadChildrenImpl(parentMediaId, result);
            return;
        }
        result.detach();
        if (!this.loadingChats) {
            this.loadingChats = true;
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MusicBrowserService$1$1 */
                class C15621 implements Runnable {
                    C15621() {
                    }

                    public void run() {
                        MusicBrowserService.this.chatsLoaded = true;
                        MusicBrowserService.this.loadingChats = false;
                        MusicBrowserService.this.loadChildrenImpl(parentMediaId, result);
                        if (MusicBrowserService.this.lastSelectedDialog == 0 && !MusicBrowserService.this.dialogs.isEmpty()) {
                            MusicBrowserService.this.lastSelectedDialog = ((Integer) MusicBrowserService.this.dialogs.get(0)).intValue();
                        }
                        if (MusicBrowserService.this.lastSelectedDialog != 0) {
                            ArrayList<MessageObject> arrayList = (ArrayList) MusicBrowserService.this.musicObjects.get(Integer.valueOf(MusicBrowserService.this.lastSelectedDialog));
                            ArrayList<QueueItem> arrayList1 = (ArrayList) MusicBrowserService.this.musicQueues.get(Integer.valueOf(MusicBrowserService.this.lastSelectedDialog));
                            if (!(arrayList == null || arrayList.isEmpty())) {
                                MusicBrowserService.this.mediaSession.setQueue(arrayList1);
                                if (MusicBrowserService.this.lastSelectedDialog > 0) {
                                    User user = (User) MusicBrowserService.this.users.get(Integer.valueOf(MusicBrowserService.this.lastSelectedDialog));
                                    if (user != null) {
                                        MusicBrowserService.this.mediaSession.setQueueTitle(ContactsController.formatName(user.first_name, user.last_name));
                                    } else {
                                        MusicBrowserService.this.mediaSession.setQueueTitle("DELETED USER");
                                    }
                                } else {
                                    TLRPC$Chat chat = (TLRPC$Chat) MusicBrowserService.this.chats.get(Integer.valueOf(-MusicBrowserService.this.lastSelectedDialog));
                                    if (chat != null) {
                                        MusicBrowserService.this.mediaSession.setQueueTitle(chat.title);
                                    } else {
                                        MusicBrowserService.this.mediaSession.setQueueTitle("DELETED CHAT");
                                    }
                                }
                                MessageObject messageObject = (MessageObject) arrayList.get(0);
                                Builder builder = new Builder();
                                builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (long) (messageObject.getDuration() * 1000));
                                builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, messageObject.getMusicAuthor());
                                builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, messageObject.getMusicTitle());
                                MusicBrowserService.this.mediaSession.setMetadata(builder.build());
                            }
                        }
                        MusicBrowserService.this.updatePlaybackState(null);
                    }
                }

                public void run() {
                    try {
                        ArrayList<Integer> usersToLoad = new ArrayList();
                        ArrayList<Integer> chatsToLoad = new ArrayList();
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT DISTINCT uid FROM media_v2 WHERE uid != 0 AND mid > 0 AND type = %d", new Object[]{Integer.valueOf(4)}), new Object[0]);
                        while (cursor.next()) {
                            int lower_part = (int) cursor.longValue(0);
                            if (lower_part != 0) {
                                MusicBrowserService.this.dialogs.add(Integer.valueOf(lower_part));
                                if (lower_part > 0) {
                                    usersToLoad.add(Integer.valueOf(lower_part));
                                } else {
                                    chatsToLoad.add(Integer.valueOf(-lower_part));
                                }
                            }
                        }
                        cursor.dispose();
                        if (!MusicBrowserService.this.dialogs.isEmpty()) {
                            int a;
                            String ids = TextUtils.join(",", MusicBrowserService.this.dialogs);
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT uid, data, mid FROM media_v2 WHERE uid IN (%s) AND mid > 0 AND type = %d ORDER BY date DESC, mid DESC", new Object[]{ids, Integer.valueOf(4)}), new Object[0]);
                            while (cursor.next()) {
                                NativeByteBuffer data = cursor.byteBufferValue(1);
                                if (data != null) {
                                    TLRPC$Message message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                    if (MessageObject.isMusicMessage(message)) {
                                        int did = cursor.intValue(0);
                                        message.id = cursor.intValue(2);
                                        message.dialog_id = (long) did;
                                        ArrayList<MessageObject> arrayList = (ArrayList) MusicBrowserService.this.musicObjects.get(Integer.valueOf(did));
                                        ArrayList<QueueItem> arrayList1 = (ArrayList) MusicBrowserService.this.musicQueues.get(Integer.valueOf(did));
                                        if (arrayList == null) {
                                            arrayList = new ArrayList();
                                            MusicBrowserService.this.musicObjects.put(Integer.valueOf(did), arrayList);
                                            arrayList1 = new ArrayList();
                                            MusicBrowserService.this.musicQueues.put(Integer.valueOf(did), arrayList1);
                                        }
                                        MessageObject messageObject = new MessageObject(message, null, false);
                                        arrayList.add(0, messageObject);
                                        MediaDescription.Builder builder = new MediaDescription.Builder().setMediaId(did + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + arrayList.size());
                                        builder.setTitle(messageObject.getMusicTitle());
                                        builder.setSubtitle(messageObject.getMusicAuthor());
                                        arrayList1.add(0, new QueueItem(builder.build(), (long) arrayList1.size()));
                                    }
                                }
                            }
                            cursor.dispose();
                            if (!usersToLoad.isEmpty()) {
                                ArrayList<User> usersArrayList = new ArrayList();
                                MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), usersArrayList);
                                for (a = 0; a < usersArrayList.size(); a++) {
                                    User user = (User) usersArrayList.get(a);
                                    MusicBrowserService.this.users.put(Integer.valueOf(user.id), user);
                                }
                            }
                            if (!chatsToLoad.isEmpty()) {
                                ArrayList<TLRPC$Chat> chatsArrayList = new ArrayList();
                                MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chatsArrayList);
                                for (a = 0; a < chatsArrayList.size(); a++) {
                                    TLRPC$Chat chat = (TLRPC$Chat) chatsArrayList.get(a);
                                    MusicBrowserService.this.chats.put(Integer.valueOf(chat.id), chat);
                                }
                            }
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    AndroidUtilities.runOnUIThread(new C15621());
                }
            });
        }
    }

    private void loadChildrenImpl(String parentMediaId, Result<List<MediaItem>> result) {
        List<MediaItem> mediaItems = new ArrayList();
        int a;
        int did;
        MediaDescription.Builder builder;
        if (MEDIA_ID_ROOT.equals(parentMediaId)) {
            for (a = 0; a < this.dialogs.size(); a++) {
                did = ((Integer) this.dialogs.get(a)).intValue();
                builder = new MediaDescription.Builder().setMediaId("__CHAT_" + did);
                TLRPC$FileLocation avatar = null;
                if (did > 0) {
                    User user = (User) this.users.get(Integer.valueOf(did));
                    if (user != null) {
                        builder.setTitle(ContactsController.formatName(user.first_name, user.last_name));
                        if (user.photo != null && (user.photo.photo_small instanceof TLRPC$TL_fileLocation)) {
                            avatar = user.photo.photo_small;
                        }
                    } else {
                        builder.setTitle("DELETED USER");
                    }
                } else {
                    TLRPC$Chat chat = (TLRPC$Chat) this.chats.get(Integer.valueOf(-did));
                    if (chat != null) {
                        builder.setTitle(chat.title);
                        if (chat.photo != null && (chat.photo.photo_small instanceof TLRPC$TL_fileLocation)) {
                            avatar = chat.photo.photo_small;
                        }
                    } else {
                        builder.setTitle("DELETED CHAT");
                    }
                }
                Bitmap bitmap = null;
                if (avatar != null) {
                    bitmap = createRoundBitmap(FileLoader.getPathToAttach(avatar, true));
                    if (bitmap != null) {
                        builder.setIconBitmap(bitmap);
                    }
                }
                if (avatar == null || bitmap == null) {
                    builder.setIconUri(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/drawable/contact_blue"));
                }
                mediaItems.add(new MediaItem(builder.build(), 1));
            }
        } else if (parentMediaId != null) {
            if (parentMediaId.startsWith("__CHAT_")) {
                did = 0;
                try {
                    did = Integer.parseInt(parentMediaId.replace("__CHAT_", ""));
                } catch (Exception e) {
                    FileLog.e(e);
                }
                ArrayList<MessageObject> arrayList = (ArrayList) this.musicObjects.get(Integer.valueOf(did));
                if (arrayList != null) {
                    for (a = 0; a < arrayList.size(); a++) {
                        MessageObject messageObject = (MessageObject) arrayList.get(a);
                        builder = new MediaDescription.Builder().setMediaId(did + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + a);
                        builder.setTitle(messageObject.getMusicTitle());
                        builder.setSubtitle(messageObject.getMusicAuthor());
                        mediaItems.add(new MediaItem(builder.build(), 2));
                    }
                }
            }
        }
        result.sendResult(mediaItems);
    }

    private Bitmap createRoundBitmap(File path) {
        try {
            Options options = new Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(path.toString(), options);
            if (bitmap != null) {
                Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
                result.eraseColor(0);
                Canvas canvas = new Canvas(result);
                BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                if (this.roundPaint == null) {
                    this.roundPaint = new Paint(1);
                    this.bitmapRect = new RectF();
                }
                this.roundPaint.setShader(shader);
                this.bitmapRect.set(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
                canvas.drawRoundRect(this.bitmapRect, (float) bitmap.getWidth(), (float) bitmap.getHeight(), this.roundPaint);
                return result;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        return null;
    }

    private void updatePlaybackState(String error) {
        int state;
        long position = -1;
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject != null) {
            position = (long) (playingMessageObject.audioProgressSec * 1000);
        }
        PlaybackState.Builder stateBuilder = new PlaybackState.Builder().setActions(getAvailableActions());
        if (playingMessageObject == null) {
            state = 1;
        } else if (MediaController.getInstance().isDownloadingCurrentMessage()) {
            state = 6;
        } else {
            state = MediaController.getInstance().isMessagePaused() ? 2 : 3;
        }
        if (error != null) {
            stateBuilder.setErrorMessage(error);
            state = 7;
        }
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime());
        if (playingMessageObject != null) {
            stateBuilder.setActiveQueueItemId((long) MediaController.getInstance().getPlayingMessageObjectNum());
        } else {
            stateBuilder.setActiveQueueItemId(0);
        }
        this.mediaSession.setPlaybackState(stateBuilder.build());
    }

    private long getAvailableActions() {
        long actions = 3076;
        if (MediaController.getInstance().getPlayingMessageObject() == null) {
            return 3076;
        }
        if (!MediaController.getInstance().isMessagePaused()) {
            actions = 3076 | 2;
        }
        return (actions | 16) | 32;
    }

    private void handleStopRequest(String withError) {
        this.delayedStopHandler.removeCallbacksAndMessages(null);
        this.delayedStopHandler.sendEmptyMessageDelayed(0, 30000);
        updatePlaybackState(withError);
        stopSelf();
        this.serviceStarted = false;
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
    }

    private void handlePlayRequest() {
        this.delayedStopHandler.removeCallbacksAndMessages(null);
        if (!this.serviceStarted) {
            startService(new Intent(getApplicationContext(), MusicBrowserService.class));
            this.serviceStarted = true;
        }
        if (!this.mediaSession.isActive()) {
            this.mediaSession.setActive(true);
        }
        MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
        if (messageObject != null) {
            Builder builder = new Builder();
            builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (long) (messageObject.getDuration() * 1000));
            builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, messageObject.getMusicAuthor());
            builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, messageObject.getMusicTitle());
            AudioInfo audioInfo = MediaController.getInstance().getAudioInfo();
            if (audioInfo != null) {
                Bitmap bitmap = audioInfo.getCover();
                if (bitmap != null) {
                    builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap);
                }
            }
            this.mediaSession.setMetadata(builder.build());
        }
    }

    private void handlePauseRequest() {
        MediaController.getInstance().pauseMessage(MediaController.getInstance().getPlayingMessageObject());
        this.delayedStopHandler.removeCallbacksAndMessages(null);
        this.delayedStopHandler.sendEmptyMessageDelayed(0, 30000);
    }

    public void didReceivedNotification(int id, Object... args) {
        updatePlaybackState(null);
        handlePlayRequest();
    }
}
