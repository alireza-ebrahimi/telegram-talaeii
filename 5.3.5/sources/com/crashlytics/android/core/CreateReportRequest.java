package com.crashlytics.android.core;

class CreateReportRequest {
    public final String apiKey;
    public final Report report;

    public CreateReportRequest(String apiKey, Report report) {
        this.apiKey = apiKey;
        this.report = report;
    }
}
