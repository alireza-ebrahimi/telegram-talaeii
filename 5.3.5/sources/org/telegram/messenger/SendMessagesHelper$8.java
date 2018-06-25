package org.telegram.messenger;

import org.ir.talaeii.R;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_game;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer;
import org.telegram.tgnet.TLRPC$TL_payments_paymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_paymentReceipt;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.PaymentFormActivity;

class SendMessagesHelper$8 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ TLRPC$KeyboardButton val$button;
    final /* synthetic */ boolean val$cacheFinal;
    final /* synthetic */ String val$key;
    final /* synthetic */ MessageObject val$messageObject;
    final /* synthetic */ ChatActivity val$parentFragment;

    SendMessagesHelper$8(SendMessagesHelper this$0, String str, boolean z, MessageObject messageObject, TLRPC$KeyboardButton tLRPC$KeyboardButton, ChatActivity chatActivity) {
        this.this$0 = this$0;
        this.val$key = str;
        this.val$cacheFinal = z;
        this.val$messageObject = messageObject;
        this.val$button = tLRPC$KeyboardButton;
        this.val$parentFragment = chatActivity;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SendMessagesHelper.access$1300(SendMessagesHelper$8.this.this$0).remove(SendMessagesHelper$8.this.val$key);
                if (SendMessagesHelper$8.this.val$cacheFinal && response == null) {
                    SendMessagesHelper$8.this.this$0.sendCallback(false, SendMessagesHelper$8.this.val$messageObject, SendMessagesHelper$8.this.val$button, SendMessagesHelper$8.this.val$parentFragment);
                } else if (response == null) {
                } else {
                    if (!(SendMessagesHelper$8.this.val$button instanceof TLRPC$TL_keyboardButtonBuy)) {
                        TLRPC$TL_messages_botCallbackAnswer res = response;
                        if (!(SendMessagesHelper$8.this.val$cacheFinal || res.cache_time == 0)) {
                            MessagesStorage.getInstance().saveBotCache(SendMessagesHelper$8.this.val$key, res);
                        }
                        int uid;
                        User user;
                        if (res.message != null) {
                            if (!res.alert) {
                                uid = SendMessagesHelper$8.this.val$messageObject.messageOwner.from_id;
                                if (SendMessagesHelper$8.this.val$messageObject.messageOwner.via_bot_id != 0) {
                                    uid = SendMessagesHelper$8.this.val$messageObject.messageOwner.via_bot_id;
                                }
                                String name = null;
                                if (uid > 0) {
                                    user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
                                    if (user != null) {
                                        name = ContactsController.formatName(user.first_name, user.last_name);
                                    }
                                } else {
                                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-uid));
                                    if (chat != null) {
                                        name = chat.title;
                                    }
                                }
                                if (name == null) {
                                    name = "bot";
                                }
                                SendMessagesHelper$8.this.val$parentFragment.showAlert(name, res.message);
                            } else if (SendMessagesHelper$8.this.val$parentFragment.getParentActivity() != null) {
                                Builder builder = new Builder(SendMessagesHelper$8.this.val$parentFragment.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                builder.setMessage(res.message);
                                SendMessagesHelper$8.this.val$parentFragment.showDialog(builder.create());
                            }
                        } else if (res.url != null && SendMessagesHelper$8.this.val$parentFragment.getParentActivity() != null) {
                            uid = SendMessagesHelper$8.this.val$messageObject.messageOwner.from_id;
                            if (SendMessagesHelper$8.this.val$messageObject.messageOwner.via_bot_id != 0) {
                                uid = SendMessagesHelper$8.this.val$messageObject.messageOwner.via_bot_id;
                            }
                            user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
                            boolean verified = user != null && user.verified;
                            if (SendMessagesHelper$8.this.val$button instanceof TLRPC$TL_keyboardButtonGame) {
                                TLRPC$TL_game game = SendMessagesHelper$8.this.val$messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? SendMessagesHelper$8.this.val$messageObject.messageOwner.media.game : null;
                                if (game != null) {
                                    boolean z;
                                    ChatActivity chatActivity = SendMessagesHelper$8.this.val$parentFragment;
                                    MessageObject messageObject = SendMessagesHelper$8.this.val$messageObject;
                                    String str = res.url;
                                    if (verified || !ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("askgame_" + uid, true)) {
                                        z = false;
                                    } else {
                                        z = true;
                                    }
                                    chatActivity.showOpenGameAlert(game, messageObject, str, z, uid);
                                    return;
                                }
                                return;
                            }
                            SendMessagesHelper$8.this.val$parentFragment.showOpenUrlAlert(res.url, false);
                        }
                    } else if (response instanceof TLRPC$TL_payments_paymentForm) {
                        TLRPC$TL_payments_paymentForm form = response;
                        MessagesController.getInstance().putUsers(form.users, false);
                        SendMessagesHelper$8.this.val$parentFragment.presentFragment(new PaymentFormActivity(form, SendMessagesHelper$8.this.val$messageObject));
                    } else if (response instanceof TLRPC$TL_payments_paymentReceipt) {
                        SendMessagesHelper$8.this.val$parentFragment.presentFragment(new PaymentFormActivity(SendMessagesHelper$8.this.val$messageObject, (TLRPC$TL_payments_paymentReceipt) response));
                    }
                }
            }
        });
    }
}
