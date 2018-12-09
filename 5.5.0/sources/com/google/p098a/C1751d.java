package com.google.p098a;

import java.lang.reflect.Field;

/* renamed from: com.google.a.d */
public enum C1751d implements C1750e {
    IDENTITY {
        /* renamed from: a */
        public String mo1290a(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE {
        /* renamed from: a */
        public String mo1290a(Field field) {
            return C1751d.m8365b(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        /* renamed from: a */
        public String mo1290a(Field field) {
            return C1751d.m8365b(C1751d.m8366b(field.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        /* renamed from: a */
        public String mo1290a(Field field) {
            return C1751d.m8366b(field.getName(), "_").toLowerCase();
        }
    },
    LOWER_CASE_WITH_DASHES {
        /* renamed from: a */
        public String mo1290a(Field field) {
            return C1751d.m8366b(field.getName(), "-").toLowerCase();
        }
    };

    /* renamed from: a */
    private static String m8362a(char c, String str, int i) {
        return i < str.length() ? c + str.substring(i) : String.valueOf(c);
    }

    /* renamed from: b */
    private static String m8365b(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        char charAt = str.charAt(0);
        while (i < str.length() - 1 && !Character.isLetter(charAt)) {
            stringBuilder.append(charAt);
            i++;
            charAt = str.charAt(i);
        }
        return i == str.length() ? stringBuilder.toString() : !Character.isUpperCase(charAt) ? stringBuilder.append(C1751d.m8362a(Character.toUpperCase(charAt), str, i + 1)).toString() : str;
    }

    /* renamed from: b */
    private static String m8366b(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && stringBuilder.length() != 0) {
                stringBuilder.append(str2);
            }
            stringBuilder.append(charAt);
        }
        return stringBuilder.toString();
    }
}
