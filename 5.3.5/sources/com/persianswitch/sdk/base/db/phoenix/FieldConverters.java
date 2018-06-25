package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class FieldConverters {

    public static class BigDecimalConverter implements FieldConverter<BigDecimal> {
        public BigDecimal fromCursor(Cursor cursor, int index) {
            return cursor.isNull(index) ? null : new BigDecimal(cursor.getString(index));
        }

        public void populateContentValues(String key, BigDecimal value, ContentValues cv) {
            cv.put(key, value.toString());
        }

        public Class<BigDecimal> getFieldType() {
            return BigDecimal.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }
    }

    public static class BigIntegerConverter implements FieldConverter<BigInteger> {
        public BigInteger fromCursor(Cursor cursor, int index) {
            return cursor.isNull(index) ? null : new BigInteger(cursor.getString(index));
        }

        public void populateContentValues(String key, BigInteger value, ContentValues cv) {
            cv.put(key, value.toString());
        }

        public Class<BigInteger> getFieldType() {
            return BigInteger.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }
    }

    public static class BlobArrayConverter implements FieldConverter<byte[]> {
        public byte[] fromCursor(Cursor cursor, int index) {
            return cursor.getBlob(index);
        }

        public void populateContentValues(String key, byte[] value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<byte[]> getFieldType() {
            return byte[].class;
        }

        public ColumnType getColumnType() {
            return ColumnType.BLOB;
        }
    }

    public static class BooleanConverter implements FieldConverter<Boolean> {
        public Boolean fromCursor(Cursor cursor, int index) {
            boolean z = true;
            if (cursor.getInt(index) != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        }

        public void populateContentValues(String key, Boolean value, ContentValues cv) {
            cv.put(key, Integer.valueOf(value.booleanValue() ? 1 : 0));
        }

        public Class<Boolean> getFieldType() {
            return Boolean.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class ByteConverter implements FieldConverter<Byte> {
        public Byte fromCursor(Cursor cursor, int index) {
            return Byte.valueOf((byte) cursor.getInt(index));
        }

        public void populateContentValues(String key, Byte value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Byte> getFieldType() {
            return Byte.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class DateConverter implements FieldConverter<Date> {
        public Date fromCursor(Cursor cursor, int index) {
            return cursor.isNull(index) ? null : new Date(cursor.getLong(index));
        }

        public void populateContentValues(String key, Date value, ContentValues cv) {
            cv.put(key, Long.valueOf(value.getTime()));
        }

        public Class<Date> getFieldType() {
            return Date.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class DoubleConverter implements FieldConverter<Double> {
        public Double fromCursor(Cursor cursor, int index) {
            return Double.valueOf(cursor.getDouble(index));
        }

        public void populateContentValues(String key, Double value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Double> getFieldType() {
            return Double.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.REAL;
        }
    }

    public static class FloatConverter implements FieldConverter<Float> {
        public Float fromCursor(Cursor cursor, int index) {
            return Float.valueOf(cursor.getFloat(index));
        }

        public void populateContentValues(String key, Float value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Float> getFieldType() {
            return Float.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.REAL;
        }
    }

    public static class IntegerConverter implements FieldConverter<Integer> {
        public Integer fromCursor(Cursor cursor, int index) {
            return Integer.valueOf(cursor.getInt(index));
        }

        public void populateContentValues(String key, Integer value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Integer> getFieldType() {
            return Integer.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class LongConverter implements FieldConverter<Long> {
        public Long fromCursor(Cursor cursor, int index) {
            return Long.valueOf(cursor.getLong(index));
        }

        public void populateContentValues(String key, Long value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Long> getFieldType() {
            return Long.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class ShortConverter implements FieldConverter<Short> {
        public Short fromCursor(Cursor cursor, int index) {
            return Short.valueOf(cursor.getShort(index));
        }

        public void populateContentValues(String key, Short value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<Short> getFieldType() {
            return Short.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.INTEGER;
        }
    }

    public static class StringConverter implements FieldConverter<String> {
        public String fromCursor(Cursor cursor, int index) {
            return cursor.getString(index);
        }

        public void populateContentValues(String key, String value, ContentValues cv) {
            cv.put(key, value);
        }

        public Class<String> getFieldType() {
            return String.class;
        }

        public ColumnType getColumnType() {
            return ColumnType.TEXT;
        }
    }

    public static <T> FieldConverter<T> byType(Class<T> tClass) {
        if (tClass == byte[].class) {
            return new BlobArrayConverter();
        }
        if (tClass == Boolean.class) {
            return new BooleanConverter();
        }
        if (tClass == String.class) {
            return new StringConverter();
        }
        if (tClass == Byte.class) {
            return new ByteConverter();
        }
        if (tClass == Integer.class) {
            return new IntegerConverter();
        }
        if (tClass == Short.class) {
            return new ShortConverter();
        }
        if (tClass == Long.class) {
            return new LongConverter();
        }
        if (tClass == Float.class) {
            return new FloatConverter();
        }
        if (tClass == Double.class) {
            return new DoubleConverter();
        }
        if (tClass == Date.class) {
            return new DateConverter();
        }
        if (tClass == BigDecimal.class) {
            return new BigDecimalConverter();
        }
        if (tClass == BigInteger.class) {
            return new BigDecimalConverter();
        }
        throw new RuntimeException("You must implement your own FieldConverter for type" + tClass.getName());
    }
}
