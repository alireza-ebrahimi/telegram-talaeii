package org.telegram.messenger.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.EventListener;
import org.telegram.messenger.exoplayer2.drm.DrmSession.DrmSessionException;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.Factory;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class OfflineLicenseHelper<T extends ExoMediaCrypto> {
    private final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager<T> drmSessionManager;
    private final HandlerThread handlerThread = new HandlerThread("OfflineLicenseHelper");

    /* renamed from: org.telegram.messenger.exoplayer2.drm.OfflineLicenseHelper$1 */
    class C34761 implements EventListener {
        C34761() {
        }

        public void onDrmKeysLoaded() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmKeysRemoved() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmKeysRestored() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmSessionManagerError(Exception exception) {
            OfflineLicenseHelper.this.conditionVariable.open();
        }
    }

    public OfflineLicenseHelper(ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap) {
        this.handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        EventListener c34761 = new C34761();
        this.drmSessionManager = new DefaultDrmSessionManager(C3446C.WIDEVINE_UUID, exoMediaDrm, mediaDrmCallback, hashMap, new Handler(this.handlerThread.getLooper()), c34761);
    }

    private byte[] blockingKeyRequest(int i, byte[] bArr, DrmInitData drmInitData) {
        DrmSession openBlockingKeyRequest = openBlockingKeyRequest(i, bArr, drmInitData);
        DrmSessionException error = openBlockingKeyRequest.getError();
        byte[] offlineLicenseKeySetId = openBlockingKeyRequest.getOfflineLicenseKeySetId();
        this.drmSessionManager.releaseSession(openBlockingKeyRequest);
        if (error == null) {
            return offlineLicenseKeySetId;
        }
        throw error;
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, Factory factory) {
        return newWidevineInstance(str, false, factory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, boolean z, Factory factory) {
        return newWidevineInstance(str, z, factory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, boolean z, Factory factory, HashMap<String, String> hashMap) {
        return new OfflineLicenseHelper(FrameworkMediaDrm.newInstance(C3446C.WIDEVINE_UUID), new HttpMediaDrmCallback(str, z, factory), hashMap);
    }

    private DrmSession<T> openBlockingKeyRequest(int i, byte[] bArr, DrmInitData drmInitData) {
        this.drmSessionManager.setMode(i, bArr);
        this.conditionVariable.close();
        DrmSession<T> acquireSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), drmInitData);
        this.conditionVariable.block();
        return acquireSession;
    }

    public synchronized byte[] downloadLicense(DrmInitData drmInitData) {
        Assertions.checkArgument(drmInitData != null);
        return blockingKeyRequest(2, null, drmInitData);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] bArr) {
        Pair<Long, Long> licenseDurationRemainingSec;
        Assertions.checkNotNull(bArr);
        DrmSession openBlockingKeyRequest = openBlockingKeyRequest(1, bArr, null);
        DrmSessionException error = openBlockingKeyRequest.getError();
        licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(openBlockingKeyRequest);
        this.drmSessionManager.releaseSession(openBlockingKeyRequest);
        if (error != null) {
            if (error.getCause() instanceof KeysExpiredException) {
                licenseDurationRemainingSec = Pair.create(Long.valueOf(0), Long.valueOf(0));
            } else {
                throw error;
            }
        }
        return licenseDurationRemainingSec;
    }

    public synchronized byte[] getPropertyByteArray(String str) {
        return this.drmSessionManager.getPropertyByteArray(str);
    }

    public synchronized String getPropertyString(String str) {
        return this.drmSessionManager.getPropertyString(str);
    }

    public void release() {
        this.handlerThread.quit();
    }

    public synchronized void releaseLicense(byte[] bArr) {
        Assertions.checkNotNull(bArr);
        blockingKeyRequest(3, bArr, null);
    }

    public synchronized byte[] renewLicense(byte[] bArr) {
        Assertions.checkNotNull(bArr);
        return blockingKeyRequest(2, bArr, null);
    }

    public synchronized void setPropertyByteArray(String str, byte[] bArr) {
        this.drmSessionManager.setPropertyByteArray(str, bArr);
    }

    public synchronized void setPropertyString(String str, String str2) {
        this.drmSessionManager.setPropertyString(str, str2);
    }
}
