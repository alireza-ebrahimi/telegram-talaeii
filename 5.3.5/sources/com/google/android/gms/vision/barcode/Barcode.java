package com.google.android.gms.vision.barcode;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public class Barcode extends zzbgl {
    public static final int ALL_FORMATS = 0;
    public static final int AZTEC = 4096;
    public static final int CALENDAR_EVENT = 11;
    public static final int CODABAR = 8;
    public static final int CODE_128 = 1;
    public static final int CODE_39 = 2;
    public static final int CODE_93 = 4;
    public static final int CONTACT_INFO = 1;
    @Hide
    public static final Creator<Barcode> CREATOR = new zzb();
    public static final int DATA_MATRIX = 16;
    public static final int DRIVER_LICENSE = 12;
    public static final int EAN_13 = 32;
    public static final int EAN_8 = 64;
    public static final int EMAIL = 2;
    public static final int GEO = 10;
    public static final int ISBN = 3;
    public static final int ITF = 128;
    public static final int PDF417 = 2048;
    public static final int PHONE = 4;
    public static final int PRODUCT = 5;
    public static final int QR_CODE = 256;
    public static final int SMS = 6;
    public static final int TEXT = 7;
    public static final int UPC_A = 512;
    public static final int UPC_E = 1024;
    public static final int URL = 8;
    public static final int WIFI = 9;
    public CalendarEvent calendarEvent;
    public ContactInfo contactInfo;
    public Point[] cornerPoints;
    public String displayValue;
    public DriverLicense driverLicense;
    public Email email;
    public int format;
    public GeoPoint geoPoint;
    public Phone phone;
    public String rawValue;
    public Sms sms;
    public UrlBookmark url;
    public int valueFormat;
    public WiFi wifi;

    public static class Address extends zzbgl {
        @Hide
        public static final Creator<Address> CREATOR = new zza();
        public static final int HOME = 2;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public String[] addressLines;
        public int type;

        @Hide
        public Address(int i, String[] strArr) {
            this.type = i;
            this.addressLines = strArr;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zzc(parcel, 2, this.type);
            zzbgo.zza(parcel, 3, this.addressLines, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class CalendarDateTime extends zzbgl {
        @Hide
        public static final Creator<CalendarDateTime> CREATOR = new zzd();
        public int day;
        public int hours;
        public boolean isUtc;
        public int minutes;
        public int month;
        public String rawValue;
        public int seconds;
        public int year;

        @Hide
        public CalendarDateTime(int i, int i2, int i3, int i4, int i5, int i6, boolean z, String str) {
            this.year = i;
            this.month = i2;
            this.day = i3;
            this.hours = i4;
            this.minutes = i5;
            this.seconds = i6;
            this.isUtc = z;
            this.rawValue = str;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zzc(parcel, 2, this.year);
            zzbgo.zzc(parcel, 3, this.month);
            zzbgo.zzc(parcel, 4, this.day);
            zzbgo.zzc(parcel, 5, this.hours);
            zzbgo.zzc(parcel, 6, this.minutes);
            zzbgo.zzc(parcel, 7, this.seconds);
            zzbgo.zza(parcel, 8, this.isUtc);
            zzbgo.zza(parcel, 9, this.rawValue, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class CalendarEvent extends zzbgl {
        @Hide
        public static final Creator<CalendarEvent> CREATOR = new zze();
        public String description;
        public CalendarDateTime end;
        public String location;
        public String organizer;
        public CalendarDateTime start;
        public String status;
        public String summary;

        @Hide
        public CalendarEvent(String str, String str2, String str3, String str4, String str5, CalendarDateTime calendarDateTime, CalendarDateTime calendarDateTime2) {
            this.summary = str;
            this.description = str2;
            this.location = str3;
            this.organizer = str4;
            this.status = str5;
            this.start = calendarDateTime;
            this.end = calendarDateTime2;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.summary, false);
            zzbgo.zza(parcel, 3, this.description, false);
            zzbgo.zza(parcel, 4, this.location, false);
            zzbgo.zza(parcel, 5, this.organizer, false);
            zzbgo.zza(parcel, 6, this.status, false);
            zzbgo.zza(parcel, 7, this.start, i, false);
            zzbgo.zza(parcel, 8, this.end, i, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class ContactInfo extends zzbgl {
        @Hide
        public static final Creator<ContactInfo> CREATOR = new zzf();
        public Address[] addresses;
        public Email[] emails;
        public PersonName name;
        public String organization;
        public Phone[] phones;
        public String title;
        public String[] urls;

        @Hide
        public ContactInfo(PersonName personName, String str, String str2, Phone[] phoneArr, Email[] emailArr, String[] strArr, Address[] addressArr) {
            this.name = personName;
            this.organization = str;
            this.title = str2;
            this.phones = phoneArr;
            this.emails = emailArr;
            this.urls = strArr;
            this.addresses = addressArr;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.name, i, false);
            zzbgo.zza(parcel, 3, this.organization, false);
            zzbgo.zza(parcel, 4, this.title, false);
            zzbgo.zza(parcel, 5, this.phones, i, false);
            zzbgo.zza(parcel, 6, this.emails, i, false);
            zzbgo.zza(parcel, 7, this.urls, false);
            zzbgo.zza(parcel, 8, this.addresses, i, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class DriverLicense extends zzbgl {
        @Hide
        public static final Creator<DriverLicense> CREATOR = new zzg();
        public String addressCity;
        public String addressState;
        public String addressStreet;
        public String addressZip;
        public String birthDate;
        public String documentType;
        public String expiryDate;
        public String firstName;
        public String gender;
        public String issueDate;
        public String issuingCountry;
        public String lastName;
        public String licenseNumber;
        public String middleName;

        @Hide
        public DriverLicense(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
            this.documentType = str;
            this.firstName = str2;
            this.middleName = str3;
            this.lastName = str4;
            this.gender = str5;
            this.addressStreet = str6;
            this.addressCity = str7;
            this.addressState = str8;
            this.addressZip = str9;
            this.licenseNumber = str10;
            this.issueDate = str11;
            this.expiryDate = str12;
            this.birthDate = str13;
            this.issuingCountry = str14;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.documentType, false);
            zzbgo.zza(parcel, 3, this.firstName, false);
            zzbgo.zza(parcel, 4, this.middleName, false);
            zzbgo.zza(parcel, 5, this.lastName, false);
            zzbgo.zza(parcel, 6, this.gender, false);
            zzbgo.zza(parcel, 7, this.addressStreet, false);
            zzbgo.zza(parcel, 8, this.addressCity, false);
            zzbgo.zza(parcel, 9, this.addressState, false);
            zzbgo.zza(parcel, 10, this.addressZip, false);
            zzbgo.zza(parcel, 11, this.licenseNumber, false);
            zzbgo.zza(parcel, 12, this.issueDate, false);
            zzbgo.zza(parcel, 13, this.expiryDate, false);
            zzbgo.zza(parcel, 14, this.birthDate, false);
            zzbgo.zza(parcel, 15, this.issuingCountry, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class Email extends zzbgl {
        @Hide
        public static final Creator<Email> CREATOR = new zzh();
        public static final int HOME = 2;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public String address;
        public String body;
        public String subject;
        public int type;

        @Hide
        public Email(int i, String str, String str2, String str3) {
            this.type = i;
            this.address = str;
            this.subject = str2;
            this.body = str3;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zzc(parcel, 2, this.type);
            zzbgo.zza(parcel, 3, this.address, false);
            zzbgo.zza(parcel, 4, this.subject, false);
            zzbgo.zza(parcel, 5, this.body, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class GeoPoint extends zzbgl {
        @Hide
        public static final Creator<GeoPoint> CREATOR = new zzi();
        public double lat;
        public double lng;

        @Hide
        public GeoPoint(double d, double d2) {
            this.lat = d;
            this.lng = d2;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.lat);
            zzbgo.zza(parcel, 3, this.lng);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class PersonName extends zzbgl {
        @Hide
        public static final Creator<PersonName> CREATOR = new zzj();
        public String first;
        public String formattedName;
        public String last;
        public String middle;
        public String prefix;
        public String pronunciation;
        public String suffix;

        @Hide
        public PersonName(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.formattedName = str;
            this.pronunciation = str2;
            this.prefix = str3;
            this.first = str4;
            this.middle = str5;
            this.last = str6;
            this.suffix = str7;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.formattedName, false);
            zzbgo.zza(parcel, 3, this.pronunciation, false);
            zzbgo.zza(parcel, 4, this.prefix, false);
            zzbgo.zza(parcel, 5, this.first, false);
            zzbgo.zza(parcel, 6, this.middle, false);
            zzbgo.zza(parcel, 7, this.last, false);
            zzbgo.zza(parcel, 8, this.suffix, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class Phone extends zzbgl {
        @Hide
        public static final Creator<Phone> CREATOR = new zzk();
        public static final int FAX = 3;
        public static final int HOME = 2;
        public static final int MOBILE = 4;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        public String number;
        public int type;

        @Hide
        public Phone(int i, String str) {
            this.type = i;
            this.number = str;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zzc(parcel, 2, this.type);
            zzbgo.zza(parcel, 3, this.number, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class Sms extends zzbgl {
        @Hide
        public static final Creator<Sms> CREATOR = new zzl();
        public String message;
        public String phoneNumber;

        @Hide
        public Sms(String str, String str2) {
            this.message = str;
            this.phoneNumber = str2;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.message, false);
            zzbgo.zza(parcel, 3, this.phoneNumber, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class UrlBookmark extends zzbgl {
        @Hide
        public static final Creator<UrlBookmark> CREATOR = new zzm();
        public String title;
        public String url;

        @Hide
        public UrlBookmark(String str, String str2) {
            this.title = str;
            this.url = str2;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.title, false);
            zzbgo.zza(parcel, 3, this.url, false);
            zzbgo.zzai(parcel, zze);
        }
    }

    public static class WiFi extends zzbgl {
        @Hide
        public static final Creator<WiFi> CREATOR = new zzn();
        public static final int OPEN = 1;
        public static final int WEP = 3;
        public static final int WPA = 2;
        public int encryptionType;
        public String password;
        public String ssid;

        @Hide
        public WiFi(String str, String str2, int i) {
            this.ssid = str;
            this.password = str2;
            this.encryptionType = i;
        }

        @Hide
        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbgo.zze(parcel);
            zzbgo.zza(parcel, 2, this.ssid, false);
            zzbgo.zza(parcel, 3, this.password, false);
            zzbgo.zzc(parcel, 4, this.encryptionType);
            zzbgo.zzai(parcel, zze);
        }
    }

    @Hide
    public Barcode(int i, String str, String str2, int i2, Point[] pointArr, Email email, Phone phone, Sms sms, WiFi wiFi, UrlBookmark urlBookmark, GeoPoint geoPoint, CalendarEvent calendarEvent, ContactInfo contactInfo, DriverLicense driverLicense) {
        this.format = i;
        this.rawValue = str;
        this.displayValue = str2;
        this.valueFormat = i2;
        this.cornerPoints = pointArr;
        this.email = email;
        this.phone = phone;
        this.sms = sms;
        this.wifi = wiFi;
        this.url = urlBookmark;
        this.geoPoint = geoPoint;
        this.calendarEvent = calendarEvent;
        this.contactInfo = contactInfo;
        this.driverLicense = driverLicense;
    }

    public Rect getBoundingBox() {
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MAX_VALUE;
        for (Point point : this.cornerPoints) {
            i4 = Math.min(i4, point.x);
            i3 = Math.max(i3, point.x);
            i = Math.min(i, point.y);
            i2 = Math.max(i2, point.y);
        }
        return new Rect(i4, i, i3, i2);
    }

    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.format);
        zzbgo.zza(parcel, 3, this.rawValue, false);
        zzbgo.zza(parcel, 4, this.displayValue, false);
        zzbgo.zzc(parcel, 5, this.valueFormat);
        zzbgo.zza(parcel, 6, this.cornerPoints, i, false);
        zzbgo.zza(parcel, 7, this.email, i, false);
        zzbgo.zza(parcel, 8, this.phone, i, false);
        zzbgo.zza(parcel, 9, this.sms, i, false);
        zzbgo.zza(parcel, 10, this.wifi, i, false);
        zzbgo.zza(parcel, 11, this.url, i, false);
        zzbgo.zza(parcel, 12, this.geoPoint, i, false);
        zzbgo.zza(parcel, 13, this.calendarEvent, i, false);
        zzbgo.zza(parcel, 14, this.contactInfo, i, false);
        zzbgo.zza(parcel, 15, this.driverLicense, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
