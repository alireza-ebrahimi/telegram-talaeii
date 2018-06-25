package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.data.WSRequest;

public class WebserviceFactory {
    public static WebService create(WSRequest request) {
        return new WebService(request);
    }
}
