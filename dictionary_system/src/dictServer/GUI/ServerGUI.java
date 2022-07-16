package dictServer.GUI;
/**
 * @Author Jiawei Luo
 * @Student ID: 1114028
 */

import javax.swing.*;

public class ServerGUI {
    private JTextField clientField;
    private JTextField portField;
    private JPanel panel1;
    private JTextField dictPathField;
    private JTextArea feedbackArea;
    private JTextField addressField;
    private int port;
    private String dict_path;
    private String address;

    /**
     * @description: empty constructor
     * @author: Jiawei Luo
     */
    public ServerGUI() {
    }

    /**
     * @description:
     * @param port the port number to display
     * @param dict_path the file path to the data file
     * @param address the address of host
     * @author: Jiawei Luo
     */
    public ServerGUI(int port, String dict_path, String address) {
        this.port = port;
        this.dict_path = dict_path;
        this.address = address;
        JFrame frame = new JFrame("ServerGUI");
        frame.setLocation(500, 300);
        panel1.setSize(600, 400);
        feedbackArea.setLineWrap(true);
        feedbackArea.setAutoscrolls(true);
        dictPathField.setAutoscrolls(true);
        portField.setText("port: " + port);
        dictPathField.setText(dict_path);
        addressField.setText(address);

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientGUI");
        frame.setResizable(true);
        JPanel panel = new ServerGUI().panel1;
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JTextField getClientField() {
        return clientField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JTextField getDictPathField() {
        return dictPathField;
    }

    public JTextArea getFeedbackArea() {
        return feedbackArea;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
