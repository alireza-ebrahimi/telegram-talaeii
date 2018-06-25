package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

@WorkerThread
final class zzcjr implements Runnable {
    private final String packageName;
    private final URL url;
    private final byte[] zzjlc;
    private final zzcjp zzjld;
    private final Map<String, String> zzjle;
    private /* synthetic */ zzcjn zzjlf;

    public zzcjr(zzcjn zzcjn, String str, URL url, byte[] bArr, Map<String, String> map, zzcjp zzcjp) {
        this.zzjlf = zzcjn;
        zzbq.zzgv(str);
        zzbq.checkNotNull(url);
        zzbq.checkNotNull(zzcjp);
        this.url = url;
        this.zzjlc = bArr;
        this.zzjld = zzcjp;
        this.packageName = str;
        this.zzjle = map;
    }

    public final void run() {
        HttpURLConnection zzb;
        OutputStream outputStream;
        Throwable e;
        Map map;
        int i;
        HttpURLConnection httpURLConnection;
        Throwable th;
        Map map2;
        this.zzjlf.zzaya();
        int i2 = 0;
        try {
            zzb = this.zzjlf.zzb(this.url);
            try {
                if (this.zzjle != null) {
                    for (Entry entry : this.zzjle.entrySet()) {
                        zzb.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (this.zzjlc != null) {
                    byte[] zzr = this.zzjlf.zzayl().zzr(this.zzjlc);
                    this.zzjlf.zzayp().zzbba().zzj("Uploading data. size", Integer.valueOf(zzr.length));
                    zzb.setDoOutput(true);
                    zzb.addRequestProperty(HttpRequest.HEADER_CONTENT_ENCODING, HttpRequest.ENCODING_GZIP);
                    zzb.setFixedLengthStreamingMode(zzr.length);
                    zzb.connect();
                    outputStream = zzb.getOutputStream();
                    try {
                        outputStream.write(zzr);
                        outputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        map = null;
                        i = 0;
                        httpURLConnection = zzb;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e3) {
                                this.zzjlf.zzayp().zzbau().zze("Error closing HTTP compressed POST connection output stream. appId", zzcjj.zzjs(this.packageName), e3);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i, e, null, map));
                    } catch (Throwable th2) {
                        th = th2;
                        map2 = null;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e32) {
                                this.zzjlf.zzayp().zzbau().zze("Error closing HTTP compressed POST connection output stream. appId", zzcjj.zzjs(this.packageName), e32);
                            }
                        }
                        if (zzb != null) {
                            zzb.disconnect();
                        }
                        this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i2, null, null, map2));
                        throw th;
                    }
                }
                i2 = zzb.getResponseCode();
                map2 = zzb.getHeaderFields();
            } catch (IOException e4) {
                e = e4;
                map = null;
                i = i2;
                outputStream = null;
                httpURLConnection = zzb;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i, e, null, map));
            } catch (Throwable th3) {
                th = th3;
                map2 = null;
                outputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i2, null, null, map2));
                throw th;
            }
            try {
                byte[] zza = zzcjn.zzc(zzb);
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i2, null, zza, map2));
            } catch (IOException e5) {
                e = e5;
                map = map2;
                i = i2;
                outputStream = null;
                httpURLConnection = zzb;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i, e, null, map));
            } catch (Throwable th32) {
                th = th32;
                outputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i2, null, null, map2));
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            map = null;
            i = 0;
            outputStream = null;
            httpURLConnection = null;
            if (outputStream != null) {
                outputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i, e, null, map));
        } catch (Throwable th322) {
            th = th322;
            map2 = null;
            zzb = null;
            outputStream = null;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzb != null) {
                zzb.disconnect();
            }
            this.zzjlf.zzayo().zzh(new zzcjq(this.packageName, this.zzjld, i2, null, null, map2));
            throw th;
        }
    }
}
