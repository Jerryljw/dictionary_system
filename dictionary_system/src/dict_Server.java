/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 *
 */
import dictServer.DictionaryComp.DictService;
import dictServer.DictionaryComp.DictSystem;
import dictServer.GUI.ServerGUI;
import dictServer.MySocket.MyServerSocket;
import dictServer.dictExceptions.IncorrectPortException;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import static dictServer.Util.Messages.DEFAULT_PORT_NUMBER;
import static dictServer.Util.Messages.DICT_DATA_PATH;

public class dict_Server {
    private static int port;
    private static int clientRunning = 0;
    private static String dictionary_file_path = DICT_DATA_PATH;

    public dict_Server() {
    }

    public static void main(String[] args) throws IOException {
        port = DEFAULT_PORT_NUMBER;//default port number
        dictionary_file_path = DICT_DATA_PATH;
        switch (args.length) {
            case 0:
                // case for default servers
                System.out.println("Server run by default setting");
                System.out.println("port number: " + port);
                break;
            case 2:
                // case of 2 argument
                try {
                    if (Integer.parseInt(args[0]) < 1024 || Integer.parseInt(args[0]) > 65535) {
                        throw new IncorrectPortException();
                    } else {
                        port = Integer.parseInt(args[0]);
                        dictionary_file_path = args[1];
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("argument are not correct, please try again...");
                    System.out.println("With one argument, please Run as -> " +
                            "java –jar DictionaryServer.jar <port> <dictionary-file>");
                    System.out.println("The <port> should be port number that in the interval of [1024, 65535]");
                    System.exit(0);
                } catch (IncorrectPortException ipe) {
                    System.out.println("port number are not correct, please try again...");
                    System.out.println("With two argument, please Run as -> " +
                            "java –jar DictionaryServer.jar <port> <dictionary-file>");
                    System.out.println("The <port> should be port number that in the interval of [1024, 65535]");
                    System.exit(0);
                }
                break;
            default:
                System.out.println("Wrong number of argument...");
                System.out.println("Default running, Please Run as -> " +
                        "java –jar DictionaryServer.jar");
                System.out.println("With two argument, please Run as -> " +
                        "java –jar DictionaryServer.jar <port> <dictionary-file>");
                break;
        }
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            DictService serverDictionary = new DictSystem(dictionary_file_path);
            InetAddress localaddress = InetAddress.getLocalHost();
            //create gui
            ServerGUI serverGUI = new ServerGUI(port, dictionary_file_path,localaddress.getHostAddress());
            JTextField clientNumDisplay = serverGUI.getClientField();
            clientNumDisplay.setText(String.valueOf(clientRunning));
            JTextArea feedbackArea = serverGUI.getFeedbackArea();
            printFeedback("Server Start by Using port -- " + port + ".", feedbackArea);
            printFeedback("Dictionary is loaded from -- " + dictionary_file_path, feedbackArea);
//            feedbackArea.append("Server Start by Using port -- " + String.valueOf(port) + "." + "\n");
//            feedbackArea.append("Dictionary is loaded from -- " + dictionary_file_path + "\n");
//            System.out.println("Server Start by Using port -- " + String.valueOf(port) + ".");
//            System.out.println("Dictionary is loaded from -- " + dictionary_file_path);

            while (true) {
                //waiting for client connection
                MyServerSocket myServerSoc = new MyServerSocket(serverSocket.accept());
                clientRunning++;
                displayClientN(clientNumDisplay, feedbackArea);
                //myServerSoc.setSoTimeout(8000);
                SocketThread clientThread = new SocketThread(myServerSoc, serverDictionary, clientNumDisplay, feedbackArea);
                new Thread(clientThread).start();
            }
        } catch (BindException be) {
            System.out.println("port number of address is being use, try a different port number...");
            System.out.println("The <port> should be port number that in the interval of [1024, 65536]");
            System.exit(0);
        } catch (SocketException se) {
            System.out.println("Socket error happened");
        }
    }
    /**
     * @description: display message to GUI and terminal
     * @param clientNumDisplay the JTextField that show the client numbers
     * @param feedbackArea the area show system response
     * @author: Jiawei Luo
     */
    private static void displayClientN(JTextField clientNumDisplay, JTextArea feedbackArea) {
        printFeedback("New Client connecting...", feedbackArea);
        printFeedback("Client number: " + clientRunning, feedbackArea);
//        System.out.println("New Client connecting...");
//        System.out.println("Client number: " + String.valueOf(clientRunning));
//        feedbackArea.append("New Client connecting..."+"\n");
//        feedbackArea.append("Client number: " + String.valueOf(clientRunning)+"\n");
        clientNumDisplay.setText(String.valueOf(clientRunning));
    }
    /**
     * @description: display message to GUI and terminal
     * @param message message to display
     * @param feedbackArea the area show system response
     * @author: Jiawei Luo
     */
    public static void printFeedback(String message, JTextArea feedbackArea) {
        System.out.println(message);
        feedbackArea.append(message + "\n");
    }
    /**
     * @description: display client number when client close, to GUI and terminal
     * @param clientNumDisplay the JTextField that show the client numbers
     * @param feedbackArea the area show system response
     * @author: Jiawei Luo
     */
    public synchronized void closeClientConnection(JTextField clientNumDisplay, JTextArea feedbackArea) {
        clientRunning--;
        printFeedback("one Client disconnected...", feedbackArea);
        printFeedback("Client number: " + clientRunning, feedbackArea);
//        System.out.println("one Client disconnected...");
//        System.out.println("Client number: "+ String.valueOf(clientRunning));
//        feedbackArea.append("one Client disconnected..."+"\n");
//        feedbackArea.append("Client number: "+ String.valueOf(clientRunning)+"\n");
        clientNumDisplay.setText(String.valueOf(clientRunning));
    }
}
