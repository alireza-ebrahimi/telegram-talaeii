package org.telegram.messenger.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Util;

public final class ChapterTocFrame extends Id3Frame {
    public static final Creator<ChapterTocFrame> CREATOR = new C17281();
    public static final String ID = "CTOC";
    public final String[] children;
    public final String elementId;
    public final boolean isOrdered;
    public final boolean isRoot;
    private final Id3Frame[] subFrames;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.id3.ChapterTocFrame$1 */
    static class C17281 implements Creator<ChapterTocFrame> {
        C17281() {
        }

        public ChapterTocFrame createFromParcel(Parcel in) {
            return new ChapterTocFrame(in);
        }

        public ChapterTocFrame[] newArray(int size) {
            return new ChapterTocFrame[size];
        }
    }

    public ChapterTocFrame(String elementId, boolean isRoot, boolean isOrdered, String[] children, Id3Frame[] subFrames) {
        super(ID);
        this.elementId = elementId;
        this.isRoot = isRoot;
        this.isOrdered = isOrdered;
        this.children = children;
        this.subFrames = subFrames;
    }

    ChapterTocFrame(Parcel in) {
        boolean z;
        boolean z2 = true;
        super(ID);
        this.elementId = in.readString();
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isRoot = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.isOrdered = z2;
        this.children = in.createStringArray();
        int subFrameCount = in.readInt();
        this.subFrames = new Id3Frame[subFrameCount];
        for (int i = 0; i < subFrameCount; i++) {
            this.subFrames[i] = (Id3Frame) in.readParcelable(Id3Frame.class.getClassLoader());
        }
    }

    public int getSubFrameCount() {
        return this.subFrames.length;
    }

    public Id3Frame getSubFrame(int index) {
        return this.subFrames[index];
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChapterTocFrame other = (ChapterTocFrame) obj;
        if (this.isRoot == other.isRoot && this.isOrdered == other.isOrdered && Util.areEqual(this.elementId, other.elementId) && Arrays.equals(this.children, other.children) && Arrays.equals(this.subFrames, other.subFrames)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int i3 = 0;
        if (this.isRoot) {
            i = 1;
        } else {
            i = 0;
        }
        i = (i + 527) * 31;
        if (!this.isOrdered) {
            i2 = 0;
        }
        i = (i + i2) * 31;
        if (this.elementId != null) {
            i3 = this.elementId.hashCode();
        }
        return i + i3;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.elementId);
        if (this.isRoot) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (!this.isOrdered) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeStringArray(this.children);
        dest.writeInt(this.subFrames.length);
        for (Parcelable writeParcelable : this.subFrames) {
            dest.writeParcelable(writeParcelable, 0);
        }
    }
}
