import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class task08_IncreaseMinionsAge {

	public static final String UPDATE_MINIONS = "UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id IN(%s)";
	public static final String SELECT_MINIONS = "SELECT name, age FROM minions";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();

		PreparedStatement statement = null;
		try {
			// Make sure there will be no SQL injection
			String ids = Arrays.stream(new Scanner(System.in).nextLine().split("\\s+"))
					.map(Integer::parseInt)
					.map(Object::toString)
					.collect(Collectors.joining(", "));
			// There is no possibilities for SQL injection because the result is prepared to get only
			// array of integers
			String query = String.format(UPDATE_MINIONS, ids);
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			statement.close();

			statement = connection.prepareStatement(SELECT_MINIONS);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.printf("%s %d%n", resultSet.getString(Constants.COLUMN_NAME), resultSet.getInt(2));
			}
		} catch (NumberFormatException pe) {
			System.out.println("Invalid input");
			System.out.println(pe.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		connection.close();
	}
}