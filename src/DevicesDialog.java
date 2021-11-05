import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DevicesDialog extends JDialog {

    private JPanel mainDevicesPanel;
    private JTextField devField;
    private JButton AddButton;
    private JList devList;
    private  service serviceUI;

    public DevicesDialog(guif frame, service serviceModel) {
        super(frame, "Available devices", true);
        serviceUI = serviceModel;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 250, dimension.height / 2 - 150, 500, 300);
        InitDevicesDialog();
        updatePanel();
        ActvateButton();
    }

    private void updatePanel() {
        DefaultListModel listModel = new DefaultListModel();
        for (String  item: serviceUI.devices.allDevices()) {
            listModel.addElement(item);
        }

        devList.setModel(listModel);
    }

    private void InitDevicesDialog() {
        //JPanel mainDevicesPanel = new JPanel();
        //mainDevicesPanel.setLayout(new GridBagLayout());
        setContentPane(mainDevicesPanel);
        //GridBagConstraints constraints = new GridBagConstraints();

    }

    private void ActvateButton() {
        devList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    serviceUI.deleteDevice((String)devList.getSelectedValue());
                    updatePanel();
                    //System.out.println(firmwareList.getSelectedValue());
                    //infoTextArea.setText(serviceUI.addInfoToPanel((String)firmwareList.getSelectedValue()));

                }
            }
        });

        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String device = devField.getText();
                devField.setText("");
                if (device != "") {
                    serviceUI.devices.addNewDevice(device);
                    updatePanel();
                    //update list

                }
            }
        });
    }
}