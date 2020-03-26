package fr.prive.gestionSeriesFilm.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.prive.gestionSeriesFilm.bo.*;

public class daoMysql {
	
	// CrÈer un EM et ouvrir une transaction
	public static EntityManager newEntityManager() {
	   EntityManager em = daoManager.getEntityManager();
	   em.getTransaction().begin();
	   return (em);
	}

	// Fermer un EM et dÈfaire la transaction si nÈcessaire
	public static void closeEntityManager(EntityManager em) {
	   if (em != null) {
	      if (em.isOpen()) {
	         EntityTransaction t = em.getTransaction();
	         if (t.isActive()) {
	            try {
	               t.rollback();
	            } catch (PersistenceException e) {
	            }
	         }
	         em.close();
	      }
	   }
	}
	
	/**
	 * Permet la crÈation d'une sÈrie en DB
	 * @param serie
	 * @throws daoException
	 */
	public static void ajouterSerie(Serie serie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			em.persist(serie);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}

	/**
	 * Permet la crÈation d'une saison en DB
	 * @param saison
	 * @throws daoException
	 */
	public static void ajouterSaison(Saison saison) throws daoException {
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			em.persist(saison);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}

	/**
	 * Permet la crÈation d'un Èpisode en DB
	 * @param episode
	 * @throws daoException
	 */
	public static void ajouterEpisode(Episode episode) throws daoException {
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			em.persist(episode);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	/**
	 * Modifie un film dans la DB
	 * @param film
	 * @throws daoException
	 */
	public static void ModifierSerie(Serie serie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			em.merge(serie);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	/**
	 * Permet la suppression d'une sÈrie en DB
	 * @param idSalle
	 * @throws daoException
	 */
	public static void supprimerSerie(int idSerie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			Serie serie = em.find(Serie.class, idSerie);
			em.remove(serie);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	
	/**
	 * Permet de sortir toutes les series de la DB
	 * @return
	 */
	public static List<Serie> AffichageAllSerie() {
		EntityManager em = daoManager.getEntityManager();
		try {
			TypedQuery<Serie> query = em.createNamedQuery("findTousSerie", Serie.class);
			List<Serie> s = query.getResultList();
			return s;
		} finally {
			if (em != null) {
				closeEntityManager(em);
	      }
		}
	}
	
	/**
	 * Retourne une sÈrie selon l'id
	 * @param idSalle
	 * @return
	 * @throws daoException
	 */
	public static Serie AffichageSerieById(int idSerie) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Serie serie = null;
		// Acc√®s possible
		try {
			serie = em.find(Serie.class, idSerie);
			return serie;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'acc√®s ‡ la sÈrie n∞" + idSerie + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		      }
		}
		
	}

	public static List<Saison> AffichageAllSaisonsByIdserie(int id) {
		EntityManager em = daoManager.getEntityManager();
		try {
			TypedQuery<Saison> query = em.createNamedQuery("listeSaisonById", Saison.class);
			query.setParameter("var", id);
			List<Saison> s = query.getResultList();
			return s;
		} finally {
			closeEntityManager(em);
		}
		
	}
	
	public static List<Episode> AffichageAllEpisodesByIdsaison(int id) {
		EntityManager em = daoManager.getEntityManager();
		try {
			TypedQuery<Episode> query = em.createNamedQuery("listeEpisodeById", Episode.class);
			query.setParameter("var", id);
			List<Episode> s = query.getResultList();
			return s;
		} finally {
			closeEntityManager(em);
		}
		
	}

	public static void supprimerSaison(int idSaison) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Acc√®s possible
		try {
			Saison saison = em.find(Saison.class, idSaison);
			em.remove(saison);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la saison n∞" + idSaison + " : " + e.getMessage());
		} finally {
			em.close();
		}
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
		// Acc√®s possible
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
		// Acc√®s possible
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
		// Acc√®s possible
		try {
			Film film = em.find(Film.class, idFilm);
			em.remove(film);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression du film n¬∞" + idFilm + " : " + e.getMessage());
		}
	}

	public static void ajouterSalle(Salle salle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Acc√®s possible
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
		// Acc√®s possible
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
		// Acc√®s possible
		try {
			Salle salle = em.find(Salle.class, idSalle);
			em.remove(salle);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la salle n¬∞" + idSalle + " : " + e.getMessage());
		}
	}

	public static void ajouterSeance(Seance seance) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Acc√®s possible
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
		// Acc√®s possible
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
		// Acc√®s possible
		try {
			Seance seance = em.find(Seance.class, idSeance);
			em.remove(seance);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la seance n¬∞" + idSeance + " : " + e.getMessage());
		}
	}


	public static Film afficherFilmById(int idFilm) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Film film = null;
		// Acc√®s possible
		try {
			film = em.find(Film.class, idFilm);
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'acc√®s au film n¬∞" + idFilm + " : " + e.getMessage());
		}
		return film;
	}


	public static Salle afficherSalleById(int idSalle) throws daoException {
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Salle salle = null;
		// Acc√®s possible
		try {
			salle = em.find(Salle.class, idSalle);
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'acc√®s √† la salle n¬∞" + idSalle + " : " + e.getMessage());
		}
		return salle;
	}*/

}
