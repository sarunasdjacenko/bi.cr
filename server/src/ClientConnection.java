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

    public ClientConnection(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void setDebate(Debate debate) {
        this.debate = debate;
        //System.out.println("test set debate");
    }

    public void handleResponse(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        String requestType = unJSONer.get("requestType").toString();

        switch(requestType) {
            case "messageDebate":
                debate.sendMessage(unJSONer.get("message").toString());
                break;
            //case "createDebate": 
            //case "joinDebate": 
            //case "exitToLobby": 
        }
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                String inputMessage = "";
                inputMessage = dataInputStream.readUTF();
                //debate.sendMessage(inputMessage);
                //System.out.println(inputMessage);
                handleResponse(inputMessage);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}