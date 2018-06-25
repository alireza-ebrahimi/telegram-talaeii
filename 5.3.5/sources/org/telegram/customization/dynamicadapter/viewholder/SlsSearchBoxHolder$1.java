package org.telegram.customization.dynamicadapter.viewholder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

class SlsSearchBoxHolder$1 implements OnClickListener {
    final /* synthetic */ SlsSearchBoxHolder this$0;

    SlsSearchBoxHolder$1(SlsSearchBoxHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        Log.d("alireza", "alireza  search clicked");
        ((InputMethodManager) this.this$0.getActivity().getSystemService("input_method")).toggleSoftInput(2, 0);
        SlsSearchBoxHolder.access$000(this.this$0, false);
    }
}
