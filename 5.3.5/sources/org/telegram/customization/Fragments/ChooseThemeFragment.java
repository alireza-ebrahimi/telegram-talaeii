package org.telegram.customization.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.ThemeListAdapter;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.messenger.ApplicationLoader;
import utils.view.Constants;

public class ChooseThemeFragment extends Fragment implements IResponseReceiver, OnItemClickListener {
    ThemeListAdapter adapter;
    boolean listFilled = false;
    ProgressBar pbLoading;
    RecyclerView recyclerView;
    View retry;
    String themeName;
    ArrayList<HotgramTheme> themes = new ArrayList();

    /* renamed from: org.telegram.customization.Fragments.ChooseThemeFragment$1 */
    class C11251 implements OnClickListener {
        C11251() {
        }

        public void onClick(View view) {
            ChooseThemeFragment.this.pbLoading.setVisibility(0);
            ChooseThemeFragment.this.retry.setVisibility(8);
            HandleRequest.getNew(ApplicationLoader.applicationContext, ChooseThemeFragment.this).getThemes();
        }
    }

    public String getThemeName() {
        return this.themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_theme, container, false);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        this.recyclerView.setLayoutManager(new GridLayoutManager(ApplicationLoader.applicationContext, 2));
        this.pbLoading = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        this.retry = rootView.findViewById(R.id.btn_retry);
        HandleRequest.getNew(ApplicationLoader.applicationContext, this).getThemes();
        this.retry.setOnClickListener(new C11251());
        return rootView;
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case Constants.ERROR_GET_THEMES /*-26*/:
                if (!this.listFilled) {
                    this.pbLoading.setVisibility(8);
                    this.retry.setVisibility(0);
                    return;
                }
                return;
            case 26:
                this.pbLoading.setVisibility(8);
                this.themes = (ArrayList) object;
                if (this.themes != null && this.themes.size() > 0) {
                    this.adapter = new ThemeListAdapter(this.themes, this, this.recyclerView);
                    this.recyclerView.setAdapter(this.adapter);
                    this.listFilled = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        for (int j = 0; j < this.themes.size(); j++) {
            if (((HotgramTheme) this.themes.get(j)).isSelected()) {
                ((HotgramTheme) this.themes.get(j)).setSelected(false);
                this.adapter.notifyItemChanged(j);
                break;
            }
        }
        if (!((HotgramTheme) this.themes.get(i)).isSelected()) {
            ((HotgramTheme) this.themes.get(i)).setSelected(true);
            setThemeName(((HotgramTheme) this.themes.get(i)).getName());
        }
        this.adapter.notifyItemChanged(i);
    }
}
