package org.telegram.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StatFs;
import android.support.v4.content.C0424l;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.p098a.C1768f;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.SelfUpdateActivity;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.SettingAndUpdate;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.util.C2874e;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController.LocaleInfo;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SendMessagesHelper.SendingMediaInfo;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputGameShortName;
import org.telegram.tgnet.TLRPC$TL_inputMediaGame;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_langpack_getStrings;
import org.telegram.tgnet.TLRPC$TL_messages_checkChatInvite;
import org.telegram.tgnet.TLRPC$TL_messages_importChatInvite;
import org.telegram.tgnet.TLRPC$TL_userContact_old2;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatInvite;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.LangPackString;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBarLayout;
import org.telegram.ui.ActionBar.ActionBarLayout.ActionBarLayoutDelegate;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.DrawerLayoutContainer;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.DrawerLayoutAdapter;
import org.telegram.ui.Cells.LanguageCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AudioPlayerAlert;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.JoinGroupAlert;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PasscodeView;
import org.telegram.ui.Components.PasscodeView.PasscodeViewDelegate;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.SharingLocationsAlert;
import org.telegram.ui.Components.SharingLocationsAlert.SharingLocationsAlertDelegate;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.ThemeEditorView;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.LocationActivity.LocationActivityDelegate;
import utils.p178a.C3791b;
import utils.view.ToastUtil;

public class LaunchActivity extends Activity implements C2497d, NotificationCenterDelegate, ActionBarLayoutDelegate, DialogsActivityDelegate {
    private static ArrayList<BaseFragment> layerFragmentsStack = new ArrayList();
    public static ArrayList<BaseFragment> mainFragmentsStack = new ArrayList();
    public static LaunchActivity me;
    private static ArrayList<BaseFragment> rightFragmentsStack = new ArrayList();
    final String FRG_ELECTION = "FRG_ELECTION";
    final String FRG_HOME = "FRG_HOME";
    final String FRG_HOT = "FRG_HOT";
    final String FRG_NEWS_LIST = "FRG_NEWS_LIST";
    final String FRG_SEARCH = "FRG_SEARCH";
    final String FRG_USER = "FRG_USER";
    private ActionBarLayout actionBarLayout;
    private View backgroundTablet;
    private ArrayList<User> contactsToSend;
    private int currentConnectionState;
    private String documentsMimeType;
    private ArrayList<String> documentsOriginalPathsArray;
    private ArrayList<String> documentsPathsArray;
    private ArrayList<Uri> documentsUrisArray;
    private DrawerLayoutAdapter drawerLayoutAdapter;
    public DrawerLayoutContainer drawerLayoutContainer;
    private HashMap<String, String> englishLocaleStrings;
    private boolean finished;
    private ActionBarLayout layersActionBarLayout;
    private boolean loadingLocaleDialog;
    private AlertDialog localeDialog;
    private Runnable lockRunnable;
    private FirebaseAnalytics mFirebaseAnalytics;
    private OnGlobalLayoutListener onGlobalLayoutListener;
    private Intent passcodeSaveIntent;
    private boolean passcodeSaveIntentIsNew;
    private boolean passcodeSaveIntentIsRestore;
    private PasscodeView passcodeView;
    private ArrayList<SendingMediaInfo> photoPathsArray;
    private ActionBarLayout rightActionBarLayout;
    private boolean selfUpdateCalled = false;
    private String sendingText;
    private FrameLayout shadowTablet;
    private FrameLayout shadowTabletSide;
    private RecyclerListView sideMenu;
    private HashMap<String, String> systemLocaleStrings;
    private boolean tabletFullSize;
    private String videoPath;
    private AlertDialog visibleDialog;

    /* renamed from: org.telegram.ui.LaunchActivity$2 */
    class C48442 implements Runnable {
        C48442() {
        }

