package org.telegram.customization.Model;

import java.util.HashMap;
import java.util.Map;

public class CheckUrlResponseModel {
    Map<String, String> tag = new HashMap();
    Map<String, String> url = new HashMap();

    public Map<String, String> getUrl() {
        return this.url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }

    public Map<String, String> getTag() {
        return this.tag;
    }

    public void setTag(Map<String, String> tag) {
        this.tag = tag;
    }
}
