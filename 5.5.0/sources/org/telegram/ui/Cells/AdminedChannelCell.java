package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.text.SpannableStringBuilder;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.URLSpanNoUnderline;

public class AdminedChannelCell extends FrameLayout {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private BackupImageView avatarImageView;
    private Chat currentChannel;
    private ImageView deleteButton;
    private boolean isLast;
    private SimpleTextView nameTextView;
    private SimpleTextView statusTextView;

    public AdminedChannelCell(Context context, OnClickListener onClickListener) {
        super(context);
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(24.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 12.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTextSize(17);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 62.0f : 73.0f, 15.5f, LocaleController.isRTL ? 73.0f : 62.0f, BitmapDescriptorFactory.HUE_RED));
        this.statusTextView = new SimpleTextView(context);
        this.statusTextView.setTextSize(14);
        this.statusTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        this.statusTextView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
        this.statusTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        addView(this.statusTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 62.0f : 73.0f, 38.5f, LocaleController.isRTL ? 73.0f : 62.0f, BitmapDescriptorFactory.HUE_RED));
        this.deleteButton = new ImageView(context);
        this.deleteButton.setScaleType(ScaleType.CENTER);
        this.deleteButton.setImageResource(R.drawable.msg_panel_clear);
        this.deleteButton.setOnClickListener(onClickListener);
        this.deleteButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText), Mode.MULTIPLY));
        addView(this.deleteButton, LayoutHelper.createFrame(48, 48.0f, (LocaleController.isRTL ? 3 : 5) | 48, LocaleController.isRTL ? 7.0f : BitmapDescriptorFactory.HUE_RED, 12.0f, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 7.0f, BitmapDescriptorFactory.HUE_RED));
    }

    public Chat getCurrentChannel() {
        return this.currentChannel;
    }

    public ImageView getDeleteButton() {
        return this.deleteButton;
    }

    public SimpleTextView getNameTextView() {
        return this.nameTextView;
    }

    public SimpleTextView getStatusTextView() {
        return this.statusTextView;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp((float) ((this.isLast ? 12 : 0) + 60)), 1073741824));
    }

    public void setChannel(Chat chat, boolean z) {
        TLObject tLObject = null;
        if (chat.photo != null) {
            tLObject = chat.photo.photo_small;
        }
        String str = MessagesController.getInstance().linkPrefix + "/";
        this.currentChannel = chat;
        this.avatarDrawable.setInfo(chat);
        this.nameTextView.setText(chat.title);
        CharSequence spannableStringBuilder = new SpannableStringBuilder(str + chat.username);
        spannableStringBuilder.setSpan(new URLSpanNoUnderline(TtmlNode.ANONYMOUS_REGION_ID), str.length(), spannableStringBuilder.length(), 33);
        this.statusTextView.setText(spannableStringBuilder);
        this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
        this.isLast = z;
    }

    public void update() {
        this.avatarDrawable.setInfo(this.currentChannel);
        this.avatarImageView.invalidate();
    }
}
