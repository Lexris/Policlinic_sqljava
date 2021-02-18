package LogInUI;

import ImageBackground.BackgroundImageJFrame;

import javax.swing.*;
import java.awt.*;

public class LogInView {
    //Declaram elementele care preiau informatii de la utilizator
    public BackgroundImageJFrame logInFrame;
    public JLabel wrongInputLabel;
    public JLabel usernameLabel;
    public JTextField usernameField;
    public JLabel passwordLabel;
    public JPasswordField passwordField;
    public JButton logInButton;

    //Creare chenar LogIn si aducere in mijloc
    public LogInView() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Window size si location
        logInFrame = new BackgroundImageJFrame("logInBackground.jpeg");
        logInFrame.setTitle("Success");
        logInFrame.setLayoutF(new BorderLayout());  //LayoutF eheree@@@@@@
        logInFrame.setSize(screenSize.width/2, screenSize.height/2);
        logInFrame.background.setSize(screenSize.width/2, screenSize.height/2);
        logInFrame.setLocation((screenSize.width-logInFrame.getWidth())/2, (screenSize.height-logInFrame.getHeight())/2);

         //Grupare chestii username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.setLayout(new FlowLayout());
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
        usernameField = new JTextField(10);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        //Grupare chestii pw
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new FlowLayout());
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
        passwordField = new JPasswordField(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        //Aranjare buton login
        logInButton = new JButton("LOG IN");
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        Dimension d1 = new Dimension(0, 0);
        Dimension d2 = new Dimension(logInFrame.getWidth()/2, logInButton.getHeight());
        Dimension d3 = new Dimension(logInFrame.getWidth()/2, logInButton.getHeight());
        buttonBox.add(new Box.Filler(d1, d2, d3));
        buttonBox.add(logInButton);
        buttonBox.add(new Box.Filler(d1, d2, d3));

         //Wrong input label initialization
        wrongInputLabel = new JLabel("Incorrect account data, please try again.");
        wrongInputLabel.setForeground(Color.yellow);
        wrongInputLabel.setVisible(false);
        Box textBox = new Box(BoxLayout.X_AXIS);
        textBox.add(new Box.Filler(d1, d2, d3));
        textBox.add(wrongInputLabel);
        textBox.add(new Box.Filler(d1, d2, d3));

         //Aranjare elemente login in panel
        JPanel logInPanel = new JPanel();
        logInPanel.setOpaque(false);
        logInPanel.setLayout(new GridLayout(4, 1, 0, 0));
        logInPanel.add(textBox);
        logInPanel.add(usernamePanel);
        logInPanel.add(passwordPanel);
        logInPanel.add(buttonBox);

         //Aranjare panel cu chestii login in mojloc pt aspect
        Box logInBox = new Box(BoxLayout.Y_AXIS);
        d1 = new Dimension(0, 0);
        d2 = new Dimension(logInFrame.getWidth(), logInFrame.getHeight());
        d3 = new Dimension(logInFrame.getWidth(), logInFrame.getHeight());
        logInBox.add(new Box.Filler(d1, d2, d3));
        logInBox.add(logInPanel);
        logInBox.add(new Box.Filler(d1, d2, d3));

        //Wrapping everything up and Baaaaaaaaaaaam!!! @@@@@ROUND OF APPLOUSE
        //logInFrame.getContentPane().setBackground(Color.darkGray);
        logInFrame.addTwo(logInBox, BorderLayout.CENTER);
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setVisible(true);
    }
}
