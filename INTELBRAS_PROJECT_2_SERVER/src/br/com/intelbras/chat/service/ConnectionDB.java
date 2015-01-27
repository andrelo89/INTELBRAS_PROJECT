package br.com.intelbras.chat.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

	private static Statement stm;

	public static Statement getInstance() {
		try {
			if (stm == null) {
				Connection con = null;
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(
						"jdbc:postgresql://localhost:5432/chat", "postgres",
						"admin");
				return stm = con.createStatement();
			} else {
				return stm;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return stm;
	}

}
