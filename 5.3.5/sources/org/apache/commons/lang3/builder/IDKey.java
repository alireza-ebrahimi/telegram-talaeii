package org.apache.commons.lang3.builder;

final class IDKey {
    private final int id;
    private final Object value;

    public IDKey(Object _value) {
        this.id = System.identityHashCode(_value);
        this.value = _value;
    }

    public int hashCode() {
        return this.id;
    }

    public boolean equals(Object other) {
        if (!(other instanceof IDKey)) {
            return false;
        }
        IDKey idKey = (IDKey) other;
        if (this.id == idKey.id && this.value == idKey.value) {
            return true;
        }
        return false;
    }
}
