package dictServer.MySocket;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyServerSocket extends Socket {
    private final Socket serverSoc;
    private final DataInputStream Input;
    private final DataOutputStream Output;

    /**
     * @description: the constructor with host address and port
     * @param serverSoc the socket
     * @author: Jiawei Luo
     */
    public MyServerSocket(Socket serverSoc) throws IOException {
        this.serverSoc = serverSoc;
        Input = new DataInputStream(serverSoc.getInputStream());
        Output = new DataOutputStream(serverSoc.getOutputStream());
        //serverSoc.setSoTimeout(30000);
    }

    /**
     * @description: send message
     * @param request the request String
     * @author: Jiawei Luo
     */
    public void send(String request) throws IOException {
        Output.writeUTF(request);
        Output.flush();
    }

    /**
     * @description: read message
     * @return: String the string of message from socket
     * @author: Jiawei Luo
     */
    public String read() throws IOException {
        return Input.readUTF();
    }

    /**
     * @description: close the socket
     * @author: Jiawei Luo
     */
    public void close() throws IOException {
        serverSoc.close();
        Input.close();
        Output.close();
    }
}
