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
import org.telegram.customization.p151g.C2820e;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_chatFull;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
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
    class C43761 implements OnClickListener {
        C43761() {
        }

        public void onClick(View view) {
            ChatAvatarContainer.this.parentFragment.showDialog(AlertsCreator.createTTLAlert(ChatAvatarContainer.this.getContext(), ChatAvatarContainer.this.parentFragment.getCurrentEncryptedChat()).create());
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatAvatarContainer$2 */
    class C43772 implements OnClickListener {
        C43772() {
        }

        public void onClick(View view) {
            User currentUser = ChatAvatarContainer.this.parentFragment.getCurrentUser();
            Chat currentChat = ChatAvatarContainer.this.parentFragment.getCurrentChat();
            if (currentUser != null) {
                Bundle bundle = new Bundle();
                BaseFragment mediaActivity;
                if (UserObject.isUserSelf(currentUser)) {
                    bundle.putLong("dialog_id", ChatAvatarContainer.this.parentFragment.getDialogId());
                    mediaActivity = new MediaActivity(bundle);
                    mediaActivity.setChatInfo(ChatAvatarContainer.this.parentFragment.getCurrentChatInfo());
                    ChatAvatarContainer.this.parentFragment.presentFragment(mediaActivity);
                    return;
                }
                bundle.putInt("user_id", currentUser.id);
                if (ChatAvatarContainer.this.timeItem != null) {
                    bundle.putLong("dialog_id", ChatAvatarContainer.this.parentFragment.getDialogId());
                }
                mediaActivity = new ProfileActivity(bundle);
                mediaActivity.setPlayProfileAnimation(true);
                ChatAvatarContainer.this.parentFragment.presentFragment(mediaActivity);
            } else if (currentChat != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("chat_id", currentChat.id);
                BaseFragment profileActivity = new ProfileActivity(bundle2);
                profileActivity.setChatInfo(ChatAvatarContainer.this.parentFragment.getCurrentChatInfo());
                profileActivity.setPlayProfileAnimation(true);
                ChatAvatarContainer.this.parentFragment.presentFragment(profileActivity);
            }
        }
    }

    public ChatAvatarContainer(Context context, ChatActivity chatActivity, boolean z) {
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
        if (z) {
            this.timeItem = new ImageView(context);
            this.timeItem.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(5.0f));
            this.timeItem.setScaleType(ScaleType.CENTER);
            ImageView imageView = this.timeItem;
            Drawable timerDrawable = new TimerDrawable(context);
            this.timerDrawable = timerDrawable;
            imageView.setImageDrawable(timerDrawable);
            addView(this.timeItem);
            this.timeItem.setOnClickListener(new C43761());
        }
        if (this.parentFragment != null) {
            setOnClickListener(new C43772());
            Chat currentChat = this.parentFragment.getCurrentChat();
            this.statusDrawables[0] = new TypingDotsDrawable();
            this.statusDrawables[1] = new RecordStatusDrawable();
            this.statusDrawables[2] = new SendingFileDrawable();
            this.statusDrawables[3] = new PlayingGameDrawable();
            this.statusDrawables[4] = new RoundStatusDrawable();
            for (StatusDrawable isChat : this.statusDrawables) {
                isChat.setIsChat(currentChat != null);
            }
        }
    }

    private void setTypingAnimation(boolean z) {
        int i = 0;
        if (z) {
            try {
                Integer num = (Integer) MessagesController.getInstance().printingStringsTypes.get(Long.valueOf(this.parentFragment.getDialogId()));
                this.subtitleTextView.setLeftDrawable(this.statusDrawables[num.intValue()]);
                while (i < this.statusDrawables.length) {
                    if (i == num.intValue()) {
                        this.statusDrawables[i].start();
                    } else {
                        this.statusDrawables[i].stop();
                    }
                    i++;
                }
                return;
            } catch (Throwable e) {
                FileLog.e(e);
                return;
            }
        }
        this.subtitleTextView.setLeftDrawable(null);
        for (StatusDrawable stop : this.statusDrawables) {
            stop.stop();
        }
    }

    private void updateCurrentConnectionState() {
        CharSequence string = this.currentConnectionState == 2 ? LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork) : this.currentConnectionState == 1 ? LocaleController.getString("Connecting", R.string.Connecting) : this.currentConnectionState == 5 ? LocaleController.getString("Updating", R.string.Updating) : this.currentConnectionState == 4 ? C2820e.a() ? LocaleController.getString("Connecting", R.string.Connecting) : LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy) : null;
        if (string != null) {
            this.lastSubtitle = this.subtitleTextView.getText();
            this.subtitleTextView.setText(string);
        } else if (this.lastSubtitle != null) {
            this.subtitleTextView.setText(this.lastSubtitle);
            this.lastSubtitle = null;
        }
    }

    public void checkAndUpdateAvatar() {
        if (this.parentFragment != null) {
            TLObject tLObject = null;
            User currentUser = this.parentFragment.getCurrentUser();
            Chat currentChat = this.parentFragment.getCurrentChat();
            if (currentUser != null) {
                this.avatarDrawable.setInfo(currentUser);
                if (UserObject.isUserSelf(currentUser)) {
                    this.avatarDrawable.setSavedMessages(2);
                } else if (currentUser.photo != null) {
                    tLObject = currentUser.photo.photo_small;
                }
            } else if (currentChat != null) {
                if (currentChat.photo != null) {
                    tLObject = currentChat.photo.photo_small;
                }
                this.avatarDrawable.setInfo(currentChat);
            }
            if (this.avatarImageView != null) {
                this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.didUpdatedConnectionState) {
            int connectionState = ConnectionsManager.getInstance().getConnectionState();
            if (this.currentConnectionState != connectionState) {
                this.currentConnectionState = connectionState;
                updateCurrentConnectionState();
            }
        }
    }

    public SimpleTextView getSubtitleTextView() {
        return this.subtitleTextView;
    }

    public ImageView getTimeItem() {
        return this.timeItem;
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public void hideTimeItem() {
        if (this.timeItem != null) {
            this.timeItem.setVisibility(8);
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

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int currentActionBarHeight = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(42.0f)) / 2);
        this.avatarImageView.layout(AndroidUtilities.dp(8.0f), currentActionBarHeight, AndroidUtilities.dp(50.0f), AndroidUtilities.dp(42.0f) + currentActionBarHeight);
        if (this.subtitleTextView.getVisibility() == 0) {
            this.titleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(1.3f) + currentActionBarHeight, AndroidUtilities.dp(62.0f) + this.titleTextView.getMeasuredWidth(), (this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.dp(1.3f));
        } else {
            this.titleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(11.0f) + currentActionBarHeight, AndroidUtilities.dp(62.0f) + this.titleTextView.getMeasuredWidth(), (this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.dp(11.0f));
        }
        if (this.timeItem != null) {
            this.timeItem.layout(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(15.0f) + currentActionBarHeight, AndroidUtilities.dp(58.0f), AndroidUtilities.dp(49.0f) + currentActionBarHeight);
        }
        this.subtitleTextView.layout(AndroidUtilities.dp(62.0f), AndroidUtilities.dp(24.0f) + currentActionBarHeight, AndroidUtilities.dp(62.0f) + this.subtitleTextView.getMeasuredWidth(), (currentActionBarHeight + this.subtitleTextView.getTextHeight()) + AndroidUtilities.dp(24.0f));
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int dp = size - AndroidUtilities.dp(70.0f);
        this.avatarImageView.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
        this.titleTextView.measure(MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), Integer.MIN_VALUE));
        this.subtitleTextView.measure(MeasureSpec.makeMeasureSpec(dp, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), Integer.MIN_VALUE));
        if (this.timeItem != null) {
            this.timeItem.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824));
        }
        setMeasuredDimension(size, MeasureSpec.getSize(i2));
    }

    public void setChatAvatar(Chat chat) {
        TLObject tLObject = null;
        if (chat.photo != null) {
            tLObject = chat.photo.photo_small;
        }
        this.avatarDrawable.setInfo(chat);
        if (this.avatarImageView != null) {
            this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        if (this.lastSubtitle == null) {
            this.subtitleTextView.setText(charSequence);
        } else {
            this.lastSubtitle = charSequence;
        }
    }

    public void setTime(int i) {
        if (this.timerDrawable != null) {
            this.timerDrawable.setTime(i);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.titleTextView.setText(charSequence);
    }

    public void setTitleColors(int i, int i2) {
        this.titleTextView.setTextColor(i);
        this.subtitleTextView.setTextColor(i);
    }

    public void setTitleIcons(int i, int i2) {
        this.titleTextView.setLeftDrawable(i);
        this.titleTextView.setRightDrawable(i2);
    }

    public void setTitleIcons(Drawable drawable, Drawable drawable2) {
        this.titleTextView.setLeftDrawable(drawable);
        this.titleTextView.setRightDrawable(drawable2);
    }

    public void setUserAvatar(User user) {
        TLObject tLObject = null;
        this.avatarDrawable.setInfo(user);
        if (UserObject.isUserSelf(user)) {
            this.avatarDrawable.setSavedMessages(2);
        } else if (user.photo != null) {
            tLObject = user.photo.photo_small;
        }
        if (this.avatarImageView != null) {
            this.avatarImageView.setImage(tLObject, "50_50", this.avatarDrawable);
        }
    }

    public void showTimeItem() {
        if (this.timeItem != null) {
            this.timeItem.setVisibility(0);
        }
    }

    public void updateOnlineCount() {
        if (this.parentFragment != null) {
            this.onlineCount = 0;
            ChatFull currentChatInfo = this.parentFragment.getCurrentChatInfo();
            if (currentChatInfo != null) {
                int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                if ((currentChatInfo instanceof TL_chatFull) || ((currentChatInfo instanceof TL_channelFull) && currentChatInfo.participants_count <= Callback.DEFAULT_DRAG_ANIMATION_DURATION && currentChatInfo.participants != null)) {
                    for (int i = 0; i < currentChatInfo.participants.participants.size(); i++) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) currentChatInfo.participants.participants.get(i)).user_id));
                        if (!(user == null || user.status == null || ((user.status.expires <= currentTime && user.id != UserConfig.getClientUserId()) || user.status.expires <= 10000))) {
                            this.onlineCount++;
                        }
                    }
                }
            }
        }
    }

    public void updateSubtitle() {
        if (this.parentFragment != null) {
            User currentUser = this.parentFragment.getCurrentUser();
            if (!UserObject.isUserSelf(currentUser)) {
                Chat currentChat = this.parentFragment.getCurrentChat();
                CharSequence charSequence = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.parentFragment.getDialogId()));
                if (charSequence != null) {
                    charSequence = TextUtils.replace(charSequence, new String[]{"..."}, new String[]{TtmlNode.ANONYMOUS_REGION_ID});
                }
                if (charSequence == null || charSequence.length() == 0 || (ChatObject.isChannel(currentChat) && !currentChat.megagroup)) {
                    setTypingAnimation(false);
                    if (currentChat != null) {
                        ChatFull currentChatInfo = this.parentFragment.getCurrentChatInfo();
                        if (ChatObject.isChannel(currentChat)) {
                            if (currentChatInfo == null || currentChatInfo.participants_count == 0) {
                                charSequence = currentChat.megagroup ? LocaleController.getString("Loading", R.string.Loading).toLowerCase() : (currentChat.flags & 64) != 0 ? LocaleController.getString("ChannelPublic", R.string.ChannelPublic).toLowerCase() : LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate).toLowerCase();
                            } else if (!currentChat.megagroup || currentChatInfo.participants_count > Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                                int[] iArr = new int[1];
                                CharSequence formatShortNumber = LocaleController.formatShortNumber(currentChatInfo.participants_count, iArr);
                                charSequence = currentChat.megagroup ? LocaleController.formatPluralString("Members", iArr[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr[0])}), formatShortNumber) : LocaleController.formatPluralString("Subscribers", iArr[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr[0])}), formatShortNumber);
                            } else {
                                charSequence = (this.onlineCount <= 1 || currentChatInfo.participants_count == 0) ? LocaleController.formatPluralString("Members", currentChatInfo.participants_count) : String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", currentChatInfo.participants_count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                            }
                        } else if (ChatObject.isKickedFromChat(currentChat)) {
                            charSequence = LocaleController.getString("YouWereKicked", R.string.YouWereKicked);
                        } else if (ChatObject.isLeftFromChat(currentChat)) {
                            charSequence = LocaleController.getString("YouLeft", R.string.YouLeft);
                        } else {
                            int i = currentChat.participants_count;
                            if (!(currentChatInfo == null || currentChatInfo.participants == null)) {
                                i = currentChatInfo.participants.participants.size();
                            }
                            charSequence = (this.onlineCount <= 1 || i == 0) ? LocaleController.formatPluralString("Members", i) : String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", i), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                        }
                    } else if (currentUser != null) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(currentUser.id));
                        if (user == null) {
                            user = currentUser;
                        }
                        charSequence = user.id == UserConfig.getClientUserId() ? LocaleController.getString("ChatYourSelf", R.string.ChatYourSelf) : (user.id == 333000 || user.id == 777000) ? LocaleController.getString("ServiceNotifications", R.string.ServiceNotifications) : user.bot ? LocaleController.getString("Bot", R.string.Bot) : LocaleController.formatUserStatus(user);
                    } else {
                        charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                } else {
                    setTypingAnimation(true);
                }
                if (this.lastSubtitle == null) {
                    this.subtitleTextView.setText(charSequence);
                } else {
                    this.lastSubtitle = charSequence;
                }
            } else if (this.subtitleTextView.getVisibility() != 8) {
                this.subtitleTextView.setVisibility(8);
            }
        }
    }
}
