package com.google.firebase.iid;

import android.support.annotation.Keep;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.tasks.Task;

@Keep
@KeepForSdk
interface IRpc {
    @Keep
    @KeepForSdk
    Task<Void> ackMessage(String str);

    @Keep
    @KeepForSdk
    Task<String> buildChannel(String str);

    @Keep
    @KeepForSdk
    Task<Void> deleteInstanceId(String str);

    @Keep
    @KeepForSdk
    Task<Void> deleteToken(String str, String str2, String str3);

    @Keep
    @KeepForSdk
    Task<String> getToken(String str, String str2, String str3);

    @Keep
    @KeepForSdk
    Task<Void> subscribeToTopic(String str, String str2, String str3);

    @Keep
    @KeepForSdk
    Task<Void> unsubscribeFromTopic(String str, String str2, String str3);
}
