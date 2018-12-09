package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.logging.Logger;
import com.google.firebase.C1812c;

public class PhoneAuthProvider {

    @Class(creator = "DefaultForceResendingTokenCreator")
    public static class ForceResendingToken extends AbstractSafeParcelable {
        public static final Creator<ForceResendingToken> CREATOR = new C1883m();

        @Constructor
        ForceResendingToken() {
        }

        /* renamed from: a */
        public static ForceResendingToken m8522a() {
            return new ForceResendingToken();
        }

        public void writeToParcel(Parcel parcel, int i) {
            SafeParcelWriter.finishObjectHeader(parcel, SafeParcelWriter.beginObjectHeader(parcel));
        }
    }

    /* renamed from: com.google.firebase.auth.PhoneAuthProvider$a */
    public static abstract class C1825a {
        /* renamed from: a */
        private static final Logger f5455a = new Logger("PhoneAuthProvider", new String[0]);

        /* renamed from: a */
        public abstract void m8523a(PhoneAuthCredential phoneAuthCredential);

        /* renamed from: a */
        public abstract void m8524a(C1812c c1812c);

        /* renamed from: a */
        public void m8525a(String str) {
            f5455a.m8459i("Sms auto retrieval timed-out.", new Object[0]);
        }

        /* renamed from: a */
        public void m8526a(String str, ForceResendingToken forceResendingToken) {
        }
    }
}
