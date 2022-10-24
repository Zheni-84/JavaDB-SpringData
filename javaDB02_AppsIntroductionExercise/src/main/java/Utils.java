import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

class Utils {

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static final String SELECT_ID_BY_NAME = "SELECT id FROM %s WHERE name = ?";
	public static final String SELECT_NAME_BY_ID = "SELECT name FROM %s WHERE id = ?";

	static Connection getConnection() throws SQLException {
		final Properties property = new Properties();
		property.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
		property.setProperty(Constants.PASSWORD_KEY, Constants.PASWORD_VALUE);

		return DriverManager.getConnection(Constants.DJBC_URL, property);
	}

	public static int readIntFromConsole(String message) throws IOException {
		return Integer.parseInt(readStringFromConsole(message));
	}

	public static String readStringFromConsole(String message) throws IOException {
		System.out.print(message);
		return reader.readLine();
	}

	public static int findEntityIdByName(String entityName, String name, Connection connection) throws SQLException {
		String query = String.format(SELECT_ID_BY_NAME, entityName);
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		int resultId = 0;
		if (resultSet.next()) {
			resultId = resultSet.getInt(1);
		}

		return resultId;
	}

	public static String findEntityNameById(String entityName, int id, Connection connection) throws SQLException {
		String query = String.format(SELECT_NAME_BY_ID, entityName);
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		String name = null;
		if (resultSet.next()) {
			name = resultSet.getString(1);
		}

		return name;
	}
}