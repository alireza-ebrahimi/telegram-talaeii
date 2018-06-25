package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController$Contact;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;

public class GroupCreateSpan extends View {
    private static Paint backPaint = new Paint(1);
    private static TextPaint textPaint = new TextPaint(1);
    private AvatarDrawable avatarDrawable;
    private int[] colors;
    private ContactsController$Contact currentContact;
    private Drawable deleteDrawable;
    private boolean deleting;
    private ImageReceiver imageReceiver;
    private String key;
    private long lastUpdateTime;
    private StaticLayout nameLayout;
    private float progress;
    private RectF rect;
    private int textWidth;
    private float textX;
    private int uid;

    public GroupCreateSpan(Context context, User user) {
        this(context, user, null);
    }

    public GroupCreateSpan(Context context, ContactsController$Contact contact) {
        this(context, null, contact);
    }

    public GroupCreateSpan(Context context, User user, ContactsController$Contact contact) {
        int maxNameWidth;
        String firstName;
        super(context);
        this.rect = new RectF();
        this.colors = new int[6];
        this.currentContact = contact;
        this.deleteDrawable = getResources().getDrawable(R.drawable.delete);
        textPaint.setTextSize((float) AndroidUtilities.dp(14.0f));
        this.avatarDrawable = new AvatarDrawable();
        this.avatarDrawable.setTextSize(AndroidUtilities.dp(12.0f));
        if (user != null) {
            this.avatarDrawable.setInfo(user);
            this.uid = user.id;
        } else {
            this.avatarDrawable.setInfo(0, contact.first_name, contact.last_name, false);
            this.uid = contact.contact_id;
            this.key = contact.key;
        }
        this.imageReceiver = new ImageReceiver();
        this.imageReceiver.setRoundRadius(AndroidUtilities.dp(16.0f));
        this.imageReceiver.setParentView(this);
        this.imageReceiver.setImageCoords(0, 0, AndroidUtilities.dp(32.0f), AndroidUtilities.dp(32.0f));
        if (AndroidUtilities.isTablet()) {
            maxNameWidth = AndroidUtilities.dp(366.0f) / 2;
        } else {
            maxNameWidth = (Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) - AndroidUtilities.dp(164.0f)) / 2;
        }
        if (user != null) {
            firstName = UserObject.getFirstName(user);
        } else if (TextUtils.isEmpty(contact.first_name)) {
            firstName = contact.last_name;
        } else {
            firstName = contact.first_name;
        }
        this.nameLayout = new StaticLayout(TextUtils.ellipsize(firstName.replace('\n', ' '), textPaint, (float) maxNameWidth, TruncateAt.END), textPaint, 1000, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        if (this.nameLayout.getLineCount() > 0) {
            this.textWidth = (int) Math.ceil((double) this.nameLayout.getLineWidth(0));
            this.textX = -this.nameLayout.getLineLeft(0);
        }
        TLRPC$FileLocation photo = null;
        if (!(user == null || user.photo == null)) {
            photo = user.photo.photo_small;
        }
        this.imageReceiver.setImage(photo, null, "50_50", this.avatarDrawable, null, null, 0, null, 1);
        updateColors();
    }

    public void updateColors() {
        int color = Theme.getColor(Theme.key_avatar_backgroundGroupCreateSpanBlue);
        int back = Theme.getColor(Theme.key_groupcreate_spanBackground);
        int text = Theme.getColor(Theme.key_groupcreate_spanText);
        this.colors[0] = Color.red(back);
        this.colors[1] = Color.red(color);
        this.colors[2] = Color.green(back);
        this.colors[3] = Color.green(color);
        this.colors[4] = Color.blue(back);
        this.colors[5] = Color.blue(color);
        textPaint.setColor(text);
        this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(text, Mode.MULTIPLY));
        backPaint.setColor(back);
        this.avatarDrawable.setColor(AvatarDrawable.getColorForId(5));
    }

    public boolean isDeleting() {
        return this.deleting;
    }

    public void startDeleteAnimation() {
        if (!this.deleting) {
            this.deleting = true;
            this.lastUpdateTime = System.currentTimeMillis();
            invalidate();
        }
    }

    public void cancelDeleteAnimation() {
        if (this.deleting) {
            this.deleting = false;
            this.lastUpdateTime = System.currentTimeMillis();
            invalidate();
        }
    }

    public int getUid() {
        return this.uid;
    }

    public String getKey() {
        return this.key;
    }

    public ContactsController$Contact getContact() {
        return this.currentContact;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(AndroidUtilities.dp(57.0f) + this.textWidth, AndroidUtilities.dp(32.0f));
    }

    protected void onDraw(Canvas canvas) {
        if ((this.deleting && this.progress != 1.0f) || !(this.deleting || this.progress == 0.0f)) {
            long dt = System.currentTimeMillis() - this.lastUpdateTime;
            if (dt < 0 || dt > 17) {
                dt = 17;
            }
            if (this.deleting) {
                this.progress += ((float) dt) / 120.0f;
                if (this.progress >= 1.0f) {
                    this.progress = 1.0f;
                }
            } else {
                this.progress -= ((float) dt) / 120.0f;
                if (this.progress < 0.0f) {
                    this.progress = 0.0f;
                }
            }
            invalidate();
        }
        canvas.save();
        this.rect.set(0.0f, 0.0f, (float) getMeasuredWidth(), (float) AndroidUtilities.dp(32.0f));
        backPaint.setColor(Color.argb(255, this.colors[0] + ((int) (((float) (this.colors[1] - this.colors[0])) * this.progress)), this.colors[2] + ((int) (((float) (this.colors[3] - this.colors[2])) * this.progress)), this.colors[4] + ((int) (((float) (this.colors[5] - this.colors[4])) * this.progress))));
        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(16.0f), (float) AndroidUtilities.dp(16.0f), backPaint);
        this.imageReceiver.draw(canvas);
        if (this.progress != 0.0f) {
            backPaint.setColor(this.avatarDrawable.getColor());
            backPaint.setAlpha((int) (255.0f * this.progress));
            canvas.drawCircle((float) AndroidUtilities.dp(16.0f), (float) AndroidUtilities.dp(16.0f), (float) AndroidUtilities.dp(16.0f), backPaint);
            canvas.save();
            canvas.rotate(45.0f * (1.0f - this.progress), (float) AndroidUtilities.dp(16.0f), (float) AndroidUtilities.dp(16.0f));
            this.deleteDrawable.setBounds(AndroidUtilities.dp(11.0f), AndroidUtilities.dp(11.0f), AndroidUtilities.dp(21.0f), AndroidUtilities.dp(21.0f));
            this.deleteDrawable.setAlpha((int) (255.0f * this.progress));
            this.deleteDrawable.draw(canvas);
            canvas.restore();
        }
        canvas.translate(this.textX + ((float) AndroidUtilities.dp(41.0f)), (float) AndroidUtilities.dp(8.0f));
        this.nameLayout.draw(canvas);
        canvas.restore();
    }
}
