package fr.prive.gestionSeriesFilm.dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class daoManager {
	private static EntityManagerFactory emf;

	static{
		emf = Persistence.createEntityManagerFactory("gestionSeriesFilm");
	}
	
	public static EntityManager getEntityManager(){
		return emf.createEntityManager();
	}
	
	public static void close(){
		emf.close();
	}
}
