import javax.swing.*;
import java.awt.*;

public class JoinPage extends JFrame {
    private JPanel rootPanel;
    private JButton join;
    private JButton create;
    private JButton prevButton;
    private JButton nextButton;

    public JoinPage()
    {
        setLayout(new BorderLayout());


        /*JScrollPane sp = new JScrollPane();
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        JButton debate1 = new JButton();
        debate1.setAlignmentX(this.CENTER_ALIGNMENT);
        debate1.setText("fdafsgasdgs");
        JButton debate2 = new JButton();
        debate2.setAlignmentX(this.CENTER_ALIGNMENT);
        boxPanel.add(debate1);
        boxPanel.add(debate2);
        sp.setViewportView(boxPanel);
        rootPanel.add(sp, BorderLayout.CENTER);
        add(rootPanel);*/


        setTitle("Join Page");
        setSize(400, 500);

        //JPanel boxPanel = new JPanel();
        //boxPanel.setLayout(new GridLayout(10,1));
        //JList<JButton> boxPanel = new JList<>();

        /*JButton button1 = new JButton("1");
        JButton button2 = new JButton("1");
        JButton button3 = new JButton("1");
        JButton button4 = new JButton("1");
        JButton button5 = new JButton("1");
        JButton button6 = new JButton("1");
        JButton button7 = new JButton("1");
        JButton button8 = new JButton("8");
        JButton button9 = new JButton("1");
        JButton button10 = new JButton("10");

        boxPanel.add(button1);
        boxPanel.add(button2);
        boxPanel.add(button3);
        boxPanel.add(button4);
        boxPanel.add(button5);
        boxPanel.add(button6);
        boxPanel.add(button7);
        boxPanel.add(button8);
        boxPanel.add(button9);
        boxPanel.add(button10);*/

        String[] debates = {"debate1", "debate2", "debate3"};
        JList<String> list = new JList<>(debates);



        rootPanel.add(list, BorderLayout.CENTER);
        add(rootPanel);
    }
}
