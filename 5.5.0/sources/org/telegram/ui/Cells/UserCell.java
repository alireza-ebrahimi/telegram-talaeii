package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.CheckBoxSquare;
import org.telegram.ui.Components.LayoutHelper;

public class UserCell extends FrameLayout {
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
    boolean isMutual;
    private ImageView ivMutual;
    private FileLocation lastAvatar;
    private String lastName;
    private int lastStatus;
    private SimpleTextView nameTextView;
    private boolean showMatual = false;
    private int statusColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);
    private int statusOnlineColor = Theme.getColor(Theme.key_windowBackgroundWhiteBlueText);
    private SimpleTextView statusTextView;

    public UserCell(Context context, int i, int i2, boolean z) {
        float f;
        float f2;
        super(context);
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(24.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : (float) (i + 7), 8.0f, LocaleController.isRTL ? (float) (i + 7) : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTextSize(17);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        View view = this.nameTextView;
        int i3 = (LocaleController.isRTL ? 5 : 3) | 48;
        if (LocaleController.isRTL) {
            f = (float) ((i2 == 2 ? 18 : 0) + 28);
        } else {
            f = (float) (i + 68);
        }
        if (LocaleController.isRTL) {
            f2 = (float) (i + 68);
        } else {
            f2 = (float) ((i2 == 2 ? 18 : 0) + 28);
        }
        addView(view, LayoutHelper.createFrame(-1, 20.0f, i3, f, 11.5f, f2, BitmapDescriptorFactory.HUE_RED));
        this.statusTextView = new SimpleTextView(context);
        this.statusTextView.setTextSize(14);
        this.statusTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.statusTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.statusTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 28.0f : (float) (i + 68), 34.5f, LocaleController.isRTL ? (float) (i + 68) : 28.0f, BitmapDescriptorFactory.HUE_RED));
        this.imageView = new ImageView(context);
        this.imageView.setScaleType(ScaleType.CENTER);
        this.imageView.setVisibility(8);
        addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 16, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 16.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 16.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        addMatual(context);
        if (i2 == 2) {
            this.checkBoxBig = new CheckBoxSquare(context, false);
            addView(this.checkBoxBig, LayoutHelper.createFrame(18, 18.0f, (LocaleController.isRTL ? 3 : 5) | 16, LocaleController.isRTL ? 19.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 19.0f, BitmapDescriptorFactory.HUE_RED));
        } else if (i2 == 1) {
            this.checkBox = new CheckBox(context, R.drawable.round_check2);
            this.checkBox.setVisibility(4);
            this.checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
            addView(this.checkBox, LayoutHelper.createFrame(22, 22.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : (float) (i + 37), 38.0f, LocaleController.isRTL ? (float) (i + 37) : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        }
        if (z) {
            this.adminImage = new ImageView(context);
            this.adminImage.setImageResource(R.drawable.admin_star);
            addView(this.adminImage, LayoutHelper.createFrame(16, 16.0f, (LocaleController.isRTL ? 3 : 5) | 48, LocaleController.isRTL ? 24.0f : BitmapDescriptorFactory.HUE_RED, 13.5f, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 24.0f, BitmapDescriptorFactory.HUE_RED));
        }
    }

    private void addMatual(Context context) {
        this.showMatual = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("mutualShow", false);
        this.ivMutual = new ImageView(context);
        this.ivMutual.setScaleType(ScaleType.CENTER);
        this.ivMutual.setVisibility(8);
        this.ivMutual.setBackgroundResource(R.drawable.account_switch);
        View view = this.ivMutual;
        int i = (LocaleController.isRTL ? 3 : 5) | 16;
        if (LocaleController.isRTL) {
        }
        if (LocaleController.isRTL) {
            addView(view, LayoutHelper.createFrame(-2, -2.0f, i, 16.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        } else {
            addView(view, LayoutHelper.createFrame(-2, -2.0f, i, 16.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void invalidate() {
        super.invalidate();
        if (this.checkBoxBig != null) {
            this.checkBoxBig.invalidate();
        }
    }

    public boolean isMutual() {
        return this.isMutual;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(64.0f), 1073741824));
    }

    public void setCheckDisabled(boolean z) {
        if (this.checkBoxBig != null) {
            this.checkBoxBig.setDisabled(z);
        }
    }

    public void setChecked(boolean z, boolean z2) {
        if (this.checkBox != null) {
            if (this.checkBox.getVisibility() != 0) {
                this.checkBox.setVisibility(0);
            }
            this.checkBox.setChecked(z, z2);
        } else if (this.checkBoxBig != null) {
            if (this.checkBoxBig.getVisibility() != 0) {
                this.checkBoxBig.setVisibility(0);
            }
            this.checkBoxBig.setChecked(z, z2);
        }
    }

    public void setData(TLObject tLObject, CharSequence charSequence, CharSequence charSequence2, int i) {
        if (tLObject == null) {
            this.currrntStatus = null;
            this.currentName = null;
            this.currentObject = null;
            this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.statusTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.avatarImageView.setImageDrawable(null);
            return;
        }
        this.currrntStatus = charSequence2;
        this.currentName = charSequence;
        this.currentObject = tLObject;
        this.currentDrawable = i;
        update(0);
    }

    public void setIsAdmin(int i) {
        if (this.adminImage != null) {
            this.adminImage.setVisibility(i != 0 ? 0 : 8);
            SimpleTextView simpleTextView = this.nameTextView;
            int dp = (!LocaleController.isRTL || i == 0) ? 0 : AndroidUtilities.dp(16.0f);
            int dp2 = (LocaleController.isRTL || i == 0) ? 0 : AndroidUtilities.dp(16.0f);
            simpleTextView.setPadding(dp, 0, dp2, 0);
            if (i == 1) {
                setTag(Theme.key_profile_creatorIcon);
                this.adminImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_creatorIcon), Mode.MULTIPLY));
            } else if (i == 2) {
                setTag(Theme.key_profile_adminIcon);
                this.adminImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_adminIcon), Mode.MULTIPLY));
            }
        }
    }

    public void setMutual(boolean z) {
        if (this.showMatual) {
            this.isMutual = z;
            if (this.isMutual) {
                this.ivMutual.setVisibility(0);
            } else {
                this.ivMutual.setVisibility(8);
            }
        }
    }

    public void setStatusColors(int i, int i2) {
        this.statusColor = i;
        this.statusOnlineColor = i2;
    }

    public void update(int i) {
        int i2 = 1;
        int i3 = 0;
        if (this.currentObject != null) {
            Chat chat;
            User user;
            TLObject tLObject;
            String str;
            if (this.currentObject instanceof User) {
                User user2 = (User) this.currentObject;
                if (user2.photo != null) {
                    chat = null;
                    user = user2;
                    tLObject = user2.photo.photo_small;
                } else {
                    chat = null;
                    user = user2;
                    tLObject = null;
                }
            } else {
                Chat chat2 = (Chat) this.currentObject;
                if (chat2.photo != null) {
                    chat = chat2;
                    user = null;
                    tLObject = chat2.photo.photo_small;
                } else {
                    chat = chat2;
                    user = null;
                    tLObject = null;
                }
            }
            if (i != 0) {
                int i4 = ((i & 2) == 0 || ((this.lastAvatar == null || tLObject != null) && (this.lastAvatar != null || tLObject == null || this.lastAvatar == null || tLObject == null || (this.lastAvatar.volume_id == tLObject.volume_id && this.lastAvatar.local_id == tLObject.local_id)))) ? 0 : 1;
                if (!(user == null || i4 != 0 || (i & 4) == 0)) {
                    if ((user.status != null ? user.status.expires : 0) != this.lastStatus) {
                        i4 = 1;
                    }
                }
                if (i4 != 0 || this.currentName != null || this.lastName == null || (i & 1) == 0) {
                    i2 = i4;
                    str = null;
                } else {
                    str = user != null ? UserObject.getUserName(user) : chat.title;
                    if (str.equals(this.lastName)) {
                        i2 = i4;
                    }
                }
                if (i2 == 0) {
                    return;
                }
            }
            str = null;
            if (user != null) {
                this.avatarDrawable.setInfo(user);
                if (user.status != null) {
                    this.lastStatus = user.status.expires;
                } else {
                    this.lastStatus = 0;
                }
            } else {
                this.avatarDrawable.setInfo(chat);
            }
            if (this.currentName != null) {
                this.lastName = null;
                this.nameTextView.setText(this.currentName);
            } else {
                if (user != null) {
                    if (str == null) {
                        str = UserObject.getUserName(user);
                    }
                    this.lastName = str;
                } else {
                    if (str == null) {
                        str = chat.title;
                    }
                    this.lastName = str;
                }
                this.nameTextView.setText(this.lastName);
            }
            if (this.currrntStatus != null) {
                this.statusTextView.setTextColor(this.statusColor);
                this.statusTextView.setText(this.currrntStatus);
            } else if (user != null) {
                if (user.bot) {
                    this.statusTextView.setTextColor(this.statusColor);
                    if (user.bot_chat_history || (this.adminImage != null && this.adminImage.getVisibility() == 0)) {
                        this.statusTextView.setText(LocaleController.getString("BotStatusRead", R.string.BotStatusRead));
                    } else {
                        this.statusTextView.setText(LocaleController.getString("BotStatusCantRead", R.string.BotStatusCantRead));
                    }
                } else if (user.id == UserConfig.getClientUserId() || ((user.status != null && user.status.expires > ConnectionsManager.getInstance().getCurrentTime()) || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(user.id)))) {
                    this.statusTextView.setTextColor(this.statusOnlineColor);
                    this.statusTextView.setText(LocaleController.getString("Online", R.string.Online));
                } else {
                    this.statusTextView.setTextColor(this.statusColor);
                    this.statusTextView.setText(LocaleController.formatUserStatus(user));
                }
            }
            if ((this.imageView.getVisibility() == 0 && this.currentDrawable == 0) || (this.imageView.getVisibility() == 8 && this.currentDrawable != 0)) {
                ImageView imageView = this.imageView;
                if (this.currentDrawable == 0) {
                    i3 = 8;
                }
                imageView.setVisibility(i3);
                this.imageView.setImageResource(this.currentDrawable);
            }
            this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
        }
    }
}
