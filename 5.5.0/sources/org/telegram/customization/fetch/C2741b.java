package org.telegram.customization.fetch;

/* renamed from: org.telegram.customization.fetch.b */
final class C2741b {
    /* renamed from: a */
    static int m12726a(String str) {
        return str == null ? -101 : (str.equalsIgnoreCase("FNC") || str.equalsIgnoreCase("open failed: ENOENT (No such file or directory)")) ? -102 : str.equalsIgnoreCase("TI") ? -103 : str.equalsIgnoreCase("DIE") ? -118 : (str.equalsIgnoreCase("recvfrom failed: ETIMEDOUT (Connection timed out)") || str.equalsIgnoreCase("timeout")) ? -104 : (str.equalsIgnoreCase("java.io.IOException: 404") || str.contains("No address associated with hostname")) ? -106 : str.contains("Unable to resolve host") ? -105 : str.equalsIgnoreCase("open failed: EACCES (Permission denied)") ? -107 : (str.equalsIgnoreCase("write failed: ENOSPC (No space left on device)") || str.equalsIgnoreCase("database or disk is full (code 13)")) ? -108 : str.contains("SSRV:") ? -110 : str.contains("column _file_path is not unique") ? -113 : -101;
    }
}
