package com.google.android.gms.internal;

final class zzfkh {
    static String zzbd(zzfgs zzfgs) {
        zzfkj zzfki = new zzfki(zzfgs);
        StringBuilder stringBuilder = new StringBuilder(zzfki.size());
        for (int i = 0; i < zzfki.size(); i++) {
            byte zzld = zzfki.zzld(i);
            switch (zzld) {
                case (byte) 7:
                    stringBuilder.append("\\a");
                    break;
                case (byte) 8:
                    stringBuilder.append("\\b");
                    break;
                case (byte) 9:
                    stringBuilder.append("\\t");
                    break;
                case (byte) 10:
                    stringBuilder.append("\\n");
                    break;
                case (byte) 11:
                    stringBuilder.append("\\v");
                    break;
                case (byte) 12:
                    stringBuilder.append("\\f");
                    break;
                case (byte) 13:
                    stringBuilder.append("\\r");
                    break;
                case (byte) 34:
                    stringBuilder.append("\\\"");
                    break;
                case (byte) 39:
                    stringBuilder.append("\\'");
                    break;
                case (byte) 92:
                    stringBuilder.append("\\\\");
                    break;
                default:
                    if (zzld >= (byte) 32 && zzld <= (byte) 126) {
                        stringBuilder.append((char) zzld);
                        break;
                    }
                    stringBuilder.append('\\');
                    stringBuilder.append((char) (((zzld >>> 6) & 3) + 48));
                    stringBuilder.append((char) (((zzld >>> 3) & 7) + 48));
                    stringBuilder.append((char) ((zzld & 7) + 48));
                    break;
            }
        }
        return stringBuilder.toString();
    }
}
