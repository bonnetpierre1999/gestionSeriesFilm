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
		String ajoutEffectue = "";
		String AjoutOK = "";
		int Id_S = 0;
		// ajout d'une serie
		if (request.getParameter("btnAjouter2") != null) {
            if (!"".equals(request.getParameter("nomSerie"))) {
                try {
                    Serie s = new Serie(request.getParameter("nomSerie"), request.getParameter("diffusion"));
                    daoMysql.ajouterSerie(s);
                    request.setAttribute("ajoutOK", "OK");
                    AjoutOK = "OK";
                    Id_S = s.getId();
                    request.setAttribute("idSerie", s.getId() );
                    request.setAttribute("action", null);
                    
                }
                catch (NumberFormatException | daoException ex5) {
                    request.setAttribute("erreur", (Object)"oui");
                    ajoutEffectue = "ko";
                    request.setAttribute("ajoutOK", (Object)"KO");
                }
            }
            else {
                request.setAttribute("erreur", (Object)"oui");
                ajoutEffectue = "ko";
                request.setAttribute("ajoutOK", (Object)"KO");
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
			
			if (request.getParameter("nomSerie") != null) {
				try {
					boolean serieFini = false;
					if (request.getParameter("serieFini") != null) {
						serieFini = true;
					}
					Serie s = daoMysql.AffichageSerieById(Integer.parseInt(request.getParameter("idSerie")));
					s.setNom(request.getParameter("nomSerie"));
					s.setMoyenDiffusion(request.getParameter("diffusion"));
					s.setSerieFini(serieFini);
					daoMysql.ModifierSerie(s);
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
		
		if (request.getParameter("btnAnnuler") != null) {
			//on signale qu'on a annulé, ce qui nous fera retourner sur les séries sans modifier la DB
			modifEffectue = "annule";
			request.setAttribute("modifEffectue", "ANNULE");
			ajoutEffectue = "annule";
			request.setAttribute("ajoutEffectue", "ANNULE");
		}
		
		//  modification d'une serie (avant modif)
		if (request.getParameter("btnModifier") != null || "ko".equals(modifEffectue)) {
			try {
				Serie serie = daoMysql.AffichageSerieById((Integer.parseInt(request.getParameter("idSerie"))));
				List<Serie> listeSerie = new ArrayList<Serie>();
				listeSerie.add(serie);
				request.setAttribute("listeSerie", listeSerie);
			} catch (NumberFormatException | daoException e) {
				request.setAttribute("erreur", "oui");
			}
		}
		
		// Affichage des series
		
		if (request.getParameter("action") == null || request.getParameter("btnAjouter") != null || request.getParameter("btnSupprimer") != null || "annule".equals(modifEffectue) || "ok".equals(modifEffectue) || "annule".equals(ajoutEffectue)) {
			List<Serie> listeSerie = daoMysql.AffichageAllSerie();
			request.setAttribute("listeSerie", listeSerie);
		}
		
		request.setAttribute("nomMenu", "serie");
		request.setAttribute("action", request.getParameter("action"));

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//  Trouver solution pour aller sur Saisons
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if ("OK".equals(AjoutOK)) {
			String lien = getServletContext().getContextPath() + "/Saisons?idSerie=" + Id_S;
			response.sendRedirect(lien);
		}
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
			rd.forward(request, response);
		}
	}
	
}