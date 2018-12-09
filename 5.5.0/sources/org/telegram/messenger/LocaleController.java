package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Xml;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.time.FastDateFormat;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_langPackDifference;
import org.telegram.tgnet.TLRPC$TL_langPackLanguage;
import org.telegram.tgnet.TLRPC$TL_langPackString;
import org.telegram.tgnet.TLRPC$TL_langPackStringDeleted;
import org.telegram.tgnet.TLRPC$TL_langPackStringPluralized;
import org.telegram.tgnet.TLRPC$TL_langpack_getDifference;
import org.telegram.tgnet.TLRPC$TL_langpack_getLangPack;
import org.telegram.tgnet.TLRPC$TL_langpack_getLanguages;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.LangPackString;
import org.telegram.tgnet.TLRPC.User;
import org.xmlpull.v1.XmlPullParser;
import utils.p180b.C5316a;

public class LocaleController {
    private static volatile LocaleController Instance = null;
    static final int QUANTITY_FEW = 8;
    static final int QUANTITY_MANY = 16;
    static final int QUANTITY_ONE = 2;
    static final int QUANTITY_OTHER = 0;
    static final int QUANTITY_TWO = 4;
    static final int QUANTITY_ZERO = 1;
    private static boolean is24HourFormat = false;
    public static boolean isRTL = false;
    public static int nameDisplayOrder = 1;
    private HashMap<String, PluralRules> allRules = new HashMap();
    private boolean changingConfiguration = false;
    public FastDateFormat chatDate;
    public FastDateFormat chatFullDate;
    private HashMap<String, String> currencyValues;
    private Locale currentLocale;
    private LocaleInfo currentLocaleInfo;
    private PluralRules currentPluralRules;
    public FastDateFormat formatterBannedUntil;
    public FastDateFormat formatterBannedUntilThisYear;
    public FastDateFormat formatterDay;
    public FastDateFormat formatterMonth;
    public FastDateFormat formatterMonthYear;
    public FastDateFormat formatterStats;
    public FastDateFormat formatterWeek;
    public FastDateFormat formatterYear;
    public FastDateFormat formatterYearMax;
    private String languageOverride;
    public ArrayList<LocaleInfo> languages = new ArrayList();
    public HashMap<String, LocaleInfo> languagesDict = new HashMap();
    private boolean loadingRemoteLanguages;
    private HashMap<String, String> localeValues = new HashMap();
    private ArrayList<LocaleInfo> otherLanguages = new ArrayList();
    private boolean reloadLastFile;
    public ArrayList<LocaleInfo> remoteLanguages = new ArrayList();
    private Locale systemDefaultLocale;
    private HashMap<String, String> translitChars;

    /* renamed from: org.telegram.messenger.LocaleController$1 */
    class C30991 implements Runnable {
        C30991() {
        }

        public void run() {
            LocaleController.this.loadRemoteLanguages();
        }
    }

    /* renamed from: org.telegram.messenger.LocaleController$3 */
    class C31013 implements Runnable {
        C31013() {
        }

        public void run() {
            LocaleController.this.reloadCurrentRemoteLocale();
        }
    }

