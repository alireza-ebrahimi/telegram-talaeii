package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzo;
import com.google.android.gms.auth.api.signin.internal.zzq;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends zzbgl implements Optional, ReflectedParcelable {
    public static final Creator<GoogleSignInOptions> CREATOR = new zze();
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(SCOPE_GAMES_LITE, new Scope[0]).build();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    @Hide
    public static final Scope SCOPE_GAMES = new Scope(Scopes.GAMES);
    @Hide
    public static final Scope SCOPE_GAMES_LITE = new Scope("https://www.googleapis.com/auth/games_lite");
    @Hide
    public static final Scope zzemx = new Scope(Scopes.PROFILE);
    @Hide
    public static final Scope zzemy = new Scope("email");
    @Hide
    public static final Scope zzemz = new Scope("openid");
    private static Comparator<Scope> zzeng = new zzd();
    private int versionCode;
    private Account zzeho;
    private boolean zzela;
    private String zzelb;
    private final ArrayList<Scope> zzena;
    private final boolean zzenb;
    private final boolean zzenc;
    private String zzend;
    private ArrayList<zzo> zzene;
    private Map<Integer, zzo> zzenf;

    public static final class Builder {
        private Account zzeho;
        private boolean zzela;
        private String zzelb;
        private boolean zzenb;
        private boolean zzenc;
        private String zzend;
        private Set<Scope> zzenh = new HashSet();
        private Map<Integer, zzo> zzeni = new HashMap();

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzbq.checkNotNull(googleSignInOptions);
            this.zzenh = new HashSet(googleSignInOptions.zzena);
            this.zzenb = googleSignInOptions.zzenb;
            this.zzenc = googleSignInOptions.zzenc;
            this.zzela = googleSignInOptions.zzela;
            this.zzelb = googleSignInOptions.zzelb;
            this.zzeho = googleSignInOptions.zzeho;
            this.zzend = googleSignInOptions.zzend;
            this.zzeni = GoogleSignInOptions.zzx(googleSignInOptions.zzene);
        }

        private final String zzfc(String str) {
            zzbq.zzgv(str);
            boolean z = this.zzelb == null || this.zzelb.equals(str);
            zzbq.checkArgument(z, "two different server client ids provided");
            return str;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zzeni.containsKey(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()))) {
                throw new IllegalStateException("Only one extension per type may be added");
            }
            if (googleSignInOptionsExtension.getImpliedScopes() != null) {
                this.zzenh.addAll(googleSignInOptionsExtension.getImpliedScopes());
            }
            this.zzeni.put(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()), new zzo(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.zzenh.contains(GoogleSignInOptions.SCOPE_GAMES) && this.zzenh.contains(GoogleSignInOptions.SCOPE_GAMES_LITE)) {
                this.zzenh.remove(GoogleSignInOptions.SCOPE_GAMES_LITE);
            }
            if (this.zzela && (this.zzeho == null || !this.zzenh.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(new ArrayList(this.zzenh), this.zzeho, this.zzela, this.zzenb, this.zzenc, this.zzelb, this.zzend, this.zzeni);
        }

        public final Builder requestEmail() {
            this.zzenh.add(GoogleSignInOptions.zzemy);
            return this;
        }

        public final Builder requestId() {
            this.zzenh.add(GoogleSignInOptions.zzemz);
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zzela = true;
            this.zzelb = zzfc(str);
            return this;
        }

        public final Builder requestProfile() {
            this.zzenh.add(GoogleSignInOptions.zzemx);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.zzenh.add(scope);
            this.zzenh.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder requestServerAuthCode(String str, boolean z) {
            this.zzenb = true;
            this.zzelb = zzfc(str);
            this.zzenc = z;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzeho = new Account(zzbq.zzgv(str), "com.google");
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zzend = zzbq.zzgv(str);
            return this;
        }
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<zzo> arrayList2) {
        this(i, (ArrayList) arrayList, account, z, z2, z3, str, str2, zzx(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, zzo> map) {
        this.versionCode = i;
        this.zzena = arrayList;
        this.zzeho = account;
        this.zzela = z;
        this.zzenb = z2;
        this.zzenc = z3;
        this.zzelb = str;
        this.zzend = str2;
        this.zzene = new ArrayList(map.values());
        this.zzenf = map;
    }

    private final JSONObject toJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzena, zzeng);
            ArrayList arrayList = this.zzena;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                jSONArray.put(((Scope) obj).zzaie());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzeho != null) {
                jSONObject.put("accountName", this.zzeho.name);
            }
            jSONObject.put("idTokenRequested", this.zzela);
            jSONObject.put("forceCodeForRefreshToken", this.zzenc);
            jSONObject.put("serverAuthRequested", this.zzenb);
            if (!TextUtils.isEmpty(this.zzelb)) {
                jSONObject.put("serverClientId", this.zzelb);
            }
            if (!TextUtils.isEmpty(this.zzend)) {
                jSONObject.put("hostedDomain", this.zzend);
            }
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Hide
    public static GoogleSignInOptions zzfb(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Collection hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        Object optString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, new ArrayList(hashSet), !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    private static Map<Integer, zzo> zzx(@Nullable List<zzo> list) {
        Map<Integer, zzo> hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        for (zzo zzo : list) {
            hashMap.put(Integer.valueOf(zzo.getType()), zzo);
        }
        return hashMap;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
            if (this.zzene.size() > 0 || googleSignInOptions.zzene.size() > 0 || this.zzena.size() != googleSignInOptions.zzaci().size() || !this.zzena.containsAll(googleSignInOptions.zzaci())) {
                return false;
            }
            if (this.zzeho == null) {
                if (googleSignInOptions.zzeho != null) {
                    return false;
                }
            } else if (!this.zzeho.equals(googleSignInOptions.zzeho)) {
                return false;
            }
            if (TextUtils.isEmpty(this.zzelb)) {
                if (!TextUtils.isEmpty(googleSignInOptions.zzelb)) {
                    return false;
                }
            } else if (!this.zzelb.equals(googleSignInOptions.zzelb)) {
                return false;
            }
            return this.zzenc == googleSignInOptions.zzenc && this.zzela == googleSignInOptions.zzela && this.zzenb == googleSignInOptions.zzenb;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Hide
    public final Account getAccount() {
        return this.zzeho;
    }

    public Scope[] getScopeArray() {
        return (Scope[]) this.zzena.toArray(new Scope[this.zzena.size()]);
    }

    @Hide
    public final String getServerClientId() {
        return this.zzelb;
    }

    public int hashCode() {
        List arrayList = new ArrayList();
        ArrayList arrayList2 = this.zzena;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((Scope) obj).zzaie());
        }
        Collections.sort(arrayList);
        return new zzq().zzs(arrayList).zzs(this.zzeho).zzs(this.zzelb).zzav(this.zzenc).zzav(this.zzela).zzav(this.zzenb).zzacr();
    }

    @Hide
    public final boolean isIdTokenRequested() {
        return this.zzela;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zzc(parcel, 2, zzaci(), false);
        zzbgo.zza(parcel, 3, this.zzeho, i, false);
        zzbgo.zza(parcel, 4, this.zzela);
        zzbgo.zza(parcel, 5, this.zzenb);
        zzbgo.zza(parcel, 6, this.zzenc);
        zzbgo.zza(parcel, 7, this.zzelb, false);
        zzbgo.zza(parcel, 8, this.zzend, false);
        zzbgo.zzc(parcel, 9, this.zzene, false);
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    public final ArrayList<Scope> zzaci() {
        return new ArrayList(this.zzena);
    }

    @Hide
    public final boolean zzacj() {
        return this.zzenb;
    }

    @Hide
    public final String zzack() {
        return toJsonObject().toString();
    }
}
