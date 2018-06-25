package org.telegram.messenger.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.DeniedByServerException;
import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;
import org.telegram.messenger.exoplayer2.drm.DrmSession.DrmSessionException;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import org.telegram.messenger.exoplayer2.extractor.mp4.PsshAtomUtil;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(18)
public class DefaultDrmSessionManager<T extends ExoMediaCrypto> implements DrmSessionManager<T>, DrmSession<T> {
    private static final String CENC_SCHEME_MIME_TYPE = "cenc";
    private static final int MAX_LICENSE_DURATION_TO_RENEW = 60;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    private static final int MSG_KEYS = 1;
    private static final int MSG_PROVISION = 0;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "OfflineDrmSessionMngr";
    final MediaDrmCallback callback;
    private final Handler eventHandler;
    private final EventListener eventListener;
    private DrmSessionException lastException;
    private T mediaCrypto;
    private final ExoMediaDrm<T> mediaDrm;
    MediaDrmHandler mediaDrmHandler;
    private int mode = 0;
    private byte[] offlineLicenseKeySetId;
    private int openCount;
    private final HashMap<String, String> optionalKeyRequestParameters;
    private Looper playbackLooper;
    private Handler postRequestHandler;
    PostResponseHandler postResponseHandler;
    private boolean provisioningInProgress;
    private HandlerThread requestHandlerThread;
    private byte[] schemeInitData;
    private String schemeMimeType;
    private byte[] sessionId;
    private int state;
    final UUID uuid;

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager$1 */
    class C09081 implements Runnable {
        C09081() {
        }

