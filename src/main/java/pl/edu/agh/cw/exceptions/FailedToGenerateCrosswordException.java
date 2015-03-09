package pl.edu.agh.cw.exceptions;

/**
 * Klasa wyj�tku zwi�zana z b��dem przy generowaniu krzy��wki
 * @author JakubSzczepankiewicz
 *
 */
public class FailedToGenerateCrosswordException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Konstruktor
	 */
	public FailedToGenerateCrosswordException(){
		super();
	}
	
	/**
	 * Konstruktor
	 * @param message - wiadomo�� o b��dzie
	 */
	public FailedToGenerateCrosswordException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor
	 * @param message - wiadomo�� o b��dzie
	 * @param cause - przyczyna
	 */
	public FailedToGenerateCrosswordException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Konstruktor
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FailedToGenerateCrosswordException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
