package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import java.util.UUID;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.Rect;

public class EntityView extends FrameLayout {
    private boolean announcedSelection = false;
    private EntityViewDelegate delegate;
    private GestureDetector gestureDetector;
    private boolean hasPanned = false;
    private boolean hasReleased = false;
    private boolean hasTransformed = false;
    private int offsetX;
    private int offsetY;
    protected Point position = new Point();
    private float previousLocationX;
    private float previousLocationY;
    private boolean recognizedLongPress = false;
    protected SelectionView selectionView;
    private UUID uuid = UUID.randomUUID();

    /* renamed from: org.telegram.ui.Components.Paint.Views.EntityView$1 */
    class C26531 extends SimpleOnGestureListener {
        C26531() {
        }

        public void onLongPress(MotionEvent e) {
            if (!EntityView.this.hasPanned && !EntityView.this.hasTransformed && !EntityView.this.hasReleased) {
                EntityView.this.recognizedLongPress = true;
                if (EntityView.this.delegate != null) {
                    EntityView.this.performHapticFeedback(0);
                    EntityView.this.delegate.onEntityLongClicked(EntityView.this);
                }
            }
        }
    }

    public interface EntityViewDelegate {
        boolean allowInteraction(EntityView entityView);

        boolean onEntityLongClicked(EntityView entityView);

        boolean onEntitySelected(EntityView entityView);
    }

    public class SelectionView extends FrameLayout {
        public static final int SELECTION_LEFT_HANDLE = 1;
        public static final int SELECTION_RIGHT_HANDLE = 2;
        public static final int SELECTION_WHOLE_HANDLE = 3;
        private int currentHandle;
        protected Paint dotPaint = new Paint(1);
        protected Paint dotStrokePaint = new Paint(1);
        protected Paint paint = new Paint(1);

        public SelectionView(Context context) {
            super(context);
            setWillNotDraw(false);
            this.paint.setColor(-1);
            this.dotPaint.setColor(-12793105);
            this.dotStrokePaint.setColor(-1);
            this.dotStrokePaint.setStyle(Style.STROKE);
            this.dotStrokePaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
        }

        protected void updatePosition() {
            Rect bounds = EntityView.this.getSelectionBounds();
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.leftMargin = ((int) bounds.f107x) + EntityView.this.offsetX;
            layoutParams.topMargin = ((int) bounds.f108y) + EntityView.this.offsetY;
            layoutParams.width = (int) bounds.width;
            layoutParams.height = (int) bounds.height;
            setLayoutParams(layoutParams);
            setRotation(EntityView.this.getRotation());
        }

        protected int pointInsideHandle(float x, float y) {
            return 0;
        }

        public boolean onTouchEvent(MotionEvent event) {
            boolean handled = false;
            switch (event.getActionMasked()) {
                case 0:
                case 5:
                    int handle = pointInsideHandle(event.getX(), event.getY());
                    if (handle != 0) {
                        this.currentHandle = handle;
                        EntityView.this.previousLocationX = event.getRawX();
                        EntityView.this.previousLocationY = event.getRawY();
                        EntityView.this.hasReleased = false;
                        handled = true;
                        break;
                    }
                    break;
                case 1:
                case 3:
                case 6:
                    EntityView.this.onTouchUp();
                    this.currentHandle = 0;
                    handled = true;
                    break;
                case 2:
                    if (this.currentHandle != 3) {
                        if (this.currentHandle != 0) {
                            EntityView.this.hasTransformed = true;
                            Point translation = new Point(event.getRawX() - EntityView.this.previousLocationX, event.getRawY() - EntityView.this.previousLocationY);
                            float radAngle = (float) Math.toRadians((double) getRotation());
                            float delta = (float) ((((double) translation.f105x) * Math.cos((double) radAngle)) + (((double) translation.f106y) * Math.sin((double) radAngle)));
                            if (this.currentHandle == 1) {
                                delta *= -1.0f;
                            }
                            EntityView.this.scale(1.0f + ((2.0f * delta) / ((float) getWidth())));
                            float centerX = (float) (getLeft() + (getWidth() / 2));
                            float centerY = (float) (getTop() + (getHeight() / 2));
                            float parentX = event.getRawX() - ((float) ((View) getParent()).getLeft());
                            float parentY = (event.getRawY() - ((float) ((View) getParent()).getTop())) - ((float) AndroidUtilities.statusBarHeight);
                            float angle = 0.0f;
                            if (this.currentHandle == 1) {
                                angle = (float) Math.atan2((double) (centerY - parentY), (double) (centerX - parentX));
                            } else if (this.currentHandle == 2) {
                                angle = (float) Math.atan2((double) (parentY - centerY), (double) (parentX - centerX));
                            }
                            EntityView.this.rotate((float) Math.toDegrees((double) angle));
                            EntityView.this.previousLocationX = event.getRawX();
                            EntityView.this.previousLocationY = event.getRawY();
                            handled = true;
                            break;
                        }
                    }
                    handled = EntityView.this.onTouchMove(event.getRawX(), event.getRawY());
                    break;
                    break;
            }
            if (this.currentHandle == 3) {
                EntityView.this.gestureDetector.onTouchEvent(event);
            }
            return handled;
        }
    }

