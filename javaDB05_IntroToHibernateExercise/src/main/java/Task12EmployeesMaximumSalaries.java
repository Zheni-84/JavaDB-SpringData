import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Task12EmployeesMaximumSalaries {

	private static final String SELECT_DEPARTMENTS =
			"SELECT e.department.name, MAX(e.salary) " +
					"FROM Employee e " +
					"GROUP BY e.department.id " +
					"HAVING MAX(e.salary) NOT BETWEEN :minSalary AND :maxSalary";
	private static final String MIN_SALARY = "minSalary";
	private static final String MAX_SALARY = "maxSalary";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<Object[]> resultList = entityManager
				.createQuery(SELECT_DEPARTMENTS)
				.setParameter(MIN_SALARY, BigDecimal.valueOf(30_000))
				.setParameter(MAX_SALARY, BigDecimal.valueOf(70_000))
				.getResultList();
		System.out.println(resultList
				.stream()
				.map(array -> String.format("%s %.2f", array[0], array[1]))
				.collect(Collectors.joining(System.lineSeparator())));
	}
}