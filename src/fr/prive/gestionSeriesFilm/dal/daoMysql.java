package fr.prive.gestionSeriesFilm.dal;

import java.util.ArrayList;
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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
		//TODO
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

	/**
	 * Retourne une saison selon l'id
	 * @param idSalle
	 * @return
	 * @throws daoException
	 */
	public static Saison AffichageSaisonById(int idSaison) throws daoException {
		//TODO
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Saison saison = null;
		// Acc√®s possible
		try {
			saison = em.find(Saison.class, idSaison);
			return saison;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'acc√®s ‡ la saison n∞" + idSaison + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		      }
		}
		
	}

	public static void supprimerEpisode(int idEpisode) throws daoException {
		//TODO
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		// Acc√®s possible
		try {
			Episode episode = em.find(Episode.class, idEpisode);
			em.remove(episode);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de l'episode n∞" + idEpisode + " : " + e.getMessage());
		} finally {
			em.close();
		}
	}

	
	/**
	 * Modifie un episode dans la DB
	 * @param episode
	 * @throws daoException
	 */
	public static void modifierEpisode(Episode e) {
		//TODO
		// Association au manager obligatoire
		EntityManager em = null;
		// Acc√®s possible
		try {
			em = newEntityManager();
			em.merge(e);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}

	public static List<Episode> AffichageAllEpisodesNonVu() {
		//TODO
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Saison> allSaison = null;
		List<Episode> allEpisode = new ArrayList<Episode>();
		try {
			
			allSerie = AffichageAllSerie();
			
			for (Serie serie : allSerie)
			{
				allSaison = AffichageAllSaisonsByIdserie(serie.getId());
				for (Saison saison : allSaison)
				{
					TypedQuery<Episode> query = em.createNamedQuery("findTousEpisodeNonVusBySerie", Episode.class);
					query.setParameter("var", saison.getId());
					List<Episode> episodes  = query.getResultList();
					for (Episode episode : episodes)
					{
						allEpisode.add(episode);
					}
				}
			}
			return allEpisode;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
		
	}
	
	public static List<Episode> AffichageNextEpisode() {
		//TODO
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		int nbSerie;
		int numSerie;
		boolean vuSerie;
		List<Saison> allSaison = null;
		List<Episode> allEpisode = new ArrayList<Episode>();
		try {
			
			allSerie = AffichageAllSerie();
			nbSerie = allSerie.size();
			numSerie = 0;
			vuSerie = false;
			
			for (Serie serie : allSerie)
			{
				allSaison = AffichageAllSaisonsByIdserie(serie.getId());
				for (Saison saison : allSaison)
				{
					TypedQuery<Episode> query = em.createNamedQuery("findTousEpisodeNonVusBySerie", Episode.class);
					query.setParameter("var", saison.getId());
					List<Episode> episodes  = query.getResultList();

					if(episodes.size() >= 1)
					{
						allEpisode.add(episodes.get(0));
						break;
					}
				}
			}
			return allEpisode;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
		
	}

	public static Episode AffichageEpisodeById(int idEpisode) throws daoException {
		//TODO
		// Association au manager obligatoire
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Episode episode = null;
		// Acc√®s possible
		try {
			episode = em.find(Episode.class, idEpisode);
			return episode;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'acc√®s ‡ l'episode n∞" + idEpisode + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		    }
		}
	}
	
	
	
	
	
	
	
	
	public static List<Serie> AffichageSerieAvecEpisodeNonVu() {
		//TODO
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Saison> allSaison = null;
		List<Serie> allSerieNonVu = new ArrayList<Serie>();
		try {
			
			allSerie = AffichageAllSerie();
			
			for (Serie serie : allSerie)
			{
				allSaison = AffichageAllSaisonsByIdserie(serie.getId());
				for (Saison saison : allSaison)
				{
					TypedQuery<Episode> query = em.createNamedQuery("findTousEpisodeNonVusBySerie", Episode.class);
					query.setParameter("var", saison.getId());
					List<Episode> episodes  = query.getResultList();

					if(episodes.size() >= 1)
					{
						allSerieNonVu.add(serie);
						break;
					}
				}
			}
			return allSerieNonVu;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
		
	}
	
	public static List<Episode> AffichageAllEpisodesNonVuByIdSerie(int idSerie) {
		//TODO
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Saison> allSaison = null;
		List<Episode> allEpisode = new ArrayList<Episode>();
		try {
			
				allSaison = AffichageAllSaisonsByIdserie(idSerie);
				for (Saison saison : allSaison)
				{
					TypedQuery<Episode> query = em.createNamedQuery("findTousEpisodeNonVusBySerie", Episode.class);
					query.setParameter("var", saison.getId());
					List<Episode> episodes  = query.getResultList();
					for (Episode episode : episodes)
					{
						allEpisode.add(episode);
					}
				} 
			
			return allEpisode;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
		
	}
}