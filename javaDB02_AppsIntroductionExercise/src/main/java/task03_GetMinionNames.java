import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class task03_GetMinionNames {

	private static final String GET_MINION_NAMES =
			"SELECT v.name, m.name, m.age " +
					"FROM villains AS v " +
					"    JOIN minions_villains AS mv ON mv.villain_id = v.id " +
					"    JOIN minions m ON m.id = mv.minion_id " +
					"WHERE v.id = ?";

	public static void main(String[] args) throws SQLException, IOException {
		final Connection connection = Utils.getConnection();
		PreparedStatement statement = connection.prepareStatement(GET_MINION_NAMES);

		int villainId = Utils.readIntFromConsole("Enter villain id: ");
		statement.setInt(1, villainId);
		ResultSet resultSet = statement.executeQuery();
		printMinionData(resultSet, villainId);

		connection.close();
	}

	private static void printMinionData(ResultSet resultSet, int villainId) throws SQLException {
		if (!resultSet.next()) {
			System.out.printf("No villain with ID %d exists in the database.%n", villainId);
		} else {
			String villainName = resultSet.getString(Constants.COLUMN_NAME);
			System.out.printf("Villain: %s%n", villainName);
			int counter = 1;
			do {
				String minionName = resultSet.getString(2);
				int minionAge = resultSet.getInt(3);
				System.out.printf("%d. %s %d%n", counter++, minionName, minionAge);
			} while (resultSet.next());
		}
	}

}