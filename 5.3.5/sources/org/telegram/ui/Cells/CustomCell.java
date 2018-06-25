package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.ir.talaeii.R;
import org.telegram.customization.util.AppUtilities;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.CheckBoxSquare;
import org.telegram.ui.Components.LayoutHelper;

public class CustomCell extends FrameLayout {
    private ImageView adminImage;
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private BackupImageView avatarImageView;
    private CheckBox checkBox;
    private CheckBoxSquare checkBoxBig;
    private int currentDrawable;
    private CharSequence currentName;
    private TLObject currentObject;
    private CharSequence currrntStatus;
    private ImageView imageView;
    private ImageView ivMutual;
    private TLRPC$FileLocation lastAvatar;
    private String lastName;
    private int lastStatus;
    private SimpleTextView nameTextView;
    private final boolean showMatual = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("mutualShow", false);
    private int statusColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);
    private int statusOnlineColor = Theme.getColor(Theme.key_windowBackgroundWhiteBlueText);
    private SimpleTextView statusTextView;

    public CustomCell(Context context, int padding, int checkbox, boolean admin) {
        float f;
        float f2;
        super(context);
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(24.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 0.0f : (float) (padding + 7), 8.0f, LocaleController.isRTL ? (float) (padding + 7) : 0.0f, 0.0f));
        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTypeface(AppUtilities.getLightSansTypeface(context));
        this.nameTextView.setTextSize(17);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        View view = this.nameTextView;
        int i = (LocaleController.isRTL ? 5 : 3) | 48;
        if (LocaleController.isRTL) {
            f = (float) ((checkbox == 2 ? 18 : 0) + 28);
        } else {
            f = (float) (padding + 68);
        }
        if (LocaleController.isRTL) {
            f2 = (float) (padding + 68);
        } else {
            f2 = (float) ((checkbox == 2 ? 18 : 0) + 28);
        }
        addView(view, LayoutHelper.createFrame(-1, 20.0f, i, f, 11.5f, f2, 0.0f));
        this.statusTextView = new SimpleTextView(context);
        this.statusTextView.setTypeface(AppUtilities.getLightSansTypeface(context));
        this.statusTextView.setTextSize(14);
        this.statusTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.statusTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 28.0f : (float) (padding + 68), 34.5f, LocaleController.isRTL ? (float) (padding + 68) : 28.0f, 0.0f));
        this.imageView = new ImageView(context);
        this.imageView.setScaleType(ScaleType.CENTER);
        this.imageView.setVisibility(8);
        addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 16, LocaleController.isRTL ? 0.0f : 16.0f, 0.0f, LocaleController.isRTL ? 16.0f : 0.0f, 0.0f));
        this.ivMutual = new ImageView(context);
        this.ivMutual.setScaleType(ScaleType.CENTER);
        this.ivMutual.setVisibility(8);
        this.ivMutual.setBackgroundResource(R.drawable.account_switch);
        addView(this.ivMutual, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 3 : 5) | 16, LocaleController.isRTL ? 16.0f : 16.0f, 0.0f, LocaleController.isRTL ? 16.0f : 16.0f, 0.0f));
        if (checkbox == 2) {
            this.checkBoxBig = new CheckBoxSquare(context, false);
            addView(this.checkBoxBig, LayoutHelper.createFrame(18, 18.0f, (LocaleController.isRTL ? 3 : 5) | 16, LocaleController.isRTL ? 19.0f : 0.0f, 0.0f, LocaleController.isRTL ? 0.0f : 19.0f, 0.0f));
        } else if (checkbox == 1) {
            this.checkBox = new CheckBox(context, R.drawable.round_check2);
            this.checkBox.setVisibility(4);
            this.checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
            addView(this.checkBox, LayoutHelper.createFrame(22, 22.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 0.0f : (float) (padding + 37), 38.0f, LocaleController.isRTL ? (float) (padding + 37) : 0.0f, 0.0f));
        }
        if (admin) {
            this.adminImage = new ImageView(context);
            this.adminImage.setImageResource(R.drawable.admin_star);
            addView(this.adminImage, LayoutHelper.createFrame(16, 16.0f, (LocaleController.isRTL ? 3 : 5) | 48, LocaleController.isRTL ? 24.0f : 0.0f, 13.5f, LocaleController.isRTL ? 0.0f : 24.0f, 0.0f));
        }
    }

    public void setIsAdmin(int value) {
        if (this.adminImage != null) {
            int i;
            this.adminImage.setVisibility(value != 0 ? 0 : 8);
            SimpleTextView simpleTextView = this.nameTextView;
            if (!LocaleController.isRTL || value == 0) {
                i = 0;
            } else {
                i = AndroidUtilities.dp(16.0f);
            }
            int dp = (LocaleController.isRTL || value == 0) ? 0 : AndroidUtilities.dp(16.0f);
            simpleTextView.setPadding(i, 0, dp, 0);
            if (value == 1) {
                setTag(Theme.key_profile_creatorIcon);
                this.adminImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_creatorIcon), Mode.MULTIPLY));
            } else if (value == 2) {
                setTag(Theme.key_profile_adminIcon);
                this.adminImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_adminIcon), Mode.MULTIPLY));
            }
        }
    }

    public void setData(TLObject user, CharSequence name, CharSequence status, int resId) {
        if (user == null) {
            this.currrntStatus = null;
            this.currentName = null;
            this.currentObject = null;
            this.nameTextView.setText("");
            this.statusTextView.setText("");
            this.avatarImageView.setImageDrawable(null);
            return;
        }
        this.currrntStatus = status;
        this.currentName = name;
        this.currentObject = user;
        this.currentDrawable = resId;
        update(0);
    }

    public void setChecked(boolean checked, boolean animated) {
        if (this.checkBox != null) {
            if (this.checkBox.getVisibility() != 0) {
                this.checkBox.setVisibility(0);
            }
            this.checkBox.setChecked(checked, animated);
        } else if (this.checkBoxBig != null) {
            if (this.checkBoxBig.getVisibility() != 0) {
                this.checkBoxBig.setVisibility(0);
            }
            this.checkBoxBig.setChecked(checked, animated);
        }
    }

    public void setCheckDisabled(boolean disabled) {
        if (this.checkBoxBig != null) {
            this.checkBoxBig.setDisabled(disabled);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(64.0f), 1073741824));
    }

    public void setStatusColors(int color, int onlineColor) {
        this.statusColor = color;
        this.statusOnlineColor = onlineColor;
    }

    public void invalidate() {
        super.invalidate();
        if (this.checkBoxBig != null) {
            this.checkBoxBig.invalidate();
        }
    }

    public void update(int mask) {
        int i = 8;
        if (this.currentObject != null) {
            TLRPC$FileLocation photo = null;
            String newName = null;
            User currentUser = null;
            TLRPC$Chat currentChat = null;
            if (this.currentObject instanceof User) {
                currentUser = this.currentObject;
                if (currentUser.photo != null) {
                    photo = currentUser.photo.photo_small;
                }
            } else {
                currentChat = this.currentObject;
                if (currentChat.photo != null) {
                    photo = currentChat.photo.photo_small;
                }
            }
            if (mask != 0) {
                boolean continueUpdate = false;
                if ((mask & 2) != 0 && ((this.lastAvatar != null && photo == null) || !(this.lastAvatar != null || photo == null || this.lastAvatar == null || photo == null || (this.lastAvatar.volume_id == photo.volume_id && this.lastAvatar.local_id == photo.local_id)))) {
                    continueUpdate = true;
                }
                if (!(currentUser == null || continueUpdate || (mask & 4) == 0)) {
                    int newStatus = 0;
                    if (currentUser.status != null) {
                        newStatus = currentUser.status.expires;
                    }
                    if (newStatus != this.lastStatus) {
                        continueUpdate = true;
                    }
                }
                if (!(continueUpdate || this.currentName != null || this.lastName == null || (mask & 1) == 0)) {
                    if (currentUser != null) {
                        newName = UserObject.getUserName(currentUser);
                    } else {
                        newName = currentChat.title;
                    }
                    if (!newName.equals(this.lastName)) {
                        continueUpdate = true;
                    }
                }
                if (!continueUpdate) {
                    return;
                }
            }
            if (currentUser != null) {
                this.avatarDrawable.setInfo(currentUser);
                if (currentUser.status != null) {
                    this.lastStatus = currentUser.status.expires;
                } else {
                    this.lastStatus = 0;
                }
            } else {
                this.avatarDrawable.setInfo(currentChat);
            }
            if (this.currentName != null) {
                this.lastName = null;
                this.nameTextView.setText(this.currentName);
            } else {
                if (currentUser != null) {
                    if (newName == null) {
                        newName = UserObject.getUserName(currentUser);
                    }
                    this.lastName = newName;
                } else {
                    if (newName == null) {
                        newName = currentChat.title;
                    }
                    this.lastName = newName;
                }
                this.nameTextView.setText(this.lastName);
            }
            if (this.currrntStatus != null) {
                this.statusTextView.setTextColor(this.statusColor);
                this.statusTextView.setText(this.currrntStatus);
            } else if (currentUser != null) {
                if (currentUser.bot) {
                    this.statusTextView.setTextColor(this.statusColor);
                    if (currentUser.bot_chat_history || (this.adminImage != null && this.adminImage.getVisibility() == 0)) {
                        this.statusTextView.setText(LocaleController.getString("BotStatusRead", R.string.BotStatusRead));
                    } else {
                        this.statusTextView.setText(LocaleController.getString("BotStatusCantRead", R.string.BotStatusCantRead));
                    }
                } else if (currentUser.id == UserConfig.getClientUserId() || ((currentUser.status != null && currentUser.status.expires > ConnectionsManager.getInstance().getCurrentTime()) || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(currentUser.id)))) {
                    this.statusTextView.setTextColor(this.statusOnlineColor);
                    this.statusTextView.setText(LocaleController.getString("Online", R.string.Online));
                } else {
                    this.statusTextView.setTextColor(this.statusColor);
                    this.statusTextView.setText(LocaleController.formatUserStatus(currentUser));
                }
            }
            if ((this.imageView.getVisibility() == 0 && this.currentDrawable == 0) || (this.imageView.getVisibility() == 8 && this.currentDrawable != 0)) {
                ImageView imageView = this.imageView;
                if (this.currentDrawable != 0) {
                    i = 0;
                }
                imageView.setVisibility(i);
                this.imageView.setImageResource(this.currentDrawable);
            }
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
