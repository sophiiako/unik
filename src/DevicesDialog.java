import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
        ActivateButton();
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
                    //update list

                }
            }
        });
    }
}