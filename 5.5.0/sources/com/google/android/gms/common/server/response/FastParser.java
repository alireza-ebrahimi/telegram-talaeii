package com.google.android.gms.common.server.response;

import android.util.Log;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import org.telegram.messenger.exoplayer2.C3446C;

public class FastParser<T extends FastJsonResponse> {
    private static final char[] zzwv = new char[]{'u', 'l', 'l'};
    private static final char[] zzww = new char[]{'r', 'u', 'e'};
    private static final char[] zzwx = new char[]{'r', 'u', 'e', '\"'};
    private static final char[] zzwy = new char[]{'a', 'l', 's', 'e'};
    private static final char[] zzwz = new char[]{'a', 'l', 's', 'e', '\"'};
    private static final char[] zzxa = new char[]{'\n'};
    private static final zza<Integer> zzxc = new zza();
    private static final zza<Long> zzxd = new zzb();
    private static final zza<Float> zzxe = new zzc();
    private static final zza<Double> zzxf = new zzd();
    private static final zza<Boolean> zzxg = new zze();
    private static final zza<String> zzxh = new zzf();
    private static final zza<BigInteger> zzxi = new zzg();
    private static final zza<BigDecimal> zzxj = new zzh();
    private final char[] zzwq = new char[1];
    private final char[] zzwr = new char[32];
    private final char[] zzws = new char[1024];
    private final StringBuilder zzwt = new StringBuilder(32);
    private final StringBuilder zzwu = new StringBuilder(1024);
    private final Stack<Integer> zzxb = new Stack();

    public static class ParseException extends Exception {
        public ParseException(String str) {
            super(str);
        }

        public ParseException(String str, Throwable th) {
            super(str, th);
        }

        public ParseException(Throwable th) {
            super(th);
        }
    }

    private interface zza<O> {
        O zzh(FastParser fastParser, BufferedReader bufferedReader);
    }

