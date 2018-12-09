package backend;
import gui;

public class Client {
	private String serverIP = "localhost";
	private int serverPort = 7777;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;

	public Client() {
		try {
			socket = new Socket(serverIP, serverPort);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) { throw new RuntimeException(e); }

		GUI gui = new GUI(this);
		
		while(true) {
			String inputMessage = "";
			try {
				inputMessage = dataInputStream.readUTF();
				gui.getResponse(inputMessage);
			} catch (Exception e) {}
		}
	}

	public void sendMessage(String outputMessage) {
		try {
			dataOutputStream.writeUTF(outputMessage);
		} catch (Exception e) {}
    }

    public static void main(String[] args) {
		new Client();
	}
}