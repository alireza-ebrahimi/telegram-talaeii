package org.telegram.messenger;

import android.text.TextUtils;
import org.ir.talaeii.R;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.TLRPC$TL_userContact_old2;
import org.telegram.tgnet.TLRPC$TL_userDeleted_old2;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_userSelf_old3;
import org.telegram.tgnet.TLRPC.User;

public class UserObject {
    public static String getFirstName(User user) {
        if (user == null || isDeleted(user)) {
            return "DELETED";
        }
        Object obj = user.first_name;
        if (obj == null || obj.length() == 0) {
            obj = user.last_name;
        }
        return TextUtils.isEmpty(obj) ? LocaleController.getString("HiddenName", R.string.HiddenName) : obj;
    }

    public static String getUserName(User user) {
        if (user == null || isDeleted(user)) {
            return LocaleController.getString("HiddenName", R.string.HiddenName);
        }
        String formatName = ContactsController.formatName(user.first_name, user.last_name);
        return (formatName.length() != 0 || user.phone == null || user.phone.length() == 0) ? formatName : C2488b.m12189a().m12197e("+" + user.phone);
    }

    public static boolean isContact(User user) {
        return user != null && ((user instanceof TLRPC$TL_userContact_old2) || user.contact || user.mutual_contact);
    }

    public static boolean isDeleted(User user) {
        return user == null || (user instanceof TLRPC$TL_userDeleted_old2) || (user instanceof TLRPC$TL_userEmpty) || user.deleted;
    }

    public static boolean isUserSelf(User user) {
        return user != null && ((user instanceof TLRPC$TL_userSelf_old3) || user.self);
    }
}
