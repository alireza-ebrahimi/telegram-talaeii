package org.telegram.customization.util;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import utils.view.TitleTextView;

class MaterialSpinner$HintAdapter extends BaseAdapter {
    private static final int HINT_TYPE = -1;
    private Context mContext;
    private SpinnerAdapter mSpinnerAdapter;
    final /* synthetic */ MaterialSpinner this$0;

    public MaterialSpinner$HintAdapter(MaterialSpinner materialSpinner, SpinnerAdapter spinnerAdapter, Context context) {
        this.this$0 = materialSpinner;
        this.mSpinnerAdapter = spinnerAdapter;
        this.mContext = context;
    }

    public int getViewTypeCount() {
        if (VERSION.SDK_INT >= 21) {
            return 1;
        }
        return this.mSpinnerAdapter.getViewTypeCount();
    }

    public int getItemViewType(int position) {
        if (MaterialSpinner.access$100(this.this$0) != null) {
            position--;
        }
        if (position == -1) {
            return -1;
        }
        return this.mSpinnerAdapter.getItemViewType(position);
    }

    public int getCount() {
        int count = this.mSpinnerAdapter.getCount();
        return MaterialSpinner.access$100(this.this$0) != null ? count + 1 : count;
    }

    public Object getItem(int position) {
        if (MaterialSpinner.access$100(this.this$0) != null) {
            position--;
        }
        return position == -1 ? MaterialSpinner.access$100(this.this$0) : this.mSpinnerAdapter.getItem(position);
    }

    public long getItemId(int position) {
        if (MaterialSpinner.access$100(this.this$0) != null) {
            position--;
        }
        return position == -1 ? 0 : this.mSpinnerAdapter.getItemId(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return buildView(position, convertView, parent, false);
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return buildView(position, convertView, parent, true);
    }

    private View buildView(int position, View convertView, ViewGroup parent, boolean isDropDownView) {
        if (getItemViewType(position) == -1) {
            return getHintView(convertView, parent, isDropDownView);
        }
        if (convertView != null && (convertView.getTag() == null || !(convertView.getTag() instanceof Integer) || ((Integer) convertView.getTag()).intValue() == -1)) {
            convertView = null;
        }
        if (MaterialSpinner.access$100(this.this$0) != null) {
            position--;
        }
        return isDropDownView ? this.mSpinnerAdapter.getDropDownView(position, convertView, parent) : this.mSpinnerAdapter.getView(position, convertView, parent);
    }

    private View getHintView(View convertView, ViewGroup parent, boolean isDropDownView) {
        TextView textView = (TextView) LayoutInflater.from(this.mContext).inflate(isDropDownView ? 17367049 : 17367048, parent, false);
        textView.setTypeface(TitleTextView.getTypeface(this.this$0.getContext()));
        textView.setText(MaterialSpinner.access$100(this.this$0));
        textView.setTextColor(this.this$0.isEnabled() ? MaterialSpinner.access$900(this.this$0) : MaterialSpinner.access$1000(this.this$0));
        textView.setTag(Integer.valueOf(-1));
        return textView;
    }

    private SpinnerAdapter getWrappedAdapter() {
        return this.mSpinnerAdapter;
    }
}
