package org.telegram.customization.dynamicadapter.viewholder;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.content.C0235a;
import android.support.v7.widget.CardView;
import android.support.v7.widget.av;
import android.support.v7.widget.av.C1032b;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SlsMediaViewActivity;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.dynamicadapter.data.TelegramMessage;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p153c.C2671d;
import org.telegram.customization.util.C2868b;
import org.telegram.customization.util.C2879f;
import org.telegram.customization.util.view.CircularProgressBarAutoRotate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLoader.FileLoaderDelegate;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AnimatedFileDrawable;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.LaunchActivity;
import utils.C3792d;
import utils.C5319b;
import utils.p178a.C3791b;

@ViewHolderType(model = SlsBaseMessage.class, type = 100)
public class SlsMessageHolder extends HolderBase implements OnClickListener, NotificationCenterDelegate {
    public static final int ACTION_FAVORITE = 2;
    public static final int ACTION_PLAY_MUSIC = 3;
    public static final int ACTION_SAVE_TO_MUSIC = 4;
    public static final int ACTION_SHARE = 1;
    private BaseFragment baseFragment;
    private SlsBaseMessage baseMessage;
    CardView cardView;
    long delegateDocId = -1;
    private Document document;
    private View freeView;
    private AnimatedFileDrawable gifDrawable;
    ImageView ivChannelImage;
    ImageView ivDownloadMusic;
    ImageView ivMain;
    ImageView ivPlayStreamVideo;
    ImageView ivPlayVideo;
    ImageView ivShare;
    ImageView ivShareMessageText;
    ImageView ivThumbnail;
    private View iv_more;
    FileLoaderDelegate loaderDelegate;
    private int msgRecievedAction;
    CircularProgressBar musicProgressBar;
    CircularProgressBar pbImageLoading;
    View rlBottomContainer;
    View rlFileContainer;
    Animation rotation;
    Animation rotation1;
    private boolean showingDialog;
    TelegramMessage tlMsg;
    TextView tvChannelName;
    TextView tvChannelNameWithImage;
    TextView tvDate;
    TextView tvFileDesc;
    TextView tvFileName;
    TextView tvMessageText;
    TextView tvTrafficPriceStatus;
    TextView tvViews;
    private final VideoView vvMain;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1 */
    class C26981 implements FileLoaderDelegate {
        C26981() {
        }

