package org.telegram.messenger.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;
import org.telegram.messenger.exoplayer2.drm.DrmSession.DrmSessionException;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import org.telegram.messenger.exoplayer2.extractor.mp4.PsshAtomUtil;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(18)
public class DefaultDrmSessionManager<T extends ExoMediaCrypto> implements DrmSession<T>, DrmSessionManager<T> {
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
    class C34671 implements Runnable {
        C34671() {
        }

        public void run() {
            DefaultDrmSessionManager.this.eventListener.onDrmKeysRestored();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager$2 */
    class C34682 implements Runnable {
        C34682() {
        }

        public void run() {
            DefaultDrmSessionManager.this.eventListener.onDrmKeysRemoved();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager$3 */
    class C34693 implements Runnable {
        C34693() {
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

        public void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
            if (DefaultDrmSessionManager.this.mode == 0) {
                DefaultDrmSessionManager.this.mediaDrmHandler.sendEmptyMessage(i);
            }
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (DefaultDrmSessionManager.this.openCount == 0) {
                return;
            }
            if (DefaultDrmSessionManager.this.state == 3 || DefaultDrmSessionManager.this.state == 4) {
                switch (message.what) {
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
        public PostRequestHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r4) {
            /*
            r3 = this;
            r0 = r4.what;	 Catch:{ Exception -> 0x000b }
            switch(r0) {
                case 0: goto L_0x001a;
                case 1: goto L_0x002b;
                default: goto L_0x0005;
            };	 Catch:{ Exception -> 0x000b }
        L_0x0005:
            r0 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x000b }
            r0.<init>();	 Catch:{ Exception -> 0x000b }
            throw r0;	 Catch:{ Exception -> 0x000b }
        L_0x000b:
            r0 = move-exception;
        L_0x000c:
            r1 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;
            r1 = r1.postResponseHandler;
            r2 = r4.what;
            r0 = r1.obtainMessage(r2, r0);
            r0.sendToTarget();
            return;
        L_0x001a:
            r0 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r1 = r0.callback;	 Catch:{ Exception -> 0x000b }
            r0 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r2 = r0.uuid;	 Catch:{ Exception -> 0x000b }
            r0 = r4.obj;	 Catch:{ Exception -> 0x000b }
            r0 = (org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest) r0;	 Catch:{ Exception -> 0x000b }
            r0 = r1.executeProvisionRequest(r2, r0);	 Catch:{ Exception -> 0x000b }
            goto L_0x000c;
        L_0x002b:
            r0 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r1 = r0.callback;	 Catch:{ Exception -> 0x000b }
            r0 = org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.this;	 Catch:{ Exception -> 0x000b }
            r2 = r0.uuid;	 Catch:{ Exception -> 0x000b }
            r0 = r4.obj;	 Catch:{ Exception -> 0x000b }
            r0 = (org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest) r0;	 Catch:{ Exception -> 0x000b }
            r0 = r1.executeKeyRequest(r2, r0);	 Catch:{ Exception -> 0x000b }
            goto L_0x000c;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.PostRequestHandler.handleMessage(android.os.Message):void");
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class PostResponseHandler extends Handler {
        public PostResponseHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    DefaultDrmSessionManager.this.onProvisionResponse(message.obj);
                    return;
                case 1:
                    DefaultDrmSessionManager.this.onKeyResponse(message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, EventListener eventListener) {
        this.uuid = uuid;
        this.mediaDrm = exoMediaDrm;
        this.callback = mediaDrmCallback;
        this.optionalKeyRequestParameters = hashMap;
        this.eventHandler = handler;
        this.eventListener = eventListener;
        exoMediaDrm.setOnEventListener(new MediaDrmEventListener());
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
                            this.eventHandler.post(new C34671());
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

    private long getLicenseDurationRemainingSec() {
        if (!C3446C.WIDEVINE_UUID.equals(this.uuid)) {
            return Long.MAX_VALUE;
        }
        Pair licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(this);
        return Math.min(((Long) licenseDurationRemainingSec.first).longValue(), ((Long) licenseDurationRemainingSec.second).longValue());
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newFrameworkInstance(UUID uuid, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, EventListener eventListener) {
        return new DefaultDrmSessionManager(uuid, FrameworkMediaDrm.newInstance(uuid), mediaDrmCallback, hashMap, handler, eventListener);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newPlayReadyInstance(MediaDrmCallback mediaDrmCallback, String str, Handler handler, EventListener eventListener) {
        HashMap hashMap;
        if (TextUtils.isEmpty(str)) {
            hashMap = null;
        } else {
            hashMap = new HashMap();
            hashMap.put(PLAYREADY_CUSTOM_DATA_KEY, str);
        }
        return newFrameworkInstance(C3446C.PLAYREADY_UUID, mediaDrmCallback, hashMap, handler, eventListener);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newWidevineInstance(MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, EventListener eventListener) {
        return newFrameworkInstance(C3446C.WIDEVINE_UUID, mediaDrmCallback, hashMap, handler, eventListener);
    }

    private void onError(final Exception exception) {
        this.lastException = new DrmSessionException(exception);
        if (!(this.eventHandler == null || this.eventListener == null)) {
            this.eventHandler.post(new Runnable() {
                public void run() {
                    DefaultDrmSessionManager.this.eventListener.onDrmSessionManagerError(exception);
                }
            });
        }
        if (this.state != 4) {
            this.state = 1;
        }
    }

    private void onKeyResponse(Object obj) {
        if (this.state != 3 && this.state != 4) {
            return;
        }
        if (obj instanceof Exception) {
            onKeysError((Exception) obj);
            return;
        }
        try {
            if (this.mode == 3) {
                this.mediaDrm.provideKeyResponse(this.offlineLicenseKeySetId, (byte[]) obj);
                if (this.eventHandler != null && this.eventListener != null) {
                    this.eventHandler.post(new C34682());
                    return;
                }
                return;
            }
            byte[] provideKeyResponse = this.mediaDrm.provideKeyResponse(this.sessionId, (byte[]) obj);
            if (!((this.mode != 2 && (this.mode != 0 || this.offlineLicenseKeySetId == null)) || provideKeyResponse == null || provideKeyResponse.length == 0)) {
                this.offlineLicenseKeySetId = provideKeyResponse;
            }
            this.state = 4;
            if (this.eventHandler != null && this.eventListener != null) {
                this.eventHandler.post(new C34693());
            }
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    private void onKeysError(Exception exception) {
        if (exception instanceof NotProvisionedException) {
            postProvisionRequest();
        } else {
            onError(exception);
        }
    }

    private void onProvisionResponse(Object obj) {
        this.provisioningInProgress = false;
        if (this.state != 2 && this.state != 3 && this.state != 4) {
            return;
        }
        if (obj instanceof Exception) {
            onError((Exception) obj);
            return;
        }
        try {
            this.mediaDrm.provideProvisionResponse((byte[]) obj);
            if (this.state == 2) {
                openInternal(false);
            } else {
                doLicense();
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    private void openInternal(boolean z) {
        try {
            this.sessionId = this.mediaDrm.openSession();
            this.mediaCrypto = this.mediaDrm.createMediaCrypto(this.uuid, this.sessionId);
            this.state = 3;
            doLicense();
        } catch (Exception e) {
            if (z) {
                postProvisionRequest();
            } else {
                onError(e);
            }
        } catch (Exception e2) {
            onError(e2);
        }
    }

    private void postKeyRequest(byte[] bArr, int i) {
        try {
            this.postRequestHandler.obtainMessage(1, this.mediaDrm.getKeyRequest(bArr, this.schemeInitData, this.schemeMimeType, i, this.optionalKeyRequestParameters)).sendToTarget();
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    private void postProvisionRequest() {
        if (!this.provisioningInProgress) {
            this.provisioningInProgress = true;
            this.postRequestHandler.obtainMessage(0, this.mediaDrm.getProvisionRequest()).sendToTarget();
        }
    }

    private boolean restoreKeys() {
        try {
            this.mediaDrm.restoreKeys(this.sessionId, this.offlineLicenseKeySetId);
            return true;
        } catch (Throwable e) {
            Log.e(TAG, "Error trying to restore Widevine keys.", e);
            onError(e);
            return false;
        }
    }

    public DrmSession<T> acquireSession(Looper looper, DrmInitData drmInitData) {
        boolean z = this.playbackLooper == null || this.playbackLooper == looper;
        Assertions.checkState(z);
        int i = this.openCount + 1;
        this.openCount = i;
        if (i == 1) {
            if (this.playbackLooper == null) {
                this.playbackLooper = looper;
                this.mediaDrmHandler = new MediaDrmHandler(looper);
                this.postResponseHandler = new PostResponseHandler(looper);
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
                        byte[] parseSchemeSpecificData = PsshAtomUtil.parseSchemeSpecificData(this.schemeInitData, C3446C.WIDEVINE_UUID);
                        if (parseSchemeSpecificData != null) {
                            this.schemeInitData = parseSchemeSpecificData;
                        }
                    }
                    if (Util.SDK_INT < 26 && C3446C.CLEARKEY_UUID.equals(this.uuid) && (MimeTypes.VIDEO_MP4.equals(this.schemeMimeType) || MimeTypes.AUDIO_MP4.equals(this.schemeMimeType))) {
                        this.schemeMimeType = "cenc";
                    }
                }
            }
            this.state = 2;
            openInternal(true);
        }
        return this;
    }

    public boolean canAcquireSession(DrmInitData drmInitData) {
        SchemeData schemeData = drmInitData.get(this.uuid);
        if (schemeData == null) {
            return false;
        }
        String str = schemeData.type;
        return (str == null || "cenc".equals(str)) ? true : !(C3446C.CENC_TYPE_cbc1.equals(str) || C3446C.CENC_TYPE_cbcs.equals(str) || C3446C.CENC_TYPE_cens.equals(str)) || Util.SDK_INT >= 24;
    }

    public final DrmSessionException getError() {
        return this.state == 1 ? this.lastException : null;
    }

    public final T getMediaCrypto() {
        return this.mediaCrypto;
    }

    public byte[] getOfflineLicenseKeySetId() {
        return this.offlineLicenseKeySetId;
    }

    public final byte[] getPropertyByteArray(String str) {
        return this.mediaDrm.getPropertyByteArray(str);
    }

    public final String getPropertyString(String str) {
        return this.mediaDrm.getPropertyString(str);
    }

    public final int getState() {
        return this.state;
    }

    public Map<String, String> queryKeyStatus() {
        return this.sessionId == null ? null : this.mediaDrm.queryKeyStatus(this.sessionId);
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

    public void setMode(int i, byte[] bArr) {
        Assertions.checkState(this.openCount == 0);
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.mode = i;
        this.offlineLicenseKeySetId = bArr;
    }

    public final void setPropertyByteArray(String str, byte[] bArr) {
        this.mediaDrm.setPropertyByteArray(str, bArr);
    }

    public final void setPropertyString(String str, String str2) {
        this.mediaDrm.setPropertyString(str, str2);
    }
}
