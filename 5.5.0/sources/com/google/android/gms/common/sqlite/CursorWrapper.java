package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;

public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
    private AbstractWindowedCursor zzxu;

    public CursorWrapper(Cursor cursor) {
        super(cursor);
        int i = 0;
        Object obj = cursor;
        while (i < 10 && (obj instanceof android.database.CursorWrapper)) {
            i++;
            Cursor wrappedCursor = ((android.database.CursorWrapper) obj).getWrappedCursor();
        }
        if (obj instanceof AbstractWindowedCursor) {
            this.zzxu = (AbstractWindowedCursor) obj;
            return;
        }
        String str = "Unknown type: ";
        String valueOf = String.valueOf(obj.getClass().getName());
        throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    public void fillWindow(int i, CursorWindow cursorWindow) {
        this.zzxu.fillWindow(i, cursorWindow);
    }

    public CursorWindow getWindow() {
        return this.zzxu.getWindow();
    }

    public AbstractWindowedCursor getWrappedCursor() {
        return this.zzxu;
    }

    public boolean onMove(int i, int i2) {
        return this.zzxu.onMove(i, i2);
    }

    public void setWindow(CursorWindow cursorWindow) {
        this.zzxu.setWindow(cursorWindow);
    }
}
