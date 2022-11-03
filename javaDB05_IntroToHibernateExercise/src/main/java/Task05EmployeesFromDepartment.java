import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Task05EmployeesFromDepartment {

	private static final String DEPARTMENT_NAME = "Research and Development";
	private static final String SELECT_BY_DEPARTMENT =
			"SELECT e FROM Employee e " +
					"WHERE e.department.name = :departmentName " +
					"ORDER BY e.salary, e.id";
	private static final String DEPARTMENT = "departmentName";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createQuery(SELECT_BY_DEPARTMENT, Employee.class)
				.setParameter(DEPARTMENT, DEPARTMENT_NAME)
				.getResultList()
				.forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
						e.getFirstName(),
						e.getLastName(),
						e.getDepartment(),
						e.getSalary()));

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}