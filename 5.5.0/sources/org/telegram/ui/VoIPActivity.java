package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.p015d.C0834b;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.ByteArrayOutputStream;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.ImageReceiver.ImageReceiverDelegate;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.voip.EncryptionKeyEmojifier;
import org.telegram.messenger.voip.VoIPBaseService.StateListener;
import org.telegram.messenger.voip.VoIPController;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CorrectlyMeasuringTextView;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.IdenticonDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.voip.CallSwipeView;
import org.telegram.ui.Components.voip.CallSwipeView.Listener;
import org.telegram.ui.Components.voip.CheckableImageView;
import org.telegram.ui.Components.voip.FabBackgroundDrawable;
import org.telegram.ui.Components.voip.VoIPHelper;

public class VoIPActivity extends Activity implements NotificationCenterDelegate, StateListener {
    private static final String TAG = "tg-voip-ui";
    private View acceptBtn;
    private CallSwipeView acceptSwipe;
    private ImageView blurOverlayView1;
    private ImageView blurOverlayView2;
    private Bitmap blurredPhoto1;
    private Bitmap blurredPhoto2;
    private TextView brandingText;
    private int callState;
    private View cancelBtn;
    private ImageView chatBtn;
    private FrameLayout content;
    private Animator currentAcceptAnim;
    private Animator currentDeclineAnim;
    private View declineBtn;
    private CallSwipeView declineSwipe;
    private boolean didAcceptFromHere = false;
    private TextView durationText;
    private AnimatorSet ellAnimator;
    private TextAlphaSpan[] ellSpans;
    private AnimatorSet emojiAnimator;
    boolean emojiExpanded;
    private TextView emojiExpandedText;
    boolean emojiTooltipVisible;
    private LinearLayout emojiWrap;
    private View endBtn;
    private FabBackgroundDrawable endBtnBg;
    private View endBtnIcon;
    private boolean firstStateChange = true;
    private TextView hintTextView;
    private boolean isIncomingWaiting;
    private ImageView[] keyEmojiViews = new ImageView[4];
    private boolean keyEmojiVisible;
    private String lastStateText;
    private CheckableImageView micToggle;
    private TextView nameText;
    private BackupImageView photoView;
    private AnimatorSet retryAnim;
    private boolean retrying;
    private int signalBarsCount;
    private SignalBarsDrawable signalBarsDrawable;
    private CheckableImageView spkToggle;
    private TextView stateText;
    private TextView stateText2;
    private LinearLayout swipeViewsWrap;
    private Animator textChangingAnim;
    private Animator tooltipAnim;
    private Runnable tooltipHider;
    private User user;

    /* renamed from: org.telegram.ui.VoIPActivity$1 */
    class C52681 implements ImageReceiverDelegate {
        C52681() {
        }

