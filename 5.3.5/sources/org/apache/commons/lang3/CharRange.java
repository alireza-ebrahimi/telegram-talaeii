package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class CharRange implements Iterable<Character>, Serializable {
    private static final long serialVersionUID = 8270183163158333422L;
    private final char end;
    private transient String iToString;
    private final boolean negated;
    private final char start;

    private static class CharacterIterator implements Iterator<Character> {
        private char current;
        private boolean hasNext;
        private final CharRange range;

        private CharacterIterator(CharRange r) {
            this.range = r;
            this.hasNext = true;
            if (!this.range.negated) {
                this.current = this.range.start;
            } else if (this.range.start != '\u0000') {
                this.current = '\u0000';
            } else if (this.range.end == '￿') {
                this.hasNext = false;
            } else {
                this.current = (char) (this.range.end + 1);
            }
        }

        private void prepareNext() {
            if (this.range.negated) {
                if (this.current == '￿') {
                    this.hasNext = false;
                } else if (this.current + 1 != this.range.start) {
                    this.current = (char) (this.current + 1);
                } else if (this.range.end == '￿') {
                    this.hasNext = false;
                } else {
                    this.current = (char) (this.range.end + 1);
                }
            } else if (this.current < this.range.end) {
                this.current = (char) (this.current + 1);
            } else {
                this.hasNext = false;
            }
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public Character next() {
            if (this.hasNext) {
                char cur = this.current;
                prepareNext();
                return Character.valueOf(cur);
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private CharRange(char start, char end, boolean negated) {
        if (start > end) {
            char temp = start;
            start = end;
            end = temp;
        }
        this.start = start;
        this.end = end;
        this.negated = negated;
    }

    public static CharRange is(char ch) {
        return new CharRange(ch, ch, false);
    }

    public static CharRange isNot(char ch) {
        return new CharRange(ch, ch, true);
    }

    public static CharRange isIn(char start, char end) {
        return new CharRange(start, end, false);
    }

    public static CharRange isNotIn(char start, char end) {
        return new CharRange(start, end, true);
    }

    public char getStart() {
        return this.start;
    }

    public char getEnd() {
        return this.end;
    }

    public boolean isNegated() {
        return this.negated;
    }

    public boolean contains(char ch) {
        boolean z = ch >= this.start && ch <= this.end;
        return z != this.negated;
    }

    public boolean contains(CharRange range) {
        boolean z = false;
        if (range == null) {
            throw new IllegalArgumentException("The Range must not be null");
        } else if (this.negated) {
            if (!range.negated) {
                if (range.end < this.start || range.start > this.end) {
                    z = true;
                }
                return z;
            } else if (this.start < range.start || this.end > range.end) {
                return false;
            } else {
                return true;
            }
        } else if (range.negated) {
            if (this.start == '\u0000' && this.end == '￿') {
                return true;
            }
            return false;
        } else if (this.start > range.start || this.end < range.end) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharRange)) {
            return false;
        }
        CharRange other = (CharRange) obj;
        if (this.start == other.start && this.end == other.end && this.negated == other.negated) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.negated ? 1 : 0) + ((this.end * 7) + (this.start + 83));
    }

    public String toString() {
        if (this.iToString == null) {
            StringBuilder buf = new StringBuilder(4);
            if (isNegated()) {
                buf.append('^');
            }
            buf.append(this.start);
            if (this.start != this.end) {
                buf.append('-');
                buf.append(this.end);
            }
            this.iToString = buf.toString();
        }
        return this.iToString;
    }

    public Iterator<Character> iterator() {
        return new CharacterIterator();
    }
}
