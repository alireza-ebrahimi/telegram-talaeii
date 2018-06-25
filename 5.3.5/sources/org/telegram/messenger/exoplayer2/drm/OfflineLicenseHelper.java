package org.telegram.messenger.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import java.io.IOException;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.drm.DefaultDrmSessionManager.EventListener;
import org.telegram.messenger.exoplayer2.drm.DrmSession.DrmSessionException;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.Factory;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class OfflineLicenseHelper<T extends ExoMediaCrypto> {
    private final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager<T> drmSessionManager;
    private final HandlerThread handlerThread = new HandlerThread("OfflineLicenseHelper");

    /* renamed from: org.telegram.messenger.exoplayer2.drm.OfflineLicenseHelper$1 */
    class C17121 implements EventListener {
        C17121() {
        }

        public void onDrmKeysLoaded() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmSessionManagerError(Exception e) {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmKeysRestored() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }

        public void onDrmKeysRemoved() {
            OfflineLicenseHelper.this.conditionVariable.open();
        }
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, Factory httpDataSourceFactory) throws UnsupportedDrmException {
        return newWidevineInstance(defaultLicenseUrl, false, httpDataSourceFactory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, Factory httpDataSourceFactory) throws UnsupportedDrmException {
        return newWidevineInstance(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, Factory httpDataSourceFactory, HashMap<String, String> optionalKeyRequestParameters) throws UnsupportedDrmException {
        return new OfflineLicenseHelper(FrameworkMediaDrm.newInstance(C0907C.WIDEVINE_UUID), new HttpMediaDrmCallback(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory), optionalKeyRequestParameters);
    }

    public OfflineLicenseHelper(ExoMediaDrm<T> mediaDrm, MediaDrmCallback callback, HashMap<String, String> optionalKeyRequestParameters) {
        this.handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        EventListener eventListener = new C17121();
        this.drmSessionManager = new DefaultDrmSessionManager(C0907C.WIDEVINE_UUID, mediaDrm, callback, optionalKeyRequestParameters, new Handler(this.handlerThread.getLooper()), eventListener);
    }

    public synchronized byte[] getPropertyByteArray(String key) {
        return this.drmSessionManager.getPropertyByteArray(key);
    }

    public synchronized void setPropertyByteArray(String key, byte[] value) {
        this.drmSessionManager.setPropertyByteArray(key, value);
    }

    public synchronized String getPropertyString(String key) {
        return this.drmSessionManager.getPropertyString(key);
    }

    public synchronized void setPropertyString(String key, String value) {
        this.drmSessionManager.setPropertyString(key, value);
    }

    public synchronized byte[] downloadLicense(DrmInitData drmInitData) throws IOException, InterruptedException, DrmSessionException {
        Assertions.checkArgument(drmInitData != null);
        return blockingKeyRequest(2, null, drmInitData);
    }

    public synchronized byte[] renewLicense(byte[] offlineLicenseKeySetId) throws DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        return blockingKeyRequest(2, offlineLicenseKeySetId, null);
    }

    public synchronized void releaseLicense(byte[] offlineLicenseKeySetId) throws DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        blockingKeyRequest(3, offlineLicenseKeySetId, null);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] offlineLicenseKeySetId) throws DrmSessionException {
        Pair<Long, Long> licenseDurationRemainingSec;
        Assertions.checkNotNull(offlineLicenseKeySetId);
        DrmSession<T> drmSession = openBlockingKeyRequest(1, offlineLicenseKeySetId, null);
        DrmSessionException error = drmSession.getError();
        licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(drmSession);
        this.drmSessionManager.releaseSession(drmSession);
        if (error != null) {
            if (error.getCause() instanceof KeysExpiredException) {
                licenseDurationRemainingSec = Pair.create(Long.valueOf(0), Long.valueOf(0));
            } else {
                throw error;
            }
        }
        return licenseDurationRemainingSec;
    }

    public void release() {
        this.handlerThread.quit();
    }

    private byte[] blockingKeyRequest(int licenseMode, byte[] offlineLicenseKeySetId, DrmInitData drmInitData) throws DrmSessionException {
        DrmSession<T> drmSession = openBlockingKeyRequest(licenseMode, offlineLicenseKeySetId, drmInitData);
        DrmSessionException error = drmSession.getError();
        byte[] keySetId = drmSession.getOfflineLicenseKeySetId();
        this.drmSessionManager.releaseSession(drmSession);
        if (error == null) {
            return keySetId;
        }
        throw error;
    }

    private DrmSession<T> openBlockingKeyRequest(int licenseMode, byte[] offlineLicenseKeySetId, DrmInitData drmInitData) {
        this.drmSessionManager.setMode(licenseMode, offlineLicenseKeySetId);
        this.conditionVariable.close();
        DrmSession<T> drmSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), drmInitData);
        this.conditionVariable.block();
        return drmSession;
    }
}
