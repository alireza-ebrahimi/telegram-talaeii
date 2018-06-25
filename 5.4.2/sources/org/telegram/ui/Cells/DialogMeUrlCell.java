package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
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
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChat;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlChatInvite;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlStickerSet;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUnknown;
import org.telegram.tgnet.TLRPC$TL_recentMeUrlUser;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.RecentMeUrl;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;

public class DialogMeUrlCell extends BaseCell {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private ImageReceiver avatarImage = new ImageReceiver(this);
    private int avatarTop = AndroidUtilities.dp(10.0f);
    private boolean drawNameBot;
    private boolean drawNameBroadcast;
    private boolean drawNameGroup;
    private boolean drawNameLock;
    private boolean drawVerified;
    private boolean isSelected;
    private StaticLayout messageLayout;
    private int messageLeft;
    private int messageTop = AndroidUtilities.dp(40.0f);
    private StaticLayout nameLayout;
    private int nameLeft;
    private int nameLockLeft;
    private int nameLockTop;
    private int nameMuteLeft;
    private RecentMeUrl recentMeUrl;
    public boolean useSeparator;

    public DialogMeUrlCell(Context context) {
        super(context);
        Theme.createDialogsResources(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(26.0f));
    }

    public void buildLayout() {
        String str;
        TLObject tLObject;
        int dp;
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        TextPaint textPaint = Theme.dialogs_namePaint;
        TextPaint textPaint2 = Theme.dialogs_messagePaint;
        this.drawNameGroup = false;
        this.drawNameBroadcast = false;
        this.drawNameLock = false;
        this.drawNameBot = false;
        this.drawVerified = false;
        TLObject tLObject2;
        if (this.recentMeUrl instanceof TLRPC$TL_recentMeUrlChat) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.recentMeUrl.chat_id));
            if (chat.id < 0 || (ChatObject.isChannel(chat) && !chat.megagroup)) {
                this.drawNameBroadcast = true;
                this.nameLockTop = AndroidUtilities.dp(16.5f);
            } else {
                this.drawNameGroup = true;
                this.nameLockTop = AndroidUtilities.dp(17.5f);
            }
            this.drawVerified = chat.verified;
            if (LocaleController.isRTL) {
                this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth());
                this.nameLeft = AndroidUtilities.dp(14.0f);
            } else {
                this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                this.nameLeft = (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth()) + AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
            }
            str2 = chat.title;
            tLObject2 = chat.photo != null ? chat.photo.photo_small : null;
            this.avatarDrawable.setInfo(chat);
            str = str2;
            tLObject = tLObject2;
        } else if (this.recentMeUrl instanceof TLRPC$TL_recentMeUrlUser) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.recentMeUrl.user_id));
            if (LocaleController.isRTL) {
                this.nameLeft = AndroidUtilities.dp(14.0f);
            } else {
                this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            if (user != null) {
                if (user.bot) {
                    this.drawNameBot = true;
                    this.nameLockTop = AndroidUtilities.dp(16.5f);
                    if (LocaleController.isRTL) {
                        this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - Theme.dialogs_botDrawable.getIntrinsicWidth();
                        this.nameLeft = AndroidUtilities.dp(14.0f);
                    } else {
                        this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                        this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_botDrawable.getIntrinsicWidth();
                    }
                }
                this.drawVerified = user.verified;
            }
            str2 = UserObject.getUserName(user);
            tLObject2 = user.photo != null ? user.photo.photo_small : null;
            this.avatarDrawable.setInfo(user);
            str = str2;
            tLObject = tLObject2;
        } else if (this.recentMeUrl instanceof TLRPC$TL_recentMeUrlStickerSet) {
            if (LocaleController.isRTL) {
                this.nameLeft = AndroidUtilities.dp(14.0f);
            } else {
                this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            str2 = this.recentMeUrl.set.set.title;
            tLObject2 = this.recentMeUrl.set.cover;
            this.avatarDrawable.setInfo(5, this.recentMeUrl.set.set.title, null, false);
            str = str2;
            tLObject = tLObject2;
        } else if (this.recentMeUrl instanceof TLRPC$TL_recentMeUrlChatInvite) {
            if (LocaleController.isRTL) {
                this.nameLeft = AndroidUtilities.dp(14.0f);
            } else {
                this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            if (this.recentMeUrl.chat_invite.chat != null) {
                this.avatarDrawable.setInfo(this.recentMeUrl.chat_invite.chat);
                str2 = this.recentMeUrl.chat_invite.chat.title;
                tLObject2 = this.recentMeUrl.chat_invite.chat.photo != null ? this.recentMeUrl.chat_invite.chat.photo.photo_small : null;
                if (this.recentMeUrl.chat_invite.chat.id < 0 || (ChatObject.isChannel(this.recentMeUrl.chat_invite.chat) && !this.recentMeUrl.chat_invite.chat.megagroup)) {
                    this.drawNameBroadcast = true;
                    this.nameLockTop = AndroidUtilities.dp(16.5f);
                } else {
                    this.drawNameGroup = true;
                    this.nameLockTop = AndroidUtilities.dp(17.5f);
                }
                this.drawVerified = this.recentMeUrl.chat_invite.chat.verified;
            } else {
                str2 = this.recentMeUrl.chat_invite.title;
                tLObject2 = this.recentMeUrl.chat_invite.photo.photo_small;
                this.avatarDrawable.setInfo(5, this.recentMeUrl.chat_invite.title, null, false);
                if (this.recentMeUrl.chat_invite.broadcast || this.recentMeUrl.chat_invite.channel) {
                    this.drawNameBroadcast = true;
                    this.nameLockTop = AndroidUtilities.dp(16.5f);
                } else {
                    this.drawNameGroup = true;
                    this.nameLockTop = AndroidUtilities.dp(17.5f);
                }
            }
            if (LocaleController.isRTL) {
                this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth());
                this.nameLeft = AndroidUtilities.dp(14.0f);
                str = str2;
                tLObject = tLObject2;
            } else {
                this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                this.nameLeft = (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth()) + AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
                str = str2;
                tLObject = tLObject2;
            }
        } else if (this.recentMeUrl instanceof TLRPC$TL_recentMeUrlUnknown) {
            if (LocaleController.isRTL) {
                this.nameLeft = AndroidUtilities.dp(14.0f);
            } else {
                this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            }
            str = "Url";
            tLObject = null;
        } else {
            str = str2;
            tLObject = null;
        }
        CharSequence charSequence = MessagesController.getInstance().linkPrefix + "/" + this.recentMeUrl.url;
        this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable, null, 0);
        if (TextUtils.isEmpty(str)) {
            str = LocaleController.getString("HiddenName", R.string.HiddenName);
        }
        int measuredWidth = !LocaleController.isRTL ? (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp(14.0f) : (getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
        if (this.drawNameLock) {
            measuredWidth -= AndroidUtilities.dp(4.0f) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
        } else if (this.drawNameGroup) {
            measuredWidth -= AndroidUtilities.dp(4.0f) + Theme.dialogs_groupDrawable.getIntrinsicWidth();
        } else if (this.drawNameBroadcast) {
            measuredWidth -= AndroidUtilities.dp(4.0f) + Theme.dialogs_broadcastDrawable.getIntrinsicWidth();
        } else if (this.drawNameBot) {
            measuredWidth -= AndroidUtilities.dp(4.0f) + Theme.dialogs_botDrawable.getIntrinsicWidth();
        }
        if (this.drawVerified) {
            dp = AndroidUtilities.dp(6.0f) + Theme.dialogs_verifiedDrawable.getIntrinsicWidth();
            measuredWidth -= dp;
            if (LocaleController.isRTL) {
                this.nameLeft = dp + this.nameLeft;
            }
        }
        int max = Math.max(AndroidUtilities.dp(12.0f), measuredWidth);
        try {
            this.nameLayout = new StaticLayout(TextUtils.ellipsize(str.replace('\n', ' '), textPaint, (float) (max - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint, max, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e) {
            FileLog.e(e);
        }
        dp = getMeasuredWidth() - AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 16));
        if (LocaleController.isRTL) {
            this.messageLeft = AndroidUtilities.dp(16.0f);
            measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 65.0f : 61.0f);
        } else {
            this.messageLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            measuredWidth = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 13.0f : 9.0f);
        }
        this.avatarImage.setImageCoords(measuredWidth, this.avatarTop, AndroidUtilities.dp(52.0f), AndroidUtilities.dp(52.0f));
        int max2 = Math.max(AndroidUtilities.dp(12.0f), dp);
        try {
            this.messageLayout = new StaticLayout(TextUtils.ellipsize(charSequence, textPaint2, (float) (max2 - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint2, max2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        float lineLeft;
        double ceil;
        double ceil2;
        if (LocaleController.isRTL) {
            if (this.nameLayout != null && this.nameLayout.getLineCount() > 0) {
                lineLeft = this.nameLayout.getLineLeft(0);
                ceil = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (this.drawVerified) {
                    this.nameMuteLeft = (int) (((((double) this.nameLeft) + (((double) max) - ceil)) - ((double) AndroidUtilities.dp(6.0f))) - ((double) Theme.dialogs_verifiedDrawable.getIntrinsicWidth()));
                }
                if (lineLeft == BitmapDescriptorFactory.HUE_RED && ceil < ((double) max)) {
                    this.nameLeft = (int) (((double) this.nameLeft) + (((double) max) - ceil));
                }
            }
            if (this.messageLayout != null && this.messageLayout.getLineCount() > 0 && this.messageLayout.getLineLeft(0) == BitmapDescriptorFactory.HUE_RED) {
                ceil2 = Math.ceil((double) this.messageLayout.getLineWidth(0));
                if (ceil2 < ((double) max2)) {
                    this.messageLeft = (int) ((((double) max2) - ceil2) + ((double) this.messageLeft));
                    return;
                }
                return;
            }
            return;
        }
        if (this.nameLayout != null && this.nameLayout.getLineCount() > 0) {
            lineLeft = this.nameLayout.getLineRight(0);
            if (lineLeft == ((float) max)) {
                ceil = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (ceil < ((double) max)) {
                    this.nameLeft = (int) (((double) this.nameLeft) - (((double) max) - ceil));
                }
            }
            if (this.drawVerified) {
                this.nameMuteLeft = (int) ((lineLeft + ((float) this.nameLeft)) + ((float) AndroidUtilities.dp(6.0f)));
            }
        }
        if (this.messageLayout != null && this.messageLayout.getLineCount() > 0 && this.messageLayout.getLineRight(0) == ((float) max2)) {
            ceil2 = Math.ceil((double) this.messageLayout.getLineWidth(0));
            if (ceil2 < ((double) max2)) {
                this.messageLeft = (int) (((double) this.messageLeft) - (((double) max2) - ceil2));
            }
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
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
        if (this.isSelected) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.dialogs_tabletSeletedPaint);
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
            canvas.translate((float) this.nameLeft, (float) AndroidUtilities.dp(13.0f));
            this.nameLayout.draw(canvas);
            canvas.restore();
        }
        if (this.messageLayout != null) {
            canvas.save();
            canvas.translate((float) this.messageLeft, (float) this.messageTop);
            try {
                this.messageLayout.draw(canvas);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            canvas.restore();
        }
        if (this.drawVerified) {
            setDrawableBounds(Theme.dialogs_verifiedDrawable, this.nameMuteLeft, AndroidUtilities.dp(16.5f));
            setDrawableBounds(Theme.dialogs_verifiedCheckDrawable, this.nameMuteLeft, AndroidUtilities.dp(16.5f));
            Theme.dialogs_verifiedDrawable.draw(canvas);
            Theme.dialogs_verifiedCheckDrawable.draw(canvas);
        }
        if (this.useSeparator) {
            if (LocaleController.isRTL) {
                canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
            } else {
                canvas.drawLine((float) AndroidUtilities.dp((float) AndroidUtilities.leftBaseline), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
            }
        }
        this.avatarImage.draw(canvas);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            buildLayout();
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), (this.useSeparator ? 1 : 0) + AndroidUtilities.dp(72.0f));
    }

    public void setDialogSelected(boolean z) {
        if (this.isSelected != z) {
            invalidate();
        }
        this.isSelected = z;
    }

    public void setRecentMeUrl(RecentMeUrl recentMeUrl) {
        this.recentMeUrl = recentMeUrl;
        requestLayout();
    }
}
