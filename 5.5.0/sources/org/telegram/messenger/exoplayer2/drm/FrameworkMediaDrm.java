package org.telegram.messenger.exoplayer2.drm;

import android.annotation.TargetApi;
import android.media.MediaCrypto;
import android.media.MediaDrm;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(18)
public final class FrameworkMediaDrm implements ExoMediaDrm<FrameworkMediaCrypto> {
    private final MediaDrm mediaDrm;

    private FrameworkMediaDrm(UUID uuid) {
        this.mediaDrm = new MediaDrm((UUID) Assertions.checkNotNull(uuid));
    }

    public static FrameworkMediaDrm newInstance(UUID uuid) {
        try {
            return new FrameworkMediaDrm(uuid);
        } catch (Exception e) {
            throw new UnsupportedDrmException(1, e);
        } catch (Exception e2) {
            throw new UnsupportedDrmException(2, e2);
        }
    }

    public void closeSession(byte[] bArr) {
        this.mediaDrm.closeSession(bArr);
    }

    public FrameworkMediaCrypto createMediaCrypto(UUID uuid, byte[] bArr) {
        boolean z = Util.SDK_INT < 21 && C3446C.WIDEVINE_UUID.equals(uuid) && "L3".equals(getPropertyString("securityLevel"));
        return new FrameworkMediaCrypto(new MediaCrypto(uuid, bArr), z);
    }

    public KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, String str, int i, HashMap<String, String> hashMap) {
        final MediaDrm.KeyRequest keyRequest = this.mediaDrm.getKeyRequest(bArr, bArr2, str, i, hashMap);
        return new KeyRequest() {
            public byte[] getData() {
                return keyRequest.getData();
            }

            public String getDefaultUrl() {
                return keyRequest.getDefaultUrl();
            }
        };
    }

    public byte[] getPropertyByteArray(String str) {
        return this.mediaDrm.getPropertyByteArray(str);
    }

    public String getPropertyString(String str) {
        return this.mediaDrm.getPropertyString(str);
    }

    public ProvisionRequest getProvisionRequest() {
        final MediaDrm.ProvisionRequest provisionRequest = this.mediaDrm.getProvisionRequest();
        return new ProvisionRequest() {
            public byte[] getData() {
                return provisionRequest.getData();
            }

            public String getDefaultUrl() {
                return provisionRequest.getDefaultUrl();
            }
        };
    }

    public byte[] openSession() {
        return this.mediaDrm.openSession();
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) {
        return this.mediaDrm.provideKeyResponse(bArr, bArr2);
    }

    public void provideProvisionResponse(byte[] bArr) {
        this.mediaDrm.provideProvisionResponse(bArr);
    }

    public Map<String, String> queryKeyStatus(byte[] bArr) {
        return this.mediaDrm.queryKeyStatus(bArr);
    }

    public void release() {
        this.mediaDrm.release();
    }

    public void restoreKeys(byte[] bArr, byte[] bArr2) {
        this.mediaDrm.restoreKeys(bArr, bArr2);
    }

    public void setOnEventListener(final OnEventListener<? super FrameworkMediaCrypto> onEventListener) {
        this.mediaDrm.setOnEventListener(onEventListener == null ? null : new MediaDrm.OnEventListener() {
            public void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
                onEventListener.onEvent(FrameworkMediaDrm.this, bArr, i, i2, bArr2);
            }
        });
    }

    public void setPropertyByteArray(String str, byte[] bArr) {
        this.mediaDrm.setPropertyByteArray(str, bArr);
    }

    public void setPropertyString(String str, String str2) {
        this.mediaDrm.setPropertyString(str, str2);
    }
}
