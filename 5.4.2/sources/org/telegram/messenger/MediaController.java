package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.ui.AspectRatioFrameLayout;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.InputDocument;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.PhotoFilterView.CurvesToolValue;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;
import org.telegram.ui.PhotoViewer;

public class MediaController implements SensorEventListener, OnAudioFocusChangeListener, NotificationCenterDelegate {
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
    public static AlbumEntry allMediaAlbumEntry;
    public static AlbumEntry allPhotosAlbumEntry;
    private static Runnable broadcastPhotosRunnable;
    private static final String[] projectionPhotos = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "datetaken", "orientation"};
    private static final String[] projectionVideo = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "datetaken", "duration"};
    public static int[] readArgs = new int[3];
    private static Runnable refreshGalleryRunnable;
    private Sensor accelerometerSensor;
    private boolean accelerometerVertical;
    private HashMap<String, FileDownloadProgressListener> addLaterArray = new HashMap();
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
    private ArrayList<FileDownloadProgressListener> deleteLaterArray = new ArrayList();
    private boolean directShare = true;
    private ArrayList<DownloadObject> documentDownloadQueue = new ArrayList();
    private HashMap<String, DownloadObject> downloadQueueKeys = new HashMap();
    private boolean downloadingCurrentMessage;
    private ExternalObserver externalObserver;
    private ByteBuffer fileBuffer;
    private DispatchQueue fileDecodingQueue;
    private DispatchQueue fileEncodingQueue;
    private boolean forceLoopCurrentPlaylist;
    private ArrayList<AudioBuffer> freePlayerBuffers = new ArrayList();
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
    private InternalObserver internalObserver;
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
    private EncryptedChat lastSecretChat;
    private int lastTag = 0;
    private long lastTimestamp = 0;
    private User lastUser;
    private float[] linearAcceleration = new float[3];
    private Sensor linearSensor;
    private boolean listenerInProgress = false;
    private HashMap<String, ArrayList<MessageObject>> loadingFileMessagesObservers = new HashMap();
    private HashMap<String, ArrayList<WeakReference<FileDownloadProgressListener>>> loadingFileObservers = new HashMap();
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
    private Runnable recordRunnable = new C31371();
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
    private SmsObserver smsObserver;
    private int startObserverToken;
    private StopMediaObserverRunnable stopMediaObserverRunnable;
    private final Object sync = new Object();
    private long timeSinceRaise;
    private HashMap<Long, Long> typingTimes = new HashMap();
    private boolean useFrontSpeaker;
    private ArrayList<AudioBuffer> usedPlayerBuffers = new ArrayList();
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

    /* renamed from: org.telegram.messenger.MediaController$1 */
    class C31371 implements Runnable {
        C31371() {
        }

        public void run() {
            if (MediaController.this.audioRecorder != null) {
                ByteBuffer allocateDirect;
                if (MediaController.this.recordBuffers.isEmpty()) {
                    allocateDirect = ByteBuffer.allocateDirect(MediaController.this.recordBufferSize);
                    allocateDirect.order(ByteOrder.nativeOrder());
                } else {
                    allocateDirect = (ByteBuffer) MediaController.this.recordBuffers.get(0);
                    MediaController.this.recordBuffers.remove(0);
                }
                allocateDirect.rewind();
                int read = MediaController.this.audioRecorder.read(allocateDirect, allocateDirect.capacity());
                if (read > 0) {
                    double d;
                    allocateDirect.limit(read);
                    double d2 = 0.0d;
                    try {
                        long access$300 = ((long) (read / 2)) + MediaController.this.samplesCount;
                        int access$3002 = (int) ((((double) MediaController.this.samplesCount) / ((double) access$300)) * ((double) MediaController.this.recordSamples.length));
                        int length = MediaController.this.recordSamples.length - access$3002;
                        if (access$3002 != 0) {
                            float length2 = ((float) MediaController.this.recordSamples.length) / ((float) access$3002);
                            float f = BitmapDescriptorFactory.HUE_RED;
                            for (int i = 0; i < access$3002; i++) {
                                MediaController.this.recordSamples[i] = MediaController.this.recordSamples[(int) f];
                                f += length2;
                            }
                        }
                        float f2 = (((float) read) / 2.0f) / ((float) length);
                        float f3 = BitmapDescriptorFactory.HUE_RED;
                        int i2 = access$3002;
                        for (access$3002 = 0; access$3002 < read / 2; access$3002++) {
                            short s = allocateDirect.getShort();
                            if (s > (short) 2500) {
                                d2 += (double) (s * s);
                            }
                            if (access$3002 == ((int) f3) && i2 < MediaController.this.recordSamples.length) {
                                MediaController.this.recordSamples[i2] = s;
                                f3 += f2;
                                i2++;
                            }
                        }
                        MediaController.this.samplesCount = access$300;
                        d = d2;
                    } catch (Throwable e) {
                        d = d2;
                        FileLog.m13728e(e);
                    }
                    allocateDirect.position(0);
                    d = Math.sqrt((d / ((double) read)) / 2.0d);
                    final boolean z = read != allocateDirect.capacity();
                    if (read != 0) {
                        MediaController.this.fileEncodingQueue.postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.MediaController$1$1$1 */
                            class C31311 implements Runnable {
                                C31311() {
                                }

                                public void run() {
                                    MediaController.this.recordBuffers.add(allocateDirect);
                                }
                            }

                            public void run() {
                                while (allocateDirect.hasRemaining()) {
                                    int limit;
                                    if (allocateDirect.remaining() > MediaController.this.fileBuffer.remaining()) {
                                        limit = allocateDirect.limit();
                                        allocateDirect.limit(MediaController.this.fileBuffer.remaining() + allocateDirect.position());
                                    } else {
                                        limit = -1;
                                    }
                                    MediaController.this.fileBuffer.put(allocateDirect);
                                    if (MediaController.this.fileBuffer.position() == MediaController.this.fileBuffer.limit() || z) {
                                        if (MediaController.this.writeFrame(MediaController.this.fileBuffer, !z ? MediaController.this.fileBuffer.limit() : allocateDirect.position()) != 0) {
                                            MediaController.this.fileBuffer.rewind();
                                            MediaController.this.recordTimeCount = MediaController.this.recordTimeCount + ((long) ((MediaController.this.fileBuffer.limit() / 2) / 16));
                                        }
                                    }
                                    if (limit != -1) {
                                        allocateDirect.limit(limit);
                                    }
                                }
                                MediaController.this.recordQueue.postRunnable(new C31311());
                            }
                        });
                    }
                    MediaController.this.recordQueue.postRunnable(MediaController.this.recordRunnable);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordProgressChanged, Long.valueOf(System.currentTimeMillis() - MediaController.this.recordStartTime), Double.valueOf(d));
                        }
                    });
                    return;
                }
                MediaController.this.recordBuffers.add(allocateDirect);
                MediaController.this.stopRecordingInternal(MediaController.this.sendAfterDone);
            }
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$3 */
    class C31513 implements Runnable {
        C31513() {
        }

        public void run() {
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.FileDidFailedLoad);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.didReceivedNewMessages);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.FileDidLoaded);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.FileLoadProgressChanged);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.FileUploadProgressChanged);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.removeAllMessagesFromDialog);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.musicDidLoaded);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.httpFileDidLoaded);
            NotificationCenter.getInstance().addObserver(MediaController.this, NotificationCenter.httpFileDidFailedLoad);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$4 */
    class C31524 extends BroadcastReceiver {
        C31524() {
        }

        public void onReceive(Context context, Intent intent) {
            MediaController.this.checkAutodownloadSettings();
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$5 */
    class C31545 extends PhoneStateListener {
        C31545() {
        }

        public void onCallStateChanged(final int i, String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    EmbedBottomSheet instance;
                    if (i == 1) {
                        if (MediaController.this.isPlayingMessage(MediaController.this.getPlayingMessageObject()) && !MediaController.this.isMessagePaused()) {
                            MediaController.this.pauseMessage(MediaController.this.getPlayingMessageObject());
                        } else if (!(MediaController.this.recordStartRunnable == null && MediaController.this.recordingAudio == null)) {
                            MediaController.this.stopRecording(2);
                        }
                        instance = EmbedBottomSheet.getInstance();
                        if (instance != null) {
                            instance.pause();
                        }
                        MediaController.this.callInProgress = true;
                    } else if (i == 0) {
                        MediaController.this.callInProgress = false;
                    } else if (i == 2) {
                        instance = EmbedBottomSheet.getInstance();
                        if (instance != null) {
                            instance.pause();
                        }
                        MediaController.this.callInProgress = true;
                    }
                }
            });
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$7 */
    class C31577 implements Runnable {
        C31577() {
        }

        public void run() {
            try {
                if (MediaController.this.smsObserver != null) {
                    ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.this.smsObserver);
                    MediaController.this.smsObserver = null;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$9 */
    class C31599 implements Runnable {
        C31599() {
        }

        public void run() {
            if (MediaController.this.decodingFinished) {
                MediaController.this.checkPlayerQueue();
                return;
            }
            int i = 0;
            while (true) {
                AudioBuffer audioBuffer = null;
                synchronized (MediaController.this.playerSync) {
                    if (!MediaController.this.freePlayerBuffers.isEmpty()) {
                        audioBuffer = (AudioBuffer) MediaController.this.freePlayerBuffers.get(0);
                        MediaController.this.freePlayerBuffers.remove(0);
                    }
                    if (!MediaController.this.usedPlayerBuffers.isEmpty()) {
                        i = true;
                    }
                }
                if (audioBuffer == null) {
                    break;
                }
                MediaController.this.readOpusFile(audioBuffer.buffer, MediaController.this.playerBufferSize, MediaController.readArgs);
                audioBuffer.size = MediaController.readArgs[0];
                audioBuffer.pcmOffset = (long) MediaController.readArgs[1];
                audioBuffer.finished = MediaController.readArgs[2];
                if (audioBuffer.finished == 1) {
                    MediaController.this.decodingFinished = true;
                }
                if (audioBuffer.size == 0) {
                    break;
                }
                audioBuffer.buffer.rewind();
                audioBuffer.buffer.get(audioBuffer.bufferBytes);
                synchronized (MediaController.this.playerSync) {
                    MediaController.this.usedPlayerBuffers.add(audioBuffer);
                }
                boolean z = true;
            }
            synchronized (MediaController.this.playerSync) {
                MediaController.this.freePlayerBuffers.add(audioBuffer);
            }
            if (i != 0) {
                MediaController.this.checkPlayerQueue();
            }
        }
    }

    public static class AlbumEntry {
        public int bucketId;
        public String bucketName;
        public PhotoEntry coverPhoto;
        public ArrayList<PhotoEntry> photos = new ArrayList();
        public HashMap<Integer, PhotoEntry> photosByIds = new HashMap();

        public AlbumEntry(int i, String str, PhotoEntry photoEntry) {
            this.bucketId = i;
            this.bucketName = str;
            this.coverPhoto = photoEntry;
        }

        public void addPhoto(PhotoEntry photoEntry) {
            this.photos.add(photoEntry);
            this.photosByIds.put(Integer.valueOf(photoEntry.imageId), photoEntry);
        }
    }

    private class AudioBuffer {
        ByteBuffer buffer;
        byte[] bufferBytes;
        int finished;
        long pcmOffset;
        int size;

        public AudioBuffer(int i) {
            this.buffer = ByteBuffer.allocateDirect(i);
            this.bufferBytes = new byte[i];
        }
    }

    public static class AudioEntry {
        public String author;
        public int duration;
        public String genre;
        public long id;
        public MessageObject messageObject;
        public String path;
        public String title;
    }

    private class ExternalObserver extends ContentObserver {
        public ExternalObserver() {
            super(null);
        }

        public void onChange(boolean z) {
            super.onChange(z);
            MediaController.this.processMediaObserver(Media.EXTERNAL_CONTENT_URI);
        }
    }

    public interface FileDownloadProgressListener {
        int getObserverTag();

        void onFailedDownload(String str);

        void onProgressDownload(String str, float f);

        void onProgressUpload(String str, float f, boolean z);

        void onSuccessDownload(String str);
    }

    private class GalleryObserverExternal extends ContentObserver {

        /* renamed from: org.telegram.messenger.MediaController$GalleryObserverExternal$1 */
        class C31601 implements Runnable {
            C31601() {
            }

            public void run() {
                MediaController.refreshGalleryRunnable = null;
                MediaController.loadGalleryPhotosAlbums(0);
            }
        }

        public GalleryObserverExternal() {
            super(null);
        }

        public void onChange(boolean z) {
            super.onChange(z);
            if (MediaController.refreshGalleryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
            }
            AndroidUtilities.runOnUIThread(MediaController.refreshGalleryRunnable = new C31601(), 2000);
        }
    }

    private class GalleryObserverInternal extends ContentObserver {

        /* renamed from: org.telegram.messenger.MediaController$GalleryObserverInternal$1 */
        class C31611 implements Runnable {
            C31611() {
            }

            public void run() {
                if (PhotoViewer.getInstance().isVisible()) {
                    GalleryObserverInternal.this.scheduleReloadRunnable();
                    return;
                }
                MediaController.refreshGalleryRunnable = null;
                MediaController.loadGalleryPhotosAlbums(0);
            }
        }

        public GalleryObserverInternal() {
            super(null);
        }

        private void scheduleReloadRunnable() {
            AndroidUtilities.runOnUIThread(MediaController.refreshGalleryRunnable = new C31611(), 2000);
        }

        public void onChange(boolean z) {
            super.onChange(z);
            if (MediaController.refreshGalleryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
            }
            scheduleReloadRunnable();
        }
    }

    private class InternalObserver extends ContentObserver {
        public InternalObserver() {
            super(null);
        }

        public void onChange(boolean z) {
            super.onChange(z);
            MediaController.this.processMediaObserver(Media.INTERNAL_CONTENT_URI);
        }
    }

    public static class PhotoEntry {
        public int bucketId;
        public CharSequence caption;
        public long dateTaken;
        public int duration;
        public VideoEditedInfo editedInfo;
        public int imageId;
        public String imagePath;
        public boolean isCropped;
        public boolean isFiltered;
        public boolean isMuted;
        public boolean isPainted;
        public boolean isVideo;
        public int orientation;
        public String path;
        public SavedFilterState savedFilterState;
        public ArrayList<InputDocument> stickers = new ArrayList();
        public String thumbPath;
        public int ttl;

        public PhotoEntry(int i, int i2, long j, String str, int i3, boolean z) {
            this.bucketId = i;
            this.imageId = i2;
            this.dateTaken = j;
            this.path = str;
            if (z) {
                this.duration = i3;
            } else {
                this.orientation = i3;
            }
            this.isVideo = z;
        }

        public void reset() {
            this.isFiltered = false;
            this.isPainted = false;
            this.isCropped = false;
            this.ttl = 0;
            this.imagePath = null;
            this.thumbPath = null;
            this.caption = null;
            this.savedFilterState = null;
            this.stickers.clear();
        }
    }

    public static class SavedFilterState {
        public float blurAngle;
        public float blurExcludeBlurSize;
        public Point blurExcludePoint;
        public float blurExcludeSize;
        public int blurType;
        public float contrastValue;
        public CurvesToolValue curvesToolValue = new CurvesToolValue();
        public float enhanceValue;
        public float exposureValue;
        public float fadeValue;
        public float grainValue;
        public float highlightsValue;
        public float saturationValue;
        public float shadowsValue;
        public float sharpenValue;
        public int tintHighlightsColor;
        public int tintShadowsColor;
        public float vignetteValue;
        public float warmthValue;
    }

    public static class SearchImage {
        public CharSequence caption;
        public int date;
        public Document document;
        public int height;
        public String id;
        public String imagePath;
        public String imageUrl;
        public boolean isCropped;
        public boolean isFiltered;
        public boolean isPainted;
        public String localUrl;
        public SavedFilterState savedFilterState;
        public int size;
        public ArrayList<InputDocument> stickers = new ArrayList();
        public String thumbPath;
        public String thumbUrl;
        public int ttl;
        public int type;
        public int width;

        public void reset() {
            this.isFiltered = false;
            this.isPainted = false;
            this.isCropped = false;
            this.ttl = 0;
            this.imagePath = null;
            this.thumbPath = null;
            this.caption = null;
            this.savedFilterState = null;
            this.stickers.clear();
        }
    }

    private class SmsObserver extends ContentObserver {
        public SmsObserver() {
            super(null);
        }

        public void onChange(boolean z) {
            MediaController.this.readSms();
        }
    }

    private final class StopMediaObserverRunnable implements Runnable {
        public int currentObserverToken;

        private StopMediaObserverRunnable() {
            this.currentObserverToken = 0;
        }

        public void run() {
            if (this.currentObserverToken == MediaController.this.startObserverToken) {
                try {
                    if (MediaController.this.internalObserver != null) {
                        ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.this.internalObserver);
                        MediaController.this.internalObserver = null;
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
                try {
                    if (MediaController.this.externalObserver != null) {
                        ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.this.externalObserver);
                        MediaController.this.externalObserver = null;
                    }
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        }
    }

    private static class VideoConvertRunnable implements Runnable {
        private MessageObject messageObject;

        private VideoConvertRunnable(MessageObject messageObject) {
            this.messageObject = messageObject;
        }

        public static void runConversion(final MessageObject messageObject) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread thread = new Thread(new VideoConvertRunnable(messageObject), "VideoConvertRunnable");
                        thread.start();
                        thread.join();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            }).start();
        }

        public void run() {
            MediaController.getInstance().convertVideo(this.messageObject);
        }
    }

    public MediaController() {
        int i;
        try {
            this.recordBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
            if (this.recordBufferSize <= 0) {
                this.recordBufferSize = 1280;
            }
            this.playerBufferSize = AudioTrack.getMinBufferSize(48000, 4, 2);
            if (this.playerBufferSize <= 0) {
                this.playerBufferSize = 3840;
            }
            for (i = 0; i < 5; i++) {
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(4096);
                allocateDirect.order(ByteOrder.nativeOrder());
                this.recordBuffers.add(allocateDirect);
            }
            for (i = 0; i < 3; i++) {
                this.freePlayerBuffers.add(new AudioBuffer(this.playerBufferSize));
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        try {
            this.sensorManager = (SensorManager) ApplicationLoader.applicationContext.getSystemService("sensor");
            this.linearSensor = this.sensorManager.getDefaultSensor(10);
            this.gravitySensor = this.sensorManager.getDefaultSensor(9);
            if (this.linearSensor == null || this.gravitySensor == null) {
                FileLog.m13726e("gravity or linear sensor not found");
                this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
                this.linearSensor = null;
                this.gravitySensor = null;
            }
            this.proximitySensor = this.sensorManager.getDefaultSensor(8);
            this.proximityWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(32, "proximity");
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        this.fileBuffer = ByteBuffer.allocateDirect(1920);
        this.recordQueue = new DispatchQueue("recordQueue");
        this.recordQueue.setPriority(10);
        this.fileEncodingQueue = new DispatchQueue("fileEncodingQueue");
        this.fileEncodingQueue.setPriority(10);
        this.playerQueue = new DispatchQueue("playerQueue");
        this.fileDecodingQueue = new DispatchQueue("fileDecodingQueue");
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        int i2 = 0;
        while (i2 < 4) {
            String str = "mobileDataDownloadMask" + (i2 == 0 ? TtmlNode.ANONYMOUS_REGION_ID : Integer.valueOf(i2));
            if (i2 == 0 || sharedPreferences.contains(str)) {
                this.mobileDataDownloadMask[i2] = sharedPreferences.getInt(str, 115);
                this.wifiDownloadMask[i2] = sharedPreferences.getInt("wifiDownloadMask" + (i2 == 0 ? TtmlNode.ANONYMOUS_REGION_ID : Integer.valueOf(i2)), 115);
                this.roamingDownloadMask[i2] = sharedPreferences.getInt("roamingDownloadMask" + (i2 == 0 ? TtmlNode.ANONYMOUS_REGION_ID : Integer.valueOf(i2)), 0);
            } else {
                this.mobileDataDownloadMask[i2] = this.mobileDataDownloadMask[0];
                this.wifiDownloadMask[i2] = this.wifiDownloadMask[0];
                this.roamingDownloadMask[i2] = this.roamingDownloadMask[0];
            }
            i2++;
        }
        i2 = 0;
        while (i2 < 7) {
            i = i2 == 1 ? 2097152 : i2 == 6 ? 5242880 : 10485760;
            this.mobileMaxFileSize[i2] = sharedPreferences.getInt("mobileMaxDownloadSize" + i2, i);
            this.wifiMaxFileSize[i2] = sharedPreferences.getInt("wifiMaxDownloadSize" + i2, i);
            this.roamingMaxFileSize[i2] = sharedPreferences.getInt("roamingMaxDownloadSize" + i2, i);
            i2++;
        }
        this.globalAutodownloadEnabled = sharedPreferences.getBoolean("globalAutodownloadEnabled", true);
        this.saveToGallery = sharedPreferences.getBoolean("save_gallery", false);
        this.autoplayGifs = sharedPreferences.getBoolean("autoplay_gif", true);
        this.raiseToSpeak = sharedPreferences.getBoolean("raise_to_speak", true);
        this.customTabs = sharedPreferences.getBoolean("custom_tabs", true);
        this.directShare = sharedPreferences.getBoolean("direct_share", true);
        this.shuffleMusic = sharedPreferences.getBoolean("shuffleMusic", false);
        this.playOrderReversed = sharedPreferences.getBoolean("playOrderReversed", false);
        this.inappCamera = sharedPreferences.getBoolean("inappCamera", true);
        this.roundCamera16to9 = sharedPreferences.getBoolean("roundCamera16to9", true);
        this.groupPhotosEnabled = sharedPreferences.getBoolean("groupPhotosEnabled", true);
        this.repeatMode = sharedPreferences.getInt("repeatMode", 0);
        AndroidUtilities.runOnUIThread(new C31513());
        ApplicationLoader.applicationContext.registerReceiver(new C31524(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        if (UserConfig.isClientActivated()) {
            checkAutodownloadSettings();
        }
        this.mediaProjections = new String[]{"_data", "_display_name", "bucket_display_name", "datetaken", "title", "width", "height"};
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, true, new GalleryObserverExternal());
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Media.INTERNAL_CONTENT_URI, true, new GalleryObserverInternal());
        } catch (Throwable e222) {
            FileLog.m13728e(e222);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Video.Media.EXTERNAL_CONTENT_URI, true, new GalleryObserverExternal());
        } catch (Throwable e2222) {
            FileLog.m13728e(e2222);
        }
        try {
            ApplicationLoader.applicationContext.getContentResolver().registerContentObserver(Video.Media.INTERNAL_CONTENT_URI, true, new GalleryObserverInternal());
        } catch (Throwable e22222) {
            FileLog.m13728e(e22222);
        }
        try {
            PhoneStateListener c31545 = new C31545();
            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            if (telephonyManager != null) {
                telephonyManager.listen(c31545, 32);
            }
        } catch (Throwable e222222) {
            FileLog.m13728e(e222222);
        }
    }

    private static void broadcastNewPhotos(int i, ArrayList<AlbumEntry> arrayList, ArrayList<AlbumEntry> arrayList2, Integer num, AlbumEntry albumEntry, AlbumEntry albumEntry2, int i2) {
        if (broadcastPhotosRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(broadcastPhotosRunnable);
        }
        final int i3 = i;
        final ArrayList<AlbumEntry> arrayList3 = arrayList;
        final ArrayList<AlbumEntry> arrayList4 = arrayList2;
        final Integer num2 = num;
        final AlbumEntry albumEntry3 = albumEntry;
        final AlbumEntry albumEntry4 = albumEntry2;
        Runnable anonymousClass29 = new Runnable() {
            public void run() {
                if (PhotoViewer.getInstance().isVisible()) {
                    MediaController.broadcastNewPhotos(i3, arrayList3, arrayList4, num2, albumEntry3, albumEntry4, 1000);
                    return;
                }
                MediaController.broadcastPhotosRunnable = null;
                MediaController.allPhotosAlbumEntry = albumEntry4;
                MediaController.allMediaAlbumEntry = albumEntry3;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.albumsDidLoaded, Integer.valueOf(i3), arrayList3, arrayList4, num2);
            }
        };
        broadcastPhotosRunnable = anonymousClass29;
        AndroidUtilities.runOnUIThread(anonymousClass29, (long) i2);
    }

    private void buildShuffledPlayList() {
        if (!this.playlist.isEmpty()) {
            ArrayList arrayList = new ArrayList(this.playlist);
            this.shuffledPlaylist.clear();
            MessageObject messageObject = (MessageObject) this.playlist.get(this.currentPlaylistNum);
            arrayList.remove(this.currentPlaylistNum);
            this.shuffledPlaylist.add(messageObject);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                int nextInt = Utilities.random.nextInt(arrayList.size());
                this.shuffledPlaylist.add(arrayList.get(nextInt));
                arrayList.remove(nextInt);
            }
        }
    }

    private void checkAudioFocus(MessageObject messageObject) {
        int i = (messageObject.isVoice() || messageObject.isRoundVideo()) ? this.useFrontSpeaker ? 3 : 2 : 1;
        if (this.hasAudioFocus != i) {
            this.hasAudioFocus = i;
            if (i == 3) {
                i = NotificationsController.getInstance().audioManager.requestAudioFocus(this, 0, 1);
            } else {
                i = NotificationsController.getInstance().audioManager.requestAudioFocus(this, 3, i == 2 ? 3 : 1);
            }
            if (i == 1) {
                this.audioFocus = 2;
            }
        }
    }

    private void checkConversionCanceled() {
        synchronized (this.videoConvertSync) {
            boolean z = this.cancelCurrentVideoConversion;
        }
        if (z) {
            throw new RuntimeException("canceled conversion");
        }
    }

    private void checkDecoderQueue() {
        this.fileDecodingQueue.postRunnable(new C31599());
    }

    private void checkDownloadFinished(String str, int i) {
        DownloadObject downloadObject = (DownloadObject) this.downloadQueueKeys.get(str);
        if (downloadObject != null) {
            this.downloadQueueKeys.remove(str);
            if (i == 0 || i == 2) {
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

    public static void checkGallery() {
        if (VERSION.SDK_INT >= 24 && allPhotosAlbumEntry != null) {
            final int size = allPhotosAlbumEntry.photos.size();
            Utilities.globalQueue.postRunnable(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    Cursor query;
                    int i;
                    Throwable th;
                    Cursor cursor;
                    int i2;
                    Cursor cursor2 = null;
                    try {
                        if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                            query = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Media.EXTERNAL_CONTENT_URI, new String[]{"COUNT(_id)"}, null, null, null);
                            if (query != null) {
                                try {
                                    if (query.moveToNext()) {
                                        i = 0 + query.getInt(0);
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        FileLog.m13728e(th);
                                        if (query == null) {
                                            cursor = query;
                                            i = 0;
                                        } else {
                                            query.close();
                                            cursor = query;
                                            i = 0;
                                        }
                                        if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                                            query = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, new String[]{"COUNT(_id)"}, null, null, null);
                                            if (query != null) {
                                                try {
                                                    if (query.moveToNext()) {
                                                        i2 = i + query.getInt(0);
                                                    }
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    try {
                                                        FileLog.m13728e(th);
                                                        if (query == null) {
                                                            i2 = i;
                                                        } else {
                                                            query.close();
                                                            i2 = i;
                                                        }
                                                        if (size != i2) {
                                                            if (MediaController.refreshGalleryRunnable != null) {
                                                                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
                                                                MediaController.refreshGalleryRunnable = null;
                                                            }
                                                            MediaController.loadGalleryPhotosAlbums(0);
                                                        }
                                                    } catch (Throwable th4) {
                                                        th = th4;
                                                        cursor = query;
                                                        if (cursor != null) {
                                                            cursor.close();
                                                        }
                                                        throw th;
                                                    }
                                                }
                                            }
                                            i2 = i;
                                        } else {
                                            query = cursor;
                                            i2 = i;
                                        }
                                        if (query != null) {
                                            query.close();
                                        }
                                        if (size != i2) {
                                            if (MediaController.refreshGalleryRunnable != null) {
                                                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
                                                MediaController.refreshGalleryRunnable = null;
                                            }
                                            MediaController.loadGalleryPhotosAlbums(0);
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                        cursor2 = query;
                                        if (cursor2 != null) {
                                            cursor2.close();
                                        }
                                        throw th;
                                    }
                                }
                            }
                            i = 0;
                        } else {
                            query = null;
                            i = 0;
                        }
                        if (query != null) {
                            query.close();
                            cursor = query;
                        } else {
                            cursor = query;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                    try {
                        if (ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
                            query = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, new String[]{"COUNT(_id)"}, null, null, null);
                            if (query != null) {
                                if (query.moveToNext()) {
                                    i2 = i + query.getInt(0);
                                }
                            }
                            i2 = i;
                        } else {
                            query = cursor;
                            i2 = i;
                        }
                        if (query != null) {
                            query.close();
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                    if (size != i2) {
                        if (MediaController.refreshGalleryRunnable != null) {
                            AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
                            MediaController.refreshGalleryRunnable = null;
                        }
                        MediaController.loadGalleryPhotosAlbums(0);
                    }
                }
            }, 2000);
        }
    }

    private void checkIsNextMusicFileDownloaded() {
        File file = null;
        if ((getCurrentDownloadMask() & 16) != 0) {
            ArrayList arrayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
            if (arrayList != null && arrayList.size() >= 2) {
                int i;
                if (this.playOrderReversed) {
                    i = this.currentPlaylistNum + 1;
                    if (i >= arrayList.size()) {
                        i = 0;
                    }
                } else {
                    i = this.currentPlaylistNum - 1;
                    if (i < 0) {
                        i = arrayList.size() - 1;
                    }
                }
                MessageObject messageObject = (MessageObject) arrayList.get(i);
                if (canDownloadMedia(messageObject)) {
                    File file2;
                    if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath)) {
                        file2 = new File(messageObject.messageOwner.attachPath);
                        if (file2.exists()) {
                            file = file2;
                        }
                    }
                    file2 = file != null ? file : FileLoader.getPathToMessage(messageObject.messageOwner);
                    if (file2 == null || file2.exists()) {
                        if (file2 != null && file2 != file && !file2.exists() && messageObject.isMusic()) {
                            FileLoader.getInstance().loadFile(messageObject.getDocument(), false, 0);
                        }
                    } else if (file2 != null) {
                    }
                }
            }
        }
    }

    private void checkIsNextVoiceFileDownloaded() {
        File file = null;
        if (this.voiceMessagesPlaylist != null && this.voiceMessagesPlaylist.size() >= 2) {
            File file2;
            MessageObject messageObject = (MessageObject) this.voiceMessagesPlaylist.get(1);
            if (messageObject.messageOwner.attachPath != null && messageObject.messageOwner.attachPath.length() > 0) {
                file2 = new File(messageObject.messageOwner.attachPath);
                if (file2.exists()) {
                    file = file2;
                }
            }
            file2 = file != null ? file : FileLoader.getPathToMessage(messageObject.messageOwner);
            if (file2 == null || file2.exists()) {
                if (file2 != null && file2 != file && !file2.exists()) {
                    FileLoader.getInstance().loadFile(messageObject.getDocument(), false, 0);
                }
            } else if (file2 != null) {
            }
        }
    }

    private void checkPlayerQueue() {
        this.playerQueue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r8 = this;
                r7 = 1;
                r1 = 0;
                r0 = org.telegram.messenger.MediaController.this;
                r2 = r0.playerObjectSync;
                monitor-enter(r2);
                r0 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00aa }
                r0 = r0.audioTrackPlayer;	 Catch:{ all -> 0x00aa }
                if (r0 == 0) goto L_0x001e;
            L_0x0011:
                r0 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00aa }
                r0 = r0.audioTrackPlayer;	 Catch:{ all -> 0x00aa }
                r0 = r0.getPlayState();	 Catch:{ all -> 0x00aa }
                r3 = 3;
                if (r0 == r3) goto L_0x0020;
            L_0x001e:
                monitor-exit(r2);	 Catch:{ all -> 0x00aa }
            L_0x001f:
                return;
            L_0x0020:
                monitor-exit(r2);	 Catch:{ all -> 0x00aa }
                r0 = 0;
                r2 = org.telegram.messenger.MediaController.this;
                r2 = r2.playerSync;
                monitor-enter(r2);
                r3 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00ad }
                r3 = r3.usedPlayerBuffers;	 Catch:{ all -> 0x00ad }
                r3 = r3.isEmpty();	 Catch:{ all -> 0x00ad }
                if (r3 != 0) goto L_0x00b8;
            L_0x0035:
                r0 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00ad }
                r0 = r0.usedPlayerBuffers;	 Catch:{ all -> 0x00ad }
                r3 = 0;
                r0 = r0.get(r3);	 Catch:{ all -> 0x00ad }
                r0 = (org.telegram.messenger.MediaController.AudioBuffer) r0;	 Catch:{ all -> 0x00ad }
                r3 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00ad }
                r3 = r3.usedPlayerBuffers;	 Catch:{ all -> 0x00ad }
                r4 = 0;
                r3.remove(r4);	 Catch:{ all -> 0x00ad }
                r6 = r0;
            L_0x004d:
                monitor-exit(r2);	 Catch:{ all -> 0x00ad }
                if (r6 == 0) goto L_0x0085;
            L_0x0050:
                r0 = org.telegram.messenger.MediaController.this;	 Catch:{ Exception -> 0x00b0 }
                r0 = r0.audioTrackPlayer;	 Catch:{ Exception -> 0x00b0 }
                r2 = r6.bufferBytes;	 Catch:{ Exception -> 0x00b0 }
                r3 = 0;
                r4 = r6.size;	 Catch:{ Exception -> 0x00b0 }
                r0 = r0.write(r2, r3, r4);	 Catch:{ Exception -> 0x00b0 }
            L_0x005f:
                r1 = org.telegram.messenger.MediaController.this;
                r1.buffersWrited = r1.buffersWrited + 1;
                if (r0 <= 0) goto L_0x007c;
            L_0x0066:
                r2 = r6.pcmOffset;
                r1 = r6.finished;
                if (r1 != r7) goto L_0x00b6;
            L_0x006c:
                r4 = r0;
            L_0x006d:
                r0 = org.telegram.messenger.MediaController.this;
                r5 = r0.buffersWrited;
                r0 = new org.telegram.messenger.MediaController$10$1;
                r1 = r8;
                r0.<init>(r2, r4, r5);
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r0);
            L_0x007c:
                r0 = r6.finished;
                if (r0 == r7) goto L_0x0085;
            L_0x0080:
                r0 = org.telegram.messenger.MediaController.this;
                r0.checkPlayerQueue();
            L_0x0085:
                if (r6 == 0) goto L_0x008d;
            L_0x0087:
                if (r6 == 0) goto L_0x0092;
            L_0x0089:
                r0 = r6.finished;
                if (r0 == r7) goto L_0x0092;
            L_0x008d:
                r0 = org.telegram.messenger.MediaController.this;
                r0.checkDecoderQueue();
            L_0x0092:
                if (r6 == 0) goto L_0x001f;
            L_0x0094:
                r0 = org.telegram.messenger.MediaController.this;
                r1 = r0.playerSync;
                monitor-enter(r1);
                r0 = org.telegram.messenger.MediaController.this;	 Catch:{ all -> 0x00a7 }
                r0 = r0.freePlayerBuffers;	 Catch:{ all -> 0x00a7 }
                r0.add(r6);	 Catch:{ all -> 0x00a7 }
                monitor-exit(r1);	 Catch:{ all -> 0x00a7 }
                goto L_0x001f;
            L_0x00a7:
                r0 = move-exception;
                monitor-exit(r1);	 Catch:{ all -> 0x00a7 }
                throw r0;
            L_0x00aa:
                r0 = move-exception;
                monitor-exit(r2);	 Catch:{ all -> 0x00aa }
                throw r0;
            L_0x00ad:
                r0 = move-exception;
                monitor-exit(r2);	 Catch:{ all -> 0x00ad }
                throw r0;
            L_0x00b0:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);
                r0 = r1;
                goto L_0x005f;
            L_0x00b6:
                r4 = -1;
                goto L_0x006d;
            L_0x00b8:
                r6 = r0;
                goto L_0x004d;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.10.run():void");
            }
        });
    }

    private void checkScreenshots(ArrayList<Long> arrayList) {
        if (arrayList != null && !arrayList.isEmpty() && this.lastChatEnterTime != 0) {
            if (this.lastUser != null || (this.lastSecretChat instanceof TLRPC$TL_encryptedChat)) {
                Object obj = null;
                for (int i = 0; i < arrayList.size(); i++) {
                    Long l = (Long) arrayList.get(i);
                    if ((this.lastMediaCheckTime == 0 || l.longValue() > this.lastMediaCheckTime) && l.longValue() >= this.lastChatEnterTime && (this.lastChatLeaveTime == 0 || l.longValue() <= this.lastChatLeaveTime + 2000)) {
                        this.lastMediaCheckTime = Math.max(this.lastMediaCheckTime, l.longValue());
                        obj = 1;
                    }
                }
                if (obj == null) {
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

    private native void closeOpusFile();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean convertVideo(org.telegram.messenger.MessageObject r55) {
        /*
        r54 = this;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r8 = r4.originalPath;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r0 = r4.startTime;
        r30 = r0;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r0 = r4.endTime;
        r46 = r0;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r6 = r4.resultWidth;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r7 = r4.resultHeight;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r5 = r4.rotationValue;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r9 = r4.originalWidth;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r10 = r4.originalHeight;
        r0 = r55;
        r4 = r0.videoEditedInfo;
        r0 = r4.bitrate;
        r42 = r0;
        r4 = 0;
        r45 = new java.io.File;
        r0 = r55;
        r11 = r0.messageOwner;
        r11 = r11.attachPath;
        r0 = r45;
        r0.<init>(r11);
        r11 = android.os.Build.VERSION.SDK_INT;
        r12 = 18;
        if (r11 >= r12) goto L_0x00b5;
    L_0x0050:
        if (r7 <= r6) goto L_0x00b5;
    L_0x0052:
        if (r6 == r9) goto L_0x00b5;
    L_0x0054:
        if (r7 == r10) goto L_0x00b5;
    L_0x0056:
        r5 = 90;
        r4 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        r23 = r6;
        r24 = r7;
        r53 = r4;
        r4 = r5;
        r5 = r53;
    L_0x0063:
        r6 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r7 = "videoconvert";
        r11 = 0;
        r48 = r6.getSharedPreferences(r7, r11);
        r6 = new java.io.File;
        r6.<init>(r8);
        r7 = r55.getId();
        if (r7 == 0) goto L_0x00ef;
    L_0x0078:
        r7 = "isPreviousOk";
        r11 = 1;
        r0 = r48;
        r7 = r0.getBoolean(r7, r11);
        r11 = r48.edit();
        r12 = "isPreviousOk";
        r13 = 0;
        r11 = r11.putBoolean(r12, r13);
        r11.commit();
        r6 = r6.canRead();
        if (r6 == 0) goto L_0x0099;
    L_0x0097:
        if (r7 != 0) goto L_0x00ef;
    L_0x0099:
        r4 = 1;
        r5 = 1;
        r0 = r54;
        r1 = r55;
        r2 = r45;
        r0.didWriteData(r1, r2, r4, r5);
        r4 = r48.edit();
        r5 = "isPreviousOk";
        r6 = 1;
        r4 = r4.putBoolean(r5, r6);
        r4.commit();
        r4 = 0;
    L_0x00b4:
        return r4;
    L_0x00b5:
        r11 = android.os.Build.VERSION.SDK_INT;
        r12 = 20;
        if (r11 <= r12) goto L_0x0961;
    L_0x00bb:
        r11 = 90;
        if (r5 != r11) goto L_0x00cc;
    L_0x00bf:
        r5 = 0;
        r4 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        r23 = r6;
        r24 = r7;
        r53 = r4;
        r4 = r5;
        r5 = r53;
        goto L_0x0063;
    L_0x00cc:
        r11 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        if (r5 != r11) goto L_0x00dd;
    L_0x00d0:
        r4 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r5 = 0;
        r23 = r7;
        r24 = r6;
        r53 = r4;
        r4 = r5;
        r5 = r53;
        goto L_0x0063;
    L_0x00dd:
        r11 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        if (r5 != r11) goto L_0x0961;
    L_0x00e1:
        r5 = 0;
        r4 = 90;
        r23 = r6;
        r24 = r7;
        r53 = r4;
        r4 = r5;
        r5 = r53;
        goto L_0x0063;
    L_0x00ef:
        r6 = 1;
        r0 = r54;
        r0.videoConvertFirstWrite = r6;
        r27 = 0;
        r50 = java.lang.System.currentTimeMillis();
        if (r24 == 0) goto L_0x08dc;
    L_0x00fc:
        if (r23 == 0) goto L_0x08dc;
    L_0x00fe:
        r7 = 0;
        r6 = 0;
        r49 = new android.media.MediaCodec$BufferInfo;	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r49.<init>();	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r11 = new org.telegram.messenger.video.Mp4Movie;	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r11.<init>();	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r0 = r45;
        r11.setCacheFile(r0);	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r11.setRotation(r4);	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r0 = r24;
        r1 = r23;
        r11.setSize(r0, r1);	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r4 = new org.telegram.messenger.video.MP4Builder;	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r4.<init>();	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r35 = r4.createMovie(r11);	 Catch:{ Exception -> 0x08a2, all -> 0x08f9 }
        r34 = new android.media.MediaExtractor;	 Catch:{ Exception -> 0x090c, all -> 0x0900 }
        r34.<init>();	 Catch:{ Exception -> 0x090c, all -> 0x0900 }
        r0 = r34;
        r0.setDataSource(r8);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r54.checkConversionCanceled();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r0 = r24;
        if (r0 != r9) goto L_0x0141;
    L_0x0133:
        r0 = r23;
        if (r0 != r10) goto L_0x0141;
    L_0x0137:
        if (r5 != 0) goto L_0x0141;
    L_0x0139:
        r0 = r55;
        r4 = r0.videoEditedInfo;	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r4 = r4.roundVideo;	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        if (r4 == 0) goto L_0x087d;
    L_0x0141:
        r4 = 0;
        r0 = r54;
        r1 = r34;
        r52 = r0.selectTrack(r1, r4);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        if (r52 < 0) goto L_0x095d;
    L_0x014c:
        r9 = 0;
        r12 = 0;
        r7 = 0;
        r6 = 0;
        r16 = -1;
        r39 = 0;
        r13 = 0;
        r14 = 0;
        r11 = 0;
        r38 = -5;
        r4 = 0;
        r8 = android.os.Build.MANUFACTURER;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r10 = r8.toLowerCase();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r8 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r15 = 18;
        if (r8 >= r15) goto L_0x0446;
    L_0x0166:
        r8 = "video/avc";
        r15 = selectCodec(r8);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r8 = "video/avc";
        r8 = selectColorFormat(r15, r8);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r8 != 0) goto L_0x020d;
    L_0x0176:
        r4 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r5 = "no supported color format";
        r4.<init>(r5);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        throw r4;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
    L_0x017f:
        r4 = move-exception;
        r5 = r6;
        r6 = r7;
        r7 = r9;
    L_0x0183:
        org.telegram.messenger.FileLog.m13728e(r4);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r4 = 1;
        r32 = r5;
        r33 = r6;
    L_0x018b:
        r0 = r34;
        r1 = r52;
        r0.unselectTrack(r1);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        if (r32 == 0) goto L_0x0197;
    L_0x0194:
        r32.release();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x0197:
        if (r33 == 0) goto L_0x019c;
    L_0x0199:
        r33.release();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x019c:
        if (r7 == 0) goto L_0x01a4;
    L_0x019e:
        r7.stop();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r7.release();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x01a4:
        if (r12 == 0) goto L_0x01ac;
    L_0x01a6:
        r12.stop();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r12.release();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x01ac:
        r54.checkConversionCanceled();	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x01af:
        r10 = r30;
    L_0x01b1:
        if (r4 != 0) goto L_0x01ca;
    L_0x01b3:
        r5 = -1;
        r0 = r42;
        if (r0 == r5) goto L_0x01ca;
    L_0x01b8:
        r15 = 1;
        r5 = r54;
        r6 = r55;
        r7 = r34;
        r8 = r35;
        r9 = r49;
        r12 = r46;
        r14 = r45;
        r5.readAndWriteTrack(r6, r7, r8, r9, r10, r12, r14, r15);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
    L_0x01ca:
        if (r34 == 0) goto L_0x01cf;
    L_0x01cc:
        r34.release();
    L_0x01cf:
        if (r35 == 0) goto L_0x01d4;
    L_0x01d1:
        r35.finishMovie();	 Catch:{ Exception -> 0x089c }
    L_0x01d4:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "time = ";
        r5 = r5.append(r6);
        r6 = java.lang.System.currentTimeMillis();
        r6 = r6 - r50;
        r5 = r5.append(r6);
        r5 = r5.toString();
        org.telegram.messenger.FileLog.m13726e(r5);
    L_0x01f1:
        r5 = r48.edit();
        r6 = "isPreviousOk";
        r7 = 1;
        r5 = r5.putBoolean(r6, r7);
        r5.commit();
        r5 = 1;
        r0 = r54;
        r1 = r55;
        r2 = r45;
        r0.didWriteData(r1, r2, r5, r4);
        r4 = 1;
        goto L_0x00b4;
    L_0x020d:
        r18 = r15.getName();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r19 = "OMX.qcom.";
        r19 = r18.contains(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r19 == 0) goto L_0x0415;
    L_0x021a:
        r4 = 1;
        r18 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r19 = 16;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x023c;
    L_0x0225:
        r18 = "lge";
        r0 = r18;
        r18 = r10.equals(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r18 != 0) goto L_0x023b;
    L_0x0230:
        r18 = "nokia";
        r0 = r18;
        r18 = r10.equals(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r18 == 0) goto L_0x023c;
    L_0x023b:
        r11 = 1;
    L_0x023c:
        r18 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18.<init>();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r19 = "codec = ";
        r18 = r18.append(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r15 = r15.getName();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r0 = r18;
        r15 = r0.append(r15);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = " manufacturer = ";
        r0 = r18;
        r15 = r15.append(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r15 = r15.append(r10);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = "device = ";
        r0 = r18;
        r15 = r15.append(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = android.os.Build.MODEL;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r0 = r18;
        r15 = r15.append(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r15 = r15.toString();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        org.telegram.messenger.FileLog.m13726e(r15);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r44 = r8;
        r8 = r4;
    L_0x027a:
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4.<init>();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r15 = "colorFormat = ";
        r4 = r4.append(r15);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r0 = r44;
        r4 = r4.append(r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        org.telegram.messenger.FileLog.m13726e(r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = 0;
        r15 = r24 * r23;
        r15 = r15 * 3;
        r15 = r15 / 2;
        if (r8 != 0) goto L_0x044e;
    L_0x029c:
        r8 = r23 % 16;
        if (r8 == 0) goto L_0x0959;
    L_0x02a0:
        r4 = r23 % 16;
        r4 = 16 - r4;
        r4 = r4 + r23;
        r4 = r4 - r23;
        r4 = r4 * r24;
        r8 = r4 * 5;
        r8 = r8 / 4;
        r15 = r15 + r8;
        r43 = r4;
    L_0x02b1:
        r0 = r34;
        r1 = r52;
        r0.selectTrack(r1);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = 0;
        r4 = (r30 > r18 ? 1 : (r30 == r18 ? 0 : -1));
        if (r4 <= 0) goto L_0x049b;
    L_0x02be:
        r4 = 0;
        r0 = r34;
        r1 = r30;
        r0.seekTo(r1, r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
    L_0x02c6:
        r0 = r34;
        r1 = r52;
        r10 = r0.getTrackFormat(r1);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = "video/avc";
        r0 = r24;
        r1 = r23;
        r8 = android.media.MediaFormat.createVideoFormat(r4, r0, r1);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = "color-format";
        r0 = r44;
        r8.setInteger(r4, r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = "bitrate";
        if (r42 <= 0) goto L_0x04d0;
    L_0x02e6:
        r4 = r42;
    L_0x02e8:
        r0 = r18;
        r8.setInteger(r0, r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = "frame-rate";
        r18 = 25;
        r0 = r18;
        r8.setInteger(r4, r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = "i-frame-interval";
        r18 = 10;
        r0 = r18;
        r8.setInteger(r4, r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r18 = 18;
        r0 = r18;
        if (r4 >= r0) goto L_0x031b;
    L_0x0309:
        r4 = "stride";
        r18 = r24 + 32;
        r0 = r18;
        r8.setInteger(r4, r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = "slice-height";
        r0 = r23;
        r8.setInteger(r4, r0);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
    L_0x031b:
        r4 = "video/avc";
        r12 = android.media.MediaCodec.createEncoderByType(r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = 0;
        r18 = 0;
        r19 = 1;
        r0 = r18;
        r1 = r19;
        r12.configure(r8, r4, r0, r1);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r8 = 18;
        if (r4 < r8) goto L_0x0955;
    L_0x0334:
        r8 = new org.telegram.messenger.video.InputSurface;	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r4 = r12.createInputSurface();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r8.<init>(r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r8.makeCurrent();	 Catch:{ Exception -> 0x0918, all -> 0x04a7 }
        r33 = r8;
    L_0x0342:
        r12.start();	 Catch:{ Exception -> 0x091e, all -> 0x04a7 }
        r4 = "mime";
        r4 = r10.getString(r4);	 Catch:{ Exception -> 0x091e, all -> 0x04a7 }
        r4 = android.media.MediaCodec.createDecoderByType(r4);	 Catch:{ Exception -> 0x091e, all -> 0x04a7 }
        r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04e2, all -> 0x04a7 }
        r8 = 18;
        if (r7 < r8) goto L_0x04d5;
    L_0x0356:
        r32 = new org.telegram.messenger.video.OutputSurface;	 Catch:{ Exception -> 0x04e2, all -> 0x04a7 }
        r32.<init>();	 Catch:{ Exception -> 0x04e2, all -> 0x04a7 }
    L_0x035b:
        r5 = r32.getSurface();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = 0;
        r7 = 0;
        r4.configure(r10, r5, r6, r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r4.start();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = 0;
        r37 = 0;
        r5 = 0;
        r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r8 = 21;
        if (r7 >= r8) goto L_0x094f;
    L_0x0371:
        r6 = r4.getInputBuffers();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r37 = r12.getOutputBuffers();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r8 = 18;
        if (r7 >= r8) goto L_0x0949;
    L_0x037f:
        r5 = r12.getInputBuffers();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r40 = r5;
        r41 = r6;
    L_0x0387:
        r54.checkConversionCanceled();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x038a:
        if (r39 != 0) goto L_0x086e;
    L_0x038c:
        r54.checkConversionCanceled();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r13 != 0) goto L_0x0946;
    L_0x0391:
        r18 = 0;
        r5 = r34.getSampleTrackIndex();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r52;
        if (r5 != r0) goto L_0x04ff;
    L_0x039b:
        r6 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r5 = r4.dequeueInputBuffer(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r5 < 0) goto L_0x04fc;
    L_0x03a3:
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = 21;
        if (r6 >= r7) goto L_0x04ea;
    L_0x03a9:
        r6 = r41[r5];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x03ab:
        r7 = 0;
        r0 = r34;
        r7 = r0.readSampleData(r6, r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r7 >= 0) goto L_0x04f0;
    L_0x03b4:
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r10 = 4;
        r4.queueInputBuffer(r5, r6, r7, r8, r10);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = 1;
    L_0x03bd:
        r7 = r5;
        r5 = r18;
    L_0x03c0:
        if (r5 == 0) goto L_0x03d3;
    L_0x03c2:
        r8 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r5 = r4.dequeueInputBuffer(r8);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r5 < 0) goto L_0x03d3;
    L_0x03ca:
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r10 = 4;
        r4.queueInputBuffer(r5, r6, r7, r8, r10);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = 1;
    L_0x03d3:
        if (r14 != 0) goto L_0x0506;
    L_0x03d5:
        r5 = 1;
    L_0x03d6:
        r36 = 1;
        r10 = r36;
        r19 = r5;
        r13 = r37;
        r6 = r14;
        r8 = r16;
        r14 = r39;
        r5 = r38;
    L_0x03e5:
        if (r19 != 0) goto L_0x03e9;
    L_0x03e7:
        if (r10 == 0) goto L_0x0862;
    L_0x03e9:
        r54.checkConversionCanceled();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r49;
        r1 = r16;
        r17 = r12.dequeueOutputBuffer(r0, r1);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = -1;
        r0 = r17;
        r1 = r16;
        if (r0 != r1) goto L_0x0509;
    L_0x03fe:
        r10 = 0;
        r36 = r10;
        r37 = r13;
        r38 = r5;
        r39 = r14;
    L_0x0407:
        r5 = -1;
        r0 = r17;
        if (r0 == r5) goto L_0x06cd;
    L_0x040c:
        r10 = r36;
        r13 = r37;
        r5 = r38;
        r14 = r39;
        goto L_0x03e5;
    L_0x0415:
        r19 = "OMX.Intel.";
        r19 = r18.contains(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r19 == 0) goto L_0x0421;
    L_0x041e:
        r4 = 2;
        goto L_0x023c;
    L_0x0421:
        r19 = "OMX.MTK.VIDEO.ENCODER.AVC";
        r19 = r18.equals(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r19 == 0) goto L_0x042d;
    L_0x042a:
        r4 = 3;
        goto L_0x023c;
    L_0x042d:
        r19 = "OMX.SEC.AVC.Encoder";
        r19 = r18.equals(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r19 == 0) goto L_0x043a;
    L_0x0436:
        r4 = 4;
        r11 = 1;
        goto L_0x023c;
    L_0x043a:
        r19 = "OMX.TI.DUCATI1.VIDEO.H264E";
        r18 = r18.equals(r19);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r18 == 0) goto L_0x023c;
    L_0x0443:
        r4 = 5;
        goto L_0x023c;
    L_0x0446:
        r8 = 2130708361; // 0x7f000789 float:1.701803E38 double:1.0527098025E-314;
        r44 = r8;
        r8 = r4;
        goto L_0x027a;
    L_0x044e:
        r18 = 1;
        r0 = r18;
        if (r8 != r0) goto L_0x046f;
    L_0x0454:
        r8 = r10.toLowerCase();	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        r10 = "lge";
        r8 = r8.equals(r10);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r8 != 0) goto L_0x0959;
    L_0x0461:
        r4 = r24 * r23;
        r4 = r4 + 2047;
        r4 = r4 & -2048;
        r8 = r24 * r23;
        r4 = r4 - r8;
        r15 = r15 + r4;
        r43 = r4;
        goto L_0x02b1;
    L_0x046f:
        r18 = 5;
        r0 = r18;
        if (r8 != r0) goto L_0x0479;
    L_0x0475:
        r43 = r4;
        goto L_0x02b1;
    L_0x0479:
        r18 = 3;
        r0 = r18;
        if (r8 != r0) goto L_0x0959;
    L_0x047f:
        r8 = "baidu";
        r8 = r10.equals(r8);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        if (r8 == 0) goto L_0x0959;
    L_0x0488:
        r4 = r23 % 16;
        r4 = 16 - r4;
        r4 = r4 + r23;
        r4 = r4 - r23;
        r4 = r4 * r24;
        r8 = r4 * 5;
        r8 = r8 / 4;
        r15 = r15 + r8;
        r43 = r4;
        goto L_0x02b1;
    L_0x049b:
        r18 = 0;
        r4 = 0;
        r0 = r34;
        r1 = r18;
        r0.seekTo(r1, r4);	 Catch:{ Exception -> 0x017f, all -> 0x04a7 }
        goto L_0x02c6;
    L_0x04a7:
        r4 = move-exception;
    L_0x04a8:
        if (r34 == 0) goto L_0x04ad;
    L_0x04aa:
        r34.release();
    L_0x04ad:
        if (r35 == 0) goto L_0x04b2;
    L_0x04af:
        r35.finishMovie();	 Catch:{ Exception -> 0x08d6 }
    L_0x04b2:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "time = ";
        r5 = r5.append(r6);
        r6 = java.lang.System.currentTimeMillis();
        r6 = r6 - r50;
        r5 = r5.append(r6);
        r5 = r5.toString();
        org.telegram.messenger.FileLog.m13726e(r5);
        throw r4;
    L_0x04d0:
        r4 = 921600; // 0xe1000 float:1.291437E-39 double:4.55331E-318;
        goto L_0x02e8;
    L_0x04d5:
        r32 = new org.telegram.messenger.video.OutputSurface;	 Catch:{ Exception -> 0x04e2, all -> 0x04a7 }
        r0 = r32;
        r1 = r24;
        r2 = r23;
        r0.<init>(r1, r2, r5);	 Catch:{ Exception -> 0x04e2, all -> 0x04a7 }
        goto L_0x035b;
    L_0x04e2:
        r5 = move-exception;
        r7 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r33;
        goto L_0x0183;
    L_0x04ea:
        r6 = r4.getInputBuffer(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x03ab;
    L_0x04f0:
        r6 = 0;
        r8 = r34.getSampleTime();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r10 = 0;
        r4.queueInputBuffer(r5, r6, r7, r8, r10);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r34.advance();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x04fc:
        r5 = r13;
        goto L_0x03bd;
    L_0x04ff:
        r6 = -1;
        if (r5 != r6) goto L_0x0941;
    L_0x0502:
        r5 = 1;
        r7 = r13;
        goto L_0x03c0;
    L_0x0506:
        r5 = 0;
        goto L_0x03d6;
    L_0x0509:
        r16 = -3;
        r0 = r17;
        r1 = r16;
        if (r0 != r1) goto L_0x0529;
    L_0x0511:
        r16 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r18 = 21;
        r0 = r16;
        r1 = r18;
        if (r0 >= r1) goto L_0x05f1;
    L_0x051b:
        r13 = r12.getOutputBuffers();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r36 = r10;
        r37 = r13;
        r38 = r5;
        r39 = r14;
        goto L_0x0407;
    L_0x0529:
        r16 = -2;
        r0 = r17;
        r1 = r16;
        if (r0 != r1) goto L_0x054e;
    L_0x0531:
        r16 = r12.getOutputFormat();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r18 = -5;
        r0 = r18;
        if (r5 != r0) goto L_0x0544;
    L_0x053b:
        r5 = 0;
        r0 = r35;
        r1 = r16;
        r5 = r0.addTrack(r1, r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x0544:
        r36 = r10;
        r37 = r13;
        r38 = r5;
        r39 = r14;
        goto L_0x0407;
    L_0x054e:
        if (r17 >= 0) goto L_0x0575;
    L_0x0550:
        r5 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6.<init>();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = "unexpected result from encoder.dequeueOutputBuffer: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r17;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        throw r5;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x056c:
        r5 = move-exception;
        r6 = r33;
        r7 = r4;
        r4 = r5;
        r5 = r32;
        goto L_0x0183;
    L_0x0575:
        r14 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = 21;
        r0 = r16;
        if (r14 >= r0) goto L_0x05a4;
    L_0x057d:
        r14 = r13[r17];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x057f:
        if (r14 != 0) goto L_0x05ab;
    L_0x0581:
        r5 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6.<init>();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = "encoderOutputBuffer ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r17;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = " was null";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        throw r5;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x05a4:
        r0 = r17;
        r14 = r12.getOutputBuffer(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x057f;
    L_0x05ab:
        r0 = r49;
        r0 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r18 = 1;
        r0 = r16;
        r1 = r18;
        if (r0 <= r1) goto L_0x05df;
    L_0x05b9:
        r0 = r49;
        r0 = r0.flags;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r16 = r16 & 2;
        if (r16 != 0) goto L_0x05fb;
    L_0x05c3:
        r16 = 1;
        r0 = r35;
        r1 = r49;
        r2 = r16;
        r14 = r0.writeSampleData(r5, r14, r1, r2);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r14 == 0) goto L_0x05df;
    L_0x05d1:
        r14 = 0;
        r16 = 0;
        r0 = r54;
        r1 = r55;
        r2 = r45;
        r3 = r16;
        r0.didWriteData(r1, r2, r14, r3);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x05df:
        r0 = r49;
        r14 = r0.flags;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r14 = r14 & 4;
        if (r14 == 0) goto L_0x06ca;
    L_0x05e7:
        r14 = 1;
    L_0x05e8:
        r16 = 0;
        r0 = r17;
        r1 = r16;
        r12.releaseOutputBuffer(r0, r1);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x05f1:
        r36 = r10;
        r37 = r13;
        r38 = r5;
        r39 = r14;
        goto L_0x0407;
    L_0x05fb:
        r16 = -5;
        r0 = r16;
        if (r5 != r0) goto L_0x05df;
    L_0x0601:
        r0 = r49;
        r5 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = new byte[r5];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r18 = r0;
        r0 = r49;
        r5 = r0.offset;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r49;
        r0 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r5 = r5 + r16;
        r14.limit(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r49;
        r5 = r0.offset;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r14.position(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r18;
        r14.get(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r14 = 0;
        r5 = 0;
        r0 = r49;
        r0 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r16 = r16 + -1;
    L_0x062e:
        if (r16 < 0) goto L_0x0698;
    L_0x0630:
        r20 = 3;
        r0 = r16;
        r1 = r20;
        if (r0 <= r1) goto L_0x0698;
    L_0x0638:
        r20 = r18[r16];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r21 = 1;
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x06c6;
    L_0x0642:
        r20 = r16 + -1;
        r20 = r18[r20];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r20 != 0) goto L_0x06c6;
    L_0x0648:
        r20 = r16 + -2;
        r20 = r18[r20];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r20 != 0) goto L_0x06c6;
    L_0x064e:
        r20 = r16 + -3;
        r20 = r18[r20];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r20 != 0) goto L_0x06c6;
    L_0x0654:
        r5 = r16 + -3;
        r14 = java.nio.ByteBuffer.allocate(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r49;
        r5 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r20 = r16 + -3;
        r5 = r5 - r20;
        r5 = java.nio.ByteBuffer.allocate(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r20 = 0;
        r21 = r16 + -3;
        r0 = r18;
        r1 = r20;
        r2 = r21;
        r20 = r14.put(r0, r1, r2);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r21 = 0;
        r20.position(r21);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r20 = r16 + -3;
        r0 = r49;
        r0 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r21 = r0;
        r16 = r16 + -3;
        r16 = r21 - r16;
        r0 = r18;
        r1 = r20;
        r2 = r16;
        r16 = r5.put(r0, r1, r2);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r18 = 0;
        r0 = r16;
        r1 = r18;
        r0.position(r1);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x0698:
        r16 = "video/avc";
        r0 = r16;
        r1 = r24;
        r2 = r23;
        r16 = android.media.MediaFormat.createVideoFormat(r0, r1, r2);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r14 == 0) goto L_0x06bb;
    L_0x06a7:
        if (r5 == 0) goto L_0x06bb;
    L_0x06a9:
        r18 = "csd-0";
        r0 = r16;
        r1 = r18;
        r0.setByteBuffer(r1, r14);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r14 = "csd-1";
        r0 = r16;
        r0.setByteBuffer(r14, r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x06bb:
        r5 = 0;
        r0 = r35;
        r1 = r16;
        r5 = r0.addTrack(r1, r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x05df;
    L_0x06c6:
        r16 = r16 + -1;
        goto L_0x062e;
    L_0x06ca:
        r14 = 0;
        goto L_0x05e8;
    L_0x06cd:
        if (r6 != 0) goto L_0x093d;
    L_0x06cf:
        r16 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r0 = r49;
        r1 = r16;
        r10 = r4.dequeueOutputBuffer(r0, r1);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = -1;
        if (r10 != r5) goto L_0x06e9;
    L_0x06dc:
        r5 = 0;
    L_0x06dd:
        r10 = r36;
        r19 = r5;
        r13 = r37;
        r14 = r39;
        r5 = r38;
        goto L_0x03e5;
    L_0x06e9:
        r5 = -3;
        if (r10 != r5) goto L_0x06ef;
    L_0x06ec:
        r5 = r19;
        goto L_0x06dd;
    L_0x06ef:
        r5 = -2;
        if (r10 != r5) goto L_0x0710;
    L_0x06f2:
        r5 = r4.getOutputFormat();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r10.<init>();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r13 = "newFormat = ";
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = r10.append(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        org.telegram.messenger.FileLog.m13726e(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = r19;
        goto L_0x06dd;
    L_0x0710:
        if (r10 >= 0) goto L_0x072c;
    L_0x0712:
        r5 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6.<init>();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = "unexpected result from decoder.dequeueOutputBuffer: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r6.append(r10);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        throw r5;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x072c:
        r5 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r13 = 18;
        if (r5 < r13) goto L_0x07e6;
    L_0x0732:
        r0 = r49;
        r5 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r5 == 0) goto L_0x07e3;
    L_0x0738:
        r5 = 1;
    L_0x0739:
        r16 = 0;
        r13 = (r46 > r16 ? 1 : (r46 == r16 ? 0 : -1));
        if (r13 <= 0) goto L_0x0756;
    L_0x073f:
        r0 = r49;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r13 = (r16 > r46 ? 1 : (r16 == r46 ? 0 : -1));
        if (r13 < 0) goto L_0x0756;
    L_0x0749:
        r7 = 1;
        r6 = 1;
        r5 = 0;
        r0 = r49;
        r13 = r0.flags;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r13 = r13 | 4;
        r0 = r49;
        r0.flags = r13;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x0756:
        r25 = r6;
        r26 = r7;
        r6 = 0;
        r6 = (r30 > r6 ? 1 : (r30 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x0939;
    L_0x0760:
        r6 = -1;
        r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r6 != 0) goto L_0x0939;
    L_0x0766:
        r0 = r49;
        r6 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = (r6 > r30 ? 1 : (r6 == r30 ? 0 : -1));
        if (r6 >= 0) goto L_0x07fe;
    L_0x076e:
        r5 = 0;
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6.<init>();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = "drop frame startTime = ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r30;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = " present time = ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r49;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r0 = r16;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        org.telegram.messenger.FileLog.m13726e(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r28 = r8;
    L_0x079d:
        r4.releaseOutputBuffer(r10, r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r5 == 0) goto L_0x07c3;
    L_0x07a2:
        r5 = 0;
        r32.awaitNewImage();	 Catch:{ Exception -> 0x0805, all -> 0x04a7 }
    L_0x07a6:
        if (r5 != 0) goto L_0x07c3;
    L_0x07a8:
        r5 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = 18;
        if (r5 < r6) goto L_0x080b;
    L_0x07ae:
        r5 = 0;
        r0 = r32;
        r0.drawImage(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r0 = r49;
        r6 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 * r8;
        r0 = r33;
        r0.setPresentationTime(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r33.swapBuffers();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x07c3:
        r0 = r49;
        r5 = r0.flags;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = r5 & 4;
        if (r5 == 0) goto L_0x092f;
    L_0x07cb:
        r5 = 0;
        r6 = "decoder stream end";
        org.telegram.messenger.FileLog.m13726e(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = 18;
        if (r6 < r7) goto L_0x0841;
    L_0x07d8:
        r12.signalEndOfInputStream();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r25;
        r7 = r26;
        r8 = r28;
        goto L_0x06dd;
    L_0x07e3:
        r5 = 0;
        goto L_0x0739;
    L_0x07e6:
        r0 = r49;
        r5 = r0.size;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r5 != 0) goto L_0x07f8;
    L_0x07ec:
        r0 = r49;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r20 = 0;
        r5 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r5 == 0) goto L_0x07fb;
    L_0x07f8:
        r5 = 1;
        goto L_0x0739;
    L_0x07fb:
        r5 = 0;
        goto L_0x0739;
    L_0x07fe:
        r0 = r49;
        r8 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r28 = r8;
        goto L_0x079d;
    L_0x0805:
        r6 = move-exception;
        r5 = 1;
        org.telegram.messenger.FileLog.m13728e(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x07a6;
    L_0x080b:
        r6 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r13 = r12.dequeueInputBuffer(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r13 < 0) goto L_0x083a;
    L_0x0813:
        r5 = 1;
        r0 = r32;
        r0.drawImage(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r5 = r32.getFrame();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6 = r40[r13];	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r6.clear();	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r7 = r44;
        r8 = r24;
        r9 = r23;
        r10 = r43;
        org.telegram.messenger.Utilities.convertVideoFrame(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r14 = 0;
        r0 = r49;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r16 = r0;
        r18 = 0;
        r12.queueInputBuffer(r13, r14, r15, r16, r18);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x07c3;
    L_0x083a:
        r5 = "input buffer not available";
        org.telegram.messenger.FileLog.m13726e(r5);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        goto L_0x07c3;
    L_0x0841:
        r6 = 2500; // 0x9c4 float:3.503E-42 double:1.235E-320;
        r17 = r12.dequeueInputBuffer(r6);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        if (r17 < 0) goto L_0x085a;
    L_0x0849:
        r18 = 0;
        r19 = 1;
        r0 = r49;
        r0 = r0.presentationTimeUs;	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
        r20 = r0;
        r22 = 4;
        r16 = r12;
        r16.queueInputBuffer(r17, r18, r19, r20, r22);	 Catch:{ Exception -> 0x056c, all -> 0x04a7 }
    L_0x085a:
        r6 = r25;
        r7 = r26;
        r8 = r28;
        goto L_0x06dd;
    L_0x0862:
        r37 = r13;
        r38 = r5;
        r39 = r14;
        r16 = r8;
        r14 = r6;
        r13 = r7;
        goto L_0x038a;
    L_0x086e:
        r6 = -1;
        r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x092b;
    L_0x0874:
        r6 = r16;
    L_0x0876:
        r30 = r6;
        r7 = r4;
        r4 = r27;
        goto L_0x018b;
    L_0x087d:
        r15 = 0;
        r5 = r54;
        r6 = r55;
        r7 = r34;
        r8 = r35;
        r9 = r49;
        r10 = r30;
        r12 = r46;
        r14 = r45;
        r10 = r5.readAndWriteTrack(r6, r7, r8, r9, r10, r12, r14, r15);	 Catch:{ Exception -> 0x0911, all -> 0x04a7 }
        r4 = -1;
        r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0925;
    L_0x0898:
        r4 = r27;
        goto L_0x01b1;
    L_0x089c:
        r5 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r5);
        goto L_0x01d4;
    L_0x08a2:
        r4 = move-exception;
        r5 = r4;
    L_0x08a4:
        r4 = 1;
        org.telegram.messenger.FileLog.m13728e(r5);	 Catch:{ all -> 0x0905 }
        if (r6 == 0) goto L_0x08ad;
    L_0x08aa:
        r6.release();
    L_0x08ad:
        if (r7 == 0) goto L_0x08b2;
    L_0x08af:
        r7.finishMovie();	 Catch:{ Exception -> 0x08d1 }
    L_0x08b2:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "time = ";
        r5 = r5.append(r6);
        r6 = java.lang.System.currentTimeMillis();
        r6 = r6 - r50;
        r5 = r5.append(r6);
        r5 = r5.toString();
        org.telegram.messenger.FileLog.m13726e(r5);
        goto L_0x01f1;
    L_0x08d1:
        r5 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r5);
        goto L_0x08b2;
    L_0x08d6:
        r5 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r5);
        goto L_0x04b2;
    L_0x08dc:
        r4 = r48.edit();
        r5 = "isPreviousOk";
        r6 = 1;
        r4 = r4.putBoolean(r5, r6);
        r4.commit();
        r4 = 1;
        r5 = 1;
        r0 = r54;
        r1 = r55;
        r2 = r45;
        r0.didWriteData(r1, r2, r4, r5);
        r4 = 0;
        goto L_0x00b4;
    L_0x08f9:
        r4 = move-exception;
        r34 = r6;
        r35 = r7;
        goto L_0x04a8;
    L_0x0900:
        r4 = move-exception;
        r34 = r6;
        goto L_0x04a8;
    L_0x0905:
        r4 = move-exception;
        r34 = r6;
        r35 = r7;
        goto L_0x04a8;
    L_0x090c:
        r4 = move-exception;
        r5 = r4;
        r7 = r35;
        goto L_0x08a4;
    L_0x0911:
        r4 = move-exception;
        r5 = r4;
        r6 = r34;
        r7 = r35;
        goto L_0x08a4;
    L_0x0918:
        r4 = move-exception;
        r5 = r6;
        r7 = r9;
        r6 = r8;
        goto L_0x0183;
    L_0x091e:
        r4 = move-exception;
        r5 = r6;
        r7 = r9;
        r6 = r33;
        goto L_0x0183;
    L_0x0925:
        r10 = r30;
        r4 = r27;
        goto L_0x01b1;
    L_0x092b:
        r6 = r30;
        goto L_0x0876;
    L_0x092f:
        r5 = r19;
        r6 = r25;
        r7 = r26;
        r8 = r28;
        goto L_0x06dd;
    L_0x0939:
        r28 = r8;
        goto L_0x079d;
    L_0x093d:
        r5 = r19;
        goto L_0x06dd;
    L_0x0941:
        r5 = r18;
        r7 = r13;
        goto L_0x03c0;
    L_0x0946:
        r7 = r13;
        goto L_0x03d3;
    L_0x0949:
        r40 = r5;
        r41 = r6;
        goto L_0x0387;
    L_0x094f:
        r40 = r5;
        r41 = r6;
        goto L_0x0387;
    L_0x0955:
        r33 = r7;
        goto L_0x0342;
    L_0x0959:
        r43 = r4;
        goto L_0x02b1;
    L_0x095d:
        r4 = r27;
        goto L_0x01af;
    L_0x0961:
        r23 = r7;
        r24 = r6;
        r53 = r4;
        r4 = r5;
        r5 = r53;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.convertVideo(org.telegram.messenger.MessageObject):boolean");
    }

    public static String copyFileToCache(Uri uri, String str) {
        InputStream openInputStream;
        Throwable e;
        Object obj;
        Throwable th;
        String str2 = null;
        FileOutputStream fileOutputStream;
        try {
            File file;
            String fileName = getFileName(uri);
            if (fileName == null) {
                int i = UserConfig.lastLocalId;
                UserConfig.lastLocalId--;
                UserConfig.saveConfig(false);
                fileName = String.format(Locale.US, "%d.%s", new Object[]{Integer.valueOf(i), str});
            }
            openInputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            try {
                File file2 = new File(FileLoader.getInstance().getDirectory(4), "sharing/");
                file2.mkdirs();
                file = new File(file2, fileName);
                fileOutputStream = new FileOutputStream(file);
            } catch (Exception e2) {
                e = e2;
                obj = str2;
                try {
                    FileLog.m13728e(e);
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Throwable e3) {
                            FileLog.m13728e(e3);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e32) {
                            FileLog.m13728e(e32);
                        }
                    }
                    return str2;
                } catch (Throwable th2) {
                    th = th2;
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Throwable e322) {
                            FileLog.m13728e(e322);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e3222) {
                            FileLog.m13728e(e3222);
                        }
                    }
                    throw th;
                }
            } catch (Throwable e32222) {
                obj = str2;
                th = e32222;
                if (openInputStream != null) {
                    openInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
            try {
                byte[] bArr = new byte[CacheDataSink.DEFAULT_BUFFER_SIZE];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                str2 = file.getAbsolutePath();
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (Throwable e322222) {
                        FileLog.m13728e(e322222);
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable e3222222) {
                        FileLog.m13728e(e3222222);
                    }
                }
            } catch (Exception e4) {
                e3222222 = e4;
                FileLog.m13728e(e3222222);
                if (openInputStream != null) {
                    openInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return str2;
            }
        } catch (Exception e5) {
            e3222222 = e5;
            obj = str2;
            Object obj2 = str2;
            FileLog.m13728e(e3222222);
            if (openInputStream != null) {
                openInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return str2;
        } catch (Throwable e32222222) {
            fileOutputStream = str2;
            openInputStream = str2;
            th = e32222222;
            if (openInputStream != null) {
                openInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
        return str2;
    }

    private void didWriteData(MessageObject messageObject, File file, boolean z, boolean z2) {
        final boolean z3 = this.videoConvertFirstWrite;
        if (z3) {
            this.videoConvertFirstWrite = false;
        }
        final boolean z4 = z2;
        final boolean z5 = z;
        final MessageObject messageObject2 = messageObject;
        final File file2 = file;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (z4 || z5) {
                    synchronized (MediaController.this.videoConvertSync) {
                        MediaController.this.cancelCurrentVideoConversion = false;
                    }
                    MediaController.this.videoConvertQueue.remove(messageObject2);
                    MediaController.this.startVideoConvertFromQueue();
                }
                if (z4) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FilePreparingFailed, messageObject2, file2.toString());
                    return;
                }
                if (z3) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FilePreparingStarted, messageObject2, file2.toString());
                }
                NotificationCenter instance = NotificationCenter.getInstance();
                int i = NotificationCenter.FileNewChunkAvailable;
                Object[] objArr = new Object[3];
                objArr[0] = messageObject2;
                objArr[1] = file2.toString();
                objArr[2] = Long.valueOf(z5 ? file2.length() : 0);
                instance.postNotificationName(i, objArr);
            }
        });
    }

    private int getCurrentDownloadMask() {
        int i = 0;
        if (this.globalAutodownloadEnabled) {
            int i2;
            int i3;
            if (ConnectionsManager.isConnectedToWiFi()) {
                i2 = 0;
                while (i2 < 4) {
                    i3 = this.wifiDownloadMask[i2] | i;
                    i2++;
                    i = i3;
                }
            } else if (ConnectionsManager.isRoaming()) {
                i2 = 0;
                while (i2 < 4) {
                    i3 = this.roamingDownloadMask[i2] | i;
                    i2++;
                    i = i3;
                }
            } else {
                i2 = 0;
                while (i2 < 4) {
                    i3 = this.mobileDataDownloadMask[i2] | i;
                    i2++;
                    i = i3;
                }
            }
        }
        return i;
    }

    public static String getFileName(Uri uri) {
        Cursor query;
        Throwable e;
        String str;
        int lastIndexOf;
        Cursor cursor = null;
        if (uri.getScheme().equals(C1797b.CONTENT)) {
            try {
                query = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            } catch (Exception e2) {
                e = e2;
                query = null;
                try {
                    FileLog.m13728e(e);
                    if (query != null) {
                        query.close();
                        str = null;
                        if (str == null) {
                            return str;
                        }
                        str = uri.getPath();
                        lastIndexOf = str.lastIndexOf(47);
                        return lastIndexOf == -1 ? str.substring(lastIndexOf + 1) : str;
                    }
                    str = null;
                    if (str == null) {
                        return str;
                    }
                    str = uri.getPath();
                    lastIndexOf = str.lastIndexOf(47);
                    if (lastIndexOf == -1) {
                    }
                } catch (Throwable th) {
                    e = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw e;
            }
            try {
                if (query.moveToFirst()) {
                    cursor = query.getString(query.getColumnIndex("_display_name"));
                }
                if (query != null) {
                    query.close();
                    str = cursor;
                } else {
                    Object obj = cursor;
                }
            } catch (Exception e3) {
                e = e3;
                FileLog.m13728e(e);
                if (query != null) {
                    query.close();
                    str = null;
                    if (str == null) {
                        return str;
                    }
                    str = uri.getPath();
                    lastIndexOf = str.lastIndexOf(47);
                    if (lastIndexOf == -1) {
                    }
                }
                str = null;
                if (str == null) {
                    return str;
                }
                str = uri.getPath();
                lastIndexOf = str.lastIndexOf(47);
                if (lastIndexOf == -1) {
                }
            }
            if (str == null) {
                return str;
            }
            str = uri.getPath();
            lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf == -1) {
            }
        }
        str = null;
        if (str == null) {
            return str;
        }
        str = uri.getPath();
        lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf == -1) {
        }
    }

    public static MediaController getInstance() {
        MediaController mediaController = Instance;
        if (mediaController == null) {
            synchronized (MediaController.class) {
                mediaController = Instance;
                if (mediaController == null) {
                    mediaController = new MediaController();
                    Instance = mediaController;
                }
            }
        }
        return mediaController;
    }

    private native long getTotalPcmDuration();

    public static boolean isGif(Uri uri) {
        boolean z = false;
        InputStream inputStream = null;
        try {
            inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            byte[] bArr = new byte[3];
            if (inputStream.read(bArr, 0, 3) == 3) {
                String str = new String(bArr);
                if (str != null && str.equalsIgnoreCase("gif")) {
                    z = true;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                    return z;
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e222) {
                    FileLog.m13728e(e222);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2222) {
                    FileLog.m13728e(e2222);
                }
            }
        }
        return z;
    }

    private boolean isNearToSensor(float f) {
        return f < 5.0f && f != this.proximitySensor.getMaximumRange();
    }

    private native int isOpusFile(String str);

    private static boolean isRecognizedFormat(int i) {
        switch (i) {
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

    public static boolean isWebp(Uri uri) {
        boolean z = false;
        InputStream inputStream = null;
        try {
            inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
            byte[] bArr = new byte[12];
            if (inputStream.read(bArr, 0, 12) == 12) {
                String str = new String(bArr);
                if (str != null) {
                    String toLowerCase = str.toLowerCase();
                    if (toLowerCase.startsWith("riff") && toLowerCase.endsWith("webp")) {
                        z = true;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                        return z;
                    }
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e222) {
                    FileLog.m13728e(e222);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable e2222) {
                    FileLog.m13728e(e2222);
                }
            }
        }
        return z;
    }

    public static void loadGalleryPhotosAlbums(final int i) {
        Thread thread = new Thread(new Runnable() {

            /* renamed from: org.telegram.messenger.MediaController$28$1 */
            class C31491 implements Comparator<PhotoEntry> {
                C31491() {
                }

                public int compare(PhotoEntry photoEntry, PhotoEntry photoEntry2) {
                    return photoEntry.dateTaken < photoEntry2.dateTaken ? 1 : photoEntry.dateTaken > photoEntry2.dateTaken ? -1 : 0;
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r30 = this;
                r17 = new java.util.ArrayList;
                r17.<init>();
                r18 = new java.util.ArrayList;
                r18.<init>();
                r19 = new java.util.HashMap;
                r19.<init>();
                r20 = new java.util.HashMap;
                r20.<init>();
                r13 = 0;
                r10 = 0;
                r2 = 0;
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x017f }
                r3.<init>();	 Catch:{ Exception -> 0x017f }
                r4 = android.os.Environment.DIRECTORY_DCIM;	 Catch:{ Exception -> 0x017f }
                r4 = android.os.Environment.getExternalStoragePublicDirectory(r4);	 Catch:{ Exception -> 0x017f }
                r4 = r4.getAbsolutePath();	 Catch:{ Exception -> 0x017f }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x017f }
                r4 = "/Camera/";
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x017f }
                r2 = r3.toString();	 Catch:{ Exception -> 0x017f }
                r11 = r2;
            L_0x0036:
                r9 = 0;
                r14 = 0;
                r8 = 0;
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r3 = 23;
                if (r2 < r3) goto L_0x0050;
            L_0x003f:
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r3 = 23;
                if (r2 < r3) goto L_0x0398;
            L_0x0045:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r3 = "android.permission.READ_EXTERNAL_STORAGE";
                r2 = r2.checkSelfPermission(r3);	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                if (r2 != 0) goto L_0x0398;
            L_0x0050:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r2 = r2.getContentResolver();	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r3 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r4 = org.telegram.messenger.MediaController.projectionPhotos;	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                r5 = 0;
                r6 = 0;
                r7 = "datetaken DESC";
                r12 = android.provider.MediaStore.Images.Media.query(r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x029d, all -> 0x02ba }
                if (r12 == 0) goto L_0x0392;
            L_0x0067:
                r2 = "_id";
                r21 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r2 = "bucket_id";
                r22 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r2 = "bucket_display_name";
                r23 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r2 = "_data";
                r24 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r2 = "datetaken";
                r25 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r2 = "orientation";
                r26 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x0345, all -> 0x033e }
                r16 = r14;
                r15 = r13;
                r14 = r10;
                r13 = r9;
            L_0x0096:
                r2 = r12.moveToNext();	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                if (r2 == 0) goto L_0x0196;
            L_0x009c:
                r0 = r21;
                r5 = r12.getInt(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r0 = r22;
                r4 = r12.getInt(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r0 = r23;
                r27 = r12.getString(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r0 = r24;
                r8 = r12.getString(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r0 = r25;
                r6 = r12.getLong(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r0 = r26;
                r9 = r12.getInt(r0);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                if (r8 == 0) goto L_0x0096;
            L_0x00c2:
                r2 = r8.length();	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                if (r2 == 0) goto L_0x0096;
            L_0x00c8:
                r3 = new org.telegram.messenger.MediaController$PhotoEntry;	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r10 = 0;
                r3.<init>(r4, r5, r6, r8, r9, r10);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                if (r15 != 0) goto L_0x038f;
            L_0x00d0:
                r5 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r2 = 0;
                r6 = "AllPhotos";
                r7 = 2131230862; // 0x7f08008e float:1.8077789E38 double:1.0529679523E-314;
                r6 = org.telegram.messenger.LocaleController.getString(r6, r7);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r5.<init>(r2, r6, r3);	 Catch:{ Throwable -> 0x0353, all -> 0x033e }
                r2 = 0;
                r0 = r18;
                r0.add(r2, r5);	 Catch:{ Throwable -> 0x035a, all -> 0x033e }
                r7 = r5;
            L_0x00e7:
                if (r14 != 0) goto L_0x038c;
            L_0x00e9:
                r5 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x0361, all -> 0x033e }
                r2 = 0;
                r6 = "AllMedia";
                r9 = 2131230861; // 0x7f08008d float:1.8077787E38 double:1.052967952E-314;
                r6 = org.telegram.messenger.LocaleController.getString(r6, r9);	 Catch:{ Throwable -> 0x0361, all -> 0x033e }
                r5.<init>(r2, r6, r3);	 Catch:{ Throwable -> 0x0361, all -> 0x033e }
                r2 = 0;
                r0 = r17;
                r0.add(r2, r5);	 Catch:{ Throwable -> 0x0368, all -> 0x033e }
                r6 = r5;
            L_0x0100:
                r7.addPhoto(r3);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r6.addPhoto(r3);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r2 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r0 = r19;
                r2 = r0.get(r2);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r2 = (org.telegram.messenger.MediaController.AlbumEntry) r2;	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                if (r2 != 0) goto L_0x018b;
            L_0x0114:
                r2 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r0 = r27;
                r2.<init>(r4, r0, r3);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r5 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r0 = r19;
                r0.put(r5, r2);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                if (r13 != 0) goto L_0x0186;
            L_0x0126:
                if (r11 == 0) goto L_0x0186;
            L_0x0128:
                if (r8 == 0) goto L_0x0186;
            L_0x012a:
                r5 = r8.startsWith(r11);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                if (r5 == 0) goto L_0x0186;
            L_0x0130:
                r5 = 0;
                r0 = r17;
                r0.add(r5, r2);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r13 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
                r5 = r13;
            L_0x013b:
                r2.addPhoto(r3);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r2 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r0 = r20;
                r2 = r0.get(r2);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r2 = (org.telegram.messenger.MediaController.AlbumEntry) r2;	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                if (r2 != 0) goto L_0x0387;
            L_0x014c:
                r2 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r0 = r27;
                r2.<init>(r4, r0, r3);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r9 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r0 = r20;
                r0.put(r9, r2);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                if (r16 != 0) goto L_0x018d;
            L_0x015e:
                if (r11 == 0) goto L_0x018d;
            L_0x0160:
                if (r8 == 0) goto L_0x018d;
            L_0x0162:
                r8 = r8.startsWith(r11);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                if (r8 == 0) goto L_0x018d;
            L_0x0168:
                r8 = 0;
                r0 = r18;
                r0.add(r8, r2);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r16 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r4 = r2;
                r2 = r16;
            L_0x0175:
                r4.addPhoto(r3);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r16 = r2;
                r13 = r5;
                r14 = r6;
                r15 = r7;
                goto L_0x0096;
            L_0x017f:
                r3 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r3);
                r11 = r2;
                goto L_0x0036;
            L_0x0186:
                r0 = r17;
                r0.add(r2);	 Catch:{ Throwable -> 0x036e, all -> 0x033e }
            L_0x018b:
                r5 = r13;
                goto L_0x013b;
            L_0x018d:
                r0 = r18;
                r0.add(r2);	 Catch:{ Throwable -> 0x034c, all -> 0x033e }
                r4 = r2;
                r2 = r16;
                goto L_0x0175;
            L_0x0196:
                r3 = r12;
                r4 = r13;
                r5 = r14;
                r6 = r15;
            L_0x019a:
                if (r3 == 0) goto L_0x0381;
            L_0x019c:
                r3.close();	 Catch:{ Exception -> 0x0293 }
                r8 = r3;
                r9 = r4;
                r10 = r5;
                r15 = r6;
            L_0x01a3:
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r3 = 23;
                if (r2 < r3) goto L_0x01ba;
            L_0x01a9:
                r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r3 = 23;
                if (r2 < r3) goto L_0x037d;
            L_0x01af:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r3 = "android.permission.READ_EXTERNAL_STORAGE";
                r2 = r2.checkSelfPermission(r3);	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                if (r2 != 0) goto L_0x037d;
            L_0x01ba:
                r2 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r2 = r2.getContentResolver();	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r3 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r4 = org.telegram.messenger.MediaController.projectionVideo;	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                r5 = 0;
                r6 = 0;
                r7 = "datetaken DESC";
                r12 = android.provider.MediaStore.Images.Media.query(r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x02fb, all -> 0x030d }
                if (r12 == 0) goto L_0x0378;
            L_0x01d1:
                r2 = "_id";
                r16 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r2 = "bucket_id";
                r20 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r2 = "bucket_display_name";
                r21 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r2 = "_data";
                r22 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r2 = "datetaken";
                r23 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r2 = "duration";
                r24 = r12.getColumnIndex(r2);	 Catch:{ Throwable -> 0x032d, all -> 0x0328 }
                r13 = r9;
                r14 = r10;
            L_0x01fd:
                r2 = r12.moveToNext();	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                if (r2 == 0) goto L_0x02ce;
            L_0x0203:
                r0 = r16;
                r5 = r12.getInt(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r0 = r20;
                r4 = r12.getInt(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r0 = r21;
                r25 = r12.getString(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r0 = r22;
                r8 = r12.getString(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r0 = r23;
                r6 = r12.getLong(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r0 = r24;
                r26 = r12.getLong(r0);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                if (r8 == 0) goto L_0x01fd;
            L_0x0229:
                r2 = r8.length();	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                if (r2 == 0) goto L_0x01fd;
            L_0x022f:
                r3 = new org.telegram.messenger.MediaController$PhotoEntry;	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r28 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
                r26 = r26 / r28;
                r0 = r26;
                r9 = (int) r0;	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r10 = 1;
                r3.<init>(r4, r5, r6, r8, r9, r10);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                if (r14 != 0) goto L_0x0375;
            L_0x023e:
                r6 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r2 = 0;
                r5 = "AllMedia";
                r7 = 2131230861; // 0x7f08008d float:1.8077787E38 double:1.052967952E-314;
                r5 = org.telegram.messenger.LocaleController.getString(r5, r7);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r6.<init>(r2, r5, r3);	 Catch:{ Throwable -> 0x0332, all -> 0x0328 }
                r2 = 0;
                r0 = r17;
                r0.add(r2, r6);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
            L_0x0254:
                r6.addPhoto(r3);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r2 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r0 = r19;
                r2 = r0.get(r2);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r2 = (org.telegram.messenger.MediaController.AlbumEntry) r2;	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                if (r2 != 0) goto L_0x02cc;
            L_0x0265:
                r2 = new org.telegram.messenger.MediaController$AlbumEntry;	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r0 = r25;
                r2.<init>(r4, r0, r3);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r5 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r0 = r19;
                r0.put(r5, r2);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                if (r13 != 0) goto L_0x02c7;
            L_0x0277:
                if (r11 == 0) goto L_0x02c7;
            L_0x0279:
                if (r8 == 0) goto L_0x02c7;
            L_0x027b:
                r5 = r8.startsWith(r11);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                if (r5 == 0) goto L_0x02c7;
            L_0x0281:
                r5 = 0;
                r0 = r17;
                r0.add(r5, r2);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r13 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
                r5 = r13;
            L_0x028c:
                r2.addPhoto(r3);	 Catch:{ Throwable -> 0x033b, all -> 0x0328 }
                r13 = r5;
                r14 = r6;
                goto L_0x01fd;
            L_0x0293:
                r2 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r2);
                r8 = r3;
                r9 = r4;
                r10 = r5;
                r15 = r6;
                goto L_0x01a3;
            L_0x029d:
                r2 = move-exception;
                r3 = r8;
                r4 = r9;
                r5 = r10;
                r6 = r13;
            L_0x02a2:
                org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x0341 }
                if (r3 == 0) goto L_0x0381;
            L_0x02a7:
                r3.close();	 Catch:{ Exception -> 0x02b0 }
                r8 = r3;
                r9 = r4;
                r10 = r5;
                r15 = r6;
                goto L_0x01a3;
            L_0x02b0:
                r2 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r2);
                r8 = r3;
                r9 = r4;
                r10 = r5;
                r15 = r6;
                goto L_0x01a3;
            L_0x02ba:
                r2 = move-exception;
                r12 = r8;
            L_0x02bc:
                if (r12 == 0) goto L_0x02c1;
            L_0x02be:
                r12.close();	 Catch:{ Exception -> 0x02c2 }
            L_0x02c1:
                throw r2;
            L_0x02c2:
                r3 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r3);
                goto L_0x02c1;
            L_0x02c7:
                r0 = r17;
                r0.add(r2);	 Catch:{ Throwable -> 0x0337, all -> 0x0328 }
            L_0x02cc:
                r5 = r13;
                goto L_0x028c;
            L_0x02ce:
                r8 = r12;
                r5 = r13;
                r6 = r14;
            L_0x02d1:
                if (r8 == 0) goto L_0x02d6;
            L_0x02d3:
                r8.close();	 Catch:{ Exception -> 0x02f6 }
            L_0x02d6:
                r2 = 0;
                r3 = r2;
            L_0x02d8:
                r2 = r17.size();
                if (r3 >= r2) goto L_0x031a;
            L_0x02de:
                r0 = r17;
                r2 = r0.get(r3);
                r2 = (org.telegram.messenger.MediaController.AlbumEntry) r2;
                r2 = r2.photos;
                r4 = new org.telegram.messenger.MediaController$28$1;
                r0 = r30;
                r4.<init>();
                java.util.Collections.sort(r2, r4);
                r2 = r3 + 1;
                r3 = r2;
                goto L_0x02d8;
            L_0x02f6:
                r2 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r2);
                goto L_0x02d6;
            L_0x02fb:
                r2 = move-exception;
                r3 = r8;
                r5 = r9;
                r6 = r10;
            L_0x02ff:
                org.telegram.messenger.FileLog.m13728e(r2);	 Catch:{ all -> 0x032a }
                if (r3 == 0) goto L_0x02d6;
            L_0x0304:
                r3.close();	 Catch:{ Exception -> 0x0308 }
                goto L_0x02d6;
            L_0x0308:
                r2 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r2);
                goto L_0x02d6;
            L_0x030d:
                r2 = move-exception;
                r12 = r8;
            L_0x030f:
                if (r12 == 0) goto L_0x0314;
            L_0x0311:
                r12.close();	 Catch:{ Exception -> 0x0315 }
            L_0x0314:
                throw r2;
            L_0x0315:
                r3 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r3);
                goto L_0x0314;
            L_0x031a:
                r0 = r30;
                r2 = r2;
                r8 = 0;
                r3 = r17;
                r4 = r18;
                r7 = r15;
                org.telegram.messenger.MediaController.broadcastNewPhotos(r2, r3, r4, r5, r6, r7, r8);
                return;
            L_0x0328:
                r2 = move-exception;
                goto L_0x030f;
            L_0x032a:
                r2 = move-exception;
                r12 = r3;
                goto L_0x030f;
            L_0x032d:
                r2 = move-exception;
                r3 = r12;
                r5 = r9;
                r6 = r10;
                goto L_0x02ff;
            L_0x0332:
                r2 = move-exception;
                r3 = r12;
                r5 = r13;
                r6 = r14;
                goto L_0x02ff;
            L_0x0337:
                r2 = move-exception;
                r3 = r12;
                r5 = r13;
                goto L_0x02ff;
            L_0x033b:
                r2 = move-exception;
                r3 = r12;
                goto L_0x02ff;
            L_0x033e:
                r2 = move-exception;
                goto L_0x02bc;
            L_0x0341:
                r2 = move-exception;
                r12 = r3;
                goto L_0x02bc;
            L_0x0345:
                r2 = move-exception;
                r3 = r12;
                r4 = r9;
                r5 = r10;
                r6 = r13;
                goto L_0x02a2;
            L_0x034c:
                r2 = move-exception;
                r3 = r12;
                r4 = r5;
                r5 = r6;
                r6 = r7;
                goto L_0x02a2;
            L_0x0353:
                r2 = move-exception;
                r3 = r12;
                r4 = r13;
                r5 = r14;
                r6 = r15;
                goto L_0x02a2;
            L_0x035a:
                r2 = move-exception;
                r3 = r12;
                r4 = r13;
                r6 = r5;
                r5 = r14;
                goto L_0x02a2;
            L_0x0361:
                r2 = move-exception;
                r3 = r12;
                r4 = r13;
                r5 = r14;
                r6 = r7;
                goto L_0x02a2;
            L_0x0368:
                r2 = move-exception;
                r3 = r12;
                r4 = r13;
                r6 = r7;
                goto L_0x02a2;
            L_0x036e:
                r2 = move-exception;
                r3 = r12;
                r4 = r13;
                r5 = r6;
                r6 = r7;
                goto L_0x02a2;
            L_0x0375:
                r6 = r14;
                goto L_0x0254;
            L_0x0378:
                r8 = r12;
                r5 = r9;
                r6 = r10;
                goto L_0x02d1;
            L_0x037d:
                r5 = r9;
                r6 = r10;
                goto L_0x02d1;
            L_0x0381:
                r8 = r3;
                r9 = r4;
                r10 = r5;
                r15 = r6;
                goto L_0x01a3;
            L_0x0387:
                r4 = r2;
                r2 = r16;
                goto L_0x0175;
            L_0x038c:
                r6 = r14;
                goto L_0x0100;
            L_0x038f:
                r7 = r15;
                goto L_0x00e7;
            L_0x0392:
                r3 = r12;
                r4 = r9;
                r5 = r10;
                r6 = r13;
                goto L_0x019a;
            L_0x0398:
                r3 = r8;
                r4 = r9;
                r5 = r10;
                r6 = r13;
                goto L_0x019a;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.28.run():void");
            }
        });
        thread.setPriority(1);
        thread.start();
    }

    public static int maskToIndex(int i) {
        return i == 1 ? 0 : i == 2 ? 1 : i == 4 ? 2 : i == 8 ? 3 : i == 16 ? 4 : i == 32 ? 5 : i == 64 ? 6 : 0;
    }

    private native int openOpusFile(String str);

    private void playNextMessageWithoutOrder(boolean z) {
        ArrayList arrayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (z && this.repeatMode == 2 && !this.forceLoopCurrentPlaylist) {
            cleanupPlayer(false, false);
            playMessage((MessageObject) arrayList.get(this.currentPlaylistNum));
            return;
        }
        boolean z2;
        if (this.playOrderReversed) {
            this.currentPlaylistNum++;
            if (this.currentPlaylistNum >= arrayList.size()) {
                this.currentPlaylistNum = 0;
                z2 = true;
            }
            z2 = false;
        } else {
            this.currentPlaylistNum--;
            if (this.currentPlaylistNum < 0) {
                this.currentPlaylistNum = arrayList.size() - 1;
                z2 = true;
            }
            z2 = false;
        }
        if (z2 && z && this.repeatMode == 0 && !this.forceLoopCurrentPlaylist) {
            if (this.audioPlayer != null || this.audioTrackPlayer != null || this.videoPlayer != null) {
                if (this.audioPlayer != null) {
                    try {
                        this.audioPlayer.reset();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                    try {
                        this.audioPlayer.stop();
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                    try {
                        this.audioPlayer.release();
                    } catch (Throwable e22) {
                        FileLog.m13728e(e22);
                    }
                    this.audioPlayer = null;
                } else if (this.audioTrackPlayer != null) {
                    synchronized (this.playerObjectSync) {
                        try {
                            this.audioTrackPlayer.pause();
                            this.audioTrackPlayer.flush();
                        } catch (Throwable e222) {
                            FileLog.m13728e(e222);
                        }
                        try {
                            this.audioTrackPlayer.release();
                        } catch (Throwable e2222) {
                            FileLog.m13728e(e2222);
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
                        FileLog.m13728e(e22222);
                    }
                }
                stopProgressTimer();
                this.lastProgress = 0;
                this.buffersWrited = 0;
                this.isPaused = true;
                this.playingMessageObject.audioProgress = BitmapDescriptorFactory.HUE_RED;
                this.playingMessageObject.audioProgressSec = 0;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            }
        } else if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < arrayList.size()) {
            this.playMusicAgain = true;
            playMessage((MessageObject) arrayList.get(this.currentPlaylistNum));
        }
    }

    private void processLaterArrays() {
        for (Entry entry : this.addLaterArray.entrySet()) {
            addLoadingFileObserver((String) entry.getKey(), (FileDownloadProgressListener) entry.getValue());
        }
        this.addLaterArray.clear();
        Iterator it = this.deleteLaterArray.iterator();
        while (it.hasNext()) {
            removeLoadingFileObserver((FileDownloadProgressListener) it.next());
        }
        this.deleteLaterArray.clear();
    }

    private void processMediaObserver(Uri uri) {
        try {
            android.graphics.Point realScreenSize = AndroidUtilities.getRealScreenSize();
            Cursor query = ApplicationLoader.applicationContext.getContentResolver().query(uri, this.mediaProjections, null, null, "date_added DESC LIMIT 1");
            final ArrayList arrayList = new ArrayList();
            if (query != null) {
                while (query.moveToNext()) {
                    String str = TtmlNode.ANONYMOUS_REGION_ID;
                    String string = query.getString(0);
                    String string2 = query.getString(1);
                    String string3 = query.getString(2);
                    long j = query.getLong(3);
                    String string4 = query.getString(4);
                    int i = query.getInt(5);
                    int i2 = query.getInt(6);
                    if ((string != null && string.toLowerCase().contains("screenshot")) || ((string2 != null && string2.toLowerCase().contains("screenshot")) || ((string3 != null && string3.toLowerCase().contains("screenshot")) || (string4 != null && string4.toLowerCase().contains("screenshot"))))) {
                        if (i == 0 || i2 == 0) {
                            try {
                                Options options = new Options();
                                options.inJustDecodeBounds = true;
                                BitmapFactory.decodeFile(string, options);
                                i = options.outWidth;
                                i2 = options.outHeight;
                            } catch (Exception e) {
                                arrayList.add(Long.valueOf(j));
                            }
                        }
                        if (i <= 0 || r0 <= 0 || ((i == realScreenSize.x && r0 == realScreenSize.y) || (r0 == realScreenSize.x && i == realScreenSize.y))) {
                            arrayList.add(Long.valueOf(j));
                        }
                    }
                }
                query.close();
            }
            if (!arrayList.isEmpty()) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.screenshotTook, new Object[0]);
                        MediaController.this.checkScreenshots(arrayList);
                    }
                });
            }
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
    }

    private long readAndWriteTrack(MessageObject messageObject, MediaExtractor mediaExtractor, MP4Builder mP4Builder, BufferInfo bufferInfo, long j, long j2, File file, boolean z) {
        int selectTrack = selectTrack(mediaExtractor, z);
        if (selectTrack < 0) {
            return -1;
        }
        mediaExtractor.selectTrack(selectTrack);
        MediaFormat trackFormat = mediaExtractor.getTrackFormat(selectTrack);
        int addTrack = mP4Builder.addTrack(trackFormat, z);
        int integer = trackFormat.getInteger("max-input-size");
        Object obj = null;
        if (j > 0) {
            mediaExtractor.seekTo(j, 0);
        } else {
            mediaExtractor.seekTo(0, 0);
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(integer);
        long j3 = -1;
        checkConversionCanceled();
        while (obj == null) {
            checkConversionCanceled();
            Object obj2 = null;
            integer = mediaExtractor.getSampleTrackIndex();
            if (integer == selectTrack) {
                Object obj3;
                bufferInfo.size = mediaExtractor.readSampleData(allocateDirect, 0);
                if (VERSION.SDK_INT < 21) {
                    allocateDirect.position(0);
                    allocateDirect.limit(bufferInfo.size);
                }
                if (!z) {
                    byte[] array = allocateDirect.array();
                    if (array != null) {
                        int arrayOffset = allocateDirect.arrayOffset();
                        int limit = arrayOffset + allocateDirect.limit();
                        int i = -1;
                        while (arrayOffset <= limit - 4) {
                            if ((array[arrayOffset] != (byte) 0 || array[arrayOffset + 1] != (byte) 0 || array[arrayOffset + 2] != (byte) 0 || array[arrayOffset + 3] != (byte) 1) && arrayOffset != limit - 4) {
                                integer = i;
                            } else if (i != -1) {
                                integer = (arrayOffset - i) - (arrayOffset != limit + -4 ? 4 : 0);
                                array[i] = (byte) (integer >> 24);
                                array[i + 1] = (byte) (integer >> 16);
                                array[i + 2] = (byte) (integer >> 8);
                                array[i + 3] = (byte) integer;
                                integer = arrayOffset;
                            } else {
                                integer = arrayOffset;
                            }
                            arrayOffset++;
                            i = integer;
                        }
                    }
                }
                if (bufferInfo.size >= 0) {
                    bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                    obj3 = null;
                } else {
                    bufferInfo.size = 0;
                    obj3 = 1;
                }
                if (bufferInfo.size > 0 && obj3 == null) {
                    if (j > 0 && j3 == -1) {
                        j3 = bufferInfo.presentationTimeUs;
                    }
                    if (j2 < 0 || bufferInfo.presentationTimeUs < j2) {
                        bufferInfo.offset = 0;
                        bufferInfo.flags = mediaExtractor.getSampleFlags();
                        if (mP4Builder.writeSampleData(addTrack, allocateDirect, bufferInfo, false)) {
                            didWriteData(messageObject, file, false, false);
                            obj2 = obj3;
                            if (obj2 == null) {
                                mediaExtractor.advance();
                            }
                        }
                    } else {
                        int i2 = 1;
                        if (obj2 == null) {
                            mediaExtractor.advance();
                        }
                    }
                }
                obj2 = obj3;
                if (obj2 == null) {
                    mediaExtractor.advance();
                }
            } else if (integer == -1) {
                obj2 = 1;
            } else {
                mediaExtractor.advance();
            }
            obj = obj2 != null ? 1 : obj;
        }
        mediaExtractor.unselectTrack(selectTrack);
        return j3;
    }

    private native void readOpusFile(ByteBuffer byteBuffer, int i, int[] iArr);

    private void readSms() {
    }

    public static void saveFile(String str, Context context, int i, String str2, String str3) {
        File file;
        Throwable e;
        final int i2;
        final String str4;
        final String str5;
        if (str != null) {
            if (str == null || str.length() == 0) {
                file = null;
            } else {
                file = new File(str);
                if (!file.exists()) {
                    file = null;
                }
            }
            if (file != null) {
                final boolean[] zArr = new boolean[]{false};
                if (file.exists()) {
                    AlertDialog alertDialog;
                    if (context == null || i == 0) {
                        alertDialog = null;
                    } else {
                        try {
                            alertDialog = new AlertDialog(context, 2);
                            try {
                                alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.setCancelable(true);
                                alertDialog.setOnCancelListener(new OnCancelListener() {
                                    public void onCancel(DialogInterface dialogInterface) {
                                        zArr[0] = true;
                                    }
                                });
                                alertDialog.show();
                            } catch (Exception e2) {
                                e = e2;
                                FileLog.m13728e(e);
                                i2 = i;
                                str4 = str2;
                                str5 = str3;
                                new Thread(new Runnable() {

                                    /* renamed from: org.telegram.messenger.MediaController$27$2 */
                                    class C31482 implements Runnable {
                                        C31482() {
                                        }

                                        public void run() {
                                            try {
                                                alertDialog.dismiss();
                                            } catch (Throwable e) {
                                                FileLog.m13728e(e);
                                            }
                                        }
                                    }

                                    /* JADX WARNING: inconsistent code. */
                                    /* Code decompiled incorrectly, please refer to instructions dump. */
                                    public void run() {
                                        /*
                                        r15 = this;
                                        r0 = r1;	 Catch:{ Exception -> 0x0193 }
                                        if (r0 != 0) goto L_0x008e;
                                    L_0x0004:
                                        r0 = org.telegram.messenger.AndroidUtilities.generatePicturePath();	 Catch:{ Exception -> 0x0193 }
                                        r9 = r0;
                                    L_0x0009:
                                        r0 = r9.exists();	 Catch:{ Exception -> 0x0193 }
                                        if (r0 != 0) goto L_0x0012;
                                    L_0x000f:
                                        r9.createNewFile();	 Catch:{ Exception -> 0x0193 }
                                    L_0x0012:
                                        r1 = 0;
                                        r2 = 0;
                                        r8 = 1;
                                        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0193 }
                                        r6 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
                                        r6 = r4 - r6;
                                        r0 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0170, all -> 0x0187 }
                                        r3 = r3;	 Catch:{ Exception -> 0x0170, all -> 0x0187 }
                                        r0.<init>(r3);	 Catch:{ Exception -> 0x0170, all -> 0x0187 }
                                        r1 = r0.getChannel();	 Catch:{ Exception -> 0x0170, all -> 0x0187 }
                                        r0 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x01b5, all -> 0x0187 }
                                        r0.<init>(r9);	 Catch:{ Exception -> 0x01b5, all -> 0x0187 }
                                        r0 = r0.getChannel();	 Catch:{ Exception -> 0x01b5, all -> 0x0187 }
                                        r10 = r1.size();	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r2 = 0;
                                    L_0x0037:
                                        r4 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1));
                                        if (r4 >= 0) goto L_0x0042;
                                    L_0x003b:
                                        r4 = r4;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r5 = 0;
                                        r4 = r4[r5];	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        if (r4 == 0) goto L_0x0139;
                                    L_0x0042:
                                        if (r1 == 0) goto L_0x0047;
                                    L_0x0044:
                                        r1.close();	 Catch:{ Exception -> 0x01a2 }
                                    L_0x0047:
                                        if (r0 == 0) goto L_0x004c;
                                    L_0x0049:
                                        r0.close();	 Catch:{ Exception -> 0x016c }
                                    L_0x004c:
                                        r0 = r8;
                                    L_0x004d:
                                        r1 = r4;	 Catch:{ Exception -> 0x0193 }
                                        r2 = 0;
                                        r1 = r1[r2];	 Catch:{ Exception -> 0x0193 }
                                        if (r1 == 0) goto L_0x0058;
                                    L_0x0054:
                                        r9.delete();	 Catch:{ Exception -> 0x0193 }
                                        r0 = 0;
                                    L_0x0058:
                                        if (r0 == 0) goto L_0x0081;
                                    L_0x005a:
                                        r0 = r1;	 Catch:{ Exception -> 0x0193 }
                                        r1 = 2;
                                        if (r0 != r1) goto L_0x0199;
                                    L_0x005f:
                                        r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0193 }
                                        r1 = "download";
                                        r0 = r0.getSystemService(r1);	 Catch:{ Exception -> 0x0193 }
                                        r0 = (android.app.DownloadManager) r0;	 Catch:{ Exception -> 0x0193 }
                                        r1 = r9.getName();	 Catch:{ Exception -> 0x0193 }
                                        r2 = r9.getName();	 Catch:{ Exception -> 0x0193 }
                                        r3 = 0;
                                        r4 = r6;	 Catch:{ Exception -> 0x0193 }
                                        r5 = r9.getAbsolutePath();	 Catch:{ Exception -> 0x0193 }
                                        r6 = r9.length();	 Catch:{ Exception -> 0x0193 }
                                        r8 = 1;
                                        r0.addCompletedDownload(r1, r2, r3, r4, r5, r6, r8);	 Catch:{ Exception -> 0x0193 }
                                    L_0x0081:
                                        r0 = r5;
                                        if (r0 == 0) goto L_0x008d;
                                    L_0x0085:
                                        r0 = new org.telegram.messenger.MediaController$27$2;
                                        r0.<init>();
                                        org.telegram.messenger.AndroidUtilities.runOnUIThread(r0);
                                    L_0x008d:
                                        return;
                                    L_0x008e:
                                        r0 = r1;	 Catch:{ Exception -> 0x0193 }
                                        r1 = 1;
                                        if (r0 != r1) goto L_0x009a;
                                    L_0x0093:
                                        r0 = org.telegram.messenger.AndroidUtilities.generateVideoPath();	 Catch:{ Exception -> 0x0193 }
                                        r9 = r0;
                                        goto L_0x0009;
                                    L_0x009a:
                                        r0 = r1;	 Catch:{ Exception -> 0x0193 }
                                        r1 = 2;
                                        if (r0 != r1) goto L_0x0108;
                                    L_0x009f:
                                        r0 = android.os.Environment.DIRECTORY_DOWNLOADS;	 Catch:{ Exception -> 0x0193 }
                                        r0 = android.os.Environment.getExternalStoragePublicDirectory(r0);	 Catch:{ Exception -> 0x0193 }
                                        r3 = r0;
                                    L_0x00a6:
                                        r3.mkdir();	 Catch:{ Exception -> 0x0193 }
                                        r0 = new java.io.File;	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2;	 Catch:{ Exception -> 0x0193 }
                                        r0.<init>(r3, r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = r0.exists();	 Catch:{ Exception -> 0x0193 }
                                        if (r1 == 0) goto L_0x01c5;
                                    L_0x00b6:
                                        r1 = r2;	 Catch:{ Exception -> 0x0193 }
                                        r2 = 46;
                                        r4 = r1.lastIndexOf(r2);	 Catch:{ Exception -> 0x0193 }
                                        r1 = 0;
                                        r2 = r1;
                                    L_0x00c0:
                                        r1 = 10;
                                        if (r2 >= r1) goto L_0x01c2;
                                    L_0x00c4:
                                        r0 = -1;
                                        if (r4 == r0) goto L_0x0110;
                                    L_0x00c7:
                                        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0193 }
                                        r0.<init>();	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2;	 Catch:{ Exception -> 0x0193 }
                                        r5 = 0;
                                        r1 = r1.substring(r5, r4);	 Catch:{ Exception -> 0x0193 }
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = "(";
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2 + 1;
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = ")";
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2;	 Catch:{ Exception -> 0x0193 }
                                        r1 = r1.substring(r4);	 Catch:{ Exception -> 0x0193 }
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r0 = r0.toString();	 Catch:{ Exception -> 0x0193 }
                                        r1 = r0;
                                    L_0x00fa:
                                        r0 = new java.io.File;	 Catch:{ Exception -> 0x0193 }
                                        r0.<init>(r3, r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = r0.exists();	 Catch:{ Exception -> 0x0193 }
                                        if (r1 != 0) goto L_0x0135;
                                    L_0x0105:
                                        r9 = r0;
                                        goto L_0x0009;
                                    L_0x0108:
                                        r0 = android.os.Environment.DIRECTORY_MUSIC;	 Catch:{ Exception -> 0x0193 }
                                        r0 = android.os.Environment.getExternalStoragePublicDirectory(r0);	 Catch:{ Exception -> 0x0193 }
                                        r3 = r0;
                                        goto L_0x00a6;
                                    L_0x0110:
                                        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0193 }
                                        r0.<init>();	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2;	 Catch:{ Exception -> 0x0193 }
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = "(";
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = r2 + 1;
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r1 = ")";
                                        r0 = r0.append(r1);	 Catch:{ Exception -> 0x0193 }
                                        r0 = r0.toString();	 Catch:{ Exception -> 0x0193 }
                                        r1 = r0;
                                        goto L_0x00fa;
                                    L_0x0135:
                                        r1 = r2 + 1;
                                        r2 = r1;
                                        goto L_0x00c0;
                                    L_0x0139:
                                        r4 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
                                        r12 = r10 - r2;
                                        r4 = java.lang.Math.min(r4, r12);	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r0.transferFrom(r1, r2, r4);	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r4 = r5;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        if (r4 == 0) goto L_0x01c0;
                                    L_0x0148:
                                        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r12 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
                                        r4 = r4 - r12;
                                        r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
                                        if (r4 > 0) goto L_0x01c0;
                                    L_0x0153:
                                        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r6 = (float) r2;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r7 = (float) r10;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r6 = r6 / r7;
                                        r7 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
                                        r6 = r6 * r7;
                                        r6 = (int) r6;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r7 = new org.telegram.messenger.MediaController$27$1;	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        r7.<init>(r6);	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                        org.telegram.messenger.AndroidUtilities.runOnUIThread(r7);	 Catch:{ Exception -> 0x01ba, all -> 0x01ab }
                                    L_0x0166:
                                        r6 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
                                        r2 = r2 + r6;
                                        r6 = r4;
                                        goto L_0x0037;
                                    L_0x016c:
                                        r0 = move-exception;
                                        r0 = r8;
                                        goto L_0x004d;
                                    L_0x0170:
                                        r0 = move-exception;
                                        r14 = r2;
                                        r2 = r1;
                                        r1 = r14;
                                    L_0x0174:
                                        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ all -> 0x01b0 }
                                        r0 = 0;
                                        if (r2 == 0) goto L_0x017d;
                                    L_0x017a:
                                        r2.close();	 Catch:{ Exception -> 0x01a5 }
                                    L_0x017d:
                                        if (r1 == 0) goto L_0x004d;
                                    L_0x017f:
                                        r1.close();	 Catch:{ Exception -> 0x0184 }
                                        goto L_0x004d;
                                    L_0x0184:
                                        r1 = move-exception;
                                        goto L_0x004d;
                                    L_0x0187:
                                        r0 = move-exception;
                                    L_0x0188:
                                        if (r1 == 0) goto L_0x018d;
                                    L_0x018a:
                                        r1.close();	 Catch:{ Exception -> 0x01a7 }
                                    L_0x018d:
                                        if (r2 == 0) goto L_0x0192;
                                    L_0x018f:
                                        r2.close();	 Catch:{ Exception -> 0x01a9 }
                                    L_0x0192:
                                        throw r0;	 Catch:{ Exception -> 0x0193 }
                                    L_0x0193:
                                        r0 = move-exception;
                                        org.telegram.messenger.FileLog.m13728e(r0);
                                        goto L_0x0081;
                                    L_0x0199:
                                        r0 = android.net.Uri.fromFile(r9);	 Catch:{ Exception -> 0x0193 }
                                        org.telegram.messenger.AndroidUtilities.addMediaToGallery(r0);	 Catch:{ Exception -> 0x0193 }
                                        goto L_0x0081;
                                    L_0x01a2:
                                        r1 = move-exception;
                                        goto L_0x0047;
                                    L_0x01a5:
                                        r2 = move-exception;
                                        goto L_0x017d;
                                    L_0x01a7:
                                        r1 = move-exception;
                                        goto L_0x018d;
                                    L_0x01a9:
                                        r1 = move-exception;
                                        goto L_0x0192;
                                    L_0x01ab:
                                        r2 = move-exception;
                                        r14 = r2;
                                        r2 = r0;
                                        r0 = r14;
                                        goto L_0x0188;
                                    L_0x01b0:
                                        r0 = move-exception;
                                        r14 = r1;
                                        r1 = r2;
                                        r2 = r14;
                                        goto L_0x0188;
                                    L_0x01b5:
                                        r0 = move-exception;
                                        r14 = r2;
                                        r2 = r1;
                                        r1 = r14;
                                        goto L_0x0174;
                                    L_0x01ba:
                                        r2 = move-exception;
                                        r14 = r2;
                                        r2 = r1;
                                        r1 = r0;
                                        r0 = r14;
                                        goto L_0x0174;
                                    L_0x01c0:
                                        r4 = r6;
                                        goto L_0x0166;
                                    L_0x01c2:
                                        r9 = r0;
                                        goto L_0x0009;
                                    L_0x01c5:
                                        r9 = r0;
                                        goto L_0x0009;
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.27.run():void");
                                    }
                                }).start();
                            }
                        } catch (Throwable e3) {
                            alertDialog = null;
                            e = e3;
                            FileLog.m13728e(e);
                            i2 = i;
                            str4 = str2;
                            str5 = str3;
                            new Thread(/* anonymous class already generated */).start();
                        }
                    }
                    i2 = i;
                    str4 = str2;
                    str5 = str3;
                    new Thread(/* anonymous class already generated */).start();
                }
            }
        }
    }

    private native int seekOpusFile(float f);

    private void seekOpusPlayer(final float f) {
        if (f != 1.0f) {
            if (!this.isPaused) {
                this.audioTrackPlayer.pause();
            }
            this.audioTrackPlayer.flush();
            this.fileDecodingQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MediaController$14$1 */
                class C31351 implements Runnable {
                    C31351() {
                    }

                    public void run() {
                        if (!MediaController.this.isPaused) {
                            MediaController.this.ignoreFirstProgress = 3;
                            MediaController.this.lastPlayPcm = (long) (((float) MediaController.this.currentTotalPcmDuration) * f);
                            if (MediaController.this.audioTrackPlayer != null) {
                                MediaController.this.audioTrackPlayer.play();
                            }
                            MediaController.this.lastProgress = (long) ((int) ((((float) MediaController.this.currentTotalPcmDuration) / 48.0f) * f));
                            MediaController.this.checkPlayerQueue();
                        }
                    }
                }

                public void run() {
                    MediaController.this.seekOpusFile(f);
                    synchronized (MediaController.this.playerSync) {
                        MediaController.this.freePlayerBuffers.addAll(MediaController.this.usedPlayerBuffers);
                        MediaController.this.usedPlayerBuffers.clear();
                    }
                    AndroidUtilities.runOnUIThread(new C31351());
                }
            });
        }
    }

    @SuppressLint({"NewApi"})
    public static MediaCodecInfo selectCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        MediaCodecInfo mediaCodecInfo = null;
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String equalsIgnoreCase : codecInfoAt.getSupportedTypes()) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        if (!codecInfoAt.getName().equals("OMX.SEC.avc.enc") || codecInfoAt.getName().equals("OMX.SEC.AVC.Encoder")) {
                            return codecInfoAt;
                        }
                        mediaCodecInfo = codecInfoAt;
                    }
                }
                continue;
            }
        }
        return mediaCodecInfo;
    }

    @SuppressLint({"NewApi"})
    public static int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        int i = 0;
        CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
        int i2 = 0;
        while (i < capabilitiesForType.colorFormats.length) {
            int i3 = capabilitiesForType.colorFormats[i];
            if (isRecognizedFormat(i3)) {
                if (!mediaCodecInfo.getName().equals("OMX.SEC.AVC.Encoder") || i3 != 19) {
                    return i3;
                }
                i2 = i3;
            }
            i++;
        }
        return i2;
    }

    private int selectTrack(MediaExtractor mediaExtractor, boolean z) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            String string = mediaExtractor.getTrackFormat(i).getString("mime");
            if (z) {
                if (string.startsWith("audio/")) {
                    return i;
                }
            } else if (string.startsWith("video/")) {
                return i;
            }
        }
        return -5;
    }

    private void setPlayerVolume() {
        try {
            float f = this.audioFocus != 1 ? 1.0f : VOLUME_DUCK;
            if (this.audioPlayer != null) {
                this.audioPlayer.setVolume(f, f);
            } else if (this.audioTrackPlayer != null) {
                this.audioTrackPlayer.setStereoVolume(f, f);
            } else if (this.videoPlayer != null) {
                this.videoPlayer.setVolume(f);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void setUseFrontSpeaker(boolean z) {
        this.useFrontSpeaker = z;
        AudioManager audioManager = NotificationsController.getInstance().audioManager;
        if (this.useFrontSpeaker) {
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
            return;
        }
        audioManager.setSpeakerphoneOn(true);
    }

    private void startAudioAgain(boolean z) {
        int i = 0;
        if (this.playingMessageObject != null) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.audioRouteChanged, Boolean.valueOf(this.useFrontSpeaker));
            if (this.videoPlayer != null) {
                VideoPlayer videoPlayer = this.videoPlayer;
                if (!this.useFrontSpeaker) {
                    i = 3;
                }
                videoPlayer.setStreamType(i);
                if (z) {
                    this.videoPlayer.pause();
                    return;
                } else {
                    this.videoPlayer.play();
                    return;
                }
            }
            boolean z2 = this.audioPlayer != null;
            final MessageObject messageObject = this.playingMessageObject;
            float f = this.playingMessageObject.audioProgress;
            cleanupPlayer(false, true);
            messageObject.audioProgress = f;
            playMessage(messageObject);
            if (!z) {
                return;
            }
            if (z2) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MediaController.this.pauseMessage(messageObject);
                    }
                }, 100);
            } else {
                pauseMessage(messageObject);
            }
        }
    }

    private void startProgressTimer(final MessageObject messageObject) {
        synchronized (this.progressTimerSync) {
            if (this.progressTimer != null) {
                try {
                    this.progressTimer.cancel();
                    this.progressTimer = null;
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
            this.progressTimer = new Timer();
            this.progressTimer.schedule(new TimerTask() {

                /* renamed from: org.telegram.messenger.MediaController$6$1 */
                class C31551 implements Runnable {
                    C31551() {
                    }

                    public void run() {
                        if (messageObject == null) {
                            return;
                        }
                        if ((MediaController.this.audioPlayer != null || MediaController.this.audioTrackPlayer != null || MediaController.this.videoPlayer != null) && !MediaController.this.isPaused) {
                            try {
                                if (MediaController.this.ignoreFirstProgress != 0) {
                                    MediaController.this.ignoreFirstProgress = MediaController.this.ignoreFirstProgress - 1;
                                    return;
                                }
                                long currentPosition;
                                float access$3000;
                                if (MediaController.this.videoPlayer != null) {
                                    currentPosition = MediaController.this.videoPlayer.getCurrentPosition();
                                    access$3000 = ((float) MediaController.this.lastProgress) / ((float) MediaController.this.videoPlayer.getDuration());
                                    if (currentPosition <= MediaController.this.lastProgress || access$3000 >= 1.0f) {
                                        return;
                                    }
                                } else if (MediaController.this.audioPlayer != null) {
                                    currentPosition = (long) MediaController.this.audioPlayer.getCurrentPosition();
                                    access$3000 = ((float) MediaController.this.lastProgress) / ((float) MediaController.this.audioPlayer.getDuration());
                                    if (currentPosition <= MediaController.this.lastProgress) {
                                        return;
                                    }
                                } else {
                                    currentPosition = (long) ((int) (((float) MediaController.this.lastPlayPcm) / 48.0f));
                                    access$3000 = ((float) MediaController.this.lastPlayPcm) / ((float) MediaController.this.currentTotalPcmDuration);
                                    if (currentPosition == MediaController.this.lastProgress) {
                                        return;
                                    }
                                }
                                MediaController.this.lastProgress = currentPosition;
                                messageObject.audioProgress = access$3000;
                                messageObject.audioProgressSec = (int) (MediaController.this.lastProgress / 1000);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(messageObject.getId()), Float.valueOf(access$3000));
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }
                }

                public void run() {
                    synchronized (MediaController.this.sync) {
                        AndroidUtilities.runOnUIThread(new C31551());
                    }
                }
            }, 0, 17);
        }
    }

    private native int startRecord(String str);

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
            for (int i = 0; i < messageObject.messageOwner.media.document.attributes.size(); i++) {
                if (((DocumentAttribute) messageObject.messageOwner.media.document.attributes.get(i)) instanceof TLRPC$TL_documentAttributeAnimated) {
                    intent.putExtra("gif", true);
                    break;
                }
            }
        }
        if (messageObject.getId() != 0) {
            ApplicationLoader.applicationContext.startService(intent);
        }
        VideoConvertRunnable.runConversion(messageObject);
        return true;
    }

    private void stopProgressTimer() {
        synchronized (this.progressTimerSync) {
            if (this.progressTimer != null) {
                try {
                    this.progressTimer.cancel();
                    this.progressTimer = null;
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        }
    }

    private native void stopRecord();

    private void stopRecordingInternal(final int i) {
        if (i != 0) {
            final TLRPC$TL_document tLRPC$TL_document = this.recordingAudio;
            final File file = this.recordingAudioFile;
            this.fileEncodingQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.MediaController$24$1 */
                class C31451 implements Runnable {

                    /* renamed from: org.telegram.messenger.MediaController$24$1$1 */
                    class C31431 implements OnClickListener {
                        C31431() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }

                    /* renamed from: org.telegram.messenger.MediaController$24$1$2 */
                    class C31442 implements OnClickListener {
                        C31442() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document, null, file.getAbsolutePath(), MediaController.this.recordDialogId, MediaController.this.recordReplyingMessageObject, null, null, 0);
                        }
                    }

                    C31451() {
                    }

                    public void run() {
                        VideoEditedInfo videoEditedInfo = null;
                        tLRPC$TL_document.date = ConnectionsManager.getInstance().getCurrentTime();
                        tLRPC$TL_document.size = (int) file.length();
                        TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                        tLRPC$TL_documentAttributeAudio.voice = true;
                        tLRPC$TL_documentAttributeAudio.waveform = MediaController.this.getWaveform2(MediaController.this.recordSamples, MediaController.this.recordSamples.length);
                        if (tLRPC$TL_documentAttributeAudio.waveform != null) {
                            tLRPC$TL_documentAttributeAudio.flags |= 4;
                        }
                        long access$700 = MediaController.this.recordTimeCount;
                        tLRPC$TL_documentAttributeAudio.duration = (int) (MediaController.this.recordTimeCount / 1000);
                        tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeAudio);
                        if (access$700 > 700) {
                            if (!ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("confirmForStickers", true) || MediaController.this.raiseChat == null || MediaController.this.raiseChat.getParentActivity() == null) {
                                SendMessagesHelper.getInstance().sendMessage(tLRPC$TL_document, null, file.getAbsolutePath(), MediaController.this.recordDialogId, MediaController.this.recordReplyingMessageObject, null, null, 0);
                            } else {
                                new Builder(MediaController.this.raiseChat.getParentActivity()).setMessage("برای ارسال صدا مطمین هستید؟").setPositiveButton("بله", new C31442()).setNegativeButton("خیر", new C31431()).setIcon(17301543).show();
                            }
                            NotificationCenter instance = NotificationCenter.getInstance();
                            int i = NotificationCenter.audioDidSent;
                            Object[] objArr = new Object[2];
                            objArr[0] = i == 2 ? tLRPC$TL_document : null;
                            if (i == 2) {
                                videoEditedInfo = file.getAbsolutePath();
                            }
                            objArr[1] = videoEditedInfo;
                            instance.postNotificationName(i, objArr);
                            return;
                        }
                        file.delete();
                    }
                }

                public void run() {
                    MediaController.this.stopRecord();
                    AndroidUtilities.runOnUIThread(new C31451());
                }
            });
        }
        try {
            if (this.audioRecorder != null) {
                this.audioRecorder.release();
                this.audioRecorder = null;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        this.recordingAudio = null;
        this.recordingAudioFile = null;
    }

    private native int writeFrame(ByteBuffer byteBuffer, int i);

    public void addLoadingFileObserver(String str, FileDownloadProgressListener fileDownloadProgressListener) {
        addLoadingFileObserver(str, null, fileDownloadProgressListener);
    }

    public void addLoadingFileObserver(String str, MessageObject messageObject, FileDownloadProgressListener fileDownloadProgressListener) {
        if (this.listenerInProgress) {
            this.addLaterArray.put(str, fileDownloadProgressListener);
            return;
        }
        removeLoadingFileObserver(fileDownloadProgressListener);
        ArrayList arrayList = (ArrayList) this.loadingFileObservers.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.loadingFileObservers.put(str, arrayList);
        }
        arrayList.add(new WeakReference(fileDownloadProgressListener));
        if (messageObject != null) {
            arrayList = (ArrayList) this.loadingFileMessagesObservers.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.loadingFileMessagesObservers.put(str, arrayList);
            }
            arrayList.add(messageObject);
        }
        this.observersByTag.put(Integer.valueOf(fileDownloadProgressListener.getObserverTag()), str);
    }

    public boolean canAutoplayGifs() {
        return this.autoplayGifs;
    }

    public boolean canCustomTabs() {
        return this.customTabs;
    }

    public boolean canDirectShare() {
        return this.directShare;
    }

    public boolean canDownloadMedia(MessageObject messageObject) {
        return canDownloadMedia(messageObject.messageOwner);
    }

    public boolean canDownloadMedia(Message message) {
        int i = 2;
        if (!this.globalAutodownloadEnabled) {
            return false;
        }
        int i2;
        int i3 = MessageObject.isPhoto(message) ? 1 : MessageObject.isVoiceMessage(message) ? 2 : MessageObject.isRoundVideoMessage(message) ? 64 : MessageObject.isVideoMessage(message) ? 4 : MessageObject.isMusicMessage(message) ? 16 : MessageObject.isGifMessage(message) ? 32 : 8;
        Peer peer = message.to_id;
        if (peer == null) {
            i = 1;
        } else if (peer.user_id != 0) {
            i = ContactsController.getInstance().contactsDict.containsKey(Integer.valueOf(peer.user_id)) ? 0 : 1;
        } else if (peer.chat_id == 0 && !MessageObject.isMegagroup(message)) {
            i = 3;
        }
        if (ConnectionsManager.isConnectedToWiFi()) {
            i2 = this.wifiDownloadMask[i];
            i = this.wifiMaxFileSize[maskToIndex(i3)];
        } else if (ConnectionsManager.isRoaming()) {
            i2 = this.roamingDownloadMask[i];
            i = this.roamingMaxFileSize[maskToIndex(i3)];
        } else {
            i2 = this.mobileDataDownloadMask[i];
            i = this.mobileMaxFileSize[maskToIndex(i3)];
        }
        return (i3 == 1 || MessageObject.getMessageSize(message) <= i) && (i3 & i2) != 0;
    }

    public boolean canInAppCamera() {
        return this.inappCamera;
    }

    public boolean canRaiseToSpeak() {
        return this.raiseToSpeak;
    }

    public boolean canRoundCamera16to9() {
        return this.roundCamera16to9;
    }

    public boolean canSaveToGallery() {
        return this.saveToGallery;
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

    public void checkAutodownloadSettings() {
        int currentDownloadMask = getCurrentDownloadMask();
        if (currentDownloadMask != this.lastCheckMask) {
            int i;
            this.lastCheckMask = currentDownloadMask;
            if ((currentDownloadMask & 1) == 0) {
                for (i = 0; i < this.photoDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((PhotoSize) ((DownloadObject) this.photoDownloadQueue.get(i)).object);
                }
                this.photoDownloadQueue.clear();
            } else if (this.photoDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(1);
            }
            if ((currentDownloadMask & 2) == 0) {
                for (i = 0; i < this.audioDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.audioDownloadQueue.get(i)).object);
                }
                this.audioDownloadQueue.clear();
            } else if (this.audioDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(2);
            }
            if ((currentDownloadMask & 64) == 0) {
                for (i = 0; i < this.videoMessageDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.videoMessageDownloadQueue.get(i)).object);
                }
                this.videoMessageDownloadQueue.clear();
            } else if (this.videoMessageDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(64);
            }
            if ((currentDownloadMask & 8) == 0) {
                for (i = 0; i < this.documentDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.documentDownloadQueue.get(i)).object);
                }
                this.documentDownloadQueue.clear();
            } else if (this.documentDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(8);
            }
            if ((currentDownloadMask & 4) == 0) {
                for (i = 0; i < this.videoDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.videoDownloadQueue.get(i)).object);
                }
                this.videoDownloadQueue.clear();
            } else if (this.videoDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(4);
            }
            if ((currentDownloadMask & 16) == 0) {
                for (i = 0; i < this.musicDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.musicDownloadQueue.get(i)).object);
                }
                this.musicDownloadQueue.clear();
            } else if (this.musicDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(16);
            }
            if ((currentDownloadMask & 32) == 0) {
                for (i = 0; i < this.gifDownloadQueue.size(); i++) {
                    FileLoader.getInstance().cancelLoadFile((Document) ((DownloadObject) this.gifDownloadQueue.get(i)).object);
                }
                this.gifDownloadQueue.clear();
            } else if (this.gifDownloadQueue.isEmpty()) {
                newDownloadObjectsAvailable(32);
            }
            int autodownloadMaskAll = getAutodownloadMaskAll();
            if (autodownloadMaskAll == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(0);
                return;
            }
            if ((autodownloadMaskAll & 1) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(1);
            }
            if ((autodownloadMaskAll & 2) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(2);
            }
            if ((autodownloadMaskAll & 64) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(64);
            }
            if ((autodownloadMaskAll & 4) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(4);
            }
            if ((autodownloadMaskAll & 8) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(8);
            }
            if ((autodownloadMaskAll & 16) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(16);
            }
            if ((autodownloadMaskAll & 32) == 0) {
                MessagesStorage.getInstance().clearDownloadQueue(32);
            }
        }
    }

    public void checkSaveToGalleryFiles() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "Telegram");
            File file2 = new File(file, "Telegram Images");
            file2.mkdir();
            File file3 = new File(file, "Telegram Video");
            file3.mkdir();
            if (this.saveToGallery) {
                if (file2.isDirectory()) {
                    new File(file2, ".nomedia").delete();
                }
                if (file3.isDirectory()) {
                    new File(file3, ".nomedia").delete();
                    return;
                }
                return;
            }
            if (file2.isDirectory()) {
                new File(file2, ".nomedia").createNewFile();
            }
            if (file3.isDirectory()) {
                new File(file3, ".nomedia").createNewFile();
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
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

    public void cleanupPlayer(boolean z, boolean z2) {
        cleanupPlayer(z, z2, false);
    }

    public void cleanupPlayer(boolean z, boolean z2, boolean z3) {
        if (this.audioPlayer != null) {
            try {
                this.audioPlayer.reset();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            try {
                this.audioPlayer.stop();
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
            try {
                this.audioPlayer.release();
            } catch (Throwable e22) {
                FileLog.m13728e(e22);
            }
            this.audioPlayer = null;
        } else if (this.audioTrackPlayer != null) {
            synchronized (this.playerObjectSync) {
                try {
                    this.audioTrackPlayer.pause();
                    this.audioTrackPlayer.flush();
                } catch (Throwable e222) {
                    FileLog.m13728e(e222);
                }
                try {
                    this.audioTrackPlayer.release();
                } catch (Throwable e2222) {
                    FileLog.m13728e(e2222);
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
                FileLog.m13728e(e22222);
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
            MessageObject messageObject = this.playingMessageObject;
            this.playingMessageObject.audioProgress = BitmapDescriptorFactory.HUE_RED;
            this.playingMessageObject.audioProgressSec = 0;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
            this.playingMessageObject = null;
            this.downloadingCurrentMessage = false;
            if (z) {
                NotificationsController.getInstance().audioManager.abandonAudioFocus(this);
                this.hasAudioFocus = 0;
                if (this.voiceMessagesPlaylist != null) {
                    if (z3 && this.voiceMessagesPlaylist.get(0) == messageObject) {
                        this.voiceMessagesPlaylist.remove(0);
                        this.voiceMessagesPlaylistMap.remove(Integer.valueOf(messageObject.getId()));
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
                    messageObject = (MessageObject) this.voiceMessagesPlaylist.get(0);
                    playMessage(messageObject);
                    if (!(messageObject.isRoundVideo() || this.pipRoundVideoView == null)) {
                        this.pipRoundVideoView.close(true);
                        this.pipRoundVideoView = null;
                    }
                } else {
                    if ((messageObject.isVoice() || messageObject.isRoundVideo()) && messageObject.getId() != 0) {
                        startRecordingIfFromSpeaker();
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingDidReset, Integer.valueOf(messageObject.getId()), Boolean.valueOf(z2));
                    this.pipSwitchingState = 0;
                    if (this.pipRoundVideoView != null) {
                        this.pipRoundVideoView.close(true);
                        this.pipRoundVideoView = null;
                    }
                }
            }
            if (z2) {
                ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, MusicPlayerService.class));
            }
        }
        if (!this.useFrontSpeaker && !this.raiseToSpeak) {
            ChatActivity chatActivity = this.raiseChat;
            stopRaiseToEarSensors(this.raiseChat);
            this.raiseChat = chatActivity;
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        ArrayList arrayList;
        int i2;
        WeakReference weakReference;
        if (i == NotificationCenter.FileDidFailedLoad || i == NotificationCenter.httpFileDidFailedLoad) {
            this.listenerInProgress = true;
            str = (String) objArr[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(str);
            if (arrayList != null) {
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    weakReference = (WeakReference) arrayList.get(i2);
                    if (weakReference.get() != null) {
                        ((FileDownloadProgressListener) weakReference.get()).onFailedDownload(str);
                        this.observersByTag.remove(Integer.valueOf(((FileDownloadProgressListener) weakReference.get()).getObserverTag()));
                    }
                }
                this.loadingFileObservers.remove(str);
            }
            this.listenerInProgress = false;
            processLaterArrays();
            checkDownloadFinished(str, ((Integer) objArr[1]).intValue());
        } else if (i == NotificationCenter.FileDidLoaded || i == NotificationCenter.httpFileDidLoaded) {
            this.listenerInProgress = true;
            str = (String) objArr[0];
            if (this.downloadingCurrentMessage && this.playingMessageObject != null && FileLoader.getAttachFileName(this.playingMessageObject.getDocument()).equals(str)) {
                this.playMusicAgain = true;
                playMessage(this.playingMessageObject);
            }
            arrayList = (ArrayList) this.loadingFileMessagesObservers.get(str);
            if (arrayList != null) {
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ((MessageObject) arrayList.get(i3)).mediaExists = true;
                }
                this.loadingFileMessagesObservers.remove(str);
            }
            arrayList = (ArrayList) this.loadingFileObservers.get(str);
            if (arrayList != null) {
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    weakReference = (WeakReference) arrayList.get(i2);
                    if (weakReference.get() != null) {
                        ((FileDownloadProgressListener) weakReference.get()).onSuccessDownload(str);
                        this.observersByTag.remove(Integer.valueOf(((FileDownloadProgressListener) weakReference.get()).getObserverTag()));
                    }
                }
                this.loadingFileObservers.remove(str);
            }
            this.listenerInProgress = false;
            processLaterArrays();
            checkDownloadFinished(str, 0);
        } else if (i == NotificationCenter.FileLoadProgressChanged) {
            this.listenerInProgress = true;
            str = (String) objArr[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(str);
            if (arrayList != null) {
                r2 = (Float) objArr[1];
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    r1 = (WeakReference) it.next();
                    if (r1.get() != null) {
                        ((FileDownloadProgressListener) r1.get()).onProgressDownload(str, r2.floatValue());
                    }
                }
            }
            this.listenerInProgress = false;
            processLaterArrays();
        } else if (i == NotificationCenter.FileUploadProgressChanged) {
            this.listenerInProgress = true;
            str = (String) objArr[0];
            arrayList = (ArrayList) this.loadingFileObservers.get(str);
            if (arrayList != null) {
                r2 = (Float) objArr[1];
                Boolean bool = (Boolean) objArr[2];
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    r1 = (WeakReference) it2.next();
                    if (r1.get() != null) {
                        ((FileDownloadProgressListener) r1.get()).onProgressUpload(str, r2.floatValue(), bool.booleanValue());
                    }
                }
            }
            this.listenerInProgress = false;
            processLaterArrays();
            try {
                ArrayList delayedMessages = SendMessagesHelper.getInstance().getDelayedMessages(str);
                if (delayedMessages != null) {
                    for (r2 = 0; r2 < delayedMessages.size(); r2++) {
                        DelayedMessage delayedMessage = (DelayedMessage) delayedMessages.get(r2);
                        if (delayedMessage.encryptedChat == null) {
                            long j = delayedMessage.peer;
                            if (delayedMessage.type == 4) {
                                Long l = (Long) this.typingTimes.get(Long.valueOf(j));
                                if (l == null || l.longValue() + 4000 < System.currentTimeMillis()) {
                                    MessagesController.getInstance().sendTyping(j, 4, 0);
                                    this.typingTimes.put(Long.valueOf(j), Long.valueOf(System.currentTimeMillis()));
                                }
                            } else {
                                Long l2 = (Long) this.typingTimes.get(Long.valueOf(j));
                                delayedMessage.obj.getDocument();
                                if (l2 == null || l2.longValue() + 4000 < System.currentTimeMillis()) {
                                    if (delayedMessage.obj.isRoundVideo()) {
                                        MessagesController.getInstance().sendTyping(j, 8, 0);
                                    } else if (delayedMessage.obj.isVideo()) {
                                        MessagesController.getInstance().sendTyping(j, 5, 0);
                                    } else if (delayedMessage.obj.getDocument() != null) {
                                        MessagesController.getInstance().sendTyping(j, 3, 0);
                                    } else if (delayedMessage.location != null) {
                                        MessagesController.getInstance().sendTyping(j, 4, 0);
                                    }
                                    this.typingTimes.put(Long.valueOf(j), Long.valueOf(System.currentTimeMillis()));
                                }
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        } else if (i == NotificationCenter.messagesDeleted) {
            r2 = ((Integer) objArr[1]).intValue();
            r0 = (ArrayList) objArr[0];
            if (this.playingMessageObject != null && r2 == this.playingMessageObject.messageOwner.to_id.channel_id && r0.contains(Integer.valueOf(this.playingMessageObject.getId()))) {
                cleanupPlayer(true, true);
            }
            if (this.voiceMessagesPlaylist != null && !this.voiceMessagesPlaylist.isEmpty() && r2 == ((MessageObject) this.voiceMessagesPlaylist.get(0)).messageOwner.to_id.channel_id) {
                for (r2 = 0; r2 < r0.size(); r2++) {
                    r1 = (MessageObject) this.voiceMessagesPlaylistMap.remove(r0.get(r2));
                    if (r1 != null) {
                        this.voiceMessagesPlaylist.remove(r1);
                    }
                }
            }
        } else if (i == NotificationCenter.removeAllMessagesFromDialog) {
            r0 = ((Long) objArr[0]).longValue();
            if (this.playingMessageObject != null && this.playingMessageObject.getDialogId() == r0) {
                cleanupPlayer(false, true);
            }
        } else if (i == NotificationCenter.musicDidLoaded) {
            r0 = ((Long) objArr[0]).longValue();
            if (this.playingMessageObject != null && this.playingMessageObject.isMusic() && this.playingMessageObject.getDialogId() == r0) {
                r0 = (ArrayList) objArr[1];
                this.playlist.addAll(0, r0);
                if (this.shuffleMusic) {
                    buildShuffledPlayList();
                    this.currentPlaylistNum = 0;
                    return;
                }
                this.currentPlaylistNum = r0.size() + this.currentPlaylistNum;
            }
        } else if (i == NotificationCenter.didReceivedNewMessages && this.voiceMessagesPlaylist != null && !this.voiceMessagesPlaylist.isEmpty()) {
            if (((Long) objArr[0]).longValue() == ((MessageObject) this.voiceMessagesPlaylist.get(0)).getDialogId()) {
                r0 = (ArrayList) objArr[1];
                for (r2 = 0; r2 < r0.size(); r2++) {
                    r1 = (MessageObject) r0.get(r2);
                    if ((r1.isVoice() || r1.isRoundVideo()) && (!this.voiceMessagesPlaylistUnread || (r1.isContentUnread() && !r1.isOut()))) {
                        this.voiceMessagesPlaylist.add(r1);
                        this.voiceMessagesPlaylistMap.put(Integer.valueOf(r1.getId()), r1);
                    }
                }
            }
        }
    }

    public boolean findMessageInPlaylistAndPlay(MessageObject messageObject) {
        int indexOf = this.playlist.indexOf(messageObject);
        if (indexOf == -1) {
            return playMessage(messageObject);
        }
        playMessageAtIndex(indexOf);
        return true;
    }

    public int generateObserverTag() {
        int i = this.lastTag;
        this.lastTag = i + 1;
        return i;
    }

    public void generateWaveform(MessageObject messageObject) {
        final String str = messageObject.getId() + "_" + messageObject.getDialogId();
        final String absolutePath = FileLoader.getPathToMessage(messageObject.messageOwner).getAbsolutePath();
        if (!this.generatingWaveform.containsKey(str)) {
            this.generatingWaveform.put(str, messageObject);
            Utilities.globalQueue.postRunnable(new Runnable() {
                public void run() {
                    final byte[] waveform = MediaController.getInstance().getWaveform(absolutePath);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            MessageObject messageObject = (MessageObject) MediaController.this.generatingWaveform.remove(str);
                            if (messageObject != null && waveform != null) {
                                for (int i = 0; i < messageObject.getDocument().attributes.size(); i++) {
                                    DocumentAttribute documentAttribute = (DocumentAttribute) messageObject.getDocument().attributes.get(i);
                                    if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                        documentAttribute.waveform = waveform;
                                        documentAttribute.flags |= 4;
                                        break;
                                    }
                                }
                                TLRPC$messages_Messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
                                tLRPC$TL_messages_messages.messages.add(messageObject.messageOwner);
                                MessagesStorage.getInstance().putMessages(tLRPC$TL_messages_messages, messageObject.getDialogId(), -1, 0, false);
                                new ArrayList().add(messageObject);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, Long.valueOf(messageObject.getDialogId()), r0);
                            }
                        }
                    });
                }
            });
        }
    }

    public AudioInfo getAudioInfo() {
        return this.audioInfo;
    }

    protected int getAutodownloadMask() {
        if (!this.globalAutodownloadEnabled) {
            return 0;
        }
        int[] iArr = ConnectionsManager.isConnectedToWiFi() ? this.wifiDownloadMask : ConnectionsManager.isRoaming() ? this.roamingDownloadMask : this.mobileDataDownloadMask;
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = (iArr[i2] & 1) != 0 ? 1 : 0;
            if ((iArr[i2] & 2) != 0) {
                i3 |= 2;
            }
            if ((iArr[i2] & 64) != 0) {
                i3 |= 64;
            }
            if ((iArr[i2] & 4) != 0) {
                i3 |= 4;
            }
            if ((iArr[i2] & 8) != 0) {
                i3 |= 8;
            }
            if ((iArr[i2] & 16) != 0) {
                i3 |= 16;
            }
            if ((iArr[i2] & 32) != 0) {
                i3 |= 32;
            }
            i |= i3 << (i2 * 8);
        }
        return i;
    }

    protected int getAutodownloadMaskAll() {
        int i = 0;
        if (this.globalAutodownloadEnabled) {
            int i2 = 0;
            while (i2 < 4) {
                if (!((this.mobileDataDownloadMask[i2] & 1) == 0 && (this.wifiDownloadMask[i2] & 1) == 0 && (this.roamingDownloadMask[i2] & 1) == 0)) {
                    i |= 1;
                }
                if (!((this.mobileDataDownloadMask[i2] & 2) == 0 && (this.wifiDownloadMask[i2] & 2) == 0 && (this.roamingDownloadMask[i2] & 2) == 0)) {
                    i |= 2;
                }
                if (!((this.mobileDataDownloadMask[i2] & 64) == 0 && (this.wifiDownloadMask[i2] & 64) == 0 && (this.roamingDownloadMask[i2] & 64) == 0)) {
                    i |= 64;
                }
                if (!((this.mobileDataDownloadMask[i2] & 4) == 0 && (this.wifiDownloadMask[i2] & 4) == 0 && (this.roamingDownloadMask[i2] & 4) == 0)) {
                    i |= 4;
                }
                if (!((this.mobileDataDownloadMask[i2] & 8) == 0 && (this.wifiDownloadMask[i2] & 8) == 0 && (this.roamingDownloadMask[i2] & 8) == 0)) {
                    i |= 8;
                }
                if (!((this.mobileDataDownloadMask[i2] & 16) == 0 && (this.wifiDownloadMask[i2] & 16) == 0 && (this.roamingDownloadMask[i2] & 16) == 0)) {
                    i |= 16;
                }
                if ((this.mobileDataDownloadMask[i2] & 32) != 0 || (this.wifiDownloadMask[i2] & 32) != 0 || (this.roamingDownloadMask[i2] & 32) != 0) {
                    i |= 32;
                }
                i2++;
            }
        }
        return i;
    }

    public MessageObject getPlayingMessageObject() {
        return this.playingMessageObject;
    }

    public int getPlayingMessageObjectNum() {
        return this.currentPlaylistNum;
    }

    public ArrayList<MessageObject> getPlaylist() {
        return this.playlist;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public native byte[] getWaveform(String str);

    public native byte[] getWaveform2(short[] sArr, int i);

    public boolean isDownloadingCurrentMessage() {
        return this.downloadingCurrentMessage;
    }

    public boolean isGroupPhotosEnabled() {
        return this.groupPhotosEnabled;
    }

    public boolean isMessagePaused() {
        return this.isPaused || this.downloadingCurrentMessage;
    }

    public boolean isPlayOrderReversed() {
        return this.playOrderReversed;
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

    protected boolean isRecordingAudio() {
        return (this.recordStartRunnable == null && this.recordingAudio == null) ? false : true;
    }

    public boolean isRoundVideoDrawingReady() {
        return this.currentAspectRatioFrameLayout != null && this.currentAspectRatioFrameLayout.isDrawingReady();
    }

    public boolean isShuffleMusic() {
        return this.shuffleMusic;
    }

    protected void newDownloadObjectsAvailable(int i) {
        int currentDownloadMask = getCurrentDownloadMask();
        if (!((currentDownloadMask & 1) == 0 || (i & 1) == 0 || !this.photoDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(1);
        }
        if (!((currentDownloadMask & 2) == 0 || (i & 2) == 0 || !this.audioDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(2);
        }
        if (!((currentDownloadMask & 64) == 0 || (i & 64) == 0 || !this.videoMessageDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(64);
        }
        if (!((currentDownloadMask & 4) == 0 || (i & 4) == 0 || !this.videoDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(4);
        }
        if (!((currentDownloadMask & 8) == 0 || (i & 8) == 0 || !this.documentDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(8);
        }
        if (!((currentDownloadMask & 16) == 0 || (i & 16) == 0 || !this.musicDownloadQueue.isEmpty())) {
            MessagesStorage.getInstance().getDownloadQueue(16);
        }
        if ((currentDownloadMask & 32) != 0 && (i & 32) != 0 && this.gifDownloadQueue.isEmpty()) {
            MessagesStorage.getInstance().getDownloadQueue(32);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onAudioFocusChange(int i) {
        if (i == -1) {
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                pauseMessage(getPlayingMessageObject());
            }
            this.hasAudioFocus = 0;
            this.audioFocus = 0;
        } else if (i == 1) {
            this.audioFocus = 2;
            if (this.resumeAudioOnFocusGain) {
                this.resumeAudioOnFocusGain = false;
                if (isPlayingMessage(getPlayingMessageObject()) && isMessagePaused()) {
                    playMessage(getPlayingMessageObject());
                }
            }
        } else if (i == -3) {
            this.audioFocus = 1;
        } else if (i == -2) {
            this.audioFocus = 0;
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                pauseMessage(getPlayingMessageObject());
                this.resumeAudioOnFocusGain = true;
            }
        }
        setPlayerVolume();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.sensorsStarted && VoIPService.getSharedInstance() == null) {
            if (sensorEvent.sensor == this.proximitySensor) {
                FileLog.m13726e("proximity changed to " + sensorEvent.values[0]);
                if (this.lastProximityValue == -100.0f) {
                    this.lastProximityValue = sensorEvent.values[0];
                } else if (this.lastProximityValue != sensorEvent.values[0]) {
                    this.proximityHasDifferentValues = true;
                }
                if (this.proximityHasDifferentValues) {
                    this.proximityTouched = isNearToSensor(sensorEvent.values[0]);
                }
            } else if (sensorEvent.sensor == this.accelerometerSensor) {
                double d = this.lastTimestamp == 0 ? 0.9800000190734863d : 1.0d / (1.0d + (((double) (sensorEvent.timestamp - this.lastTimestamp)) / 1.0E9d));
                this.lastTimestamp = sensorEvent.timestamp;
                this.gravity[0] = (float) ((((double) this.gravity[0]) * d) + ((1.0d - d) * ((double) sensorEvent.values[0])));
                this.gravity[1] = (float) ((((double) this.gravity[1]) * d) + ((1.0d - d) * ((double) sensorEvent.values[1])));
                this.gravity[2] = (float) (((1.0d - d) * ((double) sensorEvent.values[2])) + (((double) this.gravity[2]) * d));
                this.gravityFast[0] = (0.8f * this.gravity[0]) + (0.19999999f * sensorEvent.values[0]);
                this.gravityFast[1] = (0.8f * this.gravity[1]) + (0.19999999f * sensorEvent.values[1]);
                this.gravityFast[2] = (0.8f * this.gravity[2]) + (0.19999999f * sensorEvent.values[2]);
                this.linearAcceleration[0] = sensorEvent.values[0] - this.gravity[0];
                this.linearAcceleration[1] = sensorEvent.values[1] - this.gravity[1];
                this.linearAcceleration[2] = sensorEvent.values[2] - this.gravity[2];
            } else if (sensorEvent.sensor == this.linearSensor) {
                this.linearAcceleration[0] = sensorEvent.values[0];
                this.linearAcceleration[1] = sensorEvent.values[1];
                this.linearAcceleration[2] = sensorEvent.values[2];
            } else if (sensorEvent.sensor == this.gravitySensor) {
                float[] fArr = this.gravityFast;
                float[] fArr2 = this.gravity;
                float f = sensorEvent.values[0];
                fArr2[0] = f;
                fArr[0] = f;
                fArr = this.gravityFast;
                fArr2 = this.gravity;
                f = sensorEvent.values[1];
                fArr2[1] = f;
                fArr[1] = f;
                fArr = this.gravityFast;
                fArr2 = this.gravity;
                f = sensorEvent.values[2];
                fArr2[2] = f;
                fArr[2] = f;
            }
            if (sensorEvent.sensor == this.linearSensor || sensorEvent.sensor == this.gravitySensor || sensorEvent.sensor == this.accelerometerSensor) {
                float f2 = ((this.gravity[0] * this.linearAcceleration[0]) + (this.gravity[1] * this.linearAcceleration[1])) + (this.gravity[2] * this.linearAcceleration[2]);
                if (this.raisedToBack != 6) {
                    if (f2 <= BitmapDescriptorFactory.HUE_RED || this.previousAccValue <= BitmapDescriptorFactory.HUE_RED) {
                        if (f2 < BitmapDescriptorFactory.HUE_RED && this.previousAccValue < BitmapDescriptorFactory.HUE_RED) {
                            if (this.raisedToTop != 6 || f2 >= -15.0f) {
                                if (f2 > -15.0f) {
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
                    } else if (f2 <= 15.0f || this.raisedToBack != 0) {
                        if (f2 < 15.0f) {
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
                this.previousAccValue = f2;
                boolean z = this.gravityFast[1] > 2.5f && Math.abs(this.gravityFast[2]) < 4.0f && Math.abs(this.gravityFast[0]) > 1.5f;
                this.accelerometerVertical = z;
            }
            if (this.raisedToBack == 6 && this.accelerometerVertical && this.proximityTouched && !NotificationsController.getInstance().audioManager.isWiredHeadsetOn()) {
                FileLog.m13726e("sensor values reached");
                if (this.playingMessageObject == null && this.recordStartRunnable == null && this.recordingAudio == null && !PhotoViewer.getInstance().isVisible() && ApplicationLoader.isScreenOn && !this.inputFieldHasText && this.allowStartRecord && this.raiseChat != null && !this.callInProgress) {
                    if (!this.raiseToEarRecord) {
                        FileLog.m13726e("start record");
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
                    FileLog.m13726e("start listen");
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
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
                if (!(this.useFrontSpeaker || sharedPreferences.getBoolean("disableAudioStop", false) || this.playingMessageObject == null || ((!this.playingMessageObject.isVoice() && !this.playingMessageObject.isRoundVideo()) || this.useFrontSpeaker))) {
                    FileLog.m13726e("start listen by proximity only");
                    if (!(!this.proximityHasDifferentValues || this.proximityWakeLock == null || this.proximityWakeLock.isHeld())) {
                        this.proximityWakeLock.acquire();
                    }
                    setUseFrontSpeaker(true);
                    startAudioAgain(false);
                    this.ignoreOnPause = true;
                }
            } else if (!this.proximityTouched) {
                if (this.raiseToEarRecord) {
                    FileLog.m13726e("stop record");
                    stopRecording(2);
                    this.raiseToEarRecord = false;
                    this.ignoreOnPause = false;
                    if (this.proximityHasDifferentValues && this.proximityWakeLock != null && this.proximityWakeLock.isHeld()) {
                        this.proximityWakeLock.release();
                    }
                } else if (this.useFrontSpeaker) {
                    FileLog.m13726e("stop listen");
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
            FileLog.m13728e(e);
            this.isPaused = false;
            return false;
        }
    }

    public boolean playMessage(final MessageObject messageObject) {
        int i = 3;
        if (messageObject == null) {
            return false;
        }
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || this.playingMessageObject == null || messageObject.getId() != this.playingMessageObject.getId()) {
            File file;
            if (!messageObject.isOut() && messageObject.isContentUnread()) {
                MessagesController.getInstance().markMessageContentAsRead(messageObject);
            }
            boolean z = !this.playMusicAgain;
            if (this.playingMessageObject != null) {
                z = false;
            }
            cleanupPlayer(z, false);
            this.playMusicAgain = false;
            if (messageObject.messageOwner.attachPath == null || messageObject.messageOwner.attachPath.length() <= 0) {
                file = null;
            } else {
                file = new File(messageObject.messageOwner.attachPath);
                if (!file.exists()) {
                    file = null;
                }
            }
            final File pathToMessage = file != null ? file : FileLoader.getPathToMessage(messageObject.messageOwner);
            if (pathToMessage == null || pathToMessage == file || pathToMessage.exists()) {
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
                    this.videoPlayer.setDelegate(new VideoPlayerDelegate() {

                        /* renamed from: org.telegram.messenger.MediaController$16$1 */
                        class C31361 implements Runnable {
                            C31361() {
                            }

                            public void run() {
                                MediaController.this.cleanupPlayer(true, true);
                            }
                        }

                        public void onError(Exception exception) {
                            FileLog.m13728e((Throwable) exception);
                        }

                        public void onRenderedFirstFrame() {
                            if (MediaController.this.currentAspectRatioFrameLayout != null && !MediaController.this.currentAspectRatioFrameLayout.isDrawingReady()) {
                                MediaController.this.isDrawingWasReady = true;
                                MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
                                if (MediaController.this.currentTextureViewContainer != null && MediaController.this.currentTextureViewContainer.getVisibility() != 0) {
                                    MediaController.this.currentTextureViewContainer.setVisibility(0);
                                }
                            }
                        }

                        public void onStateChanged(boolean z, int i) {
                            if (MediaController.this.videoPlayer != null) {
                                if (i == 4 || i == 1) {
                                    try {
                                        MediaController.this.baseActivity.getWindow().clearFlags(128);
                                    } catch (Throwable e) {
                                        FileLog.m13728e(e);
                                    }
                                } else {
                                    try {
                                        MediaController.this.baseActivity.getWindow().addFlags(128);
                                    } catch (Throwable e2) {
                                        FileLog.m13728e(e2);
                                    }
                                }
                                if (i == 3) {
                                    MediaController.this.currentAspectRatioFrameLayoutReady = true;
                                    if (MediaController.this.currentTextureViewContainer != null && MediaController.this.currentTextureViewContainer.getVisibility() != 0) {
                                        MediaController.this.currentTextureViewContainer.setVisibility(0);
                                    }
                                } else if (MediaController.this.videoPlayer.isPlaying() && i == 4) {
                                    MediaController.this.cleanupPlayer(true, true, true);
                                }
                            }
                        }

                        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                            if (MediaController.this.videoPlayer == null) {
                                return false;
                            }
                            if (MediaController.this.pipSwitchingState == 2) {
                                if (MediaController.this.currentAspectRatioFrameLayout != null) {
                                    if (MediaController.this.isDrawingWasReady) {
                                        MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
                                    }
                                    if (MediaController.this.currentAspectRatioFrameLayout.getParent() == null) {
                                        MediaController.this.currentTextureViewContainer.addView(MediaController.this.currentAspectRatioFrameLayout);
                                    }
                                    if (MediaController.this.currentTextureView.getSurfaceTexture() != surfaceTexture) {
                                        MediaController.this.currentTextureView.setSurfaceTexture(surfaceTexture);
                                    }
                                    MediaController.this.videoPlayer.setTextureView(MediaController.this.currentTextureView);
                                }
                                MediaController.this.pipSwitchingState = 0;
                                return true;
                            } else if (MediaController.this.pipSwitchingState != 1) {
                                return false;
                            } else {
                                if (MediaController.this.baseActivity != null) {
                                    if (MediaController.this.pipRoundVideoView == null) {
                                        try {
                                            MediaController.this.pipRoundVideoView = new PipRoundVideoView();
                                            MediaController.this.pipRoundVideoView.show(MediaController.this.baseActivity, new C31361());
                                        } catch (Exception e) {
                                            MediaController.this.pipRoundVideoView = null;
                                        }
                                    }
                                    if (MediaController.this.pipRoundVideoView != null) {
                                        if (MediaController.this.pipRoundVideoView.getTextureView().getSurfaceTexture() != surfaceTexture) {
                                            MediaController.this.pipRoundVideoView.getTextureView().setSurfaceTexture(surfaceTexture);
                                        }
                                        MediaController.this.videoPlayer.setTextureView(MediaController.this.pipRoundVideoView.getTextureView());
                                    }
                                }
                                MediaController.this.pipSwitchingState = 0;
                                return true;
                            }
                        }

                        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                        }

                        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
                            MediaController.this.currentAspectRatioFrameLayoutRotation = i3;
                            if (i3 == 90 || i3 == 270) {
                                int i4 = i;
                                i = i2;
                                i2 = i4;
                            }
                            MediaController.this.currentAspectRatioFrameLayoutRatio = i2 == 0 ? 1.0f : (((float) i) * f) / ((float) i2);
                            if (MediaController.this.currentAspectRatioFrameLayout != null) {
                                MediaController.this.currentAspectRatioFrameLayout.setAspectRatio(MediaController.this.currentAspectRatioFrameLayoutRatio, MediaController.this.currentAspectRatioFrameLayoutRotation);
                            }
                        }
                    });
                    this.currentAspectRatioFrameLayoutReady = false;
                    if (this.pipRoundVideoView != null || !MessagesController.getInstance().isDialogCreated(messageObject.getDialogId())) {
                        if (this.pipRoundVideoView == null) {
                            try {
                                this.pipRoundVideoView = new PipRoundVideoView();
                                this.pipRoundVideoView.show(this.baseActivity, new Runnable() {
                                    public void run() {
                                        MediaController.this.cleanupPlayer(true, true);
                                    }
                                });
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
                    this.videoPlayer.preparePlayer(Uri.fromFile(pathToMessage), "other");
                    this.videoPlayer.setStreamType(this.useFrontSpeaker ? 0 : 3);
                    this.videoPlayer.play();
                } else if (isOpusFile(pathToMessage.getAbsolutePath()) == 1) {
                    this.playlist.clear();
                    this.shuffledPlaylist.clear();
                    synchronized (this.playerObjectSync) {
                        try {
                            this.ignoreFirstProgress = 3;
                            final Semaphore semaphore = new Semaphore(0);
                            final Boolean[] boolArr = new Boolean[1];
                            this.fileDecodingQueue.postRunnable(new Runnable() {
                                public void run() {
                                    boolArr[0] = Boolean.valueOf(MediaController.this.openOpusFile(pathToMessage.getAbsolutePath()) != 0);
                                    semaphore.release();
                                }
                            });
                            semaphore.acquire();
                            if (boolArr[0].booleanValue()) {
                                this.currentTotalPcmDuration = getTotalPcmDuration();
                                if (this.useFrontSpeaker) {
                                    i = 0;
                                }
                                this.audioTrackPlayer = new AudioTrack(i, 48000, 4, 2, this.playerBufferSize, 1);
                                this.audioTrackPlayer.setStereoVolume(1.0f, 1.0f);
                                this.audioTrackPlayer.setPlaybackPositionUpdateListener(new OnPlaybackPositionUpdateListener() {
                                    public void onMarkerReached(AudioTrack audioTrack) {
                                        MediaController.this.cleanupPlayer(true, true, true);
                                    }

                                    public void onPeriodicNotification(AudioTrack audioTrack) {
                                    }
                                });
                                this.audioTrackPlayer.play();
                            } else {
                                return false;
                            }
                        } catch (Throwable e2) {
                            FileLog.m13728e(e2);
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
                        MediaPlayer mediaPlayer = this.audioPlayer;
                        if (this.useFrontSpeaker) {
                            i = 0;
                        }
                        mediaPlayer.setAudioStreamType(i);
                        this.audioPlayer.setDataSource(pathToMessage.getAbsolutePath());
                        this.audioPlayer.setOnCompletionListener(new OnCompletionListener() {
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                if (MediaController.this.playlist.isEmpty() || MediaController.this.playlist.size() <= 1) {
                                    MediaController mediaController = MediaController.this;
                                    boolean z = messageObject != null && messageObject.isVoice();
                                    mediaController.cleanupPlayer(true, true, z);
                                    return;
                                }
                                MediaController.this.playNextMessageWithoutOrder(true);
                            }
                        });
                        this.audioPlayer.prepare();
                        this.audioPlayer.start();
                        if (messageObject.isVoice()) {
                            this.audioInfo = null;
                            this.playlist.clear();
                            this.shuffledPlaylist.clear();
                        } else {
                            try {
                                this.audioInfo = AudioInfo.getAudioInfo(pathToMessage);
                            } catch (Throwable e22) {
                                FileLog.m13728e(e22);
                            }
                        }
                    } catch (Throwable e222) {
                        FileLog.m13728e(e222);
                        NotificationCenter instance = NotificationCenter.getInstance();
                        int i2 = NotificationCenter.messagePlayingPlayStateChanged;
                        Object[] objArr = new Object[1];
                        objArr[0] = Integer.valueOf(this.playingMessageObject != null ? this.playingMessageObject.getId() : 0);
                        instance.postNotificationName(i2, objArr);
                        if (this.audioPlayer == null) {
                            return false;
                        }
                        this.audioPlayer.release();
                        this.audioPlayer = null;
                        this.isPaused = false;
                        this.playingMessageObject = null;
                        this.downloadingCurrentMessage = false;
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
                        if (this.playingMessageObject.audioProgress != BitmapDescriptorFactory.HUE_RED) {
                            this.videoPlayer.seekTo((long) ((int) (((float) this.videoPlayer.getDuration()) * this.playingMessageObject.audioProgress)));
                        }
                    } catch (Throwable e2222) {
                        this.playingMessageObject.audioProgress = BitmapDescriptorFactory.HUE_RED;
                        this.playingMessageObject.audioProgressSec = 0;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                        FileLog.m13728e(e2222);
                    }
                } else if (this.audioPlayer != null) {
                    try {
                        if (this.playingMessageObject.audioProgress != BitmapDescriptorFactory.HUE_RED) {
                            this.audioPlayer.seekTo((int) (((float) this.audioPlayer.getDuration()) * this.playingMessageObject.audioProgress));
                        }
                    } catch (Throwable e22222) {
                        this.playingMessageObject.audioProgress = BitmapDescriptorFactory.HUE_RED;
                        this.playingMessageObject.audioProgressSec = 0;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), Integer.valueOf(0));
                        FileLog.m13728e(e22222);
                    }
                } else if (this.audioTrackPlayer != null) {
                    if (this.playingMessageObject.audioProgress == 1.0f) {
                        this.playingMessageObject.audioProgress = BitmapDescriptorFactory.HUE_RED;
                    }
                    this.fileDecodingQueue.postRunnable(new Runnable() {
                        public void run() {
                            try {
                                if (!(MediaController.this.playingMessageObject == null || MediaController.this.playingMessageObject.audioProgress == BitmapDescriptorFactory.HUE_RED)) {
                                    MediaController.this.lastPlayPcm = (long) (((float) MediaController.this.currentTotalPcmDuration) * MediaController.this.playingMessageObject.audioProgress);
                                    MediaController.this.seekOpusFile(MediaController.this.playingMessageObject.audioProgress);
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                            synchronized (MediaController.this.playerSync) {
                                MediaController.this.freePlayerBuffers.addAll(MediaController.this.usedPlayerBuffers);
                                MediaController.this.usedPlayerBuffers.clear();
                            }
                            MediaController.this.decodingFinished = false;
                            MediaController.this.checkPlayerQueue();
                        }
                    });
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

    public void playMessageAtIndex(int i) {
        if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < this.playlist.size()) {
            this.currentPlaylistNum = i;
            this.playMusicAgain = true;
            playMessage((MessageObject) this.playlist.get(this.currentPlaylistNum));
        }
    }

    public void playNextMessage() {
        playNextMessageWithoutOrder(false);
    }

    public void playPreviousMessage() {
        ArrayList arrayList = this.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (!arrayList.isEmpty()) {
            MessageObject messageObject = (MessageObject) arrayList.get(this.currentPlaylistNum);
            if (messageObject.audioProgressSec > 10) {
                getInstance().seekToProgress(messageObject, BitmapDescriptorFactory.HUE_RED);
                return;
            }
            if (this.playOrderReversed) {
                this.currentPlaylistNum--;
                if (this.currentPlaylistNum < 0) {
                    this.currentPlaylistNum = arrayList.size() - 1;
                }
            } else {
                this.currentPlaylistNum++;
                if (this.currentPlaylistNum >= arrayList.size()) {
                    this.currentPlaylistNum = 0;
                }
            }
            if (this.currentPlaylistNum >= 0 && this.currentPlaylistNum < arrayList.size()) {
                this.playMusicAgain = true;
                playMessage((MessageObject) arrayList.get(this.currentPlaylistNum));
            }
        }
    }

    protected void processDownloadObjects(int i, ArrayList<DownloadObject> arrayList) {
        if (!arrayList.isEmpty()) {
            ArrayList arrayList2 = i == 1 ? this.photoDownloadQueue : i == 2 ? this.audioDownloadQueue : i == 64 ? this.videoMessageDownloadQueue : i == 4 ? this.videoDownloadQueue : i == 8 ? this.documentDownloadQueue : i == 16 ? this.musicDownloadQueue : i == 32 ? this.gifDownloadQueue : null;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                Object attachFileName;
                DownloadObject downloadObject = (DownloadObject) arrayList.get(i2);
                if (downloadObject.object instanceof Document) {
                    attachFileName = FileLoader.getAttachFileName((Document) downloadObject.object);
                } else {
                    String attachFileName2 = FileLoader.getAttachFileName(downloadObject.object);
                }
                if (!this.downloadQueueKeys.containsKey(attachFileName)) {
                    boolean z;
                    if (downloadObject.object instanceof PhotoSize) {
                        FileLoader.getInstance().loadFile((PhotoSize) downloadObject.object, null, downloadObject.secret ? 2 : 0);
                        z = true;
                    } else if (downloadObject.object instanceof Document) {
                        FileLoader.getInstance().loadFile((Document) downloadObject.object, false, downloadObject.secret ? 2 : 0);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        arrayList2.add(downloadObject);
                        this.downloadQueueKeys.put(attachFileName, downloadObject);
                    }
                }
            }
        }
    }

    public void removeLoadingFileObserver(FileDownloadProgressListener fileDownloadProgressListener) {
        if (this.listenerInProgress) {
            this.deleteLaterArray.add(fileDownloadProgressListener);
            return;
        }
        String str = (String) this.observersByTag.get(Integer.valueOf(fileDownloadProgressListener.getObserverTag()));
        if (str != null) {
            ArrayList arrayList = (ArrayList) this.loadingFileObservers.get(str);
            if (arrayList != null) {
                int i = 0;
                while (i < arrayList.size()) {
                    WeakReference weakReference = (WeakReference) arrayList.get(i);
                    if (weakReference.get() == null || weakReference.get() == fileDownloadProgressListener) {
                        arrayList.remove(i);
                        i--;
                    }
                    i++;
                }
                if (arrayList.isEmpty()) {
                    this.loadingFileObservers.remove(str);
                }
            }
            this.observersByTag.remove(Integer.valueOf(fileDownloadProgressListener.getObserverTag()));
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
            FileLog.m13728e(e);
            return false;
        }
    }

    public void scheduleVideoConvert(MessageObject messageObject) {
        scheduleVideoConvert(messageObject, false);
    }

    public boolean scheduleVideoConvert(MessageObject messageObject, boolean z) {
        if (z && !this.videoConvertQueue.isEmpty()) {
            return false;
        }
        if (z) {
            new File(messageObject.messageOwner.attachPath).delete();
        }
        this.videoConvertQueue.add(messageObject);
        if (this.videoConvertQueue.size() != 1) {
            return true;
        }
        startVideoConvertFromQueue();
        return true;
    }

    public boolean seekToProgress(MessageObject messageObject, float f) {
        if ((this.audioTrackPlayer == null && this.audioPlayer == null && this.videoPlayer == null) || messageObject == null || this.playingMessageObject == null) {
            return false;
        }
        if (this.playingMessageObject != null && this.playingMessageObject.getId() != messageObject.getId()) {
            return false;
        }
        try {
            if (this.audioPlayer != null) {
                int duration = (int) (((float) this.audioPlayer.getDuration()) * f);
                this.audioPlayer.seekTo(duration);
                this.lastProgress = (long) duration;
            } else if (this.audioTrackPlayer != null) {
                seekOpusPlayer(f);
            } else if (this.videoPlayer != null) {
                this.videoPlayer.seekTo((long) (((float) this.videoPlayer.getDuration()) * f));
            }
            return true;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return false;
        }
    }

    public void setAllowStartRecord(boolean z) {
        this.allowStartRecord = z;
    }

    public void setBaseActivity(Activity activity, boolean z) {
        if (z) {
            this.baseActivity = activity;
        } else if (this.baseActivity == activity) {
            this.baseActivity = null;
        }
    }

    public void setCurrentRoundVisible(boolean z) {
        if (this.currentAspectRatioFrameLayout != null) {
            if (z) {
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
                        this.pipRoundVideoView.show(this.baseActivity, new Runnable() {
                            public void run() {
                                MediaController.this.cleanupPlayer(true, true);
                            }
                        });
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

    public void setInputFieldHasText(boolean z) {
        this.inputFieldHasText = z;
    }

    public void setLastVisibleMessageIds(long j, long j2, User user, EncryptedChat encryptedChat, ArrayList<Long> arrayList, int i) {
        this.lastChatEnterTime = j;
        this.lastChatLeaveTime = j2;
        this.lastSecretChat = encryptedChat;
        this.lastUser = user;
        this.lastMessageId = i;
        this.lastChatVisibleMessages = arrayList;
    }

    public boolean setPlaylist(ArrayList<MessageObject> arrayList, MessageObject messageObject) {
        return setPlaylist(arrayList, messageObject, true);
    }

    public boolean setPlaylist(ArrayList<MessageObject> arrayList, MessageObject messageObject, boolean z) {
        boolean z2 = true;
        if (this.playingMessageObject == messageObject) {
            return playMessage(messageObject);
        }
        this.forceLoopCurrentPlaylist = !z;
        if (this.playlist.isEmpty()) {
            z2 = false;
        }
        this.playMusicAgain = z2;
        this.playlist.clear();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            MessageObject messageObject2 = (MessageObject) arrayList.get(size);
            if (messageObject2.isMusic()) {
                this.playlist.add(messageObject2);
            }
        }
        this.currentPlaylistNum = this.playlist.indexOf(messageObject);
        if (this.currentPlaylistNum == -1) {
            this.playlist.clear();
            this.shuffledPlaylist.clear();
            this.currentPlaylistNum = this.playlist.size();
            this.playlist.add(messageObject);
        }
        if (messageObject.isMusic()) {
            if (this.shuffleMusic) {
                buildShuffledPlayList();
                this.currentPlaylistNum = 0;
            }
            if (z) {
                SharedMediaQuery.loadMusic(messageObject.getDialogId(), ((MessageObject) this.playlist.get(0)).getIdWithChannel());
            }
        }
        return playMessage(messageObject);
    }

    public void setTextureView(TextureView textureView, AspectRatioFrameLayout aspectRatioFrameLayout, FrameLayout frameLayout, boolean z) {
        boolean z2 = true;
        if (!z && this.currentTextureView == textureView) {
            this.pipSwitchingState = 1;
            this.currentTextureView = null;
            this.currentAspectRatioFrameLayout = null;
            this.currentTextureViewContainer = null;
        } else if (this.videoPlayer != null && textureView != this.currentTextureView) {
            if (aspectRatioFrameLayout == null || !aspectRatioFrameLayout.isDrawingReady()) {
                z2 = false;
            }
            this.isDrawingWasReady = z2;
            this.currentTextureView = textureView;
            if (this.pipRoundVideoView != null) {
                this.videoPlayer.setTextureView(this.pipRoundVideoView.getTextureView());
            } else {
                this.videoPlayer.setTextureView(this.currentTextureView);
            }
            this.currentAspectRatioFrameLayout = aspectRatioFrameLayout;
            this.currentTextureViewContainer = frameLayout;
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

    public void setVoiceMessagesPlaylist(ArrayList<MessageObject> arrayList, boolean z) {
        this.voiceMessagesPlaylist = arrayList;
        if (this.voiceMessagesPlaylist != null) {
            this.voiceMessagesPlaylistUnread = z;
            this.voiceMessagesPlaylistMap = new HashMap();
            for (int i = 0; i < this.voiceMessagesPlaylist.size(); i++) {
                MessageObject messageObject = (MessageObject) this.voiceMessagesPlaylist.get(i);
                this.voiceMessagesPlaylistMap.put(Integer.valueOf(messageObject.getId()), messageObject);
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
                ContentObserver externalObserver = new ExternalObserver();
                this.externalObserver = externalObserver;
                contentResolver.registerContentObserver(uri, false, externalObserver);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        try {
            if (this.externalObserver == null) {
                contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                uri = Media.INTERNAL_CONTENT_URI;
                externalObserver = new InternalObserver();
                this.internalObserver = externalObserver;
                contentResolver.registerContentObserver(uri, false, externalObserver);
            }
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
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
                this.gravity[2] = BitmapDescriptorFactory.HUE_RED;
                fArr2[1] = BitmapDescriptorFactory.HUE_RED;
                fArr[0] = BitmapDescriptorFactory.HUE_RED;
                fArr = this.linearAcceleration;
                fArr2 = this.linearAcceleration;
                this.linearAcceleration[2] = BitmapDescriptorFactory.HUE_RED;
                fArr2[1] = BitmapDescriptorFactory.HUE_RED;
                fArr[0] = BitmapDescriptorFactory.HUE_RED;
                fArr = this.gravityFast;
                fArr2 = this.gravityFast;
                this.gravityFast[2] = BitmapDescriptorFactory.HUE_RED;
                fArr2[1] = BitmapDescriptorFactory.HUE_RED;
                fArr[0] = BitmapDescriptorFactory.HUE_RED;
                this.lastTimestamp = 0;
                this.previousAccValue = BitmapDescriptorFactory.HUE_RED;
                this.raisedToTop = 0;
                this.countLess = 0;
                this.raisedToBack = 0;
                Utilities.globalQueue.postRunnable(new Runnable() {
                    public void run() {
                        if (MediaController.this.gravitySensor != null) {
                            MediaController.this.sensorManager.registerListener(MediaController.this, MediaController.this.gravitySensor, DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
                        }
                        if (MediaController.this.linearSensor != null) {
                            MediaController.this.sensorManager.registerListener(MediaController.this, MediaController.this.linearSensor, DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
                        }
                        if (MediaController.this.accelerometerSensor != null) {
                            MediaController.this.sensorManager.registerListener(MediaController.this, MediaController.this.accelerometerSensor, DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
                        }
                        MediaController.this.sensorManager.registerListener(MediaController.this, MediaController.this.proximitySensor, 3);
                    }
                });
                this.sensorsStarted = true;
            }
        }
    }

    public void startRecording(final long j, final MessageObject messageObject) {
        Object obj = null;
        if (!(this.playingMessageObject == null || !isPlayingMessage(this.playingMessageObject) || isMessagePaused())) {
            obj = 1;
            pauseMessage(this.playingMessageObject);
        }
        Object obj2 = obj;
        try {
            ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(10);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        DispatchQueue dispatchQueue = this.recordQueue;
        Runnable anonymousClass22 = new Runnable() {

            /* renamed from: org.telegram.messenger.MediaController$22$1 */
            class C31381 implements Runnable {
                C31381() {
                }

                public void run() {
                    MediaController.this.recordStartRunnable = null;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
                }
            }

            /* renamed from: org.telegram.messenger.MediaController$22$2 */
            class C31392 implements Runnable {
                C31392() {
                }

                public void run() {
                    MediaController.this.recordStartRunnable = null;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
                }
            }

            /* renamed from: org.telegram.messenger.MediaController$22$3 */
            class C31403 implements Runnable {
                C31403() {
                }

                public void run() {
                    MediaController.this.recordStartRunnable = null;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStartError, new Object[0]);
                }
            }

            /* renamed from: org.telegram.messenger.MediaController$22$4 */
            class C31414 implements Runnable {
                C31414() {
                }

                public void run() {
                    MediaController.this.recordStartRunnable = null;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStarted, new Object[0]);
                }
            }

            public void run() {
                if (MediaController.this.audioRecorder != null) {
                    AndroidUtilities.runOnUIThread(new C31381());
                    return;
                }
                MediaController.this.recordingAudio = new TLRPC$TL_document();
                MediaController.this.recordingAudio.dc_id = Integer.MIN_VALUE;
                MediaController.this.recordingAudio.id = (long) UserConfig.lastLocalId;
                MediaController.this.recordingAudio.user_id = UserConfig.getClientUserId();
                MediaController.this.recordingAudio.mime_type = "audio/ogg";
                MediaController.this.recordingAudio.thumb = new TLRPC$TL_photoSizeEmpty();
                MediaController.this.recordingAudio.thumb.type = "s";
                UserConfig.lastLocalId--;
                UserConfig.saveConfig(false);
                MediaController.this.recordingAudioFile = new File(FileLoader.getInstance().getDirectory(4), FileLoader.getAttachFileName(MediaController.this.recordingAudio));
                try {
                    if (MediaController.this.startRecord(MediaController.this.recordingAudioFile.getAbsolutePath()) == 0) {
                        AndroidUtilities.runOnUIThread(new C31392());
                        return;
                    }
                    MediaController.this.audioRecorder = new AudioRecord(1, 16000, 16, 2, MediaController.this.recordBufferSize * 10);
                    MediaController.this.recordStartTime = System.currentTimeMillis();
                    MediaController.this.recordTimeCount = 0;
                    MediaController.this.samplesCount = 0;
                    MediaController.this.recordDialogId = j;
                    MediaController.this.recordReplyingMessageObject = messageObject;
                    MediaController.this.fileBuffer.rewind();
                    MediaController.this.audioRecorder.startRecording();
                    MediaController.this.recordQueue.postRunnable(MediaController.this.recordRunnable);
                    AndroidUtilities.runOnUIThread(new C31414());
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                    MediaController.this.recordingAudio = null;
                    MediaController.this.stopRecord();
                    MediaController.this.recordingAudioFile.delete();
                    MediaController.this.recordingAudioFile = null;
                    try {
                        MediaController.this.audioRecorder.release();
                        MediaController.this.audioRecorder = null;
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                    AndroidUtilities.runOnUIThread(new C31403());
                }
            }
        };
        this.recordStartRunnable = anonymousClass22;
        dispatchQueue.postRunnable(anonymousClass22, obj2 != null ? 500 : 50);
    }

    public void startRecordingIfFromSpeaker() {
        if (this.useFrontSpeaker && this.raiseChat != null && this.allowStartRecord) {
            this.raiseToEarRecord = true;
            startRecording(this.raiseChat.getDialogId(), null);
            this.ignoreOnPause = true;
        }
    }

    public void startSmsObserver() {
        try {
            if (this.smsObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri parse = Uri.parse("content://sms");
                ContentObserver smsObserver = new SmsObserver();
                this.smsObserver = smsObserver;
                contentResolver.registerContentObserver(parse, false, smsObserver);
            }
            AndroidUtilities.runOnUIThread(new C31577(), 300000);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public void stopAudio() {
        if ((this.audioTrackPlayer != null || this.audioPlayer != null || this.videoPlayer != null) && this.playingMessageObject != null) {
            try {
                if (this.audioPlayer != null) {
                    try {
                        this.audioPlayer.reset();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
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
                                FileLog.m13728e(e2);
                            }
                        }
                    } catch (Throwable e22) {
                        FileLog.m13728e(e22);
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
                FileLog.m13728e(e222);
            }
        }
    }

    public void stopMediaObserver() {
        if (this.stopMediaObserverRunnable == null) {
            this.stopMediaObserverRunnable = new StopMediaObserverRunnable();
        }
        this.stopMediaObserverRunnable.currentObserverToken = this.startObserverToken;
        ApplicationLoader.applicationHandler.postDelayed(this.stopMediaObserverRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
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
                Utilities.globalQueue.postRunnable(new Runnable() {
                    public void run() {
                        if (MediaController.this.linearSensor != null) {
                            MediaController.this.sensorManager.unregisterListener(MediaController.this, MediaController.this.linearSensor);
                        }
                        if (MediaController.this.gravitySensor != null) {
                            MediaController.this.sensorManager.unregisterListener(MediaController.this, MediaController.this.gravitySensor);
                        }
                        if (MediaController.this.accelerometerSensor != null) {
                            MediaController.this.sensorManager.unregisterListener(MediaController.this, MediaController.this.accelerometerSensor);
                        }
                        MediaController.this.sensorManager.unregisterListener(MediaController.this, MediaController.this.proximitySensor);
                    }
                });
                if (this.proximityHasDifferentValues && this.proximityWakeLock != null && this.proximityWakeLock.isHeld()) {
                    this.proximityWakeLock.release();
                }
            }
        }
    }

    public void stopRecording(final int i) {
        if (this.recordStartRunnable != null) {
            this.recordQueue.cancelRunnable(this.recordStartRunnable);
            this.recordStartRunnable = null;
        }
        this.recordQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.MediaController$25$1 */
            class C31461 implements Runnable {
                C31461() {
                }

                public void run() {
                    int i = 1;
                    NotificationCenter instance = NotificationCenter.getInstance();
                    int i2 = NotificationCenter.recordStopped;
                    Object[] objArr = new Object[1];
                    if (i != 2) {
                        i = 0;
                    }
                    objArr[0] = Integer.valueOf(i);
                    instance.postNotificationName(i2, objArr);
                }
            }

            public void run() {
                if (MediaController.this.audioRecorder != null) {
                    try {
                        MediaController.this.sendAfterDone = i;
                        MediaController.this.audioRecorder.stop();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                        if (MediaController.this.recordingAudioFile != null) {
                            MediaController.this.recordingAudioFile.delete();
                        }
                    }
                    if (i == 0) {
                        MediaController.this.stopRecordingInternal(0);
                    }
                    try {
                        ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(10);
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                    AndroidUtilities.runOnUIThread(new C31461());
                }
            }
        });
    }

    public void toggleAutoplayGifs() {
        this.autoplayGifs = !this.autoplayGifs;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("autoplay_gif", this.autoplayGifs);
        edit.commit();
    }

    public void toggleCustomTabs() {
        this.customTabs = !this.customTabs;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("custom_tabs", this.customTabs);
        edit.commit();
    }

    public void toggleDirectShare() {
        this.directShare = !this.directShare;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("direct_share", this.directShare);
        edit.commit();
    }

    public void toggleGroupPhotosEnabled() {
        this.groupPhotosEnabled = !this.groupPhotosEnabled;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("groupPhotosEnabled", this.groupPhotosEnabled);
        edit.commit();
    }

    public void toggleInappCamera() {
        this.inappCamera = !this.inappCamera;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("direct_share", this.inappCamera);
        edit.commit();
    }

    public void toggleRepeatMode() {
        this.repeatMode++;
        if (this.repeatMode > 2) {
            this.repeatMode = 0;
        }
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putInt("repeatMode", this.repeatMode);
        edit.commit();
    }

    public void toggleRoundCamera16to9() {
        this.roundCamera16to9 = !this.roundCamera16to9;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("roundCamera16to9", this.roundCamera16to9);
        edit.commit();
    }

    public void toggleSaveToGallery() {
        this.saveToGallery = !this.saveToGallery;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("save_gallery", this.saveToGallery);
        edit.commit();
        checkSaveToGalleryFiles();
    }

    public void toggleShuffleMusic(int i) {
        boolean z = this.shuffleMusic;
        if (i == 2) {
            this.shuffleMusic = !this.shuffleMusic;
        } else {
            this.playOrderReversed = !this.playOrderReversed;
        }
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("shuffleMusic", this.shuffleMusic);
        edit.putBoolean("playOrderReversed", this.playOrderReversed);
        edit.commit();
        if (z == this.shuffleMusic) {
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

    public void toogleRaiseToSpeak() {
        this.raiseToSpeak = !this.raiseToSpeak;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("raise_to_speak", this.raiseToSpeak);
        edit.commit();
    }
}
