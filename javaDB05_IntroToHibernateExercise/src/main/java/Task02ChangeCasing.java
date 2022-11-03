import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Task02ChangeCasing {

	private static final String SELECT_TOWNS = "SELECT t FROM Town t";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager
				.createQuery(SELECT_TOWNS, Town.class)
				.getResultStream()
				.forEach(town -> {
					String name = town.getName();
					if (name.length() <= 5) {
						String upper = name.toUpperCase();
						town.setName(upper);
						entityManager.persist(town);
					}
				});

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}