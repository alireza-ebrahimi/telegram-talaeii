package net.hockeyapp.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;
import net.hockeyapp.android.listeners.DownloadFileListener;
import net.hockeyapp.android.tasks.DownloadFileTask;
import net.hockeyapp.android.tasks.GetFileSizeTask;
import net.hockeyapp.android.utils.AsyncTaskUtils;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.VersionHelper;
import org.json.JSONArray;
import org.json.JSONException;

@TargetApi(11)
public class UpdateFragment extends DialogFragment implements OnClickListener, UpdateInfoListener {
    public static final String FRAGMENT_URL = "url";
    public static final String FRAGMENT_VERSION_INFO = "versionInfo";
    private DownloadFileTask mDownloadTask;
    private String mUrlString;
    private VersionHelper mVersionHelper;
    private JSONArray mVersionInfo;

    public static UpdateFragment newInstance(JSONArray versionInfo, String urlString) {
        Bundle arguments = new Bundle();
        arguments.putString("url", urlString);
        arguments.putString(FRAGMENT_VERSION_INFO, versionInfo.toString());
        UpdateFragment fragment = new UpdateFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mUrlString = getArguments().getString("url");
            this.mVersionInfo = new JSONArray(getArguments().getString(FRAGMENT_VERSION_INFO));
            setStyle(1, 16973939);
        } catch (JSONException e) {
            dismiss();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutView();
        this.mVersionHelper = new VersionHelper(getActivity(), this.mVersionInfo.toString(), this);
        ((TextView) view.findViewById(C0962R.id.label_title)).setText(getAppName());
        final TextView versionLabel = (TextView) view.findViewById(C0962R.id.label_version);
        String versionString = "Version " + this.mVersionHelper.getVersionString();
        final String fileDate = this.mVersionHelper.getFileDateString();
        String appSizeString = "Unknown size";
        if (this.mVersionHelper.getFileSizeBytes() >= 0) {
            appSizeString = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(((float) appSize) / 1048576.0f)}) + " MB";
        } else {
            final String str = versionString;
            AsyncTaskUtils.execute(new GetFileSizeTask(getActivity(), this.mUrlString, new DownloadFileListener() {
                public void downloadSuccessful(DownloadFileTask task) {
                    if (task instanceof GetFileSizeTask) {
                        long appSize = ((GetFileSizeTask) task).getSize();
                        String appSizeString = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(((float) appSize) / 1048576.0f)}) + " MB";
                        versionLabel.setText(UpdateFragment.this.getString(C0962R.string.hockeyapp_update_version_details_label, new Object[]{str, fileDate, appSizeString}));
                    }
                }
            }));
        }
        versionLabel.setText(getString(C0962R.string.hockeyapp_update_version_details_label, new Object[]{versionString, fileDate, appSizeString}));
        ((Button) view.findViewById(C0962R.id.button_update)).setOnClickListener(this);
        WebView webView = (WebView) view.findViewById(C0962R.id.web_update_details);
        webView.clearCache(true);
        webView.destroyDrawingCache();
        webView.loadDataWithBaseURL(Constants.BASE_URL, this.mVersionHelper.getReleaseNotes(false), "text/html", "utf-8", null);
        return view;
    }

    public void onClick(View view) {
        prepareDownload();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length != 0 && grantResults.length != 0 && requestCode == 1) {
            if (grantResults[0] == 0) {
                startDownloadTask(getActivity());
                return;
            }
            HockeyLog.warn("User denied write permission, can't continue with updater task.");
            UpdateManagerListener listener = UpdateManager.getLastListener();
            if (listener != null) {
                listener.onUpdatePermissionsNotGranted();
                return;
            }
            final UpdateFragment updateFragment = this;
            new Builder(getActivity()).setTitle(getString(C0962R.string.hockeyapp_permission_update_title)).setMessage(getString(C0962R.string.hockeyapp_permission_update_message)).setNegativeButton(getString(C0962R.string.hockeyapp_permission_dialog_negative_button), null).setPositiveButton(getString(C0962R.string.hockeyapp_permission_dialog_positive_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    updateFragment.prepareDownload();
                }
            }).create().show();
        }
    }

    public int getCurrentVersionCode() {
        int currentVersionCode = -1;
        try {
            return getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 128).versionCode;
        } catch (NameNotFoundException e) {
            return currentVersionCode;
        } catch (NullPointerException e2) {
            return currentVersionCode;
        }
    }

    public void prepareDownload() {
        if (VERSION.SDK_INT < 23 || getActivity().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            startDownloadTask(getActivity());
            dismiss();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
    }

    private void startDownloadTask(final Activity activity) {
        this.mDownloadTask = new DownloadFileTask(activity, this.mUrlString, new DownloadFileListener() {
            public void downloadFailed(DownloadFileTask task, Boolean userWantsRetry) {
                if (userWantsRetry.booleanValue()) {
                    UpdateFragment.this.startDownloadTask(activity);
                }
            }

            public void downloadSuccessful(DownloadFileTask task) {
            }
        });
        AsyncTaskUtils.execute(this.mDownloadTask);
    }

    public String getAppName() {
        Activity activity = getActivity();
        try {
            PackageManager pm = activity.getPackageManager();
            return pm.getApplicationLabel(pm.getApplicationInfo(activity.getPackageName(), 0)).toString();
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public View getLayoutView() {
        LinearLayout layout = new LinearLayout(getActivity());
        LayoutInflater.from(getActivity()).inflate(C0962R.layout.hockeyapp_fragment_update, layout);
        return layout;
    }
}
