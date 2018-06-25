package com.onesignal;

import com.onesignal.OneSignal.LOG_LEVEL;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.InputStream;
import java.lang.Thread.State;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

class OneSignalRestClient {
    private static final String BASE_URL = "https://onesignal.com/api/v1/";
    private static final int GET_TIMEOUT = 60000;
    private static final int TIMEOUT = 120000;

    static class ResponseHandler {
        ResponseHandler() {
        }

        void onSuccess(String response) {
        }

        void onFailure(int statusCode, String response, Throwable throwable) {
        }
    }

    OneSignalRestClient() {
    }

    private static int getThreadTimeout(int timeout) {
        return timeout + 5000;
    }

    static void put(final String url, final JSONObject jsonBody, final ResponseHandler responseHandler) {
        new Thread(new Runnable() {
            public void run() {
                OneSignalRestClient.makeRequest(url, HttpRequest.METHOD_PUT, jsonBody, responseHandler, OneSignalRestClient.TIMEOUT);
            }
        }).start();
    }

    static void post(final String url, final JSONObject jsonBody, final ResponseHandler responseHandler) {
        new Thread(new Runnable() {
            public void run() {
                OneSignalRestClient.makeRequest(url, HttpRequest.METHOD_POST, jsonBody, responseHandler, OneSignalRestClient.TIMEOUT);
            }
        }).start();
    }

    static void get(final String url, final ResponseHandler responseHandler) {
        new Thread(new Runnable() {
            public void run() {
                OneSignalRestClient.makeRequest(url, null, null, responseHandler, OneSignalRestClient.GET_TIMEOUT);
            }
        }).start();
    }

    static void getSync(String url, ResponseHandler responseHandler) {
        makeRequest(url, null, null, responseHandler, GET_TIMEOUT);
    }

    static void putSync(String url, JSONObject jsonBody, ResponseHandler responseHandler) {
        makeRequest(url, HttpRequest.METHOD_PUT, jsonBody, responseHandler, TIMEOUT);
    }

    static void postSync(String url, JSONObject jsonBody, ResponseHandler responseHandler) {
        makeRequest(url, HttpRequest.METHOD_POST, jsonBody, responseHandler, TIMEOUT);
    }

    private static void makeRequest(String url, String method, JSONObject jsonBody, ResponseHandler responseHandler, int timeout) {
        final Thread[] callbackThread = new Thread[1];
        final String str = url;
        final String str2 = method;
        final JSONObject jSONObject = jsonBody;
        final ResponseHandler responseHandler2 = responseHandler;
        final int i = timeout;
        Thread connectionThread = new Thread(new Runnable() {
            public void run() {
                callbackThread[0] = OneSignalRestClient.startHTTPConnection(str, str2, jSONObject, responseHandler2, i);
            }
        }, "OS_HTTPConnection");
        connectionThread.start();
        try {
            connectionThread.join((long) getThreadTimeout(timeout));
            if (connectionThread.getState() != State.TERMINATED) {
                connectionThread.interrupt();
            }
            if (callbackThread[0] != null) {
                callbackThread[0].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread startHTTPConnection(String url, String method, JSONObject jsonBody, ResponseHandler responseHandler, int timeout) {
        Thread callbackThread;
        HttpURLConnection con = null;
        int httpResponse = -1;
        String json = null;
        try {
            OneSignal.Log(LOG_LEVEL.DEBUG, "OneSignalRestClient: Making request to: https://onesignal.com/api/v1/" + url);
            con = (HttpURLConnection) new URL(BASE_URL + url).openConnection();
            con.setUseCaches(false);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            if (jsonBody != null) {
                con.setDoInput(true);
            }
            if (method != null) {
                con.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=UTF-8");
                con.setRequestMethod(method);
                con.setDoOutput(true);
            }
            if (jsonBody != null) {
                String strJsonBody = jsonBody.toString();
                OneSignal.Log(LOG_LEVEL.DEBUG, "OneSignalRestClient: " + method + " SEND JSON: " + strJsonBody);
                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                con.setFixedLengthStreamingMode(sendBytes.length);
                con.getOutputStream().write(sendBytes);
            }
            httpResponse = con.getResponseCode();
            OneSignal.Log(LOG_LEVEL.VERBOSE, "OneSignalRestClient: After con.getResponseCode  to: https://onesignal.com/api/v1/" + url);
            Scanner scanner;
            if (httpResponse == 200) {
                OneSignal.Log(LOG_LEVEL.DEBUG, "OneSignalRestClient: Successfully finished request to: https://onesignal.com/api/v1/" + url);
                scanner = new Scanner(con.getInputStream(), "UTF-8");
                json = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
                OneSignal.Log(LOG_LEVEL.DEBUG, method + " RECEIVED JSON: " + json);
                callbackThread = callResponseHandlerOnSuccess(responseHandler, json);
            } else {
                OneSignal.Log(LOG_LEVEL.DEBUG, "OneSignalRestClient: Failed request to: https://onesignal.com/api/v1/" + url);
                InputStream inputStream = con.getErrorStream();
                if (inputStream == null) {
                    inputStream = con.getInputStream();
                }
                if (inputStream != null) {
                    scanner = new Scanner(inputStream, "UTF-8");
                    json = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    OneSignal.Log(LOG_LEVEL.WARN, "OneSignalRestClient: " + method + " RECEIVED JSON: " + json);
                } else {
                    OneSignal.Log(LOG_LEVEL.WARN, "OneSignalRestClient: " + method + " HTTP Code: " + httpResponse + " No response body!");
                }
                callbackThread = callResponseHandlerOnFailure(responseHandler, httpResponse, json, null);
            }
            if (con != null) {
                con.disconnect();
            }
        } catch (Throwable th) {
            if (con != null) {
                con.disconnect();
            }
        }
        return callbackThread;
    }

    private static Thread callResponseHandlerOnSuccess(final ResponseHandler handler, final String response) {
        if (handler == null) {
            return null;
        }
        Thread thread = new Thread(new Runnable() {
            public void run() {
                handler.onSuccess(response);
            }
        });
        thread.start();
        return thread;
    }

    private static Thread callResponseHandlerOnFailure(final ResponseHandler handler, final int statusCode, final String response, final Throwable throwable) {
        if (handler == null) {
            return null;
        }
        Thread thread = new Thread(new Runnable() {
            public void run() {
                handler.onFailure(statusCode, response, throwable);
            }
        });
        thread.start();
        return thread;
    }
}