    private final int zza(BufferedReader bufferedReader, char[] cArr) {
        char zzj = zzj(bufferedReader);
        if (zzj == '\u0000') {
            throw new ParseException("Unexpected EOF");
        } else if (zzj == ',') {
            throw new ParseException("Missing value");
        } else if (zzj == 'n') {
            zzb(bufferedReader, zzwv);
            return 0;
        } else {
            int i;
            bufferedReader.mark(1024);
            if (zzj == '\"') {
                zzj = '\u0000';
                int i2 = 0;
                while (i2 < cArr.length && bufferedReader.read(cArr, i2, 1) != -1) {
                    char c = cArr[i2];
                    if (Character.isISOControl(c)) {
                        throw new ParseException("Unexpected control character while reading string");
                    } else if (c == '\"' && zzj == '\u0000') {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i2 + 1));
                        return i2;
                    } else {
                        zzj = c == '\\' ? zzj == '\u0000' ? '\u0001' : '\u0000' : '\u0000';
                        i2++;
                    }
                }
                i = i2;
            } else {
                cArr[0] = zzj;
                i = 1;
                while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                    if (cArr[i] == '}' || cArr[i] == ',' || Character.isWhitespace(cArr[i]) || cArr[i] == ']') {
                        bufferedReader.reset();
                        bufferedReader.skip((long) (i - 1));
                        cArr[i] = '\u0000';
                        return i;
                    }
                    i++;
                }
            }
            if (i == cArr.length) {
                throw new ParseException("Absurdly long value");
            }
            throw new ParseException("Unexpected EOF");
        }
    }

    private final String zza(BufferedReader bufferedReader) {
        String str = null;
        this.zzxb.push(Integer.valueOf(2));
        char zzj = zzj(bufferedReader);
        switch (zzj) {
            case '\"':
                this.zzxb.push(Integer.valueOf(3));
                str = zzb(bufferedReader, this.zzwr, this.zzwt, null);
                zzk(3);
                if (zzj(bufferedReader) != ':') {
                    throw new ParseException("Expected key/value separator");
                }
                break;
            case ']':
                zzk(2);
                zzk(1);
                zzk(5);
                break;
            case '}':
                zzk(2);
                break;
            default:
                throw new ParseException("Unexpected token: " + zzj);
        }
        return str;
    }

    private final String zza(BufferedReader bufferedReader, char[] cArr, StringBuilder stringBuilder, char[] cArr2) {
        switch (zzj(bufferedReader)) {
            case '\"':
                return zzb(bufferedReader, cArr, stringBuilder, cArr2);
            case 'n':
                zzb(bufferedReader, zzwv);
                return null;
            default:
                throw new ParseException("Expected string");
        }
    }

    private final <T extends FastJsonResponse> ArrayList<T> zza(BufferedReader bufferedReader, Field<?, ?> field) {
        ArrayList<T> arrayList = new ArrayList();
        char zzj = zzj(bufferedReader);
        switch (zzj) {
            case ']':
                zzk(5);
                return arrayList;
            case 'n':
                zzb(bufferedReader, zzwv);
                zzk(5);
                return null;
            case '{':
                this.zzxb.push(Integer.valueOf(1));
                while (true) {
                    try {
                        FastJsonResponse newConcreteTypeInstance = field.newConcreteTypeInstance();
                        if (!zza(bufferedReader, newConcreteTypeInstance)) {
                            return arrayList;
                        }
                        arrayList.add(newConcreteTypeInstance);
                        zzj = zzj(bufferedReader);
                        switch (zzj) {
                            case ',':
                                if (zzj(bufferedReader) != '{') {
                                    throw new ParseException("Expected start of next object in array");
                                }
                                this.zzxb.push(Integer.valueOf(1));
                            case ']':
                                zzk(5);
                                return arrayList;
                            default:
                                throw new ParseException("Unexpected token: " + zzj);
                        }
                    } catch (Throwable e) {
                        throw new ParseException("Error instantiating inner object", e);
                    } catch (Throwable e2) {
                        throw new ParseException("Error instantiating inner object", e2);
                    }
                }
            default:
                throw new ParseException("Unexpected token: " + zzj);
        }
    }

    private final <O> ArrayList<O> zza(BufferedReader bufferedReader, zza<O> zza) {
        char zzj = zzj(bufferedReader);
        if (zzj != 'n') {
            if (zzj == '[') {
                this.zzxb.push(Integer.valueOf(5));
                ArrayList<O> arrayList = new ArrayList();
                while (true) {
                    bufferedReader.mark(1024);
                    switch (zzj(bufferedReader)) {
                        case '\u0000':
                            throw new ParseException("Unexpected EOF");
                        case ',':
                            break;
                        case ']':
                            zzk(5);
                            return arrayList;
                        default:
                            bufferedReader.reset();
                            arrayList.add(zza.zzh(this, bufferedReader));
                            break;
                    }
                }
            }
            throw new ParseException("Expected start of array");
        }
        zzb(bufferedReader, zzwv);
        return null;
    }

    private final boolean zza(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) {
        Map fieldMappings = fastJsonResponse.getFieldMappings();
        Object zza = zza(bufferedReader);
        if (zza == null) {
            zzk(1);
            return false;
        }
        while (zza != null) {
            Field field = (Field) fieldMappings.get(zza);
            if (field == null) {
                zza = zzb(bufferedReader);
            } else {
                this.zzxb.push(Integer.valueOf(4));
                char zzj;
                switch (field.getTypeIn()) {
                    case 0:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setInteger(field, zzd(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setIntegers(field, zza(bufferedReader, zzxc));
                        break;
                    case 1:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setBigInteger(field, zzf(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setBigIntegers(field, zza(bufferedReader, zzxi));
                        break;
                    case 2:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setLong(field, zze(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setLongs(field, zza(bufferedReader, zzxd));
                        break;
                    case 3:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setFloat(field, zzg(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setFloats(field, zza(bufferedReader, zzxe));
                        break;
                    case 4:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setDouble(field, zzh(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setDoubles(field, zza(bufferedReader, zzxf));
                        break;
                    case 5:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setBigDecimal(field, zzi(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setBigDecimals(field, zza(bufferedReader, zzxj));
                        break;
                    case 6:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setBoolean(field, zza(bufferedReader, false));
                            break;
                        }
                        fastJsonResponse.setBooleans(field, zza(bufferedReader, zzxg));
                        break;
                    case 7:
                        if (!field.isTypeInArray()) {
                            fastJsonResponse.setString(field, zzc(bufferedReader));
                            break;
                        }
                        fastJsonResponse.setStrings(field, zza(bufferedReader, zzxh));
                        break;
                    case 8:
                        fastJsonResponse.setDecodedBytes(field, Base64Utils.decode(zza(bufferedReader, this.zzws, this.zzwu, zzxa)));
                        break;
                    case 9:
                        fastJsonResponse.setDecodedBytes(field, Base64Utils.decodeUrlSafe(zza(bufferedReader, this.zzws, this.zzwu, zzxa)));
                        break;
                    case 10:
                        Map hashMap;
                        zzj = zzj(bufferedReader);
                        if (zzj != 'n') {
                            if (zzj == '{') {
                                this.zzxb.push(Integer.valueOf(1));
                                hashMap = new HashMap();
                                while (true) {
                                    switch (zzj(bufferedReader)) {
                                        case '\u0000':
                                            throw new ParseException("Unexpected EOF");
                                        case '\"':
                                            String zzb = zzb(bufferedReader, this.zzwr, this.zzwt, null);
                                            String str;
                                            String valueOf;
                                            if (zzj(bufferedReader) == ':') {
                                                if (zzj(bufferedReader) == '\"') {
                                                    hashMap.put(zzb, zzb(bufferedReader, this.zzwr, this.zzwt, null));
                                                    char zzj2 = zzj(bufferedReader);
                                                    if (zzj2 == ',') {
                                                        break;
                                                    } else if (zzj2 == '}') {
                                                        zzk(1);
                                                        break;
                                                    } else {
                                                        throw new ParseException("Unexpected character while parsing string map: " + zzj2);
                                                    }
                                                }
                                                str = "Expected String value for key ";
                                                valueOf = String.valueOf(zzb);
                                                throw new ParseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                                            }
                                            str = "No map value found for key ";
                                            valueOf = String.valueOf(zzb);
                                            throw new ParseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                                        case '}':
                                            zzk(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            throw new ParseException("Expected start of a map object");
                        }
                        zzb(bufferedReader, zzwv);
                        hashMap = null;
                        fastJsonResponse.setStringMap(field, hashMap);
                        break;
                    case 11:
                        if (field.isTypeInArray()) {
                            zzj = zzj(bufferedReader);
                            if (zzj != 'n') {
                                this.zzxb.push(Integer.valueOf(5));
                                if (zzj == '[') {
                                    fastJsonResponse.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), zza(bufferedReader, field));
                                    break;
                                }
                                throw new ParseException("Expected array start");
                            }
                            zzb(bufferedReader, zzwv);
                            fastJsonResponse.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), null);
                            break;
                        }
                        zzj = zzj(bufferedReader);
                        if (zzj == 'n') {
                            zzb(bufferedReader, zzwv);
                            fastJsonResponse.addConcreteTypeInternal(field, field.getOutputFieldName(), null);
                            break;
                        }
                        this.zzxb.push(Integer.valueOf(1));
                        if (zzj != '{') {
                            throw new ParseException("Expected start of object");
                        }
                        try {
                            FastJsonResponse newConcreteTypeInstance = field.newConcreteTypeInstance();
                            zza(bufferedReader, newConcreteTypeInstance);
                            fastJsonResponse.addConcreteTypeInternal(field, field.getOutputFieldName(), newConcreteTypeInstance);
                            break;
                        } catch (Throwable e) {
                            throw new ParseException("Error instantiating inner object", e);
                        } catch (Throwable e2) {
                            throw new ParseException("Error instantiating inner object", e2);
                        }
                    default:
                        throw new ParseException("Invalid field type " + field.getTypeIn());
                }
                zzk(4);
                zzk(2);
                char zzj3 = zzj(bufferedReader);
                switch (zzj3) {
                    case ',':
                        zza = zza(bufferedReader);
                        break;
                    case '}':
                        zza = null;
                        break;
                    default:
                        throw new ParseException("Expected end of object or field separator, but found: " + zzj3);
                }
            }
        }
        PostProcessor postProcessor = fastJsonResponse.getPostProcessor();
        if (postProcessor != null) {
            postProcessor.postProcess(fastJsonResponse);
        }
        zzk(1);
        return true;
    }

    private final boolean zza(BufferedReader bufferedReader, boolean z) {
        while (true) {
            char zzj = zzj(bufferedReader);
            switch (zzj) {
                case '\"':
                    if (z) {
                        throw new ParseException("No boolean value found in string");
                    }
                    z = true;
                case 'f':
                    zzb(bufferedReader, z ? zzwz : zzwy);
                    return false;
                case 'n':
                    zzb(bufferedReader, zzwv);
                    return false;
                case 't':
                    zzb(bufferedReader, z ? zzwx : zzww);
                    return true;
                default:
                    throw new ParseException("Unexpected token: " + zzj);
            }
        }
    }

    private final String zzb(BufferedReader bufferedReader) {
        char c;
        bufferedReader.mark(1024);
        int i;
        int i2;
        switch (zzj(bufferedReader)) {
            case '\"':
                if (bufferedReader.read(this.zzwq) != -1) {
                    c = this.zzwq[0];
                    i = 0;
                    while (true) {
                        if (c == '\"' && i == 0) {
                            break;
                        }
                        i2 = c == '\\' ? i == 0 ? 1 : 0 : 0;
                        if (bufferedReader.read(this.zzwq) == -1) {
                            throw new ParseException("Unexpected EOF while parsing string");
                        }
                        char c2 = this.zzwq[0];
                        if (Character.isISOControl(c2)) {
                            throw new ParseException("Unexpected control character while reading string");
                        }
                        char c3 = c2;
                        i = i2;
                        c = c3;
                    }
                } else {
                    throw new ParseException("Unexpected EOF while parsing string");
                }
                break;
            case ',':
                throw new ParseException("Missing value");
            case '[':
                this.zzxb.push(Integer.valueOf(5));
                bufferedReader.mark(32);
                if (zzj(bufferedReader) != ']') {
                    bufferedReader.reset();
                    i = 1;
                    i2 = 0;
                    int i3 = 0;
                    while (i > 0) {
                        char zzj = zzj(bufferedReader);
                        if (zzj == '\u0000') {
                            throw new ParseException("Unexpected EOF while parsing array");
                        } else if (Character.isISOControl(zzj)) {
                            throw new ParseException("Unexpected control character while reading array");
                        } else {
                            int i4;
                            if (zzj == '\"' && i3 == 0) {
                                i4 = i2 == 0 ? 1 : 0;
                            } else {
                                i4 = i2;
                            }
                            i2 = (zzj == '[' && i4 == 0) ? i + 1 : i;
                            i = (zzj == ']' && i4 == 0) ? i2 - 1 : i2;
                            if (zzj != '\\' || i4 == 0) {
                                i2 = i4;
                                i3 = 0;
                            } else {
                                i3 = i3 == 0 ? 1 : 0;
                                i2 = i4;
                            }
                        }
                    }
                    zzk(5);
                    break;
                }
                zzk(5);
                break;
                break;
            case '{':
                this.zzxb.push(Integer.valueOf(1));
                bufferedReader.mark(32);
                c = zzj(bufferedReader);
                if (c == '}') {
                    zzk(1);
                    break;
                } else if (c == '\"') {
                    bufferedReader.reset();
                    zza(bufferedReader);
                    do {
                    } while (zzb(bufferedReader) != null);
                    zzk(1);
                    break;
                } else {
                    throw new ParseException("Unexpected token " + c);
                }
            default:
                bufferedReader.reset();
                zza(bufferedReader, this.zzws);
                break;
        }
        c = zzj(bufferedReader);
        switch (c) {
            case ',':
                zzk(2);
                return zza(bufferedReader);
            case '}':
                zzk(2);
                return null;
            default:
                throw new ParseException("Unexpected token " + c);
        }
    }

    private static String zzb(BufferedReader bufferedReader, char[] cArr, StringBuilder stringBuilder, char[] cArr2) {
        int i;
        stringBuilder.setLength(0);
        bufferedReader.mark(cArr.length);
        int i2 = 0;
        int i3 = 0;
        loop0:
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                i = 0;
                while (i < read) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        int i4;
                        if (cArr2 != null) {
                            for (char c2 : cArr2) {
                                if (c2 == c) {
                                    i4 = 1;
                                    break;
                                }
                            }
                        }
                        i4 = 0;
                        if (i4 == 0) {
                            throw new ParseException("Unexpected control character while reading string");
                        }
                    }
                    if (c == '\"' && i3 == 0) {
                        break loop0;
                    }
                    if (c == '\\') {
                        i3 = i3 == 0 ? 1 : 0;
                        i2 = 1;
                    } else {
                        i3 = 0;
                    }
                    i++;
                }
                stringBuilder.append(cArr, 0, read);
                bufferedReader.mark(cArr.length);
            } else {
                throw new ParseException("Unexpected EOF while parsing string");
            }
        }
        stringBuilder.append(cArr, 0, i);
        bufferedReader.reset();
        bufferedReader.skip((long) (i + 1));
        return i2 != 0 ? JsonUtils.unescapeString(stringBuilder.toString()) : stringBuilder.toString();
    }

    private final void zzb(BufferedReader bufferedReader, char[] cArr) {
        int i = 0;
        while (i < cArr.length) {
            int read = bufferedReader.read(this.zzwr, 0, cArr.length - i);
            if (read == -1) {
                throw new ParseException("Unexpected EOF");
            }
            for (int i2 = 0; i2 < read; i2++) {
                if (cArr[i2 + i] != this.zzwr[i2]) {
                    throw new ParseException("Unexpected character");
                }
            }
            i += read;
        }
    }

    private final String zzc(BufferedReader bufferedReader) {
        return zza(bufferedReader, this.zzwr, this.zzwt, null);
    }

    private final int zzd(BufferedReader bufferedReader) {
        int i = 0;
        int zza = zza(bufferedReader, this.zzws);
        if (zza == 0) {
            return 0;
        }
        char[] cArr = this.zzws;
        if (zza > 0) {
            int i2;
            int i3;
            int i4;
            if (cArr[0] == '-') {
                i2 = Integer.MIN_VALUE;
                i3 = 1;
                i4 = 1;
            } else {
                i2 = -2147483647;
                i3 = 0;
                i4 = 0;
            }
            if (i4 < zza) {
                i = i4 + 1;
                i4 = Character.digit(cArr[i4], 10);
                if (i4 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                int i5 = i;
                i = -i4;
                i4 = i5;
            }
            while (i4 < zza) {
                int i6 = i4 + 1;
                i4 = Character.digit(cArr[i4], 10);
                if (i4 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (i < -214748364) {
                    throw new ParseException("Number too large");
                } else {
                    i *= 10;
                    if (i < i2 + i4) {
                        throw new ParseException("Number too large");
                    }
                    i -= i4;
                    i4 = i6;
                }
            }
            if (i3 == 0) {
                return -i;
            }
            if (i4 > 1) {
                return i;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private final long zze(BufferedReader bufferedReader) {
        long j = 0;
        int zza = zza(bufferedReader, this.zzws);
        if (zza == 0) {
            return 0;
        }
        char[] cArr = this.zzws;
        if (zza > 0) {
            long j2;
            int i;
            int i2;
            if (cArr[0] == '-') {
                j2 = Long.MIN_VALUE;
                i = 1;
                i2 = 1;
            } else {
                i = 0;
                j2 = C3446C.TIME_UNSET;
                i2 = 0;
            }
            if (i2 < zza) {
                int i3 = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                int i4 = i3;
                j = (long) (-digit);
                i2 = i4;
            }
            while (i2 < zza) {
                int i5 = i2 + 1;
                i2 = Character.digit(cArr[i2], 10);
                if (i2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if (j < -922337203685477580L) {
                    throw new ParseException("Number too large");
                } else {
                    j *= 10;
                    if (j < ((long) i2) + j2) {
                        throw new ParseException("Number too large");
                    }
                    j -= (long) i2;
                    i2 = i5;
                }
            }
            if (i == 0) {
                return -j;
            }
            if (i2 > 1) {
                return j;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private final BigInteger zzf(BufferedReader bufferedReader) {
        int zza = zza(bufferedReader, this.zzws);
        return zza == 0 ? null : new BigInteger(new String(this.zzws, 0, zza));
    }

    private final float zzg(BufferedReader bufferedReader) {
        int zza = zza(bufferedReader, this.zzws);
        return zza == 0 ? BitmapDescriptorFactory.HUE_RED : Float.parseFloat(new String(this.zzws, 0, zza));
    }

    private final double zzh(BufferedReader bufferedReader) {
        int zza = zza(bufferedReader, this.zzws);
        return zza == 0 ? 0.0d : Double.parseDouble(new String(this.zzws, 0, zza));
    }

    private final BigDecimal zzi(BufferedReader bufferedReader) {
        int zza = zza(bufferedReader, this.zzws);
        return zza == 0 ? null : new BigDecimal(new String(this.zzws, 0, zza));
    }

    private final char zzj(BufferedReader bufferedReader) {
        if (bufferedReader.read(this.zzwq) == -1) {
            return '\u0000';
        }
        while (Character.isWhitespace(this.zzwq[0])) {
            if (bufferedReader.read(this.zzwq) == -1) {
                return '\u0000';
            }
        }
        return this.zzwq[0];
    }

    private final void zzk(int i) {
        if (this.zzxb.isEmpty()) {
            throw new ParseException("Expected state " + i + " but had empty stack");
        }
        int intValue = ((Integer) this.zzxb.pop()).intValue();
        if (intValue != i) {
            throw new ParseException("Expected state " + i + " but had " + intValue);
        }
    }

    public void parse(InputStream inputStream, T t) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            this.zzxb.push(Integer.valueOf(0));
            char zzj = zzj(bufferedReader);
            switch (zzj) {
                case '\u0000':
                    throw new ParseException("No data to parse");
                case '[':
                    this.zzxb.push(Integer.valueOf(5));
                    Map fieldMappings = t.getFieldMappings();
                    if (fieldMappings.size() == 1) {
                        Field field = (Field) ((Entry) fieldMappings.entrySet().iterator().next()).getValue();
                        t.addConcreteTypeArrayInternal(field, field.getOutputFieldName(), zza(bufferedReader, field));
                        break;
                    }
                    throw new ParseException("Object array response class must have a single Field");
                case '{':
                    this.zzxb.push(Integer.valueOf(1));
                    zza(bufferedReader, (FastJsonResponse) t);
                    break;
                default:
                    throw new ParseException("Unexpected token: " + zzj);
            }
            zzk(0);
            try {
                bufferedReader.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        } catch (Throwable e2) {
            throw new ParseException(e2);
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        }
    }

    public void parse(String str, T t) {
        InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        try {
            parse(byteArrayInputStream, (FastJsonResponse) t);
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close the input stream while parsing.");
            }
        }
    }
}
