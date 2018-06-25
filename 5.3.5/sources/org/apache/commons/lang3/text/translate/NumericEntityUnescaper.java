package org.apache.commons.lang3.text.translate;

import android.support.v4.internal.view.SupportMenu;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.EnumSet;

public class NumericEntityUnescaper extends CharSequenceTranslator {
    private final EnumSet<OPTION> options;

    public enum OPTION {
        semiColonRequired,
        semiColonOptional,
        errorIfNoSemiColon
    }

    public NumericEntityUnescaper(OPTION... options) {
        if (options.length > 0) {
            this.options = EnumSet.copyOf(Arrays.asList(options));
            return;
        }
        this.options = EnumSet.copyOf(Arrays.asList(new OPTION[]{OPTION.semiColonRequired}));
    }

    public boolean isSet(OPTION option) {
        return this.options == null ? false : this.options.contains(option);
    }

    public int translate(CharSequence input, int index, Writer out) throws IOException {
        int seqEnd = input.length();
        if (input.charAt(index) != '&' || index >= seqEnd - 2 || input.charAt(index + 1) != '#') {
            return 0;
        }
        int i;
        int start = index + 2;
        boolean isHex = false;
        char firstChar = input.charAt(start);
        if (firstChar == 'x' || firstChar == 'X') {
            start++;
            isHex = true;
            if (start == seqEnd) {
                return 0;
            }
        }
        int end = start;
        while (end < seqEnd && ((input.charAt(end) >= '0' && input.charAt(end) <= '9') || ((input.charAt(end) >= 'a' && input.charAt(end) <= 'f') || (input.charAt(end) >= 'A' && input.charAt(end) <= 'F')))) {
            end++;
        }
        boolean semiNext = end != seqEnd && input.charAt(end) == ';';
        if (!semiNext) {
            if (isSet(OPTION.semiColonRequired)) {
                return 0;
            }
            if (isSet(OPTION.errorIfNoSemiColon)) {
                throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
            }
        }
        if (isHex) {
            try {
                int entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
        if (entityValue > SupportMenu.USER_MASK) {
            char[] chrs = Character.toChars(entityValue);
            out.write(chrs[0]);
            out.write(chrs[1]);
        } else {
            out.write(entityValue);
        }
        int i2 = (end - start) + 2;
        if (isHex) {
            i = 1;
        } else {
            i = 0;
        }
        i2 += i;
        if (semiNext) {
            i = 1;
        } else {
            i = 0;
        }
        return i + i2;
    }
}
