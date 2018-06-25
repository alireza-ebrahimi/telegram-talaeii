package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;

final class zzcie {
    private final String zzcm;
    private String zzina;
    private final zzckj zzjev;
    private String zzjfk;
    private String zzjfl;
    private String zzjfm;
    private String zzjfn;
    private long zzjfo;
    private long zzjfp;
    private long zzjfq;
    private long zzjfr;
    private String zzjfs;
    private long zzjft;
    private long zzjfu;
    private boolean zzjfv;
    private long zzjfw;
    private boolean zzjfx;
    private long zzjfy;
    private long zzjfz;
    private long zzjga;
    private long zzjgb;
    private long zzjgc;
    private long zzjgd;
    private String zzjge;
    private boolean zzjgf;
    private long zzjgg;
    private long zzjgh;

    @WorkerThread
    zzcie(zzckj zzckj, String str) {
        zzbq.checkNotNull(zzckj);
        zzbq.zzgv(str);
        this.zzjev = zzckj;
        this.zzcm = str;
        this.zzjev.zzayo().zzwj();
    }

    @WorkerThread
    public final String getAppId() {
        this.zzjev.zzayo().zzwj();
        return this.zzcm;
    }

    @WorkerThread
    public final String getAppInstanceId() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfk;
    }

    @WorkerThread
    public final String getGmpAppId() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfl;
    }

    @WorkerThread
    public final void setAppVersion(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzina, str) ? 1 : 0) | this.zzjgf;
        this.zzina = str;
    }

    @WorkerThread
    public final void setMeasurementEnabled(boolean z) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfv != z ? 1 : 0) | this.zzjgf;
        this.zzjfv = z;
    }

    @WorkerThread
    public final void zzal(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfp != j ? 1 : 0) | this.zzjgf;
        this.zzjfp = j;
    }

    @WorkerThread
    public final void zzam(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfq != j ? 1 : 0) | this.zzjgf;
        this.zzjfq = j;
    }

    @WorkerThread
    public final void zzan(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfr != j ? 1 : 0) | this.zzjgf;
        this.zzjfr = j;
    }

    @WorkerThread
    public final void zzao(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjft != j ? 1 : 0) | this.zzjgf;
        this.zzjft = j;
    }

    @WorkerThread
    public final void zzap(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfu != j ? 1 : 0) | this.zzjgf;
        this.zzjfu = j;
    }

    @WorkerThread
    public final void zzaq(long j) {
        int i = 1;
        zzbq.checkArgument(j >= 0);
        this.zzjev.zzayo().zzwj();
        boolean z = this.zzjgf;
        if (this.zzjfo == j) {
            i = 0;
        }
        this.zzjgf = z | i;
        this.zzjfo = j;
    }

    @WorkerThread
    public final void zzar(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjgg != j ? 1 : 0) | this.zzjgf;
        this.zzjgg = j;
    }

    @WorkerThread
    public final void zzas(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjgh != j ? 1 : 0) | this.zzjgf;
        this.zzjgh = j;
    }

    @WorkerThread
    public final void zzat(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfy != j ? 1 : 0) | this.zzjgf;
        this.zzjfy = j;
    }

    @WorkerThread
    public final void zzau(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfz != j ? 1 : 0) | this.zzjgf;
        this.zzjfz = j;
    }

    @WorkerThread
    public final void zzav(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjga != j ? 1 : 0) | this.zzjgf;
        this.zzjga = j;
    }

    @WorkerThread
    public final void zzaw(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjgb != j ? 1 : 0) | this.zzjgf;
        this.zzjgb = j;
    }

    @WorkerThread
    public final void zzax(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjgd != j ? 1 : 0) | this.zzjgf;
        this.zzjgd = j;
    }

    @WorkerThread
    public final void zzay(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjgc != j ? 1 : 0) | this.zzjgf;
        this.zzjgc = j;
    }

    @WorkerThread
    public final void zzays() {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = false;
    }

    @WorkerThread
    public final String zzayt() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfm;
    }

    @WorkerThread
    public final String zzayu() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfn;
    }

    @WorkerThread
    public final long zzayv() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfp;
    }

    @WorkerThread
    public final long zzayw() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfq;
    }

    @WorkerThread
    public final long zzayx() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfr;
    }

    @WorkerThread
    public final String zzayy() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfs;
    }

    @WorkerThread
    public final long zzayz() {
        this.zzjev.zzayo().zzwj();
        return this.zzjft;
    }

    @WorkerThread
    public final void zzaz(long j) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (this.zzjfw != j ? 1 : 0) | this.zzjgf;
        this.zzjfw = j;
    }

    @WorkerThread
    public final long zzaza() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfu;
    }

    @WorkerThread
    public final boolean zzazb() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfv;
    }

    @WorkerThread
    public final long zzazc() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfo;
    }

    @WorkerThread
    public final long zzazd() {
        this.zzjev.zzayo().zzwj();
        return this.zzjgg;
    }

    @WorkerThread
    public final long zzaze() {
        this.zzjev.zzayo().zzwj();
        return this.zzjgh;
    }

    @WorkerThread
    public final void zzazf() {
        this.zzjev.zzayo().zzwj();
        long j = this.zzjfo + 1;
        if (j > 2147483647L) {
            this.zzjev.zzayp().zzbaw().zzj("Bundle index overflow. appId", zzcjj.zzjs(this.zzcm));
            j = 0;
        }
        this.zzjgf = true;
        this.zzjfo = j;
    }

    @WorkerThread
    public final long zzazg() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfy;
    }

    @WorkerThread
    public final long zzazh() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfz;
    }

    @WorkerThread
    public final long zzazi() {
        this.zzjev.zzayo().zzwj();
        return this.zzjga;
    }

    @WorkerThread
    public final long zzazj() {
        this.zzjev.zzayo().zzwj();
        return this.zzjgb;
    }

    @WorkerThread
    public final long zzazk() {
        this.zzjev.zzayo().zzwj();
        return this.zzjgd;
    }

    @WorkerThread
    public final long zzazl() {
        this.zzjev.zzayo().zzwj();
        return this.zzjgc;
    }

    @WorkerThread
    public final String zzazm() {
        this.zzjev.zzayo().zzwj();
        return this.zzjge;
    }

    @WorkerThread
    public final String zzazn() {
        this.zzjev.zzayo().zzwj();
        String str = this.zzjge;
        zzjd(null);
        return str;
    }

    @WorkerThread
    public final long zzazo() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfw;
    }

    @WorkerThread
    public final boolean zzazp() {
        this.zzjev.zzayo().zzwj();
        return this.zzjfx;
    }

    @WorkerThread
    public final void zzbq(boolean z) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = this.zzjfx != z;
        this.zzjfx = z;
    }

    @WorkerThread
    public final void zziy(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzjfk, str) ? 1 : 0) | this.zzjgf;
        this.zzjfk = str;
    }

    @WorkerThread
    public final void zziz(String str) {
        this.zzjev.zzayo().zzwj();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzjgf = (!zzcno.zzas(this.zzjfl, str) ? 1 : 0) | this.zzjgf;
        this.zzjfl = str;
    }

    @WorkerThread
    public final void zzja(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzjfm, str) ? 1 : 0) | this.zzjgf;
        this.zzjfm = str;
    }

    @WorkerThread
    public final void zzjb(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzjfn, str) ? 1 : 0) | this.zzjgf;
        this.zzjfn = str;
    }

    @WorkerThread
    public final void zzjc(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzjfs, str) ? 1 : 0) | this.zzjgf;
        this.zzjfs = str;
    }

    @WorkerThread
    public final void zzjd(String str) {
        this.zzjev.zzayo().zzwj();
        this.zzjgf = (!zzcno.zzas(this.zzjge, str) ? 1 : 0) | this.zzjgf;
        this.zzjge = str;
    }

    @WorkerThread
    public final String zzwo() {
        this.zzjev.zzayo().zzwj();
        return this.zzina;
    }
}
