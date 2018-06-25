package org.telegram.messenger.voip;

import android.app.KeyguardManager;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.au;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.wallet.WalletConstants;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.voip.VoIPBaseService.StateListener;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dataJSON;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messages_dhConfig;
import org.telegram.tgnet.TLRPC$TL_messages_getDhConfig;
import org.telegram.tgnet.TLRPC$TL_phoneCall;
import org.telegram.tgnet.TLRPC$TL_phoneCallAccepted;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonDisconnect;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonHangup;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscarded;
import org.telegram.tgnet.TLRPC$TL_phoneCallProtocol;
import org.telegram.tgnet.TLRPC$TL_phoneConnection;
import org.telegram.tgnet.TLRPC$TL_phone_acceptCall;
import org.telegram.tgnet.TLRPC$TL_phone_confirmCall;
import org.telegram.tgnet.TLRPC$TL_phone_discardCall;
import org.telegram.tgnet.TLRPC$TL_phone_getCallConfig;
import org.telegram.tgnet.TLRPC$TL_phone_phoneCall;
import org.telegram.tgnet.TLRPC$TL_phone_receivedCall;
import org.telegram.tgnet.TLRPC$TL_phone_requestCall;
import org.telegram.tgnet.TLRPC$TL_phone_saveCallDebug;
import org.telegram.tgnet.TLRPC$TL_updates;
import org.telegram.tgnet.TLRPC$messages_DhConfig;
import org.telegram.tgnet.TLRPC.PhoneCall;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.VoIPActivity;
import org.telegram.ui.VoIPFeedbackActivity;

public class VoIPService extends VoIPBaseService implements NotificationCenterDelegate {
    private static final int CALL_MAX_LAYER = 65;
    private static final int CALL_MIN_LAYER = 65;
    public static final int STATE_BUSY = 17;
    public static final int STATE_EXCHANGING_KEYS = 12;
    public static final int STATE_HANGING_UP = 10;
    public static final int STATE_REQUESTING = 14;
    public static final int STATE_RINGING = 16;
    public static final int STATE_WAITING = 13;
    public static final int STATE_WAITING_INCOMING = 15;
    private static final String TAG = "tg-voip-service";
    public static PhoneCall callIShouldHavePutIntoIntent;
    private byte[] a_or_b;
    private byte[] authKey;
    private PhoneCall call;
    private int callReqId;
    private Runnable delayedStartOutgoingCall;
    private boolean endCallAfterRequest = false;
    private boolean forceRating;
    private byte[] g_a;
    private byte[] g_a_hash;
    private long keyFingerprint;
    private boolean needSendDebugLog = false;
    private ArrayList<PhoneCall> pendingUpdates = new ArrayList();
    private User user;

    /* renamed from: org.telegram.messenger.voip.VoIPService$1 */
    class C37091 implements Runnable {
        C37091() {
        }

        public void run() {
            VoIPService.this.delayedStartOutgoingCall = null;
            VoIPService.this.startOutgoingCall();
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$3 */
    class C37113 implements RequestDelegate {
        C37113() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            FileLog.m13725d("Sent debug logs, response=" + tLObject);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$4 */
    class C37124 implements Runnable {
        C37124() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didStartedCall, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$5 */
    class C37185 implements RequestDelegate {
        C37185() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            VoIPService.this.callReqId = 0;
            if (tLRPC$TL_error == null) {
                byte[] bArr;
                TLRPC$messages_DhConfig tLRPC$messages_DhConfig = (TLRPC$messages_DhConfig) tLObject;
                if (tLObject instanceof TLRPC$TL_messages_dhConfig) {
                    if (Utilities.isGoodPrime(tLRPC$messages_DhConfig.f10165p, tLRPC$messages_DhConfig.f10164g)) {
                        MessagesStorage.secretPBytes = tLRPC$messages_DhConfig.f10165p;
                        MessagesStorage.secretG = tLRPC$messages_DhConfig.f10164g;
                        MessagesStorage.lastSecretVersion = tLRPC$messages_DhConfig.version;
                        MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                    } else {
                        VoIPService.this.callFailed();
                        return;
                    }
                }
                final byte[] bArr2 = new byte[256];
                for (int i = 0; i < 256; i++) {
                    bArr2[i] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ tLRPC$messages_DhConfig.random[i]);
                }
                Object toByteArray = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, bArr2), new BigInteger(1, MessagesStorage.secretPBytes)).toByteArray();
                if (toByteArray.length > 256) {
                    bArr = new byte[256];
                    System.arraycopy(toByteArray, 1, bArr, 0, 256);
                } else {
                    Object obj = toByteArray;
                }
                TLObject tLRPC$TL_phone_requestCall = new TLRPC$TL_phone_requestCall();
                tLRPC$TL_phone_requestCall.user_id = MessagesController.getInputUser(VoIPService.this.user);
                tLRPC$TL_phone_requestCall.protocol = new TLRPC$TL_phoneCallProtocol();
                tLRPC$TL_phone_requestCall.protocol.udp_p2p = true;
                tLRPC$TL_phone_requestCall.protocol.udp_reflector = true;
                tLRPC$TL_phone_requestCall.protocol.min_layer = 65;
                tLRPC$TL_phone_requestCall.protocol.max_layer = 65;
                VoIPService.this.g_a = bArr;
                tLRPC$TL_phone_requestCall.g_a_hash = Utilities.computeSHA256(bArr, 0, bArr.length);
                tLRPC$TL_phone_requestCall.random_id = Utilities.random.nextInt();
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_requestCall, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.messenger.voip.VoIPService$5$1$1$1 */
                            class C37151 implements Runnable {

                                /* renamed from: org.telegram.messenger.voip.VoIPService$5$1$1$1$1 */
                                class C37141 implements RequestDelegate {

                                    /* renamed from: org.telegram.messenger.voip.VoIPService$5$1$1$1$1$1 */
                                    class C37131 implements Runnable {
                                        C37131() {
                                        }

                                        public void run() {
                                            VoIPService.this.callFailed();
                                        }
                                    }

                                    C37141() {
                                    }

                                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                        if (tLRPC$TL_error != null) {
                                            FileLog.m13726e("error on phone.discardCall: " + tLRPC$TL_error);
                                        } else {
                                            FileLog.m13725d("phone.discardCall " + tLObject);
                                        }
                                        AndroidUtilities.runOnUIThread(new C37131());
                                    }
                                }

                                C37151() {
                                }

                                public void run() {
                                    VoIPService.this.timeoutRunnable = null;
                                    TLObject tLRPC$TL_phone_discardCall = new TLRPC$TL_phone_discardCall();
                                    tLRPC$TL_phone_discardCall.peer = new TLRPC$TL_inputPhoneCall();
                                    tLRPC$TL_phone_discardCall.peer.access_hash = VoIPService.this.call.access_hash;
                                    tLRPC$TL_phone_discardCall.peer.id = VoIPService.this.call.id;
                                    tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonMissed();
                                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_discardCall, new C37141(), 2);
                                }
                            }

