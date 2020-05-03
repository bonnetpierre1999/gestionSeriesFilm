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
import fr.prive.gestionSeriesFilm.bo.Serie;
import fr.prive.gestionSeriesFilm.dal.daoException;
import fr.prive.gestionSeriesFilm.dal.daoMysql;



/**
 * Servlet implementation class ServletSaisons
 */
public class ServletSaisons extends HttpServlet {
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
		
		if (request.getParameter("idSerie") != null)
		{
			int id = Integer.parseInt(request.getParameter("idSerie"));
			request.setAttribute("idSerie", request.getParameter("idSerie"));
			
			// ajout d'une saison et des episodes de la saison
			if (request.getParameter("btnAjouter") != null) {
				if (request.getParameter("nbEpisodes") != null) {
					try {
						int nbEpisode = Integer.parseInt(request.getParameter("nbEpisodes"));
						int numSaison = Integer.parseInt(request.getParameter("idSaison"));
						Serie serie = daoMysql.AffichageSerieById(id);
						Saison s = new Saison(serie, numSaison);
						daoMysql.ajouterSaison(s);
						
						for (int i=1 ; i<=nbEpisode;i++)
						{
							Episode e = new Episode(s, i, false);
							daoMysql.ajouterEpisode(e);
						}
					} catch (NumberFormatException | daoException e) {
						request.setAttribute("erreur", "oui");
					}
				} else {
					request.setAttribute("erreur", "oui");
				}
			}
			
			
			// Suppression d'une saison et de ses episodes
			if (request.getParameter("btnSupprimer") != null) {
				
				//calcul du nombre de saisons
				int nbSaison = 0;
				try {
					nbSaison = nbSaison(id);
					int saisonASupp = Integer.parseInt(request.getParameter("NumSaison"));
					
					if (nbSaison == saisonASupp)
					{
						try {
							daoMysql.supprimerSaison(Integer.parseInt(request.getParameter("idSaison")));
						} catch (NumberFormatException | daoException e) {
							request.setAttribute("erreur", "oui");
						}
					}
					else
					{
						request.setAttribute("erreur", "oui");
					}
				} catch (NumberFormatException e) {
					request.setAttribute("erreur", "oui");
				}
	
			}
					
					
			// Affichage des saisons
			if (request.getParameter("action") == null || request.getParameter("btnAjouter") != null|| request.getParameter("btnSupprimer") != null) {
				List<Saison> listeSaison = daoMysql.AffichageAllSaisonsByIdserie(id);
				request.setAttribute("listeSaison", listeSaison);
				//calcul du nombre de saisons
				int nbSaison = 0;
				try {
					nbSaison = nbSaison(id);
				} catch (NumberFormatException e) {
					request.setAttribute("erreur", "oui");
				}
				request.setAttribute("nbSaison", nbSaison);
				//On met la serie en attribut pour pouvoir la réutiliser
				try {
					request.setAttribute("serieChoisie", daoMysql.AffichageSerieById(id));
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			}
	
			request.setAttribute("nomMenu", "saison");
			request.setAttribute("action", request.getParameter("action"));
	
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
			rd.forward(request, response);
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/Series");
		}
	}
	
	public static int nbSaison(int idSerie)
	{
		int saison = 0;
		
		for (@SuppressWarnings("unused") Saison s : daoMysql.AffichageAllSaisonsByIdserie(idSerie))
		{
			saison++;
		}
		return saison;
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