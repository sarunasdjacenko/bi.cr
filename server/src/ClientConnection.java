import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class ClientConnection implements Runnable {
    private DataInputStream dataInputStream;
    private Socket clientSocket;
    private Debate debate;
    private Server server;
    private String username;

    public ClientConnection(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void setDebate(Debate debate) { this.debate = debate; }

    public void handleResponse(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        String requestType = unJSONer.get("requestType").toString();

        switch(requestType) {
            case "messageDebate":
                debate.sendMessage(unJSONer.get("message").toString(), unJSONer.get("username").toString(), (boolean) unJSONer.get("isForArgument"));
                break;
            case "createDebate":
                server.createDebate(unJSONer.get("debateName").toString());
                server.joinDebate(unJSONer.get("debateName").toString(), unJSONer.get("username").toString(), (boolean) unJSONer.get("isForArgument"));
                break;
            case "joinDebate":
                server.joinDebate(unJSONer.get("debateName").toString(), unJSONer.get("username").toString(), (boolean) unJSONer.get("isForArgument"));
                break;
            case "leaveDebate":
                server.leaveDebate(debate, unJSONer.get("username").toString());
                break;
            case "getDebates":
                server.sendDebates(clientSocket);
                break;
            case "setUsername":
                username = unJSONer.get("username").toString();
                server.addToLobby(username, this);
                break;
        }
    }

    public String getUsername() { return username; }

    public Socket getClientSocket() { return clientSocket; }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                String inputMessage = "";
                inputMessage = dataInputStream.readUTF();
                handleResponse(inputMessage);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}