package org.telegram.messenger;

import com.persianswitch.sdk.base.manager.LanguageManager;
import java.util.HashMap;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_langPackLanguage;
import org.telegram.tgnet.TLRPC$Vector;

class LocaleController$5 implements RequestDelegate {
    final /* synthetic */ LocaleController this$0;

    LocaleController$5(LocaleController this$0) {
        this.this$0 = this$0;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    int a;
                    LocaleController.access$1102(LocaleController$5.this.this$0, false);
                    TLRPC$Vector res = response;
                    HashMap<String, LocaleController$LocaleInfo> remoteLoaded = new HashMap();
                    LocaleController$5.this.this$0.remoteLanguages.clear();
                    for (a = 0; a < res.objects.size(); a++) {
                        TLRPC$TL_langPackLanguage language = (TLRPC$TL_langPackLanguage) res.objects.get(a);
                        FileLog.d("loaded lang " + language.name);
                        LocaleController$LocaleInfo localeInfo = new LocaleController$LocaleInfo();
                        localeInfo.nameEnglish = language.name;
                        localeInfo.name = language.native_name;
                        localeInfo.shortName = language.lang_code.replace('-', '_').toLowerCase();
                        localeInfo.pathToFile = "remote";
                        LocaleController$LocaleInfo existing = LocaleController.access$200(LocaleController$5.this.this$0, localeInfo.getKey());
                        if (existing == null) {
                            LocaleController$5.this.this$0.languages.add(localeInfo);
                            LocaleController$5.this.this$0.languagesDict.put(localeInfo.getKey(), localeInfo);
                            existing = localeInfo;
                        } else {
                            existing.nameEnglish = localeInfo.nameEnglish;
                            existing.name = localeInfo.name;
                            existing.pathToFile = localeInfo.pathToFile;
                        }
                        LocaleController$5.this.this$0.remoteLanguages.add(localeInfo);
                        remoteLoaded.put(localeInfo.getKey(), existing);
                    }
                    a = 0;
                    while (a < LocaleController$5.this.this$0.languages.size()) {
                        LocaleController$LocaleInfo info = (LocaleController$LocaleInfo) LocaleController$5.this.this$0.languages.get(a);
                        if (!info.isBuiltIn() && info.isRemote() && ((LocaleController$LocaleInfo) remoteLoaded.get(info.getKey())) == null) {
                            FileLog.d("remove lang " + info.getKey());
                            LocaleController$5.this.this$0.languages.remove(a);
                            LocaleController$5.this.this$0.languagesDict.remove(info.getKey());
                            a--;
                            if (info == LocaleController.access$400(LocaleController$5.this.this$0)) {
                                if (LocaleController.access$1200(LocaleController$5.this.this$0).getLanguage() != null) {
                                    info = LocaleController.access$200(LocaleController$5.this.this$0, LocaleController.access$1200(LocaleController$5.this.this$0).getLanguage());
                                }
                                if (info == null) {
                                    info = LocaleController.access$200(LocaleController$5.this.this$0, LocaleController.access$1300(LocaleController$5.this.this$0, LocaleController.access$1200(LocaleController$5.this.this$0)));
                                }
                                if (info == null) {
                                    info = LocaleController.access$200(LocaleController$5.this.this$0, LanguageManager.ENGLISH);
                                }
                                LocaleController$5.this.this$0.applyLanguage(info, true, false);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInterface, new Object[0]);
                            }
                        }
                        a++;
                    }
                    LocaleController.access$300(LocaleController$5.this.this$0);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.suggestedLangpack, new Object[0]);
                    LocaleController$5.this.this$0.applyLanguage(LocaleController.access$400(LocaleController$5.this.this$0), true, false);
                }
            });
        }
    }
}
