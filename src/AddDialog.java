import javax.swing.*;
import java.awt.*;

public class AddDialog extends JDialog {
    protected Gui gui;
    protected Service serviceUI;

    public AddDialog(Gui frame, String title, Service serviceModel) {
        super(frame, title, true);
        gui = frame;
        serviceUI = serviceModel;
        dialogSize();

    }

    private void dialogSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
    }
}
