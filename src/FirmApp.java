import javax.swing.*;
import java.awt.*;

public class FirmApp {
    public static void main(String[] args) {
        Service serviceUI = new Service();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ignored){}
        EventQueue.invokeLater(() -> {

            var ex = new Gui(serviceUI,"The best application of your life!");
            ex.setVisible(true);




        });
    }
}
