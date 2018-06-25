package org.telegram.messenger.support.widget;

import android.os.Parcel;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import org.telegram.messenger.support.widget.RecyclerView.SavedState;

class RecyclerView$SavedState$1 implements ParcelableCompatCreatorCallbacks<SavedState> {
    RecyclerView$SavedState$1() {
    }

    public SavedState createFromParcel(Parcel in, ClassLoader loader) {
        return new SavedState(in, loader);
    }

    public SavedState[] newArray(int size) {
        return new SavedState[size];
    }
}
