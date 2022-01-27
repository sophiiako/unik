import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DevicesDialog extends AddDialog {

    private JPanel mainDevicesPanel;
    private JTextField devField;
    private JButton AddButton;
    private JList devList;


    public DevicesDialog(Gui frame, Service serviceModule) {
        super(frame, "Available platforms", serviceModule);

        InitDialog();
    }

    private void updatePanel() {
        DefaultListModel listModel = new DefaultListModel();
        for (String  item: serviceUI.devices.allDevices()) {
            listModel.addElement(item);
        }

        devList.setModel(listModel);
    }

    private void InitDialog() {
        updatePanel();
        ActivateButton();
        getRootPane().setDefaultButton(AddButton);
        setContentPane(mainDevicesPanel);
    }

    private void ActivateButton() {
        devList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem del = new JMenuItem("Delete");
                popup.add(del);
                del.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        serviceUI.deleteDevice((String)devList.getSelectedValue());
                        updatePanel();
                    }
                });
                devList.setComponentPopupMenu(popup);
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

                }
            }
        });
    }
}