package org.telegram.ui.Components.voip;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.Collections;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$TL_phone_setCallRating;
import org.telegram.tgnet.TLRPC$TL_updates;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Components.BetterRatingView;
import org.telegram.ui.Components.BetterRatingView.OnRatingChangeListener;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.VoIPActivity;

public class VoIPHelper {
    private static final int VOIP_SUPPORT_ID = 4244000;
    public static long lastCallTime = 0;

    public static boolean canRateCall(TLRPC$TL_messageActionPhoneCall tLRPC$TL_messageActionPhoneCall) {
        if (!((tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy) || (tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed))) {
            for (String split : ApplicationLoader.applicationContext.getSharedPreferences("notifications", 0).getStringSet("calls_access_hashes", Collections.EMPTY_SET)) {
                String[] split2 = split.split(" ");
                if (split2.length >= 2 && split2[0].equals(tLRPC$TL_messageActionPhoneCall.call_id + TtmlNode.ANONYMOUS_REGION_ID)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void doInitiateCall(User user, Activity activity) {
        if (activity != null && user != null && System.currentTimeMillis() - lastCallTime >= 2000) {
            lastCallTime = System.currentTimeMillis();
            Intent intent = new Intent(activity, VoIPService.class);
            intent.putExtra("user_id", user.id);
            intent.putExtra("is_outgoing", true);
            intent.putExtra("start_incall_activity", true);
            activity.startService(intent);
        }
    }

    public static File getLogsDir() {
        File file = new File(ApplicationLoader.applicationContext.getCacheDir(), "voip_logs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static void initiateCall(final User user, final Activity activity) {
        if (activity != null && user != null) {
            if (VoIPService.getSharedInstance() != null) {
                if (VoIPService.getSharedInstance().getUser().id != user.id) {
                    new Builder(activity).setTitle(LocaleController.getString("VoipOngoingAlertTitle", R.string.VoipOngoingAlertTitle)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("VoipOngoingAlert", R.string.VoipOngoingAlert, new Object[]{ContactsController.formatName(r0.first_name, r0.last_name), ContactsController.formatName(user.first_name, user.last_name)}))).setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {

                        /* renamed from: org.telegram.ui.Components.voip.VoIPHelper$2$1 */
                        class C46621 implements Runnable {
                            C46621() {
                            }

                            public void run() {
                                VoIPHelper.doInitiateCall(user, activity);
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (VoIPService.getSharedInstance() != null) {
                                VoIPService.getSharedInstance().hangUp(new C46621());
                            } else {
                                VoIPHelper.doInitiateCall(user, activity);
                            }
                        }
                    }).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).show();
                    return;
                }
                activity.startActivity(new Intent(activity, VoIPActivity.class).addFlags(ErrorDialogData.BINDER_CRASH));
            } else if (VoIPService.callIShouldHavePutIntoIntent == null) {
                doInitiateCall(user, activity);
            }
        }
    }

    @TargetApi(23)
    public static void permissionDenied(final Activity activity, final Runnable runnable) {
        if (!activity.shouldShowRequestPermissionRationale("android.permission.RECORD_AUDIO")) {
            new Builder(activity).setTitle(LocaleController.getString("AppName", R.string.AppName)).setMessage(LocaleController.getString("VoipNeedMicPermission", R.string.VoipNeedMicPermission)).setPositiveButton(LocaleController.getString("OK", R.string.OK), null).setNegativeButton(LocaleController.getString("Settings", R.string.Settings), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                    activity.startActivity(intent);
                }
            }).show().setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }
    }

    public static void showRateAlert(Context context, Runnable runnable, long j, long j2) {
        final File file = new File(getLogsDir(), j + ".log");
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        int dp = AndroidUtilities.dp(16.0f);
        linearLayout.setPadding(dp, dp, dp, 0);
        View textView = new TextView(context);
        textView.setTextSize(2, 16.0f);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setGravity(17);
        textView.setText(LocaleController.getString("VoipRateCallAlert", R.string.VoipRateCallAlert));
        linearLayout.addView(textView);
        View betterRatingView = new BetterRatingView(context);
        linearLayout.addView(betterRatingView, LayoutHelper.createLinear(-2, -2, 1, 0, 16, 0, 0));
        View editText = new EditText(context);
        editText.setHint(LocaleController.getString("CallReportHint", R.string.CallReportHint));
        editText.setInputType(147457);
        editText.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        editText.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        editText.setBackgroundDrawable(Theme.createEditTextDrawable(context, true));
        editText.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f));
        editText.setTextSize(18.0f);
        editText.setVisibility(8);
        linearLayout.addView(editText, LayoutHelper.createLinear(-1, -2, 8.0f, 8.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        final boolean[] zArr = new boolean[]{true};
        View checkBoxCell = new CheckBoxCell(context, true);
        checkBoxCell = checkBoxCell;
        View.OnClickListener c46665 = new View.OnClickListener() {
            public void onClick(View view) {
                zArr[0] = !zArr[0];
                checkBoxCell.setChecked(zArr[0], true);
            }
        };
        checkBoxCell.setText(LocaleController.getString("CallReportIncludeLogs", R.string.CallReportIncludeLogs), null, true, false);
        checkBoxCell.setClipToPadding(false);
        checkBoxCell.setOnClickListener(c46665);
        linearLayout.addView(checkBoxCell, LayoutHelper.createLinear(-1, -2, -8.0f, BitmapDescriptorFactory.HUE_RED, -8.0f, BitmapDescriptorFactory.HUE_RED));
        final View textView2 = new TextView(context);
        textView2.setTextSize(2, 14.0f);
        textView2.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        textView2.setText(LocaleController.getString("CallReportLogsExplain", R.string.CallReportLogsExplain));
        textView2.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), 0);
        textView2.setOnClickListener(c46665);
        linearLayout.addView(textView2);
        checkBoxCell.setVisibility(8);
        textView2.setVisibility(8);
        if (!file.exists()) {
            zArr[0] = false;
        }
        final View view = betterRatingView;
        final View view2 = editText;
        final long j3 = j2;
        final long j4 = j;
        final Context context2 = context;
        final Runnable runnable2 = runnable;
        final View button = new Builder(context).setTitle(LocaleController.getString("CallMessageReportProblem", R.string.CallMessageReportProblem)).setView(linearLayout).setPositiveButton(LocaleController.getString("Send", R.string.Send), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                final TLObject tLRPC$TL_phone_setCallRating = new TLRPC$TL_phone_setCallRating();
                tLRPC$TL_phone_setCallRating.rating = view.getRating();
                if (tLRPC$TL_phone_setCallRating.rating < 5) {
                    tLRPC$TL_phone_setCallRating.comment = view2.getText().toString();
                } else {
                    tLRPC$TL_phone_setCallRating.comment = TtmlNode.ANONYMOUS_REGION_ID;
                }
                tLRPC$TL_phone_setCallRating.peer = new TLRPC$TL_inputPhoneCall();
                tLRPC$TL_phone_setCallRating.peer.access_hash = j3;
                tLRPC$TL_phone_setCallRating.peer.id = j4;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_phone_setCallRating, new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject instanceof TLRPC$TL_updates) {
                            MessagesController.getInstance().processUpdates((TLRPC$TL_updates) tLObject, false);
                            if (zArr[0] && file.exists() && tLRPC$TL_phone_setCallRating.rating < 4) {
                                SendMessagesHelper.prepareSendingDocument(file.getAbsolutePath(), file.getAbsolutePath(), null, "text/plain", 4244000, null, null);
                                Toast.makeText(context2, LocaleController.getString("CallReportSent", R.string.CallReportSent), 1).show();
                            }
                        }
                    }
                });
            }
        }).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }).show().getButton(-1);
        button.setEnabled(false);
        linearLayout = editText;
        final Context context3 = context;
        final View view3 = checkBoxCell;
        betterRatingView.setOnRatingChangeListener(new OnRatingChangeListener() {
            public void onRatingChanged(int i) {
                int i2 = 0;
                button.setEnabled(i > 0);
                linearLayout.setHint(i < 4 ? LocaleController.getString("CallReportHint", R.string.CallReportHint) : LocaleController.getString("VoipFeedbackCommentHint", R.string.VoipFeedbackCommentHint));
                EditText editText = linearLayout;
                int i3 = (i >= 5 || i <= 0) ? 8 : 0;
                editText.setVisibility(i3);
                if (linearLayout.getVisibility() == 8) {
                    ((InputMethodManager) context3.getSystemService("input_method")).hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
                }
                if (file.exists()) {
                    view3.setVisibility(i < 4 ? 0 : 8);
                    TextView textView = textView2;
                    if (i >= 4) {
                        i2 = 8;
                    }
                    textView.setVisibility(i2);
                }
            }
        });
    }

    public static void showRateAlert(Context context, TLRPC$TL_messageActionPhoneCall tLRPC$TL_messageActionPhoneCall) {
        for (String split : context.getSharedPreferences("notifications", 0).getStringSet("calls_access_hashes", Collections.EMPTY_SET)) {
            String[] split2 = split.split(" ");
            if (split2.length >= 2 && split2[0].equals(tLRPC$TL_messageActionPhoneCall.call_id + TtmlNode.ANONYMOUS_REGION_ID)) {
                try {
                    long parseLong = Long.parseLong(split2[1]);
                    showRateAlert(context, null, tLRPC$TL_messageActionPhoneCall.call_id, parseLong);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    public static void startCall(User user, final Activity activity, TLRPC$TL_userFull tLRPC$TL_userFull) {
        int i = 1;
        if (tLRPC$TL_userFull != null && tLRPC$TL_userFull.phone_calls_private) {
            new Builder(activity).setTitle(LocaleController.getString("VoipFailed", R.string.VoipFailed)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("CallNotAvailable", R.string.CallNotAvailable, new Object[]{ContactsController.formatName(user.first_name, user.last_name)}))).setPositiveButton(LocaleController.getString("OK", R.string.OK), null).show();
        } else if (ConnectionsManager.getInstance().getConnectionState() != 3) {
            if (System.getInt(activity.getContentResolver(), "airplane_mode_on", 0) == 0) {
                i = 0;
            }
            Builder positiveButton = new Builder(activity).setTitle(i != 0 ? LocaleController.getString("VoipOfflineAirplaneTitle", R.string.VoipOfflineAirplaneTitle) : LocaleController.getString("VoipOfflineTitle", R.string.VoipOfflineTitle)).setMessage(i != 0 ? LocaleController.getString("VoipOfflineAirplane", R.string.VoipOfflineAirplane) : LocaleController.getString("VoipOffline", R.string.VoipOffline)).setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            if (i != 0) {
                final Intent intent = new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    positiveButton.setNeutralButton(LocaleController.getString("VoipOfflineOpenSettings", R.string.VoipOfflineOpenSettings), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.startActivity(intent);
                        }
                    });
                }
            }
            positiveButton.show();
        } else if (VERSION.SDK_INT < 23 || activity.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
            initiateCall(user, activity);
        } else {
            activity.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 101);
        }
    }

    public static void upgradeP2pSetting() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        if (sharedPreferences.contains("calls_p2p")) {
            Editor edit = sharedPreferences.edit();
            if (!sharedPreferences.getBoolean("calls_p2p", true)) {
                edit.putInt("calls_p2p_new", 2);
            }
            edit.remove("calls_p2p").apply();
        }
    }
}
