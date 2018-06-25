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
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatInvite;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_importChatInvite;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.JoinSheetUserCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class JoinGroupAlert extends BottomSheet {
    private TLRPC$ChatInvite chatInvite;
    private BaseFragment fragment;
    private String hash;

    /* renamed from: org.telegram.ui.Components.JoinGroupAlert$1 */
    class C26211 implements OnClickListener {
        C26211() {
        }

        public void onClick(View view) {
            JoinGroupAlert.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.Components.JoinGroupAlert$2 */
    class C26242 implements OnClickListener {
        C26242() {
        }

        public void onClick(View v) {
            JoinGroupAlert.this.dismiss();
            final TLRPC$TL_messages_importChatInvite req = new TLRPC$TL_messages_importChatInvite();
            req.hash = JoinGroupAlert.this.hash;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    if (error == null) {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (JoinGroupAlert.this.fragment != null && JoinGroupAlert.this.fragment.getParentActivity() != null) {
                                if (error == null) {
                                    TLRPC$Updates updates = response;
                                    if (!updates.chats.isEmpty()) {
                                        TLRPC$Chat chat = (TLRPC$Chat) updates.chats.get(0);
                                        chat.left = false;
                                        chat.kicked = false;
                                        MessagesController.getInstance().putUsers(updates.users, false);
                                        MessagesController.getInstance().putChats(updates.chats, false);
                                        Bundle args = new Bundle();
                                        args.putInt("chat_id", chat.id);
                                        if (MessagesController.checkCanOpenChat(args, JoinGroupAlert.this.fragment)) {
                                            JoinGroupAlert.this.fragment.presentFragment(new ChatActivity(args), JoinGroupAlert.this.fragment instanceof ChatActivity);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                AlertsCreator.processError(error, JoinGroupAlert.this.fragment, req, new Object[0]);
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
            int participants_count;
            int count = JoinGroupAlert.this.chatInvite.participants.size();
            if (JoinGroupAlert.this.chatInvite.chat != null) {
                participants_count = JoinGroupAlert.this.chatInvite.chat.participants_count;
            } else {
                participants_count = JoinGroupAlert.this.chatInvite.participants_count;
            }
            if (count != participants_count) {
                return count + 1;
            }
            return count;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new JoinSheetUserCell(this.context);
            view.setLayoutParams(new LayoutParams(AndroidUtilities.dp(100.0f), AndroidUtilities.dp(90.0f)));
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            JoinSheetUserCell cell = holder.itemView;
            if (position < JoinGroupAlert.this.chatInvite.participants.size()) {
                cell.setUser((User) JoinGroupAlert.this.chatInvite.participants.get(position));
                return;
            }
            int participants_count;
            if (JoinGroupAlert.this.chatInvite.chat != null) {
                participants_count = JoinGroupAlert.this.chatInvite.chat.participants_count;
            } else {
                participants_count = JoinGroupAlert.this.chatInvite.participants_count;
            }
            cell.setCount(participants_count - JoinGroupAlert.this.chatInvite.participants.size());
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public JoinGroupAlert(Context context, TLRPC$ChatInvite invite, String group, BaseFragment parentFragment) {
        Drawable avatarDrawable;
        String title;
        int participants_count;
        super(context, false);
        setApplyBottomPadding(false);
        setApplyTopPadding(false);
        this.fragment = parentFragment;
        this.chatInvite = invite;
        this.hash = group;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setClickable(true);
        setCustomView(linearLayout);
        TLObject photo = null;
        if (invite.chat != null) {
            avatarDrawable = new AvatarDrawable(invite.chat);
            if (this.chatInvite.chat.photo != null) {
                photo = this.chatInvite.chat.photo.photo_small;
            }
            title = invite.chat.title;
            participants_count = invite.chat.participants_count;
        } else {
            avatarDrawable = new AvatarDrawable();
            avatarDrawable.setInfo(0, invite.title, null, false);
            if (this.chatInvite.photo != null) {
                photo = this.chatInvite.photo.photo_small;
            }
            title = invite.title;
            participants_count = invite.participants_count;
        }
        BackupImageView avatarImageView = new BackupImageView(context);
        avatarImageView.setRoundRadius(AndroidUtilities.dp(35.0f));
        avatarImageView.setImage(photo, "50_50", avatarDrawable);
        linearLayout.addView(avatarImageView, LayoutHelper.createLinear(70, 70, 49, 0, 12, 0, 0));
        View textView = new TextView(context);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setTextSize(1, 17.0f);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setText(title);
        textView.setSingleLine(true);
        textView.setEllipsize(TruncateAt.END);
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 49, 10, 10, 10, participants_count > 0 ? 0 : 10));
        if (participants_count > 0) {
            textView = new TextView(context);
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
            textView.setSingleLine(true);
            textView.setEllipsize(TruncateAt.END);
            textView.setText(LocaleController.formatPluralString("Members", participants_count));
            linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 49, 10, 4, 10, 10));
        }
        if (!invite.participants.isEmpty()) {
            RecyclerListView listView = new RecyclerListView(context);
            listView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
            listView.setNestedScrollingEnabled(false);
            listView.setClipToPadding(false);
            listView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            listView.setHorizontalScrollBarEnabled(false);
            listView.setVerticalScrollBarEnabled(false);
            listView.setAdapter(new UsersAdapter(context));
            listView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
            linearLayout.addView(listView, LayoutHelper.createLinear(-2, 90, 49, 0, 0, 0, 0));
        }
        textView = new View(context);
        textView.setBackgroundResource(R.drawable.header_shadow_reverse);
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, 3));
        PickerBottomLayout pickerBottomLayout = new PickerBottomLayout(context, false);
        linearLayout.addView(pickerBottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        pickerBottomLayout.cancelButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        pickerBottomLayout.cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        pickerBottomLayout.cancelButton.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        pickerBottomLayout.cancelButton.setOnClickListener(new C26211());
        pickerBottomLayout.doneButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        pickerBottomLayout.doneButton.setVisibility(0);
        pickerBottomLayout.doneButtonBadgeTextView.setVisibility(8);
        pickerBottomLayout.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        pickerBottomLayout.doneButtonTextView.setText(LocaleController.getString("JoinGroup", R.string.JoinGroup));
        pickerBottomLayout.doneButton.setOnClickListener(new C26242());
    }
}
