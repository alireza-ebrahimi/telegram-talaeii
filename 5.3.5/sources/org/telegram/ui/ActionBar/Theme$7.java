package org.telegram.ui.ActionBar;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.FileInputStream;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.NotificationCenter;

class Theme$7 implements Runnable {

    /* renamed from: org.telegram.ui.ActionBar.Theme$7$1 */
    class C20081 implements Runnable {
        C20081() {
        }

        public void run() {
            Theme.applyChatServiceMessageColor();
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetNewWallpapper, new Object[0]);
        }
    }

    Theme$7() {
    }

    public void run() {
        Throwable e;
        int i;
        SharedPreferences preferences;
        int selectedBackground;
        Throwable th;
        synchronized (Theme.access$200()) {
            File toFile;
            if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("overrideThemeWallpaper", false)) {
                Integer backgroundColor = (Integer) Theme.access$300().get(Theme.key_chat_wallpaper);
                if (backgroundColor != null) {
                    Theme.access$402(new ColorDrawable(backgroundColor.intValue()));
                    Theme.access$502(true);
                } else if (Theme.access$600() > 0 && !(Theme.access$700().pathToFile == null && Theme.access$700().assetName == null)) {
                    FileInputStream stream = null;
                    try {
                        File file;
                        if (Theme.access$700().assetName != null) {
                            file = Theme.getAssetFile(Theme.access$700().assetName);
                        } else {
                            file = new File(Theme.access$700().pathToFile);
                        }
                        FileInputStream stream2 = new FileInputStream(file);
                        try {
                            stream2.getChannel().position((long) Theme.access$600());
                            Bitmap bitmap = BitmapFactory.decodeStream(stream2);
                            if (bitmap != null) {
                                Theme.access$802(Theme.access$402(new BitmapDrawable(bitmap)));
                                Theme.access$502(true);
                            }
                            if (stream2 != null) {
                                try {
                                    stream2.close();
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            stream = stream2;
                            if (stream != null) {
                                stream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        e = th3;
                        FileLog.e(e);
                        if (stream != null) {
                            stream.close();
                        }
                        if (Theme.access$400() == null) {
                            i = 0;
                            preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            selectedBackground = preferences.getInt("selectedBackground", 1000001);
                            i = preferences.getInt("selectedColor", 0);
                            if (i == 0) {
                                if (selectedBackground == 1000001) {
                                    Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                                    Theme.access$502(false);
                                } else {
                                    toFile = new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                                    if (toFile.exists()) {
                                        Theme.access$402(Drawable.createFromPath(toFile.getAbsolutePath()));
                                        Theme.access$502(true);
                                    } else {
                                        Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                                        Theme.access$502(false);
                                    }
                                }
                            }
                            if (Theme.access$400() == null) {
                                if (i == 0) {
                                    i = -2693905;
                                }
                                Theme.access$402(new ColorDrawable(i));
                            }
                        }
                        Theme.access$900(Theme.access$400(), 1);
                        AndroidUtilities.runOnUIThread(new C20081());
                    }
                }
            }
            if (Theme.access$400() == null) {
                i = 0;
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                selectedBackground = preferences.getInt("selectedBackground", 1000001);
                i = preferences.getInt("selectedColor", 0);
                if (i == 0) {
                    if (selectedBackground == 1000001) {
                        Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                        Theme.access$502(false);
                    } else {
                        toFile = new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                        if (toFile.exists()) {
                            Theme.access$402(Drawable.createFromPath(toFile.getAbsolutePath()));
                            Theme.access$502(true);
                        } else {
                            Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                            Theme.access$502(false);
                        }
                    }
                }
                if (Theme.access$400() == null) {
                    if (i == 0) {
                        i = -2693905;
                    }
                    Theme.access$402(new ColorDrawable(i));
                }
            }
            Theme.access$900(Theme.access$400(), 1);
            AndroidUtilities.runOnUIThread(new C20081());
        }
    }
}
