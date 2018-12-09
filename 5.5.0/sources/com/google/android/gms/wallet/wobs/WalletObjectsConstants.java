package com.google.android.gms.wallet.wobs;

public interface WalletObjectsConstants {

    public interface State {
        public static final int ACTIVE = 1;
        public static final int COMPLETED = 2;
        public static final int EXPIRED = 4;
        public static final int INACTIVE = 5;
    }
}
