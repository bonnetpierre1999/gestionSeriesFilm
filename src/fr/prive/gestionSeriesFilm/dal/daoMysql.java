package fr.prive.gestionSeriesFilm.dal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.prive.gestionSeriesFilm.bo.*;

public class daoMysql {
	
	private static String GETNBSAISONSBYSERIEID(int id_Serie) {
		return "SELECT count(*) FROM Saison s where serie_id = " + id_Serie;
	}
	private static String GETNBEPISODESBYSAISONID(int id_Saison) {
		return "SELECT count(*) FROM Episode e where saison_id = " + id_Saison;
	}
	private static String GETNBEPISODESNONVUSBYSERIEID(int id_Serie) {
		return "SELECT count(*) FROM Episode e where saison_id in (SELECT id FROM Saison s where vu = false and serie_id = " + id_Serie + ")";
	}
	private static String GETNBEPISODESNONVUS() {
		return "SELECT count(*) FROM Episode e where vu = false";
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                   OUVERTURE ET FERMETURE DES ENTITYMANAGER                                   //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Créer un EM et ouvrir une transaction
	private static EntityManager newEntityManager() {
	   EntityManager em = daoManager.getEntityManager();
	   em.getTransaction().begin();
	   return (em);
	}

	// Fermer un EM et défaire la transaction si nécessaire
	private static void closeEntityManager(EntityManager em) {
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                            CREATION D'UN DATABEAN                                            //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Permet d'ajouter une série en BDD
	 * @param serie
	 * @throws daoException
	 */
	public static void ajouterSerie(Serie serie) throws daoException {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(serie);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	/**
	 * Permet d'ajouter une saison en BDD
	 * @param saison
	 * @throws daoException
	 */
	public static void ajouterSaison(Saison saison) throws daoException {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(saison);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}

	/**
	 * Permet d'ajouter un épisode en BDD
	 * @param episode
	 * @throws daoException
	 */
	public static void ajouterEpisode(Episode episode) throws daoException {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(episode);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                          MODIFICATION D'UN DATABEAN                                          //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Permet de modifier une série en BDD
	 * @param serie
	 * @throws daoException
	 */
	public static void ModifierSerie(Serie serie) throws daoException {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.merge(serie);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	/**
	 * Permet de modifier un épisode en BDD
	 * @param e
	 */
	public static void modifierEpisode(Episode e) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.merge(e);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                          SUPPRESSION D'UN DATABEAN                                           //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Permet de supprimer une série en BDD
	 * @param idSerie
	 * @throws daoException
	 */
	public static void supprimerSerie(int idSerie) throws daoException {
		EntityManager em = null;
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
	 * Permet de supprimer une saison dans la BDD
	 * @param idSaison
	 * @throws daoException
	 */
	public static void supprimerSaison(int idSaison) throws daoException {
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			Saison saison = em.find(Saison.class, idSaison);
			em.remove(saison);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de la saison n°" + idSaison + " : " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	/**
	 * Permet de supprimer un épisode dans la BDD
	 * @param idEpisode
	 * @throws daoException
	 */
	public static void supprimerEpisode(int idEpisode) throws daoException {
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			Episode episode = em.find(Episode.class, idEpisode);
			em.remove(episode);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			throw new daoException("Erreur lors de la suppression de l'episode n°" + idEpisode + " : " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                           AFFICHAGE D'UN DATABEAN                                            //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Permet de retourner une série selon son Id
	 * @param idSerie
	 * @return
	 * @throws daoException
	 */
	public static Serie AffichageSerieById(int idSerie) throws daoException {
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Serie serie = null;
		try {
			serie = em.find(Serie.class, idSerie);
			return serie;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accÃ¨s à la série n°" + idSerie + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		      }
		}
	}
	
	/**
	 * Permet l'affichage d'une saison selon son Id
	 * @param idSaison
	 * @return
	 * @throws daoException
	 */
	public static Saison AffichageSaisonById(int idSaison) throws daoException {
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Saison saison = null;
		try {
			saison = em.find(Saison.class, idSaison);
			return saison;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accÃ¨s à la saison n°" + idSaison + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		      }
		}
	}
	
	/**
	 * Permet de retourner un épisode selon son Id
	 * @param idEpisode
	 * @return
	 * @throws daoException
	 */
	public static Episode AffichageEpisodeById(int idEpisode) throws daoException {
		EntityManager em = daoManager.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Episode episode = null;
		try {
			episode = em.find(Episode.class, idEpisode);
			return episode;
		} catch (Exception e) {
			throw new daoException("Erreur lors de l'accÃ¨s à l'episode n°" + idEpisode + " : " + e.getMessage());
		} finally {
			if (em != null) {
				closeEntityManager(em);
		    }
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                               AFFICHAGES DIVERS                                              //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Permet de renvoyer toutes les séries
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
	 * Permet de renvoyer toutes les saisons d'une série selon un Id 
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Permet de retourner tous les épisodes d'une saison selon un Id
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Permet de retourner tous les épisodes non vus d'une saison
	 * @param idSaison
	 * @return
	 */
	public static List<Episode> AffichageAllEpisodesNonVuByIdSaison(int idSaison) {
		EntityManager em = daoManager.getEntityManager();
		List<Episode> episodes = new ArrayList<Episode>();
		try {
			TypedQuery<Episode> query = em.createNamedQuery("findTousEpisodeNonVusBySaison", Episode.class);
			query.setParameter("var", idSaison);
			episodes  = query.getResultList();
			return episodes;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}

	/**
	 * Permet de retourner tous les épisodes non vus d'une série
	 * @param idSerie
	 * @return
	 */
	public static List<Episode> AffichageAllEpisodesNonVuByIdSerie(int idSerie) {
		EntityManager em = daoManager.getEntityManager();
		List<Saison> allSaison = null;
		List<Episode> episodes = new ArrayList<Episode>();
		List<Episode> lstEpisodes = new ArrayList<Episode>();
		try {
				allSaison = AffichageAllSaisonsByIdserie(idSerie);
				for (Saison saison : allSaison)
				{
					episodes = AffichageAllEpisodesNonVuByIdSaison(saison.getId());
					for (Episode e : episodes)
					{
						lstEpisodes.add(e);
					}
				} 
			return lstEpisodes;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}
	
	/**
	 * Permet de retourner tous les épisodes non vus
	 * @return
	 */
	public static List<Episode> AffichageAllEpisodesNonVu() {
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Episode> allEpisode = new ArrayList<Episode>();
		List<Episode> episodes  = new ArrayList<Episode>();
		try {
			allSerie = AffichageAllSerie();
			for (Serie serie : allSerie)
			{
				episodes  = AffichageAllEpisodesNonVuByIdSerie(serie.getId());
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
	
	/**
	 * Permet de retourner les séries avec au moins un épisode non vu
	 * @return
	 */
	public static List<Serie> AffichageSerieAvecEpisodeNonVu() {
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Episode> episodes = new ArrayList<Episode>();
		List<Serie> allSerieNonVu = new ArrayList<Serie>();
		try {
			allSerie = AffichageAllSerie();
			for (Serie serie : allSerie)
			{
				episodes  = AffichageAllEpisodesNonVuByIdSerie(serie.getId());
				if(episodes.size() >= 1)
				{
					allSerieNonVu.add(serie);
				}
			}
			return allSerieNonVu;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}
	
	/**
	 * Permet de retourner les prochains épisode à voir (pour chaque série)
	 * @return
	 */
	public static List<Episode> AffichageNextEpisode() {
		EntityManager em = daoManager.getEntityManager();
		List<Serie> allSerie = null;
		List<Episode> episodes = new ArrayList<Episode>();
		List<Episode> allEpisode = new ArrayList<Episode>();
		try {
			allSerie = AffichageAllSerie();
			for (Serie serie : allSerie)
			{
				episodes  = AffichageAllEpisodesNonVuByIdSerie(serie.getId());
				if(episodes.size() >= 1)
				{
					allEpisode.add(episodes.get(0));
				}
			}
			return allEpisode;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                               AFFICHAGES AGGREGATION                                         //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Permet de retourner le nombre de saison pour une série donnée
	 * @param idSerie
	 * @return
	 */
	public static long AfficherNbSaison(int idSerie) {
		EntityManager em = daoManager.getEntityManager();
		long nb = 0;
		try {
			Query query = em.createQuery(GETNBSAISONSBYSERIEID(idSerie));
			Object l = query.getSingleResult();
			nb = (long) l;
			return nb;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}

	/**
	 * Permet de retourner le nombre d'épisode pour une saison donnée
	 * @param idSaison
	 * @return
	 */
	public static long AfficherNbEp(int idSaison) {
		EntityManager em = daoManager.getEntityManager();
		long nb = 0;
		try {
			Query query = em.createQuery(GETNBEPISODESBYSAISONID(idSaison));
			Object l = query.getSingleResult();
			nb = (long) l;
			return nb;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}

	/**
	 * Permet de retourner le nombre d'épisodes non vus pour une série donnée
	 * @param idSerie
	 * @return
	 */
	public static long AfficherNbEpNonVusBySerie(int idSerie) {
		EntityManager em = daoManager.getEntityManager();
		long nb = 0;
		try {
			Query query = em.createQuery(GETNBEPISODESNONVUSBYSERIEID(idSerie));
			Object l = query.getSingleResult();
			nb = (long) l;
			return nb;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}
	
	public static Long AfficherNbEpNonVus() {
		EntityManager em = daoManager.getEntityManager();
		long nb = 0;
		try {
			Query query = em.createQuery(GETNBEPISODESNONVUS());
			Object l = query.getSingleResult();
			nb = (long) l;
			return nb;
		} finally {
			if (em != null) {
				closeEntityManager(em);
		     }
		}
	}
	
}