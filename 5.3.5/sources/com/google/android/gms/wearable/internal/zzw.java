package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import java.util.Set;

public final class zzw implements CapabilityInfo {
    private final String mName;
    private final Set<Node> zzlsr;

    public zzw(CapabilityInfo capabilityInfo) {
        this(capabilityInfo.getName(), capabilityInfo.getNodes());
    }

    private zzw(String str, Set<Node> set) {
        this.mName = str;
        this.zzlsr = set;
    }

    public final String getName() {
        return this.mName;
    }

    public final Set<Node> getNodes() {
        return this.zzlsr;
    }
}
