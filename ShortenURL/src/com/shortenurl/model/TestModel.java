package com.shortenurl.model;

public class TestModel {

	public static void main(String[] args) {
		
		URLDBImpl urlImpl = new URLDBImpl();
		
		String longURL = "http://stackoverflow.com/questions/742013/how-to-code-a-url-shortener";
		String hash = URLConversion.getMD5Digest(longURL);
		
		URL url = urlImpl.getURLByHash(hash);
		if (url != null) {
			long id = url.getId();
			String shortURL = URLConversion.idToShortURL(id);
			System.out.println("ShortURL is: " + shortURL);
		}
		else {
			url = new URL();
			url.setHash(hash);
			url.setLongURL(longURL);
			long id = urlImpl.addURL(url);
			String shortURL = URLConversion.idToShortURL(id);
			System.out.println("Short URL is: " + shortURL);
		}
		
//		String shortURL = "C";
//		long id = URLConversion.shortToID(shortURL);
//		URL url = urlImpl.getURLByID(id);
//		System.out.println("Long URL is: " + url.getLongURL());
	}

}
