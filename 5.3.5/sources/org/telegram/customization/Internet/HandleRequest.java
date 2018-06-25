package org.telegram.customization.Internet;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.time.DateUtils;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Payment.HostResponseData;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.compression.lz4.LZ4Factory;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.util.AesBase64Wrapper;
import org.telegram.customization.util.Prefs;
import org.telegram.tgnet.ConnectionsManager;
import utils.app.AppPreferences;
import utils.view.Constants;

public class HandleRequest implements Listener<String>, ErrorListener, IResponseReceiver {
    public static long callCount = 0;
    private static long isProxyGetServerLastTime = 0;
    private static boolean isProxyGetServerRunning = false;
    public static long msAll = 0;
    public static long msChange = 0;
    private static RequestQueue requestQueue;
    private final Context _context;
    private IResponseReceiver _iResponseReceiver;
    private String _requestUrl;
    private HandleRequest$HandleInterface handleInterface;
    Handler handler;
    private IResponseReceiver iMainThreadResponseReceiver;
    private int retryNumber = 1;

    private byte[] getJsonByte(String json) {
        byte[] bytes = new byte[0];
        try {
            bytes = json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static HandleRequest getNew(Context context, IResponseReceiver iResponseReceiver) {
        return new HandleRequest(context, iResponseReceiver);
    }

    private HandleRequest(Context context, IResponseReceiver iResponseReceiver) {
        this._context = context;
        this._iResponseReceiver = this;
        this.iMainThreadResponseReceiver = iResponseReceiver;
        try {
            this.handler = new Handler(Looper.getMainLooper());
        } catch (Exception e) {
        }
    }

    public void onResult(Object object, int StatusCode) {
        runOnCallerThread(new HandleRequest$1(this, object, StatusCode));
    }

    public void runOnCallerThread(Runnable runnable) {
        if (this.iMainThreadResponseReceiver == null) {
            return;
        }
        if (this.handler != null) {
            this.handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public static RequestQueue initRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void onErrorResponse(VolleyError volleyError) {
        Log.d("LEE", "HandleRequest onErrorResponse");
        try {
            if (this.handler != null) {
                new HandleRequest$2(this, volleyError).start();
            } else {
                onErrorResponseInternal(volleyError);
            }
        } catch (Exception e) {
        }
    }

    public void onErrorResponseInternal(VolleyError volleyError) {
        try {
            if (this.handleInterface.getSetKey().equals(Constants.KEY_SETTING_PROXY_API)) {
                isProxyGetServerRunning = false;
            }
        } catch (Exception e) {
        }
        String code = "0";
        if (volleyError != null) {
            try {
                if (volleyError instanceof TimeoutError) {
                    code = "1000";
                    String label = (this.handleInterface.getRequest().getUrl() + " ") + volleyError.networkResponse.headers.toString();
                    Answers.getInstance().logCustom((CustomEvent) new CustomEvent(Constants.TRACKER_NETWORK_ERROR).putCustomAttribute(this.handleInterface.toString(), code + " - " + 135 + " " + WebservicePropertis.MainDomain));
                    if (this.handleInterface.getSetKey() != Constants.KEY_SETTING_PROXY_API && retryByAnotherAddress(code)) {
                    }
                    if (this.iMainThreadResponseReceiver != null) {
                        try {
                            this.handleInterface.onErrorResponse(volleyError);
                            return;
                        } catch (Exception e2) {
                            return;
                        }
                    }
                    return;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (volleyError != null) {
            if (volleyError.networkResponse != null) {
                code = String.valueOf(volleyError.networkResponse.statusCode);
            }
        }
        try {
            String label2 = (this.handleInterface.getRequest().getUrl() + " ") + volleyError.networkResponse.headers.toString();
        } catch (Exception e4) {
            label2 = label2 + "bad class";
        } catch (Throwable th) {
        }
        Answers.getInstance().logCustom((CustomEvent) new CustomEvent(Constants.TRACKER_NETWORK_ERROR).putCustomAttribute(this.handleInterface.toString(), code + " - " + 135 + " " + WebservicePropertis.MainDomain));
        try {
        } catch (Exception e5) {
        }
    }

    public void onResponse(String response) {
        Log.d("LEE", "HandleRequest onResponse");
        try {
            if (this.handler != null) {
                new HandleRequest$3(this, response).start();
            } else {
                onResponseInternal(response);
            }
        } catch (Exception e) {
        }
    }

    public void onResponseInternal(String response) {
        Exception e;
        try {
            if (this.handleInterface.getSetKey().equals(Constants.KEY_SETTING_PROXY_API)) {
                isProxyGetServerRunning = false;
            }
        } catch (Exception e2) {
        }
        if (this.iMainThreadResponseReceiver != null) {
            BaseResponseModel baseResponseModel = null;
            try {
                if (this.handleInterface.ignoreParsingResponse()) {
                    BaseResponseModel resObj = new BaseResponseModel();
                    try {
                        resObj.setItems(response);
                        this.handleInterface.onResponse(resObj);
                        return;
                    } catch (Exception e3) {
                        e = e3;
                        baseResponseModel = resObj;
                        try {
                            e.printStackTrace();
                        } catch (Exception e4) {
                        }
                        if (baseResponseModel == null) {
                            baseResponseModel = new BaseResponseModel();
                            baseResponseModel.setCode(-1);
                            baseResponseModel.setMessage("");
                        }
                        try {
                            this.handleInterface.onResponse(baseResponseModel);
                        } catch (Exception e5) {
                            try {
                                this.handleInterface.onErrorResponse(null);
                                return;
                            } catch (Exception e6) {
                                return;
                            }
                        }
                    }
                }
                JsonObject jo = ((JsonElement) new Gson().fromJson(response, JsonElement.class)).getAsJsonObject();
                String itemsJson = null;
                String code = null;
                String message = null;
                int statusCode = -1;
                if (jo.get("code") != null) {
                    code = jo.get("code").toString();
                }
                if (jo.get("data") != null) {
                    itemsJson = jo.get("data").toString();
                }
                if (jo.get("message") != null) {
                    message = jo.get("message").toString();
                }
                BaseResponseModel baseResponseModel2 = new BaseResponseModel();
                baseResponseModel2.setMessage(message);
                try {
                    statusCode = Integer.parseInt(code);
                } catch (Exception e7) {
                }
                if (statusCode == 200) {
                    baseResponseModel2.setCode(statusCode);
                    baseResponseModel2.setItems(new Gson().fromJson(itemsJson, this.handleInterface.getClassType()));
                    this.handleInterface.onResponse(baseResponseModel2);
                } else {
                    baseResponseModel2.setCode(statusCode);
                    this.handleInterface.onResponse(baseResponseModel2);
                }
                if (baseResponseModel == null) {
                    baseResponseModel = new BaseResponseModel();
                    baseResponseModel.setCode(-1);
                    baseResponseModel.setMessage("");
                }
                this.handleInterface.onResponse(baseResponseModel);
            } catch (Exception e8) {
                e = e8;
                e.printStackTrace();
                if (baseResponseModel == null) {
                    baseResponseModel = new BaseResponseModel();
                    baseResponseModel.setCode(-1);
                    baseResponseModel.setMessage("");
                }
                this.handleInterface.onResponse(baseResponseModel);
            }
        }
    }

    private boolean retryByAnotherAddress(String errorCode) {
        AppPreferences.reArrangeDomainsWithSetKey(this.handleInterface.getSetKey());
        this.retryNumber++;
        String url = WSUtils.changeDomain(this.retryNumber, this._requestUrl, this._context, this.handleInterface.getSetKey(), this.handleInterface.getSetKey());
        if (url == null) {
            return false;
        }
        Request request = new HandleRequest$4(this, this.handleInterface.getRequest().getMethod(), url, this, this, this._context, true, this.handleInterface.getSetKey());
        RetryPolicy rp = this.handleInterface.getRetryPolicy();
        if (rp == null) {
            request.setRetryPolicy(new DefaultRetryPolicy(WSUtils.defaultTimeOut(this._context), 0, 1.0f));
        } else {
            request.setRetryPolicy(rp);
        }
        logRequest(request);
        request.setShouldCache(true);
        if (this.handleInterface.getSetKey().contentEquals(Constants.KEY_SETTING_PAYMENT_API) && !request.getUrl().startsWith("https")) {
            return true;
        }
        getRequestQueue().add(request);
        return true;
    }

    void logRequest(Request request) {
        if (request != null) {
            try {
                Map<String, String> h = request.getHeaders();
                if (h != null) {
                    for (int i = 0; i < h.size(); i++) {
                        String combine = ((HashMap) h).entrySet().toArray()[i].toString();
                        String name = combine.substring(0, combine.indexOf(61));
                        combine.substring(combine.indexOf(61) + 1);
                    }
                }
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }
        }
    }

    public static void cancelApi(String api) {
        getRequestQueue().cancelAll((Object) api);
    }

    private void callRESTWebservice(HandleRequest$HandleInterface hi) {
        if (this.handler != null) {
            new HandleRequest$5(this, hi).start();
        } else {
            callRESTWebserviceInternal(hi);
        }
    }

    private void callRESTWebserviceInternal(HandleRequest$HandleInterface hi) {
        this.handleInterface = hi;
        Request request = this.handleInterface.getRequest();
        this._requestUrl = request.getUrl();
        if (request == null || !ConnectionsManager.isNetworkOnline()) {
            this._iResponseReceiver.onResult("offline", -1000);
            return;
        }
        logRequest(request);
        RetryPolicy rp = hi.getRetryPolicy();
        if (rp == null) {
            request.setRetryPolicy(new DefaultRetryPolicy(WSUtils.defaultTimeOut(this._context), 0, 1.0f));
        } else {
            request.setRetryPolicy(rp);
        }
        if (hi instanceof HandleRequest$HandleInterfaceWithTag) {
            try {
                request.setTag(((HandleRequest$HandleInterfaceWithTag) hi).getTag());
            } catch (Exception e) {
            }
        }
        getRequestQueue().add(request);
    }

    public void getHome(long tagId, long lastId, boolean getOlderPost, long mediaType, int limit) {
        callRESTWebservice(new HandleRequest$6(this, lastId, tagId, getOlderPost, limit, mediaType));
    }

    public void getDashboard(long userId, long lastId, boolean getOlderPost, long mediaType, int limit) {
        callRESTWebservice(new HandleRequest$7(this, userId, getOlderPost, limit, mediaType, lastId));
    }

    public void getSearch(long tagId, long lastId, boolean getOlderPost, String searchTerm, long mediaType, int limit, long sortOrder, boolean phraseSearch) {
        callRESTWebservice(new HandleRequest$8(this, searchTerm, tagId, getOlderPost, limit, mediaType, lastId, sortOrder, phraseSearch));
    }

    public void getSearchPost(long tagId, long lastId, boolean getOlderPost, String searchTerm, long mediaType, int limit, long sortOrder, boolean phraseSearch) {
        callRESTWebservice(new HandleRequest$9(this, searchTerm, getOlderPost, lastId, mediaType, limit, phraseSearch, sortOrder));
    }

    public void getFilters() {
        ArrayList<SlsFilter> filters = Prefs.getFilters(this._context);
        boolean useCache = false;
        if (DateUtils.MILLIS_PER_HOUR + Prefs.getFiltersTime(this._context) > System.currentTimeMillis() && filters != null && filters.size() > 0) {
            useCache = true;
        }
        if (useCache) {
            this._iResponseReceiver.onResult(filters, 2);
        } else {
            callRESTWebservice(new HandleRequest$10(this));
        }
    }

    public void getFileUrl(long docId, long volumeId, long localId) {
        callRESTWebservice(new HandleRequest$11(this, docId, volumeId, localId));
    }

    public void checkMonoAvailability(ArrayList<DocAvailableInfo> docs) {
        if (ConnectionsManager.isNetworkOnline()) {
            msChange = System.currentTimeMillis();
            callRESTWebservice(new HandleRequest$12(this, docs));
        }
    }

    public void checkUrl(ArrayList<DocAvailableInfo> docs) {
        checkUrl(docs, 0);
    }

    public void checkUrl(ArrayList<DocAvailableInfo> docs, long dialog_id) {
        if (docs != null && docs.size() >= 1) {
            msChange = System.currentTimeMillis();
            callRESTWebservice(new HandleRequest$13(this, docs, dialog_id));
        }
    }

    public void registerOnMono(boolean callInService) {
        callRESTWebservice(new HandleRequest$14(this, callInService));
    }

    public void sendChannelList(String json) {
        callRESTWebservice(new HandleRequest$15(this, json));
    }

    public void followTag(String json) {
        callRESTWebservice(new HandleRequest$16(this, json));
    }

    public void isFavoriteChannel(long tagId) {
        callRESTWebservice(new HandleRequest$17(this, tagId));
    }

    public void followTagStatus(String json) {
        callRESTWebservice(new HandleRequest$18(this, json));
    }

    public void getUserTags() {
        callRESTWebservice(new HandleRequest$19(this));
    }

    public void getSelfUpdateAndSetting() {
        callRESTWebservice(new HandleRequest$20(this));
    }

    public void getNewsListByTagId(long tagId, String lastId, String dir, int limit) {
        callRESTWebservice(new HandleRequest$21(this, tagId, limit, lastId, dir));
    }

    public void getNewsById(long tagId, String newsId, View view) {
        callRESTWebservice(new HandleRequest$22(this, view, newsId, tagId));
    }

    public void getPollById(String id) {
        callRESTWebservice(new HandleRequest$23(this));
    }

    public void sendStickers(String json) {
        callRESTWebservice(new HandleRequest$24(this, json));
    }

    public void sendChannelListNew(String json) {
        callRESTWebservice(new HandleRequest$25(this, json));
    }

    public void sendSuperGroup(String json) {
        callRESTWebservice(new HandleRequest$26(this, json));
    }

    public void sendBot(String json) {
        callRESTWebservice(new HandleRequest$27(this, json));
    }

    public void sendTraffic(String json) {
        callRESTWebservice(new HandleRequest$28(this, json));
    }

    public void sendContacts(String json) {
        callRESTWebservice(new HandleRequest$29(this, json));
    }

    public void sendLocation(String json) {
        callRESTWebservice(new HandleRequest$30(this, json));
    }

    public void singleCheckFilterStatus(long userId, String channelId) {
        callRESTWebservice(new HandleRequest$31(this, userId, channelId));
    }

    public void getCategories() {
        callRESTWebservice(new HandleRequest$32(this));
    }

    public void getStatistics() {
        callRESTWebservice(new HandleRequest$33(this));
    }

    public void manageCategory(ArrayList<Category> categories) {
        callRESTWebservice(new HandleRequest$34(this, categories));
    }

    public void adsLog(org.telegram.customization.Model.Ads.Log log) {
        callRESTWebservice(new HandleRequest$35(this, log));
    }

    public void proxyGetServer(boolean setProxy) {
        Log.d("LEE", "proxyGetServer");
        isProxyGetServerLastTime = System.currentTimeMillis();
        isProxyGetServerRunning = true;
        callRESTWebservice(new HandleRequest$36(this, setProxy));
    }

    public void proxyErrorGetNewServer() {
        callRESTWebservice(new HandleRequest$37(this));
    }

    public void sendTM(String json) {
        callRESTWebservice(new HandleRequest$38(this, json));
    }

    public void getThemes() {
        callRESTWebservice(new HandleRequest$39(this));
    }

    public void registerApSdk() {
        callRESTWebservice(new HandleRequest$40(this));
    }

    public void registerSupporter(User user) {
        callRESTWebservice(new HandleRequest$41(this, user));
    }

    public void checkRegisterStatus() {
        callRESTWebservice(new HandleRequest$42(this));
    }

    public void ipgCallback(HostResponseData hostResponseData) {
        callRESTWebservice(new HandleRequest$43(this, hostResponseData));
    }

    public void getPaymentDetail(String paymentId) {
        callRESTWebservice(new HandleRequest$44(this, paymentId));
    }

    public void failPayment(String paymentId, String desc) {
        callRESTWebservice(new HandleRequest$45(this, paymentId, desc));
    }

    public void requestPayment(String paymentId) {
        callRESTWebservice(new HandleRequest$46(this, paymentId));
    }

    public void getPaymentReport(int pageIndex, int pageCount, long startDate, long endDate) {
        callRESTWebservice(new HandleRequest$47(this, pageIndex, pageCount, startDate, endDate));
    }

    public void generatePaymentLink(long fromTelegramUserId, long toTelegramUserId, long amount, String description) {
        callRESTWebservice(new HandleRequest$48(this, fromTelegramUserId, toTelegramUserId, amount, description));
    }

    public void getSettleReport(int pageIndex, int pageCount, long startDate, long endDate) {
        callRESTWebservice(new HandleRequest$49(this, pageIndex, pageCount, startDate, endDate));
    }

    public static byte[] getEncondedByteArr(String json) {
        byte[] eb = null;
        try {
            eb = new AesBase64Wrapper("HR$2pIjHR$2pIj12", "shiralizadeh-rabiee-amiry-sls", "nothing-to-show-32jH8*aq#3B/2p1_K3Wdrq").encryptAndEncode(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encode(LZ4Factory.fastestInstance().fastCompressor().compress(eb), 2);
    }

    public void proxyGetServerTest(boolean setProxy) {
        callRESTWebservice(new HandleRequest$50(this));
    }

    public static String getDecodedString(String json) {
        String eb = null;
        try {
            eb = new AesBase64Wrapper(new HandleRequest$51().toString(), new HandleRequest$52().toString(), new HandleRequest$53().toString()).decodeAndDecrypt(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eb;
    }

    public String getChangedPassword(String pass) {
        int i;
        String password = "";
        for (i = 0; i < pass.length(); i++) {
            char nlet;
            Character character = Character.valueOf(pass.charAt(i));
            if (Character.isLowerCase(character.charValue())) {
                nlet = Character.toUpperCase(character.charValue());
            } else {
                nlet = Character.toLowerCase(character.charValue());
            }
            password = password + nlet;
        }
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,";
        Random rnd = new Random();
        for (i = 0; i < 5; i++) {
            password = password + alphabet.charAt(rnd.nextInt(alphabet.length()));
        }
        return password;
    }
}
