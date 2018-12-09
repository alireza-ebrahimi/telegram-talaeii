package com.persianswitch.sdk.base.preference;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import com.persianswitch.sdk.base.security.DecryptionException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.security.SecureRandom;
import java.util.Random;

public class SqliteSecurePreference extends SqlitePreference {
    /* renamed from: a */
    private Encryption f7081a;

    public SqliteSecurePreference(String str, String str2, SQLiteOpenHelper sQLiteOpenHelper, String str3) {
        super(sQLiteOpenHelper, str3);
        this.f7081a = new DefaultEncryption(str, str2);
    }

    /* renamed from: a */
    public static void m10703a(IPreference iPreference) {
        iPreference.mo3258b("s_check", "asan_pardakht");
    }

    /* renamed from: b */
    public static void m10704b(IPreference iPreference) {
        String a = iPreference.mo3254a("s_check", null);
        if (!StringUtils.m10805a("asan_pardakht", a)) {
            throw new DecryptionException("Can not pass decryption check. Are you sure secure_token not changed ?", a == null);
        }
    }

    /* renamed from: f */
    public static String m10705f() {
        byte[] bArr = new byte[16];
        new Random().nextBytes(bArr);
        SecureRandom secureRandom = new SecureRandom(bArr);
        bArr = new byte[16];
        secureRandom.nextBytes(bArr);
        return Base64.encodeToString(bArr, 2);
    }

    /* renamed from: a */
    public String mo3260a(String str) {
        return this.f7081a.mo3250a(str);
    }

    /* renamed from: b */
    public String mo3261b(String str) {
        return this.f7081a.mo3251b(str);
    }
}
