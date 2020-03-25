package fr.prive.gestionSeriesFilm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.prive.gestionSeriesFilm.bo.Serie;
import fr.prive.gestionSeriesFilm.dal.daoException;
import fr.prive.gestionSeriesFilm.dal.daoMysql;

/**
 * Servlet implementation class ServletSerie
 */
public class ServletSerie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSerie() {
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
		String modifEffectue = "";
		
		// ajout d'une serie
		if (request.getParameter("btnAjouter") != null) {
			if (request.getParameter("nomSerie") != null && isNumeric(request.getParameter("annee"))) {
				try {
					int annee = Integer.parseInt(request.getParameter("annee"));
					Serie s = new Serie(request.getParameter("nomSerie"), annee);
					daoMysql.ajouterSerie(s);
				} catch (NumberFormatException | daoException e) {
					request.setAttribute("erreur", "oui");
				}
			} else {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// Suppression d'une serie
		if (request.getParameter("btnSupprimer") != null) {
			try {
				daoMysql.supprimerSerie((Integer.parseInt(request.getParameter("idSerie"))));
			} catch (NumberFormatException | daoException e) {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// modification d'une serie (apres modif)
		if (request.getParameter("btnModifier2") != null) {
			
			if (request.getParameter("nomSerie") != null && isNumeric(request.getParameter("annee"))) {
				try {
					int annee = Integer.parseInt(request.getParameter("annee"));
					int id = Integer.parseInt(request.getParameter("idSerie"));
					Serie s = new Serie(id,request.getParameter("nomSerie"), annee);
					daoMysql.updateSerie(s);
					//on signale que l'update est passée en DB
					modifEffectue = "ok";
					request.setAttribute("modifOK", "OK");
				} catch (NumberFormatException | daoException e) {
					//on signale que l'update n'est pas passée en DB
					request.setAttribute("erreur", "oui");
					modifEffectue = "ko";
					request.setAttribute("modifOK", "KO");
				}
			} else {
				//on signale que l'update n'est pas passée en DB
				request.setAttribute("erreur", "oui");
				modifEffectue = "ko";
				request.setAttribute("modifOK", "KO");
			}
		}
		
		System.out.println(modifEffectue);
		
		//  modification d'une serie (avant modif)
		if (request.getParameter("btnModifier") != null || "ko".equals(modifEffectue)) {
			try {
				Serie serie = daoMysql.afficherSerieById((Integer.parseInt(request.getParameter("idSerie"))));
				List<Serie> listeSerie = new ArrayList<Serie>();
				listeSerie.add(serie);
				request.setAttribute("listeSerie", listeSerie);
				System.out.println(request.getParameter("idSerie"));
			} catch (NumberFormatException | daoException e) {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// Affichage des series
		if (request.getParameter("action") == null || request.getParameter("btnAjouter") != null|| request.getParameter("btnSupprimer") != null || "ok".equals(modifEffectue)) {
			System.out.println(request.getParameter("action"));System.out.println(request.getParameter("btnAjouter"));System.out.println(request.getParameter("btnSupprimer"));
			List<Serie> listeSerie = daoMysql.findAllSerie();
			request.setAttribute("listeSerie", listeSerie);
		}

		request.setAttribute("nomMenu", "serie");
		request.setAttribute("action", request.getParameter("action"));

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
		rd.forward(request, response);
	}
	
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
