package MySocket;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class MyClientSocket extends Socket {
    private Socket clientSoc;
    private DataInputStream Input;
    private DataOutputStream Output;

    /**
     * @description: the constructor with host address and port
     * @param serverHost the server address
     * @param port the port number
     * @author: Jiawei Luo
     */
    public MyClientSocket(String serverHost, int port) throws IOException, SocketTimeoutException {
        this.clientSoc = new Socket();
        this.clientSoc.connect(new InetSocketAddress(serverHost, port), 2000);
        Input = new DataInputStream(clientSoc.getInputStream());
        Output = new DataOutputStream(clientSoc.getOutputStream());
    }

    /**
     * @description: the constructor with a socket
     * @param serverSoc
     * @author: Jiawei Luo
     */
    public MyClientSocket(Socket serverSoc) throws IOException {
        this.clientSoc = serverSoc;
        Input = new DataInputStream(clientSoc.getInputStream());
        Output = new DataOutputStream(clientSoc.getOutputStream());

    }

    /**
     * @description: send message
     * @param request the request String
     * @author: Jiawei Luo
     */
    public void send(String request) throws IOException {
        System.out.println("sending search request");
        Output.writeUTF(request);
        Output.flush();
    }

    /**
     * @description: read message
     * @return: String the string of message from socket
     * @author: Jiawei Luo
     */
    public String read() throws IOException, SocketException {
        return Input.readUTF();
    }

    /**
     * @description: close the socket
     * @author: Jiawei Luo
     */
    public void close() throws IOException {
        clientSoc.close();
        Input.close();
        Output.close();
    }

}
