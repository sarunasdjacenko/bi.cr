package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class GUI {
    private JTextArea textArea;

	public GUI(Client client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		JTextField textField = new JTextField();
		textField.requestFocusInWindow();
		textField.addActionListener(e -> {
			String str = textField.getText();
			if(!str.equals("")) {
				client.sendMessage(textField.getText());
				textField.setText("");
			}
		});
		JButton button = new JButton("Send");
		button.addActionListener(e -> {
			String str = textField.getText();
			if(!str.equals("")) {
				client.sendMessage(textField.getText());
				textField.setText("");
			}
		});

		bottomPanel.add(textField, BorderLayout.CENTER);
		bottomPanel.add(button, BorderLayout.EAST);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);

		add(panel);
		setVisible(true);
	}

	public void getResponse(String str) {
		textArea.append(str);
	}
}