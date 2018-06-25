package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends zzbgl implements ReflectedParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR = new zzb();
    @Hide
    private static zze zzemg = zzi.zzanq();
    @Hide
    private int versionCode;
    private String zzbzd;
    private List<Scope> zzeie;
    private String zzekq;
    private String zzekr;
    private String zzelh;
    private String zzemh;
    private String zzemi;
    private Uri zzemj;
    private String zzemk;
    private long zzeml;
    private String zzemm;
    private Set<Scope> zzemn = new HashSet();

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.versionCode = i;
        this.zzbzd = str;
        this.zzelh = str2;
        this.zzemh = str3;
        this.zzemi = str4;
        this.zzemj = uri;
        this.zzemk = str5;
        this.zzeml = j;
        this.zzemm = str6;
        this.zzeie = list;
        this.zzekq = str7;
        this.zzekr = str8;
    }

    private final JSONObject toJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put("id", getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzeml);
            jSONObject.put("obfuscatedIdentifier", this.zzemm);
            JSONArray jSONArray = new JSONArray();
            Scope[] scopeArr = (Scope[]) this.zzeie.toArray(new Scope[this.zzeie.size()]);
            Arrays.sort(scopeArr, zza.zzemo);
            for (Scope zzaie : scopeArr) {
                jSONArray.put(zzaie.zzaie());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Hide
    private static GoogleSignInAccount zza(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Uri uri, @Nullable Long l, @NonNull String str7, @NonNull Set<Scope> set) {
        if (l == null) {
            l = Long.valueOf(zzemg.currentTimeMillis() / 1000);
        }
        return new GoogleSignInAccount(3, str, str2, str3, str4, uri, null, l.longValue(), zzbq.zzgv(str7), new ArrayList((Collection) zzbq.checkNotNull(set)), str5, str6);
    }

    @Hide
    public static GoogleSignInAccount zzacd() {
        Account account = new Account("<<default account>>", "com.google");
        Set hashSet = new HashSet();
        return zza(null, null, account.name, null, null, null, null, Long.valueOf(0), account.name, hashSet);
    }

    @Nullable
    @Hide
    public static GoogleSignInAccount zzfa(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Object optString = jSONObject.optString("photoUrl", null);
        Uri parse = !TextUtils.isEmpty(optString) ? Uri.parse(optString) : null;
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        Set hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        GoogleSignInAccount zza = zza(jSONObject.optString("id"), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), parse, Long.valueOf(parseLong), jSONObject.getString("obfuscatedIdentifier"), hashSet);
        zza.zzemk = jSONObject.optString("serverAuthCode", null);
        return zza;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        return googleSignInAccount.zzemm.equals(this.zzemm) && googleSignInAccount.zzacf().equals(zzacf());
    }

    @Nullable
    public Account getAccount() {
        return this.zzemh == null ? null : new Account(this.zzemh, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzemi;
    }

    @Nullable
    public String getEmail() {
        return this.zzemh;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzekr;
    }

    @Nullable
    public String getGivenName() {
        return this.zzekq;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zzeie);
    }

    @Nullable
    public String getId() {
        return this.zzbzd;
    }

    @Nullable
    public String getIdToken() {
        return this.zzelh;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzemj;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzemk;
    }

    public int hashCode() {
        return ((this.zzemm.hashCode() + 527) * 31) + zzacf().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zza(parcel, 2, getId(), false);
        zzbgo.zza(parcel, 3, getIdToken(), false);
        zzbgo.zza(parcel, 4, getEmail(), false);
        zzbgo.zza(parcel, 5, getDisplayName(), false);
        zzbgo.zza(parcel, 6, getPhotoUrl(), i, false);
        zzbgo.zza(parcel, 7, getServerAuthCode(), false);
        zzbgo.zza(parcel, 8, this.zzeml);
        zzbgo.zza(parcel, 9, this.zzemm, false);
        zzbgo.zzc(parcel, 10, this.zzeie, false);
        zzbgo.zza(parcel, 11, getGivenName(), false);
        zzbgo.zza(parcel, 12, getFamilyName(), false);
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    public final GoogleSignInAccount zza(Scope... scopeArr) {
        if (scopeArr != null) {
            Collections.addAll(this.zzemn, scopeArr);
        }
        return this;
    }

    @Hide
    public final boolean zza() {
        return zzemg.currentTimeMillis() / 1000 >= this.zzeml - 300;
    }

    @Hide
    @NonNull
    public final String zzace() {
        return this.zzemm;
    }

    @Hide
    @NonNull
    public final Set<Scope> zzacf() {
        Set<Scope> hashSet = new HashSet(this.zzeie);
        hashSet.addAll(this.zzemn);
        return hashSet;
    }

    @Hide
    public final String zzacg() {
        JSONObject toJsonObject = toJsonObject();
        toJsonObject.remove("serverAuthCode");
        return toJsonObject.toString();
    }
}
