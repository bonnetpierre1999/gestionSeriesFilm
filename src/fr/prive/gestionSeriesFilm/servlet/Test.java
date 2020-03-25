package fr.prive.gestionSeriesFilm.servlet;

import java.io.IOException;

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
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Serie s1 = new Serie("NCIS", 2003);
			daoMysql.ajouterSerie(s1);
			Saison s2 = new Saison(s1, 1);
			daoMysql.ajouterSaison(s2);
			Episode s3 = new Episode(s2, 1, true);
			daoMysql.ajouterEpisode(s3); 
			
		} catch (daoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
