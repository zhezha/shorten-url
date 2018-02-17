package org.zzy.shorten_url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zzy.shorten_url.service.ShortenUrlService;

@Controller
public class WebController {
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ShortenUrlService shortenUrlService;

    @RequestMapping(value = "/generateShortUrl", method = RequestMethod.POST)
    public @ResponseBody String generateShortUrl(@RequestParam("longURL") String url) {
        log.info("Long URL: {}", url);
        String shortUrl = shortenUrlService.toShortUrl(url);
        log.info("Short URL: {}", shortUrl);
        return shortUrl;
    }

    /**
     * Redirect any url in the form of "host:port/{shortUrl}" to the original long URL
     * except for "host:port/"(the welcome page) and "host:port/generateShortUrl".
     * @param shortUrl
     * @return
     */
    @RequestMapping(value = "/{shortUrl:(?!generateShortUrl).+}", method = RequestMethod.GET)
    public String redeirectUrl(@PathVariable String shortUrl) {
        String longUrl = shortenUrlService.toLongUrl(shortUrl);
        return "redirect:" + longUrl;
    }
}
