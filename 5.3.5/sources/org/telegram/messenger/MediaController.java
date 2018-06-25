package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.TextureView;
import android.widget.FrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.PhotoViewer;

public class MediaController implements OnAudioFocusChangeListener, NotificationCenterDelegate, SensorEventListener {
    private static final int AUDIO_FOCUSED = 2;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    public static final int AUTODOWNLOAD_MASK_AUDIO = 2;
    public static final int AUTODOWNLOAD_MASK_DOCUMENT = 8;
    public static final int AUTODOWNLOAD_MASK_GIF = 32;
    public static final int AUTODOWNLOAD_MASK_MUSIC = 16;
    public static final int AUTODOWNLOAD_MASK_PHOTO = 1;
    public static final int AUTODOWNLOAD_MASK_VIDEO = 4;
    public static final int AUTODOWNLOAD_MASK_VIDEOMESSAGE = 64;
    private static volatile MediaController Instance = null;
    public static final String MIME_TYPE = "video/avc";
    private static final int PROCESSOR_TYPE_INTEL = 2;
    private static final int PROCESSOR_TYPE_MTK = 3;
    private static final int PROCESSOR_TYPE_OTHER = 0;
    private static final int PROCESSOR_TYPE_QCOM = 1;
    private static final int PROCESSOR_TYPE_SEC = 4;
    private static final int PROCESSOR_TYPE_TI = 5;
    private static final float VOLUME_DUCK = 0.2f;
    private static final float VOLUME_NORMAL = 1.0f;
    public static MediaController$AlbumEntry allMediaAlbumEntry;
    public static MediaController$AlbumEntry allPhotosAlbumEntry;
    private static Runnable broadcastPhotosRunnable;
    private static final String[] projectionPhotos = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "datetaken", "orientation"};
    private static final String[] projectionVideo = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "datetaken", "duration"};
    public static int[] readArgs = new int[3];
    private static Runnable refreshGalleryRunnable;
    private Sensor accelerometerSensor;
    private boolean accelerometerVertical;
    private HashMap<String, MediaController$FileDownloadProgressListener> addLaterArray = new HashMap();
    private boolean allowStartRecord;
    private ArrayList<DownloadObject> audioDownloadQueue = new ArrayList();
    private int audioFocus = 0;
    private AudioInfo audioInfo;
    private MediaPlayer audioPlayer = null;
    private AudioRecord audioRecorder;
    private AudioTrack audioTrackPlayer = null;
    private boolean autoplayGifs = true;
    private Activity baseActivity;
    private int buffersWrited;
    private boolean callInProgress;
    private boolean cancelCurrentVideoConversion = false;
    private int countLess;
    private AspectRatioFrameLayout currentAspectRatioFrameLayout;
    private float currentAspectRatioFrameLayoutRatio;
    private boolean currentAspectRatioFrameLayoutReady;
    private int currentAspectRatioFrameLayoutRotation;
    private int currentPlaylistNum;
    private TextureView currentTextureView;
    private FrameLayout currentTextureViewContainer;
    private long currentTotalPcmDuration;
    private boolean customTabs = true;
    private boolean decodingFinished = false;
    private ArrayList<MediaController$FileDownloadProgressListener> deleteLaterArray = new ArrayList();
    private boolean directShare = true;
    private ArrayList<DownloadObject> documentDownloadQueue = new ArrayList();
    private HashMap<String, DownloadObject> downloadQueueKeys = new HashMap();
    private boolean downloadingCurrentMessage;
    private MediaController$ExternalObserver externalObserver;
    private ByteBuffer fileBuffer;
    private DispatchQueue fileDecodingQueue;
    private DispatchQueue fileEncodingQueue;
    private boolean forceLoopCurrentPlaylist;
    private ArrayList<MediaController$AudioBuffer> freePlayerBuffers = new ArrayList();
    private HashMap<String, MessageObject> generatingWaveform = new HashMap();
    private ArrayList<DownloadObject> gifDownloadQueue = new ArrayList();
    public boolean globalAutodownloadEnabled;
    private float[] gravity = new float[3];
    private float[] gravityFast = new float[3];
    private Sensor gravitySensor;
    private boolean groupPhotosEnabled = true;
    private int hasAudioFocus;
    private int ignoreFirstProgress = 0;
    private boolean ignoreOnPause;
    private boolean ignoreProximity;
    private boolean inappCamera = true;
    private boolean inputFieldHasText;
    private MediaController$InternalObserver internalObserver;
    private boolean isDrawingWasReady;
    private boolean isPaused = false;
    private long lastChatEnterTime;
    private long lastChatLeaveTime;
    private ArrayList<Long> lastChatVisibleMessages;
    private int lastCheckMask = 0;
    private long lastMediaCheckTime;
    private int lastMessageId;
    private long lastPlayPcm;
    private long lastProgress = 0;
    private float lastProximityValue = -100.0f;
    private TLRPC$EncryptedChat lastSecretChat;
    private int lastTag = 0;
    private long lastTimestamp = 0;
    private User lastUser;
    private float[] linearAcceleration = new float[3];
    private Sensor linearSensor;
    private boolean listenerInProgress = false;
    private HashMap<String, ArrayList<MessageObject>> loadingFileMessagesObservers = new HashMap();
    private HashMap<String, ArrayList<WeakReference<MediaController$FileDownloadProgressListener>>> loadingFileObservers = new HashMap();
    private String[] mediaProjections = null;
    public int[] mobileDataDownloadMask = new int[4];
    public int[] mobileMaxFileSize = new int[7];
    private ArrayList<DownloadObject> musicDownloadQueue = new ArrayList();
    private HashMap<Integer, String> observersByTag = new HashMap();
    private ArrayList<DownloadObject> photoDownloadQueue = new ArrayList();
    private PipRoundVideoView pipRoundVideoView;
    private int pipSwitchingState;
    private boolean playMusicAgain;
    private boolean playOrderReversed;
    private int playerBufferSize = 0;
    private final Object playerObjectSync = new Object();
    private DispatchQueue playerQueue;
    private final Object playerSync = new Object();
    private MessageObject playingMessageObject;
    private ArrayList<MessageObject> playlist = new ArrayList();
    private float previousAccValue;
    private Timer progressTimer = null;
    private final Object progressTimerSync = new Object();
    private boolean proximityHasDifferentValues;
    private Sensor proximitySensor;
    private boolean proximityTouched;
    private WakeLock proximityWakeLock;
    private ChatActivity raiseChat;
    private boolean raiseToEarRecord;
    private boolean raiseToSpeak = true;
    private int raisedToBack;
    private int raisedToTop;
    private int recordBufferSize;
    private ArrayList<ByteBuffer> recordBuffers = new ArrayList();
    private long recordDialogId;
    private DispatchQueue recordQueue;
    private MessageObject recordReplyingMessageObject;
    private Runnable recordRunnable = new MediaController$1(this);
    private short[] recordSamples = new short[1024];
    private Runnable recordStartRunnable;
    private long recordStartTime;
    private long recordTimeCount;
    private TLRPC$TL_document recordingAudio;
    private File recordingAudioFile;
    private int repeatMode;
    private boolean resumeAudioOnFocusGain;
    public int[] roamingDownloadMask = new int[4];
    public int[] roamingMaxFileSize = new int[7];
    private boolean roundCamera16to9 = true;
    private long samplesCount;
    private boolean saveToGallery = true;
    private int sendAfterDone;
    private SensorManager sensorManager;
    private boolean sensorsStarted;
    private boolean shuffleMusic;
    private ArrayList<MessageObject> shuffledPlaylist = new ArrayList();
    private MediaController$SmsObserver smsObserver;
    private int startObserverToken;
    private MediaController$StopMediaObserverRunnable stopMediaObserverRunnable;
    private final Object sync = new Object();
    private long timeSinceRaise;
    private HashMap<Long, Long> typingTimes = new HashMap();
    private boolean useFrontSpeaker;
    private ArrayList<MediaController$AudioBuffer> usedPlayerBuffers = new ArrayList();
    private boolean videoConvertFirstWrite = true;
    private ArrayList<MessageObject> videoConvertQueue = new ArrayList();
    private final Object videoConvertSync = new Object();
    private ArrayList<DownloadObject> videoDownloadQueue = new ArrayList();
    private ArrayList<DownloadObject> videoMessageDownloadQueue = new ArrayList();
    private VideoPlayer videoPlayer;
    private final Object videoQueueSync = new Object();
    private ArrayList<MessageObject> voiceMessagesPlaylist;
    private HashMap<Integer, MessageObject> voiceMessagesPlaylistMap;
    private boolean voiceMessagesPlaylistUnread;
    public int[] wifiDownloadMask = new int[4];
    public int[] wifiMaxFileSize = new int[7];

    private native void closeOpusFile();

    private native long getTotalPcmDuration();

    private native int isOpusFile(String str);

    private native int openOpusFile(String str);

    private native void readOpusFile(ByteBuffer byteBuffer, int i, int[] iArr);

    private native int seekOpusFile(float f);

    private native int startRecord(String str);

    private native void stopRecord();

    private native int writeFrame(ByteBuffer byteBuffer, int i);

    public native byte[] getWaveform(String str);

    public native byte[] getWaveform2(short[] sArr, int i);

    private void readSms() {
    }

    public static int maskToIndex(int mask) {
        if (mask == 1) {
            return 0;
        }
        if (mask == 2) {
            return 1;
        }
        if (mask == 4) {
            return 2;
        }
        if (mask == 8) {
            return 3;
        }
        if (mask == 16) {
            return 4;
        }
        if (mask == 32) {
            return 5;
        }
        if (mask == 64) {
            return 6;
        }
        return 0;
    }

    public static void checkGallery() {
        if (VERSION.SDK_INT >= 24 && allPhotosAlbumEntry != null) {
            Utilities.globalQueue.postRunnable(new MediaController$2(allPhotosAlbumEntry.photos.size()), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
        }
    }

    public static MediaController getInstance() {
        MediaController localInstance = Instance;
        if (localInstance == null) {
            synchronized (MediaController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        MediaController localInstance2 = new MediaController();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public MediaController() {
        int a;
        try {
            this.recordBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
            if (this.recordBufferSize <= 0) {
                this.recordBufferSize = 1280;
            }
            this.playerBufferSize = AudioTrack.getMinBufferSize(48000, 4, 2);
            if (this.playerBufferSize <= 0) {
                this.playerBufferSize = 3840;
            }
            for (a = 0; a < 5; a++) {
                ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                buffer.order(ByteOrder.nativeOrder());
                this.recordBuffers.add(buffer);
            }
            for (a = 0; a < 3; a++) {
                this.freePlayerBuffers.add(new MediaController$AudioBuffer(this, this.playerBufferSize));
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        try {
            this.sensorManager = (SensorManager) ApplicationLoader.applicationContext.getSystemService("sensor");
            this.linearSensor = this.sensorManager.getDefaultSensor(10);
            this.gravitySensor = this.sensorManager.getDefaultSensor(9);
            if (this.linearSensor == null || this.gravitySensor == null) {
                FileLog.m92e("gravity or linear sensor not found");
                this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
                this.linearSensor = null;
                this.gravitySensor = null;
            }
            this.proximitySensor = this.sensorManager.getDefaultSensor(8);
            this.proximityWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(32, "proximity");
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
        this.fileBuffer = ByteBuffer.allocateDirect(1920);
        this.recordQueue = new DispatchQueue("recordQueue");
        this.recordQueue.setPriority(10);
        this.fileEncodingQueue = new DispatchQueue("fileEncodingQueue");
        this.fileEncodingQueue.setPriority(10);
        this.playerQueue = new DispatchQueue("playerQueue");
        this.fileDecodingQueue = new DispatchQueue("fileDecodingQueue");
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        a = 0;
        while (a < 4) {
            String key = "mobileDataDownloadMask" + (a == 0 ? "" : Integer.valueOf(a));
            if (a == 0 || preferences.contains(key)) {
                Object obj;
                this.mobileDataDownloadMask[a] = preferences.getInt(key, 115);
                this.wifiDownloadMask[a] = preferences.getInt("wifiDownloadMask" + (a == 0 ? "" : Integer.valueOf(a)), 115);
                int[] iArr = this.roamingDownloadMask;
                StringBuilder append = new StringBuilder().append("roamingDownloadMask");
                if (a == 0) {
                    obj = "";
                } else {
                    obj = Integer.valueOf(a);
                }
                iArr[a] = preferences.getInt(append.append(obj).toString(), 0);
            } else {
                this.mobileDataDownloadMask[a] = this.mobileDataDownloadMask[0];
                this.wifiDownloadMask[a] = this.wifiDownloadMask[0];
                this.roamingDownloadMask[a] = this.roamingDownloadMask[0];
            }
            a++;
        }
        for (a = 0; a < 7; a++) {
            int sdefault;
            if (a == 1) {
                sdefault = 2097152;
            } else if (a == 6) {
                sdefault = 5242880;
            } else {
                sdefault = 10485760;
            }
            this.mobileMaxFileSize[a] = preferences.getInt("mobileMaxDownloadSize" + a, sdefault);
            this.wifiMaxFileSize[a] = preferences.getInt("wifiMaxDownloadSize" + a, sdefault);
            this.roamingMaxFileSize[a] = preferences.getInt("roamingMaxDownloadSize" + a, sdefault);
        }
        this.globalAutodownloadEnabled = preferences.getBoolean("globalAutodownloadEnabled", true);
        this.saveToGallery = preferences.getBoolean("save_gallery", false);
        this.autoplayGifs = preferences.getBoolean("autoplay_gif", true);
        this.raiseToSpeak = preferences.getBoolean("raise_to_speak", true);
        this.customTabs = preferences.getBoolean("custom_tabs", true);
        this.directShare = preferences.getBoolean("direct_share", true);
        this.shuffleMusic = preferences.getBoolean("shuffleMusic", false);
        this.playOrderReversed = preferences.getBoolean("playOrderReversed", false);
        this.inappCamera = preferences.getBoolean("inappCamera", true);
        this.roundCamera16to9 = preferences.getBoolean("roundCamera16to9", true);
        this.groupPhotosEnabled = preferences.getBoolean("groupPhotosEnabled", true);
        this.repeatMode = preferences.getInt("repeatMode", 0);
        AndroidUtilities.runOnUIThread(new MediaController$3(this));
        ApplicationLoader.applicationContext.registerReceiver(new MediaController$4(this), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        if (UserConfig.isClientActivated()) {
            checkAutodownloadSettings();
        }
        this.mediaProjections = new String[]{"_data", "_display_name", "bucket_display_name", "datetaken", "title", SettingsJsonConstants.ICON_WIDTH_KEY, "height"};
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, true, new MediaController$GalleryObserverExternal(this));
        } catch (Throwable e22) {
            FileLog.m94e(e22);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Media.INTERNAL_CONTENT_URI, true, new MediaController$GalleryObserverInternal(this));
        } catch (Throwable e222) {
            FileLog.m94e(e222);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Video.Media.EXTERNAL_CONTENT_URI, true, new MediaController$GalleryObserverExternal(this));
        } catch (Throwable e2222) {
            FileLog.m94e(e2222);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Video.Media.INTERNAL_CONTENT_URI, true, new MediaController$GalleryObserverInternal(this));
        } catch (Throwable e22222) {
            FileLog.m94e(e22222);
        }
        try {
            PhoneStateListener phoneStateListener = new MediaController$5(this);
            TelephonyManager mgr = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            if (mgr != null) {
                mgr.listen(phoneStateListener, 32);
            }
        } catch (Throwable e222222) {
            FileLog.m94e(e222222);
        }
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange == -1) {
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                pauseMessage(getPlayingMessageObject());
            }
            this.hasAudioFocus = 0;
            this.audioFocus = 0;
        } else if (focusChange == 1) {
            this.audioFocus = 2;
            if (this.resumeAudioOnFocusGain) {
                this.resumeAudioOnFocusGain = false;
                if (isPlayingMessage(getPlayingMessageObject()) && isMessagePaused()) {
                    playMessage(getPlayingMessageObject());
                }
            }
        } else if (focusChange == -3) {
            this.audioFocus = 1;
        } else if (focusChange == -2) {
            this.audioFocus = 0;
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                pauseMessage(getPlayingMessageObject());
                this.resumeAudioOnFocusGain = true;
            }
        }
        setPlayerVolume();
    }

    private void setPlayerVolume() {
        try {
            float volume;
            if (this.audioFocus != 1) {
                volume = 1.0f;
            } else {
                volume = VOLUME_DUCK;
            }
            if (this.audioPlayer != null) {
                this.audioPlayer.setVolume(volume, volume);
            } else if (this.audioTrackPlayer != null) {
                this.audioTrackPlayer.setStereoVolume(volume, volume);
            } else if (this.videoPlayer != null) {
                this.videoPlayer.setVolume(volume);
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    private void startProgressTimer(MessageObject currentPlayingMessageObject) {
        synchronized (this.progressTimerSync) {
            if (this.progressTimer != null) {
                try {
                    this.progressTimer.cancel();
                    this.progressTimer = null;
                } catch (Throwable e) {
                    FileLog.m94e(e);
                }
            }
            this.progressTimer = new Timer();
            this.progressTimer.schedule(new MediaController$6(this, currentPlayingMessageObject), 0, 17);
        }
    }

    private void stopProgressTimer() {
        synchronized (this.progressTimerSync) {
            if (this.progressTimer != null) {
                try {
                    this.progressTimer.cancel();
                    this.progressTimer = null;
                } catch (Throwable e) {
                    FileLog.m94e(e);
                }
            }
        }
    }

    public void cleanup() {
        cleanupPlayer(false, true);
        this.audioInfo = null;
        this.playMusicAgain = false;
        this.photoDownloadQueue.clear();
        this.audioDownloadQueue.clear();
        this.videoMessageDownloadQueue.clear();
        this.documentDownloadQueue.clear();
        this.videoDownloadQueue.clear();
        this.musicDownloadQueue.clear();
        this.gifDownloadQueue.clear();
        this.downloadQueueKeys.clear();
        this.videoConvertQueue.clear();
        this.playlist.clear();
        this.shuffledPlaylist.clear();
        this.generatingWaveform.clear();
        this.typingTimes.clear();
        this.voiceMessagesPlaylist = null;
        this.voiceMessagesPlaylistMap = null;
        cancelVideoConvert(null);
    }

    protected int getAutodownloadMask() {
        if (!this.globalAutodownloadEnabled) {
            return 0;
        }
        int[] masksArray;
        int result = 0;
        if (ConnectionsManager.isConnectedToWiFi()) {
            masksArray = this.wifiDownloadMask;
        } else if (ConnectionsManager.isRoaming()) {
            masksArray = this.roamingDownloadMask;
        } else {
            masksArray = this.mobileDataDownloadMask;
        }
        for (int a = 0; a < 4; a++) {
            int mask = 0;
            if ((masksArray[a] & 1) != 0) {
                mask = 0 | 1;
            }
            if ((masksArray[a] & 2) != 0) {
                mask |= 2;
            }
            if ((masksArray[a] & 64) != 0) {
                mask |= 64;
            }
            if ((masksArray[a] & 4) != 0) {
                mask |= 4;
            }
            if ((masksArray[a] & 8) != 0) {
                mask |= 8;
            }
            if ((masksArray[a] & 16) != 0) {
                mask |= 16;
            }
            if ((masksArray[a] & 32) != 0) {
                mask |= 32;
            }
            result |= mask << (a * 8);
        }
        return result;
    }

    protected int getAutodownloadMaskAll() {
        if (!this.globalAutodownloadEnabled) {
            return 0;
        }
        int mask = 0;
        int a = 0;
        while (a < 4) {
            if (!((this.mobileDataDownloadMask[a] & 1) == 0 && (this.wifiDownloadMask[a] & 1) == 0 && (this.roamingDownloadMask[a] & 1) == 0)) {
                mask |= 1;
            }
            if (!((this.mobileDataDownloadMask[a] & 2) == 0 && (this.wifiDownloadMask[a] & 2) == 0 && (this.roamingDownloadMask[a] & 2) == 0)) {
                mask |= 2;
            }
            if (!((this.mobileDataDownloadMask[a] & 64) == 0 && (this.wifiDownloadMask[a] & 64) == 0 && (this.roamingDownloadMask[a] & 64) == 0)) {
                mask |= 64;
            }
            if (!((this.mobileDataDownloadMask[a] & 4) == 0 && (this.wifiDownloadMask[a] & 4) == 0 && (this.roamingDownloadMask[a] & 4) == 0)) {
                mask |= 4;
            }
            if (!((this.mobileDataDownloadMask[a] & 8) == 0 && (this.wifiDownloadMask[a] & 8) == 0 && (this.roamingDownloadMask[a] & 8) == 0)) {
                mask |= 8;
            }
            if (!((this.mobileDataDownloadMask[a] & 16) == 0 && (this.wifiDownloadMask[a] & 16) == 0 && (this.roamingDownloadMask[a] & 16) == 0)) {
                mask |= 16;
            }
            if ((this.mobileDataDownloadMask[a] & 32) != 0 || (this.wifiDownloadMask[a] & 32) != 0 || (this.roamingDownloadMask[a] & 32) != 0) {
                mask |= 32;
            }
            a++;
        }
        return mask;
    }

    public void checkAutodownloadSettings() {
        int currentMask = getCurrentDownloadMask();
        if (currentMask != this.lastCheckMask) {
            int a;
            this.lastCheckMask = currentMask;
            if ((currentMask & 1) == 0) {
                for (a = 0; a < this.photoDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$PhotoSize) ((DownloadObject) this.photoDownloadQueue.get(a)).object);
                }
                this.photoDownloadQueue.clear();
            } else if (this.photoDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(1);
            }
            if ((currentMask & 2) == 0) {
                for (a = 0; a < this.audioDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$Document) ((DownloadObject) this.audioDownloadQueue.get(a)).object);
                }
                this.audioDownloadQueue.clear();
            } else if (this.audioDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(2);
            }
            if ((currentMask & 64) == 0) {
                for (a = 0; a < this.videoMessageDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$Document) ((DownloadObject) this.videoMessageDownloadQueue.get(a)).object);
                }
                this.videoMessageDownloadQueue.clear();
            } else if (this.videoMessageDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(64);
            }
            if ((currentMask & 8) == 0) {
                for (a = 0; a < this.documentDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile(((DownloadObject) this.documentDownloadQueue.get(a)).object);
                }
                this.documentDownloadQueue.clear();
            } else if (this.documentDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(8);
            }
            if ((currentMask & 4) == 0) {
                for (a = 0; a < this.videoDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$Document) ((DownloadObject) this.videoDownloadQueue.get(a)).object);
                }
                this.videoDownloadQueue.clear();
            } else if (this.videoDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(4);
            }
            if ((currentMask & 16) == 0) {
                for (a = 0; a < this.musicDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$Document) ((DownloadObject) this.musicDownloadQueue.get(a)).object);
                }
                this.musicDownloadQueue.clear();
            } else if (this.musicDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(16);
            }
            if ((currentMask & 32) == 0) {
                for (a = 0; a < this.gifDownloadQueue.size(); a++) {
                    FileLoader.getInstance().cancelLoadFile((TLRPC$Document) ((DownloadObject) this.gifDownloadQueue.get(a)).object);
                }
                this.gifDownloadQueue.clear();
            } else if (this.gifDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(32);
            }
            int mask = getAutodownloadMaskAll();
            if (mask == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(0);
                return;
            }
            if ((mask & 1) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(1);
            }
            if ((mask & 2) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(2);
            }
            if ((mask & 64) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(64);
            }
            if ((mask & 4) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(4);
            }
            if ((mask & 8) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(8);
            }
            if ((mask & 16) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(16);
            }
            if ((mask & 32) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(32);
            }
        }
    }

    public boolean canDownloadMedia(MessageObject messageObject) {
        return canDownloadMedia(messageObject.messageOwner);
    }

    public boolean canDownloadMedia(TLRPC$Message message) {
        if (!this.globalAutodownloadEnabled) {
            return false;
        }
        int type;
        int index;
        if (MessageObject.isPhoto(message)) {
            type = 1;
        } else if (MessageObject.isVoiceMessage(message)) {
            type = 2;
        } else if (MessageObject.isRoundVideoMessage(message)) {
            type = 64;
        } else if (MessageObject.isVideoMessage(message)) {
            type = 4;
        } else if (MessageObject.isMusicMessage(message)) {
            type = 16;
        } else if (MessageObject.isGifMessage(message)) {
            type = 32;
        } else {
            type = 8;
        }
        TLRPC$Peer peer = message.to_id;
        if (peer == null) {
            index = 1;
        } else if (peer.user_id != 0) {
            if (ContactsController.getInstance().contactsDict.containsKey(Integer.valueOf(peer.user_id))) {
                index = 0;
            } else {
                index = 1;
            }
        } else if (peer.chat_id != 0) {
            index = 2;
        } else if (MessageObject.isMegagroup(message)) {
            index = 2;
        } else {
            index = 3;
        }
        int mask;
        int maxSize;
        if (ConnectionsManager.isConnectedToWiFi()) {
            mask = this.wifiDownloadMask[index];
            maxSize = this.wifiMaxFileSize[maskToIndex(type)];
        } else if (ConnectionsManager.isRoaming()) {
            mask = this.roamingDownloadMask[index];
            maxSize = this.roamingMaxFileSize[maskToIndex(type)];
        } else {
            mask = this.mobileDataDownloadMask[index];
            maxSize = this.mobileMaxFileSize[maskToIndex(type)];
        }
        if ((type == 1 || MessageObject.getMessageSize(message) <= maxSize) && (mask & type) != 0) {
            return true;
        }
        return false;
    }

    private int getCurrentDownloadMask() {
        if (!this.globalAutodownloadEnabled) {
            return 0;
        }
        int mask;
        int a;
        if (ConnectionsManager.isConnectedToWiFi()) {
            mask = 0;
            for (a = 0; a < 4; a++) {
                mask |= this.wifiDownloadMask[a];
            }
            return mask;
        } else if (ConnectionsManager.isRoaming()) {
            mask = 0;
            for (a = 0; a < 4; a++) {
                mask |= this.roamingDownloadMask[a];
            }
            return mask;
        } else {
            mask = 0;
            for (a = 0; a < 4; a++) {
                mask |= this.mobileDataDownloadMask[a];
            }
            return mask;
        }
    }

    protected void processDownloadObjects(int type, ArrayList<DownloadObject> objects) {
        if (!objects.isEmpty()) {
            ArrayList<DownloadObject> queue = null;
            if (type == 1) {
                queue = this.photoDownloadQueue;
            } else if (type == 2) {
                queue = this.audioDownloadQueue;
            } else if (type == 64) {
                queue = this.videoMessageDownloadQueue;
            } else if (type == 4) {
                queue = this.videoDownloadQueue;
            } else if (type == 8) {
                queue = this.documentDownloadQueue;
            } else if (type == 16) {
                queue = this.musicDownloadQueue;
            } else if (type == 32) {
                queue = this.gifDownloadQueue;
            }
            for (int a = 0; a < objects.size(); a++) {
                String path;
                DownloadObject downloadObject = (DownloadObject) objects.get(a);
                if (downloadObject.object instanceof TLRPC$Document) {
                    path = FileLoader.getAttachFileName(downloadObject.object);
                } else {
                    path = FileLoader.getAttachFileName(downloadObject.object);
                }
                if (!this.downloadQueueKeys.containsKey(path)) {
                    boolean added = true;
                    if (downloadObject.object instanceof TLRPC$PhotoSize) {
                        FileLoader.getInstance().loadFile((TLRPC$PhotoSize) downloadObject.object, null, downloadObject.secret ? 2 : 0);
                    } else if (downloadObject.object instanceof TLRPC$Document) {
                        FileLoader.getInstance().loadFile((TLRPC$Document) downloadObject.object, false, downloadObject.secret ? 2 : 0);
                    } else {
                        added = false;
                    }
                    if (added) {
                        queue.add(downloadObject);
                        this.downloadQueueKeys.put(path, downloadObject);
                    }
                }
            }
        }
    }

    protected void newDownloadObjectsAvailable(int downloadMask) {
        int mask = getCurrentDownloadMask();
        if (!((mask & 1) == 0 || (downloadMask & 1) == 0 || !this.photoDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(1);
        }
        if (!((mask & 2) == 0 || (downloadMask & 2) == 0 || !this.audioDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(2);
        }
        if (!((mask & 64) == 0 || (downloadMask & 64) == 0 || !this.videoMessageDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(64);
        }
        if (!((mask & 4) == 0 || (downloadMask & 4) == 0 || !this.videoDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(4);
        }
        if (!((mask & 8) == 0 || (downloadMask & 8) == 0 || !this.documentDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(8);
        }
        if (!((mask & 16) == 0 || (downloadMask & 16) == 0 || !this.musicDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(16);
        }
        if ((mask & 32) != 0 && (downloadMask & 32) != 0 && this.gifDownloadQueue.isEmpty()) {
            MessagesStorage.getInstance().getDownloadQueue(32);
        }
    }

    private void checkDownloadFinished(String fileName, int state) {
        DownloadObject downloadObject = (DownloadObject) this.downloadQueueKeys.get(fileName);
        if (downloadObject != null) {
            this.downloadQueueKeys.remove(fileName);
            if (state == 0 || state == 2) {
                MessagesStorage.getInstance().removeFromDownloadQueue(downloadObject.id, downloadObject.type, false);
            }
            if (downloadObject.type == 1) {
                this.photoDownloadQueue.remove(downloadObject);
                if (this.photoDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(1);
                }
            } else if (downloadObject.type == 2) {
                this.audioDownloadQueue.remove(downloadObject);
                if (this.audioDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(2);
                }
            } else if (downloadObject.type == 64) {
                this.videoMessageDownloadQueue.remove(downloadObject);
                if (this.videoMessageDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(64);
                }
            } else if (downloadObject.type == 4) {
                this.videoDownloadQueue.remove(downloadObject);
                if (this.videoDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(4);
                }
            } else if (downloadObject.type == 8) {
                this.documentDownloadQueue.remove(downloadObject);
                if (this.documentDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(8);
                }
            } else if (downloadObject.type == 16) {
                this.musicDownloadQueue.remove(downloadObject);
                if (this.musicDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(16);
                }
            } else if (downloadObject.type == 32) {
                this.gifDownloadQueue.remove(downloadObject);
                if (this.gifDownloadQueue.isEmpty()) {
                    newDownloadObjectsAvailable(32);
                }
            }
        }
    }

    public void startMediaObserver() {
        ApplicationLoader.applicationHandler.removeCallbacks(this.stopMediaObserverRunnable);
        this.startObserverToken++;
        try {
            if (this.internalObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri uri = Media.EXTERNAL_CONTENT_URI;
                ContentObserver mediaController$ExternalObserver = new MediaController$ExternalObserver(this);
                this.externalObserver = mediaController$ExternalObserver;
                contentResolver.registerContentObserver(uri, false, mediaController$ExternalObserver);
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        try {
            if (this.externalObserver == null) {
                contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                uri = Media.INTERNAL_CONTENT_URI;
                mediaController$ExternalObserver = new MediaController$InternalObserver(this);
                this.internalObserver = mediaController$ExternalObserver;
                contentResolver.registerContentObserver(uri, false, mediaController$ExternalObserver);
            }
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
    }

    public void startSmsObserver() {
        try {
            if (this.smsObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri parse = Uri.parse("content://sms");
                ContentObserver mediaController$SmsObserver = new MediaController$SmsObserver(this);
                this.smsObserver = mediaController$SmsObserver;
                contentResolver.registerContentObserver(parse, false, mediaController$SmsObserver);
            }
            AndroidUtilities.runOnUIThread(new MediaController$7(this), 300000);
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public void stopMediaObserver() {
        if (this.stopMediaObserverRunnable == null) {
            this.stopMediaObserverRunnable = new MediaController$StopMediaObserverRunnable(this, null);
        }
        this.stopMediaObserverRunnable.currentObserverToken = this.startObserverToken;
        ApplicationLoader.applicationHandler.postDelayed(this.stopMediaObserverRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    private void processMediaObserver(Uri uri) {
        try {
            Point size = AndroidUtilities.getRealScreenSize();
            Cursor cursor = ApplicationLoader.applicationContext.getContentResolver().query(uri, this.mediaProjections, null, null, "date_added DESC LIMIT 1");
            ArrayList<Long> screenshotDates = new ArrayList();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String val = "";
                    String data = cursor.getString(0);
                    String display_name = cursor.getString(1);
                    String album_name = cursor.getString(2);
                    long date = cursor.getLong(3);
                    String title = cursor.getString(4);
                    int photoW = cursor.getInt(5);
                    int photoH = cursor.getInt(6);
                    if ((data != null && data.toLowerCase().contains("screenshot")) || ((display_name != null && display_name.toLowerCase().contains("screenshot")) || ((album_name != null && album_name.toLowerCase().contains("screenshot")) || (title != null && title.toLowerCase().contains("screenshot"))))) {
                        if (photoW == 0 || photoH == 0) {
                            try {
                                Options bmOptions = new Options();
                                bmOptions.inJustDecodeBounds = true;
                                BitmapFactory.decodeFile(data, bmOptions);
                                photoW = bmOptions.outWidth;
                                photoH = bmOptions.outHeight;
                            } catch (Exception e) {
                                screenshotDates.add(Long.valueOf(date));
                            }
                        }
                        if (photoW <= 0 || photoH <= 0 || ((photoW == size.x && photoH == size.y) || (photoH == size.x && photoW == size.y))) {
                            screenshotDates.add(Long.valueOf(date));
                        }
                    }
                }
                cursor.close();
            }
            if (!screenshotDates.isEmpty()) {
                AndroidUtilities.runOnUIThread(new MediaController$8(this, screenshotDates));
            }
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
    }

    private void checkScreenshots(ArrayList<Long> dates) {
        if (dates != null && !dates.isEmpty() && this.lastChatEnterTime != 0) {
            if (this.lastUser != null || (this.lastSecretChat instanceof TLRPC$TL_encryptedChat)) {
                boolean send = false;
                for (int a = 0; a < dates.size(); a++) {
                    Long date = (Long) dates.get(a);
                    if ((this.lastMediaCheckTime == 0 || date.longValue() > this.lastMediaCheckTime) && date.longValue() >= this.lastChatEnterTime && (this.lastChatLeaveTime == 0 || date.longValue() <= this.lastChatLeaveTime + FetchConst.DEFAULT_ON_UPDATE_INTERVAL)) {
                        this.lastMediaCheckTime = Math.max(this.lastMediaCheckTime, date.longValue());
                        send = true;
                    }
                }
                if (!send) {
                    return;
                }
                if (this.lastSecretChat != null) {
                    SecretChatHelper.getInstance().sendScreenshotMessage(this.lastSecretChat, this.lastChatVisibleMessages, null);
                } else {
                    SendMessagesHelper.getInstance().sendScreenshotMessage(this.lastUser, this.lastMessageId, null);
                }
            }
        }
    }

    public void setLastVisibleMessageIds(long enterTime, long leaveTime, User user, TLRPC$EncryptedChat encryptedChat, ArrayList<Long> visibleMessages, int visibleMessage) {
        this.lastChatEnterTime = enterTime;
        this.lastChatLeaveTime = leaveTime;
        this.lastSecretChat = encryptedChat;
        this.lastUser = user;
        this.lastMessageId = visibleMessage;
        this.lastChatVisibleMessages = visibleMessages;
    }

    public int generateObserverTag() {
        int i = this.lastTag;
        this.lastTag = i + 1;
        return i;
    }

    public void addLoadingFileObserver(String fileName, MediaController$FileDownloadProgressListener observer) {
        addLoadingFileObserver(fileName, null, observer);
    }

    public void addLoadingFileObserver(String fileName, MessageObject messageObject, MediaController$FileDownloadProgressListener observer) {
        if (this.listenerInProgress) {
            this.addLaterArray.put(fileName, observer);
            return;
        }
        removeLoadingFileObserver(observer);
        ArrayList<WeakReference<MediaController$FileDownloadProgressListener>> arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.loadingFileObservers.put(fileName, arrayList);
        }
        arrayList.add(new WeakReference(observer));
        if (messageObject != null) {
            ArrayList<MessageObject> messageObjects = (ArrayList) this.loadingFileMessagesObservers.get(fileName);
            if (messageObjects == null) {
                messageObjects = new ArrayList();
                this.loadingFileMessagesObservers.put(fileName, messageObjects);
            }
            messageObjects.add(messageObject);
        }
        this.observersByTag.put(Integer.valueOf(observer.getObserverTag()), fileName);
    }

    public void removeLoadingFileObserver(MediaController$FileDownloadProgressListener observer) {
        if (this.listenerInProgress) {
            this.deleteLaterArray.add(observer);
            return;
        }
        String fileName = (String) this.observersByTag.get(Integer.valueOf(observer.getObserverTag()));
        if (fileName != null) {
            ArrayList<WeakReference<MediaController$FileDownloadProgressListener>> arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
            if (arrayList != null) {
                int a = 0;
                while (a < arrayList.size()) {
                    WeakReference<MediaController$FileDownloadProgressListener> reference = (WeakReference) arrayList.get(a);
                    if (reference.get() == null || reference.get() == observer) {
                        arrayList.remove(a);
                        a--;
                    }
                    a++;
                }
                if (arrayList.isEmpty()) {
                    this.loadingFileObservers.remove(fileName);
                }
            }
            this.observersByTag.remove(Integer.valueOf(observer.getObserverTag()));
        }
    }

    private void processLaterArrays() {
        for (Entry<String, MediaController$FileDownloadProgressListener> listener : this.addLaterArray.entrySet()) {
            addLoadingFileObserver((String) listener.getKey(), (MediaController$FileDownloadProgressListener) listener.getValue());
        }
        this.addLaterArray.clear();
        Iterator it = this.deleteLaterArray.iterator();
        while (it.hasNext()) {
            removeLoadingFileObserver((MediaController$FileDownloadProgressListener) it.next());
        }
        this.deleteLaterArray.clear();
    }

    public void didReceivedNotification(int id, Object... args) {
        String fileName;
        ArrayList<WeakReference<MediaController$FileDownloadProgressListener>> arrayList;
        int a;
        WeakReference<MediaController$FileDownloadProgressListener> reference;
        if (id == NotificationCenter.FileDidFailedLoad || id == NotificationCenter.httpFileDidFailedLoad) {
            this.listenerInProgress = true;
            fileName = args[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
            if (arrayList != null) {
                for (a = 0; a < arrayList.size(); a++) {
                    reference = (WeakReference) arrayList.get(a);
                    if (reference.get() != null) {
                        ((MediaController$FileDownloadProgressListener) reference.get()).onFailedDownload(fileName);
                        this.observersByTag.remove(Integer.valueOf(((MediaController$FileDownloadProgressListener) reference.get()).getObserverTag()));
                    }
                }
                this.loadingFileObservers.remove(fileName);
            }
            this.listenerInProgress = false;
            processLaterArrays();
            checkDownloadFinished(fileName, ((Integer) args[1]).intValue());
        } else if (id == NotificationCenter.FileDidLoaded || id == NotificationCenter.httpFileDidLoaded) {
            this.listenerInProgress = true;
            fileName = (String) args[0];
            if (this.downloadingCurrentMessage && this.playingMessageObject != null && FileLoader.getAttachFileName(this.playingMessageObject.getDocument()).equals(fileName)) {
                this.playMusicAgain = true;
                playMessage(this.playingMessageObject);
            }
            ArrayList<MessageObject> messageObjects = (ArrayList) this.loadingFileMessagesObservers.get(fileName);
            if (messageObjects != null) {
                for (a = 0; a < messageObjects.size(); a++) {
                    ((MessageObject) messageObjects.get(a)).mediaExists = true;
                }
                this.loadingFileMessagesObservers.remove(fileName);
            }
            arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
            if (arrayList != null) {
                for (a = 0; a < arrayList.size(); a++) {
                    reference = (WeakReference) arrayList.get(a);
                    if (reference.get() != null) {
                        ((MediaController$FileDownloadProgressListener) reference.get()).onSuccessDownload(fileName);
                        this.observersByTag.remove(Integer.valueOf(((MediaController$FileDownloadProgressListener) reference.get()).getObserverTag()));
                    }
                }
                this.loadingFileObservers.remove(fileName);
            }
            this.listenerInProgress = false;
            processLaterArrays();
            checkDownloadFinished(fileName, 0);
        } else if (id == NotificationCenter.FileLoadProgressChanged) {
            this.listenerInProgress = true;
            fileName = (String) args[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
            if (arrayList != null) {
                progress = args[1];
                r27 = arrayList.iterator();
                while (r27.hasNext()) {
                    reference = (WeakReference) r27.next();
                    if (reference.get() != null) {
                        ((MediaController$FileDownloadProgressListener) reference.get()).onProgressDownload(fileName, progress.floatValue());
                    }
                }
            }
            this.listenerInProgress = false;
            processLaterArrays();
        } else if (id == NotificationCenter.FileUploadProgressChanged) {
            this.listenerInProgress = true;
            fileName = (String) args[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(fileName);
            if (arrayList != null) {
                progress = (Float) args[1];
                Boolean enc = args[2];
                r27 = arrayList.iterator();
                while (r27.hasNext()) {
                    reference = (WeakReference) r27.next();
                    if (reference.get() != null) {
                        ((MediaController$FileDownloadProgressListener) reference.get()).onProgressUpload(fileName, progress.floatValue(), enc.booleanValue());
                    }
                }
            }
            this.listenerInProgress = false;
            processLaterArrays();
            try {
                ArrayList<SendMessagesHelper$DelayedMessage> delayedMessages = SendMessagesHelper.getInstance().getDelayedMessages(fileName);
                if (delayedMessages != null) {
                    for (a = 0; a < delayedMessages.size(); a++) {
                        SendMessagesHelper$DelayedMessage delayedMessage = (SendMessagesHelper$DelayedMessage) delayedMessages.get(a);
                        if (delayedMessage.encryptedChat == null) {
                            long dialog_id = delayedMessage.peer;
                            Long lastTime;
                            if (delayedMessage.type == 4) {
                                lastTime = (Long) this.typingTimes.get(Long.valueOf(dialog_id));
                                if (lastTime == null || lastTime.longValue() + 4000 < System.currentTimeMillis()) {
                                    MessagesController.getInstance().sendTyping(dialog_id, 4, 0);
                                    this.typingTimes.put(Long.valueOf(dialog_id), Long.valueOf(System.currentTimeMillis()));
                                }
                            } else {
                                lastTime = (Long) this.typingTimes.get(Long.valueOf(dialog_id));
                                TLRPC$Document document = delayedMessage.obj.getDocument();
                                if (lastTime == null || lastTime.longValue() + 4000 < System.currentTimeMillis()) {
                                    if (delayedMessage.obj.isRoundVideo()) {
                                        MessagesController.getInstance().sendTyping(dialog_id, 8, 0);
                                    } else if (delayedMessage.obj.isVideo()) {
                                        MessagesController.getInstance().sendTyping(dialog_id, 5, 0);
                                    } else if (delayedMessage.obj.getDocument() != null) {
                                        MessagesController.getInstance().sendTyping(dialog_id, 3, 0);
                                    } else if (delayedMessage.location != null) {
                                        MessagesController.getInstance().sendTyping(dialog_id, 4, 0);
                                    }
                                    this.typingTimes.put(Long.valueOf(dialog_id), Long.valueOf(System.currentTimeMillis()));
                                }
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        } else if (id == NotificationCenter.messagesDeleted) {
            int channelId = ((Integer) args[1]).intValue();
            ArrayList<Integer> markAsDeletedMessages = args[0];
            if (this.playingMessageObject != null && channelId == this.playingMessageObject.messageOwner.to_id.channel_id && markAsDeletedMessages.contains(Integer.valueOf(this.playingMessageObject.getId()))) {
                cleanupPlayer(true, true);
            }
            if (this.voiceMessagesPlaylist != null && !this.voiceMessagesPlaylist.isEmpty() && channelId == ((MessageObject) this.voiceMessagesPlaylist.get(0)).messageOwner.to_id.channel_id) {
                for (a = 0; a < markAsDeletedMessages.size(); a++) {
                    messageObject = (MessageObject) this.voiceMessagesPlaylistMap.remove(markAsDeletedMessages.get(a));
                    if (messageObject != null) {
                        this.voiceMessagesPlaylist.remove(messageObject);
                    }
                }
            }
        } else if (id == NotificationCenter.removeAllMessagesFromDialog) {
            did = ((Long) args[0]).longValue();
            if (this.playingMessageObject != null && this.playingMessageObject.getDialogId() == did) {
                cleanupPlayer(false, true);
            }
        } else if (id == NotificationCenter.musicDidLoaded) {
            did = ((Long) args[0]).longValue();
            if (this.playingMessageObject != null && this.playingMessageObject.isMusic() && this.playingMessageObject.getDialogId() == did) {
                ArrayList<MessageObject> arrayList2 = args[1];
                this.playlist.addAll(0, arrayList2);
                if (this.shuffleMusic) {
                    buildShuffledPlayList();
                    this.currentPlaylistNum = 0;
                    return;
                }
                this.currentPlaylistNum += arrayList2.size();
            }
        } else if (id == NotificationCenter.didReceivedNewMessages && this.voiceMessagesPlaylist != null && !this.voiceMessagesPlaylist.isEmpty()) {
            if (((Long) args[0]).longValue() == ((MessageObject) this.voiceMessagesPlaylist.get(0)).getDialogId()) {
                ArrayList<MessageObject> arr = args[1];
                for (a = 0; a < arr.size(); a++) {
                    messageObject = (MessageObject) arr.get(a);
                    if ((messageObject.isVoice() || messageObject.isRoundVideo()) && (!this.voiceMessagesPlaylistUnread || (messageObject.isContentUnread() && !messageObject.isOut()))) {
                        this.voiceMessagesPlaylist.add(messageObject);
                        this.voiceMessagesPlaylistMap.put(Integer.valueOf(messageObject.getId()), messageObject);
                    }
                }
            }
        }
    }

    private void checkDecoderQueue() {
        this.fileDecodingQueue.postRunnable(new MediaController$9(this));
    }

    private void checkPlayerQueue() {
        this.playerQueue.postRunnable(new MediaController$10(this));
    }

    protected boolean isRecordingAudio() {
        return (this.recordStartRunnable == null && this.recordingAudio == null) ? false : true;
    }

    private boolean isNearToSensor(float value) {
        return value < 5.0f && value != this.proximitySensor.getMaximumRange();
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.sensorsStarted && VoIPService.getSharedInstance() == null) {
            if (event.sensor == this.proximitySensor) {
                FileLog.m92e("proximity changed to " + event.values[0]);
                if (this.lastProximityValue == -100.0f) {
                    this.lastProximityValue = event.values[0];
                } else if (this.lastProximityValue != event.values[0]) {
                    this.proximityHasDifferentValues = true;
                }
                if (this.proximityHasDifferentValues) {
                    this.proximityTouched = isNearToSensor(event.values[0]);
                }
            } else if (event.sensor == this.accelerometerSensor) {
                double alpha;
                if (this.lastTimestamp == 0) {
                    alpha = 0.9800000190734863d;
                } else {
                    alpha = 1.0d / (1.0d + (((double) (event.timestamp - this.lastTimestamp)) / 1.0E9d));
                }
                this.lastTimestamp = event.timestamp;
                this.gravity[0] = (float) ((((double) this.gravity[0]) * alpha) + ((1.0d - alpha) * ((double) event.values[0])));
                this.gravity[1] = (float) ((((double) this.gravity[1]) * alpha) + ((1.0d - alpha) * ((double) event.values[1])));
                this.gravity[2] = (float) ((((double) this.gravity[2]) * alpha) + ((1.0d - alpha) * ((double) event.values[2])));
                this.gravityFast[0] = (0.8f * this.gravity[0]) + (0.19999999f * event.values[0]);
                this.gravityFast[1] = (0.8f * this.gravity[1]) + (0.19999999f * event.values[1]);
                this.gravityFast[2] = (0.8f * this.gravity[2]) + (0.19999999f * event.values[2]);
                this.linearAcceleration[0] = event.values[0] - this.gravity[0];
                this.linearAcceleration[1] = event.values[1] - this.gravity[1];
                this.linearAcceleration[2] = event.values[2] - this.gravity[2];
            } else if (event.sensor == this.linearSensor) {
                this.linearAcceleration[0] = event.values[0];
                this.linearAcceleration[1] = event.values[1];
                this.linearAcceleration[2] = event.values[2];
            } else if (event.sensor == this.gravitySensor) {
                float[] fArr = this.gravityFast;
                float[] fArr2 = this.gravity;
                float f = event.values[0];
                fArr2[0] = f;
                fArr[0] = f;
                fArr = this.gravityFast;
                fArr2 = this.gravity;
                f = event.values[1];
                fArr2[1] = f;
                fArr[1] = f;
                fArr = this.gravityFast;
                fArr2 = this.gravity;
                f = event.values[2];
                fArr2[2] = f;
                fArr[2] = f;
            }
            if (event.sensor == this.linearSensor || event.sensor == this.gravitySensor || event.sensor == this.accelerometerSensor) {
                float val = ((this.gravity[0] * this.linearAcceleration[0]) + (this.gravity[1] * this.linearAcceleration[1])) + (this.gravity[2] * this.linearAcceleration[2]);
                if (this.raisedToBack != 6) {
                    if (val <= 0.0f || this.previousAccValue <= 0.0f) {
                        if (val < 0.0f && this.previousAccValue < 0.0f) {
                            if (this.raisedToTop != 6 || val >= -15.0f) {
                                if (val > -15.0f) {
                                    this.countLess++;
                                }
                                if (!(this.countLess != 10 && this.raisedToTop == 6 && this.raisedToBack == 0)) {
                                    this.raisedToTop = 0;
                                    this.raisedToBack = 0;
                                    this.countLess = 0;
                                }
                            } else if (this.raisedToBack < 6) {
                                this.raisedToBack++;
                                if (this.raisedToBack == 6) {
                                    this.raisedToTop = 0;
                                    this.countLess = 0;
                                    this.timeSinceRaise = System.currentTimeMillis();
                                }
                            }
                        }
                    } else if (val <= 15.0f || this.raisedToBack != 0) {
                        if (val < 15.0f) {
                            this.countLess++;
                        }
                        if (!(this.countLess != 10 && this.raisedToTop == 6 && this.raisedToBack == 0)) {
                            this.raisedToBack = 0;
                            this.raisedToTop = 0;
                            this.countLess = 0;
                        }
                    } else if (this.raisedToTop < 6 && !this.proximityTouched) {
                        this.raisedToTop++;
                        if (this.raisedToTop == 6) {
                            this.countLess = 0;
                        }
                    }
                }
                this.previousAccValue = val;
                boolean z = this.gravityFast[1] > 2.5f && Math.abs(this.gravityFast[2]) < 4.0f && Math.abs(this.gravityFast[0]) > 1.5f;
                this.accelerometerVertical = z;
            }
            if (this.raisedToBack == 6 && this.accelerometerVertical && this.proximityTouched && !NotificationsController.getInstance().audioManager.isWiredHeadsetOn()) {
                FileLog.m92e("sensor values reached");
                if (this.playingMessageObject == null && this.recordStartRunnable == null && this.recordingAudio == null && !PhotoViewer.getInstance().isVisible() && ApplicationLoader.isScreenOn && !this.inputFieldHasText && this.allowStartRecord && this.raiseChat != null && !this.callInProgress) {
                    if (!this.raiseToEarRecord) {
                        FileLog.m92e("start record");
                        this.useFrontSpeaker = true;
                        if (!this.raiseChat.playFirstUnreadVoiceMessage()) {
                            this.raiseToEarRecord = true;
                            this.useFrontSpeaker = false;
                            startRecording(this.raiseChat.getDialogId(), null);
                        }
                        if (this.useFrontSpeaker) {
                            setUseFrontSpeaker(true);
                        }
                        this.ignoreOnPause = true;
                        if (!(!this.proximityHasDifferentValues || this.proximityWakeLock == null || this.proximityWakeLock.isHeld())) {
                            this.proximityWakeLock.acquire();
                        }
                    }
                } else if (this.playingMessageObject != null && ((this.playingMessageObject.isVoice() || this.playingMessageObject.isRoundVideo()) && !this.useFrontSpeaker)) {
                    FileLog.m92e("start listen");
                    if (!(!this.proximityHasDifferentValues || this.proximityWakeLock == null || this.proximityWakeLock.isHeld())) {
                        this.proximityWakeLock.acquire();
                    }
                    setUseFrontSpeaker(true);
                    startAudioAgain(false);
                    this.ignoreOnPause = true;
                }
                this.raisedToBack = 0;
                this.raisedToTop = 0;
                this.countLess = 0;
            } else if (this.proximityTouched) {
                SharedPreferences plusPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                if (!(this.useFrontSpeaker || plusPreferences.getBoolean("disableAudioStop", false) || this.playingMessageObject == null || ((!this.playingMessageObject.isVoice() && !this.playingMessageObject.isRoundVideo()) || this.useFrontSpeaker))) {
                    FileLog.m92e("start listen by proximity only");
                    if (!(!this.proximityHasDifferentValues || this.proximityWakeLock == null || this.proximityWakeLock.isHeld())) {
                        this.proximityWakeLock.acquire();
                    }
                    setUseFrontSpeaker(true);
                    startAudioAgain(false);
                    this.ignoreOnPause = true;
                }
            } else if (!this.proximityTouched) {
                if (this.raiseToEarRecord) {
                    FileLog.m92e("stop record");
                    stopRecording(2);
                    this.raiseToEarRecord = false;
                    this.ignoreOnPause = false;
                    if (this.proximityHasDifferentValues && this.proximityWakeLock != null && this.proximityWakeLock.isHeld()) {
                        this.proximityWakeLock.release();
                    }
                } else if (this.useFrontSpeaker) {
                    FileLog.m92e("stop listen");
                    this.useFrontSpeaker = false;
                    startAudioAgain(true);
                    this.ignoreOnPause = false;
                    if (this.proximityHasDifferentValues && this.proximityWakeLock != null && this.proximityWakeLock.isHeld()) {
                        this.proximityWakeLock.release();
                    }
                }
            }
            if (this.timeSinceRaise != 0 && this.raisedToBack == 6 && Math.abs(System.currentTimeMillis() - this.timeSinceRaise) > 1000) {
                this.raisedToBack = 0;
                this.raisedToTop = 0;
                this.countLess = 0;
                this.timeSinceRaise = 0;
            }
        }
    }

    private void setUseFrontSpeaker(boolean value) {
        this.useFrontSpeaker = value;
        AudioManager audioManager = NotificationsController.getInstance().audioManager;
        if (this.useFrontSpeaker) {
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
            return;
        }
        audioManager.setSpeakerphoneOn(true);
    }

    public void startRecordingIfFromSpeaker() {
        if (this.useFrontSpeaker && this.raiseChat != null && this.allowStartRecord) {
            this.raiseToEarRecord = true;
            startRecording(this.raiseChat.getDialogId(), null);
            this.ignoreOnPause = true;
        }
    }

    private void startAudioAgain(boolean paused) {
        int i = 0;
        if (this.playingMessageObject != null) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.audioRouteChanged, Boolean.valueOf(this.useFrontSpeaker));
            if (this.videoPlayer != null) {
                VideoPlayer videoPlayer = this.videoPlayer;
                if (!this.useFrontSpeaker) {
                    i = 3;
                }
                videoPlayer.setStreamType(i);
                if (paused) {
                    this.videoPlayer.pause();
                    return;
                } else {
                    this.videoPlayer.play();
                    return;
                }
            }
            boolean post;
            if (this.audioPlayer != null) {
                post = true;
            } else {
                post = false;
            }
            MessageObject currentMessageObject = this.playingMessageObject;
            float progress = this.playingMessageObject.audioProgress;
            cleanupPlayer(false, true);
            currentMessageObject.audioProgress = progress;
            playMessage(currentMessageObject);
            if (!paused) {
                return;
            }
            if (post) {
                AndroidUtilities.runOnUIThread(new MediaController$11(this, currentMessageObject), 100);
            } else {
                pauseMessage(currentMessageObject);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void setInputFieldHasText(boolean value) {
        this.inputFieldHasText = value;
    }

    public void setAllowStartRecord(boolean value) {
        this.allowStartRecord = value;
    }

    public void startRaiseToEarSensors(ChatActivity chatActivity) {
        if (chatActivity == null) {
            return;
        }
        if ((this.accelerometerSensor != null || (this.gravitySensor != null && this.linearAcceleration != null)) && this.proximitySensor != null) {
            this.raiseChat = chatActivity;
            if (!this.raiseToSpeak) {
                if (this.playingMessageObject == null) {
                    return;
                }
                if (!(this.playingMessageObject.isVoice() || this.playingMessageObject.isRoundVideo())) {
                    return;
                }
            }
            if (!this.sensorsStarted) {
                float[] fArr = this.gravity;
                float[] fArr2 = this.gravity;
                this.gravity[2] = 0.0f;
                fArr2[1] = 0.0f;
                fArr[0] = 0.0f;
                fArr = this.linearAcceleration;
                fArr2 = this.linearAcceleration;
                this.linearAcceleration[2] = 0.0f;
                fArr2[1] = 0.0f;
                fArr[0] = 0.0f;
                fArr = this.gravityFast;
                fArr2 = this.gravityFast;
                this.gravityFast[2] = 0.0f;
                fArr2[1] = 0.0f;
                fArr[0] = 0.0f;
                this.lastTimestamp = 0;
                this.previousAccValue = 0.0f;
                this.raisedToTop = 0;
                this.countLess = 0;
                this.raisedToBack = 0;
                Utilities.globalQueue.postRunnable(new MediaController$12(this));
                this.sensorsStarted = true;
            }
        }
    }

    public void stopRaiseToEarSensors(ChatActivity chatActivity) {
        if (this.ignoreOnPause) {
            this.ignoreOnPause = false;
        } else if (this.sensorsStarted && !this.ignoreOnPause) {
            if ((this.accelerometerSensor != null || (this.gravitySensor != null && this.linearAcceleration != null)) && this.proximitySensor != null && this.raiseChat == chatActivity) {
                this.raiseChat = null;
                stopRecording(0);
                this.sensorsStarted = false;
                this.accelerometerVertical = false;
                this.proximityTouched = false;
                this.raiseToEarRecord = false;
                this.useFrontSpeaker = false;
                Utilities.globalQueue.postRunnable(new MediaController$13(this));
                if (this.proximityHasDifferentValues && this.proximityWakeLock != null && this.proximityWakeLock.isHeld()) {
                    this.proximityWakeLock.release();
                }
            }
        }
    }

    public void cleanupPlayer(boolean notify, boolean stopService) {
        cleanupPlayer(notify, stopService, false);
    }

    public void cleanupPlayer(boolean notify, boolean stopService, boolean byVoiceEnd) {
        if (this.audioPlayer != null) {
            try {
                this.audioPlayer.reset();
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
            try {
                this.audioPlayer.stop();
            } catch (Throwable e2) {
                FileLog.m94e(e2);
            }
            try {
                this.audioPlayer.release();
            } catch (Throwable e22) {
                FileLog.m94e(e22);
            }
            this.audioPlayer = null;
        } else if (this.audioTrackPlayer != null) {
            synchronized (this.playerObjectSync) {
                try {
                    this.audioTrackPlayer.pause();
                    this.audioTrackPlayer.flush();
                } catch (Throwable e222) {
                    FileLog.m94e(e222);
                }
                try {
                    this.audioTrackPlayer.release();
                } catch (Throwable e2222) {
                    FileLog.m94e(e2222);
                }
                this.audioTrackPlayer = null;
            }
        } else if (this.videoPlayer != null) {
            this.currentAspectRatioFrameLayout = null;
            this.currentTextureViewContainer = null;
            this.currentAspectRatioFrameLayoutReady = false;
            this.currentTextureView = null;
            this.videoPlayer.releasePlayer();
            this.videoPlayer = null;
            try {
                this.baseActivity.getWindow().clearFlags(128);
            } catch (Throwable e22222) {
                FileLog.m94e(e22222);
            }
        }
        stopProgressTimer();
        this.lastProgress = 0;
        this.buffersWrited = 0;
        this.isPaused = false;
        if (this.playingMessageObject != null) {
            if (this.downloadingCurrentMessage) {
                FileLoader.getInstance().cancelLoadFile(this.playingMessageObject.getDocument());
            }
            MessageObject lastFile = this.playingMessageObject;
            this.playingMessageObject.audioProgress = 0.0f;
            this.playingMessageObject.audioProgressSec = 0;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
            this.playingMessageObject = null;
            this.downloadingCurrentMessage = false;
            if (notify) {
                NotificationsController.getInstance().audioManager.abandonAudioFocus(this);
                this.hasAudioFocus = 0;
                if (this.voiceMessagesPlaylist != null) {
                    if (byVoiceEnd && this.voiceMessagesPlaylist.get(0) == lastFile) {
                        this.voiceMessagesPlaylist.remove(0);
                        this.voiceMessagesPlaylistMap.remove(Integer.valueOf(lastFile.getId()));
                        if (this.voiceMessagesPlaylist.isEmpty()) {
                            this.voiceMessagesPlaylist = null;
                            this.voiceMessagesPlaylistMap = null;
                        }
                    } else {
                        this.voiceMessagesPlaylist = null;
                        this.voiceMessagesPlaylistMap = null;
                    }
                }
                if (this.voiceMessagesPlaylist != null) {
                    MessageObject nextVoiceMessage = (MessageObject) this.voiceMessagesPlaylist.get(0);
                    playMessage(nextVoiceMessage);
                    if (!(nextVoiceMessage.isRoundVideo() || this.pipRoundVideoView == null)) {
                        this.pipRoundVideoView.close(true);
                        this.pipRoundVideoView = null;
                    }
                } else {
                    if ((lastFile.isVoice() || lastFile.isRoundVideo()) && lastFile.getId() != 0) {
                        startRecordingIfFromSpeaker();
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingDidReset, Integer.valueOf(lastFile.getId()), Boolean.valueOf(stopService));
                    this.pipSwitchingState = 0;
                    if (this.pipRoundVideoView != null) {
                        this.pipRoundVideoView.close(true);
                        this.pipRoundVideoView = null;
                    }
                }
            }
            if (stopService) {
                ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
            }
        }
        if (!this.useFrontSpeaker && !this.raiseToSpeak) {
            ChatActivity chat = this.raiseChat;
            stopRaiseToEarSensors(this.raiseChat);
            this.raiseChat = chat;
        }
    }

    private void seekOpusPlayer(float progress) {
        if (progress != 1.0f) {
            if (!this.isPaused) {
                this.audioTrackPlayer.pause();
            }
            this.audioTrackPlayer.flush();
            this.fileDecodingQueue.postRunnable(new MediaController$14(this, progress));
        }
    }

    public boolean seekToProgress(MessageObject messageObject, float progress) {
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || messageObject == null || this.playingMessageObject == null) {
            return false;
        }
        if (this.playingMessageObject != null && this.playingMessageObject.getId() != messageObject.getId()) {
            return false;
        }
        try {
            if (this.audioPlayer != null) {
                int seekTo = (int) (((float) this.audioPlayer.getDuration()) * progress);
                this.audioPlayer.seekTo(seekTo);
                this.lastProgress = (long) seekTo;
            } else if (this.audioTrackPlayer != null) {
                seekOpusPlayer(progress);
            } else if (this.videoPlayer != null) {
                this.videoPlayer.seekTo((long) (((float) this.videoPlayer.getDuration()) * progress));
            }
            return true;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return false;
        }
    }

    public MessageObject getPlayingMessageObject() {
        return this.playingMessageObject;
    }

    public int getPlayingMessageObjectNum() {
        return this.currentPlaylistNum;
    }

    private void buildShuffledPlayList() {
        if (!this.playlist.isEmpty()) {
            ArrayList<MessageObject> all = new ArrayList(this.playlist);
            this.shuffledPlaylist.clear();
            MessageObject messageObject = (MessageObject) this.playlist.get(this.currentPlaylistNum);
            all.remove(this.currentPlaylistNum);
            this.shuffledPlaylist.add(messageObject);
            int count = all.size();
            for (int a = 0; a < count; a++) {
                int index = Utilities.random.nextInt(all.size());
                this.shuffledPlaylist.add(all.get(index));
                all.remove(index);
            }
        }
    }

    public boolean setPlaylist(ArrayList<MessageObject> messageObjects, MessageObject current) {
        return setPlaylist(messageObjects, current, true);
    }

    public boolean setPlaylist(ArrayList<MessageObject> messageObjects, MessageObject current, boolean loadMusic) {
        boolean z = true;
        if (this.playingMessageObject == current) {
            return playMessage(current);
        }
        boolean z2;
        if (loadMusic) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.forceLoopCurrentPlaylist = z2;
        if (this.playlist.isEmpty()) {
            z = false;
        }
        this.playMusicAgain = z;
        this.playlist.clear();
        for (int a = messageObjects.size() - 1; a >= 0; a--) {
            MessageObject messageObject = (MessageObject) messageObjects.get(a);
            if (messageObject.isMusic()) {
                this.playlist.add(messageObject);
            }
        }
        this.currentPlaylistNum = this.playlist.indexOf(current);
        if (this.currentPlaylistNum == -1) {
            this.playlist.clear();
            this.shuffledPlaylist.clear();
            this.currentPlaylistNum = this.playlist.size();
            this.playlist.add(current);
        }
        if (current.isMusic()) {
            if (this.shuffleMusic) {
                buildShuffledPlayList();
                this.currentPlaylistNum = 0;
            }
            if (loadMusic) {
                SharedMediaQuery.loadMusic(current.getDialogId(), ((MessageObject) this.playlist.get(0)).getIdWithChannel());
            }
        }
        return playMessage(current);
    }

    public void playNextMessage() {
        playNextMessageWithoutOrder(false);
    }

    public boolean findMessageInPlaylistAndPlay(MessageObject messageObject) {
        int index = this.playlist.indexOf(messageObject);
        if (index == -1) {
            return playMessage(messageObject);
        }
        playMessageAtIndex(index);
        return true;
    }

    public void playMessageAtIndex(int index) {
        if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < this.playlist.size()) {
            this.currentPlaylistNum = index;
            this.playMusicAgain = true;
            playMessage((MessageObject) this.playlist.get(this.currentPlaylistNum));
        }
    }

    private void playNextMessageWithoutOrder(boolean byStop) {
        ArrayList<MessageObject> currentPlayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (byStop && this.repeatMode == 2 && !this.forceLoopCurrentPlaylist) {
            cleanupPlayer(false, false);
            playMessage((MessageObject) currentPlayList.get(this.currentPlaylistNum));
            return;
        }
        boolean last = false;
        if (this.playOrderReversed) {
            this.currentPlaylistNum++;
            if (this.currentPlaylistNum >= currentPlayList.size()) {
                this.currentPlaylistNum = 0;
                last = true;
            }
        } else {
            this.currentPlaylistNum--;
            if (this.currentPlaylistNum < 0) {
                this.currentPlaylistNum = currentPlayList.size() - 1;
                last = true;
            }
        }
        if (last && byStop && this.repeatMode == 0 && !this.forceLoopCurrentPlaylist) {
            if (this.audioPlayer != null || this.audioTrackPlayer != null || this.videoPlayer != null) {
                if (this.audioPlayer != null) {
                    try {
                        this.audioPlayer.reset();
                    } catch (Throwable e) {
                        FileLog.m94e(e);
                    }
                    try {
                        this.audioPlayer.stop();
                    } catch (Throwable e2) {
                        FileLog.m94e(e2);
                    }
                    try {
                        this.audioPlayer.release();
                    } catch (Throwable e22) {
                        FileLog.m94e(e22);
                    }
                    this.audioPlayer = null;
                } else if (this.audioTrackPlayer != null) {
                    synchronized (this.playerObjectSync) {
                        try {
                            this.audioTrackPlayer.pause();
                            this.audioTrackPlayer.flush();
                        } catch (Throwable e222) {
                            FileLog.m94e(e222);
                        }
                        try {
                            this.audioTrackPlayer.release();
                        } catch (Throwable e2222) {
                            FileLog.m94e(e2222);
                        }
                        this.audioTrackPlayer = null;
                    }
                } else if (this.videoPlayer != null) {
                    this.currentAspectRatioFrameLayout = null;
                    this.currentTextureViewContainer = null;
                    this.currentAspectRatioFrameLayoutReady = false;
                    this.currentTextureView = null;
                    this.videoPlayer.releasePlayer();
                    this.videoPlayer = null;
                    try {
                        this.baseActivity.getWindow().clearFlags(128);
                    } catch (Throwable e22222) {
                        FileLog.m94e(e22222);
                    }
                }
                stopProgressTimer();
                this.lastProgress = 0;
                this.buffersWrited = 0;
                this.isPaused = true;
                this.playingMessageObject.audioProgress = 0.0f;
                this.playingMessageObject.audioProgressSec = 0;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            }
        } else if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < currentPlayList.size()) {
            this.playMusicAgain = true;
            playMessage((MessageObject) currentPlayList.get(this.currentPlaylistNum));
        }
    }

    public void playPreviousMessage() {
        ArrayList<MessageObject> currentPlayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (!currentPlayList.isEmpty()) {
            MessageObject currentSong = (MessageObject) currentPlayList.get(this.currentPlaylistNum);
            if (currentSong.audioProgressSec > 10) {
                getInstance().seekToProgress(currentSong, 0.0f);
                return;
            }
            if (this.playOrderReversed) {
                this.currentPlaylistNum--;
                if (this.currentPlaylistNum < 0) {
                    this.currentPlaylistNum = currentPlayList.size() - 1;
                }
            } else {
                this.currentPlaylistNum++;
                if (this.currentPlaylistNum >= currentPlayList.size()) {
                    this.currentPlaylistNum = 0;
                }
            }
            if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < currentPlayList.size()) {
                this.playMusicAgain = true;
                playMessage((MessageObject) currentPlayList.get(this.currentPlaylistNum));
            }
        }
    }

    private void checkIsNextVoiceFileDownloaded() {
        if (this.voiceMessagesPlaylist != null && this.voiceMessagesPlaylist.size() >= 2) {
            MessageObject nextAudio = (MessageObject) this.voiceMessagesPlaylist.get(true);
            File file = null;
            if (nextAudio.messageOwner.attachPath != null && nextAudio.messageOwner.attachPath.length() > 0) {
                file = new File(nextAudio.messageOwner.attachPath);
                if (!file.exists()) {
                    file = null;
                }
            }
            File cacheFile = file != null ? file : FileLoader.getPathToMessage(nextAudio.messageOwner);
            if (cacheFile == null || !cacheFile.exists()) {
                boolean z = false;
            }
            if (cacheFile != null && cacheFile != file && !cacheFile.exists()) {
                FileLoader.getInstance().loadFile(nextAudio.getDocument(), false, 0);
            }
        }
    }

    private void checkIsNextMusicFileDownloaded() {
        if ((getCurrentDownloadMask() & 16) != 0) {
            ArrayList<MessageObject> currentPlayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
            if (currentPlayList != null && currentPlayList.size() >= 2) {
                int nextIndex;
                if (this.playOrderReversed) {
                    nextIndex = this.currentPlaylistNum + 1;
                    if (nextIndex >= currentPlayList.size()) {
                        nextIndex = 0;
                    }
                } else {
                    nextIndex = this.currentPlaylistNum - 1;
                    if (nextIndex < 0) {
                        nextIndex = currentPlayList.size() - 1;
                    }
                }
                MessageObject nextAudio = (MessageObject) currentPlayList.get(nextIndex);
                if (canDownloadMedia(nextAudio)) {
                    File file = null;
                    if (!TextUtils.isEmpty(nextAudio.messageOwner.attachPath)) {
                        file = new File(nextAudio.messageOwner.attachPath);
                        if (!file.exists()) {
                            file = null;
                        }
                    }
                    File cacheFile = file != null ? file : FileLoader.getPathToMessage(nextAudio.messageOwner);
                    if (cacheFile == null || !cacheFile.exists()) {
                        boolean z = false;
                    }
                    if (cacheFile != null && cacheFile != file && !cacheFile.exists() && nextAudio.isMusic()) {
                        FileLoader.getInstance().loadFile(nextAudio.getDocument(), false, 0);
                    }
                }
            }
        }
    }

    public void setVoiceMessagesPlaylist(ArrayList<MessageObject> playlist, boolean unread) {
        this.voiceMessagesPlaylist = playlist;
        if (this.voiceMessagesPlaylist != null) {
            this.voiceMessagesPlaylistUnread = unread;
            this.voiceMessagesPlaylistMap = new HashMap();
            for (int a = 0; a < this.voiceMessagesPlaylist.size(); a++) {
                MessageObject messageObject = (MessageObject) this.voiceMessagesPlaylist.get(a);
                this.voiceMessagesPlaylistMap.put(Integer.valueOf(messageObject.getId()), messageObject);
            }
        }
    }

    private void checkAudioFocus(MessageObject messageObject) {
        int neededAudioFocus;
        if (!messageObject.isVoice() && !messageObject.isRoundVideo()) {
            neededAudioFocus = 1;
        } else if (this.useFrontSpeaker) {
            neededAudioFocus = 3;
        } else {
            neededAudioFocus = 2;
        }
        if (this.hasAudioFocus != neededAudioFocus) {
            int result;
            this.hasAudioFocus = neededAudioFocus;
            if (neededAudioFocus == 3) {
                result = NotificationsController.getInstance().audioManager.requestAudioFocus(this, 0, 1);
            } else {
                result = NotificationsController.getInstance().audioManager.requestAudioFocus(this, 3, neededAudioFocus == 2 ? 3 : 1);
            }
            if (result == 1) {
                this.audioFocus = 2;
            }
        }
    }

    public void setCurrentRoundVisible(boolean visible) {
        if (this.currentAspectRatioFrameLayout != null) {
            if (visible) {
                if (this.pipRoundVideoView != null) {
                    this.pipSwitchingState = 2;
                    this.pipRoundVideoView.close(true);
                    this.pipRoundVideoView = null;
                } else if (this.currentAspectRatioFrameLayout != null) {
                    if (this.currentAspectRatioFrameLayout.getParent() == null) {
                        this.currentTextureViewContainer.addView(this.currentAspectRatioFrameLayout);
                    }
                    this.videoPlayer.setTextureView(this.currentTextureView);
                }
            } else if (this.currentAspectRatioFrameLayout.getParent() != null) {
                this.pipSwitchingState = 1;
                this.currentTextureViewContainer.removeView(this.currentAspectRatioFrameLayout);
            } else {
                if (this.pipRoundVideoView == null) {
                    try {
                        this.pipRoundVideoView = new PipRoundVideoView();
                        this.pipRoundVideoView.show(this.baseActivity, new MediaController$15(this));
                    } catch (Exception e) {
                        this.pipRoundVideoView = null;
                    }
                }
                if (this.pipRoundVideoView != null) {
                    this.videoPlayer.setTextureView(this.pipRoundVideoView.getTextureView());
                }
            }
        }
    }

    public void setTextureView(TextureView textureView, AspectRatioFrameLayout aspectRatioFrameLayout, FrameLayout container, boolean set) {
        boolean z = true;
        if (!set && this.currentTextureView == textureView) {
            this.pipSwitchingState = 1;
            this.currentTextureView = null;
            this.currentAspectRatioFrameLayout = null;
            this.currentTextureViewContainer = null;
        } else if (this.videoPlayer != null && textureView != this.currentTextureView) {
            if (aspectRatioFrameLayout == null || !aspectRatioFrameLayout.isDrawingReady()) {
                z = false;
            }
            this.isDrawingWasReady = z;
            this.currentTextureView = textureView;
            if (this.pipRoundVideoView != null) {
                this.videoPlayer.setTextureView(this.pipRoundVideoView.getTextureView());
            } else {
                this.videoPlayer.setTextureView(this.currentTextureView);
            }
            this.currentAspectRatioFrameLayout = aspectRatioFrameLayout;
            this.currentTextureViewContainer = container;
            if (this.currentAspectRatioFrameLayoutReady && this.currentAspectRatioFrameLayout != null) {
                if (this.currentAspectRatioFrameLayout != null) {
                    this.currentAspectRatioFrameLayout.setAspectRatio(this.currentAspectRatioFrameLayoutRatio, this.currentAspectRatioFrameLayoutRotation);
                }
                if (this.currentTextureViewContainer.getVisibility() != 0) {
                    this.currentTextureViewContainer.setVisibility(0);
                }
            }
        }
    }

    public void setBaseActivity(Activity activity, boolean set) {
        if (set) {
            this.baseActivity = activity;
        } else if (this.baseActivity == activity) {
            this.baseActivity = null;
        }
    }

    public boolean playMessage(MessageObject messageObject) {
        if (messageObject == null) {
            return false;
        }
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || this.playingMessageObject == null || messageObject.getId() != this.playingMessageObject.getId()) {
            File cacheFile;
            if (!messageObject.isOut() && messageObject.isContentUnread()) {
                MessagesController.getInstance().markMessageContentAsRead(messageObject);
            }
            boolean notify = !this.playMusicAgain;
            if (this.playingMessageObject != null) {
                notify = false;
            }
            cleanupPlayer(notify, false);
            this.playMusicAgain = false;
            File file = null;
            if (messageObject.messageOwner.attachPath != null && messageObject.messageOwner.attachPath.length() > 0) {
                file = new File(messageObject.messageOwner.attachPath);
                if (!file.exists()) {
                    file = null;
                }
            }
            if (file != null) {
                cacheFile = file;
            } else {
                cacheFile = FileLoader.getPathToMessage(messageObject.messageOwner);
            }
            if (cacheFile == null || cacheFile == file || cacheFile.exists()) {
                this.downloadingCurrentMessage = false;
                if (messageObject.isMusic()) {
                    checkIsNextMusicFileDownloaded();
                } else {
                    checkIsNextVoiceFileDownloaded();
                }
                if (this.currentAspectRatioFrameLayout != null) {
                    this.isDrawingWasReady = false;
                    this.currentAspectRatioFrameLayout.setDrawingReady(false);
                }
                if (messageObject.isRoundVideo()) {
                    this.playlist.clear();
                    this.shuffledPlaylist.clear();
                    this.videoPlayer = new VideoPlayer();
                    this.videoPlayer.setDelegate(new MediaController$16(this));
                    this.currentAspectRatioFrameLayoutReady = false;
                    if (this.pipRoundVideoView != null || !MessagesController.getInstance().isDialogCreated(messageObject.getDialogId())) {
                        if (this.pipRoundVideoView == null) {
                            try {
                                this.pipRoundVideoView = new PipRoundVideoView();
                                this.pipRoundVideoView.show(this.baseActivity, new MediaController$17(this));
                            } catch (Exception e) {
                                this.pipRoundVideoView = null;
                            }
                        }
                        if (this.pipRoundVideoView != null) {
                            this.videoPlayer.setTextureView(this.pipRoundVideoView.getTextureView());
                        }
                    } else if (this.currentTextureView != null) {
                        this.videoPlayer.setTextureView(this.currentTextureView);
                    }
                    this.videoPlayer.preparePlayer(Uri.fromFile(cacheFile), "other");
                    this.videoPlayer.setStreamType(this.useFrontSpeaker ? 0 : 3);
                    this.videoPlayer.play();
                } else if (isOpusFile(cacheFile.getAbsolutePath()) == 1) {
                    this.playlist.clear();
                    this.shuffledPlaylist.clear();
                    synchronized (this.playerObjectSync) {
                        try {
                            this.ignoreFirstProgress = 3;
                            Semaphore semaphore = new Semaphore(0);
                            Boolean[] result = new Boolean[1];
                            this.fileDecodingQueue.postRunnable(new MediaController$18(this, result, cacheFile, semaphore));
                            semaphore.acquire();
                            if (result[0].booleanValue()) {
                                this.currentTotalPcmDuration = getTotalPcmDuration();
                                this.audioTrackPlayer = new AudioTrack(this.useFrontSpeaker ? 0 : 3, 48000, 4, 2, this.playerBufferSize, 1);
                                this.audioTrackPlayer.setStereoVolume(1.0f, 1.0f);
                                this.audioTrackPlayer.setPlaybackPositionUpdateListener(new MediaController$19(this));
                                this.audioTrackPlayer.play();
                            } else {
                                return false;
                            }
                        } catch (Throwable e2) {
                            FileLog.m94e(e2);
                            if (this.audioTrackPlayer != null) {
                                this.audioTrackPlayer.release();
                                this.audioTrackPlayer = null;
                                this.isPaused = false;
                                this.playingMessageObject = null;
                                this.downloadingCurrentMessage = false;
                            }
                            return false;
                        }
                    }
                } else {
                    try {
                        this.audioPlayer = new MediaPlayer();
                        this.audioPlayer.setAudioStreamType(this.useFrontSpeaker ? 0 : 3);
                        this.audioPlayer.setDataSource(cacheFile.getAbsolutePath());
                        this.audioPlayer.setOnCompletionListener(new MediaController$20(this, messageObject));
                        this.audioPlayer.prepare();
                        this.audioPlayer.start();
                        if (messageObject.isVoice()) {
                            this.audioInfo = null;
                            this.playlist.clear();
                            this.shuffledPlaylist.clear();
                        } else {
                            try {
                                this.audioInfo = AudioInfo.getAudioInfo(cacheFile);
                            } catch (Throwable e22) {
                                FileLog.m94e(e22);
                            }
                        }
                    } catch (Throwable e222) {
                        FileLog.m94e(e222);
                        NotificationCenter instance = NotificationCenter.getInstance();
                        int i = NotificationCenter.messagePlayingPlayStateChanged;
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(this.playingMessageObject != null ? this.playingMessageObject.getId() : 0);
                        instance.postNotificationName(i, objArr);
                        if (this.audioPlayer != null) {
                            this.audioPlayer.release();
                            this.audioPlayer = null;
                            this.isPaused = false;
                            this.playingMessageObject = null;
                            this.downloadingCurrentMessage = false;
                        }
                        return false;
                    }
                }
                checkAudioFocus(messageObject);
                setPlayerVolume();
                this.isPaused = false;
                this.lastProgress = 0;
                this.lastPlayPcm = 0;
                this.playingMessageObject = messageObject;
                if (!this.raiseToSpeak) {
                    startRaiseToEarSensors(this.raiseChat);
                }
                startProgressTimer(this.playingMessageObject);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingDidStarted, messageObject);
                if (this.videoPlayer != null) {
                    try {
                        if (this.playingMessageObject.audioProgress != 0.0f) {
                            this.videoPlayer.seekTo((long) ((int) (((float) this.videoPlayer.getDuration()) * this.playingMessageObject.audioProgress)));
                        }
                    } catch (Throwable e23) {
                        this.playingMessageObject.audioProgress = 0.0f;
                        this.playingMessageObject.audioProgressSec = 0;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                        FileLog.m94e(e23);
                    }
                } else if (this.audioPlayer != null) {
                    try {
                        if (this.playingMessageObject.audioProgress != 0.0f) {
                            this.audioPlayer.seekTo((int) (((float) this.audioPlayer.getDuration()) * this.playingMessageObject.audioProgress));
                        }
                    } catch (Throwable e232) {
                        this.playingMessageObject.audioProgress = 0.0f;
                        this.playingMessageObject.audioProgressSec = 0;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                        FileLog.m94e(e232);
                    }
                } else if (this.audioTrackPlayer != null) {
                    if (this.playingMessageObject.audioProgress == 1.0f) {
                        this.playingMessageObject.audioProgress = 0.0f;
                    }
                    this.fileDecodingQueue.postRunnable(new MediaController$21(this));
                }
                if (this.playingMessageObject.isMusic()) {
                    ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
                } else {
                    ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
                }
                return true;
            }
            FileLoader.getInstance().loadFile(messageObject.getDocument(), false, 0);
            this.downloadingCurrentMessage = true;
            this.isPaused = false;
            this.lastProgress = 0;
            this.lastPlayPcm = 0;
            this.audioInfo = null;
            this.playingMessageObject = messageObject;
            if (this.playingMessageObject.getDocument() != null) {
                ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
            } else {
                ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            return true;
        }
        if (this.isPaused) {
            resumeAudio(messageObject);
        }
        if (!this.raiseToSpeak) {
            startRaiseToEarSensors(this.raiseChat);
        }
        return true;
    }

    public void stopAudio() {
        if ((this.audioTrackPlayer != null || this.audioPlayer != null || this.videoPlayer != null) && this.playingMessageObject != null) {
            try {
                if (this.audioPlayer != null) {
                    try {
                        this.audioPlayer.reset();
                    } catch (Throwable e) {
                        FileLog.m94e(e);
                    }
                    this.audioPlayer.stop();
                    try {
                        if (this.audioPlayer != null) {
                            this.audioPlayer.release();
                            this.audioPlayer = null;
                        } else if (this.audioTrackPlayer != null) {
                            synchronized (this.playerObjectSync) {
                                this.audioTrackPlayer.release();
                                this.audioTrackPlayer = null;
                            }
                        } else if (this.videoPlayer != null) {
                            this.currentAspectRatioFrameLayout = null;
                            this.currentTextureViewContainer = null;
                            this.currentAspectRatioFrameLayoutReady = false;
                            this.currentTextureView = null;
                            this.videoPlayer.releasePlayer();
                            this.videoPlayer = null;
                            try {
                                this.baseActivity.getWindow().clearFlags(128);
                            } catch (Throwable e2) {
                                FileLog.m94e(e2);
                            }
                        }
                    } catch (Throwable e22) {
                        FileLog.m94e(e22);
                    }
                    stopProgressTimer();
                    this.playingMessageObject = null;
                    this.downloadingCurrentMessage = false;
                    this.isPaused = false;
                    ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
                }
                if (this.audioTrackPlayer != null) {
                    this.audioTrackPlayer.pause();
                    this.audioTrackPlayer.flush();
                } else if (this.videoPlayer != null) {
                    this.videoPlayer.pause();
                }
                if (this.audioPlayer != null) {
                    this.audioPlayer.release();
                    this.audioPlayer = null;
                } else if (this.audioTrackPlayer != null) {
                    synchronized (this.playerObjectSync) {
                        this.audioTrackPlayer.release();
                        this.audioTrackPlayer = null;
                    }
                } else if (this.videoPlayer != null) {
                    this.currentAspectRatioFrameLayout = null;
                    this.currentTextureViewContainer = null;
                    this.currentAspectRatioFrameLayoutReady = false;
                    this.currentTextureView = null;
                    this.videoPlayer.releasePlayer();
                    this.videoPlayer = null;
                    this.baseActivity.getWindow().clearFlags(128);
                }
                stopProgressTimer();
                this.playingMessageObject = null;
                this.downloadingCurrentMessage = false;
                this.isPaused = false;
                ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
            } catch (Throwable e222) {
                FileLog.m94e(e222);
            }
        }
    }

    public AudioInfo getAudioInfo() {
        return this.audioInfo;
    }

    public boolean isShuffleMusic() {
        return this.shuffleMusic;
    }

    public boolean isPlayOrderReversed() {
        return this.playOrderReversed;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void toggleShuffleMusic(int type) {
        boolean oldShuffle = this.shuffleMusic;
        if (type == 2) {
            boolean z;
            if (this.shuffleMusic) {
                z = false;
            } else {
                z = true;
            }
            this.shuffleMusic = z;
        } else {
            this.playOrderReversed = !this.playOrderReversed;
        }
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("shuffleMusic", this.shuffleMusic);
        editor.putBoolean("playOrderReversed", this.playOrderReversed);
        editor.commit();
        if (oldShuffle == this.shuffleMusic) {
            return;
        }
        if (this.shuffleMusic) {
            buildShuffledPlayList();
            this.currentPlaylistNum = 0;
        } else if (this.playingMessageObject != null) {
            this.currentPlaylistNum = this.playlist.indexOf(this.playingMessageObject);
            if (this.currentPlaylistNum == -1) {
                this.playlist.clear();
                this.shuffledPlaylist.clear();
                cleanupPlayer(true, true);
            }
        }
    }

    public void toggleRepeatMode() {
        this.repeatMode++;
        if (this.repeatMode > 2) {
            this.repeatMode = 0;
        }
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putInt("repeatMode", this.repeatMode);
        editor.commit();
    }

    public boolean pauseMessage(MessageObject messageObject) {
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || messageObject == null || this.playingMessageObject == null) {
            return false;
        }
        if (this.playingMessageObject != null && this.playingMessageObject.getId() != messageObject.getId()) {
            return false;
        }
        stopProgressTimer();
        try {
            if (this.audioPlayer != null) {
                this.audioPlayer.pause();
            } else if (this.audioTrackPlayer != null) {
                this.audioTrackPlayer.pause();
            } else if (this.videoPlayer != null) {
                this.videoPlayer.pause();
            }
            this.isPaused = true;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            return true;
        } catch (Throwable e) {
            FileLog.m94e(e);
            this.isPaused = false;
            return false;
        }
    }

    public boolean resumeAudio(MessageObject messageObject) {
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || messageObject == null || this.playingMessageObject == null) {
            return false;
        }
        if (this.playingMessageObject != null && this.playingMessageObject.getId() != messageObject.getId()) {
            return false;
        }
        try {
            startProgressTimer(this.playingMessageObject);
            if (this.audioPlayer != null) {
                this.audioPlayer.start();
            } else if (this.audioTrackPlayer != null) {
                this.audioTrackPlayer.play();
                checkPlayerQueue();
            } else if (this.videoPlayer != null) {
                this.videoPlayer.play();
            }
            checkAudioFocus(messageObject);
            this.isPaused = false;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            return true;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return false;
        }
    }

    public boolean isRoundVideoDrawingReady() {
        return this.currentAspectRatioFrameLayout != null && this.currentAspectRatioFrameLayout.isDrawingReady();
    }

    public ArrayList<MessageObject> getPlaylist() {
        return this.playlist;
    }

    public boolean isPlayingMessage(MessageObject messageObject) {
        boolean z = true;
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || messageObject == null || this.playingMessageObject == null) {
            return false;
        }
        if (this.playingMessageObject.eventId != 0 && this.playingMessageObject.eventId == this.playingMessageObject.eventId) {
            if (this.downloadingCurrentMessage) {
                z = false;
            }
            return z;
        } else if (this.playingMessageObject.getDialogId() != messageObject.getDialogId() || this.playingMessageObject.getId() != messageObject.getId()) {
            return false;
        } else {
            if (this.downloadingCurrentMessage) {
                z = false;
            }
            return z;
        }
    }

    public boolean isMessagePaused() {
        return this.isPaused || this.downloadingCurrentMessage;
    }

    public boolean isDownloadingCurrentMessage() {
        return this.downloadingCurrentMessage;
    }

    public void startRecording(long dialog_id, MessageObject reply_to_msg) {
        long j;
        boolean paused = false;
        if (!(this.playingMessageObject == null || !isPlayingMessage(this.playingMessageObject) || isMessagePaused())) {
            paused = true;
            pauseMessage(this.playingMessageObject);
        }
        try {
            ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(10);
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        DispatchQueue dispatchQueue = this.recordQueue;
        Runnable mediaController$22 = new MediaController$22(this, dialog_id, reply_to_msg);
        this.recordStartRunnable = mediaController$22;
        if (paused) {
            j = 500;
        } else {
            j = 50;
        }
        dispatchQueue.postRunnable(mediaController$22, j);
    }

    public void generateWaveform(MessageObject messageObject) {
        String id = messageObject.getId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + messageObject.getDialogId();
        String path = FileLoader.getPathToMessage(messageObject.messageOwner).getAbsolutePath();
        if (!this.generatingWaveform.containsKey(id)) {
            this.generatingWaveform.put(id, messageObject);
            Utilities.globalQueue.postRunnable(new MediaController$23(this, path, id));
        }
    }

    private void stopRecordingInternal(int send) {
        if (send != 0) {
            this.fileEncodingQueue.postRunnable(new MediaController$24(this, this.recordingAudio, this.recordingAudioFile, send));
        }
        try {
            if (this.audioRecorder != null) {
                this.audioRecorder.release();
                this.audioRecorder = null;
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        this.recordingAudio = null;
        this.recordingAudioFile = null;
    }

    public void stopRecording(int send) {
        if (this.recordStartRunnable != null) {
            this.recordQueue.cancelRunnable(this.recordStartRunnable);
            this.recordStartRunnable = null;
        }
        this.recordQueue.postRunnable(new MediaController$25(this, send));
    }

    public static void saveFile(String fullPath, Context context, int type, String name, String mime) {
        Throwable e;
        if (fullPath != null) {
            File file = null;
            if (!(fullPath == null || fullPath.length() == 0)) {
                file = new File(fullPath);
                if (!file.exists()) {
                    file = null;
                }
            }
            if (file != null) {
                File sourceFile = file;
                boolean[] cancelled = new boolean[]{false};
                if (sourceFile.exists()) {
                    AlertDialog progressDialog = null;
                    if (!(context == null || type == 0)) {
                        try {
                            AlertDialog progressDialog2 = new AlertDialog(context, 2);
                            try {
                                progressDialog2.setMessage(LocaleController.getString("Loading", R.string.Loading));
                                progressDialog2.setCanceledOnTouchOutside(false);
                                progressDialog2.setCancelable(true);
                                progressDialog2.setOnCancelListener(new MediaController$26(cancelled));
                                progressDialog2.show();
                                progressDialog = progressDialog2;
                            } catch (Exception e2) {
                                e = e2;
                                progressDialog = progressDialog2;
                                FileLog.m94e(e);
                                new Thread(new MediaController$27(type, name, sourceFile, cancelled, progressDialog, mime)).start();
                            }
                        } catch (Exception e3) {
                            e = e3;
                            FileLog.m94e(e);
                            new Thread(new MediaController$27(type, name, sourceFile, cancelled, progressDialog, mime)).start();
                        }
                    }
                    new Thread(new MediaController$27(type, name, sourceFile, cancelled, progressDialog, mime)).start();
                }
            }
        }
    }

    public static boolean isWebp(Uri uri) {
        boolean z = false;
        InputStream inputStream = null;
        try {
            inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            byte[] header = new byte[12];
            if (inputStream.read(header, 0, 12) == 12) {
                String str = new String(header);
                if (str != null) {
                    str = str.toLowerCase();
                    if (str.startsWith("riff") && str.endsWith("webp")) {
                        z = true;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable e2) {
                                FileLog.m94e(e2);
                            }
                        }
                        return z;
                    }
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e22) {
                    FileLog.m94e(e22);
                }
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e222) {
                    FileLog.m94e(e222);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2222) {
                    FileLog.m94e(e2222);
                }
            }
        }
        return z;
    }

    public static boolean isGif(Uri uri) {
        boolean z = false;
        InputStream inputStream = null;
        try {
            inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            byte[] header = new byte[3];
            if (inputStream.read(header, 0, 3) == 3) {
                String str = new String(header);
                if (str != null && str.equalsIgnoreCase("gif")) {
                    z = true;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable e2) {
                            FileLog.m94e(e2);
                        }
                    }
                    return z;
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e22) {
                    FileLog.m94e(e22);
                }
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e222) {
                    FileLog.m94e(e222);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2222) {
                    FileLog.m94e(e2222);
                }
            }
        }
        return z;
    }

    public static String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals(Param.CONTENT)) {
            Cursor cursor = null;
            try {
                cursor = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
                if (cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex("_display_name"));
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result != null) {
            return result;
        }
        result = uri.getPath();
        int cut = result.lastIndexOf(47);
        if (cut != -1) {
            return result.substring(cut + 1);
        }
        return result;
    }

    public static String copyFileToCache(Uri uri, String ext) {
        Throwable e;
        Throwable th;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String name = getFileName(uri);
            if (name == null) {
                int id = UserConfig.lastLocalId;
                UserConfig.lastLocalId--;
                UserConfig.saveConfig(false);
                name = String.format(Locale.US, "%d.%s", new Object[]{Integer.valueOf(id), ext});
            }
            inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            File f = new File(FileLoader.getInstance().getDirectory(4), "sharing/");
            f.mkdirs();
            File f2 = new File(f, name);
            FileOutputStream output = new FileOutputStream(f2);
            try {
                byte[] buffer = new byte[CacheDataSink.DEFAULT_BUFFER_SIZE];
                while (true) {
                    int len = inputStream.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    output.write(buffer, 0, len);
                }
                String absolutePath = f2.getAbsolutePath();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable e2) {
                        FileLog.m94e(e2);
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (Throwable e22) {
                        FileLog.m94e(e22);
                    }
                }
                fileOutputStream = output;
                return absolutePath;
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = output;
                try {
                    FileLog.m94e(e);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable e222) {
                            FileLog.m94e(e222);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e2222) {
                            FileLog.m94e(e2222);
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable e22222) {
                            FileLog.m94e(e22222);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e222222) {
                            FileLog.m94e(e222222);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = output;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            FileLog.m94e(e);
            if (inputStream != null) {
                inputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return null;
        }
    }

    public void toggleSaveToGallery() {
        boolean z;
        if (this.saveToGallery) {
            z = false;
        } else {
            z = true;
        }
        this.saveToGallery = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("save_gallery", this.saveToGallery);
        editor.commit();
        checkSaveToGalleryFiles();
    }

    public void toggleAutoplayGifs() {
        boolean z;
        if (this.autoplayGifs) {
            z = false;
        } else {
            z = true;
        }
        this.autoplayGifs = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("autoplay_gif", this.autoplayGifs);
        editor.commit();
    }

    public void toogleRaiseToSpeak() {
        boolean z;
        if (this.raiseToSpeak) {
            z = false;
        } else {
            z = true;
        }
        this.raiseToSpeak = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("raise_to_speak", this.raiseToSpeak);
        editor.commit();
    }

    public void toggleCustomTabs() {
        boolean z;
        if (this.customTabs) {
            z = false;
        } else {
            z = true;
        }
        this.customTabs = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("custom_tabs", this.customTabs);
        editor.commit();
    }

    public void toggleDirectShare() {
        boolean z;
        if (this.directShare) {
            z = false;
        } else {
            z = true;
        }
        this.directShare = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("direct_share", this.directShare);
        editor.commit();
    }

    public void toggleInappCamera() {
        boolean z;
        if (this.inappCamera) {
            z = false;
        } else {
            z = true;
        }
        this.inappCamera = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("direct_share", this.inappCamera);
        editor.commit();
    }

    public void toggleRoundCamera16to9() {
        boolean z;
        if (this.roundCamera16to9) {
            z = false;
        } else {
            z = true;
        }
        this.roundCamera16to9 = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("roundCamera16to9", this.roundCamera16to9);
        editor.commit();
    }

    public void toggleGroupPhotosEnabled() {
        boolean z;
        if (this.groupPhotosEnabled) {
            z = false;
        } else {
            z = true;
        }
        this.groupPhotosEnabled = z;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("groupPhotosEnabled", this.groupPhotosEnabled);
        editor.commit();
    }

    public void checkSaveToGalleryFiles() {
        try {
            File telegramPath = new File(Environment.getExternalStorageDirectory(), "Telegram");
            File imagePath = new File(telegramPath, "Telegram Images");
            imagePath.mkdir();
            File videoPath = new File(telegramPath, "Telegram Video");
            videoPath.mkdir();
            if (this.saveToGallery) {
                if (imagePath.isDirectory()) {
                    new File(imagePath, ".nomedia").delete();
                }
                if (videoPath.isDirectory()) {
                    new File(videoPath, ".nomedia").delete();
                    return;
                }
                return;
            }
            if (imagePath.isDirectory()) {
                new File(imagePath, ".nomedia").createNewFile();
            }
            if (videoPath.isDirectory()) {
                new File(videoPath, ".nomedia").createNewFile();
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public boolean canSaveToGallery() {
        return this.saveToGallery;
    }

    public boolean canAutoplayGifs() {
        return this.autoplayGifs;
    }

    public boolean canRaiseToSpeak() {
        return this.raiseToSpeak;
    }

    public boolean canCustomTabs() {
        return this.customTabs;
    }

    public boolean canDirectShare() {
        return this.directShare;
    }

    public boolean canInAppCamera() {
        return this.inappCamera;
    }

    public boolean canRoundCamera16to9() {
        return this.roundCamera16to9;
    }

    public boolean isGroupPhotosEnabled() {
        return this.groupPhotosEnabled;
    }

    public static void loadGalleryPhotosAlbums(int guid) {
        Thread thread = new Thread(new MediaController$28(guid));
        thread.setPriority(1);
        thread.start();
    }

    private static void broadcastNewPhotos(int guid, ArrayList<MediaController$AlbumEntry> mediaAlbumsSorted, ArrayList<MediaController$AlbumEntry> photoAlbumsSorted, Integer cameraAlbumIdFinal, MediaController$AlbumEntry allMediaAlbumFinal, MediaController$AlbumEntry allPhotosAlbumFinal, int delay) {
        if (broadcastPhotosRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(broadcastPhotosRunnable);
        }
        Runnable mediaController$29 = new MediaController$29(guid, mediaAlbumsSorted, photoAlbumsSorted, cameraAlbumIdFinal, allMediaAlbumFinal, allPhotosAlbumFinal);
        broadcastPhotosRunnable = mediaController$29;
        AndroidUtilities.runOnUIThread(mediaController$29, (long) delay);
    }

    public void scheduleVideoConvert(MessageObject messageObject) {
        scheduleVideoConvert(messageObject, false);
    }

    public boolean scheduleVideoConvert(MessageObject messageObject, boolean isEmpty) {
        if (isEmpty && !this.videoConvertQueue.isEmpty()) {
            return false;
        }
        if (isEmpty) {
            new File(messageObject.messageOwner.attachPath).delete();
        }
        this.videoConvertQueue.add(messageObject);
        if (this.videoConvertQueue.size() != 1) {
            return true;
        }
        startVideoConvertFromQueue();
        return true;
    }

    public void cancelVideoConvert(MessageObject messageObject) {
        if (messageObject == null) {
            synchronized (this.videoConvertSync) {
                this.cancelCurrentVideoConversion = true;
            }
        } else if (!this.videoConvertQueue.isEmpty()) {
            if (this.videoConvertQueue.get(0) == messageObject) {
                synchronized (this.videoConvertSync) {
                    this.cancelCurrentVideoConversion = true;
                }
                return;
            }
            this.videoConvertQueue.remove(messageObject);
        }
    }

    private boolean startVideoConvertFromQueue() {
        if (this.videoConvertQueue.isEmpty()) {
            return false;
        }
        synchronized (this.videoConvertSync) {
            this.cancelCurrentVideoConversion = false;
        }
        MessageObject messageObject = (MessageObject) this.videoConvertQueue.get(0);
        Intent intent = new Intent(ApplicationLoader.applicationContext, VideoEncodingService.class);
        intent.putExtra("path", messageObject.messageOwner.attachPath);
        if (messageObject.messageOwner.media.document != null) {
            for (int a = 0; a < messageObject.messageOwner.media.document.attributes.size(); a++) {
                if (((DocumentAttribute) messageObject.messageOwner.media.document.attributes.get(a)) instanceof TLRPC$TL_documentAttributeAnimated) {
                    intent.putExtra("gif", true);
                    break;
                }
            }
        }
        if (messageObject.getId() != 0) {
            ApplicationLoader.applicationContext.startService(intent);
        }
        MediaController$VideoConvertRunnable.runConversion(messageObject);
        return true;
    }

    @SuppressLint({"NewApi"})
    public static MediaCodecInfo selectCodec(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        MediaCodecInfo lastCodecInfo = null;
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (codecInfo.isEncoder()) {
                for (String type : codecInfo.getSupportedTypes()) {
                    if (type.equalsIgnoreCase(mimeType)) {
                        lastCodecInfo = codecInfo;
                        if (!lastCodecInfo.getName().equals("OMX.SEC.avc.enc")) {
                            return lastCodecInfo;
                        }
                        if (lastCodecInfo.getName().equals("OMX.SEC.AVC.Encoder")) {
                            return lastCodecInfo;
                        }
                    }
                }
                continue;
            }
        }
        return lastCodecInfo;
    }

    private static boolean isRecognizedFormat(int colorFormat) {
        switch (colorFormat) {
            case 19:
            case 20:
            case 21:
            case 39:
            case 2130706688:
                return true;
            default:
                return false;
        }
    }

    @SuppressLint({"NewApi"})
    public static int selectColorFormat(MediaCodecInfo codecInfo, String mimeType) {
        CodecCapabilities capabilities = codecInfo.getCapabilitiesForType(mimeType);
        int lastColorFormat = 0;
        for (int colorFormat : capabilities.colorFormats) {
            if (isRecognizedFormat(colorFormat)) {
                lastColorFormat = colorFormat;
                if (!codecInfo.getName().equals("OMX.SEC.AVC.Encoder") || colorFormat != 19) {
                    return colorFormat;
                }
            }
        }
        return lastColorFormat;
    }

    private int selectTrack(MediaExtractor extractor, boolean audio) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            String mime = extractor.getTrackFormat(i).getString("mime");
            if (audio) {
                if (mime.startsWith("audio/")) {
                    return i;
                }
            } else if (mime.startsWith("video/")) {
                return i;
            }
        }
        return -5;
    }

    private void didWriteData(MessageObject messageObject, File file, boolean last, boolean error) {
        boolean firstWrite = this.videoConvertFirstWrite;
        if (firstWrite) {
            this.videoConvertFirstWrite = false;
        }
        AndroidUtilities.runOnUIThread(new MediaController$30(this, error, last, messageObject, file, firstWrite));
    }

    private long readAndWriteTrack(MessageObject messageObject, MediaExtractor extractor, MP4Builder mediaMuxer, BufferInfo info, long start, long end, File file, boolean isAudio) throws Exception {
        int trackIndex = selectTrack(extractor, isAudio);
        if (trackIndex < 0) {
            return -1;
        }
        extractor.selectTrack(trackIndex);
        MediaFormat trackFormat = extractor.getTrackFormat(trackIndex);
        int muxerTrackIndex = mediaMuxer.addTrack(trackFormat, isAudio);
        int maxBufferSize = trackFormat.getInteger("max-input-size");
        boolean inputDone = false;
        if (start > 0) {
            extractor.seekTo(start, 0);
        } else {
            extractor.seekTo(0, 0);
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(maxBufferSize);
        long startTime = -1;
        checkConversionCanceled();
        while (!inputDone) {
            checkConversionCanceled();
            boolean eof = false;
            int index = extractor.getSampleTrackIndex();
            if (index == trackIndex) {
                info.size = extractor.readSampleData(buffer, 0);
                if (VERSION.SDK_INT < 21) {
                    buffer.position(0);
                    buffer.limit(info.size);
                }
                if (!isAudio) {
                    byte[] array = buffer.array();
                    if (array != null) {
                        int offset = buffer.arrayOffset();
                        int len = offset + buffer.limit();
                        int writeStart = -1;
                        int a = offset;
                        while (a <= len - 4) {
                            if ((array[a] == (byte) 0 && array[a + 1] == (byte) 0 && array[a + 2] == (byte) 0 && array[a + 3] == (byte) 1) || a == len - 4) {
                                if (writeStart != -1) {
                                    int l = (a - writeStart) - (a != len + -4 ? 4 : 0);
                                    array[writeStart] = (byte) (l >> 24);
                                    array[writeStart + 1] = (byte) (l >> 16);
                                    array[writeStart + 2] = (byte) (l >> 8);
                                    array[writeStart + 3] = (byte) l;
                                    writeStart = a;
                                } else {
                                    writeStart = a;
                                }
                            }
                            a++;
                        }
                    }
                }
                if (info.size >= 0) {
                    info.presentationTimeUs = extractor.getSampleTime();
                } else {
                    info.size = 0;
                    eof = true;
                }
                if (info.size > 0 && !eof) {
                    if (start > 0 && startTime == -1) {
                        startTime = info.presentationTimeUs;
                    }
                    if (end < 0 || info.presentationTimeUs < end) {
                        info.offset = 0;
                        info.flags = extractor.getSampleFlags();
                        if (mediaMuxer.writeSampleData(muxerTrackIndex, buffer, info, false)) {
                            didWriteData(messageObject, file, false, false);
                        }
                    } else {
                        eof = true;
                    }
                }
                if (!eof) {
                    extractor.advance();
                }
            } else if (index == -1) {
                eof = true;
            } else {
                extractor.advance();
            }
            if (eof) {
                inputDone = true;
            }
        }
        extractor.unselectTrack(trackIndex);
        return startTime;
    }

    private void checkConversionCanceled() throws Exception {
        synchronized (this.videoConvertSync) {
            boolean cancelConversion = this.cancelCurrentVideoConversion;
        }
        if (cancelConversion) {
            throw new RuntimeException("canceled conversion");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean convertVideo(org.telegram.messenger.MessageObject r91) {
        /*
        r90 = this;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.originalPath;
        r84 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.startTime;
        r76 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.endTime;
        r18 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.resultWidth;
        r72 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.resultHeight;
        r70 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.rotationValue;
        r74 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.originalWidth;
        r61 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.originalHeight;
        r60 = r0;
        r0 = r91;
        r6 = r0.videoEditedInfo;
        r0 = r6.bitrate;
        r24 = r0;
        r73 = 0;
        r20 = new java.io.File;
        r0 = r91;
        r6 = r0.messageOwner;
        r6 = r6.attachPath;
        r0 = r20;
        r0.<init>(r6);
        r6 = android.os.Build.VERSION.SDK_INT;
        r10 = 18;
        if (r6 >= r10) goto L_0x00cf;
    L_0x005d:
        r0 = r70;
        r1 = r72;
        if (r0 <= r1) goto L_0x00cf;
    L_0x0063:
        r0 = r72;
        r1 = r61;
        if (r0 == r1) goto L_0x00cf;
    L_0x0069:
        r0 = r70;
        r1 = r60;
        if (r0 == r1) goto L_0x00cf;
    L_0x006f:
        r79 = r70;
        r70 = r72;
        r72 = r79;
        r74 = 90;
        r73 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
    L_0x0079:
        r6 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r10 = "videoconvert";
        r11 = 0;
        r68 = r6.getSharedPreferences(r10, r11);
        r51 = new java.io.File;
        r0 = r51;
        r1 = r84;
        r0.<init>(r1);
        r6 = r91.getId();
        if (r6 == 0) goto L_0x0103;
    L_0x0092:
        r6 = "isPreviousOk";
        r10 = 1;
        r0 = r68;
        r55 = r0.getBoolean(r6, r10);
        r6 = r68.edit();
        r10 = "isPreviousOk";
        r11 = 0;
        r6 = r6.putBoolean(r10, r11);
        r6.commit();
        r6 = r51.canRead();
        if (r6 == 0) goto L_0x00b3;
    L_0x00b1:
        if (r55 != 0) goto L_0x0103;
    L_0x00b3:
        r6 = 1;
        r10 = 1;
        r0 = r90;
        r1 = r91;
        r2 = r20;
        r0.didWriteData(r1, r2, r6, r10);
        r6 = r68.edit();
        r10 = "isPreviousOk";
        r11 = 1;
        r6 = r6.putBoolean(r10, r11);
        r6.commit();
        r6 = 0;
    L_0x00ce:
        return r6;
    L_0x00cf:
        r6 = android.os.Build.VERSION.SDK_INT;
        r10 = 20;
        if (r6 <= r10) goto L_0x0079;
    L_0x00d5:
        r6 = 90;
        r0 = r74;
        if (r0 != r6) goto L_0x00e6;
    L_0x00db:
        r79 = r70;
        r70 = r72;
        r72 = r79;
        r74 = 0;
        r73 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        goto L_0x0079;
    L_0x00e6:
        r6 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r0 = r74;
        if (r0 != r6) goto L_0x00f1;
    L_0x00ec:
        r73 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r74 = 0;
        goto L_0x0079;
    L_0x00f1:
        r6 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        r0 = r74;
        if (r0 != r6) goto L_0x0079;
    L_0x00f7:
        r79 = r70;
        r70 = r72;
        r72 = r79;
        r74 = 0;
        r73 = 90;
        goto L_0x0079;
    L_0x0103:
        r6 = 1;
        r0 = r90;
        r0.videoConvertFirstWrite = r6;
        r43 = 0;
        r86 = r76;
        r80 = java.lang.System.currentTimeMillis();
        if (r72 == 0) goto L_0x088c;
    L_0x0112:
        if (r70 == 0) goto L_0x088c;
    L_0x0114:
        r57 = 0;
        r45 = 0;
        r48 = new android.media.MediaCodec$BufferInfo;	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r48.<init>();	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r58 = new org.telegram.messenger.video.Mp4Movie;	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r58.<init>();	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r0 = r58;
        r1 = r20;
        r0.setCacheFile(r1);	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r0 = r58;
        r1 = r74;
        r0.setRotation(r1);	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r0 = r58;
        r1 = r72;
        r2 = r70;
        r0.setSize(r1, r2);	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r6 = new org.telegram.messenger.video.MP4Builder;	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r6.<init>();	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r0 = r58;
        r57 = r6.createMovie(r0);	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r46 = new android.media.MediaExtractor;	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r46.<init>();	 Catch:{ Exception -> 0x0850, all -> 0x08a9 }
        r0 = r46;
        r1 = r84;
        r0.setDataSource(r1);	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        r90.checkConversionCanceled();	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        r0 = r72;
        r1 = r61;
        if (r0 != r1) goto L_0x0169;
    L_0x0159:
        r0 = r70;
        r1 = r60;
        if (r0 != r1) goto L_0x0169;
    L_0x015f:
        if (r73 != 0) goto L_0x0169;
    L_0x0161:
        r0 = r91;
        r6 = r0.videoEditedInfo;	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        r6 = r6.roundVideo;	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        if (r6 == 0) goto L_0x082e;
    L_0x0169:
        r6 = 0;
        r0 = r90;
        r1 = r46;
        r83 = r0.selectTrack(r1, r6);	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        if (r83 < 0) goto L_0x08c5;
    L_0x0174:
        r4 = 0;
        r37 = 0;
        r53 = 0;
        r64 = 0;
        r88 = -1;
        r62 = 0;
        r50 = 0;
        r30 = 0;
        r78 = 0;
        r85 = -5;
        r69 = 0;
        r6 = android.os.Build.MANUFACTURER;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r56 = r6.toLowerCase();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 >= r10) goto L_0x0478;
    L_0x0195:
        r6 = "video/avc";
        r26 = selectCodec(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "video/avc";
        r0 = r26;
        r28 = selectColorFormat(r0, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r28 != 0) goto L_0x0239;
    L_0x01a7:
        r6 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "no supported color format";
        r6.<init>(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        throw r6;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x01b0:
        r35 = move-exception;
    L_0x01b1:
        org.telegram.messenger.FileLog.m94e(r35);	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        r43 = 1;
        r16 = r86;
    L_0x01b8:
        r0 = r46;
        r1 = r83;
        r0.unselectTrack(r1);	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
        if (r64 == 0) goto L_0x01c4;
    L_0x01c1:
        r64.release();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01c4:
        if (r53 == 0) goto L_0x01c9;
    L_0x01c6:
        r53.release();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01c9:
        if (r4 == 0) goto L_0x01d1;
    L_0x01cb:
        r4.stop();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
        r4.release();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01d1:
        if (r37 == 0) goto L_0x01d9;
    L_0x01d3:
        r37.stop();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
        r37.release();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01d9:
        r90.checkConversionCanceled();	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01dc:
        if (r43 != 0) goto L_0x01f2;
    L_0x01de:
        r6 = -1;
        r0 = r24;
        if (r0 == r6) goto L_0x01f2;
    L_0x01e3:
        r21 = 1;
        r11 = r90;
        r12 = r91;
        r13 = r46;
        r14 = r57;
        r15 = r48;
        r11.readAndWriteTrack(r12, r13, r14, r15, r16, r18, r20, r21);	 Catch:{ Exception -> 0x08bc, all -> 0x08ae }
    L_0x01f2:
        if (r46 == 0) goto L_0x01f7;
    L_0x01f4:
        r46.release();
    L_0x01f7:
        if (r57 == 0) goto L_0x01fc;
    L_0x01f9:
        r57.finishMovie();	 Catch:{ Exception -> 0x084a }
    L_0x01fc:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r10 = "time = ";
        r6 = r6.append(r10);
        r10 = java.lang.System.currentTimeMillis();
        r10 = r10 - r80;
        r6 = r6.append(r10);
        r6 = r6.toString();
        org.telegram.messenger.FileLog.m92e(r6);
        r45 = r46;
    L_0x021b:
        r6 = r68.edit();
        r10 = "isPreviousOk";
        r11 = 1;
        r6 = r6.putBoolean(r10, r11);
        r6.commit();
        r6 = 1;
        r0 = r90;
        r1 = r91;
        r2 = r20;
        r3 = r43;
        r0.didWriteData(r1, r2, r6, r3);
        r6 = 1;
        goto L_0x00ce;
    L_0x0239:
        r27 = r26.getName();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "OMX.qcom.";
        r0 = r27;
        r6 = r0.contains(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x043a;
    L_0x0248:
        r69 = 1;
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 16;
        if (r6 != r10) goto L_0x0268;
    L_0x0250:
        r6 = "lge";
        r0 = r56;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x0266;
    L_0x025b:
        r6 = "nokia";
        r0 = r56;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x0268;
    L_0x0266:
        r78 = 1;
    L_0x0268:
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "codec = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r26.getName();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = " manufacturer = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r56;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "device = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = android.os.Build.MODEL;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x029d:
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "colorFormat = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r28;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r71 = r70;
        r66 = 0;
        r6 = r72 * r70;
        r6 = r6 * 3;
        r25 = r6 / 2;
        if (r69 != 0) goto L_0x047d;
    L_0x02c2:
        r6 = r70 % 16;
        if (r6 == 0) goto L_0x02d6;
    L_0x02c6:
        r6 = r70 % 16;
        r6 = 16 - r6;
        r71 = r71 + r6;
        r6 = r71 - r70;
        r66 = r72 * r6;
        r6 = r66 * 5;
        r6 = r6 / 4;
        r25 = r25 + r6;
    L_0x02d6:
        r0 = r46;
        r1 = r83;
        r0.selectTrack(r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 0;
        r6 = (r76 > r10 ? 1 : (r76 == r10 ? 0 : -1));
        if (r6 <= 0) goto L_0x04c6;
    L_0x02e3:
        r6 = 0;
        r0 = r46;
        r1 = r76;
        r0.seekTo(r1, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x02eb:
        r0 = r46;
        r1 = r83;
        r52 = r0.getTrackFormat(r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "video/avc";
        r0 = r72;
        r1 = r70;
        r63 = android.media.MediaFormat.createVideoFormat(r6, r0, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "color-format";
        r0 = r63;
        r1 = r28;
        r0.setInteger(r6, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "bitrate";
        if (r24 <= 0) goto L_0x04fd;
    L_0x030d:
        r6 = r24;
    L_0x030f:
        r0 = r63;
        r0.setInteger(r10, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "frame-rate";
        r10 = 25;
        r0 = r63;
        r0.setInteger(r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "i-frame-interval";
        r10 = 10;
        r0 = r63;
        r0.setInteger(r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 >= r10) goto L_0x0342;
    L_0x032e:
        r6 = "stride";
        r10 = r72 + 32;
        r0 = r63;
        r0.setInteger(r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "slice-height";
        r0 = r63;
        r1 = r70;
        r0.setInteger(r6, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x0342:
        r6 = "video/avc";
        r37 = android.media.MediaCodec.createEncoderByType(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = 0;
        r10 = 0;
        r11 = 1;
        r0 = r37;
        r1 = r63;
        r0.configure(r1, r6, r10, r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 < r10) goto L_0x0369;
    L_0x0359:
        r54 = new org.telegram.messenger.video.InputSurface;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r37.createInputSurface();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r54;
        r0.<init>(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r54.makeCurrent();	 Catch:{ Exception -> 0x08c0, all -> 0x04d0 }
        r53 = r54;
    L_0x0369:
        r37.start();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "mime";
        r0 = r52;
        r6 = r0.getString(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r4 = android.media.MediaCodec.createDecoderByType(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 < r10) goto L_0x0502;
    L_0x037f:
        r65 = new org.telegram.messenger.video.OutputSurface;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r65.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r64 = r65;
    L_0x0386:
        r6 = r64.getSurface();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 0;
        r11 = 0;
        r0 = r52;
        r4.configure(r0, r6, r10, r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r4.start();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r22 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r31 = 0;
        r40 = 0;
        r38 = 0;
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 21;
        if (r6 >= r10) goto L_0x03b4;
    L_0x03a2:
        r31 = r4.getInputBuffers();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r40 = r37.getOutputBuffers();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 >= r10) goto L_0x03b4;
    L_0x03b0:
        r38 = r37.getInputBuffers();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x03b4:
        r90.checkConversionCanceled();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x03b7:
        if (r62 != 0) goto L_0x0824;
    L_0x03b9:
        r90.checkConversionCanceled();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r50 != 0) goto L_0x0405;
    L_0x03be:
        r42 = 0;
        r47 = r46.getSampleTrackIndex();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r47;
        r1 = r83;
        if (r0 != r1) goto L_0x0527;
    L_0x03ca:
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r5 = r4.dequeueInputBuffer(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r5 < 0) goto L_0x03ef;
    L_0x03d2:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 21;
        if (r6 >= r10) goto L_0x0513;
    L_0x03d8:
        r49 = r31[r5];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x03da:
        r6 = 0;
        r0 = r46;
        r1 = r49;
        r7 = r0.readSampleData(r1, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r7 >= 0) goto L_0x0519;
    L_0x03e5:
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r10 = 4;
        r4.queueInputBuffer(r5, r6, r7, r8, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r50 = 1;
    L_0x03ef:
        if (r42 == 0) goto L_0x0405;
    L_0x03f1:
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r5 = r4.dequeueInputBuffer(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r5 < 0) goto L_0x0405;
    L_0x03f9:
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r14 = 4;
        r8 = r4;
        r9 = r5;
        r8.queueInputBuffer(r9, r10, r11, r12, r14);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r50 = 1;
    L_0x0405:
        if (r30 != 0) goto L_0x0530;
    L_0x0407:
        r32 = 1;
    L_0x0409:
        r39 = 1;
    L_0x040b:
        if (r32 != 0) goto L_0x040f;
    L_0x040d:
        if (r39 == 0) goto L_0x03b7;
    L_0x040f:
        r90.checkConversionCanceled();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r37;
        r1 = r48;
        r41 = r0.dequeueOutputBuffer(r1, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = -1;
        r0 = r41;
        if (r0 != r6) goto L_0x0534;
    L_0x0421:
        r39 = 0;
    L_0x0423:
        r6 = -1;
        r0 = r41;
        if (r0 != r6) goto L_0x040b;
    L_0x0428:
        if (r30 != 0) goto L_0x040b;
    L_0x042a:
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r48;
        r33 = r4.dequeueOutputBuffer(r0, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = -1;
        r0 = r33;
        if (r0 != r6) goto L_0x06ad;
    L_0x0437:
        r32 = 0;
        goto L_0x040b;
    L_0x043a:
        r6 = "OMX.Intel.";
        r0 = r27;
        r6 = r0.contains(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x0449;
    L_0x0445:
        r69 = 2;
        goto L_0x0268;
    L_0x0449:
        r6 = "OMX.MTK.VIDEO.ENCODER.AVC";
        r0 = r27;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x0458;
    L_0x0454:
        r69 = 3;
        goto L_0x0268;
    L_0x0458:
        r6 = "OMX.SEC.AVC.Encoder";
        r0 = r27;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x0469;
    L_0x0463:
        r69 = 4;
        r78 = 1;
        goto L_0x0268;
    L_0x0469:
        r6 = "OMX.TI.DUCATI1.VIDEO.H264E";
        r0 = r27;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x0268;
    L_0x0474:
        r69 = 5;
        goto L_0x0268;
    L_0x0478:
        r28 = 2130708361; // 0x7f000789 float:1.701803E38 double:1.0527098025E-314;
        goto L_0x029d;
    L_0x047d:
        r6 = 1;
        r0 = r69;
        if (r0 != r6) goto L_0x049f;
    L_0x0482:
        r6 = r56.toLowerCase();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "lge";
        r6 = r6.equals(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x02d6;
    L_0x048f:
        r6 = r72 * r70;
        r6 = r6 + 2047;
        r0 = r6 & -2048;
        r82 = r0;
        r6 = r72 * r70;
        r66 = r82 - r6;
        r25 = r25 + r66;
        goto L_0x02d6;
    L_0x049f:
        r6 = 5;
        r0 = r69;
        if (r0 == r6) goto L_0x02d6;
    L_0x04a4:
        r6 = 3;
        r0 = r69;
        if (r0 != r6) goto L_0x02d6;
    L_0x04a9:
        r6 = "baidu";
        r0 = r56;
        r6 = r0.equals(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x02d6;
    L_0x04b4:
        r6 = r70 % 16;
        r6 = 16 - r6;
        r71 = r71 + r6;
        r6 = r71 - r70;
        r66 = r72 * r6;
        r6 = r66 * 5;
        r6 = r6 / 4;
        r25 = r25 + r6;
        goto L_0x02d6;
    L_0x04c6:
        r10 = 0;
        r6 = 0;
        r0 = r46;
        r0.seekTo(r10, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x02eb;
    L_0x04d0:
        r6 = move-exception;
        r45 = r46;
        r16 = r86;
    L_0x04d5:
        if (r45 == 0) goto L_0x04da;
    L_0x04d7:
        r45.release();
    L_0x04da:
        if (r57 == 0) goto L_0x04df;
    L_0x04dc:
        r57.finishMovie();	 Catch:{ Exception -> 0x0886 }
    L_0x04df:
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "time = ";
        r10 = r10.append(r11);
        r12 = java.lang.System.currentTimeMillis();
        r12 = r12 - r80;
        r10 = r10.append(r12);
        r10 = r10.toString();
        org.telegram.messenger.FileLog.m92e(r10);
        throw r6;
    L_0x04fd:
        r6 = 921600; // 0xe1000 float:1.291437E-39 double:4.55331E-318;
        goto L_0x030f;
    L_0x0502:
        r65 = new org.telegram.messenger.video.OutputSurface;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r65;
        r1 = r72;
        r2 = r70;
        r3 = r73;
        r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r64 = r65;
        goto L_0x0386;
    L_0x0513:
        r49 = r4.getInputBuffer(r5);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x03da;
    L_0x0519:
        r6 = 0;
        r8 = r46.getSampleTime();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 0;
        r4.queueInputBuffer(r5, r6, r7, r8, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r46.advance();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x03ef;
    L_0x0527:
        r6 = -1;
        r0 = r47;
        if (r0 != r6) goto L_0x03ef;
    L_0x052c:
        r42 = 1;
        goto L_0x03ef;
    L_0x0530:
        r32 = 0;
        goto L_0x0409;
    L_0x0534:
        r6 = -3;
        r0 = r41;
        if (r0 != r6) goto L_0x0545;
    L_0x0539:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 21;
        if (r6 >= r10) goto L_0x0423;
    L_0x053f:
        r40 = r37.getOutputBuffers();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0423;
    L_0x0545:
        r6 = -2;
        r0 = r41;
        if (r0 != r6) goto L_0x055e;
    L_0x054a:
        r59 = r37.getOutputFormat();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = -5;
        r0 = r85;
        if (r0 != r6) goto L_0x0423;
    L_0x0553:
        r6 = 0;
        r0 = r57;
        r1 = r59;
        r85 = r0.addTrack(r1, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0423;
    L_0x055e:
        if (r41 >= 0) goto L_0x057c;
    L_0x0560:
        r6 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r11 = "unexpected result from encoder.dequeueOutputBuffer: ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r41;
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        throw r6;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x057c:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 21;
        if (r6 >= r10) goto L_0x05a9;
    L_0x0582:
        r36 = r40[r41];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x0584:
        if (r36 != 0) goto L_0x05b2;
    L_0x0586:
        r6 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r11 = "encoderOutputBuffer ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r41;
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r11 = " was null";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        throw r6;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x05a9:
        r0 = r37;
        r1 = r41;
        r36 = r0.getOutputBuffer(r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0584;
    L_0x05b2:
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 1;
        if (r6 <= r10) goto L_0x05db;
    L_0x05b9:
        r0 = r48;
        r6 = r0.flags;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6 & 2;
        if (r6 != 0) goto L_0x05ef;
    L_0x05c1:
        r6 = 1;
        r0 = r57;
        r1 = r85;
        r2 = r36;
        r3 = r48;
        r6 = r0.writeSampleData(r1, r2, r3, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x05db;
    L_0x05d0:
        r6 = 0;
        r10 = 0;
        r0 = r90;
        r1 = r91;
        r2 = r20;
        r0.didWriteData(r1, r2, r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x05db:
        r0 = r48;
        r6 = r0.flags;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6 & 4;
        if (r6 == 0) goto L_0x06a9;
    L_0x05e3:
        r62 = 1;
    L_0x05e5:
        r6 = 0;
        r0 = r37;
        r1 = r41;
        r0.releaseOutputBuffer(r1, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0423;
    L_0x05ef:
        r6 = -5;
        r0 = r85;
        if (r0 != r6) goto L_0x05db;
    L_0x05f4:
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = new byte[r6];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r29 = r0;
        r0 = r48;
        r6 = r0.offset;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r48;
        r10 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6 + r10;
        r0 = r36;
        r0.limit(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r48;
        r6 = r0.offset;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r36;
        r0.position(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r36;
        r1 = r29;
        r0.get(r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r75 = 0;
        r67 = 0;
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r23 = r6 + -1;
    L_0x0624:
        if (r23 < 0) goto L_0x0677;
    L_0x0626:
        r6 = 3;
        r0 = r23;
        if (r0 <= r6) goto L_0x0677;
    L_0x062b:
        r6 = r29[r23];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 1;
        if (r6 != r10) goto L_0x06a5;
    L_0x0630:
        r6 = r23 + -1;
        r6 = r29[r6];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x06a5;
    L_0x0636:
        r6 = r23 + -2;
        r6 = r29[r6];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x06a5;
    L_0x063c:
        r6 = r23 + -3;
        r6 = r29[r6];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x06a5;
    L_0x0642:
        r6 = r23 + -3;
        r75 = java.nio.ByteBuffer.allocate(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r23 + -3;
        r6 = r6 - r10;
        r67 = java.nio.ByteBuffer.allocate(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = 0;
        r10 = r23 + -3;
        r0 = r75;
        r1 = r29;
        r6 = r0.put(r1, r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 0;
        r6.position(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r23 + -3;
        r0 = r48;
        r10 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r11 = r23 + -3;
        r10 = r10 - r11;
        r0 = r67;
        r1 = r29;
        r6 = r0.put(r1, r6, r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 0;
        r6.position(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x0677:
        r6 = "video/avc";
        r0 = r72;
        r1 = r70;
        r59 = android.media.MediaFormat.createVideoFormat(r6, r0, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r75 == 0) goto L_0x069a;
    L_0x0684:
        if (r67 == 0) goto L_0x069a;
    L_0x0686:
        r6 = "csd-0";
        r0 = r59;
        r1 = r75;
        r0.setByteBuffer(r6, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = "csd-1";
        r0 = r59;
        r1 = r67;
        r0.setByteBuffer(r6, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x069a:
        r6 = 0;
        r0 = r57;
        r1 = r59;
        r85 = r0.addTrack(r1, r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x05db;
    L_0x06a5:
        r23 = r23 + -1;
        goto L_0x0624;
    L_0x06a9:
        r62 = 0;
        goto L_0x05e5;
    L_0x06ad:
        r6 = -3;
        r0 = r33;
        if (r0 == r6) goto L_0x040b;
    L_0x06b2:
        r6 = -2;
        r0 = r33;
        if (r0 != r6) goto L_0x06d6;
    L_0x06b7:
        r59 = r4.getOutputFormat();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "newFormat = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r59;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x040b;
    L_0x06d6:
        if (r33 >= 0) goto L_0x06f4;
    L_0x06d8:
        r6 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r11 = "unexpected result from decoder.dequeueOutputBuffer: ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r33;
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        throw r6;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x06f4:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 < r10) goto L_0x07a8;
    L_0x06fa:
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 == 0) goto L_0x07a4;
    L_0x0700:
        r34 = 1;
    L_0x0702:
        r10 = 0;
        r6 = (r18 > r10 ? 1 : (r18 == r10 ? 0 : -1));
        if (r6 <= 0) goto L_0x0720;
    L_0x0708:
        r0 = r48;
        r10 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1));
        if (r6 < 0) goto L_0x0720;
    L_0x0710:
        r50 = 1;
        r30 = 1;
        r34 = 0;
        r0 = r48;
        r6 = r0.flags;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6 | 4;
        r0 = r48;
        r0.flags = r6;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x0720:
        r10 = 0;
        r6 = (r76 > r10 ? 1 : (r76 == r10 ? 0 : -1));
        if (r6 <= 0) goto L_0x075e;
    L_0x0726:
        r10 = -1;
        r6 = (r88 > r10 ? 1 : (r88 == r10 ? 0 : -1));
        if (r6 != 0) goto L_0x075e;
    L_0x072c:
        r0 = r48;
        r10 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = (r10 > r76 ? 1 : (r10 == r76 ? 0 : -1));
        if (r6 >= 0) goto L_0x07bf;
    L_0x0734:
        r34 = 0;
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6.<init>();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = "drop frame startTime = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r76;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = " present time = ";
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r48;
        r10 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x075e:
        r0 = r33;
        r1 = r34;
        r4.releaseOutputBuffer(r0, r1);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r34 == 0) goto L_0x0789;
    L_0x0767:
        r44 = 0;
        r64.awaitNewImage();	 Catch:{ Exception -> 0x07c6, all -> 0x04d0 }
    L_0x076c:
        if (r44 != 0) goto L_0x0789;
    L_0x076e:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 < r10) goto L_0x07cd;
    L_0x0774:
        r6 = 0;
        r0 = r64;
        r0.drawImage(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r0 = r48;
        r10 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r12 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r10 = r10 * r12;
        r0 = r53;
        r0.setPresentationTime(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r53.swapBuffers();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
    L_0x0789:
        r0 = r48;
        r6 = r0.flags;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = r6 & 4;
        if (r6 == 0) goto L_0x040b;
    L_0x0791:
        r32 = 0;
        r6 = "decoder stream end";
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = 18;
        if (r6 < r10) goto L_0x080a;
    L_0x079f:
        r37.signalEndOfInputStream();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x040b;
    L_0x07a4:
        r34 = 0;
        goto L_0x0702;
    L_0x07a8:
        r0 = r48;
        r6 = r0.size;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r6 != 0) goto L_0x07b8;
    L_0x07ae:
        r0 = r48;
        r10 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r12 = 0;
        r6 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r6 == 0) goto L_0x07bc;
    L_0x07b8:
        r34 = 1;
    L_0x07ba:
        goto L_0x0702;
    L_0x07bc:
        r34 = 0;
        goto L_0x07ba;
    L_0x07bf:
        r0 = r48;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r88 = r0;
        goto L_0x075e;
    L_0x07c6:
        r35 = move-exception;
        r44 = 1;
        org.telegram.messenger.FileLog.m94e(r35);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x076c;
    L_0x07cd:
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r37;
        r5 = r0.dequeueInputBuffer(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r5 < 0) goto L_0x0803;
    L_0x07d7:
        r6 = 1;
        r0 = r64;
        r0.drawImage(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r8 = r64.getFrame();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r9 = r38[r5];	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r9.clear();	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r10 = r28;
        r11 = r72;
        r12 = r70;
        r13 = r66;
        r14 = r78;
        org.telegram.messenger.Utilities.convertVideoFrame(r8, r9, r10, r11, r12, r13, r14);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r12 = 0;
        r0 = r48;
        r14 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r16 = 0;
        r10 = r37;
        r11 = r5;
        r13 = r25;
        r10.queueInputBuffer(r11, r12, r13, r14, r16);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0789;
    L_0x0803:
        r6 = "input buffer not available";
        org.telegram.messenger.FileLog.m92e(r6);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x0789;
    L_0x080a:
        r10 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r37;
        r5 = r0.dequeueInputBuffer(r10);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        if (r5 < 0) goto L_0x040b;
    L_0x0814:
        r12 = 0;
        r13 = 1;
        r0 = r48;
        r14 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        r16 = 4;
        r10 = r37;
        r11 = r5;
        r10.queueInputBuffer(r11, r12, r13, r14, r16);	 Catch:{ Exception -> 0x01b0, all -> 0x04d0 }
        goto L_0x040b;
    L_0x0824:
        r10 = -1;
        r6 = (r88 > r10 ? 1 : (r88 == r10 ? 0 : -1));
        if (r6 == 0) goto L_0x08c9;
    L_0x082a:
        r16 = r88;
        goto L_0x01b8;
    L_0x082e:
        r21 = 0;
        r11 = r90;
        r12 = r91;
        r13 = r46;
        r14 = r57;
        r15 = r48;
        r16 = r76;
        r88 = r11.readAndWriteTrack(r12, r13, r14, r15, r16, r18, r20, r21);	 Catch:{ Exception -> 0x08b6, all -> 0x04d0 }
        r10 = -1;
        r6 = (r88 > r10 ? 1 : (r88 == r10 ? 0 : -1));
        if (r6 == 0) goto L_0x08c5;
    L_0x0846:
        r16 = r88;
        goto L_0x01dc;
    L_0x084a:
        r35 = move-exception;
        org.telegram.messenger.FileLog.m94e(r35);
        goto L_0x01fc;
    L_0x0850:
        r35 = move-exception;
        r16 = r86;
    L_0x0853:
        r43 = 1;
        org.telegram.messenger.FileLog.m94e(r35);	 Catch:{ all -> 0x08b3 }
        if (r45 == 0) goto L_0x085d;
    L_0x085a:
        r45.release();
    L_0x085d:
        if (r57 == 0) goto L_0x0862;
    L_0x085f:
        r57.finishMovie();	 Catch:{ Exception -> 0x0881 }
    L_0x0862:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r10 = "time = ";
        r6 = r6.append(r10);
        r10 = java.lang.System.currentTimeMillis();
        r10 = r10 - r80;
        r6 = r6.append(r10);
        r6 = r6.toString();
        org.telegram.messenger.FileLog.m92e(r6);
        goto L_0x021b;
    L_0x0881:
        r35 = move-exception;
        org.telegram.messenger.FileLog.m94e(r35);
        goto L_0x0862;
    L_0x0886:
        r35 = move-exception;
        org.telegram.messenger.FileLog.m94e(r35);
        goto L_0x04df;
    L_0x088c:
        r6 = r68.edit();
        r10 = "isPreviousOk";
        r11 = 1;
        r6 = r6.putBoolean(r10, r11);
        r6.commit();
        r6 = 1;
        r10 = 1;
        r0 = r90;
        r1 = r91;
        r2 = r20;
        r0.didWriteData(r1, r2, r6, r10);
        r6 = 0;
        goto L_0x00ce;
    L_0x08a9:
        r6 = move-exception;
        r16 = r86;
        goto L_0x04d5;
    L_0x08ae:
        r6 = move-exception;
        r45 = r46;
        goto L_0x04d5;
    L_0x08b3:
        r6 = move-exception;
        goto L_0x04d5;
    L_0x08b6:
        r35 = move-exception;
        r45 = r46;
        r16 = r86;
        goto L_0x0853;
    L_0x08bc:
        r35 = move-exception;
        r45 = r46;
        goto L_0x0853;
    L_0x08c0:
        r35 = move-exception;
        r53 = r54;
        goto L_0x01b1;
    L_0x08c5:
        r16 = r86;
        goto L_0x01dc;
    L_0x08c9:
        r16 = r86;
        goto L_0x01b8;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.convertVideo(org.telegram.messenger.MessageObject):boolean");
    }
}
