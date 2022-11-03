import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Task03ContainsEmployee {

	private static final String SELECT_EMPLOYEES =
			"SELECT COUNT (e) " +
					"FROM Employee e " +
					"WHERE e.firstName = :firstName " +
					"AND e.lastName = :lastName";
	private static final String NO = "No";
	private static final String YES = "Yes";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String[] name = new Scanner(System.in).nextLine().split("\\s+");
		String firstName = name[0];
		String lastName = name[1];
		Long countMatches = entityManager.createQuery(SELECT_EMPLOYEES, Long.class)
				.setParameter(FIRST_NAME, firstName)
				.setParameter(LAST_NAME, lastName)
				.getSingleResult();

		System.out.println(countMatches == 0 ? NO : YES);

		entityManager.close();
	}
}