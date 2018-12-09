package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
/* renamed from: com.google.firebase.components.g */
public class C1907g extends C1906h {
    /* renamed from: a */
    private final List<C1902a<?>> f5614a;

    @KeepForSdk
    public C1907g(List<C1902a<?>> list) {
        super("Dependency cycle detected: " + Arrays.toString(list.toArray()));
        this.f5614a = list;
    }
}
