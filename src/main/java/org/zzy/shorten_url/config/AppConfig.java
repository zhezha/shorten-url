package org.zzy.shorten_url.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class AppConfig {
    private String hostName = InetAddress.getLoopbackAddress().getHostAddress();
    @Value("${server.port}")
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }
}
