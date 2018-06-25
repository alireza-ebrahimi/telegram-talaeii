package com.google.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;
import com.google.android.gms.common.util.zzw;
import java.util.Arrays;

public final class FirebaseOptions {
    private final String zzetb;
    private final String zzmna;
    private final String zzmnb;
    private final String zzmnc;
    private final String zzmnd;
    private final String zzmne;
    private final String zzmnf;

    public static final class Builder {
        private String zzetb;
        private String zzmna;
        private String zzmnb;
        private String zzmnc;
        private String zzmnd;
        private String zzmne;
        private String zzmnf;

        public Builder(FirebaseOptions firebaseOptions) {
            this.zzetb = firebaseOptions.zzetb;
            this.zzmna = firebaseOptions.zzmna;
            this.zzmnb = firebaseOptions.zzmnb;
            this.zzmnc = firebaseOptions.zzmnc;
            this.zzmnd = firebaseOptions.zzmnd;
            this.zzmne = firebaseOptions.zzmne;
            this.zzmnf = firebaseOptions.zzmnf;
        }

        public final FirebaseOptions build() {
            return new FirebaseOptions(this.zzetb, this.zzmna, this.zzmnb, this.zzmnc, this.zzmnd, this.zzmne, this.zzmnf);
        }

        public final Builder setApiKey(@NonNull String str) {
            this.zzmna = zzbq.zzh(str, "ApiKey must be set.");
            return this;
        }

        public final Builder setApplicationId(@NonNull String str) {
            this.zzetb = zzbq.zzh(str, "ApplicationId must be set.");
            return this;
        }

        public final Builder setDatabaseUrl(@Nullable String str) {
            this.zzmnb = str;
            return this;
        }

        public final Builder setGcmSenderId(@Nullable String str) {
            this.zzmnd = str;
            return this;
        }

        public final Builder setProjectId(@Nullable String str) {
            this.zzmnf = str;
            return this;
        }

        public final Builder setStorageBucket(@Nullable String str) {
            this.zzmne = str;
            return this;
        }
    }

    private FirebaseOptions(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7) {
        zzbq.zza(!zzw.zzhb(str), (Object) "ApplicationId must be set.");
        this.zzetb = str;
        this.zzmna = str2;
        this.zzmnb = str3;
        this.zzmnc = str4;
        this.zzmnd = str5;
        this.zzmne = str6;
        this.zzmnf = str7;
    }

    public static FirebaseOptions fromResource(Context context) {
        zzca zzca = new zzca(context);
        Object string = zzca.getString("google_app_id");
        return TextUtils.isEmpty(string) ? null : new FirebaseOptions(string, zzca.getString("google_api_key"), zzca.getString("firebase_database_url"), zzca.getString("ga_trackingId"), zzca.getString("gcm_defaultSenderId"), zzca.getString("google_storage_bucket"), zzca.getString("project_id"));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof FirebaseOptions)) {
            return false;
        }
        FirebaseOptions firebaseOptions = (FirebaseOptions) obj;
        return zzbg.equal(this.zzetb, firebaseOptions.zzetb) && zzbg.equal(this.zzmna, firebaseOptions.zzmna) && zzbg.equal(this.zzmnb, firebaseOptions.zzmnb) && zzbg.equal(this.zzmnc, firebaseOptions.zzmnc) && zzbg.equal(this.zzmnd, firebaseOptions.zzmnd) && zzbg.equal(this.zzmne, firebaseOptions.zzmne) && zzbg.equal(this.zzmnf, firebaseOptions.zzmnf);
    }

    public final String getApiKey() {
        return this.zzmna;
    }

    public final String getApplicationId() {
        return this.zzetb;
    }

    public final String getDatabaseUrl() {
        return this.zzmnb;
    }

    public final String getGcmSenderId() {
        return this.zzmnd;
    }

    public final String getProjectId() {
        return this.zzmnf;
    }

    public final String getStorageBucket() {
        return this.zzmne;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzetb, this.zzmna, this.zzmnb, this.zzmnc, this.zzmnd, this.zzmne, this.zzmnf});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("applicationId", this.zzetb).zzg("apiKey", this.zzmna).zzg("databaseUrl", this.zzmnb).zzg("gcmSenderId", this.zzmnd).zzg("storageBucket", this.zzmne).zzg("projectId", this.zzmnf).toString();
    }
}
