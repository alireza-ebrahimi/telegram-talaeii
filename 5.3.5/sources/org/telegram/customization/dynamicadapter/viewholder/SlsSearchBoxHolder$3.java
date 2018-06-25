package org.telegram.customization.dynamicadapter.viewholder;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import org.telegram.customization.util.Prefs;

class SlsSearchBoxHolder$3 implements TextWatcher {
    final /* synthetic */ SlsSearchBoxHolder this$0;

    SlsSearchBoxHolder$3(SlsSearchBoxHolder this$0) {
        this.this$0 = this$0;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean isFake = false;
        if (!(TextUtils.isEmpty(Prefs.getSearchTerm(this.this$0.getActivity())) || TextUtils.isEmpty(s) || Prefs.getSearchTerm(this.this$0.getActivity()).length() != s.length())) {
            isFake = true;
        }
        Prefs.setSearchTerm(s.toString(), this.this$0.getActivity());
        if (this.this$0.lastTerm == null || s == null || !this.this$0.lastTerm.equals(s.toString()) || isFake) {
            this.this$0.lastTerm = s.toString();
            if (s.length() == 0) {
                this.this$0.mJJSearchView.resetAnim();
                SlsSearchBoxHolder.access$000(this.this$0, false);
                return;
            }
            this.this$0.mJJSearchView.startAnim();
            return;
        }
        SlsSearchBoxHolder.access$000(this.this$0, true);
    }

    public void afterTextChanged(Editable s) {
    }
}
