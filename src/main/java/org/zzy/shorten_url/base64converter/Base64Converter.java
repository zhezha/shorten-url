package org.zzy.shorten_url.base64converter;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import java.util.Base64;

/**
 * Created by zhengyang on 02/06/17.
 */
public class Base64Converter {

    public static String longToBase64String(long in) {
        byte[] bytes = Longs.toByteArray(in);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        // remove the consecutive characters of "A" from the beginning
        return new String(encodedBytes).replaceFirst("^A+", "");
    }

    public static long base64StringToLong(String in) {
        // pad characters of "A" from start to make the string length to 12.
        String padded = Strings.padStart(in, 12, 'A');
        byte[] encodedBytes = padded.getBytes();
        byte[] bytes = Base64.getDecoder().decode(encodedBytes);
        return Longs.fromByteArray(bytes);
    }
}
