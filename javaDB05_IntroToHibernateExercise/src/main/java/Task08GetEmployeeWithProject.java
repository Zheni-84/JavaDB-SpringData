import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task08GetEmployeeWithProject {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		int employeeId = Integer.parseInt(new Scanner(System.in).nextLine());
		Employee employee = entityManager.find(Employee.class, employeeId);
		System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

		System.out.print(employee.getProjects()
				.stream()
				.map(Project::getName)
				.sorted(String::compareTo)
				.map(s -> String.format("\t%s", s))
				.collect(Collectors.joining(System.lineSeparator())));

		entityManager.close();
	}
}