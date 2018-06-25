package org.telegram.customization.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.ir.talaeii.R;

public class ChooseTabStyleFragment extends Fragment implements OnClickListener {
    ImageView ivCheck;
    ImageView ivCheck1;
    RelativeLayout rlNoTab;
    RelativeLayout rlWithTab;
    boolean withTab;

    public boolean isWithTab() {
        return this.withTab;
    }

    public void setWithTab(boolean withTab) {
        this.withTab = withTab;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_tab_style, container, false);
        this.rlWithTab = (RelativeLayout) rootView.findViewById(R.id.rl_with_tab);
        this.rlNoTab = (RelativeLayout) rootView.findViewById(R.id.rl_no_tab);
        this.ivCheck = (ImageView) rootView.findViewById(R.id.iv_checked);
        this.ivCheck1 = (ImageView) rootView.findViewById(R.id.iv_checked1);
        this.rlWithTab.setOnClickListener(this);
        this.rlNoTab.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_with_tab:
                this.ivCheck.setImageResource(R.drawable.check_circle_green);
                this.ivCheck1.setImageResource(R.drawable.check_circle_gray);
                setWithTab(false);
                return;
            case R.id.rl_no_tab:
                this.ivCheck.setImageResource(R.drawable.check_circle_gray);
                this.ivCheck1.setImageResource(R.drawable.check_circle_green);
                setWithTab(true);
                return;
            default:
                return;
        }
    }
}
