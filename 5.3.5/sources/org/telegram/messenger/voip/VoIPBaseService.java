package org.telegram.messenger.voip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.voip.VoIPController.ConnectionStateListener;
import org.telegram.messenger.voip.VoIPController.Stats;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.VoIPActivity;
import org.telegram.ui.VoIPPermissionActivity;

public abstract class VoIPBaseService extends Service implements SensorEventListener, OnAudioFocusChangeListener, ConnectionStateListener {
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final int DISCARD_REASON_DISCONNECT = 2;
    public static final int DISCARD_REASON_HANGUP = 1;
    public static final int DISCARD_REASON_LINE_BUSY = 4;
    public static final int DISCARD_REASON_MISSED = 3;
    protected static final int ID_INCOMING_CALL_NOTIFICATION = 202;
    protected static final int ID_ONGOING_CALL_NOTIFICATION = 201;
    protected static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final int STATE_ENDED = 11;
    public static final int STATE_ESTABLISHED = 3;
    public static final int STATE_FAILED = 4;
    public static final int STATE_RECONNECTING = 5;
    public static final int STATE_WAIT_INIT = 1;
    public static final int STATE_WAIT_INIT_ACK = 2;
    protected static VoIPBaseService sharedInstance;
    protected Runnable afterSoundRunnable = new C19031();
    protected BluetoothAdapter btAdapter;
    protected VoIPController controller;
    protected boolean controllerStarted;
    protected WakeLock cpuWakelock;
    protected int currentState = 0;
    protected boolean haveAudioFocus;
    protected boolean isBtHeadsetConnected;
    protected boolean isHeadsetPlugged;
    protected boolean isOutgoing;
    protected boolean isProximityNear;
    protected int lastError;
    protected long lastKnownDuration = 0;
    protected NetworkInfo lastNetInfo;
    private Boolean mHasEarpiece = null;
    protected boolean micMute;
    protected boolean needPlayEndSound;
    protected Notification ongoingCallNotification;
    protected boolean playingSound;
    protected Stats prevStats = new Stats();
    protected WakeLock proximityWakelock;
    protected BroadcastReceiver receiver = new C19042();
    protected MediaPlayer ringtonePlayer;
    protected int signalBarCount;
    protected SoundPool soundPool;
    protected int spBusyId;
    protected int spConnectingId;
    protected int spEndId;
    protected int spFailedID;
    protected int spPlayID;
    protected int spRingbackID;
    protected ArrayList<StateListener> stateListeners = new ArrayList();
    protected Stats stats = new Stats();
    protected Runnable timeoutRunnable;
    protected Vibrator vibrator;
    private boolean wasEstablished;

    /* renamed from: org.telegram.messenger.voip.VoIPBaseService$1 */
    class C19031 implements Runnable {
        C19031() {
        }

        public void run() {
            VoIPBaseService.this.soundPool.release();
            if (VoIPBaseService.this.isBtHeadsetConnected) {
                ((AudioManager) ApplicationLoader.applicationContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).stopBluetoothSco();
            }
            ((AudioManager) ApplicationLoader.applicationContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).setSpeakerphoneOn(false);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPBaseService$2 */
    class C19042 extends BroadcastReceiver {
        C19042() {
        }

        public void onReceive(Context context, Intent intent) {
            boolean z = true;
            VoIPBaseService voIPBaseService;
            if (VoIPBaseService.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                voIPBaseService = VoIPBaseService.this;
                if (intent.getIntExtra("state", 0) != 1) {
                    z = false;
                }
                voIPBaseService.isHeadsetPlugged = z;
                if (VoIPBaseService.this.isHeadsetPlugged && VoIPBaseService.this.proximityWakelock != null && VoIPBaseService.this.proximityWakelock.isHeld()) {
                    VoIPBaseService.this.proximityWakelock.release();
                }
                VoIPBaseService.this.isProximityNear = false;
                VoIPBaseService.this.updateOutputGainControlState();
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                VoIPBaseService.this.updateNetworkType();
            } else if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                voIPBaseService = VoIPBaseService.this;
                if (intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0) != 2) {
                    z = false;
                }
                voIPBaseService.updateBluetoothHeadsetState(z);
            } else if ("android.media.ACTION_SCO_AUDIO_STATE_UPDATED".equals(intent.getAction())) {
                Iterator it = VoIPBaseService.this.stateListeners.iterator();
                while (it.hasNext()) {
                    ((StateListener) it.next()).onAudioSettingsChanged();
                }
            } else if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
                if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(intent.getStringExtra("state"))) {
                    VoIPBaseService.this.hangUp();
                }
            }
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPBaseService$3 */
    class C19053 implements Runnable {
        C19053() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didEndedCall, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPBaseService$4 */
    class C19064 implements Runnable {
        C19064() {
        }

