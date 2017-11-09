package co.markhoward.jipadvisor.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPATestHelper {
	private static final String TEST_PU = "testPu";
	private static EntityManagerFactory factory;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(TEST_PU);
		}
		return factory;
	}

	public static void shutdown() {
		if (factory != null) {
			factory.close();
		}
	}
}
