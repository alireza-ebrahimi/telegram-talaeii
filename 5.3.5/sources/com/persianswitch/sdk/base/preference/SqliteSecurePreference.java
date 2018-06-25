package com.persianswitch.sdk.base.preference;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import com.persianswitch.sdk.base.security.DecryptionException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.security.SecureRandom;
import java.util.Random;

public class SqliteSecurePreference extends SqlitePreference {
    public static final String ENCRYPTION_CHECK_PASS = "asan_pardakht";
    private Encryption mEncryption;

    public SqliteSecurePreference(String password, String saltAsBase64, SQLiteOpenHelper dbHelper, String preferenceName) {
        super(dbHelper, preferenceName);
        this.mEncryption = new DefaultEncryption(password, saltAsBase64);
    }

    public String serializeValue(String value) {
        return this.mEncryption.encrypt(value);
    }

    public String deserializeValue(String serializedValue) {
        return this.mEncryption.decrypt(serializedValue);
    }

    public static String generateSalt() {
        byte[] seed = new byte[16];
        new Random().nextBytes(seed);
        byte[] salt = new byte[16];
        new SecureRandom(seed).nextBytes(salt);
        return Base64.encodeToString(salt, 2);
    }

    public static void setEncryptionCheckPass(IPreference securePreference) {
        securePreference.putString("s_check", ENCRYPTION_CHECK_PASS);
    }

    public static void checkEncryption(IPreference securePreference) throws DecryptionException {
        String encryptedCheck = securePreference.getString("s_check", null);
        if (!StringUtils.isEquals(ENCRYPTION_CHECK_PASS, encryptedCheck)) {
            throw new DecryptionException("Can not pass decryption check. Are you sure secure_token not changed ?", encryptedCheck == null);
        }
    }
}
