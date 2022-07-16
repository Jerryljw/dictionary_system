package dictServer.dictExceptions;

/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */
public class IncorrectPortException extends Exception {
    /**
     * Exception for throw bad port numbers
     */
    public IncorrectPortException() {
    }

    public IncorrectPortException(String message) {
        super(message);
    }
}
