import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Task06AddNewAddressUpdateEmployee {

	private static final String SELECT_TOWN = "SELECT t FROM Town t WHERE t.name =: townName";
	private static final String SELECT_EMPLOYEE_BY_LAST_NAME = "SELECT e FROM Employee e WHERE e.lastName = :lastName";
	private static final String LAST_NAME = "lastName";
	private static final String VITOSHKA_15 = "Vitoshka 15";
	private static final String SOFIA = "Sofia";
	private static final String TOWN_NAME = "townName";
	private static final String OUTPUT_FORMAT = "The address of the employee \"%s %s\" was set to \"%s\" successfully.%n";
	private static final String EMPLOYEE_NOT_FOUND = "Employee \"%s\" not found.%n";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String lastName = new Scanner(System.in).nextLine();

		Employee employee = entityManager
				.createQuery(SELECT_EMPLOYEE_BY_LAST_NAME, Employee.class)
				.setParameter(LAST_NAME, lastName)
				.getResultList().stream().findFirst().orElse(null);
		if (employee != null) {
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			Address address = createAddress(entityManager);
			employee.setAddress(address);
			transaction.commit();
			System.out.printf(OUTPUT_FORMAT, employee.getFirstName(), employee.getLastName(), address.getText());
		} else {
			System.out.printf(EMPLOYEE_NOT_FOUND, lastName);
		}
		entityManager.close();
	}

	private static Address createAddress(EntityManager entityManager) {
		Town town = entityManager
				.createQuery(SELECT_TOWN, Town.class)
				.setParameter(TOWN_NAME, SOFIA)
				.getResultStream()
				.findFirst()
				.orElse(null);
		Address address = new Address();
		address.setText(VITOSHKA_15);
		address.setTown(town);
		entityManager.persist(address);
		return address;
	}
}