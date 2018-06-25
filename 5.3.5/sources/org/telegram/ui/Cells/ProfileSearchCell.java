package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;

public class ProfileSearchCell extends BaseCell {
    private AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage = new ImageReceiver(this);
    private TLRPC$Chat chat;
    private StaticLayout countLayout;
    private int countLeft;
    private int countTop = AndroidUtilities.dp(25.0f);
    private int countWidth;
    private CharSequence currentName;
    private long dialog_id;
    private boolean drawCheck;
    private boolean drawCount;
    private boolean drawNameBot;
    private boolean drawNameBroadcast;
    private boolean drawNameGroup;
    private boolean drawNameLock;
    private TLRPC$EncryptedChat encryptedChat;
    private TLRPC$FileLocation lastAvatar;
    private String lastName;
    private int lastStatus;
    private int lastUnreadCount;
    private StaticLayout nameLayout;
    private int nameLeft;
    private int nameLockLeft;
    private int nameLockTop;
    private int nameTop;
    private StaticLayout onlineLayout;
    private int onlineLeft;
    private int paddingRight;
    private RectF rect = new RectF();
    private boolean savedMessages;
    private CharSequence subLabel;
    public boolean useSeparator;
    private User user;

    public ProfileSearchCell(Context context) {
        super(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(26.0f));
        this.avatarDrawable = new AvatarDrawable();
    }

    public void setData(TLObject object, TLRPC$EncryptedChat ec, CharSequence n, CharSequence s, boolean needCount, boolean saved) {
        this.currentName = n;
        if (object instanceof User) {
            this.user = (User) object;
            this.chat = null;
        } else if (object instanceof TLRPC$Chat) {
            this.chat = (TLRPC$Chat) object;
            this.user = null;
        }
        this.encryptedChat = ec;
        this.subLabel = s;
        this.drawCount = needCount;
        this.savedMessages = saved;
        update(0);
    }

