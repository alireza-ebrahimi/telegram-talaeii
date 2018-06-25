package org.telegram.messenger.support.widget;

interface OpReorderer$Callback {
    UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj);

    void recycleUpdateOp(UpdateOp updateOp);
}
