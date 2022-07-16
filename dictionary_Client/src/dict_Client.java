/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import GUI.ClientGUI;
import dictClient.ClientConnection;
import dictExceptions.IncorrectPortException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static Util.Messages.DEFAULT_HOST;
import static Util.Messages.DEFAULT_PORT_NUMBER;

public class dict_Client {
    private static int port;
    private static String ServerAddress;

    public static void main(String args[]) throws IOException {
        port = DEFAULT_PORT_NUMBER;//default port number
        ServerAddress = DEFAULT_HOST;
        switch (args.length) {
            case 0:
                // case for default servers
                System.out.println("Client run by default setting");
                System.out.println("port number: " + port);
                break;
            case 2:
                // case of 2 argument
                try {
                    if (Integer.parseInt(args[1]) < 1024 || Integer.parseInt(args[1]) > 65535) {
                        throw new IncorrectPortException();
                    } else {
                        port = Integer.parseInt(args[1]);
                        ServerAddress = args[0];
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("argument are not correct, please try again...");
                    System.out.println("With two argument, please Run as -> " +
                            "java –jar DictionaryClient.jar <server-address> <server-port>");
                    System.out.println("The <port> should be port number that in the interval of [1024, 65535]");
                    System.exit(0);
                } catch (IncorrectPortException ipe) {
                    System.out.println("port number are not correct, please try again...");
                    System.out.println("With two argument, please Run as -> " +
                            "java –jar DictionaryClient.jar <server-address> <server-port>");
                    System.out.println("The <port> should be port number that in the interval of [1024, 65535]");
                    System.exit(0);
                }
                break;
            default:
                System.out.println("Wrong number of argument...");
                System.out.println("Default running, Please Run as -> " +
                        "java –jar DictionaryClient.jar");
                System.out.println("With two argument, please Run as -> " +
                        "java –jar DictionaryClient.jar <server-address> <server-port>");
                break;
        }

        // Get an input file handle from the socket and read the input
        try {
            ClientConnection clientConnection = new ClientConnection(ServerAddress, port);
            ClientGUI GUI1 = new ClientGUI(clientConnection, clientConnection.getClientSoc());
        } catch (ConnectException | SocketTimeoutException ce) {
            System.out.println("Server is not reachable, try again after server runs...");
        } catch (SocketException | UnknownHostException se) {
            System.out.println("Network is unreachable, please use correct address...");
        }
    }
}
