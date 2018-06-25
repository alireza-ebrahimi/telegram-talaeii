package org.telegram.messenger.exoplayer2.drm;

import android.annotation.TargetApi;
import android.net.Uri;
import android.text.TextUtils;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSourceInputStream;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.Factory;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(18)
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private final Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(String str, Factory factory) {
        this(str, false, factory);
    }

    public HttpMediaDrmCallback(String str, boolean z, Factory factory) {
        this.dataSourceFactory = factory;
        this.defaultLicenseUrl = str;
        this.forceDefaultLicenseUrl = z;
        this.keyRequestProperties = new HashMap();
    }

    private static byte[] executePost(Factory factory, String str, byte[] bArr, Map<String, String> map) {
        DataSource createDataSource = factory.createDataSource();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                createDataSource.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
        }
        Closeable dataSourceInputStream = new DataSourceInputStream(createDataSource, new DataSpec(Uri.parse(str), bArr, 0, 0, -1, null, 1));
        try {
            byte[] toByteArray = Util.toByteArray(dataSourceInputStream);
            return toByteArray;
        } finally {
            Util.closeQuietly(dataSourceInputStream);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(str);
        }
    }

    public byte[] executeKeyRequest(UUID uuid, KeyRequest keyRequest) {
        String defaultUrl = keyRequest.getDefaultUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(defaultUrl)) {
            defaultUrl = this.defaultLicenseUrl;
        }
        Map hashMap = new HashMap();
        Object obj = C3446C.PLAYREADY_UUID.equals(uuid) ? "text/xml" : C3446C.CLEARKEY_UUID.equals(uuid) ? "application/json" : "application/octet-stream";
        hashMap.put("Content-Type", obj);
        if (C3446C.PLAYREADY_UUID.equals(uuid)) {
            hashMap.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            hashMap.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, defaultUrl, keyRequest.getData(), hashMap);
    }

    public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest provisionRequest) {
        return executePost(this.dataSourceFactory, provisionRequest.getDefaultUrl() + "&signedRequest=" + new String(provisionRequest.getData()), new byte[0], null);
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(str, str2);
        }
    }
}