    /* renamed from: org.telegram.messenger.LocaleController$5 */
    class C31045 implements RequestDelegate {
        C31045() {
        }

        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        int i;
                        LocaleController.this.loadingRemoteLanguages = false;
                        TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                        HashMap hashMap = new HashMap();
                        LocaleController.this.remoteLanguages.clear();
                        for (i = 0; i < tLRPC$Vector.objects.size(); i++) {
                            TLRPC$TL_langPackLanguage tLRPC$TL_langPackLanguage = (TLRPC$TL_langPackLanguage) tLRPC$Vector.objects.get(i);
                            FileLog.m13725d("loaded lang " + tLRPC$TL_langPackLanguage.name);
                            LocaleInfo localeInfo = new LocaleInfo();
                            localeInfo.nameEnglish = tLRPC$TL_langPackLanguage.name;
                            localeInfo.name = tLRPC$TL_langPackLanguage.native_name;
                            localeInfo.shortName = tLRPC$TL_langPackLanguage.lang_code.replace('-', '_').toLowerCase();
                            localeInfo.pathToFile = "remote";
                            Object access$200 = LocaleController.this.getLanguageFromDict(localeInfo.getKey());
                            if (access$200 == null) {
                                LocaleController.this.languages.add(localeInfo);
                                LocaleController.this.languagesDict.put(localeInfo.getKey(), localeInfo);
                                access$200 = localeInfo;
                            } else {
                                access$200.nameEnglish = localeInfo.nameEnglish;
                                access$200.name = localeInfo.name;
                                access$200.pathToFile = localeInfo.pathToFile;
                            }
                            LocaleController.this.remoteLanguages.add(localeInfo);
                            hashMap.put(localeInfo.getKey(), access$200);
                        }
                        i = 0;
                        while (i < LocaleController.this.languages.size()) {
                            LocaleInfo localeInfo2 = (LocaleInfo) LocaleController.this.languages.get(i);
                            if (!localeInfo2.isBuiltIn() && localeInfo2.isRemote() && ((LocaleInfo) hashMap.get(localeInfo2.getKey())) == null) {
                                FileLog.m13725d("remove lang " + localeInfo2.getKey());
                                LocaleController.this.languages.remove(i);
                                LocaleController.this.languagesDict.remove(localeInfo2.getKey());
                                i--;
                                if (localeInfo2 == LocaleController.this.currentLocaleInfo) {
                                    if (LocaleController.this.systemDefaultLocale.getLanguage() != null) {
                                        localeInfo2 = LocaleController.this.getLanguageFromDict(LocaleController.this.systemDefaultLocale.getLanguage());
                                    }
                                    if (localeInfo2 == null) {
                                        localeInfo2 = LocaleController.this.getLanguageFromDict(LocaleController.this.getLocaleString(LocaleController.this.systemDefaultLocale));
                                    }
                                    if (localeInfo2 == null) {
                                        localeInfo2 = LocaleController.this.getLanguageFromDict("en");
                                    }
                                    LocaleController.this.applyLanguage(localeInfo2, true, false);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInterface, new Object[0]);
                                }
                            }
                            i++;
                        }
                        LocaleController.this.saveOtherLanguages();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.suggestedLangpack, new Object[0]);
                        LocaleController.this.applyLanguage(LocaleController.this.currentLocaleInfo, true, false);
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.LocaleController$6 */
    class C31066 implements RequestDelegate {
        C31066() {
        }

        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LocaleController.this.saveRemoteLocaleStrings((TLRPC$TL_langPackDifference) tLObject);
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.LocaleController$7 */
    class C31087 implements RequestDelegate {
        C31087() {
        }

        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LocaleController.this.saveRemoteLocaleStrings((TLRPC$TL_langPackDifference) tLObject);
                    }
                });
            }
        }
    }

    public static class LocaleInfo {
        public boolean builtIn;
        public String name;
        public String nameEnglish;
        public String pathToFile;
        public String shortName;
        public int version;

        public static LocaleInfo createWithString(String str) {
            LocaleInfo localeInfo = null;
            if (!(str == null || str.length() == 0)) {
                String[] split = str.split("\\|");
                if (split.length >= 4) {
                    localeInfo = new LocaleInfo();
                    localeInfo.name = split[0];
                    localeInfo.nameEnglish = split[1];
                    localeInfo.shortName = split[2].toLowerCase();
                    localeInfo.pathToFile = split[3];
                    if (split.length >= 5) {
                        localeInfo.version = Utilities.parseInt(split[4]).intValue();
                    }
                }
            }
            return localeInfo;
        }

        public String getKey() {
            return (this.pathToFile == null || "remote".equals(this.pathToFile)) ? this.shortName : "local_" + this.shortName;
        }

        public File getPathToFile() {
            return isRemote() ? new File(ApplicationLoader.getFilesDirFixed(), "remote_" + this.shortName + ".xml") : !TextUtils.isEmpty(this.pathToFile) ? new File(this.pathToFile) : null;
        }

        public String getSaveString() {
            return this.name + "|" + this.nameEnglish + "|" + this.shortName + "|" + this.pathToFile + "|" + this.version;
        }

        public boolean isBuiltIn() {
            return this.builtIn;
        }

        public boolean isLocal() {
            return (TextUtils.isEmpty(this.pathToFile) || isRemote()) ? false : true;
        }

        public boolean isRemote() {
            return "remote".equals(this.pathToFile);
        }
    }

    public static abstract class PluralRules {
        abstract int quantityForNumber(int i);
    }

    public static class PluralRules_Arabic extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            return i == 0 ? 1 : i == 1 ? 2 : i == 2 ? 4 : (i2 < 3 || i2 > 10) ? (i2 < 11 || i2 > 99) ? 0 : 16 : 8;
        }
    }

    public static class PluralRules_Balkan extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            int i3 = i % 10;
            return (i3 != 1 || i2 == 11) ? (i3 < 2 || i3 > 4 || (i2 >= 12 && i2 <= 14)) ? (i3 == 0 || ((i3 >= 5 && i3 <= 9) || (i2 >= 11 && i2 <= 14))) ? 16 : 0 : 8 : 2;
        }
    }

    public static class PluralRules_Breton extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 0 ? 1 : i == 1 ? 2 : i == 2 ? 4 : i == 3 ? 8 : i == 6 ? 16 : 0;
        }
    }

    public static class PluralRules_Czech extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 1 ? 2 : (i < 2 || i > 4) ? 0 : 8;
        }
    }

    public static class PluralRules_French extends PluralRules {
        public int quantityForNumber(int i) {
            return (i < 0 || i >= 2) ? 0 : 2;
        }
    }

    public static class PluralRules_Langi extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 0 ? 1 : (i <= 0 || i >= 2) ? 0 : 2;
        }
    }

    public static class PluralRules_Latvian extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 0 ? 1 : (i % 10 != 1 || i % 100 == 11) ? 0 : 2;
        }
    }

    public static class PluralRules_Lithuanian extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            int i3 = i % 10;
            return (i3 != 1 || (i2 >= 11 && i2 <= 19)) ? (i3 < 2 || i3 > 9 || (i2 >= 11 && i2 <= 19)) ? 0 : 8 : 2;
        }
    }

    public static class PluralRules_Macedonian extends PluralRules {
        public int quantityForNumber(int i) {
            return (i % 10 != 1 || i == 11) ? 0 : 2;
        }
    }

    public static class PluralRules_Maltese extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            return i == 1 ? 2 : (i == 0 || (i2 >= 2 && i2 <= 10)) ? 8 : (i2 < 11 || i2 > 19) ? 0 : 16;
        }
    }

    public static class PluralRules_None extends PluralRules {
        public int quantityForNumber(int i) {
            return 0;
        }
    }

    public static class PluralRules_One extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 1 ? 2 : 0;
        }
    }

    public static class PluralRules_Polish extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            int i3 = i % 10;
            return i == 1 ? 2 : (i3 < 2 || i3 > 4 || ((i2 >= 12 && i2 <= 14) || (i2 >= 22 && i2 <= 24))) ? 0 : 8;
        }
    }

    public static class PluralRules_Romanian extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            return i == 1 ? 2 : (i == 0 || (i2 >= 1 && i2 <= 19)) ? 8 : 0;
        }
    }

    public static class PluralRules_Slovenian extends PluralRules {
        public int quantityForNumber(int i) {
            int i2 = i % 100;
            return i2 == 1 ? 2 : i2 == 2 ? 4 : (i2 < 3 || i2 > 4) ? 0 : 8;
        }
    }

    public static class PluralRules_Tachelhit extends PluralRules {
        public int quantityForNumber(int i) {
            return (i < 0 || i > 1) ? (i < 2 || i > 10) ? 0 : 8 : 2;
        }
    }

    public static class PluralRules_Two extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 1 ? 2 : i == 2 ? 4 : 0;
        }
    }

    public static class PluralRules_Welsh extends PluralRules {
        public int quantityForNumber(int i) {
            return i == 0 ? 1 : i == 1 ? 2 : i == 2 ? 4 : i == 3 ? 8 : i == 6 ? 16 : 0;
        }
    }

    public static class PluralRules_Zero extends PluralRules {
        public int quantityForNumber(int i) {
            return (i == 0 || i == 1) ? 2 : 0;
        }
    }

    private class TimeZoneChangedReceiver extends BroadcastReceiver {

        /* renamed from: org.telegram.messenger.LocaleController$TimeZoneChangedReceiver$1 */
        class C31091 implements Runnable {
            C31091() {
            }

            public void run() {
                if (!LocaleController.this.formatterMonth.getTimeZone().equals(TimeZone.getDefault())) {
                    LocaleController.getInstance().recreateFormatters();
                }
            }
        }

        private TimeZoneChangedReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            ApplicationLoader.applicationHandler.post(new C31091());
        }
    }

    public LocaleController() {
        int i;
        boolean z = false;
        addRules(new String[]{"bem", "brx", "da", "de", "el", "en", "eo", "es", "et", "fi", "fo", "gl", "he", "iw", "it", "nb", "nl", "nn", "no", "sv", "af", "bg", "bn", "ca", "eu", "fur", "fy", "gu", "ha", "is", "ku", "lb", "ml", "mr", "nah", "ne", "om", "or", "pa", "pap", "ps", "so", "sq", "sw", "ta", "te", "tk", "ur", "zu", "mn", "gsw", "chr", "rm", "pt", "an", "ast"}, new PluralRules_One());
        addRules(new String[]{"cs", "sk"}, new PluralRules_Czech());
        addRules(new String[]{"ff", "fr", "kab"}, new PluralRules_French());
        addRules(new String[]{"hr", "ru", "sr", "uk", "be", "bs", "sh"}, new PluralRules_Balkan());
        addRules(new String[]{"lv"}, new PluralRules_Latvian());
        addRules(new String[]{"lt"}, new PluralRules_Lithuanian());
        addRules(new String[]{"pl"}, new PluralRules_Polish());
        addRules(new String[]{"ro", "mo"}, new PluralRules_Romanian());
        addRules(new String[]{"sl"}, new PluralRules_Slovenian());
        addRules(new String[]{"ar"}, new PluralRules_Arabic());
        addRules(new String[]{"mk"}, new PluralRules_Macedonian());
        addRules(new String[]{"cy"}, new PluralRules_Welsh());
        addRules(new String[]{TtmlNode.TAG_BR}, new PluralRules_Breton());
        addRules(new String[]{"lag"}, new PluralRules_Langi());
        addRules(new String[]{"shi"}, new PluralRules_Tachelhit());
        addRules(new String[]{"mt"}, new PluralRules_Maltese());
        addRules(new String[]{"ga", "se", "sma", "smi", "smj", "smn", "sms"}, new PluralRules_Two());
        addRules(new String[]{"ak", "am", "bh", "fil", "tl", "guw", "hi", "ln", "mg", "nso", "ti", "wa"}, new PluralRules_Zero());
        addRules(new String[]{"az", "bm", "fa", "ig", "hu", "ja", "kde", "kea", "ko", "my", "ses", "sg", "to", "tr", "vi", "wo", "yo", "zh", "bo", "dz", TtmlNode.ATTR_ID, "jv", "jw", "ka", "km", "kn", "ms", "th", "in"}, new PluralRules_None());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.name = "English";
        localeInfo.nameEnglish = "English";
        localeInfo.shortName = "en";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "پارسی";
        localeInfo.nameEnglish = "Parsi";
        localeInfo.shortName = "fa";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "Italiano";
        localeInfo.nameEnglish = "Italian";
        localeInfo.shortName = "it";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "Español";
        localeInfo.nameEnglish = "Spanish";
        localeInfo.shortName = "es";
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "Deutsch";
        localeInfo.nameEnglish = "German";
        localeInfo.shortName = "de";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "Nederlands";
        localeInfo.nameEnglish = "Dutch";
        localeInfo.shortName = "nl";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "العربية";
        localeInfo.nameEnglish = "Arabic";
        localeInfo.shortName = "ar";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "Português (Brasil)";
        localeInfo.nameEnglish = "Portuguese (Brazil)";
        localeInfo.shortName = "pt_br";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleInfo();
        localeInfo.name = "한국어";
        localeInfo.nameEnglish = "Korean";
        localeInfo.shortName = "ko";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        loadOtherLanguages();
        if (this.remoteLanguages.isEmpty()) {
            AndroidUtilities.runOnUIThread(new C30991());
        }
        for (i = 0; i < this.otherLanguages.size(); i++) {
            localeInfo = (LocaleInfo) this.otherLanguages.get(i);
            this.languages.add(localeInfo);
            this.languagesDict.put(localeInfo.getKey(), localeInfo);
        }
        for (i = 0; i < this.remoteLanguages.size(); i++) {
            localeInfo = (LocaleInfo) this.remoteLanguages.get(i);
            LocaleInfo languageFromDict = getLanguageFromDict(localeInfo.getKey());
            if (languageFromDict != null) {
                languageFromDict.pathToFile = localeInfo.pathToFile;
                languageFromDict.version = localeInfo.version;
            } else {
                this.languages.add(localeInfo);
                this.languagesDict.put(localeInfo.getKey(), localeInfo);
            }
        }
        this.systemDefaultLocale = Locale.getDefault();
        is24HourFormat = DateFormat.is24HourFormat(ApplicationLoader.applicationContext);
        try {
            String string = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getString("language", null);
            if (string != null) {
                if (string.contentEquals("fa_IR")) {
                    string = "fa";
                }
                localeInfo = getLanguageFromDict(string);
                if (localeInfo != null) {
                    z = true;
                }
            } else {
                localeInfo = null;
            }
            if (localeInfo == null && this.systemDefaultLocale.getLanguage() != null) {
                localeInfo = getLanguageFromDict(this.systemDefaultLocale.getLanguage());
            }
            if (localeInfo == null) {
                localeInfo = getLanguageFromDict(getLocaleString(this.systemDefaultLocale));
                if (localeInfo == null) {
                    localeInfo = getLanguageFromDict("en");
                }
            }
            applyLanguage(localeInfo, z, true);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        try {
            ApplicationLoader.applicationContext.registerReceiver(new TimeZoneChangedReceiver(), new IntentFilter("android.intent.action.TIMEZONE_CHANGED"));
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
    }

    private void addRules(String[] strArr, PluralRules pluralRules) {
        for (Object put : strArr) {
            this.allRules.put(put, pluralRules);
        }
    }

    private void applyRemoteLanguage(LocaleInfo localeInfo, TLRPC$TL_langPackLanguage tLRPC$TL_langPackLanguage, boolean z) {
        if (localeInfo != null || tLRPC$TL_langPackLanguage != null) {
            if (localeInfo != null && !localeInfo.isRemote()) {
                return;
            }
            TLObject tLRPC$TL_langpack_getLangPack;
            if (localeInfo.version == 0 || z) {
                ConnectionsManager.getInstance().setLangCode(localeInfo != null ? localeInfo.shortName : tLRPC$TL_langPackLanguage.lang_code);
                tLRPC$TL_langpack_getLangPack = new TLRPC$TL_langpack_getLangPack();
                if (tLRPC$TL_langPackLanguage == null) {
                    tLRPC$TL_langpack_getLangPack.lang_code = localeInfo.shortName;
                } else {
                    tLRPC$TL_langpack_getLangPack.lang_code = tLRPC$TL_langPackLanguage.lang_code;
                }
                tLRPC$TL_langpack_getLangPack.lang_code = tLRPC$TL_langpack_getLangPack.lang_code.replace("_", "-");
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_langpack_getLangPack, new C31087(), 8);
                return;
            }
            tLRPC$TL_langpack_getLangPack = new TLRPC$TL_langpack_getDifference();
            tLRPC$TL_langpack_getLangPack.from_version = localeInfo.version;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_langpack_getLangPack, new C31066(), 8);
        }
    }

    private FastDateFormat createFormatter(Locale locale, String str, String str2) {
        if (str == null || str.length() == 0) {
            str = str2;
        }
        try {
            return FastDateFormat.getInstance(str, locale);
        } catch (Exception e) {
            return FastDateFormat.getInstance(str2, locale);
        }
    }

    private String escapeString(String str) {
        return str.contains("[CDATA") ? str : str.replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
    }

    public static String formatCallDuration(int i) {
        if (i <= 3600) {
            return i > 60 ? formatPluralString("Minutes", i / 60) : formatPluralString("Seconds", i);
        } else {
            String formatPluralString = formatPluralString("Hours", i / 3600);
            int i2 = (i % 3600) / 60;
            return i2 > 0 ? formatPluralString + ", " + formatPluralString("Minutes", i2) : formatPluralString;
        }
    }

    public static String formatDate(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            int i2 = instance.get(1);
            instance.setTimeInMillis(j2);
            int i3 = instance.get(6);
            int i4 = instance.get(1);
            return (i3 == i && i2 == i4) ? getInstance().formatterDay.format(new Date(j2)) : (i3 + 1 == i && i2 == i4) ? getString("Yesterday", R.string.Yesterday) : Math.abs(System.currentTimeMillis() - j2) < 31536000000L ? getInstance().formatterMonth.format(new Date(j2)) : getInstance().formatterYear.format(new Date(j2));
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR: formatDate";
        }
    }

    public static String formatDateAudio(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            int i2 = instance.get(1);
            instance.setTimeInMillis(j2);
            int i3 = instance.get(6);
            int i4 = instance.get(1);
            if (i3 == i && i2 == i4) {
                return String.format("%s %s", new Object[]{getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (i3 + 1 == i && i2 == i4) {
                return String.format("%s %s", new Object[]{getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (Math.abs(System.currentTimeMillis() - j2) < 31536000000L) {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
            } else {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    public static String formatDateCallLog(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            int i2 = instance.get(1);
            instance.setTimeInMillis(j2);
            int i3 = instance.get(6);
            int i4 = instance.get(1);
            if (i3 == i && i2 == i4) {
                return getInstance().formatterDay.format(new Date(j2));
            }
            if (i3 + 1 == i && i2 == i4) {
                return String.format("%s %s", new Object[]{getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (Math.abs(System.currentTimeMillis() - j2) < 31536000000L) {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().chatDate.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
            } else {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().chatFullDate.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    public static String formatDateChat(long j) {
        try {
            long j2 = 1000 * j;
            Calendar.getInstance().setTimeInMillis(j2);
            return Math.abs(System.currentTimeMillis() - j2) < 31536000000L ? getInstance().chatDate.format(j2) : getInstance().chatFullDate.format(j2);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR: formatDateChat";
        }
    }

    public static String formatDateForBan(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(1);
            instance.setTimeInMillis(j2);
            return i == instance.get(1) ? getInstance().formatterBannedUntilThisYear.format(new Date(j2)) : getInstance().formatterBannedUntil.format(new Date(j2));
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    public static String formatDateOnline(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            int i2 = instance.get(1);
            instance.setTimeInMillis(j2);
            int i3 = instance.get(6);
            int i4 = instance.get(1);
            if (i3 == i && i2 == i4) {
                return String.format("%s %s %s", new Object[]{getString("LastSeen", R.string.LastSeen), getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (i3 + 1 == i && i2 == i4) {
                return String.format("%s %s %s", new Object[]{getString("LastSeen", R.string.LastSeen), getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (Math.abs(System.currentTimeMillis() - j2) < 31536000000L) {
                r0 = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
                return String.format("%s %s", new Object[]{getString("LastSeenDate", R.string.LastSeenDate), r0});
            } else {
                r0 = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
                return String.format("%s %s", new Object[]{getString("LastSeenDate", R.string.LastSeenDate), r0});
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    public static String formatLocationLeftTime(int i) {
        int i2 = 1;
        int i3 = (i / 60) / 60;
        int i4 = i - ((i3 * 60) * 60);
        int i5 = i4 / 60;
        i4 -= i5 * 60;
        Object[] objArr;
        if (i3 != 0) {
            String str = "%dh";
            objArr = new Object[1];
            if (i5 <= 30) {
                i2 = 0;
            }
            objArr[0] = Integer.valueOf(i2 + i3);
            return String.format(str, objArr);
        } else if (i5 != 0) {
            String str2 = "%d";
            objArr = new Object[1];
            if (i4 <= 30) {
                i2 = 0;
            }
            objArr[0] = Integer.valueOf(i2 + i5);
            return String.format(str2, objArr);
        } else {
            return String.format("%d", new Object[]{Integer.valueOf(i4)});
        }
    }

    public static String formatLocationUpdateDate(long j) {
        long j2 = 1000 * j;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            int i2 = instance.get(1);
            instance.setTimeInMillis(j2);
            int i3 = instance.get(6);
            int i4 = instance.get(1);
            if (i3 == i && i2 == i4) {
                i4 = ((int) (((long) ConnectionsManager.getInstance().getCurrentTime()) - (j2 / 1000))) / 60;
                if (i4 < 1) {
                    return getString("LocationUpdatedJustNow", R.string.LocationUpdatedJustNow);
                }
                if (i4 < 60) {
                    return formatPluralString("UpdatedMinutes", i4);
                }
                return String.format("%s %s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (i3 + 1 == i && i2 == i4) {
                return String.format("%s %s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(j2))});
            } else if (Math.abs(System.currentTimeMillis() - j2) < 31536000000L) {
                r0 = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
                return String.format("%s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), r0});
            } else {
                r0 = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(j2)), getInstance().formatterDay.format(new Date(j2)));
                return String.format("%s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), r0});
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    public static String formatPluralString(String str, int i) {
        if (str == null || str.length() == 0 || getInstance().currentPluralRules == null) {
            return "LOC_ERR:" + str;
        }
        String str2 = str + "_" + getInstance().stringForQuantity(getInstance().currentPluralRules.quantityForNumber(i));
        return formatString(str2, ApplicationLoader.applicationContext.getResources().getIdentifier(str2, "string", ApplicationLoader.applicationContext.getPackageName()), Integer.valueOf(i));
    }

    public static String formatShortNumber(int i, int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder();
        int i2 = 0;
        while (i / 1000 > 0) {
            stringBuilder.append("K");
            i2 = (i % 1000) / 100;
            i /= 1000;
        }
        if (iArr != null) {
            double d = ((double) i) + (((double) i2) / 10.0d);
            for (int i3 = 0; i3 < stringBuilder.length(); i3++) {
                d *= 1000.0d;
            }
            iArr[0] = (int) d;
        }
        if (i2 == 0 || stringBuilder.length() <= 0) {
            if (stringBuilder.length() == 2) {
                return String.format(Locale.US, "%dM", new Object[]{Integer.valueOf(i)});
            }
            return String.format(Locale.US, "%d%s", new Object[]{Integer.valueOf(i), stringBuilder.toString()});
        } else if (stringBuilder.length() == 2) {
            return String.format(Locale.US, "%d.%dM", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        } else {
            return String.format(Locale.US, "%d.%d%s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), stringBuilder.toString()});
        }
    }

    public static String formatString(String str, int i, Object... objArr) {
        try {
            String str2 = (String) getInstance().localeValues.get(str);
            if (str2 == null) {
                str2 = ApplicationLoader.applicationContext.getString(i);
            }
            return getInstance().currentLocale != null ? String.format(getInstance().currentLocale, str2, objArr) : String.format(str2, objArr);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR: " + str;
        }
    }

    public static String formatStringSimple(String str, Object... objArr) {
        try {
            return getInstance().currentLocale != null ? String.format(getInstance().currentLocale, str, objArr) : String.format(str, objArr);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR: " + str;
        }
    }

    public static String formatTTLString(int i) {
        if (i < 60) {
            return formatPluralString("Seconds", i);
        }
        if (i < 3600) {
            return formatPluralString("Minutes", i / 60);
        }
        if (i < 86400) {
            return formatPluralString("Hours", (i / 60) / 60);
        }
        if (i < 604800) {
            return formatPluralString("Days", ((i / 60) / 60) / 24);
        }
        int i2 = ((i / 60) / 60) / 24;
        if (i % 7 == 0) {
            return formatPluralString("Weeks", i2 / 7);
        }
        return String.format("%s %s", new Object[]{formatPluralString("Weeks", i2 / 7), formatPluralString("Days", i2 % 7)});
    }

    public static String formatUserStatus(User user) {
        if (!(user == null || user.status == null || user.status.expires != 0)) {
            if (user.status instanceof TLRPC$TL_userStatusRecently) {
                user.status.expires = -100;
            } else if (user.status instanceof TLRPC$TL_userStatusLastWeek) {
                user.status.expires = -101;
            } else if (user.status instanceof TLRPC$TL_userStatusLastMonth) {
                user.status.expires = -102;
            }
        }
        if (user != null && user.status != null && user.status.expires <= 0 && MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(user.id))) {
            return getString("Online", R.string.Online);
        }
        if (user == null || user.status == null || user.status.expires == 0 || UserObject.isDeleted(user) || (user instanceof TLRPC$TL_userEmpty)) {
            return getString("ALongTimeAgo", R.string.ALongTimeAgo);
        }
        return user.status.expires > ConnectionsManager.getInstance().getCurrentTime() ? getString("Online", R.string.Online) : user.status.expires == -1 ? getString("Invisible", R.string.Invisible) : user.status.expires == -100 ? getString("Lately", R.string.Lately) : user.status.expires == -101 ? getString("WithinAWeek", R.string.WithinAWeek) : user.status.expires == -102 ? getString("WithinAMonth", R.string.WithinAMonth) : formatDateOnline((long) user.status.expires);
    }

    public static String getCurrentLanguageName() {
        return getString("LanguageName", R.string.LanguageName);
    }

    public static LocaleController getInstance() {
        LocaleController localeController = Instance;
        if (localeController == null) {
            synchronized (LocaleController.class) {
                localeController = Instance;
                if (localeController == null) {
                    localeController = new LocaleController();
                    Instance = localeController;
                }
            }
        }
        return localeController;
    }

    private LocaleInfo getLanguageFromDict(String str) {
        return str == null ? null : (LocaleInfo) this.languagesDict.get(str.toLowerCase().replace("-", "_"));
    }

    public static String getLocaleAlias(String str) {
        if (str == null) {
            return null;
        }
        Object obj = -1;
        switch (str.hashCode()) {
            case 3325:
                if (str.equals("he")) {
                    obj = 7;
                    break;
                }
                break;
            case 3355:
                if (str.equals(TtmlNode.ATTR_ID)) {
                    obj = 6;
                    break;
                }
                break;
            case 3365:
                if (str.equals("in")) {
                    obj = null;
                    break;
                }
                break;
            case 3374:
                if (str.equals("iw")) {
                    obj = 1;
                    break;
                }
                break;
            case 3391:
                if (str.equals("ji")) {
                    obj = 5;
                    break;
                }
                break;
            case 3404:
                if (str.equals("jv")) {
                    obj = 8;
                    break;
                }
                break;
            case 3405:
                if (str.equals("jw")) {
                    obj = 2;
                    break;
                }
                break;
            case 3508:
                if (str.equals("nb")) {
                    obj = 9;
                    break;
                }
                break;
            case 3521:
                if (str.equals("no")) {
                    obj = 3;
                    break;
                }
                break;
            case 3704:
                if (str.equals("tl")) {
                    obj = 4;
                    break;
                }
                break;
            case 3856:
                if (str.equals("yi")) {
                    obj = 11;
                    break;
                }
                break;
            case 101385:
                if (str.equals("fil")) {
                    obj = 10;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return TtmlNode.ATTR_ID;
            case 1:
                return "he";
            case 2:
                return "jv";
            case 3:
                return "nb";
            case 4:
                return "fil";
            case 5:
                return "yi";
            case 6:
                return "in";
            case 7:
                return "iw";
            case 8:
                return "jw";
            case 9:
                return "no";
            case 10:
                return "tl";
            case 11:
                return "ji";
            default:
                return null;
        }
    }

    private HashMap<String, String> getLocaleFileStrings(File file) {
        return getLocaleFileStrings(file, false);
    }

    private HashMap<String, String> getLocaleFileStrings(File file, boolean z) {
        Throwable e;
        FileInputStream fileInputStream = null;
        this.reloadLastFile = false;
        FileInputStream fileInputStream2;
        try {
            if (file.exists()) {
                HashMap<String, String> hashMap = new HashMap();
                XmlPullParser newPullParser = Xml.newPullParser();
                fileInputStream2 = new FileInputStream(file);
                try {
                    newPullParser.setInput(fileInputStream2, C3446C.UTF8_NAME);
                    String str = null;
                    String str2 = null;
                    String str3 = null;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        if (eventType == 2) {
                            str2 = newPullParser.getName();
                            if (newPullParser.getAttributeCount() > 0) {
                                str3 = newPullParser.getAttributeValue(0);
                            }
                        } else if (eventType == 4) {
                            if (str3 != null) {
                                str = newPullParser.getText();
                                if (str != null) {
                                    str = str.trim();
                                    if (z) {
                                        str = str.replace("<", "&lt;").replace(">", "&gt;").replace("'", "\\'").replace("& ", "&amp; ");
                                    } else {
                                        String replace = str.replace("\\n", "\n").replace("\\", TtmlNode.ANONYMOUS_REGION_ID);
                                        str = replace.replace("&lt;", "<");
                                        if (!(this.reloadLastFile || str.equals(replace))) {
                                            this.reloadLastFile = true;
                                        }
                                    }
                                }
                            }
                        } else if (eventType == 3) {
                            str3 = null;
                            str = null;
                            str2 = null;
                        }
                        if (!(str2 == null || !str2.equals("string") || str == null || str3 == null || str.length() == 0 || str3.length() == 0)) {
                            hashMap.put(str3, str);
                            str3 = null;
                            str = null;
                            str2 = null;
                        }
                    }
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Throwable e2) {
                            FileLog.m13728e(e2);
                        }
                    }
                    return hashMap;
                } catch (Exception e3) {
                    e2 = e3;
                    fileInputStream = fileInputStream2;
                    try {
                        FileLog.m13728e(e2);
                        this.reloadLastFile = true;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable e22) {
                                FileLog.m13728e(e22);
                            }
                        }
                        return new HashMap();
                    } catch (Throwable th) {
                        e22 = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (Throwable e4) {
                                FileLog.m13728e(e4);
                            }
                        }
                        throw e22;
                    }
                } catch (Throwable th2) {
                    e22 = th2;
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    throw e22;
                }
            }
            HashMap<String, String> hashMap2 = new HashMap();
            if (null == null) {
                return hashMap2;
            }
            try {
                fileInputStream.close();
                return hashMap2;
            } catch (Throwable e42) {
                FileLog.m13728e(e42);
                return hashMap2;
            }
        } catch (Exception e5) {
            e22 = e5;
            fileInputStream = null;
            FileLog.m13728e(e22);
            this.reloadLastFile = true;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return new HashMap();
        } catch (Throwable th3) {
            e22 = th3;
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw e22;
        }
    }

    private String getLocaleString(Locale locale) {
        if (locale == null) {
            return "en";
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        if (language.length() == 0 && country.length() == 0) {
            return "en";
        }
        StringBuilder stringBuilder = new StringBuilder(11);
        stringBuilder.append(language);
        if (country.length() > 0 || variant.length() > 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(country);
        if (variant.length() > 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(variant);
        return stringBuilder.toString();
    }

    public static String getLocaleStringIso639() {
        Locale locale = getInstance().currentLocale;
        if (locale == null) {
            return "en";
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        if (language.length() == 0 && country.length() == 0) {
            return "en";
        }
        StringBuilder stringBuilder = new StringBuilder(11);
        stringBuilder.append(language);
        if (country.length() > 0 || variant.length() > 0) {
            stringBuilder.append('-');
        }
        stringBuilder.append(country);
        if (variant.length() > 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(variant);
        return stringBuilder.toString();
    }

    public static String getPluralString(String str, int i) {
        if (str == null || str.length() == 0 || getInstance().currentPluralRules == null) {
            return "LOC_ERR:" + str;
        }
        String str2 = str + "_" + getInstance().stringForQuantity(getInstance().currentPluralRules.quantityForNumber(i));
        return getString(str2, ApplicationLoader.applicationContext.getResources().getIdentifier(str2, "string", ApplicationLoader.applicationContext.getPackageName()));
    }

    public static String getString(String str, int i) {
        return getInstance().getStringInternal(str, i);
    }

    private String getStringInternal(String str, int i) {
        String str2 = (String) this.localeValues.get(str);
        if (str2 == null) {
            try {
                str2 = ApplicationLoader.applicationContext.getString(i);
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
        return str2 == null ? "LOC_ERR:" + str : str2;
    }

    public static String getSystemLocaleStringIso639() {
        Locale systemDefaultLocale = getInstance().getSystemDefaultLocale();
        if (systemDefaultLocale == null) {
            return "en";
        }
        String language = systemDefaultLocale.getLanguage();
        String country = systemDefaultLocale.getCountry();
        String variant = systemDefaultLocale.getVariant();
        if (language.length() == 0 && country.length() == 0) {
            return "en";
        }
        StringBuilder stringBuilder = new StringBuilder(11);
        stringBuilder.append(language);
        if (country.length() > 0 || variant.length() > 0) {
            stringBuilder.append('-');
        }
        stringBuilder.append(country);
        if (variant.length() > 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(variant);
        return stringBuilder.toString();
    }

    public static boolean isRTLCharacter(char c) {
        return Character.getDirectionality(c) == (byte) 1 || Character.getDirectionality(c) == (byte) 2 || Character.getDirectionality(c) == (byte) 16 || Character.getDirectionality(c) == (byte) 17;
    }

    private void loadOtherLanguages() {
        int i = 0;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("langconfig", 0);
        Object string = sharedPreferences.getString("locales", null);
        if (!TextUtils.isEmpty(string)) {
            for (String createWithString : string.split("&")) {
                LocaleInfo createWithString2 = LocaleInfo.createWithString(createWithString);
                if (createWithString2 != null) {
                    this.otherLanguages.add(createWithString2);
                }
            }
        }
        string = sharedPreferences.getString("remote", null);
        if (!TextUtils.isEmpty(string)) {
            String[] split = string.split("&");
            int length = split.length;
            while (i < length) {
                LocaleInfo createWithString3 = LocaleInfo.createWithString(split[i]);
                createWithString3.shortName = createWithString3.shortName.replace("-", "_");
                if (createWithString3 != null) {
                    this.remoteLanguages.add(createWithString3);
                }
                i++;
            }
        }
    }

    private void saveOtherLanguages() {
        int i = 0;
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("langconfig", 0).edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < this.otherLanguages.size(); i2++) {
            String saveString = ((LocaleInfo) this.otherLanguages.get(i2)).getSaveString();
            if (saveString != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(saveString);
            }
        }
        edit.putString("locales", stringBuilder.toString());
        stringBuilder.setLength(0);
        while (i < this.remoteLanguages.size()) {
            saveString = ((LocaleInfo) this.remoteLanguages.get(i)).getSaveString();
            if (saveString != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(saveString);
            }
            i++;
        }
        edit.putString("remote", stringBuilder.toString());
        edit.commit();
    }

    public static String stringForMessageListDate(long j) {
        long j2 = j * 1000;
        try {
            Calendar instance = Calendar.getInstance();
            int i = instance.get(6);
            instance.setTimeInMillis(j2);
            int i2 = instance.get(6);
            if (Math.abs(System.currentTimeMillis() - j2) >= 31536000000L) {
                return getInstance().formatterYear.format(new Date(j2));
            }
            i2 -= i;
            if (i2 == 0 || (i2 == -1 && System.currentTimeMillis() - j2 < 28800000)) {
                return getInstance().formatterDay.format(new Date(j2));
            }
            if (i2 > -7 && i2 <= -1) {
                return getInstance().formatterWeek.format(new Date(j2));
            }
            C5316a c5316a = new C5316a(j2);
            return !getCurrentLanguageName().contentEquals("فارسی") ? getInstance().formatterMonth.format(new Date(j2)) : c5316a.c() + " " + c5316a.d();
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return "LOC_ERR";
        }
    }

    private String stringForQuantity(int i) {
        switch (i) {
            case 1:
                return "zero";
            case 2:
                return "one";
            case 4:
                return "two";
            case 8:
                return "few";
            case 16:
                return "many";
            default:
                return "other";
        }
    }

    public void applyLanguage(LocaleInfo localeInfo, boolean z, boolean z2) {
        applyLanguage(localeInfo, z, z2, false, false);
    }

    public void applyLanguage(final LocaleInfo localeInfo, boolean z, boolean z2, boolean z3, boolean z4) {
        if (!(localeInfo == null || TextUtils.isEmpty(localeInfo.getKey()) || !localeInfo.getKey().contentEquals("fa_IR"))) {
            localeInfo.shortName = "fa";
        }
        if (localeInfo != null) {
            File pathToFile = localeInfo.getPathToFile();
            String str = localeInfo.shortName;
            if (!z2) {
                ConnectionsManager.getInstance().setLangCode(str.replace("_", "-"));
            }
            if (localeInfo.isRemote() && (z4 || !pathToFile.exists())) {
                FileLog.m13725d("reload locale because file doesn't exist " + pathToFile);
                if (z2) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            LocaleController.this.applyRemoteLanguage(localeInfo, null, true);
                        }
                    });
                } else {
                    applyRemoteLanguage(localeInfo, null, true);
                }
            }
            try {
                String[] split = localeInfo.shortName.split("_");
                Locale locale = split.length == 1 ? new Locale(localeInfo.shortName) : new Locale(split[0], split[1]);
                if (z) {
                    this.languageOverride = localeInfo.shortName;
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                    edit.putString("language", localeInfo.getKey());
                    edit.commit();
                }
                if (pathToFile == null) {
                    this.localeValues.clear();
                } else if (!z3) {
                    this.localeValues = getLocaleFileStrings(pathToFile);
                }
                this.currentLocale = locale;
                this.currentLocaleInfo = localeInfo;
                this.currentPluralRules = (PluralRules) this.allRules.get(split[0]);
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = (PluralRules) this.allRules.get(this.currentLocale.getLanguage());
                }
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = new PluralRules_None();
                }
                this.changingConfiguration = true;
                Locale.setDefault(this.currentLocale);
                Configuration configuration = new Configuration();
                configuration.locale = this.currentLocale;
                ApplicationLoader.applicationContext.getResources().updateConfiguration(configuration, ApplicationLoader.applicationContext.getResources().getDisplayMetrics());
                this.changingConfiguration = false;
                if (this.reloadLastFile) {
                    if (z2) {
                        AndroidUtilities.runOnUIThread(new C31013());
                    } else {
                        reloadCurrentRemoteLocale();
                    }
                    this.reloadLastFile = false;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
                this.changingConfiguration = false;
            }
            recreateFormatters();
        }
    }

    public boolean applyLanguageFile(File file) {
        try {
            HashMap localeFileStrings = getLocaleFileStrings(file);
            String str = (String) localeFileStrings.get("LanguageName");
            String str2 = (String) localeFileStrings.get("LanguageNameInEnglish");
            String str3 = (String) localeFileStrings.get("LanguageCode");
            if (str != null && str.length() > 0 && str2 != null && str2.length() > 0 && str3 != null && str3.length() > 0) {
                if (str.contains("&") || str.contains("|")) {
                    return false;
                }
                if (str2.contains("&") || str2.contains("|")) {
                    return false;
                }
                if (str3.contains("&") || str3.contains("|") || str3.contains("/") || str3.contains("\\")) {
                    return false;
                }
                File file2 = new File(ApplicationLoader.getFilesDirFixed(), str3 + ".xml");
                if (!AndroidUtilities.copyFile(file, file2)) {
                    return false;
                }
                LocaleInfo localeInfo;
                LocaleInfo languageFromDict = getLanguageFromDict(str3);
                if (languageFromDict == null) {
                    languageFromDict = new LocaleInfo();
                    languageFromDict.name = str;
                    languageFromDict.nameEnglish = str2;
                    languageFromDict.shortName = str3.toLowerCase();
                    languageFromDict.pathToFile = file2.getAbsolutePath();
                    this.languages.add(languageFromDict);
                    this.languagesDict.put(languageFromDict.getKey(), languageFromDict);
                    this.otherLanguages.add(languageFromDict);
                    saveOtherLanguages();
                    localeInfo = languageFromDict;
                } else {
                    localeInfo = languageFromDict;
                }
                this.localeValues = localeFileStrings;
                applyLanguage(localeInfo, true, false, true, false);
                return true;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return false;
    }

    public boolean deleteLanguage(LocaleInfo localeInfo) {
        if (localeInfo.pathToFile == null || localeInfo.isRemote()) {
            return false;
        }
        if (this.currentLocaleInfo == localeInfo) {
            LocaleInfo localeInfo2 = null;
            if (this.systemDefaultLocale.getLanguage() != null) {
                localeInfo2 = getLanguageFromDict(this.systemDefaultLocale.getLanguage());
            }
            if (localeInfo2 == null) {
                localeInfo2 = getLanguageFromDict(getLocaleString(this.systemDefaultLocale));
            }
            if (localeInfo2 == null) {
                localeInfo2 = getLanguageFromDict("en");
            }
            applyLanguage(localeInfo2, true, false);
        }
        this.otherLanguages.remove(localeInfo);
        this.languages.remove(localeInfo);
        this.languagesDict.remove(localeInfo.shortName);
        new File(localeInfo.pathToFile).delete();
        saveOtherLanguages();
        return true;
    }

    public String formatCurrencyDecimalString(long j, String str, boolean z) {
        String str2;
        double d;
        String toUpperCase = str.toUpperCase();
        long abs = Math.abs(j);
        int i = -1;
        switch (toUpperCase.hashCode()) {
            case 65726:
                if (toUpperCase.equals("BHD")) {
                    i = 1;
                    break;
                }
                break;
            case 65759:
                if (toUpperCase.equals("BIF")) {
                    i = 8;
                    break;
                }
                break;
            case 66267:
                if (toUpperCase.equals("BYR")) {
                    i = 9;
                    break;
                }
                break;
            case 66813:
                if (toUpperCase.equals("CLF")) {
                    i = 0;
                    break;
                }
                break;
            case 66823:
                if (toUpperCase.equals("CLP")) {
                    i = 10;
                    break;
                }
                break;
            case 67122:
                if (toUpperCase.equals("CVE")) {
                    i = 11;
                    break;
                }
                break;
            case 67712:
                if (toUpperCase.equals("DJF")) {
                    i = 12;
                    break;
                }
                break;
            case 70719:
                if (toUpperCase.equals("GNF")) {
                    i = 13;
                    break;
                }
                break;
            case 72732:
                if (toUpperCase.equals("IQD")) {
                    i = 2;
                    break;
                }
                break;
            case 72801:
                if (toUpperCase.equals("ISK")) {
                    i = 14;
                    break;
                }
                break;
            case 73631:
                if (toUpperCase.equals("JOD")) {
                    i = 3;
                    break;
                }
                break;
            case 73683:
                if (toUpperCase.equals("JPY")) {
                    i = 15;
                    break;
                }
                break;
            case 74532:
                if (toUpperCase.equals("KMF")) {
                    i = 16;
                    break;
                }
                break;
            case 74704:
                if (toUpperCase.equals("KRW")) {
                    i = 17;
                    break;
                }
                break;
            case 74840:
                if (toUpperCase.equals("KWD")) {
                    i = 4;
                    break;
                }
                break;
            case 75863:
                if (toUpperCase.equals("LYD")) {
                    i = 5;
                    break;
                }
                break;
            case 76263:
                if (toUpperCase.equals("MGA")) {
                    i = 18;
                    break;
                }
                break;
            case 76618:
                if (toUpperCase.equals("MRO")) {
                    i = 28;
                    break;
                }
                break;
            case 78388:
                if (toUpperCase.equals("OMR")) {
                    i = 6;
                    break;
                }
                break;
            case 79710:
                if (toUpperCase.equals("PYG")) {
                    i = 19;
                    break;
                }
                break;
            case 81569:
                if (toUpperCase.equals("RWF")) {
                    i = 20;
                    break;
                }
                break;
            case 83210:
                if (toUpperCase.equals("TND")) {
                    i = 7;
                    break;
                }
                break;
            case 83974:
                if (toUpperCase.equals("UGX")) {
                    i = 21;
                    break;
                }
                break;
            case 84517:
                if (toUpperCase.equals("UYI")) {
                    i = 22;
                    break;
                }
                break;
            case 85132:
                if (toUpperCase.equals("VND")) {
                    i = 23;
                    break;
                }
                break;
            case 85367:
                if (toUpperCase.equals("VUV")) {
                    i = 24;
                    break;
                }
                break;
            case 86653:
                if (toUpperCase.equals("XAF")) {
                    i = 25;
                    break;
                }
                break;
            case 87087:
                if (toUpperCase.equals("XOF")) {
                    i = 26;
                    break;
                }
                break;
            case 87118:
                if (toUpperCase.equals("XPF")) {
                    i = 27;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                str2 = " %.4f";
                d = ((double) abs) / 10000.0d;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                str2 = " %.3f";
                d = ((double) abs) / 1000.0d;
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                str2 = " %.0f";
                d = (double) abs;
                break;
            case 28:
                str2 = " %.1f";
                d = ((double) abs) / 10.0d;
                break;
            default:
                str2 = " %.2f";
                d = ((double) abs) / 100.0d;
                break;
        }
        return String.format(Locale.US, z ? toUpperCase : TtmlNode.ANONYMOUS_REGION_ID + str2, new Object[]{Double.valueOf(d)}).trim();
    }

    public String formatCurrencyString(long j, String str) {
        String str2;
        double d;
        String toUpperCase = str.toUpperCase();
        int i = j < 0 ? 1 : 0;
        long abs = Math.abs(j);
        int i2 = -1;
        switch (toUpperCase.hashCode()) {
            case 65726:
                if (toUpperCase.equals("BHD")) {
                    i2 = 1;
                    break;
                }
                break;
            case 65759:
                if (toUpperCase.equals("BIF")) {
                    i2 = 8;
                    break;
                }
                break;
            case 66267:
                if (toUpperCase.equals("BYR")) {
                    i2 = 9;
                    break;
                }
                break;
            case 66813:
                if (toUpperCase.equals("CLF")) {
                    i2 = 0;
                    break;
                }
                break;
            case 66823:
                if (toUpperCase.equals("CLP")) {
                    i2 = 10;
                    break;
                }
                break;
            case 67122:
                if (toUpperCase.equals("CVE")) {
                    i2 = 11;
                    break;
                }
                break;
            case 67712:
                if (toUpperCase.equals("DJF")) {
                    i2 = 12;
                    break;
                }
                break;
            case 70719:
                if (toUpperCase.equals("GNF")) {
                    i2 = 13;
                    break;
                }
                break;
            case 72732:
                if (toUpperCase.equals("IQD")) {
                    i2 = 2;
                    break;
                }
                break;
            case 72801:
                if (toUpperCase.equals("ISK")) {
                    i2 = 14;
                    break;
                }
                break;
            case 73631:
                if (toUpperCase.equals("JOD")) {
                    i2 = 3;
                    break;
                }
                break;
            case 73683:
                if (toUpperCase.equals("JPY")) {
                    i2 = 15;
                    break;
                }
                break;
            case 74532:
                if (toUpperCase.equals("KMF")) {
                    i2 = 16;
                    break;
                }
                break;
            case 74704:
                if (toUpperCase.equals("KRW")) {
                    i2 = 17;
                    break;
                }
                break;
            case 74840:
                if (toUpperCase.equals("KWD")) {
                    i2 = 4;
                    break;
                }
                break;
            case 75863:
                if (toUpperCase.equals("LYD")) {
                    i2 = 5;
                    break;
                }
                break;
            case 76263:
                if (toUpperCase.equals("MGA")) {
                    i2 = 18;
                    break;
                }
                break;
            case 76618:
                if (toUpperCase.equals("MRO")) {
                    i2 = 28;
                    break;
                }
                break;
            case 78388:
                if (toUpperCase.equals("OMR")) {
                    i2 = 6;
                    break;
                }
                break;
            case 79710:
                if (toUpperCase.equals("PYG")) {
                    i2 = 19;
                    break;
                }
                break;
            case 81569:
                if (toUpperCase.equals("RWF")) {
                    i2 = 20;
                    break;
                }
                break;
            case 83210:
                if (toUpperCase.equals("TND")) {
                    i2 = 7;
                    break;
                }
                break;
            case 83974:
                if (toUpperCase.equals("UGX")) {
                    i2 = 21;
                    break;
                }
                break;
            case 84517:
                if (toUpperCase.equals("UYI")) {
                    i2 = 22;
                    break;
                }
                break;
            case 85132:
                if (toUpperCase.equals("VND")) {
                    i2 = 23;
                    break;
                }
                break;
            case 85367:
                if (toUpperCase.equals("VUV")) {
                    i2 = 24;
                    break;
                }
                break;
            case 86653:
                if (toUpperCase.equals("XAF")) {
                    i2 = 25;
                    break;
                }
                break;
            case 87087:
                if (toUpperCase.equals("XOF")) {
                    i2 = 26;
                    break;
                }
                break;
            case 87118:
                if (toUpperCase.equals("XPF")) {
                    i2 = 27;
                    break;
                }
                break;
        }
        switch (i2) {
            case 0:
                str2 = " %.4f";
                d = ((double) abs) / 10000.0d;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                str2 = " %.3f";
                d = ((double) abs) / 1000.0d;
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                str2 = " %.0f";
                d = (double) abs;
                break;
            case 28:
                str2 = " %.1f";
                d = ((double) abs) / 10.0d;
                break;
            default:
                str2 = " %.2f";
                d = ((double) abs) / 100.0d;
                break;
        }
        Currency instance = Currency.getInstance(toUpperCase);
        if (instance != null) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(this.currentLocale != null ? this.currentLocale : this.systemDefaultLocale);
            currencyInstance.setCurrency(instance);
            return (i != 0 ? "-" : TtmlNode.ANONYMOUS_REGION_ID) + currencyInstance.format(d);
        }
        return (i != 0 ? "-" : TtmlNode.ANONYMOUS_REGION_ID) + String.format(Locale.US, toUpperCase + str2, new Object[]{Double.valueOf(d)});
    }

    public LocaleInfo getCurrentLocaleInfo() {
        return this.currentLocaleInfo;
    }

    public Locale getSystemDefaultLocale() {
        return this.systemDefaultLocale;
    }

    public String getTranslitString(String str) {
        if (this.translitChars == null) {
            this.translitChars = new HashMap(520);
            this.translitChars.put("ȼ", "c");
            this.translitChars.put("ᶇ", "n");
            this.translitChars.put("ɖ", "d");
            this.translitChars.put("ỿ", "y");
            this.translitChars.put("ᴓ", "o");
            this.translitChars.put("ø", "o");
            this.translitChars.put("ḁ", "a");
            this.translitChars.put("ʯ", "h");
            this.translitChars.put("ŷ", "y");
            this.translitChars.put("ʞ", "k");
            this.translitChars.put("ừ", "u");
            this.translitChars.put("ꜳ", "aa");
            this.translitChars.put("ĳ", "ij");
            this.translitChars.put("ḽ", "l");
            this.translitChars.put("ɪ", "i");
            this.translitChars.put("ḇ", "b");
            this.translitChars.put("ʀ", "r");
            this.translitChars.put("ě", "e");
            this.translitChars.put("ﬃ", "ffi");
            this.translitChars.put("ơ", "o");
            this.translitChars.put("ⱹ", "r");
            this.translitChars.put("ồ", "o");
            this.translitChars.put("ǐ", "i");
            this.translitChars.put("ꝕ", TtmlNode.TAG_P);
            this.translitChars.put("ý", "y");
            this.translitChars.put("ḝ", "e");
            this.translitChars.put("ₒ", "o");
            this.translitChars.put("ⱥ", "a");
            this.translitChars.put("ʙ", "b");
            this.translitChars.put("ḛ", "e");
            this.translitChars.put("ƈ", "c");
            this.translitChars.put("ɦ", "h");
            this.translitChars.put("ᵬ", "b");
            this.translitChars.put("ṣ", "s");
            this.translitChars.put("đ", "d");
            this.translitChars.put("ỗ", "o");
            this.translitChars.put("ɟ", "j");
            this.translitChars.put("ẚ", "a");
            this.translitChars.put("ɏ", "y");
            this.translitChars.put("л", "l");
            this.translitChars.put("ʌ", "v");
            this.translitChars.put("ꝓ", TtmlNode.TAG_P);
            this.translitChars.put("ﬁ", "fi");
            this.translitChars.put("ᶄ", "k");
            this.translitChars.put("ḏ", "d");
            this.translitChars.put("ᴌ", "l");
            this.translitChars.put("ė", "e");
            this.translitChars.put("ё", "yo");
            this.translitChars.put("ᴋ", "k");
            this.translitChars.put("ċ", "c");
            this.translitChars.put("ʁ", "r");
            this.translitChars.put("ƕ", "hv");
            this.translitChars.put("ƀ", "b");
            this.translitChars.put("ṍ", "o");
            this.translitChars.put("ȣ", "ou");
            this.translitChars.put("ǰ", "j");
            this.translitChars.put("ᶃ", "g");
            this.translitChars.put("ṋ", "n");
            this.translitChars.put("ɉ", "j");
            this.translitChars.put("ǧ", "g");
            this.translitChars.put("ǳ", "dz");
            this.translitChars.put("ź", "z");
            this.translitChars.put("ꜷ", "au");
            this.translitChars.put("ǖ", "u");
            this.translitChars.put("ᵹ", "g");
            this.translitChars.put("ȯ", "o");
            this.translitChars.put("ɐ", "a");
            this.translitChars.put("ą", "a");
            this.translitChars.put("õ", "o");
            this.translitChars.put("ɻ", "r");
            this.translitChars.put("ꝍ", "o");
            this.translitChars.put("ǟ", "a");
            this.translitChars.put("ȴ", "l");
            this.translitChars.put("ʂ", "s");
            this.translitChars.put("ﬂ", "fl");
            this.translitChars.put("ȉ", "i");
            this.translitChars.put("ⱻ", "e");
            this.translitChars.put("ṉ", "n");
            this.translitChars.put("ï", "i");
            this.translitChars.put("ñ", "n");
            this.translitChars.put("ᴉ", "i");
            this.translitChars.put("ʇ", "t");
            this.translitChars.put("ẓ", "z");
            this.translitChars.put("ỷ", "y");
            this.translitChars.put("ȳ", "y");
            this.translitChars.put("ṩ", "s");
            this.translitChars.put("ɽ", "r");
            this.translitChars.put("ĝ", "g");
            this.translitChars.put("в", "v");
            this.translitChars.put("ᴝ", "u");
            this.translitChars.put("ḳ", "k");
            this.translitChars.put("ꝫ", "et");
            this.translitChars.put("ī", "i");
            this.translitChars.put("ť", "t");
            this.translitChars.put("ꜿ", "c");
            this.translitChars.put("ʟ", "l");
            this.translitChars.put("ꜹ", "av");
            this.translitChars.put("û", "u");
            this.translitChars.put("æ", "ae");
            this.translitChars.put("и", "i");
            this.translitChars.put("ă", "a");
            this.translitChars.put("ǘ", "u");
            this.translitChars.put("ꞅ", "s");
            this.translitChars.put("ᵣ", "r");
            this.translitChars.put("ᴀ", "a");
            this.translitChars.put("ƃ", "b");
            this.translitChars.put("ḩ", "h");
            this.translitChars.put("ṧ", "s");
            this.translitChars.put("ₑ", "e");
            this.translitChars.put("ʜ", "h");
            this.translitChars.put("ẋ", "x");
            this.translitChars.put("ꝅ", "k");
            this.translitChars.put("ḋ", "d");
            this.translitChars.put("ƣ", "oi");
            this.translitChars.put("ꝑ", TtmlNode.TAG_P);
            this.translitChars.put("ħ", "h");
            this.translitChars.put("ⱴ", "v");
            this.translitChars.put("ẇ", "w");
            this.translitChars.put("ǹ", "n");
            this.translitChars.put("ɯ", "m");
            this.translitChars.put("ɡ", "g");
            this.translitChars.put("ɴ", "n");
            this.translitChars.put("ᴘ", TtmlNode.TAG_P);
            this.translitChars.put("ᵥ", "v");
            this.translitChars.put("ū", "u");
            this.translitChars.put("ḃ", "b");
            this.translitChars.put("ṗ", TtmlNode.TAG_P);
            this.translitChars.put("ь", TtmlNode.ANONYMOUS_REGION_ID);
            this.translitChars.put("å", "a");
            this.translitChars.put("ɕ", "c");
            this.translitChars.put("ọ", "o");
            this.translitChars.put("ắ", "a");
            this.translitChars.put("ƒ", "f");
            this.translitChars.put("ǣ", "ae");
            this.translitChars.put("ꝡ", "vy");
            this.translitChars.put("ﬀ", "ff");
            this.translitChars.put("ᶉ", "r");
            this.translitChars.put("ô", "o");
            this.translitChars.put("ǿ", "o");
            this.translitChars.put("ṳ", "u");
            this.translitChars.put("ȥ", "z");
            this.translitChars.put("ḟ", "f");
            this.translitChars.put("ḓ", "d");
            this.translitChars.put("ȇ", "e");
            this.translitChars.put("ȕ", "u");
            this.translitChars.put("п", TtmlNode.TAG_P);
            this.translitChars.put("ȵ", "n");
            this.translitChars.put("ʠ", "q");
            this.translitChars.put("ấ", "a");
            this.translitChars.put("ǩ", "k");
            this.translitChars.put("ĩ", "i");
            this.translitChars.put("ṵ", "u");
            this.translitChars.put("ŧ", "t");
            this.translitChars.put("ɾ", "r");
            this.translitChars.put("ƙ", "k");
            this.translitChars.put("ṫ", "t");
            this.translitChars.put("ꝗ", "q");
            this.translitChars.put("ậ", "a");
            this.translitChars.put("н", "n");
            this.translitChars.put("ʄ", "j");
            this.translitChars.put("ƚ", "l");
            this.translitChars.put("ᶂ", "f");
            this.translitChars.put("д", "d");
            this.translitChars.put("ᵴ", "s");
            this.translitChars.put("ꞃ", "r");
            this.translitChars.put("ᶌ", "v");
            this.translitChars.put("ɵ", "o");
            this.translitChars.put("ḉ", "c");
            this.translitChars.put("ᵤ", "u");
            this.translitChars.put("ẑ", "z");
            this.translitChars.put("ṹ", "u");
            this.translitChars.put("ň", "n");
            this.translitChars.put("ʍ", "w");
            this.translitChars.put("ầ", "a");
            this.translitChars.put("ǉ", "lj");
            this.translitChars.put("ɓ", "b");
            this.translitChars.put("ɼ", "r");
            this.translitChars.put("ò", "o");
            this.translitChars.put("ẘ", "w");
            this.translitChars.put("ɗ", "d");
            this.translitChars.put("ꜽ", "ay");
            this.translitChars.put("ư", "u");
            this.translitChars.put("ᶀ", "b");
            this.translitChars.put("ǜ", "u");
            this.translitChars.put("ẹ", "e");
            this.translitChars.put("ǡ", "a");
            this.translitChars.put("ɥ", "h");
            this.translitChars.put("ṏ", "o");
            this.translitChars.put("ǔ", "u");
            this.translitChars.put("ʎ", "y");
            this.translitChars.put("ȱ", "o");
            this.translitChars.put("ệ", "e");
            this.translitChars.put("ế", "e");
            this.translitChars.put("ĭ", "i");
            this.translitChars.put("ⱸ", "e");
            this.translitChars.put("ṯ", "t");
            this.translitChars.put("ᶑ", "d");
            this.translitChars.put("ḧ", "h");
            this.translitChars.put("ṥ", "s");
            this.translitChars.put("ë", "e");
            this.translitChars.put("ᴍ", "m");
            this.translitChars.put("ö", "o");
            this.translitChars.put("é", "e");
            this.translitChars.put("ı", "i");
            this.translitChars.put("ď", "d");
            this.translitChars.put("ᵯ", "m");
            this.translitChars.put("ỵ", "y");
            this.translitChars.put("я", "ya");
            this.translitChars.put("ŵ", "w");
            this.translitChars.put("ề", "e");
            this.translitChars.put("ứ", "u");
            this.translitChars.put("ƶ", "z");
            this.translitChars.put("ĵ", "j");
            this.translitChars.put("ḍ", "d");
            this.translitChars.put("ŭ", "u");
            this.translitChars.put("ʝ", "j");
            this.translitChars.put("ж", "zh");
            this.translitChars.put("ê", "e");
            this.translitChars.put("ǚ", "u");
            this.translitChars.put("ġ", "g");
            this.translitChars.put("ṙ", "r");
            this.translitChars.put("ƞ", "n");
            this.translitChars.put("ъ", TtmlNode.ANONYMOUS_REGION_ID);
            this.translitChars.put("ḗ", "e");
            this.translitChars.put("ẝ", "s");
            this.translitChars.put("ᶁ", "d");
            this.translitChars.put("ķ", "k");
            this.translitChars.put("ᴂ", "ae");
            this.translitChars.put("ɘ", "e");
            this.translitChars.put("ợ", "o");
            this.translitChars.put("ḿ", "m");
            this.translitChars.put("ꜰ", "f");
            this.translitChars.put("а", "a");
            this.translitChars.put("ẵ", "a");
            this.translitChars.put("ꝏ", "oo");
            this.translitChars.put("ᶆ", "m");
            this.translitChars.put("ᵽ", TtmlNode.TAG_P);
            this.translitChars.put("ц", "ts");
            this.translitChars.put("ữ", "u");
            this.translitChars.put("ⱪ", "k");
            this.translitChars.put("ḥ", "h");
            this.translitChars.put("ţ", "t");
            this.translitChars.put("ᵱ", TtmlNode.TAG_P);
            this.translitChars.put("ṁ", "m");
            this.translitChars.put("á", "a");
            this.translitChars.put("ᴎ", "n");
            this.translitChars.put("ꝟ", "v");
            this.translitChars.put("è", "e");
            this.translitChars.put("ᶎ", "z");
            this.translitChars.put("ꝺ", "d");
            this.translitChars.put("ᶈ", TtmlNode.TAG_P);
            this.translitChars.put("м", "m");
            this.translitChars.put("ɫ", "l");
            this.translitChars.put("ᴢ", "z");
            this.translitChars.put("ɱ", "m");
            this.translitChars.put("ṝ", "r");
            this.translitChars.put("ṽ", "v");
            this.translitChars.put("ũ", "u");
            this.translitChars.put("ß", "ss");
            this.translitChars.put("т", "t");
            this.translitChars.put("ĥ", "h");
            this.translitChars.put("ᵵ", "t");
            this.translitChars.put("ʐ", "z");
            this.translitChars.put("ṟ", "r");
            this.translitChars.put("ɲ", "n");
            this.translitChars.put("à", "a");
            this.translitChars.put("ẙ", "y");
            this.translitChars.put("ỳ", "y");
            this.translitChars.put("ᴔ", "oe");
            this.translitChars.put("ы", "i");
            this.translitChars.put("ₓ", "x");
            this.translitChars.put("ȗ", "u");
            this.translitChars.put("ⱼ", "j");
            this.translitChars.put("ẫ", "a");
            this.translitChars.put("ʑ", "z");
            this.translitChars.put("ẛ", "s");
            this.translitChars.put("ḭ", "i");
            this.translitChars.put("ꜵ", "ao");
            this.translitChars.put("ɀ", "z");
            this.translitChars.put("ÿ", "y");
            this.translitChars.put("ǝ", "e");
            this.translitChars.put("ǭ", "o");
            this.translitChars.put("ᴅ", "d");
            this.translitChars.put("ᶅ", "l");
            this.translitChars.put("ù", "u");
            this.translitChars.put("ạ", "a");
            this.translitChars.put("ḅ", "b");
            this.translitChars.put("ụ", "u");
            this.translitChars.put("к", "k");
            this.translitChars.put("ằ", "a");
            this.translitChars.put("ᴛ", "t");
            this.translitChars.put("ƴ", "y");
            this.translitChars.put("ⱦ", "t");
            this.translitChars.put("з", "z");
            this.translitChars.put("ⱡ", "l");
            this.translitChars.put("ȷ", "j");
            this.translitChars.put("ᵶ", "z");
            this.translitChars.put("ḫ", "h");
            this.translitChars.put("ⱳ", "w");
            this.translitChars.put("ḵ", "k");
            this.translitChars.put("ờ", "o");
            this.translitChars.put("î", "i");
            this.translitChars.put("ģ", "g");
            this.translitChars.put("ȅ", "e");
            this.translitChars.put("ȧ", "a");
            this.translitChars.put("ẳ", "a");
            this.translitChars.put("щ", "sch");
            this.translitChars.put("ɋ", "q");
            this.translitChars.put("ṭ", "t");
            this.translitChars.put("ꝸ", "um");
            this.translitChars.put("ᴄ", "c");
            this.translitChars.put("ẍ", "x");
            this.translitChars.put("ủ", "u");
            this.translitChars.put("ỉ", "i");
            this.translitChars.put("ᴚ", "r");
            this.translitChars.put("ś", "s");
            this.translitChars.put("ꝋ", "o");
            this.translitChars.put("ỹ", "y");
            this.translitChars.put("ṡ", "s");
            this.translitChars.put("ǌ", "nj");
            this.translitChars.put("ȁ", "a");
            this.translitChars.put("ẗ", "t");
            this.translitChars.put("ĺ", "l");
            this.translitChars.put("ž", "z");
            this.translitChars.put("ᵺ", "th");
            this.translitChars.put("ƌ", "d");
            this.translitChars.put("ș", "s");
            this.translitChars.put("š", "s");
            this.translitChars.put("ᶙ", "u");
            this.translitChars.put("ẽ", "e");
            this.translitChars.put("ẜ", "s");
            this.translitChars.put("ɇ", "e");
            this.translitChars.put("ṷ", "u");
            this.translitChars.put("ố", "o");
            this.translitChars.put("ȿ", "s");
            this.translitChars.put("ᴠ", "v");
            this.translitChars.put("ꝭ", "is");
            this.translitChars.put("ᴏ", "o");
            this.translitChars.put("ɛ", "e");
            this.translitChars.put("ǻ", "a");
            this.translitChars.put("ﬄ", "ffl");
            this.translitChars.put("ⱺ", "o");
            this.translitChars.put("ȋ", "i");
            this.translitChars.put("ᵫ", "ue");
            this.translitChars.put("ȡ", "d");
            this.translitChars.put("ⱬ", "z");
            this.translitChars.put("ẁ", "w");
            this.translitChars.put("ᶏ", "a");
            this.translitChars.put("ꞇ", "t");
            this.translitChars.put("ğ", "g");
            this.translitChars.put("ɳ", "n");
            this.translitChars.put("ʛ", "g");
            this.translitChars.put("ᴜ", "u");
            this.translitChars.put("ф", "f");
            this.translitChars.put("ẩ", "a");
            this.translitChars.put("ṅ", "n");
            this.translitChars.put("ɨ", "i");
            this.translitChars.put("ᴙ", "r");
            this.translitChars.put("ǎ", "a");
            this.translitChars.put("ſ", "s");
            this.translitChars.put("у", "u");
            this.translitChars.put("ȫ", "o");
            this.translitChars.put("ɿ", "r");
            this.translitChars.put("ƭ", "t");
            this.translitChars.put("ḯ", "i");
            this.translitChars.put("ǽ", "ae");
            this.translitChars.put("ⱱ", "v");
            this.translitChars.put("ɶ", "oe");
            this.translitChars.put("ṃ", "m");
            this.translitChars.put("ż", "z");
            this.translitChars.put("ĕ", "e");
            this.translitChars.put("ꜻ", "av");
            this.translitChars.put("ở", "o");
            this.translitChars.put("ễ", "e");
            this.translitChars.put("ɬ", "l");
            this.translitChars.put("ị", "i");
            this.translitChars.put("ᵭ", "d");
            this.translitChars.put("ﬆ", "st");
            this.translitChars.put("ḷ", "l");
            this.translitChars.put("ŕ", "r");
            this.translitChars.put("ᴕ", "ou");
            this.translitChars.put("ʈ", "t");
            this.translitChars.put("ā", "a");
            this.translitChars.put("э", "e");
            this.translitChars.put("ḙ", "e");
            this.translitChars.put("ᴑ", "o");
            this.translitChars.put("ç", "c");
            this.translitChars.put("ᶊ", "s");
            this.translitChars.put("ặ", "a");
            this.translitChars.put("ų", "u");
            this.translitChars.put("ả", "a");
            this.translitChars.put("ǥ", "g");
            this.translitChars.put("р", "r");
            this.translitChars.put("ꝁ", "k");
            this.translitChars.put("ẕ", "z");
            this.translitChars.put("ŝ", "s");
            this.translitChars.put("ḕ", "e");
            this.translitChars.put("ɠ", "g");
            this.translitChars.put("ꝉ", "l");
            this.translitChars.put("ꝼ", "f");
            this.translitChars.put("ᶍ", "x");
            this.translitChars.put("х", "h");
            this.translitChars.put("ǒ", "o");
            this.translitChars.put("ę", "e");
            this.translitChars.put("ổ", "o");
            this.translitChars.put("ƫ", "t");
            this.translitChars.put("ǫ", "o");
            this.translitChars.put("i̇", "i");
            this.translitChars.put("ṇ", "n");
            this.translitChars.put("ć", "c");
            this.translitChars.put("ᵷ", "g");
            this.translitChars.put("ẅ", "w");
            this.translitChars.put("ḑ", "d");
            this.translitChars.put("ḹ", "l");
            this.translitChars.put("ч", "ch");
            this.translitChars.put("œ", "oe");
            this.translitChars.put("ᵳ", "r");
            this.translitChars.put("ļ", "l");
            this.translitChars.put("ȑ", "r");
            this.translitChars.put("ȭ", "o");
            this.translitChars.put("ᵰ", "n");
            this.translitChars.put("ᴁ", "ae");
            this.translitChars.put("ŀ", "l");
            this.translitChars.put("ä", "a");
            this.translitChars.put("ƥ", TtmlNode.TAG_P);
            this.translitChars.put("ỏ", "o");
            this.translitChars.put("į", "i");
            this.translitChars.put("ȓ", "r");
            this.translitChars.put("ǆ", "dz");
            this.translitChars.put("ḡ", "g");
            this.translitChars.put("ṻ", "u");
            this.translitChars.put("ō", "o");
            this.translitChars.put("ľ", "l");
            this.translitChars.put("ẃ", "w");
            this.translitChars.put("ț", "t");
            this.translitChars.put("ń", "n");
            this.translitChars.put("ɍ", "r");
            this.translitChars.put("ȃ", "a");
            this.translitChars.put("ü", "u");
            this.translitChars.put("ꞁ", "l");
            this.translitChars.put("ᴐ", "o");
            this.translitChars.put("ớ", "o");
            this.translitChars.put("ᴃ", "b");
            this.translitChars.put("ɹ", "r");
            this.translitChars.put("ᵲ", "r");
            this.translitChars.put("ʏ", "y");
            this.translitChars.put("ᵮ", "f");
            this.translitChars.put("ⱨ", "h");
            this.translitChars.put("ŏ", "o");
            this.translitChars.put("ú", "u");
            this.translitChars.put("ṛ", "r");
            this.translitChars.put("ʮ", "h");
            this.translitChars.put("ó", "o");
            this.translitChars.put("ů", "u");
            this.translitChars.put("ỡ", "o");
            this.translitChars.put("ṕ", TtmlNode.TAG_P);
            this.translitChars.put("ᶖ", "i");
            this.translitChars.put("ự", "u");
            this.translitChars.put("ã", "a");
            this.translitChars.put("ᵢ", "i");
            this.translitChars.put("ṱ", "t");
            this.translitChars.put("ể", "e");
            this.translitChars.put("ử", "u");
            this.translitChars.put("í", "i");
            this.translitChars.put("ɔ", "o");
            this.translitChars.put("с", "s");
            this.translitChars.put("й", "i");
            this.translitChars.put("ɺ", "r");
            this.translitChars.put("ɢ", "g");
            this.translitChars.put("ř", "r");
            this.translitChars.put("ẖ", "h");
            this.translitChars.put("ű", "u");
            this.translitChars.put("ȍ", "o");
            this.translitChars.put("ш", "sh");
            this.translitChars.put("ḻ", "l");
            this.translitChars.put("ḣ", "h");
            this.translitChars.put("ȶ", "t");
            this.translitChars.put("ņ", "n");
            this.translitChars.put("ᶒ", "e");
            this.translitChars.put("ì", "i");
            this.translitChars.put("ẉ", "w");
            this.translitChars.put("б", "b");
            this.translitChars.put("ē", "e");
            this.translitChars.put("ᴇ", "e");
            this.translitChars.put("ł", "l");
            this.translitChars.put("ộ", "o");
            this.translitChars.put("ɭ", "l");
            this.translitChars.put("ẏ", "y");
            this.translitChars.put("ᴊ", "j");
            this.translitChars.put("ḱ", "k");
            this.translitChars.put("ṿ", "v");
            this.translitChars.put("ȩ", "e");
            this.translitChars.put("â", "a");
            this.translitChars.put("ş", "s");
            this.translitChars.put("ŗ", "r");
            this.translitChars.put("ʋ", "v");
            this.translitChars.put("ₐ", "a");
            this.translitChars.put("ↄ", "c");
            this.translitChars.put("ᶓ", "e");
            this.translitChars.put("ɰ", "m");
            this.translitChars.put("е", "e");
            this.translitChars.put("ᴡ", "w");
            this.translitChars.put("ȏ", "o");
            this.translitChars.put("č", "c");
            this.translitChars.put("ǵ", "g");
            this.translitChars.put("ĉ", "c");
            this.translitChars.put("ю", "yu");
            this.translitChars.put("ᶗ", "o");
            this.translitChars.put("ꝃ", "k");
            this.translitChars.put("ꝙ", "q");
            this.translitChars.put("г", "g");
            this.translitChars.put("ṑ", "o");
            this.translitChars.put("ꜱ", "s");
            this.translitChars.put("ṓ", "o");
            this.translitChars.put("ȟ", "h");
            this.translitChars.put("ő", "o");
            this.translitChars.put("ꜩ", "tz");
            this.translitChars.put("ẻ", "e");
            this.translitChars.put("о", "o");
        }
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int length = str.length();
        for (int i = 0; i < length; i++) {
            String substring = str.substring(i, i + 1);
            String str2 = (String) this.translitChars.get(substring);
            if (str2 != null) {
                stringBuilder.append(str2);
            } else {
                stringBuilder.append(substring);
            }
        }
        return stringBuilder.toString();
    }

    public boolean isCurrentLocalLocale() {
        return this.currentLocaleInfo.isLocal();
    }

    public void loadRemoteLanguages() {
        if (!this.loadingRemoteLanguages) {
            this.loadingRemoteLanguages = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_langpack_getLanguages(), new C31045(), 8);
        }
    }

    public void onDeviceConfigurationChange(Configuration configuration) {
        if (!this.changingConfiguration) {
            is24HourFormat = DateFormat.is24HourFormat(ApplicationLoader.applicationContext);
            this.systemDefaultLocale = configuration.locale;
            if (this.languageOverride != null) {
                LocaleInfo localeInfo = this.currentLocaleInfo;
                this.currentLocaleInfo = null;
                applyLanguage(localeInfo, false, false);
                return;
            }
            Locale locale = configuration.locale;
            if (locale != null) {
                String displayName = locale.getDisplayName();
                String displayName2 = this.currentLocale.getDisplayName();
                if (!(displayName == null || displayName2 == null || displayName.equals(displayName2))) {
                    recreateFormatters();
                }
                this.currentLocale = locale;
                this.currentPluralRules = (PluralRules) this.allRules.get(this.currentLocale.getLanguage());
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = (PluralRules) this.allRules.get("en");
                }
            }
        }
    }

    public void recreateFormatters() {
        int i = 1;
        Locale locale = this.currentLocale;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        if (language == null) {
            language = "en";
        }
        String toLowerCase = language.toLowerCase();
        boolean z = toLowerCase.startsWith("ar") || toLowerCase.toLowerCase().equals("fa") || (BuildVars.DEBUG_VERSION && (toLowerCase.startsWith("he") || toLowerCase.startsWith("iw") || toLowerCase.startsWith("fa")));
        isRTL = z;
        if (toLowerCase.equals("ko")) {
            i = 2;
        }
        nameDisplayOrder = i;
        this.formatterMonth = createFormatter(locale, getStringInternal("formatterMonth", R.string.formatterMonth), "dd MMM");
        this.formatterYear = createFormatter(locale, getStringInternal("formatterYear", R.string.formatterYear), "dd.MM.yy");
        this.formatterYearMax = createFormatter(locale, getStringInternal("formatterYearMax", R.string.formatterYearMax), "dd.MM.yyyy");
        this.chatDate = createFormatter(locale, getStringInternal("chatDate", R.string.chatDate), "d MMMM");
        this.chatFullDate = createFormatter(locale, getStringInternal("chatFullDate", R.string.chatFullDate), "d MMMM yyyy");
        this.formatterWeek = createFormatter(locale, getStringInternal("formatterWeek", R.string.formatterWeek), "EEE");
        this.formatterMonthYear = createFormatter(locale, getStringInternal("formatterMonthYear", R.string.formatterMonthYear), "MMMM yyyy");
        Locale locale2 = (toLowerCase.toLowerCase().equals("ar") || toLowerCase.toLowerCase().equals("ko")) ? locale : Locale.US;
        this.formatterDay = createFormatter(locale2, is24HourFormat ? getStringInternal("formatterDay24H", R.string.formatterDay24H) : getStringInternal("formatterDay12H", R.string.formatterDay12H), is24HourFormat ? "HH:mm" : "h:mm a");
        this.formatterStats = createFormatter(locale, is24HourFormat ? getStringInternal("formatterStats24H", R.string.formatterStats24H) : getStringInternal("formatterStats12H", R.string.formatterStats12H), is24HourFormat ? "MMM dd yyyy, HH:mm" : "MMM dd yyyy, h:mm a");
        this.formatterBannedUntil = createFormatter(locale, is24HourFormat ? getStringInternal("formatterBannedUntil24H", R.string.formatterBannedUntil24H) : getStringInternal("formatterBannedUntil12H", R.string.formatterBannedUntil12H), is24HourFormat ? "MMM dd yyyy, HH:mm" : "MMM dd yyyy, h:mm a");
        this.formatterBannedUntilThisYear = createFormatter(locale, is24HourFormat ? getStringInternal("formatterBannedUntilThisYear24H", R.string.formatterBannedUntilThisYear24H) : getStringInternal("formatterBannedUntilThisYear12H", R.string.formatterBannedUntilThisYear12H), is24HourFormat ? "MMM dd, HH:mm" : "MMM dd, h:mm a");
    }

    public void reloadCurrentRemoteLocale() {
        applyRemoteLanguage(this.currentLocaleInfo, null, true);
    }

    public void saveRemoteLocaleStrings(final TLRPC$TL_langPackDifference tLRPC$TL_langPackDifference) {
        if (tLRPC$TL_langPackDifference != null && !tLRPC$TL_langPackDifference.strings.isEmpty()) {
            final String toLowerCase = tLRPC$TL_langPackDifference.lang_code.replace('-', '_').toLowerCase();
            File file = new File(ApplicationLoader.getFilesDirFixed(), "remote_" + toLowerCase + ".xml");
            try {
                HashMap hashMap = tLRPC$TL_langPackDifference.from_version == 0 ? new HashMap() : getLocaleFileStrings(file, true);
                for (int i = 0; i < tLRPC$TL_langPackDifference.strings.size(); i++) {
                    LangPackString langPackString = (LangPackString) tLRPC$TL_langPackDifference.strings.get(i);
                    if (langPackString instanceof TLRPC$TL_langPackString) {
                        hashMap.put(langPackString.key, escapeString(langPackString.value));
                    } else if (langPackString instanceof TLRPC$TL_langPackStringPluralized) {
                        hashMap.put(langPackString.key + "_zero", langPackString.zero_value != null ? escapeString(langPackString.zero_value) : TtmlNode.ANONYMOUS_REGION_ID);
                        hashMap.put(langPackString.key + "_one", langPackString.one_value != null ? escapeString(langPackString.one_value) : TtmlNode.ANONYMOUS_REGION_ID);
                        hashMap.put(langPackString.key + "_two", langPackString.two_value != null ? escapeString(langPackString.two_value) : TtmlNode.ANONYMOUS_REGION_ID);
                        hashMap.put(langPackString.key + "_few", langPackString.few_value != null ? escapeString(langPackString.few_value) : TtmlNode.ANONYMOUS_REGION_ID);
                        hashMap.put(langPackString.key + "_many", langPackString.many_value != null ? escapeString(langPackString.many_value) : TtmlNode.ANONYMOUS_REGION_ID);
                        hashMap.put(langPackString.key + "_other", langPackString.other_value != null ? escapeString(langPackString.other_value) : TtmlNode.ANONYMOUS_REGION_ID);
                    } else if (langPackString instanceof TLRPC$TL_langPackStringDeleted) {
                        hashMap.remove(langPackString.key);
                    }
                }
                FileLog.m13725d("save locale file to " + file);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                bufferedWriter.write("<resources>\n");
                for (Entry entry : hashMap.entrySet()) {
                    bufferedWriter.write(String.format("<string name=\"%1$s\">%2$s</string>\n", new Object[]{entry.getKey(), entry.getValue()}));
                }
                bufferedWriter.write("</resources>");
                bufferedWriter.close();
                final HashMap localeFileStrings = getLocaleFileStrings(file);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        LocaleInfo access$200 = LocaleController.this.getLanguageFromDict(toLowerCase);
                        if (access$200 != null) {
                            access$200.version = tLRPC$TL_langPackDifference.version;
                        }
                        LocaleController.this.saveOtherLanguages();
                        if (LocaleController.this.currentLocaleInfo == null || !LocaleController.this.currentLocaleInfo.isLocal()) {
                            try {
                                String[] split = access$200.shortName.split("_");
                                Locale locale = split.length == 1 ? new Locale(access$200.shortName) : new Locale(split[0], split[1]);
                                if (locale != null) {
                                    LocaleController.this.languageOverride = access$200.shortName;
                                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                                    edit.putString("language", access$200.getKey());
                                    edit.commit();
                                }
                                if (locale != null) {
                                    LocaleController.this.localeValues = localeFileStrings;
                                    LocaleController.this.currentLocale = locale;
                                    LocaleController.this.currentLocaleInfo = access$200;
                                    LocaleController.this.currentPluralRules = (PluralRules) LocaleController.this.allRules.get(LocaleController.this.currentLocale.getLanguage());
                                    if (LocaleController.this.currentPluralRules == null) {
                                        LocaleController.this.currentPluralRules = (PluralRules) LocaleController.this.allRules.get("en");
                                    }
                                    LocaleController.this.changingConfiguration = true;
                                    Locale.setDefault(LocaleController.this.currentLocale);
                                    Configuration configuration = new Configuration();
                                    configuration.locale = LocaleController.this.currentLocale;
                                    ApplicationLoader.applicationContext.getResources().updateConfiguration(configuration, ApplicationLoader.applicationContext.getResources().getDisplayMetrics());
                                    LocaleController.this.changingConfiguration = false;
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                                LocaleController.this.changingConfiguration = false;
                            }
                            LocaleController.this.recreateFormatters();
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInterface, new Object[0]);
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