        public void fileDidFailedLoad(final String str, int i) {
            if (str.contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                SlsMessageHolder.this.ivPlayVideo.post(new Runnable() {

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1 */
                    class C26931 implements Runnable {

                        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1$1 */
                        class C26911 implements OnClickListener {
                            C26911() {
                            }

                            public void onClick(View view) {
                                SlsMessageHolder.this.fillTlMsg(SlsMessageHolder.this.baseMessage);
                                SlsMessageHolder.this.document = SlsMessageHolder.this.tlMsg.getMessage().media.document;
                                if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.this.document))) {
                                    FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.this.document);
                                    return;
                                }
                                FileLoader.getInstance().loadFile(SlsMessageHolder.this.document, true, 0);
                                SlsMessageHolder.this.musicProgressBar.setVisibility(0);
                                SlsMessageHolder.this.musicProgressBar.setProgress(2.0f);
                                SlsMessageHolder.this.musicProgressBar.startAnimation(SlsMessageHolder.this.rotation1);
                                SlsMessageHolder.this.musicProgressBar.setAnimation(SlsMessageHolder.this.rotation1);
                                SlsMessageHolder.this.rotation1.startNow();
                                SlsMessageHolder.this.musicProgressBar.invalidate();
                            }
                        }

                        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$2$1$2 */
                        class C26922 implements OnClickListener {
                            C26922() {
                            }

                            public void onClick(View view) {
                                SlsMessageHolder.this.fillTlMsg(SlsMessageHolder.this.baseMessage);
                                SlsMessageHolder.this.document = SlsMessageHolder.this.tlMsg.getMessage().media.document;
                                if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.this.document))) {
                                    FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.this.document);
                                    return;
                                }
                                FileLoader.getInstance().loadFile(SlsMessageHolder.this.document, true, 0);
                                SlsMessageHolder.this.pbImageLoading.setVisibility(0);
                                SlsMessageHolder.this.pbImageLoading.setProgress(2.0f);
                                SlsMessageHolder.this.pbImageLoading.startAnimation(SlsMessageHolder.this.rotation);
                                SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                            }
                        }

                        C26931() {
                        }

                        public void run() {
                            if (!str.contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                                return;
                            }
                            if (SlsMessageHolder.this.baseMessage.getMessage().getMediaType() == 9) {
                                SlsMessageHolder.this.musicProgressBar.clearAnimation();
                                SlsMessageHolder.this.musicProgressBar.setVisibility(8);
                                SlsMessageHolder.this.ivDownloadMusic.setVisibility(0);
                                SlsMessageHolder.this.ivDownloadMusic.setImageResource(R.drawable.load_big);
                                SlsMessageHolder.this.ivDownloadMusic.setOnClickListener(new C26911());
                                return;
                            }
                            SlsMessageHolder.this.pbImageLoading.clearAnimation();
                            SlsMessageHolder.this.pbImageLoading.setVisibility(8);
                            SlsMessageHolder.this.ivPlayVideo.setVisibility(0);
                            SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.load_big);
                            SlsMessageHolder.this.ivPlayVideo.setOnClickListener(new C26922());
                        }
                    }

                    public void run() {
                        AndroidUtilities.runOnUIThread(new C26931());
                    }
                });
            }
        }

        public void fileDidFailedUpload(String str, boolean z) {
        }

        public void fileDidLoaded(String str, final File file, final int i) {
            if (file.getName().contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$1 */
                    class C26871 implements OnClickListener {
                        C26871() {
                        }

                        public void onClick(View view) {
                            SlsMessageHolder.this.ivPlayVideo.setVisibility(8);
                            SlsMessageHolder.this.gifDrawable = new AnimatedFileDrawable(file, false);
                            SlsMessageHolder.this.gifDrawable.setParentView(SlsMessageHolder.this.ivMain);
                            SlsMessageHolder.this.gifDrawable.start();
                            SlsMessageHolder.this.ivMain.setImageDrawable(SlsMessageHolder.this.gifDrawable);
                        }
                    }

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$2 */
                    class C26882 implements OnClickListener {
                        C26882() {
                        }

                        public void onClick(View view) {
                            SlsMessageHolder.this.gotoMediaPagerActivity();
                        }
                    }

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$1$1$3 */
                    class C26893 implements OnClickListener {
                        C26893() {
                        }

                        public void onClick(View view) {
                            Log.d("LEE", "Music_Ready_For_Play");
                            SlsMessageHolder.this.msgRecievedAction = 3;
                            SlsMessageHolder.this.loadSingleMessage();
                        }
                    }

                    public void run() {
                        if (file.getName().contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                            SlsMessageHolder.this.pbImageLoading.clearAnimation();
                            SlsMessageHolder.this.pbImageLoading.setVisibility(8);
                            SlsMessageHolder.this.musicProgressBar.clearAnimation();
                            SlsMessageHolder.this.musicProgressBar.setVisibility(8);
                            if (file.exists()) {
                                SlsMessageHolder.this.pbImageLoading.clearAnimation();
                                SlsMessageHolder.this.ivPlayStreamVideo.setVisibility(8);
                                if (i != -22 && SlsMessageHolder.this.baseMessage.getMessage().getMediaType() != 8 && SlsMessageHolder.this.baseMessage.getMessage().getMediaType() != 9) {
                                    SlsMessageHolder.this.ivPlayVideo.setVisibility(0);
                                    SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.playvideo_pressed);
                                    SlsMessageHolder.this.ivPlayVideo.setOnClickListener(new C26871());
                                } else if (SlsMessageHolder.this.baseMessage.getMessage().getMediaType() == 6) {
                                    SlsMessageHolder.this.ivPlayVideo.setVisibility(8);
                                    SlsMessageHolder.this.gifDrawable = new AnimatedFileDrawable(file, false);
                                    SlsMessageHolder.this.gifDrawable.setParentView(SlsMessageHolder.this.ivMain);
                                    SlsMessageHolder.this.gifDrawable.start();
                                    SlsMessageHolder.this.ivMain.setImageDrawable(SlsMessageHolder.this.gifDrawable);
                                } else if (SlsMessageHolder.this.baseMessage.getMessage().getMediaType() == 8) {
                                    SlsMessageHolder.this.ivPlayVideo.setVisibility(0);
                                    SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.playvideo_pressed);
                                    SlsMessageHolder.this.ivPlayVideo.setOnClickListener(new C26882());
                                } else if (SlsMessageHolder.this.baseMessage.getMessage().getMediaType() == 9) {
                                    SlsMessageHolder.this.musicProgressBar.setVisibility(8);
                                    SlsMessageHolder.this.ivDownloadMusic.setVisibility(0);
                                    SlsMessageHolder.this.ivDownloadMusic.setImageResource(R.drawable.playvideo_pressed);
                                    SlsMessageHolder.this.ivDownloadMusic.setOnClickListener(new C26893());
                                }
                            }
                        }
                    }
                });
            }
        }

        public void fileDidUploaded(String str, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2, long j) {
        }

        public void fileLoadProgressChanged(final String str, float f) {
            if (str.contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                final int i = (int) (100.0f * f);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (!str.contains(String.valueOf(SlsMessageHolder.this.delegateDocId))) {
                            return;
                        }
                        if (SlsMessageHolder.this.baseMessage.getMessage().getMediaType() == 9) {
                            SlsMessageHolder.this.musicProgressBar.setVisibility(0);
                            SlsMessageHolder.this.musicProgressBar.setProgress((float) i);
                            return;
                        }
                        SlsMessageHolder.this.pbImageLoading.setVisibility(0);
                        SlsMessageHolder.this.pbImageLoading.setProgress((float) i);
                        SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                    }
                });
            }
        }

        public void fileUploadProgressChanged(String str, float f, boolean z) {
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$2 */
    class C27002 implements OnClickListener {

        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$2$1 */
        class C26991 implements DialogInterface.OnClickListener {
            C26991() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }

        C27002() {
        }

        public void onClick(View view) {
            if (C3791b.m13917U(SlsMessageHolder.this.getActivity())) {
                Builder builder = new Builder(SlsMessageHolder.this.getActivity());
                builder.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
                CharSequence e = C3791b.m13966e(ApplicationLoader.applicationContext, SlsMessageHolder.this.baseMessage.getMessage().getFreeState());
                if (TextUtils.isEmpty(e)) {
                    e = TtmlNode.ANONYMOUS_REGION_ID;
                }
                if (!TextUtils.isEmpty(e)) {
                    builder.setMessage(e).setCancelable(true).setPositiveButton("گرفتم", new C26991());
                    builder.create().show();
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$5 */
    class C27045 implements OnClickListener {

        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$5$1 */
        class C27031 implements C1032b {
            C27031() {
            }

            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == 1) {
                    SlsMessageHolder.this.msgRecievedAction = 2;
                    SlsMessageHolder.this.loadSingleMessage();
                } else if (menuItem.getItemId() == 2) {
                    try {
                        SlsMessageHolder.this.msgRecievedAction = 4;
                        SlsMessageHolder.this.loadSingleMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }

        C27045() {
        }

        public void onClick(View view) {
            av avVar = new av(SlsMessageHolder.this.getActivity(), view);
            CharSequence string = LocaleController.getString("SaveToMusic", R.string.SaveToMusic);
            if (SlsMessageHolder.this.baseMessage != null && SlsMessageHolder.this.baseMessage.getMessage() != null && SlsMessageHolder.this.baseMessage.getMessage().to_id != null) {
                avVar.m5566a().add(0, 1, 0, Favourite.isFavouriteMessage(Long.valueOf((long) (-Math.abs(SlsMessageHolder.this.baseMessage.getMessage().to_id.channel_id))), (long) SlsMessageHolder.this.baseMessage.getMessage().id) ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites));
                avVar.m5566a().add(0, 2, 1, string);
                avVar.m5568b();
                avVar.m5567a(new C27031());
            }
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$8 */
    class C27078 implements OnClickListener {
        C27078() {
        }

        public void onClick(View view) {
            SlsMessageHolder.this.gotoMediaPagerActivity();
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$9 */
    class C27089 implements Runnable {
        C27089() {
        }

        public void run() {
            SlsMessageHolder.this.ivPlayVideo.setVisibility(8);
            SlsMessageHolder.this.pbImageLoading.setVisibility(0);
            SlsMessageHolder.this.pbImageLoading.startAnimation(SlsMessageHolder.this.rotation);
            SlsMessageHolder.this.pbImageLoading.setProgress(2.0f);
        }
    }

    public SlsMessageHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_item_message_with_image, dynamicAdapter, extraData);
        this.extraData = extraData;
        this.cardView = (CardView) findViewById(R.id.main);
        this.ivMain = (ImageView) findViewById(R.id.ivMain);
        this.ivThumbnail = (ImageView) findViewById(R.id.ivThumb);
        this.vvMain = (VideoView) findViewById(R.id.vvMain);
        this.ivChannelImage = (ImageView) findViewById(R.id.ivChannelImage);
        this.tvMessageText = (TextView) findViewById(R.id.tvMessageText);
        this.tvChannelNameWithImage = (TextView) findViewById(R.id.tvChannelNameWithImage);
        this.tvChannelName = (TextView) findViewById(R.id.tvChannelName);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.tvViews = (TextView) findViewById(R.id.tvViews);
        this.ivPlayVideo = (ImageView) findViewById(R.id.ivPlayVideo);
        this.ivShare = (ImageView) findViewById(R.id.iv_share);
        this.ivShareMessageText = (ImageView) findViewById(R.id.iv_share_text_msg);
        this.rlBottomContainer = findViewById(R.id.rl_bottom_container);
        this.iv_more = findViewById(R.id.iv_more);
        this.pbImageLoading = (CircularProgressBar) findViewById(R.id.pb_image_loading);
        this.musicProgressBar = (CircularProgressBar) findViewById(R.id.pb_music);
        this.ivPlayStreamVideo = (ImageView) findViewById(R.id.ivPlayStreamVideo);
        this.ivDownloadMusic = (ImageView) findViewById(R.id.iv_download_music);
        if (!C3791b.m13910N(getActivity())) {
            this.ivPlayStreamVideo.setVisibility(8);
        }
        this.rlFileContainer = findViewById(R.id.rl_audio_container);
        this.tvFileName = (TextView) findViewById(R.id.tv_file_name);
        this.tvFileDesc = (TextView) findViewById(R.id.tv_file_provider);
        this.rlBottomContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        this.tvChannelName.setTextColor(Theme.getColor(Theme.key_chats_name));
        this.tvMessageText.setTextColor(Theme.getColor(Theme.key_chat_messageTextIn));
        this.tvChannelNameWithImage.setTextColor(Theme.getColor(Theme.key_chats_name));
        this.pbImageLoading.setColor(C0235a.m1075c(getActivity(), R.color.white));
        this.pbImageLoading.setBackgroundColor(C0235a.m1075c(getActivity(), 17170445));
        this.musicProgressBar.setColor(C0235a.m1075c(getActivity(), R.color.white));
        this.musicProgressBar.setBackgroundColor(C0235a.m1075c(getActivity(), 17170445));
        this.freeView = findViewById(R.id.freeView);
        this.freeView.setVisibility(0);
        this.tvTrafficPriceStatus = (TextView) this.freeView.findViewById(R.id.freeViewLabel);
        try {
            this.tlMsg = TelegramMessage.getTelegramMessage(getActivity());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        this.rotation = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        this.rotation.setRepeatCount(-1);
        this.rotation1 = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        this.rotation1.setRepeatCount(-1);
        this.loaderDelegate = new C26981();
        FileLoader.getInstance().addDelegate(this.loaderDelegate);
        this.baseFragment = new BaseFragment();
        this.baseFragment.setParentLayout(((LaunchActivity) getActivity()).getActionBarLayout());
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "animUpdate", new float[]{BitmapDescriptorFactory.HUE_RED, 360.0f});
        ofFloat.setDuration(3000);
        ofFloat.setRepeatCount(-1);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.start();
    }

    public static void addToChannel(int i, String str) {
        addToChannel(i, str, 0);
    }

    public static void addToChannel(int i, final String str, final long j) {
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(i));
        if (chat == null) {
            MessagesController.loadChannelInfoByUsername(str, new C2497d() {
                public void onResult(Object obj, int i) {
                    Chat chat = (Chat) ((ArrayList) obj).get(0);
                    if (chat != null && ChatObject.isNotInChat(chat)) {
                        MessagesController.getInstance().addUserToChat(chat.id, UserConfig.getCurrentUser(), null, 0, null, null);
                        if (j != 0) {
                            C3791b.m13931a(ApplicationLoader.applicationContext, str, System.currentTimeMillis() + j);
                        }
                    }
                }
            });
        } else if (ChatObject.isNotInChat(chat)) {
            MessagesController.getInstance().addUserToChat(chat.id, UserConfig.getCurrentUser(), null, 0, null, null);
        }
    }

    private int getPositionOfObject(SlsBaseMessage slsBaseMessage) {
        List a = C2671d.m12537a(this.extraData.getPoolId());
        if (a != null) {
            for (int i = 0; i < a.size(); i++) {
                if (((SlsBaseMessage) a.get(i)).getRow() == slsBaseMessage.getRow()) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void gotoMediaPagerActivity() {
        Log.d("LEE", "poolID:" + this.extraData.getPoolId());
        Intent intent = new Intent(getActivity(), SlsMediaViewActivity.class);
        intent.putExtra("EXTRA_TAG_ID", this.extraData.getTagId());
        intent.putExtra("EXTRA_POOL_ID", this.extraData.getPoolId());
        intent.putExtra("EXTRA_CURRENT_POSITION", getPositionOfObject(this.baseMessage));
        getActivity().startActivity(intent);
    }

    private void loadSingleMessage() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.baseMessage.getMessage().to_id.channel_id));
        if (chat == null) {
            MessagesController.loadChannelInfoByUsername(this.baseMessage.getMessage().getUsername(), new C2497d() {
                public void onResult(Object obj, int i) {
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(SlsMessageHolder.this.baseMessage.getMessage().to_id.channel_id));
                    if (chat != null) {
                        MessagesController.getInstance().loadPeerSettings(UserConfig.getCurrentUser(), chat);
                        if (SlsMessageHolder.this.baseMessage != null && SlsMessageHolder.this.baseMessage.getMessage() != null && SlsMessageHolder.this.baseMessage.getMessage().to_id != null) {
                            MessagesController.getInstance().loadMessages((long) (-SlsMessageHolder.this.baseMessage.getMessage().to_id.channel_id), 1, SlsMessageHolder.this.baseMessage.getMessage().id + 1, 0, true, 0, 0, 3, 0, true, 0);
                        }
                    }
                }
            });
            return;
        }
        MessagesController.getInstance().loadPeerSettings(UserConfig.getCurrentUser(), chat);
        if (this.baseMessage != null && this.baseMessage.getMessage() != null && this.baseMessage.getMessage().to_id != null) {
            MessagesController.getInstance().loadMessages((long) (-this.baseMessage.getMessage().to_id.channel_id), 1, this.baseMessage.getMessage().id + 1, 0, true, 0, 0, 3, 0, true, 0);
        }
    }

    public synchronized void didReceivedNotification(int i, Object... objArr) {
        int i2 = 2;
        synchronized (this) {
            if (i == NotificationCenter.messagesDidLoaded) {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
                ArrayList arrayList = (ArrayList) objArr[2];
                Log.d("LEE", "MessageCount:" + arrayList.size());
                synchronized (this) {
                    if (this.showingDialog) {
                    } else {
                        if (arrayList != null) {
                            if (arrayList.size() > 0) {
                                switch (this.msgRecievedAction) {
                                    case 1:
                                        this.showingDialog = true;
                                        Dialog shareAlert = new ShareAlert(getActivity(), arrayList, "alireza", true, TtmlNode.ANONYMOUS_REGION_ID, false);
                                        this.baseFragment.showDialog(shareAlert);
                                        shareAlert.setOnDismissListener(new OnDismissListener() {
                                            public void onDismiss(DialogInterface dialogInterface) {
                                                SlsMessageHolder.this.showingDialog = false;
                                            }
                                        });
                                        shareAlert.setOnCancelListener(new OnCancelListener() {
                                            public void onCancel(DialogInterface dialogInterface) {
                                                SlsMessageHolder.this.showingDialog = false;
                                            }
                                        });
                                        break;
                                    case 2:
                                        long j = (long) (-Math.abs(this.baseMessage.getMessage().to_id.channel_id));
                                        long j2 = (long) this.baseMessage.getMessage().id;
                                        if (!Favourite.isFavouriteMessage(Long.valueOf(j), j2)) {
                                            Favourite.addFavouriteMessage(Long.valueOf(j), j2, (MessageObject) arrayList.get(0));
                                            break;
                                        } else {
                                            Favourite.deleteFavouriteMessage(Long.valueOf(j), Long.valueOf(j2));
                                            break;
                                        }
                                    case 3:
                                        MediaController.getInstance().playMessage((MessageObject) arrayList.get(0));
                                        break;
                                    case 4:
                                        try {
                                            if (VERSION.SDK_INT >= 23 && getActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                                                getActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                                                break;
                                            }
                                            String fileName;
                                            CharSequence documentFileName = FileLoader.getDocumentFileName(((MessageObject) arrayList.get(0)).getDocument());
                                            if (TextUtils.isEmpty(documentFileName)) {
                                                fileName = ((MessageObject) arrayList.get(0)).getFileName();
                                            } else {
                                                CharSequence charSequence = documentFileName;
                                            }
                                            String str = ((MessageObject) arrayList.get(0)).messageOwner.attachPath;
                                            if (!(str == null || str.length() <= 0 || new File(str).exists())) {
                                                str = null;
                                            }
                                            String file = (str == null || str.length() == 0) ? FileLoader.getPathToMessage(((MessageObject) arrayList.get(0)).messageOwner).toString() : str;
                                            Context activity = getActivity();
                                            if (((MessageObject) arrayList.get(0)).isMusic()) {
                                                i2 = 3;
                                            }
                                            MediaController.saveFile(file, activity, i2, fileName, ((MessageObject) arrayList.get(0)).getDocument() != null ? ((MessageObject) arrayList.get(0)).getDocument().mime_type : TtmlNode.ANONYMOUS_REGION_ID);
                                            break;
                                        } catch (Exception e) {
                                            break;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void fillTlMsg(SlsBaseMessage slsBaseMessage) {
        this.tlMsg.mediaType = slsBaseMessage.getMessage().getMediaType();
        this.tlMsg.castToMedia();
        this.tlMsg.message.media.document.access_hash = -1;
        this.tlMsg.message.media.document.date = ((int) System.currentTimeMillis()) / 1000;
        this.tlMsg.message.media.document.dc_id = slsBaseMessage.getMessage().media.document.dc_id;
        this.tlMsg.message.media.document.id = slsBaseMessage.getMessage().media.document.id;
        this.tlMsg.message.media.document.mime_type = slsBaseMessage.getMessage().media.document.mime_type;
        this.tlMsg.message.media.document.size = slsBaseMessage.getMessage().media.document.size;
    }

    public void itemClicked(ObjBase objBase) {
        Log.d("LEE", "AAAAALLLIIIIII" + objBase.getPosition() + "  id: " + this.baseMessage.getMessage().id);
        try {
            C2879f.m13356a((long) Math.abs(this.baseMessage.getMessage().to_id.channel_id), (long) this.baseMessage.getMessage().id, getActivity(), this.baseMessage.getMessage().getUsername());
        } catch (Exception e) {
        }
    }

    public void onBind(final ObjBase objBase) {
        this.baseMessage = (SlsBaseMessage) objBase;
        this.rlFileContainer.setVisibility(8);
        if (C3791b.m13914R(getActivity()) && !TextUtils.isEmpty(this.baseMessage.getMessage().getLabel()) && SlsBaseMessage.isMediaAvailable(objBase)) {
            this.freeView.setVisibility(0);
            if (this.baseMessage.getMessage().getLabel().contentEquals("showicon")) {
                this.freeView.setBackgroundResource(R.drawable.gift_grey);
                this.freeView.getLayoutParams().width = C3792d.m14073a(35.0f, getActivity());
                this.freeView.getLayoutParams().height = C3792d.m14073a(35.0f, getActivity());
            } else {
                this.freeView.setBackgroundResource(R.drawable.semi_curve_bg);
                this.tvTrafficPriceStatus.setText(this.baseMessage.getMessage().getLabel() + TtmlNode.ANONYMOUS_REGION_ID);
            }
            this.freeView.setOnClickListener(new C27002());
        } else {
            this.freeView.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.baseMessage.getMessage().getChannel_image())) {
            this.ivChannelImage.setImageDrawable(C0235a.m1066a(getActivity(), (int) R.drawable.ic_channel));
        } else {
            C2868b.m13329a(this.ivChannelImage, this.baseMessage.getMessage().getChannel_image());
        }
        if (this.baseMessage.getMessage().getMediaType() == 1) {
            this.tvChannelName.setVisibility(8);
            this.tvChannelNameWithImage.setVisibility(0);
        } else {
            this.tvChannelName.setVisibility(0);
            this.tvChannelNameWithImage.setVisibility(8);
        }
        this.tvChannelNameWithImage.setText(this.baseMessage.getMessage().getChannel_name());
        this.tvChannelName.setText(this.baseMessage.getMessage().getChannel_name());
        if (!(this.baseMessage.getMessage().date == 0 || TextUtils.isEmpty(C3792d.m14077a((long) this.baseMessage.getMessage().date)))) {
            try {
                this.tvDate.setText(C3792d.m14077a((long) this.baseMessage.getMessage().date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.tvViews.setText(String.format("%s", new Object[]{LocaleController.formatShortNumber(Math.max(1, this.baseMessage.getMessage().views), null)}));
        this.tvMessageText.setText(this.baseMessage.getMessage().message);
        this.ivMain.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SlsBaseMessage.isMediaAvailable(objBase)) {
                    SlsMessageHolder.this.gotoMediaPagerActivity();
                } else {
                    SlsMessageHolder.this.itemClicked(objBase);
                }
            }
        });
        this.ivShare.setOnClickListener(this);
        this.ivShareMessageText.setOnClickListener(this);
        this.rlBottomContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlsMessageHolder.this.itemClicked(objBase);
            }
        });
        this.iv_more.setOnClickListener(new C27045());
        this.ivShare.setVisibility(0);
        this.ivShareMessageText.setVisibility(8);
        this.ivPlayStreamVideo.setVisibility(8);
        this.ivPlayVideo.setVisibility(8);
        DocAvailableInfo docAvailableInfo;
        File file;
        switch (this.baseMessage.getMessage().getMediaType()) {
            case 1:
                this.pbImageLoading.setVisibility(8);
                this.tvMessageText.setMaxLines(20);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.ivMain.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SlsMessageHolder.this.itemClicked(objBase);
                    }
                });
                this.freeView.setVisibility(8);
                this.ivShareMessageText.setVisibility(0);
                this.ivShare.setVisibility(8);
                return;
            case 2:
                this.ivThumbnail.setImageDrawable(null);
                this.ivMain.setImageDrawable(null);
                C2868b.m13330a(this.ivMain, SlsBaseMessage.isMediaAvailable(objBase) ? this.baseMessage.getMessage().getFileUrl() : TtmlNode.ANONYMOUS_REGION_ID, this.pbImageLoading);
                this.tvMessageText.setMaxLines(1);
                this.ivMain.setVisibility(0);
                this.ivThumbnail.setVisibility(0);
                this.ivPlayVideo.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.tvMessageText.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SlsMessageHolder.this.itemClicked(objBase);
                    }
                });
                return;
            case 6:
            case 8:
                this.delegateDocId = this.baseMessage.getMessage().media.document.id;
                boolean z = VERSION.SDK_INT > 19;
                this.ivThumbnail.setImageDrawable(null);
                this.ivMain.setImageDrawable(null);
                C2868b.m13331b(this.ivThumbnail, SlsBaseMessage.isMediaAvailable(objBase) ? this.baseMessage.getMessage().getThumbnailUrl() : TtmlNode.ANONYMOUS_REGION_ID, this.pbImageLoading);
                fillTlMsg(this.baseMessage);
                ArrayList arrayList = new ArrayList();
                docAvailableInfo = new DocAvailableInfo(this.tlMsg.getMessage().media.document.id, 0, 0, (long) this.tlMsg.getMessage().id, false);
                docAvailableInfo.localUrl = this.baseMessage.getMessage().getFileUrl();
                docAvailableInfo.exists = true;
                docAvailableInfo.setSourceType(1);
                arrayList.add(docAvailableInfo);
                C5319b.a(arrayList);
                this.document = this.tlMsg.getMessage().media.document;
                file = new File(FileLoader.getInstance().getDirectory(this.baseMessage.getMessage().getMediaType() == 8 ? 2 : 3).getAbsolutePath() + File.separator + FileLoader.getAttachFileName(this.tlMsg.getMessage().media.document));
                if (file.exists() && file.length() == ((long) this.tlMsg.getMessage().media.document.size)) {
                    this.loaderDelegate.fileDidLoaded(file.getAbsolutePath(), file, -22);
                } else {
                    if (this.baseMessage.getMessage().getMediaType() == 8) {
                        if (!SlsBaseMessage.isMediaAvailable(objBase)) {
                            this.ivPlayStreamVideo.setVisibility(8);
                        }
                        this.ivPlayStreamVideo.setOnClickListener(new C27078());
                    }
                    if (!z || this.baseMessage.getMessage().getMediaType() != 6) {
                        Log.d("LEE", "IsAvailable:" + SlsBaseMessage.isMediaAvailable(objBase));
                        if (SlsBaseMessage.isMediaAvailable(objBase)) {
                            this.pbImageLoading.setVisibility(8);
                            this.pbImageLoading.clearAnimation();
                            this.ivPlayVideo.setVisibility(0);
                            this.ivPlayVideo.setImageResource(R.drawable.load_big);
                            this.ivPlayVideo.setOnClickListener(new OnClickListener() {
                                public void onClick(View view) {
                                    Log.d("LEE", "OnClick:ivPlayVideo");
                                    SlsMessageHolder.this.fillTlMsg(SlsMessageHolder.this.baseMessage);
                                    SlsMessageHolder.this.document = SlsMessageHolder.this.tlMsg.getMessage().media.document;
                                    if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(SlsMessageHolder.this.document))) {
                                        FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.this.document);
                                        return;
                                    }
                                    FileLoader.getInstance().loadFile(SlsMessageHolder.this.document, true, 0);
                                    SlsMessageHolder.this.pbImageLoading.setVisibility(0);
                                    SlsMessageHolder.this.pbImageLoading.setProgress(2.0f);
                                    SlsMessageHolder.this.pbImageLoading.startAnimation(SlsMessageHolder.this.rotation);
                                    SlsMessageHolder.this.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                                }
                            });
                        }
                    } else if (SlsBaseMessage.isMediaAvailable(objBase)) {
                        FileLoader.getInstance().loadFile(this.tlMsg.getMessage().media.document, true, 0);
                        this.pbImageLoading.post(new C27089());
                    }
                }
                if (FileLoader.getInstance().isLoadingFile(FileLoader.getAttachFileName(this.document))) {
                    this.pbImageLoading.clearAnimation();
                    this.pbImageLoading.setVisibility(0);
                    this.pbImageLoading.setProgress(2.0f);
                    this.pbImageLoading.startAnimation(this.rotation);
                    this.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                }
                this.tvMessageText.setMaxLines(1);
                this.ivMain.setVisibility(0);
                this.vvMain.setVisibility(8);
                return;
            case 9:
                this.ivShare.setVisibility(8);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.delegateDocId = this.baseMessage.getMessage().media.document.id;
                this.ivMain.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SlsMessageHolder.this.itemClicked(objBase);
                    }
                });
                this.freeView.setVisibility(8);
                if (!(this.baseMessage.getMessage().media == null || this.baseMessage.getMessage().media.document == null || this.baseMessage.getMessage().media.document.attributes == null || this.baseMessage.getMessage().media.document.attributes.size() <= 0)) {
                    this.tvFileName.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).getDocumentTitle());
                }
                this.tvFileDesc.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).performer);
                this.rlFileContainer.setVisibility(0);
                fillTlMsg(this.baseMessage);
                this.tlMsg.getMessage().media.document.mime_type = this.baseMessage.getMessage().media.document.mime_type;
                ArrayList arrayList2 = new ArrayList();
                docAvailableInfo = new DocAvailableInfo(this.tlMsg.getMessage().media.document.id, 0, 0, (long) this.tlMsg.getMessage().id, false);
                docAvailableInfo.localUrl = this.baseMessage.getMessage().getFileUrl();
                docAvailableInfo.exists = true;
                docAvailableInfo.setSourceType(1);
                arrayList2.add(docAvailableInfo);
                C5319b.a(arrayList2);
                this.document = this.tlMsg.getMessage().media.document;
                file = new File((this.baseMessage.getMessage().media.document.mime_type.contentEquals("audio/ogg") ? FileLoader.getInstance().getDirectory(1) : FileLoader.getInstance().getDirectory(3)).getAbsolutePath() + File.separator + FileLoader.slsGetAttachFileName(this.document, null));
                if (file.exists() && file.length() == ((long) this.tlMsg.getMessage().media.document.size)) {
                    this.loaderDelegate.fileDidLoaded(file.getAbsolutePath(), file, -22);
                    this.ivDownloadMusic.setVisibility(0);
                    this.ivDownloadMusic.setImageResource(R.drawable.playvideo_pressed);
                } else {
                    this.ivDownloadMusic.setVisibility(0);
                    this.ivDownloadMusic.setImageResource(R.drawable.load_big);
                }
                if (FileLoader.getInstance().isLoadingFile(FileLoader.slsGetAttachFileName(this.document, null))) {
                    this.musicProgressBar.clearAnimation();
                    this.musicProgressBar.setVisibility(0);
                    this.musicProgressBar.setProgress(2.0f);
                    this.musicProgressBar.startAnimation(this.rotation1);
                    this.musicProgressBar.setAnimation(this.rotation1);
                    this.rotation1.startNow();
                    this.musicProgressBar.invalidate();
                    this.ivPlayVideo.setImageResource(R.drawable.cancel_big);
                }
                this.ivDownloadMusic.setOnClickListener(new OnClickListener() {

                    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$12$1 */
                    class C26971 implements Runnable {

                        /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$12$1$1 */
                        class C26961 implements Runnable {
                            C26961() {
                            }

                            public void run() {
                                SlsMessageHolder.this.musicProgressBar.setVisibility(0);
                                SlsMessageHolder.this.musicProgressBar.setProgress(2.0f);
                                SlsMessageHolder.this.musicProgressBar.requestLayout();
                                SlsMessageHolder.this.musicProgressBar.invalidate();
                                SlsMessageHolder.this.musicProgressBar.startAnimation(SlsMessageHolder.this.rotation1);
                                SlsMessageHolder.this.musicProgressBar.setAnimation(SlsMessageHolder.this.rotation1);
                                SlsMessageHolder.this.rotation1.startNow();
                                SlsMessageHolder.this.musicProgressBar.invalidate();
                                SlsMessageHolder.this.msgRecievedAction = 3;
                                SlsMessageHolder.this.loadSingleMessage();
                            }
                        }

                        C26971() {
                        }

                        public void run() {
                            SlsMessageHolder.this.musicProgressBar.post(new C26961());
                        }
                    }

                    public void onClick(View view) {
                        if (FileLoader.getInstance().isLoadingFile(FileLoader.slsGetAttachFileName(SlsMessageHolder.this.document, null))) {
                            Log.d("alireza", "alireza file is loading");
                            FileLoader.getInstance().cancelLoadFile(SlsMessageHolder.this.document);
                            MediaController.getInstance().cleanupPlayer(true, true);
                        } else if (file.exists() && file.length() == ((long) SlsMessageHolder.this.tlMsg.getMessage().media.document.size)) {
                            SlsMessageHolder.this.msgRecievedAction = 3;
                            SlsMessageHolder.this.loadSingleMessage();
                        } else {
                            AndroidUtilities.runOnUIThread(new C26971());
                        }
                    }
                });
                return;
            case 10:
                this.ivShare.setVisibility(8);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.ivMain.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SlsMessageHolder.this.itemClicked(objBase);
                    }
                });
                this.freeView.setVisibility(8);
                if (!(this.baseMessage.getMessage().media == null || this.baseMessage.getMessage().media.document == null || this.baseMessage.getMessage().media.document.attributes == null || this.baseMessage.getMessage().media.document.attributes.size() <= 0)) {
                    this.tvFileName.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).getDocumentTitle());
                }
                this.tvFileDesc.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).performer);
                this.rlFileContainer.setVisibility(0);
                return;
            default:
                return;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
            case R.id.iv_share_text_msg:
                this.msgRecievedAction = 1;
                loadSingleMessage();
                return;
            default:
                return;
        }
    }

    public void onRecycled() {
        try {
            this.rotation.cancel();
            this.rotation1.cancel();
        } catch (Exception e) {
        }
        try {
            if (this.baseMessage.getMessage().getMediaType() == 6 || this.baseMessage.getMessage().getMediaType() == 8) {
                FileLoader.getInstance().cancelLoadFile(this.baseMessage.getMessage().media.document);
                this.ivMain.setImageDrawable(null);
                if (this.gifDrawable != null) {
                    this.gifDrawable.stop();
                    this.gifDrawable.recycle();
                    this.gifDrawable = null;
                }
            }
        } catch (Exception e2) {
        }
        super.onRecycled();
    }

    void setAnimUpdate(float f) {
        try {
            ((CircularProgressBarAutoRotate) this.pbImageLoading).setStartAngle((int) f);
        } catch (Exception e) {
        }
        this.pbImageLoading.invalidate();
    }
}
