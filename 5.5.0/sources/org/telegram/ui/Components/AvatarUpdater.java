package org.telegram.ui.Components;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper.SendingMediaInfo;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PhotoAlbumPickerActivity;
import org.telegram.ui.PhotoAlbumPickerActivity.PhotoAlbumPickerActivityDelegate;
import org.telegram.ui.PhotoCropActivity;
import org.telegram.ui.PhotoCropActivity.PhotoEditActivityDelegate;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;

public class AvatarUpdater implements NotificationCenterDelegate, PhotoEditActivityDelegate {
    private PhotoSize bigPhoto;
    private boolean clearAfterUpdate = false;
    public String currentPicturePath;
    public AvatarUpdaterDelegate delegate;
    public BaseFragment parentFragment = null;
    File picturePath = null;
    public boolean returnOnly = false;
    private PhotoSize smallPhoto;
    public String uploadingAvatar = null;

    public interface AvatarUpdaterDelegate {
        void didUploadedPhoto(InputFile inputFile, PhotoSize photoSize, PhotoSize photoSize2);
    }

    /* renamed from: org.telegram.ui.Components.AvatarUpdater$1 */
    class C43371 implements PhotoAlbumPickerActivityDelegate {
        C43371() {
        }

        public void didSelectPhotos(ArrayList<SendingMediaInfo> arrayList) {
            if (!arrayList.isEmpty()) {
                AvatarUpdater.this.processBitmap(ImageLoader.loadBitmap(((SendingMediaInfo) arrayList.get(0)).path, null, 800.0f, 800.0f, true));
            }
        }

        public void startPhotoSelectActivity() {
            try {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                AvatarUpdater.this.parentFragment.startActivityForResult(intent, 14);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    private void processBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.smallPhoto = ImageLoader.scaleAndSaveImage(bitmap, 100.0f, 100.0f, 80, false);
            this.bigPhoto = ImageLoader.scaleAndSaveImage(bitmap, 800.0f, 800.0f, 80, false, 320, 320);
            bitmap.recycle();
            if (this.bigPhoto != null && this.smallPhoto != null) {
                if (!this.returnOnly) {
                    UserConfig.saveConfig(false);
                    this.uploadingAvatar = FileLoader.getInstance().getDirectory(4) + "/" + this.bigPhoto.location.volume_id + "_" + this.bigPhoto.location.local_id + ".jpg";
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidUpload);
                    NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailUpload);
                    FileLoader.getInstance().uploadFile(this.uploadingAvatar, false, true, 16777216);
                } else if (this.delegate != null) {
                    this.delegate.didUploadedPhoto(null, this.smallPhoto, this.bigPhoto);
                }
            }
        }
    }

    private void startCrop(String str, Uri uri) {
        try {
            LaunchActivity launchActivity = (LaunchActivity) this.parentFragment.getParentActivity();
            if (launchActivity != null) {
                Bundle bundle = new Bundle();
                if (str != null) {
                    bundle.putString("photoPath", str);
                } else if (uri != null) {
                    bundle.putParcelable("photoUri", uri);
                }
                BaseFragment photoCropActivity = new PhotoCropActivity(bundle);
                photoCropActivity.setDelegate(this);
                launchActivity.presentFragment(photoCropActivity);
            }
        } catch (Throwable e) {
            FileLog.e(e);
            processBitmap(ImageLoader.loadBitmap(str, uri, 800.0f, 800.0f, true));
        }
    }

    public void clear() {
        if (this.uploadingAvatar != null) {
            this.clearAfterUpdate = true;
            return;
        }
        this.parentFragment = null;
        this.delegate = null;
    }

    public void didFinishEdit(Bitmap bitmap) {
        processBitmap(bitmap);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        if (i == NotificationCenter.FileDidUpload) {
            str = (String) objArr[0];
            if (this.uploadingAvatar != null && str.equals(this.uploadingAvatar)) {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidUpload);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailUpload);
                if (this.delegate != null) {
                    this.delegate.didUploadedPhoto((InputFile) objArr[1], this.smallPhoto, this.bigPhoto);
                }
                this.uploadingAvatar = null;
                if (this.clearAfterUpdate) {
                    this.parentFragment = null;
                    this.delegate = null;
                }
            }
        } else if (i == NotificationCenter.FileDidFailUpload) {
            str = (String) objArr[0];
            if (this.uploadingAvatar != null && str.equals(this.uploadingAvatar)) {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidUpload);
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailUpload);
                this.uploadingAvatar = null;
                if (this.clearAfterUpdate) {
                    this.parentFragment = null;
                    this.delegate = null;
                }
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        if (i == 13) {
            int i3;
            PhotoViewer.getInstance().setParentActivity(this.parentFragment.getParentActivity());
            try {
                int i4;
                switch (new ExifInterface(this.currentPicturePath).getAttributeInt("Orientation", 1)) {
                    case 3:
                        i4 = 180;
                        break;
                    case 6:
                        i4 = 90;
                        break;
                    case 8:
                        i4 = 270;
                        break;
                    default:
                        i4 = 0;
                        break;
                }
                i3 = i4;
            } catch (Throwable e) {
                FileLog.e(e);
                i3 = 0;
            }
            final ArrayList arrayList = new ArrayList();
            arrayList.add(new PhotoEntry(0, 0, 0, this.currentPicturePath, i3, false));
            PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, 1, new EmptyPhotoViewerProvider() {
                public boolean allowCaption() {
                    return false;
                }

                public boolean canScrollAway() {
                    return false;
                }

                public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
                    PhotoEntry photoEntry = (PhotoEntry) arrayList.get(0);
                    String str = photoEntry.imagePath != null ? photoEntry.imagePath : photoEntry.path != null ? photoEntry.path : null;
                    AvatarUpdater.this.processBitmap(ImageLoader.loadBitmap(str, null, 800.0f, 800.0f, true));
                }
            }, null);
            AndroidUtilities.addMediaToGallery(this.currentPicturePath);
            this.currentPicturePath = null;
        } else if (i == 14 && intent != null && intent.getData() != null) {
            startCrop(null, intent.getData());
        }
    }

    public void openCamera() {
        if (this.parentFragment != null && this.parentFragment.getParentActivity() != null) {
            try {
                if (VERSION.SDK_INT < 23 || this.parentFragment.getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File generatePicturePath = AndroidUtilities.generatePicturePath();
                    if (generatePicturePath != null) {
                        if (VERSION.SDK_INT >= 24) {
                            intent.putExtra("output", FileProvider.a(this.parentFragment.getParentActivity(), "org.ir.talaeii.provider", generatePicturePath));
                            intent.addFlags(2);
                            intent.addFlags(1);
                        } else {
                            intent.putExtra("output", Uri.fromFile(generatePicturePath));
                        }
                        this.currentPicturePath = generatePicturePath.getAbsolutePath();
                    }
                    this.parentFragment.startActivityForResult(intent, 13);
                    return;
                }
                this.parentFragment.getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 19);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public void openGallery() {
        if (VERSION.SDK_INT < 23 || this.parentFragment == null || this.parentFragment.getParentActivity() == null || this.parentFragment.getParentActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            BaseFragment photoAlbumPickerActivity = new PhotoAlbumPickerActivity(true, false, false, null);
            photoAlbumPickerActivity.setDelegate(new C43371());
            this.parentFragment.presentFragment(photoAlbumPickerActivity);
            return;
        }
        this.parentFragment.getParentActivity().requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 4);
    }
}
