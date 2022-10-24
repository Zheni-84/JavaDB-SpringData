import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task05_ChangeTownNamesCasing {

	public static final String UPDATE_TOWNS = "UPDATE towns SET name = UPPER(name) WHERE country = ?";
	public static final String SELECT_NAME_FROM_TOWNS = "SELECT name FROM towns WHERE country = ?";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();

		String countryName = new Scanner(System.in).nextLine();

		int updatedTownsCount = updateTownsIntoDB(countryName, connection);
		if (updatedTownsCount > 0) {
			System.out.printf("%d town names were affected.%n", updatedTownsCount);
			List<String> affectedTownsNames = getUpdatedTownNames(countryName, connection);
			System.out.println(affectedTownsNames);
		} else {
			System.out.println("No town names were affected.");
		}

		connection.close();
	}

	private static int updateTownsIntoDB(String countryName, Connection connection) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_TOWNS)) {
			statement.setString(1, countryName);
			return statement.executeUpdate();
		}
	}

	private static List<String> getUpdatedTownNames(String countryName, Connection connection) throws SQLException {
		List<String> townsNames;
		ResultSet resultSet;
		try (PreparedStatement statement = connection.prepareStatement(SELECT_NAME_FROM_TOWNS)) {
			statement.setString(1, countryName);
			townsNames = new ArrayList<>();
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				townsNames.add(resultSet.getString(1));
			}

			return townsNames;
		}
	}
}