import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class task04_AddMinion {

	public static final String INSERT_INTO_MINIONS_VILLAINS = "INSERT INTO minions_villains(minion_id, villain_id) VALUES (?,?)";
	public static final String INSERT_INTO_VILLAINS = "INSERT INTO villains(name, evilness_factor) VALUES (?, 'evil')";
	public static final String INSERT_INTO_MINIONS = "INSERT INTO minions(name, age, town_id) VALUES (?,?,?)";
	public static final String INSERT_INTO_TOWNS = "INSERT INTO towns(name) VALUES (?)";

	public static void main(String[] args) throws SQLException, IOException {

		final Connection connection = Utils.getConnection();

		String minionName = "";
		int minionAge = 0;
		String town = "";
		String villainName = "";
		for (int i = 0; i < 2; i++) {
			String[] minionInfo = Utils.readStringFromConsole("").split("\\s+");
			if (minionInfo[0].equals("Minion:")) {
				minionName = minionInfo[1];
				minionAge = Integer.parseInt(minionInfo[2]);
				town = minionInfo[3];
			} else {
				villainName = minionInfo[1];
			}
		}
		int townId = insertTownIfNotExist(town, connection);
		int minionId = insertMinionIfNotExist(minionName, minionAge, townId, connection);
		int villainId = insertVillainIfNotExist(villainName, connection);
		if (insertMinionToVillain(minionId, villainId, connection)) {
			System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
		}

		connection.close();
	}

	private static boolean insertMinionToVillain(int minionId, int villainId, Connection connection) throws SQLException {
		try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS)) {
			insertStatement.setInt(1, minionId);
			insertStatement.setInt(2, villainId);
			int insertedRecordsCount = insertStatement.executeUpdate();
			return insertedRecordsCount > 0;
		}
	}

	private static int insertVillainIfNotExist(String villainName, Connection connection) throws SQLException {
		int villainId = Utils.findEntityIdByName("villains", villainName, connection);
		if (villainId == 0) {
			try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_VILLAINS)) {
				statement.setString(1, villainName);
				int insertedRecordsCount = statement.executeUpdate();
				if (insertedRecordsCount > 0) {
					villainId = Utils.findEntityIdByName("villains", villainName, connection);
					System.out.printf("Villain %s was added to the database.%n", villainName);
				}
			}
		}

		return villainId;
	}

	private static int insertMinionIfNotExist(String minionName, int minionAge, int townId, Connection connection) throws SQLException {
		int minionId = Utils.findEntityIdByName("minions", minionName, connection);
		if (minionId == 0) {
			try (PreparedStatement statement = connection
					.prepareStatement(INSERT_INTO_MINIONS)) {
				statement.setString(1, minionName);
				statement.setInt(2, minionAge);
				statement.setInt(3, townId);
				int insertedRecordsCount = statement.executeUpdate();
				if (insertedRecordsCount > 0) {
					minionId = Utils.findEntityIdByName("minions", minionName, connection);
				}
			}
		}

		return minionId;
	}

	private static int insertTownIfNotExist(String townName, Connection connection) throws SQLException {
		int townId = Utils.findEntityIdByName("towns", townName, connection);
		if (townId == 0) {
			try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_TOWNS)) {
				statement.setString(1, townName);
				int insertedRecordsCount = statement.executeUpdate();
				if (insertedRecordsCount > 0) {
					townId = Utils.findEntityIdByName("towns", townName, connection);
					System.out.printf("Town %s was added to the database.%n", townName);
				}
			}
		}

		return townId;
	}
}