        public void run() {
            if (VoIPBaseService.this.controller != null) {
                StatsController.getInstance().incrementTotalCallsTime(VoIPBaseService.this.getStatsNetworkType(), 5);
                AndroidUtilities.runOnUIThread(this, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            }
        }
    }

    public interface StateListener {
        void onAudioSettingsChanged();

        void onSignalBarsCountChanged(int i);

        void onStateChanged(int i);
    }

    public abstract void acceptIncomingCall();

    public abstract void declineIncomingCall();

    public abstract void declineIncomingCall(int i, Runnable runnable);

    protected abstract long getCallID();

    public abstract void hangUp();

    protected abstract void showNotification();

    protected abstract void updateServerConfig();

    public boolean hasEarpiece() {
        if (((TelephonyManager) getSystemService("phone")).getPhoneType() != 0) {
            return true;
        }
        if (this.mHasEarpiece != null) {
            return this.mHasEarpiece.booleanValue();
        }
        try {
            AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            Method method = AudioManager.class.getMethod("getDevicesForStream", new Class[]{Integer.TYPE});
            int earpieceFlag = AudioManager.class.getField("DEVICE_OUT_EARPIECE").getInt(null);
            if ((((Integer) method.invoke(am, new Object[]{Integer.valueOf(0)})).intValue() & earpieceFlag) == earpieceFlag) {
                this.mHasEarpiece = Boolean.TRUE;
            } else {
                this.mHasEarpiece = Boolean.FALSE;
            }
        } catch (Throwable error) {
            FileLog.e("Error while checking earpiece! ", error);
            this.mHasEarpiece = Boolean.TRUE;
        }
        return this.mHasEarpiece.booleanValue();
    }

    protected int getStatsNetworkType() {
        if (this.lastNetInfo == null || this.lastNetInfo.getType() != 0) {
            return 1;
        }
        return this.lastNetInfo.isRoaming() ? 2 : 0;
    }

    public void registerStateListener(StateListener l) {
        this.stateListeners.add(l);
        if (this.currentState != 0) {
            l.onStateChanged(this.currentState);
        }
        if (this.signalBarCount != 0) {
            l.onSignalBarsCountChanged(this.signalBarCount);
        }
    }

    public void unregisterStateListener(StateListener l) {
        this.stateListeners.remove(l);
    }

    public void setMicMute(boolean mute) {
        VoIPController voIPController = this.controller;
        this.micMute = mute;
        voIPController.setMicMute(mute);
    }

    public boolean isMicMute() {
        return this.micMute;
    }

    public String getDebugString() {
        return this.controller.getDebugString();
    }

    public long getCallDuration() {
        if (!this.controllerStarted || this.controller == null) {
            return this.lastKnownDuration;
        }
        long callDuration = this.controller.getCallDuration();
        this.lastKnownDuration = callDuration;
        return callDuration;
    }

    public static VoIPBaseService getSharedInstance() {
        return sharedInstance;
    }

    public void stopRinging() {
        if (this.ringtonePlayer != null) {
            this.ringtonePlayer.stop();
            this.ringtonePlayer.release();
            this.ringtonePlayer = null;
        }
        if (this.vibrator != null) {
            this.vibrator.cancel();
            this.vibrator = null;
        }
    }

