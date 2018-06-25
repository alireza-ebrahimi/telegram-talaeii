package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.base.manager.RequestTimeManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public abstract class WebServiceCallback<T extends WSResponse> implements IWebServiceCallback<T> {
    private boolean mRetriedForTimeSync;
    protected boolean mWipeData;

    public void onPreProcess() {
    }

    public boolean handleResponse(Context context, Config config, T response, WebService ws, IWebServiceCallback<T> callback) {
        if (response != null) {
            if (!StringUtils.isEmpty(response.getServerTime())) {
                new RequestTimeManager().syncTimeByServer(context, StringUtils.toLong(response.getServerTime().split(";")[0]));
            }
            if (response.getSecurityCode() == 1 && (response.getSecurityCode() == 2 || response.getSecurityCode() == 1)) {
                if (response.getStatus() == StatusCode.APP_NOT_REGISTERED) {
                    onWipeData(context);
                } else if (response.getStatus() == StatusCode.NEED_APP_RE_VERIFICATION) {
                    BaseSetting.setNeedReVerification(context, true);
                } else if (response.getStatus() == StatusCode.SYNC_TIME_BY_SERVER_FAILED && !this.mRetriedForTimeSync) {
                    this.mRetriedForTimeSync = true;
                    if (ws instanceof SyncWebService) {
                        ((SyncWebService) ws).syncLaunch(context, config, callback);
                        return true;
                    }
                    ws.launch(context, callback);
                    return true;
                }
            } else if (response.getSecurityCode() == 10) {
                onWipeData(context);
            }
        }
        return false;
    }

    private void onWipeData(Context context) {
        this.mWipeData = true;
        new BaseDatabase(context).clearAllData();
    }
}
