package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzr implements Continuation<Void, List<TResult>> {
    private /* synthetic */ Collection zzles;

    zzr(Collection collection) {
        this.zzles = collection;
    }

    public final /* synthetic */ Object then(@NonNull Task task) throws Exception {
        if (this.zzles.size() == 0) {
            return Collections.emptyList();
        }
        List arrayList = new ArrayList();
        for (Task result : this.zzles) {
            arrayList.add(result.getResult());
        }
        return arrayList;
    }
}
