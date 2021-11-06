import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFirmwareDialog  extends JDialog {
    private JTextField nameField;
    private JTextField md5Field;
    private JTextField dateField;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainFirmwarePanel;
    private JTextField versionField;
    private JTextField platformField;
    public Service serviceUI;
    public FirmwareElement tempFirmwareElement;

    public NewFirmwareDialog(Gui frame, Service serviceModule) {
        super(frame, "New firmware", true);
        tempFirmwareElement = new FirmwareElement();
        serviceUI = serviceModule;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
        InitDevicesDialog();
        activateButtons();
    }

    private void InitDevicesDialog() {
        //JPanel mainDevicesPanel = new JPanel();
        //mainDevicesPanel.setLayout(new GridBagLayout());
        setContentPane(mainFirmwarePanel);
        //GridBagConstraints constraints = new GridBagConstraints();


    }

    private boolean checkTextFieldsData(FirmwareElement temp) {
        if (temp.version.equals("") || temp.date.equals("") || temp.platform.equals("") ||
                                                                   temp.md5.equals("") || temp.name.equals("")) {
            return false;
        }
        return true;
    }

    private boolean createNewFirmwareElement() {
        tempFirmwareElement.name = nameField.getText();
        nameField.setText("");
        tempFirmwareElement.md5 = md5Field.getText();
        md5Field.setText("");
        tempFirmwareElement.date = dateField.getText();
        dateField.setText("");
        tempFirmwareElement.version = versionField.getText();
        versionField.setText("");
        tempFirmwareElement.platform = platformField.getText();
        platformField.setText("");

        if (checkTextFieldsData(tempFirmwareElement)) {
            return true;
        }
        else {
            tempFirmwareElement.goToInitialValues();
            return false;
        }
    }

    private void activateButtons() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createNewFirmwareElement()) {
                    serviceUI.addNewFirmware(tempFirmwareElement);
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), "All fields must be specified!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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
