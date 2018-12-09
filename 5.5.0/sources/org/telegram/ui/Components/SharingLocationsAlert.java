package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.SharingLiveLocationCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.StickerPreviewViewer;

public class SharingLocationsAlert extends BottomSheet implements NotificationCenterDelegate {
    private ListAdapter adapter;
    private SharingLocationsAlertDelegate delegate;
    private boolean ignoreLayout;
    private RecyclerListView listView;
    private int reqId;
    private int scrollOffsetY;
    private Drawable shadowDrawable;
    private TextView textView;
    private Pattern urlPattern;

    public interface SharingLocationsAlertDelegate {
        void didSelectLocation(SharingLocationInfo sharingLocationInfo);
    }

    /* renamed from: org.telegram.ui.Components.SharingLocationsAlert$3 */
    class C45883 extends OnScrollListener {
        C45883() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            SharingLocationsAlert.this.updateLayout();
        }
    }

    /* renamed from: org.telegram.ui.Components.SharingLocationsAlert$4 */
    class C45894 implements OnItemClickListener {
        C45894() {
        }

        public void onItemClick(View view, int i) {
            int i2 = i - 1;
            if (i2 >= 0 && i2 < LocationController.getInstance().sharingLocationsUI.size()) {
                SharingLocationsAlert.this.delegate.didSelectLocation((SharingLocationInfo) LocationController.getInstance().sharingLocationsUI.get(i2));
                SharingLocationsAlert.this.dismiss();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.SharingLocationsAlert$5 */
    class C45905 implements OnClickListener {
        C45905() {
        }

        public void onClick(View view) {
            LocationController.getInstance().removeAllLocationSharings();
            SharingLocationsAlert.this.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.Components.SharingLocationsAlert$6 */
    class C45916 implements OnClickListener {
        C45916() {
        }

        public void onClick(View view) {
            SharingLocationsAlert.this.dismiss();
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return LocationController.getInstance().sharingLocationsUI.size() + 1;
        }

        public int getItemViewType(int i) {
            return i == 0 ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ((SharingLiveLocationCell) viewHolder.itemView).setDialog((SharingLocationInfo) LocationController.getInstance().sharingLocationsUI.get(i - 1));
                    return;
                case 1:
                    if (SharingLocationsAlert.this.textView != null) {
                        SharingLocationsAlert.this.textView.setText(LocaleController.formatString("SharingLiveLocationTitle", R.string.SharingLiveLocationTitle, new Object[]{LocaleController.formatPluralString("Chats", LocationController.getInstance().sharingLocationsUI.size())}));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View sharingLiveLocationCell;
            switch (i) {
                case 0:
                    sharingLiveLocationCell = new SharingLiveLocationCell(this.context, false);
                    break;
                default:
                    sharingLiveLocationCell = new FrameLayout(this.context) {
                        protected void onDraw(Canvas canvas) {
                            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(40.0f), (float) getMeasuredWidth(), (float) AndroidUtilities.dp(40.0f), Theme.dividerPaint);
                        }

                        protected void onMeasure(int i, int i2) {
                            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f) + 1, 1073741824));
                        }
                    };
                    sharingLiveLocationCell.setWillNotDraw(false);
                    SharingLocationsAlert.this.textView = new TextView(this.context);
                    SharingLocationsAlert.this.textView.setTextColor(Theme.getColor(Theme.key_dialogIcon));
                    SharingLocationsAlert.this.textView.setTextSize(1, 14.0f);
                    SharingLocationsAlert.this.textView.setGravity(17);
                    SharingLocationsAlert.this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
                    sharingLiveLocationCell.addView(SharingLocationsAlert.this.textView, LayoutHelper.createFrame(-1, 40.0f));
                    break;
            }
            return new Holder(sharingLiveLocationCell);
        }
    }

    public SharingLocationsAlert(Context context, SharingLocationsAlertDelegate sharingLocationsAlertDelegate) {
        super(context, false);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.liveLocationsChanged);
        this.delegate = sharingLocationsAlertDelegate;
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.containerView = new FrameLayout(context) {
            protected void onDraw(Canvas canvas) {
                SharingLocationsAlert.this.shadowDrawable.setBounds(0, SharingLocationsAlert.this.scrollOffsetY - SharingLocationsAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                SharingLocationsAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || SharingLocationsAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) SharingLocationsAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                SharingLocationsAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                SharingLocationsAlert.this.updateLayout();
            }

            protected void onMeasure(int i, int i2) {
                int dp;
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                getMeasuredWidth();
                int size2 = (LocationController.getInstance().sharingLocationsUI.size() * AndroidUtilities.dp(54.0f)) + ((AndroidUtilities.dp(56.0f) + AndroidUtilities.dp(56.0f)) + 1);
                if (size2 < (size / 5) * 3) {
                    dp = AndroidUtilities.dp(8.0f);
                } else {
                    dp = (size / 5) * 2;
                    if (size2 < size) {
                        dp -= size - size2;
                    }
                }
                if (SharingLocationsAlert.this.listView.getPaddingTop() != dp) {
                    SharingLocationsAlert.this.ignoreLayout = true;
                    SharingLocationsAlert.this.listView.setPadding(0, dp, 0, AndroidUtilities.dp(8.0f));
                    SharingLocationsAlert.this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(size2, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !SharingLocationsAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!SharingLocationsAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.listView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, SharingLocationsAlert.this.listView, 0, null);
            }

            public void requestLayout() {
                if (!SharingLocationsAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.listView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setClipToPadding(false);
        this.listView.setEnabled(true);
        this.listView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.listView.setOnScrollListener(new C45883());
        this.listView.setOnItemClickListener(new C45894());
        this.containerView.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.containerView.addView(view, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        View pickerBottomLayout = new PickerBottomLayout(context, false);
        pickerBottomLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.containerView.addView(pickerBottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        pickerBottomLayout.cancelButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        pickerBottomLayout.cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextRed));
        pickerBottomLayout.cancelButton.setText(LocaleController.getString("StopAllLocationSharings", R.string.StopAllLocationSharings));
        pickerBottomLayout.cancelButton.setOnClickListener(new C45905());
        pickerBottomLayout.doneButtonTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        pickerBottomLayout.doneButtonTextView.setText(LocaleController.getString("Close", R.string.Close).toUpperCase());
        pickerBottomLayout.doneButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        pickerBottomLayout.doneButton.setOnClickListener(new C45916());
        pickerBottomLayout.doneButtonBadgeTextView.setVisibility(8);
        this.adapter.notifyDataSetChanged();
    }

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        if (this.listView.getChildCount() <= 0) {
            RecyclerListView recyclerListView = this.listView;
            int paddingTop = this.listView.getPaddingTop();
            this.scrollOffsetY = paddingTop;
            recyclerListView.setTopGlowOffset(paddingTop);
            this.containerView.invalidate();
            return;
        }
        View childAt = this.listView.getChildAt(0);
        Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
        paddingTop = childAt.getTop() - AndroidUtilities.dp(8.0f);
        int i = (paddingTop <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : paddingTop;
        if (this.scrollOffsetY != i) {
            RecyclerListView recyclerListView2 = this.listView;
            this.scrollOffsetY = i;
            recyclerListView2.setTopGlowOffset(i);
            this.containerView.invalidate();
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i != NotificationCenter.liveLocationsChanged) {
            return;
        }
        if (LocationController.getInstance().sharingLocationsUI.isEmpty()) {
            dismiss();
        } else {
            this.adapter.notifyDataSetChanged();
        }
    }

    public void dismiss() {
        super.dismiss();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.liveLocationsChanged);
    }
}
