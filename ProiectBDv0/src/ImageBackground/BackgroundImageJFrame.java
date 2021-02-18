package ImageBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Creare subclasa frame pt a implementa optiunea de background
 */
public class BackgroundImageJFrame extends JFrame {
    public JLabel background = null;

    public BackgroundImageJFrame(String imageName) {
        setLayout(new BorderLayout());
        try {
            background = new JLabel(new ImageIcon(ImageIO.read(new File(imageName))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Aici adaugam la frame backgroundul, iar apoi lucram doar pe background
        this.add(background);
        background.setLayout(new FlowLayout());
    }

    /**
     * Metoda care adauga obiectele peste background, nu doar in frame, pt a nu ajunge noile obiecte sub background
     * @param comp e adaugat la label(pus pe frame)
     * @return returneaza componenta adaugata
     */
    public Component addTwo(Component comp) {
        this.background.add(comp);
        return comp;
    }

    /**
     * Metoda care adauga obiectele peste background, nu doar in frame, pt a nu ajunge noile obiecte sub background
     * @param comp se adauga comp la label adica pe frame
     * @param constraints constraints
     */
    public void addTwo(Component comp, Object constraints) {
        this.background.add(comp, constraints);
    }

    /**
     * Sets layout of background insatead of frame(which is of not interest anymroe)
     * @param mgr tipul de layout
     */
    public void setLayoutF(LayoutManager mgr) {
        try {
            this.background.setLayout(mgr);
        }
        catch(NullPointerException e) {}
    }

    /*public static void main(String args[]) {
        BackgroundImageJFrame a = new BackgroundImageJFrame();
        a.setSize(400,400);
        a.setVisible(true);
    }*/
}
