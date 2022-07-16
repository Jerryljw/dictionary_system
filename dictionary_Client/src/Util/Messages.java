package Util;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 *
 */
public class Messages {
    // default value set
    public final static int DEFAULT_PORT_NUMBER = 10111;
    public final static String DEFAULT_HOST = "localhost";
    // data path
    public final static String DICT_DATA_PATH = "src\\dictServer\\dictionaryData\\dictionarydata.json";
    // message to client
    public final static String NO_WORD_MESSAGE = "Word is not found in dictionary";
    public final static String WORD_EXIST_MESSAGE = "Word is already exist in dictionary";
    // json to client message component
    public final static String JSON_WORD_STRING = "word";
    public final static String JSON_MEANING_STRING = "meaning";
    public final static String RESPONSE_STATUS = "status";
    public final static String SUCCESS_STATUS = "success";
    public final static String FAIL_STATUS = "fail";
    public final static String RESPONSE_CONTENT = "content";
    // json from client message component
    public final static String OPERATION_HEAD = "operation";
    public final static String ADD_OPERATION = "add";
    public final static String REMOVE_OPERATION = "remove";
    public final static String SEARCH_OPERATION = "search";
    public final static String UPDATE_OPERATION = "update";
}
