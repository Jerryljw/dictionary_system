package dictServer.dictExceptions;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 *
 */
public class NoWordException extends Exception {
    /**
     * Exception for no word in dictionary
     */
    public NoWordException() {
    }

    public NoWordException(String message) {
        super(message);
    }
}
