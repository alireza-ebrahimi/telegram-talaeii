package com.google.android.gms.internal;

import com.android.volley.toolbox.HttpClientStack.HttpPatch;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLSocketFactory;

public final class zzas extends zzai {
    private final zzat zzcg;
    private final SSLSocketFactory zzch;

    public zzas() {
        this(null);
    }

    private zzas(zzat zzat) {
        this(null, null);
    }

    private zzas(zzat zzat, SSLSocketFactory sSLSocketFactory) {
        this.zzcg = null;
        this.zzch = null;
    }

    private static InputStream zza(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            return httpURLConnection.getErrorStream();
        }
    }

    private static void zza(HttpURLConnection httpURLConnection, zzr<?> zzr) throws IOException, zza {
        byte[] zzf = zzr.zzf();
        if (zzf != null) {
            httpURLConnection.setDoOutput(true);
            String str = HttpRequest.HEADER_CONTENT_TYPE;
            String str2 = "application/x-www-form-urlencoded; charset=";
            String valueOf = String.valueOf("UTF-8");
            httpURLConnection.addRequestProperty(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzf);
            dataOutputStream.close();
        }
    }

    private static List<zzl> zzc(Map<String, List<String>> map) {
        List<zzl> arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() != null) {
                for (String zzl : (List) entry.getValue()) {
                    arrayList.add(new zzl((String) entry.getKey(), zzl));
                }
            }
        }
        return arrayList;
    }

    public final zzaq zza(zzr<?> zzr, Map<String, String> map) throws IOException, zza {
        String zzg;
        String url = zzr.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(zzr.getHeaders());
        hashMap.putAll(map);
        if (this.zzcg != null) {
            zzg = this.zzcg.zzg(url);
            if (zzg == null) {
                String str = "URL blocked by rewriter: ";
                zzg = String.valueOf(url);
                throw new IOException(zzg.length() != 0 ? str.concat(zzg) : new String(str));
            }
        }
        zzg = url;
        URL url2 = new URL(zzg);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        int zzh = zzr.zzh();
        httpURLConnection.setConnectTimeout(zzh);
        httpURLConnection.setReadTimeout(zzh);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        "https".equals(url2.getProtocol());
        for (String url3 : hashMap.keySet()) {
            httpURLConnection.addRequestProperty(url3, (String) hashMap.get(url3));
        }
        switch (zzr.getMethod()) {
            case -1:
                break;
            case 0:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                break;
            case 1:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
                zza(httpURLConnection, (zzr) zzr);
                break;
            case 2:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_PUT);
                zza(httpURLConnection, (zzr) zzr);
                break;
            case 3:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_DELETE);
                break;
            case 4:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_HEAD);
                break;
            case 5:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_OPTIONS);
                break;
            case 6:
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_TRACE);
                break;
            case 7:
                httpURLConnection.setRequestMethod(HttpPatch.METHOD_NAME);
                zza(httpURLConnection, (zzr) zzr);
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
        zzh = httpURLConnection.getResponseCode();
        if (zzh == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        boolean z = (zzr.getMethod() == 4 || ((100 <= zzh && zzh < 200) || zzh == 204 || zzh == 304)) ? false : true;
        return !z ? new zzaq(zzh, zzc(httpURLConnection.getHeaderFields())) : new zzaq(zzh, zzc(httpURLConnection.getHeaderFields()), httpURLConnection.getContentLength(), zza(httpURLConnection));
    }
}
