package org.telegram.messenger.support.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

@RestrictTo({Scope.LIBRARY_GROUP})
public class LinearLayoutManager$SavedState implements Parcelable {
    public static final Creator<LinearLayoutManager$SavedState> CREATOR = new C18781();
    boolean mAnchorLayoutFromEnd;
    int mAnchorOffset;
    int mAnchorPosition;

    /* renamed from: org.telegram.messenger.support.widget.LinearLayoutManager$SavedState$1 */
    static class C18781 implements Creator<LinearLayoutManager$SavedState> {
        C18781() {
        }

        public LinearLayoutManager$SavedState createFromParcel(Parcel in) {
            return new LinearLayoutManager$SavedState(in);
        }

        public LinearLayoutManager$SavedState[] newArray(int size) {
            return new LinearLayoutManager$SavedState[size];
        }
    }

    LinearLayoutManager$SavedState(Parcel in) {
        boolean z = true;
        this.mAnchorPosition = in.readInt();
        this.mAnchorOffset = in.readInt();
        if (in.readInt() != 1) {
            z = false;
        }
        this.mAnchorLayoutFromEnd = z;
    }

    public LinearLayoutManager$SavedState(LinearLayoutManager$SavedState other) {
        this.mAnchorPosition = other.mAnchorPosition;
        this.mAnchorOffset = other.mAnchorOffset;
        this.mAnchorLayoutFromEnd = other.mAnchorLayoutFromEnd;
    }

    boolean hasValidAnchor() {
        return this.mAnchorPosition >= 0;
    }

    void invalidateAnchor() {
        this.mAnchorPosition = -1;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAnchorPosition);
        dest.writeInt(this.mAnchorOffset);
        dest.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
    }
}
