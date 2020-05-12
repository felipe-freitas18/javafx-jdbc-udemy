package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	// Objeto de conexão com o banco de dados JDBC
	private static Connection conn = null;
	
	public static Connection getConnection() {
		//Verifica se ja não existe uma conexão em aberto
		if(conn == null) {
			try{
				Properties props = loadProperties();
				String url = props.getProperty("dburl");// url do banco definido em db.properties
				conn = DriverManager.getConnection(url, props);//Drive de conexão do banco
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		//verifica se a conexão esta aberta e fecha ela
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}	
	}
	
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	/*
	 * Metodo auxiliar para fechar um Statement, 
	 * Evitar o uso repetitivo de Try-Catch na classe principal
	 */
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	/*
	 * Metodo auxiliar para fechar um ResultSet, 
	 * Evitar o uso repetitivo de Try-Catch na classe principal
	 */
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
