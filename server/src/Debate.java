import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Debate implements Runnable {
    private int serverPort = 7777;

    private String name;
    private List<Socket> connectedClients;
    private List<String> messages;
    private ServerSocket serverSocket;
    private DataOutputStream dataOutputStream;

    public Debate(String name) {
        this.name = name;
        connectedClients = new ArrayList<>();
        messages = new LinkedList<>();
    }

    public void addClient(Socket newClient) {
        connectedClients.add(newClient);
    }

    public List<Socket> getConnectedClients() {
        return connectedClients;
    }

    public void sendMessageToClient(String message) {
        for(Socket client : connectedClients) {
            try {
                dataOutputStream = new DataOutputStream(client.getOutputStream());
                dataOutputStream.writeUTF(message);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("~~~~ DEBATE CREATED ~~~~");

        while(true) {

        }

    }

    public static void main(String args[]) {
        Debate debate = new Debate("Test");
        new Thread(debate).start();
    }
}
