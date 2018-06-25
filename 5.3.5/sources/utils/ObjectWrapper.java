package utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.Model.HotgramNotification;
import org.telegram.customization.Model.JoinChannelModel;
import org.telegram.customization.Model.Setting;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.service.ProxyService;
import org.telegram.customization.util.AppUtilities;
import org.telegram.customization.util.view.Poll.PollModel;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_dialog;
import utils.app.AppPreferences;
import utils.view.Constants;

public class ObjectWrapper {

    /* renamed from: utils.ObjectWrapper$1 */
    static class C34681 implements Runnable {
        C34681() {
        }

        public void run() {
            ArrayList<TLRPC$TL_dialog> channelList = MessagesController.getInstance().dialogs;
            ArrayList<SLSChannel> slsChannels = new ArrayList();
            Iterator it = channelList.iterator();
            while (it.hasNext()) {
                long id = -((TLRPC$TL_dialog) it.next()).id;
                TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(Integer.parseInt("" + id)));
                SLSChannel channel = new SLSChannel();
                channel.setChannelId(currentChat.username);
                channel.setName(currentChat.title);
                channel.setId(id);
                slsChannels.add(channel);
            }
        }
    }

    public static ArrayList<TLRPC$TL_dialog> getChannelList() {
        MessagesController.getInstance().loadDialogs(0, 1000, true);
        new Handler().postDelayed(new C34681(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        return null;
    }

    public static PollModel convertObjectToPollModel(String response) {
        return (PollModel) new Gson().fromJson(response.toString(), PollModel.class);
    }

    public static void saveSetting(final ArrayList<Setting> response, final Context context) {
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread() {

            /* renamed from: utils.ObjectWrapper$2$2 */
            class C34702 extends TypeToken<ArrayList<FilterResponse>> {
                C34702() {
                }
            }

            public void run() {
                super.run();
                Iterator it = response.iterator();
                while (it.hasNext()) {
                    final Setting model = (Setting) it.next();
                    String key = model.getKey();
                    Object obj = -1;
                    switch (key.hashCode()) {
                        case -628486815:
                            if (key.equals(Constants.KEY_SETTING_FS_MSG1)) {
                                obj = 19;
                                break;
                            }
                            break;
                        case -628486814:
                            if (key.equals(Constants.KEY_SETTING_FS_MSG2)) {
                                obj = 20;
                                break;
                            }
                            break;
                        case 1367331562:
                            if (key.equals(Constants.CHECK_MONO_AVAILABILITY)) {
                                obj = 1;
                                break;
                            }
                            break;
                        case 1367331563:
                            if (key.equals(Constants.KEY_SETTING_MIRROR_ADDRESS)) {
                                obj = null;
                                break;
                            }
                            break;
                        case 1367331566:
                            if (key.equals(Constants.KEY_SETTING_MAIN_DOMAIN)) {
                                obj = 2;
                                break;
                            }
                            break;
                        case 1367331567:
                            if (key.equals(Constants.KEY_SETTING_ELECTION_TAB)) {
                                obj = 3;
                                break;
                            }
                            break;
                        case 1367331568:
                            if (key.equals(Constants.KEY_SETTING_NEWS_TAB)) {
                                obj = 4;
                                break;
                            }
                            break;
                        case 1367331569:
                            if (key.equals(Constants.KEY_SETTING_NEWS_TAG_ID)) {
                                obj = 5;
                                break;
                            }
                            break;
                        case 1367331570:
                            if (key.equals(Constants.KEY_SETTING_TAG_BASE_URL)) {
                                obj = 6;
                                break;
                            }
                            break;
                        case 1367331571:
                            if (key.equals(Constants.KEY_SETTING_INVAITE_TO_APP)) {
                                obj = 7;
                                break;
                            }
                            break;
                        case 1367331593:
                            if (key.equals(Constants.KEY_SETTING_CHANNEL_SYNC_PERIOD)) {
                                obj = 8;
                                break;
                            }
                            break;
                        case 1367331594:
                            if (key.equals(Constants.KEY_SETTING_CONTACT_SYNC_PERIOD)) {
                                obj = 9;
                                break;
                            }
                            break;
                        case 1367331595:
                            if (key.equals(Constants.KEY_SETTING_LOCATION_SYNC_PERIOD)) {
                                obj = 10;
                                break;
                            }
                            break;
                        case 1367331596:
                            if (key.equals(Constants.KEY_SETTING_JOIN_CHANNEL)) {
                                obj = 13;
                                break;
                            }
                            break;
                        case 1367331597:
                            if (key.equals(Constants.KEY_SETTING_SUPER_GROUP_SYNC_PERIOD)) {
                                obj = 11;
                                break;
                            }
                            break;
                        case 1367331598:
                            if (key.equals(Constants.KEY_SETTING_BOT_SYNC_PERIOD)) {
                                obj = 12;
                                break;
                            }
                            break;
                        case 1367331599:
                            if (key.equals(Constants.KEY_SETTING_STREAM_ENABLE)) {
                                obj = 14;
                                break;
                            }
                            break;
                        case 1367331600:
                            if (key.equals(Constants.KEY_SETTING_GHOST_ENABLE)) {
                                obj = 15;
                                break;
                            }
                            break;
                        case 1367331601:
                            if (key.equals(Constants.KEY_SETTING_TELEGRAM_APP_ID)) {
                                obj = 16;
                                break;
                            }
                            break;
                        case 1367331602:
                            if (key.equals(Constants.KEY_SETTING_TELEGRAM_HASH_ID)) {
                                obj = 17;
                                break;
                            }
                            break;
                        case 1367331624:
                            if (key.equals(Constants.KEY_SETTING_CHECK_URL_MAIN_DOMAIN)) {
                                obj = 21;
                                break;
                            }
                            break;
                        case 1367331625:
                            if (key.equals(Constants.KEY_SETTING_SHOW_FREE_ICON)) {
                                obj = 18;
                                break;
                            }
                            break;
                        case 1367331626:
                            if (key.equals(Constants.KEY_SETTING_CHECK_URL_MIRROR)) {
                                obj = 22;
                                break;
                            }
                            break;
                        case 1367331627:
                            if (key.equals(Constants.KEY_SETTING_CLIENT_OFFICIAL_CHANNEL)) {
                                obj = 23;
                                break;
                            }
                            break;
                        case 1367331628:
                            if (key.equals(Constants.KEY_SETTING_FILTER_MESSAGE)) {
                                obj = 24;
                                break;
                            }
                            break;
                        case 1367331629:
                            if (key.equals(Constants.KEY_SETTING_SHOW_FREE_POPUP)) {
                                obj = 25;
                                break;
                            }
                            break;
                        case 1367331630:
                            if (key.equals(Constants.KEY_SETTING_ADS_ENABLE)) {
                                obj = 26;
                                break;
                            }
                            break;
                        case 1367331631:
                            if (key.equals(Constants.KEY_SETTING_ADS_DIALOG_ACT_TUTORIAL)) {
                                obj = 27;
                                break;
                            }
                            break;
                        case 1367331632:
                            if (key.equals(Constants.KEY_SETTING_ADS_CHANNEL_LIST_TUTORIAL)) {
                                obj = 28;
                                break;
                            }
                            break;
                        case 1367331633:
                            if (key.equals(Constants.KEY_SETTING_ADS_JOIN_MESSAGE)) {
                                obj = 29;
                                break;
                            }
                            break;
                        case 1367331686:
                            if (key.equals(Constants.KEY_SETTING_ADS_URL)) {
                                obj = 30;
                                break;
                            }
                            break;
                        case 1367331687:
                            if (key.equals(Constants.KEY_SETTING_ADS_TUTORIAL_URL)) {
                                obj = 31;
                                break;
                            }
                            break;
                        case 1367331688:
                            if (key.equals(Constants.KEY_SETTING_CHANGE_SP)) {
                                obj = 32;
                                break;
                            }
                            break;
                        case 1367331690:
                            if (key.equals(Constants.KEY_SETTING_SLS_PROXY_ENABLE)) {
                                obj = 33;
                                break;
                            }
                            break;
                        case 1367331692:
                            if (key.equals(Constants.KEY_SETTING_FILTER_CHANNEL)) {
                                obj = 34;
                                break;
                            }
                            break;
                        case 1367331693:
                            if (key.equals(Constants.KEY_SETTING_SHOW_FILTER_DIALOG)) {
                                obj = 35;
                                break;
                            }
                            break;
                        case 1367331718:
                            if (key.equals(Constants.KEY_SETTING_USUAL_API)) {
                                obj = 36;
                                break;
                            }
                            break;
                        case 1367331719:
                            if (key.equals(Constants.KEY_SETTING_CHECK_URL_API)) {
                                obj = 39;
                                break;
                            }
                            break;
                        case 1367331720:
                            if (key.equals(Constants.KEY_SETTING_SETTING_API)) {
                                obj = 41;
                                break;
                            }
                            break;
                        case 1367331721:
                            if (key.equals(Constants.KEY_SETTING_INFO_API)) {
                                obj = 38;
                                break;
                            }
                            break;
                        case 1367331722:
                            if (key.equals(Constants.KEY_SETTING_REGISTER_API)) {
                                obj = 40;
                                break;
                            }
                            break;
                        case 1367331723:
                            if (key.equals(Constants.KEY_SETTING_PROXY_API)) {
                                obj = 37;
                                break;
                            }
                            break;
                        case 1367331724:
                            if (key.equals(Constants.KEY_SETTING_FILTER_CHANNEL_API)) {
                                obj = 43;
                                break;
                            }
                            break;
                        case 1367331725:
                            if (key.equals(Constants.KEY_SEETING_CHECK_FILTER)) {
                                obj = 47;
                                break;
                            }
                            break;
                        case 1367331748:
                            if (key.equals(Constants.KEY_SETTING_HOT_TAB)) {
                                obj = 48;
                                break;
                            }
                            break;
                        case 1367331749:
                            if (key.equals(Constants.KEY_SETTING_SEARCH_TAB)) {
                                obj = 49;
                                break;
                            }
                            break;
                        case 1367331750:
                            if (key.equals(Constants.KEY_SETTING_PAYMENT_API)) {
                                obj = 42;
                                break;
                            }
                            break;
                        case 1367331751:
                            if (key.equals(Constants.KEY_SETTING_PAYMENT_ENABLE)) {
                                obj = 50;
                                break;
                            }
                            break;
                        case 1367331753:
                            if (key.equals(Constants.KEY_SETTING_LIGHT_CHECK_URL_API)) {
                                obj = 44;
                                break;
                            }
                            break;
                        case 1367331754:
                            if (key.equals(Constants.KEY_SETTING_LIGHT_CONFIG_API)) {
                                obj = 45;
                                break;
                            }
                            break;
                        case 1367331755:
                            if (key.equals(Constants.KEY_SETTING_LIGHT_PROXY_API)) {
                                obj = 46;
                                break;
                            }
                            break;
                        case 1367331756:
                            if (key.equals(Constants.KEY_SETTING_TAG_POST_ENABLE)) {
                                obj = 51;
                                break;
                            }
                            break;
                        case 1367331757:
                            if (key.equals(Constants.KEY_SETTING_GET_PROXY_PERIOD)) {
                                obj = 52;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case null:
                            AppPreferences.setMirrorAddress(context, model.getValue());
                            break;
                        case 1:
                            try {
                                AppPreferences.setShouldCheckLocalUrl(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }
                        case 2:
                            AppPreferences.setMainDomain(context, model.getValue());
                            break;
                        case 3:
                            try {
                                AppPreferences.setShouldShowElectionTab(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                break;
                            }
                        case 4:
                            try {
                                AppPreferences.setShouldShowNewsTab(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e22) {
                                e22.printStackTrace();
                                break;
                            }
                        case 5:
                            try {
                                AppPreferences.setNewsPartTagId(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e222) {
                                e222.printStackTrace();
                                break;
                            }
                        case 6:
                            AppPreferences.setTagBaseUrl(context, model.getValue());
                            break;
                        case 7:
                            AppPreferences.setInviteMessage(context, model.getValue());
                            break;
                        case 8:
                            try {
                                AppPreferences.setChannelSyncPeriod(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e2222) {
                                e2222.printStackTrace();
                                break;
                            }
                        case 9:
                            try {
                                AppPreferences.setContactSyncPeriod(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e22222) {
                                e22222.printStackTrace();
                                break;
                            }
                        case 10:
                            try {
                                AppPreferences.setLocationSyncPeriod(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e222222) {
                                e222222.printStackTrace();
                                break;
                            }
                        case 11:
                            try {
                                AppPreferences.setSuperGroupSyncPeriod(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e2222222) {
                                e2222222.printStackTrace();
                                break;
                            }
                        case 12:
                            try {
                                AppPreferences.setBotSyncPeriod(context, Long.parseLong(model.getValue()));
                                break;
                            } catch (Exception e22222222) {
                                e22222222.printStackTrace();
                                break;
                            }
                        case 13:
                            try {
                                if (!TextUtils.isEmpty(model.getValue())) {
                                    JoinChannelModel joinChannelModel = ObjectWrapper.convertJsonToJoinChannelModel(model.getValue());
                                    if (!(joinChannelModel == null || TextUtils.isEmpty(joinChannelModel.getUsername()))) {
                                        long expireDate = AppPreferences.getChannelJoinExpireDate(context, joinChannelModel.getUsername());
                                        if (expireDate != 0 && System.currentTimeMillis() <= expireDate) {
                                            break;
                                        }
                                        SlsMessageHolder.addToChannel(-1, AppUtilities.normalizeUsername(joinChannelModel.getUsername()), joinChannelModel.getExpireTime());
                                        break;
                                    }
                                }
                                break;
                            } catch (Exception e3) {
                                break;
                            }
                            break;
                        case 14:
                            try {
                                AppPreferences.setStreamEnable(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e222222222) {
                                e222222222.printStackTrace();
                                break;
                            }
                        case 15:
                            try {
                                AppPreferences.setGhostEnable(context, Integer.parseInt(model.getValue()));
                                break;
                            } catch (Exception e2222222222) {
                                e2222222222.printStackTrace();
                                break;
                            }
                        case 16:
                            try {
                                AppPreferences.setTelegramAppId(context, Integer.parseInt(model.getValue()));
                                break;
                            } catch (Exception e22222222222) {
                                e22222222222.printStackTrace();
                                break;
                            }
                        case 17:
                            try {
                                AppPreferences.setTelegramHashId(context, String.valueOf(model.getValue()));
                                break;
                            } catch (Exception e222222222222) {
                                e222222222222.printStackTrace();
                                break;
                            }
                        case 18:
                            try {
                                AppPreferences.setShowFreeIconEnable(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e2222222222222) {
                                e2222222222222.printStackTrace();
                                break;
                            }
                        case 19:
                        case 20:
                            try {
                                AppPreferences.setFreeStateText(context, model.getKey(), model.getValue());
                                break;
                            } catch (Exception e22222222222222) {
                                e22222222222222.printStackTrace();
                                break;
                            }
                        case 21:
                            try {
                                AppPreferences.setMainDomainForCheckUrl(context, model.getValue());
                                break;
                            } catch (Exception e222222222222222) {
                                e222222222222222.printStackTrace();
                                break;
                            }
                        case 22:
                            try {
                                AppPreferences.setMirrorAddressForCheckUrl(context, model.getValue());
                                break;
                            } catch (Exception e2222222222222222) {
                                e2222222222222222.printStackTrace();
                                break;
                            }
                        case 23:
                            try {
                                AppPreferences.setJoinToOfficialChannel(context, model.getValue());
                                break;
                            } catch (Exception e22222222222222222) {
                                e22222222222222222.printStackTrace();
                                break;
                            }
                        case 24:
                            try {
                                AppPreferences.setFilterMessage(context, model.getValue());
                                break;
                            } catch (Exception e222222222222222222) {
                                e222222222222222222.printStackTrace();
                                break;
                            }
                        case 25:
                            try {
                                AppPreferences.setShowFreePopup(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e2222222222222222222) {
                                e2222222222222222222.printStackTrace();
                                break;
                            }
                        case 26:
                            try {
                                AppPreferences.setAdsEnable(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e22222222222222222222) {
                                e22222222222222222222.printStackTrace();
                                break;
                            }
                        case 27:
                            try {
                                AppPreferences.setAdsTutorialDialogAct(context, model.getValue());
                                break;
                            } catch (Exception e222222222222222222222) {
                                e222222222222222222222.printStackTrace();
                                break;
                            }
                        case 28:
                            try {
                                AppPreferences.setAdsTutorialChannelList(context, model.getValue());
                                break;
                            } catch (Exception e2222222222222222222222) {
                                e2222222222222222222222.printStackTrace();
                                break;
                            }
                        case 29:
                            try {
                                AppPreferences.setAdsJoinMessage(context, model.getValue());
                                break;
                            } catch (Exception e22222222222222222222222) {
                                e22222222222222222222222.printStackTrace();
                                break;
                            }
                        case 30:
                            try {
                                AppPreferences.setAdsUrl(context, model.getValue());
                                break;
                            } catch (Exception e222222222222222222222222) {
                                e222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 31:
                            try {
                                AppPreferences.setAdsUrlTu(context, model.getValue());
                                break;
                            } catch (Exception e2222222222222222222222222) {
                                e2222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 32:
                            try {
                                ObjectWrapper.changeSpValues(context, (HotgramNotification) new Gson().fromJson(model.getValue(), HotgramNotification.class));
                                break;
                            } catch (Exception e22222222222222222222222222) {
                                e22222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 33:
                            handler.post(new Runnable() {
                                int enable = Integer.parseInt(model.getValue());

                                public void run() {
                                    try {
                                        AppPreferences.setProxyEnable(context, this.enable);
                                        if (AppPreferences.getProxyEnable(context) > 0) {
                                            ProxyService.registerService(context);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            break;
                        case 34:
                            try {
                                ArrayList<FilterResponse> filtersChannels = (ArrayList) new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create().fromJson(model.getValue(), new C34702().getType());
                                ApplicationLoader.databaseHandler.clearFilterStatus();
                                Iterator it2 = filtersChannels.iterator();
                                while (it2.hasNext()) {
                                    FilterResponse filterResponse = (FilterResponse) it2.next();
                                    DialogStatus dialogStatus = new DialogStatus();
                                    dialogStatus.setFilter(filterResponse.isFilter());
                                    dialogStatus.setDialogId(filterResponse.getChannelId());
                                    ApplicationLoader.databaseHandler.createOrUpdateDialogStatus(dialogStatus);
                                }
                                break;
                            } catch (Exception e4) {
                                break;
                            }
                        case 35:
                            try {
                                AppPreferences.setShowFilterDialog(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e222222222222222222222222222) {
                                e222222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 36:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_USUAL_API);
                            break;
                        case 37:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_PROXY_API);
                            break;
                        case 38:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_INFO_API);
                            break;
                        case 39:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_CHECK_URL_API);
                            break;
                        case 40:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_REGISTER_API);
                            break;
                        case 41:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_SETTING_API);
                            break;
                        case 42:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_PAYMENT_API);
                            break;
                        case 43:
                            AppPreferences.setDomainsWithSetKey(context, model.getValue(), Constants.KEY_SETTING_FILTER_CHANNEL_API);
                            break;
                        case 44:
                            AppPreferences.setDomainsWithSetKey(context, ObjectWrapper.getParsedUrls(model.getValue()), Constants.KEY_SETTING_LIGHT_CHECK_URL_API);
                            break;
                        case 45:
                            AppPreferences.setDomainsWithSetKey(context, ObjectWrapper.getParsedUrls(model.getValue()), Constants.KEY_SETTING_LIGHT_CONFIG_API);
                            break;
                        case 46:
                            AppPreferences.setDomainsWithSetKey(context, ObjectWrapper.getParsedUrls(model.getValue()), Constants.KEY_SETTING_LIGHT_PROXY_API);
                            break;
                        case 47:
                            try {
                                AppPreferences.setCheckFilterChannel(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e2222222222222222222222222222) {
                                e2222222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 48:
                            try {
                                AppPreferences.setShouldShowHotTab(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e22222222222222222222222222222) {
                                try {
                                    e22222222222222222222222222222.printStackTrace();
                                    break;
                                } catch (Exception e5) {
                                    return;
                                }
                            }
                        case 49:
                            try {
                                AppPreferences.setShouldShowSearchTab(context, Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e222222222222222222222222222222) {
                                e222222222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 50:
                            try {
                                AppPreferences.setPaymentEnable(Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e2222222222222222222222222222222) {
                                e2222222222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 51:
                            try {
                                AppPreferences.setShowTagPost(Boolean.parseBoolean(model.getValue()));
                                break;
                            } catch (Exception e22222222222222222222222222222222) {
                                e22222222222222222222222222222222.printStackTrace();
                                break;
                            }
                        case 52:
                            try {
                                if (AppPreferences.getProxyEnable(ApplicationLoader.applicationContext) <= 0) {
                                    break;
                                }
                                AppPreferences.setProxyCallPeriod(Long.parseLong(model.getValue()));
                                ProxyService.registerService(ApplicationLoader.applicationContext);
                                break;
                            } catch (Exception e222222222222222222222222222222222) {
                                e222222222222222222222222222222222.printStackTrace();
                                break;
                            }
                        default:
                            break;
                    }
                }
            }
        }.start();
    }

    private static JoinChannelModel convertJsonToJoinChannelModel(String json) {
        JoinChannelModel model = null;
        try {
            return (JoinChannelModel) new Gson().fromJson(json, JoinChannelModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return model;
        }
    }

    private static void changeSpValues(Context context, HotgramNotification notification) {
        try {
            Editor editor = context.getSharedPreferences(notification.getTitle(), 0).edit();
            if (notification.getMessage().equals("i")) {
                editor.putInt(notification.getBigText(), Integer.parseInt(notification.getExtraData()));
            } else if (notification.getMessage().equals("l")) {
                editor.putLong(notification.getBigText(), Long.parseLong(notification.getExtraData()));
            } else if (notification.getMessage().equals("s")) {
                editor.putString(notification.getBigText(), notification.getExtraData());
            } else if (notification.getMessage().equals("f")) {
                editor.putFloat(notification.getBigText(), Float.parseFloat(notification.getExtraData()));
            } else if (notification.getMessage().equals("b")) {
                editor.putBoolean(notification.getBigText(), Boolean.parseBoolean(notification.getExtraData()));
            }
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static String getParsedUrls(String urls) {
        List<String> notParsedUrl = Arrays.asList(urls.split(","));
        ArrayList<String> parsedUrls = new ArrayList();
        if (notParsedUrl != null && notParsedUrl.size() > 0) {
            for (String url : notParsedUrl) {
                String tmpUrl = "http://";
                String hostName = "";
                String prefixUrl = "";
                if (url.startsWith("h")) {
                    hostName = "hotgram.ir/";
                    prefixUrl = url.replace("h", "");
                } else if (url.startsWith("s")) {
                    hostName = "harsobh.com/";
                    prefixUrl = url.replace("s", "");
                } else if (url.startsWith("t")) {
                    hostName = "talagram.ir/";
                    prefixUrl = url.replace("t", "");
                }
                parsedUrls.add(tmpUrl + "lh" + prefixUrl + "." + hostName);
            }
        }
        if (parsedUrls.size() > 0) {
            return TextUtils.join(",", parsedUrls);
        }
        return "";
    }
}
