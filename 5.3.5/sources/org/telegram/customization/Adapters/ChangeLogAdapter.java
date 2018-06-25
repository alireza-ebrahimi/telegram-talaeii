package org.telegram.customization.Adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

public class ChangeLogAdapter extends Adapter<ChangeLogHolder> {
    ArrayList<ContactChangeLog> changeLogs = new ArrayList();
    private final OnQuickAccessClickListener onQuickAccessClickListener;

    public interface OnQuickAccessClickListener {
        void onClick(User user, TLRPC$Chat tLRPC$Chat);
    }

    class ChangeLogHolder extends ViewHolder {
        BackupImageView avatar;
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        View divider;
        View root;
        TextView txtChangeLog;
        TextView txtDate;
        TextView txtUser;

        public ChangeLogHolder(View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.avatar = (BackupImageView) itemView.findViewById(R.id.userAvatar);
            this.avatar.setRoundRadius(AndroidUtilities.dp(50.0f));
            this.txtDate = (TextView) itemView.findViewById(R.id.date);
            this.txtUser = (TextView) itemView.findViewById(R.id.user);
            this.txtChangeLog = (TextView) itemView.findViewById(R.id.log);
            this.divider = itemView.findViewById(R.id.divider);
        }
    }

    public ChangeLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChangeLogHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.change_log_item, parent, false));
    }

    public ChangeLogAdapter(OnQuickAccessClickListener onQuickAccessClickListener, ArrayList<ContactChangeLog> data) {
        this.onQuickAccessClickListener = onQuickAccessClickListener;
        this.changeLogs = data;
    }

    public ArrayList<ContactChangeLog> getData() {
        return this.changeLogs;
    }

    public void onBindViewHolder(ChangeLogHolder holder, int position) {
        User user = null;
        long currentDialogId = ((ContactChangeLog) getData().get(position)).getChatId();
        int lower_id = (int) currentDialogId;
        int high_id = (int) (currentDialogId >> 32);
        if (lower_id == 0) {
            TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id));
            if (encryptedChat != null) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
            }
        } else if (high_id == 1) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(lower_id));
        } else if (lower_id < 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
        } else {
            user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
        }
        TLObject photo = null;
        if (user != null) {
            if (user.photo != null) {
                photo = user.photo.photo_small;
            }
            holder.avatarDrawable.setInfo(user);
            String name = (user.first_name == null ? "" : user.first_name + " ") + (user.last_name == null ? "" : user.last_name);
            TextView textView = holder.txtUser;
            if (name == null) {
                name = "";
            }
            textView.setText(name);
            holder.txtUser.setTextColor(Theme.getColor(Theme.key_chats_name));
            holder.txtChangeLog.setText(ContactChangeLog.getLogStringByType(((ContactChangeLog) getData().get(position)).getType()));
            holder.txtChangeLog.setTextColor(Theme.getColor(Theme.key_chats_message));
            holder.avatar.setImage(photo, "50_50", holder.avatarDrawable);
            final User finalUser = user;
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ChangeLogAdapter.this.onQuickAccessClickListener.onClick(finalUser, null);
                }
            });
        }
        holder.txtDate.setText(AppUtilities.getPersianDate(((ContactChangeLog) getData().get(position)).getDate()));
        holder.root.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        holder.divider.setBackgroundColor(Theme.getColor(Theme.key_divider));
    }

    public int getItemCount() {
        if (getData() == null) {
            return 0;
        }
        return getData().size();
    }

    public static SpannableStringBuilder makeSectionOfTextBold(String text, String textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (textToBold.length() <= 0 || textToBold.trim().equals("")) {
            return builder.append(text);
        }
        String testText = text.toLowerCase(Locale.US);
        String testTextToBold = textToBold.toLowerCase(Locale.US);
        int startingIndex = testText.indexOf(testTextToBold);
        int endingIndex = startingIndex + testTextToBold.length();
        if (startingIndex < 0 || endingIndex < 0) {
            return builder.append(text);
        }
        if (startingIndex < 0 || endingIndex < 0) {
            return builder;
        }
        builder.append(text);
        builder.setSpan(new StyleSpan(1), startingIndex, endingIndex, 0);
        return builder;
    }
}
