package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemDelegate;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.AudioPlayerCell;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate;
import org.telegram.ui.DialogsActivity;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.LaunchActivity;

public class AudioPlayerAlert extends BottomSheet implements FileDownloadProgressListener, NotificationCenterDelegate {
    private int TAG;
    private ActionBar actionBar;
    private AnimatorSet actionBarAnimation;
    private AnimatorSet animatorSet;
    private TextView authorTextView;
    private ChatAvatarContainer avatarContainer;
    private View[] buttons = new View[5];
    private TextView durationTextView;
    private float endTranslation;
    private float fullAnimationProgress;
    private boolean hasNoCover;
    private boolean hasOptions = true;
    private boolean inFullSize;
    private boolean isInFullMode;
    private int lastTime;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private ActionBarMenuItem menuItem;
    private Drawable noCoverDrawable;
    private ActionBarMenuItem optionsButton;
    private Paint paint = new Paint(1);
    private float panelEndTranslation;
    private float panelStartTranslation;
    private LaunchActivity parentActivity;
    private BackupImageView placeholderImageView;
    private ImageView playButton;
    private Drawable[] playOrderButtons = new Drawable[2];
    private FrameLayout playerLayout;
    private ArrayList<MessageObject> playlist = new ArrayList();
    private LineProgressView progressView;
    private ImageView repeatButton;
    private int scrollOffsetY = Integer.MAX_VALUE;
    private boolean scrollToSong = true;
    private ActionBarMenuItem searchItem;
    private int searchOpenOffset;
    private int searchOpenPosition = -1;
    private boolean searchWas;
    private boolean searching;
    private SeekBarView seekBarView;
    private View shadow;
    private View shadow2;
    private Drawable shadowDrawable;
    private ActionBarMenuItem shuffleButton;
    private float startTranslation;
    private float thumbMaxScale;
    private int thumbMaxX;
    private int thumbMaxY;
    private SimpleTextView timeTextView;
    private TextView titleTextView;
    private int topBeforeSwitch;

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$2 */
    class C43242 extends ActionBarMenuItemSearchListener {
        C43242() {
        }

        public void onSearchCollapse() {
            AudioPlayerAlert.this.avatarContainer.setVisibility(0);
            if (AudioPlayerAlert.this.hasOptions) {
                AudioPlayerAlert.this.menuItem.setVisibility(4);
            }
            if (AudioPlayerAlert.this.searching) {
                AudioPlayerAlert.this.searchWas = false;
                AudioPlayerAlert.this.searching = false;
                AudioPlayerAlert.this.setAllowNestedScroll(true);
                AudioPlayerAlert.this.listAdapter.search(null);
            }
        }

        public void onSearchExpand() {
            AudioPlayerAlert.this.searchOpenPosition = AudioPlayerAlert.this.layoutManager.findLastVisibleItemPosition();
            View findViewByPosition = AudioPlayerAlert.this.layoutManager.findViewByPosition(AudioPlayerAlert.this.searchOpenPosition);
            AudioPlayerAlert.this.searchOpenOffset = (findViewByPosition == null ? 0 : findViewByPosition.getTop()) - AudioPlayerAlert.this.listView.getPaddingTop();
            AudioPlayerAlert.this.avatarContainer.setVisibility(8);
            if (AudioPlayerAlert.this.hasOptions) {
                AudioPlayerAlert.this.menuItem.setVisibility(8);
            }
            AudioPlayerAlert.this.searching = true;
            AudioPlayerAlert.this.setAllowNestedScroll(false);
            AudioPlayerAlert.this.listAdapter.notifyDataSetChanged();
        }

