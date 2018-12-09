package org.telegram.ui;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.aa;
import android.text.style.CharacterStyle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.MenuDrawable;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.Cells.ChatActionCell;
import org.telegram.ui.Cells.ChatActionCell.ChatActionCellDelegate;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.DialogCell.CustomDialog;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.SizeNotifierFrameLayout;

public class ThemePreviewActivity extends BaseFragment implements NotificationCenterDelegate {
    private ActionBar actionBar2;
    private boolean applied;
    private ThemeInfo applyingTheme;
    private DialogsAdapter dialogsAdapter;
    private View dotsContainer;
    private ImageView floatingButton;
    private RecyclerListView listView;
    private RecyclerListView listView2;
    private MessagesAdapter messagesAdapter;
    private FrameLayout page1;
    private SizeNotifierFrameLayout page2;
    private File themeFile;

    /* renamed from: org.telegram.ui.ThemePreviewActivity$1 */
    class C52331 extends ActionBarMenuItemSearchListener {
        C52331() {
        }

        public boolean canCollapseSearch() {
            return true;
        }

        public void onSearchCollapse() {
        }

        public void onSearchExpand() {
        }

        public void onTextChanged(EditText editText) {
        }
    }

    /* renamed from: org.telegram.ui.ThemePreviewActivity$3 */
    class C52353 extends ViewOutlineProvider {
        C52353() {
        }

