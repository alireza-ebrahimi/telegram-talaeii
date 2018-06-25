package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_importChatInvite;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatInvite;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.JoinSheetUserCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class JoinGroupAlert extends BottomSheet {
    private ChatInvite chatInvite;
    private BaseFragment fragment;
    private String hash;

    /* renamed from: org.telegram.ui.Components.JoinGroupAlert$1 */
    class C44591 implements OnClickListener {
        C44591() {
        }

        public void onClick(View view) {
            JoinGroupAlert.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.Components.JoinGroupAlert$2 */
    class C44622 implements OnClickListener {
        C44622() {
        }

        public void onClick(View view) {
            JoinGroupAlert.this.dismiss();
            final TLObject tLRPC$TL_messages_importChatInvite = new TLRPC$TL_messages_importChatInvite();
            tLRPC$TL_messages_importChatInvite.hash = JoinGroupAlert.this.hash;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_importChatInvite, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (JoinGroupAlert.this.fragment != null && JoinGroupAlert.this.fragment.getParentActivity() != null) {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                                    if (!tLRPC$Updates.chats.isEmpty()) {
                                        Chat chat = (Chat) tLRPC$Updates.chats.get(0);
                                        chat.left = false;
                                        chat.kicked = false;
                                        MessagesController.getInstance().putUsers(tLRPC$Updates.users, false);
                                        MessagesController.getInstance().putChats(tLRPC$Updates.chats, false);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("chat_id", chat.id);
                                        if (MessagesController.checkCanOpenChat(bundle, JoinGroupAlert.this.fragment)) {
                                            JoinGroupAlert.this.fragment.presentFragment(new ChatActivity(bundle), JoinGroupAlert.this.fragment instanceof ChatActivity);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                AlertsCreator.processError(tLRPC$TL_error, JoinGroupAlert.this.fragment, tLRPC$TL_messages_importChatInvite, new Object[0]);
                            }
                        }
                    });
                }
            }, 2);
        }
    }

    private class UsersAdapter extends SelectionAdapter {
        private Context context;

        public UsersAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            int size = JoinGroupAlert.this.chatInvite.participants.size();
            return size != (JoinGroupAlert.this.chatInvite.chat != null ? JoinGroupAlert.this.chatInvite.chat.participants_count : JoinGroupAlert.this.chatInvite.participants_count) ? size + 1 : size;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            JoinSheetUserCell joinSheetUserCell = (JoinSheetUserCell) viewHolder.itemView;
            if (i < JoinGroupAlert.this.chatInvite.participants.size()) {
                joinSheetUserCell.setUser((User) JoinGroupAlert.this.chatInvite.participants.get(i));
            } else {
                joinSheetUserCell.setCount((JoinGroupAlert.this.chatInvite.chat != null ? JoinGroupAlert.this.chatInvite.chat.participants_count : JoinGroupAlert.this.chatInvite.participants_count) - JoinGroupAlert.this.chatInvite.participants.size());
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View joinSheetUserCell = new JoinSheetUserCell(this.context);
            joinSheetUserCell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(100.0f), AndroidUtilities.dp(90.0f)));
            return new Holder(joinSheetUserCell);
        }
    }

    public JoinGroupAlert(Context context, ChatInvite chatInvite, String str, BaseFragment baseFragment) {
        int i;
        CharSequence charSequence;
        Drawable drawable;
        super(context, false);
        setApplyBottomPadding(false);
        setApplyTopPadding(false);
        this.fragment = baseFragment;
        this.chatInvite = chatInvite;
        this.hash = str;
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setClickable(true);
        setCustomView(linearLayout);
        TLObject tLObject = null;
        Drawable avatarDrawable;
        String str2;
        if (chatInvite.chat != null) {
            avatarDrawable = new AvatarDrawable(chatInvite.chat);
            if (this.chatInvite.chat.photo != null) {
                tLObject = this.chatInvite.chat.photo.photo_small;
            }
            str2 = chatInvite.chat.title;
            i = chatInvite.chat.participants_count;
            charSequence = str2;
            drawable = avatarDrawable;
        } else {
            avatarDrawable = new AvatarDrawable();
            avatarDrawable.setInfo(0, chatInvite.title, null, false);
            if (this.chatInvite.photo != null) {
                tLObject = this.chatInvite.photo.photo_small;
            }
            str2 = chatInvite.title;
            i = chatInvite.participants_count;
            Object obj = str2;
            drawable = avatarDrawable;
        }
        View backupImageView = new BackupImageView(context);
        backupImageView.setRoundRadius(AndroidUtilities.dp(35.0f));
        backupImageView.setImage(tLObject, "50_50", drawable);
        linearLayout.addView(backupImageView, LayoutHelper.createLinear(70, 70, 49, 0, 12, 0, 0));
        backupImageView = new TextView(context);
        backupImageView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        backupImageView.setTextSize(1, 17.0f);
        backupImageView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        backupImageView.setText(charSequence);
        backupImageView.setSingleLine(true);
        backupImageView.setEllipsize(TruncateAt.END);
        linearLayout.addView(backupImageView, LayoutHelper.createLinear(-2, -2, 49, 10, 10, 10, i > 0 ? 0 : 10));
        if (i > 0) {
            View textView = new TextView(context);
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
            textView.setSingleLine(true);
            textView.setEllipsize(TruncateAt.END);
            textView.setText(LocaleController.formatPluralString("Members", i));
            linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 49, 10, 4, 10, 10));
        }
        if (!chatInvite.participants.isEmpty()) {
            View recyclerListView = new RecyclerListView(context);
            recyclerListView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
            recyclerListView.setNestedScrollingEnabled(false);
            recyclerListView.setClipToPadding(false);
            recyclerListView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            recyclerListView.setHorizontalScrollBarEnabled(false);
            recyclerListView.setVerticalScrollBarEnabled(false);
            recyclerListView.setAdapter(new UsersAdapter(context));
            recyclerListView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
            linearLayout.addView(recyclerListView, LayoutHelper.createLinear(-2, 90, 49, 0, 0, 0, 0));
        }
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        linearLayout.addView(view, LayoutHelper.createLinear(-1, 3));
        view = new PickerBottomLayout(context, false);
        linearLayout.addView(view, LayoutHelper.createFrame(-1, 48, 83));
        view.cancelButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        view.cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        view.cancelButton.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        view.cancelButton.setOnClickListener(new C44591());
        view.doneButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        view.doneButton.setVisibility(0);
        view.doneButtonBadgeTextView.setVisibility(8);
        view.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        view.doneButtonTextView.setText(LocaleController.getString("JoinGroup", R.string.JoinGroup));
        view.doneButton.setOnClickListener(new C44622());
    }
}
