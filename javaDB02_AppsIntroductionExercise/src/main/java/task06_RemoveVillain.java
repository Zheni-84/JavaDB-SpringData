import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class task06_RemoveVillain {

	public static final String DELETE_FROM_VILLAINS = "DELETE FROM villains WHERE id = ?";
	public static final String DELETE_FROM_MINIONS_VILLAINS = "DELETE FROM minions_villains WHERE villain_id = ?";

	public static void main(String[] args) throws SQLException {
		final Connection connection = Utils.getConnection();

		int villainId = Integer.parseInt(new Scanner(System.in).nextLine());
		String villainName = Utils.findEntityNameById("villains", villainId, connection);
		connection.setAutoCommit(false);
		if (villainName != null) {
			int releasedMinionsCount = freeThePoorMinionsFrom(villainId, connection);
			if (deleteVillain(villainId, connection)) {
				System.out.printf("%s was deleted%n", villainName);
				System.out.printf("%d minions released%n", releasedMinionsCount);
				connection.commit();
			}
		} else {
			System.out.println("No such villain was found");
		}

		connection.close();
	}

	private static int freeThePoorMinionsFrom(int id, Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETE_FROM_MINIONS_VILLAINS);
		statement.setInt(1, id);
		return statement.executeUpdate();
	}

	private static boolean deleteVillain(int id, Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETE_FROM_VILLAINS);
		statement.setInt(1, id);
		return statement.executeUpdate() > 0;
	}
}