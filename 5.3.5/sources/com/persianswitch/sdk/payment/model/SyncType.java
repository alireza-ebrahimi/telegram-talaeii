package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.func.Option;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class SyncType {
    public static final SyncType[] ALL_SYNCS_TYPE = new SyncType[]{CLIENT_CONFIG, CVV2_JAVASCRIPT};
    public static final SyncType CLIENT_CONFIG = new SyncType(FetchConst.NETWORK_WIFI, 101, 1);
    public static final SyncType CVV2_JAVASCRIPT = new SyncType(FetchConst.NETWORK_WIFI, 100, 1);
    private final int mSubType;
    private final int mType;
    private final int mVarType;

    private SyncType(int type, int subType, int varType) {
        this.mType = type;
        this.mSubType = subType;
        this.mVarType = varType;
    }

    public static SyncType getInstance(int type, int subType, int varType) {
        return new SyncType(type, subType, varType);
    }

    public static SyncType getInstance(String flatted) {
        String[] split = flatted.split("\\.", 3);
        return new SyncType(((Integer) Option.getOrDefault(StringUtils.toInteger(split[0]), Integer.valueOf(0))).intValue(), ((Integer) Option.getOrDefault(StringUtils.toInteger(split[1]), Integer.valueOf(0))).intValue(), ((Integer) Option.getOrDefault(StringUtils.toInteger(split[2]), Integer.valueOf(0))).intValue());
    }

    public int getType() {
        return this.mType;
    }

    public int getSubType() {
        return this.mSubType;
    }

    public int getVarType() {
        return this.mVarType;
    }

    public String getFlatType() {
        return StringUtils.trimJoin(".", Integer.valueOf(this.mType), Integer.valueOf(this.mSubType), Integer.valueOf(this.mVarType));
    }
}
