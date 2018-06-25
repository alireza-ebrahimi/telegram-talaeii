package org.telegram.ui.Components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.base.log.LogCollector;
import org.ir.talaeii.R;
import org.telegram.customization.fetch.FetchConst;
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
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$TL_account_reportPeer;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonPornography;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonSpam;
import org.telegram.tgnet.TLRPC$TL_inputReportReasonViolence;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.app.Dialog processError(org.telegram.tgnet.TLRPC$TL_error r7, org.telegram.ui.ActionBar.BaseFragment r8, org.telegram.tgnet.TLObject r9, java.lang.Object... r10) {
        /*
        r6 = 2131231177; // 0x7f0801c9 float:1.8078428E38 double:1.052968108E-314;
        r5 = 2131231396; // 0x7f0802a4 float:1.8078872E38 double:1.052968216E-314;
        r2 = 1;
        r4 = 2131231486; // 0x7f0802fe float:1.8079054E38 double:1.0529682606E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_channels_joinChannel;
        if (r0 != 0) goto L_0x002f;
    L_0x001b:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_channels_editAdmin;
        if (r0 != 0) goto L_0x002f;
    L_0x001f:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_channels_inviteToChannel;
        if (r0 != 0) goto L_0x002f;
    L_0x0023:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_addChatUser;
        if (r0 != 0) goto L_0x002f;
    L_0x0027:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_messages_startBot;
        if (r0 != 0) goto L_0x002f;
    L_0x002b:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_channels_editBanned;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_channels_createChannel;
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
        r1 = 2131231346; // 0x7f080272 float:1.807877E38 double:1.0529681914E-314;
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
        r1 = 2131231682; // 0x7f0803c2 float:1.8079452E38 double:1.0529683574E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x0119:
        r0 = "JoinToGroupErrorNotExist";
        r1 = 2131231683; // 0x7f0803c3 float:1.8079454E38 double:1.052968358E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_confirmPhone;
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
        r1 = 2131231656; // 0x7f0803a8 float:1.80794E38 double:1.0529683446E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_auth_resendCode;
        if (r0 == 0) goto L_0x025d;
    L_0x01c4:
        r0 = r7.text;
        r1 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x01de;
    L_0x01cf:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231660; // 0x7f0803ac float:1.8079407E38 double:1.0529683465E-314;
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
        r1 = 2131231656; // 0x7f0803a8 float:1.80794E38 double:1.0529683446E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_sendConfirmPhoneCode;
        if (r0 == 0) goto L_0x02a0;
    L_0x0261:
        r0 = r7.code;
        r1 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r0 != r1) goto L_0x0277;
    L_0x0267:
        r0 = "CancelLinkExpired";
        r1 = 2131231024; // 0x7f080130 float:1.8078117E38 double:1.0529680323E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_changePhone;
        if (r0 == 0) goto L_0x0318;
    L_0x02a4:
        r0 = r7.text;
        r1 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r1);
        if (r0 == 0) goto L_0x02be;
    L_0x02af:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231660; // 0x7f0803ac float:1.8079407E38 double:1.0529683465E-314;
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
        r1 = 2131231656; // 0x7f0803a8 float:1.80794E38 double:1.0529683446E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_sendChangePhoneCode;
        if (r0 == 0) goto L_0x03b7;
    L_0x031c:
        r0 = r7.text;
        r3 = "PHONE_NUMBER_INVALID";
        r0 = r0.contains(r3);
        if (r0 == 0) goto L_0x0336;
    L_0x0327:
        r0 = "InvalidPhoneNumber";
        r1 = 2131231660; // 0x7f0803ac float:1.8079407E38 double:1.0529683465E-314;
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
        r1 = 2131231656; // 0x7f0803a8 float:1.80794E38 double:1.0529683446E-314;
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
        r4 = 2131231037; // 0x7f08013d float:1.8078144E38 double:1.0529680387E-314;
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
        r1 = 2131232578; // 0x7f080742 float:1.808127E38 double:1.0529688E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleAlert(r8, r0);
        goto L_0x003e;
    L_0x03f9:
        r0 = "UsernameInUse";
        r1 = 2131232577; // 0x7f080741 float:1.8081267E38 double:1.0529687996E-314;
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
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_getPassword;
        if (r0 != 0) goto L_0x0453;
    L_0x044f:
        r0 = r9 instanceof org.telegram.tgnet.TLRPC$TL_account_getTmpPassword;
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
        r1 = 2131232105; // 0x7f080569 float:1.808031E38 double:1.0529685664E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleToast(r8, r0);
        goto L_0x003e;
    L_0x04ad:
        r0 = "PaymentFailed";
        r1 = 2131232102; // 0x7f080566 float:1.8080304E38 double:1.052968565E-314;
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
        r1 = 2131232104; // 0x7f080568 float:1.8080308E38 double:1.052968566E-314;
        r0 = org.telegram.messenger.LocaleController.getString(r0, r1);
        showSimpleToast(r8, r0);
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.AlertsCreator.processError(org.telegram.tgnet.TLRPC$TL_error, org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLObject, java.lang.Object[]):android.app.Dialog");
    }

    public static Toast showSimpleToast(BaseFragment baseFragment, String text) {
        if (text == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        Toast toast = Toast.makeText(baseFragment.getParentActivity(), text, 1);
        toast.show();
        return toast;
    }

    public static Dialog showSimpleAlert(BaseFragment baseFragment, String text) {
        if (text == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        Builder builder = new Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setMessage(text);
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        Dialog dialog = builder.create();
        baseFragment.showDialog(dialog);
        return dialog;
    }

    public static Dialog createMuteAlert(Context context, final long dialog_id) {
        if (context == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        builder.setTitle(LocaleController.getString("Notifications", R.string.Notifications));
        CharSequence[] items = new CharSequence[4];
        items[0] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 1)});
        items[1] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 8)});
        items[2] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Days", 2)});
        items[3] = LocaleController.getString("MuteDisable", R.string.MuteDisable);
        builder.setItems(items, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                long flags;
                int untilTime = ConnectionsManager.getInstance().getCurrentTime();
                if (i == 0) {
                    untilTime += 3600;
                } else if (i == 1) {
                    untilTime += 28800;
                } else if (i == 2) {
                    untilTime += 172800;
                } else if (i == 3) {
                    untilTime = Integer.MAX_VALUE;
                }
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (i == 3) {
                    editor.putInt("notify2_" + dialog_id, 2);
                    flags = 1;
                } else {
                    editor.putInt("notify2_" + dialog_id, 3);
                    editor.putInt("notifyuntil_" + dialog_id, untilTime);
                    flags = (((long) untilTime) << 32) | 1;
                }
                NotificationsController.getInstance().removeNotificationsForDialog(dialog_id);
                MessagesStorage.getInstance().setDialogFlags(dialog_id, flags);
                editor.commit();
                TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(dialog_id));
                if (dialog != null) {
                    dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                    dialog.notify_settings.mute_until = untilTime;
                }
                NotificationsController.updateServerNotificationsSettings(dialog_id);
            }
        });
        return builder.create();
    }

    public static Dialog createReportAlert(Context context, final long dialog_id, final BaseFragment parentFragment) {
        if (context == null || parentFragment == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        builder.setTitle(LocaleController.getString("ReportChat", R.string.ReportChat));
        builder.setItems(new CharSequence[]{LocaleController.getString("ReportChatSpam", R.string.ReportChatSpam), LocaleController.getString("ReportChatViolence", R.string.ReportChatViolence), LocaleController.getString("ReportChatPornography", R.string.ReportChatPornography), LocaleController.getString("ReportChatOther", R.string.ReportChatOther)}, new OnClickListener() {

            /* renamed from: org.telegram.ui.Components.AlertsCreator$2$1 */
            class C24721 implements RequestDelegate {
                C24721() {
                }

                public void run(TLObject response, TLRPC$TL_error error) {
                }
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 3) {
                    Bundle args = new Bundle();
                    args.putLong("dialog_id", dialog_id);
                    parentFragment.presentFragment(new ReportOtherActivity(args));
                    return;
                }
                TLRPC$TL_account_reportPeer req = new TLRPC$TL_account_reportPeer();
                req.peer = MessagesController.getInputPeer((int) dialog_id);
                if (i == 0) {
                    req.reason = new TLRPC$TL_inputReportReasonSpam();
                } else if (i == 1) {
                    req.reason = new TLRPC$TL_inputReportReasonViolence();
                } else if (i == 2) {
                    req.reason = new TLRPC$TL_inputReportReasonPornography();
                }
                ConnectionsManager.getInstance().sendRequest(req, new C24721());
            }
        });
        return builder.create();
    }

    private static String getFloodWaitString(String error) {
        String timeString;
        int time = Utilities.parseInt(error).intValue();
        if (time < 60) {
            timeString = LocaleController.formatPluralString("Seconds", time);
        } else {
            timeString = LocaleController.formatPluralString("Minutes", time / 60);
        }
        return LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString});
    }

    public static void showFloodWaitAlert(String error, BaseFragment fragment) {
        if (error != null && error.startsWith("FLOOD_WAIT") && fragment != null && fragment.getParentActivity() != null) {
            String timeString;
            int time = Utilities.parseInt(error).intValue();
            if (time < 60) {
                timeString = LocaleController.formatPluralString("Seconds", time);
            } else {
                timeString = LocaleController.formatPluralString("Minutes", time / 60);
            }
            Builder builder = new Builder(fragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            fragment.showDialog(builder.create(), true, null);
        }
    }

    public static void showSendMediaAlert(int result, BaseFragment fragment) {
        if (result != 0) {
            Builder builder = new Builder(fragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            if (result == 1) {
                builder.setMessage(LocaleController.getString("ErrorSendRestrictedStickers", R.string.ErrorSendRestrictedStickers));
            } else if (result == 2) {
                builder.setMessage(LocaleController.getString("ErrorSendRestrictedMedia", R.string.ErrorSendRestrictedMedia));
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            fragment.showDialog(builder.create(), true, null);
        }
    }

    public static void showAddUserAlert(String error, final BaseFragment fragment, boolean isChannel) {
        if (error != null && fragment != null && fragment.getParentActivity() != null) {
            Builder builder = new Builder(fragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            boolean z = true;
            switch (error.hashCode()) {
                case -1763467626:
                    if (error.equals("USERS_TOO_FEW")) {
                        z = true;
                        break;
                    }
                    break;
                case -538116776:
                    if (error.equals("USER_BLOCKED")) {
                        z = true;
                        break;
                    }
                    break;
                case -512775857:
                    if (error.equals("USER_RESTRICTED")) {
                        z = true;
                        break;
                    }
                    break;
                case -454039871:
                    if (error.equals("PEER_FLOOD")) {
                        z = false;
                        break;
                    }
                    break;
                case -420079733:
                    if (error.equals("BOTS_TOO_MUCH")) {
                        z = true;
                        break;
                    }
                    break;
                case 98635865:
                    if (error.equals("USER_KICKED")) {
                        z = true;
                        break;
                    }
                    break;
                case 517420851:
                    if (error.equals("USER_BOT")) {
                        z = true;
                        break;
                    }
                    break;
                case 845559454:
                    if (error.equals("YOU_BLOCKED_USER")) {
                        z = true;
                        break;
                    }
                    break;
                case 916342611:
                    if (error.equals("USER_ADMIN_INVALID")) {
                        z = true;
                        break;
                    }
                    break;
                case 1047173446:
                    if (error.equals("CHAT_ADMIN_BAN_REQUIRED")) {
                        z = true;
                        break;
                    }
                    break;
                case 1167301807:
                    if (error.equals("USERS_TOO_MUCH")) {
                        z = true;
                        break;
                    }
                    break;
                case 1227003815:
                    if (error.equals("USER_ID_INVALID")) {
                        z = true;
                        break;
                    }
                    break;
                case 1253103379:
                    if (error.equals("ADMINS_TOO_MUCH")) {
                        z = true;
                        break;
                    }
                    break;
                case 1623167701:
                    if (error.equals("USER_NOT_MUTUAL_CONTACT")) {
                        z = true;
                        break;
                    }
                    break;
                case 1754587486:
                    if (error.equals("CHAT_ADMIN_INVITE_REQUIRED")) {
                        z = true;
                        break;
                    }
                    break;
                case 1916725894:
                    if (error.equals("USER_PRIVACY_RESTRICTED")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    builder.setMessage(LocaleController.getString("NobodyLikesSpam2", R.string.NobodyLikesSpam2));
                    builder.setNegativeButton(LocaleController.getString("MoreInfo", R.string.MoreInfo), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MessagesController.openByUserName("spambot", fragment, 1);
                        }
                    });
                    break;
                case true:
                case true:
                case true:
                    if (!isChannel) {
                        builder.setMessage(LocaleController.getString("GroupUserCantAdd", R.string.GroupUserCantAdd));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantAdd", R.string.ChannelUserCantAdd));
                        break;
                    }
                case true:
                    if (!isChannel) {
                        builder.setMessage(LocaleController.getString("GroupUserAddLimit", R.string.GroupUserAddLimit));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserAddLimit", R.string.ChannelUserAddLimit));
                        break;
                    }
                case true:
                    if (!isChannel) {
                        builder.setMessage(LocaleController.getString("GroupUserLeftError", R.string.GroupUserLeftError));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserLeftError", R.string.ChannelUserLeftError));
                        break;
                    }
                case true:
                    if (!isChannel) {
                        builder.setMessage(LocaleController.getString("GroupUserCantAdmin", R.string.GroupUserCantAdmin));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantAdmin", R.string.ChannelUserCantAdmin));
                        break;
                    }
                case true:
                    if (!isChannel) {
                        builder.setMessage(LocaleController.getString("GroupUserCantBot", R.string.GroupUserCantBot));
                        break;
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelUserCantBot", R.string.ChannelUserCantBot));
                        break;
                    }
                case true:
                    if (!isChannel) {
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
                    builder.setMessage(LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + LogCollector.LINE_SEPARATOR + error);
                    break;
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            fragment.showDialog(builder.create(), true, null);
        }
    }

    public static Dialog createColorSelectDialog(Activity parentActivity, long dialog_id, boolean globalGroup, boolean globalAll, Runnable onSelect) {
        int currentColor;
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        if (globalGroup) {
            currentColor = preferences.getInt("GroupLed", -16776961);
        } else if (globalAll) {
            currentColor = preferences.getInt("MessagesLed", -16776961);
        } else {
            if (preferences.contains("color_" + dialog_id)) {
                currentColor = preferences.getInt("color_" + dialog_id, -16776961);
            } else if (((int) dialog_id) < 0) {
                currentColor = preferences.getInt("GroupLed", -16776961);
            } else {
                currentColor = preferences.getInt("MessagesLed", -16776961);
            }
        }
        View linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        String[] descriptions = new String[]{LocaleController.getString("ColorRed", R.string.ColorRed), LocaleController.getString("ColorOrange", R.string.ColorOrange), LocaleController.getString("ColorYellow", R.string.ColorYellow), LocaleController.getString("ColorGreen", R.string.ColorGreen), LocaleController.getString("ColorCyan", R.string.ColorCyan), LocaleController.getString("ColorBlue", R.string.ColorBlue), LocaleController.getString("ColorViolet", R.string.ColorViolet), LocaleController.getString("ColorPink", R.string.ColorPink), LocaleController.getString("ColorWhite", R.string.ColorWhite)};
        final int[] selectedColor = new int[]{currentColor};
        for (int a = 0; a < 9; a++) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(TextColorCell.colors[a], TextColorCell.colors[a]);
            cell.setTextAndValue(descriptions[a], currentColor == TextColorCell.colorsToSave[a]);
            linearLayout.addView(cell);
            linearLayout = linearLayout;
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int count = linearLayout.getChildCount();
                    for (int a = 0; a < count; a++) {
                        boolean z;
                        View cell = (RadioColorCell) linearLayout.getChildAt(a);
                        if (cell == v) {
                            z = true;
                        } else {
                            z = false;
                        }
                        cell.setChecked(z, true);
                    }
                    selectedColor[0] = TextColorCell.colorsToSave[((Integer) v.getTag()).intValue()];
                }
            });
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(LocaleController.getString("LedColor", R.string.LedColor));
        builder.setView(linearLayout);
        final boolean z = globalAll;
        final boolean z2 = globalGroup;
        final long j = dialog_id;
        final Runnable runnable = onSelect;
        builder.setPositiveButton(LocaleController.getString("Set", R.string.Set), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int which) {
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (z) {
                    editor.putInt("MessagesLed", selectedColor[0]);
                } else if (z2) {
                    editor.putInt("GroupLed", selectedColor[0]);
                } else {
                    editor.putInt("color_" + j, selectedColor[0]);
                }
                editor.commit();
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        final boolean z3 = globalAll;
        final boolean z4 = globalGroup;
        final long j2 = dialog_id;
        final Runnable runnable2 = onSelect;
        builder.setNeutralButton(LocaleController.getString("LedDisabled", R.string.LedDisabled), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                if (z3) {
                    editor.putInt("MessagesLed", 0);
                } else if (z4) {
                    editor.putInt("GroupLed", 0);
                } else {
                    editor.putInt("color_" + j2, 0);
                }
                editor.commit();
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        if (!(globalAll || globalGroup)) {
            final long j3 = dialog_id;
            final Runnable runnable3 = onSelect;
            builder.setNegativeButton(LocaleController.getString("Default", R.string.Default), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    editor.remove("color_" + j3);
                    editor.commit();
                    if (runnable3 != null) {
                        runnable3.run();
                    }
                }
            });
        }
        return builder.create();
    }

    public static Dialog createVibrationSelectDialog(Activity parentActivity, BaseFragment parentFragment, long dialog_id, boolean globalGroup, boolean globalAll, Runnable onSelect) {
        String prefix;
        if (dialog_id != 0) {
            prefix = "vibrate_";
        } else {
            prefix = globalGroup ? "vibrate_group" : "vibrate_messages";
        }
        return createVibrationSelectDialog(parentActivity, parentFragment, dialog_id, prefix, onSelect);
    }

    public static Dialog createVibrationSelectDialog(Activity parentActivity, BaseFragment parentFragment, long dialog_id, String prefKeyPrefix, Runnable onSelect) {
        String[] descriptions;
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] selected = new int[1];
        if (dialog_id != 0) {
            selected[0] = preferences.getInt(prefKeyPrefix + dialog_id, 0);
            if (selected[0] == 3) {
                selected[0] = 2;
            } else if (selected[0] == 2) {
                selected[0] = 3;
            }
            descriptions = new String[]{LocaleController.getString("VibrationDefault", R.string.VibrationDefault), LocaleController.getString("Short", R.string.Short), LocaleController.getString("Long", R.string.Long), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled)};
        } else {
            selected[0] = preferences.getInt(prefKeyPrefix, 0);
            if (selected[0] == 0) {
                selected[0] = 1;
            } else if (selected[0] == 1) {
                selected[0] = 2;
            } else if (selected[0] == 2) {
                selected[0] = 0;
            }
            descriptions = new String[]{LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), LocaleController.getString("Short", R.string.Short), LocaleController.getString("Long", R.string.Long), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent)};
        }
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        int a = 0;
        while (a < descriptions.length) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            cell.setTextAndValue(descriptions[a], selected[0] == a);
            linearLayout.addView(cell);
            final long j = dialog_id;
            final String str = prefKeyPrefix;
            final BaseFragment baseFragment = parentFragment;
            final Runnable runnable = onSelect;
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selected[0] = ((Integer) v.getTag()).intValue();
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    if (j != 0) {
                        if (selected[0] == 0) {
                            editor.putInt(str + j, 0);
                        } else if (selected[0] == 1) {
                            editor.putInt(str + j, 1);
                        } else if (selected[0] == 2) {
                            editor.putInt(str + j, 3);
                        } else if (selected[0] == 3) {
                            editor.putInt(str + j, 2);
                        }
                    } else if (selected[0] == 0) {
                        editor.putInt(str, 2);
                    } else if (selected[0] == 1) {
                        editor.putInt(str, 0);
                    } else if (selected[0] == 2) {
                        editor.putInt(str, 1);
                    } else if (selected[0] == 3) {
                        editor.putInt(str, 3);
                    } else if (selected[0] == 4) {
                        editor.putInt(str, 4);
                    }
                    editor.commit();
                    if (baseFragment != null) {
                        baseFragment.dismissCurrentDialig();
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            a++;
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(LocaleController.getString("Vibrate", R.string.Vibrate));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createLocationUpdateDialog(Activity parentActivity, User user, IntCallback callback) {
        final int[] selected = new int[1];
        String[] descriptions = new String[]{LocaleController.getString("SendLiveLocationFor15m", R.string.SendLiveLocationFor15m), LocaleController.getString("SendLiveLocationFor1h", R.string.SendLiveLocationFor1h), LocaleController.getString("SendLiveLocationFor8h", R.string.SendLiveLocationFor8h)};
        final LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        TextView titleTextView = new TextView(parentActivity);
        if (user != null) {
            titleTextView.setText(LocaleController.formatString("LiveLocationAlertPrivate", R.string.LiveLocationAlertPrivate, new Object[]{UserObject.getFirstName(user)}));
        } else {
            titleTextView.setText(LocaleController.getString("LiveLocationAlertGroup", R.string.LiveLocationAlertGroup));
        }
        titleTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        titleTextView.setTextSize(1, 16.0f);
        titleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        linearLayout.addView(titleTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, 0, 24, 8));
        int a = 0;
        while (a < descriptions.length) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            cell.setTextAndValue(descriptions[a], selected[0] == a);
            linearLayout.addView(cell);
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selected[0] = ((Integer) v.getTag()).intValue();
                    int count = linearLayout.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View child = linearLayout.getChildAt(a);
                        if (child instanceof RadioColorCell) {
                            boolean z;
                            RadioColorCell radioColorCell = (RadioColorCell) child;
                            if (child == v) {
                                z = true;
                            } else {
                                z = false;
                            }
                            radioColorCell.setChecked(z, true);
                        }
                    }
                }
            });
            a++;
        }
        Builder builder = new Builder(parentActivity);
        builder.setTopImage(new ShareLocationDrawable(parentActivity, false), Theme.getColor(Theme.key_dialogTopBackground));
        builder.setView(linearLayout);
        final IntCallback intCallback = callback;
        builder.setPositiveButton(LocaleController.getString("ShareFile", R.string.ShareFile), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int time;
                if (selected[0] == 0) {
                    time = FetchConst.STATUS_QUEUED;
                } else if (selected[0] == 1) {
                    time = 3600;
                } else {
                    time = 28800;
                }
                intCallback.run(time);
            }
        });
        builder.setNeutralButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createFreeSpaceDialog(LaunchActivity parentActivity) {
        final int[] selected = new int[1];
        int keepMedia = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("keep_media", 2);
        if (keepMedia == 2) {
            selected[0] = 3;
        } else if (keepMedia == 0) {
            selected[0] = 1;
        } else if (keepMedia == 1) {
            selected[0] = 2;
        } else if (keepMedia == 3) {
            selected[0] = 0;
        }
        String[] descriptions = new String[]{LocaleController.formatPluralString("Days", 3), LocaleController.formatPluralString("Weeks", 1), LocaleController.formatPluralString("Months", 1), LocaleController.getString("LowDiskSpaceNeverRemove", R.string.LowDiskSpaceNeverRemove)};
        final LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        View titleTextView = new TextView(parentActivity);
        titleTextView.setText(LocaleController.getString("LowDiskSpaceTitle2", R.string.LowDiskSpaceTitle2));
        titleTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        titleTextView.setTextSize(1, 16.0f);
        titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        titleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        linearLayout.addView(titleTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, 0, 24, 8));
        int a = 0;
        while (a < descriptions.length) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            cell.setTextAndValue(descriptions[a], selected[0] == a);
            linearLayout.addView(cell);
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int num = ((Integer) v.getTag()).intValue();
                    if (num == 0) {
                        selected[0] = 3;
                    } else if (num == 1) {
                        selected[0] = 0;
                    } else if (num == 2) {
                        selected[0] = 1;
                    } else if (num == 3) {
                        selected[0] = 2;
                    }
                    int count = linearLayout.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View child = linearLayout.getChildAt(a);
                        if (child instanceof RadioColorCell) {
                            boolean z;
                            RadioColorCell radioColorCell = (RadioColorCell) child;
                            if (child == v) {
                                z = true;
                            } else {
                                z = false;
                            }
                            radioColorCell.setChecked(z, true);
                        }
                    }
                }
            });
            a++;
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(LocaleController.getString("LowDiskSpaceTitle", R.string.LowDiskSpaceTitle));
        builder.setMessage(LocaleController.getString("LowDiskSpaceMessage", R.string.LowDiskSpaceMessage));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("keep_media", selected[0]).commit();
            }
        });
        final LaunchActivity launchActivity = parentActivity;
        builder.setNeutralButton(LocaleController.getString("ClearMediaCache", R.string.ClearMediaCache), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                launchActivity.presentFragment(new CacheControlActivity());
            }
        });
        return builder.create();
    }

    public static Dialog createPrioritySelectDialog(Activity parentActivity, BaseFragment parentFragment, long dialog_id, boolean globalGroup, boolean globalAll, Runnable onSelect) {
        String[] descriptions;
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] selected = new int[1];
        if (dialog_id != 0) {
            selected[0] = preferences.getInt("priority_" + dialog_id, 3);
            if (selected[0] == 3) {
                selected[0] = 0;
            } else {
                selected[0] = selected[0] + 1;
            }
            descriptions = new String[]{LocaleController.getString("NotificationsPrioritySettings", R.string.NotificationsPrioritySettings), LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax)};
        } else {
            if (globalAll) {
                selected[0] = preferences.getInt("priority_messages", 1);
            } else if (globalGroup) {
                selected[0] = preferences.getInt("priority_group", 1);
            }
            descriptions = new String[]{LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax)};
        }
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        int a = 0;
        while (a < descriptions.length) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            cell.setTextAndValue(descriptions[a], selected[0] == a);
            linearLayout.addView(cell);
            final long j = dialog_id;
            final boolean z = globalGroup;
            final BaseFragment baseFragment = parentFragment;
            final Runnable runnable = onSelect;
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selected[0] = ((Integer) v.getTag()).intValue();
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    if (j != 0) {
                        if (selected[0] == 0) {
                            selected[0] = 3;
                        } else {
                            int[] iArr = selected;
                            iArr[0] = iArr[0] - 1;
                        }
                        editor.putInt("priority_" + j, selected[0]);
                    } else {
                        editor.putInt(z ? "priority_group" : "priority_messages", selected[0]);
                    }
                    editor.commit();
                    if (baseFragment != null) {
                        baseFragment.dismissCurrentDialig();
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            a++;
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createPopupSelectDialog(Activity parentActivity, final BaseFragment parentFragment, final boolean globalGroup, boolean globalAll, final Runnable onSelect) {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        final int[] selected = new int[1];
        if (globalAll) {
            selected[0] = preferences.getInt("popupAll", 0);
        } else if (globalGroup) {
            selected[0] = preferences.getInt("popupGroup", 0);
        }
        String[] descriptions = new String[]{LocaleController.getString("NoPopup", R.string.NoPopup), LocaleController.getString("OnlyWhenScreenOn", R.string.OnlyWhenScreenOn), LocaleController.getString("OnlyWhenScreenOff", R.string.OnlyWhenScreenOff), LocaleController.getString("AlwaysShowPopup", R.string.AlwaysShowPopup)};
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        int a = 0;
        while (a < descriptions.length) {
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setTag(Integer.valueOf(a));
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            cell.setTextAndValue(descriptions[a], selected[0] == a);
            linearLayout.addView(cell);
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    selected[0] = ((Integer) v.getTag()).intValue();
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    editor.putInt(globalGroup ? "popupGroup" : "popupAll", selected[0]);
                    editor.commit();
                    if (parentFragment != null) {
                        parentFragment.dismissCurrentDialig();
                    }
                    if (onSelect != null) {
                        onSelect.run();
                    }
                }
            });
            a++;
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(LocaleController.getString("PopupNotification", R.string.PopupNotification));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Dialog createSingleChoiceDialog(Activity parentActivity, final BaseFragment parentFragment, String[] options, String title, int selected, final OnClickListener listener) {
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        for (int a = 0; a < options.length; a++) {
            boolean z;
            RadioColorCell cell = new RadioColorCell(parentActivity);
            cell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
            cell.setTag(Integer.valueOf(a));
            cell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            String str = options[a];
            if (selected == a) {
                z = true;
            } else {
                z = false;
            }
            cell.setTextAndValue(str, z);
            linearLayout.addView(cell);
            cell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int sel = ((Integer) v.getTag()).intValue();
                    if (parentFragment != null) {
                        parentFragment.dismissCurrentDialig();
                    }
                    listener.onClick(null, sel);
                }
            });
        }
        Builder builder = new Builder(parentActivity);
        builder.setTitle(title);
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        return builder.create();
    }

    public static Builder createTTLAlert(Context context, final TLRPC$EncryptedChat encryptedChat) {
        Builder builder = new Builder(context);
        builder.setTitle(LocaleController.getString("MessageLifetime", R.string.MessageLifetime));
        final NumberPicker numberPicker = new NumberPicker(context);
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
            public String format(int value) {
                if (value == 0) {
                    return LocaleController.getString("ShortMessageLifetimeForever", R.string.ShortMessageLifetimeForever);
                }
                if (value >= 1 && value < 16) {
                    return LocaleController.formatTTLString(value);
                }
                if (value == 16) {
                    return LocaleController.formatTTLString(30);
                }
                if (value == 17) {
                    return LocaleController.formatTTLString(60);
                }
                if (value == 18) {
                    return LocaleController.formatTTLString(3600);
                }
                if (value == 19) {
                    return LocaleController.formatTTLString(86400);
                }
                if (value == 20) {
                    return LocaleController.formatTTLString(604800);
                }
                return "";
            }
        });
        builder.setView(numberPicker);
        builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int oldValue = encryptedChat.ttl;
                which = numberPicker.getValue();
                if (which >= 0 && which < 16) {
                    encryptedChat.ttl = which;
                } else if (which == 16) {
                    encryptedChat.ttl = 30;
                } else if (which == 17) {
                    encryptedChat.ttl = 60;
                } else if (which == 18) {
                    encryptedChat.ttl = 3600;
                } else if (which == 19) {
                    encryptedChat.ttl = 86400;
                } else if (which == 20) {
                    encryptedChat.ttl = 604800;
                }
                if (oldValue != encryptedChat.ttl) {
                    SecretChatHelper.getInstance().sendTTLMessage(encryptedChat, null);
                    MessagesStorage.getInstance().updateEncryptedChatTTL(encryptedChat);
                }
            }
        });
        return builder;
    }
}
