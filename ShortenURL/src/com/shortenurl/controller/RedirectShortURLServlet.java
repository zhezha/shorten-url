package com.shortenurl.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shortenurl.model.URL;
import com.shortenurl.model.URLConversion;
import com.shortenurl.model.URLDBImpl;

@WebServlet("/Redirect/*")
public class RedirectShortURLServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private URLDBImpl urldbImpl;

	
	public void init() throws ServletException {
		urldbImpl = new URLDBImpl();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// With the request URL like "http://localhost:8080/ShortenURL/Redirect/abcd",
		// get the String of "abcd".
		String shortURL = request.getRequestURI().replace("/ShortenURL/Redirect/", "");

		if (!isValid(shortURL)) {
			response.sendRedirect("/ShortenURL/InvalidShortURL.jsp");
		} else {
			long id = URLConversion.shortToID(shortURL);
			URL url = urldbImpl.getURLByID(id);
			if (url == null) {
				response.sendRedirect("/ShortenURL/InvalidShortURL.jsp");
			} else {
				String longURL = urldbImpl.getURLByID(id).getLongURL();
				response.sendRedirect(longURL);
			}
		}
	}

	
	// Check if a shortURL is valid.
	private boolean isValid(String shortURL) {

		if (shortURL == null) {
			return false;
		} else {
			String urlRegex = "^[a-zA-Z0-9]+$";
			Pattern urlPattern = Pattern.compile(urlRegex);
			Matcher urlMatcher = urlPattern.matcher(shortURL);
			if (!urlMatcher.matches()) {
				return false;
			}
		}

		return true;
	}

}
