package org.telegram.customization.fetch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.customization.fetch.callback.FetchTask;
import org.telegram.customization.fetch.exception.InvalidStatusException;
import org.telegram.customization.fetch.exception.NotUsableException;
import org.telegram.customization.fetch.request.Header;
import org.telegram.customization.fetch.request.RequestInfo;

final class Utils {
    private Utils() {
    }

    static void throwIfFetchTaskNull(FetchTask fetchTask) {
        if (fetchTask == null) {
            throw new NullPointerException("FetchTask cannot be null");
        }
    }

    static void throwIfInvalidStatus(int status) {
        switch (status) {
            case FetchConst.STATUS_NOT_QUEUED /*-900*/:
            case FetchConst.STATUS_QUEUED /*900*/:
            case FetchConst.STATUS_DOWNLOADING /*901*/:
            case FetchConst.STATUS_PAUSED /*902*/:
            case FetchConst.STATUS_DONE /*903*/:
            case FetchConst.STATUS_ERROR /*904*/:
            case FetchConst.STATUS_REMOVED /*905*/:
                return;
            default:
                throw new InvalidStatusException(status + " is not a valid status ", -114);
        }
    }

    static boolean hasIntervalElapsed(long startTime, long stopTime, long onUpdateInterval) {
        if (TimeUnit.NANOSECONDS.toMillis(stopTime - startTime) >= onUpdateInterval) {
            return true;
        }
        return false;
    }

    static int getProgress(long downloadedBytes, long fileSize) {
        if (fileSize < 1 || downloadedBytes < 1) {
            return 0;
        }
        if (downloadedBytes >= fileSize) {
            return 100;
        }
        return (int) ((((double) downloadedBytes) / ((double) fileSize)) * 100.0d);
    }

    static String headerListToString(List<Header> headers, boolean loggingEnabled) {
        if (headers == null) {
            return "{}";
        }
        try {
            JSONObject headerObject = new JSONObject();
            for (Header header : headers) {
                headerObject.put(header.getHeader(), header.getValue());
            }
            return headerObject.toString();
        } catch (JSONException e) {
            if (loggingEnabled) {
                e.printStackTrace();
            }
            return "{}";
        }
    }

