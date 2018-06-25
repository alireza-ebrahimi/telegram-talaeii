package com.google.android.gms.wallet.fragment;

public final class WalletFragmentState {
    public static final int PROCESSING = 3;
    public static final int READY = 2;
    public static final int UNINITIALIZED = 1;
    public static final int UNKNOWN = 0;
    public static final int WALLET_UNAVAILABLE = 4;

    private WalletFragmentState() {
    }
}
