package com.shortenurl.model;

public interface URLDBInterface {
	
	public URL getURLByHash(String hash);
	public URL getURLByID(long id);
	public long addURL(URL url);

}
