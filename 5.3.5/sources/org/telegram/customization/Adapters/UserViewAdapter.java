package org.telegram.customization.Adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

public class UserViewAdapter extends Adapter<UserViewAdapterHolder> {
    private final OnQuickAccessClickListener onQuickAccessClickListener;

    public interface OnQuickAccessClickListener {
        void onClick(User user, TLRPC$Chat tLRPC$Chat);
    }

    class UserViewAdapterHolder extends ViewHolder {
        BackupImageView avatar;
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        TextView name;

        public UserViewAdapterHolder(View itemView) {
            super(itemView);
            this.avatar = (BackupImageView) itemView.findViewById(R.id.userAvatar);
            this.avatar.setRoundRadius(AndroidUtilities.dp(50.0f));
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.name.setTextColor(-16777216);
        }
    }

    public UserViewAdapter(OnQuickAccessClickListener onQuickAccessClickListener) {
        this.onQuickAccessClickListener = onQuickAccessClickListener;
    }

    public UserViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_access_to_users_item, parent, false));
    }

    ArrayList<TLRPC$TL_dialog> getData() {
        return MessagesController.getInstance().dialogs;
    }

    public void onBindViewHolder(UserViewAdapterHolder holder, int position) {
        if (getData() != null) {
            User user = null;
            TLRPC$Chat chat = null;
            long currentDialogId = ((TLRPC$TL_dialog) getData().get(position)).id;
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
                String str;
                if (user.photo != null) {
                    photo = user.photo.photo_small;
                }
                holder.avatarDrawable.setInfo(user);
                StringBuilder stringBuilder = new StringBuilder();
                if (user.first_name == null) {
                    str = "";
                } else {
                    str = user.first_name + " ";
                }
                stringBuilder = stringBuilder.append(str);
                if (user.last_name == null) {
                    str = "";
                } else {
                    str = user.last_name;
                }
                holder.name.setText(stringBuilder.append(str).toString());
                holder.avatar.setImage(photo, "50_50", holder.avatarDrawable);
                final User finalUser = user;
                holder.avatar.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        UserViewAdapter.this.onQuickAccessClickListener.onClick(finalUser, null);
                    }
                });
            } else if (chat != null) {
                if (chat.photo != null) {
                    photo = chat.photo.photo_small;
                }
                holder.avatarDrawable.setInfo(chat);
                holder.name.setText(chat.title == null ? "" : chat.title);
                holder.avatar.setImage(photo, "50_50", holder.avatarDrawable);
                final TLRPC$Chat finalChat = chat;
                holder.avatar.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        UserViewAdapter.this.onQuickAccessClickListener.onClick(null, finalChat);
                    }
                });
            }
        }
    }

    public int getItemCount() {
        if (getData() == null) {
            return 0;
        }
        return getData().size();
    }
}
