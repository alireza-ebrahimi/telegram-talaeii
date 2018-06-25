package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.SLSProxyHelper;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.MediaActivity;
import org.telegram.ui.ProfileActivity;

public class ChatAvatarContainer extends FrameLayout implements NotificationCenterDelegate {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    public BackupImageView avatarImageView;
    private int currentConnectionState;
    private CharSequence lastSubtitle;
    private int onlineCount = -1;
    private ChatActivity parentFragment;
    private StatusDrawable[] statusDrawables = new StatusDrawable[5];
    private SimpleTextView subtitleTextView;
    private ImageView timeItem;
    private TimerDrawable timerDrawable;
    private SimpleTextView titleTextView;

    /* renamed from: org.telegram.ui.Components.ChatAvatarContainer$1 */
    class C25381 implements OnClickListener {
        C25381() {
        }

        public void onClick(View v) {
            ChatAvatarContainer.this.parentFragment.showDialog(AlertsCreator.createTTLAlert(ChatAvatarContainer.this.getContext(), ChatAvatarContainer.this.parentFragment.getCurrentEncryptedChat()).create());
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAvatarContainer$2 */
    class C25392 implements OnClickListener {
        C25392() {
        }

        public void onClick(View v) {
            User user = ChatAvatarContainer.this.parentFragment.getCurrentUser();
            TLRPC$Chat chat = ChatAvatarContainer.this.parentFragment.getCurrentChat();
            Bundle args;
            ProfileActivity fragment;
            if (user != null) {
                args = new Bundle();
                if (UserObject.isUserSelf(user)) {
                    args.putLong("dialog_id", ChatAvatarContainer.this.parentFragment.getDialogId());
                    MediaActivity fragment2 = new MediaActivity(args);
                    fragment2.setChatInfo(ChatAvatarContainer.this.parentFragment.getCurrentChatInfo());
                    ChatAvatarContainer.this.parentFragment.presentFragment(fragment2);
                    return;
                }
                args.putInt("user_id", user.id);
                if (ChatAvatarContainer.this.timeItem != null) {
                    args.putLong("dialog_id", ChatAvatarContainer.this.parentFragment.getDialogId());
                }
                fragment = new ProfileActivity(args);
                fragment.setPlayProfileAnimation(true);
                ChatAvatarContainer.this.parentFragment.presentFragment(fragment);
            } else if (chat != null) {
                args = new Bundle();
                args.putInt("chat_id", chat.id);
                fragment = new ProfileActivity(args);
                fragment.setChatInfo(ChatAvatarContainer.this.parentFragment.getCurrentChatInfo());
                fragment.setPlayProfileAnimation(true);
                ChatAvatarContainer.this.parentFragment.presentFragment(fragment);
            }
        }
    }

    public ChatAvatarContainer(Context context, ChatActivity chatActivity, boolean needTime) {
        super(context);
        this.parentFragment = chatActivity;
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(21.0f));
        addView(this.avatarImageView);
        this.titleTextView = new SimpleTextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
        this.titleTextView.setTextSize(18);
        this.titleTextView.setGravity(3);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
        addView(this.titleTextView);
        this.subtitleTextView = new SimpleTextView(context);
        this.subtitleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        this.subtitleTextView.setTextSize(14);
        this.subtitleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.subtitleTextView.setGravity(3);
        addView(this.subtitleTextView);
        if (needTime) {
            this.timeItem = new ImageView(context);
            this.timeItem.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(5.0f));
            this.timeItem.setScaleType(ScaleType.CENTER);
            ImageView imageView = this.timeItem;
            Drawable timerDrawable = new TimerDrawable(context);
            this.timerDrawable = timerDrawable;
            imageView.setImageDrawable(timerDrawable);
            addView(this.timeItem);
            this.timeItem.setOnClickListener(new C25381());
        }
        if (this.parentFragment != null) {
            setOnClickListener(new C25392());
            TLRPC$Chat chat = this.parentFragment.getCurrentChat();
            this.statusDrawables[0] = new TypingDotsDrawable();
            this.statusDrawables[1] = new RecordStatusDrawable();
            this.statusDrawables[2] = new SendingFileDrawable();
            this.statusDrawables[3] = new PlayingGameDrawable();
            this.statusDrawables[4] = new RoundStatusDrawable();
            for (StatusDrawable statusDrawable : this.statusDrawables) {
                boolean z;
                if (chat != null) {
                    z = true;
                } else {
                    z = false;
                }
                statusDrawable.setIsChat(z);
            }
        }
    }

