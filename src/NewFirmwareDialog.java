import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFirmwareDialog  extends JDialog {
    private Gui gui;
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
    private DefaultListModel listModel;

    public NewFirmwareDialog(DefaultListModel Model, Gui frame, Service serviceModule) {
        super(frame, "New firmware", true);
        gui = frame;
        getRootPane().setDefaultButton(okButton);
        listModel = Model;
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

    private String checkTextFieldsData(FirmwareElement temp) {
        // returns error msg
        if (temp.version.equals("") ||  temp.platform.equals("") ||
                                                                   temp.md5.equals("") || temp.name.equals("")) {
            return "Error. Not enough arguments. Specify all fields!";
        }
        if (temp.date.resultDate.equals("")) {
            return "Date format is wrong. Format must be [day.month.year]. Also year can be only 2017-2021. Check it.";
        }

        if (!(new Devices()).isExist(temp.platform)) {
            return "This platform doesn\'t exist in available devices. If this platform is really exist, Add it.";
        }
        return "";
    }

    private String createNewFirmwareElement() {
        // returns error msg
        tempFirmwareElement.name = nameField.getText();
        nameField.setText("");
        tempFirmwareElement.md5 = md5Field.getText();
        md5Field.setText("");
        tempFirmwareElement.date = new Date(dateField.getText());
        dateField.setText("");
        tempFirmwareElement.version = versionField.getText();
        versionField.setText("");
        tempFirmwareElement.platform = platformField.getText();
        platformField.setText("");
        String errorMsg = checkTextFieldsData(tempFirmwareElement);

        if (!errorMsg.equals("")) {
            tempFirmwareElement.goToInitialValues();
        }
        return errorMsg;
    }

    private void activateButtons() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String errMsg = createNewFirmwareElement();
                if (errMsg.equals("")) {
                    serviceUI.addNewFirmware(tempFirmwareElement);
                    listModel.addElement(tempFirmwareElement.name);

                    // sort list panel
                    gui.loadFirmwareList();

                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(), errMsg, "Error",
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