    static List<Header> headerStringToList(String headers, boolean loggingEnabled) {
        List<Header> headerList = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(headers);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                headerList.add(new Header(key, jsonObject.getString(key)));
            }
        } catch (JSONException e) {
            if (loggingEnabled) {
                e.printStackTrace();
            }
        }
        return headerList;
    }

    static boolean isOnWiFi(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        if (activeNetworkInfo.getType() == 1) {
            return true;
        }
        return false;
    }

    static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static boolean createFileIfNotExist(String path) throws IOException, NullPointerException {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.createNewFile();
    }

    static boolean createDirIfNotExist(String path) throws NullPointerException {
        File dir = new File(path);
        if (dir.exists()) {
            return true;
        }
        return dir.mkdirs();
    }

    static boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    static long getFileSize(String filePath) {
        return new File(filePath).length();
    }

    static boolean fileExist(String filePath) {
        return new File(filePath).exists();
    }

    static File getFile(String filePath) {
        return new File(filePath);
    }

    static void createFileOrThrow(String filePath) throws IOException, NullPointerException {
        File file = getFile(filePath);
        boolean parentDirCreated = createDirIfNotExist(file.getParentFile().getAbsolutePath());
        boolean fileCreated = createFileIfNotExist(file.getAbsolutePath());
        if (!parentDirCreated || !fileCreated) {
            throw new IOException("File could not be created for the filePath:" + filePath);
        }
    }

    static void throwIfNotUsable(Fetch fetch) {
        if (fetch == null) {
            throw new NullPointerException("Fetch cannot be null");
        } else if (fetch.isReleased()) {
            throw new NotUsableException("Fetch instance: " + fetch.toString() + " cannot be reused after calling its release() method." + "Call Fetch.getInstance() for a new instance of Fetch.", -115);
        }
    }

    static RequestInfo cursorToRequestInfo(Cursor cursor, boolean closeCursor, boolean loggingEnabled) {
        RequestInfo requestInfo = null;
        if (cursor != null) {
            try {
                if (!cursor.isClosed() && cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    requestInfo = createRequestInfo(cursor, loggingEnabled);
                    if (cursor != null && closeCursor) {
                        cursor.close();
                    }
                    return requestInfo;
                }
            } catch (Exception e) {
                if (loggingEnabled) {
                    e.printStackTrace();
                }
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && closeCursor) {
            cursor.close();
        }
        return requestInfo;
    }

    static List<RequestInfo> cursorToRequestInfoList(Cursor cursor, boolean closeCursor, boolean loggingEnabled) {
        List<RequestInfo> requests = new ArrayList();
        if (cursor != null) {
            try {
                if (!cursor.isClosed() && cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        requests.add(createRequestInfo(cursor, loggingEnabled));
                        cursor.moveToNext();
                    }
                    if (cursor != null && closeCursor) {
                        cursor.close();
                    }
                    return requests;
                }
            } catch (Exception e) {
                if (loggingEnabled) {
                    e.printStackTrace();
                }
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && closeCursor) {
            cursor.close();
        }
        return requests;
    }

    static RequestInfo createRequestInfo(Cursor cursor, boolean loggingEnabled) {
        if (cursor == null || cursor.isClosed() || cursor.getCount() < 1) {
            return null;
        }
        long id = cursor.getLong(0);
        int status = cursor.getInt(3);
        String url = cursor.getString(1);
        String filePath = cursor.getString(2);
        int error = cursor.getInt(7);
        long fileSize = cursor.getLong(6);
        int priority = cursor.getInt(8);
        long downloadedBytes = cursor.getLong(5);
        return new RequestInfo(id, status, url, filePath, getProgress(downloadedBytes, fileSize), downloadedBytes, fileSize, error, headerStringToList(cursor.getString(4), loggingEnabled), priority);
    }

    static ArrayList<Bundle> cursorToQueryResultList(Cursor cursor, boolean closeCursor, boolean loggingEnabled) {
        ArrayList<Bundle> requests = new ArrayList();
        if (cursor != null) {
            try {
                if (!cursor.isClosed()) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        long id = cursor.getLong(0);
                        int status = cursor.getInt(3);
                        String url = cursor.getString(1);
                        String filePath = cursor.getString(2);
                        int error = cursor.getInt(7);
                        long fileSize = cursor.getLong(6);
                        int priority = cursor.getInt(8);
                        long downloadedBytes = cursor.getLong(5);
                        ArrayList<Bundle> headersList = headersToBundleList(cursor.getString(4), loggingEnabled);
                        int progress = getProgress(downloadedBytes, fileSize);
                        Bundle bundle = new Bundle();
                        bundle.putLong(FetchService.EXTRA_ID, id);
                        bundle.putInt(FetchService.EXTRA_STATUS, status);
                        bundle.putString(FetchService.EXTRA_URL, url);
                        bundle.putString(FetchService.EXTRA_FILE_PATH, filePath);
                        bundle.putInt(FetchService.EXTRA_ERROR, error);
                        bundle.putLong(FetchService.EXTRA_DOWNLOADED_BYTES, downloadedBytes);
                        bundle.putLong(FetchService.EXTRA_FILE_SIZE, fileSize);
                        bundle.putInt(FetchService.EXTRA_PROGRESS, progress);
                        bundle.putInt(FetchService.EXTRA_PRIORITY, priority);
                        bundle.putParcelableArrayList(FetchService.EXTRA_HEADERS, headersList);
                        requests.add(bundle);
                        cursor.moveToNext();
                    }
                    if (cursor != null && closeCursor) {
                        cursor.close();
                    }
                    return requests;
                }
            } catch (Exception e) {
                if (loggingEnabled) {
                    e.printStackTrace();
                }
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && closeCursor) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && closeCursor) {
            cursor.close();
        }
        return requests;
    }

    static long generateRequestId() {
        return System.nanoTime();
    }

    static void sendEventUpdate(LocalBroadcastManager broadcastManager, long id, int status, int progress, long downloadedBytes, long fileSize, int error) {
        if (broadcastManager != null) {
            Intent intent = new Intent(FetchService.EVENT_ACTION_UPDATE);
            intent.putExtra(FetchService.EXTRA_ID, id);
            intent.putExtra(FetchService.EXTRA_STATUS, status);
            intent.putExtra(FetchService.EXTRA_PROGRESS, progress);
            intent.putExtra(FetchService.EXTRA_DOWNLOADED_BYTES, downloadedBytes);
            intent.putExtra(FetchService.EXTRA_FILE_SIZE, fileSize);
            intent.putExtra(FetchService.EXTRA_ERROR, error);
            broadcastManager.sendBroadcast(intent);
        }
    }

    static ArrayList<Bundle> headersToBundleList(String headers, boolean loggingEnabled) {
        ArrayList<Bundle> headerList = new ArrayList();
        if (headers != null) {
            try {
                JSONObject jsonObject = new JSONObject(headers);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Bundle bundle = new Bundle();
                    bundle.putString(FetchService.EXTRA_HEADER_NAME, key);
                    bundle.putString(FetchService.EXTRA_HEADER_VALUE, jsonObject.getString(key));
                    headerList.add(bundle);
                }
            } catch (JSONException e) {
                if (loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return headerList;
    }

    static String bundleListToHeaderString(List<Bundle> headers, boolean loggingEnabled) {
        if (headers == null) {
            return "{}";
        }
        JSONObject headerObject = new JSONObject();
        try {
            for (Bundle headerBundle : headers) {
                String headerName = headerBundle.getString(FetchService.EXTRA_HEADER_NAME);
                String headerValue = headerBundle.getString(FetchService.EXTRA_HEADER_VALUE);
                if (headerValue == null) {
                    headerValue = "";
                }
                if (headerName != null) {
                    headerObject.put(headerName, headerValue);
                }
            }
            return headerObject.toString();
        } catch (JSONException e) {
            if (loggingEnabled) {
                e.printStackTrace();
            }
            return "{}";
        }
    }

    static boolean containsRequest(Cursor cursor, boolean closeCursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            return false;
        }
        if (closeCursor) {
            cursor.close();
        }
        return true;
    }

    static void throwIfInvalidUrl(String url) {
        String scheme = Uri.parse(url).getScheme();
        if (scheme == null || !(scheme.equals("http") || scheme.equals("https"))) {
            throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + url);
        }
    }
}
