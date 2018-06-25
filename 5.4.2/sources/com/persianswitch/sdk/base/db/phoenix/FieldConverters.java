package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class FieldConverters {

    public static class BigDecimalConverter implements FieldConverter<BigDecimal> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.TEXT;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10510b(cursor, i);
        }

        /* renamed from: a */
        public void m10509a(String str, BigDecimal bigDecimal, ContentValues contentValues) {
            contentValues.put(str, bigDecimal.toString());
        }

        /* renamed from: b */
        public BigDecimal m10510b(Cursor cursor, int i) {
            return cursor.isNull(i) ? null : new BigDecimal(cursor.getString(i));
        }
    }

    public static class BigIntegerConverter implements FieldConverter<BigInteger> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.TEXT;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10515b(cursor, i);
        }

        /* renamed from: a */
        public void m10514a(String str, BigInteger bigInteger, ContentValues contentValues) {
            contentValues.put(str, bigInteger.toString());
        }

        /* renamed from: b */
        public BigInteger m10515b(Cursor cursor, int i) {
            return cursor.isNull(i) ? null : new BigInteger(cursor.getString(i));
        }
    }

    public static class BlobArrayConverter implements FieldConverter<byte[]> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.BLOB;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10520b(cursor, i);
        }

        /* renamed from: a */
        public void m10519a(String str, byte[] bArr, ContentValues contentValues) {
            contentValues.put(str, bArr);
        }

        /* renamed from: b */
        public byte[] m10520b(Cursor cursor, int i) {
            return cursor.getBlob(i);
        }
    }

    public static class BooleanConverter implements FieldConverter<Boolean> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10525b(cursor, i);
        }

        /* renamed from: a */
        public void m10523a(String str, Boolean bool, ContentValues contentValues) {
            contentValues.put(str, Integer.valueOf(bool.booleanValue() ? 1 : 0));
        }

        /* renamed from: b */
        public Boolean m10525b(Cursor cursor, int i) {
            boolean z = true;
            if (cursor.getInt(i) != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        }
    }

    public static class ByteConverter implements FieldConverter<Byte> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10530b(cursor, i);
        }

        /* renamed from: a */
        public void m10528a(String str, Byte b, ContentValues contentValues) {
            contentValues.put(str, b);
        }

        /* renamed from: b */
        public Byte m10530b(Cursor cursor, int i) {
            return Byte.valueOf((byte) cursor.getInt(i));
        }
    }

    public static class DateConverter implements FieldConverter<Date> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10535b(cursor, i);
        }

        /* renamed from: a */
        public void m10534a(String str, Date date, ContentValues contentValues) {
            contentValues.put(str, Long.valueOf(date.getTime()));
        }

        /* renamed from: b */
        public Date m10535b(Cursor cursor, int i) {
            return cursor.isNull(i) ? null : new Date(cursor.getLong(i));
        }
    }

    public static class DoubleConverter implements FieldConverter<Double> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.REAL;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10540b(cursor, i);
        }

        /* renamed from: a */
        public void m10538a(String str, Double d, ContentValues contentValues) {
            contentValues.put(str, d);
        }

        /* renamed from: b */
        public Double m10540b(Cursor cursor, int i) {
            return Double.valueOf(cursor.getDouble(i));
        }
    }

    public static class FloatConverter implements FieldConverter<Float> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.REAL;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10545b(cursor, i);
        }

        /* renamed from: a */
        public void m10543a(String str, Float f, ContentValues contentValues) {
            contentValues.put(str, f);
        }

        /* renamed from: b */
        public Float m10545b(Cursor cursor, int i) {
            return Float.valueOf(cursor.getFloat(i));
        }
    }

    public static class IntegerConverter implements FieldConverter<Integer> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10550b(cursor, i);
        }

        /* renamed from: a */
        public void m10548a(String str, Integer num, ContentValues contentValues) {
            contentValues.put(str, num);
        }

        /* renamed from: b */
        public Integer m10550b(Cursor cursor, int i) {
            return Integer.valueOf(cursor.getInt(i));
        }
    }

    public static class LongConverter implements FieldConverter<Long> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10555b(cursor, i);
        }

        /* renamed from: a */
        public void m10553a(String str, Long l, ContentValues contentValues) {
            contentValues.put(str, l);
        }

        /* renamed from: b */
        public Long m10555b(Cursor cursor, int i) {
            return Long.valueOf(cursor.getLong(i));
        }
    }

    public static class ShortConverter implements FieldConverter<Short> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.INTEGER;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10560b(cursor, i);
        }

        /* renamed from: a */
        public void m10559a(String str, Short sh, ContentValues contentValues) {
            contentValues.put(str, sh);
        }

        /* renamed from: b */
        public Short m10560b(Cursor cursor, int i) {
            return Short.valueOf(cursor.getShort(i));
        }
    }

    public static class StringConverter implements FieldConverter<String> {
        /* renamed from: a */
        public ColumnType mo3234a() {
            return ColumnType.TEXT;
        }

        /* renamed from: a */
        public /* synthetic */ Object mo3235a(Cursor cursor, int i) {
            return m10565b(cursor, i);
        }

        /* renamed from: a */
        public void m10564a(String str, String str2, ContentValues contentValues) {
            contentValues.put(str, str2);
        }

        /* renamed from: b */
        public String m10565b(Cursor cursor, int i) {
            return cursor.getString(i);
        }
    }

    /* renamed from: a */
    public static <T> FieldConverter<T> m10566a(Class<T> cls) {
        if (cls == byte[].class) {
            return new BlobArrayConverter();
        }
        if (cls == Boolean.class) {
            return new BooleanConverter();
        }
        if (cls == String.class) {
            return new StringConverter();
        }
        if (cls == Byte.class) {
            return new ByteConverter();
        }
        if (cls == Integer.class) {
            return new IntegerConverter();
        }
        if (cls == Short.class) {
            return new ShortConverter();
        }
        if (cls == Long.class) {
            return new LongConverter();
        }
        if (cls == Float.class) {
            return new FloatConverter();
        }
        if (cls == Double.class) {
            return new DoubleConverter();
        }
        if (cls == Date.class) {
            return new DateConverter();
        }
        if (cls == BigDecimal.class) {
            return new BigDecimalConverter();
        }
        if (cls == BigInteger.class) {
            return new BigDecimalConverter();
        }
        throw new RuntimeException("You must implement your own FieldConverter for type" + cls.getName());
    }
}
