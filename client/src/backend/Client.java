package backend;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;

public class Client {
    private String username;

    private String serverIP;
    private int serverPort;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    public Client(String username, String serverIP, int serverPort) {
        this.username = username;
        this.serverIP = serverIP;
        this.serverPort = serverPort;

        try {
            socket = new Socket(serverIP, serverPort);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sendMessage("test");

        while(true) {
            String inputMessage = "";
            try {
                inputMessage = dataInputStream.readUTF();
                System.out.println(inputMessage);
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }
    }

    private void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessageDebate(String message) {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("messageDebate")
                    .key("userName").value(username)
                    .key("message").value(message)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendCreateDebate(String debateName, boolean forDebate) {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("createDebate")
                    .key("debateName").value(debateName)
                    .key("userName").value(username)
                    .key("forDebate").value(forDebate)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendJoinDebate(String debateName, boolean forDebate) {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("joinDebate")
                    .key("debateName").value(debateName)
                    .key("userName").value(username)
                    .key("forDebate").value(forDebate)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendExit() {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("exitToLobby")
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public String[] deJSON(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        String[] nameMessage = new String[2];
        nameMessage[0] = unJSONer.get("name").toString();
        nameMessage[1] = unJSONer.get("message").toString();
        return nameMessage;
    }
}
