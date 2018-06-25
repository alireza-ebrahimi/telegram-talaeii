package org.telegram.messenger;

import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import com.persianswitch.sdk.base.manager.LanguageManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashMap;
import java.util.Locale;
import org.telegram.tgnet.TLRPC$TL_langPackDifference;

class LocaleController$4 implements Runnable {
    final /* synthetic */ LocaleController this$0;
    final /* synthetic */ TLRPC$TL_langPackDifference val$difference;
    final /* synthetic */ String val$langCode;
    final /* synthetic */ HashMap val$valuesToSet;

    LocaleController$4(LocaleController this$0, String str, TLRPC$TL_langPackDifference tLRPC$TL_langPackDifference, HashMap hashMap) {
        this.this$0 = this$0;
        this.val$langCode = str;
        this.val$difference = tLRPC$TL_langPackDifference;
        this.val$valuesToSet = hashMap;
    }

    public void run() {
        LocaleController$LocaleInfo localeInfo = LocaleController.access$200(this.this$0, this.val$langCode);
        if (localeInfo != null) {
            localeInfo.version = this.val$difference.version;
        }
        LocaleController.access$300(this.this$0);
        if (LocaleController.access$400(this.this$0) == null || !LocaleController.access$400(this.this$0).isLocal()) {
            try {
                Locale newLocale;
                String[] args = localeInfo.shortName.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                if (args.length == 1) {
                    newLocale = new Locale(localeInfo.shortName);
                } else {
                    newLocale = new Locale(args[0], args[1]);
                }
                if (newLocale != null) {
                    LocaleController.access$502(this.this$0, localeInfo.shortName);
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                    editor.putString("language", localeInfo.getKey());
                    editor.commit();
                }
                if (newLocale != null) {
                    LocaleController.access$602(this.this$0, this.val$valuesToSet);
                    LocaleController.access$702(this.this$0, newLocale);
                    LocaleController.access$402(this.this$0, localeInfo);
                    LocaleController.access$802(this.this$0, (LocaleController$PluralRules) LocaleController.access$900(this.this$0).get(LocaleController.access$700(this.this$0).getLanguage()));
                    if (LocaleController.access$800(this.this$0) == null) {
                        LocaleController.access$802(this.this$0, (LocaleController$PluralRules) LocaleController.access$900(this.this$0).get(LanguageManager.ENGLISH));
                    }
                    LocaleController.access$1002(this.this$0, true);
                    Locale.setDefault(LocaleController.access$700(this.this$0));
                    Configuration config = new Configuration();
                    config.locale = LocaleController.access$700(this.this$0);
                    ApplicationLoader.applicationContext.getResources().updateConfiguration(config, ApplicationLoader.applicationContext.getResources().getDisplayMetrics());
                    LocaleController.access$1002(this.this$0, false);
                }
            } catch (Exception e) {
                FileLog.e(e);
                LocaleController.access$1002(this.this$0, false);
            }
            this.this$0.recreateFormatters();
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInterface, new Object[0]);
        }
    }
}
