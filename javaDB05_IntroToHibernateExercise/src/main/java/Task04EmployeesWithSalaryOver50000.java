import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Task04EmployeesWithSalaryOver50000 {

	private static final String SELECT_EMPLOYEES = "SELECT e.firstName " +
			"FROM Employee e " +
			"WHERE e.salary > :salary";
	private static final String SALARY = "salary";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createQuery(SELECT_EMPLOYEES, String.class)
				.setParameter(SALARY, BigDecimal.valueOf(50_000))
				.getResultList()
				.forEach(System.out::println);

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}