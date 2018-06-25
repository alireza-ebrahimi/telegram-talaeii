package com.persianswitch.sdk.base.jsevaluator;

public class JsFunctionCallFormatter {
    public static String paramToString(Object param) {
        String str = "";
        if (param instanceof String) {
            str = ((String) param).replace("\\", "\\\\").replace("\"", "\\\"").replace(LogCollector.LINE_SEPARATOR, "\\n");
            return String.format("\"%s\"", new Object[]{str});
        }
        try {
            double d = Double.parseDouble(param.toString());
            return param.toString();
        } catch (NumberFormatException e) {
            return str;
        }
    }

    public static String toString(String functionName, Object... args) {
        StringBuilder paramsStr = new StringBuilder();
        for (Object param : args) {
            if (paramsStr.length() > 0) {
                paramsStr.append(", ");
            }
            paramsStr.append(paramToString(param));
        }
        return String.format("%s(%s)", new Object[]{functionName, paramsStr});
    }
}
