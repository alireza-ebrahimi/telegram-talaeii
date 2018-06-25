package org.telegram.messenger;

import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC.User;

public class WearDataLayerListenerService extends WearableListenerService {
    public void onCreate() {
        super.onCreate();
        FileLog.d("WearableDataLayer service created");
    }

    public void onDestroy() {
        super.onDestroy();
        FileLog.d("WearableDataLayer service destroyed");
    }

    public void onChannelOpened(Channel ch) {
        GoogleApiClient apiClient = new Builder(this).addApi(Wearable.API).build();
        if (apiClient.blockingConnect().isSuccess()) {
            String path = ch.getPath();
            FileLog.d("wear channel path: " + path);
            try {
                DataOutputStream out;
                final CyclicBarrier barrier;
                final NotificationCenterDelegate listener;
                if ("/getCurrentUser".equals(path)) {
                    out = new DataOutputStream(new BufferedOutputStream(((GetOutputStreamResult) ch.getOutputStream(apiClient).await()).getOutputStream()));
                    if (UserConfig.isClientActivated()) {
                        final User user = UserConfig.getCurrentUser();
                        out.writeInt(user.id);
                        out.writeUTF(user.first_name);
                        out.writeUTF(user.last_name);
                        out.writeUTF(user.phone);
                        if (user.photo != null) {
                            final File photo = FileLoader.getPathToAttach(user.photo.photo_small, true);
                            barrier = new CyclicBarrier(2);
                            if (!photo.exists()) {
                                listener = new NotificationCenterDelegate() {
                                    public void didReceivedNotification(int id, Object... args) {
                                        if (id == NotificationCenter.FileDidLoaded) {
                                            FileLog.d("file loaded: " + args[0] + " " + args[0].getClass().getName());
                                            if (args[0].equals(photo.getName())) {
                                                FileLog.e("LOADED USER PHOTO");
                                                try {
                                                    barrier.await(10, TimeUnit.MILLISECONDS);
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                    }
                                };
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        NotificationCenter.getInstance().addObserver(listener, NotificationCenter.FileDidLoaded);
                                        FileLoader.getInstance().loadFile(user.photo.photo_small, null, 0, 1);
                                    }
                                });
                                try {
                                    barrier.await(10, TimeUnit.SECONDS);
                                } catch (Exception e) {
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        NotificationCenter.getInstance().removeObserver(listener, NotificationCenter.FileDidLoaded);
                                    }
                                });
                            }
                            if (!photo.exists() || photo.length() > 52428800) {
                                out.writeInt(0);
                            } else {
                                byte[] photoData = new byte[((int) photo.length())];
                                FileInputStream photoIn = new FileInputStream(photo);
                                new DataInputStream(photoIn).readFully(photoData);
                                photoIn.close();
                                out.writeInt(photoData.length);
                                out.write(photoData);
                            }
                        } else {
                            out.writeInt(0);
                        }
                    } else {
                        out.writeInt(0);
                    }
                    out.flush();
                    out.close();
                    ch.close(apiClient).await();
                    apiClient.disconnect();
                    FileLog.d("WearableDataLayer channel thread exiting");
                }
                if ("/waitForAuthCode".equals(path)) {
                    ConnectionsManager.getInstance().setAppPaused(false, false);
                    final String[] code = new String[]{null};
                    barrier = new CyclicBarrier(2);
                    listener = new NotificationCenterDelegate() {
                        public void didReceivedNotification(int id, Object... args) {
                            if (id == NotificationCenter.didReceivedNewMessages && ((Long) args[0]).longValue() == 777000) {
                                ArrayList<MessageObject> arr = args[1];
                                if (arr.size() > 0) {
                                    MessageObject msg = (MessageObject) arr.get(0);
                                    if (!TextUtils.isEmpty(msg.messageText)) {
                                        Matcher matcher = Pattern.compile("[0-9]+").matcher(msg.messageText);
                                        if (matcher.find()) {
                                            code[0] = matcher.group();
                                            try {
                                                barrier.await(10, TimeUnit.MILLISECONDS);
                                            } catch (Exception e) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    };
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().addObserver(listener, NotificationCenter.didReceivedNewMessages);
                        }
                    });
                    try {
                        barrier.await(15, TimeUnit.SECONDS);
                    } catch (Exception e2) {
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().removeObserver(listener, NotificationCenter.didReceivedNewMessages);
                        }
                    });
                    out = new DataOutputStream(((GetOutputStreamResult) ch.getOutputStream(apiClient).await()).getOutputStream());
                    if (code[0] != null) {
                        out.writeUTF(code[0]);
                    } else {
                        out.writeUTF("");
                    }
                    out.flush();
                    out.close();
                    ConnectionsManager.getInstance().setAppPaused(true, false);
                }
                ch.close(apiClient).await();
                apiClient.disconnect();
                FileLog.d("WearableDataLayer channel thread exiting");
            } catch (Exception x) {
                FileLog.e("error processing wear request", x);
            }
        } else {
            FileLog.e("failed to connect google api client");
        }
    }
}
