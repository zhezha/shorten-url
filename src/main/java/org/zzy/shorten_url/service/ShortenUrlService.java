package org.zzy.shorten_url.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzy.shorten_url.base64converter.Base64Converter;
import org.zzy.shorten_url.domain.UrlRecord;
import org.zzy.shorten_url.domain.UrlRecordRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ShortenUrlService {
    private static final Logger log = LoggerFactory.getLogger(ShortenUrlService.class);
    private List<String> urlSchemes = Arrays.asList("http:", "ftp:", "mailto:", "file:", "data:", "irc:");
    private final static String DEFAULT_SCHEME = "http://";

    @Autowired
    private UrlRecordRepository repository;

    public String toShortUrl(String longUrl) {
        if (!hasScheme(longUrl)) {
            longUrl = DEFAULT_SCHEME + longUrl;
        }
        // Get id from database insertion.
        long id = repository.save(new UrlRecord(longUrl)).getId();
        String shortUrl = Base64Converter.longToBase64String(id);
        log.info("long url: {} to short url: {}", longUrl, shortUrl);
        return shortUrl;
    }

    private boolean hasScheme(String url) {
        return urlSchemes.stream().anyMatch(it -> url.toLowerCase().startsWith(it));
    }

    public String toLongUrl(String shortUrl) {
        long id = Base64Converter.base64StringToLong(shortUrl);
        return repository.findOne(id).getLongUrl();
    }
}
