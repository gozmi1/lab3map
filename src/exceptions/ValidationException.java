package exceptions;

/**
 * Excepție personalizată aruncată atunci când o entitate nu trece de validare.
 */
public class ValidationException extends RuntimeException{
    /**
     * Construiește o nouă excepție de validare.
     * @param mesaj Mesajul detaliat al erorii.
     */
    public ValidationException(String mesaj) {
        super(mesaj);
    }
}
