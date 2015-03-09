package pl.edu.agh.cw.exceptions;

/**
 * Klasa wyj¹tku zwi¹zana z b³êdem przy generowaniu krzy¿ówki
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
	 * @param message - wiadomoœæ o b³êdzie
	 */
	public FailedToGenerateCrosswordException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor
	 * @param message - wiadomoœæ o b³êdzie
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
