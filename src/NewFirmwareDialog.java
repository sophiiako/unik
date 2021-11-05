import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFirmwareDialog  extends JDialog {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainFirmwarePanel;
    service serviceUI;

    public NewFirmwareDialog(guif frame, service serviceModule) {
        super(frame, "New firmware", true);
        serviceUI = serviceModule;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
        InitDevicesDialog();
        activiteButtons();
    }

    private void InitDevicesDialog() {
        //JPanel mainDevicesPanel = new JPanel();
        //mainDevicesPanel.setLayout(new GridBagLayout());
        setContentPane(mainFirmwarePanel);
        //GridBagConstraints constraints = new GridBagConstraints();


    }

    private void activiteButtons() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceUI.addNewFirmware();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
