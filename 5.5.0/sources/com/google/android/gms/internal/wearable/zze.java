package com.google.android.gms.internal.wearable;

import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

@VisibleForTesting
public final class zze {
    public static zzf zza(DataMap dataMap) {
        zzg zzg = new zzg();
        List arrayList = new ArrayList();
        TreeSet treeSet = new TreeSet(dataMap.keySet());
        zzh[] zzhArr = new zzh[treeSet.size()];
        Iterator it = treeSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            Object obj = dataMap.get(str);
            zzhArr[i] = new zzh();
            zzhArr[i].name = str;
            zzhArr[i].zzga = zza(arrayList, obj);
            i++;
        }
        zzg.zzfy = zzhArr;
        return new zzf(zzg, arrayList);
    }

    private static zzi zza(List<Asset> list, Object obj) {
        zzi zzi = new zzi();
        if (obj == null) {
            zzi.type = 14;
            return zzi;
        }
        zzi.zzgc = new zzj();
        if (obj instanceof String) {
            zzi.type = 2;
            zzi.zzgc.zzge = (String) obj;
        } else if (obj instanceof Integer) {
            zzi.type = 6;
            zzi.zzgc.zzgi = ((Integer) obj).intValue();
        } else if (obj instanceof Long) {
            zzi.type = 5;
            zzi.zzgc.zzgh = ((Long) obj).longValue();
        } else if (obj instanceof Double) {
            zzi.type = 3;
            zzi.zzgc.zzgf = ((Double) obj).doubleValue();
        } else if (obj instanceof Float) {
            zzi.type = 4;
            zzi.zzgc.zzgg = ((Float) obj).floatValue();
        } else if (obj instanceof Boolean) {
            zzi.type = 8;
            zzi.zzgc.zzgk = ((Boolean) obj).booleanValue();
        } else if (obj instanceof Byte) {
            zzi.type = 7;
            zzi.zzgc.zzgj = ((Byte) obj).byteValue();
        } else if (obj instanceof byte[]) {
            zzi.type = 1;
            zzi.zzgc.zzgd = (byte[]) obj;
        } else if (obj instanceof String[]) {
            zzi.type = 11;
            zzi.zzgc.zzgn = (String[]) obj;
        } else if (obj instanceof long[]) {
            zzi.type = 12;
            zzi.zzgc.zzgo = (long[]) obj;
        } else if (obj instanceof float[]) {
            zzi.type = 15;
            zzi.zzgc.zzgp = (float[]) obj;
        } else if (obj instanceof Asset) {
            zzi.type = 13;
            zzj zzj = zzi.zzgc;
            list.add((Asset) obj);
            zzj.zzgq = (long) (list.size() - 1);
        } else if (obj instanceof DataMap) {
            zzi.type = 9;
            DataMap dataMap = (DataMap) obj;
            TreeSet treeSet = new TreeSet(dataMap.keySet());
            zzh[] zzhArr = new zzh[treeSet.size()];
            Iterator it = treeSet.iterator();
            r1 = 0;
            while (it.hasNext()) {
                r0 = (String) it.next();
                zzhArr[r1] = new zzh();
                zzhArr[r1].name = r0;
                zzhArr[r1].zzga = zza(list, dataMap.get(r0));
                r1++;
            }
            zzi.zzgc.zzgl = zzhArr;
        } else if (obj instanceof ArrayList) {
            zzi.type = 10;
            ArrayList arrayList = (ArrayList) obj;
            zzi[] zziArr = new zzi[arrayList.size()];
            Object obj2 = null;
            int size = arrayList.size();
            int i = 0;
            int i2 = 14;
            while (i < size) {
                Object obj3 = arrayList.get(i);
                zzi zza = zza(list, obj3);
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
                    zziArr[i] = zza;
                    i++;
                    i2 = r1;
                    obj2 = obj3;
                } else {
                    r0 = String.valueOf(obj3.getClass());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(r0).length() + TsExtractor.TS_STREAM_TYPE_HDMV_DTS).append("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a ").append(r0).toString());
                }
            }
            zzi.zzgc.zzgm = zziArr;
        } else {
            String str = "newFieldValueFromValue: unexpected value ";
            r0 = String.valueOf(obj.getClass().getSimpleName());
            throw new RuntimeException(r0.length() != 0 ? str.concat(r0) : new String(str));
        }
        return zzi;
    }

    public static DataMap zza(zzf zzf) {
        DataMap dataMap = new DataMap();
        for (zzh zzh : zzf.zzfw.zzfy) {
            zza(zzf.zzfx, dataMap, zzh.name, zzh.zzga);
        }
        return dataMap;
    }

    private static void zza(List<Asset> list, DataMap dataMap, String str, zzi zzi) {
        int i = zzi.type;
        if (i == 14) {
            dataMap.putString(str, null);
            return;
        }
        zzj zzj = zzi.zzgc;
        if (i == 1) {
            dataMap.putByteArray(str, zzj.zzgd);
        } else if (i == 11) {
            dataMap.putStringArray(str, zzj.zzgn);
        } else if (i == 12) {
            dataMap.putLongArray(str, zzj.zzgo);
        } else if (i == 15) {
            dataMap.putFloatArray(str, zzj.zzgp);
        } else if (i == 2) {
            dataMap.putString(str, zzj.zzge);
        } else if (i == 3) {
            dataMap.putDouble(str, zzj.zzgf);
        } else if (i == 4) {
            dataMap.putFloat(str, zzj.zzgg);
        } else if (i == 5) {
            dataMap.putLong(str, zzj.zzgh);
        } else if (i == 6) {
            dataMap.putInt(str, zzj.zzgi);
        } else if (i == 7) {
            dataMap.putByte(str, (byte) zzj.zzgj);
        } else if (i == 8) {
            dataMap.putBoolean(str, zzj.zzgk);
        } else if (i == 13) {
            if (list == null) {
                String str2 = "populateBundle: unexpected type for: ";
                String valueOf = String.valueOf(str);
                throw new RuntimeException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            dataMap.putAsset(str, (Asset) list.get((int) zzj.zzgq));
        } else if (i == 9) {
            DataMap dataMap2 = new DataMap();
            for (zzh zzh : zzj.zzgl) {
                zza(list, dataMap2, zzh.name, zzh.zzga);
            }
            dataMap.putDataMap(str, dataMap2);
        } else if (i == 10) {
            i = 14;
            for (zzi zzi2 : zzj.zzgm) {
                if (i == 14) {
                    if (zzi2.type == 9 || zzi2.type == 2 || zzi2.type == 6) {
                        i = zzi2.type;
                    } else if (zzi2.type != 14) {
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 48).append("Unexpected TypedValue type: ").append(zzi2.type).append(" for key ").append(str).toString());
                    }
                } else if (zzi2.type != i) {
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 126).append("The ArrayList elements should all be the same type, but ArrayList with key ").append(str).append(" contains items of type ").append(i).append(" and ").append(zzi2.type).toString());
                }
            }
            ArrayList arrayList = new ArrayList(zzj.zzgm.length);
            for (zzi zzi3 : zzj.zzgm) {
                if (zzi3.type == 14) {
                    arrayList.add(null);
                } else if (i == 9) {
                    DataMap dataMap3 = new DataMap();
                    for (zzh zzh2 : zzi3.zzgc.zzgl) {
                        zza(list, dataMap3, zzh2.name, zzh2.zzga);
                    }
                    arrayList.add(dataMap3);
                } else if (i == 2) {
                    arrayList.add(zzi3.zzgc.zzge);
                } else if (i == 6) {
                    arrayList.add(Integer.valueOf(zzi3.zzgc.zzgi));
                } else {
                    throw new IllegalArgumentException("Unexpected typeOfArrayList: " + i);
                }
            }
            if (i == 14) {
                dataMap.putStringArrayList(str, arrayList);
            } else if (i == 9) {
                dataMap.putDataMapArrayList(str, arrayList);
            } else if (i == 2) {
                dataMap.putStringArrayList(str, arrayList);
            } else if (i == 6) {
                dataMap.putIntegerArrayList(str, arrayList);
            } else {
                throw new IllegalStateException("Unexpected typeOfArrayList: " + i);
            }
        } else {
            throw new RuntimeException("populateBundle: unexpected type " + i);
        }
    }
}
