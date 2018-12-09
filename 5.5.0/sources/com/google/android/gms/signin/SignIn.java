package com.google.android.gms.signin;

import android.os.Bundle;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.internal.SignInClientImpl;

public final class SignIn {
    public static final Api<SignInOptions> API = new Api("SignIn.API", CLIENT_BUILDER, CLIENT_KEY);
    public static final AbstractClientBuilder<SignInClientImpl, SignInOptions> CLIENT_BUILDER = new zza();
    public static final ClientKey<SignInClientImpl> CLIENT_KEY = new ClientKey();
    public static final Api<SignInOptionsInternal> INTERNAL_API = new Api("SignIn.INTERNAL_API", zzacz, INTERNAL_CLIENT_KEY);
    public static final ClientKey<SignInClientImpl> INTERNAL_CLIENT_KEY = new ClientKey();
    public static final Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);
    public static final Scope SCOPE_PROFILE = new Scope(Scopes.PROFILE);
    private static final AbstractClientBuilder<SignInClientImpl, SignInOptionsInternal> zzacz = new zzb();

    public static class SignInOptionsInternal implements HasOptions {
        private final Bundle zzada;

        private SignInOptionsInternal(Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.zzada = bundle;
        }

        public static SignInOptionsInternal create(Bundle bundle) {
            return new SignInOptionsInternal(bundle);
        }

        public Bundle getSignInOptionsBundle() {
            return this.zzada;
        }
    }

    private SignIn() {
    }
}
