package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@Hide
public final class zzdng {
    private static int zza(String str, zzdnk[] zzdnkArr) {
        int i = 14;
        for (zzdnk zzdnk : zzdnkArr) {
            if (i == 14) {
                if (zzdnk.type == 9 || zzdnk.type == 2 || zzdnk.type == 6) {
                    i = zzdnk.type;
                } else if (zzdnk.type != 14) {
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 48).append("Unexpected TypedValue type: ").append(zzdnk.type).append(" for key ").append(str).toString());
                }
            } else if (zzdnk.type != i) {
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + TransportMediator.KEYCODE_MEDIA_PLAY).append("The ArrayList elements should all be the same type, but ArrayList with key ").append(str).append(" contains items of type ").append(i).append(" and ").append(zzdnk.type).toString());
            }
        }
        return i;
    }

    public static zzdnh zza(DataMap dataMap) {
        zzdni zzdni = new zzdni();
        List arrayList = new ArrayList();
        zzdni.zzlwm = zza(dataMap, arrayList);
        return new zzdnh(zzdni, arrayList);
    }

    private static zzdnk zza(List<Asset> list, Object obj) {
        zzdnk zzdnk = new zzdnk();
        if (obj == null) {
            zzdnk.type = 14;
            return zzdnk;
        }
        zzdnk.zzlwq = new zzdnl();
        if (obj instanceof String) {
            zzdnk.type = 2;
            zzdnk.zzlwq.zzlws = (String) obj;
        } else if (obj instanceof Integer) {
            zzdnk.type = 6;
            zzdnk.zzlwq.zzlww = ((Integer) obj).intValue();
        } else if (obj instanceof Long) {
            zzdnk.type = 5;
            zzdnk.zzlwq.zzlwv = ((Long) obj).longValue();
        } else if (obj instanceof Double) {
            zzdnk.type = 3;
            zzdnk.zzlwq.zzlwt = ((Double) obj).doubleValue();
        } else if (obj instanceof Float) {
            zzdnk.type = 4;
            zzdnk.zzlwq.zzlwu = ((Float) obj).floatValue();
        } else if (obj instanceof Boolean) {
            zzdnk.type = 8;
            zzdnk.zzlwq.zzlwy = ((Boolean) obj).booleanValue();
        } else if (obj instanceof Byte) {
            zzdnk.type = 7;
            zzdnk.zzlwq.zzlwx = ((Byte) obj).byteValue();
        } else if (obj instanceof byte[]) {
            zzdnk.type = 1;
            zzdnk.zzlwq.zzlwr = (byte[]) obj;
        } else if (obj instanceof String[]) {
            zzdnk.type = 11;
            zzdnk.zzlwq.zzlxb = (String[]) obj;
        } else if (obj instanceof long[]) {
            zzdnk.type = 12;
            zzdnk.zzlwq.zzlxc = (long[]) obj;
        } else if (obj instanceof float[]) {
            zzdnk.type = 15;
            zzdnk.zzlwq.zzlxd = (float[]) obj;
        } else if (obj instanceof Asset) {
            zzdnk.type = 13;
            zzdnl zzdnl = zzdnk.zzlwq;
            list.add((Asset) obj);
            zzdnl.zzlxe = (long) (list.size() - 1);
        } else if (obj instanceof DataMap) {
            zzdnk.type = 9;
            DataMap dataMap = (DataMap) obj;
            TreeSet treeSet = new TreeSet(dataMap.keySet());
            zzdnj[] zzdnjArr = new zzdnj[treeSet.size()];
            Iterator it = treeSet.iterator();
            r1 = 0;
            while (it.hasNext()) {
                r0 = (String) it.next();
                zzdnjArr[r1] = new zzdnj();
                zzdnjArr[r1].name = r0;
                zzdnjArr[r1].zzlwo = zza((List) list, dataMap.get(r0));
                r1++;
            }
            zzdnk.zzlwq.zzlwz = zzdnjArr;
        } else if (obj instanceof ArrayList) {
            zzdnk.type = 10;
            ArrayList arrayList = (ArrayList) obj;
            zzdnk[] zzdnkArr = new zzdnk[arrayList.size()];
            Object obj2 = null;
            int size = arrayList.size();
            int i = 0;
            int i2 = 14;
            while (i < size) {
                Object obj3 = arrayList.get(i);
                zzdnk zza = zza((List) list, obj3);
                if (zza.type == 14 || zza.type == 2 || zza.type == 6 || zza.type == 9) {
                    if (i2 == 14 && zza.type != 14) {
                        r1 = zza.type;
                    } else if (zza.type != i2) {
                        String valueOf = String.valueOf(obj2.getClass());
                        r0 = String.valueOf(obj3.getClass());
                        throw new IllegalArgumentException(new StringBuilder((String.valueOf(valueOf).length() + 80) + String.valueOf(r0).length()).append("ArrayList elements must all be of the sameclass, but this one contains a ").append(valueOf).append(" and a ").append(r0).toString());
                    } else {
                        obj3 = obj2;
                        r1 = i2;
                    }
                    zzdnkArr[i] = zza;
                    i++;
                    i2 = r1;
                    obj2 = obj3;
                } else {
                    r0 = String.valueOf(obj3.getClass());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(r0).length() + 130).append("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a ").append(r0).toString());
                }
            }
            zzdnk.zzlwq.zzlxa = zzdnkArr;
        } else {
            String str = "newFieldValueFromValue: unexpected value ";
            r0 = String.valueOf(obj.getClass().getSimpleName());
            throw new RuntimeException(r0.length() != 0 ? str.concat(r0) : new String(str));
        }
        return zzdnk;
    }

    public static DataMap zza(zzdnh zzdnh) {
        DataMap dataMap = new DataMap();
        for (zzdnj zzdnj : zzdnh.zzlwk.zzlwm) {
            zza(zzdnh.zzlwl, dataMap, zzdnj.name, zzdnj.zzlwo);
        }
        return dataMap;
    }

    private static ArrayList zza(List<Asset> list, zzdnl zzdnl, int i) {
        ArrayList arrayList = new ArrayList(zzdnl.zzlxa.length);
        for (zzdnk zzdnk : zzdnl.zzlxa) {
            if (zzdnk.type == 14) {
                arrayList.add(null);
            } else if (i == 9) {
                DataMap dataMap = new DataMap();
                for (zzdnj zzdnj : zzdnk.zzlwq.zzlwz) {
                    zza(list, dataMap, zzdnj.name, zzdnj.zzlwo);
                }
                arrayList.add(dataMap);
            } else if (i == 2) {
                arrayList.add(zzdnk.zzlwq.zzlws);
            } else if (i == 6) {
                arrayList.add(Integer.valueOf(zzdnk.zzlwq.zzlww));
            } else {
                throw new IllegalArgumentException("Unexpected typeOfArrayList: " + i);
            }
        }
        return arrayList;
    }

    private static void zza(List<Asset> list, DataMap dataMap, String str, zzdnk zzdnk) {
        int i = zzdnk.type;
        if (i == 14) {
            dataMap.putString(str, null);
            return;
        }
        zzdnl zzdnl = zzdnk.zzlwq;
        if (i == 1) {
            dataMap.putByteArray(str, zzdnl.zzlwr);
        } else if (i == 11) {
            dataMap.putStringArray(str, zzdnl.zzlxb);
        } else if (i == 12) {
            dataMap.putLongArray(str, zzdnl.zzlxc);
        } else if (i == 15) {
            dataMap.putFloatArray(str, zzdnl.zzlxd);
        } else if (i == 2) {
            dataMap.putString(str, zzdnl.zzlws);
        } else if (i == 3) {
            dataMap.putDouble(str, zzdnl.zzlwt);
        } else if (i == 4) {
            dataMap.putFloat(str, zzdnl.zzlwu);
        } else if (i == 5) {
            dataMap.putLong(str, zzdnl.zzlwv);
        } else if (i == 6) {
            dataMap.putInt(str, zzdnl.zzlww);
        } else if (i == 7) {
            dataMap.putByte(str, (byte) zzdnl.zzlwx);
        } else if (i == 8) {
            dataMap.putBoolean(str, zzdnl.zzlwy);
        } else if (i == 13) {
            if (list == null) {
                String str2 = "populateBundle: unexpected type for: ";
                String valueOf = String.valueOf(str);
                throw new RuntimeException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            dataMap.putAsset(str, (Asset) list.get((int) zzdnl.zzlxe));
        } else if (i == 9) {
            DataMap dataMap2 = new DataMap();
            for (zzdnj zzdnj : zzdnl.zzlwz) {
                zza(list, dataMap2, zzdnj.name, zzdnj.zzlwo);
            }
            dataMap.putDataMap(str, dataMap2);
        } else if (i == 10) {
            i = zza(str, zzdnl.zzlxa);
            ArrayList zza = zza(list, zzdnl, i);
            if (i == 14) {
                dataMap.putStringArrayList(str, zza);
            } else if (i == 9) {
                dataMap.putDataMapArrayList(str, zza);
            } else if (i == 2) {
                dataMap.putStringArrayList(str, zza);
            } else if (i == 6) {
                dataMap.putIntegerArrayList(str, zza);
            } else {
                throw new IllegalStateException("Unexpected typeOfArrayList: " + i);
            }
        } else {
            throw new RuntimeException("populateBundle: unexpected type " + i);
        }
    }

    private static zzdnj[] zza(DataMap dataMap, List<Asset> list) {
        TreeSet treeSet = new TreeSet(dataMap.keySet());
        zzdnj[] zzdnjArr = new zzdnj[treeSet.size()];
        Iterator it = treeSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            Object obj = dataMap.get(str);
            zzdnjArr[i] = new zzdnj();
            zzdnjArr[i].name = str;
            zzdnjArr[i].zzlwo = zza((List) list, obj);
            i++;
        }
        return zzdnjArr;
    }
}
