import javax.swing.*;
import java.awt.Font;

public class FirstPage extends JFrame {
    private JPanel rootPanel;
    private JTextField textField1;
    private JLabel username;

    public FirstPage() {
        username.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        add(rootPanel);
        setTitle("DebatePage");
        setSize(400, 500);
    }
}
