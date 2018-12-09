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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;
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
    private Runnable checkLocationRunnable = new C44281();
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
    class C44281 implements Runnable {
        C44281() {
        }

        public void run() {
            FragmentContextView.this.checkLocationString();
            AndroidUtilities.runOnUIThread(FragmentContextView.this.checkLocationRunnable, 1000);
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$2 */
    class C44292 implements OnClickListener {
        C44292() {
        }

        public void onClick(View view) {
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
    class C44313 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.FragmentContextView$3$1 */
        class C44301 implements DialogInterface.OnClickListener {
            C44301() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (FragmentContextView.this.fragment instanceof DialogsActivity) {
                    LocationController.getInstance().removeAllLocationSharings();
                } else {
                    LocationController.getInstance().removeSharingLocation(((ChatActivity) FragmentContextView.this.fragment).getDialogId());
                }
            }
        }

        C44313() {
        }

        public void onClick(View view) {
            if (FragmentContextView.this.currentStyle == 2) {
                Builder builder = new Builder(FragmentContextView.this.fragment.getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                if (FragmentContextView.this.fragment instanceof DialogsActivity) {
                    builder.setMessage(LocaleController.getString("StopLiveLocationAlertAll", R.string.StopLiveLocationAlertAll));
                } else {
                    ChatActivity chatActivity = (ChatActivity) FragmentContextView.this.fragment;
                    Chat currentChat = chatActivity.getCurrentChat();
                    User currentUser = chatActivity.getCurrentUser();
                    if (currentChat != null) {
                        builder.setMessage(LocaleController.formatString("StopLiveLocationAlertToGroup", R.string.StopLiveLocationAlertToGroup, new Object[]{currentChat.title}));
                    } else if (currentUser != null) {
                        builder.setMessage(LocaleController.formatString("StopLiveLocationAlertToUser", R.string.StopLiveLocationAlertToUser, new Object[]{UserObject.getFirstName(currentUser)}));
                    } else {
                        builder.setMessage(LocaleController.getString("AreYouSure", R.string.AreYouSure));
                    }
                }
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C44301());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.show();
                return;
            }
            MediaController.getInstance().cleanupPlayer(true, true);
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$4 */
    class C44334 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.FragmentContextView$4$1 */
        class C44321 implements SharingLocationsAlertDelegate {
            C44321() {
            }

            public void didSelectLocation(SharingLocationInfo sharingLocationInfo) {
                FragmentContextView.this.openSharingLocation(sharingLocationInfo);
            }
        }

        C44334() {
        }

        public void onClick(View view) {
            long dialogId;
            if (FragmentContextView.this.currentStyle == 0) {
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                if (FragmentContextView.this.fragment != null && playingMessageObject != null) {
                    if (playingMessageObject.isMusic()) {
                        FragmentContextView.this.fragment.showDialog(new AudioPlayerAlert(FragmentContextView.this.getContext()));
                        return;
                    }
                    if (playingMessageObject.getDialogId() == (FragmentContextView.this.fragment instanceof ChatActivity ? ((ChatActivity) FragmentContextView.this.fragment).getDialogId() : 0)) {
                        ((ChatActivity) FragmentContextView.this.fragment).scrollToMessageId(playingMessageObject.getId(), 0, false, 0, true);
                        return;
                    }
                    dialogId = playingMessageObject.getDialogId();
                    Bundle bundle = new Bundle();
                    int i = (int) dialogId;
                    int i2 = (int) (dialogId >> 32);
                    if (i == 0) {
                        bundle.putInt("enc_id", i2);
                    } else if (i2 == 1) {
                        bundle.putInt("chat_id", i);
                    } else if (i > 0) {
                        bundle.putInt("user_id", i);
                    } else if (i < 0) {
                        bundle.putInt("chat_id", -i);
                    }
                    bundle.putInt("message_id", playingMessageObject.getId());
                    FragmentContextView.this.fragment.presentFragment(new ChatActivity(bundle), FragmentContextView.this.fragment instanceof ChatActivity);
                }
            } else if (FragmentContextView.this.currentStyle == 1) {
                Intent intent = new Intent(FragmentContextView.this.getContext(), VoIPActivity.class);
                intent.addFlags(805306368);
                FragmentContextView.this.getContext().startActivity(intent);
            } else if (FragmentContextView.this.currentStyle == 2) {
                dialogId = FragmentContextView.this.fragment instanceof ChatActivity ? ((ChatActivity) FragmentContextView.this.fragment).getDialogId() : LocationController.getInstance().sharingLocationsUI.size() == 1 ? ((SharingLocationInfo) LocationController.getInstance().sharingLocationsUI.get(0)).did : 0;
                if (dialogId != 0) {
                    FragmentContextView.this.openSharingLocation(LocationController.getInstance().getSharingLocationInfo(dialogId));
                } else {
                    FragmentContextView.this.fragment.showDialog(new SharingLocationsAlert(FragmentContextView.this.getContext(), new C44321()));
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$6 */
    class C44356 extends AnimatorListenerAdapter {
        C44356() {
        }

        public void onAnimationEnd(Animator animator) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                FragmentContextView.this.setVisibility(8);
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$7 */
    class C44367 extends AnimatorListenerAdapter {
        C44367() {
        }

        public void onAnimationEnd(Animator animator) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$8 */
    class C44378 extends AnimatorListenerAdapter {
        C44378() {
        }

        public void onAnimationEnd(Animator animator) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                FragmentContextView.this.setVisibility(8);
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$9 */
    class C44389 extends AnimatorListenerAdapter {
        C44389() {
        }

        public void onAnimationEnd(Animator animator) {
            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                FragmentContextView.this.animatorSet = null;
            }
        }
    }

    public FragmentContextView(Context context, BaseFragment baseFragment, boolean z) {
        super(context);
        this.fragment = baseFragment;
        this.visible = true;
        this.isLocation = z;
        ((ViewGroup) this.fragment.getFragmentView()).setClipToPadding(false);
        setTag(Integer.valueOf(1));
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setWillNotDraw(false);
        addView(this.frameLayout, LayoutHelper.createFrame(-1, 36.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow);
        addView(view, LayoutHelper.createFrame(-1, 3.0f, 51, BitmapDescriptorFactory.HUE_RED, 36.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.playButton = new ImageView(context);
        this.playButton.setScaleType(ScaleType.CENTER);
        this.playButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_inappPlayerPlayPause), Mode.MULTIPLY));
        addView(this.playButton, LayoutHelper.createFrame(36, 36.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.playButton.setOnClickListener(new C44292());
        this.titleTextView = new TextView(context);
        this.titleTextView.setMaxLines(1);
        this.titleTextView.setLines(1);
        this.titleTextView.setSingleLine(true);
        this.titleTextView.setEllipsize(TruncateAt.END);
        this.titleTextView.setTextSize(1, 15.0f);
        this.titleTextView.setGravity(19);
        addView(this.titleTextView, LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, BitmapDescriptorFactory.HUE_RED, 36.0f, BitmapDescriptorFactory.HUE_RED));
        this.closeButton = new ImageView(context);
        this.closeButton.setImageResource(R.drawable.miniplayer_close);
        this.closeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_inappPlayerClose), Mode.MULTIPLY));
        this.closeButton.setScaleType(ScaleType.CENTER);
        addView(this.closeButton, LayoutHelper.createFrame(36, 36, 53));
        this.closeButton.setOnClickListener(new C44313());
        setOnClickListener(new C44334());
    }

    private void checkCall(boolean z) {
        View fragmentView = this.fragment.getFragmentView();
        if (!(z || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            z = true;
        }
        boolean z2 = (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().getCallState() == 15) ? false : true;
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (z2) {
            updateStyle(1);
            if (z && this.topPadding == BitmapDescriptorFactory.HUE_RED) {
                setTopPadding((float) AndroidUtilities.dp2(36.0f));
                if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
                } else {
                    ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
                }
                setTranslationY(BitmapDescriptorFactory.HUE_RED);
                this.yPosition = BitmapDescriptorFactory.HUE_RED;
            }
            if (!this.visible) {
                if (!z) {
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
                    animatorArr[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), BitmapDescriptorFactory.HUE_RED});
                    animatorArr[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                    animatorSet.playTogether(animatorArr);
                    this.animatorSet.setDuration(200);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
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
            if (z) {
                if (getVisibility() != 8) {
                    setVisibility(8);
                }
                setTopPadding(BitmapDescriptorFactory.HUE_RED);
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
            animatorArr[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.animatorSet.setDuration(200);
            this.animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                        FragmentContextView.this.setVisibility(8);
                        FragmentContextView.this.animatorSet = null;
                    }
                }
            });
            this.animatorSet.start();
        }
    }

    private void checkLiveLocation(boolean z) {
        View fragmentView = this.fragment.getFragmentView();
        if (!(z || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            z = true;
        }
        boolean isSharingLocation = this.fragment instanceof DialogsActivity ? !LocationController.getInstance().sharingLocationsUI.isEmpty() : LocationController.getInstance().isSharingLocation(((ChatActivity) this.fragment).getDialogId());
        if (isSharingLocation) {
            updateStyle(2);
            this.playButton.setImageDrawable(new ShareLocationDrawable(getContext(), true));
            if (z && this.topPadding == BitmapDescriptorFactory.HUE_RED) {
                setTopPadding((float) AndroidUtilities.dp2(36.0f));
                setTranslationY(BitmapDescriptorFactory.HUE_RED);
                this.yPosition = BitmapDescriptorFactory.HUE_RED;
            }
            if (!this.visible) {
                if (!z) {
                    if (this.animatorSet != null) {
                        this.animatorSet.cancel();
                        this.animatorSet = null;
                    }
                    this.animatorSet = new AnimatorSet();
                    AnimatorSet animatorSet = this.animatorSet;
                    r3 = new Animator[2];
                    r3[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), BitmapDescriptorFactory.HUE_RED});
                    r3[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                    animatorSet.playTogether(r3);
                    this.animatorSet.setDuration(200);
                    this.animatorSet.addListener(new C44367());
                    this.animatorSet.start();
                }
                this.visible = true;
                setVisibility(0);
            }
            if (this.fragment instanceof DialogsActivity) {
                String firstName;
                String string = LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation);
                ArrayList arrayList = LocationController.getInstance().sharingLocationsUI;
                if (arrayList.size() == 1) {
                    int dialogId = (int) ((SharingLocationInfo) arrayList.get(0)).messageObject.getDialogId();
                    if (dialogId > 0) {
                        firstName = UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(dialogId)));
                    } else {
                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-dialogId));
                        firstName = chat != null ? chat.title : TtmlNode.ANONYMOUS_REGION_ID;
                    }
                } else {
                    firstName = LocaleController.formatPluralString("Chats", LocationController.getInstance().sharingLocationsUI.size());
                }
                CharSequence format = String.format(LocaleController.getString("AttachLiveLocationIsSharing", R.string.AttachLiveLocationIsSharing), new Object[]{string, firstName});
                int indexOf = format.indexOf(string);
                CharSequence spannableStringBuilder = new SpannableStringBuilder(format);
                this.titleTextView.setEllipsize(TruncateAt.END);
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), indexOf, string.length() + indexOf, 18);
                this.titleTextView.setText(spannableStringBuilder);
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
            if (z) {
                if (getVisibility() != 8) {
                    setVisibility(8);
                }
                setTopPadding(BitmapDescriptorFactory.HUE_RED);
                return;
            }
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
                this.animatorSet = null;
            }
            this.animatorSet = new AnimatorSet();
            animatorSet = this.animatorSet;
            r3 = new Animator[2];
            r3[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f))});
            r3[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(r3);
            this.animatorSet.setDuration(200);
            this.animatorSet.addListener(new C44356());
            this.animatorSet.start();
        }
    }

    private void checkLocationString() {
        if ((this.fragment instanceof ChatActivity) && this.titleTextView != null) {
            long dialogId = ((ChatActivity) this.fragment).getDialogId();
            ArrayList arrayList = (ArrayList) LocationController.getInstance().locationsCache.get(Long.valueOf(dialogId));
            if (!this.firstLocationsLoaded) {
                LocationController.getInstance().loadLiveLocations(dialogId);
                this.firstLocationsLoaded = true;
            }
            int i = 0;
            User user = null;
            if (arrayList != null) {
                int clientUserId = UserConfig.getClientUserId();
                int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    int i3;
                    Message message = (Message) arrayList.get(i2);
                    if (message.media == null) {
                        i3 = i;
                    } else if (message.date + message.media.period > currentTime) {
                        User user2 = (user != null || message.from_id == clientUserId) ? user : MessagesController.getInstance().getUser(Integer.valueOf(message.from_id));
                        User user3 = user2;
                        i3 = i + 1;
                        user = user3;
                    } else {
                        i3 = i;
                    }
                    i2++;
                    i = i3;
                }
            }
            if (this.lastLocationSharingCount != i) {
                CharSequence charSequence;
                this.lastLocationSharingCount = i;
                String string = LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation);
                if (i == 0) {
                    charSequence = string;
                } else {
                    int i4 = i - 1;
                    if (!LocationController.getInstance().isSharingLocation(dialogId)) {
                        charSequence = i4 != 0 ? String.format("%1$s - %2$s %3$s", new Object[]{string, UserObject.getFirstName(user), LocaleController.formatPluralString("AndOther", i4)}) : String.format("%1$s - %2$s", new Object[]{string, UserObject.getFirstName(user)});
                    } else if (i4 == 0) {
                        charSequence = String.format("%1$s - %2$s", new Object[]{string, LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName)});
                    } else if (i4 != 1 || user == null) {
                        charSequence = String.format("%1$s - %2$s %3$s", new Object[]{string, LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName), LocaleController.formatPluralString("AndOther", i4)});
                    } else {
                        Object[] objArr = new Object[2];
                        objArr[0] = string;
                        objArr[1] = LocaleController.formatString("SharingYouAndOtherName", R.string.SharingYouAndOtherName, new Object[]{UserObject.getFirstName(user)});
                        charSequence = String.format("%1$s - %2$s", objArr);
                    }
                }
                if (this.lastString == null || !charSequence.equals(this.lastString)) {
                    this.lastString = charSequence;
                    int indexOf = charSequence.indexOf(string);
                    CharSequence spannableStringBuilder = new SpannableStringBuilder(charSequence);
                    this.titleTextView.setEllipsize(TruncateAt.END);
                    if (indexOf >= 0) {
                        spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), indexOf, string.length() + indexOf, 18);
                    }
                    this.titleTextView.setText(spannableStringBuilder);
                }
            }
        }
    }

    private void checkPlayer(boolean z) {
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        View fragmentView = this.fragment.getFragmentView();
        if (!(z || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0))) {
            z = true;
        }
        if (playingMessageObject == null || playingMessageObject.getId() == 0) {
            this.lastMessageObject = null;
            if (this.visible) {
                this.visible = false;
                if (z) {
                    if (getVisibility() != 8) {
                        setVisibility(8);
                    }
                    setTopPadding(BitmapDescriptorFactory.HUE_RED);
                    return;
                }
                if (this.animatorSet != null) {
                    this.animatorSet.cancel();
                    this.animatorSet = null;
                }
                this.animatorSet = new AnimatorSet();
                AnimatorSet animatorSet = this.animatorSet;
                r2 = new Animator[2];
                r2[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f))});
                r2[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(r2);
                this.animatorSet.setDuration(200);
                this.animatorSet.addListener(new C44378());
                this.animatorSet.start();
                return;
            }
            return;
        }
        int i = this.currentStyle;
        updateStyle(0);
        if (z && this.topPadding == BitmapDescriptorFactory.HUE_RED) {
            setTopPadding((float) AndroidUtilities.dp2(36.0f));
            if (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) {
                ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(36.0f);
            } else {
                ((LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.dp(72.0f);
            }
            setTranslationY(BitmapDescriptorFactory.HUE_RED);
            this.yPosition = BitmapDescriptorFactory.HUE_RED;
        }
        if (!this.visible) {
            if (!z) {
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
                r4 = new Animator[2];
                r4[0] = ObjectAnimator.ofFloat(this, "translationY", new float[]{(float) (-AndroidUtilities.dp2(36.0f)), BitmapDescriptorFactory.HUE_RED});
                r4[1] = ObjectAnimator.ofFloat(this, "topPadding", new float[]{(float) AndroidUtilities.dp2(36.0f)});
                animatorSet.playTogether(r4);
                this.animatorSet.setDuration(200);
                this.animatorSet.addListener(new C44389());
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
        if (this.lastMessageObject != playingMessageObject || i != 0) {
            CharSequence spannableStringBuilder;
            this.lastMessageObject = playingMessageObject;
            if (this.lastMessageObject.isVoice() || this.lastMessageObject.isRoundVideo()) {
                spannableStringBuilder = new SpannableStringBuilder(String.format("%s %s", new Object[]{playingMessageObject.getMusicAuthor(), playingMessageObject.getMusicTitle()}));
                this.titleTextView.setEllipsize(TruncateAt.MIDDLE);
            } else {
                spannableStringBuilder = new SpannableStringBuilder(String.format("%s - %s", new Object[]{playingMessageObject.getMusicAuthor(), playingMessageObject.getMusicTitle()}));
                this.titleTextView.setEllipsize(TruncateAt.END);
            }
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, Theme.getColor(Theme.key_inappPlayerPerformer)), 0, playingMessageObject.getMusicAuthor().length(), 18);
            this.titleTextView.setText(spannableStringBuilder);
        }
    }

    private void checkVisibility() {
        boolean z = true;
        int i = 0;
        if (this.isLocation) {
            if (!(this.fragment instanceof DialogsActivity)) {
                z = LocationController.getInstance().isSharingLocation(((ChatActivity) this.fragment).getDialogId());
            } else if (LocationController.getInstance().sharingLocationsUI.isEmpty()) {
                z = false;
            }
        } else if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().getCallState() == 15) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject == null || playingMessageObject.getId() == 0) {
                z = false;
            }
        }
        if (!z) {
            i = 8;
        }
        setVisibility(i);
    }

    private void openSharingLocation(SharingLocationInfo sharingLocationInfo) {
        if (sharingLocationInfo != null) {
            BaseFragment locationActivity = new LocationActivity(2);
            locationActivity.setMessageObject(sharingLocationInfo.messageObject);
            final long dialogId = sharingLocationInfo.messageObject.getDialogId();
            locationActivity.setDelegate(new LocationActivityDelegate() {
                public void didSelectLocation(MessageMedia messageMedia, int i) {
                    SendMessagesHelper.getInstance().sendMessage(messageMedia, dialogId, null, null, null);
                }
            });
            this.fragment.presentFragment(locationActivity);
        }
    }

    private void updateStyle(int i) {
        if (this.currentStyle != i) {
            this.currentStyle = i;
            if (i == 0 || i == 2) {
                this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_inappPlayerBackground));
                this.titleTextView.setTextColor(Theme.getColor(Theme.key_inappPlayerTitle));
                this.closeButton.setVisibility(0);
                this.playButton.setVisibility(0);
                this.titleTextView.setTypeface(Typeface.DEFAULT);
                this.titleTextView.setTextSize(1, 15.0f);
                this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, BitmapDescriptorFactory.HUE_RED, 36.0f, BitmapDescriptorFactory.HUE_RED));
                if (i == 0) {
                    this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, BitmapDescriptorFactory.HUE_RED, 36.0f, BitmapDescriptorFactory.HUE_RED));
                } else if (i == 2) {
                    this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, 8.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 51.0f, BitmapDescriptorFactory.HUE_RED, 36.0f, BitmapDescriptorFactory.HUE_RED));
                }
            } else if (i == 1) {
                this.titleTextView.setText(LocaleController.getString("ReturnToCall", R.string.ReturnToCall));
                this.frameLayout.setBackgroundColor(Theme.getColor(Theme.key_returnToCallBackground));
                this.titleTextView.setTextColor(Theme.getColor(Theme.key_returnToCallText));
                this.closeButton.setVisibility(8);
                this.playButton.setVisibility(8);
                this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.titleTextView.setTextSize(1, 14.0f);
                this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 2.0f));
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.liveLocationsChanged) {
            checkLiveLocation(false);
        } else if (i == NotificationCenter.liveLocationsCacheChanged) {
            if (this.fragment instanceof ChatActivity) {
                if (((ChatActivity) this.fragment).getDialogId() == ((Long) objArr[0]).longValue()) {
                    checkLocationString();
                }
            }
        } else if (i == NotificationCenter.messagePlayingDidStarted || i == NotificationCenter.messagePlayingPlayStateChanged || i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.didEndedCall) {
            checkPlayer(false);
        } else if (i == NotificationCenter.didStartedCall) {
            checkCall(false);
        } else {
            checkPlayer(false);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        int save = canvas.save();
        if (this.yPosition < BitmapDescriptorFactory.HUE_RED) {
            canvas.clipRect(0, (int) (-this.yPosition), view.getMeasuredWidth(), AndroidUtilities.dp2(39.0f));
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        return drawChild;
    }

    public float getTopPadding() {
        return this.topPadding;
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

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.topPadding = BitmapDescriptorFactory.HUE_RED;
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

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, AndroidUtilities.dp2(39.0f));
    }

    public void setAdditionalContextView(FragmentContextView fragmentContextView) {
        this.additionalContextView = fragmentContextView;
    }

    public void setTopPadding(float f) {
        this.topPadding = f;
        if (this.fragment != null) {
            View fragmentView = this.fragment.getFragmentView();
            int dp = (this.additionalContextView == null || this.additionalContextView.getVisibility() != 0) ? 0 : AndroidUtilities.dp(36.0f);
            if (fragmentView != null) {
                fragmentView.setPadding(0, dp + ((int) this.topPadding), 0, 0);
            }
            if (this.isLocation && this.additionalContextView != null) {
                ((LayoutParams) this.additionalContextView.getLayoutParams()).topMargin = (-AndroidUtilities.dp(36.0f)) - ((int) this.topPadding);
            }
        }
    }

    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.yPosition = f;
        invalidate();
    }
}