        public void run() {
            C2818c.a(LaunchActivity.this, LaunchActivity.this).d();
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$3 */
    class C48473 {
        /* renamed from: t */
        int f10193t;

        C48473() {
        }

        public String toString() {
            r0 = new byte[26];
            this.f10193t = 1705453171;
            r0[0] = (byte) (this.f10193t >>> 18);
            this.f10193t = 1090886822;
            r0[1] = (byte) (this.f10193t >>> 6);
            this.f10193t = -1902331162;
            r0[2] = (byte) (this.f10193t >>> 10);
            this.f10193t = 767419969;
            r0[3] = (byte) (this.f10193t >>> 21);
            this.f10193t = 1509530619;
            r0[4] = (byte) (this.f10193t >>> 6);
            this.f10193t = 1552084696;
            r0[5] = (byte) (this.f10193t >>> 9);
            this.f10193t = 421031242;
            r0[6] = (byte) (this.f10193t >>> 5);
            this.f10193t = 802056146;
            r0[7] = (byte) (this.f10193t >>> 8);
            this.f10193t = -1293672300;
            r0[8] = (byte) (this.f10193t >>> 17);
            this.f10193t = -762115146;
            r0[9] = (byte) (this.f10193t >>> 11);
            this.f10193t = 49135232;
            r0[10] = (byte) (this.f10193t >>> 13);
            this.f10193t = 1707571853;
            r0[11] = (byte) (this.f10193t >>> 21);
            this.f10193t = -1249043617;
            r0[12] = (byte) (this.f10193t >>> 6);
            this.f10193t = 416337382;
            r0[13] = (byte) (this.f10193t >>> 5);
            this.f10193t = 535474519;
            r0[14] = (byte) (this.f10193t >>> 7);
            this.f10193t = -2014468776;
            r0[15] = (byte) (this.f10193t >>> 10);
            this.f10193t = 175844766;
            r0[16] = (byte) (this.f10193t >>> 5);
            this.f10193t = 455855405;
            r0[17] = (byte) (this.f10193t >>> 19);
            this.f10193t = 695606310;
            r0[18] = (byte) (this.f10193t >>> 19);
            this.f10193t = 895604158;
            r0[19] = (byte) (this.f10193t >>> 16);
            this.f10193t = -1552903591;
            r0[20] = (byte) (this.f10193t >>> 19);
            this.f10193t = 1687952122;
            r0[21] = (byte) (this.f10193t >>> 24);
            this.f10193t = 1247660581;
            r0[22] = (byte) (this.f10193t >>> 10);
            this.f10193t = -1363680916;
            r0[23] = (byte) (this.f10193t >>> 15);
            this.f10193t = 1235170458;
            r0[24] = (byte) (this.f10193t >>> 7);
            this.f10193t = -3782245;
            r0[25] = (byte) (this.f10193t >>> 12);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$4 */
    class C48514 {
        /* renamed from: t */
        int f10194t;

        C48514() {
        }

        public String toString() {
            r0 = new byte[22];
            this.f10194t = 299524090;
            r0[0] = (byte) (this.f10194t >>> 14);
            this.f10194t = 1752321629;
            r0[1] = (byte) (this.f10194t >>> 16);
            this.f10194t = 1818083148;
            r0[2] = (byte) (this.f10194t >>> 17);
            this.f10194t = -908860963;
            r0[3] = (byte) (this.f10194t >>> 18);
            this.f10194t = 606006627;
            r0[4] = (byte) (this.f10194t >>> 9);
            this.f10194t = 222220938;
            r0[5] = (byte) (this.f10194t >>> 21);
            this.f10194t = 1300117516;
            r0[6] = (byte) (this.f10194t >>> 7);
            this.f10194t = 389146037;
            r0[7] = (byte) (this.f10194t >>> 23);
            this.f10194t = -709462407;
            r0[8] = (byte) (this.f10194t >>> 18);
            this.f10194t = 1282406643;
            r0[9] = (byte) (this.f10194t >>> 16);
            this.f10194t = 2030852233;
            r0[10] = (byte) (this.f10194t >>> 13);
            this.f10194t = 691313664;
            r0[11] = (byte) (this.f10194t >>> 15);
            this.f10194t = -1115239348;
            r0[12] = (byte) (this.f10194t >>> 12);
            this.f10194t = -1145064736;
            r0[13] = (byte) (this.f10194t >>> 7);
            this.f10194t = 846302086;
            r0[14] = (byte) (this.f10194t >>> 6);
            this.f10194t = 166470214;
            r0[15] = (byte) (this.f10194t >>> 13);
            this.f10194t = -15386767;
            r0[16] = (byte) (this.f10194t >>> 7);
            this.f10194t = -1530310599;
            r0[17] = (byte) (this.f10194t >>> 17);
            this.f10194t = -183243192;
            r0[18] = (byte) (this.f10194t >>> 5);
            this.f10194t = 824457205;
            r0[19] = (byte) (this.f10194t >>> 7);
            this.f10194t = -809652958;
            r0[20] = (byte) (this.f10194t >>> 5);
            this.f10194t = 623721673;
            r0[21] = (byte) (this.f10194t >>> 1);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$5 */
    class C48525 {
        /* renamed from: t */
        int f10195t;

        C48525() {
        }

        public String toString() {
            r0 = new byte[22];
            this.f10195t = -831939475;
            r0[0] = (byte) (this.f10195t >>> 16);
            this.f10195t = -238294920;
            r0[1] = (byte) (this.f10195t >>> 18);
            this.f10195t = -2062082041;
            r0[2] = (byte) (this.f10195t >>> 15);
            this.f10195t = -1510329396;
            r0[3] = (byte) (this.f10195t >>> 7);
            this.f10195t = 1998982461;
            r0[4] = (byte) (this.f10195t >>> 20);
            this.f10195t = -83320501;
            r0[5] = (byte) (this.f10195t >>> 19);
            this.f10195t = 538668511;
            r0[6] = (byte) (this.f10195t >>> 8);
            this.f10195t = -876437411;
            r0[7] = (byte) (this.f10195t >>> 1);
            this.f10195t = 1255806847;
            r0[8] = (byte) (this.f10195t >>> 17);
            this.f10195t = 997064253;
            r0[9] = (byte) (this.f10195t >>> 13);
            this.f10195t = -632235873;
            r0[10] = (byte) (this.f10195t >>> 6);
            this.f10195t = 1641943460;
            r0[11] = (byte) (this.f10195t >>> 2);
            this.f10195t = 918751756;
            r0[12] = (byte) (this.f10195t >>> 20);
            this.f10195t = -653945036;
            r0[13] = (byte) (this.f10195t >>> 10);
            this.f10195t = -1713182348;
            r0[14] = (byte) (this.f10195t >>> 3);
            this.f10195t = 1485081651;
            r0[15] = (byte) (this.f10195t >>> 5);
            this.f10195t = 1862199966;
            r0[16] = (byte) (this.f10195t >>> 24);
            this.f10195t = -624398439;
            r0[17] = (byte) (this.f10195t >>> 17);
            this.f10195t = -1086767893;
            r0[18] = (byte) (this.f10195t >>> 15);
            this.f10195t = 1350622955;
            r0[19] = (byte) (this.f10195t >>> 9);
            this.f10195t = -1041439081;
            r0[20] = (byte) (this.f10195t >>> 4);
            this.f10195t = 423166681;
            r0[21] = (byte) (this.f10195t >>> 22);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$6 */
    class C48536 {
        /* renamed from: t */
        int f10196t;

        C48536() {
        }

        public String toString() {
            r0 = new byte[26];
            this.f10196t = -1926972377;
            r0[0] = (byte) (this.f10196t >>> 21);
            this.f10196t = 1570692236;
            r0[1] = (byte) (this.f10196t >>> 6);
            this.f10196t = 542939554;
            r0[2] = (byte) (this.f10196t >>> 17);
            this.f10196t = 1667964611;
            r0[3] = (byte) (this.f10196t >>> 1);
            this.f10196t = 398912970;
            r0[4] = (byte) (this.f10196t >>> 2);
            this.f10196t = 814161269;
            r0[5] = (byte) (this.f10196t >>> 23);
            this.f10196t = 411174284;
            r0[6] = (byte) (this.f10196t >>> 22);
            this.f10196t = -1233908027;
            r0[7] = (byte) (this.f10196t >>> 20);
            this.f10196t = 388926492;
            r0[8] = (byte) (this.f10196t >>> 20);
            this.f10196t = 1818022595;
            r0[9] = (byte) (this.f10196t >>> 1);
            this.f10196t = 973303461;
            r0[10] = (byte) (this.f10196t >>> 11);
            this.f10196t = -1946249458;
            r0[11] = (byte) (this.f10196t >>> 7);
            this.f10196t = -614375312;
            r0[12] = (byte) (this.f10196t >>> 22);
            this.f10196t = 935445584;
            r0[13] = (byte) (this.f10196t >>> 23);
            this.f10196t = -225687742;
            r0[14] = (byte) (this.f10196t >>> 13);
            this.f10196t = 1397413830;
            r0[15] = (byte) (this.f10196t >>> 19);
            this.f10196t = 1068196507;
            r0[16] = (byte) (this.f10196t >>> 11);
            this.f10196t = -1452561226;
            r0[17] = (byte) (this.f10196t >>> 5);
            this.f10196t = 787086141;
            r0[18] = (byte) (this.f10196t >>> 24);
            this.f10196t = -1509325764;
            r0[19] = (byte) (this.f10196t >>> 10);
            this.f10196t = 2109351659;
            r0[20] = (byte) (this.f10196t >>> 18);
            this.f10196t = 1052659089;
            r0[21] = (byte) (this.f10196t >>> 2);
            this.f10196t = -862007726;
            r0[22] = (byte) (this.f10196t >>> 5);
            this.f10196t = -1740071967;
            r0[23] = (byte) (this.f10196t >>> 6);
            this.f10196t = -2036386836;
            r0[24] = (byte) (this.f10196t >>> 20);
            this.f10196t = -1722207933;
            r0[25] = (byte) (this.f10196t >>> 14);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$7 */
    class C48547 {
        /* renamed from: t */
        int f10197t;

        C48547() {
        }

        public String toString() {
            byte[] bArr = new byte[25];
            this.f10197t = 1773415109;
            bArr[0] = (byte) (this.f10197t >>> 24);
            this.f10197t = -1235806775;
            bArr[1] = (byte) (this.f10197t >>> 2);
            this.f10197t = -657748426;
            bArr[2] = (byte) (this.f10197t >>> 14);
            this.f10197t = 874775470;
            bArr[3] = (byte) (this.f10197t >>> 23);
            this.f10197t = -1977340445;
            bArr[4] = (byte) (this.f10197t >>> 5);
            this.f10197t = -1173586549;
            bArr[5] = (byte) (this.f10197t >>> 23);
            this.f10197t = -165987389;
            bArr[6] = (byte) (this.f10197t >>> 11);
            this.f10197t = 961753757;
            bArr[7] = (byte) (this.f10197t >>> 23);
            this.f10197t = 1110370692;
            bArr[8] = (byte) (this.f10197t >>> 2);
            this.f10197t = -116922660;
            bArr[9] = (byte) (this.f10197t >>> 4);
            this.f10197t = 776606134;
            bArr[10] = (byte) (this.f10197t >>> 24);
            this.f10197t = 657206695;
            bArr[11] = (byte) (this.f10197t >>> 5);
            this.f10197t = 498302408;
            bArr[12] = (byte) (this.f10197t >>> 11);
            this.f10197t = -1827653527;
            bArr[13] = (byte) (this.f10197t >>> 19);
            this.f10197t = 546993427;
            bArr[14] = (byte) (this.f10197t >>> 14);
            this.f10197t = 1528860349;
            bArr[15] = (byte) (this.f10197t >>> 22);
            this.f10197t = 2033478839;
            bArr[16] = (byte) (this.f10197t >>> 5);
            this.f10197t = -877430684;
            bArr[17] = (byte) (this.f10197t >>> 22);
            this.f10197t = -210117949;
            bArr[18] = (byte) (this.f10197t >>> 1);
            this.f10197t = -1015862059;
            bArr[19] = (byte) (this.f10197t >>> 19);
            this.f10197t = -2023458854;
            bArr[20] = (byte) (this.f10197t >>> 16);
            this.f10197t = -2095082268;
            bArr[21] = (byte) (this.f10197t >>> 1);
            this.f10197t = -288184590;
            bArr[22] = (byte) (this.f10197t >>> 4);
            this.f10197t = 1397355623;
            bArr[23] = (byte) (this.f10197t >>> 19);
            this.f10197t = -522637089;
            bArr[24] = (byte) (this.f10197t >>> 14);
            return new String(bArr);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$8 */
    class C48558 {
        /* renamed from: t */
        int f10198t;

        C48558() {
        }

        public String toString() {
            r0 = new byte[14];
            this.f10198t = -490283506;
            r0[0] = (byte) (this.f10198t >>> 9);
            this.f10198t = -241392647;
            r0[1] = (byte) (this.f10198t >>> 14);
            this.f10198t = 290969214;
            r0[2] = (byte) (this.f10198t >>> 4);
            this.f10198t = -476707747;
            r0[3] = (byte) (this.f10198t >>> 1);
            this.f10198t = 794532465;
            r0[4] = (byte) (this.f10198t >>> 6);
            this.f10198t = -672936378;
            r0[5] = (byte) (this.f10198t >>> 5);
            this.f10198t = -1600496363;
            r0[6] = (byte) (this.f10198t >>> 9);
            this.f10198t = 1497920749;
            r0[7] = (byte) (this.f10198t >>> 8);
            this.f10198t = -684967864;
            r0[8] = (byte) (this.f10198t >>> 13);
            this.f10198t = 1567376376;
            r0[9] = (byte) (this.f10198t >>> 16);
            this.f10198t = -654111366;
            r0[10] = (byte) (this.f10198t >>> 11);
            this.f10198t = -2078719414;
            r0[11] = (byte) (this.f10198t >>> 14);
            this.f10198t = 35274797;
            r0[12] = (byte) (this.f10198t >>> 14);
            this.f10198t = 288049982;
            r0[13] = (byte) (this.f10198t >>> 11);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$9 */
    class C48569 {
        /* renamed from: t */
        int f10199t;

        C48569() {
        }

        public String toString() {
            byte[] bArr = new byte[31];
            this.f10199t = 1839699309;
            bArr[0] = (byte) (this.f10199t >>> 18);
            this.f10199t = 2145106230;
            bArr[1] = (byte) (this.f10199t >>> 7);
            this.f10199t = -1583934362;
            bArr[2] = (byte) (this.f10199t >>> 15);
            this.f10199t = -1454084749;
            bArr[3] = (byte) (this.f10199t >>> 8);
            this.f10199t = -1244140322;
            bArr[4] = (byte) (this.f10199t >>> 1);
            this.f10199t = 1569290104;
            bArr[5] = (byte) (this.f10199t >>> 4);
            this.f10199t = -1033430379;
            bArr[6] = (byte) (this.f10199t >>> 6);
            this.f10199t = -857479072;
            bArr[7] = (byte) (this.f10199t >>> 21);
            this.f10199t = -656637881;
            bArr[8] = (byte) (this.f10199t >>> 14);
            this.f10199t = 1242311821;
            bArr[9] = (byte) (this.f10199t >>> 13);
            this.f10199t = 114423346;
            bArr[10] = (byte) (this.f10199t >>> 20);
            this.f10199t = 1170620504;
            bArr[11] = (byte) (this.f10199t >>> 21);
            this.f10199t = -699241089;
            bArr[12] = (byte) (this.f10199t >>> 8);
            this.f10199t = 1386274713;
            bArr[13] = (byte) (this.f10199t >>> 9);
            this.f10199t = 1761920197;
            bArr[14] = (byte) (this.f10199t >>> 1);
            this.f10199t = -1266670647;
            bArr[15] = (byte) (this.f10199t >>> 23);
            this.f10199t = 1013728844;
            bArr[16] = (byte) (this.f10199t >>> 16);
            this.f10199t = -1723297579;
            bArr[17] = (byte) (this.f10199t >>> 22);
            this.f10199t = 657364699;
            bArr[18] = (byte) (this.f10199t >>> 16);
            this.f10199t = -228580596;
            bArr[19] = (byte) (this.f10199t >>> 3);
            this.f10199t = -37561321;
            bArr[20] = (byte) (this.f10199t >>> 9);
            this.f10199t = -1516686021;
            bArr[21] = (byte) (this.f10199t >>> 14);
            this.f10199t = 1304662493;
            bArr[22] = (byte) (this.f10199t >>> 11);
            this.f10199t = -1270646353;
            bArr[23] = (byte) (this.f10199t >>> 11);
            this.f10199t = 1579313408;
            bArr[24] = (byte) (this.f10199t >>> 8);
            this.f10199t = -1398853220;
            bArr[25] = (byte) (this.f10199t >>> 21);
            this.f10199t = 1278785848;
            bArr[26] = (byte) (this.f10199t >>> 10);
            this.f10199t = 780948324;
            bArr[27] = (byte) (this.f10199t >>> 13);
            this.f10199t = 1006945881;
            bArr[28] = (byte) (this.f10199t >>> 4);
            this.f10199t = -1535840486;
            bArr[29] = (byte) (this.f10199t >>> 16);
            this.f10199t = -1785976633;
            bArr[30] = (byte) (this.f10199t >>> 13);
            return new String(bArr);
        }
    }

    private class VcardData {
        String name;
        ArrayList<String> phones;

        private VcardData() {
            this.phones = new ArrayList();
        }
    }

    private void checkFreeDiscSpace() {
        if (VERSION.SDK_INT < 26) {
            Utilities.globalQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.LaunchActivity$37$1 */
                class C48461 implements Runnable {
                    C48461() {
                    }

                    public void run() {
                        try {
                            AlertsCreator.createFreeSpaceDialog(LaunchActivity.this).show();
                        } catch (Throwable th) {
                        }
                    }
                }

                public void run() {
                    if (UserConfig.isClientActivated()) {
                        try {
                            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                            if (Math.abs(sharedPreferences.getLong("last_space_check", 0) - System.currentTimeMillis()) >= 259200000) {
                                File directory = FileLoader.getInstance().getDirectory(4);
                                if (directory != null) {
                                    long abs;
                                    StatFs statFs = new StatFs(directory.getAbsolutePath());
                                    if (VERSION.SDK_INT < 18) {
                                        abs = (long) Math.abs(statFs.getAvailableBlocks() * statFs.getBlockSize());
                                    } else {
                                        abs = statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
                                    }
                                    sharedPreferences.edit().putLong("last_space_check", System.currentTimeMillis()).commit();
                                    if (abs < 104857600) {
                                        AndroidUtilities.runOnUIThread(new C48461());
                                    }
                                }
                            }
                        } catch (Throwable th) {
                        }
                    }
                }
            }, 2000);
        }
    }

    private void checkLayout() {
        int i = 8;
        int i2 = 0;
        if (AndroidUtilities.isTablet() && this.rightActionBarLayout != null) {
            BaseFragment baseFragment;
            if (AndroidUtilities.isInMultiwindow || (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2)) {
                this.tabletFullSize = true;
                if (!this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                    while (this.rightActionBarLayout.fragmentsStack.size() > 0) {
                        baseFragment = (BaseFragment) this.rightActionBarLayout.fragmentsStack.get(0);
                        if (baseFragment instanceof ChatActivity) {
                            ((ChatActivity) baseFragment).setIgnoreAttachOnPause(true);
                        }
                        baseFragment.onPause();
                        this.rightActionBarLayout.fragmentsStack.remove(0);
                        this.actionBarLayout.fragmentsStack.add(baseFragment);
                    }
                    if (this.passcodeView.getVisibility() != 0) {
                        this.actionBarLayout.showLastFragment();
                    }
                }
                this.shadowTabletSide.setVisibility(8);
                this.rightActionBarLayout.setVisibility(8);
                View view = this.backgroundTablet;
                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                    i = 0;
                }
                view.setVisibility(i);
                return;
            }
            this.tabletFullSize = false;
            if (this.actionBarLayout.fragmentsStack.size() >= 2) {
                while (1 < this.actionBarLayout.fragmentsStack.size()) {
                    baseFragment = (BaseFragment) this.actionBarLayout.fragmentsStack.get(1);
                    if (baseFragment instanceof ChatActivity) {
                        ((ChatActivity) baseFragment).setIgnoreAttachOnPause(true);
                    }
                    baseFragment.onPause();
                    this.actionBarLayout.fragmentsStack.remove(1);
                    this.rightActionBarLayout.fragmentsStack.add(baseFragment);
                }
                if (this.passcodeView.getVisibility() != 0) {
                    this.actionBarLayout.showLastFragment();
                    this.rightActionBarLayout.showLastFragment();
                }
            }
            this.rightActionBarLayout.setVisibility(this.rightActionBarLayout.fragmentsStack.isEmpty() ? 8 : 0);
            this.backgroundTablet.setVisibility(this.rightActionBarLayout.fragmentsStack.isEmpty() ? 0 : 8);
            FrameLayout frameLayout = this.shadowTabletSide;
            if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                i2 = 8;
            }
            frameLayout.setVisibility(i2);
        }
    }

    private String getStringForLanguageAlert(HashMap<String, String> hashMap, String str, int i) {
        String str2 = (String) hashMap.get(str);
        return str2 == null ? LocaleController.getString(str, i) : str2;
    }

    private boolean handleIntent(Intent intent, boolean z, boolean z2, boolean z3) {
        Throwable e;
        Integer parseInt;
        final Bundle bundle;
        Bundle bundle2;
        BaseFragment dialogsActivity;
        if (AndroidUtilities.handleProxyIntent(this, intent)) {
            return true;
        }
        int flags = intent.getFlags();
        if (z3 || !(AndroidUtilities.needShowPasscode(true) || UserConfig.isWaitingForPasscodeEnter)) {
            Object obj;
            Object obj2;
            Integer num;
            Integer num2;
            boolean z4;
            ArrayList arrayList;
            boolean z5 = false;
            Integer valueOf = Integer.valueOf(0);
            Integer valueOf2 = Integer.valueOf(0);
            Integer valueOf3 = Integer.valueOf(0);
            Integer valueOf4 = Integer.valueOf(0);
            Integer valueOf5 = Integer.valueOf(0);
            Integer valueOf6 = Integer.valueOf(0);
            long j = (intent == null || intent.getExtras() == null) ? 0 : intent.getExtras().getLong("dialogId", 0);
            Object obj3 = null;
            this.photoPathsArray = null;
            this.videoPath = null;
            this.sendingText = null;
            this.documentsPathsArray = null;
            this.documentsOriginalPathsArray = null;
            this.documentsMimeType = null;
            this.documentsUrisArray = null;
            this.contactsToSend = null;
            if (!(!UserConfig.isClientActivated() || (ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES & flags) != 0 || intent == null || intent.getAction() == null || z2)) {
                String type;
                String stringExtra;
                String stringExtra2;
                Parcelable parcelableExtra;
                Uri uri;
                String[] split;
                String str;
                int i;
                if ("android.intent.action.SEND".equals(intent.getAction())) {
                    Object obj4 = null;
                    type = intent.getType();
                    if (type == null || !type.equals("text/x-vcard")) {
                        stringExtra = intent.getStringExtra("android.intent.extra.TEXT");
                        if (stringExtra == null) {
                            CharSequence charSequenceExtra = intent.getCharSequenceExtra("android.intent.extra.TEXT");
                            if (charSequenceExtra != null) {
                                stringExtra = charSequenceExtra.toString();
                            }
                        }
                        stringExtra2 = intent.getStringExtra("android.intent.extra.SUBJECT");
                        if (stringExtra != null && stringExtra.length() != 0) {
                            if (!((!stringExtra.startsWith("http://") && !stringExtra.startsWith("https://")) || stringExtra2 == null || stringExtra2.length() == 0)) {
                                stringExtra = stringExtra2 + "\n" + stringExtra;
                            }
                            this.sendingText = stringExtra;
                        } else if (stringExtra2 != null && stringExtra2.length() > 0) {
                            this.sendingText = stringExtra2;
                        }
                        parcelableExtra = intent.getParcelableExtra("android.intent.extra.STREAM");
                        if (parcelableExtra != null) {
                            if (!(parcelableExtra instanceof Uri)) {
                                parcelableExtra = Uri.parse(parcelableExtra.toString());
                            }
                            uri = (Uri) parcelableExtra;
                            if (uri != null && AndroidUtilities.isInternalUri(uri)) {
                                obj4 = 1;
                            }
                            if (obj4 == null) {
                                if (uri == null || ((type == null || !type.startsWith("image/")) && !uri.toString().toLowerCase().endsWith(".jpg"))) {
                                    stringExtra2 = AndroidUtilities.getPath(uri);
                                    if (stringExtra2 != null) {
                                        if (stringExtra2.startsWith("file:")) {
                                            stringExtra2 = stringExtra2.replace("file://", TtmlNode.ANONYMOUS_REGION_ID);
                                        }
                                        if (type == null || !type.startsWith("video/")) {
                                            if (this.documentsPathsArray == null) {
                                                this.documentsPathsArray = new ArrayList();
                                                this.documentsOriginalPathsArray = new ArrayList();
                                            }
                                            this.documentsPathsArray.add(stringExtra2);
                                            this.documentsOriginalPathsArray.add(uri.toString());
                                        } else {
                                            this.videoPath = stringExtra2;
                                        }
                                    } else {
                                        if (this.documentsUrisArray == null) {
                                            this.documentsUrisArray = new ArrayList();
                                        }
                                        this.documentsUrisArray.add(uri);
                                        this.documentsMimeType = type;
                                    }
                                } else {
                                    if (this.photoPathsArray == null) {
                                        this.photoPathsArray = new ArrayList();
                                    }
                                    SendingMediaInfo sendingMediaInfo = new SendingMediaInfo();
                                    sendingMediaInfo.uri = uri;
                                    this.photoPathsArray.add(sendingMediaInfo);
                                }
                            }
                            obj = obj4;
                        } else {
                            obj = this.sendingText == null ? 1 : null;
                        }
                    } else {
                        uri = (Uri) intent.getExtras().get("android.intent.extra.STREAM");
                        if (uri != null) {
                            int i2;
                            InputStream openInputStream = getContentResolver().openInputStream(uri);
                            ArrayList arrayList2 = new ArrayList();
                            VcardData vcardData = null;
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openInputStream, C3446C.UTF8_NAME));
                            while (true) {
                                stringExtra2 = bufferedReader.readLine();
                                if (stringExtra2 != null) {
                                    FileLog.e(stringExtra2);
                                    split = stringExtra2.split(":");
                                    if (split.length == 2) {
                                        VcardData vcardData2;
                                        if (split[0].equals("BEGIN") && split[1].equals("VCARD")) {
                                            LaunchActivity launchActivity = this;
                                            vcardData = new VcardData();
                                            arrayList2.add(vcardData);
                                            vcardData2 = vcardData;
                                        } else {
                                            vcardData2 = (split[0].equals("END") && split[1].equals("VCARD")) ? null : vcardData;
                                        }
                                        if (vcardData2 == null) {
                                            vcardData = vcardData2;
                                        } else {
                                            if (split[0].startsWith("FN") || (split[0].startsWith("ORG") && TextUtils.isEmpty(vcardData2.name))) {
                                                stringExtra = null;
                                                stringExtra2 = null;
                                                for (String str2 : split[0].split(";")) {
                                                    String[] split2 = str2.split("=");
                                                    if (split2.length == 2) {
                                                        if (split2[0].equals("CHARSET")) {
                                                            stringExtra = split2[1];
                                                        } else if (split2[0].equals("ENCODING")) {
                                                            stringExtra2 = split2[1];
                                                        }
                                                    }
                                                }
                                                vcardData2.name = split[1];
                                                if (stringExtra2 != null && stringExtra2.equalsIgnoreCase("QUOTED-PRINTABLE")) {
                                                    while (vcardData2.name.endsWith("=") && stringExtra2 != null) {
                                                        vcardData2.name = vcardData2.name.substring(0, vcardData2.name.length() - 1);
                                                        type = bufferedReader.readLine();
                                                        if (type == null) {
                                                            break;
                                                        }
                                                        vcardData2.name += type;
                                                    }
                                                    byte[] decodeQuotedPrintable = AndroidUtilities.decodeQuotedPrintable(vcardData2.name.getBytes());
                                                    if (!(decodeQuotedPrintable == null || decodeQuotedPrintable.length == 0)) {
                                                        type = new String(decodeQuotedPrintable, stringExtra);
                                                        if (type != null) {
                                                            vcardData2.name = type;
                                                        }
                                                    }
                                                }
                                            } else {
                                                try {
                                                    if (split[0].startsWith("TEL")) {
                                                        stringExtra = C2488b.a(split[1], true);
                                                        if (stringExtra.length() > 0) {
                                                            vcardData2.phones.add(stringExtra);
                                                        }
                                                    }
                                                } catch (Throwable e2) {
                                                    FileLog.e(e2);
                                                    obj = 1;
                                                }
                                            }
                                            vcardData = vcardData2;
                                        }
                                    }
                                } else {
                                    try {
                                        break;
                                    } catch (Throwable e22) {
                                        FileLog.e(e22);
                                    }
                                }
                            }
                            bufferedReader.close();
                            openInputStream.close();
                            for (i = 0; i < arrayList2.size(); i++) {
                                vcardData = (VcardData) arrayList2.get(i);
                                if (!(vcardData.name == null || vcardData.phones.isEmpty())) {
                                    if (this.contactsToSend == null) {
                                        this.contactsToSend = new ArrayList();
                                    }
                                    for (i2 = 0; i2 < vcardData.phones.size(); i2++) {
                                        stringExtra2 = (String) vcardData.phones.get(i2);
                                        User tLRPC$TL_userContact_old2 = new TLRPC$TL_userContact_old2();
                                        tLRPC$TL_userContact_old2.phone = stringExtra2;
                                        tLRPC$TL_userContact_old2.first_name = vcardData.name;
                                        tLRPC$TL_userContact_old2.last_name = TtmlNode.ANONYMOUS_REGION_ID;
                                        tLRPC$TL_userContact_old2.id = 0;
                                        this.contactsToSend.add(tLRPC$TL_userContact_old2);
                                    }
                                }
                            }
                            obj = null;
                        } else {
                            obj = 1;
                        }
                    }
                    if (obj != null) {
                        Toast.makeText(this, "Unsupported content", 0).show();
                    }
                    obj = null;
                    obj2 = null;
                    num = valueOf6;
                    num2 = valueOf5;
                } else if (intent.getAction().equals("android.intent.action.SEND_MULTIPLE")) {
                    try {
                        ArrayList arrayList3;
                        Parcelable parcelable;
                        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
                        r8 = intent.getType();
                        if (parcelableArrayListExtra != null) {
                            r3 = 0;
                            while (r3 < parcelableArrayListExtra.size()) {
                                parcelableExtra = (Parcelable) parcelableArrayListExtra.get(r3);
                                if (!(parcelableExtra instanceof Uri)) {
                                    parcelableExtra = Uri.parse(parcelableExtra.toString());
                                }
                                uri = (Uri) parcelableExtra;
                                if (uri == null || !AndroidUtilities.isInternalUri(uri)) {
                                    r2 = r3;
                                } else {
                                    parcelableArrayListExtra.remove(r3);
                                    r2 = r3 - 1;
                                }
                                r3 = r2 + 1;
                            }
                            if (parcelableArrayListExtra.isEmpty()) {
                                arrayList3 = null;
                                if (arrayList3 != null) {
                                    obj = 1;
                                } else if (r8 == null && r8.startsWith("image/")) {
                                    for (r3 = 0; r3 < arrayList3.size(); r3++) {
                                        parcelableExtra = (Parcelable) arrayList3.get(r3);
                                        if (!(parcelableExtra instanceof Uri)) {
                                            parcelableExtra = Uri.parse(parcelableExtra.toString());
                                        }
                                        uri = (Uri) parcelableExtra;
                                        if (this.photoPathsArray == null) {
                                            this.photoPathsArray = new ArrayList();
                                        }
                                        SendingMediaInfo sendingMediaInfo2 = new SendingMediaInfo();
                                        sendingMediaInfo2.uri = uri;
                                        this.photoPathsArray.add(sendingMediaInfo2);
                                    }
                                    obj = null;
                                } else {
                                    for (i = 0; i < arrayList3.size(); i++) {
                                        parcelableExtra = (Parcelable) arrayList3.get(i);
                                        if (parcelableExtra instanceof Uri) {
                                            obj2 = Uri.parse(parcelableExtra.toString());
                                        } else {
                                            parcelable = parcelableExtra;
                                        }
                                        uri = (Uri) obj2;
                                        type = AndroidUtilities.getPath(uri);
                                        obj2 = obj2.toString();
                                        if (obj2 == null) {
                                            obj2 = type;
                                        }
                                        if (type == null) {
                                            if (type.startsWith("file:")) {
                                                stringExtra = type;
                                            } else {
                                                obj = type.replace("file://", TtmlNode.ANONYMOUS_REGION_ID);
                                            }
                                            if (this.documentsPathsArray == null) {
                                                this.documentsPathsArray = new ArrayList();
                                                this.documentsOriginalPathsArray = new ArrayList();
                                            }
                                            this.documentsPathsArray.add(obj);
                                            this.documentsOriginalPathsArray.add(obj2);
                                        } else {
                                            if (this.documentsUrisArray == null) {
                                                this.documentsUrisArray = new ArrayList();
                                            }
                                            this.documentsUrisArray.add(uri);
                                            this.documentsMimeType = r8;
                                        }
                                    }
                                    obj = null;
                                }
                                if (obj != null) {
                                    Toast.makeText(this, "Unsupported content", 0).show();
                                }
                                obj = null;
                                obj2 = null;
                                num = valueOf6;
                                num2 = valueOf5;
                            }
                        }
                        arrayList3 = parcelableArrayListExtra;
                        if (arrayList3 != null) {
                            obj = 1;
                        } else {
                            if (r8 == null) {
                            }
                            for (i = 0; i < arrayList3.size(); i++) {
                                parcelableExtra = (Parcelable) arrayList3.get(i);
                                if (parcelableExtra instanceof Uri) {
                                    parcelable = parcelableExtra;
                                } else {
                                    obj2 = Uri.parse(parcelableExtra.toString());
                                }
                                uri = (Uri) obj2;
                                type = AndroidUtilities.getPath(uri);
                                obj2 = obj2.toString();
                                if (obj2 == null) {
                                    obj2 = type;
                                }
                                if (type == null) {
                                    if (this.documentsUrisArray == null) {
                                        this.documentsUrisArray = new ArrayList();
                                    }
                                    this.documentsUrisArray.add(uri);
                                    this.documentsMimeType = r8;
                                } else {
                                    if (type.startsWith("file:")) {
                                        stringExtra = type;
                                    } else {
                                        obj = type.replace("file://", TtmlNode.ANONYMOUS_REGION_ID);
                                    }
                                    if (this.documentsPathsArray == null) {
                                        this.documentsPathsArray = new ArrayList();
                                        this.documentsOriginalPathsArray = new ArrayList();
                                    }
                                    this.documentsPathsArray.add(obj);
                                    this.documentsOriginalPathsArray.add(obj2);
                                }
                            }
                            obj = null;
                        }
                    } catch (Throwable e222) {
                        FileLog.e(e222);
                        obj = 1;
                    }
                    if (obj != null) {
                        Toast.makeText(this, "Unsupported content", 0).show();
                    }
                    obj = null;
                    obj2 = null;
                    num = valueOf6;
                    num2 = valueOf5;
                } else if ("android.intent.action.VIEW".equals(intent.getAction())) {
                    Integer num3;
                    Integer num4;
                    Uri data = intent.getData();
                    if (data != null) {
                        String str3;
                        String str4;
                        String str5;
                        Integer num5;
                        boolean z6;
                        String str6;
                        String[] strArr;
                        Integer num6;
                        num2 = null;
                        boolean z7 = false;
                        stringExtra = data.getScheme();
                        if (stringExtra != null) {
                            String str7;
                            String replace;
                            if (stringExtra.equals("http") || stringExtra.equals("https")) {
                                String str8;
                                String str9;
                                stringExtra = data.getHost().toLowerCase();
                                if (stringExtra.equals("telegram.me") || stringExtra.equals("t.me") || stringExtra.equals("telegram.dog") || stringExtra.equals("telesco.pe")) {
                                    stringExtra = data.getPath();
                                    if (stringExtra != null && stringExtra.length() > 1) {
                                        stringExtra = stringExtra.substring(1);
                                        if (stringExtra.startsWith("joinchat/")) {
                                            stringExtra2 = null;
                                            r8 = null;
                                            str3 = null;
                                            split = null;
                                            str2 = null;
                                            str4 = null;
                                            str7 = null;
                                            replace = stringExtra.replace("joinchat/", TtmlNode.ANONYMOUS_REGION_ID);
                                            stringExtra = null;
                                            str5 = null;
                                        } else if (stringExtra.startsWith("addstickers/")) {
                                            stringExtra2 = null;
                                            r8 = null;
                                            str3 = stringExtra.replace("addstickers/", TtmlNode.ANONYMOUS_REGION_ID);
                                            stringExtra = null;
                                            str5 = null;
                                            split = null;
                                            str2 = null;
                                            str4 = null;
                                            str7 = null;
                                            replace = null;
                                        } else if (stringExtra.startsWith("iv/")) {
                                            null[0] = data.getQueryParameter(ImagesContract.URL);
                                            null[1] = data.getQueryParameter("rhash");
                                            if (TextUtils.isEmpty(null[0]) || TextUtils.isEmpty(null[1])) {
                                                stringExtra2 = null;
                                                str2 = null;
                                                str4 = null;
                                                r8 = null;
                                                str7 = null;
                                                str3 = null;
                                                replace = null;
                                                split = null;
                                                stringExtra = null;
                                                str5 = null;
                                            }
                                        } else if (stringExtra.startsWith("msg/") || stringExtra.startsWith("share/")) {
                                            stringExtra = data.getQueryParameter(ImagesContract.URL);
                                            if (stringExtra == null) {
                                                stringExtra = TtmlNode.ANONYMOUS_REGION_ID;
                                            }
                                            if (data.getQueryParameter(MimeTypes.BASE_TYPE_TEXT) != null) {
                                                if (stringExtra.length() > 0) {
                                                    stringExtra2 = stringExtra + "\n";
                                                    z4 = true;
                                                } else {
                                                    stringExtra2 = stringExtra;
                                                    z4 = false;
                                                }
                                                stringExtra2 = stringExtra2 + data.getQueryParameter(MimeTypes.BASE_TYPE_TEXT);
                                            } else {
                                                stringExtra2 = stringExtra;
                                                z4 = false;
                                            }
                                            if (stringExtra2.length() > MessagesController.UPDATE_MASK_CHAT_ADMINS) {
                                                stringExtra2 = stringExtra2.substring(0, MessagesController.UPDATE_MASK_CHAT_ADMINS);
                                            }
                                            while (stringExtra2.endsWith("\n")) {
                                                stringExtra2 = stringExtra2.substring(0, stringExtra2.length() - 1);
                                            }
                                            z7 = z4;
                                            split = null;
                                            stringExtra = null;
                                            str2 = null;
                                            str5 = stringExtra2;
                                            str4 = null;
                                            str7 = null;
                                            stringExtra2 = null;
                                            replace = null;
                                            r8 = null;
                                            str3 = null;
                                        } else if (stringExtra.startsWith("confirmphone")) {
                                            stringExtra2 = data.getQueryParameter("phone");
                                            stringExtra = data.getQueryParameter("hash");
                                            str5 = null;
                                            str7 = null;
                                            split = null;
                                            replace = null;
                                            str2 = null;
                                            str4 = stringExtra2;
                                            stringExtra2 = null;
                                            r8 = null;
                                            str3 = null;
                                        } else if (stringExtra.length() >= 1) {
                                            List pathSegments = data.getPathSegments();
                                            if (pathSegments.size() > 0) {
                                                stringExtra = (String) pathSegments.get(0);
                                                if (pathSegments.size() > 1) {
                                                    parseInt = Utilities.parseInt((String) pathSegments.get(1));
                                                    if (parseInt.intValue() == 0) {
                                                        stringExtra2 = stringExtra;
                                                        num3 = null;
                                                    } else {
                                                        Integer num7 = parseInt;
                                                        stringExtra2 = stringExtra;
                                                        num3 = num7;
                                                    }
                                                } else {
                                                    stringExtra2 = stringExtra;
                                                    num3 = null;
                                                }
                                            } else {
                                                num3 = null;
                                                stringExtra2 = null;
                                            }
                                            r8 = data.getQueryParameter(TtmlNode.START);
                                            str4 = data.getQueryParameter("startgroup");
                                            str3 = null;
                                            replace = null;
                                            num2 = num3;
                                            stringExtra = null;
                                            str5 = null;
                                            split = null;
                                            str2 = stringExtra2;
                                            stringExtra2 = data.getQueryParameter("game");
                                            str8 = str4;
                                            str4 = null;
                                            str7 = r8;
                                            r8 = str8;
                                        }
                                        num5 = valueOf4;
                                        num4 = valueOf;
                                        valueOf4 = valueOf2;
                                        str9 = str4;
                                        str4 = str7;
                                        z6 = z7;
                                        str6 = replace;
                                        strArr = split;
                                        num6 = num2;
                                        type = str3;
                                        str3 = stringExtra2;
                                        stringExtra2 = str2;
                                        str2 = str9;
                                        str8 = r8;
                                        r8 = str5;
                                        str5 = str8;
                                        if (r8 != null && r8.startsWith("@")) {
                                            r8 = " " + r8;
                                        }
                                        if (str2 == null || stringExtra != null) {
                                            bundle = new Bundle();
                                            bundle.putString("phone", str2);
                                            bundle.putString("hash", stringExtra);
                                            AndroidUtilities.runOnUIThread(new Runnable() {
                                                public void run() {
                                                    LaunchActivity.this.presentFragment(new CancelAccountDeletionActivity(bundle));
                                                }
                                            });
                                            num3 = num5;
                                            parseInt = valueOf4;
                                        } else if (stringExtra2 == null && str6 == null && type == null && r8 == null && str3 == null && strArr == null) {
                                            try {
                                                Cursor query = getContentResolver().query(intent.getData(), null, null, null, null);
                                                if (query != null) {
                                                    if (query.moveToFirst()) {
                                                        r2 = query.getInt(query.getColumnIndex("DATA4"));
                                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                        num3 = Integer.valueOf(r2);
                                                    } else {
                                                        num3 = num4;
                                                    }
                                                    try {
                                                        query.close();
                                                    } catch (Throwable e3) {
                                                        num4 = num3;
                                                        e222 = e3;
                                                        FileLog.e(e222);
                                                        num3 = num5;
                                                        parseInt = valueOf4;
                                                        num = valueOf6;
                                                        num2 = valueOf5;
                                                        valueOf4 = num3;
                                                        valueOf2 = parseInt;
                                                        valueOf = num4;
                                                        obj2 = null;
                                                        obj = null;
                                                        if (valueOf.intValue() == 0) {
                                                            bundle = new Bundle();
                                                            bundle.putInt("user_id", valueOf.intValue());
                                                            if (valueOf4.intValue() != 0) {
                                                                bundle.putInt("message_id", valueOf4.intValue());
                                                            }
                                                            if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                                                                z4 = true;
                                                            }
                                                            z4 = false;
                                                        } else if (valueOf2.intValue() == 0) {
                                                            bundle = new Bundle();
                                                            bundle.putInt("chat_id", valueOf2.intValue());
                                                            if (valueOf4.intValue() != 0) {
                                                                bundle.putInt("message_id", valueOf4.intValue());
                                                            }
                                                            if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                                                                z5 = true;
                                                            }
                                                            z4 = z5;
                                                        } else if (valueOf3.intValue() == 0) {
                                                            bundle2 = new Bundle();
                                                            bundle2.putInt("enc_id", valueOf3.intValue());
                                                            if (this.actionBarLayout.presentFragment(new ChatActivity(bundle2), false, true, true)) {
                                                                z5 = true;
                                                            }
                                                            z4 = z5;
                                                        } else if (obj3 == null) {
                                                            if (AndroidUtilities.isTablet()) {
                                                                this.actionBarLayout.removeAllFragments();
                                                            } else if (!this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                                                                while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                                                                    this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                                                                }
                                                                this.layersActionBarLayout.closeLastFragment(false);
                                                            }
                                                            z = false;
                                                            z4 = false;
                                                        } else if (obj2 == null) {
                                                            if (!this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                                ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new AudioPlayerAlert(this));
                                                            }
                                                            z4 = false;
                                                        } else if (obj == null) {
                                                            if (this.videoPath == null) {
                                                            }
                                                            if (!AndroidUtilities.isTablet()) {
                                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                            }
                                                            if (j != 0) {
                                                                arrayList = new ArrayList();
                                                                arrayList.add(Long.valueOf(j));
                                                                didSelectDialogs(null, arrayList, null, false);
                                                                z4 = false;
                                                            } else {
                                                                bundle2 = new Bundle();
                                                                bundle2.putBoolean("onlySelect", true);
                                                                bundle2.putInt("dialogsType", 3);
                                                                if (this.contactsToSend == null) {
                                                                    bundle2.putString("selectAlertString", LocaleController.getString("SendMessagesTo", R.string.SendMessagesTo));
                                                                    bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendMessagesToGroup", R.string.SendMessagesToGroup));
                                                                } else {
                                                                    bundle2.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendMessagesTo));
                                                                    bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                                                                }
                                                                dialogsActivity = new DialogsActivity(bundle2);
                                                                dialogsActivity.setDelegate(this);
                                                                if (AndroidUtilities.isTablet()) {
                                                                    if (this.layersActionBarLayout.fragmentsStack.size() <= 0) {
                                                                    }
                                                                }
                                                                this.actionBarLayout.presentFragment(dialogsActivity, z4, true, true);
                                                                if (!SecretMediaViewer.getInstance().isVisible()) {
                                                                    SecretMediaViewer.getInstance().closePhoto(false, false);
                                                                } else if (!PhotoViewer.getInstance().isVisible()) {
                                                                    PhotoViewer.getInstance().closePhoto(false, true);
                                                                } else if (ArticleViewer.getInstance().isVisible()) {
                                                                    ArticleViewer.getInstance().close(false, true);
                                                                }
                                                                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                                if (AndroidUtilities.isTablet()) {
                                                                    this.actionBarLayout.showLastFragment();
                                                                    this.rightActionBarLayout.showLastFragment();
                                                                } else {
                                                                    this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                                }
                                                                z4 = true;
                                                            }
                                                        } else {
                                                            if (!this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                                ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new SharingLocationsAlert(this, new SharingLocationsAlertDelegate() {
                                                                    public void didSelectLocation(SharingLocationInfo sharingLocationInfo) {
                                                                        BaseFragment locationActivity = new LocationActivity(2);
                                                                        locationActivity.setMessageObject(sharingLocationInfo.messageObject);
                                                                        final long dialogId = sharingLocationInfo.messageObject.getDialogId();
                                                                        locationActivity.setDelegate(new LocationActivityDelegate() {
                                                                            public void didSelectLocation(MessageMedia messageMedia, int i) {
                                                                                SendMessagesHelper.getInstance().sendMessage(messageMedia, dialogId, null, null, null);
                                                                            }
                                                                        });
                                                                        LaunchActivity.this.presentFragment(locationActivity);
                                                                    }
                                                                }));
                                                            }
                                                            z4 = false;
                                                        }
                                                        if (AndroidUtilities.isTablet()) {
                                                            if (UserConfig.isClientActivated()) {
                                                                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                                    dialogsActivity = new DialogsActivity(null);
                                                                    dialogsActivity.setSideMenu(this.sideMenu);
                                                                    this.actionBarLayout.addFragmentToStack(dialogsActivity);
                                                                    this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                                }
                                                            } else if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                                                                this.layersActionBarLayout.addFragmentToStack(new LoginActivity());
                                                                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                            }
                                                        } else if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                            if (UserConfig.isClientActivated()) {
                                                                dialogsActivity = new DialogsActivity(null);
                                                                dialogsActivity.setSideMenu(this.sideMenu);
                                                                this.actionBarLayout.addFragmentToStack(dialogsActivity);
                                                                this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                            } else {
                                                                this.actionBarLayout.addFragmentToStack(new LoginActivity());
                                                                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                            }
                                                        }
                                                        this.actionBarLayout.showLastFragment();
                                                        if (AndroidUtilities.isTablet()) {
                                                            this.layersActionBarLayout.showLastFragment();
                                                            this.rightActionBarLayout.showLastFragment();
                                                        }
                                                        intent.setAction(null);
                                                        return z4;
                                                    }
                                                }
                                                num3 = num4;
                                                parseInt = valueOf4;
                                                num4 = num3;
                                                num3 = num5;
                                            } catch (Exception e4) {
                                                e222 = e4;
                                                FileLog.e(e222);
                                                num3 = num5;
                                                parseInt = valueOf4;
                                                num = valueOf6;
                                                num2 = valueOf5;
                                                valueOf4 = num3;
                                                valueOf2 = parseInt;
                                                valueOf = num4;
                                                obj2 = null;
                                                obj = null;
                                                if (valueOf.intValue() == 0) {
                                                    bundle = new Bundle();
                                                    bundle.putInt("user_id", valueOf.intValue());
                                                    if (valueOf4.intValue() != 0) {
                                                        bundle.putInt("message_id", valueOf4.intValue());
                                                    }
                                                    if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                                                        z4 = true;
                                                    }
                                                    z4 = false;
                                                } else if (valueOf2.intValue() == 0) {
                                                    bundle = new Bundle();
                                                    bundle.putInt("chat_id", valueOf2.intValue());
                                                    if (valueOf4.intValue() != 0) {
                                                        bundle.putInt("message_id", valueOf4.intValue());
                                                    }
                                                    if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                                                        z5 = true;
                                                    }
                                                    z4 = z5;
                                                } else if (valueOf3.intValue() == 0) {
                                                    bundle2 = new Bundle();
                                                    bundle2.putInt("enc_id", valueOf3.intValue());
                                                    if (this.actionBarLayout.presentFragment(new ChatActivity(bundle2), false, true, true)) {
                                                        z5 = true;
                                                    }
                                                    z4 = z5;
                                                } else if (obj3 == null) {
                                                    if (AndroidUtilities.isTablet()) {
                                                        this.actionBarLayout.removeAllFragments();
                                                    } else if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                                                        while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                                                            this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                                                        }
                                                        this.layersActionBarLayout.closeLastFragment(false);
                                                    }
                                                    z = false;
                                                    z4 = false;
                                                } else if (obj2 == null) {
                                                    if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                        ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new AudioPlayerAlert(this));
                                                    }
                                                    z4 = false;
                                                } else if (obj == null) {
                                                    if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                        ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new SharingLocationsAlert(this, /* anonymous class already generated */));
                                                    }
                                                    z4 = false;
                                                } else {
                                                    if (this.videoPath == null) {
                                                    }
                                                    if (AndroidUtilities.isTablet()) {
                                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                    }
                                                    if (j != 0) {
                                                        bundle2 = new Bundle();
                                                        bundle2.putBoolean("onlySelect", true);
                                                        bundle2.putInt("dialogsType", 3);
                                                        if (this.contactsToSend == null) {
                                                            bundle2.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendMessagesTo));
                                                            bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                                                        } else {
                                                            bundle2.putString("selectAlertString", LocaleController.getString("SendMessagesTo", R.string.SendMessagesTo));
                                                            bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendMessagesToGroup", R.string.SendMessagesToGroup));
                                                        }
                                                        dialogsActivity = new DialogsActivity(bundle2);
                                                        dialogsActivity.setDelegate(this);
                                                        if (AndroidUtilities.isTablet()) {
                                                            if (this.layersActionBarLayout.fragmentsStack.size() <= 0) {
                                                            }
                                                        }
                                                        this.actionBarLayout.presentFragment(dialogsActivity, z4, true, true);
                                                        if (!SecretMediaViewer.getInstance().isVisible()) {
                                                            SecretMediaViewer.getInstance().closePhoto(false, false);
                                                        } else if (!PhotoViewer.getInstance().isVisible()) {
                                                            PhotoViewer.getInstance().closePhoto(false, true);
                                                        } else if (ArticleViewer.getInstance().isVisible()) {
                                                            ArticleViewer.getInstance().close(false, true);
                                                        }
                                                        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                        if (AndroidUtilities.isTablet()) {
                                                            this.actionBarLayout.showLastFragment();
                                                            this.rightActionBarLayout.showLastFragment();
                                                        } else {
                                                            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                        }
                                                        z4 = true;
                                                    } else {
                                                        arrayList = new ArrayList();
                                                        arrayList.add(Long.valueOf(j));
                                                        didSelectDialogs(null, arrayList, null, false);
                                                        z4 = false;
                                                    }
                                                }
                                                if (AndroidUtilities.isTablet()) {
                                                    if (UserConfig.isClientActivated()) {
                                                        if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                                                            this.layersActionBarLayout.addFragmentToStack(new LoginActivity());
                                                            this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                        }
                                                    } else if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                        dialogsActivity = new DialogsActivity(null);
                                                        dialogsActivity.setSideMenu(this.sideMenu);
                                                        this.actionBarLayout.addFragmentToStack(dialogsActivity);
                                                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                    }
                                                } else if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                                                    if (UserConfig.isClientActivated()) {
                                                        this.actionBarLayout.addFragmentToStack(new LoginActivity());
                                                        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                                    } else {
                                                        dialogsActivity = new DialogsActivity(null);
                                                        dialogsActivity.setSideMenu(this.sideMenu);
                                                        this.actionBarLayout.addFragmentToStack(dialogsActivity);
                                                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                                    }
                                                }
                                                this.actionBarLayout.showLastFragment();
                                                if (AndroidUtilities.isTablet()) {
                                                    this.layersActionBarLayout.showLastFragment();
                                                    this.rightActionBarLayout.showLastFragment();
                                                }
                                                intent.setAction(null);
                                                return z4;
                                            }
                                        } else {
                                            runLinkRequest(stringExtra2, str6, type, str4, str5, r8, z6, num6, str3, strArr, 0);
                                            num3 = num5;
                                            parseInt = valueOf4;
                                        }
                                    }
                                }
                                stringExtra = null;
                                stringExtra2 = null;
                                str5 = null;
                                r8 = null;
                                split = null;
                                str3 = null;
                                str2 = null;
                                str4 = null;
                                str7 = null;
                                replace = null;
                                num5 = valueOf4;
                                num4 = valueOf;
                                valueOf4 = valueOf2;
                                str9 = str4;
                                str4 = str7;
                                z6 = z7;
                                str6 = replace;
                                strArr = split;
                                num6 = num2;
                                type = str3;
                                str3 = stringExtra2;
                                stringExtra2 = str2;
                                str2 = str9;
                                str8 = r8;
                                r8 = str5;
                                str5 = str8;
                                r8 = " " + r8;
                                if (str2 == null) {
                                }
                                bundle = new Bundle();
                                bundle.putString("phone", str2);
                                bundle.putString("hash", stringExtra);
                                AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                num3 = num5;
                                parseInt = valueOf4;
                            } else if (stringExtra.equals("tg")) {
                                stringExtra = data.toString();
                                if (stringExtra.startsWith("tg:resolve") || stringExtra.startsWith("tg://resolve")) {
                                    uri = Uri.parse(stringExtra.replace("tg:resolve", "tg://telegram.org").replace("tg://resolve", "tg://telegram.org"));
                                    str4 = uri.getQueryParameter("domain");
                                    replace = uri.getQueryParameter(TtmlNode.START);
                                    str3 = uri.getQueryParameter("startgroup");
                                    r8 = uri.getQueryParameter("game");
                                    num2 = Utilities.parseInt(uri.getQueryParameter("post"));
                                    if (num2.intValue() == 0) {
                                        stringExtra = null;
                                        stringExtra2 = str4;
                                        str5 = str3;
                                        str4 = replace;
                                        str3 = r8;
                                        strArr = null;
                                        str2 = null;
                                        r8 = null;
                                        z6 = false;
                                        num6 = null;
                                        str6 = null;
                                        type = null;
                                        num5 = valueOf4;
                                        num4 = valueOf;
                                        valueOf4 = valueOf2;
                                    } else {
                                        stringExtra = null;
                                        stringExtra2 = str4;
                                        str5 = str3;
                                        str4 = replace;
                                        str3 = r8;
                                        strArr = null;
                                        str2 = null;
                                        r8 = null;
                                        z6 = false;
                                        num6 = num2;
                                        str6 = null;
                                        type = null;
                                        num5 = valueOf4;
                                        num4 = valueOf;
                                        valueOf4 = valueOf2;
                                    }
                                    r8 = " " + r8;
                                    if (str2 == null) {
                                    }
                                    bundle = new Bundle();
                                    bundle.putString("phone", str2);
                                    bundle.putString("hash", stringExtra);
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    num3 = num5;
                                    parseInt = valueOf4;
                                } else if (stringExtra.startsWith("tg:join") || stringExtra.startsWith("tg://join")) {
                                    String queryParameter = Uri.parse(stringExtra.replace("tg:join", "tg://telegram.org").replace("tg://join", "tg://telegram.org")).getQueryParameter("invite");
                                    stringExtra = null;
                                    stringExtra2 = null;
                                    str5 = null;
                                    str4 = null;
                                    str3 = null;
                                    strArr = null;
                                    str2 = null;
                                    r8 = null;
                                    z6 = false;
                                    num6 = null;
                                    str6 = queryParameter;
                                    type = null;
                                    num5 = valueOf4;
                                    num4 = valueOf;
                                    valueOf4 = valueOf2;
                                    r8 = " " + r8;
                                    if (str2 == null) {
                                    }
                                    bundle = new Bundle();
                                    bundle.putString("phone", str2);
                                    bundle.putString("hash", stringExtra);
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    num3 = num5;
                                    parseInt = valueOf4;
                                } else if (stringExtra.startsWith("tg:addstickers") || stringExtra.startsWith("tg://addstickers")) {
                                    String queryParameter2 = Uri.parse(stringExtra.replace("tg:addstickers", "tg://telegram.org").replace("tg://addstickers", "tg://telegram.org")).getQueryParameter("set");
                                    stringExtra = null;
                                    stringExtra2 = null;
                                    str5 = null;
                                    str4 = null;
                                    str3 = null;
                                    strArr = null;
                                    str2 = null;
                                    r8 = null;
                                    z6 = false;
                                    num6 = null;
                                    str6 = null;
                                    type = queryParameter2;
                                    num5 = valueOf4;
                                    num4 = valueOf;
                                    valueOf4 = valueOf2;
                                    r8 = " " + r8;
                                    if (str2 == null) {
                                    }
                                    bundle = new Bundle();
                                    bundle.putString("phone", str2);
                                    bundle.putString("hash", stringExtra);
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    num3 = num5;
                                    parseInt = valueOf4;
                                } else if (stringExtra.startsWith("tg:msg") || stringExtra.startsWith("tg://msg") || stringExtra.startsWith("tg://share") || stringExtra.startsWith("tg:share")) {
                                    Uri parse = Uri.parse(stringExtra.replace("tg:msg", "tg://telegram.org").replace("tg://msg", "tg://telegram.org").replace("tg://share", "tg://telegram.org").replace("tg:share", "tg://telegram.org"));
                                    stringExtra = parse.getQueryParameter(ImagesContract.URL);
                                    if (stringExtra == null) {
                                        stringExtra = TtmlNode.ANONYMOUS_REGION_ID;
                                    }
                                    if (parse.getQueryParameter(MimeTypes.BASE_TYPE_TEXT) != null) {
                                        if (stringExtra.length() > 0) {
                                            z7 = true;
                                            stringExtra = stringExtra + "\n";
                                        }
                                        stringExtra = stringExtra + parse.getQueryParameter(MimeTypes.BASE_TYPE_TEXT);
                                    }
                                    if (stringExtra.length() > MessagesController.UPDATE_MASK_CHAT_ADMINS) {
                                        stringExtra = stringExtra.substring(0, MessagesController.UPDATE_MASK_CHAT_ADMINS);
                                    }
                                    while (stringExtra.endsWith("\n")) {
                                        stringExtra = stringExtra.substring(0, stringExtra.length() - 1);
                                    }
                                    num6 = null;
                                    stringExtra2 = null;
                                    str4 = null;
                                    type = null;
                                    strArr = null;
                                    num4 = valueOf;
                                    str2 = null;
                                    z6 = z7;
                                    str6 = null;
                                    num5 = valueOf4;
                                    valueOf4 = valueOf2;
                                    str5 = null;
                                    str3 = null;
                                    r8 = stringExtra;
                                    stringExtra = null;
                                    r8 = " " + r8;
                                    if (str2 == null) {
                                    }
                                    bundle = new Bundle();
                                    bundle.putString("phone", str2);
                                    bundle.putString("hash", stringExtra);
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    num3 = num5;
                                    parseInt = valueOf4;
                                } else if (stringExtra.startsWith("tg:confirmphone") || stringExtra.startsWith("tg://confirmphone")) {
                                    str7 = data.getQueryParameter("phone");
                                    stringExtra = data.getQueryParameter("hash");
                                    stringExtra2 = null;
                                    str5 = null;
                                    str4 = null;
                                    str3 = null;
                                    strArr = null;
                                    str2 = str7;
                                    r8 = null;
                                    z6 = false;
                                    num6 = null;
                                    str6 = null;
                                    type = null;
                                    num5 = valueOf4;
                                    num4 = valueOf;
                                    valueOf4 = valueOf2;
                                    r8 = " " + r8;
                                    if (str2 == null) {
                                    }
                                    bundle = new Bundle();
                                    bundle.putString("phone", str2);
                                    bundle.putString("hash", stringExtra);
                                    AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                    num3 = num5;
                                    parseInt = valueOf4;
                                } else if (stringExtra.startsWith("tg:openmessage") || stringExtra.startsWith("tg://openmessage")) {
                                    stringExtra = data.getQueryParameter("user_id");
                                    stringExtra2 = data.getQueryParameter("chat_id");
                                    String queryParameter3 = data.getQueryParameter("message_id");
                                    if (stringExtra != null) {
                                        try {
                                            valueOf = Integer.valueOf(Integer.parseInt(stringExtra));
                                        } catch (NumberFormatException e5) {
                                        }
                                    } else if (stringExtra2 != null) {
                                        try {
                                            valueOf2 = Integer.valueOf(Integer.parseInt(stringExtra2));
                                        } catch (NumberFormatException e6) {
                                        }
                                    }
                                    if (queryParameter3 != null) {
                                        try {
                                            stringExtra = null;
                                            stringExtra2 = null;
                                            str5 = null;
                                            str4 = null;
                                            str3 = null;
                                            strArr = null;
                                            str2 = null;
                                            r8 = null;
                                            z6 = false;
                                            num6 = null;
                                            str6 = null;
                                            type = null;
                                            num5 = Integer.valueOf(Integer.parseInt(queryParameter3));
                                            num4 = valueOf;
                                            valueOf4 = valueOf2;
                                        } catch (NumberFormatException e7) {
                                            stringExtra = null;
                                            stringExtra2 = null;
                                            str5 = null;
                                            str4 = null;
                                            str3 = null;
                                            strArr = null;
                                            str2 = null;
                                            r8 = null;
                                            z6 = false;
                                            num6 = null;
                                            str6 = null;
                                            type = null;
                                            num5 = valueOf4;
                                            num4 = valueOf;
                                            valueOf4 = valueOf2;
                                        }
                                        r8 = " " + r8;
                                        if (str2 == null) {
                                        }
                                        bundle = new Bundle();
                                        bundle.putString("phone", str2);
                                        bundle.putString("hash", stringExtra);
                                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                                        num3 = num5;
                                        parseInt = valueOf4;
                                    }
                                }
                            }
                        }
                        stringExtra = null;
                        stringExtra2 = null;
                        str5 = null;
                        str4 = null;
                        str3 = null;
                        strArr = null;
                        str2 = null;
                        r8 = null;
                        z6 = false;
                        num6 = null;
                        str6 = null;
                        type = null;
                        num5 = valueOf4;
                        num4 = valueOf;
                        valueOf4 = valueOf2;
                        r8 = " " + r8;
                        if (str2 == null) {
                        }
                        bundle = new Bundle();
                        bundle.putString("phone", str2);
                        bundle.putString("hash", stringExtra);
                        AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
                        num3 = num5;
                        parseInt = valueOf4;
                    } else {
                        num3 = valueOf4;
                        parseInt = valueOf2;
                        num4 = valueOf;
                    }
                    num = valueOf6;
                    num2 = valueOf5;
                    valueOf4 = num3;
                    valueOf2 = parseInt;
                    valueOf = num4;
                    obj2 = null;
                    obj = null;
                } else if (intent.getAction().equals("org.telegram.messenger.OPEN_ACCOUNT")) {
                    obj2 = null;
                    num = valueOf6;
                    num2 = Integer.valueOf(1);
                    obj = null;
                } else if (intent.getAction().equals("new_dialog")) {
                    obj2 = null;
                    num = Integer.valueOf(1);
                    num2 = valueOf5;
                    obj = null;
                } else if (intent.getAction().startsWith("com.tmessages.openchat")) {
                    r2 = intent.getIntExtra("chatId", 0);
                    r3 = intent.getIntExtra("userId", 0);
                    flags = intent.getIntExtra("encId", 0);
                    if (r2 != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        valueOf2 = Integer.valueOf(r2);
                        obj = null;
                        parseInt = valueOf3;
                    } else if (r3 != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        valueOf = Integer.valueOf(r3);
                        obj = null;
                        parseInt = valueOf3;
                    } else if (flags != 0) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        parseInt = Integer.valueOf(flags);
                        obj = null;
                    } else {
                        obj = 1;
                        parseInt = valueOf3;
                    }
                    obj3 = obj;
                    num = valueOf6;
                    num2 = valueOf5;
                    valueOf3 = parseInt;
                    obj = null;
                    obj2 = null;
                } else if (intent.getAction().equals("com.tmessages.openplayer")) {
                    r3 = 1;
                    num = valueOf6;
                    num2 = valueOf5;
                    obj = null;
                } else if (intent.getAction().equals("org.tmessages.openlocations")) {
                    obj = 1;
                    obj2 = null;
                    num = valueOf6;
                    num2 = valueOf5;
                }
                if (valueOf.intValue() == 0) {
                    bundle = new Bundle();
                    bundle.putInt("user_id", valueOf.intValue());
                    if (valueOf4.intValue() != 0) {
                        bundle.putInt("message_id", valueOf4.intValue());
                    }
                    if (mainFragmentsStack.isEmpty() || MessagesController.checkCanOpenChat(bundle, (BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1))) {
                        if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                            z4 = true;
                        }
                    }
                    z4 = false;
                } else if (valueOf2.intValue() == 0) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", valueOf2.intValue());
                    if (valueOf4.intValue() != 0) {
                        bundle.putInt("message_id", valueOf4.intValue());
                    }
                    if (mainFragmentsStack.isEmpty() || MessagesController.checkCanOpenChat(bundle, (BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1))) {
                        if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                            z5 = true;
                        }
                    }
                    z4 = z5;
                } else if (valueOf3.intValue() == 0) {
                    bundle2 = new Bundle();
                    bundle2.putInt("enc_id", valueOf3.intValue());
                    if (this.actionBarLayout.presentFragment(new ChatActivity(bundle2), false, true, true)) {
                        z5 = true;
                    }
                    z4 = z5;
                } else if (obj3 == null) {
                    if (AndroidUtilities.isTablet()) {
                        this.actionBarLayout.removeAllFragments();
                    } else if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                            this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(false);
                    }
                    z = false;
                    z4 = false;
                } else if (obj2 == null) {
                    if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                        ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new AudioPlayerAlert(this));
                    }
                    z4 = false;
                } else if (obj == null) {
                    if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                        ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new SharingLocationsAlert(this, /* anonymous class already generated */));
                    }
                    z4 = false;
                } else if (this.videoPath == null || this.photoPathsArray != null || this.sendingText != null || this.documentsPathsArray != null || this.contactsToSend != null || this.documentsUrisArray != null) {
                    if (AndroidUtilities.isTablet()) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                    }
                    if (j != 0) {
                        bundle2 = new Bundle();
                        bundle2.putBoolean("onlySelect", true);
                        bundle2.putInt("dialogsType", 3);
                        if (this.contactsToSend == null) {
                            bundle2.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendMessagesTo));
                            bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                        } else {
                            bundle2.putString("selectAlertString", LocaleController.getString("SendMessagesTo", R.string.SendMessagesTo));
                            bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendMessagesToGroup", R.string.SendMessagesToGroup));
                        }
                        dialogsActivity = new DialogsActivity(bundle2);
                        dialogsActivity.setDelegate(this);
                        z4 = AndroidUtilities.isTablet() ? this.layersActionBarLayout.fragmentsStack.size() <= 0 && (this.layersActionBarLayout.fragmentsStack.get(this.layersActionBarLayout.fragmentsStack.size() - 1) instanceof DialogsActivity) : this.actionBarLayout.fragmentsStack.size() <= 1 && (this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1) instanceof DialogsActivity);
                        this.actionBarLayout.presentFragment(dialogsActivity, z4, true, true);
                        if (!SecretMediaViewer.getInstance().isVisible()) {
                            SecretMediaViewer.getInstance().closePhoto(false, false);
                        } else if (!PhotoViewer.getInstance().isVisible()) {
                            PhotoViewer.getInstance().closePhoto(false, true);
                        } else if (ArticleViewer.getInstance().isVisible()) {
                            ArticleViewer.getInstance().close(false, true);
                        }
                        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                        if (AndroidUtilities.isTablet()) {
                            this.actionBarLayout.showLastFragment();
                            this.rightActionBarLayout.showLastFragment();
                        } else {
                            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                        }
                        z4 = true;
                    } else {
                        arrayList = new ArrayList();
                        arrayList.add(Long.valueOf(j));
                        didSelectDialogs(null, arrayList, null, false);
                        z4 = false;
                    }
                } else if (num2.intValue() != 0) {
                    this.actionBarLayout.presentFragment(new SettingsActivity(), false, true, true);
                    if (AndroidUtilities.isTablet()) {
                        this.actionBarLayout.showLastFragment();
                        this.rightActionBarLayout.showLastFragment();
                        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                    } else {
                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    }
                    z4 = true;
                } else {
                    if (num.intValue() != 0) {
                        bundle2 = new Bundle();
                        bundle2.putBoolean("destroyAfterSelect", true);
                        this.actionBarLayout.presentFragment(new ContactsActivity(bundle2), false, true, true);
                        if (AndroidUtilities.isTablet()) {
                            this.actionBarLayout.showLastFragment();
                            this.rightActionBarLayout.showLastFragment();
                            this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                        } else {
                            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                        }
                        z4 = true;
                    }
                    z4 = false;
                }
                if (!(z4 || r34)) {
                    if (AndroidUtilities.isTablet()) {
                        if (UserConfig.isClientActivated()) {
                            if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                                this.layersActionBarLayout.addFragmentToStack(new LoginActivity());
                                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                            }
                        } else if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                            dialogsActivity = new DialogsActivity(null);
                            dialogsActivity.setSideMenu(this.sideMenu);
                            this.actionBarLayout.addFragmentToStack(dialogsActivity);
                            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                        }
                    } else if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                        if (UserConfig.isClientActivated()) {
                            this.actionBarLayout.addFragmentToStack(new LoginActivity());
                            this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                        } else {
                            dialogsActivity = new DialogsActivity(null);
                            dialogsActivity.setSideMenu(this.sideMenu);
                            this.actionBarLayout.addFragmentToStack(dialogsActivity);
                            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                        }
                    }
                    this.actionBarLayout.showLastFragment();
                    if (AndroidUtilities.isTablet()) {
                        this.layersActionBarLayout.showLastFragment();
                        this.rightActionBarLayout.showLastFragment();
                    }
                }
                intent.setAction(null);
                return z4;
            }
            obj = null;
            obj2 = null;
            num = valueOf6;
            num2 = valueOf5;
            if (valueOf.intValue() == 0) {
                bundle = new Bundle();
                bundle.putInt("user_id", valueOf.intValue());
                if (valueOf4.intValue() != 0) {
                    bundle.putInt("message_id", valueOf4.intValue());
                }
                if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                    z4 = true;
                }
                z4 = false;
            } else if (valueOf2.intValue() == 0) {
                bundle = new Bundle();
                bundle.putInt("chat_id", valueOf2.intValue());
                if (valueOf4.intValue() != 0) {
                    bundle.putInt("message_id", valueOf4.intValue());
                }
                if (this.actionBarLayout.presentFragment(new ChatActivity(bundle), false, true, true)) {
                    z5 = true;
                }
                z4 = z5;
            } else if (valueOf3.intValue() == 0) {
                bundle2 = new Bundle();
                bundle2.putInt("enc_id", valueOf3.intValue());
                if (this.actionBarLayout.presentFragment(new ChatActivity(bundle2), false, true, true)) {
                    z5 = true;
                }
                z4 = z5;
            } else if (obj3 == null) {
                if (AndroidUtilities.isTablet()) {
                    this.actionBarLayout.removeAllFragments();
                } else if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                    while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                        this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(false);
                }
                z = false;
                z4 = false;
            } else if (obj2 == null) {
                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                    ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new AudioPlayerAlert(this));
                }
                z4 = false;
            } else if (obj == null) {
                if (this.videoPath == null) {
                }
                if (AndroidUtilities.isTablet()) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                }
                if (j != 0) {
                    arrayList = new ArrayList();
                    arrayList.add(Long.valueOf(j));
                    didSelectDialogs(null, arrayList, null, false);
                    z4 = false;
                } else {
                    bundle2 = new Bundle();
                    bundle2.putBoolean("onlySelect", true);
                    bundle2.putInt("dialogsType", 3);
                    if (this.contactsToSend == null) {
                        bundle2.putString("selectAlertString", LocaleController.getString("SendMessagesTo", R.string.SendMessagesTo));
                        bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendMessagesToGroup", R.string.SendMessagesToGroup));
                    } else {
                        bundle2.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendMessagesTo));
                        bundle2.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                    }
                    dialogsActivity = new DialogsActivity(bundle2);
                    dialogsActivity.setDelegate(this);
                    if (AndroidUtilities.isTablet()) {
                        if (this.actionBarLayout.fragmentsStack.size() <= 1) {
                        }
                    }
                    this.actionBarLayout.presentFragment(dialogsActivity, z4, true, true);
                    if (!SecretMediaViewer.getInstance().isVisible()) {
                        SecretMediaViewer.getInstance().closePhoto(false, false);
                    } else if (!PhotoViewer.getInstance().isVisible()) {
                        PhotoViewer.getInstance().closePhoto(false, true);
                    } else if (ArticleViewer.getInstance().isVisible()) {
                        ArticleViewer.getInstance().close(false, true);
                    }
                    this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                    if (AndroidUtilities.isTablet()) {
                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    } else {
                        this.actionBarLayout.showLastFragment();
                        this.rightActionBarLayout.showLastFragment();
                    }
                    z4 = true;
                }
            } else {
                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                    ((BaseFragment) this.actionBarLayout.fragmentsStack.get(0)).showDialog(new SharingLocationsAlert(this, /* anonymous class already generated */));
                }
                z4 = false;
            }
            if (AndroidUtilities.isTablet()) {
                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                    if (UserConfig.isClientActivated()) {
                        dialogsActivity = new DialogsActivity(null);
                        dialogsActivity.setSideMenu(this.sideMenu);
                        this.actionBarLayout.addFragmentToStack(dialogsActivity);
                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    } else {
                        this.actionBarLayout.addFragmentToStack(new LoginActivity());
                        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                    }
                }
            } else if (UserConfig.isClientActivated()) {
                if (this.actionBarLayout.fragmentsStack.isEmpty()) {
                    dialogsActivity = new DialogsActivity(null);
                    dialogsActivity.setSideMenu(this.sideMenu);
                    this.actionBarLayout.addFragmentToStack(dialogsActivity);
                    this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                }
            } else if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                this.layersActionBarLayout.addFragmentToStack(new LoginActivity());
                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
            }
            this.actionBarLayout.showLastFragment();
            if (AndroidUtilities.isTablet()) {
                this.layersActionBarLayout.showLastFragment();
                this.rightActionBarLayout.showLastFragment();
            }
            intent.setAction(null);
            return z4;
        }
        showPasscodeActivity();
        this.passcodeSaveIntent = intent;
        this.passcodeSaveIntentIsNew = z;
        this.passcodeSaveIntentIsRestore = z2;
        UserConfig.saveConfig(false);
        return false;
    }

    private void onFinish() {
        if (!this.finished) {
            this.finished = true;
            if (this.lockRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
                this.lockRunnable = null;
            }
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.mainUserInfoChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeOtherAppActivities);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didUpdatedConnectionState);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.needShowAlert);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetNewWallpapper);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetPasscode);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.reloadInterface);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.openArticle);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.hasNewContactsToImport);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetNewTheme);
        }
    }

    private void onPasscodePause() {
        if (this.lockRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (UserConfig.passcodeHash.length() != 0) {
            UserConfig.lastPauseTime = ConnectionsManager.getInstance().getCurrentTime();
            this.lockRunnable = new Runnable() {
                public void run() {
                    if (LaunchActivity.this.lockRunnable == this) {
                        if (AndroidUtilities.needShowPasscode(true)) {
                            FileLog.e("lock app");
                            LaunchActivity.this.showPasscodeActivity();
                        } else {
                            FileLog.e("didn't pass lock check");
                        }
                        LaunchActivity.this.lockRunnable = null;
                    }
                }
            };
            if (UserConfig.appLocked) {
                AndroidUtilities.runOnUIThread(this.lockRunnable, 1000);
            } else if (UserConfig.autoLockIn != 0) {
                AndroidUtilities.runOnUIThread(this.lockRunnable, (((long) UserConfig.autoLockIn) * 1000) + 1000);
            }
        } else {
            UserConfig.lastPauseTime = 0;
        }
        UserConfig.saveConfig(false);
    }

    private void onPasscodeResume() {
        if (this.lockRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (AndroidUtilities.needShowPasscode(true)) {
            showPasscodeActivity();
        }
        if (UserConfig.lastPauseTime != 0) {
            UserConfig.lastPauseTime = 0;
            UserConfig.saveConfig(false);
        }
    }

    private void registerSdkAp() {
        C2818c.a(ApplicationLoader.applicationContext, new C2497d() {
            public void onResult(Object obj, int i) {
                switch (i) {
                    case 27:
                        HostRequestData hostRequestData = (HostRequestData) obj;
                        ToastUtil.m14196a(LaunchActivity.this.getApplicationContext(), new C1768f().a(hostRequestData)).show();
                        Log.e("APPPPP:", new C1768f().a(hostRequestData));
                        Bundle bundle = new Bundle();
                        bundle.putString("host_security_token", C3791b.ar(ApplicationLoader.applicationContext));
                        bundle.putInt("host_id", 1152);
                        bundle.putString("protocol_version", "1.8.0");
                        bundle.putString("host_data", hostRequestData.getHostRequest());
                        bundle.putString("host_data_sign", hostRequestData.getHostRequestSign());
                        String str = UserConfig.getCurrentUser().phone;
                        if (str.startsWith("98")) {
                            str = "0" + str.substring(2);
                        }
                        Log.e("alireza", "alireza phone no " + str);
                        bundle.putString("client_mobile_no", str);
                        return;
                    default:
                        return;
                }
            }
        }).i();
    }

    private void runLinkRequest(String str, String str2, String str3, String str4, String str5, String str6, boolean z, Integer num, String str7, String[] strArr, int i) {
        final AlertDialog alertDialog = new AlertDialog(this, 1);
        alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        int i2 = 0;
        final String str8;
        final String str9;
        final String str10;
        if (str != null) {
            TLObject tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
            tLRPC$TL_contacts_resolveUsername.username = str;
            str8 = str7;
            str9 = str5;
            str10 = str4;
            final Integer num2 = num;
            i2 = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (!LaunchActivity.this.isFinishing()) {
                                try {
                                    alertDialog.dismiss();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                final TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
                                if (tLRPC$TL_error != null || LaunchActivity.this.actionBarLayout == null || (str8 != null && (str8 == null || tLRPC$TL_contacts_resolvedPeer.users.isEmpty()))) {
                                    try {
                                        Toast.makeText(LaunchActivity.this, LocaleController.getString("NoUsernameFound", R.string.NoUsernameFound), 0).show();
                                        return;
                                    } catch (Throwable e2) {
                                        FileLog.e(e2);
                                        return;
                                    }
                                }
                                MessagesController.getInstance().putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
                                MessagesController.getInstance().putChats(tLRPC$TL_contacts_resolvedPeer.chats, false);
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, false, true);
                                Bundle bundle;
                                if (str8 != null) {
                                    bundle = new Bundle();
                                    bundle.putBoolean("onlySelect", true);
                                    bundle.putBoolean("cantSendToChannels", true);
                                    bundle.putInt("dialogsType", 1);
                                    bundle.putString("selectAlertString", LocaleController.getString("SendGameTo", R.string.SendGameTo));
                                    bundle.putString("selectAlertStringGroup", LocaleController.getString("SendGameToGroup", R.string.SendGameToGroup));
                                    BaseFragment dialogsActivity = new DialogsActivity(bundle);
                                    dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                                        public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                                            long longValue = ((Long) arrayList.get(0)).longValue();
                                            TLRPC$TL_inputMediaGame tLRPC$TL_inputMediaGame = new TLRPC$TL_inputMediaGame();
                                            tLRPC$TL_inputMediaGame.id = new TLRPC$TL_inputGameShortName();
                                            tLRPC$TL_inputMediaGame.id.short_name = str8;
                                            tLRPC$TL_inputMediaGame.id.bot_id = MessagesController.getInputUser((User) tLRPC$TL_contacts_resolvedPeer.users.get(0));
                                            SendMessagesHelper.getInstance().sendGame(MessagesController.getInputPeer((int) longValue), tLRPC$TL_inputMediaGame, 0, 0);
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean("scrollToTopOnResume", true);
                                            int i = (int) longValue;
                                            int i2 = (int) (longValue >> 32);
                                            if (i == 0) {
                                                bundle.putInt("enc_id", i2);
                                            } else if (i2 == 1) {
                                                bundle.putInt("chat_id", i);
                                            } else if (i > 0) {
                                                bundle.putInt("user_id", i);
                                            } else if (i < 0) {
                                                bundle.putInt("chat_id", -i);
                                            }
                                            if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                LaunchActivity.this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true);
                                            }
                                        }
                                    });
                                    boolean z = AndroidUtilities.isTablet() ? LaunchActivity.this.layersActionBarLayout.fragmentsStack.size() > 0 && (LaunchActivity.this.layersActionBarLayout.fragmentsStack.get(LaunchActivity.this.layersActionBarLayout.fragmentsStack.size() - 1) instanceof DialogsActivity) : LaunchActivity.this.actionBarLayout.fragmentsStack.size() > 1 && (LaunchActivity.this.actionBarLayout.fragmentsStack.get(LaunchActivity.this.actionBarLayout.fragmentsStack.size() - 1) instanceof DialogsActivity);
                                    LaunchActivity.this.actionBarLayout.presentFragment(dialogsActivity, z, true, true);
                                    if (SecretMediaViewer.getInstance().isVisible()) {
                                        SecretMediaViewer.getInstance().closePhoto(false, false);
                                    } else if (PhotoViewer.getInstance().isVisible()) {
                                        PhotoViewer.getInstance().closePhoto(false, true);
                                    } else if (ArticleViewer.getInstance().isVisible()) {
                                        ArticleViewer.getInstance().close(false, true);
                                    }
                                    LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                                    if (AndroidUtilities.isTablet()) {
                                        LaunchActivity.this.actionBarLayout.showLastFragment();
                                        LaunchActivity.this.rightActionBarLayout.showLastFragment();
                                        return;
                                    }
                                    LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                                } else if (str9 != null) {
                                    final User user = !tLRPC$TL_contacts_resolvedPeer.users.isEmpty() ? (User) tLRPC$TL_contacts_resolvedPeer.users.get(0) : null;
                                    if (user == null || (user.bot && user.bot_nochats)) {
                                        try {
                                            Toast.makeText(LaunchActivity.this, LocaleController.getString("BotCantJoinGroups", R.string.BotCantJoinGroups), 0).show();
                                            return;
                                        } catch (Throwable e22) {
                                            FileLog.e(e22);
                                            return;
                                        }
                                    }
                                    bundle = new Bundle();
                                    bundle.putBoolean("onlySelect", true);
                                    bundle.putInt("dialogsType", 2);
                                    bundle.putString("addToGroupAlertString", LocaleController.formatString("AddToTheGroupTitle", R.string.AddToTheGroupTitle, new Object[]{UserObject.getUserName(user), "%1$s"}));
                                    BaseFragment dialogsActivity2 = new DialogsActivity(bundle);
                                    dialogsActivity2.setDelegate(new DialogsActivityDelegate() {
                                        public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                                            long longValue = ((Long) arrayList.get(0)).longValue();
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean("scrollToTopOnResume", true);
                                            bundle.putInt("chat_id", -((int) longValue));
                                            if (LaunchActivity.mainFragmentsStack.isEmpty() || MessagesController.checkCanOpenChat(bundle, (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1))) {
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                MessagesController.getInstance().addUserToChat(-((int) longValue), user, null, 0, str9, null);
                                                LaunchActivity.this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true);
                                            }
                                        }
                                    });
                                    LaunchActivity.this.presentFragment(dialogsActivity2);
                                } else {
                                    boolean z2;
                                    Bundle bundle2 = new Bundle();
                                    long j;
                                    if (tLRPC$TL_contacts_resolvedPeer.chats.isEmpty()) {
                                        bundle2.putInt("user_id", ((User) tLRPC$TL_contacts_resolvedPeer.users.get(0)).id);
                                        j = (long) ((User) tLRPC$TL_contacts_resolvedPeer.users.get(0)).id;
                                    } else {
                                        bundle2.putInt("chat_id", ((Chat) tLRPC$TL_contacts_resolvedPeer.chats.get(0)).id);
                                        j = (long) (-((Chat) tLRPC$TL_contacts_resolvedPeer.chats.get(0)).id);
                                    }
                                    if (str10 == null || tLRPC$TL_contacts_resolvedPeer.users.size() <= 0 || !((User) tLRPC$TL_contacts_resolvedPeer.users.get(0)).bot) {
                                        z2 = false;
                                    } else {
                                        bundle2.putString("botUser", str10);
                                        z2 = true;
                                    }
                                    if (num2 != null) {
                                        bundle2.putInt("message_id", num2.intValue());
                                    }
                                    BaseFragment baseFragment = !LaunchActivity.mainFragmentsStack.isEmpty() ? (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1) : null;
                                    if (baseFragment != null && !MessagesController.checkCanOpenChat(bundle2, baseFragment)) {
                                        return;
                                    }
                                    if (z2 && baseFragment != null && (baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).getDialogId() == r4) {
                                        ((ChatActivity) baseFragment).setBotUser(str10);
                                        return;
                                    }
                                    BaseFragment chatActivity = new ChatActivity(bundle2);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    LaunchActivity.this.actionBarLayout.presentFragment(chatActivity, false, true, true);
                                }
                            }
                        }
                    });
                }
            });
        } else if (str2 != null) {
            if (i == 0) {
                TLObject tLRPC$TL_messages_checkChatInvite = new TLRPC$TL_messages_checkChatInvite();
                tLRPC$TL_messages_checkChatInvite.hash = str2;
                str8 = str2;
                str9 = str;
                str10 = str3;
                final String str11 = str4;
                final String str12 = str5;
                final String str13 = str6;
                final boolean z2 = z;
                final Integer num3 = num;
                final String str14 = str7;
                final String[] strArr2 = strArr;
                i2 = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_checkChatInvite, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.LaunchActivity$24$1$1 */
                            class C48411 implements OnClickListener {
                                C48411() {
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LaunchActivity.this.runLinkRequest(str9, str8, str10, str11, str12, str13, z2, num3, str14, strArr2, 1);
                                }
                            }

                            public void run() {
                                if (!LaunchActivity.this.isFinishing()) {
                                    try {
                                        alertDialog.dismiss();
                                    } catch (Throwable e) {
                                        FileLog.e(e);
                                    }
                                    if (tLRPC$TL_error != null || LaunchActivity.this.actionBarLayout == null) {
                                        Builder builder = new Builder(LaunchActivity.this);
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                            builder.setMessage(LocaleController.getString("FloodWait", R.string.FloodWait));
                                        } else {
                                            builder.setMessage(LocaleController.getString("JoinToGroupErrorNotExist", R.string.JoinToGroupErrorNotExist));
                                        }
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        LaunchActivity.this.showAlertDialog(builder);
                                        return;
                                    }
                                    ChatInvite chatInvite = (ChatInvite) tLObject;
                                    if (chatInvite.chat != null && !ChatObject.isLeftFromChat(chatInvite.chat)) {
                                        MessagesController.getInstance().putChat(chatInvite.chat, false);
                                        ArrayList arrayList = new ArrayList();
                                        arrayList.add(chatInvite.chat);
                                        MessagesStorage.getInstance().putUsersAndChats(null, arrayList, false, true);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("chat_id", chatInvite.chat.id);
                                        if (LaunchActivity.mainFragmentsStack.isEmpty() || MessagesController.checkCanOpenChat(bundle, (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1))) {
                                            BaseFragment chatActivity = new ChatActivity(bundle);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                            LaunchActivity.this.actionBarLayout.presentFragment(chatActivity, false, true, true);
                                        }
                                    } else if (((chatInvite.chat != null || (chatInvite.channel && !chatInvite.megagroup)) && (chatInvite.chat == null || (ChatObject.isChannel(chatInvite.chat) && !chatInvite.chat.megagroup))) || LaunchActivity.mainFragmentsStack.isEmpty()) {
                                        Builder builder2 = new Builder(LaunchActivity.this);
                                        builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        String str = "ChannelJoinTo";
                                        Object[] objArr = new Object[1];
                                        objArr[0] = chatInvite.chat != null ? chatInvite.chat.title : chatInvite.title;
                                        builder2.setMessage(LocaleController.formatString(str, R.string.ChannelJoinTo, objArr));
                                        builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C48411());
                                        builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                        LaunchActivity.this.showAlertDialog(builder2);
                                    } else {
                                        BaseFragment baseFragment = (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1);
                                        baseFragment.showDialog(new JoinGroupAlert(LaunchActivity.this, chatInvite, str8, baseFragment));
                                    }
                                }
                            }
                        });
                    }
                }, 2);
            } else if (i == 1) {
                TLObject tLRPC$TL_messages_importChatInvite = new TLRPC$TL_messages_importChatInvite();
                tLRPC$TL_messages_importChatInvite.hash = str2;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_importChatInvite, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null) {
                            MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (!LaunchActivity.this.isFinishing()) {
                                    try {
                                        alertDialog.dismiss();
                                    } catch (Throwable e) {
                                        FileLog.e(e);
                                    }
                                    if (tLRPC$TL_error != null) {
                                        Builder builder = new Builder(LaunchActivity.this);
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                            builder.setMessage(LocaleController.getString("FloodWait", R.string.FloodWait));
                                        } else if (tLRPC$TL_error.text.equals("USERS_TOO_MUCH")) {
                                            builder.setMessage(LocaleController.getString("JoinToGroupErrorFull", R.string.JoinToGroupErrorFull));
                                        } else {
                                            builder.setMessage(LocaleController.getString("JoinToGroupErrorNotExist", R.string.JoinToGroupErrorNotExist));
                                        }
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        LaunchActivity.this.showAlertDialog(builder);
                                    } else if (LaunchActivity.this.actionBarLayout != null) {
                                        TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                                        if (!tLRPC$Updates.chats.isEmpty()) {
                                            Chat chat = (Chat) tLRPC$Updates.chats.get(0);
                                            chat.left = false;
                                            chat.kicked = false;
                                            MessagesController.getInstance().putUsers(tLRPC$Updates.users, false);
                                            MessagesController.getInstance().putChats(tLRPC$Updates.chats, false);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("chat_id", chat.id);
                                            if (LaunchActivity.mainFragmentsStack.isEmpty() || MessagesController.checkCanOpenChat(bundle, (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1))) {
                                                BaseFragment chatActivity = new ChatActivity(bundle);
                                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                                LaunchActivity.this.actionBarLayout.presentFragment(chatActivity, false, true, true);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }, 2);
            }
        } else if (str3 != null) {
            if (!mainFragmentsStack.isEmpty()) {
                InputStickerSet tLRPC$TL_inputStickerSetShortName = new TLRPC$TL_inputStickerSetShortName();
                tLRPC$TL_inputStickerSetShortName.short_name = str3;
                BaseFragment baseFragment = (BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1);
                baseFragment.showDialog(new StickersAlert(this, baseFragment, tLRPC$TL_inputStickerSetShortName, null, null));
                return;
            }
            return;
        } else if (str6 != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("onlySelect", true);
            BaseFragment dialogsActivity = new DialogsActivity(bundle);
            final boolean z3 = z;
            final String str15 = str6;
            dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                    long longValue = ((Long) arrayList.get(0)).longValue();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("scrollToTopOnResume", true);
                    bundle.putBoolean("hasUrl", z3);
                    int i = (int) longValue;
                    int i2 = (int) (longValue >> 32);
                    if (i == 0) {
                        bundle.putInt("enc_id", i2);
                    } else if (i2 == 1) {
                        bundle.putInt("chat_id", i);
                    } else if (i > 0) {
                        bundle.putInt("user_id", i);
                    } else if (i < 0) {
                        bundle.putInt("chat_id", -i);
                    }
                    if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        DraftQuery.saveDraft(longValue, str15, null, null, true);
                        LaunchActivity.this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true);
                    }
                }
            });
            presentFragment(dialogsActivity, false, true);
        } else if (strArr != null) {
        }
        if (i2 != 0) {
            alertDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConnectionsManager.getInstance().cancelRequest(i2, true);
                    try {
                        dialogInterface.dismiss();
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            });
            try {
                alertDialog.show();
            } catch (Exception e) {
            }
        }
    }

    private void showLanguageAlert(boolean z) {
        int i = 0;
        try {
            if (!this.loadingLocaleDialog) {
                String string = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getString("language_showed2", TtmlNode.ANONYMOUS_REGION_ID);
                final String toLowerCase = LocaleController.getSystemLocaleStringIso639().toLowerCase();
                if (z || !string.equals(toLowerCase)) {
                    Object obj;
                    final LocaleInfo[] localeInfoArr = new LocaleInfo[2];
                    if (toLowerCase.contains("-")) {
                        obj = toLowerCase.split("-")[0];
                    } else {
                        String str = toLowerCase;
                    }
                    Object obj2;
                    if ("in".equals(obj)) {
                        obj2 = TtmlNode.ATTR_ID;
                    } else if ("iw".equals(obj)) {
                        string = "he";
                    } else if ("jw".equals(obj)) {
                        string = "jv";
                    } else {
                        obj2 = null;
                    }
                    while (i < LocaleController.getInstance().languages.size()) {
                        LocaleInfo localeInfo = (LocaleInfo) LocaleController.getInstance().languages.get(i);
                        if (localeInfo.shortName.equals("en")) {
                            localeInfoArr[0] = localeInfo;
                        }
                        if (localeInfo.shortName.replace("_", "-").equals(toLowerCase) || localeInfo.shortName.equals(obj) || (r1 != null && localeInfo.shortName.equals(r1))) {
                            localeInfoArr[1] = localeInfo;
                        }
                        if (localeInfoArr[0] != null && localeInfoArr[1] != null) {
                            break;
                        }
                        i++;
                    }
                    if (localeInfoArr[0] != null && localeInfoArr[1] != null && localeInfoArr[0] != localeInfoArr[1]) {
                        FileLog.d("show lang alert for " + localeInfoArr[0].getKey() + " and " + localeInfoArr[1].getKey());
                        this.systemLocaleStrings = null;
                        this.englishLocaleStrings = null;
                        this.loadingLocaleDialog = true;
                        TLObject tLRPC$TL_langpack_getStrings = new TLRPC$TL_langpack_getStrings();
                        tLRPC$TL_langpack_getStrings.lang_code = localeInfoArr[1].shortName.replace("_", "-");
                        tLRPC$TL_langpack_getStrings.keys.add("English");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguage");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguageOther");
                        tLRPC$TL_langpack_getStrings.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_langpack_getStrings, new RequestDelegate() {
                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                final HashMap hashMap = new HashMap();
                                if (tLObject != null) {
                                    TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                                    for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                                        LangPackString langPackString = (LangPackString) tLRPC$Vector.objects.get(i);
                                        hashMap.put(langPackString.key, langPackString.value);
                                    }
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        LaunchActivity.this.systemLocaleStrings = hashMap;
                                        if (LaunchActivity.this.englishLocaleStrings != null && LaunchActivity.this.systemLocaleStrings != null) {
                                            LaunchActivity.this.showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], toLowerCase);
                                        }
                                    }
                                });
                            }
                        }, 8);
                        tLRPC$TL_langpack_getStrings = new TLRPC$TL_langpack_getStrings();
                        tLRPC$TL_langpack_getStrings.lang_code = localeInfoArr[0].shortName.replace("_", "-");
                        tLRPC$TL_langpack_getStrings.keys.add("English");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguage");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguageOther");
                        tLRPC$TL_langpack_getStrings.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_langpack_getStrings, new RequestDelegate() {
                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                final HashMap hashMap = new HashMap();
                                if (tLObject != null) {
                                    TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                                    for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                                        LangPackString langPackString = (LangPackString) tLRPC$Vector.objects.get(i);
                                        hashMap.put(langPackString.key, langPackString.value);
                                    }
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        LaunchActivity.this.englishLocaleStrings = hashMap;
                                        if (LaunchActivity.this.englishLocaleStrings != null && LaunchActivity.this.systemLocaleStrings != null) {
                                            LaunchActivity.this.showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], toLowerCase);
                                        }
                                    }
                                });
                            }
                        }, 8);
                        return;
                    }
                    return;
                }
                FileLog.d("alert already showed for " + string);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    private void showLanguageAlertInternal(LocaleInfo localeInfo, LocaleInfo localeInfo2, String str) {
        try {
            this.loadingLocaleDialog = false;
            Object obj = (localeInfo.builtIn || LocaleController.getInstance().isCurrentLocalLocale()) ? 1 : null;
            Builder builder = new Builder(this);
            builder.setTitle(getStringForLanguageAlert(this.systemLocaleStrings, "ChooseYourLanguage", R.string.ChooseYourLanguage));
            builder.setSubtitle(getStringForLanguageAlert(this.englishLocaleStrings, "ChooseYourLanguage", R.string.ChooseYourLanguage));
            View linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            final LanguageCell[] languageCellArr = new LanguageCell[2];
            final LocaleInfo[] localeInfoArr = new LocaleInfo[1];
            LocaleInfo[] localeInfoArr2 = new LocaleInfo[2];
            String stringForLanguageAlert = getStringForLanguageAlert(this.systemLocaleStrings, "English", R.string.English);
            localeInfoArr2[0] = obj != null ? localeInfo : localeInfo2;
            localeInfoArr2[1] = obj != null ? localeInfo2 : localeInfo;
            if (obj == null) {
                localeInfo = localeInfo2;
            }
            localeInfoArr[0] = localeInfo;
            int i = 0;
            while (i < 2) {
                languageCellArr[i] = new LanguageCell(this, true);
                languageCellArr[i].setLanguage(localeInfoArr2[i], localeInfoArr2[i] == localeInfo2 ? stringForLanguageAlert : null, true);
                languageCellArr[i].setTag(Integer.valueOf(i));
                languageCellArr[i].setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 2));
                languageCellArr[i].setLanguageSelected(i == 0);
                linearLayout.addView(languageCellArr[i], LayoutHelper.createLinear(-1, 48));
                languageCellArr[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Integer num = (Integer) view.getTag();
                        localeInfoArr[0] = ((LanguageCell) view).getCurrentLocale();
                        int i = 0;
                        while (i < languageCellArr.length) {
                            languageCellArr[i].setLanguageSelected(i == num.intValue());
                            i++;
                        }
                    }
                });
                i++;
            }
            View languageCell = new LanguageCell(this, true);
            languageCell.setValue(getStringForLanguageAlert(this.systemLocaleStrings, "ChooseYourLanguageOther", R.string.ChooseYourLanguageOther), getStringForLanguageAlert(this.englishLocaleStrings, "ChooseYourLanguageOther", R.string.ChooseYourLanguageOther));
            languageCell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    LaunchActivity.this.localeDialog = null;
                    LaunchActivity.this.drawerLayoutContainer.closeDrawer(true);
                    LaunchActivity.this.presentFragment(new LanguageSelectActivity());
                    if (LaunchActivity.this.visibleDialog != null) {
                        LaunchActivity.this.visibleDialog.dismiss();
                        LaunchActivity.this.visibleDialog = null;
                    }
                }
            });
            linearLayout.addView(languageCell, LayoutHelper.createLinear(-1, 48));
            builder.setView(linearLayout);
            builder.setNegativeButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    LocaleController.getInstance().applyLanguage(localeInfoArr[0], true, false);
                    LaunchActivity.this.rebuildAllFragments(true);
                }
            });
            this.localeDialog = showAlertDialog(builder);
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putString("language_showed2", str).commit();
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    private void showPasscodeActivity() {
        if (this.passcodeView != null) {
            UserConfig.appLocked = true;
            if (SecretMediaViewer.getInstance().isVisible()) {
                SecretMediaViewer.getInstance().closePhoto(false, false);
            } else if (PhotoViewer.getInstance().isVisible()) {
                PhotoViewer.getInstance().closePhoto(false, true);
            } else if (ArticleViewer.getInstance().isVisible()) {
                ArticleViewer.getInstance().close(false, true);
            }
            this.passcodeView.onShow();
            UserConfig.isWaitingForPasscodeEnter = true;
            this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
            this.passcodeView.setDelegate(new PasscodeViewDelegate() {
                public void didAcceptedPassword() {
                    UserConfig.isWaitingForPasscodeEnter = false;
                    if (LaunchActivity.this.passcodeSaveIntent != null) {
                        LaunchActivity.this.handleIntent(LaunchActivity.this.passcodeSaveIntent, LaunchActivity.this.passcodeSaveIntentIsNew, LaunchActivity.this.passcodeSaveIntentIsRestore, true);
                        LaunchActivity.this.passcodeSaveIntent = null;
                    }
                    LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    LaunchActivity.this.actionBarLayout.showLastFragment();
                    if (AndroidUtilities.isTablet()) {
                        LaunchActivity.this.layersActionBarLayout.showLastFragment();
                        LaunchActivity.this.rightActionBarLayout.showLastFragment();
                    }
                }
            });
        }
    }

    private void updateCurrentConnectionState() {
        String string;
        String str;
        Runnable runnable = null;
        if (this.currentConnectionState == 2) {
            string = LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork);
            str = null;
        } else if (this.currentConnectionState == 1) {
            string = LocaleController.getString("Connecting", R.string.Connecting);
            AnonymousClass44 anonymousClass44 = new Runnable() {
                public void run() {
                    if (AndroidUtilities.isTablet()) {
                        if (!LaunchActivity.layerFragmentsStack.isEmpty() && (LaunchActivity.layerFragmentsStack.get(LaunchActivity.layerFragmentsStack.size() - 1) instanceof ProxySettingsActivity)) {
                            return;
                        }
                    } else if (!LaunchActivity.mainFragmentsStack.isEmpty() && (LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1) instanceof ProxySettingsActivity)) {
                        return;
                    }
                    LaunchActivity.this.presentFragment(new ProxySettingsActivity());
                }
            };
            str = null;
            Object obj = anonymousClass44;
        } else if (this.currentConnectionState == 5) {
            string = LocaleController.getString("Updating", R.string.Updating);
            str = null;
        } else if (this.currentConnectionState != 4) {
            str = null;
            string = null;
        } else if (C2820e.a()) {
            string = LocaleController.getString("Connecting2", R.string.Connecting2);
            str = null;
        } else {
            string = LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy);
            str = LocaleController.getString("ConnectingToProxyTapToDisable", R.string.ConnectingToProxyTapToDisable);
            runnable = new Runnable() {

                /* renamed from: org.telegram.ui.LaunchActivity$45$1 */
                class C48501 implements OnClickListener {
                    C48501() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                        edit.putBoolean("proxy_enabled", false);
                        edit.commit();
                        ConnectionsManager.native_setProxySettings(TtmlNode.ANONYMOUS_REGION_ID, 0, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
                    }
                }

                public void run() {
                    if (LaunchActivity.this.actionBarLayout != null && !LaunchActivity.this.actionBarLayout.fragmentsStack.isEmpty()) {
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                        BaseFragment baseFragment = (BaseFragment) LaunchActivity.this.actionBarLayout.fragmentsStack.get(LaunchActivity.this.actionBarLayout.fragmentsStack.size() - 1);
                        Builder builder = new Builder(LaunchActivity.this);
                        builder.setTitle(LocaleController.getString("Proxy", R.string.Proxy));
                        builder.setMessage(LocaleController.formatString("ConnectingToProxyDisableAlert", R.string.ConnectingToProxyDisableAlert, new Object[]{sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID)}));
                        builder.setPositiveButton(LocaleController.getString("ConnectingToProxyDisable", R.string.ConnectingToProxyDisable), new C48501());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        baseFragment.showDialog(builder.create());
                    }
                }
            };
        }
        this.actionBarLayout.setTitleOverlayText(string, str, runnable);
    }

    boolean back1() {
        try {
            if (this.actionBarLayout != null && this.actionBarLayout.fragmentsStack != null && this.actionBarLayout.fragmentsStack.size() > 1 && this.actionBarLayout.fragmentsStack.size() > 1) {
                return true;
            }
            if (C2874e.b().a().size() > 0) {
                ArrayList a = C2874e.b().a();
                String str = (String) a.get(a.size() - 1);
                String str2 = (String) a.get(a.size() - 2);
                C2874e.b().a().remove(str);
                try {
                    Log.d("alireza", "alireza" + str);
                    Log.d("alireza", "alireza will" + str2);
                } catch (Exception e) {
                }
                int i = str2.contentEquals("FRG_HOME") ? 3 : str2.contentEquals("FRG_HOT") ? 2 : str2.contentEquals("FRG_SEARCH") ? 1 : str2.contentEquals("FRG_NEWS_LIST") ? 0 : 3;
                Intent intent = new Intent("ACTION_SWITCH_TAB");
                intent.putExtra("EXTRA_CURRENT_POSITION", i);
                C0424l.a(getApplicationContext()).a(intent);
                return false;
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.appDidLogout) {
            if (this.drawerLayoutAdapter != null) {
                this.drawerLayoutAdapter.notifyDataSetChanged();
            }
            Iterator it = this.actionBarLayout.fragmentsStack.iterator();
            while (it.hasNext()) {
                ((BaseFragment) it.next()).onFragmentDestroy();
            }
            this.actionBarLayout.fragmentsStack.clear();
            if (AndroidUtilities.isTablet()) {
                it = this.layersActionBarLayout.fragmentsStack.iterator();
                while (it.hasNext()) {
                    ((BaseFragment) it.next()).onFragmentDestroy();
                }
                this.layersActionBarLayout.fragmentsStack.clear();
                it = this.rightActionBarLayout.fragmentsStack.iterator();
                while (it.hasNext()) {
                    ((BaseFragment) it.next()).onFragmentDestroy();
                }
                this.rightActionBarLayout.fragmentsStack.clear();
            }
            startActivity(new Intent(this, IntroActivity.class));
            onFinish();
            finish();
        } else if (i == NotificationCenter.closeOtherAppActivities) {
            if (objArr[0] != this) {
                onFinish();
                finish();
            }
        } else if (i == NotificationCenter.didUpdatedConnectionState) {
            int connectionState = ConnectionsManager.getInstance().getConnectionState();
            if (this.currentConnectionState != connectionState) {
                FileLog.d("switch to state " + connectionState);
                this.currentConnectionState = connectionState;
                updateCurrentConnectionState();
            }
        } else if (i == NotificationCenter.mainUserInfoChanged) {
            this.drawerLayoutAdapter.notifyDataSetChanged();
        } else if (i == NotificationCenter.needShowAlert) {
            Integer num = (Integer) objArr[0];
            r1 = new Builder(this);
            r1.setTitle(LocaleController.getString("AppName", R.string.AppName));
            r1.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            if (num.intValue() != 2) {
                r1.setNegativeButton(LocaleController.getString("MoreInfo", R.string.MoreInfo), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!LaunchActivity.mainFragmentsStack.isEmpty()) {
                            MessagesController.openByUserName("spambot", (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                        }
                    }
                });
            }
            if (num.intValue() == 0) {
                r1.setMessage(LocaleController.getString("NobodyLikesSpam1", R.string.NobodyLikesSpam1));
            } else if (num.intValue() == 1) {
                r1.setMessage(LocaleController.getString("NobodyLikesSpam2", R.string.NobodyLikesSpam2));
            } else if (num.intValue() == 2) {
                r1.setMessage((String) objArr[1]);
            }
            if (!mainFragmentsStack.isEmpty()) {
                ((BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1)).showDialog(r1.create());
            }
        } else if (i == NotificationCenter.wasUnableToFindCurrentLocation) {
            r0 = (HashMap) objArr[0];
            r1 = new Builder(this);
            r1.setTitle(LocaleController.getString("AppName", R.string.AppName));
            r1.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            r1.setNegativeButton(LocaleController.getString("ShareYouLocationUnableManually", R.string.ShareYouLocationUnableManually), new OnClickListener() {

                /* renamed from: org.telegram.ui.LaunchActivity$33$1 */
                class C48451 implements LocationActivityDelegate {
                    C48451() {
                    }

                    public void didSelectLocation(MessageMedia messageMedia, int i) {
                        for (Entry value : r0.entrySet()) {
                            MessageObject messageObject = (MessageObject) value.getValue();
                            SendMessagesHelper.getInstance().sendMessage(messageMedia, messageObject.getDialogId(), messageObject, null, null);
                        }
                    }
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!LaunchActivity.mainFragmentsStack.isEmpty() && AndroidUtilities.isGoogleMapsInstalled((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1))) {
                        BaseFragment locationActivity = new LocationActivity(0);
                        locationActivity.setDelegate(new C48451());
                        LaunchActivity.this.presentFragment(locationActivity);
                    }
                }
            });
            r1.setMessage(LocaleController.getString("ShareYouLocationUnable", R.string.ShareYouLocationUnable));
            if (!mainFragmentsStack.isEmpty()) {
                ((BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1)).showDialog(r1.create());
            }
        } else if (i == NotificationCenter.didSetNewWallpapper) {
            if (this.sideMenu != null) {
                View childAt = this.sideMenu.getChildAt(0);
                if (childAt != null) {
                    childAt.invalidate();
                }
            }
        } else if (i == NotificationCenter.didSetPasscode) {
            if (UserConfig.passcodeHash.length() <= 0 || UserConfig.allowScreenCapture) {
                try {
                    getWindow().clearFlags(MessagesController.UPDATE_MASK_CHANNEL);
                    return;
                } catch (Throwable e) {
                    FileLog.e(e);
                    return;
                }
            }
            try {
                getWindow().setFlags(MessagesController.UPDATE_MASK_CHANNEL, MessagesController.UPDATE_MASK_CHANNEL);
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        } else if (i == NotificationCenter.reloadInterface) {
            rebuildAllFragments(true);
        } else if (i == NotificationCenter.suggestedLangpack) {
            showLanguageAlert(false);
        } else if (i == NotificationCenter.openArticle) {
            if (!mainFragmentsStack.isEmpty()) {
                ArticleViewer.getInstance().setParentActivity(this, (BaseFragment) mainFragmentsStack.get(mainFragmentsStack.size() - 1));
                ArticleViewer.getInstance().open((TLRPC$TL_webPage) objArr[0], (String) objArr[1]);
            }
        } else if (i == NotificationCenter.hasNewContactsToImport) {
            if (this.actionBarLayout != null && !this.actionBarLayout.fragmentsStack.isEmpty()) {
                ((Integer) objArr[0]).intValue();
                r0 = (HashMap) objArr[1];
                final boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
                final boolean booleanValue2 = ((Boolean) objArr[3]).booleanValue();
                BaseFragment baseFragment = (BaseFragment) this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1);
                Builder builder = new Builder(this);
                builder.setTitle(LocaleController.getString("UpdateContactsTitle", R.string.UpdateContactsTitle));
                builder.setMessage(LocaleController.getString("UpdateContactsMessage", R.string.UpdateContactsMessage));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactsController.getInstance().syncPhoneBookByAlert(r0, booleanValue, booleanValue2, false);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactsController.getInstance().syncPhoneBookByAlert(r0, booleanValue, booleanValue2, true);
                    }
                });
                builder.setOnBackButtonListener(new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactsController.getInstance().syncPhoneBookByAlert(r0, booleanValue, booleanValue2, true);
                    }
                });
                Dialog create = builder.create();
                baseFragment.showDialog(create);
                create.setCanceledOnTouchOutside(false);
            }
        } else if (i == NotificationCenter.didSetNewTheme) {
            if (this.sideMenu != null) {
                this.sideMenu.setBackgroundColor(Theme.getColor(Theme.key_chats_menuBackground));
                this.sideMenu.setGlowColor(Theme.getColor(Theme.key_chats_menuBackground));
                this.sideMenu.getAdapter().notifyDataSetChanged();
            }
            if (VERSION.SDK_INT >= 21) {
                try {
                    setTaskDescription(new TaskDescription(null, null, Theme.getColor(Theme.key_actionBarDefault) | Theme.ACTION_BAR_VIDEO_EDIT_COLOR));
                } catch (Exception e3) {
                }
            }
        }
    }

    public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
        long longValue = ((Long) arrayList.get(0)).longValue();
        int i = (int) longValue;
        int i2 = (int) (longValue >> 32);
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        if (!AndroidUtilities.isTablet()) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
        }
        if (i == 0) {
            bundle.putInt("enc_id", i2);
        } else if (i2 == 1) {
            bundle.putInt("chat_id", i);
        } else if (i > 0) {
            bundle.putInt("user_id", i);
        } else if (i < 0) {
            bundle.putInt("chat_id", -i);
        }
        if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
            BaseFragment chatActivity = new ChatActivity(bundle);
            this.actionBarLayout.presentFragment(chatActivity, dialogsActivity != null, dialogsActivity == null, true);
            if (this.videoPath != null) {
                chatActivity.openVideoEditor(this.videoPath, this.sendingText);
                this.sendingText = null;
            }
            if (this.photoPathsArray != null) {
                if (this.sendingText != null && this.sendingText.length() <= Callback.DEFAULT_DRAG_ANIMATION_DURATION && this.photoPathsArray.size() == 1) {
                    ((SendingMediaInfo) this.photoPathsArray.get(0)).caption = this.sendingText;
                }
                SendMessagesHelper.prepareSendingMedia(this.photoPathsArray, longValue, null, null, false, false);
            }
            if (this.sendingText != null) {
                SendMessagesHelper.prepareSendingText(this.sendingText, longValue);
            }
            if (!(this.documentsPathsArray == null && this.documentsUrisArray == null)) {
                SendMessagesHelper.prepareSendingDocuments(this.documentsPathsArray, this.documentsOriginalPathsArray, this.documentsUrisArray, this.documentsMimeType, longValue, null, null);
            }
            if (!(this.contactsToSend == null || this.contactsToSend.isEmpty())) {
                Iterator it = this.contactsToSend.iterator();
                while (it.hasNext()) {
                    SendMessagesHelper.getInstance().sendMessage((User) it.next(), longValue, null, null, null);
                }
            }
            this.photoPathsArray = null;
            this.videoPath = null;
            this.sendingText = null;
            this.documentsPathsArray = null;
            this.documentsOriginalPathsArray = null;
            this.contactsToSend = null;
        }
    }

    public ActionBarLayout getActionBarLayout() {
        return this.actionBarLayout;
    }

    public ActionBarLayout getLayersActionBarLayout() {
        return this.layersActionBarLayout;
    }

    public ActionBarLayout getRightActionBarLayout() {
        return this.rightActionBarLayout;
    }

    public boolean needAddFragmentToStack(BaseFragment baseFragment, ActionBarLayout actionBarLayout) {
        if (AndroidUtilities.isTablet()) {
            DrawerLayoutContainer drawerLayoutContainer = this.drawerLayoutContainer;
            boolean z = ((baseFragment instanceof LoginActivity) || (baseFragment instanceof CountrySelectActivity) || this.layersActionBarLayout.getVisibility() == 0) ? false : true;
            drawerLayoutContainer.setAllowOpenDrawer(z, true);
            if (baseFragment instanceof DialogsActivity) {
                if (((DialogsActivity) baseFragment).isMainDialogList() && actionBarLayout != this.actionBarLayout) {
                    this.actionBarLayout.removeAllFragments();
                    this.actionBarLayout.addFragmentToStack(baseFragment);
                    this.layersActionBarLayout.removeAllFragments();
                    this.layersActionBarLayout.setVisibility(8);
                    this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    if (this.tabletFullSize) {
                        return false;
                    }
                    this.shadowTabletSide.setVisibility(0);
                    if (!this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                        return false;
                    }
                    this.backgroundTablet.setVisibility(0);
                    return false;
                }
            } else if (baseFragment instanceof ChatActivity) {
                if (!this.tabletFullSize && actionBarLayout != this.rightActionBarLayout) {
                    this.rightActionBarLayout.setVisibility(0);
                    this.backgroundTablet.setVisibility(8);
                    this.rightActionBarLayout.removeAllFragments();
                    this.rightActionBarLayout.addFragmentToStack(baseFragment);
                    if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        return false;
                    }
                    while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                        this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(true);
                    return false;
                } else if (this.tabletFullSize && actionBarLayout != this.actionBarLayout) {
                    this.actionBarLayout.addFragmentToStack(baseFragment);
                    if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        return false;
                    }
                    while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                        this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(true);
                    return false;
                }
            } else if (actionBarLayout != this.layersActionBarLayout) {
                this.layersActionBarLayout.setVisibility(0);
                this.drawerLayoutContainer.setAllowOpenDrawer(false, true);
                if (baseFragment instanceof LoginActivity) {
                    this.backgroundTablet.setVisibility(0);
                    this.shadowTabletSide.setVisibility(8);
                    this.shadowTablet.setBackgroundColor(0);
                } else {
                    this.shadowTablet.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
                }
                this.layersActionBarLayout.addFragmentToStack(baseFragment);
                return false;
            }
            return true;
        }
        drawerLayoutContainer = this.drawerLayoutContainer;
        z = ((baseFragment instanceof LoginActivity) || (baseFragment instanceof CountrySelectActivity)) ? false : true;
        drawerLayoutContainer.setAllowOpenDrawer(z, false);
        return true;
    }

    public boolean needCloseLastFragment(ActionBarLayout actionBarLayout) {
        if (AndroidUtilities.isTablet()) {
            if (actionBarLayout == this.actionBarLayout && actionBarLayout.fragmentsStack.size() <= 1) {
                onFinish();
                finish();
                return false;
            } else if (actionBarLayout == this.rightActionBarLayout) {
                if (!this.tabletFullSize) {
                    this.backgroundTablet.setVisibility(0);
                }
            } else if (actionBarLayout == this.layersActionBarLayout && this.actionBarLayout.fragmentsStack.isEmpty() && this.layersActionBarLayout.fragmentsStack.size() == 1) {
                onFinish();
                finish();
                return false;
            }
        } else if (actionBarLayout.fragmentsStack.size() <= 1) {
            onFinish();
            finish();
            return false;
        } else if (actionBarLayout.fragmentsStack.size() >= 2 && !(actionBarLayout.fragmentsStack.get(0) instanceof LoginActivity)) {
            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
        }
        return true;
    }

    public boolean needPresentFragment(BaseFragment baseFragment, boolean z, boolean z2, ActionBarLayout actionBarLayout) {
        boolean z3 = true;
        if (ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        if (AndroidUtilities.isTablet()) {
            DrawerLayoutContainer drawerLayoutContainer = this.drawerLayoutContainer;
            boolean z4 = ((baseFragment instanceof LoginActivity) || (baseFragment instanceof CountrySelectActivity) || this.layersActionBarLayout.getVisibility() == 0) ? false : true;
            drawerLayoutContainer.setAllowOpenDrawer(z4, true);
            if ((baseFragment instanceof DialogsActivity) && ((DialogsActivity) baseFragment).isMainDialogList() && actionBarLayout != this.actionBarLayout) {
                this.actionBarLayout.removeAllFragments();
                this.actionBarLayout.presentFragment(baseFragment, z, z2, false);
                this.layersActionBarLayout.removeAllFragments();
                this.layersActionBarLayout.setVisibility(8);
                this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                if (this.tabletFullSize) {
                    return false;
                }
                this.shadowTabletSide.setVisibility(0);
                if (!this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                    return false;
                }
                this.backgroundTablet.setVisibility(0);
                return false;
            } else if (baseFragment instanceof ChatActivity) {
                ActionBarLayout actionBarLayout2;
                if ((!this.tabletFullSize && actionBarLayout == this.rightActionBarLayout) || (this.tabletFullSize && actionBarLayout == this.actionBarLayout)) {
                    boolean z5 = (this.tabletFullSize && actionBarLayout == this.actionBarLayout && this.actionBarLayout.fragmentsStack.size() == 1) ? false : true;
                    if (!this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                            this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                        }
                        actionBarLayout2 = this.layersActionBarLayout;
                        if (z2) {
                            z3 = false;
                        }
                        actionBarLayout2.closeLastFragment(z3);
                    }
                    if (!z5) {
                        this.actionBarLayout.presentFragment(baseFragment, false, z2, false);
                    }
                    return z5;
                } else if (!this.tabletFullSize && actionBarLayout != this.rightActionBarLayout) {
                    this.rightActionBarLayout.setVisibility(0);
                    this.backgroundTablet.setVisibility(8);
                    this.rightActionBarLayout.removeAllFragments();
                    this.rightActionBarLayout.presentFragment(baseFragment, z, true, false);
                    if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        return false;
                    }
                    while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                        this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                    }
                    actionBarLayout2 = this.layersActionBarLayout;
                    if (z2) {
                        z3 = false;
                    }
                    actionBarLayout2.closeLastFragment(z3);
                    return false;
                } else if (!this.tabletFullSize || actionBarLayout == this.actionBarLayout) {
                    if (!this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                            this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                        }
                        this.layersActionBarLayout.closeLastFragment(!z2);
                    }
                    actionBarLayout2 = this.actionBarLayout;
                    if (this.actionBarLayout.fragmentsStack.size() <= 1) {
                        z3 = false;
                    }
                    actionBarLayout2.presentFragment(baseFragment, z3, z2, false);
                    return false;
                } else {
                    this.actionBarLayout.presentFragment(baseFragment, this.actionBarLayout.fragmentsStack.size() > 1, z2, false);
                    if (this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                        return false;
                    }
                    while (this.layersActionBarLayout.fragmentsStack.size() - 1 > 0) {
                        this.layersActionBarLayout.removeFragmentFromStack((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(0));
                    }
                    actionBarLayout2 = this.layersActionBarLayout;
                    if (z2) {
                        z3 = false;
                    }
                    actionBarLayout2.closeLastFragment(z3);
                    return false;
                }
            } else if (actionBarLayout == this.layersActionBarLayout) {
                return true;
            } else {
                this.layersActionBarLayout.setVisibility(0);
                this.drawerLayoutContainer.setAllowOpenDrawer(false, true);
                if (baseFragment instanceof LoginActivity) {
                    this.backgroundTablet.setVisibility(0);
                    this.shadowTabletSide.setVisibility(8);
                    this.shadowTablet.setBackgroundColor(0);
                } else {
                    this.shadowTablet.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
                }
                this.layersActionBarLayout.presentFragment(baseFragment, z, z2, false);
                return false;
            }
        }
        drawerLayoutContainer = this.drawerLayoutContainer;
        z4 = ((baseFragment instanceof LoginActivity) || (baseFragment instanceof CountrySelectActivity)) ? false : true;
        drawerLayoutContainer.setAllowOpenDrawer(z4, false);
        return true;
    }

    public void onActionModeFinished(ActionMode actionMode) {
        super.onActionModeFinished(actionMode);
        if (VERSION.SDK_INT < 23 || actionMode.getType() != 1) {
            this.actionBarLayout.onActionModeFinished(actionMode);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.onActionModeFinished(actionMode);
                this.layersActionBarLayout.onActionModeFinished(actionMode);
            }
        }
    }

    public void onActionModeStarted(ActionMode actionMode) {
        super.onActionModeStarted(actionMode);
        try {
            Menu menu = actionMode.getMenu();
            if (!(menu == null || this.actionBarLayout.extendActionMode(menu) || !AndroidUtilities.isTablet() || this.rightActionBarLayout.extendActionMode(menu))) {
                this.layersActionBarLayout.extendActionMode(menu);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (VERSION.SDK_INT < 23 || actionMode.getType() != 1) {
            this.actionBarLayout.onActionModeStarted(actionMode);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.onActionModeStarted(actionMode);
                this.layersActionBarLayout.onActionModeStarted(actionMode);
            }
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (!(UserConfig.passcodeHash.length() == 0 || UserConfig.lastPauseTime == 0)) {
            UserConfig.lastPauseTime = 0;
            UserConfig.saveConfig(false);
        }
        super.onActivityResult(i, i2, intent);
        ThemeEditorView instance = ThemeEditorView.getInstance();
        if (instance != null) {
            instance.onActivityResult(i, i2, intent);
        }
        if (this.actionBarLayout.fragmentsStack.size() != 0) {
            ((BaseFragment) this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1)).onActivityResultFragment(i, i2, intent);
        }
        if (AndroidUtilities.isTablet()) {
            if (this.rightActionBarLayout.fragmentsStack.size() != 0) {
                ((BaseFragment) this.rightActionBarLayout.fragmentsStack.get(this.rightActionBarLayout.fragmentsStack.size() - 1)).onActivityResultFragment(i, i2, intent);
            }
            if (this.layersActionBarLayout.fragmentsStack.size() != 0) {
                ((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(this.layersActionBarLayout.fragmentsStack.size() - 1)).onActivityResultFragment(i, i2, intent);
            }
        }
    }

    public void onBackPressed() {
        boolean z = false;
        if (this.passcodeView.getVisibility() == 0) {
            finish();
        } else if (SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(true, false);
        } else if (PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(true, false);
        } else if (ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(true, false);
        } else if (this.drawerLayoutContainer.isDrawerOpened()) {
            this.drawerLayoutContainer.closeDrawer(false);
        } else if (AndroidUtilities.isTablet()) {
            if (this.layersActionBarLayout.getVisibility() == 0) {
                this.layersActionBarLayout.onBackPressed();
                return;
            }
            if (this.rightActionBarLayout.getVisibility() == 0 && !this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                z = !((BaseFragment) this.rightActionBarLayout.fragmentsStack.get(this.rightActionBarLayout.fragmentsStack.size() + -1)).onBackPressed();
            }
            if (!z && back1()) {
                this.actionBarLayout.onBackPressed();
            }
        } else if (back1()) {
            this.actionBarLayout.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        AndroidUtilities.checkDisplaySize(this, configuration);
        super.onConfigurationChanged(configuration);
        checkLayout();
        PipRoundVideoView instance = PipRoundVideoView.getInstance();
        if (instance != null) {
            instance.onConfigurationChanged();
        }
        EmbedBottomSheet instance2 = EmbedBottomSheet.getInstance();
        if (instance2 != null) {
            instance2.onConfigurationChanged(configuration);
        }
        ThemeEditorView instance3 = ThemeEditorView.getInstance();
        if (instance3 != null) {
            instance3.onConfigurationChanged();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onCreate(android.os.Bundle r14) {
        /*
        r13 = this;
        r12 = 0;
        r1 = 8;
        r4 = -1;
        r3 = 1;
        r2 = 0;
        org.telegram.messenger.ApplicationLoader.postInitApplication();
        r0 = com.google.firebase.analytics.FirebaseAnalytics.getInstance(r13);
        r13.mFirebaseAnalytics = r0;
        me = r13;
        r0 = r13.getResources();
        r0 = r0.getConfiguration();
        org.telegram.messenger.AndroidUtilities.checkDisplaySize(r13, r0);
        r0 = android.os.Build.VERSION.SDK_INT;
        r5 = 16;
        if (r0 >= r5) goto L_0x003f;
    L_0x0022:
        r0 = new android.app.Dialog;
        r0.<init>(r13);
        r5 = 2130903142; // 0x7f030066 float:1.7413094E38 double:1.052806037E-314;
        r0.setContentView(r5);
        r5 = 2131689816; // 0x7f0f0158 float:1.9008658E38 double:1.0531947057E-314;
        r5 = r0.findViewById(r5);
        r6 = new org.telegram.ui.LaunchActivity$1;
        r6.<init>(r0);
        r5.setOnClickListener(r6);
        r0.show();
    L_0x003f:
        r0 = org.telegram.messenger.UserConfig.isClientActivated();
        if (r0 != 0) goto L_0x00dc;
    L_0x0045:
        r0 = r13.getIntent();
        if (r0 == 0) goto L_0x0072;
    L_0x004b:
        r5 = r0.getAction();
        if (r5 == 0) goto L_0x0072;
    L_0x0051:
        r5 = "android.intent.action.SEND";
        r6 = r0.getAction();
        r5 = r5.equals(r6);
        if (r5 != 0) goto L_0x006b;
    L_0x005e:
        r5 = r0.getAction();
        r6 = "android.intent.action.SEND_MULTIPLE";
        r5 = r5.equals(r6);
        if (r5 == 0) goto L_0x0072;
    L_0x006b:
        super.onCreate(r14);
        r13.finish();
    L_0x0071:
        return;
    L_0x0072:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r6 = "mainconfig";
        r5 = r5.getSharedPreferences(r6, r2);
        r6 = "intro_crashed_time";
        r8 = 0;
        r6 = r5.getLong(r6, r8);
        r8 = "fromIntro";
        r8 = r0.getBooleanExtra(r8, r2);
        if (r8 == 0) goto L_0x009d;
    L_0x008d:
        r5 = r5.edit();
        r9 = "intro_crashed_time";
        r10 = 0;
        r5 = r5.putLong(r9, r10);
        r5.commit();
    L_0x009d:
        r10 = java.lang.System.currentTimeMillis();
        r6 = r6 - r10;
        r6 = java.lang.Math.abs(r6);
        r10 = 120000; // 0x1d4c0 float:1.68156E-40 double:5.9288E-319;
        r5 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r5 < 0) goto L_0x00dc;
    L_0x00ad:
        if (r0 == 0) goto L_0x00dc;
    L_0x00af:
        if (r8 != 0) goto L_0x00dc;
    L_0x00b1:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r6 = "logininfo2";
        r5 = r5.getSharedPreferences(r6, r2);
        r5 = r5.getAll();
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x00dc;
    L_0x00c4:
        r1 = new android.content.Intent;
        r2 = org.telegram.ui.IntroActivity.class;
        r1.<init>(r13, r2);
        r0 = r0.getData();
        r1.setData(r0);
        r13.startActivity(r1);
        super.onCreate(r14);
        r13.finish();
        goto L_0x0071;
    L_0x00dc:
        r0 = utils.p178a.C3791b.n();
        if (r0 != 0) goto L_0x00ec;
    L_0x00e2:
        r0 = org.telegram.messenger.MessagesController.getInstance();
        r0.performLogout(r3);
        utils.p178a.C3791b.e(r3);
    L_0x00ec:
        r0 = new android.os.Handler;
        r0.<init>();
        r5 = new org.telegram.ui.LaunchActivity$2;
        r5.<init>();
        r6 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        r0.postDelayed(r5, r6);
        r0 = 12;
        r5 = new java.lang.String[r0];
        r0 = new org.telegram.ui.LaunchActivity$3;
        r0.<init>();
        r0 = r0.toString();
        r5[r2] = r0;
        r0 = new org.telegram.ui.LaunchActivity$4;
        r0.<init>();
        r0 = r0.toString();
        r5[r3] = r0;
        r0 = 2;
        r6 = new org.telegram.ui.LaunchActivity$5;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 3;
        r6 = new org.telegram.ui.LaunchActivity$6;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 4;
        r6 = new org.telegram.ui.LaunchActivity$7;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 5;
        r6 = new org.telegram.ui.LaunchActivity$8;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 6;
        r6 = new org.telegram.ui.LaunchActivity$9;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 7;
        r6 = new org.telegram.ui.LaunchActivity$10;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = new org.telegram.ui.LaunchActivity$11;
        r0.<init>();
        r0 = r0.toString();
        r5[r1] = r0;
        r0 = 9;
        r6 = new org.telegram.ui.LaunchActivity$12;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 10;
        r6 = new org.telegram.ui.LaunchActivity$13;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r0 = 11;
        r6 = new org.telegram.ui.LaunchActivity$14;
        r6.<init>();
        r6 = r6.toString();
        r5[r0] = r6;
        r6 = r5.length;
        r0 = r2;
    L_0x0191:
        if (r0 >= r6) goto L_0x0695;
    L_0x0193:
        r7 = r5[r0];
        r8 = "org.ir.talaeii";
        r7 = r7.equals(r8);
        if (r7 == 0) goto L_0x04ea;
    L_0x019e:
        r0 = r2;
    L_0x019f:
        if (r0 == 0) goto L_0x01ac;
    L_0x01a1:
        r0 = new java.lang.NullPointerException;
        r5 = "try to call onCreate on a null object reference.";
        r0.<init>(r5);
        utils.C3792d.a(r0);
    L_0x01ac:
        r13.requestWindowFeature(r3);
        r0 = 2131361950; // 0x7f0a009e float:1.8343667E38 double:1.0530327183E-314;
        r13.setTheme(r0);
        r0 = android.os.Build.VERSION.SDK_INT;
        r5 = 21;
        if (r0 < r5) goto L_0x01cf;
    L_0x01bb:
        r0 = new android.app.ActivityManager$TaskDescription;	 Catch:{ Exception -> 0x0690 }
        r5 = 0;
        r6 = 0;
        r7 = "actionBarDefault";
        r7 = org.telegram.ui.ActionBar.Theme.getColor(r7);	 Catch:{ Exception -> 0x0690 }
        r8 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r7 = r7 | r8;
        r0.<init>(r5, r6, r7);	 Catch:{ Exception -> 0x0690 }
        r13.setTaskDescription(r0);	 Catch:{ Exception -> 0x0690 }
    L_0x01cf:
        r0 = r13.getWindow();
        r5 = 2130838365; // 0x7f02035d float:1.728171E38 double:1.052774033E-314;
        r0.setBackgroundDrawableResource(r5);
        r0 = org.telegram.messenger.UserConfig.passcodeHash;
        r0 = r0.length();
        if (r0 <= 0) goto L_0x01f0;
    L_0x01e1:
        r0 = org.telegram.messenger.UserConfig.allowScreenCapture;
        if (r0 != 0) goto L_0x01f0;
    L_0x01e5:
        r0 = r13.getWindow();	 Catch:{ Exception -> 0x04ee }
        r5 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r6 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0.setFlags(r5, r6);	 Catch:{ Exception -> 0x04ee }
    L_0x01f0:
        super.onCreate(r14);
        r0 = android.os.Build.VERSION.SDK_INT;
        r5 = 24;
        if (r0 < r5) goto L_0x01ff;
    L_0x01f9:
        r0 = r13.isInMultiWindowMode();
        org.telegram.messenger.AndroidUtilities.isInMultiwindow = r0;
    L_0x01ff:
        org.telegram.ui.ActionBar.Theme.createChatResources(r13, r2);
        r0 = org.telegram.messenger.UserConfig.passcodeHash;
        r0 = r0.length();
        if (r0 == 0) goto L_0x0218;
    L_0x020a:
        r0 = org.telegram.messenger.UserConfig.appLocked;
        if (r0 == 0) goto L_0x0218;
    L_0x020e:
        r0 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r0 = r0.getCurrentTime();
        org.telegram.messenger.UserConfig.lastPauseTime = r0;
    L_0x0218:
        r0 = r13.getResources();
        r5 = "status_bar_height";
        r6 = "dimen";
        r7 = "android";
        r0 = r0.getIdentifier(r5, r6, r7);
        if (r0 <= 0) goto L_0x0235;
    L_0x022b:
        r5 = r13.getResources();
        r0 = r5.getDimensionPixelSize(r0);
        org.telegram.messenger.AndroidUtilities.statusBarHeight = r0;
    L_0x0235:
        r0 = new org.telegram.ui.ActionBar.ActionBarLayout;
        r0.<init>(r13);
        r13.actionBarLayout = r0;
        r0 = new org.telegram.ui.ActionBar.DrawerLayoutContainer;
        r0.<init>(r13);
        r13.drawerLayoutContainer = r0;
        r0 = r13.drawerLayoutContainer;
        r5 = new android.view.ViewGroup$LayoutParams;
        r5.<init>(r4, r4);
        r13.setContentView(r0, r5);
        r0 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r0 == 0) goto L_0x04fa;
    L_0x0253:
        r0 = r13.getWindow();
        r5 = 16;
        r0.setSoftInputMode(r5);
        r5 = new org.telegram.ui.LaunchActivity$15;
        r5.<init>(r13);
        r0 = r13.drawerLayoutContainer;
        r6 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r6 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r6);
        r0.addView(r5, r6);
        r0 = new android.view.View;
        r0.<init>(r13);
        r13.backgroundTablet = r0;
        r0 = r13.getResources();
        r6 = 2130837749; // 0x7f0200f5 float:1.728046E38 double:1.0527737286E-314;
        r0 = r0.getDrawable(r6);
        r0 = (android.graphics.drawable.BitmapDrawable) r0;
        r6 = android.graphics.Shader.TileMode.REPEAT;
        r7 = android.graphics.Shader.TileMode.REPEAT;
        r0.setTileModeXY(r6, r7);
        r6 = r13.backgroundTablet;
        r6.setBackgroundDrawable(r0);
        r0 = r13.backgroundTablet;
        r6 = org.telegram.ui.Components.LayoutHelper.createRelative(r4, r4);
        r5.addView(r0, r6);
        r0 = r13.actionBarLayout;
        r5.addView(r0);
        r0 = new org.telegram.ui.ActionBar.ActionBarLayout;
        r0.<init>(r13);
        r13.rightActionBarLayout = r0;
        r0 = r13.rightActionBarLayout;
        r6 = rightFragmentsStack;
        r0.init(r6);
        r0 = r13.rightActionBarLayout;
        r0.setDelegate(r13);
        r0 = r13.rightActionBarLayout;
        r5.addView(r0);
        r0 = new android.widget.FrameLayout;
        r0.<init>(r13);
        r13.shadowTabletSide = r0;
        r0 = r13.shadowTabletSide;
        r6 = 1076449908; // 0x40295274 float:2.6456575 double:5.31836919E-315;
        r0.setBackgroundColor(r6);
        r0 = r13.shadowTabletSide;
        r5.addView(r0);
        r0 = new android.widget.FrameLayout;
        r0.<init>(r13);
        r13.shadowTablet = r0;
        r6 = r13.shadowTablet;
        r0 = layerFragmentsStack;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x04f4;
    L_0x02d7:
        r0 = r1;
    L_0x02d8:
        r6.setVisibility(r0);
        r0 = r13.shadowTablet;
        r6 = 2130706432; // 0x7f000000 float:1.7014118E38 double:1.0527088494E-314;
        r0.setBackgroundColor(r6);
        r0 = r13.shadowTablet;
        r5.addView(r0);
        r0 = r13.shadowTablet;
        r6 = new org.telegram.ui.LaunchActivity$16;
        r6.<init>();
        r0.setOnTouchListener(r6);
        r0 = r13.shadowTablet;
        r6 = new org.telegram.ui.LaunchActivity$17;
        r6.<init>();
        r0.setOnClickListener(r6);
        r0 = new org.telegram.ui.ActionBar.ActionBarLayout;
        r0.<init>(r13);
        r13.layersActionBarLayout = r0;
        r0 = r13.layersActionBarLayout;
        r0.setRemoveActionBarExtraHeight(r3);
        r0 = r13.layersActionBarLayout;
        r6 = r13.shadowTablet;
        r0.setBackgroundView(r6);
        r0 = r13.layersActionBarLayout;
        r0.setUseAlphaAnimations(r3);
        r0 = r13.layersActionBarLayout;
        r6 = 2130837739; // 0x7f0200eb float:1.728044E38 double:1.0527737237E-314;
        r0.setBackgroundResource(r6);
        r0 = r13.layersActionBarLayout;
        r6 = layerFragmentsStack;
        r0.init(r6);
        r0 = r13.layersActionBarLayout;
        r0.setDelegate(r13);
        r0 = r13.layersActionBarLayout;
        r6 = r13.drawerLayoutContainer;
        r0.setDrawerLayoutContainer(r6);
        r0 = r13.layersActionBarLayout;
        r6 = layerFragmentsStack;
        r6 = r6.isEmpty();
        if (r6 == 0) goto L_0x04f7;
    L_0x0338:
        r0.setVisibility(r1);
        r0 = r13.layersActionBarLayout;
        r5.addView(r0);
    L_0x0340:
        r0 = new org.telegram.ui.Components.RecyclerListView;
        r0.<init>(r13);
        r13.sideMenu = r0;
        r0 = r13.sideMenu;
        r1 = "chats_menuBackground";
        r1 = org.telegram.ui.ActionBar.Theme.getColor(r1);
        r0.setBackgroundColor(r1);
        r0 = r13.sideMenu;
        r1 = new org.telegram.messenger.support.widget.LinearLayoutManager;
        r1.<init>(r13, r3, r2);
        r0.setLayoutManager(r1);
        r0 = r13.sideMenu;
        r1 = new org.telegram.ui.Adapters.DrawerLayoutAdapter;
        r1.<init>(r13);
        r13.drawerLayoutAdapter = r1;
        r0.setAdapter(r1);
        r0 = r13.drawerLayoutContainer;
        r1 = r13.sideMenu;
        r0.setDrawerLayout(r1);
        r0 = r13.sideMenu;
        r0 = r0.getLayoutParams();
        r0 = (android.widget.FrameLayout.LayoutParams) r0;
        r1 = org.telegram.messenger.AndroidUtilities.getRealScreenSize();
        r5 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r5 == 0) goto L_0x0508;
    L_0x0382:
        r1 = 1134559232; // 0x43a00000 float:320.0 double:5.605467397E-315;
        r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
    L_0x0388:
        r0.width = r1;
        r0.height = r4;
        r1 = r13.sideMenu;
        r1.setLayoutParams(r0);
        r0 = r13.sideMenu;
        r1 = new org.telegram.ui.LaunchActivity$18;
        r1.<init>();
        r0.setOnItemClickListener(r1);
        r0 = r13.drawerLayoutContainer;
        r1 = r13.actionBarLayout;
        r0.setParentActionBarLayout(r1);
        r0 = r13.actionBarLayout;
        r1 = r13.drawerLayoutContainer;
        r0.setDrawerLayoutContainer(r1);
        r0 = r13.actionBarLayout;
        r1 = mainFragmentsStack;
        r0.init(r1);
        r0 = r13.actionBarLayout;
        r0.setDelegate(r13);
        org.telegram.ui.ActionBar.Theme.loadWallpaper();
        r0 = new org.telegram.ui.Components.PasscodeView;
        r0.<init>(r13);
        r13.passcodeView = r0;
        r0 = r13.drawerLayoutContainer;
        r1 = r13.passcodeView;
        r5 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r5 = org.telegram.ui.Components.LayoutHelper.createFrame(r4, r5);
        r0.addView(r1, r5);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.closeOtherAppActivities;
        r5 = new java.lang.Object[r3];
        r5[r2] = r13;
        r0.postNotificationName(r1, r5);
        r0 = org.telegram.tgnet.ConnectionsManager.getInstance();
        r0 = r0.getConnectionState();
        r13.currentConnectionState = r0;
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.appDidLogout;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.mainUserInfoChanged;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.closeOtherAppActivities;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.didUpdatedConnectionState;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.needShowAlert;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.wasUnableToFindCurrentLocation;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.didSetNewWallpapper;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.didSetPasscode;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.reloadInterface;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.suggestedLangpack;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.openArticle;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.hasNewContactsToImport;
        r0.addObserver(r13, r1);
        r0 = org.telegram.messenger.NotificationCenter.getInstance();
        r1 = org.telegram.messenger.NotificationCenter.didSetNewTheme;
        r0.addObserver(r13, r1);
        r0 = r13.actionBarLayout;
        r0 = r0.fragmentsStack;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0615;
    L_0x0462:
        r0 = org.telegram.messenger.UserConfig.isClientActivated();
        if (r0 != 0) goto L_0x0523;
    L_0x0468:
        r0 = r13.actionBarLayout;
        r1 = new org.telegram.ui.LoginActivity;
        r1.<init>();
        r0.addFragmentToStack(r1);
        r0 = r13.drawerLayoutContainer;
        r0.setAllowOpenDrawer(r2, r2);
    L_0x0477:
        if (r14 == 0) goto L_0x0494;
    L_0x0479:
        r0 = "fragment";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0494;
    L_0x0482:
        r1 = "args";
        r1 = r14.getBundle(r1);	 Catch:{ Exception -> 0x05a1 }
        r5 = r0.hashCode();	 Catch:{ Exception -> 0x05a1 }
        switch(r5) {
            case -1529105743: goto L_0x0581;
            case -1349522494: goto L_0x0575;
            case 3052376: goto L_0x0539;
            case 3108362: goto L_0x0569;
            case 98629247: goto L_0x0551;
            case 738950403: goto L_0x055d;
            case 1434631203: goto L_0x0545;
            default: goto L_0x0490;
        };
    L_0x0490:
        r0 = r4;
    L_0x0491:
        switch(r0) {
            case 0: goto L_0x058d;
            case 1: goto L_0x05a7;
            case 2: goto L_0x05b6;
            case 3: goto L_0x05ca;
            case 4: goto L_0x05de;
            case 5: goto L_0x05f2;
            case 6: goto L_0x0606;
            default: goto L_0x0494;
        };
    L_0x0494:
        r13.checkLayout();
        r1 = r13.getIntent();
        if (r14 == 0) goto L_0x067c;
    L_0x049d:
        r0 = r3;
    L_0x049e:
        r13.handleIntent(r1, r2, r0, r2);
        r0 = android.os.Build.DISPLAY;	 Catch:{ Exception -> 0x068a }
        r2 = android.os.Build.USER;	 Catch:{ Exception -> 0x068a }
        if (r0 == 0) goto L_0x067f;
    L_0x04a7:
        r0 = r0.toLowerCase();	 Catch:{ Exception -> 0x068a }
        r1 = r0;
    L_0x04ac:
        if (r2 == 0) goto L_0x0685;
    L_0x04ae:
        r0 = r1.toLowerCase();	 Catch:{ Exception -> 0x068a }
    L_0x04b2:
        r2 = "flyme";
        r1 = r1.contains(r2);	 Catch:{ Exception -> 0x068a }
        if (r1 != 0) goto L_0x04c4;
    L_0x04bb:
        r1 = "flyme";
        r0 = r0.contains(r1);	 Catch:{ Exception -> 0x068a }
        if (r0 == 0) goto L_0x04e1;
    L_0x04c4:
        r0 = 1;
        org.telegram.messenger.AndroidUtilities.incorrectDisplaySizeFix = r0;	 Catch:{ Exception -> 0x068a }
        r0 = r13.getWindow();	 Catch:{ Exception -> 0x068a }
        r0 = r0.getDecorView();	 Catch:{ Exception -> 0x068a }
        r0 = r0.getRootView();	 Catch:{ Exception -> 0x068a }
        r1 = r0.getViewTreeObserver();	 Catch:{ Exception -> 0x068a }
        r2 = new org.telegram.ui.LaunchActivity$19;	 Catch:{ Exception -> 0x068a }
        r2.<init>(r0);	 Catch:{ Exception -> 0x068a }
        r13.onGlobalLayoutListener = r2;	 Catch:{ Exception -> 0x068a }
        r1.addOnGlobalLayoutListener(r2);	 Catch:{ Exception -> 0x068a }
    L_0x04e1:
        r0 = org.telegram.messenger.MediaController.getInstance();
        r0.setBaseActivity(r13, r3);
        goto L_0x0071;
    L_0x04ea:
        r0 = r0 + 1;
        goto L_0x0191;
    L_0x04ee:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x01f0;
    L_0x04f4:
        r0 = r2;
        goto L_0x02d8;
    L_0x04f7:
        r1 = r2;
        goto L_0x0338;
    L_0x04fa:
        r0 = r13.drawerLayoutContainer;
        r1 = r13.actionBarLayout;
        r5 = new android.view.ViewGroup$LayoutParams;
        r5.<init>(r4, r4);
        r0.addView(r1, r5);
        goto L_0x0340;
    L_0x0508:
        r5 = 1134559232; // 0x43a00000 float:320.0 double:5.605467397E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r6 = r1.x;
        r1 = r1.y;
        r1 = java.lang.Math.min(r6, r1);
        r6 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r1 = r1 - r6;
        r1 = java.lang.Math.min(r5, r1);
        goto L_0x0388;
    L_0x0523:
        r0 = new org.telegram.ui.DialogsActivity;
        r0.<init>(r12);
        r1 = r13.sideMenu;
        r0.setSideMenu(r1);
        r1 = r13.actionBarLayout;
        r1.addFragmentToStack(r0);
        r0 = r13.drawerLayoutContainer;
        r0.setAllowOpenDrawer(r3, r2);
        goto L_0x0477;
    L_0x0539:
        r5 = "chat";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x0542:
        r0 = r2;
        goto L_0x0491;
    L_0x0545:
        r5 = "settings";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x054e:
        r0 = r3;
        goto L_0x0491;
    L_0x0551:
        r5 = "group";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x055a:
        r0 = 2;
        goto L_0x0491;
    L_0x055d:
        r5 = "channel";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x0566:
        r0 = 3;
        goto L_0x0491;
    L_0x0569:
        r5 = "edit";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x0572:
        r0 = 4;
        goto L_0x0491;
    L_0x0575:
        r5 = "chat_profile";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x057e:
        r0 = 5;
        goto L_0x0491;
    L_0x0581:
        r5 = "wallpapers";
        r0 = r0.equals(r5);	 Catch:{ Exception -> 0x05a1 }
        if (r0 == 0) goto L_0x0490;
    L_0x058a:
        r0 = 6;
        goto L_0x0491;
    L_0x058d:
        if (r1 == 0) goto L_0x0494;
    L_0x058f:
        r0 = new org.telegram.ui.ChatActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1 = r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r1 == 0) goto L_0x0494;
    L_0x059c:
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x05a1:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0494;
    L_0x05a7:
        r0 = new org.telegram.ui.SettingsActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>();	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x05b6:
        if (r1 == 0) goto L_0x0494;
    L_0x05b8:
        r0 = new org.telegram.ui.GroupCreateFinalActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1 = r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r1 == 0) goto L_0x0494;
    L_0x05c5:
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x05ca:
        if (r1 == 0) goto L_0x0494;
    L_0x05cc:
        r0 = new org.telegram.ui.ChannelCreateActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1 = r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r1 == 0) goto L_0x0494;
    L_0x05d9:
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x05de:
        if (r1 == 0) goto L_0x0494;
    L_0x05e0:
        r0 = new org.telegram.ui.ChannelEditActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1 = r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r1 == 0) goto L_0x0494;
    L_0x05ed:
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x05f2:
        if (r1 == 0) goto L_0x0494;
    L_0x05f4:
        r0 = new org.telegram.ui.ProfileActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1 = r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        if (r1 == 0) goto L_0x0494;
    L_0x0601:
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x0606:
        r0 = new org.telegram.ui.WallpapersActivity;	 Catch:{ Exception -> 0x05a1 }
        r0.<init>();	 Catch:{ Exception -> 0x05a1 }
        r1 = r13.actionBarLayout;	 Catch:{ Exception -> 0x05a1 }
        r1.addFragmentToStack(r0);	 Catch:{ Exception -> 0x05a1 }
        r0.restoreSelfArgs(r14);	 Catch:{ Exception -> 0x05a1 }
        goto L_0x0494;
    L_0x0615:
        r0 = r13.actionBarLayout;
        r0 = r0.fragmentsStack;
        r0 = r0.get(r2);
        r0 = (org.telegram.ui.ActionBar.BaseFragment) r0;
        r1 = r0 instanceof org.telegram.ui.DialogsActivity;
        if (r1 == 0) goto L_0x062a;
    L_0x0623:
        r0 = (org.telegram.ui.DialogsActivity) r0;
        r1 = r13.sideMenu;
        r0.setSideMenu(r1);
    L_0x062a:
        r0 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r0 == 0) goto L_0x0693;
    L_0x0630:
        r0 = r13.actionBarLayout;
        r0 = r0.fragmentsStack;
        r0 = r0.size();
        if (r0 > r3) goto L_0x067a;
    L_0x063a:
        r0 = r13.layersActionBarLayout;
        r0 = r0.fragmentsStack;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x067a;
    L_0x0644:
        r0 = r3;
    L_0x0645:
        r1 = r13.layersActionBarLayout;
        r1 = r1.fragmentsStack;
        r1 = r1.size();
        if (r1 != r3) goto L_0x065c;
    L_0x064f:
        r1 = r13.layersActionBarLayout;
        r1 = r1.fragmentsStack;
        r1 = r1.get(r2);
        r1 = r1 instanceof org.telegram.ui.LoginActivity;
        if (r1 == 0) goto L_0x065c;
    L_0x065b:
        r0 = r2;
    L_0x065c:
        r1 = r13.actionBarLayout;
        r1 = r1.fragmentsStack;
        r1 = r1.size();
        if (r1 != r3) goto L_0x0673;
    L_0x0666:
        r1 = r13.actionBarLayout;
        r1 = r1.fragmentsStack;
        r1 = r1.get(r2);
        r1 = r1 instanceof org.telegram.ui.LoginActivity;
        if (r1 == 0) goto L_0x0673;
    L_0x0672:
        r0 = r2;
    L_0x0673:
        r1 = r13.drawerLayoutContainer;
        r1.setAllowOpenDrawer(r0, r2);
        goto L_0x0494;
    L_0x067a:
        r0 = r2;
        goto L_0x0645;
    L_0x067c:
        r0 = r2;
        goto L_0x049e;
    L_0x067f:
        r0 = "";
        r1 = r0;
        goto L_0x04ac;
    L_0x0685:
        r0 = "";
        goto L_0x04b2;
    L_0x068a:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x04e1;
    L_0x0690:
        r0 = move-exception;
        goto L_0x01cf;
    L_0x0693:
        r0 = r3;
        goto L_0x065c;
    L_0x0695:
        r0 = r3;
        goto L_0x019f;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.onCreate(android.os.Bundle):void");
    }

    protected void onDestroy() {
        PhotoViewer.getInstance().destroyPhotoViewer();
        SecretMediaViewer.getInstance().destroyPhotoViewer();
        ArticleViewer.getInstance().destroyArticleViewer();
        StickerPreviewViewer.getInstance().destroy();
        PipRoundVideoView instance = PipRoundVideoView.getInstance();
        MediaController.getInstance().setBaseActivity(this, false);
        if (instance != null) {
            instance.close(false);
        }
        Theme.destroyResources();
        EmbedBottomSheet instance2 = EmbedBottomSheet.getInstance();
        if (instance2 != null) {
            instance2.destroy();
        }
        ThemeEditorView instance3 = ThemeEditorView.getInstance();
        if (instance3 != null) {
            instance3.destroy();
        }
        try {
            if (this.visibleDialog != null) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        try {
            if (this.onGlobalLayoutListener != null) {
                getWindow().getDecorView().getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
            }
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        super.onDestroy();
        onFinish();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 82 && !UserConfig.isWaitingForPasscodeEnter) {
            if (PhotoViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (ArticleViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (AndroidUtilities.isTablet()) {
                if (this.layersActionBarLayout.getVisibility() == 0 && !this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                    this.layersActionBarLayout.onKeyUp(i, keyEvent);
                } else if (this.rightActionBarLayout.getVisibility() != 0 || this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                    this.actionBarLayout.onKeyUp(i, keyEvent);
                } else {
                    this.rightActionBarLayout.onKeyUp(i, keyEvent);
                }
            } else if (this.actionBarLayout.fragmentsStack.size() != 1) {
                this.actionBarLayout.onKeyUp(i, keyEvent);
            } else if (this.drawerLayoutContainer.isDrawerOpened()) {
                this.drawerLayoutContainer.closeDrawer(false);
            } else {
                if (getCurrentFocus() != null) {
                    AndroidUtilities.hideKeyboard(getCurrentFocus());
                }
                this.drawerLayoutContainer.openDrawer(false);
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.actionBarLayout.onLowMemory();
        if (AndroidUtilities.isTablet()) {
            this.rightActionBarLayout.onLowMemory();
            this.layersActionBarLayout.onLowMemory();
        }
    }

    public void onMultiWindowModeChanged(boolean z) {
        AndroidUtilities.isInMultiwindow = z;
        checkLayout();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, true, false, false);
    }

    protected void onPause() {
        super.onPause();
        UserConfig.lastAppPauseTime = System.currentTimeMillis();
        ApplicationLoader.mainInterfacePaused = true;
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                ApplicationLoader.mainInterfacePausedStageQueue = true;
                ApplicationLoader.mainInterfacePausedStageQueueTime = 0;
            }
        });
        onPasscodePause();
        this.actionBarLayout.onPause();
        if (AndroidUtilities.isTablet()) {
            this.rightActionBarLayout.onPause();
            this.layersActionBarLayout.onPause();
        }
        if (this.passcodeView != null) {
            this.passcodeView.onPause();
        }
        ConnectionsManager.getInstance().setAppPaused(true, false);
        AndroidUtilities.unregisterUpdates();
        if (PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onPause();
        }
    }

    public boolean onPreIme() {
        if (SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(true, false);
            return true;
        } else if (PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(true, false);
            return true;
        } else if (!ArticleViewer.getInstance().isVisible()) {
            return false;
        } else {
            ArticleViewer.getInstance().close(true, false);
            return true;
        }
    }

    public void onRebuildAllFragments(ActionBarLayout actionBarLayout) {
        if (AndroidUtilities.isTablet() && actionBarLayout == this.layersActionBarLayout) {
            this.rightActionBarLayout.rebuildAllFragmentViews(true, true);
            this.actionBarLayout.rebuildAllFragmentViews(true, true);
        }
        this.drawerLayoutAdapter.notifyDataSetChanged();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 3 || i == 4 || i == 5 || i == 19 || i == 20) {
            int i2 = 1;
            if (iArr.length > 0 && iArr[0] == 0) {
                if (i == 4) {
                    ImageLoader.getInstance().checkMediaPaths();
                    return;
                } else if (i == 5) {
                    ContactsController.getInstance().forceImportContacts();
                    return;
                } else if (i == 3) {
                    if (MediaController.getInstance().canInAppCamera()) {
                        CameraController.getInstance().initCamera();
                        return;
                    }
                    return;
                } else if (i == 19 || i == 20) {
                    i2 = 0;
                }
            }
            if (i2 != 0) {
                Builder builder = new Builder(this);
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                if (i == 3) {
                    builder.setMessage(LocaleController.getString("PermissionNoAudio", R.string.PermissionNoAudio));
                } else if (i == 4) {
                    builder.setMessage(LocaleController.getString("PermissionStorage", R.string.PermissionStorage));
                } else if (i == 5) {
                    builder.setMessage(LocaleController.getString("PermissionContacts", R.string.PermissionContacts));
                } else if (i == 19 || i == 20) {
                    builder.setMessage(LocaleController.getString("PermissionNoCamera", R.string.PermissionNoCamera));
                }
                builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new OnClickListener() {
                    @TargetApi(9)
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                            LaunchActivity.this.startActivity(intent);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                });
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                builder.show();
                return;
            }
        } else if (i == 2 && iArr.length > 0 && iArr[0] == 0) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.locationPermissionGranted, new Object[0]);
        }
        if (this.actionBarLayout.fragmentsStack.size() != 0) {
            ((BaseFragment) this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1)).onRequestPermissionsResultFragment(i, strArr, iArr);
        }
        if (AndroidUtilities.isTablet()) {
            if (this.rightActionBarLayout.fragmentsStack.size() != 0) {
                ((BaseFragment) this.rightActionBarLayout.fragmentsStack.get(this.rightActionBarLayout.fragmentsStack.size() - 1)).onRequestPermissionsResultFragment(i, strArr, iArr);
            }
            if (this.layersActionBarLayout.fragmentsStack.size() != 0) {
                ((BaseFragment) this.layersActionBarLayout.fragmentsStack.get(this.layersActionBarLayout.fragmentsStack.size() - 1)).onRequestPermissionsResultFragment(i, strArr, iArr);
            }
        }
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 10:
                Log.d("alireza", "alireza setting recieved");
                SettingAndUpdate settingAndUpdate = (SettingAndUpdate) obj;
                if (settingAndUpdate != null && settingAndUpdate.getUpdate() != null) {
                    Log.d("alireza", "alireza  response " + settingAndUpdate.getUpdate().getDownloadLink());
                    if (settingAndUpdate.getUpdate().getLastVersion() > BuildConfig.VERSION_CODE && !this.selfUpdateCalled) {
                        this.selfUpdateCalled = true;
                        Intent intent = new Intent(getApplicationContext(), SelfUpdateActivity.class);
                        intent.putExtra("EXTRA_UPDATE_MODEL", new C1768f().a(settingAndUpdate, SettingAndUpdate.class));
                        startActivityForResult(intent, 8888);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        showLanguageAlert(false);
        ApplicationLoader.mainInterfacePaused = false;
        Utilities.stageQueue.postRunnable(new Runnable() {
            public void run() {
                ApplicationLoader.mainInterfacePausedStageQueue = false;
                ApplicationLoader.mainInterfacePausedStageQueueTime = System.currentTimeMillis();
            }
        });
        checkFreeDiscSpace();
        MediaController.checkGallery();
        onPasscodeResume();
        if (this.passcodeView.getVisibility() != 0) {
            this.actionBarLayout.onResume();
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.onResume();
                this.layersActionBarLayout.onResume();
            }
        } else {
            this.actionBarLayout.dismissDialogs();
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.dismissDialogs();
                this.layersActionBarLayout.dismissDialogs();
            }
            this.passcodeView.onResume();
        }
        ConnectionsManager.getInstance().setAppPaused(false, false);
        updateCurrentConnectionState();
        if (PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onResume();
        }
        if (PipRoundVideoView.getInstance() != null && MediaController.getInstance().isMessagePaused()) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject != null) {
                MediaController.getInstance().seekToProgress(playingMessageObject, playingMessageObject.audioProgress);
            }
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        try {
            BaseFragment baseFragment;
            super.onSaveInstanceState(bundle);
            if (!AndroidUtilities.isTablet()) {
                if (!this.actionBarLayout.fragmentsStack.isEmpty()) {
                    baseFragment = (BaseFragment) this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1);
                }
                baseFragment = null;
            } else if (!this.layersActionBarLayout.fragmentsStack.isEmpty()) {
                baseFragment = (BaseFragment) this.layersActionBarLayout.fragmentsStack.get(this.layersActionBarLayout.fragmentsStack.size() - 1);
            } else if (this.rightActionBarLayout.fragmentsStack.isEmpty()) {
                if (!this.actionBarLayout.fragmentsStack.isEmpty()) {
                    baseFragment = (BaseFragment) this.actionBarLayout.fragmentsStack.get(this.actionBarLayout.fragmentsStack.size() - 1);
                }
                baseFragment = null;
            } else {
                baseFragment = (BaseFragment) this.rightActionBarLayout.fragmentsStack.get(this.rightActionBarLayout.fragmentsStack.size() - 1);
            }
            if (baseFragment != null) {
                Bundle arguments = baseFragment.getArguments();
                if ((baseFragment instanceof ChatActivity) && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "chat");
                } else if (baseFragment instanceof SettingsActivity) {
                    bundle.putString("fragment", "settings");
                } else if ((baseFragment instanceof GroupCreateFinalActivity) && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "group");
                } else if (baseFragment instanceof WallpapersActivity) {
                    bundle.putString("fragment", "wallpapers");
                } else if ((baseFragment instanceof ProfileActivity) && ((ProfileActivity) baseFragment).isChat() && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "chat_profile");
                } else if ((baseFragment instanceof ChannelCreateActivity) && arguments != null && arguments.getInt("step") == 0) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "channel");
                } else if ((baseFragment instanceof ChannelEditActivity) && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "edit");
                }
                baseFragment.saveSelfArgs(bundle);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    protected void onStart() {
        super.onStart();
        Browser.bindCustomTabsService(this);
    }

    protected void onStop() {
        super.onStop();
        Browser.unbindCustomTabsService(this);
    }

    public void presentFragment(BaseFragment baseFragment) {
        this.actionBarLayout.presentFragment(baseFragment);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z, boolean z2) {
        return this.actionBarLayout.presentFragment(baseFragment, z, z2, true);
    }

    public void rebuildAllFragments(boolean z) {
        if (this.layersActionBarLayout != null) {
            this.layersActionBarLayout.rebuildAllFragmentViews(z, true);
        } else {
            this.actionBarLayout.rebuildAllFragmentViews(z, true);
        }
    }

    public AlertDialog showAlertDialog(Builder builder) {
        AlertDialog alertDialog = null;
        try {
            if (this.visibleDialog != null) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        try {
            this.visibleDialog = builder.show();
            this.visibleDialog.setCanceledOnTouchOutside(true);
            this.visibleDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    if (LaunchActivity.this.visibleDialog != null && LaunchActivity.this.visibleDialog == LaunchActivity.this.localeDialog) {
                        try {
                            Toast.makeText(LaunchActivity.this, LaunchActivity.this.getStringForLanguageAlert(LocaleController.getInstance().getCurrentLocaleInfo().shortName.equals("en") ? LaunchActivity.this.englishLocaleStrings : LaunchActivity.this.systemLocaleStrings, "ChangeLanguageLater", R.string.ChangeLanguageLater), 1).show();
                        } catch (Throwable e) {
                            FileLog.e("tmessages", e);
                        }
                        LaunchActivity.this.localeDialog = null;
                    }
                    LaunchActivity.this.visibleDialog = null;
                }
            });
            return this.visibleDialog;
        } catch (Throwable e2) {
            FileLog.e(e2);
            return alertDialog;
        }
    }
}
