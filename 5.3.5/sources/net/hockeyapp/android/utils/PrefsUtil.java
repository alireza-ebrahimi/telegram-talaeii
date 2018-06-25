package net.hockeyapp.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefsUtil {
    private SharedPreferences mFeedbackTokenPrefs;
    private Editor mFeedbackTokenPrefsEditor;
    private SharedPreferences mNameEmailSubjectPrefs;
    private Editor mNameEmailSubjectPrefsEditor;

    private static class PrefsUtilHolder {
        public static final PrefsUtil INSTANCE = new PrefsUtil();

        private PrefsUtilHolder() {
        }
    }

    private PrefsUtil() {
    }

    public static PrefsUtil getInstance() {
        return PrefsUtilHolder.INSTANCE;
    }

    public void saveFeedbackTokenToPrefs(Context context, String token) {
        if (context != null) {
            this.mFeedbackTokenPrefs = context.getSharedPreferences(Util.PREFS_FEEDBACK_TOKEN, 0);
            if (this.mFeedbackTokenPrefs != null) {
                this.mFeedbackTokenPrefsEditor = this.mFeedbackTokenPrefs.edit();
                this.mFeedbackTokenPrefsEditor.putString(Util.PREFS_KEY_FEEDBACK_TOKEN, token);
                this.mFeedbackTokenPrefsEditor.apply();
            }
        }
    }

    public String getFeedbackTokenFromPrefs(Context context) {
        if (context == null) {
            return null;
        }
        this.mFeedbackTokenPrefs = context.getSharedPreferences(Util.PREFS_FEEDBACK_TOKEN, 0);
        if (this.mFeedbackTokenPrefs != null) {
            return this.mFeedbackTokenPrefs.getString(Util.PREFS_KEY_FEEDBACK_TOKEN, null);
        }
        return null;
    }

    public void saveNameEmailSubjectToPrefs(Context context, String name, String email, String subject) {
        if (context != null) {
            this.mNameEmailSubjectPrefs = context.getSharedPreferences(Util.PREFS_NAME_EMAIL_SUBJECT, 0);
            if (this.mNameEmailSubjectPrefs != null) {
                this.mNameEmailSubjectPrefsEditor = this.mNameEmailSubjectPrefs.edit();
                if (name == null || email == null || subject == null) {
                    this.mNameEmailSubjectPrefsEditor.putString(Util.PREFS_KEY_NAME_EMAIL_SUBJECT, null);
                } else {
                    this.mNameEmailSubjectPrefsEditor.putString(Util.PREFS_KEY_NAME_EMAIL_SUBJECT, String.format("%s|%s|%s", new Object[]{name, email, subject}));
                }
                this.mNameEmailSubjectPrefsEditor.apply();
            }
        }
    }

    public String getNameEmailFromPrefs(Context context) {
        if (context == null) {
            return null;
        }
        this.mNameEmailSubjectPrefs = context.getSharedPreferences(Util.PREFS_NAME_EMAIL_SUBJECT, 0);
        if (this.mNameEmailSubjectPrefs != null) {
            return this.mNameEmailSubjectPrefs.getString(Util.PREFS_KEY_NAME_EMAIL_SUBJECT, null);
        }
        return null;
    }
}
