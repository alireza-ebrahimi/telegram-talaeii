package com.google.android.gms.internal.firebase_auth;

final class zzfj {
    static String zzd(zzbu zzbu) {
        zzfl zzfk = new zzfk(zzbu);
        StringBuilder stringBuilder = new StringBuilder(zzfk.size());
        for (int i = 0; i < zzfk.size(); i++) {
            byte zzk = zzfk.zzk(i);
            switch (zzk) {
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
                    if (zzk >= (byte) 32 && zzk <= (byte) 126) {
                        stringBuilder.append((char) zzk);
                        break;
                    }
                    stringBuilder.append('\\');
                    stringBuilder.append((char) (((zzk >>> 6) & 3) + 48));
                    stringBuilder.append((char) (((zzk >>> 3) & 7) + 48));
                    stringBuilder.append((char) ((zzk & 7) + 48));
                    break;
            }
        }
        return stringBuilder.toString();
    }
}
