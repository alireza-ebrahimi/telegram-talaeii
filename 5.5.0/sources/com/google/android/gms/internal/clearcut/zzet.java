package com.google.android.gms.internal.clearcut;

final class zzet {
    static String zzc(zzbb zzbb) {
        zzev zzeu = new zzeu(zzbb);
        StringBuilder stringBuilder = new StringBuilder(zzeu.size());
        for (int i = 0; i < zzeu.size(); i++) {
            byte zzj = zzeu.zzj(i);
            switch (zzj) {
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
                    if (zzj >= (byte) 32 && zzj <= (byte) 126) {
                        stringBuilder.append((char) zzj);
                        break;
                    }
                    stringBuilder.append('\\');
                    stringBuilder.append((char) (((zzj >>> 6) & 3) + 48));
                    stringBuilder.append((char) (((zzj >>> 3) & 7) + 48));
                    stringBuilder.append((char) ((zzj & 7) + 48));
                    break;
            }
        }
        return stringBuilder.toString();
    }
}
