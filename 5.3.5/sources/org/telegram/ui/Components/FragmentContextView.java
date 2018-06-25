package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.SharingLocationsAlert.SharingLocationsAlertDelegate;
import org.telegram.ui.DialogsActivity;
import org.telegram.ui.LocationActivity;
import org.telegram.ui.LocationActivity.LocationActivityDelegate;
import org.telegram.ui.VoIPActivity;

public class FragmentContextView extends FrameLayout implements NotificationCenterDelegate {
    private FragmentContextView additionalContextView;
    private AnimatorSet animatorSet;
    private Runnable checkLocationRunnable = new C25901();
    private ImageView closeButton;
    private int currentStyle = -1;
    private boolean firstLocationsLoaded;
    private BaseFragment fragment;
    private FrameLayout frameLayout;
    private boolean isLocation;
    private int lastLocationSharingCount = -1;
    private MessageObject lastMessageObject;
    private String lastString;
    private boolean loadingSharingCount;
    private ImageView playButton;
    private TextView titleTextView;
    private float topPadding;
    private boolean visible;
    private float yPosition;

    /* renamed from: org.telegram.ui.Components.FragmentContextView$1 */
    class C25901 implements Runnable {
        C25901() {
        }

        public void run() {
            FragmentContextView.this.checkLocationString();
            AndroidUtilities.runOnUIThread(FragmentContextView.this.checkLocationRunnable, 1000);
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$2 */
    class C25912 implements OnClickListener {
        C25912() {
        }

        public void onClick(View v) {
            if (FragmentContextView.this.currentStyle != 0) {
                return;
            }
            if (MediaController.getInstance().isMessagePaused()) {
                MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
            } else {
                MediaController.getInstance().pauseMessage(MediaController.getInstance().getPlayingMessageObject());
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$3 */
    class C25933 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.FragmentContextView$3$1 */
        class C25921 implements DialogInterface.OnClickListener {
            C25921() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (FragmentContextView.this.fragment instanceof DialogsActivity) {
                    LocationController.getInstance().removeAllLocationSharings();
                } else {
                    LocationController.getInstance().removeSharingLocation(((ChatActivity) FragmentContextView.this.fragment).getDialogId());
                }
            }
        }

        C25933() {
        }

        public void onClick(View v) {
            if (FragmentContextView.this.currentStyle == 2) {
                Builder builder = new Builder(FragmentContextView.this.fragment.getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                if (FragmentContextView.this.fragment instanceof DialogsActivity) {
                    builder.setMessage(LocaleController.getString("StopLiveLocationAlertAll", R.string.StopLiveLocationAlertAll));
                } else {
                    ChatActivity activity = (ChatActivity) FragmentContextView.this.fragment;
                    TLRPC$Chat chat = activity.getCurrentChat();
                    User user = activity.getCurrentUser();
                    if (chat != null) {
                        builder.setMessage(LocaleController.formatString("StopLiveLocationAlertToGroup", R.string.StopLiveLocationAlertToGroup, new Object[]{chat.title}));
                    } else if (user != null) {
                        builder.setMessage(LocaleController.formatString("StopLiveLocationAlertToUser", R.string.StopLiveLocationAlertToUser, new Object[]{UserObject.getFirstName(user)}));
                    } else {
                        builder.setMessage(LocaleController.getString("AreYouSure", R.string.AreYouSure));
                    }
                }
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C25921());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.show();
                return;
            }
            MediaController.getInstance().cleanupPlayer(true, true);
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$4 */
    class C25954 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.FragmentContextView$4$1 */
        class C25941 implements SharingLocationsAlertDelegate {
            C25941() {
            }

            public void didSelectLocation(SharingLocationInfo info) {
                FragmentContextView.this.openSharingLocation(info);
            }
        }

        C25954() {
        }

        public void onClick(View v) {
            if (FragmentContextView.this.currentStyle == 0) {
                MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
                if (FragmentContextView.this.fragment != null && messageObject != null) {
                    if (messageObject.isMusic()) {
                        FragmentContextView.this.fragment.showDialog(new AudioPlayerAlert(FragmentContextView.this.getContext()));
                        return;
                    }
                    long dialog_id = 0;
                    if (FragmentContextView.this.fragment instanceof ChatActivity) {
                        dialog_id = ((ChatActivity) FragmentContextView.this.fragment).getDialogId();
                    }
                    if (messageObject.getDialogId() == dialog_id) {
                        ((ChatActivity) FragmentContextView.this.fragment).scrollToMessageId(messageObject.getId(), 0, false, 0, true);
                        return;
                    }
                    dialog_id = messageObject.getDialogId();
                    Bundle args = new Bundle();
                    int lower_part = (int) dialog_id;
                    int high_id = (int) (dialog_id >> 32);
                    if (lower_part == 0) {
                        args.putInt("enc_id", high_id);
                    } else if (high_id == 1) {
                        args.putInt("chat_id", lower_part);
                    } else if (lower_part > 0) {
                        args.putInt("user_id", lower_part);
                    } else if (lower_part < 0) {
                        args.putInt("chat_id", -lower_part);
                    }
                    args.putInt("message_id", messageObject.getId());
                    FragmentContextView.this.fragment.presentFragment(new ChatActivity(args), FragmentContextView.this.fragment instanceof ChatActivity);
                }
            } else if (FragmentContextView.this.currentStyle == 1) {
                Intent intent = new Intent(FragmentContextView.this.getContext(), VoIPActivity.class);
                intent.addFlags(805306368);
                FragmentContextView.this.getContext().startActivity(intent);
            } else if (FragmentContextView.this.currentStyle == 2) {
                long did;
                if (FragmentContextView.this.fragment instanceof ChatActivity) {
                    did = ((ChatActivity) FragmentContextView.this.fragment).getDialogId();
                } else if (LocationController.getInstance().sharingLocationsUI.size() == 1) {
                    did = ((SharingLocationInfo) LocationController.getInstance().sharingLocationsUI.get(0)).did;
                } else {
                    did = 0;
                }
                if (did != 0) {
                    FragmentContextView.this.openSharingLocation(LocationController.getInstance().getSharingLocationInfo(did));
                } else {
                    FragmentContextView.this.fragment.showDialog(new SharingLocationsAlert(FragmentContextView.this.getContext(), new C25941()));
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$6 */
    class C25976 extends AnimatorListenerAdapter {
        C25976() {
        }

        public void onAnimationEnd(Animator animation) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                FragmentContextView.this.setVisibility(8);
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$7 */
    class C25987 extends AnimatorListenerAdapter {
        C25987() {
        }

        public void onAnimationEnd(Animator animation) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$8 */
    class C25998 extends AnimatorListenerAdapter {
        C25998() {
        }

        public void onAnimationEnd(Animator animation) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                FragmentContextView.this.setVisibility(8);
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$9 */
    class C26009 extends AnimatorListenerAdapter {
        C26009() {
        }

        public void onAnimationEnd(Animator animation) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    public FragmentContextView(Context context, BaseFragment parentFragment, boolean location) {
        super(context);
        this.fragment = parentFragment;
        this.visible = true;
        this.isLocation = location;
        ((ViewGroup) this.fragment.getFragmentView()).setClipToPadding(false);
        setTag(Integer.valueOf(1));
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setWillNotDraw(false);
        addView(this.frameLayout, LayoutHelper.createFrame(-1, 36.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        View shadow = new View(context);
        shadow.setBackgroundResource(R.drawable.header_shadow);
        addView(shadow, LayoutHelper.createFrame(-1, 3.0f, 51, 0.0f, 36.0f, 0.0f, 0.0f));
        this.playButton = new ImageView(context);
        this.playButton.setScaleType(ScaleType.CENTER);
        this.playButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_inappPlayerPlayPause), Mode.MULTIPLY));
        addView(this.playButton, LayoutHelper.createFrame(36, 36.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        this.playButton.setOnClickListener(new C25912());
        this.titleTextView = new TextView(context);
        this.titleTextView.setMaxLines(1);
        this.titleTextView.setLines(1);
        this.titleTextView.setSingleLine(true);
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setTextSize(1, 15.0f);
        this.titleTextView.setGravity(19);
        addView(this.titleTextView, LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 0.0f, 36.0f, 0.0f));
        this.closeButton = new ImageView(context);
        this.closeButton.setImageResource(R.drawable.miniplayer_close);
        this.closeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_inappPlayerClose), Mode.MULTIPLY));
        this.closeButton.setScaleType(ScaleType.CENTER);
        addView(this.closeButton, LayoutHelper.createFrame(36, 36, 53));
        this.closeButton.setOnClickListener(new C25933());
        setOnClickListener(new C25954());
    }

    public void setAdditionalContextView(FragmentContextView contextView) {
        this.additionalContextView = contextView;
    }

    private void openSharingLocation(SharingLocationInfo info) {
        if (info != null) {
            LocationActivity locationActivity = new LocationActivity(2);
            locationActivity.setMessageObject(info.messageObject);
            final long dialog_id = info.messageObject.getDialogId();
            locationActivity.setDelegate(new LocationActivityDelegate() {
                public void didSelectLocation(TLRPC$MessageMedia location, int live) {
                    SendMessagesHelper.getInstance().sendMessage(location, dialog_id, null, null, null);
                }
            });
            this.fragment.presentFragment(locationActivity);
        }
    }

    public float getTopPadding() {
        return this.topPadding;
    }

    private void checkVisibility() {
        int i = 0;
        boolean show = false;
        if (this.isLocation) {
            show = this.fragment instanceof DialogsActivity ? !LocationController.getInstance().sharingLocationsUI.isEmpty() : LocationController.getInstance().isSharingLocation(((ChatActivity) this.fragment).getDialogId());
        } else if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().getCallState() == 15) {
            MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
            if (!(messageObject == null || messageObject.getId() == 0)) {
                show = true;
            }
        } else {
            show = true;
        }
        if (!show) {
            i = 8;
        }
        setVisibility(i);
    }

    public void setTopPadding(float value) {
        this.topPadding = value;
        if (this.fragment != null) {
            View view = this.fragment.getFragmentView();
            int additionalPadding = 0;
            if (this.additionalContextView != null && this.additionalContextView.getVisibility() == 0) {
                additionalPadding = AndroidUtilities.dp(36.0f);
            }
            if (view != null) {
                view.setPadding(0, ((int) this.topPadding) + additionalPadding, 0, 0);
            }
            if (this.isLocation && this.additionalContextView != null) {
                ((LayoutParams) this.additionalContextView.getLayoutParams()).topMargin = (-AndroidUtilities.dp(36.0f)) - ((int) this.topPadding);
            }
        }
    }

    private void updateStyle(int style) {
        if (this.currentStyle != style) {
            this.currentStyle = style;
            if (style == 0 || style == 2) {
                this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_inappPlayerBackground));
                this.titleTextView.setTextColor(Theme.getColor(Theme.key_inappPlayerTitle));
                this.closeButton.setVisibility(0);
                this.playButton.setVisibility(0);
                this.titleTextView.setTypeface(Typeface.DEFAULT);
                this.titleTextView.setTextSize(1, 15.0f);
                this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 0.0f, 36.0f, 0.0f));
                if (style == 0) {
                    this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                    this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 0.0f, 36.0f, 0.0f));
                } else if (style == 2) {
                    this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, 8.0f, 0.0f, 0.0f, 0.0f));
                    this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 51.0f, 0.0f, 36.0f, 0.0f));
                }
            } else if (style == 1) {
                this.titleTextView.setText(LocaleController.getString("ReturnToCall", R.string.ReturnToCall));
                this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_returnToCallBackground));
                this.titleTextView.setTextColor(Theme.getColor(Theme.key_returnToCallText));
                this.closeButton.setVisibility(8);
                this.playButton.setVisibility(8);
                this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.titleTextView.setTextSize(1, 14.0f);
                this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 0.0f, 0.0f, 2.0f));
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.topPadding = 0.0f;
        if (this.isLocation) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.liveLocationsChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.liveLocationsCacheChanged);
            return;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didStartedCall);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didEndedCall);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isLocation) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.liveLocationsChanged);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.liveLocationsCacheChanged);
            if (this.additionalContextView != null) {
                this.additionalContextView.checkVisibility();
            }
            checkLiveLocation(true);
            return;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didStartedCall);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didEndedCall);
        if (this.additionalContextView != null) {
            this.additionalContextView.checkVisibility();
        }
        if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().getCallState() == 15) {
            checkPlayer(true);
        } else {
            checkCall(true);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, AndroidUtilities.dp2(39.0f));
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.liveLocationsChanged) {
            checkLiveLocation(false);
        } else if (id == NotificationCenter.liveLocationsCacheChanged) {
            if (this.fragment instanceof ChatActivity) {
                if (((ChatActivity) this.fragment).getDialogId() == ((Long) args[0]).longValue()) {
                    checkLocationString();
                }
            }
        } else if (id == NotificationCenter.messagePlayingDidStarted || id == NotificationCenter.messagePlayingPlayStateChanged || id == NotificationCenter.messagePlayingDidReset || id == NotificationCenter.didEndedCall) {
            checkPlayer(false);
        } else if (id == NotificationCenter.didStartedCall) {
            checkCall(false);
        } else {
            checkPlayer(false);
        }
    }

    private void checkLiveLocation(boolean create) {
        View fragmentView = this.fragment.getFragmentView();
        if (!(create || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            create = true;
        }
        boolean show = this.fragment instanceof DialogsActivity ? !LocationController.getInstance().sharingLocationsUI.isEmpty() : LocationController.getInstance().isSharingLocation(((ChatActivity) this.fragment).getDialogId());
        if (show) {
            updateStyle(2);
            this.playButton.setImageDrawable(new ShareLocationDrawable(getContext(), true));
            if (create && this.topPadding == 0.0f) {
                setTopPadding((float) AndroidUtilities.dp2(36.0f));
                setTranslationY(0.0f);
                this.yPosition = 0.0f;
            }
            if (!this.visible) {
                if (!create) {
                    if (this.animatorSet != null) {
                        this.animatorSet.cancel();
                        this.animatorSet = null;
                    }
                    this.animatorSet = new AnimatorSet();
                    AnimatorSet animatorSet = this.animatorSet;
                    r18 = new Animator[2];
                    r18[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), 0.0f});
                    r18[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                    animatorSet.playTogether(r18);
                    this.animatorSet.setDuration(200);
                    this.animatorSet.addListener(new C25987());
                    this.animatorSet.start();
                }
                this.visible = true;
                setVisibility(0);
            }
            if (this.fragment instanceof DialogsActivity) {
                String param;
                String liveLocation = LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation);
                ArrayList<SharingLocationInfo> infos = LocationController.getInstance().sharingLocationsUI;
                if (infos.size() == 1) {
                    int lower_id = (int) ((SharingLocationInfo) infos.get(0)).messageObject.getDialogId();
                    if (lower_id > 0) {
                        param = UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(lower_id)));
                    } else {
                        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                        if (chat != null) {
                            param = chat.title;
                        } else {
                            param = "";
                        }
                    }
                } else {
                    param = LocaleController.formatPluralString("Chats", LocationController.getInstance().sharingLocationsUI.size());
                }
                String fullString = String.format(LocaleController.getString("AttachLiveLocationIsSharing", R.string.AttachLiveLocationIsSharing), new Object[]{liveLocation, param});
                int start = fullString.indexOf(liveLocation);
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder(fullString);
                this.titleTextView.setEllipsize(TruncateAt.END);
                stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), start, liveLocation.length() + start, 18);
                this.titleTextView.setText(stringBuilder);
                return;
            }
            this.checkLocationRunnable.run();
            checkLocationString();
            return;
        }
        this.lastLocationSharingCount = -1;
        AndroidUtilities.cancelRunOnUIThread(this.checkLocationRunnable);
        if (this.visible) {
            this.visible = false;
            if (create) {
                if (getVisibility() != 8) {
                    setVisibility(8);
                }
                setTopPadding(0.0f);
                return;
            }
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
                this.animatorSet = null;
            }
            this.animatorSet = new AnimatorSet();
            animatorSet = this.animatorSet;
            r18 = new Animator[2];
            r18[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f))});
            r18[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{0.0f});
            animatorSet.playTogether(r18);
            this.animatorSet.setDuration(200);
            this.animatorSet.addListener(new C25976());
            this.animatorSet.start();
        }
    }

    private void checkLocationString() {
        if ((this.fragment instanceof ChatActivity) && this.titleTextView != null) {
            long dialogId = this.fragment.getDialogId();
            ArrayList<TLRPC$Message> messages = (ArrayList) LocationController.getInstance().locationsCache.get(Long.valueOf(dialogId));
            if (!this.firstLocationsLoaded) {
                LocationController.getInstance().loadLiveLocations(dialogId);
                this.firstLocationsLoaded = true;
            }
            int locationSharingCount = 0;
            User notYouUser = null;
            if (messages != null) {
                int currentUserId = UserConfig.getClientUserId();
                int date = ConnectionsManager.getInstance().getCurrentTime();
                for (int a = 0; a < messages.size(); a++) {
                    TLRPC$Message message = (TLRPC$Message) messages.get(a);
                    if (message.media != null && message.date + message.media.period > date) {
                        if (notYouUser == null && message.from_id != currentUserId) {
                            notYouUser = MessagesController.getInstance().getUser(Integer.valueOf(message.from_id));
                        }
                        locationSharingCount++;
                    }
                }
            }
            if (this.lastLocationSharingCount != locationSharingCount) {
                String fullString;
                this.lastLocationSharingCount = locationSharingCount;
                String liveLocation = LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation);
                if (locationSharingCount == 0) {
                    fullString = liveLocation;
                } else {
                    int otherSharingCount = locationSharingCount - 1;
                    if (LocationController.getInstance().isSharingLocation(dialogId)) {
                        if (otherSharingCount == 0) {
                            fullString = String.format("%1$s - %2$s", new Object[]{liveLocation, LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName)});
                        } else if (otherSharingCount != 1 || notYouUser == null) {
                            fullString = String.format("%1$s - %2$s %3$s", new Object[]{liveLocation, LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName), LocaleController.formatPluralString("AndOther", otherSharingCount)});
                        } else {
                            Object[] objArr = new Object[2];
                            objArr[0] = liveLocation;
                            objArr[1] = LocaleController.formatString("SharingYouAndOtherName", R.string.SharingYouAndOtherName, new Object[]{UserObject.getFirstName(notYouUser)});
                            fullString = String.format("%1$s - %2$s", objArr);
                        }
                    } else if (otherSharingCount != 0) {
                        fullString = String.format("%1$s - %2$s %3$s", new Object[]{liveLocation, UserObject.getFirstName(notYouUser), LocaleController.formatPluralString("AndOther", otherSharingCount)});
                    } else {
                        fullString = String.format("%1$s - %2$s", new Object[]{liveLocation, UserObject.getFirstName(notYouUser)});
                    }
                }
                if (this.lastString == null || !fullString.equals(this.lastString)) {
                    this.lastString = fullString;
                    int start = fullString.indexOf(liveLocation);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(fullString);
                    this.titleTextView.setEllipsize(TruncateAt.END);
                    if (start >= 0) {
                        spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), start, liveLocation.length() + start, 18);
                    }
                    this.titleTextView.setText(spannableStringBuilder);
                }
            }
        }
    }

    private void checkPlayer(boolean create) {
        MessageObject messageObject = MediaController.getInstance().getPlayingMessageObject();
        View fragmentView = this.fragment.getFragmentView();
        if (!(create || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            create = true;
        }
        if (messageObject == null || messageObject.getId() == 0) {
            this.lastMessageObject = null;
            if (this.visible) {
                this.visible = false;
                if (create) {
                    if (getVisibility() != 8) {
                        setVisibility(8);
                    }
                    setTopPadding(0.0f);
                    return;
                }
                if (this.animatorSet != null) {
                    this.animatorSet.cancel();
                    this.animatorSet = null;
                }
                this.animatorSet = new AnimatorSet();
                AnimatorSet animatorSet = this.animatorSet;
                r6 = new Animator[2];
                r6[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f))});
                r6[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{0.0f});
                animatorSet.playTogether(r6);
                this.animatorSet.setDuration(200);
                this.animatorSet.addListener(new C25998());
                this.animatorSet.start();
                return;
            }
            return;
        }
        int prevStyle = this.currentStyle;
        updateStyle(0);
        if (create && this.topPadding == 0.0f) {
            setTopPadding((float) AndroidUtilities.dp2(36.0f));
            if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
            } else {
                ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
            }
            setTranslationY(0.0f);
            this.yPosition = 0.0f;
        }
        if (!this.visible) {
            if (!create) {
                if (this.animatorSet != null) {
                    this.animatorSet.cancel();
                    this.animatorSet = null;
                }
                this.animatorSet = new AnimatorSet();
                if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
                } else {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
                }
                animatorSet = this.animatorSet;
                r6 = new Animator[2];
                r6[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), 0.0f});
                r6[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                animatorSet.playTogether(r6);
                this.animatorSet.setDuration(200);
                this.animatorSet.addListener(new C26009());
                this.animatorSet.start();
            }
            this.visible = true;
            setVisibility(0);
        }
        if (MediaController.getInstance().isMessagePaused()) {
            this.playButton.setImageResource(R.drawable.miniplayer_play);
        } else {
            this.playButton.setImageResource(R.drawable.miniplayer_pause);
        }
        if (this.lastMessageObject != messageObject || prevStyle != 0) {
            SpannableStringBuilder stringBuilder;
            this.lastMessageObject = messageObject;
            if (this.lastMessageObject.isVoice() || this.lastMessageObject.isRoundVideo()) {
                stringBuilder = new SpannableStringBuilder(String.format("%s %s", new Object[]{messageObject.getMusicAuthor(), messageObject.getMusicTitle()}));
                this.titleTextView.setEllipsize(TruncateAt.MIDDLE);
            } else {
                stringBuilder = new SpannableStringBuilder(String.format("%s - %s", new Object[]{messageObject.getMusicAuthor(), messageObject.getMusicTitle()}));
                this.titleTextView.setEllipsize(TruncateAt.END);
            }
            stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), 0, messageObject.getMusicAuthor().length(), 18);
            this.titleTextView.setText(stringBuilder);
        }
    }

    private void checkCall(boolean create) {
        boolean callAvailable;
        View fragmentView = this.fragment.getFragmentView();
        if (!(create || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            create = true;
        }
        if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().getCallState() == 15) {
            callAvailable = false;
        } else {
            callAvailable = true;
        }
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (callAvailable) {
            updateStyle(1);
            if (create && this.topPadding == 0.0f) {
                setTopPadding((float) AndroidUtilities.dp2(36.0f));
                if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
                } else {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
                }
                setTranslationY(0.0f);
                this.yPosition = 0.0f;
            }
            if (!this.visible) {
                if (!create) {
                    if (this.animatorSet != null) {
                        this.animatorSet.cancel();
                        this.animatorSet = null;
                    }
                    this.animatorSet = new AnimatorSet();
                    if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                        ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
                    } else {
                        ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
                    }
                    animatorSet = this.animatorSet;
                    animatorArr = new Animator[2];
                    animatorArr[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), 0.0f});
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                    animatorSet.playTogether(animatorArr);
                    this.animatorSet.setDuration(200);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                                FragmentContextView.this.animatorSet = null;
                            }
                        }
                    });
                    this.animatorSet.start();
                }
                this.visible = true;
                setVisibility(0);
            }
        } else if (this.visible) {
            this.visible = false;
            if (create) {
                if (getVisibility() != 8) {
                    setVisibility(8);
                }
                setTopPadding(0.0f);
                return;
            }
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
                this.animatorSet = null;
            }
            this.animatorSet = new AnimatorSet();
            animatorSet = this.animatorSet;
            animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f))});
            animatorArr[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{0.0f});
            animatorSet.playTogether(animatorArr);
            this.animatorSet.setDuration(200);
            this.animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animation)) {
                        FragmentContextView.this.setVisibility(8);
                        FragmentContextView.this.animatorSet = null;
                    }
                }
            });
            this.animatorSet.start();
        }
    }

    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY);
        this.yPosition = translationY;
        invalidate();
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        int restoreToCount = canvas.save();
        if (this.yPosition < 0.0f) {
            canvas.clipRect(0, (int) (-this.yPosition), child.getMeasuredWidth(), AndroidUtilities.dp2(39.0f));
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(restoreToCount);
        return result;
    }
}
