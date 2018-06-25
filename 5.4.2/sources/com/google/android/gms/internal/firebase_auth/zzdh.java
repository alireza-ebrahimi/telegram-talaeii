package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

public class zzdh extends IOException {
    private zzeh zzrw = null;

    public zzdh(String str) {
        super(str);
    }

    static zzdh zzee() {
        return new zzdh("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzdh zzef() {
        return new zzdh("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzdh zzeg() {
        return new zzdh("CodedInputStream encountered a malformed varint.");
    }

    static zzdh zzeh() {
        return new zzdh("Protocol message end-group tag did not match expected tag.");
    }

    static zzdi zzei() {
        return new zzdi("Protocol message tag had invalid wire type.");
    }

    static zzdh zzej() {
        return new zzdh("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static zzdh zzek() {
        return new zzdh("Failed to parse the message.");
    }

    public final zzdh zzg(zzeh zzeh) {
        this.zzrw = zzeh;
        return this;
    }
}
