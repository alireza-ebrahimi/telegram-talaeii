package com.google.android.gms.flags;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

public interface IFlagProvider extends IInterface {

    public static abstract class Stub extends zzb implements IFlagProvider {

        public static class Proxy extends zza implements IFlagProvider {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.flags.IFlagProvider");
            }

            public boolean getBooleanFlagValue(String str, boolean z, int i) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeString(str);
                zzc.zza(obtainAndWriteInterfaceToken, z);
                obtainAndWriteInterfaceToken.writeInt(i);
                obtainAndWriteInterfaceToken = transactAndReadException(2, obtainAndWriteInterfaceToken);
                boolean zza = zzc.zza(obtainAndWriteInterfaceToken);
                obtainAndWriteInterfaceToken.recycle();
                return zza;
            }

            public int getIntFlagValue(String str, int i, int i2) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeString(str);
                obtainAndWriteInterfaceToken.writeInt(i);
                obtainAndWriteInterfaceToken.writeInt(i2);
                obtainAndWriteInterfaceToken = transactAndReadException(3, obtainAndWriteInterfaceToken);
                int readInt = obtainAndWriteInterfaceToken.readInt();
                obtainAndWriteInterfaceToken.recycle();
                return readInt;
            }

            public long getLongFlagValue(String str, long j, int i) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeString(str);
                obtainAndWriteInterfaceToken.writeLong(j);
                obtainAndWriteInterfaceToken.writeInt(i);
                obtainAndWriteInterfaceToken = transactAndReadException(4, obtainAndWriteInterfaceToken);
                long readLong = obtainAndWriteInterfaceToken.readLong();
                obtainAndWriteInterfaceToken.recycle();
                return readLong;
            }

            public String getStringFlagValue(String str, String str2, int i) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeString(str);
                obtainAndWriteInterfaceToken.writeString(str2);
                obtainAndWriteInterfaceToken.writeInt(i);
                obtainAndWriteInterfaceToken = transactAndReadException(5, obtainAndWriteInterfaceToken);
                String readString = obtainAndWriteInterfaceToken.readString();
                obtainAndWriteInterfaceToken.recycle();
                return readString;
            }

            public void init(IObjectWrapper iObjectWrapper) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
                transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
            }
        }

        public Stub() {
            super("com.google.android.gms.flags.IFlagProvider");
        }

        public static IFlagProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
            return queryLocalInterface instanceof IFlagProvider ? (IFlagProvider) queryLocalInterface : new Proxy(iBinder);
        }

        protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
            switch (i) {
                case 1:
                    init(com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    break;
                case 2:
                    boolean booleanFlagValue = getBooleanFlagValue(parcel.readString(), zzc.zza(parcel), parcel.readInt());
                    parcel2.writeNoException();
                    zzc.zza(parcel2, booleanFlagValue);
                    break;
                case 3:
                    int intFlagValue = getIntFlagValue(parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(intFlagValue);
                    break;
                case 4:
                    long longFlagValue = getLongFlagValue(parcel.readString(), parcel.readLong(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeLong(longFlagValue);
                    break;
                case 5:
                    String stringFlagValue = getStringFlagValue(parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(stringFlagValue);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    boolean getBooleanFlagValue(String str, boolean z, int i);

    int getIntFlagValue(String str, int i, int i2);

    long getLongFlagValue(String str, long j, int i);

    String getStringFlagValue(String str, String str2, int i);

    void init(IObjectWrapper iObjectWrapper);
}
