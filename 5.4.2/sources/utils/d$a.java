package utils;

import java.util.Date;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

class d$a {
    /* renamed from: a */
    public String f10240a = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: b */
    public String f10241b = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: c */
    int f10242c;
    /* renamed from: d */
    int f10243d;
    /* renamed from: e */
    int f10244e;
    /* renamed from: f */
    final /* synthetic */ C3792d f10245f;

    public d$a(C3792d c3792d, Date date) {
        this.f10245f = c3792d;
        m14149a(date);
    }

    /* renamed from: a */
    private void m14149a(Date date) {
        int i = 10;
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int date2 = date.getDate();
        int day = date.getDay();
        r5 = new int[12];
        int[] iArr = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        iArr[0] = 0;
        iArr[1] = 31;
        iArr[2] = 60;
        iArr[3] = 91;
        iArr[4] = 121;
        iArr[5] = 152;
        iArr[6] = 182;
        iArr[7] = 213;
        iArr[8] = 244;
        iArr[9] = 274;
        iArr[10] = 305;
        iArr[11] = 335;
        if (year % 4 != 0) {
            this.f10242c = r5[month - 1] + date2;
            if (this.f10242c > 79) {
                this.f10242c -= 79;
                if (this.f10242c <= 186) {
                    switch (this.f10242c % 31) {
                        case 0:
                            this.f10243d = this.f10242c / 31;
                            this.f10242c = 31;
                            break;
                        default:
                            this.f10243d = (this.f10242c / 31) + 1;
                            this.f10242c %= 31;
                            break;
                    }
                    this.f10244e = year - 621;
                } else {
                    this.f10242c -= 186;
                    switch (this.f10242c % 30) {
                        case 0:
                            this.f10243d = (this.f10242c / 30) + 6;
                            this.f10242c = 30;
                            break;
                        default:
                            this.f10243d = (this.f10242c / 30) + 7;
                            this.f10242c %= 30;
                            break;
                    }
                    this.f10244e = year - 621;
                }
            } else {
                if (year > 1996 && year % 4 == 1) {
                    i = 11;
                }
                this.f10242c = i + this.f10242c;
                switch (this.f10242c % 30) {
                    case 0:
                        this.f10243d = (this.f10242c / 30) + 9;
                        this.f10242c = 30;
                        break;
                    default:
                        this.f10243d = (this.f10242c / 30) + 10;
                        this.f10242c %= 30;
                        break;
                }
                this.f10244e = year - 622;
            }
        } else {
            this.f10242c = iArr[month - 1] + date2;
            i = year >= 1996 ? 79 : 80;
            if (this.f10242c > i) {
                this.f10242c -= i;
                if (this.f10242c <= 186) {
                    switch (this.f10242c % 31) {
                        case 0:
                            this.f10243d = this.f10242c / 31;
                            this.f10242c = 31;
                            break;
                        default:
                            this.f10243d = (this.f10242c / 31) + 1;
                            this.f10242c %= 31;
                            break;
                    }
                    this.f10244e = year - 621;
                } else {
                    this.f10242c -= 186;
                    switch (this.f10242c % 30) {
                        case 0:
                            this.f10243d = (this.f10242c / 30) + 6;
                            this.f10242c = 30;
                            break;
                        default:
                            this.f10243d = (this.f10242c / 30) + 7;
                            this.f10242c %= 30;
                            break;
                    }
                    this.f10244e = year - 621;
                }
            } else {
                this.f10242c += 10;
                switch (this.f10242c % 30) {
                    case 0:
                        this.f10243d = (this.f10242c / 30) + 9;
                        this.f10242c = 30;
                        break;
                    default:
                        this.f10243d = (this.f10242c / 30) + 10;
                        this.f10242c %= 30;
                        break;
                }
                this.f10244e = year - 622;
            }
        }
        switch (this.f10243d) {
            case 1:
                this.f10241b = "فروردين";
                break;
            case 2:
                this.f10241b = "ارديبهشت";
                break;
            case 3:
                this.f10241b = "خرداد";
                break;
            case 4:
                this.f10241b = "تير";
                break;
            case 5:
                this.f10241b = "مرداد";
                break;
            case 6:
                this.f10241b = "شهريور";
                break;
            case 7:
                this.f10241b = "مهر";
                break;
            case 8:
                this.f10241b = "آبان";
                break;
            case 9:
                this.f10241b = "آذر";
                break;
            case 10:
                this.f10241b = "دي";
                break;
            case 11:
                this.f10241b = "بهمن";
                break;
            case 12:
                this.f10241b = "اسفند";
                break;
        }
        switch (day) {
            case 0:
                this.f10240a = "يکشنبه";
                return;
            case 1:
                this.f10240a = "دوشنبه";
                return;
            case 2:
                this.f10240a = "سه شنبه";
                return;
            case 3:
                this.f10240a = "چهارشنبه";
                return;
            case 4:
                this.f10240a = "پنج شنبه";
                return;
            case 5:
                this.f10240a = "جمعه";
                return;
            case 6:
                this.f10240a = "شنبه";
                return;
            default:
                return;
        }
    }
}
