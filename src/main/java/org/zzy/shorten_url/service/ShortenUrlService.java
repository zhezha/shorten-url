package org.zzy.shorten_url.service;

import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzy.shorten_url.domain.UrlRecord;
import org.zzy.shorten_url.domain.UrlRecordRepository;

import java.util.Base64;

@Service
public class ShortenUrlService {

    @Autowired
    private UrlRecordRepository repository;

    public String toShortUrl(String longUrl) {
        if (!longUrl.contains("http")
                && !longUrl.contains("https")) {
            longUrl = "http://" + longUrl;
        }
        // Get id from database insertion.
        long id = repository.save(new UrlRecord(longUrl)).getId();
        return longToBase64String(id);
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
