package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contacts_importContacts;
import org.telegram.tgnet.TLRPC$TL_contacts_importedContacts;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPhoneContact;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.HintEditText;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.CountrySelectActivity.CountrySelectActivityDelegate;

public class NewContactActivity extends BaseFragment implements OnItemSelectedListener {
    private static final int done_button = 1;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private EditTextBoldCursor codeField;
    private HashMap<String, String> codesMap = new HashMap();
    private ArrayList<String> countriesArray = new ArrayList();
    private HashMap<String, String> countriesMap = new HashMap();
    private TextView countryButton;
    private int countryState;
    private boolean donePressed;
    private ActionBarMenuItem editDoneItem;
    private AnimatorSet editDoneItemAnimation;
    private ContextProgressView editDoneItemProgress;
    private EditTextBoldCursor firstNameField;
    private boolean ignoreOnPhoneChange;
    private boolean ignoreOnTextChange;
    private boolean ignoreSelection;
    private EditTextBoldCursor lastNameField;
    private View lineView;
    private HintEditText phoneField;
    private HashMap<String, String> phoneFormatMap = new HashMap();
    private TextView textView;

    /* renamed from: org.telegram.ui.NewContactActivity$1 */
    class C49611 extends ActionBarMenuOnItemClick {
        C49611() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                NewContactActivity.this.finishFragment();
            } else if (i == 1 && !NewContactActivity.this.donePressed) {
                Vibrator vibrator;
                if (NewContactActivity.this.firstNameField.length() == 0) {
                    vibrator = (Vibrator) NewContactActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(NewContactActivity.this.firstNameField, 2.0f, 0);
                } else if (NewContactActivity.this.codeField.length() == 0) {
                    vibrator = (Vibrator) NewContactActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(NewContactActivity.this.codeField, 2.0f, 0);
                } else if (NewContactActivity.this.phoneField.length() == 0) {
                    vibrator = (Vibrator) NewContactActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(NewContactActivity.this.phoneField, 2.0f, 0);
                } else {
                    NewContactActivity.this.donePressed = true;
                    NewContactActivity.this.showEditDoneProgress(true, true);
                    final TLObject tLRPC$TL_contacts_importContacts = new TLRPC$TL_contacts_importContacts();
                    final TLRPC$TL_inputPhoneContact tLRPC$TL_inputPhoneContact = new TLRPC$TL_inputPhoneContact();
                    tLRPC$TL_inputPhoneContact.first_name = NewContactActivity.this.firstNameField.getText().toString();
                    tLRPC$TL_inputPhoneContact.last_name = NewContactActivity.this.lastNameField.getText().toString();
                    tLRPC$TL_inputPhoneContact.phone = "+" + NewContactActivity.this.codeField.getText().toString() + NewContactActivity.this.phoneField.getText().toString();
                    tLRPC$TL_contacts_importContacts.contacts.add(tLRPC$TL_inputPhoneContact);
                    ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_importContacts, new RequestDelegate() {
                        public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            final TLRPC$TL_contacts_importedContacts tLRPC$TL_contacts_importedContacts = (TLRPC$TL_contacts_importedContacts) tLObject;
                            AndroidUtilities.runOnUIThread(new Runnable() {

                                /* renamed from: org.telegram.ui.NewContactActivity$1$1$1$1 */
                                class C49581 implements OnClickListener {
                                    C49581() {
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", tLRPC$TL_inputPhoneContact.phone, null));
                                            intent.putExtra("sms_body", ContactsController.getInstance().getInviteText(1));
                                            NewContactActivity.this.getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                                        } catch (Throwable e) {
                                            FileLog.e(e);
                                        }
                                    }
                                }

                                public void run() {
                                    NewContactActivity.this.donePressed = false;
                                    if (tLRPC$TL_contacts_importedContacts == null) {
                                        NewContactActivity.this.showEditDoneProgress(false, true);
                                        AlertsCreator.processError(tLRPC$TL_error, NewContactActivity.this, tLRPC$TL_contacts_importContacts, new Object[0]);
                                    } else if (!tLRPC$TL_contacts_importedContacts.users.isEmpty()) {
                                        MessagesController.getInstance().putUsers(tLRPC$TL_contacts_importedContacts.users, false);
                                        MessagesController.openChatOrProfileWith((User) tLRPC$TL_contacts_importedContacts.users.get(0), null, NewContactActivity.this, 1, true);
                                    } else if (NewContactActivity.this.getParentActivity() != null) {
                                        NewContactActivity.this.showEditDoneProgress(false, true);
                                        Builder builder = new Builder(NewContactActivity.this.getParentActivity());
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        builder.setMessage(LocaleController.formatString("ContactNotRegistered", R.string.ContactNotRegistered, new Object[]{ContactsController.formatName(tLRPC$TL_inputPhoneContact.first_name, tLRPC$TL_inputPhoneContact.last_name)}));
                                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                        builder.setPositiveButton(LocaleController.getString("Invite", R.string.Invite), new C49581());
                                        NewContactActivity.this.showDialog(builder.create());
                                    }
                                }
                            });
                        }
                    }, 2), NewContactActivity.this.classGuid);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$2 */
    class C49622 implements OnTouchListener {
        C49622() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$3 */
    class C49633 implements OnEditorActionListener {
        C49633() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            NewContactActivity.this.lastNameField.requestFocus();
            NewContactActivity.this.lastNameField.setSelection(NewContactActivity.this.lastNameField.length());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$4 */
    class C49644 implements TextWatcher {
        C49644() {
        }

        public void afterTextChanged(Editable editable) {
            NewContactActivity.this.avatarDrawable.setInfo(5, NewContactActivity.this.firstNameField.getText().toString(), NewContactActivity.this.lastNameField.getText().toString(), false);
            NewContactActivity.this.avatarImage.invalidate();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$5 */
    class C49655 implements OnEditorActionListener {
        C49655() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            NewContactActivity.this.phoneField.requestFocus();
            NewContactActivity.this.phoneField.setSelection(NewContactActivity.this.phoneField.length());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$6 */
    class C49666 implements TextWatcher {
        C49666() {
        }

        public void afterTextChanged(Editable editable) {
            NewContactActivity.this.avatarDrawable.setInfo(5, NewContactActivity.this.firstNameField.getText().toString(), NewContactActivity.this.lastNameField.getText().toString(), false);
            NewContactActivity.this.avatarImage.invalidate();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$7 */
    class C49697 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.NewContactActivity$7$1 */
        class C49681 implements CountrySelectActivityDelegate {

            /* renamed from: org.telegram.ui.NewContactActivity$7$1$1 */
            class C49671 implements Runnable {
                C49671() {
                }

                public void run() {
                    AndroidUtilities.showKeyboard(NewContactActivity.this.phoneField);
                }
            }

            C49681() {
            }

            public void didSelectCountry(String str, String str2) {
                NewContactActivity.this.selectCountry(str);
                AndroidUtilities.runOnUIThread(new C49671(), 300);
                NewContactActivity.this.phoneField.requestFocus();
                NewContactActivity.this.phoneField.setSelection(NewContactActivity.this.phoneField.length());
            }
        }

        C49697() {
        }

        public void onClick(View view) {
            BaseFragment countrySelectActivity = new CountrySelectActivity(true);
            countrySelectActivity.setCountrySelectActivityDelegate(new C49681());
            NewContactActivity.this.presentFragment(countrySelectActivity);
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$8 */
    class C49708 implements TextWatcher {
        C49708() {
        }

        public void afterTextChanged(Editable editable) {
            String str = null;
            if (!NewContactActivity.this.ignoreOnTextChange) {
                NewContactActivity.this.ignoreOnTextChange = true;
                String b = C2488b.b(NewContactActivity.this.codeField.getText().toString());
                NewContactActivity.this.codeField.setText(b);
                if (b.length() == 0) {
                    NewContactActivity.this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                    NewContactActivity.this.phoneField.setHintText(null);
                    NewContactActivity.this.countryState = 1;
                } else {
                    String str2;
                    Object obj;
                    boolean z;
                    CharSequence charSequence;
                    if (b.length() > 4) {
                        String substring;
                        boolean z2;
                        NewContactActivity.this.ignoreOnTextChange = true;
                        for (int i = 4; i >= 1; i--) {
                            substring = b.substring(0, i);
                            if (((String) NewContactActivity.this.codesMap.get(substring)) != null) {
                                str2 = b.substring(i, b.length()) + NewContactActivity.this.phoneField.getText().toString();
                                NewContactActivity.this.codeField.setText(substring);
                                z2 = true;
                                break;
                            }
                        }
                        str2 = null;
                        substring = b;
                        z2 = false;
                        if (!z2) {
                            NewContactActivity.this.ignoreOnTextChange = true;
                            str2 = substring.substring(1, substring.length()) + NewContactActivity.this.phoneField.getText().toString();
                            EditTextBoldCursor access$200 = NewContactActivity.this.codeField;
                            substring = substring.substring(0, 1);
                            access$200.setText(substring);
                        }
                        obj = substring;
                        z = z2;
                        charSequence = str2;
                    } else {
                        z = false;
                        String str3 = b;
                        charSequence = null;
                    }
                    str2 = (String) NewContactActivity.this.codesMap.get(obj);
                    if (str2 != null) {
                        int indexOf = NewContactActivity.this.countriesArray.indexOf(str2);
                        if (indexOf != -1) {
                            NewContactActivity.this.ignoreSelection = true;
                            NewContactActivity.this.countryButton.setText((CharSequence) NewContactActivity.this.countriesArray.get(indexOf));
                            str2 = (String) NewContactActivity.this.phoneFormatMap.get(obj);
                            HintEditText access$300 = NewContactActivity.this.phoneField;
                            if (str2 != null) {
                                str = str2.replace('X', '–');
                            }
                            access$300.setHintText(str);
                            NewContactActivity.this.countryState = 0;
                        } else {
                            NewContactActivity.this.countryButton.setText(LocaleController.getString("WrongCountry", R.string.WrongCountry));
                            NewContactActivity.this.phoneField.setHintText(null);
                            NewContactActivity.this.countryState = 2;
                        }
                    } else {
                        NewContactActivity.this.countryButton.setText(LocaleController.getString("WrongCountry", R.string.WrongCountry));
                        NewContactActivity.this.phoneField.setHintText(null);
                        NewContactActivity.this.countryState = 2;
                    }
                    if (!z) {
                        NewContactActivity.this.codeField.setSelection(NewContactActivity.this.codeField.getText().length());
                    }
                    if (charSequence != null) {
                        NewContactActivity.this.phoneField.requestFocus();
                        NewContactActivity.this.phoneField.setText(charSequence);
                        NewContactActivity.this.phoneField.setSelection(NewContactActivity.this.phoneField.length());
                    }
                }
                NewContactActivity.this.ignoreOnTextChange = false;
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.NewContactActivity$9 */
    class C49719 implements OnEditorActionListener {
        C49719() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            NewContactActivity.this.phoneField.requestFocus();
            NewContactActivity.this.phoneField.setSelection(NewContactActivity.this.phoneField.length());
            return true;
        }
    }

    private void showEditDoneProgress(final boolean z, boolean z2) {
        if (this.editDoneItemAnimation != null) {
            this.editDoneItemAnimation.cancel();
        }
        if (z2) {
            this.editDoneItemAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (z) {
                this.editDoneItemProgress.setVisibility(0);
                this.editDoneItem.setEnabled(false);
                animatorSet = this.editDoneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else {
                this.editDoneItem.getImageView().setVisibility(0);
                this.editDoneItem.setEnabled(true);
                animatorSet = this.editDoneItemAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.editDoneItemProgress, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.editDoneItem.getImageView(), "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.editDoneItemAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (NewContactActivity.this.editDoneItemAnimation != null && NewContactActivity.this.editDoneItemAnimation.equals(animator)) {
                        NewContactActivity.this.editDoneItemAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (NewContactActivity.this.editDoneItemAnimation != null && NewContactActivity.this.editDoneItemAnimation.equals(animator)) {
                        if (z) {
                            NewContactActivity.this.editDoneItem.getImageView().setVisibility(4);
                        } else {
                            NewContactActivity.this.editDoneItemProgress.setVisibility(4);
                        }
                    }
                }
            });
            this.editDoneItemAnimation.setDuration(150);
            this.editDoneItemAnimation.start();
        } else if (z) {
            this.editDoneItem.getImageView().setScaleX(0.1f);
            this.editDoneItem.getImageView().setScaleY(0.1f);
            this.editDoneItem.getImageView().setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.editDoneItemProgress.setScaleX(1.0f);
            this.editDoneItemProgress.setScaleY(1.0f);
            this.editDoneItemProgress.setAlpha(1.0f);
            this.editDoneItem.getImageView().setVisibility(4);
            this.editDoneItemProgress.setVisibility(0);
            this.editDoneItem.setEnabled(false);
        } else {
            this.editDoneItemProgress.setScaleX(0.1f);
            this.editDoneItemProgress.setScaleY(0.1f);
            this.editDoneItemProgress.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.editDoneItem.getImageView().setScaleX(1.0f);
            this.editDoneItem.getImageView().setScaleY(1.0f);
            this.editDoneItem.getImageView().setAlpha(1.0f);
            this.editDoneItem.getImageView().setVisibility(0);
            this.editDoneItemProgress.setVisibility(4);
            this.editDoneItem.setEnabled(true);
        }
    }

    public View createView(Context context) {
        Object toUpperCase;
        String str;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("AddContactTitle", R.string.AddContactTitle));
        this.actionBar.setActionBarMenuOnItemClick(new C49611());
        this.avatarDrawable = new AvatarDrawable();
        this.avatarDrawable.setInfo(5, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, false);
        this.editDoneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.editDoneItemProgress = new ContextProgressView(context, 1);
        this.editDoneItem.addView(this.editDoneItemProgress, LayoutHelper.createFrame(-1, -1.0f));
        this.editDoneItemProgress.setVisibility(4);
        this.fragmentView = new ScrollView(context);
        View linearLayout = new LinearLayout(context);
        linearLayout.setPadding(AndroidUtilities.dp(24.0f), 0, AndroidUtilities.dp(24.0f), 0);
        linearLayout.setOrientation(1);
        ((ScrollView) this.fragmentView).addView(linearLayout, LayoutHelper.createScroll(-1, -2, 51));
        linearLayout.setOnTouchListener(new C49622());
        View frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, BitmapDescriptorFactory.HUE_RED, 24.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setImageDrawable(this.avatarDrawable);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(60, 60.0f, 51, BitmapDescriptorFactory.HUE_RED, 9.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.firstNameField = new EditTextBoldCursor(context);
        this.firstNameField.setTextSize(1, 18.0f);
        this.firstNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.firstNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setMaxLines(1);
        this.firstNameField.setLines(1);
        this.firstNameField.setSingleLine(true);
        this.firstNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.firstNameField.setGravity(3);
        this.firstNameField.setInputType(49152);
        this.firstNameField.setImeOptions(5);
        this.firstNameField.setHint(LocaleController.getString("FirstName", R.string.FirstName));
        this.firstNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.firstNameField.setCursorWidth(1.5f);
        frameLayout.addView(this.firstNameField, LayoutHelper.createFrame(-1, 34.0f, 51, 84.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.firstNameField.setOnEditorActionListener(new C49633());
        this.firstNameField.addTextChangedListener(new C49644());
        this.lastNameField = new EditTextBoldCursor(context);
        this.lastNameField.setTextSize(1, 18.0f);
        this.lastNameField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.lastNameField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.lastNameField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.lastNameField.setMaxLines(1);
        this.lastNameField.setLines(1);
        this.lastNameField.setSingleLine(true);
        this.lastNameField.setGravity(3);
        this.lastNameField.setInputType(49152);
        this.lastNameField.setImeOptions(5);
        this.lastNameField.setHint(LocaleController.getString("LastName", R.string.LastName));
        this.lastNameField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.lastNameField.setCursorWidth(1.5f);
        frameLayout.addView(this.lastNameField, LayoutHelper.createFrame(-1, 34.0f, 51, 84.0f, 44.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.lastNameField.setOnEditorActionListener(new C49655());
        this.lastNameField.addTextChangedListener(new C49666());
        this.countryButton = new TextView(context);
        this.countryButton.setTextSize(1, 18.0f);
        this.countryButton.setPadding(AndroidUtilities.dp(6.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(6.0f), 0);
        this.countryButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.countryButton.setMaxLines(1);
        this.countryButton.setSingleLine(true);
        this.countryButton.setEllipsize(TruncateAt.END);
        this.countryButton.setGravity(3);
        this.countryButton.setBackgroundResource(R.drawable.spinner_states);
        linearLayout.addView(this.countryButton, LayoutHelper.createLinear(-1, 36, BitmapDescriptorFactory.HUE_RED, 24.0f, BitmapDescriptorFactory.HUE_RED, 14.0f));
        this.countryButton.setOnClickListener(new C49697());
        this.lineView = new View(context);
        this.lineView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), 0);
        this.lineView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayLine));
        linearLayout.addView(this.lineView, LayoutHelper.createLinear(-1, 1, BitmapDescriptorFactory.HUE_RED, -17.5f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, BitmapDescriptorFactory.HUE_RED, 20.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.textView = new TextView(context);
        this.textView.setText("+");
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 18.0f);
        linearLayout2.addView(this.textView, LayoutHelper.createLinear(-2, -2));
        this.codeField = new EditTextBoldCursor(context);
        this.codeField.setInputType(3);
        this.codeField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.codeField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.codeField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.codeField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.codeField.setCursorWidth(1.5f);
        this.codeField.setPadding(AndroidUtilities.dp(10.0f), 0, 0, 0);
        this.codeField.setTextSize(1, 18.0f);
        this.codeField.setMaxLines(1);
        this.codeField.setGravity(19);
        this.codeField.setImeOptions(268435461);
        this.codeField.setFilters(new InputFilter[]{new LengthFilter(5)});
        linearLayout2.addView(this.codeField, LayoutHelper.createLinear(55, 36, -9.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.codeField.addTextChangedListener(new C49708());
        this.codeField.setOnEditorActionListener(new C49719());
        this.phoneField = new HintEditText(context);
        this.phoneField.setInputType(3);
        this.phoneField.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.phoneField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.phoneField.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.phoneField.setPadding(0, 0, 0, 0);
        this.phoneField.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.phoneField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.phoneField.setCursorWidth(1.5f);
        this.phoneField.setTextSize(1, 18.0f);
        this.phoneField.setMaxLines(1);
        this.phoneField.setGravity(19);
        this.phoneField.setImeOptions(268435462);
        linearLayout2.addView(this.phoneField, LayoutHelper.createFrame(-1, 36.0f));
        this.phoneField.addTextChangedListener(new TextWatcher() {
            private int actionPosition;
            private int characterAction = -1;

            public void afterTextChanged(Editable editable) {
                if (!NewContactActivity.this.ignoreOnPhoneChange) {
                    int selectionStart = NewContactActivity.this.phoneField.getSelectionStart();
                    String str = "0123456789";
                    String obj = NewContactActivity.this.phoneField.getText().toString();
                    if (this.characterAction == 3) {
                        obj = obj.substring(0, this.actionPosition) + obj.substring(this.actionPosition + 1, obj.length());
                        selectionStart--;
                    }
                    CharSequence stringBuilder = new StringBuilder(obj.length());
                    for (int i = 0; i < obj.length(); i++) {
                        Object substring = obj.substring(i, i + 1);
                        if (str.contains(substring)) {
                            stringBuilder.append(substring);
                        }
                    }
                    NewContactActivity.this.ignoreOnPhoneChange = true;
                    String hintText = NewContactActivity.this.phoneField.getHintText();
                    if (hintText != null) {
                        int i2 = 0;
                        while (i2 < stringBuilder.length()) {
                            if (i2 < hintText.length()) {
                                if (hintText.charAt(i2) == ' ') {
                                    stringBuilder.insert(i2, ' ');
                                    i2++;
                                    if (!(selectionStart != i2 || this.characterAction == 2 || this.characterAction == 3)) {
                                        selectionStart++;
                                    }
                                }
                                i2++;
                            } else {
                                stringBuilder.insert(i2, ' ');
                                if (!(selectionStart != i2 + 1 || this.characterAction == 2 || this.characterAction == 3)) {
                                    selectionStart++;
                                }
                            }
                        }
                    }
                    NewContactActivity.this.phoneField.setText(stringBuilder);
                    if (selectionStart >= 0) {
                        HintEditText access$300 = NewContactActivity.this.phoneField;
                        if (selectionStart > NewContactActivity.this.phoneField.length()) {
                            selectionStart = NewContactActivity.this.phoneField.length();
                        }
                        access$300.setSelection(selectionStart);
                    }
                    NewContactActivity.this.phoneField.onTextChange();
                    NewContactActivity.this.ignoreOnPhoneChange = false;
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (i2 == 0 && i3 == 1) {
                    this.characterAction = 1;
                } else if (i2 != 1 || i3 != 0) {
                    this.characterAction = -1;
                } else if (charSequence.charAt(i) != ' ' || i <= 0) {
                    this.characterAction = 2;
                } else {
                    this.characterAction = 3;
                    this.actionPosition = i - 1;
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.phoneField.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                NewContactActivity.this.editDoneItem.performClick();
                return true;
            }
        });
        HashMap hashMap = new HashMap();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("countries.txt")));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(";");
                this.countriesArray.add(0, split[2]);
                this.countriesMap.put(split[2], split[0]);
                this.codesMap.put(split[0], split[2]);
                if (split.length > 3) {
                    this.phoneFormatMap.put(split[0], split[3]);
                }
                hashMap.put(split[1], split[2]);
            }
            bufferedReader.close();
        } catch (Throwable e) {
            FileLog.e(e);
        }
        Collections.sort(this.countriesArray, new Comparator<String>() {
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            if (telephonyManager != null) {
                toUpperCase = telephonyManager.getSimCountryIso().toUpperCase();
                if (toUpperCase != null) {
                    str = (String) hashMap.get(toUpperCase);
                    if (!(str == null || this.countriesArray.indexOf(str) == -1)) {
                        this.codeField.setText((CharSequence) this.countriesMap.get(str));
                        this.countryState = 0;
                    }
                }
                if (this.codeField.length() == 0) {
                    this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
                    this.phoneField.setHintText(null);
                    this.countryState = 1;
                }
                return this.fragmentView;
            }
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        toUpperCase = null;
        if (toUpperCase != null) {
            str = (String) hashMap.get(toUpperCase);
            this.codeField.setText((CharSequence) this.countriesMap.get(str));
            this.countryState = 0;
        }
        if (this.codeField.length() == 0) {
            this.countryButton.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
            this.phoneField.setHintText(null);
            this.countryState = 1;
        }
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate anonymousClass14 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                if (NewContactActivity.this.avatarImage != null) {
                    NewContactActivity.this.avatarDrawable.setInfo(5, NewContactActivity.this.firstNameField.getText().toString(), NewContactActivity.this.lastNameField.getText().toString(), false);
                    NewContactActivity.this.avatarImage.invalidate();
                }
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[34];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[7] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[8] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[9] = new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[10] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[11] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[12] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[13] = new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[14] = new ThemeDescription(this.codeField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[15] = new ThemeDescription(this.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[16] = new ThemeDescription(this.codeField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[17] = new ThemeDescription(this.phoneField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[18] = new ThemeDescription(this.phoneField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        themeDescriptionArr[19] = new ThemeDescription(this.phoneField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        themeDescriptionArr[20] = new ThemeDescription(this.phoneField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        themeDescriptionArr[21] = new ThemeDescription(this.textView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[22] = new ThemeDescription(this.lineView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhiteGrayLine);
        themeDescriptionArr[23] = new ThemeDescription(this.countryButton, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[24] = new ThemeDescription(this.editDoneItemProgress, 0, null, null, null, null, Theme.key_contextProgressInner2);
        themeDescriptionArr[25] = new ThemeDescription(this.editDoneItemProgress, 0, null, null, null, null, Theme.key_contextProgressOuter2);
        int i = 0;
        Class[] clsArr = null;
        Paint paint = null;
        themeDescriptionArr[26] = new ThemeDescription(null, i, clsArr, paint, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, anonymousClass14, Theme.key_avatar_text);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[30] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[31] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[32] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[33] = new ThemeDescription(null, 0, null, null, null, anonymousClass14, Theme.key_avatar_backgroundPink);
        return themeDescriptionArr;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.ignoreSelection) {
            this.ignoreSelection = false;
            return;
        }
        this.ignoreOnTextChange = true;
        this.codeField.setText((CharSequence) this.countriesMap.get((String) this.countriesArray.get(i)));
        this.ignoreOnTextChange = false;
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onResume() {
        super.onResume();
        if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true)) {
            this.firstNameField.requestFocus();
            AndroidUtilities.showKeyboard(this.firstNameField);
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            this.firstNameField.requestFocus();
            AndroidUtilities.showKeyboard(this.firstNameField);
        }
    }

    public void selectCountry(String str) {
        if (this.countriesArray.indexOf(str) != -1) {
            this.ignoreOnTextChange = true;
            String str2 = (String) this.countriesMap.get(str);
            this.codeField.setText(str2);
            this.countryButton.setText(str);
            str2 = (String) this.phoneFormatMap.get(str2);
            this.phoneField.setHintText(str2 != null ? str2.replace('X', '–') : null);
            this.countryState = 0;
            this.ignoreOnTextChange = false;
        }
    }
}
