package org.telegram.tgnet;

public interface RequestDelegate {
    void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error);
}
