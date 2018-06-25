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
import com.thin.downloadmanager.BuildConfig;
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
import org.telegram.messenger.MediaController$FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
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

public class AudioPlayerAlert extends BottomSheet implements NotificationCenterDelegate, MediaController$FileDownloadProgressListener {
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
    class C24862 extends ActionBarMenuItemSearchListener {
        C24862() {
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
            View firstVisView = AudioPlayerAlert.this.layoutManager.findViewByPosition(AudioPlayerAlert.this.searchOpenPosition);
            AudioPlayerAlert.this.searchOpenOffset = (firstVisView == null ? 0 : firstVisView.getTop()) - AudioPlayerAlert.this.listView.getPaddingTop();
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
    class C24873 extends ActionBarMenuOnItemClick {
        C24873() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                AudioPlayerAlert.this.dismiss();
            } else {
                AudioPlayerAlert.this.onSubItemClick(id);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$5 */
    class C24905 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$5$1 */
        class C24891 extends AnimatorListenerAdapter {
            C24891() {
            }

            public void onAnimationEnd(Animator animation) {
                if (animation.equals(AudioPlayerAlert.this.animatorSet)) {
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

        C24905() {
        }

        public void onClick(View view) {
            float f = 0.0f;
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
                float f2;
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
                if (AudioPlayerAlert.this.isInFullMode) {
                    f2 = 0.0f;
                } else {
                    f2 = 1.0f;
                }
                fArr2[0] = f2;
                animatorArr2[1] = ObjectAnimator.ofFloat(access$1300, str2, fArr2);
                View access$1400 = AudioPlayerAlert.this.shadow;
                String str3 = "alpha";
                float[] fArr3 = new float[1];
                if (AudioPlayerAlert.this.isInFullMode) {
                    f2 = 0.0f;
                } else {
                    f2 = 1.0f;
                }
                fArr3[0] = f2;
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
            AudioPlayerAlert.this.animatorSet.addListener(new C24891());
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
    class C24916 implements ActionBarMenuItemDelegate {
        C24916() {
        }

        public void onItemClick(int id) {
            AudioPlayerAlert.this.onSubItemClick(id);
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$7 */
    class C24927 implements SeekBarViewDelegate {
        C24927() {
        }

        public void onSeekBarDrag(float progress) {
            MediaController.getInstance().seekToProgress(MediaController.getInstance().getPlayingMessageObject(), progress);
        }
    }

    /* renamed from: org.telegram.ui.Components.AudioPlayerAlert$9 */
    class C24949 implements ActionBarMenuItemDelegate {
        C24949() {
        }

        public void onItemClick(int id) {
            MediaController.getInstance().toggleShuffleMusic(id);
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

        public int getItemCount() {
            if (AudioPlayerAlert.this.searchWas) {
                return this.searchResult.size();
            }
            if (AudioPlayerAlert.this.searching) {
                return AudioPlayerAlert.this.playlist.size();
            }
            return AudioPlayerAlert.this.playlist.size() + 1;
        }

        public boolean isEnabled(ViewHolder holder) {
            return AudioPlayerAlert.this.searchWas || holder.getAdapterPosition() > 0;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
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

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.getItemViewType() == 1) {
                AudioPlayerCell cell = holder.itemView;
                if (AudioPlayerAlert.this.searchWas) {
                    cell.setMessageObject((MessageObject) this.searchResult.get(position));
                } else if (AudioPlayerAlert.this.searching) {
                    cell.setMessageObject((MessageObject) AudioPlayerAlert.this.playlist.get((AudioPlayerAlert.this.playlist.size() - position) - 1));
                } else if (position > 0) {
                    cell.setMessageObject((MessageObject) AudioPlayerAlert.this.playlist.get(AudioPlayerAlert.this.playlist.size() - position));
                }
            }
        }

        public int getItemViewType(int i) {
            if (AudioPlayerAlert.this.searchWas || AudioPlayerAlert.this.searching || i != 0) {
                return 1;
            }
            return 0;
        }

        public void search(final String query) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (query == null) {
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
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    ListAdapter.this.processSearch(query);
                }
            }, 200, 300);
        }

        private void processSearch(final String query) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    final ArrayList<MessageObject> copy = new ArrayList();
                    copy.addAll(AudioPlayerAlert.this.playlist);
                    Utilities.searchQueue.postRunnable(new Runnable() {
                        public void run() {
                            String search1 = query.trim().toLowerCase();
                            if (search1.length() == 0) {
                                ListAdapter.this.updateSearchResults(new ArrayList());
                                return;
                            }
                            String search2 = LocaleController.getInstance().getTranslitString(search1);
                            if (search1.equals(search2) || search2.length() == 0) {
                                search2 = null;
                            }
                            String[] search = new String[((search2 != null ? 1 : 0) + 1)];
                            search[0] = search1;
                            if (search2 != null) {
                                search[1] = search2;
                            }
                            ArrayList<MessageObject> resultArray = new ArrayList();
                            for (int a = 0; a < copy.size(); a++) {
                                MessageObject messageObject = (MessageObject) copy.get(a);
                                for (String q : search) {
                                    String name = messageObject.getDocumentName();
                                    if (!(name == null || name.length() == 0)) {
                                        if (!name.toLowerCase().contains(q)) {
                                            TLRPC$Document document;
                                            if (messageObject.type == 0) {
                                                document = messageObject.messageOwner.media.webpage.document;
                                            } else {
                                                document = messageObject.messageOwner.media.document;
                                            }
                                            boolean ok = false;
                                            int c = 0;
                                            while (c < document.attributes.size()) {
                                                DocumentAttribute attribute = (DocumentAttribute) document.attributes.get(c);
                                                if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                                                    if (attribute.performer != null) {
                                                        ok = attribute.performer.toLowerCase().contains(q);
                                                    }
                                                    if (!(ok || attribute.title == null)) {
                                                        ok = attribute.title.toLowerCase().contains(q);
                                                    }
                                                    if (ok) {
                                                        resultArray.add(messageObject);
                                                        break;
                                                    }
                                                } else {
                                                    c++;
                                                }
                                            }
                                            if (ok) {
                                                resultArray.add(messageObject);
                                                break;
                                            }
                                        } else {
                                            resultArray.add(messageObject);
                                            break;
                                        }
                                    }
                                }
                            }
                            ListAdapter.this.updateSearchResults(resultArray);
                        }
                    });
                }
            });
        }

        private void updateSearchResults(final ArrayList<MessageObject> documents) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ListAdapter.this.searchResult = documents;
                    ListAdapter.this.notifyDataSetChanged();
                    AudioPlayerAlert.this.layoutManager.scrollToPosition(0);
                }
            });
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

            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ev.getAction() != 0 || AudioPlayerAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) AudioPlayerAlert.this.scrollOffsetY) || AudioPlayerAlert.this.placeholderImageView.getTranslationX() != 0.0f) {
                    return super.onInterceptTouchEvent(ev);
                }
                AudioPlayerAlert.this.dismiss();
                return true;
            }

            public boolean onTouchEvent(MotionEvent e) {
                return !AudioPlayerAlert.this.isDismissed() && super.onTouchEvent(e);
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int padding;
                int height = MeasureSpec.getSize(heightMeasureSpec);
                int contentSize = (((AndroidUtilities.dp(178.0f) + (AudioPlayerAlert.this.playlist.size() * AndroidUtilities.dp(56.0f))) + AudioPlayerAlert.backgroundPaddingTop) + ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.statusBarHeight;
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
                if (AudioPlayerAlert.this.searching) {
                    padding = (ActionBar.getCurrentActionBarHeight() + AndroidUtilities.dp(178.0f)) + (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0);
                } else {
                    if (contentSize < height) {
                        padding = height - contentSize;
                    } else {
                        padding = contentSize < height ? 0 : height - ((height / 5) * 3);
                    }
                    padding += (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight();
                }
                if (AudioPlayerAlert.this.listView.getPaddingTop() != padding) {
                    this.ignoreLayout = true;
                    AudioPlayerAlert.this.listView.setPadding(0, padding, 0, AndroidUtilities.dp(8.0f));
                    this.ignoreLayout = false;
                }
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                AudioPlayerAlert.this.inFullSize = getMeasuredHeight() >= height;
                int availableHeight = ((height - ActionBar.getCurrentActionBarHeight()) - (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) - AndroidUtilities.dp(120.0f);
                int maxSize = Math.max(availableHeight, getMeasuredWidth());
                AudioPlayerAlert.this.thumbMaxX = ((getMeasuredWidth() - maxSize) / 2) - AndroidUtilities.dp(17.0f);
                AudioPlayerAlert.this.thumbMaxY = AndroidUtilities.dp(19.0f);
                AudioPlayerAlert.this.panelEndTranslation = (float) (getMeasuredHeight() - AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
                AudioPlayerAlert.this.thumbMaxScale = (((float) maxSize) / ((float) AudioPlayerAlert.this.placeholderImageView.getMeasuredWidth())) - 1.0f;
                AudioPlayerAlert.this.endTranslation = (float) (ActionBar.getCurrentActionBarHeight() + AndroidUtilities.dp(5.0f));
                int scaledHeight = (int) Math.ceil((double) (((float) AudioPlayerAlert.this.placeholderImageView.getMeasuredHeight()) * (1.0f + AudioPlayerAlert.this.thumbMaxScale)));
                if (scaledHeight > availableHeight) {
                    AudioPlayerAlert.this.endTranslation = AudioPlayerAlert.this.endTranslation - ((float) (scaledHeight - availableHeight));
                }
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                int y = AudioPlayerAlert.this.actionBar.getMeasuredHeight();
                AudioPlayerAlert.this.shadow.layout(AudioPlayerAlert.this.shadow.getLeft(), y, AudioPlayerAlert.this.shadow.getRight(), AudioPlayerAlert.this.shadow.getMeasuredHeight() + y);
                AudioPlayerAlert.this.updateLayout();
                AudioPlayerAlert.this.setFullAnimationProgress(AudioPlayerAlert.this.fullAnimationProgress);
            }

            public void requestLayout() {
                if (!this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            protected void onDraw(Canvas canvas) {
                AudioPlayerAlert.this.shadowDrawable.setBounds(0, Math.max(AudioPlayerAlert.this.actionBar.getMeasuredHeight(), AudioPlayerAlert.this.scrollOffsetY) - AudioPlayerAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                AudioPlayerAlert.this.shadowDrawable.draw(canvas);
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
        this.actionBar.setAlpha(0.0f);
        this.actionBar.setTitle(BuildConfig.VERSION_NAME);
        this.actionBar.setSubtitle(BuildConfig.VERSION_NAME);
        this.actionBar.getTitleTextView().setAlpha(0.0f);
        this.actionBar.getSubtitleTextView().setAlpha(0.0f);
        this.avatarContainer = new ChatAvatarContainer(context, null, false);
        this.avatarContainer.setEnabled(false);
        this.avatarContainer.setTitleColors(Theme.getColor(Theme.key_player_actionBarTitle), Theme.getColor(Theme.key_player_actionBarSubtitle));
        MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
        if (messageObject != null) {
            long did = messageObject.getDialogId();
            int lower_id = (int) did;
            int high_id = (int) (did >> 32);
            User user;
            if (lower_id == 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id)).user_id));
                this.avatarContainer.setTitle(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarContainer.setUserAvatar(user);
            } else if (lower_id > 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                this.avatarContainer.setTitle(ContactsController.formatName(user.first_name, user.last_name));
                this.avatarContainer.setUserAvatar(user);
            } else {
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                this.avatarContainer.setTitle(chat.title);
                this.avatarContainer.setChatAvatar(chat);
            }
        }
        this.avatarContainer.setSubtitle(LocaleController.getString("AudioTitle", R.string.AudioTitle));
        ActionBar actionBar = this.actionBar;
        actionBar.addView(this.avatarContainer, 0, LayoutHelper.createFrame(-2, -1.0f, 51, 56.0f, 0.0f, 40.0f, 0.0f));
        ActionBarMenu menu = this.actionBar.createMenu();
        this.menuItem = menu.addItem(0, (int) R.drawable.ic_ab_other);
        this.menuItem.addSubItem(1, LocaleController.getString("Forward", R.string.Forward));
        this.menuItem.addSubItem(2, LocaleController.getString("ShareFile", R.string.ShareFile));
        this.menuItem.addSubItem(4, LocaleController.getString("ShowInChat", R.string.ShowInChat));
        this.menuItem.setTranslationX((float) AndroidUtilities.dp(48.0f));
        this.menuItem.setAlpha(0.0f);
        this.searchItem = menu.addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C24862());
        EditTextBoldCursor editText = this.searchItem.getSearchField();
        editText.setHint(LocaleController.getString("Search", R.string.Search));
        editText.setTextColor(Theme.getColor(Theme.key_player_actionBarTitle));
        editText.setHintTextColor(Theme.getColor(Theme.key_player_time));
        editText.setCursorColor(Theme.getColor(Theme.key_player_actionBarTitle));
        if (!AndroidUtilities.isTablet()) {
            this.actionBar.showActionModeTop();
            this.actionBar.setActionModeTopColor(Theme.getColor(Theme.key_player_actionBarTop));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C24873());
        this.shadow = new View(context);
        this.shadow.setAlpha(0.0f);
        this.shadow.setBackgroundResource(R.drawable.header_shadow);
        this.shadow2 = new View(context);
        this.shadow2.setAlpha(0.0f);
        this.shadow2.setBackgroundResource(R.drawable.header_shadow);
        this.playerLayout = new FrameLayout(context);
        this.playerLayout.setBackgroundColor(Theme.getColor(Theme.key_player_background));
        this.placeholderImageView = new BackupImageView(context) {
            private RectF rect = new RectF();

            protected void onDraw(Canvas canvas) {
                if (AudioPlayerAlert.this.hasNoCover) {
                    this.rect.set(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight());
                    canvas.drawRoundRect(this.rect, (float) getRoundRadius(), (float) getRoundRadius(), AudioPlayerAlert.this.paint);
                    int s = (int) (((float) AndroidUtilities.dp(63.0f)) * Math.max(((AudioPlayerAlert.this.thumbMaxScale / getScaleX()) / 3.0f) / AudioPlayerAlert.this.thumbMaxScale, 1.0f / AudioPlayerAlert.this.thumbMaxScale));
                    int x = (int) (this.rect.centerX() - ((float) (s / 2)));
                    int y = (int) (this.rect.centerY() - ((float) (s / 2)));
                    AudioPlayerAlert.this.noCoverDrawable.setBounds(x, y, x + s, y + s);
                    AudioPlayerAlert.this.noCoverDrawable.draw(canvas);
                    return;
                }
                super.onDraw(canvas);
            }
        };
        this.placeholderImageView.setRoundRadius(AndroidUtilities.dp(20.0f));
        this.placeholderImageView.setPivotX(0.0f);
        this.placeholderImageView.setPivotY(0.0f);
        this.placeholderImageView.setOnClickListener(new C24905());
        this.titleTextView = new TextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_player_actionBarTitle));
        this.titleTextView.setTextSize(1, 15.0f);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setSingleLine(true);
        FrameLayout frameLayout = this.playerLayout;
        frameLayout.addView(this.titleTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 72.0f, 18.0f, 60.0f, 0.0f));
        this.authorTextView = new TextView(context);
        this.authorTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        this.authorTextView.setTextSize(1, 14.0f);
        this.authorTextView.setEllipsize(TruncateAt.END);
        this.authorTextView.setSingleLine(true);
        frameLayout = this.playerLayout;
        frameLayout.addView(this.authorTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 72.0f, 40.0f, 60.0f, 0.0f));
        this.optionsButton = new ActionBarMenuItem(context, null, 0, Theme.getColor(Theme.key_player_actionBarItems));
        this.optionsButton.setIcon((int) R.drawable.ic_ab_other);
        this.optionsButton.setAdditionalOffset(-AndroidUtilities.dp(120.0f));
        frameLayout = this.playerLayout;
        frameLayout.addView(this.optionsButton, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, 19.0f, 10.0f, 0.0f));
        this.optionsButton.addSubItem(1, LocaleController.getString("Forward", R.string.Forward));
        this.optionsButton.addSubItem(2, LocaleController.getString("ShareFile", R.string.ShareFile));
        this.optionsButton.addSubItem(4, LocaleController.getString("ShowInChat", R.string.ShowInChat));
        this.optionsButton.setDelegate(new C24916());
        this.seekBarView = new SeekBarView(context);
        this.seekBarView.setDelegate(new C24927());
        frameLayout = this.playerLayout;
        frameLayout.addView(this.seekBarView, LayoutHelper.createFrame(-1, 30.0f, 51, 8.0f, 62.0f, 8.0f, 0.0f));
        this.progressView = new LineProgressView(context);
        this.progressView.setVisibility(4);
        this.progressView.setBackgroundColor(Theme.getColor(Theme.key_player_progressBackground));
        this.progressView.setProgressColor(Theme.getColor(Theme.key_player_progress));
        frameLayout = this.playerLayout;
        frameLayout.addView(this.progressView, LayoutHelper.createFrame(-1, 2.0f, 51, 20.0f, 78.0f, 20.0f, 0.0f));
        this.timeTextView = new SimpleTextView(context);
        this.timeTextView.setTextSize(12);
        this.timeTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        frameLayout = this.playerLayout;
        frameLayout.addView(this.timeTextView, LayoutHelper.createFrame(100, -2.0f, 51, 20.0f, 92.0f, 0.0f, 0.0f));
        this.durationTextView = new TextView(context);
        this.durationTextView.setTextSize(1, 12.0f);
        this.durationTextView.setTextColor(Theme.getColor(Theme.key_player_time));
        this.durationTextView.setGravity(17);
        frameLayout = this.playerLayout;
        frameLayout.addView(this.durationTextView, LayoutHelper.createFrame(-2, -2.0f, 53, 0.0f, 90.0f, 20.0f, 0.0f));
        FrameLayout bottomView = new FrameLayout(context) {
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int dist = ((right - left) - AndroidUtilities.dp(248.0f)) / 4;
                for (int a = 0; a < 5; a++) {
                    int l = AndroidUtilities.dp((float) ((a * 48) + 4)) + (dist * a);
                    int t = AndroidUtilities.dp(9.0f);
                    AudioPlayerAlert.this.buttons[a].layout(l, t, AudioPlayerAlert.this.buttons[a].getMeasuredWidth() + l, AudioPlayerAlert.this.buttons[a].getMeasuredHeight() + t);
                }
            }
        };
        this.playerLayout.addView(bottomView, LayoutHelper.createFrame(-1, 66.0f, 51, 0.0f, 106.0f, 0.0f, 0.0f));
        View[] viewArr = this.buttons;
        ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(context, null, 0, 0);
        this.shuffleButton = actionBarMenuItem;
        viewArr[0] = actionBarMenuItem;
        this.shuffleButton.setAdditionalOffset(-AndroidUtilities.dp(10.0f));
        bottomView.addView(this.shuffleButton, LayoutHelper.createFrame(48, 48, 51));
        TextView textView = this.shuffleButton.addSubItem(1, LocaleController.getString("ReverseOrder", R.string.ReverseOrder));
        textView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(16.0f), 0);
        this.playOrderButtons[0] = context.getResources().getDrawable(R.drawable.music_reverse).mutate();
        textView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        textView.setCompoundDrawablesWithIntrinsicBounds(this.playOrderButtons[0], null, null, null);
        textView = this.shuffleButton.addSubItem(2, LocaleController.getString("Shuffle", R.string.Shuffle));
        textView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(16.0f), 0);
        this.playOrderButtons[1] = context.getResources().getDrawable(R.drawable.pl_shuffle).mutate();
        textView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        textView.setCompoundDrawablesWithIntrinsicBounds(this.playOrderButtons[1], null, null, null);
        this.shuffleButton.setDelegate(new C24949());
        viewArr = this.buttons;
        View imageView = new ImageView(context);
        viewArr[1] = imageView;
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageDrawable(Theme.createSimpleSelectorDrawable(context, R.drawable.pl_previous, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
        bottomView.addView(imageView, LayoutHelper.createFrame(48, 48, 51));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MediaController.getInstance().playPreviousMessage();
            }
        });
        viewArr = this.buttons;
        ImageView imageView2 = new ImageView(context);
        this.playButton = imageView2;
        viewArr[2] = imageView2;
        this.playButton.setScaleType(ScaleType.CENTER);
        this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(context, R.drawable.pl_play, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
        bottomView.addView(this.playButton, LayoutHelper.createFrame(48, 48, 51));
        this.playButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
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
        bottomView.addView(imageView, LayoutHelper.createFrame(48, 48, 51));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MediaController.getInstance().playNextMessage();
            }
        });
        viewArr = this.buttons;
        imageView2 = new ImageView(context);
        this.repeatButton = imageView2;
        viewArr[4] = imageView2;
        this.repeatButton.setScaleType(ScaleType.CENTER);
        this.repeatButton.setPadding(0, 0, AndroidUtilities.dp(8.0f), 0);
        bottomView.addView(this.repeatButton, LayoutHelper.createFrame(50, 48, 51));
        this.repeatButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MediaController.getInstance().toggleRepeatMode();
                AudioPlayerAlert.this.updateRepeatButton();
            }
        });
        this.listView = new RecyclerListView(context) {
            boolean ignoreLayout;

            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);
                if (AudioPlayerAlert.this.searchOpenPosition != -1 && !AudioPlayerAlert.this.actionBar.isSearchFieldVisible()) {
                    this.ignoreLayout = true;
                    AudioPlayerAlert.this.layoutManager.scrollToPositionWithOffset(AudioPlayerAlert.this.searchOpenPosition, AudioPlayerAlert.this.searchOpenOffset);
                    super.onLayout(false, l, t, r, b);
                    this.ignoreLayout = false;
                    AudioPlayerAlert.this.searchOpenPosition = -1;
                } else if (AudioPlayerAlert.this.scrollToSong) {
                    AudioPlayerAlert.this.scrollToSong = false;
                    boolean found = false;
                    MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                    if (playingMessageObject != null) {
                        int idx;
                        int count = AudioPlayerAlert.this.listView.getChildCount();
                        for (int a = 0; a < count; a++) {
                            View child = AudioPlayerAlert.this.listView.getChildAt(a);
                            if ((child instanceof AudioPlayerCell) && ((AudioPlayerCell) child).getMessageObject() == playingMessageObject) {
                                if (child.getBottom() <= getMeasuredHeight()) {
                                    found = true;
                                }
                                if (!found) {
                                    idx = AudioPlayerAlert.this.playlist.indexOf(playingMessageObject);
                                    if (idx >= 0) {
                                        this.ignoreLayout = true;
                                        AudioPlayerAlert.this.layoutManager.scrollToPosition(AudioPlayerAlert.this.playlist.size() - idx);
                                        super.onLayout(false, l, t, r, b);
                                        this.ignoreLayout = false;
                                    }
                                }
                            }
                        }
                        if (!found) {
                            idx = AudioPlayerAlert.this.playlist.indexOf(playingMessageObject);
                            if (idx >= 0) {
                                this.ignoreLayout = true;
                                AudioPlayerAlert.this.layoutManager.scrollToPosition(AudioPlayerAlert.this.playlist.size() - idx);
                                super.onLayout(false, l, t, r, b);
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

            protected boolean allowSelectChildAtPosition(float x, float y) {
                float p = AudioPlayerAlert.this.playerLayout.getY() + ((float) AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
                return AudioPlayerAlert.this.playerLayout == null || y > AudioPlayerAlert.this.playerLayout.getY() + ((float) AudioPlayerAlert.this.playerLayout.getMeasuredHeight());
            }

            public boolean drawChild(Canvas canvas, View child, long drawingTime) {
                int measuredHeight;
                canvas.save();
                if (AudioPlayerAlert.this.actionBar != null) {
                    measuredHeight = AudioPlayerAlert.this.actionBar.getMeasuredHeight();
                } else {
                    measuredHeight = 0;
                }
                canvas.clipRect(0, measuredHeight + AndroidUtilities.dp(50.0f), getMeasuredWidth(), getMeasuredHeight());
                boolean result = super.drawChild(canvas, child, drawingTime);
                canvas.restore();
                return result;
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
            public void onItemClick(View view, int position) {
                if (view instanceof AudioPlayerCell) {
                    ((AudioPlayerCell) view).didPressedButton();
                }
            }
        });
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 1 && AudioPlayerAlert.this.searching && AudioPlayerAlert.this.searchWas) {
                    AndroidUtilities.hideKeyboard(AudioPlayerAlert.this.getCurrentFocus());
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                AudioPlayerAlert.this.updateLayout();
            }
        });
        this.playlist = MediaController.getInstance().getPlaylist();
        this.listAdapter.notifyDataSetChanged();
        this.containerView.addView(this.playerLayout, LayoutHelper.createFrame(-1, 178.0f));
        this.containerView.addView(this.shadow2, LayoutHelper.createFrame(-1, 3.0f));
        ViewGroup viewGroup = this.containerView;
        viewGroup.addView(this.placeholderImageView, LayoutHelper.createFrame(40, 40.0f, 51, 17.0f, 19.0f, 0.0f, 0.0f));
        this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f));
        this.containerView.addView(this.actionBar);
        updateTitle(false);
        updateRepeatButton();
        updateShuffleButton();
    }

    public void setFullAnimationProgress(float value) {
        this.fullAnimationProgress = value;
        this.placeholderImageView.setRoundRadius(AndroidUtilities.dp(20.0f * (1.0f - this.fullAnimationProgress)));
        float scale = 1.0f + (this.thumbMaxScale * this.fullAnimationProgress);
        this.placeholderImageView.setScaleX(scale);
        this.placeholderImageView.setScaleY(scale);
        float translationY = this.placeholderImageView.getTranslationY();
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

    public float getFullAnimationProgress() {
        return this.fullAnimationProgress;
    }

    private void onSubItemClick(int id) {
        Exception e;
        MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
        if (messageObject != null) {
            Bundle args;
            if (id == 1) {
                args = new Bundle();
                args.putBoolean("onlySelect", true);
                args.putInt("dialogsType", 3);
                BaseFragment dialogsActivity = new DialogsActivity(args);
                ArrayList<MessageObject> fmessages = new ArrayList();
                fmessages.add(messageObject);
                final ArrayList<MessageObject> arrayList = fmessages;
                dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                    public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
                        long did;
                        if (dids.size() > 1 || ((Long) dids.get(0)).longValue() == ((long) UserConfig.getClientUserId()) || message != null) {
                            for (int a = 0; a < dids.size(); a++) {
                                did = ((Long) dids.get(a)).longValue();
                                if (message != null) {
                                    SendMessagesHelper.getInstance().sendMessage(message.toString(), did, null, null, true, null, null, null);
                                }
                                SendMessagesHelper.getInstance().sendMessage(arrayList, did);
                            }
                            fragment.finishFragment();
                            return;
                        }
                        did = ((Long) dids.get(0)).longValue();
                        int lower_part = (int) did;
                        int high_part = (int) (did >> 32);
                        Bundle args = new Bundle();
                        args.putBoolean("scrollToTopOnResume", true);
                        if (lower_part == 0) {
                            args.putInt("enc_id", high_part);
                        } else if (lower_part > 0) {
                            args.putInt("user_id", lower_part);
                        } else if (lower_part < 0) {
                            args.putInt("chat_id", -lower_part);
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        ChatActivity chatActivity = new ChatActivity(args);
                        if (AudioPlayerAlert.this.parentActivity.presentFragment(chatActivity, true, false)) {
                            chatActivity.showReplyPanel(true, null, arrayList, null, false);
                        } else {
                            fragment.finishFragment();
                        }
                    }
                });
                this.parentActivity.presentFragment(dialogsActivity);
                dismiss();
            } else if (id == 2) {
                File f = null;
                try {
                    if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath)) {
                        File file = new File(messageObject.messageOwner.attachPath);
                        try {
                            if (file.exists()) {
                                f = file;
                            } else {
                                f = null;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            f = file;
                            FileLog.e(e);
                        }
                    }
                    if (f == null) {
                        f = FileLoader.getPathToMessage(messageObject.messageOwner);
                    }
                    if (f.exists()) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        if (messageObject != null) {
                            intent.setType(messageObject.getMimeType());
                        } else {
                            intent.setType("audio/mp3");
                        }
                        if (VERSION.SDK_INT >= 24) {
                            try {
                                intent = intent;
                                intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(ApplicationLoader.applicationContext, "org.ir.talaeii.provider", f));
                                intent.setFlags(1);
                            } catch (Exception e3) {
                                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(f));
                            }
                        } else {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(f));
                        }
                        this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), 500);
                        return;
                    }
                    builder = new Builder(this.parentActivity);
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    builder.setMessage(LocaleController.getString("PleaseDownload", R.string.PleaseDownload));
                    builder.show();
                } catch (Exception e4) {
                    e = e4;
                    FileLog.e(e);
                }
            } else if (id == 3) {
                builder = new Builder(this.parentActivity);
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                boolean[] deleteForAll = new boolean[1];
                int lower_id = (int) messageObject.getDialogId();
                if (lower_id != 0) {
                    User currentUser;
                    TLRPC$Chat currentChat;
                    if (lower_id > 0) {
                        currentUser = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                        currentChat = null;
                    } else {
                        currentUser = null;
                        currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                    }
                    if (!(currentUser == null && ChatObject.isChannel(currentChat))) {
                        int currentDate = ConnectionsManager.getInstance().getCurrentTime();
                        if (!((currentUser == null || currentUser.id == UserConfig.getClientUserId()) && currentChat == null) && ((messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty)) && messageObject.isOut() && currentDate - messageObject.messageOwner.date <= 172800)) {
                            int dp;
                            int dp2;
                            View frameLayout = new FrameLayout(this.parentActivity);
                            CheckBoxCell cell = new CheckBoxCell(this.parentActivity, true);
                            cell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            if (currentChat != null) {
                                cell.setText(LocaleController.getString("DeleteForAll", R.string.DeleteForAll), "", false, false);
                            } else {
                                cell.setText(LocaleController.formatString("DeleteForUser", R.string.DeleteForUser, new Object[]{UserObject.getFirstName(currentUser)}), "", false, false);
                            }
                            if (LocaleController.isRTL) {
                                dp = AndroidUtilities.dp(16.0f);
                            } else {
                                dp = AndroidUtilities.dp(8.0f);
                            }
                            if (LocaleController.isRTL) {
                                dp2 = AndroidUtilities.dp(8.0f);
                            } else {
                                dp2 = AndroidUtilities.dp(16.0f);
                            }
                            cell.setPadding(dp, 0, dp2, 0);
                            frameLayout.addView(cell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                            final boolean[] zArr = deleteForAll;
                            cell.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) {
                                    boolean z;
                                    CheckBoxCell cell = (CheckBoxCell) v;
                                    boolean[] zArr = zArr;
                                    if (zArr[0]) {
                                        z = false;
                                    } else {
                                        z = true;
                                    }
                                    zArr[0] = z;
                                    cell.setChecked(zArr[0], true);
                                }
                            });
                            builder.setView(frameLayout);
                        }
                    }
                }
                final MessageObject messageObject2 = messageObject;
                final boolean[] zArr2 = deleteForAll;
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AudioPlayerAlert.this.dismiss();
                        ArrayList<Integer> arr = new ArrayList();
                        arr.add(Integer.valueOf(messageObject2.getId()));
                        ArrayList<Long> random_ids = null;
                        TLRPC$EncryptedChat encryptedChat = null;
                        if (((int) messageObject2.getDialogId()) == 0 && messageObject2.messageOwner.random_id != 0) {
                            random_ids = new ArrayList();
                            random_ids.add(Long.valueOf(messageObject2.messageOwner.random_id));
                            encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (messageObject2.getDialogId() >> 32)));
                        }
                        MessagesController.getInstance().deleteMessages(arr, random_ids, encryptedChat, messageObject2.messageOwner.to_id.channel_id, zArr2[0]);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.show();
            } else if (id == 4) {
                args = new Bundle();
                long did = messageObject.getDialogId();
                int lower_part = (int) did;
                int high_id = (int) (did >> 32);
                if (lower_part == 0) {
                    args.putInt("enc_id", high_id);
                } else if (high_id == 1) {
                    args.putInt("chat_id", lower_part);
                } else if (lower_part > 0) {
                    args.putInt("user_id", lower_part);
                } else if (lower_part < 0) {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_part));
                    if (!(chat == null || chat.migrated_to == null)) {
                        args.putInt("migrated_to", lower_part);
                        lower_part = -chat.migrated_to.channel_id;
                    }
                    args.putInt("chat_id", -lower_part);
                }
                args.putInt("message_id", messageObject.getId());
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                this.parentActivity.presentFragment(new ChatActivity(args), false, false);
                dismiss();
            }
        }
    }

    private int getCurrentTop() {
        int i = 0;
        if (this.listView.getChildCount() != 0) {
            View child = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(child);
            if (holder != null) {
                int paddingTop = this.listView.getPaddingTop();
                if (holder.getAdapterPosition() == 0 && child.getTop() >= 0) {
                    i = child.getTop();
                }
                return paddingTop - i;
            }
        }
        return -1000;
    }

    public void didReceivedNotification(int id, Object... args) {
        MessageObject messageObject;
        if (id == NotificationCenter.messagePlayingDidStarted || id == NotificationCenter.messagePlayingPlayStateChanged || id == NotificationCenter.messagePlayingDidReset) {
            boolean z;
            if (id == NotificationCenter.messagePlayingDidReset && ((Boolean) args[1]).booleanValue()) {
                z = true;
            } else {
                z = false;
            }
            updateTitle(z);
            int count;
            int a;
            View view;
            AudioPlayerCell cell;
            if (id == NotificationCenter.messagePlayingDidReset || id == NotificationCenter.messagePlayingPlayStateChanged) {
                count = this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    view = this.listView.getChildAt(a);
                    if (view instanceof AudioPlayerCell) {
                        cell = (AudioPlayerCell) view;
                        messageObject = cell.getMessageObject();
                        if (messageObject != null && (messageObject.isVoice() || messageObject.isMusic())) {
                            cell.updateButtonState(false);
                        }
                    }
                }
            } else if (id == NotificationCenter.messagePlayingDidStarted && ((MessageObject) args[0]).eventId == 0) {
                count = this.listView.getChildCount();
                for (a = 0; a < count; a++) {
                    view = this.listView.getChildAt(a);
                    if (view instanceof AudioPlayerCell) {
                        cell = (AudioPlayerCell) view;
                        MessageObject messageObject1 = cell.getMessageObject();
                        if (messageObject1 != null && (messageObject1.isVoice() || messageObject1.isMusic())) {
                            cell.updateButtonState(false);
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.messagePlayingProgressDidChanged) {
            messageObject = MediaController.getInstance().getPlayingMessageObject();
            if (messageObject != null && messageObject.isMusic()) {
                updateProgress(messageObject);
            }
        } else if (id == NotificationCenter.musicDidLoaded) {
            this.playlist = MediaController.getInstance().getPlaylist();
            this.listAdapter.notifyDataSetChanged();
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    private void updateLayout() {
        if (this.listView.getChildCount() > 0) {
            int newOffset;
            View child = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(child);
            int top = child.getTop();
            if (top <= 0 || holder == null || holder.getAdapterPosition() != 0) {
                newOffset = 0;
            } else {
                newOffset = top;
            }
            if (this.searchWas || this.searching) {
                newOffset = 0;
            }
            if (this.scrollOffsetY != newOffset) {
                RecyclerListView recyclerListView = this.listView;
                this.scrollOffsetY = newOffset;
                recyclerListView.setTopGlowOffset(newOffset);
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
                        animatorArr[0] = ObjectAnimator.ofFloat(this.actionBar, "alpha", new float[]{0.0f});
                        animatorArr[1] = ObjectAnimator.ofFloat(this.shadow, "alpha", new float[]{0.0f});
                        animatorArr[2] = ObjectAnimator.ofFloat(this.shadow2, "alpha", new float[]{0.0f});
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

    public void dismiss() {
        super.dismiss();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.musicDidLoaded);
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    public void onBackPressed() {
        if (this.actionBar == null || !this.actionBar.isSearchFieldVisible()) {
            super.onBackPressed();
        } else {
            this.actionBar.closeSearchField();
        }
    }

    public void onFailedDownload(String fileName) {
    }

    public void onSuccessDownload(String fileName) {
    }

    public void onProgressDownload(String fileName, float progress) {
        this.progressView.setProgress(progress, true);
    }

    public void onProgressUpload(String fileName, float progress, boolean isEncrypted) {
    }

    public int getObserverTag() {
        return this.TAG;
    }

    private void updateShuffleButton() {
        Drawable drawable;
        if (MediaController.getInstance().isShuffleMusic()) {
            drawable = getContext().getResources().getDrawable(R.drawable.pl_shuffle).mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
            this.shuffleButton.setIcon(drawable);
        } else {
            drawable = getContext().getResources().getDrawable(R.drawable.music_reverse).mutate();
            if (MediaController.getInstance().isPlayOrderReversed()) {
                drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
            } else {
                drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_button), Mode.MULTIPLY));
            }
            this.shuffleButton.setIcon(drawable);
        }
        this.playOrderButtons[0].setColorFilter(new PorterDuffColorFilter(Theme.getColor(MediaController.getInstance().isPlayOrderReversed() ? Theme.key_player_buttonActive : Theme.key_player_button), Mode.MULTIPLY));
        this.playOrderButtons[1].setColorFilter(new PorterDuffColorFilter(Theme.getColor(MediaController.getInstance().isShuffleMusic() ? Theme.key_player_buttonActive : Theme.key_player_button), Mode.MULTIPLY));
    }

    private void updateRepeatButton() {
        int mode = MediaController.getInstance().getRepeatMode();
        if (mode == 0) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat);
            this.repeatButton.setTag(Theme.key_player_button);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_button), Mode.MULTIPLY));
        } else if (mode == 1) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat);
            this.repeatButton.setTag(Theme.key_player_buttonActive);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
        } else if (mode == 2) {
            this.repeatButton.setImageResource(R.drawable.pl_repeat1);
            this.repeatButton.setTag(Theme.key_player_buttonActive);
            this.repeatButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_player_buttonActive), Mode.MULTIPLY));
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

    private void checkIfMusicDownloaded(MessageObject messageObject) {
        File cacheFile = null;
        if (messageObject.messageOwner.attachPath != null && messageObject.messageOwner.attachPath.length() > 0) {
            cacheFile = new File(messageObject.messageOwner.attachPath);
            if (!cacheFile.exists()) {
                cacheFile = null;
            }
        }
        if (cacheFile == null) {
            cacheFile = FileLoader.getPathToMessage(messageObject.messageOwner);
        }
        if (cacheFile.exists()) {
            MediaController.getInstance().removeLoadingFileObserver(this);
            this.progressView.setVisibility(4);
            this.seekBarView.setVisibility(0);
            this.playButton.setEnabled(true);
            return;
        }
        String fileName = messageObject.getFileName();
        MediaController.getInstance().addLoadingFileObserver(fileName, this);
        Float progress = ImageLoader.getInstance().getFileProgress(fileName);
        this.progressView.setProgress(progress != null ? progress.floatValue() : 0.0f, false);
        this.progressView.setVisibility(0);
        this.seekBarView.setVisibility(4);
        this.playButton.setEnabled(false);
    }

    private void updateTitle(boolean shutdown) {
        MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
        if ((messageObject == null && shutdown) || (messageObject != null && !messageObject.isMusic())) {
            dismiss();
        } else if (messageObject != null) {
            if (messageObject.eventId != 0) {
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
            checkIfMusicDownloaded(messageObject);
            updateProgress(messageObject);
            if (MediaController.getInstance().isMessagePaused()) {
                this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(this.playButton.getContext(), R.drawable.pl_play, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
            } else {
                this.playButton.setImageDrawable(Theme.createSimpleSelectorDrawable(this.playButton.getContext(), R.drawable.pl_pause, Theme.getColor(Theme.key_player_button), Theme.getColor(Theme.key_player_buttonActive)));
            }
            String title = messageObject.getMusicTitle();
            String author = messageObject.getMusicAuthor();
            this.titleTextView.setText(title);
            this.authorTextView.setText(author);
            this.actionBar.setTitle(title);
            this.actionBar.setSubtitle(author);
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
                CharSequence format;
                int duration = 0;
                TLRPC$Document document = messageObject.getDocument();
                if (document != null) {
                    for (int a = 0; a < document.attributes.size(); a++) {
                        DocumentAttribute attribute = (DocumentAttribute) document.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            duration = attribute.duration;
                            break;
                        }
                    }
                }
                TextView textView = this.durationTextView;
                if (duration != 0) {
                    format = String.format("%d:%02d", new Object[]{Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)});
                } else {
                    format = "-:--";
                }
                textView.setText(format);
            }
        }
    }
}
