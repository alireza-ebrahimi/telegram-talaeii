package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class SpliceNullCommand extends SpliceCommand {
    public static final Creator<SpliceNullCommand> CREATOR = new C17361();

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.scte35.SpliceNullCommand$1 */
    static class C17361 implements Creator<SpliceNullCommand> {
        C17361() {
        }

        public SpliceNullCommand createFromParcel(Parcel in) {
            return new SpliceNullCommand();
        }

        public SpliceNullCommand[] newArray(int size) {
            return new SpliceNullCommand[size];
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
