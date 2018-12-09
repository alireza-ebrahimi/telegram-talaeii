package com.persianswitch.sdk.base.jsevaluator;

public class JsFunctionCallFormatter {
    /* renamed from: a */
    public static String m10645a(Object obj) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        if (obj instanceof String) {
            str = ((String) obj).replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
            return String.format("\"%s\"", new Object[]{str});
        }
        try {
            Double.parseDouble(obj.toString());
            return obj.toString();
        } catch (NumberFormatException e) {
            return str;
        }
    }

    /* renamed from: a */
    public static String m10646a(String str, Object... objArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : objArr) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(m10645a(obj));
        }
        return String.format("%s(%s)", new Object[]{str, stringBuilder});
    }
}
