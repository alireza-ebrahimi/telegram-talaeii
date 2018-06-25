package com.google.android.gms.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class zzx implements Continuation<Void, Task<List<Task<?>>>> {
    private final /* synthetic */ Collection zzagk;

    zzx(Collection collection) {
        this.zzagk = collection;
    }

    public final /* synthetic */ Object then(Task task) {
        List arrayList = new ArrayList();
        arrayList.addAll(this.zzagk);
        return Tasks.forResult(arrayList);
    }
}
