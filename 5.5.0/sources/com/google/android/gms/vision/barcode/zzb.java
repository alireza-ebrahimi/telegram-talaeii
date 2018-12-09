package com.google.android.gms.vision.barcode;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.vision.barcode.Barcode.CalendarEvent;
import com.google.android.gms.vision.barcode.Barcode.ContactInfo;
import com.google.android.gms.vision.barcode.Barcode.DriverLicense;
import com.google.android.gms.vision.barcode.Barcode.Email;
import com.google.android.gms.vision.barcode.Barcode.GeoPoint;
import com.google.android.gms.vision.barcode.Barcode.Phone;
import com.google.android.gms.vision.barcode.Barcode.Sms;
import com.google.android.gms.vision.barcode.Barcode.UrlBookmark;
import com.google.android.gms.vision.barcode.Barcode.WiFi;

public final class zzb implements Creator<Barcode> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        int i2 = 0;
        Point[] pointArr = null;
        Email email = null;
        Phone phone = null;
        Sms sms = null;
        WiFi wiFi = null;
        UrlBookmark urlBookmark = null;
        GeoPoint geoPoint = null;
        CalendarEvent calendarEvent = null;
        ContactInfo contactInfo = null;
        DriverLicense driverLicense = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 3:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 6:
                    pointArr = (Point[]) SafeParcelReader.createTypedArray(parcel, readHeader, Point.CREATOR);
                    break;
                case 7:
                    email = (Email) SafeParcelReader.createParcelable(parcel, readHeader, Email.CREATOR);
                    break;
                case 8:
                    phone = (Phone) SafeParcelReader.createParcelable(parcel, readHeader, Phone.CREATOR);
                    break;
                case 9:
                    sms = (Sms) SafeParcelReader.createParcelable(parcel, readHeader, Sms.CREATOR);
                    break;
                case 10:
                    wiFi = (WiFi) SafeParcelReader.createParcelable(parcel, readHeader, WiFi.CREATOR);
                    break;
                case 11:
                    urlBookmark = (UrlBookmark) SafeParcelReader.createParcelable(parcel, readHeader, UrlBookmark.CREATOR);
                    break;
                case 12:
                    geoPoint = (GeoPoint) SafeParcelReader.createParcelable(parcel, readHeader, GeoPoint.CREATOR);
                    break;
                case 13:
                    calendarEvent = (CalendarEvent) SafeParcelReader.createParcelable(parcel, readHeader, CalendarEvent.CREATOR);
                    break;
                case 14:
                    contactInfo = (ContactInfo) SafeParcelReader.createParcelable(parcel, readHeader, ContactInfo.CREATOR);
                    break;
                case 15:
                    driverLicense = (DriverLicense) SafeParcelReader.createParcelable(parcel, readHeader, DriverLicense.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new Barcode(i, str, str2, i2, pointArr, email, phone, sms, wiFi, urlBookmark, geoPoint, calendarEvent, contactInfo, driverLicense);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode[i];
    }
}
