package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.ObjectUtils;

public class ConstantInitializer<T> implements ConcurrentInitializer<T> {
    private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
    private final T object;

    public ConstantInitializer(T obj) {
        this.object = obj;
    }

    public final T getObject() {
        return this.object;
    }

    public T get() throws ConcurrentException {
        return getObject();
    }

    public int hashCode() {
        return getObject() != null ? getObject().hashCode() : 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConstantInitializer)) {
            return false;
        }
        return ObjectUtils.equals(getObject(), ((ConstantInitializer) obj).getObject());
    }

    public String toString() {
        return String.format(FMT_TO_STRING, new Object[]{Integer.valueOf(System.identityHashCode(this)), String.valueOf(getObject())});
    }
}
