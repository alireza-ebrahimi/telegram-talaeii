package com.persianswitch.sdk.base.utils.pdate;

import com.persianswitch.okhttp3.internal.http.StatusLine;
import java.util.Locale;

public class Roozh {
    private int day;
    private int gD;
    private int gM;
    private int gY;
    private int jD;
    private int jM;
    private int jY;
    private int leap;
    private int march;
    private int month;
    private int year;

    private int JG2JD(int year, int month, int day, int J1G0) {
        int jd = (((((((year + 4800) + ((month - 14) / 12)) * 1461) / 4) + ((((month - 2) - (((month - 14) / 12) * 12)) * 367) / 12)) - (((((year + 4900) + ((month - 14) / 12)) / 100) * 3) / 4)) + day) - 32075;
        if (J1G0 == 0) {
            return (jd - (((((100100 + year) + ((month - 8) / 6)) / 100) * 3) / 4)) + 752;
        }
        return jd;
    }

    private void JD2JG(int JD, int J1G0) {
        int j = (JD * 4) + 139361631;
        if (J1G0 == 0) {
            j = (((((((JD * 4) + 183187720) / 146097) * 3) / 4) * 4) + j) - 3908;
        }
        int i = (((j % 1461) / 4) * 5) + StatusLine.HTTP_PERM_REDIRECT;
        this.gD = ((i % 153) / 5) + 1;
        this.gM = ((i / 153) % 12) + 1;
        this.gY = ((j / 1461) - 100100) + ((8 - this.gM) / 6);
    }

    private void JD2Jal(int JDN) {
        JD2JG(JDN, 0);
        this.jY = this.gY - 621;
        JalCal(this.jY);
        int k = JDN - JG2JD(this.gY, 3, this.march, 0);
        if (k < 0) {
            this.jY--;
            k += 179;
            if (this.leap == 1) {
                k++;
            }
        } else if (k <= 185) {
            this.jM = (k / 31) + 1;
            this.jD = (k % 31) + 1;
            return;
        } else {
            k -= 186;
        }
        this.jM = (k / 30) + 7;
        this.jD = (k % 30) + 1;
    }

    private int Jal2JD(int jY, int jM, int jD) {
        JalCal(jY);
        return (((JG2JD(this.gY, 3, this.march, 1) + ((jM - 1) * 31)) - ((jM / 7) * (jM - 7))) + jD) - 1;
    }

    private void JalCal(int jY) {
        this.march = 0;
        this.leap = 0;
        int[] breaks = new int[]{-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        this.gY = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];
        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            int jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = (((N / 33) * 8) + leapJ) + (((N % 33) + 3) / 4);
                if (jump % 33 == 4 && jump - N == 4) {
                    leapJ++;
                }
                this.march = (leapJ + 20) - (((this.gY / 4) - ((((this.gY / 100) + 1) * 3) / 4)) - 150);
                if (jump - N < 6) {
                    N = (N - jump) + (((jump + 4) / 33) * 33);
                }
                this.leap = (((N + 1) % 33) - 1) % 4;
                if (this.leap == -1) {
                    this.leap = 4;
                    return;
                }
                return;
            }
            leapJ = (((jump / 33) * 8) + leapJ) + ((jump % 33) / 4);
            jp = jm;
        }
    }

    public String toString() {
        return String.format(Locale.US, "%04d-%02d-%02d", new Object[]{Integer.valueOf(getYear()), Integer.valueOf(getMonth()), Integer.valueOf(getDay())});
    }

    public void GregorianToPersian(int year, int month, int day) {
        JD2Jal(JG2JD(year, month, day, 0));
        this.year = this.jY;
        this.month = this.jM;
        this.day = this.jD;
    }

    public void PersianToGregorian(int year, int month, int day) {
        JD2JG(Jal2JD(year, month, day), 0);
        this.year = this.gY;
        this.month = this.gM;
        this.day = this.gD;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }
}
