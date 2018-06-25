package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.util.Constants;
import org.telegram.customization.util.Prefs;
import org.telegram.customization.util.view.sva.JJSearchView;
import org.telegram.customization.util.view.sva.anim.controller.JJChangeArrowController;

@ViewHolderType(model = ObjBase.class, type = 105)
public class SlsSearchBoxHolder extends HolderBase {
    EditText etTerm = ((EditText) findViewById(R.id.et_term));
    String lastTerm;
    RelativeLayout llRoot = ((RelativeLayout) findViewById(R.id.ll_root));
    JJSearchView mJJSearchView = ((JJSearchView) findViewById(R.id.jjsv));
    private TextWatcher textWatcher;

    public SlsSearchBoxHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter adapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_holder, adapter, extraData);
        this.mJJSearchView.setController(new JJChangeArrowController());
        this.mJJSearchView.setOnClickListener(new SlsSearchBoxHolder$1(this));
    }

    public void onBind(ObjBase obj) {
        this.etTerm.setHint(obj.getTitle());
        String tmp = Prefs.getSearchTerm(getActivity());
        if (tmp == null) {
            tmp = "";
        }
        this.etTerm.setText(tmp);
        this.etTerm.setOnEditorActionListener(new SlsSearchBoxHolder$2(this));
        if (this.textWatcher == null) {
            this.textWatcher = new SlsSearchBoxHolder$3(this);
            this.etTerm.addTextChangedListener(this.textWatcher);
        }
    }

    public void itemClicked(ObjBase obj) {
    }

    private void sendBroadcast(boolean justScrollToTop) {
        Intent intent = new Intent(Constants.ACTION_SEARCH);
        intent.putExtra(utils.view.Constants.EXTRA_SCROLL_TO_TOP, justScrollToTop);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void sendBroadcast(boolean justScrollToTop, boolean keepRequestFocus) {
        Intent intent = new Intent(Constants.ACTION_SEARCH);
        intent.putExtra(utils.view.Constants.EXTRA_SCROLL_TO_TOP, justScrollToTop);
        intent.putExtra(utils.view.Constants.EXTRA_REQUEST_FOCUS, keepRequestFocus);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