                            public void run() {
                                if (tLRPC$TL_error == null) {
                                    VoIPService.this.call = ((TLRPC$TL_phone_phoneCall) tLObject).phone_call;
                                    VoIPService.this.a_or_b = bArr2;
                                    VoIPService.this.dispatchStateChanged(13);
                                    if (VoIPService.this.endCallAfterRequest) {
                                        VoIPService.this.hangUp();
                                        return;
                                    }
                                    if (VoIPService.this.pendingUpdates.size() > 0 && VoIPService.this.call != null) {
                                        Iterator it = VoIPService.this.pendingUpdates.iterator();
                                        while (it.hasNext()) {
                                            VoIPService.this.onCallUpdated((PhoneCall) it.next());
                                        }
                                        VoIPService.this.pendingUpdates.clear();
                                    }
                                    VoIPService.this.timeoutRunnable = new C37151();
                                    AndroidUtilities.runOnUIThread(VoIPService.this.timeoutRunnable, (long) MessagesController.getInstance().callReceiveTimeout);
                                } else if (tLRPC$TL_error.code == ChatActivity.scheduleDownloads && "PARTICIPANT_VERSION_OUTDATED".equals(tLRPC$TL_error.text)) {
                                    VoIPService.this.callFailed(-1);
                                } else if (tLRPC$TL_error.code == 403 && "USER_PRIVACY_RESTRICTED".equals(tLRPC$TL_error.text)) {
                                    VoIPService.this.callFailed(-2);
                                } else if (tLRPC$TL_error.code == WalletConstants.ERROR_CODE_SPENDING_LIMIT_EXCEEDED) {
                                    VoIPService.this.callFailed(-3);
                                } else {
                                    FileLog.m13726e("Error on phone.requestCall: " + tLRPC$TL_error);
                                    VoIPService.this.callFailed();
                                }
                            }
                        });
                    }
                }, 2);
                return;
            }
            FileLog.m13726e("Error on getDhConfig " + tLRPC$TL_error);
            VoIPService.this.callFailed();
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$6 */
    class C37206 implements RequestDelegate {
        C37206() {
        }

        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (VoIPBaseService.sharedInstance != null) {
                        FileLog.m13729w("receivedCall response = " + tLObject);
                        if (tLRPC$TL_error != null) {
                            FileLog.m13726e("error on receivedCall: " + tLRPC$TL_error);
                            VoIPService.this.stopSelf();
                            return;
                        }
                        VoIPService.this.startRinging();
                    }
                }
            });
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$7 */
    class C37217 implements OnPreparedListener {
        C37217() {
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            VoIPService.this.ringtonePlayer.start();
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$8 */
    class C37228 implements Runnable {
        C37228() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didStartedCall, new Object[0]);
        }
    }

    /* renamed from: org.telegram.messenger.voip.VoIPService$9 */
    class C37259 implements RequestDelegate {

        /* renamed from: org.telegram.messenger.voip.VoIPService$9$1 */
        class C37241 implements RequestDelegate {
            C37241() {
            }

            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            FileLog.m13729w("accept call ok! " + tLObject);
                            VoIPService.this.call = ((TLRPC$TL_phone_phoneCall) tLObject).phone_call;
                            if (VoIPService.this.call instanceof TLRPC$TL_phoneCallDiscarded) {
                                VoIPService.this.onCallUpdated(VoIPService.this.call);
                                return;
                            }
                            return;
                        }
                        FileLog.m13726e("Error on phone.acceptCall: " + tLRPC$TL_error);
                        VoIPService.this.callFailed();
                    }
                });
            }
        }

        C37259() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLRPC$TL_error == null) {
                byte[] bArr;
                TLRPC$messages_DhConfig tLRPC$messages_DhConfig = (TLRPC$messages_DhConfig) tLObject;
                if (tLObject instanceof TLRPC$TL_messages_dhConfig) {
                    if (Utilities.isGoodPrime(tLRPC$messages_DhConfig.f10165p, tLRPC$messages_DhConfig.f10164g)) {
                        MessagesStorage.secretPBytes = tLRPC$messages_DhConfig.f10165p;
                        MessagesStorage.secretG = tLRPC$messages_DhConfig.f10164g;
                        MessagesStorage.lastSecretVersion = tLRPC$messages_DhConfig.version;
                        MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                    } else {
                        FileLog.m13726e("stopping VoIP service, bad prime");
                        VoIPService.this.callFailed();
                        return;
                    }
                }
                byte[] bArr2 = new byte[256];
                for (int i = 0; i < 256; i++) {
                    bArr2[i] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ tLRPC$messages_DhConfig.random[i]);
                }
                VoIPService.this.a_or_b = bArr2;
                BigInteger modPow = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, bArr2), new BigInteger(1, MessagesStorage.secretPBytes));
                VoIPService.this.g_a_hash = VoIPService.this.call.g_a_hash;
                Object toByteArray = modPow.toByteArray();
                if (toByteArray.length > 256) {
                    bArr = new byte[256];
                    System.arraycopy(toByteArray, 1, bArr, 0, 256);
                } else {
                    Object obj = toByteArray;
                }
                TLObject tLRPC$TL_phone_acceptCall = new TLRPC$TL_phone_acceptCall();
                tLRPC$TL_phone_acceptCall.g_b = bArr;
                tLRPC$TL_phone_acceptCall.peer = new TLRPC$TL_inputPhoneCall();
                tLRPC$TL_phone_acceptCall.peer.id = VoIPService.this.call.id;
                tLRPC$TL_phone_acceptCall.peer.access_hash = VoIPService.this.call.access_hash;
                tLRPC$TL_phone_acceptCall.protocol = new TLRPC$TL_phoneCallProtocol();
                TLRPC$TL_phoneCallProtocol tLRPC$TL_phoneCallProtocol = tLRPC$TL_phone_acceptCall.protocol;
                tLRPC$TL_phone_acceptCall.protocol.udp_reflector = true;
                tLRPC$TL_phoneCallProtocol.udp_p2p = true;
                tLRPC$TL_phone_acceptCall.protocol.min_layer = 65;
                tLRPC$TL_phone_acceptCall.protocol.max_layer = 65;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_acceptCall, new C37241(), 2);
                return;
            }
            VoIPService.this.callFailed();
        }
    }

    private void acknowledgeCallAndStartRinging() {
        if (this.call instanceof TLRPC$TL_phoneCallDiscarded) {
            FileLog.m13729w("Call " + this.call.id + " was discarded before the service started, stopping");
            stopSelf();
            return;
        }
        TLObject tLRPC$TL_phone_receivedCall = new TLRPC$TL_phone_receivedCall();
        tLRPC$TL_phone_receivedCall.peer = new TLRPC$TL_inputPhoneCall();
        tLRPC$TL_phone_receivedCall.peer.id = this.call.id;
        tLRPC$TL_phone_receivedCall.peer.access_hash = this.call.access_hash;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_receivedCall, new C37206(), 2);
    }

    private void dumpCallObject() {
        try {
            for (Field field : PhoneCall.class.getFields()) {
                FileLog.m13725d(field.getName() + " = " + field.get(this.call));
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static VoIPService getSharedInstance() {
        return sharedInstance instanceof VoIPService ? (VoIPService) sharedInstance : null;
    }

    private void initiateActualEncryptedCall() {
        if (this.timeoutRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.timeoutRunnable);
            this.timeoutRunnable = null;
        }
        try {
            FileLog.m13725d("InitCall: keyID=" + this.keyFingerprint);
            SharedPreferences sharedPreferences = getSharedPreferences("notifications", 0);
            Set hashSet = new HashSet(sharedPreferences.getStringSet("calls_access_hashes", Collections.EMPTY_SET));
            hashSet.add(this.call.id + " " + this.call.access_hash + " " + System.currentTimeMillis());
            while (hashSet.size() > 20) {
                Object obj = null;
                long j = Long.MAX_VALUE;
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    String[] split = str.split(" ");
                    if (split.length < 2) {
                        it.remove();
                    } else {
                        try {
                            String str2;
                            long j2;
                            long parseLong = Long.parseLong(split[2]);
                            if (parseLong < j) {
                                long j3 = parseLong;
                                str2 = str;
                                j2 = j3;
                            } else {
                                str2 = obj;
                                j2 = j;
                            }
                            j = j2;
                            obj = str2;
                        } catch (Exception e) {
                            it.remove();
                        }
                    }
                }
                if (obj != null) {
                    hashSet.remove(obj);
                }
            }
            sharedPreferences.edit().putStringSet("calls_access_hashes", hashSet).apply();
            this.controller.setConfig(((double) MessagesController.getInstance().callPacketTimeout) / 1000.0d, ((double) MessagesController.getInstance().callConnectTimeout) / 1000.0d, getSharedPreferences("mainconfig", 0).getInt("VoipDataSaving", 0), this.call.id);
            this.controller.setEncryptionKey(this.authKey, this.isOutgoing);
            TLRPC$TL_phoneConnection[] tLRPC$TL_phoneConnectionArr = new TLRPC$TL_phoneConnection[(this.call.alternative_connections.size() + 1)];
            tLRPC$TL_phoneConnectionArr[0] = this.call.connection;
            for (int i = 0; i < this.call.alternative_connections.size(); i++) {
                tLRPC$TL_phoneConnectionArr[i + 1] = (TLRPC$TL_phoneConnection) this.call.alternative_connections.get(i);
            }
            VoIPHelper.upgradeP2pSetting();
            Object obj2 = 1;
            switch (getSharedPreferences("mainconfig", 0).getInt("calls_p2p_new", MessagesController.getInstance().defaultP2pContacts ? 1 : 0)) {
                case 0:
                    obj2 = 1;
                    break;
                case 1:
                    if (ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.user.id)) == null) {
                        obj2 = null;
                        break;
                    } else {
                        obj2 = 1;
                        break;
                    }
                case 2:
                    obj2 = null;
                    break;
            }
            VoIPController voIPController = this.controller;
            boolean z = this.call.protocol.udp_p2p && obj2 != null;
            voIPController.setRemoteEndpoints(tLRPC$TL_phoneConnectionArr, z);
            SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            if (sharedPreferences2.getBoolean("proxy_enabled", false) && sharedPreferences2.getBoolean("proxy_enabled_calls", false)) {
                String string = sharedPreferences2.getString("proxy_ip", null);
                if (string != null) {
                    this.controller.setProxy(string, sharedPreferences2.getInt("proxy_port", 0), sharedPreferences2.getString("proxy_user", null), sharedPreferences2.getString("proxy_pass", null));
                }
            }
            this.controller.start();
            updateNetworkType();
            this.controller.connect();
            this.controllerStarted = true;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (VoIPService.this.controller != null) {
                        VoIPService.this.updateStats();
                        AndroidUtilities.runOnUIThread(this, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                    }
                }
            }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        } catch (Throwable e2) {
            FileLog.m13727e("error starting call", e2);
            callFailed();
        }
    }

    private void processAcceptedCall() {
        dispatchStateChanged(12);
        BigInteger bigInteger = new BigInteger(1, MessagesStorage.secretPBytes);
        BigInteger bigInteger2 = new BigInteger(1, this.call.g_b);
        if (Utilities.isGoodGaAndGb(bigInteger2, bigInteger)) {
            byte[] bArr;
            Object obj;
            Object toByteArray = bigInteger2.modPow(new BigInteger(1, this.a_or_b), bigInteger).toByteArray();
            if (toByteArray.length > 256) {
                bArr = new byte[256];
                System.arraycopy(toByteArray, toByteArray.length - 256, bArr, 0, 256);
            } else if (toByteArray.length < 256) {
                obj = new byte[256];
                System.arraycopy(toByteArray, 0, obj, 256 - toByteArray.length, toByteArray.length);
                for (int i = 0; i < 256 - toByteArray.length; i++) {
                    toByteArray[i] = null;
                }
                r0 = obj;
            } else {
                r0 = toByteArray;
            }
            obj = Utilities.computeSHA1(bArr);
            toByteArray = new byte[8];
            System.arraycopy(obj, obj.length - 8, toByteArray, 0, 8);
            long bytesToLong = Utilities.bytesToLong(toByteArray);
            this.authKey = bArr;
            this.keyFingerprint = bytesToLong;
            TLObject tLRPC$TL_phone_confirmCall = new TLRPC$TL_phone_confirmCall();
            tLRPC$TL_phone_confirmCall.g_a = this.g_a;
            tLRPC$TL_phone_confirmCall.key_fingerprint = bytesToLong;
            tLRPC$TL_phone_confirmCall.peer = new TLRPC$TL_inputPhoneCall();
            tLRPC$TL_phone_confirmCall.peer.id = this.call.id;
            tLRPC$TL_phone_confirmCall.peer.access_hash = this.call.access_hash;
            tLRPC$TL_phone_confirmCall.protocol = new TLRPC$TL_phoneCallProtocol();
            tLRPC$TL_phone_confirmCall.protocol.max_layer = 65;
            tLRPC$TL_phone_confirmCall.protocol.min_layer = 65;
            TLRPC$TL_phoneCallProtocol tLRPC$TL_phoneCallProtocol = tLRPC$TL_phone_confirmCall.protocol;
            tLRPC$TL_phone_confirmCall.protocol.udp_reflector = true;
            tLRPC$TL_phoneCallProtocol.udp_p2p = true;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_confirmCall, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error != null) {
                                VoIPService.this.callFailed();
                                return;
                            }
                            VoIPService.this.call = ((TLRPC$TL_phone_phoneCall) tLObject).phone_call;
                            VoIPService.this.initiateActualEncryptedCall();
                        }
                    });
                }
            });
            return;
        }
        FileLog.m13729w("stopping VoIP service, bad Ga and Gb");
        callFailed();
    }

    private void showIncomingNotification() {
        Intent intent = new Intent(this, VoIPActivity.class);
        intent.addFlags(805306368);
        Builder contentIntent = new Builder(this).setContentTitle(LocaleController.getString("VoipInCallBranding", R.string.VoipInCallBranding)).setContentText(ContactsController.formatName(this.user.first_name, this.user.last_name)).setSmallIcon(R.drawable.notification).setContentIntent(PendingIntent.getActivity(this, 0, intent, 0));
        if (VERSION.SDK_INT >= 16) {
            CharSequence spannableString;
            Intent intent2 = new Intent(this, VoIPActionsReceiver.class);
            intent2.setAction(getPackageName() + ".DECLINE_CALL");
            intent2.putExtra("call_id", getCallID());
            CharSequence string = LocaleController.getString("VoipDeclineCall", R.string.VoipDeclineCall);
            if (VERSION.SDK_INT >= 24) {
                spannableString = new SpannableString(string);
                ((SpannableString) spannableString).setSpan(new ForegroundColorSpan(-769226), 0, spannableString.length(), 0);
            } else {
                spannableString = string;
            }
            contentIntent.addAction(R.drawable.ic_call_end_white_24dp, spannableString, PendingIntent.getBroadcast(this, 0, intent2, 134217728));
            intent2 = new Intent(this, VoIPActionsReceiver.class);
            intent2.setAction(getPackageName() + ".ANSWER_CALL");
            intent2.putExtra("call_id", getCallID());
            string = LocaleController.getString("VoipAnswerCall", R.string.VoipAnswerCall);
            if (VERSION.SDK_INT >= 24) {
                spannableString = new SpannableString(string);
                ((SpannableString) spannableString).setSpan(new ForegroundColorSpan(-16733696), 0, spannableString.length(), 0);
            } else {
                spannableString = string;
            }
            contentIntent.addAction(R.drawable.ic_call_white_24dp, spannableString, PendingIntent.getBroadcast(this, 0, intent2, 134217728));
            contentIntent.setPriority(2);
        }
        if (VERSION.SDK_INT >= 17) {
            contentIntent.setShowWhen(false);
        }
        if (VERSION.SDK_INT >= 21) {
            contentIntent.setColor(-13851168);
            contentIntent.setVibrate(new long[0]);
            contentIntent.setCategory("call");
            contentIntent.setFullScreenIntent(PendingIntent.getActivity(this, 0, intent, 0), true);
        }
        if (this.user.photo != null) {
            TLObject tLObject = this.user.photo.photo_small;
            if (tLObject != null) {
                BitmapDrawable imageFromMemory = ImageLoader.getInstance().getImageFromMemory(tLObject, null, "50_50");
                if (imageFromMemory != null) {
                    contentIntent.setLargeIcon(imageFromMemory.getBitmap());
                } else {
                    try {
                        float dp = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                        Options options = new Options();
                        options.inSampleSize = dp < 1.0f ? 1 : (int) dp;
                        Bitmap decodeFile = BitmapFactory.decodeFile(FileLoader.getPathToAttach(tLObject, true).toString(), options);
                        if (decodeFile != null) {
                            contentIntent.setLargeIcon(decodeFile);
                        }
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                    }
                }
            }
        }
        startForeground(202, contentIntent.getNotification());
    }

    private void startConnectingSound() {
        if (this.spPlayID != 0) {
            this.soundPool.stop(this.spPlayID);
        }
        this.spPlayID = this.soundPool.play(this.spConnectingId, 1.0f, 1.0f, 0, -1, 1.0f);
        if (this.spPlayID == 0) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (VoIPBaseService.sharedInstance != null) {
                        if (VoIPService.this.spPlayID == 0) {
                            VoIPService.this.spPlayID = VoIPService.this.soundPool.play(VoIPService.this.spConnectingId, 1.0f, 1.0f, 0, -1, 1.0f);
                        }
                        if (VoIPService.this.spPlayID == 0) {
                            AndroidUtilities.runOnUIThread(this, 100);
                        }
                    }
                }
            }, 100);
        }
    }

    private void startOutgoingCall() {
        configureDeviceForCall();
        showNotification();
        startConnectingSound();
        dispatchStateChanged(14);
        AndroidUtilities.runOnUIThread(new C37124());
        Utilities.random.nextBytes(new byte[256]);
        TLObject tLRPC$TL_messages_getDhConfig = new TLRPC$TL_messages_getDhConfig();
        tLRPC$TL_messages_getDhConfig.random_length = 256;
        tLRPC$TL_messages_getDhConfig.version = MessagesStorage.lastSecretVersion;
        this.callReqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDhConfig, new C37185(), 2);
    }

    private void startRatingActivity() {
        try {
            PendingIntent.getActivity(this, 0, new Intent(this, VoIPFeedbackActivity.class).putExtra("call_id", this.call.id).putExtra("call_access_hash", this.call.access_hash).addFlags(805306368), 0).send();
        } catch (Throwable e) {
            FileLog.m13727e("Error starting incall activity", e);
        }
    }

    private void startRinging() {
        FileLog.m13725d("starting ringing for call " + this.call.id);
        dispatchStateChanged(15);
        SharedPreferences sharedPreferences = getSharedPreferences("Notifications", 0);
        this.ringtonePlayer = new MediaPlayer();
        this.ringtonePlayer.setOnPreparedListener(new C37217());
        this.ringtonePlayer.setLooping(true);
        this.ringtonePlayer.setAudioStreamType(2);
        try {
            this.ringtonePlayer.setDataSource(this, Uri.parse(sharedPreferences.getBoolean(new StringBuilder().append("custom_").append(this.user.id).toString(), false) ? sharedPreferences.getString("ringtone_path_" + this.user.id, RingtoneManager.getDefaultUri(1).toString()) : sharedPreferences.getString("CallsRingtonePath", RingtoneManager.getDefaultUri(1).toString())));
            this.ringtonePlayer.prepareAsync();
        } catch (Throwable e) {
            FileLog.m13728e(e);
            if (this.ringtonePlayer != null) {
                this.ringtonePlayer.release();
                this.ringtonePlayer = null;
            }
        }
        AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        int i = sharedPreferences.getBoolean(new StringBuilder().append("custom_").append(this.user.id).toString(), false) ? sharedPreferences.getInt("calls_vibrate_" + this.user.id, 0) : sharedPreferences.getInt("vibrate_calls", 0);
        if (!(i == 2 || i == 4 || (audioManager.getRingerMode() != 1 && audioManager.getRingerMode() != 2)) || (i == 4 && audioManager.getRingerMode() == 1)) {
            this.vibrator = (Vibrator) getSystemService("vibrator");
            long j = 700;
            if (i == 1) {
                j = 700 / 2;
            } else if (i == 3) {
                j = 700 * 2;
            }
            this.vibrator.vibrate(new long[]{0, j, 500}, 0);
        }
        if (VERSION.SDK_INT < 21 || ((KeyguardManager) getSystemService("keyguard")).inKeyguardRestrictedInputMode() || !au.m1378a((Context) this).m1386a()) {
            FileLog.m13725d("Starting incall activity for incoming call");
            try {
                PendingIntent.getActivity(this, 12345, new Intent(this, VoIPActivity.class).addFlags(ErrorDialogData.BINDER_CRASH), 0).send();
                return;
            } catch (Throwable e2) {
                FileLog.m13727e("Error starting incall activity", e2);
                return;
            }
        }
        showIncomingNotification();
        FileLog.m13725d("Showing incoming call notification");
    }

    public void acceptIncomingCall() {
        stopRinging();
        showNotification();
        configureDeviceForCall();
        startConnectingSound();
        dispatchStateChanged(12);
        AndroidUtilities.runOnUIThread(new C37228());
        TLObject tLRPC$TL_messages_getDhConfig = new TLRPC$TL_messages_getDhConfig();
        tLRPC$TL_messages_getDhConfig.random_length = 256;
        tLRPC$TL_messages_getDhConfig.version = MessagesStorage.lastSecretVersion;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDhConfig, new C37259());
    }

    protected void callFailed(int i) {
        if (this.call != null) {
            FileLog.m13725d("Discarding failed call");
            TLObject tLRPC$TL_phone_discardCall = new TLRPC$TL_phone_discardCall();
            tLRPC$TL_phone_discardCall.peer = new TLRPC$TL_inputPhoneCall();
            tLRPC$TL_phone_discardCall.peer.access_hash = this.call.access_hash;
            tLRPC$TL_phone_discardCall.peer.id = this.call.id;
            int callDuration = (this.controller == null || !this.controllerStarted) ? 0 : (int) (this.controller.getCallDuration() / 1000);
            tLRPC$TL_phone_discardCall.duration = callDuration;
            long preferredRelayID = (this.controller == null || !this.controllerStarted) ? 0 : this.controller.getPreferredRelayID();
            tLRPC$TL_phone_discardCall.connection_id = preferredRelayID;
            tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonDisconnect();
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_discardCall, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error != null) {
                        FileLog.m13726e("error on phone.discardCall: " + tLRPC$TL_error);
                    } else {
                        FileLog.m13725d("phone.discardCall " + tLObject);
                    }
                }
            });
        }
        super.callFailed(i);
    }

    public void debugCtl(int i, int i2) {
        if (this.controller != null) {
            this.controller.debugCtl(i, i2);
        }
    }

    public void declineIncomingCall() {
        declineIncomingCall(1, null);
    }

    public void declineIncomingCall(int i, final Runnable runnable) {
        boolean z = false;
        if (this.currentState == 14) {
            if (this.delayedStartOutgoingCall != null) {
                AndroidUtilities.cancelRunOnUIThread(this.delayedStartOutgoingCall);
                callEnded();
                return;
            }
            dispatchStateChanged(10);
            this.endCallAfterRequest = true;
        } else if (this.currentState != 10 && this.currentState != 11) {
            dispatchStateChanged(10);
            if (this.call == null) {
                if (runnable != null) {
                    runnable.run();
                }
                callEnded();
                if (this.callReqId != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.callReqId, false);
                    this.callReqId = 0;
                    return;
                }
                return;
            }
            Runnable runnable2;
            TLObject tLRPC$TL_phone_discardCall = new TLRPC$TL_phone_discardCall();
            tLRPC$TL_phone_discardCall.peer = new TLRPC$TL_inputPhoneCall();
            tLRPC$TL_phone_discardCall.peer.access_hash = this.call.access_hash;
            tLRPC$TL_phone_discardCall.peer.id = this.call.id;
            int callDuration = (this.controller == null || !this.controllerStarted) ? 0 : (int) (this.controller.getCallDuration() / 1000);
            tLRPC$TL_phone_discardCall.duration = callDuration;
            long preferredRelayID = (this.controller == null || !this.controllerStarted) ? 0 : this.controller.getPreferredRelayID();
            tLRPC$TL_phone_discardCall.connection_id = preferredRelayID;
            switch (i) {
                case 2:
                    tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonDisconnect();
                    break;
                case 3:
                    tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonMissed();
                    break;
                case 4:
                    tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonBusy();
                    break;
                default:
                    tLRPC$TL_phone_discardCall.reason = new TLRPC$TL_phoneCallDiscardReasonHangup();
                    break;
            }
            if (ConnectionsManager.getInstance().getConnectionState() != 3) {
                z = true;
            }
            if (z) {
                if (runnable != null) {
                    runnable.run();
                }
                callEnded();
                runnable2 = null;
            } else {
                runnable2 = new Runnable() {
                    private boolean done = false;

                    public void run() {
                        if (!this.done) {
                            this.done = true;
                            if (runnable != null) {
                                runnable.run();
                            }
                            VoIPService.this.callEnded();
                        }
                    }
                };
                AndroidUtilities.runOnUIThread(runnable2, (long) ((int) (VoIPServerConfig.getDouble("hangup_ui_timeout", 5.0d) * 1000.0d)));
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_discardCall, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error != null) {
                        FileLog.m13726e("error on phone.discardCall: " + tLRPC$TL_error);
                    } else {
                        if (tLObject instanceof TLRPC$TL_updates) {
                            MessagesController.getInstance().processUpdates((TLRPC$TL_updates) tLObject, false);
                        }
                        FileLog.m13725d("phone.discardCall " + tLObject);
                    }
                    if (!z) {
                        AndroidUtilities.cancelRunOnUIThread(runnable2);
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
            }, 2);
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.appDidLogout) {
            callEnded();
        }
    }

    public void forceRating() {
        this.forceRating = true;
    }

    protected long getCallID() {
        return this.call != null ? this.call.id : 0;
    }

    public byte[] getEncryptionKey() {
        return this.authKey;
    }

    public byte[] getGA() {
        return this.g_a;
    }

    public User getUser() {
        return this.user;
    }

    public void hangUp() {
        int i = (this.currentState == 16 || (this.currentState == 13 && this.isOutgoing)) ? 3 : 1;
        declineIncomingCall(i, null);
    }

    public void hangUp(Runnable runnable) {
        int i = (this.currentState == 16 || (this.currentState == 13 && this.isOutgoing)) ? 3 : 1;
        declineIncomingCall(i, runnable);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCallUpdated(PhoneCall phoneCall) {
        if (this.call == null) {
            this.pendingUpdates.add(phoneCall);
        } else if (phoneCall == null) {
        } else {
            if (phoneCall.id == this.call.id) {
                if (phoneCall.access_hash == 0) {
                    phoneCall.access_hash = this.call.access_hash;
                }
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m13725d("Call updated: " + phoneCall);
                    dumpCallObject();
                }
                this.call = phoneCall;
                if (phoneCall instanceof TLRPC$TL_phoneCallDiscarded) {
                    this.needSendDebugLog = phoneCall.need_debug;
                    FileLog.m13725d("call discarded, stopping service");
                    if (phoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy) {
                        dispatchStateChanged(17);
                        this.playingSound = true;
                        this.soundPool.play(this.spBusyId, 1.0f, 1.0f, 0, -1, 1.0f);
                        AndroidUtilities.runOnUIThread(this.afterSoundRunnable, 1500);
                        stopSelf();
                    } else {
                        callEnded();
                    }
                    if (phoneCall.need_rating || this.forceRating) {
                        startRatingActivity();
                    }
                } else if ((phoneCall instanceof TLRPC$TL_phoneCall) && this.authKey == null) {
                    if (phoneCall.g_a_or_b == null) {
                        FileLog.m13729w("stopping VoIP service, Ga == null");
                        callFailed();
                    } else if (Arrays.equals(this.g_a_hash, Utilities.computeSHA256(phoneCall.g_a_or_b, 0, phoneCall.g_a_or_b.length))) {
                        this.g_a = phoneCall.g_a_or_b;
                        BigInteger bigInteger = new BigInteger(1, phoneCall.g_a_or_b);
                        BigInteger bigInteger2 = new BigInteger(1, MessagesStorage.secretPBytes);
                        if (Utilities.isGoodGaAndGb(bigInteger, bigInteger2)) {
                            byte[] bArr;
                            Object obj;
                            Object toByteArray = bigInteger.modPow(new BigInteger(1, this.a_or_b), bigInteger2).toByteArray();
                            if (toByteArray.length > 256) {
                                bArr = new byte[256];
                                System.arraycopy(toByteArray, toByteArray.length - 256, bArr, 0, 256);
                            } else if (toByteArray.length < 256) {
                                obj = new byte[256];
                                System.arraycopy(toByteArray, 0, obj, 256 - toByteArray.length, toByteArray.length);
                                for (int i = 0; i < 256 - toByteArray.length; i++) {
                                    toByteArray[i] = null;
                                }
                                r0 = obj;
                            } else {
                                r0 = toByteArray;
                            }
                            obj = Utilities.computeSHA1(bArr);
                            toByteArray = new byte[8];
                            System.arraycopy(obj, obj.length - 8, toByteArray, 0, 8);
                            this.authKey = bArr;
                            this.keyFingerprint = Utilities.bytesToLong(toByteArray);
                            if (this.keyFingerprint != phoneCall.key_fingerprint) {
                                FileLog.m13729w("key fingerprints don't match");
                                callFailed();
                                return;
                            }
                            initiateActualEncryptedCall();
                            return;
                        }
                        FileLog.m13729w("stopping VoIP service, bad Ga and Gb (accepting)");
                        callFailed();
                    } else {
                        FileLog.m13729w("stopping VoIP service, Ga hash doesn't match");
                        callFailed();
                    }
                } else if ((phoneCall instanceof TLRPC$TL_phoneCallAccepted) && this.authKey == null) {
                    processAcceptedCall();
                } else if (this.currentState == 13 && phoneCall.receive_date != 0) {
                    dispatchStateChanged(16);
                    FileLog.m13725d("!!!!!! CALL RECEIVED");
                    if (this.spPlayID != 0) {
                        this.soundPool.stop(this.spPlayID);
                    }
                    this.spPlayID = this.soundPool.play(this.spRingbackID, 1.0f, 1.0f, 0, -1, 1.0f);
                    if (this.timeoutRunnable != null) {
                        AndroidUtilities.cancelRunOnUIThread(this.timeoutRunnable);
                        this.timeoutRunnable = null;
                    }
                    this.timeoutRunnable = new Runnable() {
                        public void run() {
                            VoIPService.this.timeoutRunnable = null;
                            VoIPService.this.declineIncomingCall(3, null);
                        }
                    };
                    AndroidUtilities.runOnUIThread(this.timeoutRunnable, (long) MessagesController.getInstance().callRingTimeout);
                }
            } else if (BuildVars.DEBUG_VERSION) {
                FileLog.m13729w("onCallUpdated called with wrong call id (got " + phoneCall.id + ", expected " + this.call.id + ")");
            }
        }
    }

    protected void onControllerPreRelease() {
        if (this.needSendDebugLog) {
            String debugLog = this.controller.getDebugLog();
            TLObject tLRPC$TL_phone_saveCallDebug = new TLRPC$TL_phone_saveCallDebug();
            tLRPC$TL_phone_saveCallDebug.debug = new TLRPC$TL_dataJSON();
            tLRPC$TL_phone_saveCallDebug.debug.data = debugLog;
            tLRPC$TL_phone_saveCallDebug.peer = new TLRPC$TL_inputPhoneCall();
            tLRPC$TL_phone_saveCallDebug.peer.access_hash = this.call.access_hash;
            tLRPC$TL_phone_saveCallDebug.peer.id = this.call.id;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_saveCallDebug, new C37113());
        }
    }

    void onMediaButtonEvent(KeyEvent keyEvent) {
        boolean z = true;
        if (keyEvent.getKeyCode() != 79 || keyEvent.getAction() != 1) {
            return;
        }
        if (this.currentState == 15) {
            acceptIncomingCall();
            return;
        }
        if (isMicMute()) {
            z = false;
        }
        setMicMute(z);
        Iterator it = this.stateListeners.iterator();
        while (it.hasNext()) {
            ((StateListener) it.next()).onAudioSettingsChanged();
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (sharedInstance != null) {
            FileLog.m13726e("Tried to start the VoIP service when it's already started");
        } else {
            int intExtra = intent.getIntExtra("user_id", 0);
            this.isOutgoing = intent.getBooleanExtra("is_outgoing", false);
            this.user = MessagesController.getInstance().getUser(Integer.valueOf(intExtra));
            if (this.user == null) {
                FileLog.m13729w("VoIPService: user==null");
                stopSelf();
            } else {
                if (this.isOutgoing) {
                    dispatchStateChanged(14);
                    this.delayedStartOutgoingCall = new C37091();
                    AndroidUtilities.runOnUIThread(this.delayedStartOutgoingCall, 2000);
                    if (intent.getBooleanExtra("start_incall_activity", false)) {
                        startActivity(new Intent(this, VoIPActivity.class).addFlags(ErrorDialogData.BINDER_CRASH));
                    }
                } else {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeInCallActivity, new Object[0]);
                    this.call = callIShouldHavePutIntoIntent;
                    callIShouldHavePutIntoIntent = null;
                    acknowledgeCallAndStartRinging();
                }
                sharedInstance = this;
            }
        }
        return 2;
    }

    public void onUIForegroundStateChanged(boolean z) {
        if (this.currentState != 15) {
            return;
        }
        if (z) {
            stopForeground(true);
        } else if (((KeyguardManager) getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    Intent intent = new Intent(VoIPService.this, VoIPActivity.class);
                    intent.addFlags(805306368);
                    try {
                        PendingIntent.getActivity(VoIPService.this, 0, intent, 0).send();
                    } catch (Throwable e) {
                        FileLog.m13727e("error restarting activity", e);
                    }
                }
            }, 500);
        } else {
            showIncomingNotification();
        }
    }

    protected void showNotification() {
        showNotification(ContactsController.formatName(this.user.first_name, this.user.last_name), this.user.photo != null ? this.user.photo.photo_small : null, VoIPActivity.class);
    }

    protected void updateServerConfig() {
        final SharedPreferences sharedPreferences = getSharedPreferences("mainconfig", 0);
        VoIPServerConfig.setConfig(sharedPreferences.getString("voip_server_config", "{}"));
        if (System.currentTimeMillis() - sharedPreferences.getLong("voip_server_config_updated", 0) > 86400000) {
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_phone_getCallConfig(), new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        String str = ((TLRPC$TL_dataJSON) tLObject).data;
                        VoIPServerConfig.setConfig(str);
                        sharedPreferences.edit().putString("voip_server_config", str).putLong("voip_server_config_updated", System.currentTimeMillis()).apply();
                    }
                }
            });
        }
    }
}
