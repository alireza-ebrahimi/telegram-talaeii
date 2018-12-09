package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api.Client;
import java.util.ArrayList;

final class zzap extends zzat {
    private final /* synthetic */ zzaj zzhv;
    private final ArrayList<Client> zzib;

    public zzap(zzaj zzaj, ArrayList<Client> arrayList) {
        this.zzhv = zzaj;
        super(zzaj);
        this.zzib = arrayList;
    }

    public final void zzaq() {
        this.zzhv.zzhf.zzfq.zzim = this.zzhv.zzaw();
        ArrayList arrayList = this.zzib;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Client) obj).getRemoteService(this.zzhv.zzhr, this.zzhv.zzhf.zzfq.zzim);
        }
    }
}
