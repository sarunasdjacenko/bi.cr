import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList; 
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class Debate {
    private String debateName; //not doing anything yet

    private Map<String, ClientConnection> forArgument;
    private Map<String, ClientConnection> againstArgument;
    private List<String> messagesList;

    public Debate(String debateName) {
        this.debateName = debateName;
        forArgument = new HashMap<>();
        againstArgument = new HashMap<>();
        messagesList = new LinkedList<>();
    }

    public void addClient(ClientConnection client, String username, boolean isForArgument) {
        client.setDebate(this);

        if (isForArgument) forArgument.put(username, client);
        else againstArgument.put(username, client);
    }

    public void removeClient(ClientConnection client, String username) {
        client.setDebate(null);

        forArgument.remove(username);
        againstArgument.remove(username);
    }

    public ClientConnection getClient(String username) {
        if (forArgument.get(username) != null) return forArgument.get(username);
        else return againstArgument.get(username);
    }

    public void sendMessage(String message, String username, boolean isForArgument) {
        // append time to message
        messagesList.add(message);

        for(ClientConnection client : forArgument.values()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(client.getClientSocket().getOutputStream());
                dataOutputStream.writeUTF(message);
            } catch(Exception e) { e.printStackTrace(); }
        }
        for(ClientConnection client : againstArgument.values()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(client.getClientSocket().getOutputStream());
                dataOutputStream.writeUTF(message);
            } catch(Exception e) { e.printStackTrace(); }
        }
    }
}
