package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.data.WSRequest;

public interface IWebServiceDescriptor<T extends WSRequest> {
    T getRequest();
}
