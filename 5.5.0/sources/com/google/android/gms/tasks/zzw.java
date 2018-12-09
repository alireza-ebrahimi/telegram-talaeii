package com.google.android.gms.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzw implements Continuation<Void, List<TResult>> {
    private final /* synthetic */ Collection zzagk;

    zzw(Collection collection) {
        this.zzagk = collection;
    }

    public final /* synthetic */ Object then(Task task) {
        if (this.zzagk.size() == 0) {
            return Collections.emptyList();
        }
        List arrayList = new ArrayList();
        for (Task result : this.zzagk) {
            arrayList.add(result.getResult());
        }
        return arrayList;
    }
}
