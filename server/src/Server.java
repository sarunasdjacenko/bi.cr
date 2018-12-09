import org.json.JSONWriter;
import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Server implements Runnable {
    private int serverPort = 7777;
    private ServerSocket serverSocket;
    private Map<String, ClientConnection> clientsInLobby;
    private Map<String, Debate> debates;

    public Server() {
        clientsInLobby = new HashMap<String, ClientConnection>();
        debates = new HashMap<String, Debate>();
    }

    public void addToLobby(String username, ClientConnection client) { clientsInLobby.put(username, client); System.out.println("User created: " + username); }

    public void sendDebates(Socket clientSocket) {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
            .object()
                .key("requestType").value("getDebates")
                .key("debates").value(new JSONArray(debates.keySet()))
            .endObject();

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF(JSONMessage.toString());
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void createDebate(String debateName) {
        if (!debates.containsKey(debateName)) {
            Debate debate = new Debate(debateName);
            debates.put(debateName, debate);
        }
    }

    public void joinDebate(String debateName, String username, Boolean isForArgument) {
        ClientConnection client = clientsInLobby.get(username);
        clientsInLobby.remove(username);
        Debate debate = debates.get(debateName);
        debate.addClient(client, username, isForArgument);
    }

    public void leaveDebate(Debate debate, String username) {
        ClientConnection client = debate.getClient(username);
        debate.removeClient(client, username);
        clientsInLobby.put(username, client);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
		} catch (Exception e) {
            System.out.println("Failed to create server.");
            System.exit(1);
        }

		System.out.println("--- SERVER CREATED ---");
		while(true) {
			try {
                Socket clientSocket = serverSocket.accept();
                
                System.out.println("\n --- NEW CLIENT HAS CONNECTED TO THE SERVER ---");
				System.out.println("\nCLIENT'S IP ADDRESS: " + clientSocket.getInetAddress().getHostAddress());

                ClientConnection client = new ClientConnection(clientSocket, this);

                new Thread(client).start();
                
			} catch (Exception e) {}
		}
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }
}