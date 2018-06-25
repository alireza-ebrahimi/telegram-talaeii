package org.telegram.ui.ActionBar;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.NotificationCenter;

class Theme$7 implements Runnable {

    /* renamed from: org.telegram.ui.ActionBar.Theme$7$1 */
    class C38461 implements Runnable {
        C38461() {
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
        InputStream inputStream;
        SharedPreferences sharedPreferences;
        int i;
        int i2;
        File file;
        synchronized (Theme.access$200()) {
            if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("overrideThemeWallpaper", false)) {
                Integer num = (Integer) Theme.access$300().get(Theme.key_chat_wallpaper);
                if (num != null) {
                    Theme.access$402(new ColorDrawable(num.intValue()));
                    Theme.access$502(true);
                } else if (Theme.access$600() > 0 && !(Theme.access$700().pathToFile == null && Theme.access$700().assetName == null)) {
                    FileInputStream fileInputStream = null;
                    try {
                        InputStream fileInputStream2 = new FileInputStream(Theme.access$700().assetName != null ? Theme.getAssetFile(Theme.access$700().assetName) : new File(Theme.access$700().pathToFile));
                        try {
                            fileInputStream2.getChannel().position((long) Theme.access$600());
                            Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream2);
                            if (decodeStream != null) {
                                Theme.access$802(Theme.access$402(new BitmapDrawable(decodeStream)));
                                Theme.access$502(true);
                            }
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Throwable e2) {
                                    FileLog.e(e2);
                                }
                            }
                        } catch (Throwable th) {
                            e2 = th;
                            inputStream = fileInputStream2;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw e2;
                        }
                    } catch (Throwable th2) {
                        e2 = th2;
                        FileLog.e(e2);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (Theme.access$400() == null) {
                            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            i = sharedPreferences.getInt("selectedBackground", 1000001);
                            i2 = sharedPreferences.getInt("selectedColor", 0);
                            if (i2 == 0) {
                                if (i == 1000001) {
                                    Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                                    Theme.access$502(false);
                                } else {
                                    file = new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                                    if (file.exists()) {
                                        Theme.access$402(Drawable.createFromPath(file.getAbsolutePath()));
                                        Theme.access$502(true);
                                    } else {
                                        Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                                        Theme.access$502(false);
                                    }
                                }
                            }
                            if (Theme.access$400() == null) {
                                if (i2 == 0) {
                                    i2 = -2693905;
                                }
                                Theme.access$402(new ColorDrawable(i2));
                            }
                        }
                        Theme.access$900(Theme.access$400(), 1);
                        AndroidUtilities.runOnUIThread(new C38461());
                    }
                }
            }
            if (Theme.access$400() == null) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                i = sharedPreferences.getInt("selectedBackground", 1000001);
                i2 = sharedPreferences.getInt("selectedColor", 0);
                if (i2 == 0) {
                    if (i == 1000001) {
                        Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                        Theme.access$502(false);
                    } else {
                        file = new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                        if (file.exists()) {
                            Theme.access$402(Drawable.createFromPath(file.getAbsolutePath()));
                            Theme.access$502(true);
                        } else {
                            Theme.access$402(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.background_hd));
                            Theme.access$502(false);
                        }
                    }
                }
                if (Theme.access$400() == null) {
                    if (i2 == 0) {
                        i2 = -2693905;
                    }
                    Theme.access$402(new ColorDrawable(i2));
                }
            }
            Theme.access$900(Theme.access$400(), 1);
            AndroidUtilities.runOnUIThread(new C38461());
        }
    }
}
