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


@WebServlet("/ShortenURLServlet")
public class ShortenURLServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private URLDBImpl urldbImpl;
       
    
    public void init() throws ServletException {
    	urldbImpl = new URLDBImpl();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String longURL = request.getParameter("longURL");
		
		if (!isValid(longURL)) {
			response.sendRedirect("InvalidLongURL.jsp");
		}
		else {
			String hash = URLConversion.getMD5Digest(longURL);
			String shortURL = null;
			
			URL url = urldbImpl.getURLByHash(hash);
			if (url != null) {
				long id = url.getId();
				shortURL = URLConversion.idToShortURL(id);
			}
			else {
				url = new URL();
				url.setHash(hash);
				url.setLongURL(longURL);
				long id = urldbImpl.addURL(url);
				shortURL = URLConversion.idToShortURL(id);
			}
			String path = "localhost:8080/ShortenURL/Redirect/";
			shortURL = path + shortURL;
			
			request.setAttribute("longURL", longURL);
			request.setAttribute("shortURL", shortURL);
			request.getRequestDispatcher("ShortenURLView.jsp").forward(request, response);
		}
	}
	
	
	// Check if a url is valid.
	private boolean isValid(String url) {
		
		if (url == null) {
			return false;
		}
		else {
			String urlRegex = "\\b(https?|ftp|file|ldap)://" 
					+ "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
					+ "*[-A-Za-z0-9+&@#/%=~_|]";
			Pattern urlPattern = Pattern.compile(urlRegex);
			Matcher urlMatcher = urlPattern.matcher(url);
			if (!urlMatcher.matches()) {
				return false;
			}
		}
		
		return true;
	}

}
