package org.zzy.shorten_url.service;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzy.shorten_url.domain.UrlRecord;
import org.zzy.shorten_url.domain.UrlRecordRepository;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class ShortenUrlService {
    private static final Logger log = LoggerFactory.getLogger(ShortenUrlService.class);
    private List<String> urlSchemes = Arrays.asList("http", "ftp", "mailto", "file", "data", "irc");
    private final static String DEFAULT_SCHEME = "http://";

    @Autowired
    private UrlRecordRepository repository;

    public String toShortUrl(String longUrl) {
        if (!hasScheme(longUrl)) {
            longUrl = DEFAULT_SCHEME + longUrl;
        }
        // Get id from database insertion.
        long id = repository.save(new UrlRecord(longUrl)).getId();
        return longToBase64String(id);
    }

    private boolean hasScheme(String url) {
        return urlSchemes.stream().anyMatch(it -> url.toLowerCase().contains(it));
    }

    public String longToBase64String(long in) {
        byte[] bytes = Longs.toByteArray(in);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes);
    }

    public String toLongUrl(String shortUrl) {
        long id = base64StringToLong(shortUrl);
        return repository.findOne(id).getLongUrl();
    }

    public long base64StringToLong(String in) {
        byte[] encodedBytes = in.getBytes();
        byte[] bytes = Base64.getDecoder().decode(encodedBytes);
        return Longs.fromByteArray(bytes);
    }
}
