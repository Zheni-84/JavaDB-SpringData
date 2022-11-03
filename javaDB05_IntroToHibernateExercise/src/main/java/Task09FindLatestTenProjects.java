import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Task09FindLatestTenProjects {

	private static final int RESULTS_LIMIT = 10;
	private static final String SELECT_PROJECTS =
			"SELECT p FROM Project p " +
					"WHERE p.startDate IS NOT NULL " +
					"ORDER BY p.startDate DESC";

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		List<Project> projects = entityManager.createQuery(SELECT_PROJECTS, Project.class)
				.setMaxResults(RESULTS_LIMIT)
				.getResultList();

		System.out.println(projects.isEmpty()
				? "No results Found"
				: projects.stream()
				.sorted(Comparator.comparing(Project::getName))
				.map(Project::toString)
				.collect(Collectors.joining(System.lineSeparator()))
		);

		entityManager.close();
	}
}