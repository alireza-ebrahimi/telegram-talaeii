package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage extends zzbgl {
    public static final Creator<WebImage> CREATOR = new zze();
    private final int zzalt;
    private final int zzalu;
    private int zzehz;
    private final Uri zzfgr;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.zzehz = i;
        this.zzfgr = uri;
        this.zzalt = i2;
        this.zzalu = i3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    @Hide
    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(zzx(jSONObject), jSONObject.optInt(SettingsJsonConstants.ICON_WIDTH_KEY, 0), jSONObject.optInt("height", 0));
    }

    private static Uri zzx(JSONObject jSONObject) {
        Uri uri = null;
        if (jSONObject.has("url")) {
            try {
                uri = Uri.parse(jSONObject.getString("url"));
            } catch (JSONException e) {
            }
        }
        return uri;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        return zzbg.equal(this.zzfgr, webImage.zzfgr) && this.zzalt == webImage.zzalt && this.zzalu == webImage.zzalu;
    }

    public final int getHeight() {
        return this.zzalu;
    }

    public final Uri getUrl() {
        return this.zzfgr;
    }

    public final int getWidth() {
        return this.zzalt;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzfgr, Integer.valueOf(this.zzalt), Integer.valueOf(this.zzalu)});
    }

    @Hide
    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.zzfgr.toString());
            jSONObject.put(SettingsJsonConstants.ICON_WIDTH_KEY, this.zzalt);
            jSONObject.put("height", this.zzalu);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zzalt), Integer.valueOf(this.zzalu), this.zzfgr.toString()});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, getUrl(), i, false);
        zzbgo.zzc(parcel, 3, getWidth());
        zzbgo.zzc(parcel, 4, getHeight());
        zzbgo.zzai(parcel, zze);
    }
}
