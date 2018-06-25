package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Objects {

    public static final class ToStringHelper {
        private final List<String> zzul;
        private final Object zzum;

        private ToStringHelper(Object obj) {
            this.zzum = Preconditions.checkNotNull(obj);
            this.zzul = new ArrayList();
        }

        public final ToStringHelper add(String str, Object obj) {
            List list = this.zzul;
            String str2 = (String) Preconditions.checkNotNull(str);
            String valueOf = String.valueOf(obj);
            list.add(new StringBuilder((String.valueOf(str2).length() + 1) + String.valueOf(valueOf).length()).append(str2).append("=").append(valueOf).toString());
            return this;
        }

        public final ToStringHelper addValue(Object obj) {
            this.zzul.add(String.valueOf(obj));
            return this;
        }

        public final String toString() {
            StringBuilder append = new StringBuilder(100).append(this.zzum.getClass().getSimpleName()).append('{');
            int size = this.zzul.size();
            for (int i = 0; i < size; i++) {
                append.append((String) this.zzul.get(i));
                if (i < size - 1) {
                    append.append(", ");
                }
            }
            return append.append('}').toString();
        }
    }

    private Objects() {
        throw new AssertionError("Uninstantiable");
    }

    public static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }
}
