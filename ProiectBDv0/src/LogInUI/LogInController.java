package LogInUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LogInController {

    /**
     * Variabilele de legatura MVC
     */
    LogInModel model;
    LogInView view;

    public LogInController(LogInModel model, LogInView view) {
        this.model = model;
        this.view = view;

        this.view.logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getModifiers() == ActionEvent.MOUSE_EVENT_MASK) {
                    view.wrongInputLabel.setVisible(true);
                    model.connect(view.logInFrame, "polyclinic_chain", view.usernameField.getText(), view.passwordField.getPassword());
                }
            }
        });

        this.view.logInButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.wrongInputLabel.setVisible(true);
                    model.connect(view.logInFrame, "polyclinic_chain", view.usernameField.getText(), view.passwordField.getPassword());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.view.usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.passwordField.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.view.passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.logInButton.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
