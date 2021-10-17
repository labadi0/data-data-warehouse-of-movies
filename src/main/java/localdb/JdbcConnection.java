package localdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class JdbcConnection {

	// Local database data
	private static String host = "localhost";
	private static String base = "ied";
	private static String user = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://" + host + "/" + base;
	private static final int COLUMN_NUMBER = 9;

	// Establish connection with the local database.
	public static Connection getConnection() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.err.println("Connection to local database failed : " + e.getMessage());
		}
		return connection;
	}

	// SQL Query to get movie's budget infos.
	public static ArrayList<String> getMovieInfo(String movie_name) throws SQLException {
		String movie_budget_query = "SELECT * FROM moviesbudgets WHERE id_movie =" + movie_name + "";
		Connection connection = getConnection();
		Statement statement = (Statement) connection.createStatement();
		ResultSet rs = statement.executeQuery(movie_budget_query);
		if (rs.next() == false) {
			System.out.println("--------------------------" + movie_name
					+ " does not exist on database -------------------------- \n");
			ArrayList<String> movie_info = new ArrayList<String>();

			for (int i = 1; i < COLUMN_NUMBER; i++) {
				movie_info.add("?");
			}
			return movie_info;

		} else {
			ArrayList<String> movie_info = new ArrayList<String>();

			for (int i = 1; i < COLUMN_NUMBER; i++) {
				movie_info.add(rs.getString(i));
			}
			return movie_info;
		}
	}

	// SQL Query to get movie's budget infos.
	public static ArrayList<String> getID(String movie_name) throws SQLException {
		ArrayList<String> ids = new ArrayList<String>();

		String query = "SELECT * FROM moviesbudgets WHERE Movie_name ='" + movie_name + "'";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = (PreparedStatement) dbConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		if (!rs.next()) {
			System.out.println("--------------------------" + movie_name
					+ " does not exist on database -------------------------- \n");
		}

		else {
			rs.previous();
			while (rs.next()) {
				ids.add(rs.getString("id_movie"));
			}

		}
		return ids;
	}

	public static ArrayList<ArrayList<String>> getMoviesss(String name) throws SQLException {
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		ArrayList<String> ids = getID(name);
		for (int i = 0; i < ids.size(); i++) {
			ArrayList<String> moviInfo = getMovieInfo(ids.get(i));
			array.add(moviInfo);
		}
		return array;
	}

	public static void main(String[] args) throws Exception {
		// ArrayList<String> movie_infos = getMovieBudget("Avatar");
		// ArrayList<String> movie_infos = getMovieInfo("A Nightmare on Elm Street");

		/*
		ArrayList<String> movie_id = getID("A Nightmare on Elm Street(((");
		
		System.out.println(movie_id.size());
		
		for (String string : movie_id) {
			System.out.println(string);
		}
		*/
		

		
		
		
		
		
		
		/*

		ArrayList<ArrayList<String>> tabs = getMoviesss("A Nightmare on Elm Street");
		
		for (int i = 0; i < tabs.size(); i++) {
				for (int j = 0; j < tabs.get(i).size(); j++) {
						System.out.println(tabs.get(i).get(j));
				}
				System.out.println("==================");
		}
		
		*/
		
		


	}
}