package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface IGmsServiceBroker extends IInterface {

    public static abstract class Stub extends Binder implements IGmsServiceBroker {

        private static class zza implements IGmsServiceBroker {
            private final IBinder zza;

            zza(IBinder iBinder) {
                this.zza = iBinder;
            }

            public final IBinder asBinder() {
                return this.zza;
            }

            public final void getService(IGmsCallbacks iGmsCallbacks, GetServiceRequest getServiceRequest) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(iGmsCallbacks != null ? iGmsCallbacks.asBinder() : null);
                    if (getServiceRequest != null) {
                        obtain.writeInt(1);
                        getServiceRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zza.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
        }

        public static IGmsServiceBroker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IGmsServiceBroker)) ? new zza(iBinder) : (IGmsServiceBroker) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        protected void getLegacyService(int i, IGmsCallbacks iGmsCallbacks, int i2, String str, String str2, String[] strArr, Bundle bundle, IBinder iBinder, String str3, String str4) {
            throw new UnsupportedOperationException();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r12, android.os.Parcel r13, android.os.Parcel r14, int r15) {
            /*
            r11 = this;
            r1 = 0;
            r0 = 16777215; // 0xffffff float:2.3509886E-38 double:8.2890456E-317;
            if (r12 <= r0) goto L_0x000b;
        L_0x0006:
            r0 = super.onTransact(r12, r13, r14, r15);
        L_0x000a:
            return r0;
        L_0x000b:
            r0 = "com.google.android.gms.common.internal.IGmsServiceBroker";
            r13.enforceInterface(r0);
            r0 = r13.readStrongBinder();
            r2 = com.google.android.gms.common.internal.IGmsCallbacks.Stub.asInterface(r0);
            r0 = 46;
            if (r12 != r0) goto L_0x0033;
        L_0x001d:
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x0122;
        L_0x0023:
            r0 = com.google.android.gms.common.internal.GetServiceRequest.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (com.google.android.gms.common.internal.GetServiceRequest) r0;
        L_0x002b:
            r11.getService(r2, r0);
        L_0x002e:
            r14.writeNoException();
            r0 = 1;
            goto L_0x000a;
        L_0x0033:
            r0 = 47;
            if (r12 != r0) goto L_0x0049;
        L_0x0037:
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x011f;
        L_0x003d:
            r0 = com.google.android.gms.common.internal.ValidateAccountRequest.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (com.google.android.gms.common.internal.ValidateAccountRequest) r0;
        L_0x0045:
            r11.validateAccount(r2, r0);
            goto L_0x002e;
        L_0x0049:
            r3 = r13.readInt();
            r0 = 4;
            if (r12 == r0) goto L_0x011c;
        L_0x0050:
            r4 = r13.readString();
        L_0x0054:
            switch(r12) {
                case 1: goto L_0x007b;
                case 2: goto L_0x00f1;
                case 3: goto L_0x0057;
                case 4: goto L_0x0057;
                case 5: goto L_0x00f1;
                case 6: goto L_0x00f1;
                case 7: goto L_0x00f1;
                case 8: goto L_0x00f1;
                case 9: goto L_0x0099;
                case 10: goto L_0x00d8;
                case 11: goto L_0x00f1;
                case 12: goto L_0x00f1;
                case 13: goto L_0x00f1;
                case 14: goto L_0x00f1;
                case 15: goto L_0x00f1;
                case 16: goto L_0x00f1;
                case 17: goto L_0x00f1;
                case 18: goto L_0x00f1;
                case 19: goto L_0x0063;
                case 20: goto L_0x00bd;
                case 21: goto L_0x0057;
                case 22: goto L_0x0057;
                case 23: goto L_0x00f1;
                case 24: goto L_0x0057;
                case 25: goto L_0x00f1;
                case 26: goto L_0x0057;
                case 27: goto L_0x00f1;
                case 28: goto L_0x0057;
                case 29: goto L_0x0057;
                case 30: goto L_0x00bd;
                case 31: goto L_0x0057;
                case 32: goto L_0x0057;
                case 33: goto L_0x0057;
                case 34: goto L_0x00e6;
                case 35: goto L_0x0057;
                case 36: goto L_0x0057;
                case 37: goto L_0x00f1;
                case 38: goto L_0x00f1;
                case 39: goto L_0x0057;
                case 40: goto L_0x0057;
                case 41: goto L_0x00f1;
                case 42: goto L_0x0057;
                case 43: goto L_0x00f1;
                default: goto L_0x0057;
            };
        L_0x0057:
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r1;
            r6 = r1;
            r5 = r1;
        L_0x005d:
            r0 = r11;
            r1 = r12;
            r0.getLegacyService(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
            goto L_0x002e;
        L_0x0063:
            r8 = r13.readStrongBinder();
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x0115;
        L_0x006d:
            r0 = android.os.Bundle.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (android.os.Bundle) r0;
            r10 = r1;
            r9 = r1;
            r7 = r0;
            r6 = r1;
            r5 = r1;
            goto L_0x005d;
        L_0x007b:
            r9 = r13.readString();
            r6 = r13.createStringArray();
            r5 = r13.readString();
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x0110;
        L_0x008d:
            r0 = android.os.Bundle.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (android.os.Bundle) r0;
            r10 = r1;
            r8 = r1;
            r7 = r0;
            goto L_0x005d;
        L_0x0099:
            r5 = r13.readString();
            r6 = r13.createStringArray();
            r9 = r13.readString();
            r8 = r13.readStrongBinder();
            r10 = r13.readString();
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x010d;
        L_0x00b3:
            r0 = android.os.Bundle.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (android.os.Bundle) r0;
            r7 = r0;
            goto L_0x005d;
        L_0x00bd:
            r6 = r13.createStringArray();
            r5 = r13.readString();
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x0107;
        L_0x00cb:
            r0 = android.os.Bundle.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (android.os.Bundle) r0;
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r0;
            goto L_0x005d;
        L_0x00d8:
            r5 = r13.readString();
            r6 = r13.createStringArray();
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r1;
            goto L_0x005d;
        L_0x00e6:
            r5 = r13.readString();
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r1;
            r6 = r1;
            goto L_0x005d;
        L_0x00f1:
            r0 = r13.readInt();
            if (r0 == 0) goto L_0x0057;
        L_0x00f7:
            r0 = android.os.Bundle.CREATOR;
            r0 = r0.createFromParcel(r13);
            r0 = (android.os.Bundle) r0;
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r0;
            r6 = r1;
            r5 = r1;
            goto L_0x005d;
        L_0x0107:
            r10 = r1;
            r9 = r1;
            r8 = r1;
            r7 = r1;
            goto L_0x005d;
        L_0x010d:
            r7 = r1;
            goto L_0x005d;
        L_0x0110:
            r10 = r1;
            r8 = r1;
            r7 = r1;
            goto L_0x005d;
        L_0x0115:
            r10 = r1;
            r9 = r1;
            r7 = r1;
            r6 = r1;
            r5 = r1;
            goto L_0x005d;
        L_0x011c:
            r4 = r1;
            goto L_0x0054;
        L_0x011f:
            r0 = r1;
            goto L_0x0045;
        L_0x0122:
            r0 = r1;
            goto L_0x002b;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.IGmsServiceBroker.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        protected void validateAccount(IGmsCallbacks iGmsCallbacks, ValidateAccountRequest validateAccountRequest) {
            throw new UnsupportedOperationException();
        }
    }

    void getService(IGmsCallbacks iGmsCallbacks, GetServiceRequest getServiceRequest);
}
