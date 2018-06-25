package com.persianswitch.okhttp3.internal;

import com.persianswitch.okhttp3.Address;
import com.persianswitch.okhttp3.Call;
import com.persianswitch.okhttp3.ConnectionPool;
import com.persianswitch.okhttp3.ConnectionSpec;
import com.persianswitch.okhttp3.Headers.Builder;
import com.persianswitch.okhttp3.HttpUrl;
import com.persianswitch.okhttp3.OkHttpClient;
import com.persianswitch.okhttp3.internal.http.StreamAllocation;
import com.persianswitch.okhttp3.internal.io.RealConnection;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;

public abstract class Internal {
    public static Internal instance;

    public abstract void addLenient(Builder builder, String str);

    public abstract void addLenient(Builder builder, String str, String str2);

    public abstract void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean z);

    public abstract StreamAllocation callEngineGetStreamAllocation(Call call);

    public abstract boolean connectionBecameIdle(ConnectionPool connectionPool, RealConnection realConnection);

    public abstract RealConnection get(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation);

    public abstract HttpUrl getHttpUrlChecked(String str) throws MalformedURLException, UnknownHostException;

    public abstract InternalCache internalCache(OkHttpClient okHttpClient);

    public abstract void put(ConnectionPool connectionPool, RealConnection realConnection);

    public abstract RouteDatabase routeDatabase(ConnectionPool connectionPool);

    public abstract void setCache(OkHttpClient.Builder builder, InternalCache internalCache);

    public abstract void setCallWebSocket(Call call);

    public static void initializeInstanceForTests() {
        OkHttpClient okHttpClient = new OkHttpClient();
    }
}
