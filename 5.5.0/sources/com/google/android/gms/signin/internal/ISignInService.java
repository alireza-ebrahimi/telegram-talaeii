package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.IResolveAccountCallbacks;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

public interface ISignInService extends IInterface {

    public static abstract class Stub extends zzb implements ISignInService {

        public static class Proxy extends zza implements ISignInService {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
            }

            public void authAccount(AuthAccountRequest authAccountRequest, ISignInCallbacks iSignInCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) authAccountRequest);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iSignInCallbacks);
                transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
            }

            public void clearAccountFromSessionStore(int i) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeInt(i);
                transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken);
            }

            public void getCurrentAccount(ISignInCallbacks iSignInCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iSignInCallbacks);
                transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken);
            }

            public void onCheckServerAuthorization(CheckServerAuthResult checkServerAuthResult) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) checkServerAuthResult);
                transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken);
            }

            public void onUploadServerAuthCode(boolean z) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken);
            }

            public void recordConsent(RecordConsentRequest recordConsentRequest, ISignInCallbacks iSignInCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) recordConsentRequest);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iSignInCallbacks);
                transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
            }

            public void resolveAccount(ResolveAccountRequest resolveAccountRequest, IResolveAccountCallbacks iResolveAccountCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) resolveAccountRequest);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iResolveAccountCallbacks);
                transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken);
            }

            public void saveAccountToSessionStore(int i, Account account, ISignInCallbacks iSignInCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                obtainAndWriteInterfaceToken.writeInt(i);
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) account);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iSignInCallbacks);
                transactAndReadExceptionReturnVoid(8, obtainAndWriteInterfaceToken);
            }

            public void saveDefaultAccountToSharedPref(IAccountAccessor iAccountAccessor, int i, boolean z) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iAccountAccessor);
                obtainAndWriteInterfaceToken.writeInt(i);
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
            }

            public void setGamesHasBeenGreeted(boolean z) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
            }

            public void signIn(SignInRequest signInRequest, ISignInCallbacks iSignInCallbacks) {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) signInRequest);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iSignInCallbacks);
                transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
            }
        }

        public Stub() {
            super("com.google.android.gms.signin.internal.ISignInService");
        }

        public static ISignInService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
            return queryLocalInterface instanceof ISignInService ? (ISignInService) queryLocalInterface : new Proxy(iBinder);
        }

        protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
            switch (i) {
                case 2:
                    authAccount((AuthAccountRequest) zzc.zza(parcel, AuthAccountRequest.CREATOR), com.google.android.gms.signin.internal.ISignInCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 3:
                    onCheckServerAuthorization((CheckServerAuthResult) zzc.zza(parcel, CheckServerAuthResult.CREATOR));
                    break;
                case 4:
                    onUploadServerAuthCode(zzc.zza(parcel));
                    break;
                case 5:
                    resolveAccount((ResolveAccountRequest) zzc.zza(parcel, ResolveAccountRequest.CREATOR), com.google.android.gms.common.internal.IResolveAccountCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 7:
                    clearAccountFromSessionStore(parcel.readInt());
                    break;
                case 8:
                    saveAccountToSessionStore(parcel.readInt(), (Account) zzc.zza(parcel, Account.CREATOR), com.google.android.gms.signin.internal.ISignInCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 9:
                    saveDefaultAccountToSharedPref(com.google.android.gms.common.internal.IAccountAccessor.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), zzc.zza(parcel));
                    break;
                case 10:
                    recordConsent((RecordConsentRequest) zzc.zza(parcel, RecordConsentRequest.CREATOR), com.google.android.gms.signin.internal.ISignInCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 11:
                    getCurrentAccount(com.google.android.gms.signin.internal.ISignInCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 12:
                    signIn((SignInRequest) zzc.zza(parcel, SignInRequest.CREATOR), com.google.android.gms.signin.internal.ISignInCallbacks.Stub.asInterface(parcel.readStrongBinder()));
                    break;
                case 13:
                    setGamesHasBeenGreeted(zzc.zza(parcel));
                    break;
                default:
                    return false;
            }
            parcel2.writeNoException();
            return true;
        }
    }

    void authAccount(AuthAccountRequest authAccountRequest, ISignInCallbacks iSignInCallbacks);

    void clearAccountFromSessionStore(int i);

    void getCurrentAccount(ISignInCallbacks iSignInCallbacks);

    void onCheckServerAuthorization(CheckServerAuthResult checkServerAuthResult);

    void onUploadServerAuthCode(boolean z);

    void recordConsent(RecordConsentRequest recordConsentRequest, ISignInCallbacks iSignInCallbacks);

    void resolveAccount(ResolveAccountRequest resolveAccountRequest, IResolveAccountCallbacks iResolveAccountCallbacks);

    void saveAccountToSessionStore(int i, Account account, ISignInCallbacks iSignInCallbacks);

    void saveDefaultAccountToSharedPref(IAccountAccessor iAccountAccessor, int i, boolean z);

    void setGamesHasBeenGreeted(boolean z);

    void signIn(SignInRequest signInRequest, ISignInCallbacks iSignInCallbacks);
}
