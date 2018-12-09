package org.telegram.messenger.exoplayer2.drm;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface ExoMediaDrm<T extends ExoMediaCrypto> {

    public interface OnEventListener<T extends ExoMediaCrypto> {
        void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] bArr, int i, int i2, byte[] bArr2);
    }

    public interface KeyRequest {
        byte[] getData();

        String getDefaultUrl();
    }

    public interface ProvisionRequest {
        byte[] getData();

        String getDefaultUrl();
    }

    void closeSession(byte[] bArr);

    T createMediaCrypto(UUID uuid, byte[] bArr);

    KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, String str, int i, HashMap<String, String> hashMap);

    byte[] getPropertyByteArray(String str);

    String getPropertyString(String str);

    ProvisionRequest getProvisionRequest();

    byte[] openSession();

    byte[] provideKeyResponse(byte[] bArr, byte[] bArr2);

    void provideProvisionResponse(byte[] bArr);

    Map<String, String> queryKeyStatus(byte[] bArr);

    void release();

    void restoreKeys(byte[] bArr, byte[] bArr2);

    void setOnEventListener(OnEventListener<? super T> onEventListener);

    void setPropertyByteArray(String str, byte[] bArr);

    void setPropertyString(String str, String str2);
}
