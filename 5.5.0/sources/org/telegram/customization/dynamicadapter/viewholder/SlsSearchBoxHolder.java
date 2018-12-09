package org.telegram.customization.dynamicadapter.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.C0424l;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.annotations.ViewHolderType;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.util.C2885i;
import org.telegram.customization.util.view.sva.JJSearchView;
import org.telegram.customization.util.view.sva.p173a.p174a.C2961a;

@ViewHolderType(model = ObjBase.class, type = 105)
public class SlsSearchBoxHolder extends HolderBase {
    EditText etTerm = ((EditText) findViewById(R.id.et_term));
    String lastTerm;
    RelativeLayout llRoot = ((RelativeLayout) findViewById(R.id.ll_root));
    JJSearchView mJJSearchView = ((JJSearchView) findViewById(R.id.jjsv));
    private TextWatcher textWatcher;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsSearchBoxHolder$1 */
    class C27091 implements OnClickListener {
        C27091() {
        }

        public void onClick(View view) {
            Log.d("alireza", "alireza  search clicked");
            ((InputMethodManager) SlsSearchBoxHolder.this.getActivity().getSystemService("input_method")).toggleSoftInput(2, 0);
            SlsSearchBoxHolder.this.sendBroadcast(false);
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsSearchBoxHolder$2 */
    class C27102 implements OnEditorActionListener {
        C27102() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 3) {
                return false;
            }
            ((InputMethodManager) SlsSearchBoxHolder.this.getActivity().getSystemService("input_method")).toggleSoftInput(2, 0);
            SlsSearchBoxHolder.this.sendBroadcast(false);
            return true;
        }
    }

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsSearchBoxHolder$3 */
    class C27113 implements TextWatcher {
        C27113() {
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            boolean z = (TextUtils.isEmpty(C2885i.m13388h(SlsSearchBoxHolder.this.getActivity())) || TextUtils.isEmpty(charSequence) || C2885i.m13388h(SlsSearchBoxHolder.this.getActivity()).length() != charSequence.length()) ? false : true;
            C2885i.m13376a(charSequence.toString(), SlsSearchBoxHolder.this.getActivity());
            if (SlsSearchBoxHolder.this.lastTerm == null || charSequence == null || !SlsSearchBoxHolder.this.lastTerm.equals(charSequence.toString()) || z) {
                SlsSearchBoxHolder.this.lastTerm = charSequence.toString();
                if (charSequence.length() == 0) {
                    SlsSearchBoxHolder.this.mJJSearchView.m13649b();
                    SlsSearchBoxHolder.this.sendBroadcast(false);
                    return;
                }
                SlsSearchBoxHolder.this.mJJSearchView.m13647a();
                return;
            }
            SlsSearchBoxHolder.this.sendBroadcast(true);
        }
    }

    public SlsSearchBoxHolder(Activity activity, ViewGroup viewGroup, DynamicAdapter dynamicAdapter, ExtraData extraData) {
        super(activity, viewGroup, R.layout.sls_search_holder, dynamicAdapter, extraData);
        this.mJJSearchView.setController(new C2961a());
        this.mJJSearchView.setOnClickListener(new C27091());
    }

    private void sendBroadcast(boolean z) {
        Intent intent = new Intent("ACTION_SEARCH");
        intent.putExtra("EXTRA_SCROLL_TO_TOP", z);
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    private void sendBroadcast(boolean z, boolean z2) {
        Intent intent = new Intent("ACTION_SEARCH");
        intent.putExtra("EXTRA_SCROLL_TO_TOP", z);
        intent.putExtra("EXTRA_REQUEST_FOCUS", z2);
        C0424l.m1899a(getActivity()).m1904a(intent);
    }

    public void itemClicked(ObjBase objBase) {
    }

    public void onBind(ObjBase objBase) {
        this.etTerm.setHint(objBase.getTitle());
        CharSequence h = C2885i.m13388h(getActivity());
        if (h == null) {
            h = TtmlNode.ANONYMOUS_REGION_ID;
        }
        this.etTerm.setText(h);
        this.etTerm.setOnEditorActionListener(new C27102());
        if (this.textWatcher == null) {
            this.textWatcher = new C27113();
            this.etTerm.addTextChangedListener(this.textWatcher);
        }
    }
}
