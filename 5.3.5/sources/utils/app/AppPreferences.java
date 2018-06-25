package utils.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.WebservicePropertis;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.customization.Model.OfficialJoinChannel;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import utils.view.Constants;

public class AppPreferences {
    public static final String ADS_CAHNNEL = "ADS_CAHNNEL";
    public static final String APP_VERSION = "APP_VERSION";
    public static final String CART_COUNT = "CART_COUNT";
    public static final String CHANGE_JOIN_TIME = "CHANGE_JOIN_TIME";
    public static final String CHECK_LOCAL_URL = "CHECK_LOCAL_URL";
    public static final String CURRENT_USER_ADDED_TO_FAVE = "CURRENT_USER_ADDED_TO_FAVE";
    public static final String DIALOG_TABS = "DIALOG_TABS";
    public static final String END_DOWNLOAD_TIME = "END_DOWNLOAD_TIME";
    public static final String FILTER_CHANNEL = "FILTER_CHANNEL";
    public static final String FREE_TEXT = "FREE_TEXT";
    public static final String HIDDEN_CHATS = "HIDDEN_CHATS";
    public static final String HOME_FRAGMENT_POS = "HOME_FRAGMENT_POS";
    public static final String HOT_POST_RANDOM_NUM = "HOT_POST_RANDOM_NUM";
    public static final String INVITE_MESSEAGE = "INVITE_MESSEAGE";
    public static final String IS_ENABLE_ADS = "IS_ENABLE_ADS";
    public static final String IS_ENABLE_FREE_ICON = "IS_ENABLE_FREE_ICON";
    public static final String IS_ENABLE_GHOST = "IS_ENABLE_GHOST";
    public static final String IS_ENABLE_PROXY_FOR_REG = "IS_ENABLE_PROXY_FOR_REG";
    public static final String IS_ENABLE_SCHEDULE_DOWNLOAD = "IS_ENABLE_SCHEDULE_DOWNLOAD";
    public static final String IS_ENABLE_STREAM = "IS_ENABLE_STREAM";
    public static final String IS_JOIN = "IS_JOIN";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String IS_LOGIN_BY_GOOGLE = "IS_LOGIN_BY_GOOGLE";
    public static final String IS_SHOW_COIN = "IS_SHOW_COIN";
    public static final String IS_SHOW_QUCIK_ICON = "IS_SHOW_QUCIK_ICON";
    public static final String JOIN_OFFICIAL_CHANNEL = "JOIN_OFFICIAL_CHANNEL";
    public static final String LAST_RUNNING_DOWNLODER_SERVICE_TIME = "LAST_RUNNING_DOWNLODER_SERVICE_TIME";
    public static final String LAST_RUNNING_SERVICE_TIME = "LAST_RUNNING_SERVICE_TIME";
    public static final String LAST_RUNNING_SERVICE_TIME_FOR_CONTACTS = "LAST_RUNNING_SERVICE_TIME_FOR_CONTACTS";
    public static final String LAST_RUNNING_SERVICE_TIME_FOR_LOCATION = "LAST_RUNNING_SERVICE_TIME_FOR_LOCATION";
    public static final String MAIN_DOMAIN = "MAIN_DOMAIN";
    public static final String MAIN_DOMAIN_CHECK_URL = "MAIN_DOMAIN_CHECK_URL";
    public static final String NETWORK_TYPE = "NETWORK_TYPE";
    public static final String NEWS_TAG_ID = "NEWS_TAG_ID";
    public static final String OFF_MODE = "OFF_MODE";
    public static final String PASS_FOR_LOCK_CHATS = "PFLC";
    public static final String PINNED_DIALOGS = "PINNED_DIALOGS";
    public static final String PUSH_MESSAGE_ID = "PUSH_MESSAGE_ID";
    public static final String RESEND_VERIFICATION_T = "UMRVT";
    public static final String RESEND_VERIFICATION_V = "UMRVV";
    public static final String SAVE_CHANNEL_PERIOD = "SAVE_CHANNEL_PERIOD";
    public static final String SAVE_CONTACT_PERIOD = "SAVE_CONTACT_PERIOD";
    public static final String SAVE_LOCATION_PERIOD = "SAVE_LOCATION_PERIOD";
    public static final String SCHEDULED_DOWNLOAD = "SCHEDULED_DOWNLOAD";
    public static final String SECURITY_TOKEN = "SECURITY_TOKEN";
    public static final String SELECTED_FONT = "SELECTED_FONT";
    public static final String SHOW_ELECTION_TAB = "SHOW_ELECTION_TAB";
    public static final String SHOW_FREE_POPUP = "SHOW_FREE_POPUP";
    public static final String SHOW_HIDDEN_CHATS_TAB = "SHOW_HIDDEN_CHATS_TAB";
    public static final String SHOW_HIDDEN_DIALOGS = "SHOW_HIDDEN_DIALOGS";
    public static final String SHOW_HOT_TAB = "SHOW_HOT_TAB";
    public static final String SHOW_ICON = "SHOW_ICON";
    public static final String SHOW_NEWS_LIST = "SHOW_NEWS_LIST";
    public static final String SHOW_SEARCH_TAB = "SHOW_SEARCH_TAB";
    static final String SIGN_SCHEME = "SHA256withRSA";
    public static final String SORT_DIALOGS_BY_UNREAD = "SORT_DIALOGS_BY_UNREAD";
    public static final String SP_ADS_JOIN_MSG = "SP_ADS_JOIN_MSG";
    public static final String SP_ADS_TU = "SP_ADS_TU";
    public static final String SP_ADS_TU1 = "SP_ADS_TU1";
    public static final String SP_ADS_TU_URL = "SP_ADS_TU_URL";
    public static final String SP_ADS_URL = "SP_ADS_URL";
    public static final String SP_AP_REGISTERED = "AP_REGISTERED";
    public static final String SP_BOT_SYNC_PERIOD = "SP_BOT_SYNC_PERIOD";
    public static final String SP_CHANNEL_SYNC_PERIOD = "SP_CHANNEL_SYNC_PERIOD";
    public static final String SP_CONTACT_SYNC_PERIOD = "SP_CONTACT_SYNC_PERIOD";
    public static final String SP_COUNTRY_LIST = "SP_COUNTRY_LIST";
    public static final String SP_DEFAULT_MIRROR_PROXY = "SP_DEFAULT_MIRROR_PROXY";
    public static final String SP_DEFAULT_MIRROR_REGISTER_PROXY = "SP_DEFAULT_MIRROR_REGISTER_PROXY";
    public static final String SP_DEFAULT_PROXY = "SP_DEFAULT_PROXY";
    public static final String SP_DEFAULT_REGISTER_PROXY = "SP_DEFAULT_REGISTER_PROXY";
    public static final String SP_EMAIL = "SP_EMAIL";
    public static final String SP_FILTER_MESSAGE = "SP_FILTER_MESSAGE";
    public static final String SP_FIRST_TIME_SYNC = "SP_FIRST_TIME_SYNC";
    public static final String SP_FULLNAME = "SP_FULLNAME";
    public static final String SP_GET_PROXY_PERIOD = "SP_GET_PROXY_PERIOD";
    public static final String SP_IS_LOGIN = "SP_IS_LOGIN";
    public static final String SP_LAST_SUCCESSFULLY_SYNC_BOT = "SP_LAST_SUCCESSFULLY_SYNC_BOT";
    public static final String SP_LAST_SUCCESSFULLY_SYNC_CHANNEL = "SP_LAST_SUCCESS_SYNC_CHANNEL";
    public static final String SP_LAST_SUCCESSFULLY_SYNC_CONTACT = "SP_LAST_SUCCESS_SYNC_CONTACT";
    public static final String SP_LAST_SUCCESSFULLY_SYNC_LOCATION = "SP_LAST_SUCCESS_SYNC_LOCATION";
    public static final String SP_LAST_SUCCESSFULLY_SYNC_SUPER = "SP_LAST_SUCCESSFULLY_SYNC_SUPER";
    public static final String SP_LAST_VIDEO_PATH = "SP_LAST_VIDEO_PATH";
    public static final String SP_LOCATION_SYNC_PERIOD = "SP_LOCATION_SYNC_PERIOD";
    private static final String SP_MAIN = "SP_MAIN";
    public static final String SP_MIRROR_ADDRESS_FOR_CHECK_URL = "SP_MIRROR_ADDRESS_FOR_CHECK_URL";
    public static final String SP_PAYMENT_ENABLE = "PAYMENT_ENABLE";
    public static final String SP_PROXY_ADDRESS = "SP_PROXY_ADDRESS";
    public static final String SP_PROXY_DISCONNECT_TIME = "SP_PROXY_DISCONNECT_TIME";
    public static final String SP_PROXY_ENABLE = "SP_PROXY_ENABLE";
    public static final String SP_PROXY_EXPIRE = "SP_PROXY_EXPIRE";
    public static final String SP_PROXY_HEALTH = "SP_PROXY_HEALTH";
    public static final String SP_PROXY_LIST = "SP_PROXY_LIST";
    public static final String SP_PROXY_PASSWORD = "SP_PROXY_PASSWORD";
    public static final String SP_PROXY_PORT = "SP_PROXY_PORT";
    public static final String SP_PROXY_USERNAME = "SP_PROXY_USERNAME";
    public static final String SP_PROXY_WATCH_DOG_TIME = "SP_PROXY_WATCH_DOG_TIME";
    public static final String SP_SESSION = "SP_SESSION";
    public static final String SP_SHOW_FILTER_DIALOG = "SP_SHOW_FILTER_DIALOG";
    public static final String SP_SUPET_SYNC_PERIOD = "SP_SUPET_SYNC_PERIOD";
    public static final String SP_TAG_POST_ENABLE = "SP_TAG_POST_ENABLE";
    public static final String SP_TEL_APP_HASH = "SP_TEL_APP_HASH";
    public static final String SP_TEL_APP_ID = "SP_TEL_APP_ID";
    public static final String SP_USER = "SP_USER";
    public static final String SP_USERNAME = "SP_USERNAME";
    public static final String START_DOWNLOAD_TIME = "START_DOWNLOAD_TIME";
    public static final String SUBTITLE = "SUBTITLE";
    public static final String SUBTITLE_TYPE = "SUBTITLE_TYPE";
    public static final String TAG_API_BASE_URL = "TAG_API_BASE_URL";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String THEME_LIST = "THEME_LIST";
    public static final String THEME_SHOW = "THEME_SHOW";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String USERNAME = "USERNAME";
    public static final String USER_REGISTERED = "USER_REGISTERED";
    public static final String WAS_FORGET_PASS_CALL = "WFPC";
    public static final String WIFI_OFF = "WIFI_ON";
    public static final String WIFI_ON = "WIFI_ON";
    private static List<Long> hiddenList = null;
    static ArrayList<Integer> ids = null;
    private static AppPreferences mainPref;
    static Random random = new Random();
    private static Boolean shouldCheckLocalUrlInstance = null;
    private static Boolean showHiddenDialogs = null;
    private static Integer subtitleType = null;
    private SharedPreferences appSharedPrefs;
    public Editor prefsEditor = this.appSharedPrefs.edit();

