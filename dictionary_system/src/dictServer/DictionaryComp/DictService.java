package dictServer.DictionaryComp;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import dictServer.dictExceptions.NoWordException;
import dictServer.dictExceptions.WordExistException;

public interface DictService {
    /**
     * @description: search operation
     * @param word the word to search
     * @return: String the searched word
     * @author: Jiawei Luo
     */
    String search(String word) throws NoWordException;

    /**
     * @description: remove operation
     * @param word the word to remove
     * @author: Jiawei Luo
     */
    void remove(String word) throws NoWordException;

    /**
     * @description: add operation
     * @param word the word to add
     * @param meaning the meaning of the word
     * @author: Jiawei Luo
     */
    void add(String word, String meaning) throws WordExistException;

    /**
     * @description: save the current dictionary to file path
     * @author: Jiawei Luo
     */
    void save();

    /**
     * @description: update operation
     * @param word  the word to update
     * @param meaning the meaning of the word
     * @author: Jiawei Luo
     */
    void update(String word, String meaning) throws NoWordException;

}
