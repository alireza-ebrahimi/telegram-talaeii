package com.google.android.gms.vision.barcode;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.vision.barcode.Barcode.CalendarEvent;
import com.google.android.gms.vision.barcode.Barcode.ContactInfo;
import com.google.android.gms.vision.barcode.Barcode.DriverLicense;
import com.google.android.gms.vision.barcode.Barcode.Email;
import com.google.android.gms.vision.barcode.Barcode.GeoPoint;
import com.google.android.gms.vision.barcode.Barcode.Phone;
import com.google.android.gms.vision.barcode.Barcode.Sms;
import com.google.android.gms.vision.barcode.Barcode.UrlBookmark;
import com.google.android.gms.vision.barcode.Barcode.WiFi;

@Hide
public final class zzb implements Creator<Barcode> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
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
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 6:
                    pointArr = (Point[]) zzbgm.zzb(parcel, readInt, Point.CREATOR);
                    break;
                case 7:
                    email = (Email) zzbgm.zza(parcel, readInt, Email.CREATOR);
                    break;
                case 8:
                    phone = (Phone) zzbgm.zza(parcel, readInt, Phone.CREATOR);
                    break;
                case 9:
                    sms = (Sms) zzbgm.zza(parcel, readInt, Sms.CREATOR);
                    break;
                case 10:
                    wiFi = (WiFi) zzbgm.zza(parcel, readInt, WiFi.CREATOR);
                    break;
                case 11:
                    urlBookmark = (UrlBookmark) zzbgm.zza(parcel, readInt, UrlBookmark.CREATOR);
                    break;
                case 12:
                    geoPoint = (GeoPoint) zzbgm.zza(parcel, readInt, GeoPoint.CREATOR);
                    break;
                case 13:
                    calendarEvent = (CalendarEvent) zzbgm.zza(parcel, readInt, CalendarEvent.CREATOR);
                    break;
                case 14:
                    contactInfo = (ContactInfo) zzbgm.zza(parcel, readInt, ContactInfo.CREATOR);
                    break;
                case 15:
                    driverLicense = (DriverLicense) zzbgm.zza(parcel, readInt, DriverLicense.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new Barcode(i, str, str2, i2, pointArr, email, phone, sms, wiFi, urlBookmark, geoPoint, calendarEvent, contactInfo, driverLicense);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode[i];
    }
}
