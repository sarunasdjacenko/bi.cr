import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList; 
import java.util.LinkedList;

public class Debate {
    private String debateName; //not doing anything yet
    
    private List<Socket> forArgument;
    private List<Socket> againstArgument;
    private List<String> messagesList;
    private DataOutputStream dataOutputStream;

    public Debate(String debateName) {
        this.debateName = debateName;
        forArgument = new ArrayList<>();
        againstArgument = new ArrayList<>();
        messagesList = new LinkedList<>();
    }

    public void addClient(Socket newClient, boolean isForArgument) {
        if (isForArgument) forArgument.add(newClient);
        else againstArgument.add(newClient);
    }

    public void sendMessage(String message) {
        // append time to message
        messagesList.add(message);

        for(Socket client : forArgument) {
            try {
                dataOutputStream = new DataOutputStream(client.getOutputStream());
                dataOutputStream.writeUTF(message);
            } catch(Exception e) { e.printStackTrace(); }
        }
        for(Socket client : againstArgument) {
            try {
                dataOutputStream = new DataOutputStream(client.getOutputStream());
                dataOutputStream.writeUTF(message);
            } catch(Exception e) { e.printStackTrace(); }
        }
    }
}