        public void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2) {
            Bitmap bitmap = imageReceiver.getBitmap();
            if (bitmap != null) {
                VoIPActivity.this.updateBlurredPhotos(bitmap);
            }
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$2 */
    class C52742 implements OnClickListener {
        private int tapCount = 0;

        C52742() {
        }

        public void onClick(View view) {
            if (BuildVars.DEBUG_VERSION || this.tapCount == 9) {
                VoIPActivity.this.showDebugAlert();
                this.tapCount = 0;
                return;
            }
            this.tapCount++;
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$3 */
    class C52763 implements OnClickListener {

        /* renamed from: org.telegram.ui.VoIPActivity$3$1 */
        class C52751 implements Runnable {
            C52751() {
            }

            public void run() {
                if (VoIPService.getSharedInstance() == null && !VoIPActivity.this.isFinishing()) {
                    VoIPActivity.this.endBtn.postDelayed(this, 100);
                } else if (VoIPService.getSharedInstance() != null) {
                    VoIPService.getSharedInstance().registerStateListener(VoIPActivity.this);
                }
            }
        }

        C52763() {
        }

        public void onClick(View view) {
            VoIPActivity.this.endBtn.setEnabled(false);
            if (VoIPActivity.this.retrying) {
                Intent intent = new Intent(VoIPActivity.this, VoIPService.class);
                intent.putExtra("user_id", VoIPActivity.this.user.id);
                intent.putExtra("is_outgoing", true);
                intent.putExtra("start_incall_activity", false);
                VoIPActivity.this.startService(intent);
                VoIPActivity.this.hideRetry();
                VoIPActivity.this.endBtn.postDelayed(new C52751(), 100);
            } else if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().hangUp();
            }
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$4 */
    class C52784 implements OnClickListener {

        /* renamed from: org.telegram.ui.VoIPActivity$4$1 */
        class C52771 implements DialogInterface.OnClickListener {
            C52771() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                AudioManager audioManager = (AudioManager) VoIPActivity.this.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
                if (VoIPService.getSharedInstance() != null) {
                    switch (i) {
                        case 0:
                            audioManager.setBluetoothScoOn(true);
                            audioManager.setSpeakerphoneOn(false);
                            break;
                        case 1:
                            audioManager.setBluetoothScoOn(false);
                            audioManager.setSpeakerphoneOn(false);
                            break;
                        case 2:
                            audioManager.setBluetoothScoOn(false);
                            audioManager.setSpeakerphoneOn(true);
                            break;
                    }
                    VoIPActivity.this.onAudioSettingsChanged();
                    VoIPService.getSharedInstance().updateOutputGainControlState();
                }
            }
        }

        C52784() {
        }

        public void onClick(View view) {
            boolean z = false;
            VoIPService sharedInstance = VoIPService.getSharedInstance();
            if (sharedInstance != null) {
                if (sharedInstance.isBluetoothHeadsetConnected() && sharedInstance.hasEarpiece()) {
                    new Builder(VoIPActivity.this).setItems(new CharSequence[]{LocaleController.getString("VoipAudioRoutingBluetooth", R.string.VoipAudioRoutingBluetooth), LocaleController.getString("VoipAudioRoutingEarpiece", R.string.VoipAudioRoutingEarpiece), LocaleController.getString("VoipAudioRoutingSpeaker", R.string.VoipAudioRoutingSpeaker)}, new int[]{R.drawable.ic_bluetooth_white_24dp, R.drawable.ic_phone_in_talk_white_24dp, R.drawable.ic_volume_up_white_24dp}, new C52771()).show();
                    return;
                }
                if (!VoIPActivity.this.spkToggle.isChecked()) {
                    z = true;
                }
                VoIPActivity.this.spkToggle.setChecked(z);
                AudioManager audioManager = (AudioManager) VoIPActivity.this.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
                if (sharedInstance.hasEarpiece()) {
                    audioManager.setSpeakerphoneOn(z);
                } else {
                    audioManager.setBluetoothScoOn(z);
                }
                sharedInstance.updateOutputGainControlState();
            }
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$5 */
    class C52795 implements OnClickListener {
        C52795() {
        }

        public void onClick(View view) {
            if (VoIPService.getSharedInstance() == null) {
                VoIPActivity.this.finish();
                return;
            }
            boolean z = !VoIPActivity.this.micToggle.isChecked();
            VoIPActivity.this.micToggle.setChecked(z);
            VoIPService.getSharedInstance().setMicMute(z);
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$6 */
    class C52806 implements OnClickListener {
        C52806() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
            intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
            intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
            intent.putExtra("userId", VoIPActivity.this.user.id);
            VoIPActivity.this.startActivity(intent);
            VoIPActivity.this.finish();
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$7 */
    class C52837 implements Listener {

        /* renamed from: org.telegram.ui.VoIPActivity$7$1 */
        class C52811 extends AnimatorListenerAdapter {
            C52811() {
            }

            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.currentDeclineAnim = null;
            }
        }

        /* renamed from: org.telegram.ui.VoIPActivity$7$2 */
        class C52822 extends AnimatorListenerAdapter {
            C52822() {
            }

            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.currentDeclineAnim = null;
            }
        }

        C52837() {
        }

        public void onDragCancel() {
            if (VoIPActivity.this.currentDeclineAnim != null) {
                VoIPActivity.this.currentDeclineAnim.cancel();
            }
            Animator animatorSet = new AnimatorSet();
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(VoIPActivity.this.declineSwipe, "alpha", new float[]{1.0f});
            r1[1] = ObjectAnimator.ofFloat(VoIPActivity.this.declineBtn, "alpha", new float[]{1.0f});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
            animatorSet.addListener(new C52822());
            VoIPActivity.this.currentDeclineAnim = animatorSet;
            animatorSet.start();
            VoIPActivity.this.declineSwipe.startAnimatingArrows();
        }

        public void onDragComplete() {
            VoIPActivity.this.acceptSwipe.setEnabled(false);
            VoIPActivity.this.declineSwipe.setEnabled(false);
            if (VoIPService.getSharedInstance() == null) {
                VoIPActivity.this.finish();
                return;
            }
            VoIPActivity.this.didAcceptFromHere = true;
            if (VERSION.SDK_INT < 23 || VoIPActivity.this.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                VoIPService.getSharedInstance().acceptIncomingCall();
                VoIPActivity.this.callAccepted();
                return;
            }
            VoIPActivity.this.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 101);
        }

        public void onDragStart() {
            if (VoIPActivity.this.currentDeclineAnim != null) {
                VoIPActivity.this.currentDeclineAnim.cancel();
            }
            Animator animatorSet = new AnimatorSet();
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(VoIPActivity.this.declineSwipe, "alpha", new float[]{0.2f});
            r1[1] = ObjectAnimator.ofFloat(VoIPActivity.this.declineBtn, "alpha", new float[]{0.2f});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
            animatorSet.addListener(new C52811());
            VoIPActivity.this.currentDeclineAnim = animatorSet;
            animatorSet.start();
            VoIPActivity.this.declineSwipe.stopAnimatingArrows();
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$8 */
    class C52868 implements Listener {

        /* renamed from: org.telegram.ui.VoIPActivity$8$1 */
        class C52841 extends AnimatorListenerAdapter {
            C52841() {
            }

            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.currentAcceptAnim = null;
            }
        }

        /* renamed from: org.telegram.ui.VoIPActivity$8$2 */
        class C52852 extends AnimatorListenerAdapter {
            C52852() {
            }

            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.currentAcceptAnim = null;
            }
        }

        C52868() {
        }

        public void onDragCancel() {
            if (VoIPActivity.this.currentAcceptAnim != null) {
                VoIPActivity.this.currentAcceptAnim.cancel();
            }
            Animator animatorSet = new AnimatorSet();
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(VoIPActivity.this.acceptSwipe, "alpha", new float[]{1.0f});
            r1[1] = ObjectAnimator.ofFloat(VoIPActivity.this.acceptBtn, "alpha", new float[]{1.0f});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
            animatorSet.addListener(new C52852());
            VoIPActivity.this.currentAcceptAnim = animatorSet;
            animatorSet.start();
            VoIPActivity.this.acceptSwipe.startAnimatingArrows();
        }

        public void onDragComplete() {
            VoIPActivity.this.acceptSwipe.setEnabled(false);
            VoIPActivity.this.declineSwipe.setEnabled(false);
            if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().declineIncomingCall(4, null);
            } else {
                VoIPActivity.this.finish();
            }
        }

        public void onDragStart() {
            if (VoIPActivity.this.currentAcceptAnim != null) {
                VoIPActivity.this.currentAcceptAnim.cancel();
            }
            Animator animatorSet = new AnimatorSet();
            r1 = new Animator[2];
            r1[0] = ObjectAnimator.ofFloat(VoIPActivity.this.acceptSwipe, "alpha", new float[]{0.2f});
            r1[1] = ObjectAnimator.ofFloat(VoIPActivity.this.acceptBtn, "alpha", new float[]{0.2f});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.addListener(new C52841());
            VoIPActivity.this.currentAcceptAnim = animatorSet;
            animatorSet.start();
            VoIPActivity.this.acceptSwipe.stopAnimatingArrows();
        }
    }

    /* renamed from: org.telegram.ui.VoIPActivity$9 */
    class C52879 implements OnClickListener {
        C52879() {
        }

        public void onClick(View view) {
            VoIPActivity.this.finish();
        }
    }

    private class SignalBarsDrawable extends Drawable {
        private int[] barHeights;
        private int offsetStart;
        private Paint paint;
        private RectF rect;

        private SignalBarsDrawable() {
            this.barHeights = new int[]{AndroidUtilities.dp(3.0f), AndroidUtilities.dp(6.0f), AndroidUtilities.dp(9.0f), AndroidUtilities.dp(12.0f)};
            this.paint = new Paint(1);
            this.rect = new RectF();
            this.offsetStart = 6;
        }

        public void draw(Canvas canvas) {
            if (VoIPActivity.this.callState == 3 || VoIPActivity.this.callState == 5) {
                this.paint.setColor(-1);
                int dp = getBounds().left + AndroidUtilities.dp(LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : (float) this.offsetStart);
                int i = getBounds().top;
                for (int i2 = 0; i2 < 4; i2++) {
                    this.paint.setAlpha(i2 + 1 <= VoIPActivity.this.signalBarsCount ? 242 : 102);
                    this.rect.set((float) (AndroidUtilities.dp((float) (i2 * 4)) + dp), (float) ((getIntrinsicHeight() + i) - this.barHeights[i2]), (float) (((AndroidUtilities.dp(4.0f) * i2) + dp) + AndroidUtilities.dp(3.0f)), (float) (getIntrinsicHeight() + i));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(0.3f), (float) AndroidUtilities.dp(0.3f), this.paint);
                }
            }
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(12.0f);
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp((float) (this.offsetStart + 15));
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    private class TextAlphaSpan extends CharacterStyle {
        private int alpha = 0;

        public int getAlpha() {
            return this.alpha;
        }

        public void setAlpha(int i) {
            this.alpha = i;
            VoIPActivity.this.stateText.invalidate();
            VoIPActivity.this.stateText2.invalidate();
        }

        public void updateDrawState(TextPaint textPaint) {
            textPaint.setAlpha(this.alpha);
        }
    }

    private void callAccepted() {
        this.endBtn.setVisibility(0);
        this.micToggle.setVisibility(0);
        if (VoIPService.getSharedInstance().hasEarpiece()) {
            this.spkToggle.setVisibility(0);
        }
        this.chatBtn.setVisibility(0);
        if (this.didAcceptFromHere) {
            ObjectAnimator ofArgb;
            this.acceptBtn.setVisibility(8);
            if (VERSION.SDK_INT >= 21) {
                ofArgb = ObjectAnimator.ofArgb(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-12207027, -1696188});
            } else {
                ofArgb = ObjectAnimator.ofInt(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-12207027, -1696188});
                ofArgb.setEvaluator(new ArgbEvaluator());
            }
            AnimatorSet animatorSet = new AnimatorSet();
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.micToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.spkToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.chatBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.endBtnIcon, "rotation", new float[]{-135.0f, BitmapDescriptorFactory.HUE_RED}), ofArgb});
            animatorSet2.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            animatorSet2.setDuration(500);
            AnimatorSet animatorSet3 = new AnimatorSet();
            Animator[] animatorArr = new Animator[2];
            animatorArr[0] = ObjectAnimator.ofFloat(this.swipeViewsWrap, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
            animatorArr[1] = ObjectAnimator.ofFloat(this.declineBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet3.playTogether(animatorArr);
            animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_IN);
            animatorSet3.setDuration(125);
            animatorSet.playTogether(new Animator[]{animatorSet2, animatorSet3});
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    VoIPActivity.this.swipeViewsWrap.setVisibility(8);
                    VoIPActivity.this.declineBtn.setVisibility(8);
                }
            });
            animatorSet.start();
            return;
        }
        animatorSet3 = new AnimatorSet();
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.micToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.spkToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.chatBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
        animatorSet.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        animatorSet.setDuration(500);
        animatorSet2 = new AnimatorSet();
        animatorArr = new Animator[3];
        animatorArr[1] = ObjectAnimator.ofFloat(this.declineBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorArr[2] = ObjectAnimator.ofFloat(this.acceptBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet2.playTogether(animatorArr);
        animatorSet2.setInterpolator(CubicBezierInterpolator.EASE_IN);
        animatorSet2.setDuration(125);
        animatorSet3.playTogether(new Animator[]{animatorSet, animatorSet2});
        animatorSet3.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.swipeViewsWrap.setVisibility(8);
                VoIPActivity.this.declineBtn.setVisibility(8);
                VoIPActivity.this.acceptBtn.setVisibility(8);
            }
        });
        animatorSet3.start();
    }

    @SuppressLint({"ObjectAnimatorBinding"})
    private ObjectAnimator createAlphaAnimator(Object obj, int i, int i2, int i3, int i4) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(obj, "alpha", new int[]{i, i2});
        ofInt.setDuration((long) i4);
        ofInt.setStartDelay((long) i3);
        ofInt.setInterpolator(CubicBezierInterpolator.DEFAULT);
        return ofInt;
    }

    private View createContentView() {
        View frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(0);
        View anonymousClass10 = new BackupImageView(this) {
            private Drawable bottomGradient = getResources().getDrawable(R.drawable.gradient_bottom);
            private Paint paint = new Paint();
            private Drawable topGradient = getResources().getDrawable(R.drawable.gradient_top);

            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                this.paint.setColor(1275068416);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getWidth(), (float) getHeight(), this.paint);
                this.topGradient.setBounds(0, 0, getWidth(), AndroidUtilities.dp(170.0f));
                this.topGradient.setAlpha(128);
                this.topGradient.draw(canvas);
                this.bottomGradient.setBounds(0, getHeight() - AndroidUtilities.dp(220.0f), getWidth(), getHeight());
                this.bottomGradient.setAlpha(178);
                this.bottomGradient.draw(canvas);
            }
        };
        this.photoView = anonymousClass10;
        frameLayout.addView(anonymousClass10);
        this.blurOverlayView1 = new ImageView(this);
        this.blurOverlayView1.setScaleType(ScaleType.CENTER_CROP);
        this.blurOverlayView1.setAlpha(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.blurOverlayView1);
        this.blurOverlayView2 = new ImageView(this);
        this.blurOverlayView2.setScaleType(ScaleType.CENTER_CROP);
        this.blurOverlayView2.setAlpha(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.blurOverlayView2);
        View textView = new TextView(this);
        textView.setTextColor(-855638017);
        textView.setText(LocaleController.getString("VoipInCallBranding", R.string.VoipInCallBranding));
        Drawable mutate = getResources().getDrawable(R.drawable.notification).mutate();
        mutate.setAlpha(204);
        mutate.setBounds(0, 0, AndroidUtilities.dp(15.0f), AndroidUtilities.dp(15.0f));
        this.signalBarsDrawable = new SignalBarsDrawable();
        this.signalBarsDrawable.setBounds(0, 0, this.signalBarsDrawable.getIntrinsicWidth(), this.signalBarsDrawable.getIntrinsicHeight());
        Drawable drawable = LocaleController.isRTL ? this.signalBarsDrawable : mutate;
        if (!LocaleController.isRTL) {
            mutate = this.signalBarsDrawable;
        }
        textView.setCompoundDrawables(drawable, null, mutate, null);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setGravity(LocaleController.isRTL ? 5 : 3);
        textView.setCompoundDrawablePadding(AndroidUtilities.dp(5.0f));
        textView.setTextSize(1, 14.0f);
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 18.0f, 18.0f, 18.0f, BitmapDescriptorFactory.HUE_RED));
        this.brandingText = textView;
        textView = new TextView(this);
        textView.setSingleLine();
        textView.setTextColor(-1);
        textView.setTextSize(1, 40.0f);
        textView.setEllipsize(TruncateAt.END);
        textView.setGravity(LocaleController.isRTL ? 5 : 3);
        textView.setShadowLayer((float) AndroidUtilities.dp(3.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(0.6666667f), 1275068416);
        textView.setTypeface(Typeface.create("sans-serif-light", 0));
        this.nameText = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 51, 18.0f, 43.0f, 18.0f, BitmapDescriptorFactory.HUE_RED));
        textView = new TextView(this);
        textView.setTextColor(-855638017);
        textView.setSingleLine();
        textView.setEllipsize(TruncateAt.END);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setShadowLayer((float) AndroidUtilities.dp(3.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(0.6666667f), 1275068416);
        textView.setTextSize(1, 15.0f);
        textView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.stateText = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 51, 18.0f, 98.0f, 18.0f, BitmapDescriptorFactory.HUE_RED));
        this.durationText = textView;
        textView = new TextView(this);
        textView.setTextColor(-855638017);
        textView.setSingleLine();
        textView.setEllipsize(TruncateAt.END);
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setShadowLayer((float) AndroidUtilities.dp(3.0f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(0.6666667f), 1275068416);
        textView.setTextSize(1, 15.0f);
        textView.setGravity(LocaleController.isRTL ? 5 : 3);
        textView.setVisibility(8);
        this.stateText2 = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 51, 18.0f, 98.0f, 18.0f, BitmapDescriptorFactory.HUE_RED));
        this.ellSpans = new TextAlphaSpan[]{new TextAlphaSpan(), new TextAlphaSpan(), new TextAlphaSpan()};
        textView = new CheckableImageView(this);
        textView.setBackgroundResource(R.drawable.bg_voip_icon_btn);
        drawable = getResources().getDrawable(R.drawable.ic_mic_off_white_24dp).mutate();
        drawable.setAlpha(204);
        textView.setImageDrawable(drawable);
        textView.setScaleType(ScaleType.CENTER);
        this.micToggle = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(38, 38.0f, 83, 16.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 10.0f));
        textView = new CheckableImageView(this);
        textView.setBackgroundResource(R.drawable.bg_voip_icon_btn);
        drawable = getResources().getDrawable(R.drawable.ic_volume_up_white_24dp).mutate();
        drawable.setAlpha(204);
        textView.setImageDrawable(drawable);
        textView.setScaleType(ScaleType.CENTER);
        this.spkToggle = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(38, 38.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 16.0f, 10.0f));
        textView = new ImageView(this);
        drawable = getResources().getDrawable(R.drawable.ic_chat_bubble_white_24dp).mutate();
        drawable.setAlpha(204);
        textView.setImageDrawable(drawable);
        textView.setScaleType(ScaleType.CENTER);
        this.chatBtn = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(38, 38.0f, 81, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 10.0f));
        textView = new LinearLayout(this);
        textView.setOrientation(0);
        View callSwipeView = new CallSwipeView(this);
        callSwipeView.setColor(-12207027);
        this.acceptSwipe = callSwipeView;
        textView.addView(callSwipeView, LayoutHelper.createLinear(-1, 70, 1.0f, 4, 4, -35, 4));
        View callSwipeView2 = new CallSwipeView(this);
        callSwipeView2.setColor(-1696188);
        this.declineSwipe = callSwipeView2;
        textView.addView(callSwipeView2, LayoutHelper.createLinear(-1, 70, 1.0f, -35, 4, 4, 4));
        this.swipeViewsWrap = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 80, 20.0f, BitmapDescriptorFactory.HUE_RED, 20.0f, 68.0f));
        textView = new ImageView(this);
        drawable = new FabBackgroundDrawable();
        drawable.setColor(-12207027);
        textView.setBackgroundDrawable(drawable);
        textView.setImageResource(R.drawable.ic_call_end_white_36dp);
        textView.setScaleType(ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) AndroidUtilities.dp(17.0f), (float) AndroidUtilities.dp(17.0f));
        matrix.postRotate(-135.0f, (float) AndroidUtilities.dp(35.0f), (float) AndroidUtilities.dp(35.0f));
        textView.setImageMatrix(matrix);
        this.acceptBtn = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(78, 78.0f, 83, 20.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 68.0f));
        View imageView = new ImageView(this);
        drawable = new FabBackgroundDrawable();
        drawable.setColor(-1696188);
        imageView.setBackgroundDrawable(drawable);
        imageView.setImageResource(R.drawable.ic_call_end_white_36dp);
        imageView.setScaleType(ScaleType.CENTER);
        this.declineBtn = imageView;
        frameLayout.addView(imageView, LayoutHelper.createFrame(78, 78.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 20.0f, 68.0f));
        callSwipeView.setViewToDrag(textView, false);
        callSwipeView2.setViewToDrag(imageView, true);
        textView = new FrameLayout(this);
        drawable = new FabBackgroundDrawable();
        drawable.setColor(-1696188);
        this.endBtnBg = drawable;
        textView.setBackgroundDrawable(drawable);
        anonymousClass10 = new ImageView(this);
        anonymousClass10.setImageResource(R.drawable.ic_call_end_white_36dp);
        anonymousClass10.setScaleType(ScaleType.CENTER);
        this.endBtnIcon = anonymousClass10;
        textView.addView(anonymousClass10, LayoutHelper.createFrame(70, 70.0f));
        textView.setForeground(getResources().getDrawable(R.drawable.fab_highlight_dark));
        this.endBtn = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(78, 78.0f, 81, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 68.0f));
        textView = new ImageView(this);
        drawable = new FabBackgroundDrawable();
        drawable.setColor(-1);
        textView.setBackgroundDrawable(drawable);
        textView.setImageResource(R.drawable.edit_cancel);
        textView.setColorFilter(-1996488704);
        textView.setScaleType(ScaleType.CENTER);
        textView.setVisibility(8);
        this.cancelBtn = textView;
        frameLayout.addView(textView, LayoutHelper.createFrame(78, 78.0f, 83, 52.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 68.0f));
        this.emojiWrap = new LinearLayout(this);
        this.emojiWrap.setOrientation(0);
        this.emojiWrap.setClipToPadding(false);
        this.emojiWrap.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.emojiWrap.setPivotY(BitmapDescriptorFactory.HUE_RED);
        this.emojiWrap.setPadding(AndroidUtilities.dp(14.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(14.0f), AndroidUtilities.dp(10.0f));
        int i = 0;
        while (i < 4) {
            textView = new ImageView(this);
            textView.setScaleType(ScaleType.FIT_XY);
            this.emojiWrap.addView(textView, LayoutHelper.createLinear(22, 22, i == 0 ? BitmapDescriptorFactory.HUE_RED : 4.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.keyEmojiViews[i] = textView;
            i++;
        }
        this.emojiWrap.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                boolean z = false;
                if (VoIPActivity.this.emojiTooltipVisible) {
                    VoIPActivity.this.setEmojiTooltipVisible(false);
                    if (VoIPActivity.this.tooltipHider != null) {
                        VoIPActivity.this.hintTextView.removeCallbacks(VoIPActivity.this.tooltipHider);
                        VoIPActivity.this.tooltipHider = null;
                    }
                }
                VoIPActivity voIPActivity = VoIPActivity.this;
                if (!VoIPActivity.this.emojiExpanded) {
                    z = true;
                }
                voIPActivity.setEmojiExpanded(z);
            }
        });
        frameLayout.addView(this.emojiWrap, LayoutHelper.createFrame(-2, -2, (LocaleController.isRTL ? 3 : 5) | 48));
        this.emojiWrap.setOnLongClickListener(new OnLongClickListener() {

            /* renamed from: org.telegram.ui.VoIPActivity$12$1 */
            class C52661 implements Runnable {
                C52661() {
                }

                public void run() {
                    VoIPActivity.this.tooltipHider = null;
                    VoIPActivity.this.setEmojiTooltipVisible(false);
                }
            }

            public boolean onLongClick(View view) {
                boolean z = false;
                if (VoIPActivity.this.emojiExpanded) {
                    return false;
                }
                if (VoIPActivity.this.tooltipHider != null) {
                    VoIPActivity.this.hintTextView.removeCallbacks(VoIPActivity.this.tooltipHider);
                    VoIPActivity.this.tooltipHider = null;
                }
                VoIPActivity voIPActivity = VoIPActivity.this;
                if (!VoIPActivity.this.emojiTooltipVisible) {
                    z = true;
                }
                voIPActivity.setEmojiTooltipVisible(z);
                if (VoIPActivity.this.emojiTooltipVisible) {
                    VoIPActivity.this.hintTextView.postDelayed(VoIPActivity.this.tooltipHider = new C52661(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                }
                return true;
            }
        });
        this.emojiExpandedText = new TextView(this);
        this.emojiExpandedText.setTextSize(1, 16.0f);
        this.emojiExpandedText.setTextColor(-1);
        this.emojiExpandedText.setGravity(17);
        this.emojiExpandedText.setAlpha(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.emojiExpandedText, LayoutHelper.createFrame(-1, -2.0f, 17, 10.0f, 32.0f, 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.hintTextView = new CorrectlyMeasuringTextView(this);
        this.hintTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), -231525581));
        this.hintTextView.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
        this.hintTextView.setTextSize(1, 14.0f);
        this.hintTextView.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
        this.hintTextView.setGravity(17);
        this.hintTextView.setMaxWidth(AndroidUtilities.dp(300.0f));
        this.hintTextView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.hintTextView, LayoutHelper.createFrame(-2, -2.0f, 53, BitmapDescriptorFactory.HUE_RED, 42.0f, 10.0f, BitmapDescriptorFactory.HUE_RED));
        int alpha = this.stateText.getPaint().getAlpha();
        this.ellAnimator = new AnimatorSet();
        AnimatorSet animatorSet = this.ellAnimator;
        r9 = new Animator[6];
        r9[0] = createAlphaAnimator(this.ellSpans[0], 0, alpha, 0, 300);
        r9[1] = createAlphaAnimator(this.ellSpans[1], 0, alpha, BuildConfig.VERSION_CODE, 300);
        r9[2] = createAlphaAnimator(this.ellSpans[2], 0, alpha, 300, 300);
        r9[3] = createAlphaAnimator(this.ellSpans[0], alpha, 0, 1000, ChatActivity.scheduleDownloads);
        r9[4] = createAlphaAnimator(this.ellSpans[1], alpha, 0, 1000, ChatActivity.scheduleDownloads);
        r9[5] = createAlphaAnimator(this.ellSpans[2], alpha, 0, 1000, ChatActivity.scheduleDownloads);
        animatorSet.playTogether(r9);
        this.ellAnimator.addListener(new AnimatorListenerAdapter() {
            private Runnable restarter = new C52671();

            /* renamed from: org.telegram.ui.VoIPActivity$13$1 */
            class C52671 implements Runnable {
                C52671() {
                }

                public void run() {
                    if (!VoIPActivity.this.isFinishing()) {
                        VoIPActivity.this.ellAnimator.start();
                    }
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (!VoIPActivity.this.isFinishing()) {
                    VoIPActivity.this.content.postDelayed(this.restarter, 300);
                }
            }
        });
        frameLayout.setClipChildren(false);
        this.content = frameLayout;
        return frameLayout;
    }

    private CharSequence getFormattedDebugString() {
        String debugString = VoIPService.getSharedInstance().getDebugString();
        CharSequence spannableString = new SpannableString(debugString);
        int i = 0;
        do {
            int indexOf = debugString.indexOf(10, i + 1);
            if (indexOf == -1) {
                indexOf = debugString.length();
            }
            String substring = debugString.substring(i, indexOf);
            if (substring.contains("IN_USE")) {
                spannableString.setSpan(new ForegroundColorSpan(-16711936), i, indexOf, 0);
            } else if (substring.contains(": ")) {
                spannableString.setSpan(new ForegroundColorSpan(-1426063361), i, (substring.indexOf(58) + i) + 1, 0);
            }
            i = debugString.indexOf(10, i + 1);
        } while (i != -1);
        return spannableString;
    }

    private void hideRetry() {
        if (this.retryAnim != null) {
            this.retryAnim.cancel();
        }
        this.retrying = false;
        this.spkToggle.setVisibility(0);
        this.micToggle.setVisibility(0);
        this.chatBtn.setVisibility(0);
        ObjectAnimator ofArgb;
        if (VERSION.SDK_INT >= 21) {
            ofArgb = ObjectAnimator.ofArgb(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-12207027, -1696188});
        } else {
            ofArgb = ObjectAnimator.ofInt(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-12207027, -1696188});
            ofArgb.setEvaluator(new ArgbEvaluator());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        r2 = new Animator[7];
        r2[2] = ObjectAnimator.ofFloat(this.endBtn, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
        r2[3] = ObjectAnimator.ofFloat(this.cancelBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        r2[4] = ObjectAnimator.ofFloat(this.spkToggle, "alpha", new float[]{1.0f});
        r2[5] = ObjectAnimator.ofFloat(this.micToggle, "alpha", new float[]{1.0f});
        r2[6] = ObjectAnimator.ofFloat(this.chatBtn, "alpha", new float[]{1.0f});
        animatorSet.playTogether(r2);
        animatorSet.setStartDelay(200);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.cancelBtn.setVisibility(8);
                VoIPActivity.this.endBtn.setEnabled(true);
                VoIPActivity.this.retryAnim = null;
            }
        });
        this.retryAnim = animatorSet;
        animatorSet.start();
    }

    private void setEmojiExpanded(boolean z) {
        if (this.emojiExpanded != z) {
            this.emojiExpanded = z;
            if (this.emojiAnimator != null) {
                this.emojiAnimator.cancel();
            }
            if (z) {
                int[] iArr = new int[]{0, 0};
                int[] iArr2 = new int[]{0, 0};
                this.emojiWrap.getLocationInWindow(iArr);
                this.emojiExpandedText.getLocationInWindow(iArr2);
                Rect rect = new Rect();
                getWindow().getDecorView().getGlobalVisibleRect(rect);
                int height = ((iArr2[1] - (iArr[1] + this.emojiWrap.getHeight())) - AndroidUtilities.dp(32.0f)) - this.emojiWrap.getHeight();
                int width = ((rect.width() / 2) - (Math.round(((float) this.emojiWrap.getWidth()) * 2.5f) / 2)) - iArr[0];
                AnimatorSet animatorSet = new AnimatorSet();
                Animator[] animatorArr = new Animator[7];
                animatorArr[0] = ObjectAnimator.ofFloat(this.emojiWrap, "translationY", new float[]{(float) height});
                animatorArr[1] = ObjectAnimator.ofFloat(this.emojiWrap, "translationX", new float[]{(float) width});
                animatorArr[2] = ObjectAnimator.ofFloat(this.emojiWrap, "scaleX", new float[]{2.5f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.emojiWrap, "scaleY", new float[]{2.5f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.blurOverlayView1, "alpha", new float[]{this.blurOverlayView1.getAlpha(), 1.0f, 1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.blurOverlayView2, "alpha", new float[]{this.blurOverlayView2.getAlpha(), this.blurOverlayView2.getAlpha(), 1.0f});
                animatorArr[6] = ObjectAnimator.ofFloat(this.emojiExpandedText, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
                animatorSet.setDuration(300);
                animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.emojiAnimator = animatorSet;
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        VoIPActivity.this.emojiAnimator = null;
                    }
                });
                animatorSet.start();
                return;
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            Animator[] animatorArr2 = new Animator[7];
            animatorArr2[0] = ObjectAnimator.ofFloat(this.emojiWrap, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr2[1] = ObjectAnimator.ofFloat(this.emojiWrap, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr2[2] = ObjectAnimator.ofFloat(this.emojiWrap, "scaleX", new float[]{1.0f});
            animatorArr2[3] = ObjectAnimator.ofFloat(this.emojiWrap, "scaleY", new float[]{1.0f});
            animatorArr2[4] = ObjectAnimator.ofFloat(this.blurOverlayView1, "alpha", new float[]{this.blurOverlayView1.getAlpha(), this.blurOverlayView1.getAlpha(), BitmapDescriptorFactory.HUE_RED});
            animatorArr2[5] = ObjectAnimator.ofFloat(this.blurOverlayView2, "alpha", new float[]{this.blurOverlayView2.getAlpha(), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED});
            animatorArr2[6] = ObjectAnimator.ofFloat(this.emojiExpandedText, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet2.playTogether(animatorArr2);
            animatorSet2.setDuration(300);
            animatorSet2.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.emojiAnimator = animatorSet2;
            animatorSet2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    VoIPActivity.this.emojiAnimator = null;
                }
            });
            animatorSet2.start();
        }
    }

    private void setEmojiTooltipVisible(boolean z) {
        this.emojiTooltipVisible = z;
        if (this.tooltipAnim != null) {
            this.tooltipAnim.cancel();
        }
        this.hintTextView.setVisibility(0);
        TextView textView = this.hintTextView;
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        Animator ofFloat = ObjectAnimator.ofFloat(textView, str, fArr);
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(CubicBezierInterpolator.DEFAULT);
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.tooltipAnim = null;
            }
        });
        this.tooltipAnim = ofFloat;
        ofFloat.start();
    }

    private void setStateTextAnimated(String str, boolean z) {
        if (!str.equals(this.lastStateText)) {
            CharSequence spannableStringBuilder;
            this.lastStateText = str;
            if (this.textChangingAnim != null) {
                this.textChangingAnim.cancel();
            }
            if (z) {
                if (!this.ellAnimator.isRunning()) {
                    this.ellAnimator.start();
                }
                spannableStringBuilder = new SpannableStringBuilder(str.toUpperCase());
                for (TextAlphaSpan alpha : this.ellSpans) {
                    alpha.setAlpha(0);
                }
                CharSequence spannableString = new SpannableString("...");
                spannableString.setSpan(this.ellSpans[0], 0, 1, 0);
                spannableString.setSpan(this.ellSpans[1], 1, 2, 0);
                spannableString.setSpan(this.ellSpans[2], 2, 3, 0);
                spannableStringBuilder.append(spannableString);
            } else {
                if (this.ellAnimator.isRunning()) {
                    this.ellAnimator.cancel();
                }
                spannableStringBuilder = str.toUpperCase();
            }
            this.stateText2.setText(spannableStringBuilder);
            this.stateText2.setVisibility(0);
            this.stateText.setPivotX(LocaleController.isRTL ? (float) this.stateText.getWidth() : BitmapDescriptorFactory.HUE_RED);
            this.stateText.setPivotY((float) (this.stateText.getHeight() / 2));
            this.stateText2.setPivotX(LocaleController.isRTL ? (float) this.stateText.getWidth() : BitmapDescriptorFactory.HUE_RED);
            this.stateText2.setPivotY((float) (this.stateText.getHeight() / 2));
            this.durationText = this.stateText2;
            Animator animatorSet = new AnimatorSet();
            r2 = new Animator[8];
            r2[1] = ObjectAnimator.ofFloat(this.stateText2, "translationY", new float[]{(float) (this.stateText.getHeight() / 2), BitmapDescriptorFactory.HUE_RED});
            r2[2] = ObjectAnimator.ofFloat(this.stateText2, "scaleX", new float[]{0.7f, 1.0f});
            r2[3] = ObjectAnimator.ofFloat(this.stateText2, "scaleY", new float[]{0.7f, 1.0f});
            r2[4] = ObjectAnimator.ofFloat(this.stateText, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
            r2[5] = ObjectAnimator.ofFloat(this.stateText, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED, (float) ((-this.stateText.getHeight()) / 2)});
            r2[6] = ObjectAnimator.ofFloat(this.stateText, "scaleX", new float[]{1.0f, 0.7f});
            r2[7] = ObjectAnimator.ofFloat(this.stateText, "scaleY", new float[]{1.0f, 0.7f});
            animatorSet.playTogether(r2);
            animatorSet.setDuration(200);
            animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    VoIPActivity.this.textChangingAnim = null;
                    VoIPActivity.this.stateText2.setVisibility(8);
                    VoIPActivity.this.durationText = VoIPActivity.this.stateText;
                    VoIPActivity.this.stateText.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    VoIPActivity.this.stateText.setScaleX(1.0f);
                    VoIPActivity.this.stateText.setScaleY(1.0f);
                    VoIPActivity.this.stateText.setAlpha(1.0f);
                    VoIPActivity.this.stateText.setText(VoIPActivity.this.stateText2.getText());
                }
            });
            this.textChangingAnim = animatorSet;
            animatorSet.start();
        }
    }

    private void showDebugAlert() {
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().forceRating();
            final View linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            linearLayout.setBackgroundColor(-872415232);
            int dp = AndroidUtilities.dp(16.0f);
            linearLayout.setPadding(dp, dp * 2, dp, dp * 2);
            View textView = new TextView(this);
            textView.setTextColor(-1);
            textView.setTextSize(1, 15.0f);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setGravity(17);
            textView.setText("libtgvoip v" + VoIPController.getVersion());
            linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 16.0f));
            View scrollView = new ScrollView(this);
            final View textView2 = new TextView(this);
            textView2.setTypeface(Typeface.MONOSPACE);
            textView2.setTextSize(1, 11.0f);
            textView2.setMaxWidth(AndroidUtilities.dp(350.0f));
            textView2.setTextColor(-1);
            textView2.setText(getFormattedDebugString());
            scrollView.addView(textView2);
            linearLayout.addView(scrollView, LayoutHelper.createLinear(-1, -1, 1.0f));
            View textView3 = new TextView(this);
            textView3.setBackgroundColor(-1);
            textView3.setTextColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            textView3.setPadding(dp, dp, dp, dp);
            textView3.setTextSize(1, 15.0f);
            textView3.setText(LocaleController.getString("Close", R.string.Close));
            linearLayout.addView(textView3, LayoutHelper.createLinear(-2, -2, 1, 0, 16, 0, 0));
            final WindowManager windowManager = (WindowManager) getSystemService("window");
            windowManager.addView(linearLayout, new LayoutParams(-1, -1, 1000, 0, -3));
            textView3.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    windowManager.removeView(linearLayout);
                }
            });
            linearLayout.postDelayed(new Runnable() {
                public void run() {
                    if (!VoIPActivity.this.isFinishing() && VoIPService.getSharedInstance() != null) {
                        textView2.setText(VoIPActivity.this.getFormattedDebugString());
                        linearLayout.postDelayed(this, 500);
                    }
                }
            }, 500);
        }
    }

    private void showErrorDialog(CharSequence charSequence) {
        AlertDialog show = new AlertDialog.Builder(this).setTitle(LocaleController.getString("VoipFailed", R.string.VoipFailed)).setMessage(charSequence).setPositiveButton(LocaleController.getString("OK", R.string.OK), null).show();
        show.setCanceledOnTouchOutside(true);
        show.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                VoIPActivity.this.finish();
            }
        });
    }

    private void showRetry() {
        ObjectAnimator ofArgb;
        if (this.retryAnim != null) {
            this.retryAnim.cancel();
        }
        this.endBtn.setEnabled(false);
        this.retrying = true;
        this.cancelBtn.setVisibility(0);
        this.cancelBtn.setAlpha(BitmapDescriptorFactory.HUE_RED);
        AnimatorSet animatorSet = new AnimatorSet();
        if (VERSION.SDK_INT >= 21) {
            ofArgb = ObjectAnimator.ofArgb(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-1696188, -12207027});
        } else {
            ofArgb = ObjectAnimator.ofInt(this.endBtnBg, TtmlNode.ATTR_TTS_COLOR, new int[]{-1696188, -12207027});
            ofArgb.setEvaluator(new ArgbEvaluator());
        }
        r2 = new Animator[7];
        r2[1] = ObjectAnimator.ofFloat(this.endBtn, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED, (float) (((this.content.getWidth() / 2) - AndroidUtilities.dp(52.0f)) - (this.endBtn.getWidth() / 2))});
        r2[2] = ofArgb;
        r2[3] = ObjectAnimator.ofFloat(this.endBtnIcon, "rotation", new float[]{BitmapDescriptorFactory.HUE_RED, -135.0f});
        r2[4] = ObjectAnimator.ofFloat(this.spkToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        r2[5] = ObjectAnimator.ofFloat(this.micToggle, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        r2[6] = ObjectAnimator.ofFloat(this.chatBtn, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(r2);
        animatorSet.setStartDelay(200);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                VoIPActivity.this.spkToggle.setVisibility(8);
                VoIPActivity.this.micToggle.setVisibility(8);
                VoIPActivity.this.chatBtn.setVisibility(8);
                VoIPActivity.this.retryAnim = null;
                VoIPActivity.this.endBtn.setEnabled(true);
            }
        });
        this.retryAnim = animatorSet;
        animatorSet.start();
    }

    private void startUpdatingCallDuration() {
        new Runnable() {
            public void run() {
                if (!VoIPActivity.this.isFinishing() && VoIPService.getSharedInstance() != null) {
                    if (VoIPActivity.this.callState == 3 || VoIPActivity.this.callState == 5) {
                        CharSequence format;
                        long callDuration = VoIPService.getSharedInstance().getCallDuration() / 1000;
                        TextView access$2500 = VoIPActivity.this.durationText;
                        if (callDuration > 3600) {
                            format = String.format("%d:%02d:%02d", new Object[]{Long.valueOf(callDuration / 3600), Long.valueOf((callDuration % 3600) / 60), Long.valueOf(callDuration % 60)});
                        } else {
                            format = String.format("%d:%02d", new Object[]{Long.valueOf(callDuration / 60), Long.valueOf(callDuration % 60)});
                        }
                        access$2500.setText(format);
                        VoIPActivity.this.durationText.postDelayed(this, 500);
                    }
                }
            }
        }.run();
    }

    private void updateBlurredPhotos(final Bitmap bitmap) {
        new Thread(new Runnable() {

            /* renamed from: org.telegram.ui.VoIPActivity$29$1 */
            class C52731 implements Runnable {
                C52731() {
                }

                public void run() {
                    VoIPActivity.this.blurOverlayView1.setImageBitmap(VoIPActivity.this.blurredPhoto1);
                    VoIPActivity.this.blurOverlayView2.setImageBitmap(VoIPActivity.this.blurredPhoto2);
                }
            }

            public void run() {
                Bitmap createBitmap = Bitmap.createBitmap(BuildConfig.VERSION_CODE, BuildConfig.VERSION_CODE, Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawBitmap(bitmap, null, new Rect(0, 0, BuildConfig.VERSION_CODE, BuildConfig.VERSION_CODE), new Paint(2));
                Utilities.blurBitmap(createBitmap, 3, 0, createBitmap.getWidth(), createBitmap.getHeight(), createBitmap.getRowBytes());
                C0834b a = C0834b.a(bitmap).a();
                Paint paint = new Paint();
                paint.setColor((a.a(-11242343) & 16777215) | 1140850688);
                canvas.drawColor(637534208);
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) canvas.getWidth(), (float) canvas.getHeight(), paint);
                Bitmap createBitmap2 = Bitmap.createBitmap(50, 50, Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap2);
                canvas2.drawBitmap(bitmap, null, new Rect(0, 0, 50, 50), new Paint(2));
                Utilities.blurBitmap(createBitmap2, 3, 0, createBitmap2.getWidth(), createBitmap2.getHeight(), createBitmap2.getRowBytes());
                paint.setAlpha(102);
                canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) canvas2.getWidth(), (float) canvas2.getHeight(), paint);
                VoIPActivity.this.blurredPhoto1 = createBitmap;
                VoIPActivity.this.blurredPhoto2 = createBitmap2;
                VoIPActivity.this.runOnUiThread(new C52731());
            }
        }).start();
    }

    private void updateKeyView() {
        if (VoIPService.getSharedInstance() != null) {
            new IdenticonDrawable().setColors(new int[]{16777215, -1, -1711276033, 872415231});
            EncryptedChat tLRPC$TL_encryptedChat = new TLRPC$TL_encryptedChat();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(VoIPService.getSharedInstance().getEncryptionKey());
                byteArrayOutputStream.write(VoIPService.getSharedInstance().getGA());
                tLRPC$TL_encryptedChat.auth_key = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
            }
            String[] emojifyForCall = EncryptionKeyEmojifier.emojifyForCall(Utilities.computeSHA256(tLRPC$TL_encryptedChat.auth_key, 0, tLRPC$TL_encryptedChat.auth_key.length));
            for (int i = 0; i < 4; i++) {
                Drawable emojiDrawable = Emoji.getEmojiDrawable(emojifyForCall[i]);
                if (emojiDrawable != null) {
                    emojiDrawable.setBounds(0, 0, AndroidUtilities.dp(22.0f), AndroidUtilities.dp(22.0f));
                    this.keyEmojiViews[i].setImageDrawable(emojiDrawable);
                }
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded) {
            for (ImageView invalidate : this.keyEmojiViews) {
                invalidate.invalidate();
            }
        }
        if (i == NotificationCenter.closeInCallActivity) {
            finish();
        }
    }

    public void onAudioSettingsChanged() {
        if (VoIPService.getSharedInstance() != null) {
            this.micToggle.setChecked(VoIPService.getSharedInstance().isMicMute());
            if (VoIPService.getSharedInstance().hasEarpiece() || VoIPService.getSharedInstance().isBluetoothHeadsetConnected()) {
                this.spkToggle.setVisibility(0);
                AudioManager audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
                if (!VoIPService.getSharedInstance().hasEarpiece()) {
                    this.spkToggle.setImageResource(R.drawable.ic_bluetooth_white_24dp);
                    this.spkToggle.setChecked(audioManager.isBluetoothScoOn());
                    return;
                } else if (VoIPService.getSharedInstance().isBluetoothHeadsetConnected()) {
                    if (audioManager.isBluetoothScoOn()) {
                        this.spkToggle.setImageResource(R.drawable.ic_bluetooth_white_24dp);
                    } else if (audioManager.isSpeakerphoneOn()) {
                        this.spkToggle.setImageResource(R.drawable.ic_volume_up_white_24dp);
                    } else {
                        this.spkToggle.setImageResource(R.drawable.ic_phone_in_talk_white_24dp);
                    }
                    this.spkToggle.setChecked(false);
                    return;
                } else {
                    this.spkToggle.setImageResource(R.drawable.ic_volume_up_white_24dp);
                    this.spkToggle.setChecked(audioManager.isSpeakerphoneOn());
                    return;
                }
            }
            this.spkToggle.setVisibility(4);
        }
    }

    public void onBackPressed() {
        if (this.emojiExpanded) {
            setEmojiExpanded(false);
        } else if (!this.isIncomingWaiting) {
            super.onBackPressed();
        }
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        getWindow().addFlags(524288);
        super.onCreate(bundle);
        if (VoIPService.getSharedInstance() == null) {
            finish();
            return;
        }
        if ((getResources().getConfiguration().screenLayout & 15) < 3) {
            setRequestedOrientation(1);
        }
        View createContentView = createContentView();
        setContentView(createContentView);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        }
        this.user = VoIPService.getSharedInstance().getUser();
        if (this.user.photo != null) {
            this.photoView.getImageReceiver().setDelegate(new C52681());
            this.photoView.setImage(this.user.photo.photo_big, null, new ColorDrawable(Theme.ACTION_BAR_VIDEO_EDIT_COLOR));
        } else {
            this.photoView.setVisibility(8);
            createContentView.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-14994098, -14328963}));
        }
        setVolumeControlStream(0);
        this.nameText.setOnClickListener(new C52742());
        this.endBtn.setOnClickListener(new C52763());
        this.spkToggle.setOnClickListener(new C52784());
        this.micToggle.setOnClickListener(new C52795());
        this.chatBtn.setOnClickListener(new C52806());
        this.spkToggle.setChecked(((AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO)).isSpeakerphoneOn());
        this.micToggle.setChecked(VoIPService.getSharedInstance().isMicMute());
        onAudioSettingsChanged();
        this.nameText.setText(ContactsController.formatName(this.user.first_name, this.user.last_name));
        VoIPService.getSharedInstance().registerStateListener(this);
        this.acceptSwipe.setListener(new C52837());
        this.declineSwipe.setListener(new C52868());
        this.cancelBtn.setOnClickListener(new C52879());
        getWindow().getDecorView().setKeepScreenOn(true);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeInCallActivity);
        this.hintTextView.setText(LocaleController.formatString("CallEmojiKeyTooltip", R.string.CallEmojiKeyTooltip, new Object[]{this.user.first_name}));
        this.emojiExpandedText.setText(LocaleController.formatString("CallEmojiKeyTooltip", R.string.CallEmojiKeyTooltip, new Object[]{this.user.first_name}));
    }

    protected void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeInCallActivity);
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().unregisterStateListener(this);
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!this.isIncomingWaiting || (i != 25 && i != 24)) {
            return super.onKeyDown(i, keyEvent);
        }
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().stopRinging();
        } else {
            finish();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        if (this.retrying) {
            finish();
        }
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().onUIForegroundStateChanged(false);
        }
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 101) {
            return;
        }
        if (VoIPService.getSharedInstance() == null) {
            finish();
        } else if (iArr.length > 0 && iArr[0] == 0) {
            VoIPService.getSharedInstance().acceptIncomingCall();
            callAccepted();
        } else if (shouldShowRequestPermissionRationale("android.permission.RECORD_AUDIO")) {
            this.acceptSwipe.reset();
        } else {
            VoIPService.getSharedInstance().declineIncomingCall();
            VoIPHelper.permissionDenied(this, new Runnable() {
                public void run() {
                    VoIPActivity.this.finish();
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().onUIForegroundStateChanged(true);
        }
    }

    public void onSignalBarsCountChanged(final int i) {
        runOnUiThread(new Runnable() {
            public void run() {
                VoIPActivity.this.signalBarsCount = i;
                VoIPActivity.this.brandingText.invalidate();
            }
        });
    }

    public void onStateChanged(final int i) {
        final int i2 = this.callState;
        this.callState = i;
        runOnUiThread(new Runnable() {

            /* renamed from: org.telegram.ui.VoIPActivity$22$1 */
            class C52691 implements Runnable {
                C52691() {
                }

                public void run() {
                    VoIPActivity.this.acceptSwipe.startAnimatingArrows();
                    VoIPActivity.this.declineSwipe.startAnimatingArrows();
                }
            }

            /* renamed from: org.telegram.ui.VoIPActivity$22$2 */
            class C52702 implements Runnable {
                C52702() {
                }

                public void run() {
                    VoIPActivity.this.finish();
                }
            }

            /* renamed from: org.telegram.ui.VoIPActivity$22$3 */
            class C52713 implements Runnable {
                C52713() {
                }

                public void run() {
                    VoIPActivity.this.tooltipHider = null;
                    VoIPActivity.this.setEmojiTooltipVisible(false);
                }
            }

            /* renamed from: org.telegram.ui.VoIPActivity$22$4 */
            class C52724 implements Runnable {
                C52724() {
                }

                public void run() {
                    VoIPActivity.this.finish();
                }
            }

            public void run() {
                boolean access$3000 = VoIPActivity.this.firstStateChange;
                if (VoIPActivity.this.firstStateChange) {
                    VoIPActivity.this.spkToggle.setChecked(((AudioManager) VoIPActivity.this.getSystemService(MimeTypes.BASE_TYPE_AUDIO)).isSpeakerphoneOn());
                    if (VoIPActivity.this.isIncomingWaiting = i == 15) {
                        VoIPActivity.this.swipeViewsWrap.setVisibility(0);
                        VoIPActivity.this.endBtn.setVisibility(8);
                        VoIPActivity.this.micToggle.setVisibility(8);
                        VoIPActivity.this.spkToggle.setVisibility(8);
                        VoIPActivity.this.chatBtn.setVisibility(8);
                        AndroidUtilities.runOnUIThread(new C52691(), 500);
                        VoIPActivity.this.getWindow().addFlags(2097152);
                    } else {
                        VoIPActivity.this.swipeViewsWrap.setVisibility(8);
                        VoIPActivity.this.acceptBtn.setVisibility(8);
                        VoIPActivity.this.declineBtn.setVisibility(8);
                        VoIPActivity.this.getWindow().clearFlags(2097152);
                    }
                    if (i != 3) {
                        VoIPActivity.this.emojiWrap.setVisibility(8);
                    }
                    VoIPActivity.this.firstStateChange = false;
                }
                if (!(!VoIPActivity.this.isIncomingWaiting || i == 15 || i == 11 || i == 10)) {
                    VoIPActivity.this.isIncomingWaiting = false;
                    if (!VoIPActivity.this.didAcceptFromHere) {
                        VoIPActivity.this.callAccepted();
                    }
                }
                if (i == 15) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipIncoming", R.string.VoipIncoming), false);
                    VoIPActivity.this.getWindow().addFlags(2097152);
                } else if (i == 1 || i == 2) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipConnecting", R.string.VoipConnecting), true);
                } else if (i == 12) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipExchangingKeys", R.string.VoipExchangingKeys), true);
                } else if (i == 13) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipWaiting", R.string.VoipWaiting), true);
                } else if (i == 16) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipRinging", R.string.VoipRinging), true);
                } else if (i == 14) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipRequesting", R.string.VoipRequesting), true);
                } else if (i == 10) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipHangingUp", R.string.VoipHangingUp), true);
                    VoIPActivity.this.endBtnIcon.setAlpha(0.5f);
                    VoIPActivity.this.endBtn.setEnabled(false);
                } else if (i == 11) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipCallEnded", R.string.VoipCallEnded), false);
                    VoIPActivity.this.stateText.postDelayed(new C52702(), 200);
                } else if (i == 17) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipBusy", R.string.VoipBusy), false);
                    VoIPActivity.this.showRetry();
                } else if (i == 3 || i == 5) {
                    if (!access$3000 && i == 3) {
                        r0 = VoIPActivity.this.getSharedPreferences("mainconfig", 0).getInt("call_emoji_tooltip_count", 0);
                        if (r0 < 3) {
                            VoIPActivity.this.setEmojiTooltipVisible(true);
                            VoIPActivity.this.hintTextView.postDelayed(VoIPActivity.this.tooltipHider = new C52713(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                            VoIPActivity.this.getSharedPreferences("mainconfig", 0).edit().putInt("call_emoji_tooltip_count", r0 + 1).apply();
                        }
                    }
                    if (!(i2 == 3 || i2 == 5)) {
                        VoIPActivity.this.setStateTextAnimated("0:00", false);
                        VoIPActivity.this.startUpdatingCallDuration();
                        VoIPActivity.this.updateKeyView();
                        if (VoIPActivity.this.emojiWrap.getVisibility() != 0) {
                            VoIPActivity.this.emojiWrap.setVisibility(0);
                            VoIPActivity.this.emojiWrap.setAlpha(BitmapDescriptorFactory.HUE_RED);
                            VoIPActivity.this.emojiWrap.animate().alpha(1.0f).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
                        }
                    }
                } else if (i == 4) {
                    VoIPActivity.this.setStateTextAnimated(LocaleController.getString("VoipFailed", R.string.VoipFailed), false);
                    r0 = VoIPService.getSharedInstance() != null ? VoIPService.getSharedInstance().getLastError() : 0;
                    if (r0 == 1) {
                        VoIPActivity.this.showErrorDialog(AndroidUtilities.replaceTags(LocaleController.formatString("VoipPeerIncompatible", R.string.VoipPeerIncompatible, new Object[]{ContactsController.formatName(VoIPActivity.this.user.first_name, VoIPActivity.this.user.last_name)})));
                    } else if (r0 == -1) {
                        VoIPActivity.this.showErrorDialog(AndroidUtilities.replaceTags(LocaleController.formatString("VoipPeerOutdated", R.string.VoipPeerOutdated, new Object[]{ContactsController.formatName(VoIPActivity.this.user.first_name, VoIPActivity.this.user.last_name)})));
                    } else if (r0 == -2) {
                        VoIPActivity.this.showErrorDialog(AndroidUtilities.replaceTags(LocaleController.formatString("CallNotAvailable", R.string.CallNotAvailable, new Object[]{ContactsController.formatName(VoIPActivity.this.user.first_name, VoIPActivity.this.user.last_name)})));
                    } else if (r0 == 3) {
                        VoIPActivity.this.showErrorDialog("Error initializing audio hardware");
                    } else if (r0 == -3) {
                        VoIPActivity.this.finish();
                    } else {
                        VoIPActivity.this.stateText.postDelayed(new C52724(), 1000);
                    }
                }
                VoIPActivity.this.brandingText.invalidate();
            }
        });
    }
}
