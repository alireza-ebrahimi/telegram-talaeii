package org.telegram.customization.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.messenger.ApplicationLoader;
import utils.app.AppPreferences;

public class Prefs {
    public static final String SP_FILTERS = "SP_FILTERS";
    public static final String SP_FILTERS_TIME = "SP_FILTERS_TIME";
    public static final String SP_FL = "SP_FL";
    public static final String SP_IS_3_TIME = "SP_IS_3_TIME";
    public static final String SP_IS_FIRST_TIME = "SP_IS_FIRST_TIME";
    public static final String SP_IS_FIRST_TIME_LOAD = "SP_IS_FIRST_TIME_LOAD";
    public static final String SP_IS_SECOND_TIME = "SP_IS_SECOND_TIME";
    public static final String SP_MAIN = "SP_MAIN";
    public static final String SP_MAIN_ADS_TU = "SP_MAIN_ADS_TU";
    public static final String SP_MAIN_ADS_TU1 = "SP_MAIN_ADS_TU1";
    public static final String SP_MAIN_GHOST = "SP_MAIN_GHOST";
    public static final String SP_MAIN_GHOST_TU = "SP_MAIN_GHOST_TU";
    public static final String SP_MAIN_HOT_TU = "SP_MAIN_HOT_TU";
    public static final String SP_MAIN_SEARCH_TU = "SP_MAIN_SEARCH_TU";
    public static final String SP_SEARCH_TERM = "SP_SEARCH_TERM";
    private static Prefs mainPref;
    private SharedPreferences appSharedPrefs;
    public Editor prefsEditor = this.appSharedPrefs.edit();

    private Prefs(Context context, String name) {
        this.appSharedPrefs = context.getSharedPreferences(name, 0);
    }

    public static Prefs getMainPref(Context context) {
        if (mainPref == null) {
            mainPref = new Prefs(context, SP_MAIN);
        }
        return mainPref;
    }

    public int getInt(String Name) {
        return this.appSharedPrefs.getInt(Name, 0);
    }

    public void putInt(String Name, int Value) {
        this.prefsEditor.putInt(Name, Value);
        this.prefsEditor.commit();
    }

    public long getLong(String Name) {
        return this.appSharedPrefs.getLong(Name, 0);
    }

    public void putLong(String Name, long Value) {
        this.prefsEditor.putLong(Name, Value);
        this.prefsEditor.commit();
    }

    public boolean getBool(String Name) {
        return this.appSharedPrefs.getBoolean(Name, true);
    }

    public void putBool(String Name, boolean Value) {
        this.prefsEditor.putBoolean(Name, Value);
        this.prefsEditor.commit();
    }

    public String getString(String name) {
        return this.appSharedPrefs.getString(name, null);
    }

    public void putString(String Name, String Value) {
        this.prefsEditor.putString(Name, Value);
        this.prefsEditor.commit();
    }

    public static void switchGhostMode(Context context) {
        int ghost;
        if (getGhostMode(context) == 0) {
            ghost = 1;
        } else {
            ghost = 0;
        }
        getMainPref(context).putInt(SP_MAIN_GHOST, ghost);
    }

    public static void setGhostMode(Context context, int on) {
        getMainPref(context).putInt(SP_MAIN_GHOST, on);
    }

    public static int getGhostMode(Context context) {
        if (AppPreferences.getGhostEnable(context) == 0) {
            return 0;
        }
        int ans = 0;
        try {
            return getMainPref(context).getInt(SP_MAIN_GHOST);
        } catch (Exception e) {
            return ans;
        }
    }

    public static void setSpIsFirstTime(Context context, boolean on) {
        getMainPref(context).putBool(SP_IS_FIRST_TIME, on);
    }

    public static boolean getIsFirstTime(Context context) {
        return getMainPref(context).getBool(SP_IS_FIRST_TIME);
    }

    public static void setSpIsSecondTime(Context context, boolean on) {
        getMainPref(context).putBool(SP_IS_SECOND_TIME, on);
    }

    public static boolean getIsSecondTime(Context context) {
        return getMainPref(context).getBool(SP_IS_SECOND_TIME);
    }

