import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LastPage extends JFrame{
    private JPanel rootPanel;
    private JLabel Title;
    private JTextField textField1;

    public LastPage()
    {
        add(rootPanel);
        setTitle("DebatePage");
        setSize(400, 500);
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER){
                    System.out.println(textField1.getText());
                }
            }
        });
    }

}
