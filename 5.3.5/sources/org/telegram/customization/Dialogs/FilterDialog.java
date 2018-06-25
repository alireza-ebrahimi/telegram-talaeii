package org.telegram.customization.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.FilterItemAdapter;
import org.telegram.customization.Interfaces.FilterChangeListener;
import org.telegram.customization.Model.FilterHelper;
import org.telegram.customization.Model.FilterItem;
import org.telegram.ui.ActionBar.Theme;

public class FilterDialog extends Dialog {
    Activity activity;
    FilterItemAdapter adapter;
    Button btnClear;
    Button btnFilterSubmit;
    View filterButtonContainer;
    FilterHelper filterHelper = new FilterHelper();
    ArrayList<FilterItem> items = new ArrayList();
    FilterChangeListener listener;
    View pbLoading;
    RecyclerView recyclerView;
    String title;
    TextView tvHeaderTitle;
    int type;
    View vClose;
    View vHeader;

    public FilterDialog(@NonNull Activity activity, String title, int type, ArrayList<FilterItem> filterItems, FilterChangeListener changeListener) {
        super(activity);
        this.activity = activity;
        this.title = title;
        this.type = type;
        this.items = filterItems;
        this.listener = changeListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_filter);
        this.tvHeaderTitle = (TextView) findViewById(R.id.tv_header);
        this.vHeader = findViewById(R.id.v_header);
        this.vHeader.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.filterButtonContainer = findViewById(R.id.ll_btn_container);
        this.btnFilterSubmit = (Button) findViewById(R.id.btn_submit_filter);
        this.btnClear = (Button) findViewById(R.id.btn_cancel);
        this.pbLoading = findViewById(R.id.pb_loading);
        this.vClose = findViewById(R.id.v_close);
        this.adapter = new FilterItemAdapter(this, this.items, this.listener);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.activity, 1, false));
        this.recyclerView.setAdapter(this.adapter);
        this.tvHeaderTitle.setText(this.title);
        this.pbLoading.setVisibility(8);
        this.recyclerView.setVisibility(0);
        if (this.type == 3) {
            this.filterButtonContainer.setVisibility(8);
        }
    }
}
