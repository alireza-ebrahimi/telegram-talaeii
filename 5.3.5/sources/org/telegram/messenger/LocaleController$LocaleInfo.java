package org.telegram.messenger;

import android.text.TextUtils;
import java.io.File;

public class LocaleController$LocaleInfo {
    public boolean builtIn;
    public String name;
    public String nameEnglish;
    public String pathToFile;
    public String shortName;
    public int version;

    public String getSaveString() {
        return this.name + "|" + this.nameEnglish + "|" + this.shortName + "|" + this.pathToFile + "|" + this.version;
    }

    public static LocaleController$LocaleInfo createWithString(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String[] args = string.split("\\|");
        if (args.length < 4) {
            return null;
        }
        LocaleController$LocaleInfo localeInfo = new LocaleController$LocaleInfo();
        localeInfo.name = args[0];
        localeInfo.nameEnglish = args[1];
        localeInfo.shortName = args[2].toLowerCase();
        localeInfo.pathToFile = args[3];
        if (args.length < 5) {
            return localeInfo;
        }
        localeInfo.version = Utilities.parseInt(args[4]).intValue();
        return localeInfo;
    }

    public File getPathToFile() {
        if (isRemote()) {
            return new File(ApplicationLoader.getFilesDirFixed(), "remote_" + this.shortName + ".xml");
        }
        return !TextUtils.isEmpty(this.pathToFile) ? new File(this.pathToFile) : null;
    }

    public String getKey() {
        if (this.pathToFile == null || "remote".equals(this.pathToFile)) {
            return this.shortName;
        }
        return "local_" + this.shortName;
    }

    public boolean isRemote() {
        return "remote".equals(this.pathToFile);
    }

    public boolean isLocal() {
        return (TextUtils.isEmpty(this.pathToFile) || isRemote()) ? false : true;
    }

    public boolean isBuiltIn() {
        return this.builtIn;
    }
}
