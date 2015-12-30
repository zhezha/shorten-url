package com.shortenurl.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class URLDBImpl implements URLDBInterface {

	// Get URL for a given hash.
	// If the URL is not in database, return null.
	public URL getURLByHash(String hash) {
		
		URL url = new URL();
		Connection conn = SingletonConnection.getConnection();
		try {
			String sql = "select * from url where hash_value = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, hash);
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			
			while (rs.next()) {
				url.setId(rs.getLong("id"));
				url.setHash(rs.getString("hash_value"));
				url.setLongURL(rs.getString("long_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}


	// Get URL for a given id.
	// If the URL is not in database, return null.
	public URL getURLByID(long id) {
		
		URL url = new URL();
		Connection conn = SingletonConnection.getConnection();
		try {
			String sql = "select * from url where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return null;
			}
			
			while (rs.next()) {				
				url.setId(rs.getLong("id"));
				url.setHash(rs.getString("hash_value"));
				url.setLongURL(rs.getString("long_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return url;
	}


	// Add a URL to database and return id of the added item.
	// If fail to add URL, return -1.
	public long addURL(URL url) {
		
		long id = -1;
		Connection conn = SingletonConnection.getConnection();
		String sql = "insert into url (hash_value, long_url) values (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, url.getHash());
			ps.setString(2, url.getLongURL());
			int affectedRow = ps.executeUpdate();
			if (affectedRow == 0) {
				System.out.println("Insertion failed!");
			}
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			else {
				System.out.println("Insertion failed! No ID retrieved.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}

}
