/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import dictServer.DictionaryComp.DictService;
import dictServer.MySocket.MyServerSocket;
import dictServer.dictExceptions.NoWordException;
import dictServer.dictExceptions.WordExistException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketTimeoutException;

import static dictServer.Util.Messages.*;

public class SocketThread implements Runnable {
    private MyServerSocket serverSoc;
    private DictService dictService;
    private Boolean isAlive;
    private dict_Server dict_server;
    private JTextField clientNumDisplay;
    private JTextArea feedbackArea;


    public SocketThread(MyServerSocket serverSoc, DictService dictService, JTextField clientNumDisplay, JTextArea feedbackArea) {
        this.serverSoc = serverSoc;
        this.dictService = dictService;
        this.isAlive = true;
        this.clientNumDisplay = clientNumDisplay;
        this.feedbackArea = feedbackArea;
        this.dict_server = new dict_Server();
    }

    @Override
    public void run() {
        dict_Server.printFeedback("client are connecting...", feedbackArea);
        //System.out.println("client are connecting...");
        try {
            while (true) {
                String clientMessage = null;
                try {
                    clientMessage = serverSoc.read();
                } catch (SocketTimeoutException stoe) {
                    dict_Server.printFeedback("Client socket connect time out...", feedbackArea);
//                    System.out.println("No Client connect, time out...");
//                    feedbackArea.append("No Client connect, time out..." + "\n");
                    break;
                }
                // get request from client
                JSONObject clientJson = null;
                JSONParser clientJParser = new JSONParser();
                try {
                    clientJson = (JSONObject) clientJParser.parse(clientMessage);
                }
                //String jsonString = "{\"Name\":\"Raja\",\"EmployeeId\":\"115\",\"Age\":\"30\"}";
                catch (ParseException pe) {
                    dict_Server.printFeedback("UNKNOWN JSON parse error...", feedbackArea);
                    //System.out.println("UNKNOWN JSON parse error...");
                }
                dict_Server.printFeedback("received request from Client" + clientJson.toString(), feedbackArea);
                //System.out.println("received request from Client" + clientJson.toString());
                String Operation = (String) clientJson.get(OPERATION_HEAD);
                if (ADD_OPERATION.equals(Operation)) {
                    add(clientJson);
                } else if (SEARCH_OPERATION.equals(Operation)) {
                    search(clientJson);
                } else if (REMOVE_OPERATION.equals(Operation)) {
                    remove(clientJson);
                } else if (UPDATE_OPERATION.equals(Operation)) {
                    update(clientJson);
                } else {
                    dict_Server.printFeedback("Wrong operation from client...", feedbackArea);
                    //System.out.println("Wrong operation from client...");
                }
            }
            serverSoc.close();
            this.isAlive = false;
        } catch (IOException ioe) {
            dict_server.closeClientConnection(clientNumDisplay, feedbackArea);
            dict_Server.printFeedback("I/O operations interrupted, Client closed.", feedbackArea);
            //System.out.println("I/O operations interrupted, Client closed.");
        } catch (NullPointerException npe) {
            dict_Server.printFeedback("I/O operations closed, Client closed.", feedbackArea);
            //System.out.println("I/O operations closed, Client closed.");
        } finally {

        }
    }

    public Boolean getAlive() {
        return isAlive;
    }

    /**
     * @description: generate response of add and send message
     * @param clientJson the message from client
     * @author: Jiawei Luo
     */
    private void add(JSONObject clientJson) throws IOException {
        String word;
        String meaning;
        JSONObject serverJson = new JSONObject();
        try {
            word = (String) clientJson.get(JSON_WORD_STRING);
            meaning = (String) clientJson.get(JSON_MEANING_STRING);
            dictService.add(word, meaning);
            serverJson.put(RESPONSE_STATUS, SUCCESS_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word added successfully...");
            dictService.save();
        } catch (WordExistException e) {
            serverJson.put(RESPONSE_STATUS, FAIL_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word already exist in dictionary...");
        } finally {
            serverSoc.send(serverJson.toString());
        }
    }

    /**
     * @description: generate response of search and send message
     * @param clientJson the message from client
     * @author: Jiawei Luo
     */
    private void search(JSONObject clientJson) throws IOException {
        String word;
        String meaning;
        JSONObject serverJson = new JSONObject();
        try {
            word = (String) clientJson.get(JSON_WORD_STRING);
            meaning = dictService.search(word);
            serverJson.put(RESPONSE_STATUS, SUCCESS_STATUS);
            serverJson.put(RESPONSE_CONTENT, meaning);
            dictService.save();
        } catch (NoWordException e) {
            serverJson.put(RESPONSE_STATUS, FAIL_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word not exist in dictionary...");
        } finally {
            serverSoc.send(serverJson.toString());
        }
    }

    /**
     * @description: generate response of remove and send message
     * @param clientJson the message from client
     * @author: Jiawei Luo
     */
    private void remove(JSONObject clientJson) throws IOException {
        JSONObject serverJson = new JSONObject();
        try {
            String word = (String) clientJson.get(JSON_WORD_STRING);
            dictService.remove(word);
            serverJson.put(RESPONSE_STATUS, SUCCESS_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word removed successfully...");
            dictService.save();
        } catch (NoWordException e) {
            serverJson.put(RESPONSE_STATUS, FAIL_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word not exist in dictionary...");
        } finally {
            serverSoc.send(serverJson.toString());
        }
    }

    /**
     * @description: generate response of update and send message
     * @param clientJson the message from client
     * @author: Jiawei Luo
     */
    private void update(JSONObject clientJson) throws IOException {
        String word;
        String meaning;
        JSONObject serverJson = new JSONObject();
        try {
            word = (String) clientJson.get(JSON_WORD_STRING);
            meaning = (String) clientJson.get(JSON_MEANING_STRING);
            dictService.update(word, meaning);
            serverJson.put(RESPONSE_STATUS, SUCCESS_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word updated successfully...");
            dictService.save();
        } catch (NoWordException e) {
            serverJson.put(RESPONSE_STATUS, FAIL_STATUS);
            serverJson.put(RESPONSE_CONTENT, "word not exist in dictionary...");
        } finally {
            serverSoc.send(serverJson.toString());
        }
    }
}
