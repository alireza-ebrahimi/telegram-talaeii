package com.crashlytics.android.answers;

public class ShareEvent extends PredefinedEvent<ShareEvent> {
    static final String CONTENT_ID_ATTRIBUTE = "contentId";
    static final String CONTENT_NAME_ATTRIBUTE = "contentName";
    static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
    static final String METHOD_ATTRIBUTE = "method";
    static final String TYPE = "share";

    public ShareEvent putMethod(String shareMethod) {
        this.predefinedAttributes.put("method", shareMethod);
        return this;
    }

    public ShareEvent putContentId(String contentId) {
        this.predefinedAttributes.put(CONTENT_ID_ATTRIBUTE, contentId);
        return this;
    }

    public ShareEvent putContentName(String contentName) {
        this.predefinedAttributes.put(CONTENT_NAME_ATTRIBUTE, contentName);
        return this;
    }

    public ShareEvent putContentType(String contentType) {
        this.predefinedAttributes.put(CONTENT_TYPE_ATTRIBUTE, contentType);
        return this;
    }

    String getPredefinedType() {
        return "share";
    }
}
