package com.google.android.gms.internal;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzam implements zzb {
    private final Map<String, zzan> zzbw;
    private long zzbx;
    private final File zzby;
    private final int zzbz;

    public zzam(File file) {
        this(file, 5242880);
    }

    private zzam(File file, int i) {
        this.zzbw = new LinkedHashMap(16, 0.75f, true);
        this.zzbx = 0;
        this.zzby = file;
        this.zzbz = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        removeEntry(str);
        if (!delete) {
            zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private final void removeEntry(String str) {
        zzan zzan = (zzan) this.zzbw.remove(str);
        if (zzan != null) {
            this.zzbx -= zzan.zzca;
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    private static InputStream zza(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    static String zza(zzao zzao) throws IOException {
        return new String(zza(zzao, zzc(zzao)), "UTF-8");
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzan zzan) {
        if (this.zzbw.containsKey(str)) {
            zzan zzan2 = (zzan) this.zzbw.get(str);
            this.zzbx = (zzan.zzca - zzan2.zzca) + this.zzbx;
        } else {
            this.zzbx += zzan.zzca;
        }
        this.zzbw.put(str, zzan);
    }

    private static byte[] zza(zzao zzao, long j) throws IOException {
        long zzn = zzao.zzn();
        if (j < 0 || j > zzn || ((long) ((int) j)) != j) {
            throw new IOException("streamToBytes length=" + j + ", maxLength=" + zzn);
        }
        byte[] bArr = new byte[((int) j)];
        new DataInputStream(zzao).readFully(bArr);
        return bArr;
    }

    static int zzb(InputStream inputStream) throws IOException {
        return (((zza(inputStream) | 0) | (zza(inputStream) << 8)) | (zza(inputStream) << 16)) | (zza(inputStream) << 24);
    }

    static List<zzl> zzb(zzao zzao) throws IOException {
        int zzb = zzb((InputStream) zzao);
        List<zzl> emptyList = zzb == 0 ? Collections.emptyList() : new ArrayList(zzb);
        for (int i = 0; i < zzb; i++) {
            emptyList.add(new zzl(zza(zzao).intern(), zza(zzao).intern()));
        }
        return emptyList;
    }

    static long zzc(InputStream inputStream) throws IOException {
        return (((((((0 | (((long) zza(inputStream)) & 255)) | ((((long) zza(inputStream)) & 255) << 8)) | ((((long) zza(inputStream)) & 255) << 16)) | ((((long) zza(inputStream)) & 255) << 24)) | ((((long) zza(inputStream)) & 255) << 32)) | ((((long) zza(inputStream)) & 255) << 40)) | ((((long) zza(inputStream)) & 255) << 48)) | ((((long) zza(inputStream)) & 255) << 56);
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzby, zzd(str));
    }

    public final synchronized void initialize() {
        if (this.zzby.exists()) {
            File[] listFiles = this.zzby.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    zzao zzao;
                    try {
                        long length = file.length();
                        zzao = new zzao(new BufferedInputStream(zza(file)), length);
                        zzan zzc = zzan.zzc(zzao);
                        zzc.zzca = length;
                        zza(zzc.key, zzc);
                        zzao.close();
                    } catch (IOException e) {
                        file.delete();
                    } catch (Throwable th) {
                        zzao.close();
                    }
                }
            }
        } else if (!this.zzby.mkdirs()) {
            zzaf.zzc("Unable to create cache dir %s", this.zzby.getAbsolutePath());
        }
    }

    public final synchronized zzc zza(String str) {
        zzc zzc;
        zzan zzan = (zzan) this.zzbw.get(str);
        if (zzan == null) {
            zzc = null;
        } else {
            File zze = zze(str);
            zzao zzao;
            try {
                zzao = new zzao(new BufferedInputStream(zza(zze)), zze.length());
                if (TextUtils.equals(str, zzan.zzc(zzao).key)) {
                    byte[] zza = zza(zzao, zzao.zzn());
                    zzc zzc2 = new zzc();
                    zzc2.data = zza;
                    zzc2.zza = zzan.zza;
                    zzc2.zzb = zzan.zzb;
                    zzc2.zzc = zzan.zzc;
                    zzc2.zzd = zzan.zzd;
                    zzc2.zze = zzan.zze;
                    zzc2.zzf = zzap.zza(zzan.zzg);
                    zzc2.zzg = Collections.unmodifiableList(zzan.zzg);
                    zzao.close();
                    zzc = zzc2;
                } else {
                    zzaf.zzb("%s: key=%s, found=%s", zze.getAbsolutePath(), str, zzan.zzc(zzao).key);
                    removeEntry(str);
                    zzao.close();
                    zzc = null;
                }
            } catch (IOException e) {
                zzaf.zzb("%s: %s", zze.getAbsolutePath(), e.toString());
                remove(str);
                zzc = null;
            } catch (Throwable th) {
                zzao.close();
            }
        }
        return zzc;
    }

    public final synchronized void zza(String str, zzc zzc) {
        int i = 0;
        synchronized (this) {
            int length = zzc.data.length;
            if (this.zzbx + ((long) length) >= ((long) this.zzbz)) {
                int i2;
                if (zzaf.DEBUG) {
                    zzaf.zza("Pruning old cache entries.", new Object[0]);
                }
                long j = this.zzbx;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = this.zzbw.entrySet().iterator();
                while (it.hasNext()) {
                    zzan zzan = (zzan) ((Entry) it.next()).getValue();
                    if (zze(zzan.key).delete()) {
                        this.zzbx -= zzan.zzca;
                    } else {
                        zzaf.zzb("Could not delete cache entry for key=%s, filename=%s", zzan.key, zzd(zzan.key));
                    }
                    it.remove();
                    i2 = i + 1;
                    if (((float) (this.zzbx + ((long) length))) < ((float) this.zzbz) * 0.9f) {
                        break;
                    }
                    i = i2;
                }
                i2 = i;
                if (zzaf.DEBUG) {
                    zzaf.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i2), Long.valueOf(this.zzbx - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            }
            File zze = zze(str);
            try {
                OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zze));
                zzan zzan2 = new zzan(str, zzc);
                if (zzan2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.write(zzc.data);
                    bufferedOutputStream.close();
                    zza(str, zzan2);
                } else {
                    bufferedOutputStream.close();
                    zzaf.zzb("Failed to write header for %s", zze.getAbsolutePath());
                    throw new IOException();
                }
            } catch (IOException e) {
                if (!zze.delete()) {
                    zzaf.zzb("Could not clean up file %s", zze.getAbsolutePath());
                }
            }
        }
    }
}
