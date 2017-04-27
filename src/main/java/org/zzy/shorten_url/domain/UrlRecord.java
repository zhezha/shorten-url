package org.zzy.shorten_url.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class UrlRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String longUrl;

    protected UrlRecord() {}

    public UrlRecord(String longUrl) {
        this.longUrl = longUrl;
    }

    public Long getId() {
        return this.id;
    }

    public String getLongUrl() {
        return this.longUrl;
    }

    public String toString() {
        return String.format(
                "UrlRecord[id=%d, longUrl=%s]",
                id, longUrl
        );
    }
}
