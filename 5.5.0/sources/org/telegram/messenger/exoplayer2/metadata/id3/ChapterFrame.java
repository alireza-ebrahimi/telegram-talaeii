package org.telegram.messenger.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Util;

public final class ChapterFrame extends Id3Frame {
    public static final Creator<ChapterFrame> CREATOR = new C35011();
    public static final String ID = "CHAP";
    public final String chapterId;
    public final long endOffset;
    public final int endTimeMs;
    public final long startOffset;
    public final int startTimeMs;
    private final Id3Frame[] subFrames;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.id3.ChapterFrame$1 */
    static class C35011 implements Creator<ChapterFrame> {
        C35011() {
        }

        public ChapterFrame createFromParcel(Parcel parcel) {
            return new ChapterFrame(parcel);
        }

        public ChapterFrame[] newArray(int i) {
            return new ChapterFrame[i];
        }
    }

    ChapterFrame(Parcel parcel) {
        super(ID);
        this.chapterId = parcel.readString();
        this.startTimeMs = parcel.readInt();
        this.endTimeMs = parcel.readInt();
        this.startOffset = parcel.readLong();
        this.endOffset = parcel.readLong();
        int readInt = parcel.readInt();
        this.subFrames = new Id3Frame[readInt];
        for (int i = 0; i < readInt; i++) {
            this.subFrames[i] = (Id3Frame) parcel.readParcelable(Id3Frame.class.getClassLoader());
        }
    }

    public ChapterFrame(String str, int i, int i2, long j, long j2, Id3Frame[] id3FrameArr) {
        super(ID);
        this.chapterId = str;
        this.startTimeMs = i;
        this.endTimeMs = i2;
        this.startOffset = j;
        this.endOffset = j2;
        this.subFrames = id3FrameArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChapterFrame chapterFrame = (ChapterFrame) obj;
        return this.startTimeMs == chapterFrame.startTimeMs && this.endTimeMs == chapterFrame.endTimeMs && this.startOffset == chapterFrame.startOffset && this.endOffset == chapterFrame.endOffset && Util.areEqual(this.chapterId, chapterFrame.chapterId) && Arrays.equals(this.subFrames, chapterFrame.subFrames);
    }

    public Id3Frame getSubFrame(int i) {
        return this.subFrames[i];
    }

    public int getSubFrameCount() {
        return this.subFrames.length;
    }

    public int hashCode() {
        return (this.chapterId != null ? this.chapterId.hashCode() : 0) + ((((((((this.startTimeMs + 527) * 31) + this.endTimeMs) * 31) + ((int) this.startOffset)) * 31) + ((int) this.endOffset)) * 31);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.chapterId);
        parcel.writeInt(this.startTimeMs);
        parcel.writeInt(this.endTimeMs);
        parcel.writeLong(this.startOffset);
        parcel.writeLong(this.endOffset);
        parcel.writeInt(this.subFrames.length);
        for (Parcelable writeParcelable : this.subFrames) {
            parcel.writeParcelable(writeParcelable, 0);
        }
    }
}
