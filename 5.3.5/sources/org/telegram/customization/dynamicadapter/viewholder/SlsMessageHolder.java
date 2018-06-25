package org.telegram.customization.dynamicadapter.viewholder;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SlsMediaViewActivity;
import org.telegram.customization.DataPool.PostPoolMulti;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.dynamicadapter.data.TelegramMessage;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.customization.util.IntentMaker;
import org.telegram.customization.util.view.CircularProgressBarAutoRotate;
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
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AnimatedFileDrawable;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.LaunchActivity;
import utils.FreeDownload;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

@ViewHolderType(model = SlsBaseMessage.class, type = 100)
public class SlsMessageHolder extends HolderBase implements NotificationCenterDelegate, OnClickListener {
    public static final int ACTION_FAVORITE = 2;
    public static final int ACTION_PLAY_MUSIC = 3;
    public static final int ACTION_SAVE_TO_MUSIC = 4;
    public static final int ACTION_SHARE = 1;
    private BaseFragment baseFragment;
    private SlsBaseMessage baseMessage;
    CardView cardView;
    long delegateDocId = -1;
    private TLRPC$Document document;
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

    public SlsMessageHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData mExtraData) {
        super(activity, viewGroup, R.layout.sls_item_message_with_image, adapter, mExtraData);
        this.extraData = mExtraData;
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
        if (AppPreferences.isStreamEnable(getActivity())) {
            this.ivPlayStreamVideo.setVisibility(0);
        } else {
            this.ivPlayStreamVideo.setVisibility(8);
        }
        this.rlFileContainer = findViewById(R.id.rl_audio_container);
        this.tvFileName = (TextView) findViewById(R.id.tv_file_name);
        this.tvFileDesc = (TextView) findViewById(R.id.tv_file_provider);
        this.rlBottomContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        this.tvChannelName.setTextColor(Theme.getColor(Theme.key_chats_name));
        this.tvMessageText.setTextColor(Theme.getColor(Theme.key_chat_messageTextIn));
        this.tvChannelNameWithImage.setTextColor(Theme.getColor(Theme.key_chats_name));
        this.pbImageLoading.setColor(ContextCompat.getColor(getActivity(), R.color.white));
        this.pbImageLoading.setBackgroundColor(ContextCompat.getColor(getActivity(), 17170445));
        this.musicProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.white));
        this.musicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), 17170445));
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
        this.loaderDelegate = new SlsMessageHolder$1(this);
        FileLoader.getInstance().addDelegate(this.loaderDelegate);
        this.baseFragment = new BaseFragment();
        this.baseFragment.setParentLayout(((LaunchActivity) getActivity()).getActionBarLayout());
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animUpdate", new float[]{0.0f, 360.0f});
        animator.setDuration(3000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    void setAnimUpdate(float f) {
        try {
            ((CircularProgressBarAutoRotate) this.pbImageLoading).setStartAngle((int) f);
        } catch (Exception e) {
        }
        this.pbImageLoading.invalidate();
    }

    private void gotoMediaPagerActivity() {
        Log.d("LEE", "poolID:" + this.extraData.getPoolId());
        Intent intent = new Intent(getActivity(), SlsMediaViewActivity.class);
        intent.putExtra("EXTRA_TAG_ID", this.extraData.getTagId());
        intent.putExtra(Constants.EXTRA_POOL_ID, this.extraData.getPoolId());
        intent.putExtra(org.telegram.customization.util.Constants.EXTRA_CURRENT_POSITION, getPositionOfObject(this.baseMessage));
        getActivity().startActivity(intent);
    }

    void fillTlMsg(SlsBaseMessage baseMessage) {
        this.tlMsg.mediaType = baseMessage.getMessage().getMediaType();
        this.tlMsg.castToMedia();
        this.tlMsg.message.media.document.access_hash = -1;
        this.tlMsg.message.media.document.date = ((int) System.currentTimeMillis()) / 1000;
        this.tlMsg.message.media.document.dc_id = baseMessage.getMessage().media.document.dc_id;
        this.tlMsg.message.media.document.id = baseMessage.getMessage().media.document.id;
        this.tlMsg.message.media.document.mime_type = baseMessage.getMessage().media.document.mime_type;
        this.tlMsg.message.media.document.size = baseMessage.getMessage().media.document.size;
    }

    public void onBind(ObjBase obj) {
        this.baseMessage = (SlsBaseMessage) obj;
        this.rlFileContainer.setVisibility(8);
        if (AppPreferences.isShowFreeIconEnable(getActivity()) && !TextUtils.isEmpty(this.baseMessage.getMessage().getLabel()) && SlsBaseMessage.isMediaAvailable(obj)) {
            this.freeView.setVisibility(0);
            if (this.baseMessage.getMessage().getLabel().contentEquals("showicon")) {
                this.freeView.setBackgroundResource(R.drawable.gift_grey);
                this.freeView.getLayoutParams().width = Utilities.convertDpToPixel(35.0f, getActivity());
                this.freeView.getLayoutParams().height = Utilities.convertDpToPixel(35.0f, getActivity());
            } else {
                this.freeView.setBackgroundResource(R.drawable.semi_curve_bg);
                this.tvTrafficPriceStatus.setText(this.baseMessage.getMessage().getLabel() + "");
            }
            this.freeView.setOnClickListener(new SlsMessageHolder$2(this));
        } else {
            this.freeView.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.baseMessage.getMessage().getChannel_image())) {
            this.ivChannelImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_channel));
        } else {
            AppImageLoader.loadImage(this.ivChannelImage, this.baseMessage.getMessage().getChannel_image());
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
        if (!(this.baseMessage.getMessage().date == 0 || TextUtils.isEmpty(Utilities.getShamsiDate((long) this.baseMessage.getMessage().date)))) {
            try {
                this.tvDate.setText(Utilities.getShamsiDate((long) this.baseMessage.getMessage().date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.tvViews.setText(String.format("%s", new Object[]{LocaleController.formatShortNumber(Math.max(1, this.baseMessage.getMessage().views), null)}));
        this.tvMessageText.setText(this.baseMessage.getMessage().message);
        this.ivMain.setOnClickListener(new SlsMessageHolder$3(this, obj));
        this.ivShare.setOnClickListener(this);
        this.ivShareMessageText.setOnClickListener(this);
        this.rlBottomContainer.setOnClickListener(new SlsMessageHolder$4(this, obj));
        this.iv_more.setOnClickListener(new SlsMessageHolder$5(this));
        this.ivShare.setVisibility(0);
        this.ivShareMessageText.setVisibility(8);
        this.ivPlayStreamVideo.setVisibility(8);
        this.ivPlayVideo.setVisibility(8);
        switch (this.baseMessage.getMessage().getMediaType()) {
            case 1:
                this.pbImageLoading.setVisibility(8);
                this.tvMessageText.setMaxLines(20);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.ivMain.setOnClickListener(new SlsMessageHolder$6(this, obj));
                this.freeView.setVisibility(8);
                this.ivShareMessageText.setVisibility(0);
                this.ivShare.setVisibility(8);
                return;
            case 2:
                this.ivThumbnail.setImageDrawable(null);
                this.ivMain.setImageDrawable(null);
                AppImageLoader.loadImageLikeTelegram(this.ivMain, SlsBaseMessage.isMediaAvailable(obj) ? this.baseMessage.getMessage().getFileUrl() : "", this.pbImageLoading);
                this.tvMessageText.setMaxLines(1);
                this.ivMain.setVisibility(0);
                this.ivThumbnail.setVisibility(0);
                this.ivPlayVideo.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.tvMessageText.setOnClickListener(new SlsMessageHolder$7(this, obj));
                return;
            case 6:
            case 8:
                this.delegateDocId = this.baseMessage.getMessage().media.document.id;
                boolean hasALotOfRam = VERSION.SDK_INT > 19;
                this.ivThumbnail.setImageDrawable(null);
                this.ivMain.setImageDrawable(null);
                AppImageLoader.loadImageLikeTelegramForVideo(this.ivThumbnail, SlsBaseMessage.isMediaAvailable(obj) ? this.baseMessage.getMessage().getThumbnailUrl() : "", this.pbImageLoading);
                fillTlMsg(this.baseMessage);
                ArrayList<DocAvailableInfo> infos = new ArrayList();
                DocAvailableInfo info = new DocAvailableInfo(this.tlMsg.getMessage().media.document.id, 0, 0, (long) this.tlMsg.getMessage().id, false);
                info.localUrl = this.baseMessage.getMessage().getFileUrl();
                info.exists = true;
                info.setSourceType(1);
                infos.add(info);
                FreeDownload.addDocs(infos);
                this.document = this.tlMsg.getMessage().media.document;
                File dlFile = new File(FileLoader.getInstance().getDirectory(this.baseMessage.getMessage().getMediaType() == 8 ? 2 : 3).getAbsolutePath() + File.separator + FileLoader.getAttachFileName(this.tlMsg.getMessage().media.document));
                if (dlFile.exists() && dlFile.length() == ((long) this.tlMsg.getMessage().media.document.size)) {
                    this.loaderDelegate.fileDidLoaded(dlFile.getAbsolutePath(), dlFile, -22);
                } else {
                    if (this.baseMessage.getMessage().getMediaType() == 8) {
                        if (SlsBaseMessage.isMediaAvailable(obj)) {
                            this.ivPlayStreamVideo.setVisibility(0);
                        } else {
                            this.ivPlayStreamVideo.setVisibility(8);
                        }
                        this.ivPlayStreamVideo.setOnClickListener(new SlsMessageHolder$8(this));
                    }
                    if (!hasALotOfRam || this.baseMessage.getMessage().getMediaType() != 6) {
                        Log.d("LEE", "IsAvailable:" + SlsBaseMessage.isMediaAvailable(obj));
                        if (SlsBaseMessage.isMediaAvailable(obj)) {
                            this.pbImageLoading.setVisibility(8);
                            this.pbImageLoading.clearAnimation();
                            this.ivPlayVideo.setVisibility(0);
                            this.ivPlayVideo.setImageResource(R.drawable.load_big);
                            this.ivPlayVideo.setOnClickListener(new SlsMessageHolder$10(this));
                        }
                    } else if (SlsBaseMessage.isMediaAvailable(obj)) {
                        FileLoader.getInstance().loadFile(this.tlMsg.getMessage().media.document, true, 0);
                        this.pbImageLoading.post(new SlsMessageHolder$9(this));
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
                File fileDirectory1;
                this.ivShare.setVisibility(8);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.delegateDocId = this.baseMessage.getMessage().media.document.id;
                this.ivMain.setOnClickListener(new SlsMessageHolder$11(this, obj));
                this.freeView.setVisibility(8);
                if (!(this.baseMessage.getMessage().media == null || this.baseMessage.getMessage().media.document == null || this.baseMessage.getMessage().media.document.attributes == null || this.baseMessage.getMessage().media.document.attributes.size() <= 0)) {
                    this.tvFileName.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).getDocumentTitle());
                }
                this.tvFileDesc.setText(((DocumentAttribute) this.baseMessage.getMessage().media.document.attributes.get(0)).performer);
                this.rlFileContainer.setVisibility(0);
                fillTlMsg(this.baseMessage);
                this.tlMsg.getMessage().media.document.mime_type = this.baseMessage.getMessage().media.document.mime_type;
                ArrayList<DocAvailableInfo> infos1 = new ArrayList();
                DocAvailableInfo info1 = new DocAvailableInfo(this.tlMsg.getMessage().media.document.id, 0, 0, (long) this.tlMsg.getMessage().id, false);
                info1.localUrl = this.baseMessage.getMessage().getFileUrl();
                info1.exists = true;
                info1.setSourceType(1);
                infos1.add(info1);
                FreeDownload.addDocs(infos1);
                this.document = this.tlMsg.getMessage().media.document;
                if (this.baseMessage.getMessage().media.document.mime_type.contentEquals("audio/ogg")) {
                    fileDirectory1 = FileLoader.getInstance().getDirectory(1);
                } else {
                    fileDirectory1 = FileLoader.getInstance().getDirectory(3);
                }
                File dlFile1 = new File(fileDirectory1.getAbsolutePath() + File.separator + FileLoader.slsGetAttachFileName(this.document, null));
                if (dlFile1.exists() && dlFile1.length() == ((long) this.tlMsg.getMessage().media.document.size)) {
                    this.loaderDelegate.fileDidLoaded(dlFile1.getAbsolutePath(), dlFile1, -22);
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
                this.ivDownloadMusic.setOnClickListener(new SlsMessageHolder$12(this, dlFile1));
                return;
            case 10:
                this.ivShare.setVisibility(8);
                this.ivMain.setVisibility(8);
                this.ivThumbnail.setVisibility(8);
                this.vvMain.setVisibility(8);
                this.ivPlayVideo.setVisibility(8);
                this.ivMain.setOnClickListener(new SlsMessageHolder$13(this, obj));
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

    private int getPositionOfObject(SlsBaseMessage obj) {
        List<SlsBaseMessage> messages = PostPoolMulti.getAllNews(this.extraData.getPoolId());
        if (messages != null) {
            for (int i = 0; i < messages.size(); i++) {
                if (((SlsBaseMessage) messages.get(i)).getRow() == obj.getRow()) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void itemClicked(ObjBase obj) {
        Log.d("LEE", "AAAAALLLIIIIII" + obj.getPosition() + "  id: " + this.baseMessage.getMessage().id);
        try {
            IntentMaker.goToChannel((long) Math.abs(this.baseMessage.getMessage().to_id.channel_id), (long) this.baseMessage.getMessage().id, getActivity(), this.baseMessage.getMessage().getUsername());
        } catch (Exception e) {
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

    public synchronized void didReceivedNotification(int iid, Object... args) {
        if (iid == NotificationCenter.messagesDidLoaded) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagesDidLoaded);
            ArrayList<MessageObject> messArr = args[2];
            Log.d("LEE", "MessageCount:" + messArr.size());
            synchronized (this) {
                if (!this.showingDialog) {
                    if (messArr != null) {
                        if (messArr.size() > 0) {
                            switch (this.msgRecievedAction) {
                                case 1:
                                    this.showingDialog = true;
                                    ShareAlert visibleDialog = new ShareAlert(getActivity(), messArr, "alireza", true, "", false);
                                    this.baseFragment.showDialog(visibleDialog);
                                    visibleDialog.setOnDismissListener(new SlsMessageHolder$14(this));
                                    visibleDialog.setOnCancelListener(new SlsMessageHolder$15(this));
                                    break;
                                case 2:
                                    long dialogId = (long) (-Math.abs(this.baseMessage.getMessage().to_id.channel_id));
                                    long msgId = (long) this.baseMessage.getMessage().id;
                                    if (!Favourite.isFavouriteMessage(Long.valueOf(dialogId), msgId)) {
                                        Favourite.addFavouriteMessage(Long.valueOf(dialogId), msgId, (MessageObject) messArr.get(0));
                                        break;
                                    } else {
                                        Favourite.deleteFavouriteMessage(Long.valueOf(dialogId), Long.valueOf(msgId));
                                        break;
                                    }
                                case 3:
                                    MediaController.getInstance().playMessage((MessageObject) messArr.get(0));
                                    break;
                                case 4:
                                    try {
                                        if (VERSION.SDK_INT >= 23 && getActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                                            getActivity().requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 4);
                                            break;
                                        }
                                        int i;
                                        String str;
                                        String fileName = FileLoader.getDocumentFileName(((MessageObject) messArr.get(0)).getDocument());
                                        if (TextUtils.isEmpty(fileName)) {
                                            fileName = ((MessageObject) messArr.get(0)).getFileName();
                                        }
                                        String path = ((MessageObject) messArr.get(0)).messageOwner.attachPath;
                                        if (!(path == null || path.length() <= 0 || new File(path).exists())) {
                                            path = null;
                                        }
                                        if (path == null || path.length() == 0) {
                                            path = FileLoader.getPathToMessage(((MessageObject) messArr.get(0)).messageOwner).toString();
                                        }
                                        Context activity = getActivity();
                                        if (((MessageObject) messArr.get(0)).isMusic()) {
                                            i = 3;
                                        } else {
                                            i = 2;
                                        }
                                        if (((MessageObject) messArr.get(0)).getDocument() != null) {
                                            str = ((MessageObject) messArr.get(0)).getDocument().mime_type;
                                        } else {
                                            str = "";
                                        }
                                        MediaController.saveFile(path, activity, i, fileName, str);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
            case R.id.iv_share_text_msg:
                this.msgRecievedAction = 1;
                loadSingleMessage();
                return;
            default:
                return;
        }
    }

    private void loadSingleMessage() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagesDidLoaded);
        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.baseMessage.getMessage().to_id.channel_id));
        if (currentChat == null) {
            MessagesController.loadChannelInfoByUsername(this.baseMessage.getMessage().getUsername(), new SlsMessageHolder$16(this));
            return;
        }
        MessagesController.getInstance().loadPeerSettings(UserConfig.getCurrentUser(), currentChat);
        if (this.baseMessage != null && this.baseMessage.getMessage() != null && this.baseMessage.getMessage().to_id != null) {
            MessagesController.getInstance().loadMessages((long) (-this.baseMessage.getMessage().to_id.channel_id), 1, this.baseMessage.getMessage().id + 1, 0, true, 0, 0, 3, 0, true, 0);
        }
    }

    public static void addToChannel(int id, String username) {
        addToChannel(id, username, 0);
    }

    public static void addToChannel(int id, String username, long expireAfter) {
        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(id));
        if (currentChat == null) {
            MessagesController.loadChannelInfoByUsername(username, new SlsMessageHolder$17(expireAfter, username));
        } else if (ChatObject.isNotInChat(currentChat)) {
            MessagesController.getInstance().addUserToChat(currentChat.id, UserConfig.getCurrentUser(), null, 0, null, null);
        }
    }
}
