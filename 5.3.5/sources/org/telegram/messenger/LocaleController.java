package org.telegram.messenger;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Xml;
import com.persianswitch.sdk.base.manager.LanguageManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
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
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.time.FastDateFormat;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$LangPackString;
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
import org.telegram.tgnet.TLRPC.User;
import org.xmlpull.v1.XmlPullParser;
import utils.calender.PersianCalendar;

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
    private HashMap<String, LocaleController$PluralRules> allRules = new HashMap();
    private boolean changingConfiguration = false;
    public FastDateFormat chatDate;
    public FastDateFormat chatFullDate;
    private HashMap<String, String> currencyValues;
    private Locale currentLocale;
    private LocaleController$LocaleInfo currentLocaleInfo;
    private LocaleController$PluralRules currentPluralRules;
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
    public ArrayList<LocaleController$LocaleInfo> languages = new ArrayList();
    public HashMap<String, LocaleController$LocaleInfo> languagesDict = new HashMap();
    private boolean loadingRemoteLanguages;
    private HashMap<String, String> localeValues = new HashMap();
    private ArrayList<LocaleController$LocaleInfo> otherLanguages = new ArrayList();
    private boolean reloadLastFile;
    public ArrayList<LocaleController$LocaleInfo> remoteLanguages = new ArrayList();
    private Locale systemDefaultLocale;
    private HashMap<String, String> translitChars;

    public static LocaleController getInstance() {
        LocaleController localInstance = Instance;
        if (localInstance == null) {
            synchronized (LocaleController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        LocaleController localInstance2 = new LocaleController();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public LocaleController() {
        int a;
        addRules(new String[]{"bem", "brx", "da", "de", "el", LanguageManager.ENGLISH, "eo", "es", "et", "fi", "fo", "gl", "he", "iw", "it", "nb", "nl", "nn", "no", "sv", "af", "bg", "bn", "ca", "eu", "fur", "fy", "gu", "ha", "is", "ku", "lb", "ml", "mr", "nah", "ne", "om", "or", "pa", "pap", "ps", "so", "sq", "sw", "ta", "te", "tk", "ur", "zu", "mn", "gsw", "chr", "rm", "pt", "an", "ast"}, new LocaleController$PluralRules_One());
        addRules(new String[]{"cs", "sk"}, new LocaleController$PluralRules_Czech());
        addRules(new String[]{"ff", "fr", "kab"}, new LocaleController$PluralRules_French());
        addRules(new String[]{"hr", "ru", "sr", "uk", "be", "bs", "sh"}, new LocaleController$PluralRules_Balkan());
        addRules(new String[]{"lv"}, new LocaleController$PluralRules_Latvian());
        addRules(new String[]{"lt"}, new LocaleController$PluralRules_Lithuanian());
        addRules(new String[]{"pl"}, new LocaleController$PluralRules_Polish());
        addRules(new String[]{"ro", "mo"}, new LocaleController$PluralRules_Romanian());
        addRules(new String[]{"sl"}, new LocaleController$PluralRules_Slovenian());
        addRules(new String[]{"ar"}, new LocaleController$PluralRules_Arabic());
        addRules(new String[]{"mk"}, new LocaleController$PluralRules_Macedonian());
        addRules(new String[]{"cy"}, new LocaleController$PluralRules_Welsh());
        addRules(new String[]{TtmlNode.TAG_BR}, new LocaleController$PluralRules_Breton());
        addRules(new String[]{"lag"}, new LocaleController$PluralRules_Langi());
        addRules(new String[]{"shi"}, new LocaleController$PluralRules_Tachelhit());
        addRules(new String[]{"mt"}, new LocaleController$PluralRules_Maltese());
        addRules(new String[]{"ga", "se", "sma", "smi", "smj", "smn", "sms"}, new LocaleController$PluralRules_Two());
        addRules(new String[]{"ak", "am", "bh", "fil", "tl", "guw", "hi", "ln", "mg", "nso", "ti", "wa"}, new LocaleController$PluralRules_Zero());
        addRules(new String[]{"az", "bm", LanguageManager.PERSIAN, "ig", "hu", "ja", "kde", "kea", "ko", "my", "ses", "sg", "to", "tr", "vi", "wo", "yo", "zh", "bo", "dz", "id", "jv", "jw", "ka", "km", "kn", "ms", "th", "in"}, new LocaleController$PluralRules_None());
        LocaleController$LocaleInfo localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "English";
        localeInfo.nameEnglish = "English";
        localeInfo.shortName = LanguageManager.ENGLISH;
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "پارسی";
        localeInfo.nameEnglish = "Parsi";
        localeInfo.shortName = LanguageManager.PERSIAN;
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "Italiano";
        localeInfo.nameEnglish = "Italian";
        localeInfo.shortName = "it";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "Español";
        localeInfo.nameEnglish = "Spanish";
        localeInfo.shortName = "es";
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "Deutsch";
        localeInfo.nameEnglish = "German";
        localeInfo.shortName = "de";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "Nederlands";
        localeInfo.nameEnglish = "Dutch";
        localeInfo.shortName = "nl";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "العربية";
        localeInfo.nameEnglish = "Arabic";
        localeInfo.shortName = "ar";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "Português (Brasil)";
        localeInfo.nameEnglish = "Portuguese (Brazil)";
        localeInfo.shortName = "pt_br";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = "한국어";
        localeInfo.nameEnglish = "Korean";
        localeInfo.shortName = "ko";
        localeInfo.pathToFile = null;
        localeInfo.builtIn = true;
        this.languages.add(localeInfo);
        this.languagesDict.put(localeInfo.shortName, localeInfo);
        loadOtherLanguages();
        if (this.remoteLanguages.isEmpty()) {
            AndroidUtilities.runOnUIThread(new LocaleController$1(this));
        }
        for (a = 0; a < this.otherLanguages.size(); a++) {
            LocaleController$LocaleInfo locale = (LocaleController$LocaleInfo) this.otherLanguages.get(a);
            this.languages.add(locale);
            this.languagesDict.put(locale.getKey(), locale);
        }
        for (a = 0; a < this.remoteLanguages.size(); a++) {
            locale = (LocaleController$LocaleInfo) this.remoteLanguages.get(a);
            LocaleController$LocaleInfo existingLocale = getLanguageFromDict(locale.getKey());
            if (existingLocale != null) {
                existingLocale.pathToFile = locale.pathToFile;
                existingLocale.version = locale.version;
            } else {
                this.languages.add(locale);
                this.languagesDict.put(locale.getKey(), locale);
            }
        }
        this.systemDefaultLocale = Locale.getDefault();
        is24HourFormat = DateFormat.is24HourFormat(ApplicationLoader.applicationContext);
        LocaleController$LocaleInfo currentInfo = null;
        boolean override = false;
        try {
            String lang = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getString("language", null);
            if (lang != null) {
                if (lang.contentEquals("fa_IR")) {
                    lang = LanguageManager.PERSIAN;
                }
                currentInfo = getLanguageFromDict(lang);
                if (currentInfo != null) {
                    override = true;
                }
            }
            if (currentInfo == null && this.systemDefaultLocale.getLanguage() != null) {
                currentInfo = getLanguageFromDict(this.systemDefaultLocale.getLanguage());
            }
            if (currentInfo == null) {
                currentInfo = getLanguageFromDict(getLocaleString(this.systemDefaultLocale));
                if (currentInfo == null) {
                    currentInfo = getLanguageFromDict(LanguageManager.ENGLISH);
                }
            }
            applyLanguage(currentInfo, override, true);
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        try {
            ApplicationLoader.applicationContext.registerReceiver(new LocaleController$TimeZoneChangedReceiver(this, null), new IntentFilter("android.intent.action.TIMEZONE_CHANGED"));
        } catch (Throwable e2) {
            FileLog.m94e(e2);
        }
    }

    private LocaleController$LocaleInfo getLanguageFromDict(String key) {
        if (key == null) {
            return null;
        }
        return (LocaleController$LocaleInfo) this.languagesDict.get(key.toLowerCase().replace("-", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
    }

    private void addRules(String[] languages, LocaleController$PluralRules rules) {
        for (String language : languages) {
            this.allRules.put(language, rules);
        }
    }

    private String stringForQuantity(int quantity) {
        switch (quantity) {
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

    public Locale getSystemDefaultLocale() {
        return this.systemDefaultLocale;
    }

    public boolean isCurrentLocalLocale() {
        return this.currentLocaleInfo.isLocal();
    }

    public void reloadCurrentRemoteLocale() {
        applyRemoteLanguage(this.currentLocaleInfo, null, true);
    }

    private String getLocaleString(Locale locale) {
        if (locale == null) {
            return LanguageManager.ENGLISH;
        }
        String languageCode = locale.getLanguage();
        String countryCode = locale.getCountry();
        String variantCode = locale.getVariant();
        if (languageCode.length() == 0 && countryCode.length() == 0) {
            return LanguageManager.ENGLISH;
        }
        StringBuilder result = new StringBuilder(11);
        result.append(languageCode);
        if (countryCode.length() > 0 || variantCode.length() > 0) {
            result.append('_');
        }
        result.append(countryCode);
        if (variantCode.length() > 0) {
            result.append('_');
        }
        result.append(variantCode);
        return result.toString();
    }

    public static String getSystemLocaleStringIso639() {
        Locale locale = getInstance().getSystemDefaultLocale();
        if (locale == null) {
            return LanguageManager.ENGLISH;
        }
        String languageCode = locale.getLanguage();
        String countryCode = locale.getCountry();
        String variantCode = locale.getVariant();
        if (languageCode.length() == 0 && countryCode.length() == 0) {
            return LanguageManager.ENGLISH;
        }
        StringBuilder result = new StringBuilder(11);
        result.append(languageCode);
        if (countryCode.length() > 0 || variantCode.length() > 0) {
            result.append('-');
        }
        result.append(countryCode);
        if (variantCode.length() > 0) {
            result.append('_');
        }
        result.append(variantCode);
        return result.toString();
    }

    public static String getLocaleStringIso639() {
        Locale locale = getInstance().currentLocale;
        if (locale == null) {
            return LanguageManager.ENGLISH;
        }
        String languageCode = locale.getLanguage();
        String countryCode = locale.getCountry();
        String variantCode = locale.getVariant();
        if (languageCode.length() == 0 && countryCode.length() == 0) {
            return LanguageManager.ENGLISH;
        }
        StringBuilder result = new StringBuilder(11);
        result.append(languageCode);
        if (countryCode.length() > 0 || variantCode.length() > 0) {
            result.append('-');
        }
        result.append(countryCode);
        if (variantCode.length() > 0) {
            result.append('_');
        }
        result.append(variantCode);
        return result.toString();
    }

    public static String getLocaleAlias(String code) {
        if (code == null) {
            return null;
        }
        Object obj = -1;
        switch (code.hashCode()) {
            case 3325:
                if (code.equals("he")) {
                    obj = 7;
                    break;
                }
                break;
            case 3355:
                if (code.equals("id")) {
                    obj = 6;
                    break;
                }
                break;
            case 3365:
                if (code.equals("in")) {
                    obj = null;
                    break;
                }
                break;
            case 3374:
                if (code.equals("iw")) {
                    obj = 1;
                    break;
                }
                break;
            case 3391:
                if (code.equals("ji")) {
                    obj = 5;
                    break;
                }
                break;
            case 3404:
                if (code.equals("jv")) {
                    obj = 8;
                    break;
                }
                break;
            case 3405:
                if (code.equals("jw")) {
                    obj = 2;
                    break;
                }
                break;
            case 3508:
                if (code.equals("nb")) {
                    obj = 9;
                    break;
                }
                break;
            case 3521:
                if (code.equals("no")) {
                    obj = 3;
                    break;
                }
                break;
            case 3704:
                if (code.equals("tl")) {
                    obj = 4;
                    break;
                }
                break;
            case 3856:
                if (code.equals("yi")) {
                    obj = 11;
                    break;
                }
                break;
            case 101385:
                if (code.equals("fil")) {
                    obj = 10;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return "id";
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

    public boolean applyLanguageFile(File file) {
        try {
            HashMap<String, String> stringMap = getLocaleFileStrings(file);
            String languageName = (String) stringMap.get("LanguageName");
            String languageNameInEnglish = (String) stringMap.get("LanguageNameInEnglish");
            String languageCode = (String) stringMap.get("LanguageCode");
            if (languageName != null && languageName.length() > 0 && languageNameInEnglish != null && languageNameInEnglish.length() > 0 && languageCode != null && languageCode.length() > 0) {
                if (languageName.contains("&") || languageName.contains("|")) {
                    return false;
                }
                if (languageNameInEnglish.contains("&") || languageNameInEnglish.contains("|")) {
                    return false;
                }
                if (languageCode.contains("&") || languageCode.contains("|") || languageCode.contains("/") || languageCode.contains("\\")) {
                    return false;
                }
                File finalFile = new File(ApplicationLoader.getFilesDirFixed(), languageCode + ".xml");
                if (!AndroidUtilities.copyFile(file, finalFile)) {
                    return false;
                }
                LocaleController$LocaleInfo localeInfo = getLanguageFromDict(languageCode);
                if (localeInfo == null) {
                    localeInfo = new LocaleController$LocaleInfo();
                    localeInfo.name = languageName;
                    localeInfo.nameEnglish = languageNameInEnglish;
                    localeInfo.shortName = languageCode.toLowerCase();
                    localeInfo.pathToFile = finalFile.getAbsolutePath();
                    this.languages.add(localeInfo);
                    this.languagesDict.put(localeInfo.getKey(), localeInfo);
                    this.otherLanguages.add(localeInfo);
                    saveOtherLanguages();
                }
                this.localeValues = stringMap;
                applyLanguage(localeInfo, true, false, true, false);
                return true;
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        return false;
    }

    private void saveOtherLanguages() {
        int a;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("langconfig", 0).edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (a = 0; a < this.otherLanguages.size(); a++) {
            String loc = ((LocaleController$LocaleInfo) this.otherLanguages.get(a)).getSaveString();
            if (loc != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(loc);
            }
        }
        editor.putString("locales", stringBuilder.toString());
        stringBuilder.setLength(0);
        for (a = 0; a < this.remoteLanguages.size(); a++) {
            loc = ((LocaleController$LocaleInfo) this.remoteLanguages.get(a)).getSaveString();
            if (loc != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(loc);
            }
        }
        editor.putString("remote", stringBuilder.toString());
        editor.commit();
    }

    public boolean deleteLanguage(LocaleController$LocaleInfo localeInfo) {
        if (localeInfo.pathToFile == null || localeInfo.isRemote()) {
            return false;
        }
        if (this.currentLocaleInfo == localeInfo) {
            LocaleController$LocaleInfo info = null;
            if (this.systemDefaultLocale.getLanguage() != null) {
                info = getLanguageFromDict(this.systemDefaultLocale.getLanguage());
            }
            if (info == null) {
                info = getLanguageFromDict(getLocaleString(this.systemDefaultLocale));
            }
            if (info == null) {
                info = getLanguageFromDict(LanguageManager.ENGLISH);
            }
            applyLanguage(info, true, false);
        }
        this.otherLanguages.remove(localeInfo);
        this.languages.remove(localeInfo);
        this.languagesDict.remove(localeInfo.shortName);
        new File(localeInfo.pathToFile).delete();
        saveOtherLanguages();
        return true;
    }

    private void loadOtherLanguages() {
        int length;
        LocaleController$LocaleInfo localeInfo;
        int i = 0;
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("langconfig", 0);
        String locales = preferences.getString("locales", null);
        if (!TextUtils.isEmpty(locales)) {
            for (String locale : locales.split("&")) {
                localeInfo = LocaleController$LocaleInfo.createWithString(locale);
                if (localeInfo != null) {
                    this.otherLanguages.add(localeInfo);
                }
            }
        }
        locales = preferences.getString("remote", null);
        if (!TextUtils.isEmpty(locales)) {
            String[] localesArr = locales.split("&");
            length = localesArr.length;
            while (i < length) {
                localeInfo = LocaleController$LocaleInfo.createWithString(localesArr[i]);
                localeInfo.shortName = localeInfo.shortName.replace("-", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                if (localeInfo != null) {
                    this.remoteLanguages.add(localeInfo);
                }
                i++;
            }
        }
    }

    private HashMap<String, String> getLocaleFileStrings(File file) {
        return getLocaleFileStrings(file, false);
    }

    private HashMap<String, String> getLocaleFileStrings(File file, boolean preserveEscapes) {
        Throwable e;
        Throwable th;
        FileInputStream fileInputStream = null;
        this.reloadLastFile = false;
        try {
            HashMap<String, String> stringMap;
            if (file.exists()) {
                stringMap = new HashMap();
                XmlPullParser parser = Xml.newPullParser();
                FileInputStream stream = new FileInputStream(file);
                try {
                    parser.setInput(stream, "UTF-8");
                    String name = null;
                    String value = null;
                    String attrName = null;
                    for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                        if (eventType == 2) {
                            name = parser.getName();
                            if (parser.getAttributeCount() > 0) {
                                attrName = parser.getAttributeValue(0);
                            }
                        } else if (eventType == 4) {
                            if (attrName != null) {
                                value = parser.getText();
                                if (value != null) {
                                    value = value.trim();
                                    if (preserveEscapes) {
                                        value = value.replace("<", "&lt;").replace(">", "&gt;").replace("'", "\\'").replace("& ", "&amp; ");
                                    } else {
                                        value = value.replace("\\n", LogCollector.LINE_SEPARATOR).replace("\\", "");
                                        String old = value;
                                        value = value.replace("&lt;", "<");
                                        if (!(this.reloadLastFile || value.equals(old))) {
                                            this.reloadLastFile = true;
                                        }
                                    }
                                }
                            }
                        } else if (eventType == 3) {
                            value = null;
                            attrName = null;
                            name = null;
                        }
                        if (!(name == null || !name.equals("string") || value == null || attrName == null || value.length() == 0 || attrName.length() == 0)) {
                            stringMap.put(attrName, value);
                            name = null;
                            value = null;
                            attrName = null;
                        }
                    }
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (Throwable e2) {
                            FileLog.m94e(e2);
                        }
                    }
                    fileInputStream = stream;
                    return stringMap;
                } catch (Exception e3) {
                    e2 = e3;
                    fileInputStream = stream;
                    try {
                        FileLog.m94e(e2);
                        this.reloadLastFile = true;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable e22) {
                                FileLog.m94e(e22);
                            }
                        }
                        return new HashMap();
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable e222) {
                                FileLog.m94e(e222);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = stream;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            }
            stringMap = new HashMap();
            if (fileInputStream == null) {
                return stringMap;
            }
            try {
                fileInputStream.close();
                return stringMap;
            } catch (Throwable e2222) {
                FileLog.m94e(e2222);
                return stringMap;
            }
        } catch (Exception e4) {
            e2222 = e4;
            FileLog.m94e(e2222);
            this.reloadLastFile = true;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return new HashMap();
        }
    }

    public void applyLanguage(LocaleController$LocaleInfo localeInfo, boolean override, boolean init) {
        applyLanguage(localeInfo, override, init, false, false);
    }

    public void applyLanguage(LocaleController$LocaleInfo localeInfo, boolean override, boolean init, boolean fromFile, boolean force) {
        if (!(localeInfo == null || TextUtils.isEmpty(localeInfo.getKey()) || !localeInfo.getKey().contentEquals("fa_IR"))) {
            localeInfo.shortName = LanguageManager.PERSIAN;
        }
        if (localeInfo != null) {
            File pathToFile = localeInfo.getPathToFile();
            String shortName = localeInfo.shortName;
            if (!init) {
                ConnectionsManager.getInstance().setLangCode(shortName.replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-"));
            }
            if (localeInfo.isRemote() && (force || !pathToFile.exists())) {
                FileLog.m91d("reload locale because file doesn't exist " + pathToFile);
                if (init) {
                    AndroidUtilities.runOnUIThread(new LocaleController$2(this, localeInfo));
                } else {
                    applyRemoteLanguage(localeInfo, null, true);
                }
            }
            try {
                Locale newLocale;
                String[] args = localeInfo.shortName.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                if (args.length == 1) {
                    newLocale = new Locale(localeInfo.shortName);
                } else {
                    newLocale = new Locale(args[0], args[1]);
                }
                if (override) {
                    this.languageOverride = localeInfo.shortName;
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                    editor.putString("language", localeInfo.getKey());
                    editor.commit();
                }
                if (pathToFile == null) {
                    this.localeValues.clear();
                } else if (!fromFile) {
                    this.localeValues = getLocaleFileStrings(pathToFile);
                }
                this.currentLocale = newLocale;
                this.currentLocaleInfo = localeInfo;
                this.currentPluralRules = (LocaleController$PluralRules) this.allRules.get(args[0]);
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = (LocaleController$PluralRules) this.allRules.get(this.currentLocale.getLanguage());
                }
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = new LocaleController$PluralRules_None();
                }
                this.changingConfiguration = true;
                Locale.setDefault(this.currentLocale);
                Configuration config = new Configuration();
                config.locale = this.currentLocale;
                ApplicationLoader.applicationContext.getResources().updateConfiguration(config, ApplicationLoader.applicationContext.getResources().getDisplayMetrics());
                this.changingConfiguration = false;
                if (this.reloadLastFile) {
                    if (init) {
                        AndroidUtilities.runOnUIThread(new LocaleController$3(this));
                    } else {
                        reloadCurrentRemoteLocale();
                    }
                    this.reloadLastFile = false;
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
                this.changingConfiguration = false;
            }
            recreateFormatters();
        }
    }

    public LocaleController$LocaleInfo getCurrentLocaleInfo() {
        return this.currentLocaleInfo;
    }

    public static String getCurrentLanguageName() {
        return getString("LanguageName", R.string.LanguageName);
    }

    private String getStringInternal(String key, int res) {
        String value = (String) this.localeValues.get(key);
        if (value == null) {
            try {
                value = ApplicationLoader.applicationContext.getString(res);
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
        if (value == null) {
            return "LOC_ERR:" + key;
        }
        return value;
    }

    public static String getString(String key, int res) {
        return getInstance().getStringInternal(key, res);
    }

    public static String getPluralString(String key, int plural) {
        if (key == null || key.length() == 0 || getInstance().currentPluralRules == null) {
            return "LOC_ERR:" + key;
        }
        String param = key + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getInstance().stringForQuantity(getInstance().currentPluralRules.quantityForNumber(plural));
        return getString(param, ApplicationLoader.applicationContext.getResources().getIdentifier(param, "string", ApplicationLoader.applicationContext.getPackageName()));
    }

    public static String formatPluralString(String key, int plural) {
        if (key == null || key.length() == 0 || getInstance().currentPluralRules == null) {
            return "LOC_ERR:" + key;
        }
        String param = key + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getInstance().stringForQuantity(getInstance().currentPluralRules.quantityForNumber(plural));
        return formatString(param, ApplicationLoader.applicationContext.getResources().getIdentifier(param, "string", ApplicationLoader.applicationContext.getPackageName()), Integer.valueOf(plural));
    }

    public static String formatString(String key, int res, Object... args) {
        try {
            String value = (String) getInstance().localeValues.get(key);
            if (value == null) {
                value = ApplicationLoader.applicationContext.getString(res);
            }
            if (getInstance().currentLocale != null) {
                return String.format(getInstance().currentLocale, value, args);
            }
            return String.format(value, args);
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR: " + key;
        }
    }

    public static String formatTTLString(int ttl) {
        if (ttl < 60) {
            return formatPluralString("Seconds", ttl);
        }
        if (ttl < 3600) {
            return formatPluralString("Minutes", ttl / 60);
        }
        if (ttl < 86400) {
            return formatPluralString("Hours", (ttl / 60) / 60);
        }
        if (ttl < 604800) {
            return formatPluralString("Days", ((ttl / 60) / 60) / 24);
        }
        int days = ((ttl / 60) / 60) / 24;
        if (ttl % 7 == 0) {
            return formatPluralString("Weeks", days / 7);
        }
        return String.format("%s %s", new Object[]{formatPluralString("Weeks", days / 7), formatPluralString("Days", days % 7)});
    }

    public String formatCurrencyString(long amount, String type) {
        boolean discount;
        String customFormat;
        double doubleAmount;
        type = type.toUpperCase();
        if (amount < 0) {
            discount = true;
        } else {
            discount = false;
        }
        amount = Math.abs(amount);
        int i = -1;
        switch (type.hashCode()) {
            case 65726:
                if (type.equals("BHD")) {
                    i = 1;
                    break;
                }
                break;
            case 65759:
                if (type.equals("BIF")) {
                    i = 8;
                    break;
                }
                break;
            case 66267:
                if (type.equals("BYR")) {
                    i = 9;
                    break;
                }
                break;
            case 66813:
                if (type.equals("CLF")) {
                    i = 0;
                    break;
                }
                break;
            case 66823:
                if (type.equals("CLP")) {
                    i = 10;
                    break;
                }
                break;
            case 67122:
                if (type.equals("CVE")) {
                    i = 11;
                    break;
                }
                break;
            case 67712:
                if (type.equals("DJF")) {
                    i = 12;
                    break;
                }
                break;
            case 70719:
                if (type.equals("GNF")) {
                    i = 13;
                    break;
                }
                break;
            case 72732:
                if (type.equals("IQD")) {
                    i = 2;
                    break;
                }
                break;
            case 72801:
                if (type.equals("ISK")) {
                    i = 14;
                    break;
                }
                break;
            case 73631:
                if (type.equals("JOD")) {
                    i = 3;
                    break;
                }
                break;
            case 73683:
                if (type.equals("JPY")) {
                    i = 15;
                    break;
                }
                break;
            case 74532:
                if (type.equals("KMF")) {
                    i = 16;
                    break;
                }
                break;
            case 74704:
                if (type.equals("KRW")) {
                    i = 17;
                    break;
                }
                break;
            case 74840:
                if (type.equals("KWD")) {
                    i = 4;
                    break;
                }
                break;
            case 75863:
                if (type.equals("LYD")) {
                    i = 5;
                    break;
                }
                break;
            case 76263:
                if (type.equals("MGA")) {
                    i = 18;
                    break;
                }
                break;
            case 76618:
                if (type.equals("MRO")) {
                    i = 28;
                    break;
                }
                break;
            case 78388:
                if (type.equals("OMR")) {
                    i = 6;
                    break;
                }
                break;
            case 79710:
                if (type.equals("PYG")) {
                    i = 19;
                    break;
                }
                break;
            case 81569:
                if (type.equals("RWF")) {
                    i = 20;
                    break;
                }
                break;
            case 83210:
                if (type.equals("TND")) {
                    i = 7;
                    break;
                }
                break;
            case 83974:
                if (type.equals("UGX")) {
                    i = 21;
                    break;
                }
                break;
            case 84517:
                if (type.equals("UYI")) {
                    i = 22;
                    break;
                }
                break;
            case 85132:
                if (type.equals("VND")) {
                    i = 23;
                    break;
                }
                break;
            case 85367:
                if (type.equals("VUV")) {
                    i = 24;
                    break;
                }
                break;
            case 86653:
                if (type.equals("XAF")) {
                    i = 25;
                    break;
                }
                break;
            case 87087:
                if (type.equals("XOF")) {
                    i = 26;
                    break;
                }
                break;
            case 87118:
                if (type.equals("XPF")) {
                    i = 27;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                customFormat = " %.4f";
                doubleAmount = ((double) amount) / 10000.0d;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                customFormat = " %.3f";
                doubleAmount = ((double) amount) / 1000.0d;
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
                customFormat = " %.0f";
                doubleAmount = (double) amount;
                break;
            case 28:
                customFormat = " %.1f";
                doubleAmount = ((double) amount) / 10.0d;
                break;
            default:
                customFormat = " %.2f";
                doubleAmount = ((double) amount) / 100.0d;
                break;
        }
        Currency сurrency = Currency.getInstance(type);
        if (сurrency != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance(this.currentLocale != null ? this.currentLocale : this.systemDefaultLocale);
            format.setCurrency(сurrency);
            return (discount ? "-" : "") + format.format(doubleAmount);
        }
        return (discount ? "-" : "") + String.format(Locale.US, type + customFormat, new Object[]{Double.valueOf(doubleAmount)});
    }

    public String formatCurrencyDecimalString(long amount, String type, boolean inludeType) {
        String customFormat;
        double doubleAmount;
        type = type.toUpperCase();
        amount = Math.abs(amount);
        int i = -1;
        switch (type.hashCode()) {
            case 65726:
                if (type.equals("BHD")) {
                    i = 1;
                    break;
                }
                break;
            case 65759:
                if (type.equals("BIF")) {
                    i = 8;
                    break;
                }
                break;
            case 66267:
                if (type.equals("BYR")) {
                    i = 9;
                    break;
                }
                break;
            case 66813:
                if (type.equals("CLF")) {
                    i = 0;
                    break;
                }
                break;
            case 66823:
                if (type.equals("CLP")) {
                    i = 10;
                    break;
                }
                break;
            case 67122:
                if (type.equals("CVE")) {
                    i = 11;
                    break;
                }
                break;
            case 67712:
                if (type.equals("DJF")) {
                    i = 12;
                    break;
                }
                break;
            case 70719:
                if (type.equals("GNF")) {
                    i = 13;
                    break;
                }
                break;
            case 72732:
                if (type.equals("IQD")) {
                    i = 2;
                    break;
                }
                break;
            case 72801:
                if (type.equals("ISK")) {
                    i = 14;
                    break;
                }
                break;
            case 73631:
                if (type.equals("JOD")) {
                    i = 3;
                    break;
                }
                break;
            case 73683:
                if (type.equals("JPY")) {
                    i = 15;
                    break;
                }
                break;
            case 74532:
                if (type.equals("KMF")) {
                    i = 16;
                    break;
                }
                break;
            case 74704:
                if (type.equals("KRW")) {
                    i = 17;
                    break;
                }
                break;
            case 74840:
                if (type.equals("KWD")) {
                    i = 4;
                    break;
                }
                break;
            case 75863:
                if (type.equals("LYD")) {
                    i = 5;
                    break;
                }
                break;
            case 76263:
                if (type.equals("MGA")) {
                    i = 18;
                    break;
                }
                break;
            case 76618:
                if (type.equals("MRO")) {
                    i = 28;
                    break;
                }
                break;
            case 78388:
                if (type.equals("OMR")) {
                    i = 6;
                    break;
                }
                break;
            case 79710:
                if (type.equals("PYG")) {
                    i = 19;
                    break;
                }
                break;
            case 81569:
                if (type.equals("RWF")) {
                    i = 20;
                    break;
                }
                break;
            case 83210:
                if (type.equals("TND")) {
                    i = 7;
                    break;
                }
                break;
            case 83974:
                if (type.equals("UGX")) {
                    i = 21;
                    break;
                }
                break;
            case 84517:
                if (type.equals("UYI")) {
                    i = 22;
                    break;
                }
                break;
            case 85132:
                if (type.equals("VND")) {
                    i = 23;
                    break;
                }
                break;
            case 85367:
                if (type.equals("VUV")) {
                    i = 24;
                    break;
                }
                break;
            case 86653:
                if (type.equals("XAF")) {
                    i = 25;
                    break;
                }
                break;
            case 87087:
                if (type.equals("XOF")) {
                    i = 26;
                    break;
                }
                break;
            case 87118:
                if (type.equals("XPF")) {
                    i = 27;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                customFormat = " %.4f";
                doubleAmount = ((double) amount) / 10000.0d;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                customFormat = " %.3f";
                doubleAmount = ((double) amount) / 1000.0d;
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
                customFormat = " %.0f";
                doubleAmount = (double) amount;
                break;
            case 28:
                customFormat = " %.1f";
                doubleAmount = ((double) amount) / 10.0d;
                break;
            default:
                customFormat = " %.2f";
                doubleAmount = ((double) amount) / 100.0d;
                break;
        }
        Locale locale = Locale.US;
        if (!inludeType) {
            type = "" + customFormat;
        }
        return String.format(locale, type, new Object[]{Double.valueOf(doubleAmount)}).trim();
    }

    public static String formatStringSimple(String string, Object... args) {
        try {
            if (getInstance().currentLocale != null) {
                return String.format(getInstance().currentLocale, string, args);
            }
            return String.format(string, args);
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR: " + string;
        }
    }

    public static String formatCallDuration(int duration) {
        if (duration > 3600) {
            String result = formatPluralString("Hours", duration / 3600);
            int minutes = (duration % 3600) / 60;
            if (minutes > 0) {
                return result + ", " + formatPluralString("Minutes", minutes);
            }
            return result;
        } else if (duration > 60) {
            return formatPluralString("Minutes", duration / 60);
        } else {
            return formatPluralString("Seconds", duration);
        }
    }

    public void onDeviceConfigurationChange(Configuration newConfig) {
        if (!this.changingConfiguration) {
            is24HourFormat = DateFormat.is24HourFormat(ApplicationLoader.applicationContext);
            this.systemDefaultLocale = newConfig.locale;
            if (this.languageOverride != null) {
                LocaleController$LocaleInfo toSet = this.currentLocaleInfo;
                this.currentLocaleInfo = null;
                applyLanguage(toSet, false, false);
                return;
            }
            Locale newLocale = newConfig.locale;
            if (newLocale != null) {
                String d1 = newLocale.getDisplayName();
                String d2 = this.currentLocale.getDisplayName();
                if (!(d1 == null || d2 == null || d1.equals(d2))) {
                    recreateFormatters();
                }
                this.currentLocale = newLocale;
                this.currentPluralRules = (LocaleController$PluralRules) this.allRules.get(this.currentLocale.getLanguage());
                if (this.currentPluralRules == null) {
                    this.currentPluralRules = (LocaleController$PluralRules) this.allRules.get(LanguageManager.ENGLISH);
                }
            }
        }
    }

    public static String formatDateChat(long date) {
        try {
            date *= 1000;
            Calendar.getInstance().setTimeInMillis(date);
            if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                return getInstance().chatDate.format(date);
            }
            return getInstance().chatFullDate.format(date);
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR: formatDateChat";
        }
    }

    public static String formatDate(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            int dateYear = rightNow.get(1);
            if (dateDay == day && year == dateYear) {
                return getInstance().formatterDay.format(new Date(date));
            }
            if (dateDay + 1 == day && year == dateYear) {
                return getString("Yesterday", R.string.Yesterday);
            }
            if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                return getInstance().formatterMonth.format(new Date(date));
            }
            return getInstance().formatterYear.format(new Date(date));
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR: formatDate";
        }
    }

    public static String formatDateAudio(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            int dateYear = rightNow.get(1);
            if (dateDay == day && year == dateYear) {
                return String.format("%s %s", new Object[]{getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (dateDay + 1 == day && year == dateYear) {
                return String.format("%s %s", new Object[]{getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
            } else {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    public static String formatDateCallLog(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            int dateYear = rightNow.get(1);
            if (dateDay == day && year == dateYear) {
                return getInstance().formatterDay.format(new Date(date));
            }
            if (dateDay + 1 == day && year == dateYear) {
                return String.format("%s %s", new Object[]{getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().chatDate.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
            } else {
                return formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().chatFullDate.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    public static String formatLocationUpdateDate(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            int dateYear = rightNow.get(1);
            if (dateDay == day && year == dateYear) {
                int diff = ((int) (((long) ConnectionsManager.getInstance().getCurrentTime()) - (date / 1000))) / 60;
                if (diff < 1) {
                    return getString("LocationUpdatedJustNow", R.string.LocationUpdatedJustNow);
                }
                if (diff < 60) {
                    return formatPluralString("UpdatedMinutes", diff);
                }
                return String.format("%s %s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (dateDay + 1 == day && year == dateYear) {
                return String.format("%s %s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                format = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
                return String.format("%s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), format});
            } else {
                format = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
                return String.format("%s %s", new Object[]{getString("LocationUpdated", R.string.LocationUpdated), format});
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    public static String formatLocationLeftTime(int time) {
        int i = 1;
        int hours = (time / 60) / 60;
        time -= (hours * 60) * 60;
        int minutes = time / 60;
        time -= minutes * 60;
        String str;
        Object[] objArr;
        if (hours != 0) {
            str = "%dh";
            objArr = new Object[1];
            if (minutes <= 30) {
                i = 0;
            }
            objArr[0] = Integer.valueOf(i + hours);
            return String.format(str, objArr);
        } else if (minutes != 0) {
            str = "%d";
            objArr = new Object[1];
            if (time <= 30) {
                i = 0;
            }
            objArr[0] = Integer.valueOf(i + minutes);
            return String.format(str, objArr);
        } else {
            return String.format("%d", new Object[]{Integer.valueOf(time)});
        }
    }

    public static String formatDateOnline(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            int dateYear = rightNow.get(1);
            if (dateDay == day && year == dateYear) {
                return String.format("%s %s %s", new Object[]{getString("LastSeen", R.string.LastSeen), getString("TodayAt", R.string.TodayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (dateDay + 1 == day && year == dateYear) {
                return String.format("%s %s %s", new Object[]{getString("LastSeen", R.string.LastSeen), getString("YesterdayAt", R.string.YesterdayAt), getInstance().formatterDay.format(new Date(date))});
            } else if (Math.abs(System.currentTimeMillis() - date) < 31536000000L) {
                format = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterMonth.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
                return String.format("%s %s", new Object[]{getString("LastSeenDate", R.string.LastSeenDate), format});
            } else {
                format = formatString("formatDateAtTime", R.string.formatDateAtTime, getInstance().formatterYear.format(new Date(date)), getInstance().formatterDay.format(new Date(date)));
                return String.format("%s %s", new Object[]{getString("LastSeenDate", R.string.LastSeenDate), format});
            }
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    private FastDateFormat createFormatter(Locale locale, String format, String defaultFormat) {
        if (format == null || format.length() == 0) {
            format = defaultFormat;
        }
        try {
            return FastDateFormat.getInstance(format, locale);
        } catch (Exception e) {
            return FastDateFormat.getInstance(defaultFormat, locale);
        }
    }

    public void recreateFormatters() {
        int i = 1;
        Locale locale = this.currentLocale;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String lang = locale.getLanguage();
        if (lang == null) {
            lang = LanguageManager.ENGLISH;
        }
        lang = lang.toLowerCase();
        boolean z = lang.startsWith("ar") || lang.toLowerCase().equals(LanguageManager.PERSIAN) || (BuildVars.DEBUG_VERSION && (lang.startsWith("he") || lang.startsWith("iw") || lang.startsWith(LanguageManager.PERSIAN)));
        isRTL = z;
        if (lang.equals("ko")) {
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
        Locale locale2 = (lang.toLowerCase().equals("ar") || lang.toLowerCase().equals("ko")) ? locale : Locale.US;
        this.formatterDay = createFormatter(locale2, is24HourFormat ? getStringInternal("formatterDay24H", R.string.formatterDay24H) : getStringInternal("formatterDay12H", R.string.formatterDay12H), is24HourFormat ? "HH:mm" : "h:mm a");
        this.formatterStats = createFormatter(locale, is24HourFormat ? getStringInternal("formatterStats24H", R.string.formatterStats24H) : getStringInternal("formatterStats12H", R.string.formatterStats12H), is24HourFormat ? "MMM dd yyyy, HH:mm" : "MMM dd yyyy, h:mm a");
        this.formatterBannedUntil = createFormatter(locale, is24HourFormat ? getStringInternal("formatterBannedUntil24H", R.string.formatterBannedUntil24H) : getStringInternal("formatterBannedUntil12H", R.string.formatterBannedUntil12H), is24HourFormat ? "MMM dd yyyy, HH:mm" : "MMM dd yyyy, h:mm a");
        this.formatterBannedUntilThisYear = createFormatter(locale, is24HourFormat ? getStringInternal("formatterBannedUntilThisYear24H", R.string.formatterBannedUntilThisYear24H) : getStringInternal("formatterBannedUntilThisYear12H", R.string.formatterBannedUntilThisYear12H), is24HourFormat ? "MMM dd, HH:mm" : "MMM dd, h:mm a");
    }

    public static boolean isRTLCharacter(char ch) {
        return Character.getDirectionality(ch) == (byte) 1 || Character.getDirectionality(ch) == (byte) 2 || Character.getDirectionality(ch) == (byte) 16 || Character.getDirectionality(ch) == (byte) 17;
    }

    public static String formatDateForBan(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int year = rightNow.get(1);
            rightNow.setTimeInMillis(date);
            if (year == rightNow.get(1)) {
                return getInstance().formatterBannedUntilThisYear.format(new Date(date));
            }
            return getInstance().formatterBannedUntil.format(new Date(date));
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    public static String stringForMessageListDate(long date) {
        date *= 1000;
        try {
            Calendar rightNow = Calendar.getInstance();
            int day = rightNow.get(6);
            rightNow.setTimeInMillis(date);
            int dateDay = rightNow.get(6);
            if (Math.abs(System.currentTimeMillis() - date) >= 31536000000L) {
                return getInstance().formatterYear.format(new Date(date));
            }
            int dayDiff = dateDay - day;
            if (dayDiff == 0 || (dayDiff == -1 && System.currentTimeMillis() - date < 28800000)) {
                return getInstance().formatterDay.format(new Date(date));
            }
            if (dayDiff > -7 && dayDiff <= -1) {
                return getInstance().formatterWeek.format(new Date(date));
            }
            PersianCalendar calendar = new PersianCalendar(date);
            String timeStr = calendar.getPersianMonthName() + " " + calendar.getPersianDay();
            if (getCurrentLanguageName().contentEquals("فارسی")) {
                return timeStr;
            }
            return getInstance().formatterMonth.format(new Date(date));
        } catch (Throwable e) {
            FileLog.m94e(e);
            return "LOC_ERR";
        }
    }

    public static String formatShortNumber(int number, int[] rounded) {
        StringBuilder K = new StringBuilder();
        int lastDec = 0;
        while (number / 1000 > 0) {
            K.append("K");
            lastDec = (number % 1000) / 100;
            number /= 1000;
        }
        if (rounded != null) {
            double value = ((double) number) + (((double) lastDec) / 10.0d);
            for (int a = 0; a < K.length(); a++) {
                value *= 1000.0d;
            }
            rounded[0] = (int) value;
        }
        if (lastDec == 0 || K.length() <= 0) {
            if (K.length() == 2) {
                return String.format(Locale.US, "%dM", new Object[]{Integer.valueOf(number)});
            }
            return String.format(Locale.US, "%d%s", new Object[]{Integer.valueOf(number), K.toString()});
        } else if (K.length() == 2) {
            return String.format(Locale.US, "%d.%dM", new Object[]{Integer.valueOf(number), Integer.valueOf(lastDec)});
        } else {
            return String.format(Locale.US, "%d.%d%s", new Object[]{Integer.valueOf(number), Integer.valueOf(lastDec), K.toString()});
        }
    }

    public static String formatUserStatus(User user) {
        if (!(user == null || user.status == null || user.status.expires != 0)) {
            if (user.status instanceof TLRPC$TL_userStatusRecently) {
                user.status.expires = -100;
            } else if (user.status instanceof TLRPC$TL_userStatusLastWeek) {
                user.status.expires = FetchConst.ERROR_UNKNOWN;
            } else if (user.status instanceof TLRPC$TL_userStatusLastMonth) {
                user.status.expires = FetchConst.ERROR_FILE_NOT_CREATED;
            }
        }
        if (user != null && user.status != null && user.status.expires <= 0 && MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(user.id))) {
            return getString("Online", R.string.Online);
        }
        if (user == null || user.status == null || user.status.expires == 0 || UserObject.isDeleted(user) || (user instanceof TLRPC$TL_userEmpty)) {
            return getString("ALongTimeAgo", R.string.ALongTimeAgo);
        }
        if (user.status.expires > ConnectionsManager.getInstance().getCurrentTime()) {
            return getString("Online", R.string.Online);
        }
        if (user.status.expires == -1) {
            return getString("Invisible", R.string.Invisible);
        }
        if (user.status.expires == -100) {
            return getString("Lately", R.string.Lately);
        }
        if (user.status.expires == FetchConst.ERROR_UNKNOWN) {
            return getString("WithinAWeek", R.string.WithinAWeek);
        }
        if (user.status.expires == FetchConst.ERROR_FILE_NOT_CREATED) {
            return getString("WithinAMonth", R.string.WithinAMonth);
        }
        return formatDateOnline((long) user.status.expires);
    }

    private String escapeString(String str) {
        return str.contains("[CDATA") ? str : str.replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
    }

    public void saveRemoteLocaleStrings(TLRPC$TL_langPackDifference difference) {
        if (difference != null && !difference.strings.isEmpty()) {
            String langCode = difference.lang_code.replace('-', '_').toLowerCase();
            File finalFile = new File(ApplicationLoader.getFilesDirFixed(), "remote_" + langCode + ".xml");
            try {
                HashMap<String, String> values;
                if (difference.from_version == 0) {
                    values = new HashMap();
                } else {
                    values = getLocaleFileStrings(finalFile, true);
                }
                for (int a = 0; a < difference.strings.size(); a++) {
                    TLRPC$LangPackString string = (TLRPC$LangPackString) difference.strings.get(a);
                    if (string instanceof TLRPC$TL_langPackString) {
                        values.put(string.key, escapeString(string.value));
                    } else if (string instanceof TLRPC$TL_langPackStringPluralized) {
                        Object escapeString;
                        values.put(string.key + "_zero", string.zero_value != null ? escapeString(string.zero_value) : "");
                        values.put(string.key + "_one", string.one_value != null ? escapeString(string.one_value) : "");
                        values.put(string.key + "_two", string.two_value != null ? escapeString(string.two_value) : "");
                        values.put(string.key + "_few", string.few_value != null ? escapeString(string.few_value) : "");
                        values.put(string.key + "_many", string.many_value != null ? escapeString(string.many_value) : "");
                        String str = string.key + "_other";
                        if (string.other_value != null) {
                            escapeString = escapeString(string.other_value);
                        } else {
                            escapeString = "";
                        }
                        values.put(str, escapeString);
                    } else if (string instanceof TLRPC$TL_langPackStringDeleted) {
                        values.remove(string.key);
                    }
                }
                FileLog.m91d("save locale file to " + finalFile);
                BufferedWriter writer = new BufferedWriter(new FileWriter(finalFile));
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                writer.write("<resources>\n");
                for (Entry<String, String> entry : values.entrySet()) {
                    writer.write(String.format("<string name=\"%1$s\">%2$s</string>\n", new Object[]{entry.getKey(), entry.getValue()}));
                }
                writer.write("</resources>");
                writer.close();
                AndroidUtilities.runOnUIThread(new LocaleController$4(this, langCode, difference, getLocaleFileStrings(finalFile)));
            } catch (Exception e) {
            }
        }
    }

    public void loadRemoteLanguages() {
        if (!this.loadingRemoteLanguages) {
            this.loadingRemoteLanguages = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_langpack_getLanguages(), new LocaleController$5(this), 8);
        }
    }

    private void applyRemoteLanguage(LocaleController$LocaleInfo localeInfo, TLRPC$TL_langPackLanguage language, boolean force) {
        if (localeInfo != null || language != null) {
            if (localeInfo != null && !localeInfo.isRemote()) {
                return;
            }
            if (localeInfo.version == 0 || force) {
                ConnectionsManager.getInstance().setLangCode(localeInfo != null ? localeInfo.shortName : language.lang_code);
                TLRPC$TL_langpack_getLangPack req = new TLRPC$TL_langpack_getLangPack();
                if (language == null) {
                    req.lang_code = localeInfo.shortName;
                } else {
                    req.lang_code = language.lang_code;
                }
                req.lang_code = req.lang_code.replace(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, "-");
                ConnectionsManager.getInstance().sendRequest(req, new LocaleController$7(this), 8);
                return;
            }
            TLRPC$TL_langpack_getDifference req2 = new TLRPC$TL_langpack_getDifference();
            req2.from_version = localeInfo.version;
            ConnectionsManager.getInstance().sendRequest(req2, new LocaleController$6(this), 8);
        }
    }

    public String getTranslitString(String src) {
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
            this.translitChars.put("ь", "");
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
            this.translitChars.put("ъ", "");
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
        StringBuilder dst = new StringBuilder(src.length());
        int len = src.length();
        for (int a = 0; a < len; a++) {
            String ch = src.substring(a, a + 1);
            String tch = (String) this.translitChars.get(ch);
            if (tch != null) {
                dst.append(tch);
            } else {
                dst.append(ch);
            }
        }
        return dst.toString();
    }
}
