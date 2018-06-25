package com.persianswitch.sdk.base;

import com.persianswitch.sdk.base.style.PersonalizedConfig;

public interface Config {
    String getApplicationType();

    String getDistributorCode();

    PersonalizedConfig getPersonalizedConfig(int i);

    String getServerUrl();

    String getVersion();

    boolean isDebugMode();
}
