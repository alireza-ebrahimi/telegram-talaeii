package org.telegram.messenger.exoplayer2.drm;

import java.util.UUID;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;

public interface MediaDrmCallback {
    byte[] executeKeyRequest(UUID uuid, KeyRequest keyRequest) throws Exception;

    byte[] executeProvisionRequest(UUID uuid, ProvisionRequest provisionRequest) throws Exception;
}
