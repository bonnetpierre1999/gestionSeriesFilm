package fr.prive.gestionSeriesFilm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.prive.gestionSeriesFilm.bo.Episode;
import fr.prive.gestionSeriesFilm.bo.Saison;
import fr.prive.gestionSeriesFilm.bo.Serie;
import fr.prive.gestionSeriesFilm.dal.daoException;
import fr.prive.gestionSeriesFilm.dal.daoMysql;

/**
 * Servlet implementation class ServletAVoir
 */
public class ServletAVoir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAVoir() {
        super();
    }

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
			
		// affichage de tous les ep non vus
		if (request.getParameter("btnAll") != null || "all".equals(request.getParameter("action"))) {
			try {	
				List<Episode> listeEpisode = daoMysql.AffichageAllEpisodesNonVu();
				request.setAttribute("listeEpisode", listeEpisode);
				Long nbEpisode = daoMysql.AfficherNbEpNonVus();
				request.setAttribute("nbEpisode", nbEpisode);
			} catch (NumberFormatException e) {
				request.setAttribute("erreur", "oui");
			}
		}
			
		// affichage du prochain episode à voir par serie
		if (request.getParameter("btnNext") != null || "next".equals(request.getParameter("action")) || request.getParameter("action") == null) {
			try {	
				List<Episode> listeEpisode = daoMysql.AffichageNextEpisode();
				request.setAttribute("listeEpisode", listeEpisode);
			} catch (NumberFormatException e) {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// Affichage des series
		if (request.getParameter("btnSerie") != null ) {
			try {	
				List<Serie> listeSerie = daoMysql.AffichageSerieAvecEpisodeNonVu();
				request.setAttribute("listeSerie", listeSerie);
			} catch (NumberFormatException e) {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// affichage de tous les ep non vus par serie
		if (request.getParameter("btnSerie2") != null || "parSerie".equals(request.getParameter("action"))) {
			try {	
				int idSerie = Integer.parseInt(request.getParameter("idSerie"));
				List<Episode> listeEpisode = daoMysql.AffichageAllEpisodesNonVuByIdSerie(idSerie);
				request.setAttribute("listeEpisode", listeEpisode);
			} catch (NumberFormatException e) {
				request.setAttribute("erreur", "oui");
			}
		}
				
	
		request.setAttribute("nomMenu", "AVoir");
		request.setAttribute("action", request.getParameter("action"));
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
		rd.forward(request, response); 

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