    public static void setFiltersTime(Context context, long time) {
        getMainPref(context).putLong(SP_FILTERS_TIME, time);
    }

    public static long getFiltersTime(Context context) {
        return getMainPref(context).getLong(SP_FILTERS_TIME);
    }

    public static void setFilters(Context context, String filters) {
        getMainPref(context).putString(SP_FILTERS, filters);
    }

    public static ArrayList<SlsFilter> getFilters(Context context) {
        try {
            ArrayList<SlsFilter> arrayList = (ArrayList) new Gson().fromJson(getMainPref(context).getString(SP_FILTERS), new Prefs$1().getType());
            if (arrayList == null || arrayList.size() <= 0) {
                return new ArrayList();
            }
            int i = 0;
            while (i < arrayList.size()) {
                if (((SlsFilter) arrayList.get(i)).getType() != 1) {
                    arrayList.remove(i);
                    i--;
                } else {
                    ((SlsFilter) arrayList.get(i)).setClickable(true);
                }
                i++;
            }
            return arrayList;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static ArrayList<SlsFilter> getSorts(Context context) {
        ArrayList<SlsFilter> arrayList = (ArrayList) new Gson().fromJson(getMainPref(context).getString(SP_FILTERS), new Prefs$2().getType());
        if (arrayList == null || arrayList.size() <= 0) {
            return new ArrayList();
        }
        int i = 0;
        while (i < arrayList.size()) {
            if (((SlsFilter) arrayList.get(i)).getType() != 2) {
                arrayList.remove(i);
                i--;
            } else {
                ((SlsFilter) arrayList.get(i)).setClickable(true);
            }
            i++;
        }
        return arrayList;
    }

    public static void setSearchTerm(String term, Context context) {
        getMainPref(context).putString(SP_SEARCH_TERM, term);
    }

    public static String getSearchTerm(Context context) {
        return getMainPref(context).getString(SP_SEARCH_TERM);
    }

    public static void setGhostModeTu(Context context, boolean on) {
        getMainPref(context).putBool(SP_MAIN_GHOST_TU, on);
    }

    public static boolean getGhostModeTu(Context context) {
        return getMainPref(context).getBool(SP_MAIN_GHOST_TU);
    }

    public static void setHotTu(Context context, boolean on) {
        getMainPref(context).putBool(SP_MAIN_HOT_TU, on);
    }

    public static boolean getHotTu(Context context) {
        return getMainPref(context).getBool(SP_MAIN_HOT_TU);
    }

    public static void setSearchTu(Context context, boolean on) {
        getMainPref(context).putBool(SP_MAIN_SEARCH_TU, on);
    }

    public static boolean getSearchTu(Context context) {
        return getMainPref(context).getBool(SP_MAIN_SEARCH_TU);
    }

    public static void setSpIsFirstTimeLoad(Context context, boolean on) {
        getMainPref(context).putBool(SP_IS_FIRST_TIME_LOAD, on);
    }

    public static boolean getIsFirstTimeLoad(Context context) {
        return getMainPref(context).getBool(SP_IS_FIRST_TIME_LOAD);
    }

    public static void setSpIs3Time(Context context, boolean on) {
        getMainPref(context).putBool(SP_IS_3_TIME, on);
    }

    public static boolean getIs3Time(Context context) {
        return getMainPref(context).getBool(SP_IS_3_TIME);
    }

    public static void setAdsTu(Context context, boolean on) {
        getMainPref(context).putBool(SP_MAIN_ADS_TU, on);
    }

    public static boolean getAdsTu(Context context) {
        return getMainPref(context).getBool(SP_MAIN_ADS_TU);
    }

    public static void setAdsTu1(Context context, boolean on) {
        getMainPref(context).putBool(SP_MAIN_ADS_TU1, on);
    }

    public static boolean getAdsTu1(Context context) {
        return getMainPref(context).getBool(SP_MAIN_ADS_TU1);
    }

    public static void setFirstLaunchDone(int on) {
        getMainPref(ApplicationLoader.applicationContext).putInt(SP_FL, on);
    }

    public static Integer getFirstLaunchDone() {
        return Integer.valueOf(getMainPref(ApplicationLoader.applicationContext).getInt(SP_FL));
    }
}
