package utils;

import java.util.Date;

class Utilities$SolarCalendar {
    int date;
    int month;
    public String strMonth = "";
    public String strWeekDay = "";
    final /* synthetic */ Utilities this$0;
    int year;

    public Utilities$SolarCalendar(Utilities utilities) {
        this.this$0 = utilities;
        calcSolarCalendar(new Date());
    }

    public Utilities$SolarCalendar(Utilities utilities, Date MiladiDate) {
        this.this$0 = utilities;
        calcSolarCalendar(MiladiDate);
    }

    private void calcSolarCalendar(Date MiladiDate) {
        int miladiYear = MiladiDate.getYear() + 1900;
        int miladiMonth = MiladiDate.getMonth() + 1;
        int miladiDate = MiladiDate.getDate();
        int WeekDay = MiladiDate.getDay();
        buf1 = new int[12];
        int[] buf2 = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;
        int ld;
        if (miladiYear % 4 != 0) {
            this.date = buf1[miladiMonth - 1] + miladiDate;
            if (this.date > 79) {
                this.date -= 79;
                if (this.date <= 186) {
                    switch (this.date % 31) {
                        case 0:
                            this.month = this.date / 31;
                            this.date = 31;
                            break;
                        default:
                            this.month = (this.date / 31) + 1;
                            this.date %= 31;
                            break;
                    }
                    this.year = miladiYear - 621;
                } else {
                    this.date -= 186;
                    switch (this.date % 30) {
                        case 0:
                            this.month = (this.date / 30) + 6;
                            this.date = 30;
                            break;
                        default:
                            this.month = (this.date / 30) + 7;
                            this.date %= 30;
                            break;
                    }
                    this.year = miladiYear - 621;
                }
            } else {
                if (miladiYear <= 1996 || miladiYear % 4 != 1) {
                    ld = 10;
                } else {
                    ld = 11;
                }
                this.date += ld;
                switch (this.date % 30) {
                    case 0:
                        this.month = (this.date / 30) + 9;
                        this.date = 30;
                        break;
                    default:
                        this.month = (this.date / 30) + 10;
                        this.date %= 30;
                        break;
                }
                this.year = miladiYear - 622;
            }
        } else {
            this.date = buf2[miladiMonth - 1] + miladiDate;
            if (miladiYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (this.date > ld) {
                this.date -= ld;
                if (this.date <= 186) {
                    switch (this.date % 31) {
                        case 0:
                            this.month = this.date / 31;
                            this.date = 31;
                            break;
                        default:
                            this.month = (this.date / 31) + 1;
                            this.date %= 31;
                            break;
                    }
                    this.year = miladiYear - 621;
                } else {
                    this.date -= 186;
                    switch (this.date % 30) {
                        case 0:
                            this.month = (this.date / 30) + 6;
                            this.date = 30;
                            break;
                        default:
                            this.month = (this.date / 30) + 7;
                            this.date %= 30;
                            break;
                    }
                    this.year = miladiYear - 621;
                }
            } else {
                this.date += 10;
                switch (this.date % 30) {
                    case 0:
                        this.month = (this.date / 30) + 9;
                        this.date = 30;
                        break;
                    default:
                        this.month = (this.date / 30) + 10;
                        this.date %= 30;
                        break;
                }
                this.year = miladiYear - 622;
            }
        }
        switch (this.month) {
            case 1:
                this.strMonth = "فروردين";
                break;
            case 2:
                this.strMonth = "ارديبهشت";
                break;
            case 3:
                this.strMonth = "خرداد";
                break;
            case 4:
                this.strMonth = "تير";
                break;
            case 5:
                this.strMonth = "مرداد";
                break;
            case 6:
                this.strMonth = "شهريور";
                break;
            case 7:
                this.strMonth = "مهر";
                break;
            case 8:
                this.strMonth = "آبان";
                break;
            case 9:
                this.strMonth = "آذر";
                break;
            case 10:
                this.strMonth = "دي";
                break;
            case 11:
                this.strMonth = "بهمن";
                break;
            case 12:
                this.strMonth = "اسفند";
                break;
        }
        switch (WeekDay) {
            case 0:
                this.strWeekDay = "يکشنبه";
                return;
            case 1:
                this.strWeekDay = "دوشنبه";
                return;
            case 2:
                this.strWeekDay = "سه شنبه";
                return;
            case 3:
                this.strWeekDay = "چهارشنبه";
                return;
            case 4:
                this.strWeekDay = "پنج شنبه";
                return;
            case 5:
                this.strWeekDay = "جمعه";
                return;
            case 6:
                this.strWeekDay = "شنبه";
                return;
            default:
                return;
        }
    }
}
