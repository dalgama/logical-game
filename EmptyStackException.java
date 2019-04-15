
/**
 * An object of the class <code>EmptyStackException</code> is thrown by the
 * methods of a <code>Stack</code> to indicate the illegal situations where a
 * stack was empty.
 */
@SuppressWarnings("serial")

public class EmptyStackException extends RuntimeException {

    public EmptyStackException(String message) {
        super(message) ;
    }
}
