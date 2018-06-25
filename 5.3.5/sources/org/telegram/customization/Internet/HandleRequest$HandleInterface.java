package org.telegram.customization.Internet;

import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;

interface HandleRequest$HandleInterface {
    Type getClassType();

    Request getRequest();

    RetryPolicy getRetryPolicy();

    String getSetKey();

    boolean ignoreParsingResponse();

    void onErrorResponse(VolleyError volleyError);

    void onResponse(BaseResponseModel baseResponseModel);
}