    public void setTitleColors(int title, int subtitle) {
        this.titleTextView.setTextColor(title);
        this.subtitleTextView.setTextColor(title);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth = width - AndroidUtilities.dp(70.0f);
        this.avatarImageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
        this.titleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
        this.subtitleTextView.measure(MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
        if (this.timeItem != null) {
            this.timeItem.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824));
        }
        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int viewTop = ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(42.0f)) / 2) + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
        this.avatarImageView.layout(AndroidUtilities.dp(8.0f), viewTop, AndroidUtilities.dp(50.0f), AndroidUtilities.dp(42.0f) + viewTop);
        if (this.subtitleTextView.getVisibility() == 0) {
            this.titleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(1.3f) + viewTop, AndroidUtilities.dp(62.0f) + this.titleTextView.getMeasuredWidth(), (this.titleTextView.getTextHeight() + viewTop) + AndroidUtilities.dp(1.3f));
        } else {
            this.titleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(11.0f) + viewTop, AndroidUtilities.dp(62.0f) + this.titleTextView.getMeasuredWidth(), (this.titleTextView.getTextHeight() + viewTop) + AndroidUtilities.dp(11.0f));
        }
        if (this.timeItem != null) {
            this.timeItem.layout(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(15.0f) + viewTop, AndroidUtilities.dp(58.0f), AndroidUtilities.dp(49.0f) + viewTop);
        }
        this.subtitleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(24.0f) + viewTop, AndroidUtilities.dp(62.0f) + this.subtitleTextView.getMeasuredWidth(), (this.subtitleTextView.getTextHeight() + viewTop) + AndroidUtilities.dp(24.0f));
    }

    public void showTimeItem() {
        if (this.timeItem != null) {
            this.timeItem.setVisibility(0);
        }
    }

    public void hideTimeItem() {
        if (this.timeItem != null) {
            this.timeItem.setVisibility(8);
        }
    }

    public void setTime(int value) {
        if (this.timerDrawable != null) {
            this.timerDrawable.setTime(value);
        }
    }

    public void setTitleIcons(int leftIcon, int rightIcon) {
        this.titleTextView.setLeftDrawable(leftIcon);
        this.titleTextView.setRightDrawable(rightIcon);
    }

    public void setTitleIcons(Drawable leftIcon, Drawable rightIcon) {
        this.titleTextView.setLeftDrawable(leftIcon);
        this.titleTextView.setRightDrawable(rightIcon);
    }

    public void setTitle(CharSequence value) {
        this.titleTextView.setText(value);
    }

    public void setSubtitle(CharSequence value) {
        if (this.lastSubtitle == null) {
            this.subtitleTextView.setText(value);
        } else {
            this.lastSubtitle = value;
        }
    }

    public ImageView getTimeItem() {
        return this.timeItem;
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public SimpleTextView getSubtitleTextView() {
        return this.subtitleTextView;
    }

    private void setTypingAnimation(boolean start) {
        int a;
        if (start) {
            try {
                Integer type = (Integer) MessagesController.getInstance().printingStringsTypes.get(Long.valueOf(this.parentFragment.getDialogId()));
                this.subtitleTextView.setLeftDrawable(this.statusDrawables[type.intValue()]);
                for (a = 0; a < this.statusDrawables.length; a++) {
                    if (a == type.intValue()) {
                        this.statusDrawables[a].start();
                    } else {
                        this.statusDrawables[a].stop();
                    }
                }
                return;
            } catch (Exception e) {
                FileLog.e(e);
                return;
            }
        }
        this.subtitleTextView.setLeftDrawable(null);
        for (StatusDrawable stop : this.statusDrawables) {
            stop.stop();
        }
    }

    public void updateSubtitle() {
        if (this.parentFragment != null) {
            User user = this.parentFragment.getCurrentUser();
            if (!UserObject.isUserSelf(user)) {
                CharSequence newSubtitle;
                TLRPC$Chat chat = this.parentFragment.getCurrentChat();
                CharSequence printString = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.parentFragment.getDialogId()));
                if (printString != null) {
                    printString = TextUtils.replace(printString, new String[]{"..."}, new String[]{""});
                }
                if (printString == null || printString.length() == 0 || (ChatObject.isChannel(chat) && !chat.megagroup)) {
                    setTypingAnimation(false);
                    if (chat != null) {
                        TLRPC$ChatFull info = this.parentFragment.getCurrentChatInfo();
                        if (ChatObject.isChannel(chat)) {
                            if (info == null || info.participants_count == 0) {
                                if (chat.megagroup) {
                                    newSubtitle = LocaleController.getString("Loading", R.string.Loading).toLowerCase();
                                } else if ((chat.flags & 64) != 0) {
                                    newSubtitle = LocaleController.getString("ChannelPublic", R.string.ChannelPublic).toLowerCase();
                                } else {
                                    newSubtitle = LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate).toLowerCase();
                                }
                            } else if (!chat.megagroup || info.participants_count > 200) {
                                int[] result = new int[1];
                                String shortNumber = LocaleController.formatShortNumber(info.participants_count, result);
                                if (chat.megagroup) {
                                    newSubtitle = LocaleController.formatPluralString("Members", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber);
                                } else {
                                    newSubtitle = LocaleController.formatPluralString("Subscribers", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber);
                                }
                            } else if (this.onlineCount <= 1 || info.participants_count == 0) {
                                newSubtitle = LocaleController.formatPluralString("Members", info.participants_count);
                            } else {
                                newSubtitle = String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", info.participants_count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                            }
                        } else if (ChatObject.isKickedFromChat(chat)) {
                            newSubtitle = LocaleController.getString("YouWereKicked", R.string.YouWereKicked);
                        } else if (ChatObject.isLeftFromChat(chat)) {
                            newSubtitle = LocaleController.getString("YouLeft", R.string.YouLeft);
                        } else {
                            int count = chat.participants_count;
                            if (!(info == null || info.participants == null)) {
                                count = info.participants.participants.size();
                            }
                            if (this.onlineCount <= 1 || count == 0) {
                                newSubtitle = LocaleController.formatPluralString("Members", count);
                            } else {
                                newSubtitle = String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                            }
                        }
                    } else if (user != null) {
                        String newStatus;
                        User newUser = MessagesController.getInstance().getUser(Integer.valueOf(user.id));
                        if (newUser != null) {
                            user = newUser;
                        }
                        if (user.id == UserConfig.getClientUserId()) {
                            newStatus = LocaleController.getString("ChatYourSelf", R.string.ChatYourSelf);
                        } else if (user.id == 333000 || user.id == 777000) {
                            newStatus = LocaleController.getString("ServiceNotifications", R.string.ServiceNotifications);
                        } else if (user.bot) {
                            newStatus = LocaleController.getString("Bot", R.string.Bot);
                        } else {
                            newStatus = LocaleController.formatUserStatus(user);
                        }
                        Object newSubtitle2 = newStatus;
                    } else {
                        newSubtitle = "";
                    }
                } else {
                    newSubtitle = printString;
                    setTypingAnimation(true);
                }
                if (this.lastSubtitle == null) {
                    this.subtitleTextView.setText(newSubtitle);
                } else {
                    this.lastSubtitle = newSubtitle;
                }
            } else if (this.subtitleTextView.getVisibility() != 8) {
                this.subtitleTextView.setVisibility(8);
            }
        }
    }

    public void setChatAvatar(TLRPC$Chat chat) {
        TLObject newPhoto = null;
        if (chat.photo != null) {
            newPhoto = chat.photo.photo_small;
        }
        this.avatarDrawable.setInfo(chat);
        if (this.avatarImageView != null) {
            this.avatarImageView.setImage(newPhoto, "50_50", this.avatarDrawable);
        }
    }

    public void setUserAvatar(User user) {
        TLObject newPhoto = null;
        this.avatarDrawable.setInfo(user);
        if (UserObject.isUserSelf(user)) {
            this.avatarDrawable.setSavedMessages(2);
        } else if (user.photo != null) {
            newPhoto = user.photo.photo_small;
        }
        if (this.avatarImageView != null) {
            this.avatarImageView.setImage(newPhoto, "50_50", this.avatarDrawable);
        }
    }

    public void checkAndUpdateAvatar() {
        if (this.parentFragment != null) {
            TLObject newPhoto = null;
            User user = this.parentFragment.getCurrentUser();
            TLRPC$Chat chat = this.parentFragment.getCurrentChat();
            if (user != null) {
                this.avatarDrawable.setInfo(user);
                if (UserObject.isUserSelf(user)) {
                    this.avatarDrawable.setSavedMessages(2);
                } else if (user.photo != null) {
                    newPhoto = user.photo.photo_small;
                }
            } else if (chat != null) {
                if (chat.photo != null) {
                    newPhoto = chat.photo.photo_small;
                }
                this.avatarDrawable.setInfo(chat);
            }
            if (this.avatarImageView != null) {
                this.avatarImageView.setImage(newPhoto, "50_50", this.avatarDrawable);
            }
        }
    }

    public void updateOnlineCount() {
        if (this.parentFragment != null) {
            this.onlineCount = 0;
            TLRPC$ChatFull info = this.parentFragment.getCurrentChatInfo();
            if (info != null) {
                int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                if ((info instanceof TLRPC$TL_chatFull) || ((info instanceof TLRPC$TL_channelFull) && info.participants_count <= 200 && info.participants != null)) {
                    for (int a = 0; a < info.participants.participants.size(); a++) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) info.participants.participants.get(a)).user_id));
                        if (!(user == null || user.status == null || ((user.status.expires <= currentTime && user.id != UserConfig.getClientUserId()) || user.status.expires <= 10000))) {
                            this.onlineCount++;
                        }
                    }
                }
            }
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didUpdatedConnectionState);
            this.currentConnectionState = ConnectionsManager.getInstance().getConnectionState();
            updateCurrentConnectionState();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didUpdatedConnectionState);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.didUpdatedConnectionState) {
            int state = ConnectionsManager.getInstance().getConnectionState();
            if (this.currentConnectionState != state) {
                this.currentConnectionState = state;
                updateCurrentConnectionState();
            }
        }
    }

    private void updateCurrentConnectionState() {
        String title = null;
        if (this.currentConnectionState == 2) {
            title = LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork);
        } else if (this.currentConnectionState == 1) {
            title = LocaleController.getString("Connecting", R.string.Connecting);
        } else if (this.currentConnectionState == 5) {
            title = LocaleController.getString("Updating", R.string.Updating);
        } else if (this.currentConnectionState == 4) {
            if (SLSProxyHelper.isProxyFromSLS()) {
                title = LocaleController.getString("Connecting", R.string.Connecting);
            } else {
                title = LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy);
            }
        }
        if (title != null) {
            this.lastSubtitle = this.subtitleTextView.getText();
            this.subtitleTextView.setText(title);
        } else if (this.lastSubtitle != null) {
            this.subtitleTextView.setText(this.lastSubtitle);
            this.lastSubtitle = null;
        }
    }
}
