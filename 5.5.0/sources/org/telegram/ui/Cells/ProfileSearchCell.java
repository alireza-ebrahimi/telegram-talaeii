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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;

public class ProfileSearchCell extends BaseCell {
    private AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage = new ImageReceiver(this);
    private Chat chat;
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
    private EncryptedChat encryptedChat;
    private FileLocation lastAvatar;
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

    public void buildLayout() {
        CharSequence charSequence;
        CharSequence charSequence2;
        int measuredWidth;
        int i;
        int i2;
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
                this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                this.nameLeft = (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth()) + AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
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
            charSequence = this.currentName;
        } else {
            String str = TtmlNode.ANONYMOUS_REGION_ID;
            if (this.chat != null) {
                str = this.chat.title;
            } else if (this.user != null) {
                str = UserObject.getUserName(this.user);
            }
            charSequence = str.replace('\n', ' ');
        }
        if (charSequence.length() != 0) {
            charSequence2 = charSequence;
        } else if (this.user == null || this.user.phone == null || this.user.phone.length() == 0) {
            Object string = LocaleController.getString("HiddenName", R.string.HiddenName);
        } else {
            charSequence2 = C2488b.a().e("+" + this.user.phone);
        }
        TextPaint textPaint = this.encryptedChat != null ? Theme.dialogs_nameEncryptedPaint : Theme.dialogs_namePaint;
        if (LocaleController.isRTL) {
            measuredWidth = (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            i = measuredWidth;
        } else {
            measuredWidth = (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp(14.0f);
            i = measuredWidth;
        }
        if (this.drawNameLock) {
            measuredWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
        } else if (this.drawNameBroadcast) {
            measuredWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_broadcastDrawable.getIntrinsicWidth();
        } else if (this.drawNameGroup) {
            measuredWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_groupDrawable.getIntrinsicWidth();
        } else if (this.drawNameBot) {
            measuredWidth -= AndroidUtilities.dp(6.0f) + Theme.dialogs_botDrawable.getIntrinsicWidth();
        }
        int i3 = measuredWidth - this.paddingRight;
        int i4 = i - this.paddingRight;
        if (this.drawCount) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (tLRPC$TL_dialog == null || tLRPC$TL_dialog.unread_count == 0) {
                this.lastUnreadCount = 0;
                this.countLayout = null;
                measuredWidth = i3;
            } else {
                this.lastUnreadCount = tLRPC$TL_dialog.unread_count;
                CharSequence format = String.format("%d", new Object[]{Integer.valueOf(tLRPC$TL_dialog.unread_count)});
                this.countWidth = Math.max(AndroidUtilities.dp(12.0f), (int) Math.ceil((double) Theme.dialogs_countTextPaint.measureText(format)));
                this.countLayout = new StaticLayout(format, Theme.dialogs_countTextPaint, this.countWidth, Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                i = AndroidUtilities.dp(18.0f) + this.countWidth;
                measuredWidth = i3 - i;
                if (LocaleController.isRTL) {
                    this.countLeft = AndroidUtilities.dp(19.0f);
                    this.nameLeft = i + this.nameLeft;
                } else {
                    this.countLeft = (getMeasuredWidth() - this.countWidth) - AndroidUtilities.dp(19.0f);
                }
            }
            i2 = measuredWidth;
        } else {
            this.lastUnreadCount = 0;
            this.countLayout = null;
            i2 = i3;
        }
        this.nameLayout = new StaticLayout(TextUtils.ellipsize(charSequence2, textPaint, (float) (i2 - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint, i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        if (this.chat == null || this.subLabel != null) {
            if (LocaleController.isRTL) {
                this.onlineLeft = AndroidUtilities.dp(11.0f);
            } else {
                this.onlineLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            charSequence = TtmlNode.ANONYMOUS_REGION_ID;
            TextPaint textPaint2 = Theme.dialogs_offlinePaint;
            if (this.subLabel != null) {
                charSequence = this.subLabel;
            } else if (this.user != null) {
                if (this.user.bot) {
                    charSequence = LocaleController.getString("Bot", R.string.Bot);
                } else {
                    charSequence = LocaleController.formatUserStatus(this.user);
                    if (this.user != null && (this.user.id == UserConfig.getClientUserId() || (this.user.status != null && this.user.status.expires > ConnectionsManager.getInstance().getCurrentTime()))) {
                        textPaint2 = Theme.dialogs_onlinePaint;
                        charSequence = LocaleController.getString("Online", R.string.Online);
                    }
                }
            }
            if (this.savedMessages) {
                this.onlineLayout = null;
                this.nameTop = AndroidUtilities.dp(25.0f);
            } else {
                this.onlineLayout = new StaticLayout(TextUtils.ellipsize(charSequence, textPaint2, (float) (i4 - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint2, i4, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
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
            measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 65.0f : 61.0f);
        } else {
            measuredWidth = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 13.0f : 9.0f);
        }
        this.avatarImage.setImageCoords(measuredWidth, AndroidUtilities.dp(10.0f), AndroidUtilities.dp(52.0f), AndroidUtilities.dp(52.0f));
        double ceil;
        if (LocaleController.isRTL) {
            if (this.nameLayout.getLineCount() > 0 && this.nameLayout.getLineLeft(0) == BitmapDescriptorFactory.HUE_RED) {
                ceil = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (ceil < ((double) i2)) {
                    this.nameLeft = (int) ((((double) i2) - ceil) + ((double) this.nameLeft));
                }
            }
            if (this.onlineLayout != null && this.onlineLayout.getLineCount() > 0 && this.onlineLayout.getLineLeft(0) == BitmapDescriptorFactory.HUE_RED) {
                ceil = Math.ceil((double) this.onlineLayout.getLineWidth(0));
                if (ceil < ((double) i4)) {
                    this.onlineLeft = (int) ((((double) i4) - ceil) + ((double) this.onlineLeft));
                }
            }
        } else {
            if (this.nameLayout.getLineCount() > 0 && this.nameLayout.getLineRight(0) == ((float) i2)) {
                ceil = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (ceil < ((double) i2)) {
                    this.nameLeft = (int) (((double) this.nameLeft) - (((double) i2) - ceil));
                }
            }
            if (this.onlineLayout != null && this.onlineLayout.getLineCount() > 0 && this.onlineLayout.getLineRight(0) == ((float) i4)) {
                ceil = Math.ceil((double) this.onlineLayout.getLineWidth(0));
                if (ceil < ((double) i4)) {
                    this.onlineLeft = (int) (((double) this.onlineLeft) - (((double) i4) - ceil));
                }
            }
        }
        if (LocaleController.isRTL) {
            this.nameLeft += this.paddingRight;
            this.onlineLeft += this.paddingRight;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.avatarImage.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        if (this.user != null || this.chat != null || this.encryptedChat != null) {
            int dp;
            if (this.useSeparator) {
                if (LocaleController.isRTL) {
                    canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
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
                    dp = LocaleController.isRTL ? (this.nameLeft - AndroidUtilities.dp(4.0f)) - Theme.dialogs_checkDrawable.getIntrinsicWidth() : (this.nameLeft + ((int) this.nameLayout.getLineWidth(0))) + AndroidUtilities.dp(4.0f);
                    setDrawableBounds(Theme.dialogs_verifiedDrawable, dp, this.nameLockTop);
                    setDrawableBounds(Theme.dialogs_verifiedCheckDrawable, dp, this.nameLockTop);
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
                dp = this.countLeft - AndroidUtilities.dp(5.5f);
                this.rect.set((float) dp, (float) this.countTop, (float) ((dp + this.countWidth) + AndroidUtilities.dp(11.0f)), (float) (this.countTop + AndroidUtilities.dp(23.0f)));
                canvas.drawRoundRect(this.rect, 11.5f * AndroidUtilities.density, 11.5f * AndroidUtilities.density, MessagesController.getInstance().isDialogMuted(this.dialog_id) ? Theme.dialogs_countGrayPaint : Theme.dialogs_countPaint);
                canvas.save();
                canvas.translate((float) this.countLeft, (float) (this.countTop + AndroidUtilities.dp(4.0f)));
                this.countLayout.draw(canvas);
                canvas.restore();
            }
            this.avatarImage.draw(canvas);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!(this.user == null && this.chat == null && this.encryptedChat == null) && z) {
            buildLayout();
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(72.0f));
    }

    public void setData(TLObject tLObject, EncryptedChat encryptedChat, CharSequence charSequence, CharSequence charSequence2, boolean z, boolean z2) {
        this.currentName = charSequence;
        if (tLObject instanceof User) {
            this.user = (User) tLObject;
            this.chat = null;
        } else if (tLObject instanceof Chat) {
            this.chat = (Chat) tLObject;
            this.user = null;
        }
        this.encryptedChat = encryptedChat;
        this.subLabel = charSequence2;
        this.drawCount = z;
        this.savedMessages = z2;
        update(0);
    }

    public void setPaddingRight(int i) {
        this.paddingRight = i;
    }

    public void update(int i) {
        TLObject tLObject;
        if (this.user != null) {
            this.avatarDrawable.setInfo(this.user);
            if (this.savedMessages) {
                this.avatarDrawable.setSavedMessages(1);
                tLObject = null;
            } else {
                if (this.user.photo != null) {
                    tLObject = this.user.photo.photo_small;
                }
                tLObject = null;
            }
        } else if (this.chat != null) {
            TLObject tLObject2 = this.chat.photo != null ? this.chat.photo.photo_small : null;
            this.avatarDrawable.setInfo(this.chat);
            tLObject = tLObject2;
        } else {
            this.avatarDrawable.setInfo(0, null, null, false);
            tLObject = null;
        }
        if (i != 0) {
            int i2;
            int i3;
            if ((((i & 2) == 0 || this.user == null) && ((i & 8) == 0 || this.chat == null)) || ((this.lastAvatar == null || tLObject != null) && (this.lastAvatar != null || tLObject == null || this.lastAvatar == null || tLObject == null || (this.lastAvatar.volume_id == tLObject.volume_id && this.lastAvatar.local_id == tLObject.local_id)))) {
                boolean z = false;
            } else {
                i2 = 1;
            }
            if (!(i2 != 0 || (i & 4) == 0 || this.user == null)) {
                if ((this.user.status != null ? this.user.status.expires : 0) != this.lastStatus) {
                    i2 = 1;
                }
            }
            if (!((i2 != 0 || (i & 1) == 0 || this.user == null) && ((i & 16) == 0 || this.chat == null))) {
                if (!(this.user != null ? this.user.first_name + this.user.last_name : this.chat.title).equals(this.lastName)) {
                    i2 = 1;
                }
            }
            if (i2 == 0 && this.drawCount && (i & 256) != 0) {
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
                if (!(tLRPC$TL_dialog == null || tLRPC$TL_dialog.unread_count == this.lastUnreadCount)) {
                    i3 = 1;
                    if (i3 == 0) {
                        return;
                    }
                }
            }
            i3 = i2;
            if (i3 == 0) {
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
        this.lastAvatar = tLObject;
        this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable, null, 0);
        if (getMeasuredWidth() == 0 && getMeasuredHeight() == 0) {
            requestLayout();
        } else {
            buildLayout();
        }
        postInvalidate();
    }
}
