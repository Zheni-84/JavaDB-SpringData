import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Task13RemoveTowns {

	private static final String TOWN_NAME = "townName";
	private static final String SELECT_ADDRESSES = "SELECT a FROM Address a WHERE a.town.name = :townName";
	private static final String SELECT_TOWNS = "SELECT t FROM Town t WHERE t.name = :townName";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String townName = new Scanner(System.in).nextLine();
		List<Address> addresses = getAddressesByTownName(townName, entityManager);

		entityManager.getTransaction().begin();

		for (Address address : addresses) {
			for (Employee employee : address.getEmployees()) {
				employee.setAddress(null);
			}
			entityManager.remove(address);
		}

		Town town = getTownByTownName(townName, entityManager);
		entityManager.remove(town);

		if (addresses.size() == 1) {
			System.out.printf("1 address in %s deleted%n", townName);
		} else {
			System.out.printf("%d addresses in %s deleted%n", addresses.size(), townName);
		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private static Town getTownByTownName(String townName, EntityManager entityManager) {
		return entityManager.createQuery(SELECT_TOWNS, Town.class)
				.setParameter(TOWN_NAME, townName)
				.getSingleResult();
	}

	private static List<Address> getAddressesByTownName(String townName, EntityManager entityManager) {
		return entityManager.createQuery(SELECT_ADDRESSES, Address.class)
				.setParameter(TOWN_NAME, townName)
				.getResultList();
	}
}