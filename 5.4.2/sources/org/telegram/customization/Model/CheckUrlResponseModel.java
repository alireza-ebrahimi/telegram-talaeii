package org.telegram.customization.Model;

import java.util.HashMap;
import java.util.Map;

public class CheckUrlResponseModel {
    Map<String, String> tag = new HashMap();
    Map<String, String> url = new HashMap();

    public Map<String, String> getTag() {
        return this.tag;
    }

    public Map<String, String> getUrl() {
        return this.url;
    }

    public void setTag(Map<String, String> map) {
        this.tag = map;
    }

    public void setUrl(Map<String, String> map) {
        this.url = map;
    }
}
