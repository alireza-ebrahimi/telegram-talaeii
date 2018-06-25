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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
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
import org.telegram.tgnet.TLRPC.FileLocation;
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
    protected Runnable afterSoundRunnable = new C37041();
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
    protected BroadcastReceiver receiver = new C37052();
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
    class C37041 implements Runnable {
        C37041() {
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
    class C37052 extends BroadcastReceiver {
        C37052() {
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
    class C37063 implements Runnable {
        C37063() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didEndedCall, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPBaseService$4 */
    class C37074 implements Runnable {
        C37074() {
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

    public static VoIPBaseService getSharedInstance() {
        return sharedInstance;
    }

    public abstract void acceptIncomingCall();

    protected void callEnded() {
        FileLog.m13725d("Call " + getCallID() + " ended");
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

    protected void callFailed() {
        int lastError = (this.controller == null || !this.controllerStarted) ? 0 : this.controller.getLastError();
        callFailed(lastError);
    }

    protected void callFailed(int i) {
        try {
            throw new Exception("Call " + getCallID() + " failed with error code " + i);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            this.lastError = i;
            dispatchStateChanged(4);
            if (!(i == -3 || this.soundPool == null)) {
                this.playingSound = true;
                this.soundPool.play(this.spFailedID, 1.0f, 1.0f, 0, 0, 1.0f);
                AndroidUtilities.runOnUIThread(this.afterSoundRunnable, 1000);
            }
            stopSelf();
        }
    }

    protected void configureDeviceForCall() {
        this.needPlayEndSound = true;
        AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        audioManager.setMode(3);
        audioManager.setSpeakerphoneOn(false);
        audioManager.requestAudioFocus(this, 0, 1);
        updateOutputGainControlState();
        SensorManager sensorManager = (SensorManager) getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(8);
        if (defaultSensor != null) {
            try {
                this.proximityWakelock = ((PowerManager) getSystemService("power")).newWakeLock(32, "telegram-voip-prx");
                sensorManager.registerListener(this, defaultSensor, 3);
            } catch (Throwable e) {
                FileLog.m13727e("Error initializing proximity sensor", e);
            }
        }
    }

    protected VoIPController createController() {
        return new VoIPController();
    }

    public abstract void declineIncomingCall();

    public abstract void declineIncomingCall(int i, Runnable runnable);

    protected void dispatchStateChanged(int i) {
        FileLog.m13725d("== Call " + getCallID() + " state changed to " + i + " ==");
        this.currentState = i;
        for (int i2 = 0; i2 < this.stateListeners.size(); i2++) {
            ((StateListener) this.stateListeners.get(i2)).onStateChanged(i);
        }
    }

    public long getCallDuration() {
        if (!this.controllerStarted || this.controller == null) {
            return this.lastKnownDuration;
        }
        long callDuration = this.controller.getCallDuration();
        this.lastKnownDuration = callDuration;
        return callDuration;
    }

    protected abstract long getCallID();

    public int getCallState() {
        return this.currentState;
    }

    public String getDebugString() {
        return this.controller.getDebugString();
    }

    public int getLastError() {
        return this.lastError;
    }

    protected int getStatsNetworkType() {
        return (this.lastNetInfo == null || this.lastNetInfo.getType() != 0) ? 1 : this.lastNetInfo.isRoaming() ? 2 : 0;
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
                } catch (Throwable e) {
                    FileLog.m13727e("Error starting incall activity", e);
                    return;
                }
            }
            try {
                PendingIntent.getActivity(this, 0, new Intent(this, VoIPPermissionActivity.class).addFlags(ErrorDialogData.BINDER_CRASH), 0).send();
            } catch (Throwable e2) {
                FileLog.m13727e("Error starting permission activity", e2);
            }
        }
    }

    public abstract void hangUp();

    public boolean hasEarpiece() {
        if (((TelephonyManager) getSystemService("phone")).getPhoneType() != 0) {
            return true;
        }
        if (this.mHasEarpiece != null) {
            return this.mHasEarpiece.booleanValue();
        }
        try {
            AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            Method method = AudioManager.class.getMethod("getDevicesForStream", new Class[]{Integer.TYPE});
            int i = AudioManager.class.getField("DEVICE_OUT_EARPIECE").getInt(null);
            if ((((Integer) method.invoke(audioManager, new Object[]{Integer.valueOf(0)})).intValue() & i) == i) {
                this.mHasEarpiece = Boolean.TRUE;
            } else {
                this.mHasEarpiece = Boolean.FALSE;
            }
        } catch (Throwable th) {
            FileLog.m13727e("Error while checking earpiece! ", th);
            this.mHasEarpiece = Boolean.TRUE;
        }
        return this.mHasEarpiece.booleanValue();
    }

    public boolean isBluetoothHeadsetConnected() {
        return this.isBtHeadsetConnected;
    }

    public boolean isMicMute() {
        return this.micMute;
    }

    public boolean isOutgoing() {
        return this.isOutgoing;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onAudioFocusChange(int i) {
        if (i == 1) {
            this.haveAudioFocus = true;
        } else {
            this.haveAudioFocus = false;
        }
    }

    public void onConnectionStateChanged(int i) {
        if (i == 4) {
            callFailed();
            return;
        }
        if (i == 3) {
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
                AndroidUtilities.runOnUIThread(new C37074(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                if (this.isOutgoing) {
                    StatsController.getInstance().incrementSentItemsCount(getStatsNetworkType(), 0, 1);
                } else {
                    StatsController.getInstance().incrementReceivedItemsCount(getStatsNetworkType(), 0, 1);
                }
            }
        }
        if (i == 5) {
            if (this.spPlayID != 0) {
                this.soundPool.stop(this.spPlayID);
            }
            this.spPlayID = this.soundPool.play(this.spConnectingId, 1.0f, 1.0f, 0, -1, 1.0f);
        }
        dispatchStateChanged(i);
    }

    protected void onControllerPreRelease() {
    }

    public void onCreate() {
        super.onCreate();
        FileLog.m13725d("=============== VoIPService STARTING ===============");
        AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        if (VERSION.SDK_INT < 17 || audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER") == null) {
            VoIPController.setNativeBufferSize(AudioTrack.getMinBufferSize(48000, 4, 2) / 2);
        } else {
            VoIPController.setNativeBufferSize(Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER")));
        }
        updateServerConfig();
        try {
            this.controller = createController();
            this.controller.setConnectionStateListener(this);
            this.cpuWakelock = ((PowerManager) getSystemService("power")).newWakeLock(1, "telegram-voip");
            this.cpuWakelock.acquire();
            this.btAdapter = audioManager.isBluetoothScoAvailableOffCall() ? BluetoothAdapter.getDefaultAdapter() : null;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addAction(ACTION_HEADSET_PLUG);
            if (this.btAdapter != null) {
                intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
                intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            }
            intentFilter.addAction("android.intent.action.PHONE_STATE");
            registerReceiver(this.receiver, intentFilter);
            ConnectionsManager.getInstance().setAppPaused(false, false);
            this.soundPool = new SoundPool(1, 0, 0);
            this.spConnectingId = this.soundPool.load(this, R.raw.voip_connecting, 1);
            this.spRingbackID = this.soundPool.load(this, R.raw.voip_ringback, 1);
            this.spFailedID = this.soundPool.load(this, R.raw.voip_failed, 1);
            this.spEndId = this.soundPool.load(this, R.raw.voip_end, 1);
            this.spBusyId = this.soundPool.load(this, R.raw.voip_busy, 1);
            audioManager.registerMediaButtonEventReceiver(new ComponentName(this, VoIPMediaButtonReceiver.class));
            if (this.btAdapter != null && this.btAdapter.isEnabled()) {
                int profileConnectionState = this.btAdapter.getProfileConnectionState(1);
                updateBluetoothHeadsetState(profileConnectionState == 2);
                if (profileConnectionState == 2) {
                    audioManager.setBluetoothScoOn(true);
                }
                Iterator it = this.stateListeners.iterator();
                while (it.hasNext()) {
                    ((StateListener) it.next()).onAudioSettingsChanged();
                }
            }
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.appDidLogout);
        } catch (Throwable e) {
            FileLog.m13727e("error initializing voip controller", e);
            callFailed();
        }
    }

    public void onDestroy() {
        FileLog.m13725d("=============== VoIPService STOPPING ===============");
        stopForeground(true);
        stopRinging();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appDidLogout);
        SensorManager sensorManager = (SensorManager) getSystemService("sensor");
        if (sensorManager.getDefaultSensor(8) != null) {
            sensorManager.unregisterListener(this);
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
        AndroidUtilities.runOnUIThread(new C37063());
        if (this.controller != null && this.controllerStarted) {
            this.lastKnownDuration = this.controller.getCallDuration();
            updateStats();
            StatsController.getInstance().incrementTotalCallsTime(getStatsNetworkType(), ((int) (this.lastKnownDuration / 1000)) % 5);
            onControllerPreRelease();
            this.controller.release();
            this.controller = null;
        }
        this.cpuWakelock.release();
        AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        if (this.isBtHeadsetConnected && !this.playingSound) {
            audioManager.stopBluetoothSco();
            audioManager.setSpeakerphoneOn(false);
        }
        try {
            audioManager.setMode(0);
        } catch (Throwable e) {
            FileLog.m13727e("Error setting audio more to normal", e);
        }
        audioManager.unregisterMediaButtonEventReceiver(new ComponentName(this, VoIPMediaButtonReceiver.class));
        if (this.haveAudioFocus) {
            audioManager.abandonAudioFocus(this);
        }
        if (!this.playingSound) {
            this.soundPool.release();
        }
        ConnectionsManager.getInstance().setAppPaused(true, false);
        VoIPHelper.lastCallTime = System.currentTimeMillis();
    }

    @SuppressLint({"NewApi"})
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 8) {
            AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (!this.isHeadsetPlugged && !audioManager.isSpeakerphoneOn()) {
                if (!isBluetoothHeadsetConnected() || !audioManager.isBluetoothScoOn()) {
                    boolean z = sensorEvent.values[0] < Math.min(sensorEvent.sensor.getMaximumRange(), 3.0f);
                    if (z != this.isProximityNear) {
                        FileLog.m13725d("proximity " + z);
                        this.isProximityNear = z;
                        try {
                            if (this.isProximityNear) {
                                this.proximityWakelock.acquire();
                            } else {
                                this.proximityWakelock.release(1);
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                }
            }
        }
    }

    public void onSignalBarCountChanged(int i) {
        this.signalBarCount = i;
        for (int i2 = 0; i2 < this.stateListeners.size(); i2++) {
            ((StateListener) this.stateListeners.get(i2)).onSignalBarsCountChanged(i);
        }
    }

    public void registerStateListener(StateListener stateListener) {
        this.stateListeners.add(stateListener);
        if (this.currentState != 0) {
            stateListener.onStateChanged(this.currentState);
        }
        if (this.signalBarCount != 0) {
            stateListener.onSignalBarsCountChanged(this.signalBarCount);
        }
    }

    public void setMicMute(boolean z) {
        VoIPController voIPController = this.controller;
        this.micMute = z;
        voIPController.setMicMute(z);
    }

    protected abstract void showNotification();

    protected void showNotification(String str, FileLocation fileLocation, Class<? extends Activity> cls) {
        int i = 1;
        Intent intent = new Intent(this, cls);
        intent.addFlags(805306368);
        Builder contentIntent = new Builder(this).setContentTitle(LocaleController.getString("VoipOutgoingCall", R.string.VoipOutgoingCall)).setContentText(str).setSmallIcon(R.drawable.notification).setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));
        if (VERSION.SDK_INT >= 16) {
            Intent intent2 = new Intent(this, VoIPActionsReceiver.class);
            intent2.setAction(getPackageName() + ".END_CALL");
            contentIntent.addAction(R.drawable.ic_call_end_white_24dp, LocaleController.getString("VoipEndCall", R.string.VoipEndCall), PendingIntent.getBroadcast(this, 0, intent2, 134217728));
            contentIntent.setPriority(2);
        }
        if (VERSION.SDK_INT >= 17) {
            contentIntent.setShowWhen(false);
        }
        if (VERSION.SDK_INT >= 21) {
            contentIntent.setColor(-13851168);
        }
        if (fileLocation != null) {
            BitmapDrawable imageFromMemory = ImageLoader.getInstance().getImageFromMemory(fileLocation, null, "50_50");
            if (imageFromMemory != null) {
                contentIntent.setLargeIcon(imageFromMemory.getBitmap());
            } else {
                try {
                    float dp = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                    Options options = new Options();
                    if (dp >= 1.0f) {
                        i = (int) dp;
                    }
                    options.inSampleSize = i;
                    Bitmap decodeFile = BitmapFactory.decodeFile(FileLoader.getPathToAttach(fileLocation, true).toString(), options);
                    if (decodeFile != null) {
                        contentIntent.setLargeIcon(decodeFile);
                    }
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
        }
        this.ongoingCallNotification = contentIntent.getNotification();
        startForeground(ID_ONGOING_CALL_NOTIFICATION, this.ongoingCallNotification);
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

    public void unregisterStateListener(StateListener stateListener) {
        this.stateListeners.remove(stateListener);
    }

    protected void updateBluetoothHeadsetState(boolean z) {
        if (z != this.isBtHeadsetConnected) {
            this.isBtHeadsetConnected = z;
            AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (z) {
                audioManager.startBluetoothSco();
                audioManager.setSpeakerphoneOn(false);
                audioManager.setBluetoothScoOn(true);
            } else {
                audioManager.stopBluetoothSco();
            }
            Iterator it = this.stateListeners.iterator();
            while (it.hasNext()) {
                ((StateListener) it.next()).onAudioSettingsChanged();
            }
        }
    }

    protected void updateNetworkType() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        this.lastNetInfo = activeNetworkInfo;
        int i = 0;
        if (activeNetworkInfo != null) {
            switch (activeNetworkInfo.getType()) {
                case 0:
                    switch (activeNetworkInfo.getSubtype()) {
                        case 1:
                            i = 1;
                            break;
                        case 2:
                        case 7:
                            i = 2;
                            break;
                        case 3:
                        case 5:
                            i = 3;
                            break;
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 15:
                            i = 4;
                            break;
                        case 13:
                            i = 5;
                            break;
                        default:
                            i = 11;
                            break;
                    }
                case 1:
                    i = 6;
                    break;
                case 9:
                    i = 7;
                    break;
            }
        }
        if (this.controller != null) {
            this.controller.setNetworkType(i);
        }
    }

    public void updateOutputGainControlState() {
        AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        VoIPController voIPController = this.controller;
        boolean z = (!hasEarpiece() || audioManager.isSpeakerphoneOn() || audioManager.isBluetoothScoOn() || this.isHeadsetPlugged) ? false : true;
        voIPController.setAudioOutputGainControlEnabled(z);
    }

    protected abstract void updateServerConfig();

    protected void updateStats() {
        this.controller.getStats(this.stats);
        long j = this.stats.bytesSentWifi - this.prevStats.bytesSentWifi;
        long j2 = this.stats.bytesRecvdWifi - this.prevStats.bytesRecvdWifi;
        long j3 = this.stats.bytesSentMobile - this.prevStats.bytesSentMobile;
        long j4 = this.stats.bytesRecvdMobile - this.prevStats.bytesRecvdMobile;
        Stats stats = this.stats;
        this.stats = this.prevStats;
        this.prevStats = stats;
        if (j > 0) {
            StatsController.getInstance().incrementSentBytesCount(1, 0, j);
        }
        if (j2 > 0) {
            StatsController.getInstance().incrementReceivedBytesCount(1, 0, j2);
        }
        if (j3 > 0) {
            StatsController instance = StatsController.getInstance();
            int i = (this.lastNetInfo == null || !this.lastNetInfo.isRoaming()) ? 0 : 2;
            instance.incrementSentBytesCount(i, 0, j3);
        }
        if (j4 > 0) {
            instance = StatsController.getInstance();
            i = (this.lastNetInfo == null || !this.lastNetInfo.isRoaming()) ? 0 : 2;
            instance.incrementReceivedBytesCount(i, 0, j4);
        }
    }
}
