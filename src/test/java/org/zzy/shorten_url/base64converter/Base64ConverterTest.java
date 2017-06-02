package org.zzy.shorten_url.base64converter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhengyang on 02/06/17.
 */
public class Base64ConverterTest {

    @Test
    public void longToBase64ConvertingTest() {
        long original = (long) Integer.MAX_VALUE + 1000L;
        String encoded = Base64Converter.longToBase64String(original);
        long decoded = Base64Converter.base64StringToLong(encoded);
        assertEquals(original, decoded);
    }
}