    protected void showNotification(String name, TLRPC$FileLocation photo, Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(805306368);
        Builder builder = new Builder(this).setContentTitle(LocaleController.getString("VoipOutgoingCall", R.string.VoipOutgoingCall)).setContentText(name).setSmallIcon(R.drawable.notification).setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));
        if (VERSION.SDK_INT >= 16) {
            Intent endIntent = new Intent(this, VoIPActionsReceiver.class);
            endIntent.setAction(getPackageName() + ".END_CALL");
            builder.addAction(R.drawable.ic_call_end_white_24dp, LocaleController.getString("VoipEndCall", R.string.VoipEndCall), PendingIntent.getBroadcast(this, 0, endIntent, 134217728));
            builder.setPriority(2);
        }
        if (VERSION.SDK_INT >= 17) {
            builder.setShowWhen(false);
        }
        if (VERSION.SDK_INT >= 21) {
            builder.setColor(-13851168);
        }
        if (photo != null) {
            BitmapDrawable img = ImageLoader.getInstance().getImageFromMemory(photo, null, "50_50");
            if (img != null) {
                builder.setLargeIcon(img.getBitmap());
            } else {
                try {
                    float scaleFactor = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                    Options options = new Options();
                    options.inSampleSize = scaleFactor < 1.0f ? 1 : (int) scaleFactor;
                    Bitmap bitmap = BitmapFactory.decodeFile(FileLoader.getPathToAttach(photo, true).toString(), options);
                    if (bitmap != null) {
                        builder.setLargeIcon(bitmap);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }
        this.ongoingCallNotification = builder.getNotification();
        startForeground(201, this.ongoingCallNotification);
    }

    public void onDestroy() {
        FileLog.d("=============== VoIPService STOPPING ===============");
        stopForeground(true);
        stopRinging();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appDidLogout);
        SensorManager sm = (SensorManager) getSystemService("sensor");
        if (sm.getDefaultSensor(8) != null) {
            sm.unregisterListener(this);
        }
        if (this.proximityWakelock != null && this.proximityWakelock.isHeld()) {
            this.proximityWakelock.release();
        }
        unregisterReceiver(this.receiver);
        if (this.timeoutRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.timeoutRunnable);
            this.timeoutRunnable = null;
        }
        super.onDestroy();
        sharedInstance = null;
        AndroidUtilities.runOnUIThread(new C19053());
        if (this.controller != null && this.controllerStarted) {
            this.lastKnownDuration = this.controller.getCallDuration();
            updateStats();
            StatsController.getInstance().incrementTotalCallsTime(getStatsNetworkType(), ((int) (this.lastKnownDuration / 1000)) % 5);
            onControllerPreRelease();
            this.controller.release();
            this.controller = null;
        }
        this.cpuWakelock.release();
        AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        if (this.isBtHeadsetConnected && !this.playingSound) {
            am.stopBluetoothSco();
            am.setSpeakerphoneOn(false);
        }
        try {
            am.setMode(0);
        } catch (SecurityException x) {
            FileLog.e("Error setting audio more to normal", x);
        }
        am.unregisterMediaButtonEventReceiver(new ComponentName(this, VoIPMediaButtonReceiver.class));
        if (this.haveAudioFocus) {
            am.abandonAudioFocus(this);
        }
        if (!this.playingSound) {
            this.soundPool.release();
        }
        ConnectionsManager.getInstance().setAppPaused(true, false);
        VoIPHelper.lastCallTime = System.currentTimeMillis();
    }

    protected void onControllerPreRelease() {
    }

    protected VoIPController createController() {
        return new VoIPController();
    }

