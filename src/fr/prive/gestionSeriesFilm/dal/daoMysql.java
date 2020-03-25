package fr.prive.gestionSeriesFilm.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import fr.prive.gestionSeriesFilm.bo.*;

public class daoMysql {

	/**
	 * Permet la cr�ation d'une s�rie en DB
	 * @param serie
	 * @throws daoException
	 */
	public static void ajouterSerie(Serie serie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(serie);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout de la serie " + serie + " : " + e.getMessage());
		}
		em.close();
	}

	/**
	 * Permet la cr�ation d'une saison en DB
	 * @param saison
	 * @throws daoException
	 */
	public static void ajouterSaison(Saison saison) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(saison);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout de la saison " + saison + " : " + e.getMessage());
		}
		em.close();
		
	}

	/**
	 * Permet la cr�ation d'un �pisode en DB
	 * @param episode
	 * @throws daoException
	 */
	public static void ajouterEpisode(Episode episode) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(episode);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout de la episode " + episode + " : " + e.getMessage());
		}
		em.close();
		
	}
	
	/**
	 * Modifie un film dans la DB
	 * @param film
	 * @throws daoException
	 */
	public static void updateSerie(Serie serie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.merge(serie);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la modification de la serie " + serie + " : " + e.getMessage());
		}
	}
	
	/**
	 * Permet la suppression d'une s�rie en DB
	 * @param idSalle
	 * @throws daoException
	 */
	public static void supprimerSerie(int idSerie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			Serie serie = em.find(Serie.class, idSerie);
			em.remove(serie);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la serie n�" + idSerie + " : " + e.getMessage());
		}
	}
	
	
	/**
	 * Permet de sortir toutes les series de la DB
	 * @return
	 */
	public static List<Serie> findAllSerie() {
		TypedQuery<Serie> query = daoManager.getEntityManager().createNamedQuery("findTousSerie", Serie.class);
		List<Serie> s = query.getResultList();
		return s;
	}
	
	/**
	 * Retourne une s�rie selon l'id
	 * @param idSalle
	 * @return
	 * @throws daoException
	 */
	public static Serie afficherSerieById(int idSalle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Serie serie = null;
		// Accès possible
		try {
			serie = em.find(Serie.class, idSalle);
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accès � la s�rie n�" + serie + " : " + e.getMessage());
		}
		return serie;
	}
	
	/*
	public static List<Employe> connexion(String login, String mdp) {
		TypedQuery query = daoManager.getEntityManager().createNamedQuery("connexion", Employe.class);
		query.setParameter("var1", login);
		query.setParameter("var2", mdp);
		List s = query.getResultList();
		return s;
	}

	public static List<Film> findAllFilm() {
		TypedQuery query = daoManager.getEntityManager().createNamedQuery("findTousFilm", Film.class);
		List s = query.getResultList();
		return s;
	}

	public static List<Salle> findAllSalle() {
		TypedQuery query = daoManager.getEntityManager().createNamedQuery("findTousSalle", Salle.class);
		List s = query.getResultList();
		return s;
	}

	public static List<Seance> findAllSeance() {
		TypedQuery query = daoManager.getEntityManager().createNamedQuery("findTousSeance", Seance.class);
		List s = query.getResultList();
		return s;
	}

	public static void ajouterFilm(Film film) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(film);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout du film " + film + " : " + e.getMessage());
		}
	}

	public static void updateFilm(Film film) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.merge(film);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la modification du film " + film + " : " + e.getMessage());
		}
	}

	public static void supprimerFilm(int idFilm) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			Film film = em.find(Film.class, idFilm);
			em.remove(film);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression du film n°" + idFilm + " : " + e.getMessage());
		}
	}

	public static void ajouterSalle(Salle salle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(salle);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout de la salle" + salle + " : " + e.getMessage());
		}
	}

	public static void updateSalle(Salle salle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.merge(salle);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la modification de la salle" + salle + " : " + e.getMessage());
		}
	}

	public static void supprimerSalle(int idSalle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			Salle salle = em.find(Salle.class, idSalle);
			em.remove(salle);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la salle n°" + idSalle + " : " + e.getMessage());
		}
	}

	public static void ajouterSeance(Seance seance) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.persist(seance);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de l'ajout de la seance" + seance + " : " + e.getMessage());
		}
	}

	public static void updateSeance(Seance seance) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			em.merge(seance);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la modification de la seance" + seance + " : " + e.getMessage());
		}
	}

	public static void supprimerSeance(int idSeance) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Accès possible
		try {
			Seance seance = em.find(Seance.class, idSeance);
			em.remove(seance);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la seance n°" + idSeance + " : " + e.getMessage());
		}
	}


	public static Film afficherFilmById(int idFilm) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Film film = null;
		// Accès possible
		try {
			film = em.find(Film.class, idFilm);
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accès au film n°" + idFilm + " : " + e.getMessage());
		}
		return film;
	}


	public static Salle afficherSalleById(int idSalle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Salle salle = null;
		// Accès possible
		try {
			salle = em.find(Salle.class, idSalle);
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accès à la salle n°" + idSalle + " : " + e.getMessage());
		}
		return salle;
	}*/

}
