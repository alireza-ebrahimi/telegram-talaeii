package com.stripe.android.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.stripe.android.util.StripeTextUtils;

public class RequestOptions {
    @NonNull
    private final String mApiVersion;
    @Nullable
    private final String mIdempotencyKey;
    @NonNull
    private final String mPublishableApiKey;

    public static final class RequestOptionsBuilder {
        private String apiVersion;
        private String idempotencyKey;
        private String publishableApiKey;

        public RequestOptionsBuilder(@NonNull String publishableApiKey) {
            this.publishableApiKey = publishableApiKey;
        }

        @NonNull
        public RequestOptionsBuilder setPublishableApiKey(@NonNull String publishableApiKey) {
            this.publishableApiKey = publishableApiKey;
            return this;
        }

        @NonNull
        public RequestOptionsBuilder setIdempotencyKey(@Nullable String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        @NonNull
        public RequestOptionsBuilder setApiVersion(@Nullable String apiVersion) {
            if (StripeTextUtils.isBlank(apiVersion)) {
                apiVersion = null;
            }
            this.apiVersion = apiVersion;
            return this;
        }

        public RequestOptions build() {
            return new RequestOptions(this.apiVersion, this.idempotencyKey, this.publishableApiKey);
        }
    }

    private RequestOptions(@NonNull String apiVersion, @Nullable String idempotencyKey, @NonNull String publishableApiKey) {
        this.mApiVersion = apiVersion;
        this.mIdempotencyKey = idempotencyKey;
        this.mPublishableApiKey = publishableApiKey;
    }

    @Nullable
    public String getApiVersion() {
        return this.mApiVersion;
    }

    @Nullable
    public String getIdempotencyKey() {
        return this.mIdempotencyKey;
    }

    @NonNull
    public String getPublishableApiKey() {
        return this.mPublishableApiKey;
    }

    public static RequestOptionsBuilder builder(@NonNull String publishableApiKey) {
        return new RequestOptionsBuilder(publishableApiKey);
    }
}
