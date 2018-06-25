package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

final class zzfp implements Runnable {
    private final String packageName;
    private final URL url;
    private final byte[] zzaju;
    private final zzfn zzajv;
    private final Map<String, String> zzajw;
    private final /* synthetic */ zzfl zzajx;

    public zzfp(zzfl zzfl, String str, URL url, byte[] bArr, Map<String, String> map, zzfn zzfn) {
        this.zzajx = zzfl;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzfn);
        this.url = url;
        this.zzaju = bArr;
        this.zzajv = zzfn;
        this.packageName = str;
        this.zzajw = map;
    }

    public final void run() {
        HttpURLConnection zzb;
        Throwable e;
        Map map;
        int i;
        HttpURLConnection httpURLConnection;
        Throwable th;
        this.zzajx.zzft();
        int i2 = 0;
        OutputStream outputStream;
        Map map2;
        try {
            zzb = this.zzajx.zzb(this.url);
            try {
                if (this.zzajw != null) {
                    for (Entry entry : this.zzajw.entrySet()) {
                        zzb.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (this.zzaju != null) {
                    byte[] zza = this.zzajx.zzgc().zza(this.zzaju);
                    this.zzajx.zzgf().zziz().zzg("Uploading data. size", Integer.valueOf(zza.length));
                    zzb.setDoOutput(true);
                    zzb.addRequestProperty("Content-Encoding", "gzip");
                    zzb.setFixedLengthStreamingMode(zza.length);
                    zzb.connect();
                    outputStream = zzb.getOutputStream();
                    try {
                        outputStream.write(zza);
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
                                this.zzajx.zzgf().zzis().zze("Error closing HTTP compressed POST connection output stream. appId", zzfh.zzbl(this.packageName), e3);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i, e, null, map));
                    } catch (Throwable th2) {
                        th = th2;
                        map2 = null;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e32) {
                                this.zzajx.zzgf().zzis().zze("Error closing HTTP compressed POST connection output stream. appId", zzfh.zzbl(this.packageName), e32);
                            }
                        }
                        if (zzb != null) {
                            zzb.disconnect();
                        }
                        this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i2, null, null, map2));
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
                this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i, e, null, map));
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
                this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i2, null, null, map2));
                throw th;
            }
            try {
                byte[] zza2 = zzfl.zzb(zzb);
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i2, null, zza2, map2));
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
                this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i, e, null, map));
            } catch (Throwable th32) {
                th = th32;
                outputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzb != null) {
                    zzb.disconnect();
                }
                this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i2, null, null, map2));
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
            this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i, e, null, map));
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
            this.zzajx.zzge().zzc(new zzfo(this.packageName, this.zzajv, i2, null, null, map2));
            throw th;
        }
    }
}
