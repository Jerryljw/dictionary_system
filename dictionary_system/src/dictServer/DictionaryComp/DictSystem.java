package dictServer.DictionaryComp;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import dictServer.dictExceptions.NoWordException;
import dictServer.dictExceptions.WordExistException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class DictSystem implements DictService {
    private JSONObject dictionary;
    private String filePath;

    public DictSystem(JSONObject dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * @description: constructor to do operation to dictionary
     * @param filePath the path to dictionary file
     * @author: Jiawei Luo
     */
    public DictSystem(String filePath) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        this.filePath = filePath;
        try (FileReader reader = new FileReader(filePath)) {
            //Read JSON file
            this.dictionary = (JSONObject) jsonParser.parse(reader);
            System.out.println("dictionary: " + this.dictionary.toString());
        } catch (ParseException e) {
            System.out.println("JSON parse error in dictionary system...");
            System.out.println("Please use a correct JSON file with format: {\"word\":\"meaning\"}");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found in " + filePath);
            System.out.println("Please use a correct JSON file with format: {\"word\":\"meaning\"}");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("I/O operations interrupted in loading dictionary.");
            System.out.println("Please use a correct JSON file with format: {\"word\":\"meaning\"}");
            System.exit(0);
        }
    }

    /**
     * @description: search operation
     * @param word the word to search
     * @return: String the searched word
     * @author: Jiawei Luo
     */
    @Override
    public synchronized String search(String word) throws NoWordException {
        if (dictionary.containsKey(word)) {
            return dictionary.get(word).toString();
        } else {
            throw new NoWordException();
        }
    }

    /**
     * @description: remove operation
     * @param word the word to remove
     * @author: Jiawei Luo
     */
    @Override
    public synchronized void remove(String word) throws NoWordException {
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
        } else {
            throw new NoWordException();
        }
    }

    /**
     * @description: update operation
     * @param word  the word to update
     * @param meaning the meaning of the word
     * @author: Jiawei Luo
     */
    @Override
    public synchronized void update(String word, String meaning) throws NoWordException {
        if (dictionary.containsKey(word)) {
            dictionary.remove(word);
            dictionary.put(word, meaning);
        } else {
            throw new NoWordException();
        }
    }

    /**
     * @description: add operation
     * @param word the word to add
     * @param meaning the meaning of the word
     * @author: Jiawei Luo
     */
    @Override
    public synchronized void add(String word, String meaning) throws WordExistException {
        if (dictionary.containsKey(word)) {
            throw new WordExistException();
        } else {
            dictionary.put(word, meaning);
        }
    }

    /**
     * @description: save the current dictionary to file path
     * @author: Jiawei Luo
     */
    @Override
    public synchronized void save() {
        try {
            FileWriter writer = new FileWriter(new File(filePath), false);
            //System.out.println(dictionary.toJSONString());
            writer.write(dictionary.toJSONString());
            writer.close();
        } catch (IOException e) {
            System.out.println("I/O operations interrupted in saving dictionary.");
        }
    }
}
