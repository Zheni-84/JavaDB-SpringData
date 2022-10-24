import java.sql.*;
import java.util.Scanner;

public class task09_IncreaseAgeStoredProcedure {

	/**
	 * 9. Increase Age Stored Procedure
	 * <p>
	 * You have to run SQL script to create the stored procedure before running code!!!
	 * <p>
	 * DROP procedure IF EXISTS `minions_db`.`usp_get_older`;
	 * DELIMITER $$
	 * USE `minions_db`$$
	 * CREATE PROCEDURE `usp_get_older`(minion_id INT)
	 * BEGIN
	 * UPDATE minions SET age = age + 1 WHERE id = minion_id;
	 * END$$
	 */
	public static final String CALL_USP_GET_OLDER = "CALL usp_get_older(?)";
	public static final String SELECT_MINIONS = "SELECT name, age FROM minions WHERE id = ?";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();

		int minionId = Integer.parseInt(new Scanner(System.in).nextLine());
		CallableStatement statement = connection.prepareCall(CALL_USP_GET_OLDER);
		statement.setInt(1, minionId);
		statement.executeUpdate();

		PreparedStatement selectMinionStatement = connection.prepareStatement(SELECT_MINIONS);
		selectMinionStatement.setInt(1, minionId);
		ResultSet selectedMinionSet = selectMinionStatement.executeQuery();
		selectedMinionSet.next();
		String minionName = selectedMinionSet.getString(Constants.COLUMN_NAME);
		int minionAge = selectedMinionSet.getInt(2);

		System.out.printf("%s %d", minionName, minionAge);

		connection.close();
	}
}