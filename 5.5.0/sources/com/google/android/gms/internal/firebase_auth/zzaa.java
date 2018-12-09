package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.firebase.auth.EmailAuthCredential;
import java.util.Set;

public final class zzaa {
    private static final Logger zzdx = new Logger("EmailLinkSignInRequest", new String[0]);
    private final String zzaf;
    private final String zzah;
    private final String zzdy;

    public zzaa(EmailAuthCredential emailAuthCredential, String str) {
        this.zzah = Preconditions.checkNotEmpty(emailAuthCredential.m8489b());
        this.zzdy = Preconditions.checkNotEmpty(emailAuthCredential.m8490c());
        this.zzaf = str;
    }

    private static String zzi(String str) {
        Uri parse = Uri.parse(str);
        try {
            Set queryParameterNames = parse.getQueryParameterNames();
            return queryParameterNames.contains("oobCode") ? parse.getQueryParameter("oobCode") : queryParameterNames.contains("link") ? Uri.parse(parse.getQueryParameter("link")).getQueryParameter("oobCode") : null;
        } catch (UnsupportedOperationException e) {
            Logger logger = zzdx;
            String str2 = "EmailLinkSignInRequest";
            Object[] objArr = new Object[1];
            String str3 = "No oobCode in signInLink: ";
            String valueOf = String.valueOf(e.getMessage());
            objArr[0] = valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3);
            logger.m8461v(str2, objArr);
            return null;
        }
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzj = new zzj();
        zzj.zzah = this.zzah;
        zzj.zzag = zzi(this.zzdy);
        zzj.zzaf = this.zzaf;
        return zzj;
    }
}