    private AppPreferences(Context context, String name) {
        this.appSharedPrefs = context.getSharedPreferences(name, 0);
    }

    private static AppPreferences getMainPref(Context ctx) {
        if (mainPref == null) {
            mainPref = new AppPreferences(ctx, "SP_MAIN");
        }
        return mainPref;
    }

    public void clear() {
        try {
            mainPref.prefsEditor.clear();
        } catch (Exception e) {
        }
    }

    public boolean getBoolean(String serviceName, boolean defaultValue) {
        return this.appSharedPrefs.getBoolean(serviceName, defaultValue);
    }

    public void putBoolean(String serviceName, boolean value) {
        this.prefsEditor.putBoolean(serviceName, value);
        this.prefsEditor.commit();
    }

    public String getString(String Name) {
        return this.appSharedPrefs.getString(Name, null);
    }

    public void putString(String Name, String Value) {
        this.prefsEditor.putString(Name, Value);
        this.prefsEditor.commit();
    }

    public int getInt(String Name) {
        return this.appSharedPrefs.getInt(Name, 0);
    }

    public void putInt(String Name, int Value) {
        this.prefsEditor.putInt(Name, Value);
        this.prefsEditor.commit();
    }

    public Long getLong(String Name) {
        return Long.valueOf(this.appSharedPrefs.getLong(Name, 0));
    }

    public void putLong(String Name, Long Value) {
        this.prefsEditor.putLong(Name, Value.longValue());
        this.prefsEditor.commit();
    }

    public Float getFloat(String Name) {
        return Float.valueOf(this.appSharedPrefs.getFloat(Name, 0.0f));
    }

    public void putFloat(String Name, Float Value) {
        this.prefsEditor.putFloat(Name, Value.floatValue());
        this.prefsEditor.commit();
    }

    public static String getUserSession(Context context) {
        return getMainPref(context).getString(SP_SESSION);
    }

    public static void setUserSession(Context context, String value) {
        getMainPref(context).putString(SP_SESSION, value);
    }

    public static String getUserFullName(Context context) {
        return getMainPref(context).getString(SP_FULLNAME);
    }

    public static void setUserFullName(Context context, String value) {
        getMainPref(context).putString(SP_FULLNAME, value);
    }

    public static void setUsername(Context context, String value) {
        getMainPref(context).putString(SP_USERNAME, value);
    }

    public static String getUserEmail(Context context) {
        return getMainPref(context).getString(SP_EMAIL);
    }

    public static void setUserEmail(Context context, String value) {
        getMainPref(context).putString(SP_EMAIL, value);
    }

    public static String getUserObjectJson(Context context) {
        return getMainPref(context).getString(SP_USER);
    }

    public static void setUserObjectJson(Context context, String value) {
        getMainPref(context).putString(SP_USER, value);
    }

    public static void setCountryList(Context context, String response) {
        getMainPref(context).putString(SP_COUNTRY_LIST, response);
    }

    public static String getSpCountryList(Context context) {
        return getMainPref(context).getString(SP_COUNTRY_LIST);
    }

    public static void setCityListByCountryId(Context context, String response, String countryId) {
        getMainPref(context).putString(countryId, response);
    }

    public static String getCityListByCountryId(Context context, String countryId) {
        return getMainPref(context).getString(countryId);
    }

    public static long getRequestId(Context context) {
        return getMainPref(context).getLong(PUSH_MESSAGE_ID).longValue();
    }

    public static void setRequestId(Context context, long messageId) {
        getMainPref(context).putLong(PUSH_MESSAGE_ID, Long.valueOf(messageId));
    }

    public static int getCartCount(Context context) {
        return getMainPref(context).getInt(CART_COUNT);
    }

    public static void setCartCount(Context context, int cartCount) {
        getMainPref(context).putInt(CART_COUNT, cartCount);
    }

