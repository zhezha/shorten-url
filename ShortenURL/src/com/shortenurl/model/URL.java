package com.shortenurl.model;

import java.io.Serializable;

public class URL implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String hash;
	private String longURL;
	
	
	public URL() {
		super();
	}

	public URL(String hash, String longURL) {
		super();
		this.hash = hash;
		this.longURL = longURL;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getLongURL() {
		return longURL;
	}
	
	public void setLongURL(String longURL) {
		this.longURL = longURL;
	}
}
