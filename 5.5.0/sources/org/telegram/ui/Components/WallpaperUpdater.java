package org.telegram.ui.Components;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.ui.ActionBar.AlertDialog.Builder;

public class WallpaperUpdater {
    private String currentPicturePath;
    private File currentWallpaperPath;
    private WallpaperUpdaterDelegate delegate;
    private Activity parentActivity;
    private File picturePath = null;

    public interface WallpaperUpdaterDelegate {
        void didSelectWallpaper(File file, Bitmap bitmap);

        void needOpenColorPicker();
    }

    public WallpaperUpdater(Activity activity, WallpaperUpdaterDelegate wallpaperUpdaterDelegate) {
        this.parentActivity = activity;
        this.delegate = wallpaperUpdaterDelegate;
        this.currentWallpaperPath = new File(FileLoader.getInstance().getDirectory(4), Utilities.random.nextInt() + ".jpg");
    }

    public void cleanup() {
        this.currentWallpaperPath.delete();
    }

    public String getCurrentPicturePath() {
        return this.currentPicturePath;
    }

    public File getCurrentWallpaperPath() {
        return this.currentWallpaperPath;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        FileOutputStream fileOutputStream;
        Throwable e;
        FileOutputStream fileOutputStream2 = null;
        if (i2 != -1) {
            return;
        }
        Point realScreenSize;
        Bitmap loadBitmap;
        if (i == 10) {
            AndroidUtilities.addMediaToGallery(this.currentPicturePath);
            try {
                realScreenSize = AndroidUtilities.getRealScreenSize();
                loadBitmap = ImageLoader.loadBitmap(this.currentPicturePath, null, (float) realScreenSize.x, (float) realScreenSize.y, true);
                fileOutputStream = new FileOutputStream(this.currentWallpaperPath);
                try {
                    loadBitmap.compress(CompressFormat.JPEG, 87, fileOutputStream);
                    this.delegate.didSelectWallpaper(this.currentWallpaperPath, loadBitmap);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    try {
                        FileLog.e(e2);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable e22) {
                                FileLog.e(e22);
                            }
                        }
                        this.currentPicturePath = null;
                    } catch (Throwable th) {
                        e22 = th;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (Throwable e4) {
                                FileLog.e(e4);
                            }
                        }
                        throw e22;
                    }
                }
            } catch (Exception e5) {
                e22 = e5;
                fileOutputStream = null;
                FileLog.e(e22);
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                this.currentPicturePath = null;
            } catch (Throwable th2) {
                e22 = th2;
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                throw e22;
            }
            this.currentPicturePath = null;
        } else if (i == 11 && intent != null && intent.getData() != null) {
            try {
                realScreenSize = AndroidUtilities.getRealScreenSize();
                loadBitmap = ImageLoader.loadBitmap(null, intent.getData(), (float) realScreenSize.x, (float) realScreenSize.y, true);
                loadBitmap.compress(CompressFormat.JPEG, 87, new FileOutputStream(this.currentWallpaperPath));
                this.delegate.didSelectWallpaper(this.currentWallpaperPath, loadBitmap);
            } catch (Throwable e222) {
                FileLog.e(e222);
            }
        }
    }

    public void setCurrentPicturePath(String str) {
        this.currentPicturePath = str;
    }

    public void showAlert(final boolean z) {
        Builder builder = new Builder(this.parentActivity);
        builder.setItems(z ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("SelectColor", R.string.SelectColor), LocaleController.getString("Default", R.string.Default), LocaleController.getString("Cancel", R.string.Cancel)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("Cancel", R.string.Cancel)}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                if (i == 0) {
                    try {
                        intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        File generatePicturePath = AndroidUtilities.generatePicturePath();
                        if (generatePicturePath != null) {
                            if (VERSION.SDK_INT >= 24) {
                                intent.putExtra("output", FileProvider.a(WallpaperUpdater.this.parentActivity, "org.ir.talaeii.provider", generatePicturePath));
                                intent.addFlags(2);
                                intent.addFlags(1);
                            } else {
                                intent.putExtra("output", Uri.fromFile(generatePicturePath));
                            }
                            WallpaperUpdater.this.currentPicturePath = generatePicturePath.getAbsolutePath();
                        }
                        WallpaperUpdater.this.parentActivity.startActivityForResult(intent, 10);
                    } catch (Throwable e) {
                        try {
                            FileLog.e(e);
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    }
                } else if (i == 1) {
                    intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    WallpaperUpdater.this.parentActivity.startActivityForResult(intent, 11);
                } else if (!z) {
                } else {
                    if (i == 2) {
                        WallpaperUpdater.this.delegate.needOpenColorPicker();
                    } else if (i == 3) {
                        WallpaperUpdater.this.delegate.didSelectWallpaper(null, null);
                    }
                }
            }
        });
        builder.show();
    }
}
