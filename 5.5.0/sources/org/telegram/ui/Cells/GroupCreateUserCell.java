package org.telegram.ui.Cells;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.GroupCreateCheckBox;
import org.telegram.ui.Components.LayoutHelper;

public class GroupCreateUserCell extends FrameLayout {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private BackupImageView avatarImageView;
    private GroupCreateCheckBox checkBox;
    private CharSequence currentName;
    private CharSequence currentStatus;
    private User currentUser;
    private FileLocation lastAvatar;
    private String lastName;
    private int lastStatus;
    private SimpleTextView nameTextView;
    private SimpleTextView statusTextView;

    public GroupCreateUserCell(Context context, boolean z) {
        int i = 5;
        super(context);
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(24.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(50, 50.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 11.0f, 11.0f, LocaleController.isRTL ? 11.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setTextSize(17);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 28.0f : 72.0f, 14.0f, LocaleController.isRTL ? 72.0f : 28.0f, BitmapDescriptorFactory.HUE_RED));
        this.statusTextView = new SimpleTextView(context);
        this.statusTextView.setTextSize(16);
        this.statusTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.statusTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 28.0f : 72.0f, 39.0f, LocaleController.isRTL ? 72.0f : 28.0f, BitmapDescriptorFactory.HUE_RED));
        if (z) {
            this.checkBox = new GroupCreateCheckBox(context);
            this.checkBox.setVisibility(0);
            View view = this.checkBox;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createFrame(24, 24.0f, i | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 41.0f, 41.0f, LocaleController.isRTL ? 41.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }
    }

    public User getUser() {
        return this.currentUser;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(72.0f), 1073741824));
    }

    public void recycle() {
        this.avatarImageView.getImageReceiver().cancelLoadImage();
    }

    public void setChecked(boolean z, boolean z2) {
        this.checkBox.setChecked(z, z2);
    }

    public void setUser(User user, CharSequence charSequence, CharSequence charSequence2) {
        this.currentUser = user;
        this.currentStatus = charSequence2;
        this.currentName = charSequence;
        update(0);
    }

    public void update(int i) {
        int i2 = 0;
        if (this.currentUser != null) {
            String str;
            TLObject tLObject = this.currentUser.photo != null ? this.currentUser.photo.photo_small : null;
            if (i != 0) {
                boolean z;
                boolean z2 = (i & 2) != 0 && ((this.lastAvatar != null && tLObject == null) || !(this.lastAvatar != null || tLObject == null || this.lastAvatar == null || tLObject == null || (this.lastAvatar.volume_id == tLObject.volume_id && this.lastAvatar.local_id == tLObject.local_id)));
                if (!(this.currentUser == null || this.currentStatus != null || z2 || (i & 4) == 0)) {
                    if ((this.currentUser.status != null ? this.currentUser.status.expires : 0) != this.lastStatus) {
                        z2 = true;
                    }
                }
                if (z2 || this.currentName != null || this.lastName == null || (i & 1) == 0) {
                    z = z2;
                    str = null;
                } else {
                    String userName = UserObject.getUserName(this.currentUser);
                    if (userName.equals(this.lastName)) {
                        boolean z3 = z2;
                        str = userName;
                        z = z3;
                    } else {
                        str = userName;
                        z = true;
                    }
                }
                if (!z) {
                    return;
                }
            }
            str = null;
            this.avatarDrawable.setInfo(this.currentUser);
            if (this.currentUser.status != null) {
                i2 = this.currentUser.status.expires;
            }
            this.lastStatus = i2;
            if (this.currentName != null) {
                this.lastName = null;
                this.nameTextView.setText(this.currentName, true);
            } else {
                if (str == null) {
                    str = UserObject.getUserName(this.currentUser);
                }
                this.lastName = str;
                this.nameTextView.setText(this.lastName);
            }
            if (this.currentStatus != null) {
                this.statusTextView.setText(this.currentStatus, true);
                this.statusTextView.setTag(Theme.key_groupcreate_offlineText);
                this.statusTextView.setTextColor(Theme.getColor(Theme.key_groupcreate_offlineText));
            } else if (this.currentUser.bot) {
                this.statusTextView.setTag(Theme.key_groupcreate_offlineText);
                this.statusTextView.setTextColor(Theme.getColor(Theme.key_groupcreate_offlineText));
                this.statusTextView.setText(LocaleController.getString("Bot", R.string.Bot));
            } else if (this.currentUser.id == UserConfig.getClientUserId() || ((this.currentUser.status != null && this.currentUser.status.expires > ConnectionsManager.getInstance().getCurrentTime()) || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(this.currentUser.id)))) {
                this.statusTextView.setTag(Theme.key_groupcreate_offlineText);
                this.statusTextView.setTextColor(Theme.getColor(Theme.key_groupcreate_onlineText));
                this.statusTextView.setText(LocaleController.getString("Online", R.string.Online));
            } else {
                this.statusTextView.setTag(Theme.key_groupcreate_offlineText);
                this.statusTextView.setTextColor(Theme.getColor(Theme.key_groupcreate_offlineText));
                this.statusTextView.setText(LocaleController.formatUserStatus(this.currentUser));
            }
            this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
        }
    }
}
