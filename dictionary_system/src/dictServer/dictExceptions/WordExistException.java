package dictServer.dictExceptions;

/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */
public class WordExistException extends Exception {
    /**
     * Exception for the word is already in dictionary
     */
    public WordExistException(String message) {
        super(message);
    }

    public WordExistException() {
    }
}
