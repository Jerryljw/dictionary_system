/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */
package dictClient;

import MySocket.MyClientSocket;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import static Util.Messages.*;

public class ClientConnection {

    private final String serverHost;
    private final int serverPort;
    private MyClientSocket clientSoc;

    /**
     * @description: constructor with port number and address
     * @param serverHost the address of server
     * @param serverPort the port of server
     * @author: Jiawei Luo
     */
    public ClientConnection(String serverHost, int serverPort) throws IOException,
            SocketTimeoutException, SocketException {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        clientSoc = new MyClientSocket(serverHost, serverPort);
        clientSoc.setSoTimeout(2000);
    }


    public MyClientSocket getClientSoc() {
        return clientSoc;
    }

    /**
     * @description: generate search request
     * @param word the word to search
     * @return: JSON object with search request
     * @author: Jiawei Luo
     */
    public JSONObject searchRequest(String word) {
        JSONObject jsonRequest = new JSONObject();
        System.out.println("generating search request");
        jsonRequest.put(OPERATION_HEAD, SEARCH_OPERATION);
        jsonRequest.put(JSON_WORD_STRING, word);
        return jsonRequest;
    }

    /**
     * @description: generate add request
     * @param word the word to add
     * @param meaning the meaning of word to add
     * @return: JSON object with add request
     * @author: Jiawei Luo
     */
    public JSONObject addRequest(String word, String meaning) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put(OPERATION_HEAD, ADD_OPERATION);
        jsonRequest.put(JSON_WORD_STRING, word);
        jsonRequest.put(JSON_MEANING_STRING, meaning);
        return jsonRequest;
    }

    /**
     * @description: generate remove request
     * @param word the word to remove
     * @return: JSON object with remove request
     * @author: Jiawei Luo
     */
    public JSONObject removeRequest(String word) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put(OPERATION_HEAD, REMOVE_OPERATION);
        jsonRequest.put(JSON_WORD_STRING, word);
        return jsonRequest;
    }

    /**
     * @description: generate update request
     * @param word the word to update
     * @param meaning the meaning of word to update
     * @return: JSON object with update request
     * @author: Jiawei Luo
     */
    public JSONObject updateRequest(String word, String meaning) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put(OPERATION_HEAD, UPDATE_OPERATION);
        jsonRequest.put(JSON_WORD_STRING, word);
        jsonRequest.put(JSON_MEANING_STRING, meaning);
        return jsonRequest;
    }


}
