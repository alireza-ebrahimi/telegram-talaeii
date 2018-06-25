package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Stack;

final class zzfjs {
    private final Stack<zzfgs> zzprx;

    private zzfjs() {
        this.zzprx = new Stack();
    }

    private final void zzbb(zzfgs zzfgs) {
        zzfgs zzfgs2 = zzfgs;
        while (!zzfgs2.zzcxs()) {
            if (zzfgs2 instanceof zzfjq) {
                zzfjq zzfjq = (zzfjq) zzfgs2;
                zzbb(zzfjq.zzprt);
                zzfgs2 = zzfjq.zzpru;
            } else {
                String valueOf = String.valueOf(zzfgs2.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 49).append("Has a new type of ByteString been created? Found ").append(valueOf).toString());
            }
        }
        int zzmp = zzmp(zzfgs2.size());
        int i = zzfjq.zzprr[zzmp + 1];
        if (this.zzprx.isEmpty() || ((zzfgs) this.zzprx.peek()).size() >= i) {
            this.zzprx.push(zzfgs2);
            return;
        }
        int i2 = zzfjq.zzprr[zzmp];
        zzfgs zzfgs3 = (zzfgs) this.zzprx.pop();
        while (!this.zzprx.isEmpty() && ((zzfgs) this.zzprx.peek()).size() < i2) {
            zzfgs3 = new zzfjq((zzfgs) this.zzprx.pop(), zzfgs3);
        }
        zzfgs2 = new zzfjq(zzfgs3, zzfgs2);
        while (!this.zzprx.isEmpty()) {
            if (((zzfgs) this.zzprx.peek()).size() >= zzfjq.zzprr[zzmp(zzfgs2.size()) + 1]) {
                break;
            }
            zzfgs2 = new zzfjq((zzfgs) this.zzprx.pop(), zzfgs2);
        }
        this.zzprx.push(zzfgs2);
    }

    private final zzfgs zzc(zzfgs zzfgs, zzfgs zzfgs2) {
        zzbb(zzfgs);
        zzbb(zzfgs2);
        zzfgs zzfgs3 = (zzfgs) this.zzprx.pop();
        while (!this.zzprx.isEmpty()) {
            zzfgs3 = new zzfjq((zzfgs) this.zzprx.pop(), zzfgs3);
        }
        return zzfgs3;
    }

    private static int zzmp(int i) {
        int binarySearch = Arrays.binarySearch(zzfjq.zzprr, i);
        return binarySearch < 0 ? (-(binarySearch + 1)) - 1 : binarySearch;
    }
}