        public void onTextChanged(EditText editText) {
            if (editText.length() > 0) {
                AudioPlayerAlert.this.searchWas = true;
                AudioPlayerAlert.this.listAdapter.search(editText.getText().toString());
                return;
            }
            AudioPlayerAlert.this.searchWas = false;
            AudioPlayerAlert.this.listAdapter.search(null);
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$3 */
    class C43253 extends ActionBarMenuOnItemClick {
        C43253() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                AudioPlayerAlert.this.dismiss();
            } else {
                AudioPlayerAlert.this.onSubItemClick(i);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$5 */
    class C43285 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$5$1 */
        class C43271 extends AnimatorListenerAdapter {
            C43271() {
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(AudioPlayerAlert.this.animatorSet)) {
                    if (AudioPlayerAlert.this.isInFullMode) {
                        if (AudioPlayerAlert.this.hasOptions) {
                            AudioPlayerAlert.this.menuItem.setVisibility(0);
                        }
                        AudioPlayerAlert.this.searchItem.setVisibility(4);
                    } else {
                        AudioPlayerAlert.this.listView.setScrollEnabled(true);
                        if (AudioPlayerAlert.this.hasOptions) {
                            AudioPlayerAlert.this.menuItem.setVisibility(4);
                        }
                        AudioPlayerAlert.this.searchItem.setVisibility(0);
                    }
                    AudioPlayerAlert.this.animatorSet = null;
                }
            }
        }

        C43285() {
        }

        public void onClick(View view) {
            float f = BitmapDescriptorFactory.HUE_RED;
            if (AudioPlayerAlert.this.animatorSet != null) {
                AudioPlayerAlert.this.animatorSet.cancel();
                AudioPlayerAlert.this.animatorSet = null;
            }
            AudioPlayerAlert.this.animatorSet = new AnimatorSet();
            if (AudioPlayerAlert.this.scrollOffsetY <= AudioPlayerAlert.this.actionBar.getMeasuredHeight()) {
                AnimatorSet access$3100 = AudioPlayerAlert.this.animatorSet;
                Animator[] animatorArr = new Animator[1];
                AudioPlayerAlert audioPlayerAlert = AudioPlayerAlert.this;
                String str = "fullAnimationProgress";
                float[] fArr = new float[1];
                if (!AudioPlayerAlert.this.isInFullMode) {
                    f = 1.0f;
                }
                fArr[0] = f;
                animatorArr[0] = ObjectAnimator.ofFloat(audioPlayerAlert, str, fArr);
                access$3100.playTogether(animatorArr);
            } else {
                AnimatorSet access$31002 = AudioPlayerAlert.this.animatorSet;
                Animator[] animatorArr2 = new Animator[4];
                AudioPlayerAlert audioPlayerAlert2 = AudioPlayerAlert.this;
                String str2 = "fullAnimationProgress";
                float[] fArr2 = new float[1];
                fArr2[0] = AudioPlayerAlert.this.isInFullMode ? 0.0f : 1.0f;
                animatorArr2[0] = ObjectAnimator.ofFloat(audioPlayerAlert2, str2, fArr2);
                ActionBar access$1300 = AudioPlayerAlert.this.actionBar;
                str2 = "alpha";
                fArr2 = new float[1];
                fArr2[0] = AudioPlayerAlert.this.isInFullMode ? 0.0f : 1.0f;
                animatorArr2[1] = ObjectAnimator.ofFloat(access$1300, str2, fArr2);
                View access$1400 = AudioPlayerAlert.this.shadow;
                String str3 = "alpha";
                float[] fArr3 = new float[1];
                fArr3[0] = AudioPlayerAlert.this.isInFullMode ? 0.0f : 1.0f;
                animatorArr2[2] = ObjectAnimator.ofFloat(access$1400, str3, fArr3);
                View access$3300 = AudioPlayerAlert.this.shadow2;
                str2 = "alpha";
                fArr2 = new float[1];
                if (!AudioPlayerAlert.this.isInFullMode) {
                    f = 1.0f;
                }
                fArr2[0] = f;
                animatorArr2[3] = ObjectAnimator.ofFloat(access$3300, str2, fArr2);
                access$31002.playTogether(animatorArr2);
            }
            AudioPlayerAlert.this.animatorSet.setInterpolator(new DecelerateInterpolator());
            AudioPlayerAlert.this.animatorSet.setDuration(250);
            AudioPlayerAlert.this.animatorSet.addListener(new C43271());
            AudioPlayerAlert.this.animatorSet.start();
            if (AudioPlayerAlert.this.hasOptions) {
                AudioPlayerAlert.this.menuItem.setVisibility(0);
            }
            AudioPlayerAlert.this.searchItem.setVisibility(0);
            AudioPlayerAlert.this.isInFullMode = !AudioPlayerAlert.this.isInFullMode;
            AudioPlayerAlert.this.listView.setScrollEnabled(false);
            if (AudioPlayerAlert.this.isInFullMode) {
                AudioPlayerAlert.this.shuffleButton.setAdditionalOffset(-AndroidUtilities.dp(68.0f));
            } else {
                AudioPlayerAlert.this.shuffleButton.setAdditionalOffset(-AndroidUtilities.dp(10.0f));
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$6 */
    class C43296 implements ActionBarMenuItemDelegate {
        C43296() {
        }

        public void onItemClick(int i) {
            AudioPlayerAlert.this.onSubItemClick(i);
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$7 */
    class C43307 implements SeekBarViewDelegate {
        C43307() {
        }

        public void onSeekBarDrag(float f) {
            MediaController.getInstance().seekToProgress(MediaController.getInstance().getPlayingMessageObject(), f);
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$9 */
    class C43329 implements ActionBarMenuItemDelegate {
        C43329() {
        }

        public void onItemClick(int i) {
            MediaController.getInstance().toggleShuffleMusic(i);
            AudioPlayerAlert.this.updateShuffleButton();
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context context;
        private ArrayList<MessageObject> searchResult = new ArrayList();
        private Timer searchTimer;

        public ListAdapter(Context context) {
            this.context = context;
        }

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(AudioPlayerAlert.this.playlist);
                    Utilities.searchQueue.postRunnable(new Runnable() {
                        public void run() {
                            String toLowerCase = str.trim().toLowerCase();
                            if (toLowerCase.length() == 0) {
                                ListAdapter.this.updateSearchResults(new ArrayList());
                                return;
                            }
                            String translitString = LocaleController.getInstance().getTranslitString(toLowerCase);
                            String str = (toLowerCase.equals(translitString) || translitString.length() == 0) ? null : translitString;
                            String[] strArr = new String[((str != null ? 1 : 0) + 1)];
                            strArr[0] = toLowerCase;
                            if (str != null) {
                                strArr[1] = str;
                            }
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < arrayList.size(); i++) {
                                MessageObject messageObject = (MessageObject) arrayList.get(i);
                                for (CharSequence charSequence : strArr) {
                                    String documentName = messageObject.getDocumentName();
                                    if (!(documentName == null || documentName.length() == 0)) {
                                        if (!documentName.toLowerCase().contains(charSequence)) {
                                            boolean contains;
                                            Document document = messageObject.type == 0 ? messageObject.messageOwner.media.webpage.document : messageObject.messageOwner.media.document;
                                            int i2 = 0;
                                            while (i2 < document.attributes.size()) {
                                                DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i2);
                                                if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                                    boolean contains2 = documentAttribute.performer != null ? documentAttribute.performer.toLowerCase().contains(charSequence) : false;
                                                    contains = (contains2 || documentAttribute.title == null) ? contains2 : documentAttribute.title.toLowerCase().contains(charSequence);
                                                    if (contains) {
                                                        arrayList.add(messageObject);
                                                        break;
                                                    }
                                                } else {
                                                    i2++;
                                                }
                                            }
                                            contains = false;
                                            if (contains) {
                                                arrayList.add(messageObject);
                                                break;
                                            }
                                        } else {
                                            arrayList.add(messageObject);
                                            break;
                                        }
                                    }
                                }
                            }
                            ListAdapter.this.updateSearchResults(arrayList);
                        }
                    });
                }
            });
        }

        private void updateSearchResults(final ArrayList<MessageObject> arrayList) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ListAdapter.this.searchResult = arrayList;
                    ListAdapter.this.notifyDataSetChanged();
                    AudioPlayerAlert.this.layoutManager.scrollToPosition(0);
                }
            });
        }

        public int getItemCount() {
            return AudioPlayerAlert.this.searchWas ? this.searchResult.size() : AudioPlayerAlert.this.searching ? AudioPlayerAlert.this.playlist.size() : AudioPlayerAlert.this.playlist.size() + 1;
        }

        public int getItemViewType(int i) {
            return (AudioPlayerAlert.this.searchWas || AudioPlayerAlert.this.searching || i != 0) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return AudioPlayerAlert.this.searchWas || viewHolder.getAdapterPosition() > 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 1) {
                AudioPlayerCell audioPlayerCell = (AudioPlayerCell) viewHolder.itemView;
                if (AudioPlayerAlert.this.searchWas) {
                    audioPlayerCell.setMessageObject((MessageObject) this.searchResult.get(i));
                } else if (AudioPlayerAlert.this.searching) {
                    audioPlayerCell.setMessageObject((MessageObject) AudioPlayerAlert.this.playlist.get((AudioPlayerAlert.this.playlist.size() - i) - 1));
                } else if (i > 0) {
                    audioPlayerCell.setMessageObject((MessageObject) AudioPlayerAlert.this.playlist.get(AudioPlayerAlert.this.playlist.size() - i));
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            switch (i) {
                case 0:
                    view = new View(this.context);
                    view.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(178.0f)));
                    break;
                default:
                    view = new AudioPlayerCell(this.context);
                    break;
            }
            return new Holder(view);
        }

        public void search(final String str) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (str == null) {
                this.searchResult.clear();
                notifyDataSetChanged();
                return;
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        ListAdapter.this.searchTimer.cancel();
                        ListAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    ListAdapter.this.processSearch(str);
                }
            }, 200, 300);
        }
    }

    public AudioPlayerAlert(Context context) {
        super(context, true);
        this.parentActivity = (LaunchActivity) context;
        this.noCoverDrawable = context.getResources().getDrawable(R.drawable.nocover).mutate();
        this.noCoverDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_placeholder), Mode.MULTIPLY));
        this.TAG = MediaController.getInstance().generateObserverTag();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.musicDidLoaded);
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_background), Mode.MULTIPLY));
        this.paint.setColor(Theme.getColor(Theme.key_player_placeholderBackground));
        this.containerView = new FrameLayout(context) {
            private boolean ignoreLayout = false;

            protected void onDraw(Canvas canvas) {
                AudioPlayerAlert.this.shadowDrawable.setBounds(0, Math.max(AudioPlayerAlert.this.actionBar.getMeasuredHeight(), AudioPlayerAlert.this.scrollOffsetY) - AudioPlayerAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                AudioPlayerAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || AudioPlayerAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) AudioPlayerAlert.this.scrollOffsetY) || AudioPlayerAlert.this.placeholderImageView.getTranslationX() != BitmapDescriptorFactory.HUE_RED) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                AudioPlayerAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                int measuredHeight = AudioPlayerAlert.this.actionBar.getMeasuredHeight();
                AudioPlayerAlert.this.shadow.layout(AudioPlayerAlert.this.shadow.getLeft(), measuredHeight, AudioPlayerAlert.this.shadow.getRight(), AudioPlayerAlert.this.shadow.getMeasuredHeight() + measuredHeight);
                AudioPlayerAlert.this.updateLayout();
                AudioPlayerAlert.this.setFullAnimationProgress(AudioPlayerAlert.this.fullAnimationProgress);
            }

            protected void onMeasure(int i, int i2) {
                int i3 = 0;
                int size = MeasureSpec.getSize(i2);
                int dp = (((AndroidUtilities.dp(178.0f) + (AudioPlayerAlert.this.playlist.size() * AndroidUtilities.dp(56.0f))) + AudioPlayerAlert.backgroundPaddingTop) + ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.statusBarHeight;
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(size, 1073741824);
                if (AudioPlayerAlert.this.searching) {
                    dp = (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + (ActionBar.getCurrentActionBarHeight() + AndroidUtilities.dp(178.0f));
                } else {
                    dp = dp < size ? size - dp : dp < size ? 0 : size - ((size / 5) * 3);
                    dp += (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight();
                }
                if (AudioPlayerAlert.this.listView.getPaddingTop() != dp) {
                    this.ignoreLayout = true;
                    AudioPlayerAlert.this.listView.setPadding(0, dp, 0, AndroidUtilities.dp(8.0f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(i, makeMeasureSpec);
                AudioPlayerAlert.this.inFullSize = getMeasuredHeight() >= size;
                dp = size - ActionBar.getCurrentActionBarHeight();
                if (VERSION.SDK_INT >= 21) {
                    i3 = AndroidUtilities.statusBarHeight;
                }
                dp = (dp - i3) - AndroidUtilities.dp(120.0f);
                i3 = Math.max(dp, getMeasuredWidth());
                AudioPlayerAlert.this.thumbMaxX = ((getMeasuredWidth() - i3) / 2) - AndroidUtilities.dp(17.0f);
                AudioPlayerAlert.this.thumbMaxY = AndroidUtilities.dp(19.0f);
                AudioPlayerAlert.this.panelEndTranslation = (float) (getMeasuredHeight() - AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
                AudioPlayerAlert.this.thumbMaxScale = (((float) i3) / ((float) AudioPlayerAlert.this.placeholderImageView.getMeasuredWidth())) - 1.0f;
                AudioPlayerAlert.this.endTranslation = (float) (ActionBar.getCurrentActionBarHeight() + AndroidUtilities.dp(5.0f));
                i3 = (int) Math.ceil((double) (((float) AudioPlayerAlert.this.placeholderImageView.getMeasuredHeight()) * (AudioPlayerAlert.this.thumbMaxScale + 1.0f)));
                if (i3 > dp) {
                    AudioPlayerAlert.this.endTranslation = AudioPlayerAlert.this.endTranslation - ((float) (i3 - dp));
                }
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !AudioPlayerAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.actionBar = new ActionBar(context);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_player_actionBar));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setItemsColor(Theme.getColor(Theme.key_player_actionBarItems), false);
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_player_actionBarSelector), false);
        this.actionBar.setTitleColor(Theme.getColor(Theme.key_player_actionBarTitle));
        this.actionBar.setSubtitleColor(Theme.getColor(Theme.key_player_actionBarSubtitle));
        this.actionBar.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.actionBar.setTitle("1");
        this.actionBar.setSubtitle("1");
        this.actionBar.getTitleTextView().setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.actionBar.getSubtitleTextView().setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.avatarContainer = new ChatAvatarContainer(context, null, false);
        this.avatarContainer.setEnabled(false);
        this.avatarContainer.setTitleColors(Theme.getColor(Theme.key_player_actionBarTitle), Theme.getColor(Theme.key_player_actionBarSubtitle));
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject != null) {
            long dialogId = playingMessageObject.getDialogId();
            int i = (int) dialogId;
            int i2 = (int) (dialogId >> 32);
            User user;
            if (i == 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2)).user_id));
                this.avatarContainer.setTitle(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarContainer.setUserAvatar(user);
            } else if (i > 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                this.avatarContainer.setTitle(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarContainer.setUserAvatar(user);
            } else {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                this.avatarContainer.setTitle(chat.title);
                this.avatarContainer.setChatAvatar(chat);
            }
        }
        this.avatarContainer.setSubtitle(LocaleController.getString("AudioTitle", R.string.AudioTitle));
        this.actionBar.addView(this.avatarContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, 56.0f, BitmapDescriptorFactory.HUE_RED, 40.0f, BitmapDescriptorFactory.HUE_RED));
        ActionBarMenu createMenu = this.actionBar.createMenu();
        this.menuItem = createMenu.addItem(0, (int) R.drawable.ic_ab_other);
        this.menuItem.addSubItem(1, LocaleController.getString("Forward", R.string.Forward));
        this.menuItem.addSubItem(2, LocaleController.getString("ShareFile", R.string.ShareFile));
        this.menuItem.addSubItem(4, LocaleController.getString("ShowInChat", R.string.ShowInChat));
        this.menuItem.setTranslationX((float) AndroidUtilities.dp(48.0f));
        this.menuItem.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.searchItem = createMenu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C43242());
        EditTextBoldCursor searchField = this.searchItem.getSearchField();
        searchField.setHint(LocaleController.getString("Search", R.string.Search));
        searchField.setTextColor(Theme.getColor(Theme.key_player_actionBarTitle));
        searchField.setHintTextColor(Theme.getColor(Theme.key_player_time));
        searchField.setCursorColor(Theme.getColor(Theme.key_player_actionBarTitle));
        if (!AndroidUtilities.isTablet()) {
            this.actionBar.showActionModeTop();
            this.actionBar.setActionModeTopColor(Theme.getColor(Theme.key_player_actionBarTop));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C43253());
        this.shadow = new View(context);
        this.shadow.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.shadow2 = new View(context);
        this.shadow2.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.shadow2.setBackgroundResource(R.drawable.header_shadow);
        this.playerLayout = new FrameLayout(context);
        this.playerLayout.setBackgroundColor(Theme.getColor(Theme.key_player_background));
        this.placeholderImageView = new BackupImageView(context) {
            private RectF rect = new RectF();

            protected void onDraw(Canvas canvas) {
                if (AudioPlayerAlert.this.hasNoCover) {
                    this.rect.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight());
                    canvas.drawRoundRect(this.rect, (float) getRoundRadius(), (float) getRoundRadius(), AudioPlayerAlert.this.paint);
                    float dp = (float) AndroidUtilities.dp(63.0f);
                    int max = (int) (Math.max(((AudioPlayerAlert.this.thumbMaxScale / getScaleX()) / 3.0f) / AudioPlayerAlert.this.thumbMaxScale, 1.0f / AudioPlayerAlert.this.thumbMaxScale) * dp);
                    int centerX = (int) (this.rect.centerX() - ((float) (max / 2)));
                    int centerY = (int) (this.rect.centerY() - ((float) (max / 2)));
                    AudioPlayerAlert.this.noCoverDrawable.setBounds(centerX, centerY, centerX + max, max + centerY);
                    AudioPlayerAlert.this.noCoverDrawable.draw(canvas);
                    return;
                }
                super.onDraw(canvas);
            }
        };
        this.placeholderImageView.setRoundRadius(AndroidUtilities.dp(20.0f));
        this.placeholderImageView.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.placeholderImageView.setPivotY(BitmapDescriptorFactory.HUE_RED);
        this.placeholderImageView.setOnClickListener(new C43285());
        this.titleTextView = new TextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_player_actionBarTitle));
        this.titleTextView.setTextSize(1, 15.0f);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setSingleLine(true);
        this.playerLayout.addView(this.titleTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 72.0f, 18.0f, 60.0f, BitmapDescriptorFactory.HUE_RED));
        this.authorTextView = new TextView(context);
        this.authorTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        this.authorTextView.setTextSize(1, 14.0f);
        this.authorTextView.setEllipsize(TruncateAt.END);
        this.authorTextView.setSingleLine(true);
        this.playerLayout.addView(this.authorTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 72.0f, 40.0f, 60.0f, BitmapDescriptorFactory.HUE_RED));
        this.optionsButton = new ActionBarMenuItem(context, null, 0, Theme.getColor(Theme.key_player_actionBarItems));
        this.optionsButton.setIcon((int) R.drawable.ic_ab_other);
        this.optionsButton.setAdditionalOffset(-AndroidUtilities.dp(120.0f));
        this.playerLayout.addView(this.optionsButton, LayoutHelper.createFrame(40, 40.0f, 53, BitmapDescriptorFactory.HUE_RED, 19.0f, 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.optionsButton.addSubItem(1, LocaleController.getString("Forward", R.string.Forward));
        this.optionsButton.addSubItem(2, LocaleController.getString("ShareFile", R.string.ShareFile));
        this.optionsButton.addSubItem(4, LocaleController.getString("ShowInChat", R.string.ShowInChat));
        this.optionsButton.setDelegate(new C43296());
        this.seekBarView = new SeekBarView(context);
        this.seekBarView.setDelegate(new C43307());
        this.playerLayout.addView(this.seekBarView, LayoutHelper.createFrame(-1, 30.0f, 51, 8.0f, 62.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.progressView = new LineProgressView(context);
        this.progressView.setVisibility(4);
        this.progressView.setBackgroundColor(Theme.getColor(Theme.key_player_progressBackground));
        this.progressView.setProgressColor(Theme.getColor(Theme.key_player_progress));
        this.playerLayout.addView(this.progressView, LayoutHelper.createFrame(-1, 2.0f, 51, 20.0f, 78.0f, 20.0f, BitmapDescriptorFactory.HUE_RED));
        this.timeTextView = new SimpleTextView(context);
        this.timeTextView.setTextSize(12);
        this.timeTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        this.playerLayout.addView(this.timeTextView, LayoutHelper.createFrame(100, -2.0f, 51, 20.0f, 92.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.durationTextView = new TextView(context);
        this.durationTextView.setTextSize(1, 12.0f);
        this.durationTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        this.durationTextView.setGravity(17);
        this.playerLayout.addView(this.durationTextView, LayoutHelper.createFrame(-2, -2.0f, 53, BitmapDescriptorFactory.HUE_RED, 90.0f, 20.0f, BitmapDescriptorFactory.HUE_RED));
        View c43318 = new FrameLayout(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int dp = ((i3 - i) - AndroidUtilities.dp(248.0f)) / 4;
                for (int i5 = 0; i5 < 5; i5++) {
                    int dp2 = AndroidUtilities.dp((float) ((i5 * 48) + 4)) + (dp * i5);
                    int dp3 = AndroidUtilities.dp(9.0f);
                    AudioPlayerAlert.this.buttons[i5].layout(dp2, dp3, AudioPlayerAlert.this.buttons[i5].getMeasuredWidth() + dp2, AudioPlayerAlert.this.buttons[i5].getMeasuredHeight() + dp3);
                }
            }
        };
        this.playerLayout.addView(c43318, LayoutHelper.createFrame(-1, 66.0f, 51, BitmapDescriptorFactory.HUE_RED, 106.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View[] viewArr = this.buttons;
        ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(context, null, 0, 0);
        this.shuffleButton = actionBarMenuItem;
        viewArr[0] = actionBarMenuItem;
        this.shuffleButton.setAdditionalOffset(-AndroidUtilities.dp(10.0f));
        c43318.addView(this.shuffleButton, LayoutHelper.createFrame(48, 48, 51));
        TextView addSubItem = this.shuffleButton.addSubItem(1, LocaleController.getString("ReverseOrder", R.string.ReverseOrder));
        addSubItem.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(16.0f), 0);
        this.playOrderButtons[0] = context.getResources().getDrawable(R.drawable.music_reverse).mutate();
        addSubItem.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        addSubItem.setCompoundDrawablesWithIntrinsicBounds(this.playOrderButtons[0], null, null, null);
        addSubItem = this.shuffleButton.addSubItem(2, LocaleController.getString("Shuffle", R.string.Shuffle));
        addSubItem.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(16.0f), 0);
        this.playOrderButtons[1] = context.getResources().getDrawable(R.drawable.pl_shuffle).mutate();
        addSubItem.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        addSubItem.setCompoundDrawablesWithIntrinsicBounds(this.playOrderButtons[1], null, null, null);
        this.shuffleButton.setDelegate(new C43329());
        viewArr = this.buttons;
        View imageView = new ImageView(context);
        viewArr[1] = imageView;
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageDrawable(Theme.createSimpleSelectorDrawable(context, R.drawable.pl_previous, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
        c43318.addView(imageView, LayoutHelper.createFrame(48, 48, 51));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MediaController.getInstance().playPreviousMessage();
            }
        });
        viewArr = this.buttons;
        ImageView imageView2 = new ImageView(context);
        this.playButton = imageView2;
        viewArr[2] = imageView2;
        this.playButton.setScaleType(ScaleType.CENTER);
        this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(context, R.drawable.pl_play, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
        c43318.addView(this.playButton, LayoutHelper.createFrame(48, 48, 51));
        this.playButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!MediaController.getInstance().isDownloadingCurrentMessage()) {
                    if (MediaController.getInstance().isMessagePaused()) {
                        MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
                    } else {
                        MediaController.getInstance().pauseMessage(MediaController.getInstance().getPlayingMessageObject());
                    }
                }
            }
        });
        viewArr = this.buttons;
        imageView = new ImageView(context);
        viewArr[3] = imageView;
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageDrawable(Theme.createSimpleSelectorDrawable(context, R.drawable.pl_next, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
        c43318.addView(imageView, LayoutHelper.createFrame(48, 48, 51));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MediaController.getInstance().playNextMessage();
            }
        });
        viewArr = this.buttons;
        imageView2 = new ImageView(context);
        this.repeatButton = imageView2;
        viewArr[4] = imageView2;
        this.repeatButton.setScaleType(ScaleType.CENTER);
        this.repeatButton.setPadding(0, 0, AndroidUtilities.dp(8.0f), 0);
        c43318.addView(this.repeatButton, LayoutHelper.createFrame(50, 48, 51));
        this.repeatButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MediaController.getInstance().toggleRepeatMode();
                AudioPlayerAlert.this.updateRepeatButton();
            }
        });
        this.listView = new RecyclerListView(context) {
            boolean ignoreLayout;

            protected boolean allowSelectChildAtPosition(float f, float f2) {
                float y = AudioPlayerAlert.this.playerLayout.getY() + ((float) AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
                return AudioPlayerAlert.this.playerLayout == null || f2 > AudioPlayerAlert.this.playerLayout.getY() + ((float) AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
            }

            public boolean drawChild(Canvas canvas, View view, long j) {
                canvas.save();
                canvas.clipRect(0, (AudioPlayerAlert.this.actionBar != null ? AudioPlayerAlert.this.actionBar.getMeasuredHeight() : 0) + AndroidUtilities.dp(50.0f), getMeasuredWidth(), getMeasuredHeight());
                boolean drawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                return drawChild;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                if (AudioPlayerAlert.this.searchOpenPosition != -1 && !AudioPlayerAlert.this.actionBar.isSearchFieldVisible()) {
                    this.ignoreLayout = true;
                    AudioPlayerAlert.this.layoutManager.scrollToPositionWithOffset(AudioPlayerAlert.this.searchOpenPosition, AudioPlayerAlert.this.searchOpenOffset);
                    super.onLayout(false, i, i2, i3, i4);
                    this.ignoreLayout = false;
                    AudioPlayerAlert.this.searchOpenPosition = -1;
                } else if (AudioPlayerAlert.this.scrollToSong) {
                    AudioPlayerAlert.this.scrollToSong = false;
                    MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                    if (playingMessageObject != null) {
                        boolean z2;
                        int indexOf;
                        int childCount = AudioPlayerAlert.this.listView.getChildCount();
                        for (int i5 = 0; i5 < childCount; i5++) {
                            View childAt = AudioPlayerAlert.this.listView.getChildAt(i5);
                            if ((childAt instanceof AudioPlayerCell) && ((AudioPlayerCell) childAt).getMessageObject() == playingMessageObject) {
                                if (childAt.getBottom() <= getMeasuredHeight()) {
                                    z2 = true;
                                    if (z2) {
                                        indexOf = AudioPlayerAlert.this.playlist.indexOf(playingMessageObject);
                                        if (indexOf < 0) {
                                            this.ignoreLayout = true;
                                            AudioPlayerAlert.this.layoutManager.scrollToPosition(AudioPlayerAlert.this.playlist.size() - indexOf);
                                            super.onLayout(false, i, i2, i3, i4);
                                            this.ignoreLayout = false;
                                        }
                                    }
                                }
                                z2 = false;
                                if (z2) {
                                    indexOf = AudioPlayerAlert.this.playlist.indexOf(playingMessageObject);
                                    if (indexOf < 0) {
                                        this.ignoreLayout = true;
                                        AudioPlayerAlert.this.layoutManager.scrollToPosition(AudioPlayerAlert.this.playlist.size() - indexOf);
                                        super.onLayout(false, i, i2, i3, i4);
                                        this.ignoreLayout = false;
                                    }
                                }
                            }
                        }
                        z2 = false;
                        if (z2) {
                            indexOf = AudioPlayerAlert.this.playlist.indexOf(playingMessageObject);
                            if (indexOf < 0) {
                                this.ignoreLayout = true;
                                AudioPlayerAlert.this.layoutManager.scrollToPosition(AudioPlayerAlert.this.playlist.size() - indexOf);
                                super.onLayout(false, i, i2, i3, i4);
                                this.ignoreLayout = false;
                            }
                        }
                    }
                }
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.listView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.listView.setClipToPadding(false);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setVerticalScrollBarEnabled(false);
        this.containerView.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                if (view instanceof AudioPlayerCell) {
                    ((AudioPlayerCell) view).didPressedButton();
                }
            }
        });
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1 && AudioPlayerAlert.this.searching && AudioPlayerAlert.this.searchWas) {
                    AndroidUtilities.hideKeyboard(AudioPlayerAlert.this.getCurrentFocus());
                }
            }

            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                AudioPlayerAlert.this.updateLayout();
            }
        });
        this.playlist = MediaController.getInstance().getPlaylist();
        this.listAdapter.notifyDataSetChanged();
        this.containerView.addView(this.playerLayout, LayoutHelper.createFrame(-1, 178.0f));
        this.containerView.addView(this.shadow2, LayoutHelper.createFrame(-1, 3.0f));
        this.containerView.addView(this.placeholderImageView, LayoutHelper.createFrame(40, 40.0f, 51, 17.0f, 19.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f));
        this.containerView.addView(this.actionBar);
        updateTitle(false);
        updateRepeatButton();
        updateShuffleButton();
    }

    private void checkIfMusicDownloaded(MessageObject messageObject) {
        File file = null;
        if (messageObject.messageOwner.attachPath != null && messageObject.messageOwner.attachPath.length() > 0) {
            File file2 = new File(messageObject.messageOwner.attachPath);
            if (file2.exists()) {
                file = file2;
            }
        }
        if (file == null) {
            file = FileLoader.getPathToMessage(messageObject.messageOwner);
        }
        if (file.exists()) {
            MediaController.getInstance().removeLoadingFileObserver(this);
            this.progressView.setVisibility(4);
            this.seekBarView.setVisibility(0);
            this.playButton.setEnabled(true);
            return;
        }
        String fileName = messageObject.getFileName();
        MediaController.getInstance().addLoadingFileObserver(fileName, this);
        Float fileProgress = ImageLoader.getInstance().getFileProgress(fileName);
        this.progressView.setProgress(fileProgress != null ? fileProgress.floatValue() : BitmapDescriptorFactory.HUE_RED, false);
        this.progressView.setVisibility(0);
        this.seekBarView.setVisibility(4);
        this.playButton.setEnabled(false);
    }

    private int getCurrentTop() {
        if (this.listView.getChildCount() != 0) {
            View childAt = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
            if (holder != null) {
                int paddingTop = this.listView.getPaddingTop();
                int top = (holder.getAdapterPosition() != 0 || childAt.getTop() < 0) ? 0 : childAt.getTop();
                return paddingTop - top;
            }
        }
        return C3446C.PRIORITY_DOWNLOAD;
    }

    private void onSubItemClick(int i) {
        final MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject != null) {
            if (i == 1) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("onlySelect", true);
                bundle.putInt("dialogsType", 3);
                BaseFragment dialogsActivity = new DialogsActivity(bundle);
                final ArrayList arrayList = new ArrayList();
                arrayList.add(playingMessageObject);
                dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                    public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                        int i = 0;
                        if (arrayList.size() > 1 || ((Long) arrayList.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || charSequence != null) {
                            while (i < arrayList.size()) {
                                long longValue = ((Long) arrayList.get(i)).longValue();
                                if (charSequence != null) {
                                    SendMessagesHelper.getInstance().sendMessage(charSequence.toString(), longValue, null, null, true, null, null, null);
                                }
                                SendMessagesHelper.getInstance().sendMessage(arrayList, longValue);
                                i++;
                            }
                            dialogsActivity.finishFragment();
                            return;
                        }
                        long longValue2 = ((Long) arrayList.get(0)).longValue();
                        int i2 = (int) longValue2;
                        int i3 = (int) (longValue2 >> 32);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("scrollToTopOnResume", true);
                        if (i2 == 0) {
                            bundle.putInt("enc_id", i3);
                        } else if (i2 > 0) {
                            bundle.putInt("user_id", i2);
                        } else if (i2 < 0) {
                            bundle.putInt("chat_id", -i2);
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        BaseFragment chatActivity = new ChatActivity(bundle);
                        if (AudioPlayerAlert.this.parentActivity.presentFragment(chatActivity, true, false)) {
                            chatActivity.showReplyPanel(true, null, arrayList, null, false);
                            return;
                        }
                        dialogsActivity.finishFragment();
                    }
                });
                this.parentActivity.presentFragment(dialogsActivity);
                dismiss();
            } else if (i == 2) {
                File file = null;
                try {
                    if (!TextUtils.isEmpty(playingMessageObject.messageOwner.attachPath)) {
                        file = new File(playingMessageObject.messageOwner.attachPath);
                        if (!file.exists()) {
                            file = null;
                        }
                    }
                    if (file == null) {
                        file = FileLoader.getPathToMessage(playingMessageObject.messageOwner);
                    }
                    if (file.exists()) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        if (playingMessageObject != null) {
                            intent.setType(playingMessageObject.getMimeType());
                        } else {
                            intent.setType("audio/mp3");
                        }
                        if (VERSION.SDK_INT >= 24) {
                            try {
                                intent.putExtra("android.intent.extra.STREAM", FileProvider.a(ApplicationLoader.applicationContext, "org.ir.talaeii.provider", file));
                                intent.setFlags(1);
                            } catch (Exception e) {
                                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                            }
                        } else {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                        }
                        this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
                        return;
                    }
                    Builder builder = new Builder(this.parentActivity);
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                    builder.show();
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            } else if (i == 3) {
                Builder builder2 = new Builder(this.parentActivity);
                builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                final boolean[] zArr = new boolean[1];
                int dialogId = (int) playingMessageObject.getDialogId();
                if (dialogId != 0) {
                    User user;
                    Chat chat;
                    if (dialogId > 0) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(dialogId));
                        chat = null;
                    } else {
                        user = null;
                        chat = MessagesController.getInstance().getChat(Integer.valueOf(-dialogId));
                    }
                    if (!(user == null && ChatObject.isChannel(chat))) {
                        r2 = ConnectionsManager.getInstance().getCurrentTime();
                        if (!((user == null || user.id == UserConfig.getClientUserId()) && chat == null) && ((playingMessageObject.messageOwner.action == null || (playingMessageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) && playingMessageObject.isOut() && r2 - playingMessageObject.messageOwner.date <= 172800)) {
                            View frameLayout = new FrameLayout(this.parentActivity);
                            View checkBoxCell = new CheckBoxCell(this.parentActivity, true);
                            checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            if (chat != null) {
                                checkBoxCell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                            } else {
                                checkBoxCell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(user)}), TtmlNode.ANONYMOUS_REGION_ID, false, false);
                            }
                            checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                            frameLayout.addView(checkBoxCell, LayoutHelper.createFrame(-1, 48.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                            checkBoxCell.setOnClickListener(new OnClickListener() {
                                public void onClick(View view) {
                                    CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                                    zArr[0] = !zArr[0];
                                    checkBoxCell.setChecked(zArr[0], true);
                                }
                            });
                            builder2.setView(frameLayout);
                        }
                    }
                }
                builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList arrayList;
                        EncryptedChat encryptedChat = null;
                        AudioPlayerAlert.this.dismiss();
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(Integer.valueOf(playingMessageObject.getId()));
                        if (((int) playingMessageObject.getDialogId()) != 0 || playingMessageObject.messageOwner.random_id == 0) {
                            arrayList = null;
                        } else {
                            arrayList = new ArrayList();
                            arrayList.add(Long.valueOf(playingMessageObject.messageOwner.random_id));
                            encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (playingMessageObject.getDialogId() >> 32)));
                        }
                        MessagesController.getInstance().deleteMessages(arrayList2, arrayList, encryptedChat, playingMessageObject.messageOwner.to_id.channel_id, zArr[0]);
                    }
                });
                builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder2.show();
            } else if (i == 4) {
                Bundle bundle2 = new Bundle();
                long dialogId2 = playingMessageObject.getDialogId();
                int i2 = (int) dialogId2;
                r2 = (int) (dialogId2 >> 32);
                if (i2 == 0) {
                    bundle2.putInt("enc_id", r2);
                } else if (r2 == 1) {
                    bundle2.putInt("chat_id", i2);
                } else if (i2 > 0) {
                    bundle2.putInt("user_id", i2);
                } else if (i2 < 0) {
                    Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-i2));
                    if (!(chat2 == null || chat2.migrated_to == null)) {
                        bundle2.putInt("migrated_to", i2);
                        i2 = -chat2.migrated_to.channel_id;
                    }
                    bundle2.putInt("chat_id", -i2);
                }
                bundle2.putInt("message_id", playingMessageObject.getId());
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                this.parentActivity.presentFragment(new ChatActivity(bundle2), false, false);
                dismiss();
            }
        }
    }

    private void updateLayout() {
        if (this.listView.getChildCount() > 0) {
            View childAt = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
            int top = childAt.getTop();
            int i = (top <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : top;
            if (this.searchWas || this.searching) {
                i = 0;
            }
            if (this.scrollOffsetY != i) {
                RecyclerListView recyclerListView = this.listView;
                this.scrollOffsetY = i;
                recyclerListView.setTopGlowOffset(i);
                this.playerLayout.setTranslationY((float) Math.max(this.actionBar.getMeasuredHeight(), this.scrollOffsetY));
                this.placeholderImageView.setTranslationY((float) Math.max(this.actionBar.getMeasuredHeight(), this.scrollOffsetY));
                this.shadow2.setTranslationY((float) (Math.max(this.actionBar.getMeasuredHeight(), this.scrollOffsetY) + this.playerLayout.getMeasuredHeight()));
                this.containerView.invalidate();
                AnimatorSet animatorSet;
                Animator[] animatorArr;
                if ((!this.inFullSize || this.scrollOffsetY > this.actionBar.getMeasuredHeight()) && !this.searchWas) {
                    if (this.actionBar.getTag() != null) {
                        if (this.actionBarAnimation != null) {
                            this.actionBarAnimation.cancel();
                        }
                        this.actionBar.setTag(null);
                        this.actionBarAnimation = new AnimatorSet();
                        animatorSet = this.actionBarAnimation;
                        animatorArr = new Animator[3];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[1] = ObjectAnimator.ofFloat(this.shadow, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorArr[2] = ObjectAnimator.ofFloat(this.shadow2, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        animatorSet.playTogether(animatorArr);
                        this.actionBarAnimation.setDuration(180);
                        this.actionBarAnimation.start();
                    }
                } else if (this.actionBar.getTag() == null) {
                    if (this.actionBarAnimation != null) {
                        this.actionBarAnimation.cancel();
                    }
                    this.actionBar.setTag(Integer.valueOf(1));
                    this.actionBarAnimation = new AnimatorSet();
                    animatorSet = this.actionBarAnimation;
                    animatorArr = new Animator[3];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{1.0f});
                    animatorArr[1] = ObjectAnimator.ofFloat(this.shadow, "alpha", new float[]{1.0f});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.shadow2, "alpha", new float[]{1.0f});
                    animatorSet.playTogether(animatorArr);
                    this.actionBarAnimation.setDuration(180);
                    this.actionBarAnimation.start();
                }
            }
            this.startTranslation = (float) Math.max(this.actionBar.getMeasuredHeight(), this.scrollOffsetY);
            this.panelStartTranslation = (float) Math.max(this.actionBar.getMeasuredHeight(), this.scrollOffsetY);
        }
    }

    private void updateProgress(MessageObject messageObject) {
        if (this.seekBarView != null) {
            if (!this.seekBarView.isDragging()) {
                this.seekBarView.setProgress(messageObject.audioProgress);
            }
            if (this.lastTime != messageObject.audioProgressSec) {
                this.lastTime = messageObject.audioProgressSec;
                this.timeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(messageObject.audioProgressSec / 60), Integer.valueOf(messageObject.audioProgressSec % 60)}));
            }
        }
    }

    private void updateRepeatButton() {
        int repeatMode = MediaController.getInstance().getRepeatMode();
        if (repeatMode == 0) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat);
            this.repeatButton.setTag(Theme.key_player_button);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_button), Mode.MULTIPLY));
        } else if (repeatMode == 1) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat);
            this.repeatButton.setTag(Theme.key_player_buttonActive);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
        } else if (repeatMode == 2) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat1);
            this.repeatButton.setTag(Theme.key_player_buttonActive);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
        }
    }

    private void updateShuffleButton() {
        Drawable mutate;
        if (MediaController.getInstance().isShuffleMusic()) {
            mutate = getContext().getResources().getDrawable(R.drawable.pl_shuffle).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
            this.shuffleButton.setIcon(mutate);
        } else {
            mutate = getContext().getResources().getDrawable(R.drawable.music_reverse).mutate();
            if (MediaController.getInstance().isPlayOrderReversed()) {
                mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
            } else {
                mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_button), Mode.MULTIPLY));
            }
            this.shuffleButton.setIcon(mutate);
        }
        this.playOrderButtons[0].setColorFilter(new PorterDuffColorFilter(Theme.getColor(MediaController.getInstance().isPlayOrderReversed() ? Theme.key_player_buttonActive : Theme.key_player_button), Mode.MULTIPLY));
        this.playOrderButtons[1].setColorFilter(new PorterDuffColorFilter(Theme.getColor(MediaController.getInstance().isShuffleMusic() ? Theme.key_player_buttonActive : Theme.key_player_button), Mode.MULTIPLY));
    }

    private void updateTitle(boolean z) {
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if ((playingMessageObject == null && z) || (playingMessageObject != null && !playingMessageObject.isMusic())) {
            dismiss();
        } else if (playingMessageObject != null) {
            if (playingMessageObject.eventId != 0) {
                this.hasOptions = false;
                this.menuItem.setVisibility(4);
                this.optionsButton.setVisibility(4);
            } else {
                this.hasOptions = true;
                if (!this.actionBar.isSearchFieldVisible()) {
                    this.menuItem.setVisibility(0);
                }
                this.optionsButton.setVisibility(0);
            }
            checkIfMusicDownloaded(playingMessageObject);
            updateProgress(playingMessageObject);
            if (MediaController.getInstance().isMessagePaused()) {
                this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(this.playButton.getContext(), R.drawable.pl_play, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
            } else {
                this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(this.playButton.getContext(), R.drawable.pl_pause, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
            }
            CharSequence musicTitle = playingMessageObject.getMusicTitle();
            CharSequence musicAuthor = playingMessageObject.getMusicAuthor();
            this.titleTextView.setText(musicTitle);
            this.authorTextView.setText(musicAuthor);
            this.actionBar.setTitle(musicTitle);
            this.actionBar.setSubtitle(musicAuthor);
            AudioInfo audioInfo = MediaController.getInstance().getAudioInfo();
            if (audioInfo == null || audioInfo.getCover() == null) {
                this.hasNoCover = true;
                this.placeholderImageView.invalidate();
                this.placeholderImageView.setImageDrawable(null);
            } else {
                this.hasNoCover = false;
                this.placeholderImageView.setImageBitmap(audioInfo.getCover());
            }
            if (this.durationTextView != null) {
                int i;
                CharSequence format;
                Document document = playingMessageObject.getDocument();
                if (document != null) {
                    for (int i2 = 0; i2 < document.attributes.size(); i2++) {
                        DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i2);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            i = documentAttribute.duration;
                            break;
                        }
                    }
                }
                i = 0;
                TextView textView = this.durationTextView;
                if (i != 0) {
                    format = String.format("%d:%02d", new Object[]{Integer.valueOf(i / 60), Integer.valueOf(i % 60)});
                } else {
                    format = "-:--";
                }
                textView.setText(format);
            }
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.messagePlayingDidStarted || i == NotificationCenter.messagePlayingPlayStateChanged || i == NotificationCenter.messagePlayingDidReset) {
            boolean z = i == NotificationCenter.messagePlayingDidReset && ((Boolean) objArr[1]).booleanValue();
            updateTitle(z);
            int childCount;
            int i2;
            View childAt;
            AudioPlayerCell audioPlayerCell;
            MessageObject messageObject;
            if (i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.messagePlayingPlayStateChanged) {
                childCount = this.listView.getChildCount();
                for (i2 = 0; i2 < childCount; i2++) {
                    childAt = this.listView.getChildAt(i2);
                    if (childAt instanceof AudioPlayerCell) {
                        audioPlayerCell = (AudioPlayerCell) childAt;
                        messageObject = audioPlayerCell.getMessageObject();
                        if (messageObject != null && (messageObject.isVoice() || messageObject.isMusic())) {
                            audioPlayerCell.updateButtonState(false);
                        }
                    }
                }
            } else if (i == NotificationCenter.messagePlayingDidStarted && ((MessageObject) objArr[0]).eventId == 0) {
                childCount = this.listView.getChildCount();
                for (i2 = 0; i2 < childCount; i2++) {
                    childAt = this.listView.getChildAt(i2);
                    if (childAt instanceof AudioPlayerCell) {
                        audioPlayerCell = (AudioPlayerCell) childAt;
                        messageObject = audioPlayerCell.getMessageObject();
                        if (messageObject != null && (messageObject.isVoice() || messageObject.isMusic())) {
                            audioPlayerCell.updateButtonState(false);
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject != null && playingMessageObject.isMusic()) {
                updateProgress(playingMessageObject);
            }
        } else if (i == NotificationCenter.musicDidLoaded) {
            this.playlist = MediaController.getInstance().getPlaylist();
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void dismiss() {
        super.dismiss();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.musicDidLoaded);
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    public float getFullAnimationProgress() {
        return this.fullAnimationProgress;
    }

    public int getObserverTag() {
        return this.TAG;
    }

    public void onBackPressed() {
        if (this.actionBar == null || !this.actionBar.isSearchFieldVisible()) {
            super.onBackPressed();
        } else {
            this.actionBar.closeSearchField();
        }
    }

    public void onFailedDownload(String str) {
    }

    public void onProgressDownload(String str, float f) {
        this.progressView.setProgress(f, true);
    }

    public void onProgressUpload(String str, float f, boolean z) {
    }

    public void onSuccessDownload(String str) {
    }

    public void setFullAnimationProgress(float f) {
        this.fullAnimationProgress = f;
        this.placeholderImageView.setRoundRadius(AndroidUtilities.dp(20.0f * (1.0f - this.fullAnimationProgress)));
        float f2 = (this.thumbMaxScale * this.fullAnimationProgress) + 1.0f;
        this.placeholderImageView.setScaleX(f2);
        this.placeholderImageView.setScaleY(f2);
        this.placeholderImageView.getTranslationY();
        this.placeholderImageView.setTranslationX(((float) this.thumbMaxX) * this.fullAnimationProgress);
        this.placeholderImageView.setTranslationY(this.startTranslation + ((this.endTranslation - this.startTranslation) * this.fullAnimationProgress));
        this.playerLayout.setTranslationY(this.panelStartTranslation + ((this.panelEndTranslation - this.panelStartTranslation) * this.fullAnimationProgress));
        this.shadow2.setTranslationY((this.panelStartTranslation + ((this.panelEndTranslation - this.panelStartTranslation) * this.fullAnimationProgress)) + ((float) this.playerLayout.getMeasuredHeight()));
        this.menuItem.setAlpha(this.fullAnimationProgress);
        this.searchItem.setAlpha(1.0f - this.fullAnimationProgress);
        this.avatarContainer.setAlpha(1.0f - this.fullAnimationProgress);
        this.actionBar.getTitleTextView().setAlpha(this.fullAnimationProgress);
        this.actionBar.getSubtitleTextView().setAlpha(this.fullAnimationProgress);
    }
}
