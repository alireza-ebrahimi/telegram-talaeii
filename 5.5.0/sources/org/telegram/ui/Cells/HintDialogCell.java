package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class HintDialogCell extends FrameLayout {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private StaticLayout countLayout;
    private int countWidth;
    private long dialog_id;
    private BackupImageView imageView;
    private int lastUnreadCount;
    private TextView nameTextView;
    private RectF rect = new RectF();

    public HintDialogCell(Context context) {
        super(context);
        this.imageView = new BackupImageView(context);
        this.imageView.setRoundRadius(AndroidUtilities.dp(27.0f));
        addView(this.imageView, LayoutHelper.createFrame(54, 54.0f, 49, BitmapDescriptorFactory.HUE_RED, 7.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTextSize(1, 12.0f);
        this.nameTextView.setMaxLines(2);
        this.nameTextView.setGravity(49);
        this.nameTextView.setLines(2);
        this.nameTextView.setEllipsize(TruncateAt.END);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 6.0f, 64.0f, 6.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public void checkUnreadCounter(int i) {
        if (i == 0 || (i & 256) != 0 || (i & 2048) != 0) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.dialog_id));
            if (tLRPC$TL_dialog == null || tLRPC$TL_dialog.unread_count == 0) {
                if (this.countLayout != null) {
                    if (i != 0) {
                        invalidate();
                    }
                    this.lastUnreadCount = 0;
                    this.countLayout = null;
                }
            } else if (this.lastUnreadCount != tLRPC$TL_dialog.unread_count) {
                this.lastUnreadCount = tLRPC$TL_dialog.unread_count;
                CharSequence format = String.format("%d", new Object[]{Integer.valueOf(tLRPC$TL_dialog.unread_count)});
                this.countWidth = Math.max(AndroidUtilities.dp(12.0f), (int) Math.ceil((double) Theme.dialogs_countTextPaint.measureText(format)));
                this.countLayout = new StaticLayout(format, Theme.dialogs_countTextPaint, this.countWidth, Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                if (i != 0) {
                    invalidate();
                }
            }
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        boolean drawChild = super.drawChild(canvas, view, j);
        if (view == this.imageView && this.countLayout != null) {
            int dp = AndroidUtilities.dp(6.0f);
            int dp2 = AndroidUtilities.dp(54.0f);
            int dp3 = dp2 - AndroidUtilities.dp(5.5f);
            this.rect.set((float) dp3, (float) dp, (float) ((dp3 + this.countWidth) + AndroidUtilities.dp(11.0f)), (float) (AndroidUtilities.dp(23.0f) + dp));
            canvas.drawRoundRect(this.rect, 11.5f * AndroidUtilities.density, 11.5f * AndroidUtilities.density, MessagesController.getInstance().isDialogMuted(this.dialog_id) ? Theme.dialogs_countGrayPaint : Theme.dialogs_countPaint);
            canvas.save();
            canvas.translate((float) dp2, (float) (dp + AndroidUtilities.dp(4.0f)));
            this.countLayout.draw(canvas);
            canvas.restore();
        }
        return drawChild;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824));
    }

    public void setDialog(int i, boolean z, CharSequence charSequence) {
        TLObject tLObject;
        this.dialog_id = (long) i;
        if (i > 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
            if (charSequence != null) {
                this.nameTextView.setText(charSequence);
            } else if (user != null) {
                this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
            } else {
                this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            }
            this.avatarDrawable.setInfo(user);
            if (!(user == null || user.photo == null)) {
                tLObject = user.photo.photo_small;
            }
            tLObject = null;
        } else {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
            if (charSequence != null) {
                this.nameTextView.setText(charSequence);
            } else if (chat != null) {
                this.nameTextView.setText(chat.title);
            } else {
                this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            }
            this.avatarDrawable.setInfo(chat);
            if (!(chat == null || chat.photo == null)) {
                tLObject = chat.photo.photo_small;
            }
            tLObject = null;
        }
        this.imageView.setImage(tLObject, "50_50", this.avatarDrawable);
        if (z) {
            checkUnreadCounter(0);
        } else {
            this.countLayout = null;
        }
    }

    public void update() {
        int i = (int) this.dialog_id;
        if (i > 0) {
            this.avatarDrawable.setInfo(MessagesController.getInstance().getUser(Integer.valueOf(i)));
            return;
        }
        this.avatarDrawable.setInfo(MessagesController.getInstance().getChat(Integer.valueOf(-i)));
    }
}
