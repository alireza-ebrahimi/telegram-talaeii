package org.telegram.customization.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

class MaterialSpinner$2 implements OnItemSelectedListener {
    final /* synthetic */ MaterialSpinner this$0;
    final /* synthetic */ OnItemSelectedListener val$listener;

    MaterialSpinner$2(MaterialSpinner this$0, OnItemSelectedListener onItemSelectedListener) {
        this.this$0 = this$0;
        this.val$listener = onItemSelectedListener;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!(MaterialSpinner.access$100(this.this$0) == null && MaterialSpinner.access$200(this.this$0) == null)) {
            if (!MaterialSpinner.access$300(this.this$0) && position != 0) {
                MaterialSpinner.access$400(this.this$0);
            } else if (MaterialSpinner.access$300(this.this$0) && position == 0) {
                MaterialSpinner.access$500(this.this$0);
            }
        }
        if (!(position == MaterialSpinner.access$600(this.this$0) || MaterialSpinner.access$700(this.this$0) == null)) {
            this.this$0.setError(null);
        }
        MaterialSpinner.access$602(this.this$0, position);
        if (this.val$listener != null) {
            if (MaterialSpinner.access$100(this.this$0) != null) {
                position--;
            }
            this.val$listener.onItemSelected(parent, view, position, id);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        if (this.val$listener != null) {
            this.val$listener.onNothingSelected(parent);
        }
    }
}
