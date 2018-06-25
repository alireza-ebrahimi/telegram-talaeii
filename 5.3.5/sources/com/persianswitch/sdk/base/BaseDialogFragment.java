package com.persianswitch.sdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class BaseDialogFragment extends DialogFragment {

    public interface BaseDialogInterface {
    }

    protected abstract int getLayoutResourceId();

    protected abstract void onFragmentCreated(View view, Bundle bundle);

    public abstract void onRecoverInstanceState(Bundle bundle);

    public abstract void onSaveInstanceState(Bundle bundle);

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResourceId(), container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onFragmentCreated(view, savedInstanceState);
        onRecoverInstanceState(savedInstanceState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onRecoverInstanceState(savedInstanceState);
    }

    @Nullable
    public BaseDialogInterface getCallback() {
        if (getTargetFragment() instanceof BaseDialogInterface) {
            return (BaseDialogInterface) getTargetFragment();
        }
        if (getActivity() instanceof BaseDialogInterface) {
            return (BaseDialogInterface) getActivity();
        }
        return null;
    }

    public void showAllowStateLoss(FragmentManager manager, String tag) {
        manager.beginTransaction().add((Fragment) this, tag).commitAllowingStateLoss();
    }

    public static void dismissAllDialogs(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof DialogFragment) {
                    ((DialogFragment) fragment).dismissAllowingStateLoss();
                }
            }
        }
    }
}