    public EntityView(Context context, Point pos) {
        super(context);
        this.position = pos;
        this.gestureDetector = new GestureDetector(context, new C26531());
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point value) {
        this.position = value;
        updatePosition();
    }

    public float getScale() {
        return getScaleX();
    }

    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }

    public void setDelegate(EntityViewDelegate entityViewDelegate) {
        this.delegate = entityViewDelegate;
    }

    public void setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.delegate.allowInteraction(this);
    }

    private boolean onTouchMove(float x, float y) {
        float scale = ((View) getParent()).getScaleX();
        Point translation = new Point((x - this.previousLocationX) / scale, (y - this.previousLocationY) / scale);
        if (((float) Math.hypot((double) translation.f105x, (double) translation.f106y)) <= (this.hasPanned ? 6.0f : 16.0f)) {
            return false;
        }
        pan(translation);
        this.previousLocationX = x;
        this.previousLocationY = y;
        this.hasPanned = true;
        return true;
    }

    private void onTouchUp() {
        if (!(this.recognizedLongPress || this.hasPanned || this.hasTransformed || this.announcedSelection || this.delegate == null)) {
            this.delegate.onEntitySelected(this);
        }
        this.recognizedLongPress = false;
        this.hasPanned = false;
        this.hasTransformed = false;
        this.hasReleased = true;
        this.announcedSelection = false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1 || !this.delegate.allowInteraction(this)) {
            return false;
        }
        float x = event.getRawX();
        float y = event.getRawY();
        boolean handled = false;
        switch (event.getActionMasked()) {
            case 0:
            case 5:
                if (!(isSelected() || this.delegate == null)) {
                    this.delegate.onEntitySelected(this);
                    this.announcedSelection = true;
                }
                this.previousLocationX = x;
                this.previousLocationY = y;
                handled = true;
                this.hasReleased = false;
                break;
            case 1:
            case 3:
            case 6:
                onTouchUp();
                handled = true;
                break;
            case 2:
                handled = onTouchMove(x, y);
                break;
        }
        this.gestureDetector.onTouchEvent(event);
        return handled;
    }

    public void pan(Point translation) {
        Point point = this.position;
        point.f105x += translation.f105x;
        point = this.position;
        point.f106y += translation.f106y;
        updatePosition();
    }

    protected void updatePosition() {
        float halfHeight = ((float) getHeight()) / 2.0f;
        setX(this.position.f105x - (((float) getWidth()) / 2.0f));
        setY(this.position.f106y - halfHeight);
        updateSelectionView();
    }

    public void scale(float scale) {
        setScale(Math.max(getScale() * scale, 0.1f));
        updateSelectionView();
    }

    public void rotate(float angle) {
        setRotation(angle);
        updateSelectionView();
    }

    protected Rect getSelectionBounds() {
        return new Rect(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public boolean isSelected() {
        return this.selectionView != null;
    }

    protected SelectionView createSelectionView() {
        return null;
    }

    public void updateSelectionView() {
        if (this.selectionView != null) {
            this.selectionView.updatePosition();
        }
    }

    public void select(ViewGroup selectionContainer) {
        SelectionView selectionView = createSelectionView();
        this.selectionView = selectionView;
        selectionContainer.addView(selectionView);
        selectionView.updatePosition();
    }

    public void deselect() {
        if (this.selectionView != null) {
            if (this.selectionView.getParent() != null) {
                ((ViewGroup) this.selectionView.getParent()).removeView(this.selectionView);
            }
            this.selectionView = null;
        }
    }

    public void setSelectionVisibility(boolean visible) {
        if (this.selectionView != null) {
            this.selectionView.setVisibility(visible ? 0 : 8);
        }
    }
}
