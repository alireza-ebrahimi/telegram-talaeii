package android.support.v13.view;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

@TargetApi(13)
@RequiresApi(13)
public class DragStartHelper {
    private boolean mDragging;
    private int mLastTouchX;
    private int mLastTouchY;
    private final OnDragStartListener mListener;
    private final OnLongClickListener mLongClickListener = new C01011();
    private final OnTouchListener mTouchListener = new C01022();
    private final View mView;

    /* renamed from: android.support.v13.view.DragStartHelper$1 */
    class C01011 implements OnLongClickListener {
        C01011() {
        }

        public boolean onLongClick(View v) {
            return DragStartHelper.this.onLongClick(v);
        }
    }

    /* renamed from: android.support.v13.view.DragStartHelper$2 */
    class C01022 implements OnTouchListener {
        C01022() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return DragStartHelper.this.onTouch(v, event);
        }
    }

    public interface OnDragStartListener {
        boolean onDragStart(View view, DragStartHelper dragStartHelper);
    }

    public DragStartHelper(View view, OnDragStartListener listener) {
        this.mView = view;
        this.mListener = listener;
    }

    public void attach() {
        this.mView.setOnLongClickListener(this.mLongClickListener);
        this.mView.setOnTouchListener(this.mTouchListener);
    }

    public void detach() {
        this.mView.setOnLongClickListener(null);
        this.mView.setOnTouchListener(null);
    }

    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case 0:
                this.mLastTouchX = x;
                this.mLastTouchY = y;
                return false;
            case 1:
            case 3:
                this.mDragging = false;
                return false;
            case 2:
                if (!MotionEventCompat.isFromSource(event, 8194) || (MotionEventCompat.getButtonState(event) & 1) == 0 || this.mDragging) {
                    return false;
                }
                if (this.mLastTouchX == x && this.mLastTouchY == y) {
                    return false;
                }
                this.mLastTouchX = x;
                this.mLastTouchY = y;
                this.mDragging = this.mListener.onDragStart(v, this);
                return this.mDragging;
            default:
                return false;
        }
    }

    public boolean onLongClick(View v) {
        return this.mListener.onDragStart(v, this);
    }

    public void getTouchPosition(Point point) {
        point.set(this.mLastTouchX, this.mLastTouchY);
    }
}
