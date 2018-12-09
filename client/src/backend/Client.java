package backend;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.util.LinkedList;

public class Client {
    private String username;
    private boolean currentForStatus;

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

        setUsername();
    }

    public void test() {
        while(true) {
            String inputMessage = "";
            try {
                inputMessage = dataInputStream.readUTF();
                switch(getReturnType(inputMessage)) {
                    case("messageDebate"):
                        deJSONmessage(inputMessage);
                        break;
                    case("getDebates"):
                        deJSONDebates(inputMessage);
                        break;
                }
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
                    .key("username").value(username)
                    .key("message").value(message)
                    .key("isForArgument").value(currentForStatus)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendCreateDebate(String debateName, boolean forDebate) {
        currentForStatus = forDebate;
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("createDebate")
                    .key("debateName").value(debateName)
                    .key("username").value(username)
                    .key("isForArgument").value(forDebate)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendJoinDebate(String debateName, boolean forDebate) {
        currentForStatus = forDebate;
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("joinDebate")
                    .key("debateName").value(debateName)
                    .key("username").value(username)
                    .key("isForArgument").value(forDebate)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void sendExit() {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("leaveDebate")
                    .key("username").value(username)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public void getDebates() {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("getDebates")
                    .key("username").value(username)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    private void setUsername() {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("requestType").value("setUsername")
                    .key("username").value(username)
                .endObject();
        sendMessage(JSONMessage.toString());
    }

    public String[] deJSONmessage(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        String[] nameMessage = new String[2];
        nameMessage[0] = unJSONer.get("username").toString();
        nameMessage[1] = unJSONer.get("message").toString();
        return nameMessage;
    }

    public LinkedList<String> deJSONDebates(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        LinkedList<String> debates = new LinkedList<>();
        for(Object o : unJSONer.getJSONArray("debates")) {
            debates.add((String) o);
        }
        return debates;
    }

    public String getReturnType(String JSON) {
        JSONObject unJSONer = new JSONObject(new JSONTokener(JSON));
        return (String) unJSONer.get("returnType");
    }
}