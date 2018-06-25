package com.stripe.android.model;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import com.stripe.android.util.DateUtils;
import com.stripe.android.util.StripeTextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Card {
    public static final String AMERICAN_EXPRESS = "American Express";
    public static final String DINERS_CLUB = "Diners Club";
    public static final String DISCOVER = "Discover";
    public static final String FUNDING_CREDIT = "credit";
    public static final String FUNDING_DEBIT = "debit";
    public static final String FUNDING_PREPAID = "prepaid";
    public static final String FUNDING_UNKNOWN = "unknown";
    public static final String JCB = "JCB";
    public static final String MASTERCARD = "MasterCard";
    public static final int MAX_LENGTH_AMERICAN_EXPRESS = 15;
    public static final int MAX_LENGTH_DINERS_CLUB = 14;
    public static final int MAX_LENGTH_STANDARD = 16;
    public static final String[] PREFIXES_AMERICAN_EXPRESS = new String[]{"34", "37"};
    public static final String[] PREFIXES_DINERS_CLUB = new String[]{"300", "301", "302", "303", "304", "305", "309", "36", "38", "39"};
    public static final String[] PREFIXES_DISCOVER = new String[]{"60", "62", "64", "65"};
    public static final String[] PREFIXES_JCB = new String[]{"35"};
    public static final String[] PREFIXES_MASTERCARD = new String[]{"2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226", "227", "228", "229", "23", "24", "25", "26", "270", "271", "2720", "50", "51", "52", "53", "54", "55"};
    public static final String[] PREFIXES_VISA = new String[]{"4"};
    public static final String UNKNOWN = "Unknown";
    public static final String VISA = "Visa";
    private String addressCity;
    private String addressCountry;
    private String addressLine1;
    private String addressLine2;
    private String addressState;
    private String addressZip;
    private String brand;
    private String country;
    private String currency;
    private String cvc;
    private Integer expMonth;
    private Integer expYear;
    private String fingerprint;
    private String funding;
    @Size(4)
    private String last4;
    private String name;
    private String number;

    public static class Builder {
        private String addressCity;
        private String addressCountry;
        private String addressLine1;
        private String addressLine2;
        private String addressState;
        private String addressZip;
        private String brand;
        private String country;
        private String currency;
        private final String cvc;
        private final Integer expMonth;
        private final Integer expYear;
        private String fingerprint;
        private String funding;
        @Size(4)
        private String last4;
        private String name;
        private final String number;

        public Builder(String number, @IntRange(from = 1, to = 12) Integer expMonth, @IntRange(from = 0) Integer expYear, String cvc) {
            this.number = number;
            this.expMonth = expMonth;
            this.expYear = expYear;
            this.cvc = cvc;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addressLine1(String address) {
            this.addressLine1 = address;
            return this;
        }

        public Builder addressLine2(String address) {
            this.addressLine2 = address;
            return this;
        }

        public Builder addressCity(String city) {
            this.addressCity = city;
            return this;
        }

        public Builder addressState(String state) {
            this.addressState = state;
            return this;
        }

        public Builder addressZip(String zip) {
            this.addressZip = zip;
            return this;
        }

        public Builder addressCountry(String country) {
            this.addressCountry = country;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder fingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
            return this;
        }

        public Builder funding(String funding) {
            this.funding = funding;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder last4(String last4) {
            this.last4 = last4;
            return this;
        }

        public Card build() {
            return new Card();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CardBrand {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FundingType {
    }

    public Card(String number, Integer expMonth, Integer expYear, String cvc, String name, String addressLine1, String addressLine2, String addressCity, String addressState, String addressZip, String addressCountry, String brand, @Size(4) String last4, String fingerprint, String funding, String country, String currency) {
        this.number = StripeTextUtils.nullIfBlank(normalizeCardNumber(number));
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = StripeTextUtils.nullIfBlank(cvc);
        this.name = StripeTextUtils.nullIfBlank(name);
        this.addressLine1 = StripeTextUtils.nullIfBlank(addressLine1);
        this.addressLine2 = StripeTextUtils.nullIfBlank(addressLine2);
        this.addressCity = StripeTextUtils.nullIfBlank(addressCity);
        this.addressState = StripeTextUtils.nullIfBlank(addressState);
        this.addressZip = StripeTextUtils.nullIfBlank(addressZip);
        this.addressCountry = StripeTextUtils.nullIfBlank(addressCountry);
        if (StripeTextUtils.asCardBrand(brand) == null) {
            brand = getBrand();
        }
        this.brand = brand;
        if (StripeTextUtils.nullIfBlank(last4) == null) {
            last4 = getLast4();
        }
        this.last4 = last4;
        this.fingerprint = StripeTextUtils.nullIfBlank(fingerprint);
        this.funding = StripeTextUtils.asFundingType(funding);
        this.country = StripeTextUtils.nullIfBlank(country);
        this.currency = StripeTextUtils.nullIfBlank(currency);
    }

    public Card(String number, Integer expMonth, Integer expYear, String cvc, String name, String addressLine1, String addressLine2, String addressCity, String addressState, String addressZip, String addressCountry, String currency) {
        this(number, expMonth, expYear, cvc, name, addressLine1, addressLine2, addressCity, addressState, addressZip, addressCountry, null, null, null, null, null, currency);
    }

    public Card(String number, Integer expMonth, Integer expYear, String cvc) {
        this(number, expMonth, expYear, cvc, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public boolean validateCard() {
        if (this.cvc == null) {
            if (validateNumber() && validateExpiryDate()) {
                return true;
            }
            return false;
        } else if (validateNumber() && validateExpiryDate() && validateCVC()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateNumber() {
        if (StripeTextUtils.isBlank(this.number)) {
            return false;
        }
        String rawNumber = this.number.trim().replaceAll("\\s+|-", "");
        if (StripeTextUtils.isBlank(rawNumber) || !StripeTextUtils.isWholePositiveNumber(rawNumber) || !isValidLuhnNumber(rawNumber)) {
            return false;
        }
        String updatedType = getBrand();
        if (AMERICAN_EXPRESS.equals(updatedType)) {
            if (rawNumber.length() != 15) {
                return false;
            }
            return true;
        } else if (DINERS_CLUB.equals(updatedType)) {
            if (rawNumber.length() != 14) {
                return false;
            }
            return true;
        } else if (rawNumber.length() != 16) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateExpiryDate() {
        if (validateExpMonth() && validateExpYear() && !DateUtils.hasMonthPassed(this.expYear.intValue(), this.expMonth.intValue())) {
            return true;
        }
        return false;
    }

    public boolean validateCVC() {
        boolean z = true;
        if (StripeTextUtils.isBlank(this.cvc)) {
            return false;
        }
        String cvcValue = this.cvc.trim();
        String updatedType = getBrand();
        boolean validLength;
        if ((updatedType != null || cvcValue.length() < 3 || cvcValue.length() > 4) && !((AMERICAN_EXPRESS.equals(updatedType) && cvcValue.length() == 4) || cvcValue.length() == 3)) {
            validLength = false;
        } else {
            validLength = true;
        }
        if (!(StripeTextUtils.isWholePositiveNumber(cvcValue) && validLength)) {
            z = false;
        }
        return z;
    }

    public boolean validateExpMonth() {
        return this.expMonth != null && this.expMonth.intValue() >= 1 && this.expMonth.intValue() <= 12;
    }

    public boolean validateExpYear() {
        return (this.expYear == null || DateUtils.hasYearPassed(this.expYear.intValue())) ? false : true;
    }

    public String getNumber() {
        return this.number;
    }

    @Deprecated
    public void setNumber(String number) {
        this.number = number;
        this.brand = null;
        this.last4 = null;
    }

    public String getCVC() {
        return this.cvc;
    }

    @Deprecated
    public void setCVC(String cvc) {
        this.cvc = cvc;
    }

    @Nullable
    @IntRange(from = 1, to = 12)
    public Integer getExpMonth() {
        return this.expMonth;
    }

    @Deprecated
    public void setExpMonth(@Nullable @IntRange(from = 1, to = 12) Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return this.expYear;
    }

    @Deprecated
    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    @Deprecated
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    @Deprecated
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressCity() {
        return this.addressCity;
    }

    @Deprecated
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZip() {
        return this.addressZip;
    }

    @Deprecated
    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressState() {
        return this.addressState;
    }

    @Deprecated
    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressCountry() {
        return this.addressCountry;
    }

    @Deprecated
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getCurrency() {
        return this.currency;
    }

    @Deprecated
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLast4() {
        if (!StripeTextUtils.isBlank(this.last4)) {
            return this.last4;
        }
        if (this.number == null || this.number.length() <= 4) {
            return null;
        }
        this.last4 = this.number.substring(this.number.length() - 4, this.number.length());
        return this.last4;
    }

    @Deprecated
    public String getType() {
        return getBrand();
    }

    public String getBrand() {
        if (StripeTextUtils.isBlank(this.brand) && !StripeTextUtils.isBlank(this.number)) {
            String evaluatedType;
            if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_AMERICAN_EXPRESS)) {
                evaluatedType = AMERICAN_EXPRESS;
            } else if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_DISCOVER)) {
                evaluatedType = DISCOVER;
            } else if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_JCB)) {
                evaluatedType = JCB;
            } else if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_DINERS_CLUB)) {
                evaluatedType = DINERS_CLUB;
            } else if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_VISA)) {
                evaluatedType = VISA;
            } else if (StripeTextUtils.hasAnyPrefix(this.number, PREFIXES_MASTERCARD)) {
                evaluatedType = MASTERCARD;
            } else {
                evaluatedType = UNKNOWN;
            }
            this.brand = evaluatedType;
        }
        return this.brand;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    @Nullable
    public String getFunding() {
        return this.funding;
    }

    public String getCountry() {
        return this.country;
    }

    private Card(Builder builder) {
        String last4;
        this.number = StripeTextUtils.nullIfBlank(normalizeCardNumber(builder.number));
        this.expMonth = builder.expMonth;
        this.expYear = builder.expYear;
        this.cvc = StripeTextUtils.nullIfBlank(builder.cvc);
        this.name = StripeTextUtils.nullIfBlank(builder.name);
        this.addressLine1 = StripeTextUtils.nullIfBlank(builder.addressLine1);
        this.addressLine2 = StripeTextUtils.nullIfBlank(builder.addressLine2);
        this.addressCity = StripeTextUtils.nullIfBlank(builder.addressCity);
        this.addressState = StripeTextUtils.nullIfBlank(builder.addressState);
        this.addressZip = StripeTextUtils.nullIfBlank(builder.addressZip);
        this.addressCountry = StripeTextUtils.nullIfBlank(builder.addressCountry);
        if (StripeTextUtils.nullIfBlank(builder.last4) == null) {
            last4 = getLast4();
        } else {
            last4 = builder.last4;
        }
        this.last4 = last4;
        if (StripeTextUtils.asCardBrand(builder.brand) == null) {
            last4 = getBrand();
        } else {
            last4 = builder.brand;
        }
        this.brand = last4;
        this.fingerprint = StripeTextUtils.nullIfBlank(builder.fingerprint);
        this.funding = StripeTextUtils.asFundingType(builder.funding);
        this.country = StripeTextUtils.nullIfBlank(builder.country);
        this.currency = StripeTextUtils.nullIfBlank(builder.currency);
    }

    private boolean isValidLuhnNumber(String number) {
        boolean z = true;
        boolean isOdd = true;
        int sum = 0;
        for (int index = number.length() - 1; index >= 0; index--) {
            char c = number.charAt(index);
            if (!Character.isDigit(c)) {
                return false;
            }
            int digitInteger = Integer.parseInt("" + c);
            if (isOdd) {
                isOdd = false;
            } else {
                isOdd = true;
            }
            if (isOdd) {
                digitInteger *= 2;
            }
            if (digitInteger > 9) {
                digitInteger -= 9;
            }
            sum += digitInteger;
        }
        if (sum % 10 != 0) {
            z = false;
        }
        return z;
    }

    private String normalizeCardNumber(String number) {
        if (number == null) {
            return null;
        }
        return number.trim().replaceAll("\\s+|-", "");
    }
}