        @SuppressLint({"NewApi"})
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        }
    }

    /* renamed from: org.telegram.ui.ThemePreviewActivity$5 */
    class C52375 implements C0188f {
        C52375() {
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            ThemePreviewActivity.this.dotsContainer.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.ThemePreviewActivity$6 */
    class C52386 extends aa {
        C52386() {
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            return 2;
        }

        public int getItemPosition(Object obj) {
            return -1;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View access$500 = i == 0 ? ThemePreviewActivity.this.page1 : ThemePreviewActivity.this.page2;
            viewGroup.addView(access$500);
            return access$500;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return obj == view;
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (dataSetObserver != null) {
                super.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    /* renamed from: org.telegram.ui.ThemePreviewActivity$8 */
    class C52408 implements OnClickListener {
        C52408() {
        }

        public void onClick(View view) {
            Theme.applyPreviousTheme();
            ThemePreviewActivity.this.parentLayout.rebuildAllFragmentViews(false, false);
            ThemePreviewActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.ThemePreviewActivity$9 */
    class C52419 implements OnClickListener {
        C52419() {
        }

        public void onClick(View view) {
            ThemePreviewActivity.this.applied = true;
            ThemePreviewActivity.this.parentLayout.rebuildAllFragmentViews(false, false);
            Theme.applyThemeFile(ThemePreviewActivity.this.themeFile, ThemePreviewActivity.this.applyingTheme.name, false);
            ThemePreviewActivity.this.finishFragment();
        }
    }

    public class DialogsAdapter extends SelectionAdapter {
        private ArrayList<CustomDialog> dialogs = new ArrayList();
        private Context mContext;

        public DialogsAdapter(Context context) {
            this.mContext = context;
            int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
            CustomDialog customDialog = new CustomDialog();
            customDialog.name = "Eva Summer";
            customDialog.message = "Reminds me of a Chinese prove...";
            customDialog.id = 0;
            customDialog.unread_count = 0;
            customDialog.pinned = true;
            customDialog.muted = false;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis;
            customDialog.verified = false;
            customDialog.isMedia = false;
            customDialog.sent = true;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Alexandra Smith";
            customDialog.message = "Reminds me of a Chinese prove...";
            customDialog.id = 1;
            customDialog.unread_count = 2;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis - 3600;
            customDialog.verified = false;
            customDialog.isMedia = false;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Make Apple";
            customDialog.message = "🤷‍♂️ Sticker";
            customDialog.id = 2;
            customDialog.unread_count = 3;
            customDialog.pinned = false;
            customDialog.muted = true;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis - 7200;
            customDialog.verified = false;
            customDialog.isMedia = true;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Paul Newman";
            customDialog.message = "Any ideas?";
            customDialog.id = 3;
            customDialog.unread_count = 0;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 2;
            customDialog.date = currentTimeMillis - 10800;
            customDialog.verified = false;
            customDialog.isMedia = false;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Old Pirates";
            customDialog.message = "Yo-ho-ho!";
            customDialog.id = 4;
            customDialog.unread_count = 0;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 1;
            customDialog.date = currentTimeMillis - 14400;
            customDialog.verified = false;
            customDialog.isMedia = false;
            customDialog.sent = true;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Kate Bright";
            customDialog.message = "Hola!";
            customDialog.id = 5;
            customDialog.unread_count = 0;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis - 18000;
            customDialog.verified = false;
            customDialog.isMedia = false;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Nick K";
            customDialog.message = "These are not the droids you are looking for";
            customDialog.id = 6;
            customDialog.unread_count = 0;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis - 21600;
            customDialog.verified = true;
            customDialog.isMedia = false;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
            customDialog = new CustomDialog();
            customDialog.name = "Adler Toberg";
            customDialog.message = "Did someone say peanut butter?";
            customDialog.id = 0;
            customDialog.unread_count = 0;
            customDialog.pinned = false;
            customDialog.muted = false;
            customDialog.type = 0;
            customDialog.date = currentTimeMillis - 25200;
            customDialog.verified = true;
            customDialog.isMedia = false;
            customDialog.sent = false;
            customDialog.isSelected = false;
            this.dialogs.add(customDialog);
        }

        public int getItemCount() {
            return this.dialogs.size() + 1;
        }

        public int getItemViewType(int i) {
            return i == this.dialogs.size() ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                DialogCell dialogCell = (DialogCell) viewHolder.itemView;
                dialogCell.useSeparator = i != getItemCount() + -1;
                dialogCell.setDialog((CustomDialog) this.dialogs.get(i));
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            if (i == 0) {
                view = new DialogCell(this.mContext, false);
            } else if (i == 1) {
                view = new LoadingCell(this.mContext);
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    public class MessagesAdapter extends SelectionAdapter {
        private Context mContext;
        private ArrayList<MessageObject> messages = new ArrayList();

        /* renamed from: org.telegram.ui.ThemePreviewActivity$MessagesAdapter$1 */
        class C52421 implements ChatMessageCellDelegate {
            C52421() {
            }

            public boolean canPerformActions() {
                return false;
            }

            public void didLongPressed(ChatMessageCell chatMessageCell) {
            }

            public void didPressedBotButton(ChatMessageCell chatMessageCell, KeyboardButton keyboardButton) {
            }

            public void didPressedCancelSendButton(ChatMessageCell chatMessageCell) {
            }

            public void didPressedChannelAvatar(ChatMessageCell chatMessageCell, Chat chat, int i) {
            }

            public void didPressedImage(ChatMessageCell chatMessageCell) {
            }

            public void didPressedInstantButton(ChatMessageCell chatMessageCell, int i) {
            }

            public void didPressedOther(ChatMessageCell chatMessageCell) {
            }

            public void didPressedReplyMessage(ChatMessageCell chatMessageCell, int i) {
            }

            public void didPressedShare(ChatMessageCell chatMessageCell) {
            }

            public void didPressedUrl(MessageObject messageObject, CharacterStyle characterStyle, boolean z) {
            }

            public void didPressedUserAvatar(ChatMessageCell chatMessageCell, User user) {
            }

            public void didPressedViaBot(ChatMessageCell chatMessageCell, String str) {
            }

            public void didTagPressed(String str) {
            }

            public boolean isChatAdminCell(int i) {
                return false;
            }

            public void needOpenWebView(String str, String str2, String str3, String str4, int i, int i2) {
            }

            public boolean needPlayMessage(MessageObject messageObject) {
                return false;
            }
        }

        /* renamed from: org.telegram.ui.ThemePreviewActivity$MessagesAdapter$2 */
        class C52432 implements ChatActionCellDelegate {
            C52432() {
            }

            public void didClickedImage(ChatActionCell chatActionCell) {
            }

            public void didLongPressed(ChatActionCell chatActionCell) {
            }

            public void didPressedBotButton(MessageObject messageObject, KeyboardButton keyboardButton) {
            }

            public void didPressedReplyMessage(ChatActionCell chatActionCell, int i) {
            }

            public void needOpenUserProfile(int i) {
            }
        }

        public MessagesAdapter(Context context) {
            this.mContext = context;
            int currentTimeMillis = ((int) (System.currentTimeMillis() / 1000)) - 3600;
            Message tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.message = "Reinhardt, we need to find you some new tunes 🎶.";
            tLRPC$TL_message.date = currentTimeMillis + 60;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 259;
            tLRPC$TL_message.from_id = UserConfig.getClientUserId();
            tLRPC$TL_message.id = 1;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
            tLRPC$TL_message.out = true;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = 0;
            MessageObject messageObject = new MessageObject(tLRPC$TL_message, null, true);
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.message = "I can't even take you seriously right now.";
            tLRPC$TL_message.date = currentTimeMillis + 960;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 259;
            tLRPC$TL_message.from_id = UserConfig.getClientUserId();
            tLRPC$TL_message.id = 1;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
            tLRPC$TL_message.out = true;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = 0;
            this.messages.add(new MessageObject(tLRPC$TL_message, null, true));
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.date = currentTimeMillis + TsExtractor.TS_STREAM_TYPE_HDMV_DTS;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 259;
            tLRPC$TL_message.from_id = 0;
            tLRPC$TL_message.id = 5;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
            MessageMedia messageMedia = tLRPC$TL_message.media;
            messageMedia.flags |= 3;
            tLRPC$TL_message.media.document = new TLRPC$TL_document();
            tLRPC$TL_message.media.document.mime_type = MimeTypes.AUDIO_MP4;
            tLRPC$TL_message.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
            tLRPC$TL_message.media.document.thumb.type = "s";
            TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
            tLRPC$TL_documentAttributeAudio.duration = 243;
            tLRPC$TL_documentAttributeAudio.performer = "David Hasselhoff";
            tLRPC$TL_documentAttributeAudio.title = "True Survivor";
            tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = UserConfig.getClientUserId();
            this.messages.add(new MessageObject(tLRPC$TL_message, null, true));
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.message = "Ah, you kids today with techno music! You should enjoy the classics, like Hasselhoff!";
            tLRPC$TL_message.date = currentTimeMillis + 60;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 265;
            tLRPC$TL_message.from_id = 0;
            tLRPC$TL_message.id = 1;
            tLRPC$TL_message.reply_to_msg_id = 5;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = UserConfig.getClientUserId();
            MessageObject messageObject2 = new MessageObject(tLRPC$TL_message, null, true);
            messageObject2.customReplyName = "Lucio";
            messageObject2.replyMessageObject = messageObject;
            this.messages.add(messageObject2);
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.date = currentTimeMillis + 120;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 259;
            tLRPC$TL_message.from_id = UserConfig.getClientUserId();
            tLRPC$TL_message.id = 1;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
            messageMedia = tLRPC$TL_message.media;
            messageMedia.flags |= 3;
            tLRPC$TL_message.media.document = new TLRPC$TL_document();
            tLRPC$TL_message.media.document.mime_type = "audio/ogg";
            tLRPC$TL_message.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
            tLRPC$TL_message.media.document.thumb.type = "s";
            tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
            tLRPC$TL_documentAttributeAudio.flags = 1028;
            tLRPC$TL_documentAttributeAudio.duration = 3;
            tLRPC$TL_documentAttributeAudio.voice = true;
            tLRPC$TL_documentAttributeAudio.waveform = new byte[]{(byte) 0, (byte) 4, (byte) 17, (byte) -50, (byte) -93, (byte) 86, (byte) -103, (byte) -45, (byte) -12, (byte) -26, (byte) 63, (byte) -25, (byte) -3, (byte) 109, (byte) -114, (byte) -54, (byte) -4, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -29, (byte) -1, (byte) -1, (byte) -25, (byte) -1, (byte) -1, (byte) -97, (byte) -43, (byte) 57, (byte) -57, (byte) -108, (byte) 1, (byte) -91, (byte) -4, (byte) -47, (byte) 21, (byte) 99, (byte) 10, (byte) 97, (byte) 43, (byte) 45, (byte) 115, (byte) -112, (byte) -77, (byte) 51, (byte) -63, (byte) 66, (byte) 40, (byte) 34, (byte) -122, (byte) -116, (byte) 48, (byte) -124, (byte) 16, (byte) 66, (byte) -120, (byte) 16, (byte) 68, (byte) 16, (byte) 33, (byte) 4, (byte) 1};
            tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
            tLRPC$TL_message.out = true;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = 0;
            messageObject2 = new MessageObject(tLRPC$TL_message, null, true);
            messageObject2.audioProgressSec = 1;
            messageObject2.audioProgress = 0.3f;
            messageObject2.useCustomPhoto = true;
            this.messages.add(messageObject2);
            this.messages.add(messageObject);
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.date = currentTimeMillis + 10;
            tLRPC$TL_message.dialog_id = 1;
            tLRPC$TL_message.flags = 257;
            tLRPC$TL_message.from_id = 0;
            tLRPC$TL_message.id = 1;
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaPhoto();
            MessageMedia messageMedia2 = tLRPC$TL_message.media;
            messageMedia2.flags |= 3;
            tLRPC$TL_message.media.photo = new TLRPC$TL_photo();
            tLRPC$TL_message.media.photo.has_stickers = false;
            tLRPC$TL_message.media.photo.id = 1;
            tLRPC$TL_message.media.photo.access_hash = 0;
            tLRPC$TL_message.media.photo.date = currentTimeMillis;
            TLRPC$TL_photoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
            tLRPC$TL_photoSize.size = 0;
            tLRPC$TL_photoSize.w = ChatActivity.startAllServices;
            tLRPC$TL_photoSize.h = 302;
            tLRPC$TL_photoSize.type = "s";
            tLRPC$TL_photoSize.location = new TLRPC$TL_fileLocationUnavailable();
            tLRPC$TL_message.media.photo.sizes.add(tLRPC$TL_photoSize);
            tLRPC$TL_message.media.caption = "Bring it on! I LIVE for this!";
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            tLRPC$TL_message.to_id.user_id = UserConfig.getClientUserId();
            messageObject = new MessageObject(tLRPC$TL_message, null, true);
            messageObject.useCustomPhoto = true;
            this.messages.add(messageObject);
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.message = LocaleController.formatDateChat((long) currentTimeMillis);
            tLRPC$TL_message.id = 0;
            tLRPC$TL_message.date = currentTimeMillis;
            MessageObject messageObject3 = new MessageObject(tLRPC$TL_message, null, false);
            messageObject3.type = 10;
            messageObject3.contentType = 1;
            messageObject3.isDateObject = true;
            this.messages.add(messageObject3);
        }

        public int getItemCount() {
            return this.messages.size();
        }

        public int getItemViewType(int i) {
            return (i < 0 || i >= this.messages.size()) ? 4 : ((MessageObject) this.messages.get(i)).contentType;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            MessageObject messageObject = (MessageObject) this.messages.get(i);
            View view = viewHolder.itemView;
            if (view instanceof ChatMessageCell) {
                boolean z2;
                MessageObject messageObject2;
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                chatMessageCell.isChat = false;
                int itemViewType = getItemViewType(i - 1);
                int itemViewType2 = getItemViewType(i + 1);
                if ((messageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) || itemViewType != viewHolder.getItemViewType()) {
                    z2 = false;
                } else {
                    messageObject2 = (MessageObject) this.messages.get(i - 1);
                    boolean z3 = messageObject2.isOutOwner() == messageObject.isOutOwner() && Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) <= 300;
                    z2 = z3;
                }
                if (itemViewType2 == viewHolder.getItemViewType()) {
                    messageObject2 = (MessageObject) this.messages.get(i + 1);
                    if (!(messageObject2.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) && messageObject2.isOutOwner() == messageObject.isOutOwner() && Math.abs(messageObject2.messageOwner.date - messageObject.messageOwner.date) <= 300) {
                        z = true;
                    }
                }
                chatMessageCell.setFullyDraw(true);
                chatMessageCell.setMessageObject(messageObject, null, z2, z);
            } else if (view instanceof ChatActionCell) {
                ChatActionCell chatActionCell = (ChatActionCell) view;
                chatActionCell.setMessageObject(messageObject);
                chatActionCell.setAlpha(1.0f);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            if (i == 0) {
                view = new ChatMessageCell(this.mContext);
                ((ChatMessageCell) view).setDelegate(new C52421());
            } else if (i == 1) {
                view = new ChatActionCell(this.mContext);
                ((ChatActionCell) view).setDelegate(new C52432());
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    public ThemePreviewActivity(File file, ThemeInfo themeInfo) {
        this.swipeBackEnabled = false;
        this.applyingTheme = themeInfo;
        this.themeFile = file;
    }

    public View createView(Context context) {
        Drawable combinedDrawable;
        this.page1 = new FrameLayout(context);
        this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C52331()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.actionBar.setBackButtonDrawable(new MenuDrawable());
        this.actionBar.setAddToContainer(false);
        this.actionBar.setTitle(LocaleController.getString("ThemePreview", R.string.ThemePreview));
        this.page1 = new FrameLayout(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == ThemePreviewActivity.this.actionBar && ThemePreviewActivity.this.parentLayout != null) {
                    ThemePreviewActivity.this.parentLayout.drawHeaderShadow(canvas, ThemePreviewActivity.this.actionBar.getVisibility() == 0 ? ThemePreviewActivity.this.actionBar.getMeasuredHeight() : 0);
                }
                return drawChild;
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                measureChildWithMargins(ThemePreviewActivity.this.actionBar, i, 0, i2, 0);
                int measuredHeight = ThemePreviewActivity.this.actionBar.getMeasuredHeight();
                int i3 = ThemePreviewActivity.this.actionBar.getVisibility() == 0 ? size2 - measuredHeight : size2;
                ((FrameLayout.LayoutParams) ThemePreviewActivity.this.listView.getLayoutParams()).topMargin = measuredHeight;
                ThemePreviewActivity.this.listView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(i3, 1073741824));
                measureChildWithMargins(ThemePreviewActivity.this.floatingButton, i, 0, i2, 0);
            }
        };
        this.page1.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(true);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        this.page1.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.floatingButton = new ImageView(context);
        this.floatingButton.setScaleType(ScaleType.CENTER);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (VERSION.SDK_INT < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
            combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
        } else {
            combinedDrawable = createSimpleSelectorCircleDrawable;
        }
        this.floatingButton.setBackgroundDrawable(combinedDrawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.floating_pencil);
        if (VERSION.SDK_INT >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
            this.floatingButton.setStateListAnimator(stateListAnimator);
            this.floatingButton.setOutlineProvider(new C52353());
        }
        this.page1.addView(this.floatingButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, (LocaleController.isRTL ? 3 : 5) | 80, LocaleController.isRTL ? 14.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 14.0f, 14.0f));
        this.dialogsAdapter = new DialogsAdapter(context);
        this.listView.setAdapter(this.dialogsAdapter);
        this.page2 = new SizeNotifierFrameLayout(context) {
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == ThemePreviewActivity.this.actionBar2 && ThemePreviewActivity.this.parentLayout != null) {
                    ThemePreviewActivity.this.parentLayout.drawHeaderShadow(canvas, ThemePreviewActivity.this.actionBar2.getVisibility() == 0 ? ThemePreviewActivity.this.actionBar2.getMeasuredHeight() : 0);
                }
                return drawChild;
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                measureChildWithMargins(ThemePreviewActivity.this.actionBar2, i, 0, i2, 0);
                int measuredHeight = ThemePreviewActivity.this.actionBar2.getMeasuredHeight();
                int i3 = ThemePreviewActivity.this.actionBar2.getVisibility() == 0 ? size2 - measuredHeight : size2;
                ((FrameLayout.LayoutParams) ThemePreviewActivity.this.listView2.getLayoutParams()).topMargin = measuredHeight;
                ThemePreviewActivity.this.listView2.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(i3, 1073741824));
            }
        };
        this.page2.setBackgroundImage(Theme.getCachedWallpaper());
        this.actionBar2 = createActionBar(context);
        this.actionBar2.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar2.setTitle("Reinhardt");
        this.actionBar2.setSubtitle(LocaleController.formatDateOnline((System.currentTimeMillis() / 1000) - 3600));
        this.page2.addView(this.actionBar2, LayoutHelper.createFrame(-1, -2.0f));
        this.listView2 = new RecyclerListView(context);
        this.listView2.setVerticalScrollBarEnabled(true);
        this.listView2.setItemAnimator(null);
        this.listView2.setLayoutAnimation(null);
        this.listView2.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f));
        this.listView2.setClipToPadding(false);
        this.listView2.setLayoutManager(new LinearLayoutManager(context, 1, true));
        this.listView2.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        this.page2.addView(this.listView2, LayoutHelper.createFrame(-1, -1, 51));
        this.messagesAdapter = new MessagesAdapter(context);
        this.listView2.setAdapter(this.messagesAdapter);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        final View viewPager = new ViewPager(context);
        viewPager.addOnPageChangeListener(new C52375());
        viewPager.setAdapter(new C52386());
        AndroidUtilities.setViewPagerEdgeEffectColor(viewPager, Theme.getColor(Theme.key_actionBarDefault));
        frameLayout.addView(viewPager, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        frameLayout.addView(view, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        View frameLayout2 = new FrameLayout(context);
        frameLayout2.setBackgroundColor(-1);
        frameLayout.addView(frameLayout2, LayoutHelper.createFrame(-1, 48, 83));
        this.dotsContainer = new View(context) {
            private Paint paint = new Paint(1);

            protected void onDraw(Canvas canvas) {
                int currentItem = viewPager.getCurrentItem();
                int i = 0;
                while (i < 2) {
                    this.paint.setColor(i == currentItem ? -6710887 : -3355444);
                    canvas.drawCircle((float) AndroidUtilities.dp((float) ((i * 15) + 3)), (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(3.0f), this.paint);
                    i++;
                }
            }
        };
        frameLayout2.addView(this.dotsContainer, LayoutHelper.createFrame(22, 8, 17));
        View textView = new TextView(context);
        textView.setTextSize(1, 14.0f);
        textView.setTextColor(-15095832);
        textView.setGravity(17);
        textView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
        textView.setPadding(AndroidUtilities.dp(29.0f), 0, AndroidUtilities.dp(29.0f), 0);
        textView.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        frameLayout2.addView(textView, LayoutHelper.createFrame(-2, -1, 51));
        textView.setOnClickListener(new C52408());
        textView = new TextView(context);
        textView.setTextSize(1, 14.0f);
        textView.setTextColor(-15095832);
        textView.setGravity(17);
        textView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
        textView.setPadding(AndroidUtilities.dp(29.0f), 0, AndroidUtilities.dp(29.0f), 0);
        textView.setText(LocaleController.getString("ApplyTheme", R.string.ApplyTheme).toUpperCase());
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        frameLayout2.addView(textView, LayoutHelper.createFrame(-2, -1, 53));
        textView.setOnClickListener(new C52419());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded && this.listView != null) {
            int childCount = this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.listView.getChildAt(i2);
                if (childAt instanceof DialogCell) {
                    ((DialogCell) childAt).update(0);
                }
            }
        }
    }

    public boolean onBackPressed() {
        Theme.applyPreviousTheme();
        this.parentLayout.rebuildAllFragmentViews(false, false);
        return super.onBackPressed();
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.dialogsAdapter != null) {
            this.dialogsAdapter.notifyDataSetChanged();
        }
        if (this.messagesAdapter != null) {
            this.messagesAdapter.notifyDataSetChanged();
        }
    }
}
