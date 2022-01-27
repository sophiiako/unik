import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.table.DefaultTableModel;


public class NewFirmwareDialog  extends AddDialog {
    private JTextField nameField;
    private JTextField md5Field;
    private JTextField dateField;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainFirmwarePanel;
    private JTextField branchField;
    private JTextField platformField;
    private JTextField stageField;
    private JTextField firmwareField;
    private JLabel firmwareLabel;
    public FirmwareElement tempFirmwareElement;
    private DefaultTableModel tableModel;
    private static final String[] availableStages = {"release", "develop"};

    public NewFirmwareDialog(DefaultTableModel Model, Gui frame, Service serviceModule) {
        super(frame, "New firmware", serviceModule);

        tableModel = Model;
        tempFirmwareElement = new FirmwareElement();
        InitDialog();

    }

    private void InitDialog() {
        setContentPane(mainFirmwarePanel);
        activateButtons();
        getRootPane().setDefaultButton(okButton);
    }

    private boolean checkStage(String stage) {
        for (int i = 0; i < availableStages.length; ++i) {
            if (stage.equals(availableStages[i])) {
                return true;
            }
        }
        return false;
    }

    private String checkTextFieldsData(FirmwareElement temp) {
        // returns error msg
        if (temp.branch.equals("") ||  temp.platform.equals("") ||
                temp.md5.equals("") || temp.name.equals("")
                || temp.date.equals("") || temp.stage.equals("")) {
            return "Error. Not enough arguments. Specify all fields!";
        }
        if (temp.date.resultDate.equals("")) {
            return "Date format is wrong. Format must be [dd.mm.yyyy]. Also year can be only 2017-2022. Check it.";
        }
        if (!checkStage(temp.stage)) {
            return "Stage can be only \'release\' or \'develop\'. Isn\'t it?";
        }

        if (!(new Devices()).isExist(temp.platform)) {
            return "This platform doesn\'t exist in available devices. If this platform is really exist, add it.";
        }
        return "";
    }

    private String createNewFirmwareElement() {
        // returns error msg
        tempFirmwareElement.name = nameField.getText();
        tempFirmwareElement.md5 = md5Field.getText();
        tempFirmwareElement.date = new fwDate(dateField.getText());
        tempFirmwareElement.stage = stageField.getText();
        tempFirmwareElement.branch = branchField.getText();
        tempFirmwareElement.platform = platformField.getText();

        // проверка полей данных
        String errorMsg = checkTextFieldsData(tempFirmwareElement);
        if (!errorMsg.equals("")) {
            return errorMsg;
        }

        String binDataFile = firmwareField.getText();

        // попытка открыть бинарник и прочесть его

        try {
            File file = new File(binDataFile);
            FileInputStream fin = new FileInputStream(binDataFile);
            byte[] firmwareBytes = new byte[(int)file.length()];
            firmwareBytes = fin.readNBytes((int)file.length());
            tempFirmwareElement.setBinData(firmwareBytes);
        }
        catch (IOException ioEx) {
            errorMsg = "Cannot read file with firmware because" + ioEx;
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
                    tableModel.insertRow(0, new Object[]{tempFirmwareElement.name,
                            tempFirmwareElement.md5, tempFirmwareElement.platform, tempFirmwareElement.date,
                            tempFirmwareElement.branch, tempFirmwareElement.stage});

                    // sort list panel
                    gui.loadFirmwareTable();

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
