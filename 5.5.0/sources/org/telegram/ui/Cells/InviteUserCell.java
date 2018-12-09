package org.telegram.ui.Cells;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.ContactsController.Contact;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.GroupCreateCheckBox;
import org.telegram.ui.Components.LayoutHelper;

public class InviteUserCell extends FrameLayout {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private BackupImageView avatarImageView;
    private GroupCreateCheckBox checkBox;
    private Contact currentContact;
    private CharSequence currentName;
    private SimpleTextView nameTextView;
    private SimpleTextView statusTextView;

    public InviteUserCell(Context context, boolean z) {
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

    public Contact getContact() {
        return this.currentContact;
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

    public void setUser(Contact contact, CharSequence charSequence) {
        this.currentContact = contact;
        this.currentName = charSequence;
        update(0);
    }

    public void update(int i) {
        if (this.currentContact != null) {
            this.avatarDrawable.setInfo(this.currentContact.contact_id, this.currentContact.first_name, this.currentContact.last_name, false);
            if (this.currentName != null) {
                this.nameTextView.setText(this.currentName, true);
            } else {
                this.nameTextView.setText(ContactsController.formatName(this.currentContact.first_name, this.currentContact.last_name));
            }
            this.statusTextView.setTag(Theme.key_groupcreate_offlineText);
            this.statusTextView.setTextColor(Theme.getColor(Theme.key_groupcreate_offlineText));
            if (this.currentContact.imported > 0) {
                this.statusTextView.setText(LocaleController.formatPluralString("TelegramContacts", this.currentContact.imported));
            } else {
                this.statusTextView.setText((CharSequence) this.currentContact.phones.get(0));
            }
            this.avatarImageView.setImageDrawable(this.avatarDrawable);
        }
    }
}
