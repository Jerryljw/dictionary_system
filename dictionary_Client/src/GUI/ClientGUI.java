package GUI;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import MySocket.MyClientSocket;
import dictClient.ClientConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

import static Util.Messages.*;

public class ClientGUI {
    private JTextArea meaningArea;
    private JButton removeButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton addButton;
    private JPanel panel1;
    private JTextArea responseArea;
    private JTextField wordField;

    private MyClientSocket myClientSocket;
    private ClientConnection clientConnection;
    private JFrame jFrame;

    public ClientGUI() {
    }

    /**
     * @description: create a client GUI
     * @param clientConnection the connection object to build the client socket
     * @param myClientSocket the client socket
     * @author: Jiawei Luo
     */
    public ClientGUI(ClientConnection clientConnection, MyClientSocket myClientSocket) {
        this.clientConnection = clientConnection;
        this.myClientSocket = myClientSocket;
        //set frame
        JFrame frame = new JFrame("ClientGUI");

        frame.setLocation(500, 300);
        //set panel

        // set search
        setSearchButton();
        setAddButton();
        setRemoveButton();
        setUpdateButton();
        // set text fields and areas
        meaningArea.setLineWrap(true);
        meaningArea.setWrapStyleWord(true);
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");
        frame.setSize(1380, 960);
        frame.setResizable(false);
        JPanel panel = new ClientGUI().panel1;
        panel.setSize(1380, 960);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @description: the setting of Search Button on GUI,
     *               do action to parse the message from server,
     *               display the result
     *
     * @author: Jiawei Luo
     */
    private void setSearchButton() {
        this.searchButton.addActionListener(e -> {
            String word = this.wordField.getText();
            if (word.isEmpty()) {
                responseArea.setText("Please enter you word");
            } else {
                try {
                    responseArea.setText("sending request");
                    String requestToServer = this.clientConnection.searchRequest(word).toString();
                    this.myClientSocket.send(requestToServer);
                    responseArea.setText("request sent...");
                    String response = myClientSocket.read();
                    System.out.println("get response from server: " + response);
                    responseArea.setText("response received");
                    JSONObject JsonResponse = (JSONObject) new JSONParser().parse(response);
                    String statusMessage = (String) JsonResponse.get(RESPONSE_STATUS);
                    if (statusMessage.equals(SUCCESS_STATUS)) {
                        meaningArea.setText("");
                        meaningArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                        responseArea.setText("word meaning searched successfully");

                    } else if (statusMessage.equals(FAIL_STATUS)) {
                        meaningArea.setText("");
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else {
                        System.out.println(statusMessage);
                        responseArea.setText("No response");
                    }
                } catch (NullPointerException npe) {
                    responseArea.setText("invalid response");
                } catch (IOException ioe) {
                    System.out.println("IO error...Server may be closed, try reopen client after server runs...");
                    System.exit(0);
                } catch (ParseException pe) {
                    responseArea.setText("invalid response");
                }
            }
        });
    }

    /**
     * @description: the setting of Add Button on GUI,
     *               do action to parse the message from server,
     *               display the result
     *
     * @author: Jiawei Luo
     */
    private void setAddButton() {
        this.addButton.addActionListener(e -> {
            String word = this.wordField.getText();
            String meaning = this.meaningArea.getText();
            if (word.isEmpty() || meaning.isEmpty()) {
                responseArea.setText("Please enter both your word and meaning");
            } else {
                try {
                    responseArea.setText("sending request");
                    String requestToServer = this.clientConnection.addRequest(word, meaning).toString();
                    this.myClientSocket.send(requestToServer);
                    responseArea.setText("request sent...");
                    String response = myClientSocket.read();
                    System.out.println("get response from server: " + response);
                    responseArea.setText("response received");
                    JSONObject JsonResponse = (JSONObject) new JSONParser().parse(response);
                    String statusMessage = (String) JsonResponse.get(RESPONSE_STATUS);

                    if (statusMessage.equals(SUCCESS_STATUS)) {
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else if (statusMessage.equals(FAIL_STATUS)) {
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else {
                        System.out.println(statusMessage);
                        responseArea.setText("No response");
                    }
                } catch (NullPointerException npe) {
                    responseArea.setText("invalid response");
                } catch (IOException ioe) {
                    System.out.println("IO error...Server may be closed, try reopen client after server runs...");
                    System.exit(0);
                } catch (ParseException pe) {
                    responseArea.setText("invalid response");
                }
            }
        });
    }

    /**
     * @description: the setting of Update Button on GUI,
     *               do action to parse the message from server,
     *               display the result
     *
     * @author: Jiawei Luo
     */
    private void setUpdateButton() {
        this.updateButton.addActionListener(e -> {
            String word = this.wordField.getText();
            String meaning = this.meaningArea.getText();
            if (word.isEmpty() || meaning.isEmpty()) {
                responseArea.setText("Please enter both your word and meaning");
            } else {
                try {
                    responseArea.setText("sending request");
                    String requestToServer = this.clientConnection.updateRequest(word, meaning).toString();
                    this.myClientSocket.send(requestToServer);
                    responseArea.setText("request sent...");
                    String response = myClientSocket.read();
                    System.out.println("get response from server: " + response);
                    responseArea.setText("response received");
                    JSONObject JsonResponse = (JSONObject) new JSONParser().parse(response);
                    String statusMessage = (String) JsonResponse.get(RESPONSE_STATUS);

                    if (statusMessage.equals(SUCCESS_STATUS)) {
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else if (statusMessage.equals(FAIL_STATUS)) {
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else {
                        System.out.println(statusMessage);
                        responseArea.setText("No response");
                    }
                } catch (NullPointerException npe) {
                    responseArea.setText("invalid response");
                } catch (IOException ioe) {
                    System.out.println("IO error...Server may be closed, try reopen client after server runs...");
                    System.exit(0);
                } catch (ParseException pe) {
                    responseArea.setText("invalid response");
                }
            }
        });
    }

    /**
     * @description: the setting of Remove Button on GUI,
     *               do action to parse the message from server,
     *               display the result
     *
     * @author: Jiawei Luo
     */
    private void setRemoveButton() {
        this.removeButton.addActionListener(e -> {
            String word = this.wordField.getText();
            if (word.isEmpty()) {
                responseArea.setText("Please enter you word");
            } else {
                try {
                    responseArea.setText("sending request");
                    String requestToServer = this.clientConnection.removeRequest(word).toString();
                    this.myClientSocket.send(requestToServer);
                    responseArea.setText("request sent...");
                    String response = myClientSocket.read();
                    System.out.println("get response from server: " + response);
                    responseArea.setText("response received");
                    JSONObject JsonResponse = (JSONObject) new JSONParser().parse(response);
                    String statusMessage = (String) JsonResponse.get(RESPONSE_STATUS);
                    if (statusMessage.equals(SUCCESS_STATUS)) {
                        meaningArea.setText("");
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else if (statusMessage.equals(FAIL_STATUS)) {
                        meaningArea.setText("");
                        responseArea.setText((String) JsonResponse.get(RESPONSE_CONTENT));
                    } else {
                        System.out.println(statusMessage);
                        responseArea.setText("No response");
                    }
                } catch (NullPointerException npe) {
                    responseArea.setText("invalid response");
                } catch (IOException ioe) {
                    System.out.println("IO error...Server may be closed, try reopen client after server runs...");
                    System.exit(0);
                } catch (ParseException pe) {
                    responseArea.setText("invalid response");
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
