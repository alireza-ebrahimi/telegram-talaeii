package org.telegram.messenger.exoplayer2.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import java.util.List;

public final class Metadata implements Parcelable {
    public static final Creator<Metadata> CREATOR = new Metadata$1();
    private final Metadata$Entry[] entries;

    public Metadata(Metadata$Entry... entries) {
        if (entries == null) {
            entries = new Metadata$Entry[0];
        }
        this.entries = entries;
    }

    public Metadata(List<? extends Metadata$Entry> entries) {
        if (entries != null) {
            this.entries = new Metadata$Entry[entries.size()];
            entries.toArray(this.entries);
            return;
        }
        this.entries = new Metadata$Entry[0];
    }

    Metadata(Parcel in) {
        this.entries = new Metadata$Entry[in.readInt()];
        for (int i = 0; i < this.entries.length; i++) {
            this.entries[i] = (Metadata$Entry) in.readParcelable(Metadata$Entry.class.getClassLoader());
        }
    }

    public int length() {
        return this.entries.length;
    }

    public Metadata$Entry get(int index) {
        return this.entries[index];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.entries, ((Metadata) obj).entries);
    }

    public int hashCode() {
        return Arrays.hashCode(this.entries);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entries.length);
        for (Metadata$Entry entry : this.entries) {
            dest.writeParcelable(entry, 0);
        }
    }
}
