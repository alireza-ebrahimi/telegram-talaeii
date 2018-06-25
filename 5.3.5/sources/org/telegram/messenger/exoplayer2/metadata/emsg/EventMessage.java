package org.telegram.messenger.exoplayer2.metadata.emsg;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
import org.telegram.messenger.exoplayer2.util.Util;

public final class EventMessage implements Metadata$Entry {
    public static final Creator<EventMessage> CREATOR = new C17241();
    public final long durationMs;
    private int hashCode;
    public final long id;
    public final byte[] messageData;
    public final String schemeIdUri;
    public final String value;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.emsg.EventMessage$1 */
    static class C17241 implements Creator<EventMessage> {
        C17241() {
        }

        public EventMessage createFromParcel(Parcel in) {
            return new EventMessage(in);
        }

        public EventMessage[] newArray(int size) {
            return new EventMessage[size];
        }
    }

    public EventMessage(String schemeIdUri, String value, long durationMs, long id, byte[] messageData) {
        this.schemeIdUri = schemeIdUri;
        this.value = value;
        this.durationMs = durationMs;
        this.id = id;
        this.messageData = messageData;
    }

    EventMessage(Parcel in) {
        this.schemeIdUri = in.readString();
        this.value = in.readString();
        this.durationMs = in.readLong();
        this.id = in.readLong();
        this.messageData = in.createByteArray();
    }

    public int hashCode() {
        int i = 0;
        if (this.hashCode == 0) {
            int hashCode;
            if (this.schemeIdUri != null) {
                hashCode = this.schemeIdUri.hashCode();
            } else {
                hashCode = 0;
            }
            hashCode = (hashCode + 527) * 31;
            if (this.value != null) {
                i = this.value.hashCode();
            }
            this.hashCode = ((((((hashCode + i) * 31) + ((int) (this.durationMs ^ (this.durationMs >>> 32)))) * 31) + ((int) (this.id ^ (this.id >>> 32)))) * 31) + Arrays.hashCode(this.messageData);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventMessage other = (EventMessage) obj;
        if (this.durationMs == other.durationMs && this.id == other.id && Util.areEqual(this.schemeIdUri, other.schemeIdUri) && Util.areEqual(this.value, other.value) && Arrays.equals(this.messageData, other.messageData)) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schemeIdUri);
        dest.writeString(this.value);
        dest.writeLong(this.durationMs);
        dest.writeLong(this.id);
        dest.writeByteArray(this.messageData);
    }
}
