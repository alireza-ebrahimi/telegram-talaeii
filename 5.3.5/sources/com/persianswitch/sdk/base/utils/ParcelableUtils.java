package com.persianswitch.sdk.base.utils;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelableUtils {
    public static void write(Parcel dest, String string) {
        dest.writeByte((byte) (string == null ? 0 : 1));
        if (string != null) {
            dest.writeString(string);
        }
    }

    public static String readString(Parcel source) {
        if (source.readByte() == (byte) 1) {
            return source.readString();
        }
        return null;
    }

    public static void write(Parcel dest, Parcelable parcelable, int flags) {
        dest.writeByte((byte) (parcelable == null ? 0 : 1));
        if (parcelable != null) {
            dest.writeParcelable(parcelable, flags);
        }
    }

    public static <T extends Parcelable> T readParcelable(Parcel source) {
        if (source.readByte() == (byte) 1) {
            return source.readParcelable(null);
        }
        return null;
    }

    public static void write(Parcel dest, Map<String, String> strings) {
        if (strings == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(strings.keySet().size());
        for (String key : strings.keySet()) {
            dest.writeString(key);
            dest.writeString((String) strings.get(key));
        }
    }

    public static Map<String, String> readStringMap(Parcel source) {
        int numKeys = source.readInt();
        if (numKeys == -1) {
            return null;
        }
        Map<String, String> map = new HashMap();
        for (int i = 0; i < numKeys; i++) {
            map.put(source.readString(), source.readString());
        }
        return map;
    }

    public static <T extends Parcelable> void write(Parcel dest, Map<String, T> objects, int flags) {
        if (objects == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(objects.keySet().size());
        for (String key : objects.keySet()) {
            dest.writeString(key);
            dest.writeParcelable((Parcelable) objects.get(key), flags);
        }
    }

    public static <T extends Parcelable> Map<String, T> readParcelableMap(Parcel source) {
        int numKeys = source.readInt();
        if (numKeys == -1) {
            return null;
        }
        Map<String, T> map = new HashMap();
        for (int i = 0; i < numKeys; i++) {
            map.put(source.readString(), source.readParcelable(null));
        }
        return map;
    }

    public static void write(Parcel dest, URL url) {
        dest.writeString(url.toExternalForm());
    }

    public static URL readURL(Parcel source) {
        try {
            return new URL(source.readString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void write(Parcel dest, Date date) {
        dest.writeByte((byte) (date == null ? 0 : 1));
        if (date != null) {
            dest.writeLong(date.getTime());
        }
    }

    public static Date readDate(Parcel source) {
        if (source.readByte() == (byte) 1) {
            return new Date(source.readLong());
        }
        return null;
    }

    public static <T extends Enum<T>> void write(Parcel dest, Enum<T> enu) {
        if (enu == null) {
            dest.writeString("");
        } else {
            dest.writeString(enu.name());
        }
    }

    public static <T extends Enum<T>> T readEnum(Parcel dest, Class<T> clazz) {
        String name = dest.readString();
        if ("".equals(name)) {
            return null;
        }
        return Enum.valueOf(clazz, name);
    }

    public static void write(Parcel dest, boolean bool) {
        dest.writeByte((byte) (bool ? 1 : 0));
    }

    public static boolean readBoolean(Parcel source) {
        return source.readByte() == (byte) 1;
    }

    public static <T extends Parcelable> void write(Parcel dest, List<T> fields, int flags) {
        if (fields == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(fields.size());
        for (T field : fields) {
            dest.writeParcelable(field, flags);
        }
    }

    public static <T extends Parcelable> List<T> readParcelableList(Parcel source) {
        int size = source.readInt();
        if (size == -1) {
            return null;
        }
        List<T> list = new ArrayList();
        for (int i = 0; i < size; i++) {
            list.add(source.readParcelable(null));
        }
        return list;
    }
}
