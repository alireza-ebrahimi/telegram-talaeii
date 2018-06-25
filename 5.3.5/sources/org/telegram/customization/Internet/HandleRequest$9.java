package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.Model.SearchRequest;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import utils.view.Constants;

class HandleRequest$9 implements HandleRequest$HandleInterfaceWithTag {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ boolean val$getOlderPost;
    final /* synthetic */ long val$lastId;
    final /* synthetic */ int val$limit;
    final /* synthetic */ long val$mediaType;
    final /* synthetic */ boolean val$phraseSearch;
    final /* synthetic */ String val$searchTerm;
    final /* synthetic */ long val$sortOrder;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$9$1 */
    class C11821 extends TypeToken<ArrayList<ObjBase>> {
        C11821() {
        }
    }

    HandleRequest$9(HandleRequest this$0, String str, boolean z, long j, long j2, int i, boolean z2, long j3) {
        this.this$0 = this$0;
        this.val$searchTerm = str;
        this.val$getOlderPost = z;
        this.val$lastId = j;
        this.val$mediaType = j2;
        this.val$limit = i;
        this.val$phraseSearch = z2;
        this.val$sortOrder = j3;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public String getTag() {
        return WebservicePropertis.WS_GET_SEARCH;
    }

    public void onResponse(BaseResponseModel object) {
        JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
        String itemsJson = null;
        if (jo.get("data") != null) {
            itemsJson = jo.get("data").toString();
        }
        HandleRequest.access$300(this.this$0).onResult((ArrayList) new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create().fromJson(itemsJson, new C11821().getType()), 1);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -1);
    }

    public Request getRequest() {
        String encodedSearchTerm = this.val$searchTerm;
        try {
            encodedSearchTerm = URLEncoder.encode(this.val$searchTerm, "UTF-8");
        } catch (Exception e) {
        }
        return new BaseStringRequest(1, WebservicePropertis.WS_GET_SEARCH, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                SearchRequest request = new SearchRequest();
                request.setDirection(HandleRequest$9.this.val$getOlderPost ? 1 : 0);
                request.setLastRow(HandleRequest$9.this.val$lastId);
                request.setMediaType(HandleRequest$9.this.val$mediaType);
                request.setPageSize(HandleRequest$9.this.val$limit);
                request.setPhrasesearch(HandleRequest$9.this.val$phraseSearch);
                request.setSortOrder(HandleRequest$9.this.val$sortOrder);
                request.setSearchTerm(HandleRequest$9.this.val$searchTerm);
                return HandleRequest.getEncondedByteArr(new Gson().toJson(request));
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return true;
    }

    public Type getClassType() {
        return null;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_USUAL_API;
    }

    public String toString() {
        return "WS_GET_SEARCH";
    }
}
