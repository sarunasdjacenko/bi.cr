import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class Server implements Runnable {
    private int serverPort = 7777;
    private ServerSocket serverSocket;
    private List<Socket> clientsInLobby;
    private Debate debate;

    public Server() {
        debate = new Debate("K-pop");
        clientsInLobby = new ArrayList<Socket>();
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
                clientsInLobby.add(clientSocket);
                debate.addClient(clientSocket, true);

                //System.out.println("\n --- NEW CLIENT HAS CONNECTED TO THE SERVER ---");
				//System.out.println("\nCLIENT'S IP ADDRESS: " + clientSocket.getInetAddress().getHostAddress());

                ClientConnection client = new ClientConnection(clientSocket, this);
                //
                client.setDebate(debate);
                //
                new Thread(client).start();
                //Runnable client = new ClientConnection(clientSocket)
                
			} catch (Exception e) {}
		}
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
        //Runnable server = new Server();
    }
}