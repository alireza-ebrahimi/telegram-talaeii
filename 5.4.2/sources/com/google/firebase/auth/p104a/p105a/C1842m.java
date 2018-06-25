package com.google.firebase.auth.p104a.p105a;

import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.firebase.C1812c;
import com.google.firebase.C1813a;
import com.google.firebase.C1923e;
import com.google.firebase.C1925g;
import com.google.firebase.auth.C1851c;
import com.google.firebase.auth.C1852a;
import com.google.firebase.auth.C1853b;
import com.google.firebase.auth.C1854d;
import com.google.firebase.auth.C1855e;
import com.google.firebase.auth.C1856f;
import com.google.firebase.auth.C1858h;
import com.google.firebase.auth.C1859i;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.p107c.p109a.C1898a;

/* renamed from: com.google.firebase.auth.a.a.m */
public final class C1842m {
    @VisibleForTesting
    /* renamed from: a */
    private static final SparseArray<Pair<String, String>> f5504a;

    static {
        SparseArray sparseArray = new SparseArray();
        f5504a = sparseArray;
        sparseArray.put(17000, new Pair("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
        f5504a.put(17002, new Pair("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
        f5504a.put(17004, new Pair("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
        f5504a.put(17008, new Pair("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
        f5504a.put(17009, new Pair("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
        f5504a.put(17024, new Pair("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
        f5504a.put(17014, new Pair("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
        f5504a.put(17012, new Pair("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
        f5504a.put(17007, new Pair("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
        f5504a.put(17025, new Pair("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
        f5504a.put(17005, new Pair("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
        f5504a.put(17021, new Pair("ERROR_USER_TOKEN_EXPIRED", "The user's credential is no longer valid. The user must sign in again."));
        f5504a.put(17011, new Pair("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
        f5504a.put(17017, new Pair("ERROR_INVALID_USER_TOKEN", "This user's credential isn't valid for this project. This can happen if the user's token has been tampered with, or if the user isn't for the project associated with this API key."));
        f5504a.put(17006, new Pair("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
        f5504a.put(17026, new Pair("ERROR_WEAK_PASSWORD", "The given password is invalid."));
        f5504a.put(17029, new Pair("ERROR_EXPIRED_ACTION_CODE", "The out of band code has expired."));
        f5504a.put(17030, new Pair("ERROR_INVALID_ACTION_CODE", "The out of band code is invalid. This can happen if the code is malformed, expired, or has already been used."));
        f5504a.put(17031, new Pair("ERROR_INVALID_MESSAGE_PAYLOAD", "The email template corresponding to this action contains invalid characters in its message. Please fix by going to the Auth email templates section in the Firebase Console."));
        f5504a.put(17033, new Pair("ERROR_INVALID_RECIPIENT_EMAIL", "The email corresponding to this action failed to send as the provided recipient email address is invalid."));
        f5504a.put(17032, new Pair("ERROR_INVALID_SENDER", "The email template corresponding to this action contains an invalid sender email or name. Please fix by going to the Auth email templates section in the Firebase Console."));
        f5504a.put(17034, new Pair("ERROR_MISSING_EMAIL", "An email address must be provided."));
        f5504a.put(17035, new Pair("ERROR_MISSING_PASSWORD", "A password must be provided."));
        f5504a.put(17041, new Pair("ERROR_MISSING_PHONE_NUMBER", "To send verification codes, provide a phone number for the recipient."));
        f5504a.put(17042, new Pair("ERROR_INVALID_PHONE_NUMBER", "The format of the phone number provided is incorrect. Please enter the phone number in a format that can be parsed into E.164 format. E.164 phone numbers are written in the format [+][country code][subscriber number including area code]."));
        f5504a.put(17043, new Pair("ERROR_MISSING_VERIFICATION_CODE", "The Phone Auth Credential was created with an empty sms verification Code"));
        f5504a.put(17044, new Pair("ERROR_INVALID_VERIFICATION_CODE", "The sms verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user."));
        f5504a.put(17045, new Pair("ERROR_MISSING_VERIFICATION_ID", "The Phone Auth Credential was created with an empty verification ID"));
        f5504a.put(17046, new Pair("ERROR_INVALID_VERIFICATION_ID", "The verification ID used to create the phone auth credential is invalid."));
        f5504a.put(17049, new Pair("ERROR_RETRY_PHONE_AUTH", "An error occurred during authentication using the PhoneAuthCredential. Please retry authentication."));
        f5504a.put(17051, new Pair("ERROR_SESSION_EXPIRED", "The sms code has expired. Please re-send the verification code to try again."));
        f5504a.put(17052, new Pair("ERROR_QUOTA_EXCEEDED", "The sms quota for this project has been exceeded."));
        f5504a.put(17028, new Pair("ERROR_APP_NOT_AUTHORIZED", "This app is not authorized to use Firebase Authentication. Please verifythat the correct package name and SHA-1 are configured in the Firebase Console."));
        f5504a.put(17063, new Pair("ERROR_API_NOT_AVAILABLE", "The API that you are calling is not available on devices without Google Play Services."));
        f5504a.put(17062, new Pair("ERROR_WEB_INTERNAL_ERROR", "There was an internal error in the web widget."));
        f5504a.put(17064, new Pair("ERROR_INVALID_CERT_HASH", "There was an error while trying to get your package certificate hash."));
        f5504a.put(17065, new Pair("ERROR_WEB_STORAGE_UNSUPPORTED", "This browser is not supported or 3rd party cookies and data may be disabled."));
        f5504a.put(17040, new Pair("ERROR_MISSING_CONTINUE_URI", "A continue URL must be provided in the request."));
    }

    /* renamed from: a */
    public static C1858h m8592a(Status status, PhoneAuthCredential phoneAuthCredential) {
        int statusCode = status.getStatusCode();
        return new C1858h(C1842m.m8594a(statusCode), C1842m.m8595a(C1842m.m8596b(statusCode), status), phoneAuthCredential);
    }

    /* renamed from: a */
    public static C1812c m8593a(Status status) {
        int statusCode = status.getStatusCode();
        String a = C1842m.m8595a(C1842m.m8596b(statusCode), status);
        switch (statusCode) {
            case 17000:
                return new C1854d(C1842m.m8594a(statusCode), a);
            case 17002:
            case 17004:
            case 17008:
            case 17009:
            case 17024:
                return new C1854d(C1842m.m8594a(statusCode), a);
            case 17005:
            case 17011:
            case 17017:
            case 17021:
                return new C1855e(C1842m.m8594a(statusCode), a);
            case 17006:
                return new C1851c(C1842m.m8594a(statusCode), a);
            case 17007:
            case 17012:
            case 17025:
                return new C1858h(C1842m.m8594a(statusCode), a);
            case 17010:
                return new C1925g(C1842m.m8595a("We have blocked all requests from this device due to unusual activity. Try again later.", status));
            case 17014:
                return new C1856f(C1842m.m8594a(statusCode), a);
            case 17015:
                return new C1812c(C1842m.m8595a("User has already been linked to the given provider.", status));
            case 17016:
                return new C1812c(C1842m.m8595a("User was not linked to an account with the given provider.", status));
            case 17020:
                return new C1923e(C1842m.m8595a("A network error (such as timeout, interrupted connection or unreachable host) has occurred.", status));
            case 17026:
                return new C1859i(C1842m.m8594a(statusCode), a, status.getStatusMessage());
            case 17028:
                return new C1851c(C1842m.m8594a(statusCode), a);
            case 17029:
            case 17030:
                return new C1852a(C1842m.m8594a(statusCode), a);
            case 17031:
            case 17032:
            case 17033:
                return new C1853b(C1842m.m8594a(statusCode), a);
            case 17034:
            case 17035:
            case 17041:
            case 17042:
            case 17043:
            case 17044:
            case 17045:
            case 17046:
            case 17049:
            case 17051:
                return new C1854d(C1842m.m8594a(statusCode), a);
            case 17040:
            case 17062:
            case 17064:
            case 17065:
                return new C1851c(C1842m.m8594a(statusCode), a);
            case 17052:
                return new C1925g(a);
            case 17061:
                return new C1923e(C1842m.m8595a("There was a failure in the connection between the web widget and the Firebase Auth backend.", status));
            case 17063:
                return new C1813a(a);
            case 17495:
                return new C1898a(C1842m.m8595a("Please sign in before trying to get a token.", status));
            case 17499:
                return new C1812c(C1842m.m8595a("An internal error has occurred.", status));
            default:
                return new C1812c("An internal error has occurred.");
        }
    }

    /* renamed from: a */
    private static String m8594a(int i) {
        Pair pair = (Pair) f5504a.get(i);
        return pair != null ? (String) pair.first : "INTERNAL_ERROR";
    }

    /* renamed from: a */
    private static String m8595a(String str, Status status) {
        if (TextUtils.isEmpty(status.getStatusMessage())) {
            return str;
        }
        return String.format(String.valueOf(str).concat(" [ %s ]"), new Object[]{status.getStatusMessage()});
    }

    /* renamed from: b */
    private static String m8596b(int i) {
        Pair pair = (Pair) f5504a.get(i);
        return pair != null ? (String) pair.second : "An internal error happened";
    }
}
