package org.apache.commons.lang3;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class Validate {
    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "The validated class can not be converted to the %s class";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "The validated object is not an instance of %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";

    public static void isTrue(boolean expression, String message, long value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, new Object[]{Long.valueOf(value)}));
        }
    }

    public static void isTrue(boolean expression, String message, double value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, new Object[]{new Double(value)}));
        }
    }

    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    public static <T> T notNull(T object) {
        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE, new Object[0]);
    }

    public static <T> T notNull(T object, String message, Object... values) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(String.format(message, values));
    }

    public static <T> T[] notEmpty(T[] array, String message, Object... values) {
        if (array == null) {
            throw new NullPointerException(String.format(message, values));
        } else if (array.length != 0) {
            return array;
        } else {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty((Object[]) array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message, Object... values) {
        if (collection == null) {
            throw new NullPointerException(String.format(message, values));
        } else if (collection.size() != 0) {
            return collection;
        } else {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T extends Collection<?>> T notEmpty(T collection) {
        return notEmpty((Collection) collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Map<?, ?>> T notEmpty(T map, String message, Object... values) {
        if (map == null) {
            throw new NullPointerException(String.format(message, values));
        } else if (map.size() != 0) {
            return map;
        } else {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T extends Map<?, ?>> T notEmpty(T map) {
        return notEmpty((Map) map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notEmpty(T chars, String message, Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        } else if (chars.length() != 0) {
            return chars;
        } else {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T extends CharSequence> T notEmpty(T chars) {
        return notEmpty((CharSequence) chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notBlank(T chars, String message, Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        } else if (!StringUtils.isBlank(chars)) {
            return chars;
        } else {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T extends CharSequence> T notBlank(T chars) {
        return notBlank(chars, DEFAULT_NOT_BLANK_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] noNullElements(T[] array, String message, Object... values) {
        notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException(String.format(message, ArrayUtils.add(values, Integer.valueOf(i))));
            }
        }
        return array;
    }

    public static <T> T[] noNullElements(T[] array) {
        return noNullElements((Object[]) array, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Iterable<?>> T noNullElements(T iterable, String message, Object... values) {
        notNull(iterable);
        int i = 0;
        for (Object obj : iterable) {
            if (obj == null) {
                throw new IllegalArgumentException(String.format(message, ArrayUtils.addAll(values, Integer.valueOf(i))));
            }
            i++;
        }
        return iterable;
    }

    public static <T extends Iterable<?>> T noNullElements(T iterable) {
        return noNullElements((Iterable) iterable, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] validIndex(T[] array, int index, String message, Object... values) {
        notNull(array);
        if (index >= 0 && index < array.length) {
            return array;
        }
        throw new IndexOutOfBoundsException(String.format(message, values));
    }

    public static <T> T[] validIndex(T[] array, int index) {
        return validIndex((Object[]) array, index, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, Integer.valueOf(index));
    }

    public static <T extends Collection<?>> T validIndex(T collection, int index, String message, Object... values) {
        notNull(collection);
        if (index >= 0 && index < collection.size()) {
            return collection;
        }
        throw new IndexOutOfBoundsException(String.format(message, values));
    }

    public static <T extends Collection<?>> T validIndex(T collection, int index) {
        return validIndex((Collection) collection, index, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, Integer.valueOf(index));
    }

    public static <T extends CharSequence> T validIndex(T chars, int index, String message, Object... values) {
        notNull(chars);
        if (index >= 0 && index < chars.length()) {
            return chars;
        }
        throw new IndexOutOfBoundsException(String.format(message, values));
    }

    public static <T extends CharSequence> T validIndex(T chars, int index) {
        return validIndex((CharSequence) chars, index, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, Integer.valueOf(index));
    }

    public static void validState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException(DEFAULT_VALID_STATE_EX_MESSAGE);
        }
    }

    public static void validState(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, values));
        }
    }

    public static void matchesPattern(CharSequence input, String pattern) {
        if (!Pattern.matches(pattern, input)) {
            throw new IllegalArgumentException(String.format(DEFAULT_MATCHES_PATTERN_EX, new Object[]{input, pattern}));
        }
    }

    public static void matchesPattern(CharSequence input, String pattern, String message, Object... values) {
        if (!Pattern.matches(pattern, input)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> void inclusiveBetween(T start, T end, Comparable<T> value) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, new Object[]{value, start, end}));
        }
    }

    public static <T> void inclusiveBetween(T start, T end, Comparable<T> value, String message, Object... values) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> void exclusiveBetween(T start, T end, Comparable<T> value) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, new Object[]{value, start, end}));
        }
    }

    public static <T> void exclusiveBetween(T start, T end, Comparable<T> value, String message, Object... values) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj) {
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, new Object[]{type.getName()}));
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message, Object... values) {
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isAssignableFrom(Class<?> superType, Class<?> type) {
        if (!superType.isAssignableFrom(type)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE, new Object[]{superType.getName()}));
        }
    }

    public static void isAssignableFrom(Class<?> superType, Class<?> type, String message, Object... values) {
        if (!superType.isAssignableFrom(type)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}
