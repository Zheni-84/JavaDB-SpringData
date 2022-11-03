import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task11FindEmployeesByFirstName {

	private static final String SELECT_BY_PATTERN = "SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE :pattern";
	private static final String PATTERN = "pattern";
	private static final String WILD_CARD = "%";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String pattern = new Scanner(System.in).nextLine();
		System.out.println(entityManager.createQuery(SELECT_BY_PATTERN, Employee.class)
				.setParameter(PATTERN, pattern.toLowerCase() + WILD_CARD)
				.getResultStream()
				.map(employee -> String.format("%s %s - %s - ($%.2f)",
						employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary()))
				.collect(Collectors.joining(System.lineSeparator())));
	}
}