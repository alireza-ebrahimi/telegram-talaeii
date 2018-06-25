package org.telegram.ui.Cells;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;

public class ShareDialogCell extends FrameLayout {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private CheckBox checkBox;
    private BackupImageView imageView;
    private TextView nameTextView;

    public ShareDialogCell(Context context) {
        super(context);
        this.imageView = new BackupImageView(context);
        this.imageView.setRoundRadius(AndroidUtilities.dp(27.0f));
        addView(this.imageView, LayoutHelper.createFrame(54, 54.0f, 49, 0.0f, 7.0f, 0.0f, 0.0f));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.nameTextView.setTextSize(1, 12.0f);
        this.nameTextView.setMaxLines(2);
        this.nameTextView.setGravity(49);
        this.nameTextView.setLines(2);
        this.nameTextView.setEllipsize(TruncateAt.END);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 6.0f, 64.0f, 6.0f, 0.0f));
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setSize(24);
        this.checkBox.setCheckOffset(AndroidUtilities.dp(1.0f));
        this.checkBox.setVisibility(0);
        this.checkBox.setColor(Theme.getColor(Theme.key_dialogRoundCheckBox), Theme.getColor(Theme.key_dialogRoundCheckBoxCheck));
        addView(this.checkBox, LayoutHelper.createFrame(24, 24.0f, 49, 17.0f, 39.0f, 0.0f, 0.0f));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
    }

    public void setDialog(int uid, boolean checked, CharSequence name) {
        TLObject photo = null;
        if (uid > 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
            this.avatarDrawable.setInfo(user);
            if (UserObject.isUserSelf(user)) {
                this.nameTextView.setText(LocaleController.getString("SavedMessages", R.string.SavedMessages));
                this.avatarDrawable.setSavedMessages(1);
            } else {
                if (name != null) {
                    this.nameTextView.setText(name);
                } else if (user != null) {
                    this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                } else {
                    this.nameTextView.setText("");
                }
                if (!(user == null || user.photo == null)) {
                    photo = user.photo.photo_small;
                }
            }
        } else {
            TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-uid));
            if (name != null) {
                this.nameTextView.setText(name);
            } else if (chat != null) {
                this.nameTextView.setText(chat.title);
            } else {
                this.nameTextView.setText("");
            }
            this.avatarDrawable.setInfo(chat);
            if (!(chat == null || chat.photo == null)) {
                photo = chat.photo.photo_small;
            }
        }
        this.imageView.setImage(photo, "50_50", this.avatarDrawable);
        this.checkBox.setChecked(checked, false);
    }

    public void setChecked(boolean checked, boolean animated) {
        this.checkBox.setChecked(checked, animated);
    }
}
