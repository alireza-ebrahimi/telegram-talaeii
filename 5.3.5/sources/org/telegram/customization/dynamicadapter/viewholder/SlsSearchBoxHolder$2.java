package org.telegram.customization.dynamicadapter.viewholder;

import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

class SlsSearchBoxHolder$2 implements OnEditorActionListener {
    final /* synthetic */ SlsSearchBoxHolder this$0;

    SlsSearchBoxHolder$2(SlsSearchBoxHolder this$0) {
        this.this$0 = this$0;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId != 3) {
            return false;
        }
        ((InputMethodManager) this.this$0.getActivity().getSystemService("input_method")).toggleSoftInput(2, 0);
        SlsSearchBoxHolder.access$000(this.this$0, false);
        return true;
    }
}
