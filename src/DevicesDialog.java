import javax.swing.*;
import java.awt.*;


public class DevicesDialog extends JDialog {

    private JPanel mainDevicesPanel;
    private JTextField devField;
    private JButton AddButton;
    private JList devList;

    public DevicesDialog(guif frame) {
        super(frame, "Available devices", true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
        InitDevicesDialog();
    }

    private void InitDevicesDialog() {
        //JPanel mainDevicesPanel = new JPanel();
        //mainDevicesPanel.setLayout(new GridBagLayout());
        setContentPane(mainDevicesPanel);
        //GridBagConstraints constraints = new GridBagConstraints();


    }
}