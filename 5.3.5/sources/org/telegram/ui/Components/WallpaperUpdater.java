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

    public void showAlert(final boolean fromTheme) {
        Builder builder = new Builder(this.parentActivity);
        builder.setItems(fromTheme ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("SelectColor", R.string.SelectColor), LocaleController.getString("Default", R.string.Default), LocaleController.getString("Cancel", R.string.Cancel)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("Cancel", R.string.Cancel)}, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    try {
                        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                        File image = AndroidUtilities.generatePicturePath();
                        if (image != null) {
                            if (VERSION.SDK_INT >= 24) {
                                takePictureIntent.putExtra("output", FileProvider.getUriForFile(WallpaperUpdater.this.parentActivity, "org.ir.talaeii.provider", image));
                                takePictureIntent.addFlags(2);
                                takePictureIntent.addFlags(1);
                            } else {
                                takePictureIntent.putExtra("output", Uri.fromFile(image));
                            }
                            WallpaperUpdater.this.currentPicturePath = image.getAbsolutePath();
                        }
                        WallpaperUpdater.this.parentActivity.startActivityForResult(takePictureIntent, 10);
                    } catch (Exception e) {
                        try {
                            FileLog.e(e);
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                    }
                } else if (i == 1) {
                    Intent photoPickerIntent = new Intent("android.intent.action.PICK");
                    photoPickerIntent.setType("image/*");
                    WallpaperUpdater.this.parentActivity.startActivityForResult(photoPickerIntent, 11);
                } else if (!fromTheme) {
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

    public void cleanup() {
        this.currentWallpaperPath.delete();
    }

    public File getCurrentWallpaperPath() {
        return this.currentWallpaperPath;
    }

    public String getCurrentPicturePath() {
        return this.currentPicturePath;
    }

    public void setCurrentPicturePath(String value) {
        this.currentPicturePath = value;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Exception e;
        Throwable th;
        if (resultCode != -1) {
            return;
        }
        Point screenSize;
        Bitmap bitmap;
        if (requestCode == 10) {
            AndroidUtilities.addMediaToGallery(this.currentPicturePath);
            FileOutputStream stream = null;
            try {
                screenSize = AndroidUtilities.getRealScreenSize();
                bitmap = ImageLoader.loadBitmap(this.currentPicturePath, null, (float) screenSize.x, (float) screenSize.y, true);
                FileOutputStream stream2 = new FileOutputStream(this.currentWallpaperPath);
                try {
                    bitmap.compress(CompressFormat.JPEG, 87, stream2);
                    this.delegate.didSelectWallpaper(this.currentWallpaperPath, bitmap);
                    if (stream2 != null) {
                        try {
                            stream2.close();
                        } catch (Exception e2) {
                            FileLog.e(e2);
                            stream = stream2;
                        }
                    }
                    stream = stream2;
                } catch (Exception e3) {
                    e2 = e3;
                    stream = stream2;
                    try {
                        FileLog.e(e2);
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Exception e22) {
                                FileLog.e(e22);
                            }
                        }
                        this.currentPicturePath = null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Exception e222) {
                                FileLog.e(e222);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    stream = stream2;
                    if (stream != null) {
                        stream.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e222 = e4;
                FileLog.e(e222);
                if (stream != null) {
                    stream.close();
                }
                this.currentPicturePath = null;
            }
            this.currentPicturePath = null;
        } else if (requestCode == 11 && data != null && data.getData() != null) {
            try {
                screenSize = AndroidUtilities.getRealScreenSize();
                bitmap = ImageLoader.loadBitmap(null, data.getData(), (float) screenSize.x, (float) screenSize.y, true);
                bitmap.compress(CompressFormat.JPEG, 87, new FileOutputStream(this.currentWallpaperPath));
                this.delegate.didSelectWallpaper(this.currentWallpaperPath, bitmap);
            } catch (Exception e2222) {
                FileLog.e(e2222);
            }
        }
    }
}
