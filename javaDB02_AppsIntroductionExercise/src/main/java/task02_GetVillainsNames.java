import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class task02_GetVillainsNames {

	private static final String GET_VILLAINS_NAMES = "SELECT v.`name`, COUNT(DISTINCT mv.minion_id) AS minions_count " +
			"FROM villains as v " +
			"         JOIN minions_villains as mv on v.id = mv.villain_id " +
			"GROUP BY mv.villain_id " +
			"HAVING minions_count > ? " +
			"ORDER BY minions_count DESC;";
	private static final String COLUMN_MINIONS_COUNT = "minions_count";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();
		final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);
		statement.setInt(1, 15);
		final ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String villainName = resultSet.getString(Constants.COLUMN_NAME);
			int minionsCount = resultSet.getInt(COLUMN_MINIONS_COUNT);

			System.out.printf("%s %d", villainName, minionsCount);
		}

		connection.close();
	}
}