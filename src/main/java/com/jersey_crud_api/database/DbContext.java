package com.jersey_crud_api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbContext {
	private static final String driver = "org.postgresql.Driver";
	private static final String username = "postgres";
	private static final String password = "1234";
	private static final String url = "jdbc:postgresql://localhost:5432/apijava8teste";
	
	public static Connection Connect() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("Erro ao conectar com o banco de dados ->" + e.getMessage());
		}
		if (connection == null) {
			System.out.println("Não foi possível conectar ao banco de dados, tente novamente");
		}
		return connection;
	}
	public static void Disconnect(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			System.err.println("Erro ao fechar conexão com o banco de dados"+ e.getMessage());
		}
	}
	public static void ClosePreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Erro ao fechar preparedStatement -> " + e.getMessage());
		}
	}
	public static void CloseResultSet(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (Exception e) {
			System.err.println("Erro ao fechar preparedStatement -> " + e.getMessage());
		}
		
	}
	
}