    public void onCreate() {
        super.onCreate();
        FileLog.d("=============== VoIPService STARTING ===============");
        AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        if (VERSION.SDK_INT < 17 || am.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER") == null) {
            VoIPController.setNativeBufferSize(AudioTrack.getMinBufferSize(48000, 4, 2) / 2);
        } else {
            VoIPController.setNativeBufferSize(Integer.parseInt(am.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER")));
        }
        updateServerConfig();
        try {
            this.controller = createController();
            this.controller.setConnectionStateListener(this);
            this.cpuWakelock = ((PowerManager) getSystemService("power")).newWakeLock(1, "telegram-voip");
            this.cpuWakelock.acquire();
            this.btAdapter = am.isBluetoothScoAvailableOffCall() ? BluetoothAdapter.getDefaultAdapter() : null;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction(ACTION_HEADSET_PLUG);
            if (this.btAdapter != null) {
                filter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
                filter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            }
            filter.addAction("android.intent.action.PHONE_STATE");
            registerReceiver(this.receiver, filter);
            ConnectionsManager.getInstance().setAppPaused(false, false);
            this.soundPool = new SoundPool(1, 0, 0);
            this.spConnectingId = this.soundPool.load(this, R.raw.voip_connecting, 1);
            this.spRingbackID = this.soundPool.load(this, R.raw.voip_ringback, 1);
            this.spFailedID = this.soundPool.load(this, R.raw.voip_failed, 1);
            this.spEndId = this.soundPool.load(this, R.raw.voip_end, 1);
            this.spBusyId = this.soundPool.load(this, R.raw.voip_busy, 1);
            am.registerMediaButtonEventReceiver(new ComponentName(this, VoIPMediaButtonReceiver.class));
            if (this.btAdapter != null && this.btAdapter.isEnabled()) {
                boolean z;
                int headsetState = this.btAdapter.getProfileConnectionState(1);
                if (headsetState == 2) {
                    z = true;
                } else {
                    z = false;
                }
                updateBluetoothHeadsetState(z);
                if (headsetState == 2) {
                    am.setBluetoothScoOn(true);
                }
                Iterator it = this.stateListeners.iterator();
                while (it.hasNext()) {
                    ((StateListener) it.next()).onAudioSettingsChanged();
                }
            }
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.appDidLogout);
        } catch (Exception x) {
            FileLog.e("error initializing voip controller", x);
            callFailed();
        }
    }

    protected void dispatchStateChanged(int state) {
        FileLog.d("== Call " + getCallID() + " state changed to " + state + " ==");
        this.currentState = state;
        for (int a = 0; a < this.stateListeners.size(); a++) {
            ((StateListener) this.stateListeners.get(a)).onStateChanged(state);
        }
    }

    protected void updateStats() {
        this.controller.getStats(this.stats);
        long wifiSentDiff = this.stats.bytesSentWifi - this.prevStats.bytesSentWifi;
        long wifiRecvdDiff = this.stats.bytesRecvdWifi - this.prevStats.bytesRecvdWifi;
        long mobileSentDiff = this.stats.bytesSentMobile - this.prevStats.bytesSentMobile;
        long mobileRecvdDiff = this.stats.bytesRecvdMobile - this.prevStats.bytesRecvdMobile;
        Stats tmp = this.stats;
        this.stats = this.prevStats;
        this.prevStats = tmp;
        if (wifiSentDiff > 0) {
            StatsController.getInstance().incrementSentBytesCount(1, 0, wifiSentDiff);
        }
        if (wifiRecvdDiff > 0) {
            StatsController.getInstance().incrementReceivedBytesCount(1, 0, wifiRecvdDiff);
        }
        if (mobileSentDiff > 0) {
            StatsController instance = StatsController.getInstance();
            int i = (this.lastNetInfo == null || !this.lastNetInfo.isRoaming()) ? 0 : 2;
            instance.incrementSentBytesCount(i, 0, mobileSentDiff);
        }
        if (mobileRecvdDiff > 0) {
            instance = StatsController.getInstance();
            i = (this.lastNetInfo == null || !this.lastNetInfo.isRoaming()) ? 0 : 2;
            instance.incrementReceivedBytesCount(i, 0, mobileRecvdDiff);
        }
    }

    protected void configureDeviceForCall() {
        this.needPlayEndSound = true;
        AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        am.setMode(3);
        am.setSpeakerphoneOn(false);
        am.requestAudioFocus(this, 0, 1);
        updateOutputGainControlState();
        SensorManager sm = (SensorManager) getSystemService("sensor");
        Sensor proximity = sm.getDefaultSensor(8);
        if (proximity != null) {
            try {
                this.proximityWakelock = ((PowerManager) getSystemService("power")).newWakeLock(32, "telegram-voip-prx");
                sm.registerListener(this, proximity, 3);
            } catch (Exception x) {
                FileLog.e("Error initializing proximity sensor", x);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void onSensorChanged(SensorEvent event) {
        boolean newIsNear = true;
        if (event.sensor.getType() == 8) {
            AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (!this.isHeadsetPlugged && !am.isSpeakerphoneOn()) {
                if (!isBluetoothHeadsetConnected() || !am.isBluetoothScoOn()) {
                    if (event.values[0] >= Math.min(event.sensor.getMaximumRange(), 3.0f)) {
                        newIsNear = false;
                    }
                    if (newIsNear != this.isProximityNear) {
                        FileLog.d("proximity " + newIsNear);
                        this.isProximityNear = newIsNear;
                        try {
                            if (this.isProximityNear) {
                                this.proximityWakelock.acquire();
                            } else {
                                this.proximityWakelock.release(1);
                            }
                        } catch (Exception x) {
                            FileLog.e(x);
                        }
                    }
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public boolean isBluetoothHeadsetConnected() {
        return this.isBtHeadsetConnected;
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange == 1) {
            this.haveAudioFocus = true;
        } else {
            this.haveAudioFocus = false;
        }
    }

    protected void updateBluetoothHeadsetState(boolean connected) {
        if (connected != this.isBtHeadsetConnected) {
            this.isBtHeadsetConnected = connected;
            AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (connected) {
                am.startBluetoothSco();
                am.setSpeakerphoneOn(false);
                am.setBluetoothScoOn(true);
            } else {
                am.stopBluetoothSco();
            }
            Iterator it = this.stateListeners.iterator();
            while (it.hasNext()) {
                ((StateListener) it.next()).onAudioSettingsChanged();
            }
        }
    }

    public int getLastError() {
        return this.lastError;
    }

    public int getCallState() {
        return this.currentState;
    }

    protected void updateNetworkType() {
        NetworkInfo info = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        this.lastNetInfo = info;
        int type = 0;
        if (info != null) {
            switch (info.getType()) {
                case 0:
                    switch (info.getSubtype()) {
                        case 1:
                            type = 1;
                            break;
                        case 2:
                        case 7:
                            type = 2;
                            break;
                        case 3:
                        case 5:
                            type = 3;
                            break;
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 15:
                            type = 4;
                            break;
                        case 13:
                            type = 5;
                            break;
                        default:
                            type = 11;
                            break;
                    }
                case 1:
                    type = 6;
                    break;
                case 9:
                    type = 7;
                    break;
            }
        }
        if (this.controller != null) {
            this.controller.setNetworkType(type);
        }
    }

    protected void callFailed() {
        int lastError = (this.controller == null || !this.controllerStarted) ? 0 : this.controller.getLastError();
        callFailed(lastError);
    }

    protected void callFailed(int errorCode) {
        try {
            throw new Exception("Call " + getCallID() + " failed with error code " + errorCode);
        } catch (Exception x) {
            FileLog.e(x);
            this.lastError = errorCode;
            dispatchStateChanged(4);
            if (!(errorCode == -3 || this.soundPool == null)) {
                this.playingSound = true;
                this.soundPool.play(this.spFailedID, 1.0f, 1.0f, 0, 0, 1.0f);
                AndroidUtilities.runOnUIThread(this.afterSoundRunnable, 1000);
            }
            stopSelf();
        }
    }

    public void onConnectionStateChanged(int newState) {
        if (newState == 4) {
            callFailed();
            return;
        }
        if (newState == 3) {
            if (this.spPlayID != 0) {
                this.soundPool.stop(this.spPlayID);
                this.spPlayID = 0;
            }
            if (!this.wasEstablished) {
                this.wasEstablished = true;
                if (!this.isProximityNear) {
                    Vibrator vibrator = (Vibrator) getSystemService("vibrator");
                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(100);
                    }
                }
                AndroidUtilities.runOnUIThread(new C19064(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                if (this.isOutgoing) {
                    StatsController.getInstance().incrementSentItemsCount(getStatsNetworkType(), 0, 1);
                } else {
                    StatsController.getInstance().incrementReceivedItemsCount(getStatsNetworkType(), 0, 1);
                }
            }
        }
        if (newState == 5) {
            if (this.spPlayID != 0) {
                this.soundPool.stop(this.spPlayID);
            }
            this.spPlayID = this.soundPool.play(this.spConnectingId, 1.0f, 1.0f, 0, -1, 1.0f);
        }
        dispatchStateChanged(newState);
    }

    public void onSignalBarCountChanged(int newCount) {
        this.signalBarCount = newCount;
        for (int a = 0; a < this.stateListeners.size(); a++) {
            ((StateListener) this.stateListeners.get(a)).onSignalBarsCountChanged(newCount);
        }
    }

    protected void callEnded() {
        FileLog.d("Call " + getCallID() + " ended");
        dispatchStateChanged(11);
        if (this.needPlayEndSound) {
            this.playingSound = true;
            this.soundPool.play(this.spEndId, 1.0f, 1.0f, 0, 0, 1.0f);
            AndroidUtilities.runOnUIThread(this.afterSoundRunnable, 700);
        }
        if (this.timeoutRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.timeoutRunnable);
            this.timeoutRunnable = null;
        }
        stopSelf();
    }

    public boolean isOutgoing() {
        return this.isOutgoing;
    }

    public void handleNotificationAction(Intent intent) {
        if ((getPackageName() + ".END_CALL").equals(intent.getAction())) {
            stopForeground(true);
            hangUp();
        } else if ((getPackageName() + ".DECLINE_CALL").equals(intent.getAction())) {
            stopForeground(true);
            declineIncomingCall(4, null);
        } else if ((getPackageName() + ".ANSWER_CALL").equals(intent.getAction())) {
            showNotification();
            if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                acceptIncomingCall();
                try {
                    PendingIntent.getActivity(this, 0, new Intent(this, VoIPActivity.class).addFlags(805306368), 0).send();
                    return;
                } catch (Exception x) {
                    FileLog.e("Error starting incall activity", x);
                    return;
                }
            }
            try {
                PendingIntent.getActivity(this, 0, new Intent(this, VoIPPermissionActivity.class).addFlags(268435456), 0).send();
            } catch (Exception x2) {
                FileLog.e("Error starting permission activity", x2);
            }
        }
    }

    public void updateOutputGainControlState() {
        AudioManager am = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        VoIPController voIPController = this.controller;
        boolean z = (!hasEarpiece() || am.isSpeakerphoneOn() || am.isBluetoothScoOn() || this.isHeadsetPlugged) ? false : true;
        voIPController.setAudioOutputGainControlEnabled(z);
    }
}
