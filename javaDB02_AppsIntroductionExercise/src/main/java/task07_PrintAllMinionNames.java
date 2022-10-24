import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class task07_PrintAllMinionNames {

	/*********************************
	 You have to upload a fresh copy of the minions_db
	 for this task to work as described in the exercise doc
	 ************************************/
	public static final String SELECT_NAME_FROM_MINIONS = "SELECT name FROM minions";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();

		PreparedStatement statement = connection.prepareStatement(SELECT_NAME_FROM_MINIONS);
		ArrayDeque<String> minionsNames = new ArrayDeque<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			minionsNames.offer(resultSet.getString(1));
		}

		int counter = 0;
		while (!minionsNames.isEmpty()) {
			System.out.println(counter++ % 2 == 0 ? minionsNames.poll() : minionsNames.pollLast());
		}

		connection.close();
	}
}