package fr.prive.gestionSeriesFilm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.prive.gestionSeriesFilm.bo.Episode;
import fr.prive.gestionSeriesFilm.bo.Saison;
import fr.prive.gestionSeriesFilm.dal.daoException;
import fr.prive.gestionSeriesFilm.dal.daoMysql;



/**
 * Servlet implementation class ServletSaisons
 */
public class ServletEpisode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("erreur", "non");
		
		if (request.getParameter("idSaison") != null)
		{
			int id = Integer.parseInt(request.getParameter("idSaison"));
			request.setAttribute("idSaison", request.getParameter("idSaison"));
			int idSerie = Integer.parseInt(request.getParameter("idSerie"));
			request.setAttribute("idSerie", request.getParameter("idSerie"));
			
			
			// ajout d'une saison et des episodes de la saison
			if (request.getParameter("btnAjouter") != null) {
				try {
					int numEpisode = Integer.parseInt(request.getParameter("numEpisode"));
					Saison saison = daoMysql.AffichageSaisonById(id);
					Episode e = new Episode(saison, numEpisode, false);
					daoMysql.ajouterEpisode(e);
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			}
			
			// modification si un ep est vu ou pas
			if (request.getParameter("btnVU") != null) {
				try {	
					int idEpisode = Integer.parseInt(request.getParameter("idEpisode"));
					Episode episode = daoMysql.AffichageEpisodeById(idEpisode);
					int vue = Integer.parseInt(request.getParameter("vue"));
					boolean vu = false;
					if (vue == 1)
					{
						vu=true;
					}
					Episode e = new Episode(episode.getId(), episode.getSaison(), episode.getNumEpisode(), vu);
					daoMysql.modifierEpisode(e);
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			}
			
			// Suppression d'un episode
			if (request.getParameter("btnSupprimer") != null) {
				try {
					daoMysql.supprimerEpisode(Integer.parseInt(request.getParameter("idEpisode")));
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			}
					
					
			// Affichage des saisons
			if (request.getParameter("action") == null || request.getParameter("btnAjouter") != null|| request.getParameter("btnSupprimer") != null) {
				List<Episode> listeEpisode = daoMysql.AffichageAllEpisodesByIdsaison(id);
				request.setAttribute("listeEpisode", listeEpisode);
				
				//calcul du nombre d'episode
				int nbEpisode = 0;
				try {
					nbEpisode = nbEpisode(id);
				} catch (NumberFormatException e) {
					request.setAttribute("erreur", "oui");
				}
				request.setAttribute("nbEpisode", nbEpisode);
				
				//On met la serie et la saison en attribut pour pouvoir la réutiliser
				try {
					request.setAttribute("serieChoisie", daoMysql.AffichageSerieById(idSerie));
					request.setAttribute("saisonChoisie", daoMysql.AffichageSaisonById(id));
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			}
	
			request.setAttribute("nomMenu", "episode");
			request.setAttribute("action", request.getParameter("action"));
	
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
			rd.forward(request, response);
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/Series");
		}
	}
	
	public static int nbEpisode(int idSaison)
	{
		int episode = 0;
		
		for (@SuppressWarnings("unused") Episode e : daoMysql.AffichageAllEpisodesByIdsaison(idSaison))
		{
			episode++;
		}
		return episode;
	}
}