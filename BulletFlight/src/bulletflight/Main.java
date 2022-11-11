package bulletflight;

import javax.swing.SwingUtilities;
import view.MainFrame;

/**
 *
 * @author maurizio
 * @since 2016.05.22
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MainFrame();
            }
        });
    }
}
