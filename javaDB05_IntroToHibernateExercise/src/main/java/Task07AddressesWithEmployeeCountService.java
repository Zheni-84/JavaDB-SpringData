import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Task07AddressesWithEmployeeCountService {

	private static final int RESULTS_LIMIT = 10;
	private static final String SELECT_ADDRESSES = "SELECT a FROM Address a ORDER BY a.employees.size DESC";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.createQuery(SELECT_ADDRESSES, Address.class)
				.setMaxResults(RESULTS_LIMIT)
				.getResultList()
				.forEach(address -> System.out.printf("%s, %s - %d employees%n",
						address.getText(),
						address.getTown().getName(),
						address.getEmployees().size()));

		entityManager.close();
	}
}