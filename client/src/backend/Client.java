package backend;

import org.json.JSONObject;
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
        this.serverIP = serverIP;
        this.serverPort = serverPort;

        try {
            socket = new Socket(serverIP, serverPort);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        while(true) {
            String inputMessage = "";
            try {
                inputMessage = dataInputStream.readUTF();
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }
    }

    public void sendMessage(String outputMessage) {
        try {
            String message = createMessageJSON(outputMessage);
            dataOutputStream.writeUTF(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String createMessageJSON(String message) {
        StringBuffer JSONMessage = new StringBuffer();
        new JSONWriter(JSONMessage)
                .object()
                    .key("name").value(username)
                    .key("message").value(message)
                .endObject();
        return JSONMessage.toString();
    }
}
