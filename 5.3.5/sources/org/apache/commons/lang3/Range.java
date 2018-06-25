package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Comparator;

public final class Range<T> implements Serializable {
    private static final long serialVersionUID = 1;
    private final Comparator<T> comparator;
    private transient int hashCode;
    private final T maximum;
    private final T minimum;
    private transient String toString;

    private enum ComparableComparator implements Comparator {
        INSTANCE;

        public int compare(Object obj1, Object obj2) {
            return ((Comparable) obj1).compareTo(obj2);
        }
    }

    public static <T extends Comparable<T>> Range<T> is(T element) {
        return between(element, element, null);
    }

    public static <T> Range<T> is(T element, Comparator<T> comparator) {
        return between(element, element, comparator);
    }

    public static <T extends Comparable<T>> Range<T> between(T fromInclusive, T toInclusive) {
        return between(fromInclusive, toInclusive, null);
    }

    public static <T> Range<T> between(T fromInclusive, T toInclusive, Comparator<T> comparator) {
        return new Range(fromInclusive, toInclusive, comparator);
    }

    private Range(T element1, T element2, Comparator<T> comparator) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException("Elements in a range must not be null: element1=" + element1 + ", element2=" + element2);
        }
        if (comparator == null) {
            comparator = ComparableComparator.INSTANCE;
        }
        if (comparator.compare(element1, element2) < 1) {
            this.minimum = element1;
            this.maximum = element2;
        } else {
            this.minimum = element2;
            this.maximum = element1;
        }
        this.comparator = comparator;
    }

    public T getMinimum() {
        return this.minimum;
    }

    public T getMaximum() {
        return this.maximum;
    }

    public Comparator<T> getComparator() {
        return this.comparator;
    }

    public boolean isNaturalOrdering() {
        return this.comparator == ComparableComparator.INSTANCE;
    }

    public boolean contains(T element) {
        boolean z = true;
        if (element == null) {
            return false;
        }
        if (this.comparator.compare(element, this.minimum) <= -1 || this.comparator.compare(element, this.maximum) >= 1) {
            z = false;
        }
        return z;
    }

    public boolean isAfter(T element) {
        if (element != null && this.comparator.compare(element, this.minimum) < 0) {
            return true;
        }
        return false;
    }

    public boolean isStartedBy(T element) {
        if (element != null && this.comparator.compare(element, this.minimum) == 0) {
            return true;
        }
        return false;
    }

    public boolean isEndedBy(T element) {
        if (element != null && this.comparator.compare(element, this.maximum) == 0) {
            return true;
        }
        return false;
    }

    public boolean isBefore(T element) {
        if (element != null && this.comparator.compare(element, this.maximum) > 0) {
            return true;
        }
        return false;
    }

    public int elementCompareTo(T element) {
        if (element == null) {
            throw new NullPointerException("Element is null");
        } else if (isAfter(element)) {
            return -1;
        } else {
            if (isBefore(element)) {
                return 1;
            }
            return 0;
        }
    }

    public boolean containsRange(Range<T> otherRange) {
        if (otherRange != null && contains(otherRange.minimum) && contains(otherRange.maximum)) {
            return true;
        }
        return false;
    }

    public boolean isAfterRange(Range<T> otherRange) {
        if (otherRange == null) {
            return false;
        }
        return isAfter(otherRange.maximum);
    }

    public boolean isOverlappedBy(Range<T> otherRange) {
        if (otherRange == null) {
            return false;
        }
        if (otherRange.contains(this.minimum) || otherRange.contains(this.maximum) || contains(otherRange.minimum)) {
            return true;
        }
        return false;
    }

    public boolean isBeforeRange(Range<T> otherRange) {
        if (otherRange == null) {
            return false;
        }
        return isBefore(otherRange.minimum);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Range<T> range = (Range) obj;
        if (this.minimum.equals(range.minimum) && this.maximum.equals(range.maximum)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (this.hashCode != 0) {
            return result;
        }
        result = ((((getClass().hashCode() + 629) * 37) + this.minimum.hashCode()) * 37) + this.maximum.hashCode();
        this.hashCode = result;
        return result;
    }

    public String toString() {
        String result = this.toString;
        if (result != null) {
            return result;
        }
        StringBuilder buf = new StringBuilder(32);
        buf.append('[');
        buf.append(this.minimum);
        buf.append("..");
        buf.append(this.maximum);
        buf.append(']');
        result = buf.toString();
        this.toString = result;
        return result;
    }

    public String toString(String format) {
        return String.format(format, new Object[]{this.minimum, this.maximum, this.comparator});
    }
}