    public void setPaddingRight(int padding) {
        this.paddingRight = padding;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.avatarImage.onDetachedFromWindow();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), AndroidUtilities.dp(72.0f));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!(this.user == null && this.chat == null && this.encryptedChat == null) && changed) {
            buildLayout();
        }
    }

    public void buildLayout() {
        CharSequence nameString;
        TextPaint currentNamePaint;
        int nameWidth;
        int onlineWidth;
        int avatarLeft;
        this.drawNameBroadcast = false;
        this.drawNameLock = false;
        this.drawNameGroup = false;
        this.drawCheck = false;
        this.drawNameBot = false;
        if (this.encryptedChat != null) {
            this.drawNameLock = true;
            this.dialog_id = ((long) this.encryptedChat.id) << 32;
            if (LocaleController.isRTL) {
                this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 2))) - Theme.dialogs_lockDrawable.getIntrinsicWidth();
                this.nameLeft = AndroidUtilities.dp(11.0f);
            } else {
                this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
            }
            this.nameLockTop = AndroidUtilities.dp(16.5f);
        } else if (this.chat != null) {
            if (this.chat.id < 0) {
                this.dialog_id = AndroidUtilities.makeBroadcastId(this.chat.id);
                this.drawNameBroadcast = true;
                this.nameLockTop = AndroidUtilities.dp(28.5f);
            } else {
                this.dialog_id = (long) (-this.chat.id);
                if (!ChatObject.isChannel(this.chat) || this.chat.megagroup) {
                    this.drawNameGroup = true;
                    this.nameLockTop = AndroidUtilities.dp(30.0f);
                } else {
                    this.drawNameBroadcast = true;
                    this.nameLockTop = AndroidUtilities.dp(28.5f);
                }
            }
            this.drawCheck = this.chat.verified;
            if (LocaleController.isRTL) {
                this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 2))) - (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth());
                this.nameLeft = AndroidUtilities.dp(11.0f);
            } else {
                int intrinsicWidth;
                this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                int dp = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
                if (this.drawNameGroup) {
                    intrinsicWidth = Theme.dialogs_groupDrawable.getIntrinsicWidth();
                } else {
                    intrinsicWidth = Theme.dialogs_broadcastDrawable.getIntrinsicWidth();
                }
                this.nameLeft = intrinsicWidth + dp;
            }
        } else {
            this.dialog_id = (long) this.user.id;
            if (LocaleController.isRTL) {
                this.nameLeft = AndroidUtilities.dp(11.0f);
            } else {
                this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            if (this.user.bot) {
                this.drawNameBot = true;
                if (LocaleController.isRTL) {
                    this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 2))) - Theme.dialogs_botDrawable.getIntrinsicWidth();
                    this.nameLeft = AndroidUtilities.dp(11.0f);
                } else {
                    this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                    this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_botDrawable.getIntrinsicWidth();
                }
                this.nameLockTop = AndroidUtilities.dp(16.5f);
            } else {
                this.nameLockTop = AndroidUtilities.dp(17.0f);
            }
            this.drawCheck = this.user.verified;
        }
        if (this.currentName != null) {
            nameString = this.currentName;
        } else {
            String nameString2 = "";
            if (this.chat != null) {
                nameString2 = this.chat.title;
            } else if (this.user != null) {
                nameString2 = UserObject.getUserName(this.user);
            }
            nameString = nameString2.replace('\n', ' ');
        }
        if (nameString.length() == 0) {
            if (this.user == null || this.user.phone == null || this.user.phone.length() == 0) {
                nameString = LocaleController.getString("HiddenName", R.string.HiddenName);
            } else {
                nameString = PhoneFormat.getInstance().format("+" + this.user.phone);
            }
        }
        if (this.encryptedChat != null) {
            currentNamePaint = Theme.dialogs_nameEncryptedPaint;
        } else {
            currentNamePaint = Theme.dialogs_namePaint;
        }
        if (LocaleController.isRTL) {
            nameWidth = (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            onlineWidth = nameWidth;
        } else {
            nameWidth = (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp(14.0f);
            onlineWidth = nameWidth;
        }
        if (this.drawNameLock) {
            nameWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
        } else if (this.drawNameBroadcast) {
            nameWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_broadcastDrawable.getIntrinsicWidth();
        } else if (this.drawNameGroup) {
            nameWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_groupDrawable.getIntrinsicWidth();
        } else if (this.drawNameBot) {
            nameWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_botDrawable.getIntrinsicWidth();
        }
        int nameWidth2 = nameWidth - this.paddingRight;
        onlineWidth -= this.paddingRight;
        if (this.drawCount) {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (dialog == null || dialog.unread_count == 0) {
                this.lastUnreadCount = 0;
                this.countLayout = null;
                nameWidth = nameWidth2;
            } else {
                this.lastUnreadCount = dialog.unread_count;
                String countString = String.format("%d", new Object[]{Integer.valueOf(dialog.unread_count)});
                this.countWidth = Math.max(AndroidUtilities.dp(12.0f), (int) Math.ceil((double) Theme.dialogs_countTextPaint.measureText(countString)));
                this.countLayout = new StaticLayout(countString, Theme.dialogs_countTextPaint, this.countWidth, Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
                int w = this.countWidth + AndroidUtilities.dp(18.0f);
                nameWidth = nameWidth2 - w;
                if (LocaleController.isRTL) {
                    this.countLeft = AndroidUtilities.dp(19.0f);
                    this.nameLeft += w;
                } else {
                    this.countLeft = (getMeasuredWidth() - this.countWidth) - AndroidUtilities.dp(19.0f);
                }
            }
        } else {
            this.lastUnreadCount = 0;
            this.countLayout = null;
            nameWidth = nameWidth2;
        }
        this.nameLayout = new StaticLayout(TextUtils.ellipsize(nameString, currentNamePaint, (float) (nameWidth - AndroidUtilities.dp(12.0f)), TruncateAt.END), currentNamePaint, nameWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        if (this.chat == null || this.subLabel != null) {
            if (LocaleController.isRTL) {
                this.onlineLeft = AndroidUtilities.dp(11.0f);
            } else {
                this.onlineLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            CharSequence onlineString = "";
            TextPaint currentOnlinePaint = Theme.dialogs_offlinePaint;
            if (this.subLabel != null) {
                onlineString = this.subLabel;
            } else if (this.user != null) {
                if (this.user.bot) {
                    onlineString = LocaleController.getString("Bot", R.string.Bot);
                } else {
                    onlineString = LocaleController.formatUserStatus(this.user);
                    if (this.user != null && (this.user.id == UserConfig.getClientUserId() || (this.user.status != null && this.user.status.expires > ConnectionsManager.getInstance().getCurrentTime()))) {
                        currentOnlinePaint = Theme.dialogs_onlinePaint;
                        onlineString = LocaleController.getString("Online", R.string.Online);
                    }
                }
            }
            if (this.savedMessages) {
                this.onlineLayout = null;
                this.nameTop = AndroidUtilities.dp(25.0f);
            } else {
                this.onlineLayout = new StaticLayout(TextUtils.ellipsize(onlineString, currentOnlinePaint, (float) (onlineWidth - AndroidUtilities.dp(12.0f)), TruncateAt.END), currentOnlinePaint, onlineWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                this.nameTop = AndroidUtilities.dp(13.0f);
                if (!(this.subLabel == null || this.chat == null)) {
                    this.nameLockTop -= AndroidUtilities.dp(12.0f);
                }
            }
        } else {
            this.onlineLayout = null;
            this.nameTop = AndroidUtilities.dp(25.0f);
        }
        if (LocaleController.isRTL) {
            avatarLeft = getMeasuredWidth() - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 65.0f : 61.0f);
        } else {
            avatarLeft = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 13.0f : 9.0f);
        }
        this.avatarImage.setImageCoords(avatarLeft, AndroidUtilities.dp(10.0f), AndroidUtilities.dp(52.0f), AndroidUtilities.dp(52.0f));
        double widthpx;
        if (LocaleController.isRTL) {
            if (this.nameLayout.getLineCount() > 0 && this.nameLayout.getLineLeft(0) == 0.0f) {
                widthpx = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (widthpx < ((double) nameWidth)) {
                    this.nameLeft = (int) (((double) this.nameLeft) + (((double) nameWidth) - widthpx));
                }
            }
            if (this.onlineLayout != null && this.onlineLayout.getLineCount() > 0 && this.onlineLayout.getLineLeft(0) == 0.0f) {
                widthpx = Math.ceil((double) this.onlineLayout.getLineWidth(0));
                if (widthpx < ((double) onlineWidth)) {
                    this.onlineLeft = (int) (((double) this.onlineLeft) + (((double) onlineWidth) - widthpx));
                }
            }
        } else {
            if (this.nameLayout.getLineCount() > 0 && this.nameLayout.getLineRight(0) == ((float) nameWidth)) {
                widthpx = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (widthpx < ((double) nameWidth)) {
                    this.nameLeft = (int) (((double) this.nameLeft) - (((double) nameWidth) - widthpx));
                }
            }
            if (this.onlineLayout != null && this.onlineLayout.getLineCount() > 0 && this.onlineLayout.getLineRight(0) == ((float) onlineWidth)) {
                widthpx = Math.ceil((double) this.onlineLayout.getLineWidth(0));
                if (widthpx < ((double) onlineWidth)) {
                    this.onlineLeft = (int) (((double) this.onlineLeft) - (((double) onlineWidth) - widthpx));
                }
            }
        }
        if (LocaleController.isRTL) {
            this.nameLeft += this.paddingRight;
            this.onlineLeft += this.paddingRight;
        }
    }

    public void update(int mask) {
        TLObject photo = null;
        if (this.user != null) {
            this.avatarDrawable.setInfo(this.user);
            if (this.savedMessages) {
                this.avatarDrawable.setSavedMessages(1);
            } else if (this.user.photo != null) {
                photo = this.user.photo.photo_small;
            }
        } else if (this.chat != null) {
            if (this.chat.photo != null) {
                photo = this.chat.photo.photo_small;
            }
            this.avatarDrawable.setInfo(this.chat);
        } else {
            this.avatarDrawable.setInfo(0, null, null, false);
        }
        if (mask != 0) {
            boolean continueUpdate = false;
            if (!(((mask & 2) == 0 || this.user == null) && ((mask & 8) == 0 || this.chat == null)) && ((this.lastAvatar != null && photo == null) || !(this.lastAvatar != null || photo == null || this.lastAvatar == null || photo == null || (this.lastAvatar.volume_id == photo.volume_id && this.lastAvatar.local_id == photo.local_id)))) {
                continueUpdate = true;
            }
            if (!(continueUpdate || (mask & 4) == 0 || this.user == null)) {
                int newStatus = 0;
                if (this.user.status != null) {
                    newStatus = this.user.status.expires;
                }
                if (newStatus != this.lastStatus) {
                    continueUpdate = true;
                }
            }
            if (!((continueUpdate || (mask & 1) == 0 || this.user == null) && ((mask & 16) == 0 || this.chat == null))) {
                String newName;
                if (this.user != null) {
                    newName = this.user.first_name + this.user.last_name;
                } else {
                    newName = this.chat.title;
                }
                if (!newName.equals(this.lastName)) {
                    continueUpdate = true;
                }
            }
            if (!(continueUpdate || !this.drawCount || (mask & 256) == 0)) {
                TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
                if (!(dialog == null || dialog.unread_count == this.lastUnreadCount)) {
                    continueUpdate = true;
                }
            }
            if (!continueUpdate) {
                return;
            }
        }
        if (this.user != null) {
            if (this.user.status != null) {
                this.lastStatus = this.user.status.expires;
            } else {
                this.lastStatus = 0;
            }
            this.lastName = this.user.first_name + this.user.last_name;
        } else if (this.chat != null) {
            this.lastName = this.chat.title;
        }
        this.lastAvatar = photo;
        this.avatarImage.setImage(photo, "50_50", this.avatarDrawable, null, 0);
        if (getMeasuredWidth() == 0 && getMeasuredHeight() == 0) {
            requestLayout();
        } else {
            buildLayout();
        }
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        if (this.user != null || this.chat != null || this.encryptedChat != null) {
            int x;
            if (this.useSeparator) {
                if (LocaleController.isRTL) {
                    canvas.drawLine(0.0f, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
                } else {
                    canvas.drawLine((float) AndroidUtilities.dp((float) AndroidUtilities.leftBaseline), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
                }
            }
            if (this.drawNameLock) {
                setDrawableBounds(Theme.dialogs_lockDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_lockDrawable.draw(canvas);
            } else if (this.drawNameGroup) {
                setDrawableBounds(Theme.dialogs_groupDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_groupDrawable.draw(canvas);
            } else if (this.drawNameBroadcast) {
                setDrawableBounds(Theme.dialogs_broadcastDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_broadcastDrawable.draw(canvas);
            } else if (this.drawNameBot) {
                setDrawableBounds(Theme.dialogs_botDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_botDrawable.draw(canvas);
            }
            if (this.nameLayout != null) {
                canvas.save();
                canvas.translate((float) this.nameLeft, (float) this.nameTop);
                this.nameLayout.draw(canvas);
                canvas.restore();
                if (this.drawCheck) {
                    if (LocaleController.isRTL) {
                        x = (this.nameLeft - AndroidUtilities.dp(4.0f)) - Theme.dialogs_checkDrawable.getIntrinsicWidth();
                    } else {
                        x = (this.nameLeft + ((int) this.nameLayout.getLineWidth(0))) + AndroidUtilities.dp(4.0f);
                    }
                    setDrawableBounds(Theme.dialogs_verifiedDrawable, x, this.nameLockTop);
                    setDrawableBounds(Theme.dialogs_verifiedCheckDrawable, x, this.nameLockTop);
                    Theme.dialogs_verifiedDrawable.draw(canvas);
                    Theme.dialogs_verifiedCheckDrawable.draw(canvas);
                }
            }
            if (this.onlineLayout != null) {
                canvas.save();
                canvas.translate((float) this.onlineLeft, (float) AndroidUtilities.dp(40.0f));
                this.onlineLayout.draw(canvas);
                canvas.restore();
            }
            if (this.countLayout != null) {
                x = this.countLeft - AndroidUtilities.dp(5.5f);
                this.rect.set((float) x, (float) this.countTop, (float) ((this.countWidth + x) + AndroidUtilities.dp(11.0f)), (float) (this.countTop + AndroidUtilities.dp(23.0f)));
                canvas.drawRoundRect(this.rect, 11.5f * AndroidUtilities.density, 11.5f * AndroidUtilities.density, MessagesController.getInstance().isDialogMuted(this.dialog_id) ? Theme.dialogs_countGrayPaint : Theme.dialogs_countPaint);
                canvas.save();
                canvas.translate((float) this.countLeft, (float) (this.countTop + AndroidUtilities.dp(4.0f)));
                this.countLayout.draw(canvas);
                canvas.restore();
            }
            this.avatarImage.draw(canvas);
        }
    }
}
