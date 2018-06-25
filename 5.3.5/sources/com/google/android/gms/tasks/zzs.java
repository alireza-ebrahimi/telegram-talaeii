package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class zzs implements Continuation<Void, List<Task<?>>> {
    private /* synthetic */ Collection zzles;

    zzs(Collection collection) {
        this.zzles = collection;
    }

    public final /* synthetic */ Object then(@NonNull Task task) throws Exception {
        List arrayList = new ArrayList();
        arrayList.addAll(this.zzles);
        return arrayList;
    }
}
