package fr.prive.gestionSeriesFilm.dal;

public class daoException extends Exception {

	private static final long serialVersionUID = 1L;

	// Constructeurs
	public daoException() {
		super();
	}

	public daoException(String message) {
		super(message);
	}

	public daoException(String message, Throwable exception) {
		super(message, exception);
	}

	// Méthodes
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());
		return sb.toString();
	}
}