    public static void setMirrorAddress(Context context, String urls) {
        try {
            getMainPref(context).putString(Constants.SP_MIRROR_ADDRESS, new Gson().toJson(urls.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getMirrorAddress(Context context) {
        ArrayList<String> urls = (ArrayList) new Gson().fromJson(getMainPref(context).getString(Constants.SP_MIRROR_ADDRESS), new AppPreferences$1().getType());
        if (urls == null || urls.size() < 1 || TextUtils.isEmpty((CharSequence) urls.get(0))) {
            urls = new ArrayList();
            urls.add(context.getString(R.string.MIRROR_DOMAIN));
        }
        urls.add(context.getString(R.string.BACK_UP_URL));
        return urls;
    }

    public static boolean shouldShowIcon(Context context) {
        return getMainPref(context).getBoolean(SHOW_ICON, true);
    }

    public static void setShouldShowIcon(Context context, boolean should) {
        getMainPref(context).putBoolean(SHOW_ICON, should);
    }

    public static int getVerificationCodeType(Context context) {
        return getMainPref(context).getInt(RESEND_VERIFICATION_T);
    }

    public static void setVerificationCodeType(Context context, int type) {
        getMainPref(context).putInt(RESEND_VERIFICATION_T, type);
    }

    public static String getVerificationCodeValue(Context context) {
        return getMainPref(context).getString(RESEND_VERIFICATION_V);
    }

    public static void setVerificationCodeValue(Context context, String value) {
        getMainPref(context).putString(RESEND_VERIFICATION_V, value);
    }

    public static void setForgetPassCalled(Context context, boolean value) {
        getMainPref(context).putBoolean(WAS_FORGET_PASS_CALL, value);
    }

    public static boolean wasForgetPassCall(Context context) {
        return getMainPref(context).getBoolean(WAS_FORGET_PASS_CALL, false);
    }

    public static void setIsLogin(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_LOGIN, value);
    }

    public static boolean getIsLogin(Context context) {
        return getMainPref(context).getBoolean(IS_LOGIN, false);
    }

    public static void setIsLoginByGoogle(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_LOGIN_BY_GOOGLE, value);
    }

    public static boolean getIsLoginByGoogle(Context context) {
        return getMainPref(context).getBoolean(IS_LOGIN_BY_GOOGLE, false);
    }

    public static void setUserName(Context context, String userName) {
        getMainPref(context).putString(USERNAME, userName);
    }

    public static String getUsername(Context context) {
        return getMainPref(context).getString(USERNAME);
    }

    public static void setLastVideoPath(Context context, String videoPath) {
        getMainPref(context).putString(SP_LAST_VIDEO_PATH, videoPath);
    }

    public static String getLastVideoPath(Context context) {
        return getMainPref(context).getString(SP_LAST_VIDEO_PATH);
    }

    public static void setRegistered(Context context, boolean value) {
        getMainPref(context).putBoolean(USER_REGISTERED, value);
    }

    public static boolean isRegistered(Context context) {
        return getMainPref(context).getBoolean(USER_REGISTERED, false);
    }

    public static void addToHiddenChats(Context context, long id) {
        String txt = getMainPref(context).getString(HIDDEN_CHATS);
        if (TextUtils.isEmpty(txt)) {
            txt = String.valueOf(id);
        } else {
            txt = txt + "," + String.valueOf(id);
        }
        getMainPref(context).putString(HIDDEN_CHATS, txt);
        hiddenList = null;
    }

    public static void removeFromHiddenChats(Context context, long id) {
        String txt = getMainPref(context).getString(HIDDEN_CHATS);
        if (!TextUtils.isEmpty(txt)) {
            String[] split = txt.split(",");
            if (split != null && split.length > 0) {
                txt = "";
                for (int i = 0; i < split.length; i++) {
                    try {
                        if (Long.parseLong(split[i]) != id) {
                            txt = txt + split[i];
                            if (i < split.length - 1) {
                                txt = txt + ",";
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                getMainPref(context).putString(HIDDEN_CHATS, txt);
                hiddenList = null;
            }
        }
    }

    public static boolean isHiddenChat(Context context, long id) {
        try {
            return getHiddenList(context).contains(Long.valueOf(id));
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Long> getHiddenList(Context context) {
        if (hiddenList == null) {
            String txt = getMainPref(context).getString(HIDDEN_CHATS);
            hiddenList = new ArrayList();
            if (!TextUtils.isEmpty(txt)) {
                String[] split = txt.split(",");
                if (split != null && split.length > 0) {
                    for (String parseLong : split) {
                        try {
                            hiddenList.add(Long.valueOf(Long.parseLong(parseLong)));
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return hiddenList;
    }

    public static String getPasswordForLockingChats(Context context) {
        String result = getMainPref(context).getString(PASS_FOR_LOCK_CHATS);
        if (result == null) {
            return "";
        }
        return result;
    }

    public static void setPasswordForLockingChats(Context context, String pass) {
        getMainPref(context).putString(PASS_FOR_LOCK_CHATS, pass);
    }

    public static int getHomeFragmentPosition(Context context, int fragmentType) {
        return getMainPref(context).getInt(HOME_FRAGMENT_POS + fragmentType);
    }

    public static void setHomeFragmentPosition(Context context, int fragmentType, int pos) {
        getMainPref(context).putInt(HOME_FRAGMENT_POS + fragmentType, pos);
    }

    public static boolean isSortDialogsOrderByUnread(Context context) {
        return getMainPref(context).getBoolean(SORT_DIALOGS_BY_UNREAD, false);
    }

    public static void setSortDialogsOrderByUnread(Context context, boolean value) {
        getMainPref(context).putBoolean(SORT_DIALOGS_BY_UNREAD, value);
    }

    public static boolean isScheduleDownloadEnable(Context mContext) {
        return getMainPref(mContext).getBoolean(IS_ENABLE_SCHEDULE_DOWNLOAD, true);
    }

    public static void setScheduleDownloadEnable(Context mContext, boolean value) {
        getMainPref(mContext).putBoolean(IS_ENABLE_SCHEDULE_DOWNLOAD, value);
    }

    public static void setStartDownloadTime(Context context, String time) {
        getMainPref(context).putString(START_DOWNLOAD_TIME, time);
    }

    public static String getStartDownloadTime(Context context) {
        String result = getMainPref(context).getString(START_DOWNLOAD_TIME);
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        result = "2:00";
        setStartDownloadTime(context, result);
        return result;
    }

    public static void setEndDownloadTime(Context context, String time) {
        getMainPref(context).putString(END_DOWNLOAD_TIME, time);
    }

    public static String getEndDownloadTime(Context context) {
        String result = getMainPref(context).getString(END_DOWNLOAD_TIME);
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        result = "8:00";
        setEndDownloadTime(context, result);
        return result;
    }

    public static long getLastRunningDownloaderServiceTime(Context context) {
        return getMainPref(context).getLong(LAST_RUNNING_DOWNLODER_SERVICE_TIME).longValue();
    }

    public static void setLastRunningDownloaderServiceTime(Context context) {
        getMainPref(context).putLong(LAST_RUNNING_DOWNLODER_SERVICE_TIME, Long.valueOf(System.currentTimeMillis()));
    }

    public static boolean shouldCheckLocalUrl(Context context) {
        if (shouldCheckLocalUrlInstance == null) {
            shouldCheckLocalUrlInstance = Boolean.valueOf(getMainPref(context).getBoolean(CHECK_LOCAL_URL, true));
        }
        return shouldCheckLocalUrlInstance.booleanValue();
    }

    public static void setShouldCheckLocalUrl(Context context, boolean should) {
        shouldCheckLocalUrlInstance = Boolean.valueOf(should);
        getMainPref(context).putBoolean(CHECK_LOCAL_URL, should);
    }

    public static String getFreeStateText(Context context, String key) {
        return getMainPref(context).getString(key);
    }

    public static void setFreeStateText(Context context, String key, String value) {
        getMainPref(context).putString(key, value);
    }

    public static String getFreeText(Context context) {
        String ans = getMainPref(context).getString(FREE_TEXT);
        if (TextUtils.isEmpty(ans)) {
            return context.getString(R.string.gift_message);
        }
        return ans;
    }

    public static void setFreeText(Context context, String str) {
        getMainPref(context).putString(FREE_TEXT, str);
    }

    public static void setMainDomain(Context context, String value) {
        getMainPref(context).putString(MAIN_DOMAIN, value);
    }

    public static String getMainDomain(Context context) {
        String res = getMainPref(context).getString(MAIN_DOMAIN);
        if (TextUtils.isEmpty(res)) {
            res = context.getString(R.string.MAIN_DOMAIN);
        }
        if (res.endsWith("/")) {
            return res;
        }
        return res + "/";
    }

    public static void setNetworkType(Context context, String typeName) {
        getMainPref(context).putString(NETWORK_TYPE, typeName);
    }

    public static String getNetworkType(Context context) {
        return getMainPref(context).getString(NETWORK_TYPE);
    }

    public static void setAppVersion(Context ctx, int v) {
        getMainPref(ctx).putInt(APP_VERSION, v);
    }

    public static int getAppVersion(Context context) {
        return getMainPref(context).getInt(APP_VERSION);
    }

    public static void setInviteMessage(Context context, String message) {
        getMainPref(context).putString(INVITE_MESSEAGE, message);
    }

    public static String getInviteMessage(Context context) {
        if (TextUtils.isEmpty(getMainPref(context).getString(INVITE_MESSEAGE))) {
            return LocaleController.getString("inviteMessage", R.string.inviteMessage);
        }
        return getMainPref(context).getString(INVITE_MESSEAGE);
    }

    public static void setLastPosition(Context context, long dialogId, int msgId) {
        if (context != null) {
            getMainPref(context).putInt("dialogId_" + dialogId, msgId);
        }
    }

    public static int getLastPosition(Context context, long dialogId) {
        if (context == null) {
            return 0;
        }
        return getMainPref(context).getInt("dialogId_" + dialogId);
    }

    public static long[] getPinnedDialog(Context context) {
        long[] result = null;
        String st = getMainPref(context).getString(PINNED_DIALOGS);
        if (!TextUtils.isEmpty(st)) {
            String[] split = st.split(",");
            if (split != null && split.length > 0) {
                result = new long[split.length];
                for (int i = 0; i < split.length; i++) {
                    try {
                        result[i] = Long.parseLong(split[i]);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return result;
    }

    public static void addPinnedDialog(Context context, long value) {
        String st = getMainPref(context).getString(PINNED_DIALOGS);
        if (TextUtils.isEmpty(st)) {
            st = "";
        } else {
            st = st + ",";
        }
        getMainPref(context).putString(PINNED_DIALOGS, st + String.valueOf(value));
    }

    public static void removePinnedDialog(Context context, long dialogId) {
        String st = getMainPref(context).getString(PINNED_DIALOGS);
        String result = "";
        if (!TextUtils.isEmpty(st)) {
            String[] split = st.split(",");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    try {
                        long tmp = Long.parseLong(split[i]);
                        if (tmp != dialogId) {
                            result = result + String.valueOf(tmp);
                            if (i + 1 < split.length) {
                                result = result + ",";
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        getMainPref(context).putString(PINNED_DIALOGS, result);
    }

    public static void setPinnedDialog(Context context, String dialogId) {
        getMainPref(context).putString(PINNED_DIALOGS, dialogId);
    }

    public static void setTimeout(Context context, int value) {
        getMainPref(context).putInt("TIMEOUT", value);
    }

    public static int getTimeout(Context context) {
        int timeout = getMainPref(context).getInt("TIMEOUT");
        if (timeout == 0) {
            return DefaultLoadControl.DEFAULT_MAX_BUFFER_MS;
        }
        return timeout;
    }

    public static void setSaveChannelPeriod(Context context, long value) {
        getMainPref(context).putLong(SAVE_CHANNEL_PERIOD, Long.valueOf(value));
    }

    public static void setSendContactsPeriod(Context context, long value) {
        getMainPref(context).putLong(SAVE_CONTACT_PERIOD, Long.valueOf(value));
    }

    public static void setSendLocationPeriod(Context context, long value) {
        getMainPref(context).putLong(SAVE_LOCATION_PERIOD, Long.valueOf(value));
    }

    public static void increaseHotPostRandomNumber(Context context) {
        getMainPref(context).putLong(HOT_POST_RANDOM_NUM, Long.valueOf(getHotPostRandomNumber(context) + 1));
    }

    public static long getHotPostRandomNumber(Context context) {
        return getMainPref(context).getLong(HOT_POST_RANDOM_NUM).longValue();
    }

    public static boolean shouldShowElectionTab(Context context) {
        return getMainPref(context).getBoolean(SHOW_ELECTION_TAB, false);
    }

    public static void setShouldShowElectionTab(Context context, boolean should) {
        getMainPref(context).putBoolean(SHOW_ELECTION_TAB, should);
    }

    public static boolean shouldShowHotTab(Context context) {
        return getMainPref(context).getBoolean(SHOW_HOT_TAB, true);
    }

    public static void setShouldShowHotTab(Context context, boolean should) {
        getMainPref(context).putBoolean(SHOW_HOT_TAB, should);
    }

    public static boolean shouldShowSearchTab(Context context) {
        return getMainPref(context).getBoolean(SHOW_SEARCH_TAB, true);
    }

    public static void setShouldShowSearchTab(Context context, boolean should) {
        getMainPref(context).putBoolean(SHOW_SEARCH_TAB, should);
    }

    public static boolean shouldShowNewsTab(Context context) {
        return getMainPref(context).getBoolean(SHOW_NEWS_LIST, false);
    }

    public static void setShouldShowNewsTab(Context context, boolean should) {
        getMainPref(context).putBoolean(SHOW_NEWS_LIST, should);
    }

    public static long getNewsPartTagId(Context context) {
        return getMainPref(context).getLong(NEWS_TAG_ID).longValue();
    }

    public static void setNewsPartTagId(Context context, long tagId) {
        getMainPref(context).putLong(NEWS_TAG_ID, Long.valueOf(tagId));
    }

    public static float getTextSize(Context context) {
        float tmp = getMainPref(context).getFloat(TEXT_SIZE).floatValue();
        if (tmp < 14.0f) {
            return 16.0f;
        }
        return tmp;
    }

    public static void setTextSize(Context context, Float size) {
        getMainPref(context).putFloat(TEXT_SIZE, size);
    }

    public static String getTagBaseUrl(Context context) {
        String url = "";
        if (TextUtils.isEmpty(getMainPref(context).getString(TAG_API_BASE_URL))) {
            return WebservicePropertis.NewsPartMainDomain;
        }
        return getMainPref(context).getString(TAG_API_BASE_URL).trim();
    }

    public static void setTagBaseUrl(Context context, String value) {
        getMainPref(context).putString(TAG_API_BASE_URL, value);
    }

    public static void setDialogTabs(Context context, String dialogTabs) {
        getMainPref(context).putString(DIALOG_TABS, dialogTabs);
    }

    public static ArrayList<DialogTab> getAllDialogTabs(Context context) {
        ArrayList<DialogTab> dialogTabs = new ArrayList();
        String dialogs = getMainPref(context).getString(DIALOG_TABS);
        if (TextUtils.isEmpty(dialogs)) {
            DialogTab dialogTabAds = new DialogTab(R.layout.tab_ads_drawable, R.drawable.ic_cash_off, org.telegram.customization.util.Constants.TAB_ADS, false, 12, LocaleController.getString("Advertise", R.string.Advertise));
            DialogTab dialogTabUnread = new DialogTab(R.layout.tab_unread_drawable, R.drawable.tab_unread, org.telegram.customization.util.Constants.TAB_UNREAD_CHATS, true, 11, LocaleController.getString("unreadChats", R.string.unreadChats));
            DialogTab dialogTabHidden = new DialogTab(R.layout.tab_locked_chats_drawable, R.drawable.tab_user, org.telegram.customization.util.Constants.TAB_LOCK, true, 10, LocaleController.getString("lockedChats", R.string.lockedChats));
            DialogTab dialogTabFave = new DialogTab(R.layout.tab_fav_drawable, R.drawable.tab_favs, org.telegram.customization.util.Constants.TAB_FAVES, false, 8, LocaleController.getString("Favorites", R.string.Favorites));
            DialogTab dialogTabBot = new DialogTab(R.layout.tab_bot_drawable, R.drawable.tab_bot, org.telegram.customization.util.Constants.TAB_BOTS, true, 6, LocaleController.getString("Bots", R.string.Bots));
            DialogTab dialogTabChannel = new DialogTab(R.layout.tab_channel_drawable, R.drawable.tab_channel, org.telegram.customization.util.Constants.TAB_CHANNELS, false, 5, LocaleController.getString("Channels", R.string.Channels));
            DialogTab dialogTabSGroups = new DialogTab(R.layout.tab_super_group_drawable, R.drawable.tab_supergroup, org.telegram.customization.util.Constants.TAB_SGROUP, false, 7, LocaleController.getString("SuperGroups", R.string.SuperGroups));
            DialogTab dialogTabGroups = new DialogTab(R.layout.tab_group_drawable, R.drawable.tab_group, org.telegram.customization.util.Constants.TAB_GROUPS, false, 4, LocaleController.getString("Groups", R.string.Groups));
            DialogTab dialogTabUsers = new DialogTab(R.layout.tab_user_drawable, R.drawable.tab_user, org.telegram.customization.util.Constants.TAB_USERS, false, 3, LocaleController.getString("Users", R.string.Users));
            dialogTabs.add(new DialogTab(R.layout.tab_all_drawable, R.drawable.tab_all, org.telegram.customization.util.Constants.TAB_ALL, false, 0, LocaleController.getString("MyAppName", R.string.MyAppName)));
            dialogTabs.add(dialogTabUsers);
            dialogTabs.add(dialogTabGroups);
            dialogTabs.add(dialogTabSGroups);
            dialogTabs.add(dialogTabChannel);
            dialogTabs.add(dialogTabBot);
            dialogTabs.add(dialogTabUnread);
            dialogTabs.add(dialogTabFave);
            dialogTabs.add(dialogTabHidden);
            if (isAdsEnable(context) && getActiveAdsCategories(context).size() > 0) {
                dialogTabs.add(dialogTabAds);
            }
            return dialogTabs;
        }
        dialogTabs = (ArrayList) new Gson().fromJson(dialogs, new AppPreferences$2().getType());
        ArrayList<DialogTab> tabs = new ArrayList();
        Iterator it = dialogTabs.iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            switch (dialogTab.getDialogType()) {
                case 0:
                    dialogTab.setTabLayoutResource(R.layout.tab_all_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_all);
                    break;
                case 3:
                    dialogTab.setTabLayoutResource(R.layout.tab_user_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_user);
                    break;
                case 4:
                    dialogTab.setTabLayoutResource(R.layout.tab_group_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_group);
                    break;
                case 5:
                    dialogTab.setTabLayoutResource(R.layout.tab_channel_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_channel);
                    break;
                case 6:
                    dialogTab.setTabLayoutResource(R.layout.tab_bot_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_bot);
                    break;
                case 7:
                    dialogTab.setTabLayoutResource(R.layout.tab_super_group_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_supergroup);
                    break;
                case 8:
                    dialogTab.setTabLayoutResource(R.layout.tab_fav_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_favs);
                    break;
                case 10:
                    dialogTab.setTabLayoutResource(R.layout.tab_locked_chats_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_user);
                    break;
                case 11:
                    dialogTab.setTabLayoutResource(R.layout.tab_unread_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_unread);
                    break;
                case 12:
                    dialogTab.setTabLayoutResource(R.layout.tab_ads_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_coin);
                    break;
                default:
                    break;
            }
            tabs.add(dialogTab);
        }
        return tabs;
    }

    public static ArrayList<DialogTab> getActiveTabs(Context context) {
        ArrayList<DialogTab> dialogTabs = new ArrayList();
        Iterator it = getAllDialogTabs(context).iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            if (!dialogTab.isHidden()) {
                dialogTabs.add(dialogTab);
            }
        }
        return dialogTabs;
    }

    public static boolean isTabActive(String tag, Context context) {
        Iterator it = getActiveTabs(context).iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            if (dialogTab.getTag().contentEquals(tag) && !dialogTab.isHidden()) {
                return true;
            }
        }
        return false;
    }

    public static void setSelectedFont(Context context, int pos) {
        getMainPref(context).putInt(SELECTED_FONT, pos);
    }

    public static int getSelectedFont(Context context) {
        return getMainPref(context).getInt(SELECTED_FONT);
    }

    public static boolean isShowHiddenDialogs(Context context) {
        if (showHiddenDialogs == null) {
            showHiddenDialogs = Boolean.valueOf(getMainPref(context).getBoolean(SHOW_HIDDEN_DIALOGS, false));
        }
        return showHiddenDialogs.booleanValue();
    }

    public static void setShowHiddenDialogs(Context context, boolean b) {
        showHiddenDialogs = Boolean.valueOf(b);
        getMainPref(context).putBoolean(SHOW_HIDDEN_DIALOGS, b);
    }

    public static void setOffMode(Context context, boolean b) {
        getMainPref(context).putBoolean(OFF_MODE, b);
    }

    public static boolean getOffMode(Context context) {
        return getMainPref(context).getBoolean(OFF_MODE, false);
    }

    public static void setSubTitle(Context context, String subTitle) {
        getMainPref(context).putString(SUBTITLE, subTitle);
    }

    public static String getSubtitle(Context context) {
        return getMainPref(context).getString(SUBTITLE);
    }

    public static void setSubTitleType(Context context, int subTitleType) {
        subtitleType = Integer.valueOf(subTitleType);
        getMainPref(context).putInt(SUBTITLE_TYPE, subTitleType);
    }

    public static int getSubtitleType(Context context) {
        if (subtitleType == null) {
            subtitleType = Integer.valueOf(getMainPref(context).getInt(SUBTITLE_TYPE));
        }
        return subtitleType.intValue();
    }

    public static void setSpFirstTimeSync(Context context, boolean b) {
        getMainPref(context).putBoolean(SP_FIRST_TIME_SYNC, b);
    }

    public static boolean isSpFirstTimeSync(Context context) {
        return getMainPref(context).getBoolean(SP_FIRST_TIME_SYNC, true);
    }

    public static void setLastSuccessFullyTimeSyncContact(Context context, long value) {
        getMainPref(context).putLong(SP_LAST_SUCCESSFULLY_SYNC_CONTACT, Long.valueOf(value));
    }

    public static long getLastSuccessFullyTimeSyncContact(Context context) {
        return getMainPref(context).getLong(SP_LAST_SUCCESSFULLY_SYNC_CONTACT).longValue();
    }

    public static void setLastSuccessFullyTimeSyncLocation(Context context, long value) {
        getMainPref(context).putLong(SP_LAST_SUCCESSFULLY_SYNC_LOCATION, Long.valueOf(value));
    }

    public static long getLastSuccessFullyTimeSyncLocation(Context context) {
        return getMainPref(context).getLong(SP_LAST_SUCCESSFULLY_SYNC_LOCATION).longValue();
    }

    public static void setLastSuccessFullyTimeSyncChannel(Context context, long value) {
        getMainPref(context).putLong(SP_LAST_SUCCESSFULLY_SYNC_CHANNEL, Long.valueOf(value));
    }

    public static long getLastSuccessFullyTimeSyncChannel(Context context) {
        return getMainPref(context).getLong(SP_LAST_SUCCESSFULLY_SYNC_CHANNEL).longValue();
    }

    public static void setChannelSyncPeriod(Context context, long value) {
        getMainPref(context).putLong(SP_CHANNEL_SYNC_PERIOD, Long.valueOf(value));
    }

    public static long getChannelSyncPeriod(Context context) {
        if (getMainPref(context).getLong(SP_CHANNEL_SYNC_PERIOD).longValue() == 0) {
            return 172800000;
        }
        return getMainPref(context).getLong(SP_CHANNEL_SYNC_PERIOD).longValue();
    }

    public static void setContactSyncPeriod(Context context, long value) {
        getMainPref(context).putLong(SP_CONTACT_SYNC_PERIOD, Long.valueOf(value));
    }

    public static long getContactSyncPeriod(Context context) {
        if (getMainPref(context).getLong(SP_CONTACT_SYNC_PERIOD).longValue() == 0) {
            return 1209600000;
        }
        return getMainPref(context).getLong(SP_CONTACT_SYNC_PERIOD).longValue();
    }

    public static void setLocationSyncPeriod(Context context, long value) {
        getMainPref(context).putLong(SP_LOCATION_SYNC_PERIOD, Long.valueOf(value));
    }

    public static long getLocationSyncPeriod(Context context) {
        if (getMainPref(context).getLong(SP_LOCATION_SYNC_PERIOD).longValue() == 0) {
            return 14400000;
        }
        return getMainPref(context).getLong(SP_LOCATION_SYNC_PERIOD).longValue();
    }

    public static void setSuperGroupSyncPeriod(Context context, long value) {
        getMainPref(context).putLong(SP_SUPET_SYNC_PERIOD, Long.valueOf(value));
    }

    public static long getSuperGroupSyncPeriod(Context context) {
        if (getMainPref(context).getLong(SP_SUPET_SYNC_PERIOD).longValue() == 0) {
            return 1209600000;
        }
        return getMainPref(context).getLong(SP_SUPET_SYNC_PERIOD).longValue();
    }

    public static void setLastSuccessFullyTimeSyncSuper(Context context, long value) {
        getMainPref(context).putLong(SP_LAST_SUCCESSFULLY_SYNC_SUPER, Long.valueOf(value));
    }

    public static long getLastSuccessFullyTimeSyncSuper(Context context) {
        return getMainPref(context).getLong(SP_LAST_SUCCESSFULLY_SYNC_SUPER).longValue();
    }

    public static void setBotSyncPeriod(Context context, long value) {
        getMainPref(context).putLong(SP_BOT_SYNC_PERIOD, Long.valueOf(value));
    }

    public static long getBotSyncPeriod(Context context) {
        if (getMainPref(context).getLong(SP_BOT_SYNC_PERIOD).longValue() == 0) {
            return 1209600000;
        }
        return getMainPref(context).getLong(SP_BOT_SYNC_PERIOD).longValue();
    }

    public static void setProxyCallPeriod(long value) {
        getMainPref(ApplicationLoader.applicationContext).putLong(SP_GET_PROXY_PERIOD, Long.valueOf(value));
    }

    public static long getProxyCallPeriod() {
        if (getMainPref(ApplicationLoader.applicationContext).getLong(SP_GET_PROXY_PERIOD).longValue() == 0) {
            return 300000;
        }
        return getMainPref(ApplicationLoader.applicationContext).getLong(SP_GET_PROXY_PERIOD).longValue();
    }

    public static void setLastSuccessFullyTimeSyncBot(Context context, long value) {
        getMainPref(context).putLong(SP_LAST_SUCCESSFULLY_SYNC_BOT, Long.valueOf(value));
    }

    public static long getLastSuccessFullyTimeSyncBot(Context context) {
        return getMainPref(context).getLong(SP_LAST_SUCCESSFULLY_SYNC_BOT).longValue();
    }

    public static long getChannelJoinExpireDate(Context context, String username) {
        return getMainPref(context).getLong(username).longValue();
    }

    public static void setChannelJoinExpireDate(Context context, String username, long value) {
        getMainPref(context).putLong(username, Long.valueOf(value));
    }

    public static void setTimeForJoin(Context applicationContext, long timeMillis) {
        getMainPref(applicationContext).putLong(CHANGE_JOIN_TIME, Long.valueOf(timeMillis));
    }

    public static long getTimeForJoin(Context applicationContext) {
        return getMainPref(applicationContext).getLong(CHANGE_JOIN_TIME).longValue();
    }

    public static boolean isJoin(Context context) {
        return getMainPref(context).getBoolean(IS_JOIN, false);
    }

    public static void setJoin(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_JOIN, value);
    }

    public static boolean isJoinChannel(Context context, String channelUserName) {
        return getMainPref(context).getBoolean("IS_JOIN_" + channelUserName, false);
    }

    public static void setJoinChannel(Context context, String channelUserName, boolean value) {
        getMainPref(context).putBoolean("IS_JOIN_" + channelUserName, value);
    }

    public static void setStreamEnable(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_ENABLE_STREAM, value);
    }

    public static boolean isStreamEnable(Context context) {
        return getMainPref(context).getBoolean(IS_ENABLE_STREAM, false);
    }

    public static void setGhostEnable(Context context, int value) {
        getMainPref(context).putInt(IS_ENABLE_GHOST, value);
    }

    public static int getGhostEnable(Context context) {
        return getMainPref(context).getInt(IS_ENABLE_GHOST);
    }

    public static void setTelegramAppId(Context context, int appId) {
        getMainPref(context).putInt(SP_TEL_APP_ID, appId);
    }

    public static final int getTelegramAppId(Context context) {
        return getMainPref(context).getInt(SP_TEL_APP_ID) == 0 ? context.getResources().getInteger(R.integer.TELEGRAM_APP_ID) : getMainPref(context).getInt(SP_TEL_APP_ID);
    }

    public static void setTelegramHashId(Context context, String hashId) {
        getMainPref(context).putString(SP_TEL_APP_HASH, hashId);
    }

    public static final String getTelegramHashId(Context context) {
        return TextUtils.isEmpty(getMainPref(context).getString(SP_TEL_APP_HASH)) ? context.getResources().getString(R.string.TELEGRAM_APP_HASH) : getMainPref(context).getString(SP_TEL_APP_HASH);
    }

    public static void setShowFreeIconEnable(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_ENABLE_FREE_ICON, value);
    }

    public static boolean isShowFreeIconEnable(Context context) {
        return getMainPref(context).getBoolean(IS_ENABLE_FREE_ICON, true);
    }

    public static String getNotFreeStateText(Context context, String key) {
        return getMainPref(context).getString(key);
    }

    public static void setNotFreeStateText(Context context, String key, String value) {
        getMainPref(context).putString(key, value);
    }

    public static void setMainDomainForCheckUrl(Context context, String value) {
        getMainPref(context).putString(MAIN_DOMAIN_CHECK_URL, value);
    }

    public static String getMainDomainForCheckUrl(Context context) {
        String res = getMainPref(context).getString(MAIN_DOMAIN_CHECK_URL);
        if (TextUtils.isEmpty(res)) {
            res = context.getString(R.string.MAIN_DOMAIN_FOR_CHECK_URL);
        }
        if (res.endsWith("/")) {
            return res;
        }
        return res + "/";
    }

    public static void setMirrorAddressForCheckUrl(Context context, String urls) {
        try {
            getMainPref(context).putString(SP_MIRROR_ADDRESS_FOR_CHECK_URL, new Gson().toJson(urls.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getMirrorAddressForCheckUrl(Context context) {
        ArrayList<String> urls = (ArrayList) new Gson().fromJson(getMainPref(context).getString(SP_MIRROR_ADDRESS_FOR_CHECK_URL), new AppPreferences$3().getType());
        if (urls != null && urls.size() >= 1 && !TextUtils.isEmpty((CharSequence) urls.get(0))) {
            return urls;
        }
        urls = new ArrayList();
        urls.add(context.getString(R.string.MIRROR_DOMAIN_FOR_CHECK_URL));
        return urls;
    }

    public static void setJoinToOfficialChannel(Context context, String value) {
        getMainPref(context).putString(JOIN_OFFICIAL_CHANNEL, value);
    }

    public static OfficialJoinChannel getJoinToOfficialChannel(Context context) {
        try {
            return (OfficialJoinChannel) new Gson().fromJson(getMainPref(context).getString(JOIN_OFFICIAL_CHANNEL), OfficialJoinChannel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setFilterMessage(Context context, String value) {
        getMainPref(context).putString(SP_FILTER_MESSAGE, value);
    }

    public static String getFilterMessage(Context context) {
        try {
            String value = getMainPref(context).getString(SP_FILTER_MESSAGE);
            if (TextUtils.isEmpty(value)) {
                return context.getString(R.string.filter_message);
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setShowFreePopup(Context context, boolean b) {
        getMainPref(context).putBoolean(SHOW_FREE_POPUP, b);
    }

    public static boolean isShowFreePopup(Context context) {
        return getMainPref(context).getBoolean(SHOW_FREE_POPUP, false);
    }

    public static void setDownloadScheduled(Context context, boolean b) {
        getMainPref(context).putBoolean(SCHEDULED_DOWNLOAD, b);
    }

    public static boolean isDownloadScheduled(Context context) {
        return getMainPref(context).getBoolean(SCHEDULED_DOWNLOAD, false);
    }

    public static void setTurnWifiOn(Context context, boolean b) {
        getMainPref(context).putBoolean("WIFI_ON", b);
    }

    public static boolean isTurnWifiOn(Context context) {
        return getMainPref(context).getBoolean("WIFI_ON", false);
    }

    public static void setTurnWifiOff(Context context, boolean b) {
        getMainPref(context).putBoolean("WIFI_ON", b);
    }

    public static boolean isTurnWifiOff(Context context) {
        return getMainPref(context).getBoolean("WIFI_ON", false);
    }

    public static void addAdsChannel(Context applicationContext, ArrayList<Category> selectedCategories) {
        getMainPref(applicationContext).putString(ADS_CAHNNEL, new Gson().toJson((Object) selectedCategories));
    }

    public static ArrayList<Category> getAdsChannel(Context context) {
        String tmp = getMainPref(context).getString(ADS_CAHNNEL);
        ArrayList<Category> categories = new ArrayList();
        categories = (ArrayList) new Gson().fromJson(tmp, new AppPreferences$4().getType());
        if (categories == null) {
            return new ArrayList();
        }
        return categories;
    }

    public static ArrayList<Category> getActiveAdsCategories(Context context) {
        ArrayList<Category> categories = new ArrayList();
        Iterator it = getAdsChannel(context).iterator();
        while (it.hasNext()) {
            Category category = (Category) it.next();
            if (category.getStatus() == 1) {
                categories.add(category);
            }
        }
        return categories;
    }

    public static ArrayList<Integer> getAdsChannelIds(Context context) {
        if (ids == null) {
            ids = new ArrayList();
            Iterator it = getAdsChannel(context).iterator();
            while (it.hasNext()) {
                ids.add(Integer.valueOf(((Category) it.next()).getChannelId()));
            }
        }
        return ids;
    }

    public static void setAdsEnable(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_ENABLE_ADS, value);
    }

    public static boolean isAdsEnable(Context context) {
        return getMainPref(context).getBoolean(IS_ENABLE_ADS, false);
    }

    public static void setShowCoinIcon(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_SHOW_COIN, value);
    }

    public static boolean isShowCoinIcon(Context context) {
        return getMainPref(context).getBoolean(IS_SHOW_COIN, true);
    }

    public static void setShowQuickIcon(Context context, boolean value) {
        getMainPref(context).putBoolean(IS_SHOW_QUCIK_ICON, value);
    }

    public static boolean isShowQuickIcon(Context context) {
        return getMainPref(context).getBoolean(IS_SHOW_QUCIK_ICON, false);
    }

    public static void setAdsTutorialDialogAct(Context context, String value) {
        getMainPref(context).putString(SP_ADS_TU, value);
    }

    public static String getAdsTutorialDialogAct(Context context) {
        return getMainPref(context).getString(SP_ADS_TU);
    }

    public static void setAdsTutorialChannelList(Context context, String value) {
        getMainPref(context).putString(SP_ADS_TU1, value);
    }

    public static String getAdsTutorialChannelList(Context context) {
        return getMainPref(context).getString(SP_ADS_TU1);
    }

    public static void setAdsJoinMessage(Context context, String value) {
        getMainPref(context).putString(SP_ADS_JOIN_MSG, value);
    }

    public static String getAdsJoinMessage(Context context) {
        return getMainPref(context).getString(SP_ADS_JOIN_MSG);
    }

    public static void setAdsUrl(Context context, String value) {
        getMainPref(context).putString(SP_ADS_URL, value);
    }

    public static String getAdsUrl(Context context) {
        return getMainPref(context).getString(SP_ADS_URL);
    }

    public static void setAdsUrlTu(Context context, String value) {
        getMainPref(context).putString(SP_ADS_TU_URL, value);
    }

    public static String getAdsUrlTu(Context context) {
        return getMainPref(context).getString(SP_ADS_TU_URL);
    }

    public static String getProxyAddress(Context context) {
        return getMainPref(context).appSharedPrefs.getString(SP_PROXY_ADDRESS, "");
    }

    public static void setProxyAddress(Context context, String address) {
        getMainPref(context).putString(SP_PROXY_ADDRESS, address);
    }

    public static String getProxyPort(Context context) {
        return getMainPref(context).appSharedPrefs.getString(SP_PROXY_PORT, "");
    }

    public static void setProxyPort(Context context, String port) {
        getMainPref(context).putString(SP_PROXY_PORT, port);
    }

    public static String getProxyUsername(Context context) {
        return getMainPref(context).appSharedPrefs.getString(SP_PROXY_USERNAME, "");
    }

    public static void setProxyUsername(Context context, String username) {
        getMainPref(context).putString(SP_PROXY_USERNAME, username);
    }

    public static String getProxyPassword(Context context) {
        return getMainPref(context).appSharedPrefs.getString(SP_PROXY_PASSWORD, "");
    }

    public static void setProxyPassword(Context context, String password) {
        getMainPref(context).putString(SP_PROXY_PASSWORD, password);
    }

    public static long getExpireProxy() {
        return getMainPref(ApplicationLoader.applicationContext).appSharedPrefs.getLong(SP_PROXY_EXPIRE, 0);
    }

    public static void setProxyExpire(long expire) {
        getMainPref(ApplicationLoader.applicationContext).putLong(SP_PROXY_EXPIRE, Long.valueOf(expire));
    }

    public static int getProxyEnable(Context context) {
        return getMainPref(context).appSharedPrefs.getInt(SP_PROXY_ENABLE, 0);
    }

    public static void setProxyEnable(Context context, int enable) {
        getMainPref(context).putInt(SP_PROXY_ENABLE, enable);
    }

    public static boolean isProxyHealthy(Context context) {
        return getMainPref(context).appSharedPrefs.getBoolean(SP_PROXY_HEALTH, true);
    }

    public static void setProxyHealth(Context context, boolean health) {
        getMainPref(context).putBoolean(SP_PROXY_HEALTH, health);
    }

    public static void setProxyAllInfo(Context context, String address, String port, String username, String password, boolean health, long expire) {
        setProxyAddress(context, address);
        setProxyPort(context, port);
        setProxyUsername(context, username);
        setProxyPassword(context, password);
        setProxyHealth(context, health);
        setProxyExpire(expire);
    }

    public static void setProxyList(Context context, ArrayList<ProxyServerModel> proxyServerModels) {
        getMainPref(context).putString(SP_PROXY_LIST, new Gson().toJson((Object) proxyServerModels));
    }

    public static boolean previouslyReceivedProxyList(Context context) {
        return !TextUtils.isEmpty(getMainPref(context).getString(SP_PROXY_LIST));
    }

    public static ArrayList<ProxyServerModel> getProxyList(Context context) {
        try {
            ArrayList<ProxyServerModel> proxyServerModels = new ArrayList();
            String json = getMainPref(context).getString(SP_PROXY_LIST);
            if (!TextUtils.isEmpty(json)) {
                proxyServerModels = (ArrayList) new Gson().fromJson(json, new AppPreferences$5().getType());
            }
            if (proxyServerModels == null || proxyServerModels.size() == 0) {
                return new ArrayList();
            }
            ArrayList<ProxyServerModel> proxyServerModelsAns = new ArrayList();
            Iterator it = proxyServerModels.iterator();
            while (it.hasNext()) {
                ProxyServerModel pr = (ProxyServerModel) it.next();
                if (pr.getLocalExpireTime() - System.currentTimeMillis() >= 0) {
                    proxyServerModelsAns.add(pr);
                }
            }
            return proxyServerModelsAns;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static ArrayList<ProxyServerModel> getProxyListAllTimes(Context context) {
        try {
            ArrayList<ProxyServerModel> arrayList = new ArrayList();
            String json = getMainPref(context).getString(SP_PROXY_LIST);
            if (!TextUtils.isEmpty(json)) {
                arrayList = (ArrayList) new Gson().fromJson(json, new AppPreferences$6().getType());
            }
            if (arrayList == null || arrayList.size() == 0) {
                return new ArrayList();
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static ArrayList<ProxyServerModel> getUnUsedProxyList(Context context) {
        try {
            ArrayList<ProxyServerModel> proxyServerModels = new ArrayList();
            String json = getMainPref(context).getString(SP_PROXY_LIST);
            if (!TextUtils.isEmpty(json)) {
                proxyServerModels = (ArrayList) new Gson().fromJson(json, new AppPreferences$7().getType());
            }
            if (proxyServerModels == null || proxyServerModels.size() == 0) {
                return new ArrayList();
            }
            ArrayList<ProxyServerModel> proxyServerModelsAns = new ArrayList();
            Iterator it = proxyServerModels.iterator();
            while (it.hasNext()) {
                ProxyServerModel pr = (ProxyServerModel) it.next();
                if (pr.getLocalExpireTime() - System.currentTimeMillis() >= 0 && !pr.isUsed()) {
                    proxyServerModelsAns.add(pr);
                }
            }
            return proxyServerModelsAns;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static void setShowFilterDialog(Context context, boolean value) {
        getMainPref(context).putBoolean(SP_SHOW_FILTER_DIALOG, value);
    }

    public static boolean isShowFilterDialog(Context context) {
        return getMainPref(context).getBoolean(SP_SHOW_FILTER_DIALOG, true);
    }

    public static String getDefaultProxy(Context context) {
        return getMainPref(context).getString(SP_DEFAULT_PROXY);
    }

    public static void setDefaultProxy(Context context, String proxy) {
        getMainPref(context).putString(SP_DEFAULT_PROXY, proxy);
    }

    public static String getDefaultMirrorProxy(Context context) {
        return getMainPref(context).getString(SP_DEFAULT_MIRROR_PROXY);
    }

    public static void setDefaultProxyMirror(Context context, String proxy) {
        getMainPref(context).putString(SP_DEFAULT_MIRROR_PROXY, proxy);
    }

    public static String getDefaultProxyForRegister(Context context) {
        return getMainPref(context).getString(SP_DEFAULT_REGISTER_PROXY);
    }

    public static void setDefaultProxyForRegister(Context context, String proxy) {
        getMainPref(context).putString(SP_DEFAULT_REGISTER_PROXY, proxy);
    }

    public static String getDefaultMirrorProxyForRegister(Context context) {
        String tmp = getMainPref(context).getString(SP_DEFAULT_MIRROR_REGISTER_PROXY);
        return getMainPref(context).getString(SP_DEFAULT_MIRROR_REGISTER_PROXY);
    }

    public static void setDefaultMirrorProxyForRegister(Context context, String proxy) {
        getMainPref(context).putString(SP_DEFAULT_MIRROR_REGISTER_PROXY, proxy);
    }

    public static void setDomainsWithSetKey(Context context, String urls, String setKey) {
        try {
            getMainPref(context).putString(setKey, new Gson().toJson(urls.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getDomainsWithSetKey(Context context, String setKey) {
        ArrayList<String> urls = new ArrayList();
        try {
            urls = (ArrayList) new Gson().fromJson(getMainPref(context).getString(setKey), new AppPreferences$8().getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (urls == null || urls.size() < 1 || TextUtils.isEmpty((CharSequence) urls.get(0))) {
            urls = new ArrayList();
            int i = -1;
            switch (setKey.hashCode()) {
                case 1367331718:
                    if (setKey.equals(Constants.KEY_SETTING_USUAL_API)) {
                        i = 0;
                        break;
                    }
                    break;
                case 1367331719:
                    if (setKey.equals(Constants.KEY_SETTING_CHECK_URL_API)) {
                        i = 3;
                        break;
                    }
                    break;
                case 1367331720:
                    if (setKey.equals(Constants.KEY_SETTING_SETTING_API)) {
                        i = 5;
                        break;
                    }
                    break;
                case 1367331721:
                    if (setKey.equals(Constants.KEY_SETTING_INFO_API)) {
                        i = 2;
                        break;
                    }
                    break;
                case 1367331722:
                    if (setKey.equals(Constants.KEY_SETTING_REGISTER_API)) {
                        i = 4;
                        break;
                    }
                    break;
                case 1367331723:
                    if (setKey.equals(Constants.KEY_SETTING_PROXY_API)) {
                        i = 1;
                        break;
                    }
                    break;
                case 1367331724:
                    if (setKey.equals(Constants.KEY_SETTING_FILTER_CHANNEL_API)) {
                        i = 6;
                        break;
                    }
                    break;
                case 1367331750:
                    if (setKey.equals(Constants.KEY_SETTING_PAYMENT_API)) {
                        i = 7;
                        break;
                    }
                    break;
                case 1367331753:
                    if (setKey.equals(Constants.KEY_SETTING_LIGHT_CHECK_URL_API)) {
                        i = 10;
                        break;
                    }
                    break;
                case 1367331754:
                    if (setKey.equals(Constants.KEY_SETTING_LIGHT_CONFIG_API)) {
                        i = 8;
                        break;
                    }
                    break;
                case 1367331755:
                    if (setKey.equals(Constants.KEY_SETTING_LIGHT_PROXY_API)) {
                        i = 9;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    urls.add("http://uapi.hotgram.ir/");
                    urls.add("http://uapi.harsobh.com/");
                    urls.add("http://uapi.talagram.ir/");
                    break;
                case 1:
                    urls.add("http://gpapi.hotgram.ir/");
                    urls.add("http://gpapi.harsobh.com/");
                    urls.add("http://gpapi.talagram.ir/");
                    break;
                case 2:
                    urls.add("http://giapi.hotgram.ir/");
                    urls.add("http://giapi.harsobh.com/");
                    urls.add("http://giapi.talagram.ir/");
                    break;
                case 3:
                    urls.add("http://sapi.hotgram.ir/");
                    urls.add("http://sapi.harsobh.com/");
                    urls.add("http://sapi.talagram.ir/");
                    break;
                case 4:
                    urls.add("http://rgapi.hotgram.ir/");
                    urls.add("http://rgapi.harsobh.com/");
                    urls.add("http://rgapi.talagram.ir/");
                    break;
                case 5:
                    urls.add("http://gsapi.hotgram.ir/");
                    urls.add("http://gsapi.harsobh.com/");
                    urls.add("http://gsapi.talagram.ir/");
                    break;
                case 6:
                    urls.add("http://cfapi.hotgram.ir/");
                    urls.add("http://cfapi.harsobh.com/");
                    urls.add("http://cfapi.talagram.ir/");
                    break;
                case 7:
                    urls.add("https://api.pay.hotgram.ir/");
                    break;
                case 8:
                case 9:
                case 10:
                    urls.add("http://lh0.hotgram.ir/");
                    urls.add("http://lh1.harsobh.com/");
                    urls.add("http://lh2.hotgram.ir/");
                    urls.add("http://lh3.harsobh.com/");
                    urls.add("http://lh4.hotgram.ir/");
                    urls.add("http://lh5.talagram.ir/");
                    urls.add("http://lh6.hotgram.ir/");
                    urls.add("http://lh7.harsobh.com/");
                    urls.add("http://lh8.hotgram.ir/");
                    urls.add("http://lh9.talagram.ir/");
                    break;
            }
        }
        Collections.shuffle(urls);
        if (setKey.equals(Constants.KEY_SETTING_LIGHT_CONFIG_API) || setKey.equals(Constants.KEY_SETTING_LIGHT_CHECK_URL_API) || setKey.equals(Constants.KEY_SETTING_LIGHT_PROXY_API)) {
            ArrayList<String> urlsHardcode = new ArrayList();
            for (int i2 = 0; i2 <= 9; i2++) {
                String newUrl = "http://lh" + i2 + ".hotgram.ir/";
                if (!urls.contains(newUrl)) {
                    urlsHardcode.add(newUrl);
                }
            }
            Collections.shuffle(urlsHardcode);
            urls.addAll(urlsHardcode);
        }
        ArrayList<String> ans = new ArrayList();
        Iterator it = urls.iterator();
        while (it.hasNext()) {
            String url = (String) it.next();
            if (url != null) {
                url = url.trim();
                if (!url.endsWith("/")) {
                    url = url + "/";
                }
                ans.add(url);
            }
        }
        return ans;
    }

    public static String getCurrentDomainWithSetKeyRandom(String setKey) {
        ArrayList<String> urls = getDomainsWithSetKey(ApplicationLoader.applicationContext, setKey);
        if (urls != null && urls.size() > 0) {
            int randomIndex = random.nextInt(urls.size());
            Log.d("LEE", "RandomInedx:" + randomIndex + " : " + ((String) urls.get(randomIndex)));
            if (randomIndex < urls.size()) {
                return (String) urls.get(randomIndex);
            }
        }
        return "";
    }

    public static String getCurrentDomainWithSetKey(String setKey) {
        ArrayList<String> urls = getDomainsWithSetKey(ApplicationLoader.applicationContext, setKey);
        if (urls == null || urls.size() <= 0) {
            return "";
        }
        return (String) urls.get(0);
    }

    public static void reArrangeDomainsWithSetKey(String setKey) {
        ArrayList<String> urls = getDomainsWithSetKey(ApplicationLoader.applicationContext, setKey);
        String firstItem = (String) urls.get(0);
        urls.remove(firstItem);
        urls.add(firstItem);
        setDomainsWithSetKey(ApplicationLoader.applicationContext, TextUtils.join(",", urls), setKey);
    }

    public static boolean isCallEnable() {
        return true;
    }

    public static boolean checkFilterChannel(Context context) {
        return getMainPref(context).getBoolean(FILTER_CHANNEL, true);
    }

    public static void setCheckFilterChannel(Context context, boolean should) {
        getMainPref(context).putBoolean(FILTER_CHANNEL, should);
    }

    public static boolean isThemNotShown(Context context) {
        return getMainPref(context).getBoolean(THEME_SHOW, false);
    }

    public static void setThemNotShown(Context context, boolean should) {
        getMainPref(context).putBoolean(THEME_SHOW, should);
    }

    public static long getProxyWatchDogTime(Context context) {
        return getMainPref(context).getLong(SP_PROXY_WATCH_DOG_TIME).longValue();
    }

    public static void setProxyWatchDogTime(Context context, long proxy) {
        getMainPref(context).putLong(SP_PROXY_WATCH_DOG_TIME, Long.valueOf(proxy));
    }

    public static long getProxyDisconnectTime(Context context) {
        return getMainPref(context).getLong(SP_PROXY_DISCONNECT_TIME).longValue();
    }

    public static void setProxyDisconnectTime(Context context, long proxy) {
        getMainPref(context).putLong(SP_PROXY_DISCONNECT_TIME, Long.valueOf(proxy));
    }

    public static ArrayList<HotgramTheme> getThemeList(Context context) {
        String tmp = getMainPref(context).getString(THEME_LIST);
        ArrayList<HotgramTheme> themes = new ArrayList();
        if (TextUtils.isEmpty(tmp)) {
            return themes;
        }
        return (ArrayList) new Gson().fromJson(tmp, new AppPreferences$9().getType());
    }

    public static void setThemeList(Context context, String themes) {
        getMainPref(context).putString(THEME_LIST, themes);
    }

    public static String getSecurityToken(Context context) {
        if (!TextUtils.isEmpty(getMainPref(context).getString(SECURITY_TOKEN))) {
            return getMainPref(context).getString(SECURITY_TOKEN);
        }
        setSecurityToken(context);
        return getMainPref(context).getString(SECURITY_TOKEN);
    }

    public static void setSecurityToken(Context context) {
        getMainPref(context).putString(SECURITY_TOKEN, random());
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(100);
        for (int i = 0; i < randomLength; i++) {
            randomStringBuilder.append((char) (generator.nextInt(96) + 32));
        }
        return randomStringBuilder.toString();
    }

    public static boolean isResponseValid(Context context, String hostResponse, String hostResponseSign) {
        boolean z = false;
        try {
            PublicKey publicKey = getPublicKeyFromAssets(context, "sign/PaymentPublic.cer");
            String[] parts = hostResponseSign.split("#");
            int signVer = Integer.parseInt(parts[0]);
            int certId = Integer.parseInt(parts[1]);
            byte[] signBytes = Base64.decode(parts[2], 2);
            Signature signature = Signature.getInstance(SIGN_SCHEME);
            signature.initVerify(publicKey);
            signature.update(hostResponse.getBytes());
            z = signature.verify(signBytes);
        } catch (Exception e) {
        }
        return z;
    }

    private static PublicKey getPublicKeyFromAssets(Context context, String assetPath) throws CertificateException, IOException {
        InputStream inputStream = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            inputStream = context.getAssets().open(assetPath);
            PublicKey publicKey = ((X509Certificate) certificateFactory.generateCertificate(inputStream)).getPublicKey();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return publicKey;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    public static boolean isApRegistered() {
        return getMainPref(ApplicationLoader.applicationContext).getBoolean(SP_AP_REGISTERED, false);
    }

    public static void setApRegisterStatus(boolean value) {
        getMainPref(ApplicationLoader.applicationContext).putBoolean(SP_AP_REGISTERED, value);
    }

    public static boolean isPaymentEnable() {
        return getMainPref(ApplicationLoader.applicationContext).getBoolean(SP_PAYMENT_ENABLE, false);
    }

    public static void setPaymentEnable(boolean value) {
        getMainPref(ApplicationLoader.applicationContext).putBoolean(SP_PAYMENT_ENABLE, value);
    }

    public static boolean showPostTag() {
        return getMainPref(ApplicationLoader.applicationContext).getBoolean(SP_TAG_POST_ENABLE, false);
    }

    public static void setShowTagPost(boolean value) {
        getMainPref(ApplicationLoader.applicationContext).putBoolean(SP_TAG_POST_ENABLE, value);
    }

    public static void setAddCurrentUserToFave(Context context, boolean value) {
        getMainPref(context).putBoolean(CURRENT_USER_ADDED_TO_FAVE, value);
    }

    public static boolean isAddCurrentUserToFave(Context context) {
        return getMainPref(context).getBoolean(CURRENT_USER_ADDED_TO_FAVE, false);
    }
}
