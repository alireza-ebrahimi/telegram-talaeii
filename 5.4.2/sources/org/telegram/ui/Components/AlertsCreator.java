package org.telegram.ui.Components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.MessagesStorage.IntCallback;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonPornography;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonSpam;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonViolence;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.TL_account_reportPeer;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.CacheControlActivity;
import org.telegram.ui.Cells.RadioColorCell;
import org.telegram.ui.Cells.TextColorCell;
import org.telegram.ui.Components.NumberPicker.Formatter;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.ReportOtherActivity;

public class AlertsCreator {

    public interface PaymentAlertDelegate {
        void didPressedNewCard();
    }

    public static Dialog createColorSelectDialog(Activity activity, final long j, boolean z, boolean z2, Runnable runnable) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        int i = z ? sharedPreferences.getInt("GroupLed", -16776961) : z2 ? sharedPreferences.getInt("MessagesLed", -16776961) : sharedPreferences.contains(new StringBuilder().append("color_").append(j).toString()) ? sharedPreferences.getInt("color_" + j, -16776961) : ((int) j) < 0 ? sharedPreferences.getInt("GroupLed", -16776961) : sharedPreferences.getInt("MessagesLed", -16776961);
        final View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        String[] strArr = new String[]{LocaleController.getString("ColorRed", R.string.ColorRed), LocaleController.getString("ColorOrange", R.string.ColorOrange), LocaleController.getString("ColorYellow", R.string.ColorYellow), LocaleController.getString("ColorGreen", R.string.ColorGreen), LocaleController.getString("ColorCyan", R.string.ColorCyan), LocaleController.getString("ColorBlue", R.string.ColorBlue), LocaleController.getString("ColorViolet", R.string.ColorViolet), LocaleController.getString("ColorPink", R.string.ColorPink), LocaleController.getString("ColorWhite", R.string.ColorWhite)};
        final int[] iArr = new int[]{i};
        for (int i2 = 0; i2 < 9; i2++) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setCheckColor(TextColorCell.colors[i2], TextColorCell.colors[i2]);
            radioColorCell.setTextAndValue(strArr[i2], i == TextColorCell.colorsToSave[i2]);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int childCount = linearLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view2 = (RadioColorCell) linearLayout.getChildAt(i);
                        view2.setChecked(view2 == view, true);
                    }
                    iArr[0] = TextColorCell.colorsToSave[((Integer) view.getTag()).intValue()];
                }
            });
        }
        Builder builder = new Builder(activity);
        builder.setTitle(LocaleController.getString("LedColor", R.string.LedColor));
        builder.setView(linearLayout);
        final boolean z3 = z2;
        final boolean z4 = z;
        final long j2 = j;
        final Runnable runnable2 = runnable;
        builder.setPositiveButton(LocaleController.getString("Set", R.string.Set), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (z3) {
                    edit.putInt("MessagesLed", iArr[0]);
                } else if (z4) {
                    edit.putInt("GroupLed", iArr[0]);
                } else {
                    edit.putInt("color_" + j2, iArr[0]);
                }
                edit.commit();
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        final boolean z5 = z2;
        z4 = z;
        j2 = j;
        runnable2 = runnable;
        builder.setNeutralButton(LocaleController.getString("LedDisabled", R.string.LedDisabled), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (z5) {
                    edit.putInt("MessagesLed", 0);
                } else if (z4) {
                    edit.putInt("GroupLed", 0);
                } else {
                    edit.putInt("color_" + j2, 0);
                }
                edit.commit();
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        if (!(z2 || z)) {
            final Runnable runnable3 = runnable;
            builder.setNegativeButton(LocaleController.getString("Default", R.string.Default), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    edit.remove("color_" + j);
                    edit.commit();
                    if (runnable3 != null) {
                        runnable3.run();
                    }
                }
            });
        }
        return builder.create();
    }

    public static Dialog createFreeSpaceDialog(final LaunchActivity launchActivity) {
        int i = 3;
        final int[] iArr = new int[1];
        int i2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("keep_media", 2);
        if (i2 == 2) {
            iArr[0] = 3;
        } else if (i2 == 0) {
            iArr[0] = 1;
        } else if (i2 == 1) {
            iArr[0] = 2;
        } else if (i2 == 3) {
            iArr[0] = 0;
        }
        String[] strArr = new String[]{LocaleController.formatPluralString("Days", 3), LocaleController.formatPluralString("Weeks", 1), LocaleController.formatPluralString("Months", 1), LocaleController.getString("LowDiskSpaceNeverRemove", R.string.LowDiskSpaceNeverRemove)};
        final View linearLayout = new LinearLayout(launchActivity);
        linearLayout.setOrientation(1);
        View textView = new TextView(launchActivity);
        textView.setText(LocaleController.getString("LowDiskSpaceTitle2", R.string.LowDiskSpaceTitle2));
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setTextSize(1, 16.0f);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        if (LocaleController.isRTL) {
            i = 5;
        }
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, i | 48, 24, 0, 24, 8));
        i2 = 0;
        while (i2 < strArr.length) {
            View radioColorCell = new RadioColorCell(launchActivity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    if (intValue == 0) {
                        iArr[0] = 3;
                    } else if (intValue == 1) {
                        iArr[0] = 0;
                    } else if (intValue == 2) {
                        iArr[0] = 1;
                    } else if (intValue == 3) {
                        iArr[0] = 2;
                    }
                    int childCount = linearLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = linearLayout.getChildAt(i);
                        if (childAt instanceof RadioColorCell) {
                            ((RadioColorCell) childAt).setChecked(childAt == view, true);
                        }
                    }
                }
            });
            i2++;
        }
        Builder builder = new Builder(launchActivity);
        builder.setTitle(LocaleController.getString("LowDiskSpaceTitle", R.string.LowDiskSpaceTitle));
        builder.setMessage(LocaleController.getString("LowDiskSpaceMessage", R.string.LowDiskSpaceMessage));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("keep_media", iArr[0]).commit();
            }
        });
        builder.setNeutralButton(LocaleController.getString("ClearMediaCache", R.string.ClearMediaCache), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                launchActivity.presentFragment(new CacheControlActivity());
            }
        });
        return builder.create();
    }

    public static Dialog createLocationUpdateDialog(Activity activity, User user, final IntCallback intCallback) {
        int i = 3;
        final int[] iArr = new int[1];
        String[] strArr = new String[]{LocaleController.getString("SendLiveLocationFor15m", R.string.SendLiveLocationFor15m), LocaleController.getString("SendLiveLocationFor1h", R.string.SendLiveLocationFor1h), LocaleController.getString("SendLiveLocationFor8h", R.string.SendLiveLocationFor8h)};
        final View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        View textView = new TextView(activity);
        if (user != null) {
            textView.setText(LocaleController.formatString("LiveLocationAlertPrivate", R.string.LiveLocationAlertPrivate, new Object[]{UserObject.getFirstName(user)}));
        } else {
            textView.setText(LocaleController.getString("LiveLocationAlertGroup", R.string.LiveLocationAlertGroup));
        }
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        if (LocaleController.isRTL) {
            i = 5;
        }
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, i | 48, 24, 0, 24, 8));
        int i2 = 0;
        while (i2 < strArr.length) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    iArr[0] = ((Integer) view.getTag()).intValue();
                    int childCount = linearLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = linearLayout.getChildAt(i);
                        if (childAt instanceof RadioColorCell) {
                            ((RadioColorCell) childAt).setChecked(childAt == view, true);
                        }
                    }
                }
            });
            i2++;
        }
        Builder builder = new Builder(activity);
        builder.setTopImage(new ShareLocationDrawable(activity, false), Theme.getColor(Theme.key_dialogTopBackground));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("ShareFile", R.string.ShareFile), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int i2 = iArr[0] == 0 ? 900 : iArr[0] == 1 ? 3600 : 28800;
                intCallback.run(i2);
            }
        });
        builder.setNeutralButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createMuteAlert(Context context, final long j) {
        if (context == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        builder.setTitle(LocaleController.getString("Notifications", R.string.Notifications));
        CharSequence[] charSequenceArr = new CharSequence[4];
        charSequenceArr[0] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 1)});
        charSequenceArr[1] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 8)});
        charSequenceArr[2] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Days", 2)});
        charSequenceArr[3] = LocaleController.getString("MuteDisable", R.string.MuteDisable);
        builder.setItems(charSequenceArr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                long j = 1;
                int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                int i2 = i == 0 ? currentTime + 3600 : i == 1 ? currentTime + 28800 : i == 2 ? currentTime + 172800 : i == 3 ? Integer.MAX_VALUE : currentTime;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (i == 3) {
                    edit.putInt("notify2_" + j, 2);
                } else {
                    edit.putInt("notify2_" + j, 3);
                    edit.putInt("notifyuntil_" + j, i2);
                    j = 1 | (((long) i2) << 32);
                }
                NotificationsController.getInstance().removeNotificationsForDialog(j);
                MessagesStorage.getInstance().setDialogFlags(j, j);
                edit.commit();
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(j));
                if (tLRPC$TL_dialog != null) {
                    tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                    tLRPC$TL_dialog.notify_settings.mute_until = i2;
                }
                NotificationsController.updateServerNotificationsSettings(j);
            }
        });
        return builder.create();
    }

    public static Dialog createPopupSelectDialog(Activity activity, final BaseFragment baseFragment, final boolean z, boolean z2, final Runnable runnable) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] iArr = new int[1];
        if (z2) {
            iArr[0] = sharedPreferences.getInt("popupAll", 0);
        } else if (z) {
            iArr[0] = sharedPreferences.getInt("popupGroup", 0);
        }
        String[] strArr = new String[]{LocaleController.getString("NoPopup", R.string.NoPopup), LocaleController.getString("OnlyWhenScreenOn", R.string.OnlyWhenScreenOn), LocaleController.getString("OnlyWhenScreenOff", R.string.OnlyWhenScreenOff), LocaleController.getString("AlwaysShowPopup", R.string.AlwaysShowPopup)};
        View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        int i = 0;
        while (i < strArr.length) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setTag(Integer.valueOf(i));
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i], iArr[0] == i);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    iArr[0] = ((Integer) view.getTag()).intValue();
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    edit.putInt(z ? "popupGroup" : "popupAll", iArr[0]);
                    edit.commit();
                    if (baseFragment != null) {
                        baseFragment.dismissCurrentDialig();
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            i++;
        }
        Builder builder = new Builder(activity);
        builder.setTitle(LocaleController.getString("PopupNotification", R.string.PopupNotification));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createPrioritySelectDialog(Activity activity, BaseFragment baseFragment, long j, boolean z, boolean z2, Runnable runnable) {
        String[] strArr;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] iArr = new int[1];
        if (j != 0) {
            iArr[0] = sharedPreferences.getInt("priority_" + j, 3);
            if (iArr[0] == 3) {
                iArr[0] = 0;
            } else {
                iArr[0] = iArr[0] + 1;
            }
            strArr = new String[]{LocaleController.getString("NotificationsPrioritySettings", R.string.NotificationsPrioritySettings), LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax)};
        } else {
            if (z2) {
                iArr[0] = sharedPreferences.getInt("priority_messages", 1);
            } else if (z) {
                iArr[0] = sharedPreferences.getInt("priority_group", 1);
            }
            strArr = new String[]{LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax)};
        }
        View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        int i = 0;
        while (i < strArr.length) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i], iArr[0] == i);
            linearLayout.addView(radioColorCell);
            final long j2 = j;
            final boolean z3 = z;
            final BaseFragment baseFragment2 = baseFragment;
            final Runnable runnable2 = runnable;
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    iArr[0] = ((Integer) view.getTag()).intValue();
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    if (j2 != 0) {
                        if (iArr[0] == 0) {
                            iArr[0] = 3;
                        } else {
                            int[] iArr = iArr;
                            iArr[0] = iArr[0] - 1;
                        }
                        edit.putInt("priority_" + j2, iArr[0]);
                    } else {
                        edit.putInt(z3 ? "priority_group" : "priority_messages", iArr[0]);
                    }
                    edit.commit();
                    if (baseFragment2 != null) {
                        baseFragment2.dismissCurrentDialig();
                    }
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            i++;
        }
        Builder builder = new Builder(activity);
        builder.setTitle(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createReportAlert(Context context, final long j, final BaseFragment baseFragment) {
        if (context == null || baseFragment == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        builder.setTitle(LocaleController.getString("ReportChat", R.string.ReportChat));
        builder.setItems(new CharSequence[]{LocaleController.getString("ReportChatSpam", R.string.ReportChatSpam), LocaleController.getString("ReportChatViolence", R.string.ReportChatViolence), LocaleController.getString("ReportChatPornography", R.string.ReportChatPornography), LocaleController.getString("ReportChatOther", R.string.ReportChatOther)}, new DialogInterface.OnClickListener() {

            /* renamed from: org.telegram.ui.Components.AlertsCreator$2$1 */
            class C43101 implements RequestDelegate {
                C43101() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 3) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("dialog_id", j);
                    baseFragment.presentFragment(new ReportOtherActivity(bundle));
                    return;
                }
                TLObject tL_account_reportPeer = new TL_account_reportPeer();
                tL_account_reportPeer.peer = MessagesController.getInputPeer((int) j);
                if (i == 0) {
                    tL_account_reportPeer.reason = new TLRPC$TL_inputReportReasonSpam();
                } else if (i == 1) {
                    tL_account_reportPeer.reason = new TLRPC$TL_inputReportReasonViolence();
                } else if (i == 2) {
                    tL_account_reportPeer.reason = new TLRPC$TL_inputReportReasonPornography();
                }
                ConnectionsManager.getInstance().sendRequest(tL_account_reportPeer, new C43101());
            }
        });
        return builder.create();
    }

    public static Dialog createSingleChoiceDialog(Activity activity, final BaseFragment baseFragment, String[] strArr, String str, int i, final DialogInterface.OnClickListener onClickListener) {
        View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        int i2 = 0;
        while (i2 < strArr.length) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i2], i == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    if (baseFragment != null) {
                        baseFragment.dismissCurrentDialig();
                    }
                    onClickListener.onClick(null, intValue);
                }
            });
            i2++;
        }
        Builder builder = new Builder(activity);
        builder.setTitle(str);
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Builder createTTLAlert(Context context, final EncryptedChat encryptedChat) {
        Builder builder = new Builder(context);
        builder.setTitle(LocaleController.getString("MessageLifetime", R.string.MessageLifetime));
        final View numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(20);
        if (encryptedChat.ttl > 0 && encryptedChat.ttl < 16) {
            numberPicker.setValue(encryptedChat.ttl);
        } else if (encryptedChat.ttl == 30) {
            numberPicker.setValue(16);
        } else if (encryptedChat.ttl == 60) {
            numberPicker.setValue(17);
        } else if (encryptedChat.ttl == 3600) {
            numberPicker.setValue(18);
        } else if (encryptedChat.ttl == 86400) {
            numberPicker.setValue(19);
        } else if (encryptedChat.ttl == 604800) {
            numberPicker.setValue(20);
        } else if (encryptedChat.ttl == 0) {
            numberPicker.setValue(0);
        }
        numberPicker.setFormatter(new Formatter() {
            public String format(int i) {
                return i == 0 ? LocaleController.getString("ShortMessageLifetimeForever", R.string.ShortMessageLifetimeForever) : (i < 1 || i >= 16) ? i == 16 ? LocaleController.formatTTLString(30) : i == 17 ? LocaleController.formatTTLString(60) : i == 18 ? LocaleController.formatTTLString(3600) : i == 19 ? LocaleController.formatTTLString(86400) : i == 20 ? LocaleController.formatTTLString(604800) : TtmlNode.ANONYMOUS_REGION_ID : LocaleController.formatTTLString(i);
            }
        });
        builder.setView(numberPicker);
        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int i2 = encryptedChat.ttl;
                int value = numberPicker.getValue();
                if (value >= 0 && value < 16) {
                    encryptedChat.ttl = value;
                } else if (value == 16) {
                    encryptedChat.ttl = 30;
                } else if (value == 17) {
                    encryptedChat.ttl = 60;
                } else if (value == 18) {
                    encryptedChat.ttl = 3600;
                } else if (value == 19) {
                    encryptedChat.ttl = 86400;
                } else if (value == 20) {
                    encryptedChat.ttl = 604800;
                }
                if (i2 != encryptedChat.ttl) {
                    SecretChatHelper.getInstance().sendTTLMessage(encryptedChat, null);
                    MessagesStorage.getInstance().updateEncryptedChatTTL(encryptedChat);
                }
            }
        });
        return builder;
    }

    public static Dialog createVibrationSelectDialog(Activity activity, BaseFragment baseFragment, long j, String str, Runnable runnable) {
        String[] strArr;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] iArr = new int[1];
        if (j != 0) {
            iArr[0] = sharedPreferences.getInt(str + j, 0);
            if (iArr[0] == 3) {
                iArr[0] = 2;
            } else if (iArr[0] == 2) {
                iArr[0] = 3;
            }
            strArr = new String[]{LocaleController.getString("VibrationDefault", R.string.VibrationDefault), LocaleController.getString("Short", R.string.Short), LocaleController.getString("Long", R.string.Long), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled)};
        } else {
            iArr[0] = sharedPreferences.getInt(str, 0);
            if (iArr[0] == 0) {
                iArr[0] = 1;
            } else if (iArr[0] == 1) {
                iArr[0] = 2;
            } else if (iArr[0] == 2) {
                iArr[0] = 0;
            }
            strArr = new String[]{LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), LocaleController.getString("Short", R.string.Short), LocaleController.getString("Long", R.string.Long), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent)};
        }
        View linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        int i = 0;
        while (i < strArr.length) {
            View radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i], iArr[0] == i);
            linearLayout.addView(radioColorCell);
            final long j2 = j;
            final String str2 = str;
            final BaseFragment baseFragment2 = baseFragment;
            final Runnable runnable2 = runnable;
            radioColorCell.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    iArr[0] = ((Integer) view.getTag()).intValue();
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    if (j2 != 0) {
                        if (iArr[0] == 0) {
                            edit.putInt(str2 + j2, 0);
                        } else if (iArr[0] == 1) {
                            edit.putInt(str2 + j2, 1);
                        } else if (iArr[0] == 2) {
                            edit.putInt(str2 + j2, 3);
                        } else if (iArr[0] == 3) {
                            edit.putInt(str2 + j2, 2);
                        }
                    } else if (iArr[0] == 0) {
                        edit.putInt(str2, 2);
                    } else if (iArr[0] == 1) {
                        edit.putInt(str2, 0);
                    } else if (iArr[0] == 2) {
                        edit.putInt(str2, 1);
                    } else if (iArr[0] == 3) {
                        edit.putInt(str2, 3);
                    } else if (iArr[0] == 4) {
                        edit.putInt(str2, 4);
                    }
                    edit.commit();
                    if (baseFragment2 != null) {
                        baseFragment2.dismissCurrentDialig();
                    }
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            i++;
        }
        Builder builder = new Builder(activity);
        builder.setTitle(LocaleController.getString("Vibrate", R.string.Vibrate));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createVibrationSelectDialog(Activity activity, BaseFragment baseFragment, long j, boolean z, boolean z2, Runnable runnable) {
        String str;
        if (j != 0) {
            str = "vibrate_";
        } else {
            str = z ? "vibrate_group" : "vibrate_messages";
        }
        return createVibrationSelectDialog(activity, baseFragment, j, str, runnable);
    }

    private static String getFloodWaitString(String str) {
        int intValue = Utilities.parseInt(str).intValue();
        String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
        return LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.app.Dialog processError(org.telegram.tgnet.TLRPC$TL_error r7, org.telegram.ui.ActionBar.BaseFragment r8, org.telegram.tgnet.TLObject r9, java.lang.Object... r10) {
        /*
        r6 = 2131231178; // 0x7f0801ca float:1.807843E38 double:1.0529681084E-314;
        r5 = 2131231397; // 0x7f0802a5 float:1.8078874E38 double:1.0529682166E-314;
        r2 = 1;
        r4 = 2131231487; // 0x7f0802ff float:1.8079056E38 double:1.052968261E-314;
        r1 = 0;
        r0 = r7.code;
        r3 = 406; // 0x196 float:5.69E-43 double:2.006E-321;
        if (r0 == r3) goto L_0x0015;
    L_0x0011:
        r0 = r7.text;
        if (r0 != 0) goto L_0x0017;
    L_0x0015:
        r0 = 0;
    L_0x0016:
        return r0;
    L_0x0017:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_channels_joinChannel;
        if (r0 != 0) goto L_0x002f;
    L_0x001b:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_channels_editAdmin;
        if (r0 != 0) goto L_0x002f;
    L_0x001f:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_channels_inviteToChannel;
        if (r0 != 0) goto L_0x002f;
    L_0x0023:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_addChatUser;
        if (r0 != 0) goto L_0x002f;
    L_0x0027:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_startBot;
        if (r0 != 0) goto L_0x002f;
    L_0x002b:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_channels_editBanned;
        if (r0 == 0) goto L_0x005d;
    L_0x002f:
        if (r8 == 0) goto L_0x0040;
    L_0x0031:
        r2 = r7.text;
        r0 = r10[r1];
        r0 = (java.lang.Boolean) r0;
        r0 = r0.booleanValue();
        showAddUserAlert(r2, r8, r0);
    L_0x003e:
        r0 = 0;
        goto L_0x0016;
    L_0x0040:
        r0 = r7.text;
        r3 = "PEER_FLOOD";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x003e;
    L_0x004b:
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r3 = org.telegram.messenger.NotificationCenter.needShowAlert;
        r4 = new java.lang.Object[r2];
        r2 = java.lang.Integer.valueOf(r2);
        r4[r1] = r2;
        r0.postNotificationName(r3, r4);
        goto L_0x003e;
    L_0x005d:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_createChat;
        if (r0 == 0) goto L_0x0078;
    L_0x0061:
        r0 = r7.text;
        r2 = "FLOOD_WAIT";
        r0 = r0.startsWith(r2);
        if (r0 == 0) goto L_0x0072;
    L_0x006c:
        r0 = r7.text;
        showFloodWaitAlert(r0, r8);
        goto L_0x003e;
    L_0x0072:
        r0 = r7.text;
        showAddUserAlert(r0, r8, r1);
        goto L_0x003e;
    L_0x0078:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_channels_createChannel;
        if (r0 == 0) goto L_0x008d;
    L_0x007c:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x003e;
    L_0x0087:
        r0 = r7.text;
        showFloodWaitAlert(r0, r8);
        goto L_0x003e;
    L_0x008d:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_editMessage;
        if (r0 == 0) goto L_0x00aa;
    L_0x0091:
        r0 = r7.text;
        r1 = "MESSAGE_NOT_MODIFIED";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x003e;
    L_0x009c:
        r0 = "EditMessageError";
        r1 = 2131231347; // 0x7f080273 float:1.8078772E38 double:1.052968192E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x00aa:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_sendMessage;
        if (r0 != 0) goto L_0x00c6;
    L_0x00ae:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_sendMedia;
        if (r0 != 0) goto L_0x00c6;
    L_0x00b2:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_geochats_sendMessage;
        if (r0 != 0) goto L_0x00c6;
    L_0x00b6:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_sendBroadcast;
        if (r0 != 0) goto L_0x00c6;
    L_0x00ba:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_sendInlineBotResult;
        if (r0 != 0) goto L_0x00c6;
    L_0x00be:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_geochats_sendMedia;
        if (r0 != 0) goto L_0x00c6;
    L_0x00c2:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_forwardMessages;
        if (r0 == 0) goto L_0x00e4;
    L_0x00c6:
        r0 = r7.text;
        r3 = "PEER_FLOOD";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x003e;
    L_0x00d1:
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r3 = org.telegram.messenger.NotificationCenter.needShowAlert;
        r2 = new java.lang.Object[r2];
        r4 = java.lang.Integer.valueOf(r1);
        r2[r1] = r4;
        r0.postNotificationName(r3, r2);
        goto L_0x003e;
    L_0x00e4:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_importChatInvite;
        if (r0 == 0) goto L_0x0128;
    L_0x00e8:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x00ff;
    L_0x00f3:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x00ff:
        r0 = r7.text;
        r1 = "USERS_TOO_MUCH";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0119;
    L_0x010a:
        r0 = "JoinToGroupErrorFull";
        r1 = 2131231683; // 0x7f0803c3 float:1.8079454E38 double:1.052968358E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0119:
        r0 = "JoinToGroupErrorNotExist";
        r1 = 2131231684; // 0x7f0803c4 float:1.8079456E38 double:1.0529683584E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0128:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_getAttachedStickers;
        if (r0 == 0) goto L_0x0162;
    L_0x012c:
        if (r8 == 0) goto L_0x003e;
    L_0x012e:
        r0 = r8.getParentActivity();
        if (r0 == 0) goto L_0x003e;
    L_0x0134:
        r0 = r8.getParentActivity();
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "ErrorOccurred";
        r3 = org.telegram.messenger.LocaleController.getString(r3, r5);
        r2 = r2.append(r3);
        r3 = "\n";
        r2 = r2.append(r3);
        r3 = r7.text;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r0 = android.widget.Toast.makeText(r0, r2, r1);
        r0.show();
        goto L_0x003e;
    L_0x0162:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_confirmPhone;
        if (r0 == 0) goto L_0x01c0;
    L_0x0166:
        r0 = r7.text;
        r1 = "PHONE_CODE_EMPTY";
        r0 = r0.contains(r1);
        if (r0 != 0) goto L_0x017c;
    L_0x0171:
        r0 = r7.text;
        r1 = "PHONE_CODE_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x018b;
    L_0x017c:
        r0 = "InvalidCode";
        r1 = 2131231657; // 0x7f0803a9 float:1.8079401E38 double:1.052968345E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x018b:
        r0 = r7.text;
        r1 = "PHONE_CODE_EXPIRED";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x01a2;
    L_0x0196:
        r0 = "CodeExpired";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r6);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x01a2:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x01b9;
    L_0x01ad:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x01b9:
        r0 = r7.text;
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x01c0:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_auth_resendCode;
        if (r0 == 0) goto L_0x025d;
    L_0x01c4:
        r0 = r7.text;
        r1 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x01de;
    L_0x01cf:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231661; // 0x7f0803ad float:1.807941E38 double:1.052968347E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x01de:
        r0 = r7.text;
        r1 = "PHONE_CODE_EMPTY";
        r0 = r0.contains(r1);
        if (r0 != 0) goto L_0x01f4;
    L_0x01e9:
        r0 = r7.text;
        r1 = "PHONE_CODE_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x0203;
    L_0x01f4:
        r0 = "InvalidCode";
        r1 = 2131231657; // 0x7f0803a9 float:1.8079401E38 double:1.052968345E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0203:
        r0 = r7.text;
        r1 = "PHONE_CODE_EXPIRED";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x021a;
    L_0x020e:
        r0 = "CodeExpired";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r6);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x021a:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0231;
    L_0x0225:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0231:
        r0 = r7.code;
        r1 = -1000; // 0xfffffffffffffc18 float:NaN double:NaN;
        if (r0 == r1) goto L_0x003e;
    L_0x0237:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "ErrorOccurred";
        r1 = org.telegram.messenger.LocaleController.getString(r1, r5);
        r0 = r0.append(r1);
        r1 = "\n";
        r0 = r0.append(r1);
        r1 = r7.text;
        r0 = r0.append(r1);
        r0 = r0.toString();
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x025d:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_sendConfirmPhoneCode;
        if (r0 == 0) goto L_0x02a0;
    L_0x0261:
        r0 = r7.code;
        r1 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r0 != r1) goto L_0x0277;
    L_0x0267:
        r0 = "CancelLinkExpired";
        r1 = 2131231025; // 0x7f080131 float:1.807812E38 double:1.052968033E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        r0 = showSimpleAlert(r8, r0);
        goto L_0x0016;
    L_0x0277:
        r0 = r7.text;
        if (r0 == 0) goto L_0x003e;
    L_0x027b:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0293;
    L_0x0286:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        r0 = showSimpleAlert(r8, r0);
        goto L_0x0016;
    L_0x0293:
        r0 = "ErrorOccurred";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r5);
        r0 = showSimpleAlert(r8, r0);
        goto L_0x0016;
    L_0x02a0:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_changePhone;
        if (r0 == 0) goto L_0x0318;
    L_0x02a4:
        r0 = r7.text;
        r1 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x02be;
    L_0x02af:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231661; // 0x7f0803ad float:1.807941E38 double:1.052968347E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x02be:
        r0 = r7.text;
        r1 = "PHONE_CODE_EMPTY";
        r0 = r0.contains(r1);
        if (r0 != 0) goto L_0x02d4;
    L_0x02c9:
        r0 = r7.text;
        r1 = "PHONE_CODE_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x02e3;
    L_0x02d4:
        r0 = "InvalidCode";
        r1 = 2131231657; // 0x7f0803a9 float:1.8079401E38 double:1.052968345E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x02e3:
        r0 = r7.text;
        r1 = "PHONE_CODE_EXPIRED";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x02fa;
    L_0x02ee:
        r0 = "CodeExpired";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r6);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x02fa:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0311;
    L_0x0305:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0311:
        r0 = r7.text;
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0318:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_sendChangePhoneCode;
        if (r0 == 0) goto L_0x03b7;
    L_0x031c:
        r0 = r7.text;
        r3 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r3);
        if (r0 == 0) goto L_0x0336;
    L_0x0327:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231661; // 0x7f0803ad float:1.807941E38 double:1.052968347E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0336:
        r0 = r7.text;
        r3 = "PHONE_CODE_EMPTY";
        r0 = r0.contains(r3);
        if (r0 != 0) goto L_0x034c;
    L_0x0341:
        r0 = r7.text;
        r3 = "PHONE_CODE_INVALID";
        r0 = r0.contains(r3);
        if (r0 == 0) goto L_0x035b;
    L_0x034c:
        r0 = "InvalidCode";
        r1 = 2131231657; // 0x7f0803a9 float:1.8079401E38 double:1.052968345E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x035b:
        r0 = r7.text;
        r3 = "PHONE_CODE_EXPIRED";
        r0 = r0.contains(r3);
        if (r0 == 0) goto L_0x0372;
    L_0x0366:
        r0 = "CodeExpired";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r6);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0372:
        r0 = r7.text;
        r3 = "FLOOD_WAIT";
        r0 = r0.startsWith(r3);
        if (r0 == 0) goto L_0x0389;
    L_0x037d:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0389:
        r0 = r7.text;
        r3 = "PHONE_NUMBER_OCCUPIED";
        r0 = r0.startsWith(r3);
        if (r0 == 0) goto L_0x03ab;
    L_0x0394:
        r3 = "ChangePhoneNumberOccupied";
        r4 = 2131231038; // 0x7f08013e float:1.8078146E38 double:1.052968039E-314;
        r2 = new java.lang.Object[r2];
        r0 = r10[r1];
        r0 = (java.lang.String) r0;
        r2[r1] = r0;
        r0 = org.telegram.messenger.LocaleController.formatString(r3, r4, r2);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x03ab:
        r0 = "ErrorOccurred";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r5);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x03b7:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_updateUserName;
        if (r0 == 0) goto L_0x0408;
    L_0x03bb:
        r3 = r7.text;
        r0 = -1;
        r4 = r3.hashCode();
        switch(r4) {
            case 288843630: goto L_0x03d5;
            case 533175271: goto L_0x03df;
            default: goto L_0x03c5;
        };
    L_0x03c5:
        r1 = r0;
    L_0x03c6:
        switch(r1) {
            case 0: goto L_0x03ea;
            case 1: goto L_0x03f9;
            default: goto L_0x03c9;
        };
    L_0x03c9:
        r0 = "ErrorOccurred";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r5);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x03d5:
        r2 = "USERNAME_INVALID";
        r2 = r3.equals(r2);
        if (r2 == 0) goto L_0x03c5;
    L_0x03de:
        goto L_0x03c6;
    L_0x03df:
        r1 = "USERNAME_OCCUPIED";
        r1 = r3.equals(r1);
        if (r1 == 0) goto L_0x03c5;
    L_0x03e8:
        r1 = r2;
        goto L_0x03c6;
    L_0x03ea:
        r0 = "UsernameInvalid";
        r1 = 2131232579; // 0x7f080743 float:1.8081271E38 double:1.0529688006E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x03f9:
        r0 = "UsernameInUse";
        r1 = 2131232578; // 0x7f080742 float:1.808127E38 double:1.0529688E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0408:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_contacts_importContacts;
        if (r0 == 0) goto L_0x044b;
    L_0x040c:
        if (r7 == 0) goto L_0x0419;
    L_0x040e:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0425;
    L_0x0419:
        r0 = "FloodWait";
        r0 = org.telegram.messenger.LocaleController.getString(r0, r4);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0425:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "ErrorOccurred";
        r1 = org.telegram.messenger.LocaleController.getString(r1, r5);
        r0 = r0.append(r1);
        r1 = "\n";
        r0 = r0.append(r1);
        r1 = r7.text;
        r0 = r0.append(r1);
        r0 = r0.toString();
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x044b:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_getPassword;
        if (r0 != 0) goto L_0x0453;
    L_0x044f:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC.TL_account_getTmpPassword;
        if (r0 == 0) goto L_0x0470;
    L_0x0453:
        r0 = r7.text;
        r1 = "FLOOD_WAIT";
        r0 = r0.startsWith(r1);
        if (r0 == 0) goto L_0x0469;
    L_0x045e:
        r0 = r7.text;
        r0 = getFloodWaitString(r0);
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x0469:
        r0 = r7.text;
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x0470:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_payments_sendPaymentForm;
        if (r0 == 0) goto L_0x04bc;
    L_0x0474:
        r3 = r7.text;
        r0 = -1;
        r4 = r3.hashCode();
        switch(r4) {
            case -1144062453: goto L_0x0489;
            case -784238410: goto L_0x0493;
            default: goto L_0x047e;
        };
    L_0x047e:
        r1 = r0;
    L_0x047f:
        switch(r1) {
            case 0: goto L_0x049e;
            case 1: goto L_0x04ad;
            default: goto L_0x0482;
        };
    L_0x0482:
        r0 = r7.text;
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x0489:
        r2 = "BOT_PRECHECKOUT_FAILED";
        r2 = r3.equals(r2);
        if (r2 == 0) goto L_0x047e;
    L_0x0492:
        goto L_0x047f;
    L_0x0493:
        r1 = "PAYMENT_FAILED";
        r1 = r3.equals(r1);
        if (r1 == 0) goto L_0x047e;
    L_0x049c:
        r1 = r2;
        goto L_0x047f;
    L_0x049e:
        r0 = "PaymentPrecheckoutFailed";
        r1 = 2131232106; // 0x7f08056a float:1.8080312E38 double:1.052968567E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x04ad:
        r0 = "PaymentFailed";
        r1 = 2131232103; // 0x7f080567 float:1.8080306E38 double:1.0529685654E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x04bc:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_payments_validateRequestedInfo;
        if (r0 == 0) goto L_0x003e;
    L_0x04c0:
        r2 = r7.text;
        r0 = -1;
        r3 = r2.hashCode();
        switch(r3) {
            case 1758025548: goto L_0x04d4;
            default: goto L_0x04ca;
        };
    L_0x04ca:
        switch(r0) {
            case 0: goto L_0x04df;
            default: goto L_0x04cd;
        };
    L_0x04cd:
        r0 = r7.text;
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x04d4:
        r3 = "SHIPPING_NOT_AVAILABLE";
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x04ca;
    L_0x04dd:
        r0 = r1;
        goto L_0x04ca;
    L_0x04df:
        r0 = "PaymentNoShippingMethod";
        r1 = 2131232105; // 0x7f080569 float:1.808031E38 double:1.0529685664E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleToast(r8, r0);
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.AlertsCreator.processError(org.telegram.tgnet.TLRPC$TL_error, org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLObject, java.lang.Object[]):android.app.Dialog");
    }

    public static void showAddUserAlert(String str, final BaseFragment baseFragment, boolean z) {
        if (str != null && baseFragment != null && baseFragment.getParentActivity() != null) {
            Builder builder = new Builder(baseFragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            boolean z2 = true;
            switch (str.hashCode()) {
                case -1763467626:
                    if (str.equals("USERS_TOO_FEW")) {
                        z2 = true;
                        break;
                    }
                    break;
                case -538116776:
                    if (str.equals("USER_BLOCKED")) {
                        z2 = true;
                        break;
                    }
                    break;
                case -512775857:
                    if (str.equals("USER_RESTRICTED")) {
                        z2 = true;
                        break;
                    }
                    break;
                case -454039871:
                    if (str.equals("PEER_FLOOD")) {
                        z2 = false;
                        break;
                    }
                    break;
                case -420079733:
                    if (str.equals("BOTS_TOO_MUCH")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 98635865:
                    if (str.equals("USER_KICKED")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 517420851:
                    if (str.equals("USER_BOT")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 845559454:
                    if (str.equals("YOU_BLOCKED_USER")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 916342611:
                    if (str.equals("USER_ADMIN_INVALID")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1047173446:
                    if (str.equals("CHAT_ADMIN_BAN_REQUIRED")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1167301807:
                    if (str.equals("USERS_TOO_MUCH")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1227003815:
                    if (str.equals("USER_ID_INVALID")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1253103379:
                    if (str.equals("ADMINS_TOO_MUCH")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1623167701:
                    if (str.equals("USER_NOT_MUTUAL_CONTACT")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1754587486:
                    if (str.equals("CHAT_ADMIN_INVITE_REQUIRED")) {
                        z2 = true;
                        break;
                    }
                    break;
                case 1916725894:
                    if (str.equals("USER_PRIVACY_RESTRICTED")) {
                        z2 = true;
                        break;
                    }
                    break;
            }
            switch (z2) {
                case false:
                    builder.setMessage(LocaleController.getString("NobodyLikesSpam2", R.string.NobodyLikesSpam2));
                    builder.setNegativeButton(LocaleController.getString("MoreInfo", R.string.MoreInfo), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MessagesController.openByUserName("spambot", baseFragment, 1);
                        }
                    });
                    break;
                case true:
                case true:
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("GroupUserCantAdd", R.string.GroupUserCantAdd));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantAdd", R.string.ChannelUserCantAdd));
                        break;
                    }
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("GroupUserAddLimit", R.string.GroupUserAddLimit));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserAddLimit", R.string.ChannelUserAddLimit));
                        break;
                    }
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("GroupUserLeftError", R.string.GroupUserLeftError));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserLeftError", R.string.ChannelUserLeftError));
                        break;
                    }
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("GroupUserCantAdmin", R.string.GroupUserCantAdmin));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantAdmin", R.string.ChannelUserCantAdmin));
                        break;
                    }
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("GroupUserCantBot", R.string.GroupUserCantBot));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantBot", R.string.ChannelUserCantBot));
                        break;
                    }
                case true:
                    if (!z) {
                        builder.setMessage(LocaleController.getString("InviteToGroupError", R.string.InviteToGroupError));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("InviteToChannelError", R.string.InviteToChannelError));
                        break;
                    }
                case true:
                    builder.setMessage(LocaleController.getString("CreateGroupError", R.string.CreateGroupError));
                    break;
                case true:
                    builder.setMessage(LocaleController.getString("UserRestricted", R.string.UserRestricted));
                    break;
                case true:
                    builder.setMessage(LocaleController.getString("YouBlockedUser", R.string.YouBlockedUser));
                    break;
                case true:
                case true:
                    builder.setMessage(LocaleController.getString("AddAdminErrorBlacklisted", R.string.AddAdminErrorBlacklisted));
                    break;
                case true:
                    builder.setMessage(LocaleController.getString("AddAdminErrorNotAMember", R.string.AddAdminErrorNotAMember));
                    break;
                case true:
                    builder.setMessage(LocaleController.getString("AddBannedErrorAdmin", R.string.AddBannedErrorAdmin));
                    break;
                default:
                    builder.setMessage(LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + str);
                    break;
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            baseFragment.showDialog(builder.create(), true, null);
        }
    }

    public static void showFloodWaitAlert(String str, BaseFragment baseFragment) {
        if (str != null && str.startsWith("FLOOD_WAIT") && baseFragment != null && baseFragment.getParentActivity() != null) {
            int intValue = Utilities.parseInt(str).intValue();
            String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
            Builder builder = new Builder(baseFragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            baseFragment.showDialog(builder.create(), true, null);
        }
    }

    public static void showSendMediaAlert(int i, BaseFragment baseFragment) {
        if (i != 0) {
            Builder builder = new Builder(baseFragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (i == 1) {
                builder.setMessage(LocaleController.getString("ErrorSendRestrictedStickers", R.string.ErrorSendRestrictedStickers));
            } else if (i == 2) {
                builder.setMessage(LocaleController.getString("ErrorSendRestrictedMedia", R.string.ErrorSendRestrictedMedia));
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            baseFragment.showDialog(builder.create(), true, null);
        }
    }

    public static Dialog showSimpleAlert(BaseFragment baseFragment, String str) {
        if (str == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        Builder builder = new Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setMessage(str);
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        Dialog create = builder.create();
        baseFragment.showDialog(create);
        return create;
    }

    public static Toast showSimpleToast(BaseFragment baseFragment, String str) {
        if (str == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        Toast makeText = Toast.makeText(baseFragment.getParentActivity(), str, 1);
        makeText.show();
        return makeText;
    }
}
