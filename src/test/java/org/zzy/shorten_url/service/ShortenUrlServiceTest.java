package org.zzy.shorten_url.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhengyang on 27/02/17.
 */
public class ShortenUrlServiceTest {

    private ShortenUrlService service = new ShortenUrlService();

    @Test
    public void longToBase64ConvertingTest() {
        long original = (long) Integer.MAX_VALUE + 1000L;
        String encodedString = service.longToBase64String(original);
        long reverted = service.base64StringToLong(encodedString);
        assertEquals(original, reverted);
    }
}