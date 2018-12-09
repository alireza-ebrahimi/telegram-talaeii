package org.telegram.customization.Model;

public class RequestLog {
    int duration;
    String host;
    int logType;
    String serviceName;
    int statusCode;

    public RequestLog(int i, String str, int i2, String str2, int i3) {
        this.logType = i;
        this.host = str;
        this.statusCode = i2;
        this.serviceName = str2;
        this.duration = i3;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getHost() {
        return this.host;
    }

    public int getLogType() {
        return this.logType;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setLogType(int i) {
        this.logType = i;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }
}
