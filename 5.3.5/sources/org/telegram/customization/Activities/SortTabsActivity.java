package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.gson.Gson;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.util.view.DragDrop.DialogTabsAdapter;
import org.telegram.customization.util.view.DragDrop.listeners.OnCustomerListChangedListener;
import org.telegram.customization.util.view.DragDrop.listeners.OnEndDragListener;
import org.telegram.customization.util.view.DragDrop.listeners.OnStartDragListener;
import org.telegram.customization.util.view.DragDrop.utilities.SimpleItemTouchHelperCallback;
import org.telegram.messenger.NotificationCenter;
import utils.app.AppPreferences;

public class SortTabsActivity extends Activity implements OnCustomerListChangedListener, OnStartDragListener, OnEndDragListener, OnClickListener {
    private List<DialogTab> dialogTabs;
    private DialogTabsAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_tabs);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        this.mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.dialogTabs = AppPreferences.getAllDialogTabs(getApplicationContext());
        this.mAdapter = new DialogTabsAdapter(this.dialogTabs, this, this, this, this);
        this.mItemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(this.mAdapter));
        this.mItemTouchHelper.attachToRecyclerView(this.mRecyclerView);
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public void onNoteListChanged(List<DialogTab> customers) {
        AppPreferences.setDialogTabs(getApplicationContext(), new Gson().toJson(customers));
    }

    public void onStartDrag(ViewHolder viewHolder) {
        this.mItemTouchHelper.startDrag(viewHolder);
    }

    public void onEndDrag(ViewHolder viewHolder) {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                finish();
                return;
            case R.id.download:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(0)});
                finish();
                return;
            default:
                return;
        }
    }
}
