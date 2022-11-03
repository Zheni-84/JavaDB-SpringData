import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;
import java.util.stream.Collectors;

public class Task10IncreaseSalaries {

	private static final String UPDATE_EMPLOYEES =
			"UPDATE Employee e " +
					"SET e.salary = e.salary * 1.12 " +
					"WHERE e.department.id IN :departments ";
	private static final String SELECT_EMPLOYEES = "SELECT e FROM Employee e WHERE e.department.id IN :departments";
	private static final Set<Integer> DEPARTMENTS = Set.of(1, 2, 4, 11);

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createQuery(UPDATE_EMPLOYEES)
				.setParameter("departments", DEPARTMENTS)
				.executeUpdate();
		transaction.commit();

		System.out.println(entityManager.createQuery(SELECT_EMPLOYEES, Employee.class)
				.setParameter("departments", DEPARTMENTS)
				.getResultStream()
				.map(e -> String.format("%s %s ($%.2f)", e.getFirstName(), e.getLastName(), e.getSalary()))
				.collect(Collectors.joining(System.lineSeparator())));

		entityManager.close();
	}
}