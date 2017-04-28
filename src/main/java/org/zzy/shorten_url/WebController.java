package org.zzy.shorten_url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zzy.shorten_url.service.ShortenUrlService;

import java.net.InetAddress;

@Controller
public class WebController implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    private int port;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @RequestMapping(value = "/generateShortUrl", method = RequestMethod.POST)
    public @ResponseBody String generateShortUrl(@RequestParam("longURL") String url) {
        log.debug("Long URL: {}", url);
        String shortUrl = shortenUrlService.toShortUrl(url);
        log.debug("Short URL: {}", shortUrl);
        // Get the hostname of local machine
        String host = InetAddress.getLoopbackAddress().getHostAddress();
        return String.format(
                "Shorten URL: %s:%s/%s",
                host, port, shortUrl
        );
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

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        port = event.getEmbeddedServletContainer().getPort();
    }
}
