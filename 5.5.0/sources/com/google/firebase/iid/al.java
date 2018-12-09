package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.io.IOException;

final class al implements Continuation<Bundle, String> {
    /* renamed from: a */
    private final /* synthetic */ ai f5699a;

    al(ai aiVar) {
        this.f5699a = aiVar;
    }

    public final /* synthetic */ Object then(Task task) {
        return ai.m8801a((Bundle) task.getResult(IOException.class));
    }
}
