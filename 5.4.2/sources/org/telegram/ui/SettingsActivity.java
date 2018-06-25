package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_getSupport;
import org.telegram.tgnet.TLRPC$TL_help_support;
import org.telegram.tgnet.TLRPC$TL_photos_photo;
import org.telegram.tgnet.TLRPC$TL_photos_uploadProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC$TL_userProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_userProfilePhotoEmpty;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetCell;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberPicker;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;

public class SettingsActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int edit_name = 1;
    private static final int logout = 2;
    private int askQuestionRow;
    private int autoplayGifsRow;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater = new AvatarUpdater();
    private int backgroundRow;
    private int bioRow;
    private int clearLogsRow;
    private int contactsReimportRow;
    private int contactsSectionRow;
    private int contactsSortRow;
    private int customTabsRow;
    private int dataRow;
    private int directShareRow;
    private int dumpCallStatsRow;
    private int emojiRow;
    private int emptyRow;
    private int enableAnimationsRow;
    private int extraHeight;
    private View extraHeightView;
    private int languageRow;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int messagesSectionRow;
    private int messagesSectionRow2;
    private TextView nameTextView;
    private int notificationRow;
    private int numberRow;
    private int numberSectionRow;
    private TextView onlineTextView;
    private int overscrollRow;
    private int privacyPolicyRow;
    private int privacyRow;
    private PhotoViewerProvider provider = new C51901();
    private int raiseToSpeakRow;
    private int rowCount;
    private int saveToGalleryRow;
    private int sendByEnterRow;
    private int sendLogsRow;
    private int settingsSectionRow;
    private int settingsSectionRow2;
    private View shadowView;
    private int stickersRow;
    private int supportSectionRow;
    private int supportSectionRow2;
    private int switchBackendButtonRow;
    private int telegramFaqRow;
    private int textSizeRow;
    private int themeRow;
    private int usernameRow;
    private int versionRow;
    private ImageView writeButton;
    private AnimatorSet writeButtonAnimation;

    /* renamed from: org.telegram.ui.SettingsActivity$1 */
    class C51901 extends EmptyPhotoViewerProvider {
        C51901() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            int i2 = 0;
            if (fileLocation == null) {
                return null;
            }
            User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                FileLocation fileLocation2 = user.photo.photo_big;
                if (fileLocation2.local_id == fileLocation.local_id && fileLocation2.volume_id == fileLocation.volume_id && fileLocation2.dc_id == fileLocation.dc_id) {
                    int[] iArr = new int[2];
                    SettingsActivity.this.avatarImage.getLocationInWindow(iArr);
                    PlaceProviderObject placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = iArr[0];
                    int i3 = iArr[1];
                    if (VERSION.SDK_INT < 21) {
                        i2 = AndroidUtilities.statusBarHeight;
                    }
                    placeProviderObject.viewY = i3 - i2;
                    placeProviderObject.parentView = SettingsActivity.this.avatarImage;
                    placeProviderObject.imageReceiver = SettingsActivity.this.avatarImage.getImageReceiver();
                    placeProviderObject.dialogId = UserConfig.getClientUserId();
                    placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
                    placeProviderObject.size = -1;
                    placeProviderObject.radius = SettingsActivity.this.avatarImage.getImageReceiver().getRoundRadius();
                    placeProviderObject.scale = SettingsActivity.this.avatarImage.getScaleX();
                    return placeProviderObject;
                }
            }
            return null;
        }

        public void willHidePhotoViewer() {
            SettingsActivity.this.avatarImage.getImageReceiver().setVisible(true, true);
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$2 */
    class C51932 implements AvatarUpdaterDelegate {

        /* renamed from: org.telegram.ui.SettingsActivity$2$1 */
        class C51921 implements RequestDelegate {

            /* renamed from: org.telegram.ui.SettingsActivity$2$1$1 */
            class C51911 implements Runnable {
                C51911() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(MessagesController.UPDATE_MASK_ALL)});
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                    UserConfig.saveConfig(true);
                }
            }

            C51921() {
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
                    if (user == null) {
                        user = UserConfig.getCurrentUser();
                        if (user != null) {
                            MessagesController.getInstance().putUser(user, false);
                        } else {
                            return;
                        }
                    }
                    UserConfig.setCurrentUser(user);
                    TLRPC$TL_photos_photo tLRPC$TL_photos_photo = (TLRPC$TL_photos_photo) tLObject;
                    ArrayList arrayList = tLRPC$TL_photos_photo.photo.sizes;
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 100);
                    PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 1000);
                    user.photo = new TLRPC$TL_userProfilePhoto();
                    user.photo.photo_id = tLRPC$TL_photos_photo.photo.id;
                    if (closestPhotoSizeWithSize != null) {
                        user.photo.photo_small = closestPhotoSizeWithSize.location;
                    }
                    if (closestPhotoSizeWithSize2 != null) {
                        user.photo.photo_big = closestPhotoSizeWithSize2.location;
                    } else if (closestPhotoSizeWithSize != null) {
                        user.photo.photo_small = closestPhotoSizeWithSize.location;
                    }
                    MessagesStorage.getInstance().clearUserPhotos(user.id);
                    arrayList = new ArrayList();
                    arrayList.add(user);
                    MessagesStorage.getInstance().putUsersAndChats(arrayList, null, false, true);
                    AndroidUtilities.runOnUIThread(new C51911());
                }
            }
        }

        C51932() {
        }

        public void didUploadedPhoto(InputFile inputFile, PhotoSize photoSize, PhotoSize photoSize2) {
            TLObject tLRPC$TL_photos_uploadProfilePhoto = new TLRPC$TL_photos_uploadProfilePhoto();
            tLRPC$TL_photos_uploadProfilePhoto.file = inputFile;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_photos_uploadProfilePhoto, new C51921());
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$3 */
    class C51953 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.SettingsActivity$3$1 */
        class C51941 implements OnClickListener {
            C51941() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                MessagesController.getInstance().performLogout(true);
            }
        }

        C51953() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                SettingsActivity.this.finishFragment();
            } else if (i == 1) {
                SettingsActivity.this.presentFragment(new ChangeNameActivity());
            } else if (i == 2 && SettingsActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(SettingsActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("AreYouSureLogout", R.string.AreYouSureLogout));
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C51941());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                SettingsActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$6 */
    class C52046 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.SettingsActivity$6$2 */
        class C51992 implements OnClickListener {
            C51992() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.this.performAskAQuestion();
            }
        }

        /* renamed from: org.telegram.ui.SettingsActivity$6$3 */
        class C52003 implements OnClickListener {
            C52003() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                ConnectionsManager.getInstance().switchBackend();
            }
        }

        C52046() {
        }

        public void onItemClick(View view, final int i) {
            boolean z = true;
            Builder builder;
            if (i == SettingsActivity.this.textSizeRow) {
                if (SettingsActivity.this.getParentActivity() != null) {
                    builder = new Builder(SettingsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("TextSize", R.string.TextSize));
                    final View numberPicker = new NumberPicker(SettingsActivity.this.getParentActivity());
                    numberPicker.setMinValue(12);
                    numberPicker.setMaxValue(30);
                    numberPicker.setValue(MessagesController.getInstance().fontSize);
                    builder.setView(numberPicker);
                    builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                            edit.putInt("fons_size", numberPicker.getValue());
                            MessagesController.getInstance().fontSize = numberPicker.getValue();
                            edit.commit();
                            if (SettingsActivity.this.listAdapter != null) {
                                SettingsActivity.this.listAdapter.notifyItemChanged(i);
                            }
                        }
                    });
                    SettingsActivity.this.showDialog(builder.create());
                }
            } else if (i == SettingsActivity.this.enableAnimationsRow) {
                r0 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                r3 = r0.getBoolean("view_animations", true);
                r4 = r0.edit();
                r4.putBoolean("view_animations", !r3);
                r4.commit();
                if (view instanceof TextCheckCell) {
                    r13 = (TextCheckCell) view;
                    if (r3) {
                        z = false;
                    }
                    r13.setChecked(z);
                }
            } else if (i == SettingsActivity.this.notificationRow) {
                SettingsActivity.this.presentFragment(new NotificationsSettingsActivity());
            } else if (i == SettingsActivity.this.backgroundRow) {
                SettingsActivity.this.presentFragment(new WallpapersActivity());
            } else if (i == SettingsActivity.this.askQuestionRow) {
                if (SettingsActivity.this.getParentActivity() != null) {
                    View textView = new TextView(SettingsActivity.this.getParentActivity());
                    CharSequence spannableString = new SpannableString(Html.fromHtml(LocaleController.getString("AskAQuestionInfo", R.string.AskAQuestionInfo).replace("\n", "<br>")));
                    URLSpan[] uRLSpanArr = (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class);
                    for (URLSpan uRLSpan : uRLSpanArr) {
                        int spanStart = spannableString.getSpanStart(uRLSpan);
                        int spanEnd = spannableString.getSpanEnd(uRLSpan);
                        spannableString.removeSpan(uRLSpan);
                        spannableString.setSpan(new URLSpanNoUnderline(uRLSpan.getURL()), spanStart, spanEnd, 0);
                    }
                    textView.setText(spannableString);
                    textView.setTextSize(1, 16.0f);
                    textView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink));
                    textView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection));
                    textView.setPadding(AndroidUtilities.dp(23.0f), 0, AndroidUtilities.dp(23.0f), 0);
                    textView.setMovementMethod(new LinkMovementMethodMy());
                    textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                    builder = new Builder(SettingsActivity.this.getParentActivity());
                    builder.setView(textView);
                    builder.setTitle(LocaleController.getString("AskAQuestion", R.string.AskAQuestion));
                    builder.setPositiveButton(LocaleController.getString("AskButton", R.string.AskButton), new C51992());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    SettingsActivity.this.showDialog(builder.create());
                }
            } else if (i == SettingsActivity.this.sendLogsRow) {
                SettingsActivity.this.sendLogs();
            } else if (i == SettingsActivity.this.clearLogsRow) {
                FileLog.cleanupLogs();
            } else if (i == SettingsActivity.this.sendByEnterRow) {
                r0 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                r3 = r0.getBoolean("send_by_enter", false);
                r4 = r0.edit();
                r4.putBoolean("send_by_enter", !r3);
                r4.commit();
                if (view instanceof TextCheckCell) {
                    r13 = (TextCheckCell) view;
                    if (r3) {
                        z = false;
                    }
                    r13.setChecked(z);
                }
            } else if (i == SettingsActivity.this.raiseToSpeakRow) {
                MediaController.getInstance().toogleRaiseToSpeak();
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(MediaController.getInstance().canRaiseToSpeak());
                }
            } else if (i == SettingsActivity.this.autoplayGifsRow) {
                MediaController.getInstance().toggleAutoplayGifs();
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(MediaController.getInstance().canAutoplayGifs());
                }
            } else if (i == SettingsActivity.this.saveToGalleryRow) {
                MediaController.getInstance().toggleSaveToGallery();
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(MediaController.getInstance().canSaveToGallery());
                }
            } else if (i == SettingsActivity.this.customTabsRow) {
                MediaController.getInstance().toggleCustomTabs();
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(MediaController.getInstance().canCustomTabs());
                }
            } else if (i == SettingsActivity.this.directShareRow) {
                MediaController.getInstance().toggleDirectShare();
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(MediaController.getInstance().canDirectShare());
                }
            } else if (i == SettingsActivity.this.privacyRow) {
                SettingsActivity.this.presentFragment(new PrivacySettingsActivity());
            } else if (i == SettingsActivity.this.dataRow) {
                SettingsActivity.this.presentFragment(new DataSettingsActivity());
            } else if (i == SettingsActivity.this.languageRow) {
                SettingsActivity.this.presentFragment(new LanguageSelectActivity());
            } else if (i == SettingsActivity.this.themeRow) {
                SettingsActivity.this.presentFragment(new ThemeActivity());
            } else if (i == SettingsActivity.this.switchBackendButtonRow) {
                if (SettingsActivity.this.getParentActivity() != null) {
                    builder = new Builder(SettingsActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.getString("AreYouSure", R.string.AreYouSure));
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52003());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    SettingsActivity.this.showDialog(builder.create());
                }
            } else if (i == SettingsActivity.this.telegramFaqRow) {
                Browser.openUrl(SettingsActivity.this.getParentActivity(), LocaleController.getString("TelegramFaqUrl", R.string.TelegramFaqUrl));
            } else if (i == SettingsActivity.this.privacyPolicyRow) {
                Browser.openUrl(SettingsActivity.this.getParentActivity(), LocaleController.getString("PrivacyPolicyUrl", R.string.PrivacyPolicyUrl));
            } else if (i == SettingsActivity.this.contactsReimportRow) {
            } else {
                if (i == SettingsActivity.this.contactsSortRow) {
                    if (SettingsActivity.this.getParentActivity() != null) {
                        builder = new Builder(SettingsActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("SortBy", R.string.SortBy));
                        builder.setItems(new CharSequence[]{LocaleController.getString("Default", R.string.Default), LocaleController.getString("SortFirstName", R.string.SortFirstName), LocaleController.getString("SortLastName", R.string.SortLastName)}, new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                                edit.putInt("sortContactsBy", i);
                                edit.commit();
                                if (SettingsActivity.this.listAdapter != null) {
                                    SettingsActivity.this.listAdapter.notifyItemChanged(i);
                                }
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        SettingsActivity.this.showDialog(builder.create());
                    }
                } else if (i == SettingsActivity.this.usernameRow) {
                    SettingsActivity.this.presentFragment(new ChangeUsernameActivity());
                } else if (i == SettingsActivity.this.bioRow) {
                    if (MessagesController.getInstance().getUserFull(UserConfig.getClientUserId()) != null) {
                        SettingsActivity.this.presentFragment(new ChangeBioActivity());
                    }
                } else if (i == SettingsActivity.this.numberRow) {
                    SettingsActivity.this.presentFragment(new ChangePhoneHelpActivity());
                } else if (i == SettingsActivity.this.stickersRow) {
                    SettingsActivity.this.presentFragment(new StickersActivity(0));
                } else if (i == SettingsActivity.this.emojiRow) {
                    if (SettingsActivity.this.getParentActivity() != null) {
                        final boolean[] zArr = new boolean[2];
                        BottomSheet.Builder builder2 = new BottomSheet.Builder(SettingsActivity.this.getParentActivity());
                        builder2.setApplyTopPadding(false);
                        builder2.setApplyBottomPadding(false);
                        View linearLayout = new LinearLayout(SettingsActivity.this.getParentActivity());
                        linearLayout.setOrientation(1);
                        int i2 = 0;
                        while (true) {
                            if (VERSION.SDK_INT >= 19) {
                                r3 = 2;
                            } else {
                                r3 = true;
                            }
                            if (i2 < r3) {
                                String string;
                                if (i2 == 0) {
                                    zArr[i2] = MessagesController.getInstance().allowBigEmoji;
                                    string = LocaleController.getString("EmojiBigSize", R.string.EmojiBigSize);
                                } else if (i2 == 1) {
                                    zArr[i2] = MessagesController.getInstance().useSystemEmoji;
                                    string = LocaleController.getString("EmojiUseDefault", R.string.EmojiUseDefault);
                                } else {
                                    string = null;
                                }
                                View checkBoxCell = new CheckBoxCell(SettingsActivity.this.getParentActivity(), true);
                                checkBoxCell.setTag(Integer.valueOf(i2));
                                checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                linearLayout.addView(checkBoxCell, LayoutHelper.createLinear(-1, 48));
                                checkBoxCell.setText(string, TtmlNode.ANONYMOUS_REGION_ID, zArr[i2], true);
                                checkBoxCell.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                                checkBoxCell.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                        CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                        int intValue = ((Integer) checkBoxCell.getTag()).intValue();
                                        zArr[intValue] = !zArr[intValue];
                                        checkBoxCell.setChecked(zArr[intValue], true);
                                    }
                                });
                                i2++;
                            } else {
                                View bottomSheetCell = new BottomSheetCell(SettingsActivity.this.getParentActivity(), 1);
                                bottomSheetCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                                bottomSheetCell.setTextAndIcon(LocaleController.getString("Save", R.string.Save).toUpperCase(), 0);
                                bottomSheetCell.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
                                bottomSheetCell.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                        try {
                                            if (SettingsActivity.this.visibleDialog != null) {
                                                SettingsActivity.this.visibleDialog.dismiss();
                                            }
                                        } catch (Throwable e) {
                                            FileLog.e(e);
                                        }
                                        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                                        MessagesController instance = MessagesController.getInstance();
                                        boolean z = zArr[0];
                                        instance.allowBigEmoji = z;
                                        edit.putBoolean("allowBigEmoji", z);
                                        instance = MessagesController.getInstance();
                                        z = zArr[1];
                                        instance.useSystemEmoji = z;
                                        edit.putBoolean("useSystemEmoji", z);
                                        edit.commit();
                                        if (SettingsActivity.this.listAdapter != null) {
                                            SettingsActivity.this.listAdapter.notifyItemChanged(i);
                                        }
                                    }
                                });
                                linearLayout.addView(bottomSheetCell, LayoutHelper.createLinear(-1, 48));
                                builder2.setCustomView(linearLayout);
                                SettingsActivity.this.showDialog(builder2.create());
                                return;
                            }
                        }
                    }
                } else if (i == SettingsActivity.this.dumpCallStatsRow) {
                    r0 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    r3 = r0.getBoolean("dbg_dump_call_stats", false);
                    r4 = r0.edit();
                    r4.putBoolean("dbg_dump_call_stats", !r3);
                    r4.commit();
                    if (view instanceof TextCheckCell) {
                        r13 = (TextCheckCell) view;
                        if (r3) {
                            z = false;
                        }
                        r13.setChecked(z);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$7 */
    class C52067 implements OnItemLongClickListener {
        private int pressCount = 0;

        /* renamed from: org.telegram.ui.SettingsActivity$7$1 */
        class C52051 implements OnClickListener {
            C52051() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    ContactsController.getInstance().forceImportContacts();
                } else if (i == 1) {
                    ContactsController.getInstance().loadContacts(false, 0);
                } else if (i == 2) {
                    ContactsController.getInstance().resetImportedContacts();
                } else if (i == 3) {
                    MessagesController.getInstance().forceResetDialogs();
                } else if (i == 4) {
                    MediaController.getInstance().toggleInappCamera();
                } else if (i == 5) {
                    MediaController.getInstance().toggleRoundCamera16to9();
                }
            }
        }

        C52067() {
        }

        public boolean onItemClick(View view, int i) {
            if (i != SettingsActivity.this.versionRow) {
                return false;
            }
            this.pressCount++;
            if (this.pressCount >= 2 || BuildVars.DEBUG_PRIVATE_VERSION) {
                CharSequence[] charSequenceArr;
                Builder builder = new Builder(SettingsActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("DebugMenu", R.string.DebugMenu));
                CharSequence[] charSequenceArr2;
                if (BuildVars.DEBUG_PRIVATE_VERSION) {
                    charSequenceArr2 = new CharSequence[6];
                    charSequenceArr2[0] = LocaleController.getString("DebugMenuImportContacts", R.string.DebugMenuImportContacts);
                    charSequenceArr2[1] = LocaleController.getString("DebugMenuReloadContacts", R.string.DebugMenuReloadContacts);
                    charSequenceArr2[2] = LocaleController.getString("DebugMenuResetContacts", R.string.DebugMenuResetContacts);
                    charSequenceArr2[3] = LocaleController.getString("DebugMenuResetDialogs", R.string.DebugMenuResetDialogs);
                    charSequenceArr2[4] = MediaController.getInstance().canInAppCamera() ? LocaleController.getString("DebugMenuDisableCamera", R.string.DebugMenuDisableCamera) : LocaleController.getString("DebugMenuEnableCamera", R.string.DebugMenuEnableCamera);
                    charSequenceArr2[5] = MediaController.getInstance().canRoundCamera16to9() ? "switch camera to 4:3" : "switch camera to 16:9";
                    charSequenceArr = charSequenceArr2;
                } else {
                    charSequenceArr2 = new CharSequence[5];
                    charSequenceArr2[0] = LocaleController.getString("DebugMenuImportContacts", R.string.DebugMenuImportContacts);
                    charSequenceArr2[1] = LocaleController.getString("DebugMenuReloadContacts", R.string.DebugMenuReloadContacts);
                    charSequenceArr2[2] = LocaleController.getString("DebugMenuResetContacts", R.string.DebugMenuResetContacts);
                    charSequenceArr2[3] = LocaleController.getString("DebugMenuResetDialogs", R.string.DebugMenuResetDialogs);
                    charSequenceArr2[4] = MediaController.getInstance().canInAppCamera() ? LocaleController.getString("DebugMenuDisableCamera", R.string.DebugMenuDisableCamera) : LocaleController.getString("DebugMenuEnableCamera", R.string.DebugMenuEnableCamera);
                    charSequenceArr = charSequenceArr2;
                }
                builder.setItems(charSequenceArr, new C52051());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                SettingsActivity.this.showDialog(builder.create());
            } else {
                try {
                    Toast.makeText(SettingsActivity.this.getParentActivity(), "¯\\_(ツ)_/¯", 0).show();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$8 */
    class C52078 implements View.OnClickListener {
        C52078() {
        }

        public void onClick(View view) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (user != null && user.photo != null && user.photo.photo_big != null) {
                PhotoViewer.getInstance().setParentActivity(SettingsActivity.this.getParentActivity());
                PhotoViewer.getInstance().openPhoto(user.photo.photo_big, SettingsActivity.this.provider);
            }
        }
    }

    /* renamed from: org.telegram.ui.SettingsActivity$9 */
    class C52089 extends ViewOutlineProvider {
        C52089() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            try {
                return super.onTouchEvent(textView, spannable, motionEvent);
            } catch (Throwable e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return SettingsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == SettingsActivity.this.emptyRow || i == SettingsActivity.this.overscrollRow) ? 0 : (i == SettingsActivity.this.settingsSectionRow || i == SettingsActivity.this.supportSectionRow || i == SettingsActivity.this.messagesSectionRow || i == SettingsActivity.this.contactsSectionRow) ? 1 : (i == SettingsActivity.this.enableAnimationsRow || i == SettingsActivity.this.sendByEnterRow || i == SettingsActivity.this.saveToGalleryRow || i == SettingsActivity.this.autoplayGifsRow || i == SettingsActivity.this.raiseToSpeakRow || i == SettingsActivity.this.customTabsRow || i == SettingsActivity.this.directShareRow || i == SettingsActivity.this.dumpCallStatsRow) ? 3 : (i == SettingsActivity.this.notificationRow || i == SettingsActivity.this.themeRow || i == SettingsActivity.this.backgroundRow || i == SettingsActivity.this.askQuestionRow || i == SettingsActivity.this.sendLogsRow || i == SettingsActivity.this.privacyRow || i == SettingsActivity.this.clearLogsRow || i == SettingsActivity.this.switchBackendButtonRow || i == SettingsActivity.this.telegramFaqRow || i == SettingsActivity.this.contactsReimportRow || i == SettingsActivity.this.textSizeRow || i == SettingsActivity.this.languageRow || i == SettingsActivity.this.contactsSortRow || i == SettingsActivity.this.stickersRow || i == SettingsActivity.this.privacyPolicyRow || i == SettingsActivity.this.emojiRow || i == SettingsActivity.this.dataRow) ? 2 : i == SettingsActivity.this.versionRow ? 5 : (i == SettingsActivity.this.numberRow || i == SettingsActivity.this.usernameRow || i == SettingsActivity.this.bioRow) ? 6 : (i == SettingsActivity.this.settingsSectionRow2 || i == SettingsActivity.this.messagesSectionRow2 || i == SettingsActivity.this.supportSectionRow2 || i == SettingsActivity.this.numberSectionRow) ? 4 : 2;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == SettingsActivity.this.textSizeRow || adapterPosition == SettingsActivity.this.enableAnimationsRow || adapterPosition == SettingsActivity.this.notificationRow || adapterPosition == SettingsActivity.this.backgroundRow || adapterPosition == SettingsActivity.this.numberRow || adapterPosition == SettingsActivity.this.askQuestionRow || adapterPosition == SettingsActivity.this.sendLogsRow || adapterPosition == SettingsActivity.this.sendByEnterRow || adapterPosition == SettingsActivity.this.autoplayGifsRow || adapterPosition == SettingsActivity.this.privacyRow || adapterPosition == SettingsActivity.this.clearLogsRow || adapterPosition == SettingsActivity.this.languageRow || adapterPosition == SettingsActivity.this.usernameRow || adapterPosition == SettingsActivity.this.bioRow || adapterPosition == SettingsActivity.this.switchBackendButtonRow || adapterPosition == SettingsActivity.this.telegramFaqRow || adapterPosition == SettingsActivity.this.contactsSortRow || adapterPosition == SettingsActivity.this.contactsReimportRow || adapterPosition == SettingsActivity.this.saveToGalleryRow || adapterPosition == SettingsActivity.this.stickersRow || adapterPosition == SettingsActivity.this.raiseToSpeakRow || adapterPosition == SettingsActivity.this.privacyPolicyRow || adapterPosition == SettingsActivity.this.customTabsRow || adapterPosition == SettingsActivity.this.directShareRow || adapterPosition == SettingsActivity.this.versionRow || adapterPosition == SettingsActivity.this.emojiRow || adapterPosition == SettingsActivity.this.dataRow || adapterPosition == SettingsActivity.this.themeRow || adapterPosition == SettingsActivity.this.dumpCallStatsRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            String string;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (i == SettingsActivity.this.overscrollRow) {
                        ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(88.0f));
                        return;
                    } else {
                        ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(16.0f));
                        return;
                    }
                case 2:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    int i2;
                    if (i == SettingsActivity.this.textSizeRow) {
                        i2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("fons_size", AndroidUtilities.isTablet() ? 18 : 16);
                        textSettingsCell.setTextAndValue(LocaleController.getString("TextSize", R.string.TextSize), String.format("%d", new Object[]{Integer.valueOf(i2)}), true);
                        return;
                    } else if (i == SettingsActivity.this.languageRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("Language", R.string.Language), LocaleController.getCurrentLanguageName(), true);
                        return;
                    } else if (i == SettingsActivity.this.themeRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("Theme", R.string.Theme), Theme.getCurrentThemeName(), true);
                        return;
                    } else if (i == SettingsActivity.this.contactsSortRow) {
                        i2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("sortContactsBy", 0);
                        string = i2 == 0 ? LocaleController.getString("Default", R.string.Default) : i2 == 1 ? LocaleController.getString("FirstName", R.string.SortFirstName) : LocaleController.getString("LastName", R.string.SortLastName);
                        textSettingsCell.setTextAndValue(LocaleController.getString("SortBy", R.string.SortBy), string, true);
                        return;
                    } else if (i == SettingsActivity.this.notificationRow) {
                        textSettingsCell.setText(LocaleController.getString("NotificationsAndSounds", R.string.NotificationsAndSounds), true);
                        return;
                    } else if (i == SettingsActivity.this.backgroundRow) {
                        textSettingsCell.setText(LocaleController.getString("ChatBackground", R.string.ChatBackground), true);
                        return;
                    } else if (i == SettingsActivity.this.sendLogsRow) {
                        textSettingsCell.setText("Send Logs", true);
                        return;
                    } else if (i == SettingsActivity.this.clearLogsRow) {
                        textSettingsCell.setText("Clear Logs", true);
                        return;
                    } else if (i == SettingsActivity.this.askQuestionRow) {
                        textSettingsCell.setText(LocaleController.getString("AskAQuestion", R.string.AskAQuestion), true);
                        return;
                    } else if (i == SettingsActivity.this.privacyRow) {
                        textSettingsCell.setText(LocaleController.getString("PrivacySettings", R.string.PrivacySettings), true);
                        return;
                    } else if (i == SettingsActivity.this.dataRow) {
                        textSettingsCell.setText(LocaleController.getString("DataSettings", R.string.DataSettings), true);
                        return;
                    } else if (i == SettingsActivity.this.switchBackendButtonRow) {
                        textSettingsCell.setText("Switch Backend", true);
                        return;
                    } else if (i == SettingsActivity.this.telegramFaqRow) {
                        textSettingsCell.setText(LocaleController.getString("TelegramFAQ", R.string.TelegramFAQ), true);
                        return;
                    } else if (i == SettingsActivity.this.contactsReimportRow) {
                        textSettingsCell.setText(LocaleController.getString("ImportContacts", R.string.ImportContacts), true);
                        return;
                    } else if (i == SettingsActivity.this.stickersRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("StickersName", R.string.StickersName), StickersQuery.getUnreadStickerSets().size() != 0 ? String.format("%d", new Object[]{Integer.valueOf(StickersQuery.getUnreadStickerSets().size())}) : TtmlNode.ANONYMOUS_REGION_ID, true);
                        return;
                    } else if (i == SettingsActivity.this.privacyPolicyRow) {
                        textSettingsCell.setText(LocaleController.getString("PrivacyPolicy", R.string.PrivacyPolicy), true);
                        return;
                    } else if (i == SettingsActivity.this.emojiRow) {
                        textSettingsCell.setText(LocaleController.getString("Emoji", R.string.Emoji), true);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    if (i == SettingsActivity.this.enableAnimationsRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("EnableAnimations", R.string.EnableAnimations), sharedPreferences.getBoolean("view_animations", true), false);
                        return;
                    } else if (i == SettingsActivity.this.sendByEnterRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("SendByEnter", R.string.SendByEnter), sharedPreferences.getBoolean("send_by_enter", false), true);
                        return;
                    } else if (i == SettingsActivity.this.saveToGalleryRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("SaveToGallerySettings", R.string.SaveToGallerySettings), MediaController.getInstance().canSaveToGallery(), false);
                        return;
                    } else if (i == SettingsActivity.this.autoplayGifsRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("AutoplayGifs", R.string.AutoplayGifs), MediaController.getInstance().canAutoplayGifs(), true);
                        return;
                    } else if (i == SettingsActivity.this.raiseToSpeakRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("RaiseToSpeak", R.string.RaiseToSpeak), MediaController.getInstance().canRaiseToSpeak(), true);
                        return;
                    } else if (i == SettingsActivity.this.customTabsRow) {
                        textCheckCell.setTextAndValueAndCheck(LocaleController.getString("ChromeCustomTabs", R.string.ChromeCustomTabs), LocaleController.getString("ChromeCustomTabsInfo", R.string.ChromeCustomTabsInfo), MediaController.getInstance().canCustomTabs(), false, true);
                        return;
                    } else if (i == SettingsActivity.this.directShareRow) {
                        textCheckCell.setTextAndValueAndCheck(LocaleController.getString("DirectShare", R.string.DirectShare), LocaleController.getString("DirectShareInfo", R.string.DirectShareInfo), MediaController.getInstance().canDirectShare(), false, true);
                        return;
                    } else if (i == SettingsActivity.this.dumpCallStatsRow) {
                        textCheckCell.setTextAndCheck("Dump detailed call stats", sharedPreferences.getBoolean("dbg_dump_call_stats", false), true);
                        return;
                    } else {
                        return;
                    }
                case 4:
                    if (i == SettingsActivity.this.settingsSectionRow2) {
                        ((HeaderCell) viewHolder.itemView).setText(LocaleController.getString("SETTINGS", R.string.SETTINGS));
                        return;
                    } else if (i == SettingsActivity.this.supportSectionRow2) {
                        ((HeaderCell) viewHolder.itemView).setText(LocaleController.getString("Support", R.string.Support));
                        return;
                    } else if (i == SettingsActivity.this.messagesSectionRow2) {
                        ((HeaderCell) viewHolder.itemView).setText(LocaleController.getString("MessagesSettings", R.string.MessagesSettings));
                        return;
                    } else if (i == SettingsActivity.this.numberSectionRow) {
                        ((HeaderCell) viewHolder.itemView).setText(LocaleController.getString("Info", R.string.Info));
                        return;
                    } else {
                        return;
                    }
                case 6:
                    TextDetailSettingsCell textDetailSettingsCell = (TextDetailSettingsCell) viewHolder.itemView;
                    User currentUser;
                    if (i == SettingsActivity.this.numberRow) {
                        currentUser = UserConfig.getCurrentUser();
                        string = (currentUser == null || currentUser.phone == null || currentUser.phone.length() == 0) ? LocaleController.getString("NumberUnknown", R.string.NumberUnknown) : C2488b.a().e("+" + currentUser.phone);
                        textDetailSettingsCell.setTextAndValue(string, LocaleController.getString("Phone", R.string.Phone), true);
                        return;
                    } else if (i == SettingsActivity.this.usernameRow) {
                        currentUser = UserConfig.getCurrentUser();
                        string = (currentUser == null || TextUtils.isEmpty(currentUser.username)) ? LocaleController.getString("UsernameEmpty", R.string.UsernameEmpty) : "@" + currentUser.username;
                        textDetailSettingsCell.setTextAndValue(string, LocaleController.getString("Username", R.string.Username), true);
                        return;
                    } else if (i == SettingsActivity.this.bioRow) {
                        TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(UserConfig.getClientUserId());
                        string = userFull == null ? LocaleController.getString("Loading", R.string.Loading) : !TextUtils.isEmpty(userFull.about) ? userFull.about : LocaleController.getString("UserBioEmpty", R.string.UserBioEmpty);
                        textDetailSettingsCell.setTextWithEmojiAndValue(string, LocaleController.getString("UserBio", R.string.UserBio), false);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new EmptyCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new ShadowSectionCell(this.mContext);
                    break;
                case 2:
                    view = new TextSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new TextCheckCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 5:
                    view = new TextInfoCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    try {
                        Object obj;
                        PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                        int i2 = packageInfo.versionCode / 10;
                        String str = TtmlNode.ANONYMOUS_REGION_ID;
                        String str2;
                        switch (packageInfo.versionCode % 10) {
                            case 0:
                                str2 = "arm";
                                break;
                            case 1:
                            case 3:
                                str2 = "arm-v7a";
                                break;
                            case 2:
                            case 4:
                                str2 = "x86";
                                break;
                            case 5:
                                str2 = "universal";
                                break;
                            default:
                                obj = str;
                                break;
                        }
                        TextInfoCell textInfoCell = (TextInfoCell) view;
                        Object[] objArr = new Object[1];
                        objArr[0] = String.format(Locale.US, "v%s (%d) %s", new Object[]{packageInfo.versionName, Integer.valueOf(i2), obj});
                        textInfoCell.setText(LocaleController.formatString("TelegramVersion", R.string.TelegramVersion, objArr));
                        break;
                    } catch (Throwable e) {
                        FileLog.e(e);
                        break;
                    }
                case 6:
                    view = new TextDetailSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    private void fixLayout() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (SettingsActivity.this.fragmentView != null) {
                        SettingsActivity.this.needLayout();
                        SettingsActivity.this.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return true;
                }
            });
        }
    }

    private void needLayout() {
        int i = 0;
        int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
        if (this.listView != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.listView.getLayoutParams();
            if (layoutParams.topMargin != currentActionBarHeight) {
                layoutParams.topMargin = currentActionBarHeight;
                this.listView.setLayoutParams(layoutParams);
                this.extraHeightView.setTranslationY((float) currentActionBarHeight);
            }
        }
        if (this.avatarImage != null) {
            float dp = ((float) this.extraHeight) / ((float) AndroidUtilities.dp(88.0f));
            this.extraHeightView.setScaleY(dp);
            this.shadowView.setTranslationY((float) (currentActionBarHeight + this.extraHeight));
            this.writeButton.setTranslationY((float) ((((this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight()) + this.extraHeight) - AndroidUtilities.dp(29.5f)));
            final boolean z = dp > 0.2f;
            if (z != (this.writeButton.getTag() == null)) {
                AnimatorSet animatorSet;
                if (z) {
                    this.writeButton.setTag(null);
                    this.writeButton.setVisibility(0);
                } else {
                    this.writeButton.setTag(Integer.valueOf(0));
                }
                if (this.writeButtonAnimation != null) {
                    animatorSet = this.writeButtonAnimation;
                    this.writeButtonAnimation = null;
                    animatorSet.cancel();
                }
                this.writeButtonAnimation = new AnimatorSet();
                Animator[] animatorArr;
                if (z) {
                    this.writeButtonAnimation.setInterpolator(new DecelerateInterpolator());
                    animatorSet = this.writeButtonAnimation;
                    animatorArr = new Animator[3];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{1.0f});
                    animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{1.0f});
                    animatorSet.playTogether(animatorArr);
                } else {
                    this.writeButtonAnimation.setInterpolator(new AccelerateInterpolator());
                    animatorSet = this.writeButtonAnimation;
                    animatorArr = new Animator[3];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{0.2f});
                    animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{0.2f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                }
                this.writeButtonAnimation.setDuration(150);
                this.writeButtonAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (SettingsActivity.this.writeButtonAnimation != null && SettingsActivity.this.writeButtonAnimation.equals(animator)) {
                            SettingsActivity.this.writeButton.setVisibility(z ? 0 : 8);
                            SettingsActivity.this.writeButtonAnimation = null;
                        }
                    }
                });
                this.writeButtonAnimation.start();
            }
            this.avatarImage.setScaleX(((18.0f * dp) + 42.0f) / 42.0f);
            this.avatarImage.setScaleY(((18.0f * dp) + 42.0f) / 42.0f);
            if (this.actionBar.getOccupyStatusBar()) {
                i = AndroidUtilities.statusBarHeight;
            }
            float currentActionBarHeight2 = ((((float) i) + ((((float) ActionBar.getCurrentActionBarHeight()) / 2.0f) * (1.0f + dp))) - (21.0f * AndroidUtilities.density)) + ((27.0f * AndroidUtilities.density) * dp);
            this.avatarImage.setTranslationX(((float) (-AndroidUtilities.dp(47.0f))) * dp);
            this.avatarImage.setTranslationY((float) Math.ceil((double) currentActionBarHeight2));
            this.nameTextView.setTranslationX((-21.0f * AndroidUtilities.density) * dp);
            this.nameTextView.setTranslationY((((float) Math.floor((double) currentActionBarHeight2)) - ((float) Math.ceil((double) AndroidUtilities.density))) + ((float) Math.floor((double) ((7.0f * AndroidUtilities.density) * dp))));
            this.onlineTextView.setTranslationX((-21.0f * AndroidUtilities.density) * dp);
            this.onlineTextView.setTranslationY((((float) Math.floor((double) currentActionBarHeight2)) + ((float) AndroidUtilities.dp(22.0f))) + (((float) Math.floor((double) (11.0f * AndroidUtilities.density))) * dp));
            this.nameTextView.setScaleX((0.12f * dp) + 1.0f);
            this.nameTextView.setScaleY((0.12f * dp) + 1.0f);
        }
    }

    private void performAskAQuestion() {
        User user;
        final SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        int i = sharedPreferences.getInt("support_id", 0);
        if (i != 0) {
            user = MessagesController.getInstance().getUser(Integer.valueOf(i));
            if (user == null) {
                String string = sharedPreferences.getString("support_user", null);
                if (string != null) {
                    try {
                        byte[] decode = Base64.decode(string, 0);
                        if (decode != null) {
                            AbstractSerializedData serializedData = new SerializedData(decode);
                            user = User.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                            if (user != null && user.id == 333000) {
                                user = null;
                            }
                            serializedData.cleanup();
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                        user = null;
                    }
                }
            }
        } else {
            user = null;
        }
        if (user == null) {
            final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 1);
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_help_getSupport(), new RequestDelegate() {

                /* renamed from: org.telegram.ui.SettingsActivity$12$2 */
                class C51892 implements Runnable {
                    C51892() {
                    }

                    public void run() {
                        try {
                            alertDialog.dismiss();
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        final TLRPC$TL_help_support tLRPC$TL_help_support = (TLRPC$TL_help_support) tLObject;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                Editor edit = sharedPreferences.edit();
                                edit.putInt("support_id", tLRPC$TL_help_support.user.id);
                                AbstractSerializedData serializedData = new SerializedData();
                                tLRPC$TL_help_support.user.serializeToStream(serializedData);
                                edit.putString("support_user", Base64.encodeToString(serializedData.toByteArray(), 0));
                                edit.commit();
                                serializedData.cleanup();
                                try {
                                    alertDialog.dismiss();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(tLRPC$TL_help_support.user);
                                MessagesStorage.getInstance().putUsersAndChats(arrayList, null, true, true);
                                MessagesController.getInstance().putUser(tLRPC$TL_help_support.user, false);
                                Bundle bundle = new Bundle();
                                bundle.putInt("user_id", tLRPC$TL_help_support.user.id);
                                SettingsActivity.this.presentFragment(new ChatActivity(bundle));
                            }
                        });
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new C51892());
                }
            });
            return;
        }
        MessagesController.getInstance().putUser(user, true);
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user.id);
        presentFragment(new ChatActivity(bundle));
    }

    private void sendLogs() {
        try {
            ArrayList arrayList = new ArrayList();
            for (File file : new File(ApplicationLoader.applicationContext.getExternalFilesDir(null).getAbsolutePath() + "/logs").listFiles()) {
                if (VERSION.SDK_INT >= 24) {
                    arrayList.add(FileProvider.a(getParentActivity(), "org.ir.talaeii.provider", file));
                } else {
                    arrayList.add(Uri.fromFile(file));
                }
            }
            if (!arrayList.isEmpty()) {
                Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                if (VERSION.SDK_INT >= 24) {
                    intent.addFlags(1);
                }
                intent.setType("message/rfc822");
                intent.putExtra("android.intent.extra.EMAIL", TtmlNode.ANONYMOUS_REGION_ID);
                intent.putExtra("android.intent.extra.SUBJECT", "last logs");
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
                getParentActivity().startActivityForResult(Intent.createChooser(intent, "Select email application."), ChatActivity.startAllServices);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserData() {
        TLObject tLObject;
        FileLocation fileLocation = null;
        boolean z = true;
        User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
        if (user.photo != null) {
            tLObject = user.photo.photo_small;
            fileLocation = user.photo.photo_big;
        } else {
            tLObject = null;
        }
        this.avatarDrawable = new AvatarDrawable(user, true);
        this.avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
        if (this.avatarImage != null) {
            this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable);
            this.avatarImage.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(fileLocation), false);
            this.nameTextView.setText(UserObject.getUserName(user));
            this.onlineTextView.setText(LocaleController.getString("Online", R.string.Online));
            ImageReceiver imageReceiver = this.avatarImage.getImageReceiver();
            if (PhotoViewer.getInstance().isShowingImage(fileLocation)) {
                z = false;
            }
            imageReceiver.setVisible(z, false);
        }
    }

    public View createView(Context context) {
        Drawable combinedDrawable;
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_avatar_actionBarSelectorBlue), false);
        this.actionBar.setItemsColor(Theme.getColor(Theme.key_avatar_actionBarIconBlue), false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAddToContainer(false);
        this.extraHeight = 88;
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setActionBarMenuOnItemClick(new C51953());
        ActionBarMenuItem addItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_other);
        addItem.addSubItem(1, LocaleController.getString("EditName", R.string.EditName));
        addItem.addSubItem(2, LocaleController.getString("LogOut", R.string.LogOut));
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view != SettingsActivity.this.listView) {
                    return super.drawChild(canvas, view, j);
                }
                boolean drawChild = super.drawChild(canvas, view, j);
                if (SettingsActivity.this.parentLayout != null) {
                    int childCount = getChildCount();
                    int i = 0;
                    while (i < childCount) {
                        View childAt = getChildAt(i);
                        if (childAt != view && (childAt instanceof ActionBar) && childAt.getVisibility() == 0) {
                            if (((ActionBar) childAt).getCastShadows()) {
                                i = childAt.getMeasuredHeight();
                                SettingsActivity.this.parentLayout.drawHeaderShadow(canvas, i);
                            }
                            i = 0;
                            SettingsActivity.this.parentLayout.drawHeaderShadow(canvas, i);
                        } else {
                            i++;
                        }
                    }
                    i = 0;
                    SettingsActivity.this.parentLayout.drawHeaderShadow(canvas, i);
                }
                return drawChild;
            }
        };
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager c51975 = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager = c51975;
        recyclerListView.setLayoutManager(c51975);
        this.listView.setGlowColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setOnItemClickListener(new C52046());
        this.listView.setOnItemLongClickListener(new C52067());
        frameLayout.addView(this.actionBar);
        this.extraHeightView = new View(context);
        this.extraHeightView.setPivotY(BitmapDescriptorFactory.HUE_RED);
        this.extraHeightView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
        frameLayout.addView(this.extraHeightView, LayoutHelper.createFrame(-1, 88.0f));
        this.shadowView = new View(context);
        this.shadowView.setBackgroundResource(R.drawable.header_shadow);
        frameLayout.addView(this.shadowView, LayoutHelper.createFrame(-1, 3.0f));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarImage.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.avatarImage.setPivotY(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(42, 42.0f, 51, 64.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.avatarImage.setOnClickListener(new C52078());
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_profile_title));
        this.nameTextView.setTextSize(1, 18.0f);
        this.nameTextView.setLines(1);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setGravity(3);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.nameTextView.setPivotY(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED));
        this.onlineTextView = new TextView(context);
        this.onlineTextView.setTextColor(Theme.getColor(Theme.key_avatar_subtitleInProfileBlue));
        this.onlineTextView.setTextSize(1, 14.0f);
        this.onlineTextView.setLines(1);
        this.onlineTextView.setMaxLines(1);
        this.onlineTextView.setSingleLine(true);
        this.onlineTextView.setEllipsize(TruncateAt.END);
        this.onlineTextView.setGravity(3);
        frameLayout.addView(this.onlineTextView, LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED));
        this.writeButton = new ImageView(context);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_profile_actionBackground), Theme.getColor(Theme.key_profile_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow_profile).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        } else {
            combinedDrawable = createSimpleSelectorCircleDrawable;
        }
        this.writeButton.setBackgroundDrawable(combinedDrawable);
        this.writeButton.setImageResource(R.drawable.floating_camera);
        this.writeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_actionIcon), Mode.MULTIPLY));
        this.writeButton.setScaleType(ScaleType.CENTER);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.writeButton.setStateListAnimator(stateListAnimator);
            this.writeButton.setOutlineProvider(new C52089());
        }
        frameLayout.addView(this.writeButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.writeButton.setOnClickListener(new View.OnClickListener() {

            /* renamed from: org.telegram.ui.SettingsActivity$10$1 */
            class C51871 implements OnClickListener {
                C51871() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        SettingsActivity.this.avatarUpdater.openCamera();
                    } else if (i == 1) {
                        SettingsActivity.this.avatarUpdater.openGallery();
                    } else if (i == 2) {
                        MessagesController.getInstance().deleteUserPhoto(null);
                    }
                }
            }

            public void onClick(View view) {
                if (SettingsActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(SettingsActivity.this.getParentActivity());
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId()));
                    if (user == null) {
                        user = UserConfig.getCurrentUser();
                    }
                    if (user != null) {
                        CharSequence[] charSequenceArr = (user.photo == null || user.photo.photo_big == null || (user.photo instanceof TLRPC$TL_userProfilePhotoEmpty)) ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)};
                        builder.setItems(charSequenceArr, new C51871());
                        SettingsActivity.this.showDialog(builder.create());
                    }
                }
            }
        });
        needLayout();
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int i3 = 0;
                if (SettingsActivity.this.layoutManager.getItemCount() != 0) {
                    View childAt = recyclerView.getChildAt(0);
                    if (childAt != null) {
                        if (SettingsActivity.this.layoutManager.findFirstVisibleItemPosition() == 0) {
                            int dp = AndroidUtilities.dp(88.0f);
                            if (childAt.getTop() < 0) {
                                i3 = childAt.getTop();
                            }
                            i3 += dp;
                        }
                        if (SettingsActivity.this.extraHeight != i3) {
                            SettingsActivity.this.extraHeight = i3;
                            SettingsActivity.this.needLayout();
                        }
                    }
                }
            }
        });
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if ((intValue & 2) != 0 || (intValue & 1) != 0) {
                updateUserData();
            }
        } else if (i == NotificationCenter.featuredStickersDidLoaded) {
            if (this.listAdapter != null) {
                this.listAdapter.notifyItemChanged(this.stickersRow);
            }
        } else if (i == NotificationCenter.userInfoDidLoaded) {
            if (((Integer) objArr[0]).intValue() == UserConfig.getClientUserId() && this.listAdapter != null) {
                this.listAdapter.notifyItemChanged(this.bioRow);
            }
        } else if (i == NotificationCenter.emojiDidLoaded && this.listView != null) {
            this.listView.invalidateViews();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[32];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{EmptyCell.class, TextSettingsCell.class, TextCheckCell.class, HeaderCell.class, TextInfoCell.class, TextDetailSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_avatar_backgroundActionBarBlue);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_avatar_backgroundActionBarBlue);
        themeDescriptionArr[4] = new ThemeDescription(this.extraHeightView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_avatar_backgroundActionBarBlue);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_avatar_actionBarIconBlue);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_avatar_actionBarSelectorBlue);
        themeDescriptionArr[8] = new ThemeDescription(this.nameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_profile_title);
        themeDescriptionArr[9] = new ThemeDescription(this.onlineTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_avatar_subtitleInProfileBlue);
        themeDescriptionArr[10] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        themeDescriptionArr[11] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        themeDescriptionArr[23] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[24] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[25] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText5);
        themeDescriptionArr[27] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[28] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileBlue);
        themeDescriptionArr[29] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_profile_actionIcon);
        themeDescriptionArr[30] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_profile_actionBackground);
        themeDescriptionArr[31] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_profile_actionPressedBackground);
        return themeDescriptionArr;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.avatarUpdater.onActivityResult(i, i2, intent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        fixLayout();
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.avatarUpdater.parentFragment = this;
        this.avatarUpdater.delegate = new C51932();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.userInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.overscrollRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.emptyRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.numberSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.numberRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.usernameRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.bioRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.settingsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.settingsSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.privacyRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.dataRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.backgroundRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.themeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.languageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.enableAnimationsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.customTabsRow = i;
        if (VERSION.SDK_INT >= 23) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.directShareRow = i;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.stickersRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.textSizeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.raiseToSpeakRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sendByEnterRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.autoplayGifsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.saveToGalleryRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.supportSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.supportSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.askQuestionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.telegramFaqRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.privacyPolicyRow = i;
        if (BuildVars.DEBUG_VERSION) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.sendLogsRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.clearLogsRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.dumpCallStatsRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.switchBackendButtonRow = i;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.versionRow = i;
        StickersQuery.checkFeaturedStickers();
        MessagesController.getInstance().loadFullUser(UserConfig.getCurrentUser(), this.classGuid, true);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.avatarImage != null) {
            this.avatarImage.setImageDrawable(null);
        }
        MessagesController.getInstance().cancelLoadFullUser(UserConfig.getClientUserId());
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.userInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        this.avatarUpdater.clear();
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        updateUserData();
        fixLayout();
    }

    public void restoreSelfArgs(Bundle bundle) {
        if (this.avatarUpdater != null) {
            this.avatarUpdater.currentPicturePath = bundle.getString("path");
        }
    }

    public void saveSelfArgs(Bundle bundle) {
        if (this.avatarUpdater != null && this.avatarUpdater.currentPicturePath != null) {
            bundle.putString("path", this.avatarUpdater.currentPicturePath);
        }
    }
}