        public void run() {
            DefaultDrmSessionManager.this.eventListener.onDrmKeysRestored();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager$2 */
    class C09092 implements Runnable {
        C09092() {
        }

        public void run() {
            DefaultDrmSessionManager.this.eventListener.onDrmKeysRemoved();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager$3 */
    class C09103 implements Runnable {
        C09103() {
        }

        public void run() {
            DefaultDrmSessionManager.this.eventListener.onDrmKeysLoaded();
        }
    }

    public interface EventListener {
        void onDrmKeysLoaded();

        void onDrmKeysRemoved();

        void onDrmKeysRestored();

        void onDrmSessionManagerError(Exception exception);
    }

    private class MediaDrmEventListener implements OnEventListener<T> {
        private MediaDrmEventListener() {
        }

        public void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] sessionId, int event, int extra, byte[] data) {
            if (DefaultDrmSessionManager.this.mode == 0) {
                DefaultDrmSessionManager.this.mediaDrmHandler.sendEmptyMessage(event);
            }
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (DefaultDrmSessionManager.this.openCount == 0) {
                return;
            }
            if (DefaultDrmSessionManager.this.state == 3 || DefaultDrmSessionManager.this.state == 4) {
                switch (msg.what) {
                    case 1:
                        DefaultDrmSessionManager.this.state = 3;
                        DefaultDrmSessionManager.this.postProvisionRequest();
                        return;
                    case 2:
                        DefaultDrmSessionManager.this.doLicense();
                        return;
                    case 3:
                        if (DefaultDrmSessionManager.this.state == 4) {
                            DefaultDrmSessionManager.this.state = 3;
                            DefaultDrmSessionManager.this.onError(new KeysExpiredException());
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @SuppressLint({"HandlerLeak"})
    private class PostRequestHandler extends Handler {
        public PostRequestHandler(Looper backgroundLooper) {
            super(backgroundLooper);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r6) {
            /*
            r5 = this;
            r2 = r6.what;	 Catch:{ Exception -> 0x000b }
            switch(r2) {
                case 0: goto L_0x001b;
                case 1: goto L_0x002c;
                default: goto L_0x0005;
            };	 Catch:{ Exception -> 0x000b }
        L_0x0005:
            r2 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x000b }
            r2.<init>();	 Catch:{ Exception -> 0x000b }
            throw r2;	 Catch:{ Exception -> 0x000b }
        L_0x000b:
            r0 = move-exception;
            r1 = r0;
        L_0x000d:
            r2 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;
            r2 = r2.postResponseHandler;
            r3 = r6.what;
            r2 = r2.obtainMessage(r3, r1);
            r2.sendToTarget();
            return;
        L_0x001b:
            r2 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r3 = r2.callback;	 Catch:{ Exception -> 0x000b }
            r2 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r4 = r2.uuid;	 Catch:{ Exception -> 0x000b }
            r2 = r6.obj;	 Catch:{ Exception -> 0x000b }
            r2 = (org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest) r2;	 Catch:{ Exception -> 0x000b }
            r1 = r3.executeProvisionRequest(r4, r2);	 Catch:{ Exception -> 0x000b }
            goto L_0x000d;
        L_0x002c:
            r2 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r3 = r2.callback;	 Catch:{ Exception -> 0x000b }
            r2 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r4 = r2.uuid;	 Catch:{ Exception -> 0x000b }
            r2 = r6.obj;	 Catch:{ Exception -> 0x000b }
            r2 = (org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest) r2;	 Catch:{ Exception -> 0x000b }
            r1 = r3.executeKeyRequest(r4, r2);	 Catch:{ Exception -> 0x000b }
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.PostRequestHandler.handleMessage(android.os.Message):void");
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class PostResponseHandler extends Handler {
        public PostResponseHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    DefaultDrmSessionManager.this.onProvisionResponse(msg.obj);
                    return;
                case 1:
                    DefaultDrmSessionManager.this.onKeyResponse(msg.obj);
                    return;
                default:
                    return;
            }
        }
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newWidevineInstance(MediaDrmCallback callback, HashMap<String, String> optionalKeyRequestParameters, Handler eventHandler, EventListener eventListener) throws UnsupportedDrmException {
        return newFrameworkInstance(C0907C.WIDEVINE_UUID, callback, optionalKeyRequestParameters, eventHandler, eventListener);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newPlayReadyInstance(MediaDrmCallback callback, String customData, Handler eventHandler, EventListener eventListener) throws UnsupportedDrmException {
        HashMap<String, String> optionalKeyRequestParameters;
        if (TextUtils.isEmpty(customData)) {
            optionalKeyRequestParameters = null;
        } else {
            optionalKeyRequestParameters = new HashMap();
            optionalKeyRequestParameters.put(PLAYREADY_CUSTOM_DATA_KEY, customData);
        }
        return newFrameworkInstance(C0907C.PLAYREADY_UUID, callback, optionalKeyRequestParameters, eventHandler, eventListener);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newFrameworkInstance(UUID uuid, MediaDrmCallback callback, HashMap<String, String> optionalKeyRequestParameters, Handler eventHandler, EventListener eventListener) throws UnsupportedDrmException {
        return new DefaultDrmSessionManager(uuid, FrameworkMediaDrm.newInstance(uuid), callback, optionalKeyRequestParameters, eventHandler, eventListener);
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> mediaDrm, MediaDrmCallback callback, HashMap<String, String> optionalKeyRequestParameters, Handler eventHandler, EventListener eventListener) {
        this.uuid = uuid;
        this.mediaDrm = mediaDrm;
        this.callback = callback;
        this.optionalKeyRequestParameters = optionalKeyRequestParameters;
        this.eventHandler = eventHandler;
        this.eventListener = eventListener;
        mediaDrm.setOnEventListener(new MediaDrmEventListener());
    }

    public final String getPropertyString(String key) {
        return this.mediaDrm.getPropertyString(key);
    }

    public final void setPropertyString(String key, String value) {
        this.mediaDrm.setPropertyString(key, value);
    }

    public final byte[] getPropertyByteArray(String key) {
        return this.mediaDrm.getPropertyByteArray(key);
    }

    public final void setPropertyByteArray(String key, byte[] value) {
        this.mediaDrm.setPropertyByteArray(key, value);
    }

    public void setMode(int mode, byte[] offlineLicenseKeySetId) {
        Assertions.checkState(this.openCount == 0);
        if (mode == 1 || mode == 3) {
            Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        this.mode = mode;
        this.offlineLicenseKeySetId = offlineLicenseKeySetId;
    }

    public boolean canAcquireSession(@NonNull DrmInitData drmInitData) {
        SchemeData schemeData = drmInitData.get(this.uuid);
        if (schemeData == null) {
            return false;
        }
        String schemeType = schemeData.type;
        if (schemeType == null || "cenc".equals(schemeType)) {
            return true;
        }
        if ((C0907C.CENC_TYPE_cbc1.equals(schemeType) || C0907C.CENC_TYPE_cbcs.equals(schemeType) || C0907C.CENC_TYPE_cens.equals(schemeType)) && Util.SDK_INT < 24) {
            return false;
        }
        return true;
    }

    public DrmSession<T> acquireSession(Looper playbackLooper, DrmInitData drmInitData) {
        boolean z = this.playbackLooper == null || this.playbackLooper == playbackLooper;
        Assertions.checkState(z);
        int i = this.openCount + 1;
        this.openCount = i;
        if (i == 1) {
            if (this.playbackLooper == null) {
                this.playbackLooper = playbackLooper;
                this.mediaDrmHandler = new MediaDrmHandler(playbackLooper);
                this.postResponseHandler = new PostResponseHandler(playbackLooper);
            }
            this.requestHandlerThread = new HandlerThread("DrmRequestHandler");
            this.requestHandlerThread.start();
            this.postRequestHandler = new PostRequestHandler(this.requestHandlerThread.getLooper());
            if (this.offlineLicenseKeySetId == null) {
                SchemeData schemeData = drmInitData.get(this.uuid);
                if (schemeData == null) {
                    onError(new IllegalStateException("Media does not support uuid: " + this.uuid));
                } else {
                    this.schemeInitData = schemeData.data;
                    this.schemeMimeType = schemeData.mimeType;
                    if (Util.SDK_INT < 21) {
                        byte[] psshData = PsshAtomUtil.parseSchemeSpecificData(this.schemeInitData, C0907C.WIDEVINE_UUID);
                        if (psshData != null) {
                            this.schemeInitData = psshData;
                        }
                    }
                    if (Util.SDK_INT < 26 && C0907C.CLEARKEY_UUID.equals(this.uuid) && (MimeTypes.VIDEO_MP4.equals(this.schemeMimeType) || MimeTypes.AUDIO_MP4.equals(this.schemeMimeType))) {
                        this.schemeMimeType = "cenc";
                    }
                }
            }
            this.state = 2;
            openInternal(true);
        }
        return this;
    }

    public void releaseSession(DrmSession<T> drmSession) {
        int i = this.openCount - 1;
        this.openCount = i;
        if (i == 0) {
            this.state = 0;
            this.provisioningInProgress = false;
            this.mediaDrmHandler.removeCallbacksAndMessages(null);
            this.postResponseHandler.removeCallbacksAndMessages(null);
            this.postRequestHandler.removeCallbacksAndMessages(null);
            this.postRequestHandler = null;
            this.requestHandlerThread.quit();
            this.requestHandlerThread = null;
            this.schemeInitData = null;
            this.schemeMimeType = null;
            this.mediaCrypto = null;
            this.lastException = null;
            if (this.sessionId != null) {
                this.mediaDrm.closeSession(this.sessionId);
                this.sessionId = null;
            }
        }
    }

    public final int getState() {
        return this.state;
    }

    public final DrmSessionException getError() {
        return this.state == 1 ? this.lastException : null;
    }

    public final T getMediaCrypto() {
        return this.mediaCrypto;
    }

    public Map<String, String> queryKeyStatus() {
        return this.sessionId == null ? null : this.mediaDrm.queryKeyStatus(this.sessionId);
    }

    public byte[] getOfflineLicenseKeySetId() {
        return this.offlineLicenseKeySetId;
    }

    private void openInternal(boolean allowProvisioning) {
        try {
            this.sessionId = this.mediaDrm.openSession();
            this.mediaCrypto = this.mediaDrm.createMediaCrypto(this.uuid, this.sessionId);
            this.state = 3;
            doLicense();
        } catch (NotProvisionedException e) {
            if (allowProvisioning) {
                postProvisionRequest();
            } else {
                onError(e);
            }
        } catch (Exception e2) {
            onError(e2);
        }
    }

    private void postProvisionRequest() {
        if (!this.provisioningInProgress) {
            this.provisioningInProgress = true;
            this.postRequestHandler.obtainMessage(0, this.mediaDrm.getProvisionRequest()).sendToTarget();
        }
    }

    private void onProvisionResponse(Object response) {
        this.provisioningInProgress = false;
        if (this.state != 2 && this.state != 3 && this.state != 4) {
            return;
        }
        if (response instanceof Exception) {
            onError((Exception) response);
            return;
        }
        try {
            this.mediaDrm.provideProvisionResponse((byte[]) response);
            if (this.state == 2) {
                openInternal(false);
            } else {
                doLicense();
            }
        } catch (DeniedByServerException e) {
            onError(e);
        }
    }

    private void doLicense() {
        switch (this.mode) {
            case 0:
            case 1:
                if (this.offlineLicenseKeySetId == null) {
                    postKeyRequest(this.sessionId, 1);
                    return;
                } else if (restoreKeys()) {
                    long licenseDurationRemainingSec = getLicenseDurationRemainingSec();
                    if (this.mode == 0 && licenseDurationRemainingSec <= 60) {
                        Log.d(TAG, "Offline license has expired or will expire soon. Remaining seconds: " + licenseDurationRemainingSec);
                        postKeyRequest(this.sessionId, 2);
                        return;
                    } else if (licenseDurationRemainingSec <= 0) {
                        onError(new KeysExpiredException());
                        return;
                    } else {
                        this.state = 4;
                        if (this.eventHandler != null && this.eventListener != null) {
                            this.eventHandler.post(new C09081());
                            return;
                        }
                        return;
                    }
                } else {
                    return;
                }
            case 2:
                if (this.offlineLicenseKeySetId == null) {
                    postKeyRequest(this.sessionId, 2);
                    return;
                } else if (restoreKeys()) {
                    postKeyRequest(this.sessionId, 2);
                    return;
                } else {
                    return;
                }
            case 3:
                if (restoreKeys()) {
                    postKeyRequest(this.offlineLicenseKeySetId, 3);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean restoreKeys() {
        try {
            this.mediaDrm.restoreKeys(this.sessionId, this.offlineLicenseKeySetId);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error trying to restore Widevine keys.", e);
            onError(e);
            return false;
        }
    }

    private long getLicenseDurationRemainingSec() {
        if (!C0907C.WIDEVINE_UUID.equals(this.uuid)) {
            return Long.MAX_VALUE;
        }
        Pair<Long, Long> pair = WidevineUtil.getLicenseDurationRemainingSec(this);
        return Math.min(((Long) pair.first).longValue(), ((Long) pair.second).longValue());
    }

    private void postKeyRequest(byte[] scope, int keyType) {
        try {
            this.postRequestHandler.obtainMessage(1, this.mediaDrm.getKeyRequest(scope, this.schemeInitData, this.schemeMimeType, keyType, this.optionalKeyRequestParameters)).sendToTarget();
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    private void onKeyResponse(Object response) {
        if (this.state != 3 && this.state != 4) {
            return;
        }
        if (response instanceof Exception) {
            onKeysError((Exception) response);
            return;
        }
        try {
            if (this.mode == 3) {
                this.mediaDrm.provideKeyResponse(this.offlineLicenseKeySetId, (byte[]) response);
                if (this.eventHandler != null && this.eventListener != null) {
                    this.eventHandler.post(new C09092());
                    return;
                }
                return;
            }
            byte[] keySetId = this.mediaDrm.provideKeyResponse(this.sessionId, (byte[]) response);
            if (!((this.mode != 2 && (this.mode != 0 || this.offlineLicenseKeySetId == null)) || keySetId == null || keySetId.length == 0)) {
                this.offlineLicenseKeySetId = keySetId;
            }
            this.state = 4;
            if (this.eventHandler != null && this.eventListener != null) {
                this.eventHandler.post(new C09103());
            }
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    private void onKeysError(Exception e) {
        if (e instanceof NotProvisionedException) {
            postProvisionRequest();
        } else {
            onError(e);
        }
    }

    private void onError(final Exception e) {
        this.lastException = new DrmSessionException(e);
        if (!(this.eventHandler == null || this.eventListener == null)) {
            this.eventHandler.post(new Runnable() {
                public void run() {
                    DefaultDrmSessionManager.this.eventListener.onDrmSessionManagerError(e);
                }
            });
        }
        if (this.state != 4) {
            this.state = 1;
        }
    }
}
