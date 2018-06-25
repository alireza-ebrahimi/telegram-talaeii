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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC.User;

public class WearDataLayerListenerService extends WearableListenerService {
    public void onChannelOpened(Channel channel) {
        GoogleApiClient build = new Builder(this).addApi(Wearable.API).build();
        if (build.blockingConnect().isSuccess()) {
            String path = channel.getPath();
            FileLog.m13725d("wear channel path: " + path);
            try {
                if ("/getCurrentUser".equals(path)) {
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(((GetOutputStreamResult) channel.getOutputStream(build).await()).getOutputStream()));
                    if (UserConfig.isClientActivated()) {
                        final User currentUser = UserConfig.getCurrentUser();
                        dataOutputStream.writeInt(currentUser.id);
                        dataOutputStream.writeUTF(currentUser.first_name);
                        dataOutputStream.writeUTF(currentUser.last_name);
                        dataOutputStream.writeUTF(currentUser.phone);
                        if (currentUser.photo != null) {
                            final File pathToAttach = FileLoader.getPathToAttach(currentUser.photo.photo_small, true);
                            final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
                            if (!pathToAttach.exists()) {
                                final NotificationCenterDelegate c34151 = new NotificationCenterDelegate() {
                                    public void didReceivedNotification(int i, Object... objArr) {
                                        if (i == NotificationCenter.FileDidLoaded) {
                                            FileLog.m13725d("file loaded: " + objArr[0] + " " + objArr[0].getClass().getName());
                                            if (objArr[0].equals(pathToAttach.getName())) {
                                                FileLog.m13726e("LOADED USER PHOTO");
                                                try {
                                                    cyclicBarrier.await(10, TimeUnit.MILLISECONDS);
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                    }
                                };
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        NotificationCenter.getInstance().addObserver(c34151, NotificationCenter.FileDidLoaded);
                                        FileLoader.getInstance().loadFile(currentUser.photo.photo_small, null, 0, 1);
                                    }
                                });
                                try {
                                    cyclicBarrier.await(10, TimeUnit.SECONDS);
                                } catch (Exception e) {
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        NotificationCenter.getInstance().removeObserver(c34151, NotificationCenter.FileDidLoaded);
                                    }
                                });
                            }
                            if (!pathToAttach.exists() || pathToAttach.length() > 52428800) {
                                dataOutputStream.writeInt(0);
                            } else {
                                byte[] bArr = new byte[((int) pathToAttach.length())];
                                InputStream fileInputStream = new FileInputStream(pathToAttach);
                                new DataInputStream(fileInputStream).readFully(bArr);
                                fileInputStream.close();
                                dataOutputStream.writeInt(bArr.length);
                                dataOutputStream.write(bArr);
                            }
                        } else {
                            dataOutputStream.writeInt(0);
                        }
                    } else {
                        dataOutputStream.writeInt(0);
                    }
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    channel.close(build).await();
                    build.disconnect();
                    FileLog.m13725d("WearableDataLayer channel thread exiting");
                }
                if ("/waitForAuthCode".equals(path)) {
                    ConnectionsManager.getInstance().setAppPaused(false, false);
                    final String[] strArr = new String[]{null};
                    final CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);
                    final NotificationCenterDelegate c34184 = new NotificationCenterDelegate() {
                        public void didReceivedNotification(int i, Object... objArr) {
                            if (i == NotificationCenter.didReceivedNewMessages && ((Long) objArr[0]).longValue() == 777000) {
                                ArrayList arrayList = (ArrayList) objArr[1];
                                if (arrayList.size() > 0) {
                                    MessageObject messageObject = (MessageObject) arrayList.get(0);
                                    if (!TextUtils.isEmpty(messageObject.messageText)) {
                                        Matcher matcher = Pattern.compile("[0-9]+").matcher(messageObject.messageText);
                                        if (matcher.find()) {
                                            strArr[0] = matcher.group();
                                            try {
                                                cyclicBarrier2.await(10, TimeUnit.MILLISECONDS);
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
                            NotificationCenter.getInstance().addObserver(c34184, NotificationCenter.didReceivedNewMessages);
                        }
                    });
                    try {
                        cyclicBarrier2.await(15, TimeUnit.SECONDS);
                    } catch (Exception e2) {
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            NotificationCenter.getInstance().removeObserver(c34184, NotificationCenter.didReceivedNewMessages);
                        }
                    });
                    DataOutputStream dataOutputStream2 = new DataOutputStream(((GetOutputStreamResult) channel.getOutputStream(build).await()).getOutputStream());
                    if (strArr[0] != null) {
                        dataOutputStream2.writeUTF(strArr[0]);
                    } else {
                        dataOutputStream2.writeUTF(TtmlNode.ANONYMOUS_REGION_ID);
                    }
                    dataOutputStream2.flush();
                    dataOutputStream2.close();
                    ConnectionsManager.getInstance().setAppPaused(true, false);
                }
                channel.close(build).await();
                build.disconnect();
                FileLog.m13725d("WearableDataLayer channel thread exiting");
            } catch (Throwable e3) {
                FileLog.m13727e("error processing wear request", e3);
            }
        } else {
            FileLog.m13726e("failed to connect google api client");
        }
    }

    public void onCreate() {
        super.onCreate();
        FileLog.m13725d("WearableDataLayer service created");
    }

    public void onDestroy() {
        super.onDestroy();
        FileLog.m13725d("WearableDataLayer service destroyed");
    }
}
